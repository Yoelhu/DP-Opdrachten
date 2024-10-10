package org.example.p5.database.hibernate;

import org.example.p5.database.interfaces.ProductDAO;
import org.example.p5.domain.OVChipkaart;
import org.example.p5.domain.Product;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.List;

public class ProductDAOHibernate implements ProductDAO {
    private Session session;

    public ProductDAOHibernate(Session session){
        this.session = session;
    }

    @Override
    public boolean save(Product product) throws HibernateException {
        session.beginTransaction();
        session.persist(product);
        session.getTransaction().commit();
        return true;
    }

    @Override
    public boolean update(Product product) throws HibernateException {
        session.beginTransaction();
        session.merge(product);
        session.getTransaction().commit();
        return true;
    }

    @Override
    public boolean delete(Product product) throws HibernateException {
        for (OVChipkaart ovChipkaart : product.getOvChipkaarten()){
            ovChipkaart.removeProduct(product);
        }
        session.beginTransaction();
        session.remove(product);
        session.getTransaction().commit();

        return true;
    }

    @Override
    public List<Product> findAll() throws HibernateException {
        List<Product> producten = session.createQuery("FROM Product", Product.class).list();
        return producten;
    }

    @Override
    public List<Product> findByOvChipkaart(OVChipkaart ovChipkaart) throws HibernateException {
        String query = "SELECT product FROM Product product JOIN product.ovChipkaarten ovck WHERE ovck.kaart_nummer = :kaart_nummer";
        List<Product> producten = session.createQuery(query, Product.class).setParameter("kaart_nummer", ovChipkaart.getKaart_nummer()).list();
        return producten;
    }

    @Override
    public Product findById(Product product) throws HibernateException {
        Product productToFind = session.get(Product.class, product.getProduct_nummer());
        return productToFind;
    }
}
