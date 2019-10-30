package wolanin.studentToolkit.db;

import org.hibernate.Query;
import org.hibernate.Session;
import wolanin.studentToolkit.frames.BooksFrame;
import wolanin.studentToolkit.frames.MainFrame;
import wolanin.studentToolkit.table.TableFormatter;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;
import static wolanin.studentToolkit.frames.BooksFrame.*;
import static wolanin.studentToolkit.frames.ExamFrame.changeDateFormat;
import static wolanin.studentToolkit.frames.MainFrame.bookTable;
import static wolanin.studentToolkit.frames.MainFrame.booksPanel;
import static wolanin.studentToolkit.language.LangProperties.setProperties;
import static wolanin.studentToolkit.table.TableFormatter.setTableModelProp;
import static wolanin.studentToolkit.table.TableFormatter.tableModel;

public class BooksDAO implements HibernateDBFlow {

	public static float penaltyValuePerDay = 0.2f;

	private final String[] columnNames = new String[]
			{setProperties().getProperty("book.title"),
					setProperties().getProperty("book.author"),
					setProperties().getProperty("book.borrowed"),
					setProperties().getProperty("book.returnTo"),
					setProperties().getProperty("book.isPenalty"),
					setProperties().getProperty("book.penalty")
			};

	public BooksDAO() throws IOException {
	}


	@Override
	public void showAll(Session session) throws IOException {
		setTableModelProp(columnNames);
		@SuppressWarnings("unchecked")
		List<Books> books = (List<Books>) session.createQuery("from Books").list();
		for (Books value : books) {
			String title = value.getTitle();
			String author = value.getAuthor();
			Date borrowed = value.getBorrowed();
			Date returnTo = value.getReturnTo();
			String isPenalty;
			if (value.isPenalty()) {
				isPenalty = setProperties().getProperty("yes");
			} else {
				isPenalty = setProperties().getProperty("no");
			}
			double penalty = value.getPenaltyValue();
			String formattedPenalty = String.format("%.2f", penalty);
			String[] data = {title, author, String.valueOf(borrowed), String.valueOf(returnTo), String.valueOf(isPenalty), formattedPenalty};
			tableModel.addRow(data);
		}
		TableFormatter.setTableProp(booksPanel, bookTable, tableModel);
	}

	@Override
	public void delete(Session session) throws IOException {
		int row;
		String selectedTitle = "";
		String selectedAuthor = "";
		try {
			row = bookTable.getSelectedRow();
			selectedTitle = String.valueOf(bookTable.getValueAt(row, 0));
			selectedAuthor = String.valueOf(bookTable.getValueAt(row, 1));
		} catch (IndexOutOfBoundsException e) {
			showMsg("select.books.first", "remove.book");
		}
		Query query = session.createQuery("delete from Books where title=:title and author=:author");
		query.setParameter("title", selectedTitle);
		query.setParameter("author", selectedAuthor);
		query.executeUpdate();
		showAll(session);
	}

	@Override
	public void add(Session session) throws IOException {
		new BooksFrame().setVisible(true);
		showAll(MainFrame.session);
	}

	public void addToBase(Session session) throws IOException {
		double savedPenaltyValue;
		boolean isPenalty;
		Date formattedReturnToDate, formattedBorrowedDate;
		String savedTitle = titleField.getText();
		String savedAuthor = authorField.getText();
		String savedBorrowed = borrowedPicker.getFormattedTextField().getText();
		formattedBorrowedDate = Date.valueOf(changeDateFormat(savedBorrowed));
		String savedReturnTo = returnToPicker.getFormattedTextField().getText();
		formattedReturnToDate = Date.valueOf(changeDateFormat(savedReturnTo));
		isPenalty = !requireNonNull(penaltyCombo.getSelectedItem()).toString().equals(setProperties().getProperty("no"));
		if (isPenalty) {
			savedPenaltyValue = Double.parseDouble(penaltyField.getText());
		} else {
			savedPenaltyValue = 0.0;
		}
		if (savedAuthor.equals("") | savedTitle.equals("") | formattedBorrowedDate == null | formattedReturnToDate == null) {
			showMsg("badInputMsg", "add.book");
		} else {
			session.beginTransaction();
			Books books = new Books();
			books.setTitle(savedTitle);
			books.setAuthor(savedAuthor);
			books.setBorrowed(formattedBorrowedDate);
			books.setReturnTo(formattedReturnToDate);
			books.setPenalty(isPenalty);
			books.setPenaltyValue(savedPenaltyValue);
			session.save(books);
			session.getTransaction().commit();
		}
		checkPenalty(session);
	}

	private void showMsg(String propertyName, String windowTitle) throws IOException {
		showMessageDialog(null, setProperties().getProperty(propertyName),
				setProperties().getProperty(windowTitle), INFORMATION_MESSAGE);
	}

	protected void checkPenalty(Session session) throws IOException {
		boolean penaltyFlag = false;
		LocalDate todayDate = LocalDate.now();
		@SuppressWarnings("unchecked")
		List<Books> books = (List<Books>) session.createQuery("from Books").list();
		for (Books value : books) {
			Date date = value.getReturnTo();
			LocalDate returnTo = date.toLocalDate();
			if (todayDate.isAfter(returnTo)) {
				penaltyFlag = true;
				float penaltyValue = ChronoUnit.DAYS.between(returnTo, todayDate) * penaltyValuePerDay;
				session.beginTransaction();
				value.setPenalty(true);
				value.setPenaltyValue(penaltyValue);
				session.getTransaction().commit();
			}
		}
		showPenaltyMessage(penaltyFlag);
		showAll(session);
	}

	private void showPenaltyMessage(boolean penaltyFlag) throws IOException {
		if (penaltyFlag) {
			showMsg("isPenaltyMsg", "book.checkPenalty");
		} else {
			showMsg("isNotPenaltyMsg", "book.checkPenalty");
		}
	}
}


