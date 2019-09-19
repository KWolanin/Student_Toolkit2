package wolanin.studentToolkit.db;

import org.hibernate.Query;
import org.hibernate.Session;
import wolanin.studentToolkit.frames.MainFrame;
import wolanin.studentToolkit.frames.ClassesFrame;
import wolanin.studentToolkit.table.TableFormatter;

import javax.swing.*;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Objects;


import static wolanin.studentToolkit.frames.MainFrame.*;
import static wolanin.studentToolkit.frames.ClassesFrame.*;
import static wolanin.studentToolkit.language.LangProperties.setProperties;
import static wolanin.studentToolkit.table.TableFormatter.*;

public class ClassesDAO implements HibernateDBFlow {

	private final String[] columnNames = new String[]
			{setProperties().getProperty("table.classesName"),
					setProperties().getProperty("table.startHour"),
					setProperties().getProperty("table.endHour"),
					setProperties().getProperty("table.room"),
					setProperties().getProperty("table.day")};

	public ClassesDAO() throws IOException {
	}

	@Override
	public void showAll(Session session) {
		setTableModelProp(columnNames);
		@SuppressWarnings("unchecked")
		List<Classes> classes = (List<Classes>) session.createQuery("from Classes").list();
		for (Classes value : classes) {
			String name = value.getName();
			String startHour = value.getStartHour();
			String endHour = value.getEndHour();
			int room = value.getRoom();
			String dayOfWeek = value.getDayofweek();
			String[] data = {name, startHour, endHour, String.valueOf(room), dayOfWeek};
			tableModel.addRow(data);
		}
		TableFormatter.setTableProp(classesPanel, classesTable, tableModel);
	}

	protected void showToday(Session session) {
		String today = getNameOfDay(getTodayDateToString());
		String sql = "from Classes where dayofweek='" + today + "'";
		TableFormatter.setTableModelProp(columnNames);
		@SuppressWarnings("unchecked")
		List<Classes> classes = (List<Classes>) session.createQuery(sql).list();
		for (Classes value : classes) {
			String name = value.getName();
			String startHour = value.getStartHour();
			String endHour = value.getEndHour();
			int room = value.getRoom();
			String dayOfWeek = value.getDayofweek();
			String[] data = {name, startHour, endHour, String.valueOf(room), dayOfWeek};
			tableModel.addRow(data);
		}
		TableFormatter.setTableProp(classesPanel, classesTable, tableModel);


	}

	protected void showTommorow(Session session) {
		String x = null;
		try {
			x = addOneDay(getTodayDateToString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String tommorowName = getNameOfDay(x);
		String sqle = "from Classes where dayofweek='" + tommorowName + "'";
		TableFormatter.setTableModelProp(columnNames);
		@SuppressWarnings("unchecked")
		List<Classes> classes = (List<Classes>) session.createQuery(sqle).list();
		for (Classes value : classes) {
			String name = value.getName();
			String startHour = value.getStartHour();
			String endHour = value.getEndHour();
			int room = value.getRoom();
			String dayOfWeek = value.getDayofweek();
			String[] data = {name, startHour, endHour, String.valueOf(room), dayOfWeek};
			tableModel.addRow(data);
		}
		TableFormatter.setTableProp(classesPanel, classesTable, tableModel);
	}

	@Override
	public void delete(Session session) {
		int row = classesTable.getSelectedRow();
		String selectedName = String.valueOf(classesTable.getValueAt(row, 0));
		String selectedStartHour = String.valueOf(classesTable.getValueAt(row, 1));
		String selectedDay = String.valueOf(classesTable.getValueAt(row, 4));
		Query query = session.createQuery("delete from Classes where name=:name and startHour=:startHour and dayofweek=:dayofweek");
		query.setParameter("name", selectedName);
		query.setParameter("startHour", selectedStartHour);
		query.setParameter("dayofweek", selectedDay);
		query.executeUpdate();
		showAll(session);
	}

	@Override
	public void add(Session session) throws IOException {
		new ClassesFrame().setVisible(true);
		showAll(MainFrame.session);
	}

	public void addToBase(Session session) throws IOException {
		String classesName = ClassesFrame.nameField.getText();
		String dayofWeek = Objects.requireNonNull(dayCombo.getSelectedItem()).toString();
		String startHour = startHourPicker.getText();
		String endHour = endHourPicker.getText();
		int room = Integer.parseInt(ClassesFrame.roomField.getText());
		if (classesName.equals("") | room == 0 | dayofWeek.equals("") | startHour.equals("") | endHour.equals("")) {
			JOptionPane.showMessageDialog(null, setProperties().getProperty("badInputMsg"),
					setProperties().getProperty("add.title.classes"), JOptionPane.INFORMATION_MESSAGE);
		} else {
			session.beginTransaction();
			Classes classes = new Classes();
			classes.setName(classesName);
			classes.setDayofweek(dayofWeek);
			classes.setStartHour(startHour);
			classes.setEndHour(endHour);
			classes.setRoom(room);
			session.save(classes);
			session.getTransaction().commit();
		}
	}
}
