package org.example.p5.domain;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ov_chipkaart")
public class OVChipkaart {

    @Id
    private int kaart_nummer;
    private LocalDate geldig_tot;
    private int klasse;
    private int saldo;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "reiziger_id")
    private Reiziger reiziger;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "ov_chipkaart_product",
            joinColumns = @JoinColumn(name = "kaart_nummer"),
            inverseJoinColumns = @JoinColumn(name = "product_nummer"))
    private List<Product> producten = new ArrayList<>();

    public OVChipkaart(int kaart_nummer, LocalDate geldig_tot, int klasse, int saldo, Reiziger reiziger){
        this.kaart_nummer = kaart_nummer;
        this.geldig_tot = geldig_tot;
        this.klasse = klasse;
        this.saldo = saldo;
        this.reiziger = reiziger;
    }

    public OVChipkaart() {

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

    public List<Product> getProducten() {
        return producten;
    }

    public void setProducten(List<Product> producten) {
        this.producten = producten;
    }

    public void addProduct(Product product) {
        if (!producten.contains(product)) {
            producten.add(product);
            product.getOvChipkaarten().add(this);
        }
    }

    public void removeProduct(Product product) {
        if (producten.contains(product)) {
            producten.remove(product);
        }
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
