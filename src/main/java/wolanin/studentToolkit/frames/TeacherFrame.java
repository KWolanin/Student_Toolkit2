package wolanin.studentToolkit.frames;

import javax.swing.*;

import wolanin.studentToolkit.dbHibernate.TeacherDAO;

import java.io.IOException;

import static wolanin.studentToolkit.language.LangProperties.setProperties;

public class TeacherFrame extends JDialog {

	public static final JTextField firstNameField = new JTextField();
	public static final JTextField lastNameField = new JTextField();
	public static final JTextField emailField = new JTextField();
	private static String[] teachersTitles;
	static {
		try {
			teachersTitles = new String[]{
					setProperties().getProperty("magister"),
					setProperties().getProperty("doktor"),
					setProperties().getProperty("doktorHab"),
					setProperties().getProperty("profDrHab"),
					setProperties().getProperty("magisterInz"),
					setProperties().getProperty("inz")
			};
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static final JComboBox<String> titlesCombo = new JComboBox<>(teachersTitles);


	public TeacherFrame() throws IOException {
		JPanel panel = new JPanel();
		JButton save = FormatFrame.createDialog(this, panel, setProperties().getProperty("add.title.teachers"), 4);
		save.addActionListener(e -> {
			try {
				TeacherDAO.saveToBase(MainFrame.session);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			dispose();
		});
		JLabel firstName = new JLabel(setProperties().getProperty("table.firstName"));
		panel.add(firstName);
		panel.add(firstNameField);
		JLabel lastName = new JLabel(setProperties().getProperty("table.lastName"));
		panel.add(lastName);
		panel.add(lastNameField);
		JLabel email = new JLabel(setProperties().getProperty("table.email"));
		panel.add(email);
		panel.add(emailField);
		JLabel title = new JLabel(setProperties().getProperty("table.academicDegree"));
		panel.add(title);
		panel.add(titlesCombo);

	}
}
