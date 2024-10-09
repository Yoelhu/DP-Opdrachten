package org.example.p4;

import org.example.p4.database.*;
import org.example.p4.database.hibernate.HibernateSession;
import org.example.p4.database.interfaces.AdresDAO;
import org.example.p4.database.interfaces.OVChipkaartDAO;
import org.example.p4.database.interfaces.ReizigerDAO;
import org.example.p4.database.postgresql.OVChipkaartDAOPsql;
import org.example.p4.database.postgresql.ReizigerDAOPsql;
import org.example.p4.domain.Adres;
import org.example.p4.domain.OVChipkaart;
import org.example.p4.domain.Reiziger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        testOVChipkaartDAO(new ReizigerDAOPsql(getConnectionPSQL()), new OVChipkaartDAOPsql(getConnectionPSQL()));
        closeConnectionPSQL();
    }

    private static void testReizigerDAO(ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = new ArrayList<>();
            reizigers = rdao.findAll();
            System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
            System.out.println("------------------------------");
            for (Reiziger r : reizigers) {
                System.out.println(r);
                System.out.println("------------------------------");
            }


        System.out.println("\n-----------------------------------------------------------------\n");

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        Reiziger sietske = null;
            String gbdatum = "1981-03-14";
            sietske = new Reiziger(77, "S", "", "Boers", LocalDate.parse(gbdatum));
            System.out.println("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save()\n");
            rdao.save(sietske);
            reizigers = rdao.findAll();
            System.out.println("[Test] Na ReizigerDAO.save(): " + reizigers.size() + " reizigers");

        System.out.println("\n-----------------------------------------------------------------\n");

        // Een reiziger ophalen door middel van een id (id 77 = Sietske)

        System.out.println("[Test] ReizigerDAO.findById(77) geeft de volgende reiziger:");
        Reiziger reizigerToFind = rdao.findById(77);
        System.out.println(reizigerToFind);

        System.out.println("\n-----------------------------------------------------------------\n");

        // Update een bestaande reiziger


            System.out.println("[Test] Reiziger voor ReizigerDAO.update():");
            System.out.println(sietske);
            sietske.setGeboortedatum(LocalDate.parse("1981-03-15"));
            System.out.println("\n[Test] Reiziger na ReizigerDAO.update():");
            reizigerToFind = rdao.findById(77);
            System.out.println(reizigerToFind);



        System.out.println("\n-----------------------------------------------------------------\n");

        // Delete een bestaande reiziger (id 77 = Sietske)
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

            String gbdatum = "1981-03-14";
            nieuweReiziger = new Reiziger(77, "S", "", "Boers", LocalDate.parse(gbdatum));
            rdao.save(nieuweReiziger);
            nieuwAdres = new Adres(76, "1234AB", "10A", "Sesamstraat", "Amsterdam", nieuweReiziger);
            System.out.println("[Test] Eerst " + adressen.size() + " adressen\n");
            adao.save(nieuwAdres);
            adressen = adao.findAll();
            System.out.println("[Test] Na AdresDAO.save(): " + adressen.size() + " adressen");


        System.out.println("\n-----------------------------------------------------------------\n");

        // Een adres ophalen door middel van de reiziger_id

            System.out.println("[Test] AdresDAO.findByReiziger(nieuwAdres) geeft het volgende adres:");
            Adres adresToFind = adao.findByReiziger(nieuweReiziger);
            System.out.println(adresToFind);


        System.out.println("\n-----------------------------------------------------------------\n");

        // Update een bestaande adres


            System.out.println("[Test] Adres voor AdresDAO.update():");
            System.out.println(nieuwAdres);
            nieuwAdres.setPostcode("5678CD");
            nieuwAdres.setHuisnummer("200");
            nieuwAdres.setStraat("Bruggenstraat");
            nieuwAdres.setWoonplaats("Utrecht");
            adao.update(nieuwAdres);
            System.out.println("\n[Test] Adres na AdresDAO.update():");
            adresToFind = adao.findByReiziger(nieuweReiziger);
            System.out.println(adresToFind);



        System.out.println("\n-----------------------------------------------------------------\n");

        // Delete een bestaand adres (id 77 = Sietske)

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


        //Reizigers + Adressen

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

    }

    private static void testOVChipkaartDAO(ReizigerDAO reizigerDAO, OVChipkaartDAO ovChipkaartDAO) throws SQLException {
        System.out.println("------------- Test OVChipkaartDAO -------------");
        System.out.println("FindAll():");
        for (OVChipkaart ovChipkaart : ovChipkaartDAO.findAll()) {
            System.out.println(ovChipkaart);
        }

        //save()
        Reiziger reiziger = new Reiziger(100, "Y", "el", "Ouamari", LocalDate.parse("2003-06-20"));
        OVChipkaart nieuweOvChipkaart = new OVChipkaart(99999, LocalDate.parse("2025-07-15"), 1, 20, reiziger);
        reiziger.addOVChipKaart(nieuweOvChipkaart);
        reizigerDAO.save(reiziger);

        System.out.println("\nFindByReiziger():");
        //krijg de ov chipkaarten van een reiziger terug
        ArrayList<OVChipkaart> ovChipkaarten = ovChipkaartDAO.findByReiziger(reiziger);
        for (OVChipkaart ovChipkaart : ovChipkaarten){
            System.out.println(ovChipkaart);
        }


        System.out.println("\nUpdate():");
        System.out.println("Saldo voor update:");
        System.out.println(nieuweOvChipkaart.getSaldo());
        System.out.println("Saldo na update:");
        nieuweOvChipkaart.setSaldo(50);
        ovChipkaartDAO.update(nieuweOvChipkaart);
        ArrayList<OVChipkaart> ovChipkaarten2 = ovChipkaartDAO.findByReiziger(reiziger);
        for (OVChipkaart ovChipkaart : ovChipkaarten2){
            System.out.println(ovChipkaart.getSaldo());
        }


        System.out.println("\nDelete():");
        System.out.println("Voor delete:");
        System.out.println(nieuweOvChipkaart);
        ovChipkaartDAO.delete(nieuweOvChipkaart);
        ArrayList<OVChipkaart> ovChipkaarten3 = ovChipkaartDAO.findByReiziger(reiziger);
        System.out.println("Na delete:");
        if (ovChipkaarten3.isEmpty()){
            System.out.println(reiziger.getVoorletters()+". "+reiziger.getTussenvoegsel()+" "+reiziger.getAchternaam()+" heeft geen ov chipkaart(en)");
        }
        reizigerDAO.delete(reiziger);
    }

    private static String tussenvoegselCheck(Reiziger reiziger){
        if (reiziger.getTussenvoegsel() == null){return "";}
        return " "+reiziger.getTussenvoegsel();
    }

    private static Connection getConnectionPSQL() throws SQLException, ClassNotFoundException {
        Connection connection;
        connection = Database.getConnection();
        return connection;
    }

    private static void closeConnectionPSQL() throws SQLException {
        Database.closeConnection();
    }

    private static Session getHibernateSession() throws HibernateException {
        Session session = null;
        session = HibernateSession.getSessionFactory().openSession();
        return session;
    }

    private static void closeSessionHibernate() throws HibernateException{
        HibernateSession.shutdown();
    }
}
