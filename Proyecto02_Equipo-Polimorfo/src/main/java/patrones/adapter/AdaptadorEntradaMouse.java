package patrones.adapter;

import javafx.scene.input.MouseEvent;
import javafx.scene.input.MouseButton;

/**
 * Implementación concreta de {@link AdaptadorEntrada} que adapta las acciones
 * del jugador realizadas con el mouse a señales comprensibles por el sistema
 * de control del juego Pong.
 *
 * <p>Esta clase detecta en qué parte de la pantalla el usuario mantiene
 * presionado un clic y traduce dicha información en movimientos verticales
 * (arriba o abajo) o en la acción de pausa. De este modo, el controlador del
 * juego puede interpretar el uso del mouse de manera uniforme.</p>
 *
 * <p>Utiliza los eventos de JavaFX ({@link MouseEvent}) para actualizar el
 * estado de las acciones según la posición y el botón presionado.</p>
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 * @see AdaptadorEntrada
 */
public class AdaptadorEntradaMouse implements AdaptadorEntrada {

    /** Indica si se desea mover hacia arriba. */
    private boolean arriba = false;

    /** Indica si se desea mover hacia abajo. */
    private boolean abajo = false;

    /** Indica si no se esta realizando un movimiento. */
    private boolean pausa = false;

    /**
     * Detecta la posición donde se mantiene presionado un clic del mouse
     * y actualiza las acciones correspondientes.
     *
     * @param e evento de tipo {@link MouseEvent} que representa la acción
     *         del mouse en la interfaz de juego.
     */
    public void mousePresionado(MouseEvent e) {
        double y = e.getY();
        //CHECARRRRRRRRRRRRRRRRRRRRRRR, SE SUPONE QUE DEBERIA SER ADAPTATIVO, PERO AUN NO CHECO ESO.
        if (y < 400) { 
            arriba = true;
        } else { 
            abajo = true;
        }
        //en la mitad,pausar
        if (e.getButton() == MouseButton.SECONDARY || e.getButton() == MouseButton.MIDDLE) {
            pausa = true; // al presionar
        }
    }

    /**
    * Detecta la posicion en donde se libera un clic.
     * @param e evento de tipo {@link MouseEvent} que representa la acción
     *          de liberación del clic.
     */
    public void mouseLiberado(MouseEvent e) {
        double y = e.getY();
        if (y < 400) {
            arriba = false;
        } else {
            abajo = false;
        }
        if (e.getButton() == MouseButton.SECONDARY || e.getButton() == MouseButton.MIDDLE) {
            pausa = false; // al soltar
        }
    }

    /** {@inheritDoc} */
    @Override
    public boolean arribaPresionado() {
        return arriba;
    }

    /** {@inheritDoc} */
    @Override
    public boolean abajoPresionado() {
        return abajo;
    }

    /** {@inheritDoc} */
    @Override
    public boolean pausaPresionado() {
        return pausa;
    }
}
