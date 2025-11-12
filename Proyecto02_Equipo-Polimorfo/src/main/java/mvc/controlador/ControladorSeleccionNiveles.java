package mvc.controlador;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import patrones.builder.ConstructorMapa;
import patrones.builder.DirectorNiveles;
import patrones.factory.niveles.Nivel;
import org.kordamp.ikonli.javafx.FontIcon;
import mvc.vista.GestorEscenas;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Controlador del panel de seleccion de niveles.
 * Gestiona la carga dinamica de niveles prearmados y personalizados,
 * y permite la seleccion de nivel para iniciar el juego.
 * Implementa el componente Controlador del patron MVC.
 * Extiende de ControladorBase para heredar funcionalidad comun de atajos de teclado.
 */
public class ControladorSeleccionNiveles extends ControladorBase {

    private static final int COLUMNAS_GRID = 2;
    private static final double ANCHO_TARJETA = 280.0;
    private static final double ALTO_TARJETA = 150.0;
    private static final double DURACION_ANIMACION_MS = 300.0;
    private static final double DELAY_CASCADA_MS = 80.0;

    @FXML
    private GridPane gridNiveles;

    @FXML
    private Button botonRegresar;

    @FXML
    private Button botonJugar;

    private List<Nivel> nivelesDisponibles;
    private Nivel nivelSeleccionado;
    private Button botonSeleccionado;
    private Integer dificultadIA;
    private boolean esContraIA;
    private persistencia.ServicioPersistencia servicioPersistencia;

    /**
     * Constructor por defecto requerido por FXML.
     */
    public ControladorSeleccionNiveles() {
        this.nivelesDisponibles = new ArrayList<>();
        this.nivelSeleccionado = null;
        this.botonSeleccionado = null;
        this.dificultadIA = null;
        this.esContraIA = false;
        this.servicioPersistencia = new persistencia.ServicioPersistencia();
    }

    /**
     * Inicializa el controlador despues de cargar el FXML.
     */
    @FXML
    @Override
    public void initialize() {
        configurarAtajosTecladoPantallaCompleta(gridNiveles);
        cargarNivelesPrearmados();
        cargarNivelesPersonalizados();
        generarTarjetasNiveles();
    }

    /**
     * Carga los niveles prearmados desde el DirectorNiveles.
     */
    private void cargarNivelesPrearmados() {
        DirectorNiveles director = crearDirectorNiveles();
        agregarNivelesPrearmados(director);
    }

    /**
     * Crea una instancia del DirectorNiveles con su constructor.
     *
     * @return DirectorNiveles configurado
     */
    private DirectorNiveles crearDirectorNiveles() {
        ConstructorMapa constructor = new ConstructorMapa();
        return new DirectorNiveles(constructor);
    }

    /**
     * Agrega los niveles prearmados a la lista de niveles disponibles.
     *
     * @param director DirectorNiveles que construye los niveles
     */
    private void agregarNivelesPrearmados(DirectorNiveles director) {
        nivelesDisponibles.add(director.construirNivelFacil());
        nivelesDisponibles.add(director.construirNivelMedio());
        nivelesDisponibles.add(director.construirNivelDificil());
    }

    /**
     * Carga los niveles personalizados desde la base de datos.
     * Los niveles cargados se agregan a la lista de niveles disponibles.
     */
    private void cargarNivelesPersonalizados() {
        servicioPersistencia.cargarNivelesPersonalizados()
            .onSuccess(niveles -> nivelesDisponibles.addAll(niveles))
            .onFailure(error -> {
                System.err.println("Error al cargar niveles personalizados: " + error.getMessage());
                error.printStackTrace();
            });
    }

    /**
     * Genera las tarjetas de niveles en el GridPane.
     */
    private void generarTarjetasNiveles() {
        limpiarGrid();
        int fila = 0;
        int columna = 0;

        for (int i = 0; i < nivelesDisponibles.size(); i++) {
            Nivel nivel = nivelesDisponibles.get(i);
            VBox tarjeta = crearTarjetaNivel(nivel, i);
            gridNiveles.add(tarjeta, columna, fila);

            columna++;
            if (columna >= COLUMNAS_GRID) {
                columna = 0;
                fila++;
            }
        }

        VBox tarjetaNuevo = crearTarjetaNuevoNivel();
        gridNiveles.add(tarjetaNuevo, columna, fila);
    }

    /**
     * Limpia el contenido del GridPane.
     */
    private void limpiarGrid() {
        gridNiveles.getChildren().clear();
    }

    /**
     * Crea una tarjeta visual para un nivel.
     *
     * @param nivel Nivel a representar
     * @param indice Indice del nivel para animacion
     * @return VBox con la tarjeta del nivel
     */
    private VBox crearTarjetaNivel(Nivel nivel, int indice) {
        VBox tarjeta = new VBox(15);
        configurarEstiloTarjeta(tarjeta);

        if (nivel.isMapaPersonalizado()) {
            agregarIndicadorPersonalizado(tarjeta, nivel);
        }

        Label nombreLabel = crearLabelNombre(nivel);
        Label dificultadLabel = crearLabelDificultad(nivel);
        Button botonSeleccionar = crearBotonSeleccionar(nivel);

        tarjeta.getChildren().addAll(nombreLabel, dificultadLabel, botonSeleccionar);
        animarTarjeta(tarjeta, indice);

        return tarjeta;
    }

