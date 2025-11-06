package modelo;

/**
 * Representa una paleta controlada por el jugador o la IA.
 *
 * @author Equipo-polimorfo
 * @version 1.0
 */
public class Paleta extends ObjetoJuego {

    private double velocidad;

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
}
