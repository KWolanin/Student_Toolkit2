package wolanin.studentToolkit.db;

import org.hibernate.Query;
import org.hibernate.Session;

import wolanin.studentToolkit.frames.GradeFrame;
import wolanin.studentToolkit.table.TableFormatter;

import javax.swing.*;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static wolanin.studentToolkit.frames.MainFrame.*;
import static wolanin.studentToolkit.frames.GradeFrame.*;
import static wolanin.studentToolkit.language.LangProperties.setProperties;
import static wolanin.studentToolkit.table.TableFormatter.tableModel;

public class GradesDAO implements HibernateDBFlow {
	private final String[] columnNames = new String[]{setProperties().getProperty("table.classesName"),
			setProperties().getProperty("table.grade"),
			setProperties().getProperty("table.examKind"),
			setProperties().getProperty("table.ects"),
			setProperties().getProperty("table.classesKind"),
	};

	public GradesDAO() throws IOException {
	}


	@Override
	public void showAll(Session session) {
		TableFormatter.setTableModelProp(columnNames);
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
		TableFormatter.setTableProp(gradesPanel, gradesTable, tableModel);
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

	public void saveToBase(Session session) throws IOException {
		String savedName = nameField.getText();
		if (nameField.getText().equals("")) {
			JOptionPane.showMessageDialog(null, setProperties().getProperty("badInputMsg"),
					setProperties().getProperty("add.title"), JOptionPane.INFORMATION_MESSAGE);
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
	public void add(Session session) throws IOException {
		new GradeFrame().setVisible(true);
		showAll(session);
	}

	protected void showFailed(Session session) {
		TableFormatter.setTableModelProp(columnNames);
		@SuppressWarnings("unchecked")
		List<Grades> grades = (List<Grades>) session.createQuery("from Grades where grade<3 and examKind !='zaliczenie bez oceny'").list();
		for (Grades value : grades) {
			String name = value.getName();
			float grade = value.getGrade();
			String type = value.getType();
			int ects = value.getEcts();
			String examKind = value.getExamKind();
			String[] data = {name, String.valueOf(grade), type, String.valueOf(ects), examKind};
			tableModel.addRow(data);
		}
		TableFormatter.setTableProp(gradesPanel, gradesTable, tableModel);
		//todo
		if (grades.isEmpty()){
			JOptionPane.showMessageDialog(null, "Gratulacje, wszystko zaliczone!");
		}

	}

	protected void calcAverage(Session session) throws IOException {
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
		String msg = setProperties().getProperty("grade.average.msg") + String.format("%.02f", result);
		JOptionPane.showMessageDialog(null, msg,
				setProperties().getProperty("grade.average.title"), JOptionPane.INFORMATION_MESSAGE);
	}
}