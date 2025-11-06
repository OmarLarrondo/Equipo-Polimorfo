package modelo;

/**
 * Representa la pelota del juego Pong.
 *
 * @author Equipo-polimorfo
 * @version 1.0
 */
public class Pelota extends ObjetoJuego {

    private double velocidadX;
    private double velocidadY;
    private double velocidadBase;

    public Pelota(double x, double y, double radio) {
        super(x, y, radio * 2, radio * 2);
        this.velocidadBase = 300;
        this.velocidadX = velocidadBase;
        this.velocidadY = velocidadBase;
    }

    @Override
    public void actualizar(double deltaTime) {
        x += velocidadX * deltaTime;
        y += velocidadY * deltaTime;
    }

    public double obtenerVelocidadX() {
        return velocidadX;
    }

    public double obtenerVelocidadY() {
        return velocidadY;
    }

    public void establecerVelocidadX(double velocidadX) {
        this.velocidadX = velocidadX;
    }

    public void establecerVelocidadY(double velocidadY) {
        this.velocidadY = velocidadY;
    }

    public void invertirX() {
        velocidadX = -velocidadX;
    }

    public void invertirY() {
        velocidadY = -velocidadY;
    }
}