    /**
     * Agrega un indicador visual a tarjetas de niveles personalizados.
     *
     * @param tarjeta tarjeta a la que agregar el indicador
     * @param nivel nivel personalizado
     */
    private void agregarIndicadorPersonalizado(VBox tarjeta, Nivel nivel) {
        FontIcon iconoPersonalizado = new FontIcon("fas-user");
        iconoPersonalizado.setIconSize(20);
        iconoPersonalizado.setStyle("-fx-icon-color: #4CAF50;");

        Label labelCreador = new Label("Por: " + (nivel.getCreador() != null ? nivel.getCreador() : "Desconocido"));
        labelCreador.setStyle("-fx-font-size: 9px; -fx-text-fill: #4CAF50;");

        tarjeta.getChildren().addAll(iconoPersonalizado, labelCreador);
    }

    /**
     * Configura el estilo base de una tarjeta.
     *
     * @param tarjeta VBox a configurar
     */
    private void configurarEstiloTarjeta(VBox tarjeta) {
        tarjeta.setAlignment(Pos.CENTER);
        tarjeta.setPrefSize(ANCHO_TARJETA, ALTO_TARJETA);
        tarjeta.setMaxSize(ANCHO_TARJETA, ALTO_TARJETA);
        tarjeta.setMinSize(ANCHO_TARJETA, ALTO_TARJETA);
        tarjeta.setPadding(new Insets(20));
        tarjeta.getStyleClass().add("tarjeta-nivel");
    }

    /**
     * Crea el label del nombre del nivel.
     *
     * @param nivel Nivel del que obtener el nombre
     * @return Label configurado
     */
    private Label crearLabelNombre(Nivel nivel) {
        Label label = new Label(nivel.getNombre());
        label.setStyle("-fx-font-size: 14px; -fx-text-fill: #FFFFFF;");
        return label;
    }

    /**
     * Crea el label de la dificultad del nivel.
     *
     * @param nivel Nivel del que obtener la dificultad
     * @return Label configurado
     */
    private Label crearLabelDificultad(Nivel nivel) {
        String estrellas = generarEstrellas(nivel.getDificultad());
        Label label = new Label("Dificultad: " + estrellas);
        label.setStyle("-fx-font-size: 10px; -fx-text-fill: #FFFFFF;");
        return label;
    }

    /**
     * Genera una representacion visual de la dificultad con estrellas.
     *
     * @param dificultad Nivel de dificultad (1-3)
     * @return String con estrellas
     */
    private String generarEstrellas(int dificultad) {
        return "★".repeat(dificultad) + "☆".repeat(3 - dificultad);
    }

    /**
     * Crea el boton de seleccion para un nivel.
     *
     * @param nivel Nivel a seleccionar
     * @return Button configurado
     */
    private Button crearBotonSeleccionar(Nivel nivel) {
        Button boton = new Button("Seleccionar");
        boton.getStyleClass().add("boton-secundario");
        boton.setOnAction(event -> manejarSeleccionNivel(nivel, boton));
        return boton;
    }

    /**
     * Crea la tarjeta para crear un nuevo nivel.
     *
     * @return VBox con la tarjeta de nuevo nivel
     */
    private VBox crearTarjetaNuevoNivel() {
        VBox tarjeta = new VBox(15);
        configurarEstiloTarjeta(tarjeta);

        FontIcon iconoMas = new FontIcon("fas-plus");
        iconoMas.setIconSize(40);
        iconoMas.setStyle("-fx-icon-color: #FFFFFF;");

        Label label = new Label("Crear Nivel");
        label.setStyle("-fx-font-size: 12px; -fx-text-fill: #FFFFFF;");

        Button botonCrear = new Button("Modo Constructor");
        botonCrear.getStyleClass().add("boton-secundario");
        botonCrear.setOnAction(event -> accionModoConstructor());

        tarjeta.getChildren().addAll(iconoMas, label, botonCrear);
        animarTarjeta(tarjeta, nivelesDisponibles.size());

        return tarjeta;
    }

    /**
     * Anima la aparicion de una tarjeta con efecto cascada.
     *
     * @param tarjeta Tarjeta a animar
     * @param indice Indice para calcular delay
     */
    private void animarTarjeta(VBox tarjeta, int indice) {
        tarjeta.setOpacity(0.0);
        tarjeta.setScaleX(0.8);
        tarjeta.setScaleY(0.8);

        double delay = indice * DELAY_CASCADA_MS;

        FadeTransition fade = new FadeTransition(Duration.millis(DURACION_ANIMACION_MS), tarjeta);
        fade.setFromValue(0.0);
        fade.setToValue(1.0);
        fade.setDelay(Duration.millis(delay));

        ScaleTransition escala = new ScaleTransition(Duration.millis(DURACION_ANIMACION_MS), tarjeta);
        escala.setFromX(0.8);
        escala.setFromY(0.8);
        escala.setToX(1.0);
        escala.setToY(1.0);
        escala.setDelay(Duration.millis(delay));

        fade.play();
        escala.play();
    }

