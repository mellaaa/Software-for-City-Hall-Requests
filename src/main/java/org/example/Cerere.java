package org.example;

public class Cerere {
    String text, numeUtilizator;
    TipCerere tip;
    int prioritate;

    Cerere(String text, int prior, String numeUtilizator, TipCerere tip) {
        this.text = text;
        this.prioritate = prior;
        this.numeUtilizator = numeUtilizator;
        this.tip = tip;
    }
}
