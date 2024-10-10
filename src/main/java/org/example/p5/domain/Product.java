package org.example.p5.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
public class Product {

    @Id
    private int product_nummer;
    private String naam;
    private String beschrijving;
    private double prijs;

    @ManyToMany(mappedBy = "producten")
    private List<OVChipkaart> ovChipkaarten = new ArrayList<>();

    public Product(int product_nummer, String naam, String beschrijving, double prijs, List<OVChipkaart> ovChipkaarten){
        this.product_nummer = product_nummer;
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.prijs = prijs;
        this.ovChipkaarten = ovChipkaarten;
    }

    public Product(int product_nummer, String naam, String beschrijving, double prijs){
        this.product_nummer = product_nummer;
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.prijs = prijs;
    }

    public Product() {

    }

    public int getProduct_nummer() {
        return product_nummer;
    }

    public String getNaam() {
        return naam;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public double getPrijs() {
        return prijs;
    }

    public void setProduct_nummer(int product_nummer) {
        this.product_nummer = product_nummer;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public void setPrijs(double prijs) {
        this.prijs = prijs;
    }

    public List<OVChipkaart> getOvChipkaarten() {
        return ovChipkaarten;
    }

    public void setOvChipkaarten(List<OVChipkaart> ovChipkaarten) {
        this.ovChipkaarten = ovChipkaarten;
    }

    public void addOVChipkaart(OVChipkaart ovChipkaart){
        ovChipkaarten.add(ovChipkaart);
    }

    @Override
    public String toString() {
        return "Product{" +
                "product_nummer=" + product_nummer +
                ", naam='" + naam + '\'' +
                ", beschrijving='" + beschrijving + '\'' +
                ", prijs=" + prijs +
                ", ovChipkaarten=" + ovChipkaarten +
                '}';
    }
}