    /**
     * Maneja la seleccion de un nivel.
     *
     * @param nivel Nivel seleccionado
     * @param boton Boton que fue clickeado
     */
    private void manejarSeleccionNivel(Nivel nivel, Button boton) {
        limpiarSeleccionPrevia();
        nivelSeleccionado = nivel;
        botonSeleccionado = boton;
        resaltarBotonSeleccionado(boton);
        mostrarBotonJugar();
        System.out.println("Nivel seleccionado: " + nivel.getNombre());
    }

    /**
     * Limpia el resaltado de la seleccion previa si existe.
     */
    private void limpiarSeleccionPrevia() {
        if (botonSeleccionado != null) {
            botonSeleccionado.getStyleClass().remove("boton-nivel-seleccionado");
        }
    }

    /**
     * Resalta el boton seleccionado con estilo especial.
     *
     * @param boton Boton a resaltar
     */
    private void resaltarBotonSeleccionado(Button boton) {
        boton.getStyleClass().add("boton-nivel-seleccionado");
    }

    /**
     * Muestra el boton Jugar con animacion fade-in.
     */
    private void mostrarBotonJugar() {
        botonJugar.setVisible(true);
        botonJugar.setOpacity(0.0);
        FadeTransition fade = new FadeTransition(Duration.millis(300), botonJugar);
        fade.setFromValue(0.0);
        fade.setToValue(1.0);
        fade.play();
    }

    /**
     * Maneja el evento de clic en el boton Jugar.
     * Navega al juego con el nivel seleccionado.
     *
     * @param evento Evento de accion
     */
    @FXML
    private void accionJugar(ActionEvent evento) {
        if (nivelSeleccionado != null) {
            System.out.println("Iniciando juego con nivel: " + nivelSeleccionado.getNombre());
            navegarAlJuego();
        }
    }

    /**
     * Navega a la pantalla del juego.
     */
    private void navegarAlJuego() {
        Optional.ofNullable(gestorEscenas)
                .ifPresent(GestorEscenas::mostrarJuego);
    }

    /**
     * Maneja el evento de clic en el boton Modo Constructor.
     */
    private void accionModoConstructor() {
        System.out.println("Navegando al modo constructor...");
        navegarAlEditor();
    }

    /**
     * Navega al modo constructor/editor.
     */
    private void navegarAlEditor() {
        Optional.ofNullable(gestorEscenas)
                .ifPresent(GestorEscenas::mostrarEditor);
    }

    /**
     * Maneja el evento de clic en el boton Regresar.
     * Vuelve al menu principal o seleccion de dificultad.
     *
     * @param evento Evento de accion
     */
    @FXML
    private void accionRegresar(ActionEvent evento) {
        System.out.println("Regresando...");
        navegarAlMenu();
    }

    /**
     * Navega al menu principal.
     */
    private void navegarAlMenu() {
        Optional.ofNullable(gestorEscenas)
                .ifPresent(gestor -> {
                    if (esContraIA) {
                        gestor.mostrarSeleccionDificultad();
                    } else {
                        gestor.mostrarMenu();
                    }
                });
    }

    /**
     * Reinicia el estado del controlador al estado inicial.
     */
    public void reiniciarEstado() {
        nivelSeleccionado = null;
        botonSeleccionado = null;
        botonJugar.setVisible(false);
        limpiarSeleccionPrevia();
        generarTarjetasNiveles();
    }

    /**
     * Libera los recursos del controlador.
     */
    public void liberarRecursos() {
    }

    /**
     * Establece si el juego es contra IA para determinar la navegacion de regreso.
     *
     * @param esContraIA true si es contra IA, false si es 2 jugadores
     */
    public void establecerEsContraIA(boolean esContraIA) {
        this.esContraIA = esContraIA;
    }

    /**
     * Establece el nivel de dificultad de IA seleccionado previamente.
     *
     * @param dificultad Nivel de dificultad (1-10)
     */
    public void establecerDificultadIA(Integer dificultad) {
        this.dificultadIA = dificultad;
    }

    /**
     * Obtiene el nivel actualmente seleccionado.
     *
     * @return Optional con el nivel seleccionado, o empty si no hay seleccion
     */
    public Optional<Nivel> obtenerNivelSeleccionado() {
        return Optional.ofNullable(nivelSeleccionado);
    }

    /**
     * Obtiene la dificultad de IA configurada.
     *
     * @return Optional con la dificultad, o empty si no fue configurada
     */
    public Optional<Integer> obtenerDificultadIA() {
        return Optional.ofNullable(dificultadIA);
    }
}
