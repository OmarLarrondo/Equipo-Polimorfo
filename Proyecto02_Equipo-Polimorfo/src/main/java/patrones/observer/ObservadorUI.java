package patrones.observer;

import mvc.modelo.items.Item;
import mvc.vista.VistaJuego;

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
