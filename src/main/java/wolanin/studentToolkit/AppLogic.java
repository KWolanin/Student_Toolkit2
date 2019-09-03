package wolanin.studentToolkit;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.text.ParseException;

import static wolanin.studentToolkit.MainFrame.*;
import static wolanin.studentToolkit.language.LangProperties.setProperties;

public class AppLogic {

	@SuppressWarnings("ignored")
	static void createFileDir() {
		File dir = new File("Notatki");
		if (!dir.exists()) {
			dir.mkdir();
		}
	}

	public static void listenerChooser(ActionEvent e) throws IOException {
		String tmp = e.getActionCommand();
		if (setProperties().getProperty("grade.average").equals(tmp)) {
			try {
				grade.calcAverage();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		} else if (setProperties().getProperty("grade.add").equals(tmp)) {
			grade.addToBase(con);
		} else if (setProperties().getProperty("grade.delete").equals(tmp)) {
			try {
				grade.deleteFromBase(con);
			} catch (NullPointerException | IndexOutOfBoundsException x) {
				JOptionPane.showMessageDialog(null, "Najpierw wybierz ocenę!", "Usuwanie oceny", JOptionPane.INFORMATION_MESSAGE);
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		} else if (setProperties().getProperty("grade.fulllist").equals(tmp)) {
			try {
				grade.showGrades(con);
			} catch (SQLException | NullPointerException ex) {
				ex.printStackTrace();
			}
		} else if (setProperties().getProperty("grade.showUnpassed").equals(tmp)) {
			try {
				grade.showFailed();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		} else if (setProperties().getProperty("teacher.add").equals(tmp)) {
			//teacher.addTeacher(con);
			teacher.addToBase(con);
		} else if (setProperties().getProperty("teacher.delete").equals(tmp)) {
			try {
				teacher.deleteFromBase(con);
			} catch (NullPointerException | IndexOutOfBoundsException a) {
				JOptionPane.showMessageDialog(null, "Najpierw wybierz wykładowcę!", "Usuwanie wykładowcy", JOptionPane.INFORMATION_MESSAGE);
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		} else if (setProperties().getProperty("teacher.emailTo").equals(tmp)) {
			try {
				teacher.emailTo();
			} catch (NullPointerException | ArrayIndexOutOfBoundsException n) {
				JOptionPane.showMessageDialog(null, "Najpierw wybierz wykładowcę!", "Wysyłanie e-maila", JOptionPane.INFORMATION_MESSAGE);
			} catch (IOException | URISyntaxException ex) {
				ex.printStackTrace();
			}
		} else if (setProperties().getProperty("exam.add").equals(tmp)) {
			exam.addToBase(con);
		} else if (setProperties().getProperty("exam.delete").equals(tmp)) {
			try {
				exam.deleteFromBase(con);
			} catch (NullPointerException | IndexOutOfBoundsException ee) {
				JOptionPane.showMessageDialog(null, "Najpierw wybierz zaliczenie!", "Usuwanie zaliczenia", JOptionPane.INFORMATION_MESSAGE);
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		} else if (setProperties().getProperty("schedule.today").equals(tmp)) {
			try {
				schedule.showTodaySchedule(con);
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		} else if (setProperties().getProperty("schedule.tommorow").equals(tmp)) {
			try {
				schedule.showTommorowSchedule(con);
			} catch (ParseException | SQLException ex) {
				ex.printStackTrace();
			}
		} else if (setProperties().getProperty("schedule.week").equals(tmp)) {
			try {
				schedule.showWeekSchedule(con);
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		} else if (setProperties().getProperty("schedule.add").equals(tmp)) {
			schedule.addToBase(con);
		} else if (setProperties().getProperty("schedule.delete").equals(tmp)) {
			try {
				schedule.deleteFromBase(con);
			} catch (IndexOutOfBoundsException | NullPointerException ex) {
				JOptionPane.showMessageDialog(null, "Najpierw wybierz zajęcia w widoku tygodniowym!", "Usuwanie zajęć", JOptionPane.INFORMATION_MESSAGE);
			} catch (SQLException es) {
				es.printStackTrace();
			}
		} else if (setProperties().getProperty("note.create").equals(tmp)) {
			notes.clearNoteArea();
		} else if (setProperties().getProperty("note.open").equals(tmp)) {
			try {
				notes.openFile();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		} else if (setProperties().getProperty("note.save").equals(tmp)) {
			try {
				notes.saveFile();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		} else if (setProperties().getProperty("note.openFolder").equals(tmp)) {
			try {
				notes.openDir();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		} else {
			throw new IllegalStateException("Unexpected value: " + tmp);
		}
	}

}
