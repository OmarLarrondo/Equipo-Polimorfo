package controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import modelo.FachadaJuego;
import util.CargadorRecursos;
import util.GestorVideos;
import vista.GestorEscenas;

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
    private static final String DIRECTORIO_IMAGENES = "imagenes/";
    private static final String VIDEO_IDLE = "preview-idle.mp4";
    private static final String VIDEO_UN_JUGADOR = "preview-1jugador.mp4";
    private static final String VIDEO_DOS_JUGADORES = "preview-2jugadores.mp4";
    private static final String VIDEO_CONSTRUCTOR = "preview-constructor.mp4";
    private static final String IMAGEN_IDLE = "preview-idle.png";
    private static final String IMAGEN_UN_JUGADOR = "preview-1jugador.png";
    private static final String IMAGEN_DOS_JUGADORES = "preview-2jugadores.png";
    private static final String IMAGEN_CONSTRUCTOR = "preview-constructor.png";

    @FXML
    private Button botonUnJugador;

    @FXML
    private Button botonDosJugadores;

    @FXML
    private Button botonModoConstructor;

    @FXML
    private Button botonPantallaCompleta;

    @FXML
    private MediaView mediaView;

    @FXML
    private ImageView imageView;

    private FachadaJuego fachadaJuego;
    private GestorEscenas gestorEscenas;
    private Map<String, MediaPlayer> reproductores;
    private Map<String, Image> imagenes;
    private MediaPlayer reproductorActual;
    private String imagenActual;
    private boolean usarVideos;

    /**
     * Constructor por defecto requerido por FXML.
     */
    public ControladorMenu() {
        this.reproductores = new HashMap<>();
        this.imagenes = new HashMap<>();
        this.usarVideos = true;
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
     * Intenta cargar videos, si fallan usa imágenes de fallback.
     */
    @FXML
    public void initialize() {
        cargarTodosLosVideos();
        determinarModoVisualizacion();
        iniciarVisualizacionInicial();
        configurarAtajosTeclado();
        configurarBindingsResponsivos();
    }

    /**
     * Configura los atajos de teclado para la pantalla completa.
     * F11 para alternar, ESC para salir de pantalla completa.
     */
    private void configurarAtajosTeclado() {
        mediaView.sceneProperty().addListener((observable, oldScene, newScene) ->
            Optional.ofNullable(newScene)
                    .ifPresent(escena ->
                        escena.setOnKeyPressed(this::manejarAtajoTeclado)
                    )
        );
    }

    /**
     * Configura bindings responsivos para MediaView e ImageView.
     * Escala los elementos proporcionalmente con el tamaño de la ventana.
     */
    private void configurarBindingsResponsivos() {
        mediaView.sceneProperty().addListener((observable, oldScene, newScene) ->
            Optional.ofNullable(newScene)
                    .ifPresent(this::aplicarBindingsEscalado)
        );
    }

    /**
     * Aplica los bindings de escalado a las vistas de media e imagen.
     *
     * @param escena Scene de referencia para los bindings
     */
    private void aplicarBindingsEscalado(javafx.scene.Scene escena) {
        vincularDimensionesMediaView(escena);
        vincularDimensionesImageView(escena);
    }

    /**
     * Vincula las dimensiones del MediaView con la escena.
     * Usa un factor de escala del 40% del ancho y 45% del alto de la escena.
     *
     * @param escena Scene de referencia
     */
    private void vincularDimensionesMediaView(javafx.scene.Scene escena) {
        mediaView.fitWidthProperty().bind(
            escena.widthProperty().multiply(0.40)
        );
        mediaView.fitHeightProperty().bind(
            escena.heightProperty().multiply(0.45)
        );
    }

    /**
     * Vincula las dimensiones del ImageView con la escena.
     * Usa un factor de escala del 40% del ancho y 45% del alto de la escena.
     *
     * @param escena Scene de referencia
     */
    private void vincularDimensionesImageView(javafx.scene.Scene escena) {
        imageView.fitWidthProperty().bind(
            escena.widthProperty().multiply(0.40)
        );
        imageView.fitHeightProperty().bind(
            escena.heightProperty().multiply(0.45)
        );
    }

    /**
     * Maneja los eventos de teclado para atajos de pantalla completa.
     *
     * @param evento Evento de teclado
     */
    private void manejarAtajoTeclado(KeyEvent evento) {
        procesarTecla(evento.getCode());
    }

    /**
     * Procesa la tecla presionada y ejecuta la acción correspondiente.
     *
     * @param tecla Código de la tecla presionada
     */
    private void procesarTecla(KeyCode tecla) {
        if (tecla == KeyCode.F11) {
            aplicarAlternanciaFullScreen(gestorEscenas);
        } else if (tecla == KeyCode.ESCAPE) {
            aplicarSalidaFullScreen(gestorEscenas);
        }
    }

    /**
     * Sale del modo pantalla completa si está activo.
     *
     * @param gestor Gestor de escenas con acceso al Stage
     */
    private void aplicarSalidaFullScreen(GestorEscenas gestor) {
        Optional.ofNullable(gestor)
                .map(GestorEscenas::obtenerEscenarioPrincipal)
                .filter(Stage::isFullScreen)
                .ifPresent(stage -> stage.setFullScreen(false));
    }

    /**
     * Carga todos los videos necesarios para el menú.
     * Videos que fallen al cargar serán omitidos del mapa.
     */
    private void cargarTodosLosVideos() {
        reproductores = new HashMap<>();
        obtenerNombresVideos().forEach(nombreVideo -> {
            MediaPlayer player = crearReproductorParaVideo(nombreVideo);
            if (player != null) {
                reproductores.put(nombreVideo, player);
            }
        });
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
     * Determina si se usarán videos o imágenes de fallback.
     * Si no hay reproductores disponibles, carga las imágenes.
     */
    private void determinarModoVisualizacion() {
        usarVideos = !reproductores.isEmpty();

        if (!usarVideos) {
            System.out.println("Videos no disponibles. Usando imágenes de fallback.");
            cargarTodasLasImagenes();
        }

        configurarVistas();
    }

    /**
     * Carga todas las imágenes de fallback necesarias para el menú.
     */
    private void cargarTodasLasImagenes() {
        imagenes = new HashMap<>();
        obtenerNombresImagenes().forEach((clave, rutaImagen) ->
            CargadorRecursos.cargarImagen(DIRECTORIO_IMAGENES + rutaImagen)
                    .ifPresent(imagen -> imagenes.put(clave, imagen))
        );
    }

    /**
     * Obtiene el mapeo de claves a nombres de archivos de imágenes.
     *
     * @return Map con claves de video mapeadas a nombres de imágenes
     */
    private Map<String, String> obtenerNombresImagenes() {
        return Map.of(
                VIDEO_IDLE, IMAGEN_IDLE,
                VIDEO_UN_JUGADOR, IMAGEN_UN_JUGADOR,
                VIDEO_DOS_JUGADORES, IMAGEN_DOS_JUGADORES,
                VIDEO_CONSTRUCTOR, IMAGEN_CONSTRUCTOR
        );
    }

    /**
     * Configura la visibilidad de las vistas según el modo activo.
     */
    private void configurarVistas() {
        mediaView.setVisible(usarVideos);
        imageView.setVisible(!usarVideos);
    }

    /**
     * Inicia la visualización inicial (video o imagen idle).
     */
    private void iniciarVisualizacionInicial() {
        if (usarVideos) {
            iniciarVideoIdle();
        } else {
            mostrarImagen(VIDEO_IDLE);
        }
    }

    /**
     * Muestra una imagen en el ImageView.
     *
     * @param claveImagen Clave de la imagen a mostrar
     */
    private void mostrarImagen(String claveImagen) {
        Optional.ofNullable(imagenes.get(claveImagen))
                .ifPresent(imagen -> {
                    imageView.setImage(imagen);
                    imagenActual = claveImagen;
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
     * Maneja el evento de clic en el botón "Pantalla Completa".
     * Alterna entre modo ventana y pantalla completa.
     *
     * @param evento Evento de acción
     */
    @FXML
    private void accionPantallaCompleta(ActionEvent evento) {
        aplicarAlternanciaFullScreen(gestorEscenas);
    }

    /**
     * Aplica la alternancia de pantalla completa al Stage.
     * Función que transforma el estado de fullscreen.
     *
     * @param gestor Gestor de escenas con acceso al Stage
     */
    private void aplicarAlternanciaFullScreen(GestorEscenas gestor) {
        Optional.ofNullable(gestor)
                .map(GestorEscenas::obtenerEscenarioPrincipal)
                .ifPresent(this::alternarEstadoFullScreen);
    }

    /**
     * Alterna el estado de pantalla completa del Stage.
     *
     * @param stage Stage a modificar
     */
    private void alternarEstadoFullScreen(Stage stage) {
        stage.setFullScreen(!stage.isFullScreen());
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
     * Cambia el video o imagen actual según el modo activo.
     *
     * @param nombreRecurso Nombre del recurso (video o imagen) a mostrar
     */
    private void cambiarVideo(String nombreRecurso) {
        if (usarVideos) {
            cambiarVideoInterno(nombreRecurso);
        } else {
            cambiarImagenInterno(nombreRecurso);
        }
    }

    /**
     * Cambia el video actual por otro con transición fade.
     *
     * @param nombreVideo Nombre del video a reproducir
     */
    private void cambiarVideoInterno(String nombreVideo) {
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
     * Cambia la imagen actual por otra.
     *
     * @param claveImagen Clave de la imagen a mostrar
     */
    private void cambiarImagenInterno(String claveImagen) {
        if (!claveImagen.equals(imagenActual)) {
            mostrarImagen(claveImagen);
        }
    }

    /**
     * Reinicia la visualización al estado inicial (video o imagen idle).
     */
    public void reiniciarVideos() {
        if (usarVideos) {
            Optional.ofNullable(reproductorActual)
                    .ifPresent(GestorVideos::detenerVideo);
            iniciarVideoIdle();
        } else {
            mostrarImagen(VIDEO_IDLE);
        }
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
