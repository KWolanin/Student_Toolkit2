package wolanin.studentToolkit;

import wolanin.studentToolkit.db.ClassesDAO;

import javax.swing.*;
import java.io.IOException;

import static wolanin.studentToolkit.frames.MainFrame.session;
import static wolanin.studentToolkit.language.LangProperties.setProperties;

public class ClassLogic implements ChooseAction {

	public ClassLogic() {
	}

	@Override
	public void doAction(String actionCommand) throws IOException {
		ClassesDAO classesDAO = new ClassesDAO();
		if (setProperties().getProperty("schedule.today").equals(actionCommand)) {
			classesDAO.showToday(session);
		} else if (setProperties().getProperty("schedule.tommorow").equals(actionCommand)) {
			classesDAO.showTommorow(session);
		} else if (setProperties().getProperty("schedule.week").equals(actionCommand)) {
			classesDAO.showAll(session);
		} else if (setProperties().getProperty("schedule.add").equals(actionCommand)) {
			classesDAO.add(session);
		} else if (setProperties().getProperty("schedule.delete").equals(actionCommand)) {
			try {
				classesDAO.delete(session);
			} catch (IndexOutOfBoundsException | NullPointerException ex) {
				JOptionPane.showMessageDialog(null, setProperties().getProperty("select.weekly.first"),
						setProperties().getProperty("remove.classes.title"), JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
}