package org.example.p4.domain;

import java.time.LocalDate;

public class OVChipkaart {
    private int kaart_nummer;
    private LocalDate geldig_tot;
    private int klasse;
    private int saldo;
    private Reiziger reiziger;

    public OVChipkaart(int kaart_nummer, LocalDate geldig_tot, int klasse, int saldo, Reiziger reiziger){
        this.kaart_nummer = kaart_nummer;
        this.geldig_tot = geldig_tot;
        this.klasse = klasse;
        this.saldo = saldo;
        this.reiziger = reiziger;
    }

    public int getKaart_nummer() {
        return kaart_nummer;
    }

    public LocalDate getGeldig_tot() {
        return geldig_tot;
    }

    public int getKlasse() {
        return klasse;
    }

    public int getSaldo() {
        return saldo;
    }

    public Reiziger getReiziger() {
        return reiziger;
    }

    public void setKaart_nummer(int kaart_nummer) {
        this.kaart_nummer = kaart_nummer;
    }

    public void setGeldig_tot(LocalDate geldig_tot) {
        this.geldig_tot = geldig_tot;
    }

    public void setKlasse(int klasse) {
        this.klasse = klasse;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public void setReiziger(Reiziger reiziger) {
        this.reiziger = reiziger;
    }

    @Override
    public String toString() {
        return "OVChipkaart{" +
                "kaart_nummer=" + kaart_nummer +
                ", geldig_tot=" + geldig_tot +
                ", klasse=" + klasse +
                ", saldo=" + saldo +
                ", reiziger=" + reiziger +
                '}';
    }
}
