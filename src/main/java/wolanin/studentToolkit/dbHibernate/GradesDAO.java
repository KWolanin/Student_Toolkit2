package wolanin.studentToolkit.dbHibernate;

import org.hibernate.Query;
import org.hibernate.Session;

import wolanin.studentToolkit.frames.GradeFrame;
import wolanin.studentToolkit.table.TableFormat;

import javax.swing.*;
import java.util.List;
import java.util.Objects;

import static wolanin.studentToolkit.frames.MainFrame.*;
import static wolanin.studentToolkit.frames.GradeFrame.*;
import static wolanin.studentToolkit.table.TableFormat.tableModel;

public class GradesDAO implements HibernateDBFlow {


	@Override
	public void showAll(Session session) {
		String[] columnNames = new String[]{"Przedmiot", "Ocena", "Rodzaj zaliczenia", "Punkty ECTS", "Typ zajęć"};
		TableFormat.setTableModelProp(columnNames);
		@SuppressWarnings("unchecked")
		List<Grades> grades = (List<Grades>) session.createQuery("from Grades").list();
		for (Grades value : grades) {
			String name = value.getName();
			float grade = value.getGrade();
			String type = value.getType();
			int ects = value.getEcts();
			String examKind = value.getExamKind();
			String[] data = {name, String.valueOf(grade), type, String.valueOf(ects), examKind};
			tableModel.addRow(data);
		}
		TableFormat.setTableProp(gradesPanel, gradesTable, tableModel);
		gradesTable.updateUI();

	}

	@Override
	public void delete(Session session) {
		int row = gradesTable.getSelectedRow();
		String selectedName = "" + gradesTable.getValueAt(row, 0);
		String selectedKind = "" + gradesTable.getValueAt(row, 4);
		int selectedEcts = Integer.parseInt("" + gradesTable.getValueAt(row, 3));
		Query query = session.createQuery("delete Grades where name = :name and examKind= :examKind and ects= :ects");
		query.setParameter("name", selectedName);
		query.setParameter("examKind", selectedKind);
		query.setParameter("ects", selectedEcts);
		query.executeUpdate();
		showAll(session);
	}

	public void saveToBase(Session session) {
		String savedName = nameField.getText();
		if (nameField.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Wpisz poprawne dane!", "Dodawanie oceny", JOptionPane.INFORMATION_MESSAGE);
		} else {
			double savedGrade = (double) gradesCombo.getSelectedItem();
			String savedKind = Objects.requireNonNull(examTypeCombo.getSelectedItem()).toString();
			int savedEcts = (int) ectsCombo.getSelectedItem();
			String savedType = Objects.requireNonNull(typesCombo.getSelectedItem()).toString();
			JComboBox<Double> gradesCombo = GradeFrame.gradesCombo;
			session.beginTransaction();
			Grades grades = new Grades();
			grades.setName(savedName);
			grades.setExamKind(savedKind);
			grades.setEcts(savedEcts);
			grades.setType(savedType);
			if (!gradesCombo.isEnabled()) {
				grades.setGrade(0.0f);
			} else {
				grades.setGrade((float) savedGrade);
			}
			session.save(grades);
			session.getTransaction().commit();
		}
	}


	@Override
	public void add(Session session) {
		new GradeFrame().setVisible(true);
		showAll(session);
	}

	public void showFailedByHibernate(Session session) {
		String[] columnNames = new String[]{"Przedmiot", "Ocena", "Rodzaj zaliczenia", "Punkty ECTS", "Typ zajęć"};
		TableFormat.setTableModelProp(columnNames);
		@SuppressWarnings("unchecked")
		List<Grades> grades = (List<Grades>) session.createQuery("from Grades where grade<3 and type !='zaliczenie bez oceny'").list();
		for (Grades value : grades) {
			String name = value.getName();
			float grade = value.getGrade();
			String type = value.getType();
			int ects = value.getEcts();
			String examKind = value.getExamKind();
			String[] data = {name, String.valueOf(grade), type, String.valueOf(ects), examKind};
			tableModel.addRow(data);
		}
		TableFormat.setTableProp(gradesPanel, gradesTable, tableModel);
	}

	public void calcAverageByHibernate(Session session) {
		float sum = 0.0f;
		float wage = 0.0f;
		@SuppressWarnings("unchecked")
		List<Grades> grades = (List<Grades>) session.createQuery("from Grades where examKind ='egzamin'").list();
		System.out.println(grades.size());
		for (Grades value : grades) {
			wage = wage + value.getEcts();
			sum = sum + (value.getGrade() * value.getEcts());
		}
		float result = sum / wage;
		String msg = "Twoja średnia ważona z egzaminów to: " + String.format("%.02f", result);
		JOptionPane.showMessageDialog(null, msg, "Wyliczanie średniej", JOptionPane.INFORMATION_MESSAGE);
	}
}