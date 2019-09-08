package wolanin.studentToolkit.frames;

import com.github.lgooddatepicker.components.TimePicker;
import wolanin.studentToolkit.dbHibernate.ClassesDAO;


import javax.swing.*;

public class ClassesFrame extends JDialog {

	public static final JTextField nameField = new JTextField();
	private static final String[] daysOfWeek = {"poniedziałek", "wtorek", "środa", "czwartek", "piątek", "sobota", "niedziela"};
	public static final JComboBox<String> dayCombo = new JComboBox<>(daysOfWeek);
	public static final TimePicker startHourPicker = new TimePicker();
	public static final TimePicker endHourPicker = new TimePicker();
	public static final JTextField roomField = new JTextField();
	private final ClassesDAO c = new ClassesDAO();


	public ClassesFrame() {
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
		save.addActionListener(e -> {
			c.addToBase(MainFrame.session);
			dispose();
		});
	}
}
