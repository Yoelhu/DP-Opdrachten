package org.example.p2.database;

import org.example.p2.domain.Reiziger;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface ReizigerDAO {
    public Boolean save(Reiziger reiziger) throws SQLException;
    public Boolean update(Reiziger reiziger) throws SQLException;
    public Boolean delete(Reiziger reiziger) throws SQLException;
    public Reiziger findById(int id) throws SQLException;
    public List<Reiziger> findByGbDatum(LocalDate date) throws SQLException;
    public List<Reiziger> findAll() throws SQLException;
}
