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
public interface ObjetoJuego {

    public abstract void actualizar(double deltaTime);
    public abstract Rectangle2D obtenerLimites();
    public abstract void dibujar(GraphicsContext gc);
}
