package modelo.nucleo_juego.patron_composite;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import modelo.editor.prototype.ConfigPaleta;
import modelo.gestion_ia.patron_strategy.estrategiasMovimientos.EstrategiaMovimiento;

/**
 * Representa una paleta controlada por el jugador o la IA.
 *
 * @author Equipo-polimorfo
 * @version 1.0
 */
public class Paleta extends ObjetoJuego {

    private double velocidad;
    private Color color;
    private boolean tieneEspinas;
    private EstrategiaMovimiento estrategiaMovimiento;
    private ConfigPaleta estadoOriginal;

    public Paleta(double x, double y, double ancho, double alto) {
        super(x, y, ancho, alto);
        this.velocidad = 400;
    }

    @Override
    public void actualizar(double deltaTime) {
    }

    public void moverArriba(double deltaTime) {
        y -= velocidad * deltaTime;
    }

    public void moverAbajo(double deltaTime) {
        y += velocidad * deltaTime;
    }

    public double obtenerVelocidad() {
        return velocidad;
    }

    public void establecerVelocidad(double velocidad) {
        this.velocidad = velocidad;
    }

    public void agregarEspina(){
        //aqui va su codigo
    }
    public void eliminarEspinas(){
        //aqui va su codig     
    }
    public void redimensionar(double nuevoAncho){
        //aqui va su codigo
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
    public Paleta clonar(){
        return null;

    }
    public void guardarEstado(){

    }
    public void restaurarEstado(){
        
    }
}
