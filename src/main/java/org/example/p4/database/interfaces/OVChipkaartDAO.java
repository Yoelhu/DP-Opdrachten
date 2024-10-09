package org.example.p4.database.interfaces;

import org.example.p4.domain.OVChipkaart;
import org.example.p4.domain.Reiziger;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OVChipkaartDAO {
    boolean save(OVChipkaart ovChipkaart) throws SQLException;
    boolean update(OVChipkaart ovChipkaart) throws SQLException;
    boolean delete(OVChipkaart ovChipkaart) throws SQLException;
    ArrayList<OVChipkaart> findByReiziger(Reiziger reiziger) throws SQLException;
    ArrayList<OVChipkaart> findAll() throws SQLException;
}
