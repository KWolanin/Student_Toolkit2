package wolanin.studentToolkit.db;

import org.hibernate.Query;
import org.hibernate.Session;
import wolanin.studentToolkit.frames.BooksFrame;
import wolanin.studentToolkit.frames.MainFrame;
import wolanin.studentToolkit.table.TableFormatter;

import java.io.IOException;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static javax.swing.JOptionPane.*;
import static wolanin.studentToolkit.frames.BooksFrame.*;
import static wolanin.studentToolkit.frames.MainFrame.*;
import static wolanin.studentToolkit.language.LangProperties.setProperties;
import static wolanin.studentToolkit.table.TableFormatter.setTableModelProp;
import static wolanin.studentToolkit.table.TableFormatter.tableModel;

public class BooksDAO implements HibernateDBFlow {

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
		String isPenalty;
		setTableModelProp(columnNames);
		@SuppressWarnings("unchecked")
		List<Books> books = (List<Books>) session.createQuery("from Books").list();
		for (Books value : books) {
			String title = value.getTitle();
			String author = value.getAuthor();
			String borrowed = value.getBorrowed();
			String returnTo = value.getReturnTo();
			if (value.isPenalty()) {
				isPenalty = setProperties().getProperty("yes");
			} else {
				isPenalty = setProperties().getProperty("no");
			}
			String penalty = String.valueOf(value.getPenalty());
			String[] data = {title, author, borrowed, returnTo, isPenalty, penalty};
			tableModel.addRow(data);
		}
		TableFormatter.setTableProp(booksPanel, bookTable, tableModel);
	}

	@Override
	public void delete(Session session) throws IOException {
		int row = bookTable.getSelectedRow();
		String selectedTitle = String.valueOf(bookTable.getValueAt(row, 0));
		String selectedAuthor = String.valueOf(bookTable.getValueAt(row, 1));
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
		double savedPenalty;
		boolean isPenalty;
		String savedTitle = BooksFrame.titleField.getText();
		String savedAuthor = BooksFrame.authorField.getText();
		String savedBorrowed = borrowedPicker.getFormattedTextField().getText();
		String savedReturnTo = returnToPicker.getFormattedTextField().getText();
		isPenalty = !requireNonNull(penaltyCombo.getSelectedItem()).toString().equals(setProperties().getProperty("no"));
		if (isPenalty) {
			savedPenalty = Double.parseDouble(penaltyField.getText());
		} else {
			savedPenalty = 0.0;
		}
		if (savedAuthor.equals("") | savedTitle.equals("") | savedBorrowed.equals("") | savedReturnTo.equals("")) {
			showMessageDialog(null, setProperties().getProperty("badInputMsg"),
					setProperties().getProperty("add.book"), INFORMATION_MESSAGE);
		} else {
			session.beginTransaction();
			Books books = new Books();
			books.setTitle(savedTitle);
			books.setAuthor(savedAuthor);
			books.setBorrowed(savedBorrowed);
			books.setReturnTo(savedReturnTo);
			books.setPenalty(isPenalty);
			books.setPenalty(savedPenalty);
			session.save(books);
			session.getTransaction().commit();
		}
	}
}