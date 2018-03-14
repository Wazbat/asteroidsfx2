/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroidsfx2;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

/**
 *
 * @author Wazbat
 */
public class Bala {
    private double velBala =10;
    private double angulo =0;
    private double posX = 0;
    private double posY =0;
    public double velBalaX = 0;
    public double velBalaY = 0; 
    private double variaX = 0;
    private double variaY = 0;
    public int vida=70;
    private boolean enemigo;
    
    
    Polygon object = new Polygon();
    
    public Polygon getPolygon(){
        return this.object;
    }
    public Bala(boolean hostil, double angulo, double posx, double posy, double velx, double vely){
        enemigo=hostil;
        variaX=Math.sin(Math.toRadians(angulo));
        variaY=Math.cos(Math.toRadians(angulo))*-1;
        object.getPoints().addAll(new Double[]{
        -0.5, -7.5,
        0.5, -7.5,
        0.5, 7.5,
        -0.5, 7.5});
        object.setFill(Color.WHITE);
        object.setRotate(angulo);
        posX=posx;
        posY=posy;
        velBalaX = velx+velBala*variaX;
        velBalaY = vely+velBala*variaY;
    }
        
    public void actualizar(Pane root){
        object.setLayoutX(posX);
        object.setLayoutY(posY);
        posX=posX+velBalaX;
        posY=posY+velBalaY;
        if (posX > root.getWidth()){
                posX=0;
            }
            if (posX < 0){
                posX=root.getWidth();
            }
            if (posY > root.getHeight()){
                posY=0;
            }
            if (posY < 0){
                posY=root.getHeight();
            }
        vida-=1;
    }
    public double getPosX() {
        return this.posX;
    }
    public double getPosY() {
        return this.posY;
    }
    public boolean getEnemigo(){
        return this.enemigo;
    }
    public int getVida(){
        return this.vida;
    }
    
}
