package modelo.nucleo_juego.patron_composite;

import java.util.List;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

public class ObjetoJuegoCompuesto extends ObjetoJuego{

    private List<ObjetoJuego> hijos;

    public ObjetoJuegoCompuesto(double x, double y, double ancho, double alto) {
        super(x, y, ancho, alto);
    }

    public void agregar(ObjetoJuego objeto){
        //aqui va su codigo
    }
    public void  eliminar(ObjetoJuego objeto){
        //aqui va su codigo
    }
    public List<ObjetoJuego> obenerHijos(){
        //aqui va su codigo
        return null;
    }

    @Override
    public void actualizar(double deltaTime) {
        //aqui va su codigo
    }

    @Override
    public Rectangle2D obtenerLimites() {
        //aqui va su codigo
        return null;
    }

    @Override
    public void dibujar(GraphicsContext gc) {
        //aqui va su codigo
    }
    
}
