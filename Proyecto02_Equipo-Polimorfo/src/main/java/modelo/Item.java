package modelo;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import modelo.nucleo_juego.patron_composite.ObjetoJuego;

/**
 * Representa un item o power-up que puede aparecer durante el juego.
 *
 * @author Equipo-polimorfo
 * @version 1.0
 */
public class Item extends ObjetoJuego {

    private TipoItem tipo;

    public Item(double x, double y, TipoItem tipo) {
        super(x, y, 20, 20);
        this.tipo = tipo;
    }

    @Override
    public void actualizar(double deltaTime) {
        this.y += 100 * deltaTime;
    }

    public TipoItem obtenerTipo() {
        return tipo;
    }

    public enum TipoItem {
        VELOCIDAD_AUMENTADA,
        VELOCIDAD_REDUCIDA,
        PALETA_EXTENDIDA,
        PALETA_REDUCIDA,
        MULTI_BOLA,
        VIDA_EXTRA
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
