package modelo.ui_uix.observer;

import modelo.Item;
import modelo.ui_uix.vistas_mvc_javafx.VistaJuego;

public class ObservadorUI implements ObservadorJuego {
    
    private VistaJuego vistaJuego;

    @Override
    public void alcambiarPuntaje(int jugador, int nuevoPuntaje) {
        // aqui va su codigo 
    }

    @Override
    public void alTerminarJuego(int ganador) {
        // aqui va su codigo 
    }

    @Override
    public void alCompletarNivel() {
        // aqui va su codigo 
    }

    @Override
    public void alGenrarItem(Item item) {
        // aqui va su codigo 
    }
}
