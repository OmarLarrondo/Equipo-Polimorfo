package modelo.nucleo_juego.patron_composite.bloque;

import javax.xml.transform.Templates;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import modelo.nucleo_juego.patron_composite.ObjetoJuego;
import modelo.nucleo_juego.patron_composite.ObjetoJuegoCompuesto;

/**
 * Representa un bloque en el campo de juego que puede ser destruido
 * por la pelota.
 *
 * @author Equipo-polimorfo
 * @version 1.0
 */
public class Bloques extends ObjetoJuegoCompuesto {

    private TemplateBloque[][] mapaBloques;

    public Bloques(TemplateBloque[][] mapaBloques){
        this.mapaBloques = mapaBloques;
    }

    @Override
    public void actualizar(double deltaTime) {
    }

    public void agregarBloque(TemplateBloque bloqueAAgregar, int posicionHorizontal, int posicionVertical){
        this.mapaBloques[posicionHorizontal][posicionVertical] = bloqueAAgregar;
    }

    public void removerBloque(int posicionHorizontal, int posicionVertical){
        this.mapaBloques[posicionHorizontal][posicionVertical] = null;
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
