package wolanin.studentToolkit.frames;

import javax.swing.*;

import wolanin.studentToolkit.dbHibernate.TeacherDAO;

public class TeacherFrame extends JDialog {

	public static final JTextField firstNameField = new JTextField();
	public static final JTextField lastNameField = new JTextField();
	public static final JTextField emailField = new JTextField();
	private static final String[] teachersTitles = {"mgr", "dr", "dr hab.", "prof. dr. hab.", "mgr inż.", "inż."};
	public static final JComboBox<String> titlesCombo = new JComboBox<>(teachersTitles);


	public TeacherFrame() {
		JPanel panel = new JPanel();
		JButton save = FormatFrame.createDialog(this, panel,"Dodawanie wykładowcy", 4, 2);
		save.addActionListener(e -> {
			TeacherDAO.saveToBase(MainFrame.session);
			dispose();
		});

		JLabel firstName = new JLabel("Imię: ");
		panel.add(firstName);
		panel.add(firstNameField);
		JLabel lastName = new JLabel("Nazwisko: ");
		panel.add(lastName);
		panel.add(lastNameField);
		JLabel email = new JLabel("E-mail: ");
		panel.add(email);
		panel.add(emailField);
		JLabel title = new JLabel("Stopień naukowy lub tytuł zawodowy: ");
		panel.add(title);
		panel.add(titlesCombo);

	}
}
