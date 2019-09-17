package wolanin.studentToolkit.db;

import org.hibernate.Query;
import org.hibernate.Session;
import wolanin.studentToolkit.frames.ExamFrame;
import wolanin.studentToolkit.frames.MainFrame;
import wolanin.studentToolkit.table.TableFormatter;


import javax.swing.*;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;

import static wolanin.studentToolkit.frames.ExamFrame.*;
import static wolanin.studentToolkit.frames.MainFrame.*;
import static wolanin.studentToolkit.language.LangProperties.setProperties;
import static wolanin.studentToolkit.table.TableFormatter.tableModel;

public class ExamsDAO implements HibernateDBFlow {


	private String[] columnNames;
	{
		try {
			columnNames = new String[]{
					setProperties().getProperty("table.classesName"),
					setProperties().getProperty("table.examKind"),
					setProperties().getProperty("table.date"),
					setProperties().getProperty("table.hour"),
					setProperties().getProperty("table.room")
			};
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void showAll(Session session) {
		TableFormatter.setTableModelProp(columnNames);
		@SuppressWarnings("unchecked")
		List<Exams> exams = (List<Exams>) session.createQuery("from Exams").list();
		for (Exams value : exams) {
			String name = value.getName();
			String type = value.getType();
			String date = value.getDate();
			String hour = value.getHour();
			int room = value.getRoom();
			String[] data = {name, type, date, hour, String.valueOf(room)};
			tableModel.addRow(data);
		}
		TableFormatter.setTableProp(examPanel, examTable, tableModel);
	}

	@Override
	public void delete(Session session) {
		int row = examTable.getSelectedRow();
		String selectedName = "" + MainFrame.examTable.getValueAt(row, 0);
		String selectedType = "" + MainFrame.examTable.getValueAt(row, 1);
		Query query = session.createQuery("delete from Exams where name=:name and type=:type");
		query.setParameter("name", selectedName);
		query.setParameter("type", selectedType);
		query.executeUpdate();
		showAll(session);
	}

	@Override
	public void add(Session session) throws IOException {
		new ExamFrame().setVisible(true);
		showAll(session);
	}

	public void addToBase(Session session) throws IOException {
		String examName = ExamFrame.nameField.getText();
		String type = (String) typeCombo.getSelectedItem();
		String hour = timePicker.getText();
		String date = ExamFrame.datePicker.getFormattedTextField().getText();
		String formattedDate = changeDateFormat(date);
		int room = 0;
		try {
			room = Integer.parseInt(roomField.getText());
		} catch (NumberFormatException | InputMismatchException e) {
			e.getMessage();
		}
		if (examName.equals("") | date.equals("") | hour.equals("") | room == 0) {
			JOptionPane.showMessageDialog(null, setProperties().getProperty("badInputMsg"));
		} else {
			session.beginTransaction();
			Exams exams = new Exams();
			exams.setName(examName);
			exams.setType(type);
			exams.setHour(hour);
			exams.setDate(formattedDate);
			exams.setRoom(room);
			session.save(exams);
			session.getTransaction().commit();
		}
	}
}


