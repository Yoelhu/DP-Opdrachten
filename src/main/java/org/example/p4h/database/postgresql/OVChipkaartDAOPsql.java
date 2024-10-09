package org.example.p4h.database.postgresql;

import org.example.p4h.database.interfaces.OVChipkaartDAO;
import org.example.p4h.domain.OVChipkaart;
import org.example.p4h.domain.Reiziger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OVChipkaartDAOPsql implements OVChipkaartDAO {
    private final Connection connection;

    public OVChipkaartDAOPsql(Connection connection){
        this.connection = connection;
    }


    @Override
    public boolean save(OVChipkaart ovChipkaart) throws SQLException {
        String query = "INSERT INTO ov_chipkaart (kaart_nummer, geldig_tot, klasse, saldo, reiziger_id)" +
                "VALUES (?,?,?,?,?)";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, ovChipkaart.getKaart_nummer());
        preparedStatement.setDate(2, java.sql.Date.valueOf(ovChipkaart.getGeldig_tot()));
        preparedStatement.setInt(3, ovChipkaart.getKlasse());
        preparedStatement.setInt(4, ovChipkaart.getSaldo());
        preparedStatement.setInt(5, ovChipkaart.getReiziger().getId());
        preparedStatement.executeUpdate();

        return true;
    }

    @Override
    public boolean update(OVChipkaart ovChipkaart) throws SQLException {
        String query = "UPDATE ov_chipkaart SET " +
                "geldig_tot = ?," +
                "klasse = ?," +
                "saldo = ?," +
                "reiziger_id = ? " +
                "WHERE kaart_nummer = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setDate(1, java.sql.Date.valueOf(ovChipkaart.getGeldig_tot()));
        preparedStatement.setInt(2, ovChipkaart.getKlasse());
        preparedStatement.setInt(3, ovChipkaart.getSaldo());
        preparedStatement.setInt(4, ovChipkaart.getReiziger().getId());
        preparedStatement.setInt(5, ovChipkaart.getKaart_nummer());
        preparedStatement.executeUpdate();

        return true;
    }

    @Override
    public boolean delete(OVChipkaart ovChipkaart) throws SQLException {
        String query = "DELETE FROM ov_chipkaart WHERE kaart_nummer = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, ovChipkaart.getKaart_nummer());
        preparedStatement.executeUpdate();

        return true;
    }

    @Override
    public ArrayList<OVChipkaart> findByReiziger(Reiziger reiziger) throws SQLException {
        String query = "SELECT * FROM ov_chipkaart WHERE reiziger_id = ? ";
        ArrayList<OVChipkaart> ovChipkaarten = new ArrayList<>();

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, reiziger.getId());
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            ovChipkaarten.add(mapResultSetToOVChipkaart(resultSet));
        }

        return ovChipkaarten;
    }

    @Override
    public ArrayList<OVChipkaart> findAll() throws SQLException {
        String query = "SELECT * FROM ov_chipkaart";
        ArrayList<OVChipkaart> ovChipkaarten = new ArrayList<>();

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            ovChipkaarten.add(mapResultSetToOVChipkaart(resultSet));
        }

        return ovChipkaarten;
    }

    private OVChipkaart mapResultSetToOVChipkaart(ResultSet resultSet) throws SQLException {
        ReizigerDAOPsql reizigerDAOPsql = new ReizigerDAOPsql(connection);
        Reiziger reiziger = reizigerDAOPsql.findById(resultSet.getInt("reiziger_id"));
        return new OVChipkaart(
                resultSet.getInt("kaart_nummer"),
                resultSet.getDate("geldig_tot").toLocalDate(),
                resultSet.getInt("klasse"),
                resultSet.getInt("saldo"),
                reiziger
        );
    }
}
