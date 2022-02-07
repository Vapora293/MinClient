package com.worwafi;

import java.text.DecimalFormat;

public class Vysledok {
    DecimalFormat df2 = new DecimalFormat("#.##");
    DecimalFormat df0 = new DecimalFormat("#");
    int vysledok_x;
    int vysledok_y;
    double final_vysledok;
    double uhol;
    boolean sever;
    boolean juh;
    boolean zapad;
    boolean vychod;
    String sv_strana;
    SvStranaEnum odlet_draha;
    SvStranaEnum prilet_draha;
    void vytvorenie_vysledku (int x, int y, double sucet) {
        vysledok_x = x;
        vysledok_y = y;
        final_vysledok = sucet;
    }
    void zistenie_svstran_horedole(int x, int y) {
        if(x > y) {
            sever = true;
        }
        else {
            juh = true;
        }
    }
    void zistenie_svstran_vlavovpravo(int x, int y) {
        if (x < y) {
            vychod = true;
        }
        else {
            zapad = true;
        }
    }
    void vypocet_uhla() {
        if (zapad == true && sever == true) {
            uhol = Math.toDegrees(Math.asin(vysledok_y / final_vysledok));
            uhol = 90 + uhol;
            sv_strana = "severozapad";
        }
        else if (zapad == true && juh == true) {
            uhol = Math.toDegrees(Math.asin(vysledok_y / final_vysledok));
            sv_strana = "juhozapad";
        }
        else if (vychod == true && sever == true) {
            uhol = Math.toDegrees(Math.asin(vysledok_x / final_vysledok));
            uhol = -180 + uhol;
            sv_strana = "severovychod";
        }
        else if (vychod == true && juh == true) {
            uhol = Math.toDegrees(Math.asin(vysledok_x / final_vysledok));
            uhol = 0 - uhol;
            sv_strana = "juhovychod";
        }
    }
    String vypis() {
        return "Let bude dlhy " + df0.format(final_vysledok) + " kociek\nco cini " + df2.format((final_vysledok/5)) + " kilometrov \n" +
                "Nastavte kolesa na " + odlet_draha.toString() + "\ndrahu " + "a lette na " + sv_strana + "\npod uhlom " + df2.format(uhol) + "\nna " + prilet_draha.toString() + " drahu"
                + "\nLet bude trvat " + df0.format(final_vysledok/21.6) + "\nsekund";
    }
}