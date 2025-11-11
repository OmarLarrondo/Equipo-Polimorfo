package patrones.singleton;

import mvc.modelo.enums.ModoJuego;

/**
 * Representa el puntaje de la sesión actual de juego.
 *
 * @author Equipo-polimorfo
 * @version 1.0
 */
public class PuntajeSesion {

    private int puntosJugador1;
    private int puntosJugador2;
    private ModoJuego modo;

    public PuntajeSesion(ModoJuego modo) {
        this.modo = modo;
        this.puntosJugador1 = 0;
        this.puntosJugador2 = 0;
    }

    public void agregarPuntosJugador1(int puntos) {
        puntosJugador1 += puntos;
    }

    public void agregarPuntosJugador2(int puntos) {
        puntosJugador2 += puntos;
    }

    public int obtenerPuntosJugador1() {
        return puntosJugador1;
    }

    public int obtenerPuntosJugador2() {
        return puntosJugador2;
    }

    public void reiniciar() {
        puntosJugador1 = 0;
        puntosJugador2 = 0;
    }
}
