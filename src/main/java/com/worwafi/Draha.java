package com.worwafi;

import java.util.LinkedList;

public class Draha {
    LinkedList<Integer> drahy_x;
    LinkedList<Integer> drahy_y;
    LinkedList<SvStranaEnum> sv_strany;

    void vytvorenie_drah(int drah_x[], int drah_y[], SvStranaEnum svetkove_strany[]) {
        drahy_x = new LinkedList<Integer>();
        drahy_y = new LinkedList<Integer>();
        sv_strany = new LinkedList<SvStranaEnum>();
        for(int i = 0; i < drah_x.length; i++) {
            drahy_x.add(drah_x[i]);
            drahy_y.add(drah_y[i]);
            sv_strany.add(svetkove_strany[i]);
        }
    }
}
