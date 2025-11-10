package modelo.nucleo_juego.patron_composite.bloque;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import modelo.nucleo_juego.patron_composite.ObjetoJuego;
import modelo.nucleo_juego.patron_composite.Pelota;

/**
 * Alias de Bloques para compatibilidad con código existente.
 *
 * @author Equipo-polimorfo
 * @version 1.0
 */
public abstract class TemplateBloque implements ObjetoJuego{

    protected int x;
    protected int y;
    
    public TemplateBloque(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public abstract ResultadoRebote recibirGolpe(Pelota pelota);

    public int obtenerX() {
        return x;
    }

    public int obtenerY() {
        return y;
    }

    @Override
    public void actualizar(double deltaTime){

    }

    @Override
    public Rectangle2D obtenerLimites(){
        return new Rectangle2D(x,y,x,y);
    }

    @Override
    public void dibujar(GraphicsContext gc){

    }

}
