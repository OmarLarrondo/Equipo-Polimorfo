package patrones.observer;

import mvc.modelo.items.Item;

/**
 * Implementación del observador encargado de gestionar la reproducción
 * de sonidos en respuesta a eventos del juego.
 *
 * <p>Esta clase actúa como un {@link ObservadorJuego} especializado en audio:
 * escucha los eventos emitidos por el modelo del juego (como cambios en el 
 * puntaje, finalización del juego, generación de ítems o finalización del nivel)
 * y utiliza un {@link GestorAudio} para reproducir los efectos correspondientes.</p>
 *
 * <p>El objetivo es desacoplar la lógica de sonido del núcleo del juego, 
 * manteniendo un sistema más modular y fácil de mantener.</p>
 *
 * @author Equipo-polimorfo
 * @version 1.0
 */
public class ObservadorAudio implements ObservadorJuego{

    private GestorAudio gestorAudio;

    /**{@inheritDoc} */
    @Override
    public void alcambiarPuntaje(int jugador, int nuevoPuntaje) {
        // aqui va su codigo 
    }

    /**{@inheritDoc} */
    @Override
    public void alTerminarJuego(int ganador) {
        // aqui va su codigo 
    }

    /**{@inheritDoc} */
    @Override
    public void alCompletarNivel() {
        // aqui va su codigo 
    }

    /**{@inheritDoc} */
    @Override
    public void alGenrarItem(Item item) {
        // aqui va su codigo 
    }
    
}
