/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroidsfx2;

import java.util.Random;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

/**
 *
 * @author Wazbat
 */
public class Ufo {
    private double posX = 0;
    private double posY = 0;
//private final double VEL_ROTACION_DISPARO = 4.0;
    private final double VEL_UFO = 2;
    double angulo = 0;
    double variaX = 0;
    double variaY = 0;
    private double velNaveX = 0;
    private double velNaveY = 0;
    double anguloDisparo = 0;
    public int counterDisparo = 0;
    Polygon nave = new Polygon();
    
    private Random random = new Random();
    
    public Ufo(double x, double y){
        nave.setId("ufo");
        nave.setFill(Color.WHITE);
        nave.getPoints().addAll(new Double[]{
        -30.0, 0.0,
        -20.0, -10.0,
        -10.0, -10.0,
        -5.0, -20.0,
        5.0, -20.0,
        10.0, -10.0,
        20.0, -10.0,
        30.0, 0.0,
        20.0, 10.0,
        -20.0, 10.0});
        posX=x;
        posY=y;
        nave.relocate(posX, posY);
        
        
        angulo=random.nextInt(360);
        
        
        velNaveX=VEL_UFO*(Math.sin(Math.toRadians(angulo)));
        velNaveY=VEL_UFO*(Math.cos(Math.toRadians(angulo))*-1);

    }
    public void actualizar(Pane pane){
        posX+=velNaveX;
        posY+=velNaveY;
        
        if (posX > pane.getWidth()){
            posX=0;
        }
        if (posX < 0){
            posX=pane.getWidth();
        }
        if (posY > pane.getHeight()){
            posY=0;
        }
        if (posY < 0){
            posY=pane.getHeight();
        }
        nave.relocate(posX, posY);
        variaX=Math.sin(Math.toRadians(angulo));
        variaY=Math.cos(Math.toRadians(angulo))*-1;
        anguloDisparo=random.nextInt(360);
        //anguloDisparo+=VEL_ROTACION_DISPARO;
        
//        if (anguloDisparo >=360) {
//           anguloDisparo=0; 
//        }
        counterDisparo++;
    } 
    public Polygon getUFO(){
        return this.nave;
    }
    public double getPosX(){
        return this.posX;
    }
    public double getPosY(){
        return this.posY;
    }
    public double getVelX(){
        return this.velNaveX;
    }
    public double getVelY(){
        return this.velNaveY;
    }
    public double getAnguloDisparo(){
        return this.anguloDisparo;
    }
    public int getDisparoTimer(){
        return this.counterDisparo;
    }
    public void setDisparoTimer(int timer){
        counterDisparo = timer;
    }
}
