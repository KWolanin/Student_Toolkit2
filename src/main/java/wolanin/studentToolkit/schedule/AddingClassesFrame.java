package wolanin.studentToolkit.schedule;

import com.github.lgooddatepicker.components.TimePicker;
import wolanin.studentToolkit.MainFrame;
import wolanin.studentToolkit.frame.FormatFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Objects;

class AddingClassesFrame extends JDialog {

	private final JTextField nameField = new JTextField();
	private final String[] daysOfWeek = {"poniedziałek", "wtorek", "środa", "czwartek", "piątek", "sobota", "niedziela"};
	private final JComboBox<String> dayCombo = new JComboBox<>(daysOfWeek);
	private final TimePicker startHourPicker = new TimePicker();
	private final TimePicker endHourPicker = new TimePicker();
	private final JTextField roomField = new JTextField();


	AddingClassesFrame() {
		JPanel panel = new JPanel();
		JButton save = FormatFrame.createDialog(this, panel, "Dodawanie zajeć", 5, 2);
		JLabel name = new JLabel("Nazwa: ");
		panel.add(name);
		panel.add(nameField);
		JLabel day = new JLabel("Dzień tygodnia: ");
		panel.add(day);
		panel.add(dayCombo);
		JLabel startHour = new JLabel("Godzina rozpoczęcia: ");
		panel.add(startHour);
		panel.add(startHourPicker);
		JLabel endHour = new JLabel("Godzina zakończenia: ");
		panel.add(endHour);
		panel.add(endHourPicker);
		JLabel room = new JLabel("Sala: ");
		panel.add(room);
		panel.add(roomField);
		save.addActionListener(this::actionPerformed);
	}


	private void save() throws SQLException {
		String classesName = nameField.getText();
		String dayofWeek = Objects.requireNonNull(dayCombo.getSelectedItem()).toString();
		String startHour = startHourPicker.getText();
		String endHour = endHourPicker.getText();
		int room = 0;
		try {
			room = Integer.parseInt(roomField.getText());
		} catch (NumberFormatException | InputMismatchException e) {
			e.getMessage();
		}
		if (classesName.equals("") | room == 0) {
			JOptionPane.showMessageDialog(null, "Wpisz poprawne dane!", "Dodawanie zajęć", JOptionPane.INFORMATION_MESSAGE);

		} else {
			PreparedStatement ps = MainFrame.con.prepareStatement("insert into classes(name, dayofweek, startHour, endHour, room) values(?,?,?,?,?);");
			ps.setString(1, classesName);
			ps.setString(2, dayofWeek);
			ps.setString(3, startHour);
			ps.setString(4, endHour);
			ps.setInt(5, room);
			ps.executeUpdate();
			dispose();
		}
	}


	private void actionPerformed(ActionEvent e) {
		try {
			save();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
}
