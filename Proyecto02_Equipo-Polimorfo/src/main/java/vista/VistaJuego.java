package vista;

import javafx.scene.layout.Pane;

/**
 * Vista principal del juego que muestra el campo de juego, paletas,
 * pelota y bloques.
 *
 * @author Equipo-polimorfo
 * @version 1.0
 */
public class VistaJuego {

    private Pane contenedor;

    public VistaJuego() {
        this.contenedor = new Pane();
    }

    public Pane obtenerContenedor() {
        return contenedor;
    }

    public void actualizarPuntaje(int jugador, int nuevoPuntaje) {
    }

    public void actualizarVidas(int jugador, int nuevasVidas) {
    }
}
