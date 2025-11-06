package modelo.nucleo_juego.patron_template;

import modelo.ControladorJuego;
import modelo.ModeloJuego;
import modelo.ui_uix.singleton.GestorPuntajes;

public abstract class ModoJuegoAbstracto {
    protected ModeloJuego modeloJuego;
    protected ControladorJuego controladorJuego;
    protected GestorPuntajes gestorPuntajes;

    public void jugar(){
        //aqui va su codigo
    }
    protected abstract void inicializarJuego();
    protected abstract void actualizarJuegp();
    protected abstract boolean verificarCondicionVictoria();
    protected abstract boolean verificarCondicionDerrota();
    
    protected void finalizarJuego(){
        //aqui va su codigo
    }
}
