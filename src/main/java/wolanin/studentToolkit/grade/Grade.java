package wolanin.studentToolkit.grade;

import wolanin.studentToolkit.MainFrame;
import wolanin.studentToolkit.table.TableFormat;

import javax.swing.*;
import java.sql.*;

import static wolanin.studentToolkit.MainFrame.*;
import static wolanin.studentToolkit.table.TableFormat.tableModel;

public class Grade {

	private Statement stmt;
	private ResultSet rs;

	public Grade() {

	}

	public void addGrade(Connection connection) {
		new AddingGradeFrame().setVisible(true);
		try {
			showGrades(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void showGrades(Connection con) throws SQLException, NullPointerException {
		stmt = con.createStatement();
		rs = stmt.executeQuery("SELECT name, grade, examKind, ects, type FROM grades;");
		executeShowGrades();
	}

	private void executeShowGrades() throws SQLException {
		String[] columnNames = new String[]{"Przedmiot", "Ocena", "Rodzaj zaliczenia", "Punkty ECTS", "Typ zajęć"};
		TableFormat.setTableModelProp(columnNames);
		while (rs.next()) {
			String name = rs.getString("name");
			float grade = rs.getFloat("grade");
			String type = rs.getString("type");
			int ects = rs.getInt("ects");
			String examKind = rs.getString("examKind");
			String[] data = {name, String.valueOf(grade), type, String.valueOf(ects), examKind};
			tableModel.addRow(data);
		}
		TableFormat.setTableProp(gradesPanel, gradesTable, tableModel);
		rs.close();
		stmt.close();
	}

	public void showFailed() throws SQLException {
		String sql = "select * from grades where grade<3 and type !='zaliczenie bez oceny'";
		stmt = MainFrame.con.createStatement();
		rs = stmt.executeQuery(sql);
		executeShowGrades();
	}

	public void deleteGrade() throws SQLException {
		int row = gradesTable.getSelectedRow();
		String selectedName = "" + gradesTable.getValueAt(row, 0);
		String selectedKind = "" + gradesTable.getValueAt(row, 4);
		String selectedEcts = "" + gradesTable.getValueAt(row, 3);
		String sql = "delete from grades where name=? and examKind=? and ects=?";
		PreparedStatement ps = MainFrame.con.prepareStatement(sql);
		ps.setString(1, selectedName);
		ps.setString(2, selectedKind);
		ps.setString(3, selectedEcts);
		ps.executeUpdate();
		showGrades(MainFrame.con);
	}

	public void calcAverage() throws SQLException {
		double sum = 0;
		double wage = 0;
		String sql = "select name, grade, ects from grades where type='egzamin'";
		stmt = MainFrame.con.createStatement();
		rs = stmt.executeQuery(sql);
		while (rs.next()) {
			wage = wage + (rs.getInt("ects"));
			sum = sum + (rs.getFloat("grade") * rs.getInt("ects"));
		}
		rs.close();
		stmt.close();
		double result = sum / wage;
		String msg = "Twoja średnia ważona z egzaminów to: " + String.format("%.02f", result);
		JOptionPane.showMessageDialog(null, msg, "Wyliczanie średniej", JOptionPane.INFORMATION_MESSAGE);
	}

}
