package wolanin.studentToolkit.schedule;

import wolanin.studentToolkit.MainFrame;
import wolanin.studentToolkit.table.TableFormat;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class Schedule {

	private Statement stmt;
	private ResultSet rs;
	private String[] columnNames;

	public Schedule() {

	}


	private void executeShowSchedule(String sql2, Connection connection) throws SQLException {
		stmt = connection.createStatement();
		rs = stmt.executeQuery(sql2);
		columnNames = new String[]{"Przedmiot", "Godzina rozpoczęcia", "Godzina zakończenia", "Sala"};
		TableFormat.setTableModelProp(columnNames);
		while (rs.next()) {
			String name = rs.getString("name");
			String startHour = rs.getString("startHour");
			String endHour = rs.getString("endHour");
			int room = rs.getInt("room");
			String[] data = {name, startHour, endHour, String.valueOf(room)};
			TableFormat.tableModel.addRow(data);
		}
		TableFormat.setTableProp(MainFrame.schedulePanel, MainFrame.scheduleTable, TableFormat.tableModel);
		rs.close();
		stmt.close();
	}

	public void showTodaySchedule(Connection connection) throws SQLException {
		MainFrame.scheduleLabel.setText("Dzisiejsze zajęcia:");
		String dayOfWeek = getNameOfDay(getTodayDateToString());
		String sql = "select name, startHour, endHour, room from classes where dayofweek=";
		String sql2 = sql + "\"" + dayOfWeek + "\";";
		executeShowSchedule(sql2, connection);
	}

	public void showTommorowSchedule(Connection connection) throws ParseException, SQLException {
		MainFrame.scheduleLabel.setText("Jutrzejsze zajęcia:");
		String x = addOneDay(getTodayDateToString());
		String tommorowName = getNameOfDay(x);
		String sql = "select name, startHour, endHour, room from classes where dayofweek=";
		String sql2 = sql + "\"" + tommorowName + "\";";
		executeShowSchedule(sql2, connection);
	}

	public void showWeekSchedule() throws SQLException {
		MainFrame.scheduleLabel.setText("Zajęcia w tygodniu:");
		String sql = "select name, startHour, endHour, room, dayofweek from classes";
		stmt = MainFrame.con.createStatement();
		rs = stmt.executeQuery(sql);
		columnNames = new String[]{"Przedmiot", "Godzina rozpoczęcia", "Godzina zakończenia", "Sala", "Dzień"};
		TableFormat.setTableModelProp(columnNames);
		while (rs.next()) {
			String name = rs.getString("name");
			String startHour = rs.getString("startHour");
			String endHour = rs.getString("endHour");
			int room = rs.getInt("room");
			String dayOfWeek = rs.getString("dayofweek");
			String[] data = {name, startHour, endHour, String.valueOf(room), dayOfWeek};
			TableFormat.tableModel.addRow(data);
		}
		TableFormat.setTableProp(MainFrame.schedulePanel, MainFrame.scheduleTable, TableFormat.tableModel);
		rs.close();
		stmt.close();
	}

	public void addClasses() {
		new AddingClassesFrame().setVisible(true);
		try {
			showWeekSchedule();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteClasses() throws SQLException, ArrayIndexOutOfBoundsException {
		int row = MainFrame.scheduleTable.getSelectedRow();
		String selectedName = "" + MainFrame.scheduleTable.getValueAt(row, 0);
		String selectedStartHour = "" + MainFrame.scheduleTable.getValueAt(row, 1);
		String day = "" + MainFrame.scheduleTable.getValueAt(row, 4);
		PreparedStatement ps = MainFrame.con.prepareStatement("delete from classes where name=? and startHour=? and dayofweek=?;");
		ps.setString(1, selectedName);
		ps.setString(2, selectedStartHour);
		ps.setString(3, day);
		ps.executeUpdate();
		showWeekSchedule();
	}

	private static String getTodayDateToString() {
		GregorianCalendar calendar = new GregorianCalendar();
		String day = "" + calendar.get(Calendar.DAY_OF_MONTH);
		int mountInt = calendar.get(Calendar.MONTH) + 1;
		String month = "" + mountInt;
		String year = "" + calendar.get(Calendar.YEAR);
		if (calendar.get(Calendar.DAY_OF_MONTH) < 10) {
			day = "0" + calendar.get(Calendar.DAY_OF_MONTH);
		}
		if (mountInt < 10) {
			month = "0" + mountInt;
		}
		return (day + "." + month + "." + year);
	}

	private static String getNameOfDay(String date) {
		Date d = null;
		try {
			d = new SimpleDateFormat("dd.MM.yyyy").parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new SimpleDateFormat("EEEE", new Locale("pl")).format(d);
	}

	private static String addOneDay(String date) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
		Date myDate = format.parse(date);
		Calendar cal = Calendar.getInstance();
		cal.setTime(myDate);
		cal.add(Calendar.DATE, 1);
		Date yourDate = cal.getTime();
		return format.format(yourDate);
	}

}
