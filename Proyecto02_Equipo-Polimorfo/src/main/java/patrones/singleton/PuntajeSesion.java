package patrones.singleton;

import mvc.modelo.enums.ModoJuego;

/**
 * Clase que administra y almacena el puntaje correspondiente a la 
 * sesión actual del juego.
 *
 * <p>Esta clase mantiene los puntos obtenidos por cada jugador durante 
 * la partida en curso, así como el modo de juego asociado (por ejemplo, 
 * clásico, vs IA, etc.).
 *
 * <p>Permite incrementar el puntaje de los jugadores, obtener los valores 
 * actuales y reiniciar la sesión cuando sea necesario.</p>
 *
 * @author  Equipo-polimorfo
 * @version 1.0
 */
public class PuntajeSesion {
    /** Puntos obtenidos por el jugador 1. */
    private int puntosJugador1;

    /** Puntos obtenidos por el jugador 2. */
    private int puntosJugador2;

    /** Modo de juego actual (ej. CLÁSICO, INTELIGENTE, IA, etc.). */
    private ModoJuego modo;

    /**
     * Crea una nueva sesión de puntaje asociada al modo de juego especificado.
     *
     * @param modo el modo de juego activo durante esta sesión
     */
    public PuntajeSesion(ModoJuego modo) {
        this.modo = modo;
        this.puntosJugador1 = 0;
        this.puntosJugador2 = 0;
    }

    /**
     * Incrementa el puntaje del jugador 1.
     *
     * @param puntos cantidad de puntos a agregar
     */
    public void agregarPuntosJugador1(int puntos) {
        puntosJugador1 += puntos;
    }

    /**
     * Incrementa el puntaje del jugador 2.
     *
     * @param puntos cantidad de puntos a agregar
     */
    public void agregarPuntosJugador2(int puntos) {
        puntosJugador2 += puntos;
    }

    /**
     * Obtiene el puntaje actual del jugador 1.
     *
     * @return los puntos del jugador 1
     */
    public int obtenerPuntosJugador1() {
        return puntosJugador1;
    }

    /**
     * Obtiene el puntaje actual del jugador 2.
     *
     * @return los puntos del jugador 2
     */
    public int obtenerPuntosJugador2() {
        return puntosJugador2;
    }

    /**
     * Restablece los puntajes de ambos jugadores a cero.
     */
    public void reiniciar() {
        puntosJugador1 = 0;
        puntosJugador2 = 0;
    }
}
