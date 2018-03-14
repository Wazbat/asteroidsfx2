/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroidsfx2;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


/**
 *
 * @author Wazbat
 */
public class AsteroidsFX2 extends Application {
    MainLoop mainLoop = new MainLoop();
    MenuLoop menuLoop = new MenuLoop();
    StackPane root = new StackPane();
    Scene scene = new Scene(root, 1280, 720);

    AudioClip musica = new AudioClip(Paths.get("src/mid/menu.wav").toUri().toString());
    
    
    boolean menu = true;
    @Override
    public void start(Stage primaryStage) {
        
        scene.setFill(Color.BLACK);
        primaryStage.setTitle("AsteroidsFX 2.0");
        primaryStage.getIcons().add(new Image("file:src/img/icon.png"));
        primaryStage.setScene(scene);
        primaryStage.show();
        //Si no existe el archivo de puntos, lo crea y lo llena de 10 0's
        File archivopuntos=new File("puntuaciones.txt");
        if (!archivopuntos.exists()) {
            FileWriter escribidor;
            try{
                escribidor = new FileWriter(new File("puntuaciones.txt"));
                for (int i = 0; i < 10; i++) {
                    escribidor.write("0");
                    if(i!=9){
                        escribidor.write(System.lineSeparator());
                    }
                    
                }
                escribidor.close();
            } catch (IOException ex){
                ex.printStackTrace();
            }
            System.out.println("Archivo de Puntuacion creado");
        }
        //Codigo del bucle del juego
        //Codigo del bucle del menu
        root.getChildren().add(menuLoop.getRoot());
        menuLoop.start(scene);
        
        

        
        
        musica.play(500);
        
        
        
        
        
        
        
       
        
        scene.setOnKeyPressed((KeyEvent event) -> {
            
            mainLoop.teclaPulsada(event);
            if (menu) {
                gameStart();
                musica.stop();
                
                menu=false;    
            } 
            switch (event.getCode()) {   
            }
            });

        scene.setOnKeyReleased((KeyEvent event) -> {
            mainLoop.teclaSoltada(event);
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    private void gameStart(){
        menuLoop.getRoot().setVisible(false);
        mainLoop.start(scene);
        root.getChildren().add(mainLoop.getRoot());
        
        
                
    }
    private void reinitializar(){
        mainLoop.getRoot().setVisible(false);
        menuLoop.getRoot().setVisible(false);
        mainLoop=null;
        menuLoop=null;
        MainLoop mainLoop = new MainLoop();
        MenuLoop menuLoop = new MenuLoop();
        root.getChildren().add(menuLoop.getRoot());
        root.getChildren().add(mainLoop.getRoot());
        menuLoop.start(scene);
        musica.stop();
        musica.play();
        menu=true;
        menuLoop.getRoot().setVisible(true);
        menuLoop.start(scene);
    }
}
