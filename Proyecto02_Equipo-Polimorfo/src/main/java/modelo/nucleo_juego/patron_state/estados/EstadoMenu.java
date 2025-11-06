package modelo.nucleo_juego.patron_state.estados;

/*
 * COMENTADO TEMPORALMENTE PARA PERMITIR COMPILACIÓN DEL FRONTEND
 * El backend tiene métodos faltantes en ControladorMenu
 * Descomentar cuando el backend esté completo
 */

/*
import java.awt.event.InputEvent;
import controlador.ControladorMenu;
import modelo.state.contexto.ContextoJuego;

/**
 * Representa el estado del juego en el que se muestra el menú principal.
 * Este estado maneja la lógica y las entradas relacionadas con el menú.
 *‎/
public class EstadoMenu implements EstadoJuego {

    private ControladorMenu controladorMenu;

    public EstadoMenu(ControladorMenu controladorMenu) {
        this.controladorMenu = controladorMenu;
    }

    @Override
    public void entrar(ContextoJuego contexto) {
        // Se ejecuta al entrar en el estado de menú.
        System.out.println("Entrando al estado de Menú...");
        controladorMenu.mostrarMenu();       // Muestra el menú principal
        controladorMenu.reiniciarOpciones(); // Reinicia la selección del menú
    }

    @Override
    public void actualizar(double tiempoDelta) {
        controladorMenu.actualizar(tiempoDelta);
    }

    @Override
    public void salir(ContextoJuego contexto) {
        System.out.println("Saliendo del estado de Menú...");
        controladorMenu.ocultarMenu(); // Oculta los elementos del menú
    }

    @Override
    public void manejarEntrada(InputEvent entrada) {
        // Delegamos la entrada al controlador del menú
        controladorMenu.procesarEntrada(entrada);
    }
}
*/
