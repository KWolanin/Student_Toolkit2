package wolanin.studentToolkit;

import wolanin.studentToolkit.db.BooksDAO;

import java.io.IOException;

import static wolanin.studentToolkit.frames.MainFrame.session;
import static wolanin.studentToolkit.language.LangProperties.setProperties;

public class BookLogic extends BooksDAO implements ChooseAction {
	public BookLogic() throws IOException {
	}

	@Override
	public void doAction(String actionCommand) throws IOException {
		if (setProperties().getProperty("book.add").equals(actionCommand)) {
			add(session);
		} else if (setProperties().getProperty("book.delete").equals(actionCommand)) {
			delete(session);
		}
	}
}
