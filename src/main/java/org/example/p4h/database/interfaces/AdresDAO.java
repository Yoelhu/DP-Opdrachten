package org.example.p4h.database.interfaces;

import org.example.p4h.domain.Adres;
import org.example.p4h.domain.Reiziger;

import java.sql.SQLException;
import java.util.List;

public interface AdresDAO {
    public boolean save(Adres adres) throws SQLException;
    public boolean update(Adres adres) throws SQLException;
    public boolean delete(Adres adres) throws SQLException;
    public Adres findByReiziger(Reiziger reiziger) throws SQLException;
    public List<Adres> findAll() throws SQLException;
}
