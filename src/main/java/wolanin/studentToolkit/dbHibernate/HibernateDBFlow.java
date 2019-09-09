package wolanin.studentToolkit.dbHibernate;

import org.hibernate.Session;

import java.io.IOException;


interface HibernateDBFlow {

	void showAll(Session session);

	void delete(Session session);

	void add(Session session) throws IOException;

}
