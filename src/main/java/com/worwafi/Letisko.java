package com.worwafi;

import java.util.LinkedList;

public class Letisko {
    String mesto;
    int suradnica_x;
    int suradnica_y;
    Draha vsetky_drahy;

    void vytvorenie_zaznamu(String nazov, int vstupne_drahy_x[], int vstupne_drahy_y[], SvStranaEnum vstupne_sv_strany[], int vstupna_suradnica_x, int vstupna_suradnica_y) {
        mesto = nazov;
        vsetky_drahy = new Draha();
        vsetky_drahy.vytvorenie_drah(vstupne_drahy_x, vstupne_drahy_y, vstupne_sv_strany);
        suradnica_x = vstupna_suradnica_x;
        suradnica_y = vstupna_suradnica_y;
    }
}
