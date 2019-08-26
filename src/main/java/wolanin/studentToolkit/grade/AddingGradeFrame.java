package wolanin.studentToolkit.grade;

import wolanin.studentToolkit.MainFrame;
import wolanin.studentToolkit.frame.FormatFrame;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;


class AddingGradeFrame extends JDialog {

	private final String[] examTypesArray = {"egzamin", "zaliczenie z ocena", "zaliczenie bez oceny"};
	private final JComboBox<String> examTypeCombo = new JComboBox<>(examTypesArray);
	private final String[] types = {"wyklad", "cwiczenia", "laboratorium", "seminarium"};
	private final JComboBox<String> typesCombo = new JComboBox<>(types);
	private final JTextField nameField = new JTextField();
	private final Double[] gradesArray = {2.0, 2.5, 3.0, 3.5, 4.0, 4.5, 5.0, 5.5, 6.0};
	private final JComboBox<Double> gradesCombo = new JComboBox<>(gradesArray);
	private final Integer[] ectsArray = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
	private final JComboBox<Integer> ectsCombo = new JComboBox<>(ectsArray);


	AddingGradeFrame() {
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
			try {
				save();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		});
	}

	private void save() throws SQLException {
		String savedName = nameField.getText();
		if (nameField.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Wpisz poprawne dane!", "Dodawanie oceny", JOptionPane.INFORMATION_MESSAGE);
		} else {
			double savedGrade = (double) gradesCombo.getSelectedItem();
			String savedKind = Objects.requireNonNull(examTypeCombo.getSelectedItem()).toString();
			int savedEcts = (int) ectsCombo.getSelectedItem();
			String savedType = Objects.requireNonNull(typesCombo.getSelectedItem()).toString();
			if (!gradesCombo.isEnabled()) {
				PreparedStatement ps = MainFrame.con.prepareStatement("insert into grades(name, grade, type, ects, examKind)" +
						"values(?,?,?,?,?)");
				ps.setString(1, savedName);
				ps.setObject(2, 0.0);
				ps.setString(3, savedKind);
				ps.setInt(4, savedEcts);
				ps.setString(5, savedType);
				ps.executeUpdate();
			} else {
				PreparedStatement ps = MainFrame.con.prepareStatement("insert into grades(name, grade, type, ects, examKind)values(?,?,?,?,?)");
				ps.setString(1, savedName);
				ps.setDouble(2, savedGrade);
				ps.setString(3, savedKind);
				ps.setInt(4, savedEcts);
				ps.setString(5, savedType);
				ps.executeUpdate();
			}
			dispose();
		}
	}


}
