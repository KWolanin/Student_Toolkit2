package wolanin.studentToolkit.exam;

import com.github.lgooddatepicker.components.TimePicker;
import org.jdatepicker.JDatePicker;
import wolanin.studentToolkit.MainFrame;
import wolanin.studentToolkit.frame.FormatFrame;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Locale;


class AddingExamFrame extends JDialog {

	private final JTextField nameField = new JTextField();
	private final String[] examTypesArray = {"egzamin", "zaliczenie z ocena", "zaliczenie bez oceny"};
	private final JComboBox<String> typeCombo = new JComboBox<>(examTypesArray);
	private final JTextField roomField = new JTextField();
	private final JDatePicker datePicker = new JDatePicker();
	private final TimePicker timePicker = new TimePicker();


	AddingExamFrame() {
		JPanel panel = new JPanel();
		JButton save = FormatFrame.createDialog(this, panel, "Dodawanie zaliczenia", 5, 2);
		JLabel name = new JLabel("Nazwa: ");
		panel.add(name);
		panel.add(nameField);
		JLabel type = new JLabel("Typ: ");
		panel.add(type);
		panel.add(typeCombo);
		JLabel date = new JLabel("Data: ");
		panel.add(date);
		panel.add(datePicker);
		JLabel hour = new JLabel("Godzina: ");
		panel.add(hour);
		panel.add(timePicker);
		JLabel room = new JLabel("Sala: ");
		panel.add(room);
		panel.add(roomField);
		save.addActionListener(e -> {
			try {
				save();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		});
	}


	private String changeDateFormat(String date) {
		DateFormat defaultPickerFormat = new SimpleDateFormat("dd MMM yyyy", new Locale("pl"));
		DateFormat standardDataFormat = new SimpleDateFormat("dd.MM.yyyy");
		Date standartDate = null;
		try {
			standartDate = defaultPickerFormat.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return standardDataFormat.format(standartDate);
	}

	private void save() throws SQLException {
		String examName = nameField.getText();
		String type = (String) typeCombo.getSelectedItem();
		String hour = timePicker.getText();
		String date = datePicker.getFormattedTextField().getText();
		String formattedDate = changeDateFormat(date);
		int room = 0;
		try {
			room = Integer.parseInt(roomField.getText());
		} catch (NumberFormatException | InputMismatchException e) {
			e.getMessage();
		}
		if (examName.equals("") | date.equals("") | hour.equals("") | room == 0) {
			JOptionPane.showMessageDialog(this, "Wpusz brakujące dane!");
		} else {
			PreparedStatement ps = MainFrame.con.prepareStatement("insert into exams(name, type, date, hour, room) values(?,?,?,?,?)");
			ps.setString(1, examName);
			ps.setString(2, type);
			ps.setString(3, formattedDate);
			ps.setString(4, hour);
			ps.setInt(5, room);
			ps.executeUpdate();
			dispose();
		}
	}
}