package app;

import controlador.ControladorMenu;
import controlador.ControladorSeleccionDificultad;
import fachada.FachadaJuego;
import javafx.application.Application;
import javafx.stage.Stage;
import vista.GestorEscenas;
import vista.VistaMenu;
import vista.VistaSeleccionDificultad;

/**
 * Clase principal de la aplicación Pong Evolved.
 * Extiende Application de JavaFX y configura el entorno inicial del juego.
 * Implementa el patrón MVC integrando el modelo, vista y controlador.
 */
public class AplicacionPong extends Application {

    private static final String TITULO_APLICACION = "PONG EVOLVED";
    private static final int ANCHO_MINIMO = 800;
    private static final int ALTO_MINIMO = 600;

    private GestorEscenas gestorEscenas;
    private FachadaJuego fachadaJuego;
    private Stage escenarioPrincipal;

    /**
     * Método de inicialización ejecutado antes de start().
     * Inicializa el modelo del juego y carga recursos necesarios.
     */
    @Override
    public void init() {
        System.out.println("Inicializando Pong Evolved...");
        inicializarModelo();
    }

    /**
     * Método principal de inicio de la aplicación JavaFX.
     * Configura el Stage principal y muestra el menú inicial.
     *
     * @param escenarioPrincipal Stage principal de la aplicación
     */
    @Override
    public void start(Stage escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
        configurarEscenario(escenarioPrincipal);
        inicializarGestorEscenas(escenarioPrincipal);
        inicializarVistaMenu();
        inicializarVistaSeleccionDificultad();

        gestorEscenas.mostrarMenu();

        System.out.println("Pong Evolved iniciado correctamente.");
    }

    /**
     * Inicializa el modelo del juego a través de la fachada.
     */
    private void inicializarModelo() {
        try {
            this.fachadaJuego = new FachadaJuego();
        } catch (Exception e) {
            System.err.println("Advertencia: FachadaJuego no disponible aún. Usando placeholder.");
            this.fachadaJuego = null;
        }
    }

    /**
     * Configura las propiedades del Stage principal.
     *
     * @param escenario Stage a configurar
     */
    private void configurarEscenario(Stage escenario) {
        escenario.setTitle(TITULO_APLICACION);
        escenario.setMinWidth(ANCHO_MINIMO);
        escenario.setMinHeight(ALTO_MINIMO);
        escenario.setResizable(true);

        escenario.setOnCloseRequest(evento -> {
            System.out.println("Cerrando aplicación...");
            limpiarRecursos();
        });
    }

    /**
     * Inicializa el gestor de escenas de la aplicación.
     *
     * @param escenario Stage principal
     */
    private void inicializarGestorEscenas(Stage escenario) {
        this.gestorEscenas = new GestorEscenas(escenario);
    }

    /**
     * Inicializa la vista del menú principal y la registra en el gestor.
     */
    private void inicializarVistaMenu() {
        ControladorMenu controladorMenu = new ControladorMenu();
        controladorMenu.establecerGestorEscenas(gestorEscenas);

        if (fachadaJuego != null) {
            controladorMenu.establecerFachadaJuego(fachadaJuego);
        }

        VistaMenu vistaMenu = new VistaMenu(controladorMenu);
        gestorEscenas.registrarEscena("menu", vistaMenu.obtenerEscena());
    }

    /**
     * Inicializa la vista de seleccion de dificultad y la registra en el gestor.
     */
    private void inicializarVistaSeleccionDificultad() {
        ControladorSeleccionDificultad controlador = new ControladorSeleccionDificultad();
        controlador.establecerGestorEscenas(gestorEscenas);

        if (fachadaJuego != null) {
            controlador.establecerFachadaJuego(fachadaJuego);
        }

        VistaSeleccionDificultad vista = new VistaSeleccionDificultad(controlador);
        gestorEscenas.registrarEscena("seleccion-dificultad", vista.obtenerEscena());
    }

    /**
     * Método ejecutado al cerrar la aplicación.
     * Libera recursos y realiza limpieza necesaria.
     */
    @Override
    public void stop() {
        limpiarRecursos();
        System.out.println("Aplicación cerrada.");
    }

    /**
     * Limpia los recursos de la aplicación.
     */
    private void limpiarRecursos() {
        if (gestorEscenas != null) {
            gestorEscenas.limpiarCache();
        }
    }

    /**
     * Alterna el modo de pantalla completa del escenario principal.
     * Implementa programación funcional aplicando la transformación de estado.
     */
    public void alternarPantallaCompleta() {
        aplicarCambioEstadoPantallaCompleta(escenarioPrincipal);
    }

    /**
     * Aplica el cambio de estado de pantalla completa al Stage.
     * Función pura que transforma el estado actual al opuesto.
     *
     * @param stage Stage al que aplicar el cambio
     */
    private void aplicarCambioEstadoPantallaCompleta(Stage stage) {
        stage.setFullScreen(!stage.isFullScreen());
    }

    /**
     * Obtiene el Stage principal de la aplicación.
     *
     * @return Stage principal
     */
    public Stage obtenerEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    /**
     * Método main que lanza la aplicación JavaFX.
     *
     * @param args Argumentos de línea de comandos
     */
    public static void main(String[] args) {
        launch(args);
    }
}
