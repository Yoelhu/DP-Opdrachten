package org.example.p4.database.hibernate;

import org.example.p4.database.interfaces.ReizigerDAO;
import org.example.p4.domain.Reiziger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.time.LocalDate;
import java.util.List;

public class ReizigerDAOHibernate implements ReizigerDAO {

    private Session session;

    public ReizigerDAOHibernate(Session session) {
        this.session = session;
    }

    @Override
    public Boolean save(Reiziger reiziger) throws HibernateException {
        session.beginTransaction();
        session.persist(reiziger);
        session.getTransaction().commit();
        return true;
    }

    @Override
    public Boolean update(Reiziger reiziger) throws HibernateException {
        session.beginTransaction();
        session.merge(reiziger);
        session.getTransaction().commit();
        return true;
    }

    @Override
    public Boolean delete(Reiziger reiziger) throws HibernateException {
        session.beginTransaction();
        session.remove(reiziger);
        session.getTransaction().commit();
        return true;
    }

    @Override
    public Reiziger findById(int id) throws HibernateException {
        Reiziger reiziger;
        reiziger = session.get(Reiziger.class, id);
        return reiziger;
    }

    @Override
    public List<Reiziger> findByGbDatum(LocalDate date) throws HibernateException {
        List<Reiziger> reizigers;
        String query = "select reiziger from Reiziger reiziger where reiziger.geboortedatum = :date";
        reizigers = session.createQuery(query, Reiziger.class).setParameter("date", date).list();
        return reizigers;
    }

    @Override
    public List<Reiziger> findAll() throws HibernateException {
        List<Reiziger> reizigers;
        reizigers = session.createQuery("select reiziger from Reiziger reiziger", Reiziger.class).list();
        return reizigers;
    }
}
