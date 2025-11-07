package modelo.nucleo_juego.patron_composite;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

public class ObjetoJuegoCompuesto extends ObjetoJuego{

    private List<ObjetoJuego> hijos;

    public ObjetoJuegoCompuesto(double x, double y, double ancho, double alto) {
        super(x, y, ancho, alto);
        this.hijos = new ArrayList<>();

    }

    public void agregar(ObjetoJuego objeto){
        if(!(objeto == null)){
            hijos.add(objeto);
        }
        throw new IllegalArgumentException("No es posible agregar eso");
    }
    public void  eliminar(ObjetoJuego objeto){
        if(!(objeto == null)){
            hijos.remove(objeto);
        }
        throw new IllegalArgumentException("Objeto invalido a eliminar");
    }
    public List<ObjetoJuego> obtenerHijos(){
        if(hijos == null){
            return new ArrayList<ObjetoJuego>();
        }
        return hijos;
    }

    @Override
    public void actualizar(double deltaTime) {
        if(deltaTime <= 0){
            throw new IllegalArgumentException("El tiempo debe ser positivo");
        }
        if(hijos == null){
            throw new IllegalStateException("La lista de hijos no ha sido inicializada.");
        }
        for (ObjetoJuego objetoJuego : hijos) {
            objetoJuego.actualizar(deltaTime);
        }
    }

    //CHECARRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR
    @Override
    public Rectangle2D obtenerLimites() {
        if (hijos == null || hijos.isEmpty()) {
            return new Rectangle2D(obtenerX(), obtenerY(), obtenerAncho(), obtenerAlto());
        }
        // primer hijo
        Rectangle2D limites = hijos.get(0).obtenerLimites();

        double minX = limites.getMinX();
        double minY = limites.getMinY();
        double maxX = limites.getMaxX();
        double maxY = limites.getMaxY();

        // Expandimos el rectángulo para incluir todos los hijos
        for (int i = 1; i < hijos.size(); i++) {
            Rectangle2D r = hijos.get(i).obtenerLimites();
            minX = Math.min(minX, r.getMinX());
            minY = Math.min(minY, r.getMinY());
            maxX = Math.max(maxX, r.getMaxX());
            maxY = Math.max(maxY, r.getMaxY());
        }

        return new Rectangle2D(minX, minY, maxX - minX, maxY - minY);
    }


    @Override
    public void dibujar(GraphicsContext gc) {
        if(gc == null){
            throw new IllegalArgumentException("El tiempo debe ser positivo");
        }
        if(hijos == null){
            throw new IllegalStateException("La lista de hijos no ha sido inicializada.");
        }
        for (ObjetoJuego objetoJuego : hijos) {
            objetoJuego.dibujar(gc);
        }
    }
}
