package org.example.p2;

import org.example.p2.database.*;
import org.example.p2.domain.Reiziger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        ReizigerDAOPsql reizigerDAOPsql = new ReizigerDAOPsql(getConnectionPSQL());
        testReizigerDAO(reizigerDAOPsql);
        closeConnectionPSQL();
        System.out.println();
        System.out.println("--------------------------- Hibernate ---------------------------");
        System.out.println();
        ReizigerDAOHibernate reizigerDAOHibernate = new ReizigerDAOHibernate(getHibernateSession());
        testReizigerDAO(reizigerDAOHibernate);
        closeSessionHibernate();
    }

    private static void testReizigerDAO(ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers;
        try {
            reizigers = rdao.findAll();
            System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
            System.out.println("------------------------------");
            for (Reiziger r : reizigers) {
                System.out.println(r);
                System.out.println("------------------------------");
            }
        } catch (SQLException | HibernateException e) {
            throw new RuntimeException(e);
        }

        System.out.println("\n-----------------------------------------------------------------\n");

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        Reiziger sietske;
        try {
            String gbdatum = "1981-03-14";
            sietske = new Reiziger(77, "S", "", "Boers", LocalDate.parse(gbdatum));
            System.out.println("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save()\n");
            rdao.save(sietske);
            reizigers = rdao.findAll();
            System.out.println("[Test] Na ReizigerDAO.save(): " + reizigers.size() + " reizigers");
        } catch (SQLException | HibernateException e) {
            throw new RuntimeException(e);
        }

        System.out.println("\n-----------------------------------------------------------------\n");

        // Een reiziger ophalen door middel van een id (id 77 = Sietske)
        try {
            System.out.println("[Test] ReizigerDAO.findById(77) geeft de volgende reiziger:");
            Reiziger reizigerToFind = rdao.findById(77);
            System.out.println(reizigerToFind);
        } catch (SQLException | HibernateException e) {
            throw new RuntimeException(e);
        }

        System.out.println("\n-----------------------------------------------------------------\n");

        // Update een bestaande reiziger

        try {
            System.out.println("[Test] Reiziger voor ReizigerDAO.update():");
            System.out.println(sietske);
            sietske.setGeboortedatum(LocalDate.parse("1981-03-15"));
            System.out.println("\n[Test] Reiziger na ReizigerDAO.update():");
            Reiziger reizigerToFind = rdao.findById(77);
            System.out.println(reizigerToFind);
        } catch (SQLException | HibernateException e) {
            throw new RuntimeException(e);
        }


        System.out.println("\n-----------------------------------------------------------------\n");

        // Delete een bestaande reiziger (id 77 = Sietske)
        try {
            System.out.println("[Test] Lijst van reizigers voor ReizigerDAO.delete():");
            List<Reiziger> reizigerList2 = rdao.findAll();
            for (Reiziger reiziger : reizigerList2) {
                System.out.printf("#%d: %s.%s %s (%s)\n",
                        reiziger.getId(),
                        reiziger.getVoorletters(),
                        tussenvoegselCheck(reiziger),
                        reiziger.getAchternaam(),
                        reiziger.getGeboortedatum().toString());
            }
            rdao.delete(sietske);
            System.out.println("\n[Test] Lijst van reizigers na ReizigerDAO.delete():");
            List<Reiziger> reizigerList3 = rdao.findAll();
            for (Reiziger reiziger : reizigerList3) {
                System.out.printf("#%d: %s.%s %s (%s)\n",
                        reiziger.getId(),
                        reiziger.getVoorletters(),
                        tussenvoegselCheck(reiziger),
                        reiziger.getAchternaam(),
                        reiziger.getGeboortedatum().toString());
            }

            System.out.println("\n-----------------------------------------------------------------\n");

            System.out.println("[Test] Lijst van reizigers met 2002-12-03 als geboortedatum:");
            List<Reiziger> reizigersList3 = rdao.findByGbDatum(LocalDate.parse("2002-12-03"));
            System.out.println("------------------------------");
            for (Reiziger r : reizigersList3) {
                System.out.println(r);
                System.out.println("------------------------------");
            }
        } catch (SQLException | HibernateException e) {
            throw new RuntimeException(e);
        }
    }

    private static String tussenvoegselCheck(Reiziger reiziger){
        if (reiziger.getTussenvoegsel() == null){return "";}
        return " "+reiziger.getTussenvoegsel();
    }

    private static Connection getConnectionPSQL(){
        Connection connection;
        try {
            connection = Database.getConnection();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    private static void closeConnectionPSQL(){
        try {
            Database.closeConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static Session getHibernateSession(){
        Session session;
        try {
            session = HibernateSession.getSessionFactory().openSession();
        } catch (HibernateException h) {
            throw new RuntimeException(h);
        }
        return session;
    }

    private static void closeSessionHibernate(){
        try {
            HibernateSession.shutdown();
        } catch (HibernateException h) {
            throw new RuntimeException(h);
        }
    }
}
