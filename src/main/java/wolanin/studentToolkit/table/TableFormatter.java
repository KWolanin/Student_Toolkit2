package wolanin.studentToolkit.table;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class TableFormatter {

	public static DefaultTableModel tableModel;


	public static void setTableProp(JPanel panel, JTable table, DefaultTableModel tablemodel) {
		table.setModel(tablemodel);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		panel.add(table, BorderLayout.CENTER);
		panel.add(table.getTableHeader(), BorderLayout.NORTH);
		table.setAutoCreateRowSorter(true);
		panel.updateUI();
	}

	public static void setTableModelProp(String[] columnNames) {
		tableModel = new DefaultTableModel(columnNames, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
	}


	public static String getTodayDateToString() {
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

	public static String getNameOfDay(String date) {
		Date d = null;
		try {
			d = new SimpleDateFormat("dd.MM.yyyy").parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new SimpleDateFormat("EEEE", new Locale("pl")).format(d);
	}

	public static String addOneDay(String date) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
		Date myDate = format.parse(date);
		Calendar cal = Calendar.getInstance();
		cal.setTime(myDate);
		cal.add(Calendar.DATE, 1);
		Date yourDate = cal.getTime();
		return format.format(yourDate);
	}
}
