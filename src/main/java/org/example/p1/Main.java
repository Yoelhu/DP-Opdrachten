package org.example.p1;

import org.example.p1.database.ReizigerDAO;
import org.example.p1.domain.Reiziger;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        //Eerst maak ik het reizigerDAO aan
        ReizigerDAO reizigerDAO = new ReizigerDAO();

        //De lijst van reizigers die ik verkrijg van de "getAllReizigers" method sla ik op in het "reizigers" variabel
        List<Reiziger> reizigers = reizigerDAO.getAllReizigers();

        System.out.println("Alle Reizigers:");
        //Ik print de info naar de console met een for-loop
        for (Reiziger reiziger : reizigers){
            System.out.printf("     #%d: %s.%s %s (%s)\n",
                    reiziger.getReizigerID(),
                    reiziger.getVoorletters(),
                    tussenvoegselCheck(reiziger),
                    reiziger.getAchternaam(),
                    reiziger.getGeboortedatum().toString());
        }
    }

    //Hier check ik of de reiziger een tussenvoegsel heeft. Zo niet, dan geef ik een lege String terug.
    private static String tussenvoegselCheck(Reiziger reiziger){
        if (reiziger.getTussenvoegsel() == null){return "";}
        return " "+reiziger.getTussenvoegsel();
    }
}