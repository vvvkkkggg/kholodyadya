package org.vgk.kholodyadya.database;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SingletonSessionFactory {
    private static SessionFactory sessionFactory;

    private SingletonSessionFactory() {
    }

    public static SessionFactory getSessionFactory() throws HibernateException {
        if (sessionFactory == null) {
            sessionFactory = new Configuration()
                    .configure()
                    .buildSessionFactory();
        }
        return sessionFactory;
    }
}