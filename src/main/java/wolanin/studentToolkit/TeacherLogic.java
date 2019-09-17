package wolanin.studentToolkit;

import wolanin.studentToolkit.db.TeacherDAO;

import javax.swing.*;
import java.io.IOException;
import java.net.URISyntaxException;

import static wolanin.studentToolkit.frames.MainFrame.session;
import static wolanin.studentToolkit.language.LangProperties.setProperties;

public class TeacherLogic implements ChooseAction {

	public TeacherLogic() {
	}

	@Override
	public void doAction(String actionCommand) throws IOException {
		TeacherDAO teacherDAO = new TeacherDAO();
		if (setProperties().getProperty("teacher.add").equals(actionCommand)) {
			teacherDAO.add(session);
			teacherDAO.showAll(session);
		} else if (setProperties().getProperty("teacher.delete").equals(actionCommand)) {
			try {
				teacherDAO.delete(session);
			} catch (NullPointerException | IndexOutOfBoundsException a) {
				JOptionPane.showMessageDialog(null, setProperties().getProperty("select.teacher.first"),
						setProperties().getProperty("remove.teacher.title"), JOptionPane.INFORMATION_MESSAGE);
			}
		} else if (setProperties().getProperty("teacher.emailTo").equals(actionCommand)) {
			try {
				teacherDAO.emailTo();
			} catch (NullPointerException | IndexOutOfBoundsException n) {
				JOptionPane.showMessageDialog(null, setProperties().getProperty("select.teacher.first"),
						setProperties().getProperty("email.title"), JOptionPane.INFORMATION_MESSAGE);
			} catch (IOException | URISyntaxException ex) {
				ex.printStackTrace();
			}
		}
	}
}
