package wolanin.studentToolkit.frames;

import wolanin.studentToolkit.dbHibernate.GradesDAO;

import javax.swing.*;
import java.io.IOException;
import java.util.Objects;

import static wolanin.studentToolkit.language.LangProperties.setProperties;

public class GradeFrame extends JDialog {
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
	private static String[] types;
	static {
		try {
			types = new String[]{setProperties().getProperty("lecture"),
					setProperties().getProperty("classes"),
					setProperties().getProperty("lab"),
					setProperties().getProperty("seminar")};
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static final JComboBox<String> examTypeCombo = new JComboBox<>(examTypesArray);
	public static final JComboBox<String> typesCombo = new JComboBox<>(types);
	public static final JTextField nameField = new JTextField();
	private static final Double[] gradesArray = {2.0, 2.5, 3.0, 3.5, 4.0, 4.5, 5.0, 5.5, 6.0};
	public static final JComboBox<Double> gradesCombo = new JComboBox<>(gradesArray);
	private static final Integer[] ectsArray = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
	public static final JComboBox<Integer> ectsCombo = new JComboBox<>(ectsArray);

	private final GradesDAO grades = new GradesDAO();

	public GradeFrame() throws IOException {
		JPanel panel = new JPanel();
		JButton save = FormatFrame.createDialog(this, panel, setProperties().getProperty("add.title"), 5);
		JLabel name = new JLabel(setProperties().getProperty("add.grade.name"));
		panel.add(name);
		panel.add(nameField);
		JLabel grade = new JLabel(setProperties().getProperty("table.grade"));
		panel.add(grade);
		panel.add(gradesCombo);
		JLabel examType = new JLabel(setProperties().getProperty("table.examKind"));
		panel.add(examType);
		panel.add(examTypeCombo);
		JLabel ects = new JLabel(setProperties().getProperty("table.ects"));
		panel.add(ects);
		panel.add(ectsCombo);
		JLabel type = new JLabel(setProperties().getProperty("table.classesKind"));
		panel.add(type);
		panel.add(typesCombo);
		examTypeCombo.addActionListener(e -> {
			String tmp = Objects.requireNonNull(examTypeCombo.getSelectedItem()).toString();
			try {
				if (tmp.equals(setProperties().getProperty("withoutGrade"))) {
					gradesCombo.setEnabled(false);
				} else {
					gradesCombo.setEnabled(true);
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		});
		save.addActionListener(e -> {
			try {
				grades.saveToBase(MainFrame.session);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			dispose();
		});
	}
}