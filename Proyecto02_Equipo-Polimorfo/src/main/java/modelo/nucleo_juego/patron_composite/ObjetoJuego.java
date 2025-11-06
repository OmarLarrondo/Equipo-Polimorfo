package modelo.nucleo_juego.patron_composite;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

/**
 * Clase base abstracta para todos los objetos del juego
 * (pelota, paleta, bloques, etc.).
 *
 * @author Equipo-polimorfo
 * @version 1.0
 */
public abstract class ObjetoJuego {

    protected double x;
    protected double y;
    protected double ancho;
    protected double alto;
    protected boolean activo;

    public ObjetoJuego(double x, double y, double ancho, double alto) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
        this.activo = true;
    }

    public double obtenerX() {
        return x;
    }

    public double obtenerY() {
        return y;
    }

    //AGREGAR AL DIAGRAMA
    public double obtenerAncho() {
        return ancho;
    }

    //AGREGAR AL DIAGRAMA
    public double obtenerAlto() {
        return alto;
    }

    //AGREGAR AL DIAGRAMA
    public boolean estaActivo() {
        return activo;
    }

    //AGREGAR AL DIAGRAMA
    public void establecerActivo(boolean activo) {
        this.activo = activo;
    }

    public abstract void actualizar(double deltaTime);
    public abstract Rectangle2D obtenerLimites();
    public abstract void dibujar(GraphicsContext gc);
}
