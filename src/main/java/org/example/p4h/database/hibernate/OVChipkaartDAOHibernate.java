package org.example.p4h.database.hibernate;

import org.example.p4h.database.interfaces.OVChipkaartDAO;
import org.example.p4h.domain.OVChipkaart;
import org.example.p4h.domain.Reiziger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.List;

public class OVChipkaartDAOHibernate implements OVChipkaartDAO {

    private Session session;

    public OVChipkaartDAOHibernate(Session session){
        this.session = session;
    }


    @Override
    public boolean save(OVChipkaart ovChipkaart) throws HibernateException {
        session.beginTransaction();
        session.persist(ovChipkaart);
        session.getTransaction().commit();
        return true;
    }

    @Override
    public boolean update(OVChipkaart ovChipkaart) throws HibernateException {
        session.beginTransaction();
        session.merge(ovChipkaart);
        session.getTransaction().commit();
        return true;
    }

    @Override
    public boolean delete(OVChipkaart ovChipkaart) throws HibernateException {
        session.beginTransaction();
        session.remove(ovChipkaart);

        if (ovChipkaart.getReiziger() != null){
            ovChipkaart.getReiziger().getOvChipkaarten().remove(ovChipkaart);
        }
        session.getTransaction().commit();
        return true;
    }

    @Override
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) throws HibernateException {
        session.beginTransaction();
        List<OVChipkaart> ovChipkaarten = session.createQuery("FROM OVChipkaart o WHERE o.reiziger.id = :id",
                OVChipkaart.class).setParameter("id", reiziger.getId()).list();

        session.getTransaction().commit();
        return ovChipkaarten;
    }

    @Override
    public List<OVChipkaart> findAll() throws HibernateException {
        session.beginTransaction();
        List<OVChipkaart> ovChipkaarten = session.createQuery("from OVChipkaart", OVChipkaart.class).list();
        session.getTransaction().commit();
        return ovChipkaarten;
    }
}
