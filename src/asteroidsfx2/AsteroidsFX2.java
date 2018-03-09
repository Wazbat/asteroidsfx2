/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroidsfx2;

import java.nio.file.Paths;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

    AudioClip musica = new AudioClip(Paths.get("src/mid/main.wav").toUri().toString());
    AudioClip combate = new AudioClip(Paths.get("src/mid/fight.aiff").toUri().toString());
    boolean menu = true;
    @Override
    public void start(Stage primaryStage) {
        
        scene.setFill(Color.BLACK);
        primaryStage.setTitle("AsteroidsFX 2.0");
        primaryStage.setScene(scene);
        primaryStage.show();
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
                combate.play();
                menu=false;    
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
    
}
