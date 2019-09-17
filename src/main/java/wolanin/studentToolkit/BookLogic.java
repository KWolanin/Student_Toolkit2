package wolanin.studentToolkit;

import java.io.IOException;

import static wolanin.studentToolkit.language.LangProperties.setProperties;

public class BookLogic implements ChooseAction {
	@Override
	//todo
	public void doAction(String actionCommand) throws IOException {
		if (setProperties().getProperty("book.add").equals(actionCommand)) {
			//todo
		} else if (setProperties().getProperty("book.delete").equals(actionCommand)) {
			//todo
		}
	}
}
