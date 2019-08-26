package wolanin.studentToolkit.database;


import wolanin.studentToolkit.MainFrame;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class dbConnection {


	public static void connectToBase() throws IOException, SQLException, ClassNotFoundException {
		FileInputStream fileInputStream = new FileInputStream("database.properties");
		Properties properties = new Properties();
		properties.load(fileInputStream);
		String driverName = String.valueOf(properties.get("jdbc.driver"));
		String url = String.valueOf(properties.get("jdbc.url"));
		String username = String.valueOf(properties.get("jdbc.username"));
		String password = String.valueOf(properties.get("jdbc.password"));
		Class.forName(driverName);
		MainFrame.con = DriverManager.getConnection(url, username, password);
	}

	public static void disconnectToBase(Connection con) {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}


}
