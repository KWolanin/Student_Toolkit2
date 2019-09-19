package wolanin.studentToolkit;

import wolanin.studentToolkit.db.ExamsDAO;

import java.io.IOException;

import static wolanin.studentToolkit.frames.MainFrame.session;
import static wolanin.studentToolkit.language.LangProperties.setProperties;

public class ExamLogic extends ExamsDAO implements ChooseAction {

	public ExamLogic() {
	}

	@Override
	public void doAction(String actionCommand) throws IOException {
		if (setProperties().getProperty("exam.add").equals(actionCommand)) {
			add(session);
		} else if (setProperties().getProperty("exam.delete").equals(actionCommand)) {
			delete(session);
		}
	}

}