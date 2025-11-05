package modelo.state.estados;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import modelo.state.contexto.ContextoJuego;

/**
 * Representa el estado final del juego (pantalla de resultados).
 * Muestra quién ganó y permite volver al menú principal o reiniciar.
 */
public class EstadoFinJuego implements EstadoJuego {

    private int ganador;
    private int puntajeFinal;

    public EstadoFinJuego(int ganador, int puntajeFinal) {
        this.ganador = ganador;
        this.puntajeFinal = puntajeFinal;
    }

    @Override
    public void entrar(ContextoJuego contexto) {
        System.out.println("=== FIN DEL JUEGO ===");
        System.out.println("Ganador: Jugador " + ganador);
        System.out.println("Puntaje final: " + puntajeFinal);
        System.out.println("Presiona ENTER para volver al menú o ESC para salir.");
        
        // contexto.getModeloJuego().detenerMusica();
    }

    @Override
    public void actualizar(double tiempoDelta) {
        throw new IllegalStateException("No se puede actulizar, mientras esta en estado pausado");
    }

    @Override
    public void salir(ContextoJuego contexto) {
        // Limpia recursos o prepara el cambio de estado
        System.out.println("Saliendo del estado de fin de juego...");
        //se deberia limpiar los recursos
    }

    @Override
    public void manejarEntrada(InputEvent entrada) {
        if (entrada instanceof KeyEvent) {
            KeyEvent e = (KeyEvent) entrada;

            switch (e.getKeyCode()) {
                case KeyEvent.VK_ENTER:
                    System.out.println("Volviendo al menú principal...");
                    // contexto.establecerEstado(new EstadoMenu(...));
                    break;

                case KeyEvent.VK_ESCAPE:
                    System.out.println("Saliendo del juego...");
                    System.exit(0);
                    break;
                default:
                    // Ignora otras teclas
                    break;
            }
        }
    }
}
