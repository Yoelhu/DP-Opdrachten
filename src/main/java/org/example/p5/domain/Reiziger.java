package org.example.p5.domain;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "reiziger")
public class Reiziger {

    @Id
    @Column(name = "reiziger_id")
    private int id;

    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private LocalDate geboortedatum;

//    @OneToOne
//    private Adres adres;

    @OneToMany(mappedBy = "reiziger", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<OVChipkaart> ovChipkaarten = new ArrayList<>();

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

    public List<OVChipkaart> getOvChipkaarten() {
        return ovChipkaarten;
    }

    public void addOVChipKaart(OVChipkaart ovChipkaart){
        ovChipkaarten.add(ovChipkaart);
    }

    @Override
    public String toString() {
        return "Reiziger{" +
                "id=" + id +
                ", voorletters='" + voorletters + '\'' +
                ", tussenvoegsel='" + tussenvoegsel + '\'' +
                ", achternaam='" + achternaam + '\'' +
                ", geboortedatum=" + geboortedatum +
                '}';
    }
}
