/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroidsfx2;


import java.nio.file.Paths;
import javafx.scene.media.AudioClip;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

/**
 *
 * @author Wazbat
 */
public class MainLoop {
    private boolean first = true;
    
    Nave nave = new Nave();
    Random random = new Random();
    AnimationTimer animacion;
    private boolean accelerando = false;
    private boolean rotIzq = false;
    private boolean rotDir = false;
    private boolean disparando = false;
    private int delayDisparo=0;
    private double velAsteroideMul = 1;
    private int numeroAsteroides = 3;
    private int numeroCachosAsteroides = 2;
    private int vidas = 3;
    private int respawnCounter = 300;
    private boolean seguroReaparecer = true;
    private int totalNaves = 1;
    private int navesACrear = 1;
    private int naveRespawnTimer = 500;
    private int timernave = naveRespawnTimer;
    public int puntos = 0;
    private double alturaVentana;
    private double anchuraVentana;
    private ArrayList<Asteroide> listaasteroides = new ArrayList();    
    private ArrayList<Bala> balas = new ArrayList();
    private ArrayList<Explosion> explosiones = new ArrayList();
    private ArrayList<Ufo> ufos = new ArrayList();
    AudioClip tonoMuerteUFO = new AudioClip(Paths.get("src/mid/ufodeath.wav").toUri().toString());
    AudioClip tonoMuerteNave = new AudioClip(Paths.get("src/mid/death.wav").toUri().toString());
    AudioClip tonoDisparoNave = new AudioClip(Paths.get("src/mid/shoot.wav").toUri().toString());
    AudioClip tonoDisparoUFO = new AudioClip(Paths.get("src/mid/UFOShoot.wav").toUri().toString());
    AudioClip asteroideBUM = new AudioClip(Paths.get("src/mid/asteroid.wav").toUri().toString());
    AudioClip tonoNuevaOla = new AudioClip(Paths.get("src/mid/newWave.wav").toUri().toString());
    AudioClip tonoGameOver = new AudioClip(Paths.get("src/mid/gameover.wav").toUri().toString());
    //private ArrayList<Escudo> listaescudos = new ArrayList();
    
    Pane rootJuego = new Pane();
    
