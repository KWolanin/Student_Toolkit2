package wolanin.studentToolkit;

import wolanin.studentToolkit.db.GradesDAO;

import javax.swing.*;

import java.io.IOException;

import static wolanin.studentToolkit.frames.MainFrame.gradesTable;
import static wolanin.studentToolkit.frames.MainFrame.session;
import static wolanin.studentToolkit.language.LangProperties.setProperties;

public class GradeLogic extends GradesDAO implements ChooseAction {

	public GradeLogic() throws IOException {
		super();
	}

	@Override
	public void doAction(String actionCommand) throws IOException {
		if (setProperties().getProperty("grade.average").equals(actionCommand)) {
			calcAverage(session);
		} else if (setProperties().getProperty("grade.add").equals(actionCommand)) {
			add(session);
		} else if (setProperties().getProperty("grade.delete").equals(actionCommand)) {
			try {
				delete(session);
			} catch (NullPointerException | IndexOutOfBoundsException x) {
				JOptionPane.showMessageDialog(null, setProperties().getProperty("select.grade.first"),
						setProperties().getProperty("remove.title"), JOptionPane.INFORMATION_MESSAGE);
			}
		} else if (setProperties().getProperty("grade.fulllist").equals(actionCommand)) {
			try {
				showAll(session);
				gradesTable.updateUI();
			} catch (NullPointerException ex) {
				ex.printStackTrace();
			}
		} else if (setProperties().getProperty("grade.showUnpassed").equals(actionCommand)) {
			showFailed(session);
		}
	}
}
