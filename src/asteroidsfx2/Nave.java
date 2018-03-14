/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroidsfx2;

import java.util.ArrayList;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

/**
 *
 * @author Wazbat
 */
public class Nave {
    Pane player = new Pane();
    Polygon nave = new Polygon();
    Polygon fuego = new Polygon();
    Polygon fuego2 = new Polygon();
    Circle zonaSegura = new Circle();
    private double posX = 0;
    private double posY = 0;
    private final double VEL_ROTACION = 5.0;
    private double angulo = 0;
    private double variaX = 0;
    private double variaY = 0;
    private double velNaveX = 0;
    private double velNaveY = 0;
    private boolean muerto = false;
    private final double POTENCIA_NAVE = 0.1;
    private final double MAX_VELOCIDAD_NAVE = 20;
    private int flash =0;
    private ArrayList<Polygon> trozos = new ArrayList();
    public void creaPlayer(double x, double y){ 
        posX= x;
        posY= y;
        player.setMinHeight(5);
        player.setMaxHeight(5);
        player.setMinWidth(10);       
        nave.getPoints().addAll(new Double[]{
        7.5, -10.0,
        15.0, 10.0,
        7.5, 5.0,
        0.0, 10.0 });   
        fuego.getPoints().addAll(new Double[]{
        2.0, 5.0,
        3.0, 15.0,
        6.5, 12.0,
        8.0, 18.0,
        10.0, 12.0,
        12.0, 15.0,
        13.0, 5.0 });
        fuego2.getPoints().addAll(new Double[]{
        2.0, 5.0,
        3.0, 10.0,
        6.5, 17.0,
        8.0, 13.0,
        10.0, 17.0,
        12.0, 10.0,
        13.0, 5.0 });
        zonaSegura.setRadius(100);
        zonaSegura.setCenterX(150);
        zonaSegura.setFill(Color.RED);
        zonaSegura.setVisible(false);
        
        //Agrega dentro del contenedor "player"
        player.getChildren().addAll(nave, fuego, fuego2, zonaSegura);
        fuego.toBack();
        fuego2.toBack();
        nave.setFill(Color.WHITE);
        fuego.setFill(Color.RED);
        fuego2.setFill(Color.ORANGE);
        fuego.setVisible(false);
        fuego2.setVisible(false);
        player.relocate(posX, posY);
        //Estilo
        nave.setId("nave");
        fuego.setId("fuego1");
        fuego2.setId("fuego2");
        
        
        
    }
    
    public void actualizar(Pane pane, boolean accelerando, boolean rotIzq, boolean rotDir){
        
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
        player.setRotate(angulo);
        player.relocate(posX, posY);
        zonaSegura.setCenterX(posX);
        zonaSegura.setCenterY(posY);
        variaX=Math.sin(Math.toRadians(angulo));
        variaY=Math.cos(Math.toRadians(angulo))*-1;
        if (accelerando && !muerto ) {
            if (Math.hypot(velNaveX, velNaveY) > MAX_VELOCIDAD_NAVE) {
                velNaveX-=POTENCIA_NAVE*(variaX);
                velNaveY-=POTENCIA_NAVE*(variaY);    
            }
            velNaveX+=POTENCIA_NAVE*variaX;
            velNaveY+=POTENCIA_NAVE*variaY;
            if (flash > 10){
                    flash = 0;
                }
            if(flash< 5){
                fuego.setVisible(true);
                fuego2.setVisible(false);
            } else{
                fuego.setVisible(false);
                fuego2.setVisible(true);
            }
            flash++;
        } else{
            fuego.setVisible(false);
            fuego2.setVisible(false);
            flash = 0;
        }
        if (rotIzq && !muerto) {
            angulo-=VEL_ROTACION;
            if (angulo<0){
                double exces = angulo + 360;
                angulo=exces;
            }
        } else if (rotDir && !muerto) {
            angulo+=VEL_ROTACION;
            if (angulo>360){
                double exces = angulo - 360;
                angulo=exces;
            }
        
    
        }
        
    }
    
    public Pane getPlayer(){
        return this.player;
    }
    public Polygon getPlayerCol(){
        return this.nave;
    }
    public Circle getZonaSegura(){
        return zonaSegura;
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
    public void setPosX(double x){
        posX=x;
        player.relocate(posX, posY);
        zonaSegura.setCenterX(posX);
        zonaSegura.setCenterY(posY);
    }
    public void setPosY(double y){
        posY=y;
        player.relocate(posX, posY);
        zonaSegura.setCenterX(posX);
        zonaSegura.setCenterY(posY);
    }
    public double getAngulo(){
        return this.angulo;
    }
    public void parar(){
        velNaveX=0;
        velNaveY=0;   
    }
    public void setMuerto(boolean estado){
        muerto=estado;
        angulo=0;
        parar();
    }
    public boolean getMuerto(){
        return this.muerto;
    }
//    public void rotDir(){
//        angulo+=VEL_ROTACION;
//        if (angulo>360){
//            double exces = angulo - 360;
//            angulo=exces;
//        }
//    }
//    public void rotIzq(){
//        angulo-=VEL_ROTACION;
//        if (angulo<0){
//            double exces = angulo + 360;
//            angulo=exces;
//        }
//    }
}
