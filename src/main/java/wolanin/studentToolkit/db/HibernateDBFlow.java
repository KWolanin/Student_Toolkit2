package wolanin.studentToolkit.db;

import org.hibernate.Session;

import java.io.IOException;


interface HibernateDBFlow {

	void showAll(Session session) throws IOException;

	void delete(Session session) throws IOException;

	void add(Session session) throws IOException;

}
