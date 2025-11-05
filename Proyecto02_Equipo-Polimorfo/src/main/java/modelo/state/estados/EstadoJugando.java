package modelo.state.estados;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import modelo.ControladorJuego;
import modelo.state.contexto.ContextoJuego;

/**
 * Representa el estado en el que el juego está activo (los jugadores están jugando).
 * Se encarga de actualizar la lógica del juego y procesar las entradas del jugador.
 */
public class EstadoJugando implements EstadoJuego {

    private ControladorJuego controladorJuego;

    public EstadoJugando(ControladorJuego controladorJuego) {
        this.controladorJuego = controladorJuego;
    }

    @Override
    public void entrar(ContextoJuego contexto) {
        System.out.println("Entrando al estado Jugando...");
        controladorJuego.iniciarJuego();
    }

    @Override
    public void actualizar(double tiempoDelta) {
        controladorJuego.actualizar(tiempoDelta);
    }

    @Override
    public void salir(ContextoJuego contexto) {
        System.out.println("Saliendo del estado Jugando...");
        controladorJuego.detenerJuego();
    }

    @Override
    public void manejarEntrada(InputEvent entrada) {
        if (entrada instanceof KeyEvent) {
            KeyEvent e = (KeyEvent) entrada;

            switch (e.getKeyCode()) {
                case KeyEvent.VK_P:
                case KeyEvent.VK_ESCAPE:
                    System.out.println("Juego en pausa");

                    contexto.establecerEstado(new EstadoPausado(this));
                    break;

                default:
                    // Delegar otras entradas al controlador del juego
                    controladorJuego.procesarEntrada(entrada);
                    break;
            }
        } else {
            // también se delega
            controladorJuego.procesarEntrada(entrada);
        }
    }
}
