package patrones.observer;

import mvc.modelo.items.Item;
import mvc.vista.VistaJuego;

/**
 * Observador encargado de actualizar la interfaz gráfica del usuario (UI)
 * en respuesta a los eventos del juego.
 *
 * <p>Esta clase implementa {@link ObservadorJuego} y se comunica con la
 * {@link VistaJuego} para reflejar en pantalla los cambios producidos en
 * el modelo: actualizaciones de puntaje, fin de juego, avance de nivel
 * y generación de ítems.</p>
 *
 * <p>Su propósito principal es mantener la UI sincronizada con el estado
 * interno del juego sin acoplar la lógica visual al modelo.</p>
 *
 * @author Equipo-polimorfo
 * @version 1.0
 */
public class ObservadorUI implements ObservadorJuego {

    private VistaJuego vistaJuego;

    /**
     * Constructor que inicializa el observador con la vista del juego.
     *
     * @param vistaJuego la vista que será actualizada por este observador
     */
    public ObservadorUI(final VistaJuego vistaJuego) {
        this.vistaJuego = vistaJuego;
    }

    /**{@inheritDoc}*/
    @Override
    public void alcambiarPuntaje(int jugador, int nuevoPuntaje) {
        if (vistaJuego != null) {
            vistaJuego.actualizarPuntaje(jugador, nuevoPuntaje);
        }
    }

    /**{@inheritDoc}*/
    @Override
    public void alTerminarJuego(int ganador) {
        if (vistaJuego != null) {
            final String mensaje = ganador == 0 ? "EMPATE" : "GANADOR: JUGADOR " + ganador;
            vistaJuego.mostrarMensajeCentral(mensaje);
        }
    }

    /**{@inheritDoc}*/
    @Override
    public void alCompletarNivel() {
        if (vistaJuego != null) {
            vistaJuego.mostrarMensajeCentral("NIVEL COMPLETADO");
        }
    }

    /**{@inheritDoc}*/
    @Override
    public void alGenrarItem(Item item) {
        if (vistaJuego != null) {
            vistaJuego.mostrarMensajeCentral("POWER-UP");
        }
    }
}
