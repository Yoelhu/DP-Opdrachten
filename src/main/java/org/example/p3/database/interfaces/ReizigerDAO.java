package org.example.p3.database.interfaces;

import org.example.p3.domain.Reiziger;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface ReizigerDAO {
    public Boolean save(Reiziger reiziger) throws SQLException;
    public Boolean update(Reiziger reiziger) throws SQLException;
    public Boolean delete(Reiziger reiziger) throws SQLException;
    public Reiziger findById(int id) throws SQLException;
    public List<Reiziger> findByGbDatum(LocalDate date) throws SQLException;
    public List<Reiziger> findAll() throws SQLException;
}
