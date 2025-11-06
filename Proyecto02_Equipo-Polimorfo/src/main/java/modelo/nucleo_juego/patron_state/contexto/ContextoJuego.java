package modelo.nucleo_juego.patron_state.contexto;

import java.awt.event.InputEvent;

import modelo.ModeloJuego;
import modelo.nucleo_juego.patron_state.estados.EstadoJuego;

/**
 * El contexto del patrón State. Administra el estado actual del juego
 * y delega el comportamiento a dicho estado.
 */
public class ContextoJuego {

    private EstadoJuego estadoActual;
    private ModeloJuego modeloJuego;

    public ContextoJuego(EstadoJuego estadoInicial, ModeloJuego modeloJuego) {
        this.modeloJuego = modeloJuego;
        this.estadoActual = estadoInicial;

        if (estadoInicial != null) {
            estadoInicial.entrar(this); // Al iniciar, se entra en el primer estado
        }
    }

    /** Cambia el estado actual del juego. */
    public void establecerEstado(EstadoJuego nuevoEstado) {
        if (estadoActual != null) {
            estadoActual.salir(this); 
        }
        estadoActual = nuevoEstado;
        if (estadoActual != null) {
            estadoActual.entrar(this);
        }
    }

    /** Devuelve el estado actual del juego. */
    public EstadoJuego obtenerEstado() {
        return estadoActual;
    }

    /** Actualiza la lógica del estado actual. */
    public void actualizar(double tiempoDelta) {
        if (estadoActual != null) {
            estadoActual.actualizar(tiempoDelta);
        }
    }

    /** Pasa las entradas al estado actual. */
    public void manejarEntrada(InputEvent entrada) {
        if (estadoActual != null) {
            estadoActual.manejarEntrada(entrada);
        }
    }

    /** Devuelve el modelo del juego (por si un estado lo necesita). */
    public ModeloJuego getModeloJuego() {
        return modeloJuego;
    }
}
