package com.sise.bao.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtils {

	private static SessionFactory sessionFactory;
	private static Session session;

//	static {
//		// ����Configuration,�ö������ڶ�ȡhibernate.cfg.xml������ɳ�ʼ��
//		Configuration config = new Configuration().configure();
//		sessionFactory = config.buildSessionFactory();
//	}

	/**
	 * ��ȡSessionFactory
	 * 
	 * @return
	 */
	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			// A SessionFactory is set up once for an application!
			final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
					.configure() // configures settings from hibernate.cfg.xml
					.build();
			try {
				sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
			}
			catch (Exception e) {
				// The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
				// so destroy it manually.
				StandardServiceRegistryBuilder.destroy( registry );
			}
		}

		return sessionFactory;
	}

	public static Session getCurrentSession() {
		return getSessionFactory().openSession();
	}

	public static void closeSession(Session session) {
		if (null != session) {
			session.close();
		}
	}
	
}
