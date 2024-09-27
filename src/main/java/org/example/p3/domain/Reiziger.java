package org.example.p3.domain;


import jakarta.persistence.*;


import java.time.LocalDate;

//@Entity
//@Table(name = "reiziger")
public class Reiziger {

//    @Id
//    @Column(name = "reiziger_id")
    private int id;

    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private LocalDate geboortedatum;

//    @OneToOne
    private Adres adres;

    public Reiziger(){}

    public Reiziger(
            int id,
            String voorletters,
            String tussenvoegsel,
            String achternaam,
            LocalDate geboortedatum){
        this.id = id;
        this.voorletters = voorletters;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.geboortedatum = geboortedatum;
    }

    public int getId() {
        return id;
    }

    public String getVoorletters() {
        return voorletters;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public LocalDate getGeboortedatum() {
        return geboortedatum;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setVoorletters(String voorletters) {
        this.voorletters = voorletters;
    }

    public void setTussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel = tussenvoegsel;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public void setGeboortedatum(LocalDate geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    public String toString(){
        if (tussenvoegsel == null || tussenvoegsel.isEmpty()){
            return "Reiziger: "+voorletters+". "+achternaam+"\nID: "+id+"\nGeboortedatum: "+geboortedatum;
        }
        return "Reiziger: "+voorletters+". "+tussenvoegsel+" "+achternaam+"\nID: "+id+"\nGeboortedatum: "+geboortedatum;
    }
}
