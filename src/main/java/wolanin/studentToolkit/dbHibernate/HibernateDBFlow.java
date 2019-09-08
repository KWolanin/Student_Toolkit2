package wolanin.studentToolkit.dbHibernate;

import org.hibernate.Session;


interface HibernateDBFlow {

	void showAll(Session session);

	void delete(Session session);

	void add(Session session);

}
