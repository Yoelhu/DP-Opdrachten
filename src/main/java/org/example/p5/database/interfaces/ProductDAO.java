package org.example.p5.database.interfaces;

import org.example.p5.domain.OVChipkaart;
import org.example.p5.domain.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductDAO {
    boolean save(Product product) throws SQLException;
    boolean update(Product product) throws SQLException;
    boolean delete(Product product) throws SQLException;
    List<Product> findAll() throws SQLException;
    List<Product> findByOvChipkaart(OVChipkaart ovChipkaart) throws SQLException;
    Product findById(Product product) throws SQLException;
}
