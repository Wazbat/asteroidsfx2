/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroidsfx2;

import java.util.ArrayList;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

/**
 *
 * @author Wazbat
 */
public class Explosion {
private ArrayList<Polygon> trozos = new ArrayList();
public int vida=30;
private final double VELOCIDAD_TROZO = 2;
private final double ANGULO_PRINCIPAL;

public Explosion(double posX , double posY, double angulo, Pane root){
    ANGULO_PRINCIPAL = angulo;
    double anguloTrozo = ANGULO_PRINCIPAL;
    for (int i = 0; i < 8; i++) {
        Polygon trozo = new Polygon();
        trozo.getPoints().addAll(new Double[]{
        -0.5, -7.5,
        0.5, -7.5,
        0.5, 7.5,
        -0.5, 7.5});
        root.getChildren().add(trozo);
        trozo.setFill(Color.RED);
        trozo.setRotate(anguloTrozo);
        
        trozo.setLayoutX(posX);
        trozo.setLayoutY(posY);
        trozos.add(trozo);
        anguloTrozo+=45;   
    }   
}
public void actualizar(Pane root){
    double anguloTrozo=ANGULO_PRINCIPAL;
    for(int i = 0; i < 8; i++){
        double variaX=Math.sin(Math.toRadians(anguloTrozo));
        double variaY=Math.cos(Math.toRadians(anguloTrozo))*-1;
        trozos.get(i).setLayoutX(trozos.get(i).getLayoutX()+VELOCIDAD_TROZO*variaX);
        trozos.get(i).setLayoutY(trozos.get(i).getLayoutY()+VELOCIDAD_TROZO*variaY);
        anguloTrozo+=45;
    }
    vida--;
    if (vida<=0) {
        for(int i = 0; i < 8; i++){
        trozos.get(i).setVisible(false);
        root.getChildren().remove(trozos.get(i));
    }
//    trozos.clear();
// Causes index out of bounds exception??? Why?
    }
}
}
