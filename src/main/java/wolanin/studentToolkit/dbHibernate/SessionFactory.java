package wolanin.studentToolkit.dbHibernate;

import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

class SessionFactory {

	private static class HibernateFactory {

		public org.hibernate.SessionFactory getSessionFactory() {
			Configuration configuration = new Configuration().configure();
			StandardServiceRegistryBuilder registryBuilder =
					new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
			return configuration.buildSessionFactory(registryBuilder.build());
		}
	}
}
