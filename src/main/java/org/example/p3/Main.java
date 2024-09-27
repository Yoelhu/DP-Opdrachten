package org.example.p3;

import org.example.p3.database.*;
import org.example.p3.database.hibernate.HibernateSession;
import org.example.p3.database.hibernate.ReizigerDAOHibernate;
import org.example.p3.database.interfaces.AdresDAO;
import org.example.p3.database.interfaces.ReizigerDAO;
import org.example.p3.database.postgresql.AdresDAOPsql;
import org.example.p3.database.postgresql.ReizigerDAOPsql;
import org.example.p3.domain.Adres;
import org.example.p3.domain.Reiziger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        testAdresDAO(new AdresDAOPsql(getConnectionPSQL()), new ReizigerDAOPsql(getConnectionPSQL()));
    }

    private static void testReizigerDAO(ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = new ArrayList<>();
        try {
            reizigers = rdao.findAll();
            System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
            System.out.println("------------------------------");
            for (Reiziger r : reizigers) {
                System.out.println(r);
                System.out.println("------------------------------");
            }
        } catch (SQLException | HibernateException e) {
            e.printStackTrace();
        }

        System.out.println("\n-----------------------------------------------------------------\n");

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        Reiziger sietske = null;
        try {
            String gbdatum = "1981-03-14";
            sietske = new Reiziger(77, "S", "", "Boers", LocalDate.parse(gbdatum));
            System.out.println("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save()\n");
            rdao.save(sietske);
            reizigers = rdao.findAll();
            System.out.println("[Test] Na ReizigerDAO.save(): " + reizigers.size() + " reizigers");
        } catch (SQLException | HibernateException e) {
            e.printStackTrace();
        }

        System.out.println("\n-----------------------------------------------------------------\n");

        // Een reiziger ophalen door middel van een id (id 77 = Sietske)
        try {
            System.out.println("[Test] ReizigerDAO.findById(77) geeft de volgende reiziger:");
            Reiziger reizigerToFind = rdao.findById(77);
            System.out.println(reizigerToFind);
        } catch (SQLException | HibernateException e) {
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
        }
    }

    private static void testAdresDAO(AdresDAO adao, ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test AdresDAO -------------");

        // Haal alle adressen op uit de database
        List<Adres> adressen = new ArrayList<>();
        try {
            adressen = adao.findAll();
            System.out.println("[Test] AdresDAO.findAll() geeft de volgende adressen:");
            System.out.println("------------------------------");
            for (Adres adres : adressen) {
                System.out.println(adres);
                System.out.println("------------------------------");
            }
        } catch (SQLException | HibernateException e) {
            e.printStackTrace();
        }

        System.out.println("\n-----------------------------------------------------------------\n");

        // Maak een nieuw adres aan en persisteer deze in de database
        Reiziger nieuweReiziger = null;
        Adres nieuwAdres = null;
        try {
            String gbdatum = "1981-03-14";
            nieuweReiziger = new Reiziger(77, "S", "", "Boers", LocalDate.parse(gbdatum));
            rdao.save(nieuweReiziger);
            nieuwAdres = new Adres(76, "1234AB", "10A", "Sesamstraat", "Amsterdam", nieuweReiziger);
            System.out.println("[Test] Eerst " + adressen.size() + " adressen\n");
            adao.save(nieuwAdres);
            adressen = adao.findAll();
            System.out.println("[Test] Na AdresDAO.save(): " + adressen.size() + " adressen");
        } catch (SQLException | HibernateException e) {
            e.printStackTrace();
        }

        System.out.println("\n-----------------------------------------------------------------\n");

        // Een adres ophalen door middel van de reiziger_id
        try {
            System.out.println("[Test] AdresDAO.findByReiziger(nieuwAdres) geeft het volgende adres:");
            Adres adresToFind = adao.findByReiziger(nieuweReiziger);
            System.out.println(adresToFind);
        } catch (SQLException | HibernateException e) {
            e.printStackTrace();
        }

        System.out.println("\n-----------------------------------------------------------------\n");

        // Update een bestaande adres

        try {
            System.out.println("[Test] Adres voor AdresDAO.update():");
            System.out.println(nieuwAdres);
            nieuwAdres.setPostcode("5678CD");
            nieuwAdres.setHuisnummer("200");
            nieuwAdres.setStraat("Bruggenstraat");
            nieuwAdres.setWoonplaats("Utrecht");
            adao.update(nieuwAdres);
            System.out.println("\n[Test] Adres na AdresDAO.update():");
            Adres adresToFind = adao.findByReiziger(nieuweReiziger);
            System.out.println(adresToFind);
        } catch (SQLException | HibernateException e) {
            e.printStackTrace();
        }


        System.out.println("\n-----------------------------------------------------------------\n");

        // Delete een bestaand adres (id 77 = Sietske)
        try {
            System.out.println("[Test] Lijst van adressen voor AdresDAO.delete():");
            List<Adres> adressenList2 = adao.findAll();
            for (Adres adres : adressenList2) {
                System.out.println(adres);
                System.out.println();
            }
            adao.delete(nieuwAdres);
            rdao.delete(nieuweReiziger);
            System.out.println("\n[Test] Lijst van adressen na AdresDAO.delete():");
            List<Adres> adressenList3 = adao.findAll();
            for (Adres adres : adressenList3) {
                System.out.println(adres);
                System.out.println();
            }
        } catch (SQLException | HibernateException e) {
            e.printStackTrace();
        }

        //Reizigers + Adressen
        try {
            for (Reiziger reiziger : rdao.findAll()){
                Adres reizigerAdres = adao.findByReiziger(reiziger);
                String formattedReiziger = "Reiziger {#%d %s.%s %s, geb. %s, Adres {#%d %s-%s}}".formatted(
                        reiziger.getId(),
                        reiziger.getVoorletters(),
                        tussenvoegselCheck(reiziger),
                        reiziger.getAchternaam(),
                        reiziger.getGeboortedatum(),
                        reizigerAdres.getId(),
                        reizigerAdres.getPostcode(),
                        reizigerAdres.getHuisnummer()
                );
                System.out.println(formattedReiziger);
            }
        } catch (SQLException | HibernateException e) {
            e.printStackTrace();
        }
    }

    private static String tussenvoegselCheck(Reiziger reiziger){
        if (reiziger.getTussenvoegsel() == null){return "";}
        return " "+reiziger.getTussenvoegsel();
    }

    private static Connection getConnectionPSQL(){
        Connection connection = null;
        try {
            connection = Database.getConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    private static void closeConnectionPSQL(){
        try {
            Database.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static Session getHibernateSession(){
        Session session = null;
        try {
            session = HibernateSession.getSessionFactory().openSession();
        } catch (HibernateException h) {
            h.printStackTrace();
        }
        return session;
    }

    private static void closeSessionHibernate(){
        try {
            HibernateSession.shutdown();
        } catch (HibernateException h) {
            h.printStackTrace();
        }
    }
}
