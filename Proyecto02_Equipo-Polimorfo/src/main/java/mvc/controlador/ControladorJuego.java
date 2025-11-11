package mvc.controlador;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;

/**
 * Controlador del panel del juego principal.
 * Gestiona la interfaz del juego y la lógica de actualización del estado.
 * Implementa el componente Controlador del patrón MVC.
 * Extiende de ControladorBase para heredar funcionalidad común de atajos de teclado.
 *
 * @author Equipo-polimorfo
 * @version 1.0
 */
public class ControladorJuego extends ControladorBase {

    @FXML
    private StackPane contenedorJuego;

    /**
     * Constructor por defecto requerido por FXML.
     */
    public ControladorJuego() {
    }

    /**
     * Inicializa el controlador después de cargar el FXML.
     */
    @FXML
    @Override
    public void initialize() {
        if (contenedorJuego != null) {
            configurarAtajosTecladoPantallaCompleta(contenedorJuego);
        }
        //aqui va su codigo
    }

    /**
     * Reinicia el estado del controlador al estado inicial.
     */
    public void reiniciarEstado() {
        //aqui va su codigo
    }

    /**
     * Libera los recursos del controlador.
     */
    public void liberarRecursos() {
        //aqui va su codigo
    }
}