    public void start(Scene scene) {
        alturaVentana = scene.getHeight();
        anchuraVentana = scene.getWidth();
        nave.creaPlayer(anchuraVentana/2, alturaVentana/2);
        rootJuego.getChildren().addAll(nave.getPlayer(),nave.getZonaSegura());
        
        
        
        for (int i = 0; i < numeroAsteroides; i++) {
            double x;
            double y;
            boolean creado = false;
            //Primero se aplica un valor aleatorio a la X y la Y, en las partes 20% de arriba y 20% de abajo
            if (random.nextBoolean()) {                
                x=ThreadLocalRandom.current().nextDouble(0,(anchuraVentana * 0.2));    
            } else {
                x=ThreadLocalRandom.current().nextDouble(anchuraVentana * 0.8, anchuraVentana);
            }
            if (random.nextBoolean()) {
                y=ThreadLocalRandom.current().nextDouble(0,(alturaVentana * 0.2));    
            } else {
                y=ThreadLocalRandom.current().nextDouble(alturaVentana * 0.8, alturaVentana);
            }

            Asteroide asteroide = new Asteroide(0, x, y , random.nextDouble() * 2 - 1 );
            asteroide.getPolygon().setVisible(true);
            //Cambiar a falso
            listaasteroides.add(asteroide);
            asteroide.setVelX((random.nextDouble() * 2 - 1) * velAsteroideMul);
            asteroide.setVelY((random.nextDouble() * 2 - 1) * velAsteroideMul );
            rootJuego.getChildren().add(asteroide.getPolygon());
//            if (getCollision(asteroide, nave.getZonaSegura())) {
//                listaasteroides.remove(asteroide);
//                System.out.println("Collision");
//            }else{
//                asteroide.getPolygon().setVisible(true);
//                System.out.println("Visible");
//            }
            
        }
        
        
        animacion = new AnimationTimer() {
            @Override
            public void handle(long now){
            if (!first){
                
            //Codigo para disparar, si esta disparando y no muerto    
            if (disparando && !nave.getMuerto()) {
                if (delayDisparo>10){
                    delayDisparo =0;
                }
                if (delayDisparo<1) {
                    Bala bala = new Bala(false, nave.getAngulo(), nave.getPosX()+5, nave.getPosY()+2, nave.getVelX(), nave.getVelY());
                    balas.add(bala);
                    rootJuego.getChildren().add(bala.getPolygon());
                    tonoDisparoNave.play();
                }
                delayDisparo++;  
            } else {
                delayDisparo = 0;
                disparando = false;
            }    
            //Actualizaicon del Jugador
            nave.actualizar(rootJuego, accelerando, rotIzq, rotDir);    
            //Variables temporales para guardar los objetos antes de eliminarlos    
            Bala balaAQuitar = null;
            Asteroide asteroideAQuitar = null;
            Explosion explosionAQuitar = null;
            Ufo ufoAQuitar = null;    
            //Actualizacion de Asteroides, collision Nave con Asteroide
            for(Asteroide asteroideActual : listaasteroides) {
                asteroideActual.actualizar(rootJuego);
                if (getCollision(asteroideActual, nave) && !nave.getMuerto()) {
                    muerteNave();
                    asteroideAQuitar=asteroideActual;
                }
            }
            //Actualizacion de Balas, solo collision de asteroides
            for(Iterator<Bala> iteratorBala = balas.iterator(); iteratorBala.hasNext();){
                Bala balaActual = iteratorBala.next();
                balaActual.actualizar(rootJuego);
                //Collision de balas con Asteroides
                for (Asteroide asteroideActual : listaasteroides) {
                    if (getCollision(asteroideActual, balaActual)) {                        
                        balaActual.getPolygon().setVisible(false);
                        asteroideAQuitar=asteroideActual;
                        balaAQuitar=balaActual;
                        rootJuego.getChildren().remove(asteroideActual.getPolygon());
                        rootJuego.getChildren().remove(balaActual.getPolygon());
                        
                    }
                }
                //Quita la bala si ya no tiene vida
                if (balaActual.getVida() < 0) {
                        balaActual.getPolygon().setVisible(false);
                        iteratorBala.remove();
                    }
            }
            //Actualizacion de Explosiones
            for(Explosion explosionActual : explosiones){
                explosionActual.actualizar(rootJuego);
                if (explosionActual.vida <= 0) {
                    explosionAQuitar=explosionActual;
                    
                }
            }
            
            // Actualizacion de UFO
            for(Ufo ufoActual : ufos){
                ufoActual.actualizar(rootJuego);
                //Collision de UFO con Asteroide
                for(Asteroide asteroideActual : listaasteroides){
                    if (getCollision(asteroideActual, ufoActual)) {
                        explosion(asteroideActual.getPosX(),asteroideActual.getPosY(), asteroideActual.getRot());
                        asteroideAQuitar=asteroideActual;
                        explosion(ufoActual.getPosX(), ufoActual.getPosY(), random.nextInt(360));
                        rootJuego.getChildren().remove(ufoActual.getUFO());
                        ufoAQuitar=ufoActual;
                    }
                }
                //Collision de UFO con Nave
                if (getCollision(nave, ufoActual) && !nave.getMuerto()) {
                    
                    explosion(ufoActual.getPosX(), ufoActual.getPosY(), random.nextInt(360));
                    rootJuego.getChildren().remove(ufoActual.getUFO());
                    ufoAQuitar=ufoActual;
                    muerteNave();
                    
                }
                //Disparador de Nave, reducir el if para incrementar cantidad de disparos
                if (ufoActual.getDisparoTimer() > 50) {
                    Bala bala = new Bala(true, ufoActual.getAnguloDisparo(), ufoActual.getPosX()+30, ufoActual.getPosY()+15, ufoActual.getVelX(), ufoActual.getVelY());
                    balas.add(bala);
                    rootJuego.getChildren().add(bala.getPolygon());
                    tonoDisparoUFO.play();
                    ufoActual.setDisparoTimer(0);
                }
                //Collision de Balas Con solo naves y ufos, separado de la actualizacion de balas y asteroides
                for(Bala balaActual : balas){
                    if (getCollision(nave, balaActual) && !nave.getMuerto()) {
                        balaActual.getPolygon().setVisible(false);
                        balaAQuitar=balaActual;
                        muerteNave();    
                    }
                    if (getCollision(ufoActual, balaActual)) {
                        ufoActual.getUFO().setVisible(false);
                        explosion(ufoActual.getPosX(), ufoActual.getPosY(), random.nextInt(360));
                        sumaPuntuacion(400);
                        ufoAQuitar=ufoActual;
                        balaAQuitar=balaActual;
                    }
                }
            }
            if (balaAQuitar != null) {
                balaAQuitar.getPolygon().setVisible(false);
                rootJuego.getChildren().remove(balaAQuitar.getPolygon());
                balas.remove(balaAQuitar);    
            }
            if (asteroideAQuitar != null) {
                reducirAsteroide(asteroideAQuitar);
                listaasteroides.remove(asteroideAQuitar);
            }
            if (ufoAQuitar != null) {
                tonoMuerteUFO.play();
                ufoAQuitar.getUFO().setVisible(false);
                ufos.remove(ufoAQuitar);
            }
            if (explosionAQuitar != null) {
                explosiones.remove(explosionAQuitar);    
            }
            
            // Codigo para reaparecer
            if (nave.getMuerto()) {
                respawnCounter--;
                nave.getPlayer().setVisible(false);
                nave.parar();
                
                if (respawnCounter <= 0) {
                    seguroReaparecer=true;
                    for(Asteroide asteroideactual : listaasteroides){
                        if (getCollision(asteroideactual,nave.getZonaSegura())) {
                            seguroReaparecer=false;
                        }
                    }
                    if (seguroReaparecer) {
                        
                        nave.getPlayer().setVisible(true);
                        nave.setMuerto(false);
                        respawnCounter=400;
                        
                    } else{
                        System.out.println("No esta seguro reaparecer");
                    }
                    
                }
            }
            //Fin de codigo para reaparecer
            //Creador de naves
            if (navesACrear > 0) {
                timernave--;
                if (timernave < 0) {
                    Ufo ufo = new Ufo(scene.getWidth()/2,30);
                    rootJuego.getChildren().add(ufo.getUFO());
                    ufos.add(ufo);
                    timernave=naveRespawnTimer;
                    navesACrear--;
                }        
            }
            
            
            //Nueva ola
            if (listaasteroides.isEmpty()) {
                nuevaola();
            }
            
               
           
            } else{
                first=false;
            }
            }
            
        };
        animacion.start();
    
    }
    //Entradas de teclado
    public void teclaPulsada(KeyEvent event){
        switch(event.getCode()){
            case UP:
                accelerando=true;
                break;
            case DOWN:
                Ufo ufo = new Ufo(50,50);
                rootJuego.getChildren().add(ufo.getUFO());
                ufos.add(ufo);
                
                
                break;
            case LEFT:
                rotIzq=true;
                break;
            case RIGHT:
                rotDir=true;
                break;
            case SPACE:
                disparando=true;
                break;
                

        }
    };
    public void teclaSoltada(KeyEvent event){
        switch(event.getCode()){
            case UP:
                accelerando=false;
                break;
            case DOWN:
                break;
            case LEFT:
                rotIzq=false;
                break;
            case RIGHT:
                rotDir=false;
                break;
            case SPACE:
                disparando=false;
                break;
        }
    };
    private boolean getCollision(Asteroide asteroide, Circle circle){
        return !Shape.intersect(asteroide.getPolygon(), circle).getBoundsInLocal().isEmpty();
    }
    private boolean getCollision(Asteroide asteroide, Bala bala){
        boolean intersect = !Shape.intersect(asteroide.getPolygon(), bala.getPolygon()).getBoundsInLocal().isEmpty();
        if (!bala.getEnemigo() && intersect){
            sumaPuntuacion(100);
        }
        return intersect;
    }
    private boolean getCollision(Asteroide asteroide, Nave nave){
        return !Shape.intersect(asteroide.getPolygon(), nave.getPlayerCol()).getBoundsInLocal().isEmpty();
    }
    private boolean getCollision(Asteroide asteroide, Ufo ufo){
        return !Shape.intersect(asteroide.getPolygon(), ufo.getUFO()).getBoundsInLocal().isEmpty();
    }
    private boolean getCollision(Nave nave, Ufo ufo){
        return !Shape.intersect(nave.getPlayerCol(), ufo.getUFO()).getBoundsInLocal().isEmpty();
    }
    private boolean getCollision(Nave nave, Bala bala){
        if (bala.getEnemigo()) {
            return !Shape.intersect(nave.getPlayerCol(), bala.getPolygon()).getBoundsInLocal().isEmpty();    
        } else{
            return false;
        }
    }
    private boolean getCollision(Ufo ufo, Bala bala){
        if (!bala.getEnemigo()) {
            return !Shape.intersect(ufo.getUFO(), bala.getPolygon()).getBoundsInLocal().isEmpty();    
        } else{
            return false;
        }
    }
    private void nuevaola(){
        tonoNuevaOla.play();
        sumaPuntuacion(400);
        nave.parar();
        nave.setPosX(rootJuego.getWidth()/2);
        nave.setPosY(rootJuego.getHeight()/2);
        totalNaves+=2;
        navesACrear=totalNaves;
        numeroAsteroides++;
        naveRespawnTimer=naveRespawnTimer/4*3;
        for (int i = 0; i < numeroAsteroides; i++) {
            double x;
            double y;
            
            //Primero se aplica un valor aleatorio a la X y la Y, en las partes 20% de arriba y 20% de abajo
            if (random.nextBoolean()) {                
                x=ThreadLocalRandom.current().nextDouble(0,(anchuraVentana * 0.2));    
            } else {
                x=ThreadLocalRandom.current().nextDouble(anchuraVentana * 0.8, anchuraVentana);
            }
            if (random.nextBoolean()) {
                y=ThreadLocalRandom.current().nextDouble(0,(alturaVentana * 0.2));    
            } else {
                y=ThreadLocalRandom.current().nextDouble(alturaVentana * 0.8, alturaVentana);
            }

            Asteroide asteroide = new Asteroide(0, x, y , random.nextDouble() * 2 - 1 );
            asteroide.getPolygon().setVisible(true);
            //Cambiar a falso
            listaasteroides.add(asteroide);
            asteroide.setVelX((random.nextDouble() * 2 - 1) * velAsteroideMul);
            asteroide.setVelY((random.nextDouble() * 2 - 1) * velAsteroideMul );
            rootJuego.getChildren().add(asteroide.getPolygon());
            
        }
    }
    private void reducirAsteroide(Asteroide asteroidePadre){
        asteroideBUM.play();
        explosion(asteroidePadre.getPosX(), asteroidePadre.getPosY(), asteroidePadre.getRot());
        if (asteroidePadre.getFase()<numeroCachosAsteroides) {
            for (int i = 0; i < 2; i++) {
                Asteroide asteroide = new Asteroide(asteroidePadre.getFase()+1, asteroidePadre.getPosX(), asteroidePadre.getPosY() , random.nextDouble() * 2 - 1 );
                asteroide.setVelX((random.nextDouble() * 2 - 1) * velAsteroideMul);
                asteroide.setVelY((random.nextDouble() * 2 - 1) * velAsteroideMul );
                rootJuego.getChildren().add(asteroide.getPolygon());
                listaasteroides.add(asteroide);    
            }    
        }
        asteroidePadre.getPolygon().setVisible(false);
        
        
        
        
    }
    public void pause(){
        animacion.stop();
    }
    public void resume(){
        animacion.start();
    }public Pane getRoot(){
        return rootJuego;
    }   
    private void muerteNave(){
        if (!nave.getMuerto()) {
            tonoMuerteNave.play();
        }
        if (vidas > 0) {
            nave.parar();
            explosion(nave.getPosX(), nave.getPosY(), nave.getAngulo());
            nave.setMuerto(true);
            nave.getPlayer().setVisible(false);
            nave.setPosX(rootJuego.getWidth()/2);
            nave.setPosY(rootJuego.getHeight()/2);
            vidas--;
            
        } else {
            System.out.println("Ahora deberia hacer game over");
            tonoGameOver.play();
        }
        
        System.out.println(vidas+" vidas");
        
    }
    private void explosion(double x, double y, double angulo){
        Explosion explosion = new Explosion(x, y, angulo, rootJuego);
        explosiones.add(explosion);
    }
    private void sumaPuntuacion(int incr){
        puntos+=incr;
        System.out.println(puntos);
    };
    
}
