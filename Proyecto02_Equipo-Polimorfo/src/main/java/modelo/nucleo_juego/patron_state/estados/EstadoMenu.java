package modelo.nucleo_juego.patron_state.estados;

import java.awt.event.InputEvent;

import modelo.nucleo_juego.controlador.ControladorMenu;
import modelo.nucleo_juego.patron_state.contexto.ContextoJuego;


/**
 * Representa el estado del juego en el que se muestra el menú principal.
 * Este estado maneja la lógica y las entradas relacionadas con el menú.
 */
public class EstadoMenu implements EstadoJuego {

    private ControladorMenu controladorMenu;

    public EstadoMenu(ControladorMenu controladorMenu) {
        this.controladorMenu = controladorMenu;
    }

    @Override
    public void entrar(ContextoJuego contexto) {
        // Se ejecuta al entrar en el estado de menú.
        System.out.println("Entrando al estado de Menú...");
        controladorMenu.initialize();
    }

    @Override
    public void actualizar(double tiempoDelta) {
        controladorMenu.reiniciarVideos();
    }

    @Override
    public void salir(ContextoJuego contexto) {
        System.out.println("Saliendo del estado de Menú...");
        controladorMenu.liberarRecursos();
    }

    @Override
    public void manejarEntrada(InputEvent entrada) {
        // Delegamos la entrada al controlador del menú
        //controladorMenu.NOSEJAJJA
    }
}
