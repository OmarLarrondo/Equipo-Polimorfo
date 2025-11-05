package vista;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Gestor de escenas de la aplicación.
 * Administra la navegación entre las diferentes vistas del juego.
 * Implementa un sistema de caché de escenas para mejorar el rendimiento.
 */
public class GestorEscenas {

    private static final String ESCENA_MENU = "menu";
    private static final String ESCENA_JUEGO = "juego";
    private static final String ESCENA_EDITOR = "editor";
    private static final String ESCENA_PERFIL = "perfil";
    private static final String ESCENA_LOGIN = "login";

    private final Stage escenarioPrincipal;
    private final Map<String, Scene> escenas;
    private String escenaActual;

    /**
     * Constructor del gestor de escenas.
     *
     * @param escenarioPrincipal Stage principal de la aplicación
     */
    public GestorEscenas(Stage escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
        this.escenas = new HashMap<>();
        this.escenaActual = null;
    }

    /**
     * Cambia a una escena específica por su nombre.
     *
     * @param nombreEscena Nombre de la escena a mostrar
     */
    public void cambiarEscena(String nombreEscena) {
        obtenerEscena(nombreEscena)
                .ifPresentOrElse(
                        this::establecerEscena,
                        () -> System.err.println("Escena no encontrada: " + nombreEscena)
                );
    }

    /**
     * Registra una nueva escena en el gestor.
     *
     * @param nombre Nombre identificador de la escena
     * @param escena Scene a registrar
     */
    public void registrarEscena(String nombre, Scene escena) {
        Optional.ofNullable(escena)
                .ifPresent(e -> escenas.put(nombre, e));
    }

    /**
     * Obtiene una escena del caché por su nombre.
     *
     * @param nombre Nombre de la escena
     * @return Optional con la escena, o Optional.empty() si no existe
     */
    private Optional<Scene> obtenerEscena(String nombre) {
        return Optional.ofNullable(escenas.get(nombre));
    }

    /**
     * Establece una escena como la actual en el escenario principal.
     *
     * @param escena Scene a establecer
     */
    private void establecerEscena(Scene escena) {
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.show();
    }

    /**
     * Obtiene la escena actualmente mostrada.
     *
     * @return Scene actual
     */
    public Scene obtenerEscenaActual() {
        return Optional.ofNullable(escenaActual)
                .flatMap(this::obtenerEscena)
                .orElse(null);
    }

    /**
     * Muestra el menú principal.
     */
    public void mostrarMenu() {
        cambiarEscena(ESCENA_MENU);
        escenaActual = ESCENA_MENU;
    }

    /**
     * Muestra la vista del juego.
     */
    public void mostrarJuego() {
        cambiarEscena(ESCENA_JUEGO);
        escenaActual = ESCENA_JUEGO;
    }

    /**
     * Muestra el editor de mapas.
     */
    public void mostrarEditor() {
        cambiarEscena(ESCENA_EDITOR);
        escenaActual = ESCENA_EDITOR;
    }

    /**
     * Muestra el perfil del usuario.
     */
    public void mostrarPerfil() {
        cambiarEscena(ESCENA_PERFIL);
        escenaActual = ESCENA_PERFIL;
    }

    /**
     * Muestra la pantalla de login.
     */
    public void mostrarLogin() {
        cambiarEscena(ESCENA_LOGIN);
        escenaActual = ESCENA_LOGIN;
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
     * Verifica si una escena está registrada.
     *
     * @param nombre Nombre de la escena
     * @return true si está registrada, false en caso contrario
     */
    public boolean existeEscena(String nombre) {
        return escenas.containsKey(nombre);
    }

    /**
     * Limpia todas las escenas del caché excepto la actual.
     */
    public void limpiarCache() {
        String escenaActualTemp = escenaActual;
        escenas.keySet().stream()
                .filter(nombre -> !nombre.equals(escenaActualTemp))
                .forEach(escenas::remove);
    }

    /**
     * Obtiene el nombre de la escena actual.
     *
     * @return Nombre de la escena actual
     */
    public String obtenerNombreEscenaActual() {
        return escenaActual;
    }
}
