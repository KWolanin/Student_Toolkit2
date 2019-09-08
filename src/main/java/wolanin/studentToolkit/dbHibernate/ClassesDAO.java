package wolanin.studentToolkit.dbHibernate;

import org.hibernate.Query;
import org.hibernate.Session;
import wolanin.studentToolkit.frames.MainFrame;
import wolanin.studentToolkit.frames.ClassesFrame;
import wolanin.studentToolkit.table.TableFormat;

import javax.swing.*;
import java.text.ParseException;
import java.util.List;
import java.util.Objects;


import static wolanin.studentToolkit.frames.MainFrame.*;
import static wolanin.studentToolkit.frames.ClassesFrame.*;
import static wolanin.studentToolkit.table.TableFormat.*;

public class ClassesDAO implements HibernateDBFlow {

	@Override
	public void showAll(Session session) {
		String[] columnNames = new String[]{"Przedmiot", "Godzina rozpoczęcia", "Godzina zakończenia", "Sala", "Dzień"};
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
		TableFormat.setTableProp(classesPanel, classesTable, tableModel);
	}

	public void showToday(Session session){
		String today = getNameOfDay(getTodayDateToString());
		String sql = "from Classes where dayofweek='"+today+"'";
		String[] columnNames = new String[]{"Przedmiot", "Godzina rozpoczęcia", "Godzina zakończenia", "Sala"};
		TableFormat.setTableModelProp(columnNames);
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
		TableFormat.setTableProp(classesPanel, classesTable, tableModel);


	}

	public void showTommorow(Session session) {
		String x = null;
		try {
			x = addOneDay(getTodayDateToString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String tommorowName = getNameOfDay(x);
		String sqle = "from Classes where dayofweek='"+tommorowName+"'";
		String[] columnNames = new String[]{"Przedmiot", "Godzina rozpoczęcia", "Godzina zakończenia", "Sala"};
		TableFormat.setTableModelProp(columnNames);
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
		TableFormat.setTableProp(classesPanel, classesTable, tableModel);
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
	public void add(Session session) {
		new ClassesFrame().setVisible(true);
		showAll(MainFrame.session);
	}

	public void addToBase(Session session) {
		String classesName = ClassesFrame.nameField.getText();
		String dayofWeek = Objects.requireNonNull(dayCombo.getSelectedItem()).toString();
		String startHour = startHourPicker.getText();
		String endHour = endHourPicker.getText();

		int room = Integer.parseInt(ClassesFrame.roomField.getText());
		if (classesName.equals("") | room == 0) {
			JOptionPane.showMessageDialog(null, "Wpisz poprawne dane!", "Dodawanie zajęć", JOptionPane.INFORMATION_MESSAGE);
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
