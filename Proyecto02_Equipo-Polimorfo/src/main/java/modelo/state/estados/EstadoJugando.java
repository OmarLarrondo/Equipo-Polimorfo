package main.java.modelo.state.estados;

import java.awt.event.InputEvent;

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
