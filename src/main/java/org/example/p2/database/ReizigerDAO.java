package org.example.p2.database;

import org.example.p2.domain.Reiziger;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface ReizigerDAO {
    public Boolean save(Reiziger reiziger);
    public Boolean update(Reiziger reiziger);
    public Boolean delete(Reiziger reiziger);
    public Reiziger findById(int id);
    public List<Reiziger> findByGbDatum(LocalDate date);
    public List<Reiziger> findAll();
}
