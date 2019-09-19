package wolanin.studentToolkit.frames;

import com.github.lgooddatepicker.components.TimePicker;
import wolanin.studentToolkit.db.ClassesDAO;


import javax.swing.*;
import java.io.IOException;

import static wolanin.studentToolkit.language.LangProperties.setProperties;

public class ClassesFrame extends JDialog {

	public static final JTextField nameField = new JTextField();
	private static String[] daysOfWeek;
	static {
		try {
			daysOfWeek = new String[]{setProperties().getProperty("monday"),
					setProperties().getProperty("tuesday"),
					setProperties().getProperty("wednesday"),
					setProperties().getProperty("thursday"),
					setProperties().getProperty("friday"),
					setProperties().getProperty("saturday"),
					setProperties().getProperty("sunday")};
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static final JComboBox<String> dayCombo = new JComboBox<>(daysOfWeek);
	public static final TimePicker startHourPicker = new TimePicker();
	public static final TimePicker endHourPicker = new TimePicker();
	public static final JTextField roomField = new JTextField();
	private final ClassesDAO c = new ClassesDAO();


	public ClassesFrame() throws IOException {
		JPanel panel = new JPanel();
		JButton save = ComponentCreator.createDialog(this, panel, setProperties().getProperty("add.title.classes"), 5);
		JLabel name = new JLabel(setProperties().getProperty("table.classesName") + ":");
		panel.add(name);
		panel.add(nameField);
		JLabel day = new JLabel(setProperties().getProperty("table.day") + ":");
		panel.add(day);
		panel.add(dayCombo);
		JLabel startHour = new JLabel(setProperties().getProperty("table.startHour") + ":");
		panel.add(startHour);
		panel.add(startHourPicker);
		JLabel endHour = new JLabel(setProperties().getProperty("table.endHour") + ":");
		panel.add(endHour);
		panel.add(endHourPicker);
		JLabel room = new JLabel(setProperties().getProperty("table.room") + ":");
		panel.add(room);
		panel.add(roomField);
		save.addActionListener(e -> {
			try {
				c.addToBase(MainFrame.session);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			dispose();

		});
	}
}
