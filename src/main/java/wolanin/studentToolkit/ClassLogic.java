package wolanin.studentToolkit;

import wolanin.studentToolkit.db.ClassesDAO;

import javax.swing.*;
import java.io.IOException;

import static wolanin.studentToolkit.frames.MainFrame.session;
import static wolanin.studentToolkit.language.LangProperties.setProperties;

public class ClassLogic extends ClassesDAO implements ChooseAction {

	public ClassLogic() throws IOException {
		super();
	}

	@Override
	public void doAction(String actionCommand) throws IOException {
		if (setProperties().getProperty("schedule.today").equals(actionCommand)) {
			showToday(session);
		} else if (setProperties().getProperty("schedule.tommorow").equals(actionCommand)) {
			showTommorow(session);
		} else if (setProperties().getProperty("schedule.week").equals(actionCommand)) {
			showAll(session);
		} else if (setProperties().getProperty("schedule.add").equals(actionCommand)) {
			add(session);
		} else if (setProperties().getProperty("schedule.delete").equals(actionCommand)) {
			try {
				delete(session);
			} catch (IndexOutOfBoundsException | NullPointerException ex) {
				JOptionPane.showMessageDialog(null, setProperties().getProperty("select.weekly.first"),
						setProperties().getProperty("remove.classes.title"), JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
}