package com.worwafi;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;


public class ControllerLetiskovyVypravca implements Initializable {
    @FXML
    private Canvas mapa;
    @FXML
    private JFXCheckBox checkHistory;
    @FXML
    private JFXCheckBox checkNightMap;
    @FXML
    private JFXButton buttonSpat;
    @FXML
    private JFXButton buttonKreslenie;
    @FXML
    private JFXCheckBox checkHide;
    @FXML
    private JFXTextField textfieldKoniec;
    @FXML
    private JFXTextField textfieldPociatok;
    @FXML
    private JFXTextArea textAreaVypis;
    @FXML
    private AnchorPane pozadie;
    @FXML
    private AnchorPane buttons;

    Letisko minebeach = new Letisko();
    Letisko stara_huta = new Letisko();
    Letisko westhigh = new Letisko();
    Letisko terramia = new Letisko();
    Letisko lemnos = new Letisko();
    Letisko sihla = new Letisko();
    Letisko dosadnikovo = new Letisko();
    Letisko iranium = new Letisko();
    Letisko runina = new Letisko();
    LinkedList<Letisko> zoznam = new LinkedList<Letisko>();
    File file_svetle_pozadie = new File("C:\\Users\\Filip\\intellij-workspace\\MinClient\\src\\main\\resources\\fxml\\svetle_pozadie.png");
    Image svetle_pozadie = new Image(file_svetle_pozadie.toURI().toString());
    File file_tmave_pozadie = new File("C:\\Users\\Filip\\intellij-workspace\\MinClient\\src\\main\\resources\\fxml\\tmave_pozadie.png");
    Image tmave_pozadie = new Image(file_tmave_pozadie.toURI().toString());
    boolean zaciatok = true;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nastuckovanie();
        GraphicsContext kr = mapa.getGraphicsContext2D();
        checkHistory.selectedProperty().addListener(observable -> {
            if(checkHistory.isSelected() == true) {
                kr.clearRect(0,0,1000,1000);
            }
        });
        checkNightMap.selectedProperty().addListener(observable -> {
            if (checkNightMap.isSelected()) {
                BackgroundImage bgImg = new BackgroundImage(tmave_pozadie,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.DEFAULT,
                        BackgroundSize.DEFAULT);
                pozadie.setBackground(new Background(bgImg));
            }
            if (!checkNightMap.isSelected()) {
                BackgroundImage bgImg = new BackgroundImage(svetle_pozadie,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.DEFAULT,
                        BackgroundSize.DEFAULT);
                pozadie.setBackground(new Background(bgImg));
            }
        });
        checkHide.selectedProperty().addListener(observable -> {
            if (checkHide.isSelected()) {
                skryvanie(false);
            }
            else {
                skryvanie(true);
            }
        });
        for(int i = 0; i < buttons.getChildren().size(); i++) {
            JFXButton aktualny = (JFXButton) buttons.getChildren().get(i);
            int finalI = i;
            aktualny.setOnAction(event -> {
                tlacitko(zoznam.get(finalI));
            });
        }
        buttonKreslenie.setOnAction(event -> {
            if(checkHistory.isSelected()) {
                kr.clearRect(0,0,1000,1000);
            }
            try {
                Letisko odlet;
                Letisko prilet;
                odlet = zistenie_miest(textfieldPociatok.getText());
                prilet = zistenie_miest(textfieldKoniec.getText());
                Vysledok koniec = vypocet(odlet, prilet);
                koniec.vypocet_uhla();
                textAreaVypis.setText(koniec.vypis());
                kr.setStroke(Color.rgb(247, 127, 0));
                kr.setLineWidth(10);
                kr.strokeLine(odlet.suradnica_x, odlet.suradnica_y, prilet.suradnica_x,prilet.suradnica_y);
            }
            catch (Exception e) {
                textAreaVypis.setText("Zadali ste nespravne udaje\nskuste prosim znova");
            }
        });
        buttonSpat.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main_screen.fxml"));
            Stage stageLetiskovyVypravca = (Stage) buttonSpat.getScene().getWindow();
            try {
                stageLetiskovyVypravca.setScene(
                        new Scene(loader.load())
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
            stageLetiskovyVypravca.show();
        });
    }

    public Vysledok vypocet(Letisko odlet, Letisko prilet) {
        Vysledok koniec = new Vysledok();
        koniec.final_vysledok = 250000;
        for(int i = 0; i < odlet.vsetky_drahy.drahy_x.size(); i++) {
            for(int j = 0; j < prilet.vsetky_drahy.drahy_x.size(); j++) {
                Vysledok medzivypocet = porovnanie(odlet.vsetky_drahy.drahy_x.get(i), odlet.vsetky_drahy.drahy_y.get(i), prilet.vsetky_drahy.drahy_x.get(j), prilet.vsetky_drahy.drahy_y.get(j));
                if(medzivypocet.final_vysledok < koniec.final_vysledok) {
                    koniec = medzivypocet;
                    koniec.odlet_draha = odlet.vsetky_drahy.sv_strany.get(i);
                    koniec.prilet_draha = prilet.vsetky_drahy.sv_strany.get(j);
                }
            }
        }
        koniec.zistenie_svstran_vlavovpravo(odlet.vsetky_drahy.drahy_x.get(0), prilet.vsetky_drahy.drahy_x.get(0));
        koniec.zistenie_svstran_horedole(odlet.vsetky_drahy.drahy_y.get(0), prilet.vsetky_drahy.drahy_y.get(0));
        return koniec;
    }
    public Vysledok porovnanie (int odlet_draha_x, int odlet_draha_y, int prilet_draha_x, int prilet_draha_y) {
        int vysledok_x = 0;
        int vysledok_y = 0;
        double final_vysledok = 0;
        vysledok_x = znamienka(odlet_draha_x, prilet_draha_x);
        vysledok_y = znamienka(odlet_draha_y, prilet_draha_y);
        final_vysledok = Math.pow((double)vysledok_x, 2) + Math.pow((double)vysledok_y, 2);
        final_vysledok = Math.sqrt(final_vysledok);
        Vysledok koniec = new Vysledok();
        koniec.vytvorenie_vysledku(vysledok_x, vysledok_y, final_vysledok);;
        return koniec;
    }
    public Letisko zistenie_miest(String textField) {
        for(int i = 0; i < zoznam.size(); i++) {
            if(textField.equals(zoznam.get(i).mesto)) {
                return zoznam.get(i);
            }
        }
        return null;
    }
    public int znamienka(int odlet_draha, int prilet_draha) {
        int vysledok;
        if (sposob(odlet_draha, prilet_draha) == 1) {
            vysledok = Math.abs(odlet_draha - prilet_draha);
        }
        else {
            if(odlet_draha < 0) {
                vysledok = Math.abs(odlet_draha) + prilet_draha;
            }
            else {
                vysledok = odlet_draha + Math.abs(prilet_draha);
            }
        }
        return vysledok;
    }
    public int sposob(int jedna, int dva) {
        if ((jedna < 0 && dva < 0) || (jedna > 0 && dva > 0)) {
            return 1;
        }
        else {
            return 2;
        }
    }
    public void nastuckovanie() {
        int minebeach_drahy_x[] = {905, 905, 813, 1053};
        int minebeach_drahy_y[] = {562, 847, 787, 787};
        SvStranaEnum minebeach_sv_strany[] = {SvStranaEnum.SEVER, SvStranaEnum.JUH, SvStranaEnum.ZAPAD, SvStranaEnum.VYCHOD};
        minebeach.vytvorenie_zaznamu("Minebeach", minebeach_drahy_x, minebeach_drahy_y, minebeach_sv_strany, 640, 640);
        zoznam.addFirst(minebeach);

        int stara_huta_drahy_x[] = {-1724, -1724};
        int stara_huta_drahy_y[] = {2687, 2850};
        SvStranaEnum stara_huta_sv_strany[] = {SvStranaEnum.SEVER, SvStranaEnum.JUH};
        stara_huta.vytvorenie_zaznamu("Stara Huta", stara_huta_drahy_x, stara_huta_drahy_y, stara_huta_sv_strany, 460,790);
        zoznam.add(stara_huta);

        int westhigh_drahy_x[] = {-3545, -3545, -3829};
        int westhigh_drahy_y[] = {-1709, -1312, -1476};
        SvStranaEnum westhigh_sv_strany[] = {SvStranaEnum.SEVER, SvStranaEnum.JUH, SvStranaEnum.ZAPAD};
        westhigh.vytvorenie_zaznamu("Westhigh", westhigh_drahy_x, westhigh_drahy_y, westhigh_sv_strany, 333, 478);
        zoznam.add(westhigh);

        int terramia_drahy_x[] = {-4647, -4647};
        int terramia_drahy_y[] = {-3133, -2891};
        SvStranaEnum terramia_sv_strany[] = {SvStranaEnum.SEVER, SvStranaEnum.JUH};
        terramia.vytvorenie_zaznamu("Terramia", terramia_drahy_x, terramia_drahy_y,terramia_sv_strany, 255, 368);
        zoznam.add(terramia);

        int lemnos_drahy_x[] = {-8075, -7928};
        int lemnos_drahy_y[] = {-3849, -3849};
        SvStranaEnum lemnos_sv_strany[] = {SvStranaEnum.ZAPAD, SvStranaEnum.VYCHOD};
        lemnos.vytvorenie_zaznamu("Lemnos", lemnos_drahy_x, lemnos_drahy_y, lemnos_sv_strany, 25, 305);
        zoznam.add(lemnos);

        int sihla_drahy_x[] = {-4450, -4395};
        int sihla_drahy_y[] = {-7166, -7166};
        SvStranaEnum sihla_sv_strany[] = {SvStranaEnum.ZAPAD, SvStranaEnum.VYCHOD};
        sihla.vytvorenie_zaznamu("Sihla", sihla_drahy_x, sihla_drahy_y, sihla_sv_strany, 255, 65);
        zoznam.add(sihla);

        int dosadnikovo_drahy_x[] = {315, 315, 440};
        int dosadnikovo_drahy_y[] = {-203, -41, -81};
        SvStranaEnum dosadnikovo_sv_strany[] = {SvStranaEnum.SEVER, SvStranaEnum.JUH, SvStranaEnum.VYCHOD};
        dosadnikovo.vytvorenie_zaznamu("Dosadnikovo", dosadnikovo_drahy_x, dosadnikovo_drahy_y, dosadnikovo_sv_strany, 600, 580);
        zoznam.add(dosadnikovo);

        int iranium_drahy_x[] = {2028, 2314};
        int iranium_drahy_y[] = {-1243, -1243};
        SvStranaEnum iranum_sv_strany[] = {SvStranaEnum.ZAPAD, SvStranaEnum.VYCHOD};
        iranium.vytvorenie_zaznamu("Iranium", iranium_drahy_x, iranium_drahy_y, iranum_sv_strany, 730, 500);
        zoznam.add(iranium);
        int runina_drahy_x[] = {-4386, -4386};
        int runina_drahy_y[] = {1467, 1181};
        SvStranaEnum runina_sv_strany[] = {SvStranaEnum.JUH, SvStranaEnum.SEVER};
        runina.vytvorenie_zaznamu("Runina", runina_drahy_x, runina_drahy_y, runina_sv_strany, 270, 690);
        zoznam.add(runina);
    }
    public void tlacitko(Letisko ahora) {
        if(zaciatok == true) {
            textfieldPociatok.setText(ahora.mesto);
            zaciatok = false;
        }
        else {
            textfieldKoniec.setText(ahora.mesto);
            zaciatok = true;
        }
    }
    public void skryvanie(boolean hodnota) {
        /*for(int i = 0; i < buttons.getChildren().size(); i++) {
            JFXButton aktualny = (JFXButton) buttons.getChildren().get(i);
            aktualny.setVisible(hodnota);
        }*/
        for (Node child : buttons.getChildren()) {
            child.setVisible(hodnota);
        }
    }
}

