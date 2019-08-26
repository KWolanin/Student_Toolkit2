package wolanin.studentToolkit.exam;

import wolanin.studentToolkit.MainFrame;
import wolanin.studentToolkit.table.TableFormat;

import java.sql.*;

public class Exam {

	public Exam() {
	}

	public void deleteExam(Connection conection) throws SQLException {
		int row = MainFrame.examTable.getSelectedRow();
		String selectedName = "" + MainFrame.examTable.getValueAt(row, 0);
		String selectedType = "" + MainFrame.examTable.getValueAt(row, 1);
		String sql = "delete from exams where name=? and type=?";
		PreparedStatement ps = conection.prepareStatement(sql);
		ps.setString(1, selectedName);
		ps.setString(2, selectedType);
		ps.executeUpdate();
		showExams(conection);
	}

	public void showExams(Connection connection) throws SQLException {
		String sql = "select * from exams";
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		String[] columnNames = new String[]{"Przedmiot", "Rodzaj zaliczenia", "Data", "Godzina", "Sala"};
		TableFormat.setTableModelProp(columnNames);
		while (rs.next()) {
			String name = rs.getString("name");
			String type = rs.getString("type");
			String date = rs.getString("date");
			String hour = rs.getString("hour");
			int room = rs.getInt("room");
			String[] data = {name, type, date, hour, String.valueOf(room)};
			TableFormat.tableModel.addRow(data);
		}
		TableFormat.setTableProp(MainFrame.examPanel, MainFrame.examTable, TableFormat.tableModel);
		rs.close();
		stmt.close();
	}

	public void addExam() {
		new AddingExamFrame().setVisible(true);
		try {
			showExams(MainFrame.con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
