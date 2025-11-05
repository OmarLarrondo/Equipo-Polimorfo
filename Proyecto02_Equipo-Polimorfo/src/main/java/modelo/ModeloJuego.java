package modelo;

/**
 * Modelo principal del juego que contiene el estado completo
 * de la partida actual.
 *
 * @author Equipo-polimorfo
 * @version 1.0
 */
public class ModeloJuego {

    private ModoJuego modoActual;

    public ModeloJuego() {
    }

    public ModoJuego obtenerModoActual() {
        return modoActual;
    }

    public void establecerModo(ModoJuego modo) {
        this.modoActual = modo;
    }
}
