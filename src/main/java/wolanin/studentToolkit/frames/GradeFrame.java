package wolanin.studentToolkit.frames;

import wolanin.studentToolkit.dbHibernate.GradesDAO;

import javax.swing.*;
import java.util.Objects;

public class GradeFrame extends JDialog {

	private static final String[] examTypesArray = {"egzamin", "zaliczenie z ocena", "zaliczenie bez oceny"};
	public static final JComboBox<String> examTypeCombo = new JComboBox<>(examTypesArray);
	private static final String[] types = {"wyklad", "cwiczenia", "laboratorium", "seminarium"};
	public static final JComboBox<String> typesCombo = new JComboBox<>(types);
	public static final JTextField nameField = new JTextField();
	private static final Double[] gradesArray = {2.0, 2.5, 3.0, 3.5, 4.0, 4.5, 5.0, 5.5, 6.0};
	public static final JComboBox<Double> gradesCombo = new JComboBox<>(gradesArray);
	private static final Integer[] ectsArray = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
	public static final JComboBox<Integer> ectsCombo = new JComboBox<>(ectsArray);

	private final GradesDAO grades = new GradesDAO();

	public GradeFrame() {
		JPanel panel = new JPanel();
		JButton save = FormatFrame.createDialog(this, panel, "Dodawanie oceny", 5, 2);
		JLabel name = new JLabel("Nazwa przedmiotu: ");
		panel.add(name);
		panel.add(nameField);
		JLabel grade = new JLabel("Ocena: ");
		panel.add(grade);
		panel.add(gradesCombo);
		JLabel examType = new JLabel("Rodzaj zaliczenia: ");
		panel.add(examType);
		panel.add(examTypeCombo);
		JLabel ects = new JLabel("Punkty ECTS: ");
		panel.add(ects);
		panel.add(ectsCombo);
		JLabel type = new JLabel("Typ zajęć: ");
		panel.add(type);
		panel.add(typesCombo);
		examTypeCombo.addActionListener(e -> {
			String tmp = Objects.requireNonNull(examTypeCombo.getSelectedItem()).toString();
			if (tmp.equals("zaliczenie bez oceny")) {
				gradesCombo.setEnabled(false);
			} else {
				gradesCombo.setEnabled(true);
			}
		});
		save.addActionListener(e -> {
			grades.saveToBase(MainFrame.session);
			dispose();
		});
	}
}