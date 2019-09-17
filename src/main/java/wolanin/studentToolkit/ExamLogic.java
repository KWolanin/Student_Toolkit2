package wolanin.studentToolkit;

import wolanin.studentToolkit.db.ExamsDAO;

import java.io.IOException;

import static wolanin.studentToolkit.frames.MainFrame.session;
import static wolanin.studentToolkit.language.LangProperties.setProperties;

public class ExamLogic implements ChooseAction {

	public ExamLogic() {
	}

	@Override
	public void doAction(String actionCommand) throws IOException {
		ExamsDAO em = new ExamsDAO();
		if (setProperties().getProperty("exam.add").equals(actionCommand)) {
			em.add(session);
		} else if (setProperties().getProperty("exam.delete").equals(actionCommand)) {
			em.delete(session);
		}
	}

}