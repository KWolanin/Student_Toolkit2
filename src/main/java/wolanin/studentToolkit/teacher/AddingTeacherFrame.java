package wolanin.studentToolkit.teacher;

import wolanin.studentToolkit.MainFrame;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

import wolanin.studentToolkit.frame.FormatFrame;

class AddingTeacherFrame extends JDialog {

	private final JTextField firstNameField = new JTextField();
	private final JTextField lastNameField = new JTextField();
	private final JTextField emailField = new JTextField();
	private final String[] teachersTitles = {"mgr", "dr", "dr hab.", "prof. dr. hab.", "mgr inż.", "inż."};
	private final JComboBox<String> titlesCombo = new JComboBox<>(teachersTitles);


	AddingTeacherFrame() {
		JPanel panel = new JPanel();
		JButton save = FormatFrame.createDialog(this, panel,"Dodawanie wykładowcy", 4, 2);
		save.addActionListener(e -> save());
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


	private void save() {
		String firstName = firstNameField.getText();
		String lastName = lastNameField.getText();
		String title = Objects.requireNonNull(titlesCombo.getSelectedItem()).toString();
		String email = emailField.getText();
		if (firstNameField.getText().equals("") | lastNameField.getText().equals("") | emailField.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Wpisz poprawne dane!", "Dodawanie wykładowcy", JOptionPane.INFORMATION_MESSAGE);
		} else {
			try {
				PreparedStatement ps = MainFrame.con.prepareStatement("insert into teachers(firstName, lastName, title, email) values(?,?,?,?)");
				ps.setString(1, firstName);
				ps.setString(2, lastName);
				ps.setString(3, title);
				ps.setString(4, email);
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			dispose();
		}
	}
}
