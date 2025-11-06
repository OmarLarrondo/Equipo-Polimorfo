package modelo.nucleo_juego.patron_composite;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

/**
 * Representa la pelota del juego Pong.
 *
 * @author Equipo-polimorfo
 * @version 1.0
 */
public class Pelota extends ObjetoJuego {

    private double velocidadX;
    private double velocidadY;
    private double velocidadBase;

    public Pelota(double x, double y, double radio) {
        super(x, y, radio * 2, radio * 2);
        this.velocidadBase = 300;
        this.velocidadX = velocidadBase;
        this.velocidadY = velocidadBase;
    }

    @Override
    public void actualizar(double deltaTime) {
        x += velocidadX * deltaTime;
        y += velocidadY * deltaTime;
    }

    //AGREGAR AL DIAGRAMA
    public double obtenerVelocidadX() {
        return velocidadX;
    }

    //AGREGAR AL DIAGRAMA
    public double obtenerVelocidadY() {
        return velocidadY;
    }

    //AGREGAR AL DIAGRAMA
    public void establecerVelocidadX(double velocidadX) {
        this.velocidadX = velocidadX;
    }
    //AGREGAR AL DIAGRAMA
    public void establecerVelocidadY(double velocidadY) {
        this.velocidadY = velocidadY;
    }
    //AGREGAR AL DIAGRAMA
    public void invertirX() {
        velocidadX = -velocidadX;
    }
    //AGREGAR AL DIAGRAMA
    public void invertirY() {
        velocidadY = -velocidadY;
    }
    public void reiniciar(){
        //aqui vasu codigo
    }
    public double obtenerVelocidad(){
        return velocidadBase;
    }
    public void establecerVelocidadGeneral(double velocidad){
        establecerVelocidadX(velocidad);
        establecerVelocidadY(velocidad);
    }
    

    @Override
    public Rectangle2D obtenerLimites() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'obtenerLimites'");
    }

    @Override
    public void dibujar(GraphicsContext gc) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'dibujar'");
    }
}
