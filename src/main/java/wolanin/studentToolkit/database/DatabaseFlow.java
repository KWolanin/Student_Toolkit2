package wolanin.studentToolkit.database;

import java.sql.Connection;
import java.sql.SQLException;

public interface DatabaseFlow {

	void addToBase(Connection connection);

	void deleteFromBase(Connection connection) throws SQLException;

}
