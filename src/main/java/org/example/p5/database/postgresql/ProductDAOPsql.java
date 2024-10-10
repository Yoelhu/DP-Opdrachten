package org.example.p5.database.postgresql;

import org.example.p5.database.interfaces.ProductDAO;
import org.example.p5.domain.OVChipkaart;
import org.example.p5.domain.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOPsql implements ProductDAO {
    private final Connection connection;

    public ProductDAOPsql(Connection connection){
        this.connection = connection;
    }

    @Override
    public boolean save(Product product) throws SQLException {
        String query = "INSERT INTO product (product_nummer, naam, beschrijving, prijs)" +
                "VALUES (?,?,?,?)";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, product.getProduct_nummer());
        preparedStatement.setString(2, product.getNaam());
        preparedStatement.setString(3, product.getBeschrijving());
        preparedStatement.setDouble(4, product.getPrijs());
        preparedStatement.executeUpdate();

        saveOVChipkaartProductLink(product);

        return true;
    }

    @Override
    public boolean update(Product product) throws SQLException {
        String query = "UPDATE product SET " +
                "naam = ?," +
                "beschrijving = ?," +
                "prijs = ? " +
                "WHERE product_nummer = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, product.getNaam());
        preparedStatement.setString(2, product.getBeschrijving());
        preparedStatement.setDouble(3, product.getPrijs());
        preparedStatement.setInt(4, product.getProduct_nummer());
        preparedStatement.executeUpdate();

        deleteOVChipkaartProductLink(product);
        saveOVChipkaartProductLink(product);
        return true;
    }

    @Override
    public boolean delete(Product product) throws SQLException {
        deleteOVChipkaartProductLink(product);
        String query = "DELETE FROM product WHERE product_nummer = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, product.getProduct_nummer());
        preparedStatement.executeUpdate();

        for (OVChipkaart ovChipkaart : product.getOvChipkaarten()) {
            ovChipkaart.removeProduct(product);
        }

        return true;
    }

    @Override
    public List<Product> findAll() throws SQLException {
        String query = "SELECT * FROM product";
        List<Product> producten = new ArrayList<>();

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            producten.add(mapResultSetToProduct(resultSet));
        }

        return producten;
    }

    public Product findById(Product product) throws SQLException {
        String query = "SELECT * FROM product WHERE product_nummer = ?";
        Product productToFind = null;

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, product.getProduct_nummer());
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            productToFind = mapResultSetToProduct(resultSet);
        }

        return productToFind;
    }


    private Product mapResultSetToProduct(ResultSet resultSet) throws SQLException {
        return new Product(
                resultSet.getInt("product_nummer"),
                resultSet.getString("naam"),
                resultSet.getString("beschrijving"),
                resultSet.getDouble("prijs")
        );
    }

    @Override
    public List<Product> findByOvChipkaart(OVChipkaart ovChipkaart) throws SQLException {
        String query = "SELECT product.* FROM product " +
                "JOIN ov_chipkaart_product ovcp ON ovcp.product_nummer = product.product_nummer " +
                "WHERE ovcp.kaart_nummer = ?";
        List<Product> producten = new ArrayList<>();

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, ovChipkaart.getKaart_nummer());
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            producten.add(mapResultSetToProduct(resultSet));
        }

        return producten;
    }

    private void saveOVChipkaartProductLink(Product product) throws SQLException {
        String query = "INSERT INTO ov_chipkaart_product (kaart_nummer, product_nummer) VALUES (?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        for (OVChipkaart ovChipkaart : product.getOvChipkaarten()){
            preparedStatement.setInt(1, ovChipkaart.getKaart_nummer());
            preparedStatement.setInt(2, product.getProduct_nummer());
            preparedStatement.executeUpdate();
        }

    }

    private void deleteOVChipkaartProductLink(Product product) throws SQLException {
        String query = "DELETE FROM ov_chipkaart_product WHERE product_nummer = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setInt(1, product.getProduct_nummer());
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }
}
