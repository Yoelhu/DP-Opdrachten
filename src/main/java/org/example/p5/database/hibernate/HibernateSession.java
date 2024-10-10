package org.example.p5.database.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateSession {
    private static final SessionFactory sessionFactory;

    static {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public static SessionFactory getSessionFactory() throws HibernateException{
        return sessionFactory;
    }

    public static void shutdown() throws HibernateException {
        getSessionFactory().close();
    }
}
