package wolanin.studentToolkit.table;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TableFormat {

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
}
