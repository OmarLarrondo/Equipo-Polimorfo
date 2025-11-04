package controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import modelo.FachadaJuego;
import util.CargadorRecursos;
import util.GestorVideos;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Controlador del menú principal del juego.
 * Gestiona los eventos de los botones y la reproducción de videos preview.
 * Implementa el componente Controlador del patrón MVC.
 */
public class ControladorMenu {

    private static final String DIRECTORIO_VIDEOS = "videos/";
    private static final String VIDEO_IDLE = "preview-idle.mp4";
    private static final String VIDEO_UN_JUGADOR = "preview-1jugador.mp4";
    private static final String VIDEO_DOS_JUGADORES = "preview-2jugadores.mp4";
    private static final String VIDEO_CONSTRUCTOR = "preview-constructor.mp4";

    @FXML
    private Button botonUnJugador;

    @FXML
    private Button botonDosJugadores;

    @FXML
    private Button botonModoConstructor;

    @FXML
    private MediaView mediaView;

    private FachadaJuego fachadaJuego;
    private GestorEscenas gestorEscenas;
    private Map<String, MediaPlayer> reproductores;
    private MediaPlayer reproductorActual;

    /**
     * Constructor por defecto requerido por FXML.
     */
    public ControladorMenu() {
        this.reproductores = new HashMap<>();
    }

    /**
     * Establece la fachada del juego.
     *
     * @param fachadaJuego Fachada del modelo del juego
     */
    public void establecerFachadaJuego(FachadaJuego fachadaJuego) {
        this.fachadaJuego = fachadaJuego;
    }

    /**
     * Establece el gestor de escenas.
     *
     * @param gestorEscenas Gestor para cambiar entre escenas
     */
    public void establecerGestorEscenas(GestorEscenas gestorEscenas) {
        this.gestorEscenas = gestorEscenas;
    }

    /**
     * Inicializa el controlador después de cargar el FXML.
     * Carga todos los videos y comienza la reproducción del video idle.
     */
    @FXML
    public void initialize() {
        cargarTodosLosVideos();
        iniciarVideoIdle();
    }

    /**
     * Carga todos los videos necesarios para el menú.
     */
    private void cargarTodosLosVideos() {
        reproductores = obtenerNombresVideos().stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        this::crearReproductorParaVideo
                ));
    }

    /**
     * Obtiene la lista de nombres de todos los videos a cargar.
     *
     * @return Lista con los nombres de los archivos de video
     */
    private List<String> obtenerNombresVideos() {
        return Arrays.asList(
                VIDEO_IDLE,
                VIDEO_UN_JUGADOR,
                VIDEO_DOS_JUGADORES,
                VIDEO_CONSTRUCTOR
        );
    }

    /**
     * Crea un MediaPlayer para un video específico.
     *
     * @param nombreVideo Nombre del archivo de video
     * @return MediaPlayer configurado
     */
    private MediaPlayer crearReproductorParaVideo(String nombreVideo) {
        return CargadorRecursos.cargarVideo(DIRECTORIO_VIDEOS + nombreVideo)
                .map(GestorVideos::crearReproductor)
                .orElseGet(this::crearReproductorVacio);
    }

    /**
     * Crea un reproductor vacío como fallback.
     *
     * @return MediaPlayer vacío
     */
    private MediaPlayer crearReproductorVacio() {
        System.err.println("Advertencia: No se pudo cargar un video. Usando reproductor vacío.");
        return null;
    }

    /**
     * Inicia la reproducción del video idle por defecto.
     */
    private void iniciarVideoIdle() {
        Optional.ofNullable(reproductores.get(VIDEO_IDLE))
                .ifPresent(reproductor -> {
                    reproductorActual = reproductor;
                    GestorVideos.reproducirVideo(reproductor, mediaView);
                });
    }

    /**
     * Maneja el evento de clic en el botón "1 jugador".
     *
     * @param evento Evento de acción
     */
    @FXML
    private void accionUnJugador(ActionEvent evento) {
        System.out.println("Iniciando modo 1 jugador...");
        Optional.ofNullable(gestorEscenas)
                .ifPresent(gestor -> gestor.mostrarJuego());
    }

    /**
     * Maneja el evento de clic en el botón "2 jugadores".
     *
     * @param evento Evento de acción
     */
    @FXML
    private void accionDosJugadores(ActionEvent evento) {
        System.out.println("Iniciando modo 2 jugadores...");
        Optional.ofNullable(gestorEscenas)
                .ifPresent(gestor -> gestor.mostrarJuego());
    }

    /**
     * Maneja el evento de clic en el botón "Modo constructor".
     *
     * @param evento Evento de acción
     */
    @FXML
    private void accionModoConstructor(ActionEvent evento) {
        System.out.println("Iniciando modo constructor...");
        Optional.ofNullable(gestorEscenas)
                .ifPresent(gestor -> gestor.mostrarEditor());
    }

    /**
     * Maneja el evento de clic en el botón "Acerca de".
     *
     * @param evento Evento de acción
     */
    @FXML
    private void accionAcercaDe(ActionEvent evento) {
        System.out.println("Mostrando información del juego...");
    }

    /**
     * Maneja el evento hover sobre el botón "1 jugador".
     */
    @FXML
    private void hoverUnJugador() {
        cambiarVideo(VIDEO_UN_JUGADOR);
    }

    /**
     * Maneja el evento hover sobre el botón "2 jugadores".
     */
    @FXML
    private void hoverDosJugadores() {
        cambiarVideo(VIDEO_DOS_JUGADORES);
    }

    /**
     * Maneja el evento hover sobre el botón "Modo constructor".
     */
    @FXML
    private void hoverModoConstructor() {
        cambiarVideo(VIDEO_CONSTRUCTOR);
    }

    /**
     * Maneja el evento de salida del hover de cualquier botón.
     */
    @FXML
    private void salirHover() {
        cambiarVideo(VIDEO_IDLE);
    }

    /**
     * Cambia el video actual por otro con transición fade.
     *
     * @param nombreVideo Nombre del video a reproducir
     */
    private void cambiarVideo(String nombreVideo) {
        Optional.ofNullable(reproductores.get(nombreVideo))
                .filter(nuevoReproductor -> nuevoReproductor != reproductorActual)
                .ifPresent(nuevoReproductor -> {
                    GestorVideos.transicionarVideo(
                            reproductorActual,
                            nuevoReproductor,
                            mediaView
                    );
                    reproductorActual = nuevoReproductor;
                });
    }

    /**
     * Reinicia todos los videos al estado inicial.
     */
    public void reiniciarVideos() {
        Optional.ofNullable(reproductorActual)
                .ifPresent(GestorVideos::detenerVideo);
        iniciarVideoIdle();
    }

    /**
     * Libera los recursos de todos los reproductores.
     */
    public void liberarRecursos() {
        reproductores.values()
                .forEach(GestorVideos::liberarRecursos);
        reproductores.clear();
    }
}
