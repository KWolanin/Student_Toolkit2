package wolanin.studentToolkit.db;

import org.hibernate.Session;
import wolanin.studentToolkit.table.TableFormatter;

import java.io.IOException;
import java.util.List;

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
	public void showAll(Session session) {

		setTableModelProp(columnNames);
		@SuppressWarnings("unchecked")
		List<Books> books = (List<Books>) session.createQuery("from Books").list();
		for (Books value : books) {
			String title = value.getTitle();
			String author = value.getAuthor();
			String borrowed = value.getBorrowed();
			String returnTo = value.getReturnTo();
			String isPenalty = String.valueOf(value.isPenalty());
			String penalty = String.valueOf(value.getPenalty());
			String[] data = {title, author, borrowed, returnTo, isPenalty, penalty};
			tableModel.addRow(data);
		}
		TableFormatter.setTableProp(booksPanel, bookTable, tableModel);
	}

	@Override
	public void delete(Session session) {

	}

	@Override
	public void add(Session session) {

	}
}
