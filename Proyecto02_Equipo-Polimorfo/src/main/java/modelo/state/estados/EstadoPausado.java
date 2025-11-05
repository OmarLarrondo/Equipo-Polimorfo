package modelo.state.estados;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import modelo.state.contexto.ContextoJuego;

/**
 * Representa el estado en que el juego está pausado.
 * No actualiza la lógica del juego, pero espera una entrada
 * del usuario para reanudarlo o salir al menú principal.
 */

public class EstadoPausado implements EstadoJuego {

    private  EstadoJuego estadoPrevio;

    public EstadoPausado(EstadoJuego estadoPrevio) {
        this.estadoPrevio = estadoPrevio;
    }

    @Override
    public void entrar(ContextoJuego contexto) {
        System.out.println("Juego en pausa");
        //se deberia llamar a contecto y a sus metoodos para detener el tiempo etc
        //contexto.getModeloJuego()
    }

    @Override
    public void actualizar(double tiempoDelta) {
        throw new IllegalStateException("No se puede actulizar, mientras esta en estado pausado");
    }

    @Override
    public void salir(ContextoJuego contexto) {
        //Aquí  se deberia reanudar sonidos, reiniciar el temporizador, etc.
        System.out.println("Reanudando el juego...");
    }

    @Override
    public void manejarEntrada(InputEvent entrada) {
        if (entrada instanceof KeyEvent eventoTeclado) {
            int codigoTecla = eventoTeclado.getKeyCode();

            // Si se presiona ENTER, P o ESC, reanudar el juego
            if (codigoTecla == KeyEvent.VK_ENTER 
                    || codigoTecla == KeyEvent.VK_P 
                    || codigoTecla == KeyEvent.VK_ESCAPE) {
                    
                // Recuperaf el contexto a través de la fuente del evento
                Object source = eventoTeclado.getSource();
                if (source instanceof ContextoJuego contexto) {
                    contexto.establecerEstado(estadoPrevio);
                } else {
                    System.out.println("No se pudo acceder al contexto del juego.");
                }
            }
        }
    }
}
