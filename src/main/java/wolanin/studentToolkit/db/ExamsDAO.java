package wolanin.studentToolkit.db;

import org.hibernate.Query;
import org.hibernate.Session;
import wolanin.studentToolkit.frames.ExamFrame;
import wolanin.studentToolkit.frames.MainFrame;

import javax.swing.*;
import java.io.IOException;
import java.sql.Date;
import java.util.InputMismatchException;
import java.util.List;

import static wolanin.studentToolkit.frames.ExamFrame.*;
import static wolanin.studentToolkit.frames.MainFrame.examPanel;
import static wolanin.studentToolkit.frames.MainFrame.examTable;
import static wolanin.studentToolkit.language.LangProperties.setProperties;
import static wolanin.studentToolkit.table.TableFormatter.*;

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
		setTableModelProp(columnNames);
		@SuppressWarnings("unchecked")
		List<Exams> exams = (List<Exams>) session.createQuery("from Exams").list();
		for (Exams value : exams) {
			String name = value.getName();
			String type = value.getType();
			Date date = value.getDate();
			String hour = value.getHour();
			int room = value.getRoom();
			String[] data = {name, type, String.valueOf(date), hour, String.valueOf(room)};
			tableModel.addRow(data);
		}
		setTableProp(examPanel, examTable, tableModel);
	}

	@Override
	public void delete(Session session) throws IOException {
		int row;
		String selectedName = "", selectedType = "";
		try {
			row = examTable.getSelectedRow();
			selectedName = "" + MainFrame.examTable.getValueAt(row, 0);
			selectedType = "" + MainFrame.examTable.getValueAt(row, 1);
		} catch (IndexOutOfBoundsException e) {
			showMsg("select.classes.first");
		}

		Query query = session.createQuery("delete from Exams where name=:name and type=:type");
		query.setParameter("name", selectedName);
		query.setParameter("type", selectedType);
		query.executeUpdate();
		showAll(session);
	}

	private void showMsg(String s) throws IOException {
		JOptionPane.showMessageDialog(null, setProperties().getProperty(s));
	}

	@Override
	public void add(Session session) throws IOException {
		new ExamFrame().setVisible(true);
		showAll(session);
	}

	public void addToBase(Session session) throws IOException {
		String date;
		Date formattedDate = null;
		String examName = ExamFrame.nameField.getText();
		String type;
		String hour;
		type = (String) typeCombo.getSelectedItem();
		date = datePicker.getFormattedTextField().getText();
		hour = timePicker.getText();
		int room = 0;
		try {
			date = datePicker.getFormattedTextField().getText();
			formattedDate = Date.valueOf(ExamFrame.changeDateFormat(date));
			room = Integer.parseInt(roomField.getText());
		} catch (InputMismatchException | IllegalArgumentException e) {
			showMsg("badInputMsg");
		}
		if (examName.equals("") | formattedDate == null | hour.equals("") | room == 0 | date.equals("")) {
			showMsg("badInputMsg");
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


