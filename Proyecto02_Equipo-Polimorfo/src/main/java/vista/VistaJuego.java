package vista;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.util.Optional;

/**
 * Vista principal del juego que muestra el campo de juego, paletas,
 * pelota y bloques.
 * Implementa responsividad mediante bindings con las dimensiones de la escena.
 *
 * @author Equipo-polimorfo
 * @version 1.0
 */
public class VistaJuego {

    private StackPane contenedor;
    private Pane panelJuego;
    private DoubleProperty anchoProperty;
    private DoubleProperty altoProperty;

    /**
     * Constructor que inicializa la vista del juego con responsividad.
     */
    public VistaJuego() {
        inicializarPropiedades();
        inicializarContenedores();
        configurarResponsividad();
    }

    /**
     * Inicializa las propiedades observables para dimensiones.
     */
    private void inicializarPropiedades() {
        this.anchoProperty = new SimpleDoubleProperty(800.0);
        this.altoProperty = new SimpleDoubleProperty(600.0);
    }

    /**
     * Inicializa los contenedores de la vista.
     */
    private void inicializarContenedores() {
        this.panelJuego = new Pane();
        this.contenedor = new StackPane(panelJuego);
        aplicarEstilosContenedor();
    }

    /**
     * Aplica estilos al contenedor principal.
     */
    private void aplicarEstilosContenedor() {
        contenedor.setStyle("-fx-background-color: black;");
    }

    /**
     * Configura la responsividad del panel de juego.
     * Vincula las dimensiones del panel con las propiedades observables.
     */
    private void configurarResponsividad() {
        vincularDimensionesPanel();
    }

    /**
     * Vincula las dimensiones del panel de juego con las propiedades.
     */
    private void vincularDimensionesPanel() {
        panelJuego.prefWidthProperty().bind(anchoProperty);
        panelJuego.prefHeightProperty().bind(altoProperty);
        panelJuego.minWidthProperty().bind(anchoProperty);
        panelJuego.minHeightProperty().bind(altoProperty);
        panelJuego.maxWidthProperty().bind(anchoProperty);
        panelJuego.maxHeightProperty().bind(altoProperty);
    }

    /**
     * Vincula las dimensiones de la vista con una escena.
     *
     * @param escena Scene a la cual vincular las dimensiones
     */
    public void vincularConEscena(Scene escena) {
        aplicarVinculoEscena(escena);
    }

    /**
     * Aplica el vínculo de dimensiones con la escena.
     *
     * @param escena Scene fuente de las dimensiones
     */
    private void aplicarVinculoEscena(Scene escena) {
        Optional.ofNullable(escena)
                .ifPresent(this::vincularPropiedades);
    }

    /**
     * Vincula las propiedades de ancho y alto con la escena.
     *
     * @param escena Scene a vincular
     */
    private void vincularPropiedades(Scene escena) {
        anchoProperty.bind(escena.widthProperty());
        altoProperty.bind(escena.heightProperty());
    }

    /**
     * Obtiene el contenedor principal de la vista.
     *
     * @return StackPane contenedor
     */
    public StackPane obtenerContenedor() {
        return contenedor;
    }

    /**
     * Obtiene el panel de juego interno.
     *
     * @return Pane del juego
     */
    public Pane obtenerPanelJuego() {
        return panelJuego;
    }

    /**
     * Obtiene el ancho actual del juego.
     *
     * @return Ancho en píxeles
     */
    public double obtenerAncho() {
        return anchoProperty.get();
    }

    /**
     * Obtiene el alto actual del juego.
     *
     * @return Alto en píxeles
     */
    public double obtenerAlto() {
        return altoProperty.get();
    }

    /**
     * Obtiene la propiedad de ancho para vinculación.
     *
     * @return DoubleProperty del ancho
     */
    public DoubleProperty anchoProperty() {
        return anchoProperty;
    }

    /**
     * Obtiene la propiedad de alto para vinculación.
     *
     * @return DoubleProperty del alto
     */
    public DoubleProperty altoProperty() {
        return altoProperty;
    }

    /**
     * Actualiza el puntaje mostrado para un jugador.
     *
     * @param jugador Número del jugador
     * @param nuevoPuntaje Nuevo puntaje a mostrar
     */
    public void actualizarPuntaje(int jugador, int nuevoPuntaje) {
    }

    /**
     * Actualiza las vidas mostradas para un jugador.
     *
     * @param jugador Número del jugador
     * @param nuevasVidas Nuevas vidas a mostrar
     */
    public void actualizarVidas(int jugador, int nuevasVidas) {
    }
}
