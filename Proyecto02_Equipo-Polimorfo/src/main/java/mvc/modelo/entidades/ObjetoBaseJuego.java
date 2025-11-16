package mvc.modelo.entidades;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

/**
 * Clase base abstracta para todos los objetos del juego
 * (pelota, paleta, bloques, etc.).
 *
 * @author Equipo-polimorfo
 * @version 1.0
 */
public interface ObjetoBaseJuego {
    public void actualizar(double deltaTime);
    public Rectangle2D obtenerLimites();
    public void dibujar(GraphicsContext gc);
}
