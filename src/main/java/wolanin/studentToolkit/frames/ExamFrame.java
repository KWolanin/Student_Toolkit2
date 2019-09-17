package wolanin.studentToolkit.frames;

import com.github.lgooddatepicker.components.TimePicker;
import org.jdatepicker.JDatePicker;
import wolanin.studentToolkit.db.ExamsDAO;


import javax.swing.*;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static wolanin.studentToolkit.language.LangProperties.setProperties;


public class ExamFrame extends JDialog {

	public static final JTextField nameField = new JTextField();

	private static String[] examTypesArray;

	static {
		try {
			examTypesArray = new String[]{setProperties().getProperty("exam"),
					setProperties().getProperty("withGrade"),
					setProperties().getProperty("withoutGrade")};
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static final JComboBox<String> typeCombo = new JComboBox<>(examTypesArray);
	public static final JTextField roomField = new JTextField();
	public static final JDatePicker datePicker = new JDatePicker();
	public static final TimePicker timePicker = new TimePicker();
	private final ExamsDAO ex = new ExamsDAO();


	public ExamFrame() throws IOException {
		JPanel panel = new JPanel();
		JButton save = ComponentCreator.createDialog(this, panel, setProperties().getProperty("add.title.exams"), 5);
		JLabel name = new JLabel(setProperties().getProperty("table.classesName"));
		panel.add(name);
		panel.add(nameField);
		JLabel type = new JLabel(setProperties().getProperty("table.examKind"));
		panel.add(type);
		panel.add(typeCombo);
		JLabel date = new JLabel(setProperties().getProperty("table.date"));
		panel.add(date);
		panel.add(datePicker);
		JLabel hour = new JLabel(setProperties().getProperty("table.hour"));
		panel.add(hour);
		panel.add(timePicker);
		JLabel room = new JLabel(setProperties().getProperty("table.room"));
		panel.add(room);
		panel.add(roomField);
		save.addActionListener(e -> {
			try {
				ex.addToBase(MainFrame.session);
			} catch (IOException exc) {
				exc.printStackTrace();
			}
			dispose();
		});
	}

	public static String changeDateFormat(String date) throws IOException {
		DateFormat defaultPickerFormat = new SimpleDateFormat("dd MMM yyyy",
				new Locale(setProperties().getProperty("locale")));
		DateFormat standardDataFormat = new SimpleDateFormat("dd.MM.yyyy");
		Date standardDate = null;
		try {
			standardDate = defaultPickerFormat.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return standardDataFormat.format(standardDate);
	}
}