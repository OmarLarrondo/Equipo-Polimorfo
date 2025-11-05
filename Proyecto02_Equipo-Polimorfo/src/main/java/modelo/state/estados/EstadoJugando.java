package modelo.state.estados;

import java.awt.event.InputEvent;
import modelo.ControladorJuego;
import modelo.state.contexto.ContextoJuego;

public class EstadoJugando implements EstadoJuego {

    private ControladorJuego controladorJuego;

    public EstadoJugando(ControladorJuego controladorJuego) {
        this.controladorJuego = controladorJuego;
    }

    @Override
    public void entrar(ContextoJuego contexto) {
        // aqui va su codigo 

    }

    @Override
    public void actualizar(double tiempoDelta) {
        // aqui va su codigo 

    }

    @Override
    public void salir(ContextoJuego contexto) {
        // aqui va su codigo 

    }

    @Override
    public void manejarEntrada(InputEvent entrada) {
        // aqui va su codigo 

    }
    
}
