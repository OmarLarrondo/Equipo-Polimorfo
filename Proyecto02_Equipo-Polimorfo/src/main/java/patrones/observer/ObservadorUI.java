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

    /**{@inheritDoc}*/
    @Override
    public void alcambiarPuntaje(int jugador, int nuevoPuntaje) {
        // aqui va su codigo 
    }

    /**{@inheritDoc}*/
    @Override
    public void alTerminarJuego(int ganador) {
        // aqui va su codigo 
    }

    /**{@inheritDoc}*/
    @Override
    public void alCompletarNivel() {
        // aqui va su codigo 
    }

    /**{@inheritDoc}*/
    @Override
    public void alGenrarItem(Item item) {
        // aqui va su codigo 
    }
}
