package wolanin.studentToolkit.teacher;

import wolanin.studentToolkit.MainFrame;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;

import static java.awt.Desktop.getDesktop;
import static wolanin.studentToolkit.table.TableFormat.*;

public class Teacher {

	public Teacher() {
	}

	private int row;

	public void showTeachers(Connection connection) throws SQLException {
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT first_name, last_name, email, title FROM teachers;");
		String[] columnNames = new String[]{"Imię", "Nazwisko", "E-mail", "Stopień naukowy / tytuł zawodowy"};
		setTableModelProp(columnNames);
		while (rs.next()) {
			String firstName = rs.getString("first_name");
			String lastName = rs.getString("last_name");
			String email = rs.getString("title");
			String title = rs.getString("email");
			String[] data = {firstName, lastName, title, email};
			tableModel.addRow(data);
		}
		setTableProp(MainFrame.teachersPanel, MainFrame.teacherTable, tableModel);
		rs.close();
		stmt.close();
	}

	public void addTeacher(Connection connection) {
		new AddingTeacherFrame().setVisible(true);
		try {
			showTeachers(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteTeacher(Connection con) throws SQLException {
		row = MainFrame.teacherTable.getSelectedRow();
		String selected = "" + MainFrame.teacherTable.getValueAt(row, 2);
		String sql = "delete from teachers where email=?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, selected);
		ps.executeUpdate();
		showTeachers(con);
	}

	public void emailTo() throws URISyntaxException, IOException {
		row = MainFrame.teacherTable.getSelectedRow();
		String emailFromBase = "" + MainFrame.teacherTable.getValueAt(row, 2);
		String mailto = "mailto:";
		String email = mailto + emailFromBase;
		Desktop desktop = getDesktop();
		desktop.mail(new URI(email));
	}

}
