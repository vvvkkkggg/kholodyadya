package org.vgk.kholodyadya.database;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SingletonSessionFactory {
    static private SessionFactory sessionFactory;

    private SingletonSessionFactory() {
    }

    static public SessionFactory getSessionFactory() throws HibernateException {
        if (sessionFactory != null) {
            sessionFactory = new Configuration()
                    .configure()
                    .buildSessionFactory();
        }
        return sessionFactory;
    }
}