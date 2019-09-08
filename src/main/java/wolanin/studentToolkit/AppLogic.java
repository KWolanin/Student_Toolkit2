package wolanin.studentToolkit;

import wolanin.studentToolkit.dbHibernate.ClassesDAO;
import wolanin.studentToolkit.dbHibernate.ExamsDAO;
import wolanin.studentToolkit.dbHibernate.GradesDAO;
import wolanin.studentToolkit.dbHibernate.TeacherDAO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;


import static wolanin.studentToolkit.frames.MainFrame.*;
import static wolanin.studentToolkit.language.LangProperties.setProperties;

public class AppLogic {

	private static final TeacherDAO t = new TeacherDAO();
	private static final GradesDAO g = new GradesDAO();
	private static final ExamsDAO em = new ExamsDAO();
	private static final ClassesDAO c = new ClassesDAO();


	@SuppressWarnings("Result of method call ignored")
	public static void createFileDir() {
		File dir = new File("UserNotes");
		if (!dir.exists()) {
			dir.mkdir();
		}
	}

	public static void listenerChooser(ActionEvent e) throws IOException {
		String tmp = e.getActionCommand();
		if (setProperties().getProperty("grade.average").equals(tmp)) {
			g.calcAverageByHibernate(session);
		} else if (setProperties().getProperty("grade.add").equals(tmp)) {
			g.add(session);
		} else if (setProperties().getProperty("grade.delete").equals(tmp)) {
			try {
				g.delete(session);
			} catch (NullPointerException | IndexOutOfBoundsException x) {
				JOptionPane.showMessageDialog(null, "Najpierw wybierz ocenę!", "Usuwanie oceny", JOptionPane.INFORMATION_MESSAGE);
			}
		} else if (setProperties().getProperty("grade.fulllist").equals(tmp)) {
			try {
				g.showAll(session);
				gradesTable.updateUI();
			} catch (NullPointerException ex) {
				ex.printStackTrace();
			}
		} else if (setProperties().getProperty("grade.showUnpassed").equals(tmp)) {
			g.showFailedByHibernate(session);

		} else if (setProperties().getProperty("teacher.add").equals(tmp)) {
			t.add(session);
			t.showAll(session);
		} else if (setProperties().getProperty("teacher.delete").equals(tmp)) {
			try {
				t.delete(session);
			} catch (NullPointerException | IndexOutOfBoundsException a) {
				JOptionPane.showMessageDialog(null, "Najpierw wybierz wykładowcę!", "Usuwanie wykładowcy", JOptionPane.INFORMATION_MESSAGE);
			}
		} else if (setProperties().getProperty("teacher.emailTo").equals(tmp)) {
			try {
				t.emailTo();
			} catch (NullPointerException | IndexOutOfBoundsException n) {
				JOptionPane.showMessageDialog(null, "Najpierw wybierz wykładowcę!", "Wysyłanie e-maila", JOptionPane.INFORMATION_MESSAGE);
			} catch (IOException | URISyntaxException ex) {
				ex.printStackTrace();
			}
		} else if (setProperties().getProperty("exam.add").equals(tmp)) {
			em.add(session);
		} else if (setProperties().getProperty("exam.delete").equals(tmp)) {
			try {
				em.delete(session);
			} catch (NullPointerException | IndexOutOfBoundsException ee) {
				JOptionPane.showMessageDialog(null, "Najpierw wybierz zaliczenie!", "Usuwanie zaliczenia", JOptionPane.INFORMATION_MESSAGE);
			}
		} else if (setProperties().getProperty("schedule.today").equals(tmp)) {
			c.showToday(session);
		} else if (setProperties().getProperty("schedule.tommorow").equals(tmp)) {
			c.showTommorow(session);
		} else if (setProperties().getProperty("schedule.week").equals(tmp)) {
			c.showAll(session);
		} else if (setProperties().getProperty("schedule.add").equals(tmp)) {
			c.add(session);
		} else if (setProperties().getProperty("schedule.delete").equals(tmp)) {
			try {
				c.delete(session);
			} catch (IndexOutOfBoundsException | NullPointerException ex) {
				JOptionPane.showMessageDialog(null, "Najpierw wybierz zajęcia w widoku tygodniowym!", "Usuwanie zajęć", JOptionPane.INFORMATION_MESSAGE);
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
