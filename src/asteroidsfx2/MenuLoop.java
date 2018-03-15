/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroidsfx2;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;


/**
 *
 * @author Wazbat
 */
public class MenuLoop {
    Pane rootMenu = new Pane();
    private final int numasteroides = 6;
    private ArrayList<Asteroide> listaasteroides = new ArrayList(); 
    private int estilo = 1;
    private boolean first=true;
    private Random random = new Random();
    AnimationTimer animacion;
    private double alturaventana;
    private double anchuraventana;
    Text textoPuntos = new Text();
    VBox contenedor = new VBox();
    
    
    public void start(Scene scene){
        rootMenu.setId("root");
        //Asteroides de fondo
        // Unos asteroides grandes
        for (int i = 0; i < numasteroides/2; i++) {
              
              Asteroide asteroide = new Asteroide(0, ThreadLocalRandom.current().nextInt(0, (int) (scene.getWidth() + 1)),ThreadLocalRandom.current().nextInt(0, (int) (scene.getHeight() + 1)), random.nextDouble() * 2 - 1 );
              listaasteroides.add(asteroide);
              asteroide.setVelX(random.nextDouble() * 2 - 1 );
              asteroide.setVelY(random.nextDouble() * 2 - 1 );
              rootMenu.getChildren().add(asteroide.getPolygon());
              
        }
        // El doble de mas chicos
        for (int i = 0; i < numasteroides*2; i++) {
            
              Asteroide asteroide = new Asteroide(1,ThreadLocalRandom.current().nextInt(0, (int) (scene.getWidth() + 1)),ThreadLocalRandom.current().nextInt(0, (int) (scene.getHeight() + 1)), random.nextDouble() * 2 - 1 );
              listaasteroides.add(asteroide);
              asteroide.setVelX(random.nextDouble() * 2 - 1 );
              asteroide.setVelY(random.nextDouble() * 2 - 1 );
              rootMenu.getChildren().add(asteroide.getPolygon());
              
        }
        // Lo mismo pero de los mas chicos
        for (int i = 0; i < numasteroides; i++) {
            
              Asteroide asteroide = new Asteroide(2,ThreadLocalRandom.current().nextInt(0, (int) (scene.getWidth() + 1)),ThreadLocalRandom.current().nextInt(0, (int) (scene.getHeight() + 1)), random.nextDouble() * 2 - 1 );
              listaasteroides.add(asteroide);
              asteroide.setVelX(random.nextDouble() * 2 - 1 );
              asteroide.setVelY(random.nextDouble() * 2 - 1 );
              rootMenu.getChildren().add(asteroide.getPolygon());
              
        }
//        Ufo ufo = new Ufo(scene.getWidth()/2,scene.getHeight()/2);
//        rootMenu.getChildren().add(ufo.getUFO());
        
        //Cambiador de estilo
        ImageView cambiaestilo = new ImageView("img/ok.png");
        cambiaestilo.setFitHeight(50);
        cambiaestilo.setLayoutY(scene.getHeight()-50);
        cambiaestilo.setPreserveRatio(true);
        cambiaestilo.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                scene.getStylesheets().clear();
                switch(estilo){
                case 0:
                    estilo=1;
                    break;
                case 1:
                    scene.getStylesheets().add("css/estilo1.css");
                    estilo=2;
                    break;
                case 2:
                    scene.getStylesheets().add("css/estilo2.css");
                    estilo=3;
                    break;
                case 3:
                    scene.getStylesheets().add("css/estilo3.css");
                    estilo=0;
                    break;
                }
            }
        });
        rootMenu.getChildren().add(cambiaestilo);
        //Logotipo    
        ImageView imagenlogo = new ImageView("img/logo.png");
            imagenlogo.setFitWidth(300);
            
            imagenlogo.setPreserveRatio(true);
            contenedor.getChildren().add(imagenlogo);
            
            
            
            
        // Texto para puntos
        textoPuntos.setText("Pulsa Espacio Para Comenzar \n High Scores: \n");
        textoPuntos.setFont(Font.font(20));
        File archivoPuntos=new File("puntuaciones.txt");
        FileInputStream fileIn;
        try {
            fileIn = new FileInputStream(archivoPuntos);
            Scanner scan = new Scanner(fileIn);
            while(scan.hasNext()){
                textoPuntos.setText(textoPuntos.getText()+ scan.nextInt() + "\n" );
            }
            
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        
        textoPuntos.setFill(Color.WHITE);
        textoPuntos.setTextAlignment(TextAlignment.CENTER);
        textoPuntos.setTranslateX(-textoPuntos.getWrappingWidth());
        contenedor.setAlignment(Pos.CENTER);
        contenedor.getChildren().removeAll();
        contenedor.getChildren().add(textoPuntos);
        
        rootMenu.getChildren().add(contenedor);
        animacion = new AnimationTimer(){
                @Override
                public void handle(long now) {
                    if(!first){
                        for(Asteroide i : listaasteroides) {
                            i.actualizar(rootMenu);
                        }
                        //Hay que meter todo esto aqui para que se mueve con la pantalla
                        contenedor.relocate(scene.getWidth()/2-contenedor.getWidth()/2,scene.getHeight()/2-contenedor.getHeight()/2);
//                        imagenlogo.setLayoutY(scene.getHeight()/2-39);
//                        imagenlogo.setLayoutX((scene.getWidth()/2)-150);
//                        textoPuntos.relocate(scene.getWidth()/2-textoPuntos.getWrappingWidth(), imagenlogo.getLayoutY()+100);
                        
                        cambiaestilo.setLayoutY(scene.getHeight()-50);
                    } else{
                        first=false;
                    }
                }
        };
        animacion.start();


    };
    public void resume(){
        animacion.start();
    }
    public void pause(){
        animacion.stop();
    }
    public Pane getRoot(){
        return rootMenu;
    };
    public void setAlturaVentana(double y){
        alturaventana = y;
    }

   
}
