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
public class Asteroide {
    private double velRot =0;
    private double posX = 0;
    private double posY = 0;
    private double angulo = 0;
    private double velAsteroideX = 0;
    private double velAsteroideY = 0;
    private int tamano=0;
    Polygon polyAsteroide = new Polygon();
    
    public Polygon getPolygon(){
        return this.polyAsteroide;
    }
    public Asteroide(int fase,double posx, double posy, double rot){
        //Para el estido
        polyAsteroide.setId("asteroide");
        tamano=fase;
        switch (fase) {
            case 0:
                polyAsteroide.getPoints().addAll(new Double[]{
                    -192.0, -43.0,
                    -64.0, -175.0,
                    76.0, -161.0,
                    176.0, -51.0,
                    166.0, 45.0,
                    32.0, 55.0,
                    34.0, 169.0,
                    -72.0, 215.0,
                    -158.0, 167.0,
                    -140.0, 69.0,
                    -214.0, 9.0});
                break;
            case 1:
                polyAsteroide.getPoints().addAll(new Double[]{
                    -192.0/2, -43.0/2,
                    -64.0/2, -175.0/2,
                    76.0/2, -161.0/2,
                    176.0/2, -51.0/2,
                    166.0/2, 45.0/2,
                    32.0/2, 55.0/2,
                    34.0/2, 169.0/2,
                    -72.0/2, 215.0/2,
                    -158.0/2, 167.0/2,
                    -140.0/2, 69.0/2,
                    -214.0/2, 9.0/2 });
                break;
            case 2:
                polyAsteroide.getPoints().addAll(new Double[]{
                    0.0, 0.0,
                    -10.0/4, 70.0/4,
                    -100.0/4, 100.0/4,
                    -70.0/4, 160.0/4,
                    -80.0/4, 270.0/4,
                    -30.0/4, 310.0/4,
                    80.0/4, 250.0/4,
                    100.0/4, 200.0/4,
                    70.0/4, 170.0/4,
                    70.0/4, 60.0/4 });
                break;
            default:
                break;
        }
        polyAsteroide.setFill(Color.WHITE);
        velRot=rot;
        posX=posx;
        posY=posy;
    }
        
    public void actualizar(Pane root){
        angulo+=velRot;
        if (angulo>360) {
            angulo=0;
        }        

        polyAsteroide.setRotate(angulo);
        
        posX+=velAsteroideX;
        
        posY+=velAsteroideY;
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
        polyAsteroide.setLayoutX(posX);
        polyAsteroide.setLayoutY(posY);
    }
    public double getPosX() {
        return this.posX;
    }
    public double getPosY() {
        return this.posY;
    }
    public void setPosX(double x){
        posX=x;
    }
    public void setPosY(double y){
        posY=y;
    }
    public double getRot(){
        return this.angulo;
    }
    public int getFase()    {
       return this.tamano;
    }
    
    public void setVelX(double vel) {
        velAsteroideX=vel;
    }
    public void setVelY(double vel) {
        velAsteroideY=vel;
    }
}
