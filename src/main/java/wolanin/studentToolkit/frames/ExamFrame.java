package wolanin.studentToolkit.frames;

import com.github.lgooddatepicker.components.TimePicker;
import org.jdatepicker.JDatePicker;
import wolanin.studentToolkit.dbHibernate.ExamsDAO;


import javax.swing.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class ExamFrame extends JDialog {

	public static final JTextField nameField = new JTextField();
	private static final String[] examTypesArray = {"egzamin", "zaliczenie z ocena", "zaliczenie bez oceny"};
	public static final JComboBox<String> typeCombo = new JComboBox<>(examTypesArray);
	public static final JTextField roomField = new JTextField();
	public static final JDatePicker datePicker = new JDatePicker();
	public static final TimePicker timePicker = new TimePicker();
	private final ExamsDAO ex = new ExamsDAO();


	public ExamFrame() {
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
			ex.addToBase(MainFrame.session);
			dispose();
		});
	}

	public static String changeDateFormat(String date) {
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
}