package main.java.modelo.state.estados;

import java.awt.event.InputEvent;

public class EstadoFinJuego implements EstadoJuego {

    private int ganador;
    private int puntajeFinal;

    public EstadoFinJuego(int ganador, int puntajeFinal) {
        this.ganador = ganador;
        this.puntajeFinal = puntajeFinal;
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
