package mvc.modelo.items;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import mvc.modelo.entidades.ObjetoJuego;
import mvc.modelo.entidades.Paleta;

public class ItemNeblina implements Item {
    //
    private double duracion;
    private boolean activo;
    private double tiempoRestante;
    private Color colorNeblina = Color.rgb(200, 200, 200, 0.50); 

    public ItemNeblina(double duracion) {
        this.duracion = duracion;
        this.tiempoRestante = duracion;
        this.activo = false;
    }

    @Override
    public void aplicar(ObjetoJuego objeto) {
        if (activo) return;
        if (!(objeto instanceof Paleta)) {
            throw new IllegalArgumentException("La neblina solo se puede aplicar a una paleta.");
        }
        activo = true;
        tiempoRestante = duracion;
    }

    @Override
    public double obtenerDuracion() {
        return duracion;
    }

    @Override
    public boolean estaActivo() {
        return activo;
    }

    @Override
    public void desactivar(ObjetoJuego objeto) {
        activo = false;
        tiempoRestante = duracion;
    }

    public void actualizar(double deltaTiempo, ObjetoJuego objeto) {
        if (!activo) return;
        tiempoRestante -= deltaTiempo;
        if (tiempoRestante <= 0) desactivar(objeto);
    }

    public void render(GraphicsContext gc, ObjetoJuego objeto) {
        if (!activo || !(objeto instanceof Paleta paleta)) return;

        double x = paleta.obtenerX();
        double ancho = paleta.obtenerAncho();

        gc.setFill(colorNeblina);
        gc.fillRect(x, 0, ancho, gc.getCanvas().getHeight());
    }
}
