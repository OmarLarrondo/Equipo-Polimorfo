package modelo.state.estados;

import java.awt.event.InputEvent;
import controlador.ControladorMenu;
import modelo.state.contexto.ContextoJuego;

public class EstadoMenu implements EstadoJuego {

    private ControladorMenu controladorMenu;
    
    public EstadoMenu(ControladorMenu controladorMenu) {
        this.controladorMenu = controladorMenu;
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
