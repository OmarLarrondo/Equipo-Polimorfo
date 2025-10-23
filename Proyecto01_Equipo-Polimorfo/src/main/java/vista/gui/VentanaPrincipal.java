package vista.gui;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;

import io.vavr.control.Try;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import persistencia.PersistenciaSQLite;
import persistencia.ServicioPersistencia;

/**
 * Ventana principal del sistema de ensamblaje de computadoras MonosChinos MX.
 * Proporciona el menu principal de navegacion que permite acceder a todas las
 * funcionalidades del sistema mediante una interfaz grafica moderna e intuitiva.
 *
 * <p>Esta clase implementa la pantalla inicial del sistema JavaFX, presentando
 * un menu con las siguientes opciones principales:
 * <ul>
 *   <li>PC Personalizada - Permite construir una computadora con componentes personalizados</li>
 *   <li>PC Prearmada - Permite seleccionar configuraciones prearmadas del catalogo</li>
 *   <li>Historial - Muestra el historial de ventas y tickets guardados</li>
 *   <li>Salir - Cierra la aplicacion de forma ordenada</li>
 * </ul>
 *
 * <p>Los botones del menu incluyen iconos vectoriales para mejorar la experiencia
 * de usuario. El diseno visual aplica el tema oscuro definido en theme-dark.css,
 * proporcionando una experiencia visual consistente y profesional en toda la aplicacion.
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class VentanaPrincipal {

    /**Contenedor principal con layout de tipo BorderPane.*/
    private BorderPane root;

    /**Contenedor vertical para organizar el menu de botones principales.*/
    private VBox menuPrincipal;

    /**Boton para acceder a la construccion de PC personalizada.*/
    private Button btnPCPersonalizada;

    /**Boton para acceder a la seleccion de PC prearmada.*/
    private Button btnPCPrearmada;

    /**Boton para acceder al historial de ventas y tickets.*/
    private Button btnHistorial;

    /**Boton para salir del sistema.*/
    private Button btnSalir;

    /**Referencia a la vista principal de JavaFX para navegacion entre escenas.*/
    private VistaJavaFX vista;

    /**
     * Construye una nueva ventana principal del sistema.
     * Inicializa todos los componentes de la interfaz y valida que la vista no sea nula.
     *
     * @param vista la vista principal de JavaFX, no debe ser nula
     * @throws IllegalArgumentException si la vista es nula
     */
    public VentanaPrincipal(VistaJavaFX vista) {
        Optional.ofNullable(vista)
            .orElseThrow(() -> new IllegalArgumentException("La vista no puede ser nula"));

        this.vista = vista;
        this.root = new BorderPane();
        this.menuPrincipal = new VBox(20);
        inicializarComponentes();
    }

    /**
     * Crea y retorna la escena completa de la ventana principal.
     * Configura el layout, aplica estilos CSS y retorna una escena lista para mostrarse.
     *
     * @return la escena configurada con dimensiones de 900x700 pixeles
     */
    public Scene crear() {
        return Try.of(() -> configurarLayout())
            .map(this::aplicarEstilosARoot)
            .map(pane -> new Scene(pane, 900, 700))
            .andThen(this::aplicarEstilosCSS)
            .get();
    }

    /**
     * Inicializa todos los componentes visuales del panel.
     * Crea los botones del menu principal y configura sus acciones.
     */
    private void inicializarComponentes() {
        Stream.<Runnable>of(
            this::inicializarBotones,
            this::configurarMenu
        ).forEach(Runnable::run);
    }

    /**
     * Inicializa los botones del menu principal con sus iconos y estilos.
     * Utiliza FontAwesome para iconos vectoriales profesionales.
     */
    private void inicializarBotones() {
        btnPCPersonalizada = crearBotonMenu(
            "PC Personalizada",
            e -> navegarAPanelComponentes(),
            FontAwesomeSolid.DESKTOP,
            "button-primary"
        );

        btnPCPrearmada = crearBotonMenu(
            "PC Prearmada",
            e -> navegarAPanelPCPrearmada(),
            FontAwesomeSolid.LAPTOP,
            "button-primary"
        );

        btnHistorial = crearBotonMenu(
            "Historial de Ventas",
            e -> navegarAPanelHistorial(),
            FontAwesomeSolid.HISTORY,
            "button-secondary"
        );

        btnSalir = crearBotonMenu(
            "Salir",
            e -> manejarSalida(),
            FontAwesomeSolid.SIGN_OUT_ALT,
            "button-danger"
        );
    }

    /**
     * Configura el contenedor del menu principal con todos los botones.
     * Establece el espaciado, alineacion y agrega todos los botones al VBox.
     */
    private void configurarMenu() {
        menuPrincipal.setAlignment(Pos.CENTER);
        menuPrincipal.setPadding(new Insets(40));

        Stream.of(
            btnPCPersonalizada,
            btnPCPrearmada,
            btnHistorial,
            btnSalir
        ).forEach(configurarTamanioBoton());
    }

    /**
     * Configura el layout completo de la ventana principal.
     * Organiza el encabezado, centro y pie del BorderPane.
     *
     * @return el BorderPane configurado con todos sus componentes
     */
    private BorderPane configurarLayout() {
        return Optional.of(root)
            .map(configurarEncabezado())
            .map(configurarCentro())
            .orElseThrow(() -> new IllegalStateException("Error al configurar layout"));
    }

    /**
     * Configura el encabezado de la ventana con el titulo principal.
     * Crea una etiqueta de bienvenida estilizada con el nombre del sistema.
     *
     * @return funcion que configura el encabezado en el BorderPane
     */
    private Function<BorderPane, BorderPane> configurarEncabezado() {
        return pane -> {
            Label titulo = crearTitulo("MonosChinos MX");
            Label subtitulo = crearSubtitulo("Sistema de Ensamblaje de Computadoras");

            VBox encabezado = new VBox(10, titulo, subtitulo);
            encabezado.setAlignment(Pos.CENTER);
            encabezado.setPadding(new Insets(30));
            encabezado.getStyleClass().add("encabezado-panel");

            pane.setTop(encabezado);
            return pane;
        };
    }

    /**
     * Configura el area central de la ventana con el menu de botones.
     * Posiciona el VBox del menu principal en el centro del BorderPane.
     *
     * @return funcion que configura el centro en el BorderPane
     */
    private Function<BorderPane, BorderPane> configurarCentro() {
        return pane -> {
            menuPrincipal.getChildren().addAll(
                btnPCPersonalizada,
                btnPCPrearmada,
                btnHistorial,
                btnSalir
            );
            pane.setCenter(menuPrincipal);
            return pane;
        };
    }

    /**
     * Crea un boton del menu con texto, accion, icono y estilo especificados.
     * Fabrica botones configurados uniformemente para el menu principal.
     *
     * @param texto el texto a mostrar en el boton
     * @param accion el manejador de eventos para el clic del boton
     * @param icono el icono de FontAwesome a mostrar
     * @param styleClass la clase CSS a aplicar al boton
     * @return el boton configurado
     */
    private Button crearBotonMenu(String texto, EventHandler<ActionEvent> accion,
                                   FontAwesomeSolid icono, String styleClass) {
        return Optional.of(new Button(texto))
            .map(aplicarIcono(icono))
            .map(aplicarEstilo(styleClass))
            .map(aplicarAccion(accion))
            .orElseThrow(() -> new IllegalStateException("Error al crear boton"));
    }

    /**
     * Aplica un icono de FontAwesome a un boton.
     * Crea el icono vectorial y lo configura con tamanio apropiado.
     *
     * @param icono el icono a aplicar
     * @return funcion que aplica el icono al boton
     */
    private Function<Button, Button> aplicarIcono(FontAwesomeSolid icono) {
        return boton -> {
            FontIcon fontIcon = new FontIcon(icono);
            fontIcon.setIconSize(20);
            boton.setGraphic(fontIcon);
            return boton;
        };
    }

    /**
     * Aplica una clase CSS a un boton.
     * Agrega la clase de estilo especificada a la lista de estilos del boton.
     *
     * @param styleClass la clase CSS a aplicar
     * @return funcion que aplica el estilo al boton
     */
    private Function<Button, Button> aplicarEstilo(String styleClass) {
        return boton -> {
            boton.getStyleClass().add(styleClass);
            return boton;
        };
    }

    /**
     * Aplica un manejador de eventos a un boton.
     * Configura la accion que se ejecutara al hacer clic en el boton.
     *
     * @param accion el manejador de eventos
     * @return funcion que aplica la accion al boton
     */
    private Function<Button, Button> aplicarAccion(EventHandler<ActionEvent> accion) {
        return boton -> {
            boton.setOnAction(accion);
            return boton;
        };
    }

    /**
     * Configura el tamanio uniforme para los botones del menu.
     * Establece ancho preferido y maximo para consistencia visual.
     *
     * @return operacion que configura el tamanio del boton
     */
    private Consumer<Button> configurarTamanioBoton() {
        return boton -> {
            boton.setPrefWidth(350);
            boton.setMaxWidth(350);
        };
    }

    /**
     * Navega al panel de configuracion de componentes para PC personalizada.
     * Crea una nueva instancia de PanelComponentes y cambia la escena.
     */
    private void navegarAPanelComponentes() {
        Try.run(() -> {
            PanelComponentes panel = new PanelComponentes(vista);
            vista.cambiarEscena(panel.crear());
        }).onFailure(this::manejarErrorNavegacion);
    }

    /**
     * Navega al panel de seleccion de PC prearmadas.
     * Crea una nueva instancia de PanelPCPrearmada y cambia la escena.
     */
    private void navegarAPanelPCPrearmada() {
        Try.run(() -> {
            PanelPCPrearmada panel = new PanelPCPrearmada(vista);
            vista.cambiarEscena(panel.crear());
        }).onFailure(this::manejarErrorNavegacion);
    }

    /**
     * Navega al panel de historial de ventas.
     * Crea una instancia del servicio de persistencia y del panel de historial.
     */
    private void navegarAPanelHistorial() {
        Try.of(() -> crearServicioPersistencia())
            .map(persistencia -> new PanelHistorial(vista, persistencia))
            .map(PanelHistorial::crear)
            .peek(vista::cambiarEscena)
            .onFailure(this::manejarErrorHistorial);
    }

    /**
     * Crea una instancia del servicio de persistencia SQLite.
     * Utiliza una ruta por defecto para la base de datos del sistema.
     *
     * @return instancia configurada de ServicioPersistencia
     */
    private ServicioPersistencia crearServicioPersistencia() {
        String rutaDB = Optional.ofNullable(System.getProperty("user.home"))
            .map(home -> home + "/.monoschinos/ventas.db")
            .orElse("ventas.db");

        return new PersistenciaSQLite(rutaDB);
    }

    /**
     * Maneja la accion de salir del sistema.
     * Muestra un dialogo de confirmacion antes de cerrar la aplicacion.
     */
    private void manejarSalida() {
        crearDialogoConfirmacion(
            "Confirmar Salida",
            "Esta seguro que desea salir del sistema?"
        ).filter(respuesta -> respuesta == ButtonType.OK)
         .ifPresent(respuesta -> cerrarAplicacion());
    }

    /**
     * Cierra la aplicacion de forma ordenada.
     * Finaliza la plataforma JavaFX y termina el proceso.
     */
    private void cerrarAplicacion() {
        Try.run(Platform::exit);
    }

    /**
     * Maneja errores de navegacion mostrando un mensaje al usuario.
     * Presenta un dialogo de error con los detalles del problema.
     *
     * @param error la excepcion que causo el error
     */
    private void manejarErrorNavegacion(Throwable error) {
        mostrarDialogoError(
            "Error de Navegacion",
            "No se pudo acceder a la pantalla solicitada: " + error.getMessage()
        );
    }

    /**
     * Maneja errores especificos del historial de ventas.
     * Muestra un mensaje detallado sobre problemas de persistencia.
     *
     * @param error la excepcion que causo el error
     */
    private void manejarErrorHistorial(Throwable error) {
        mostrarDialogoError(
            "Error al Cargar Historial",
            "No se pudo acceder al historial de ventas. " +
            "Verifique que la base de datos este disponible.\n\n" +
            "Detalles: " + error.getMessage()
        );
    }

    /**
     * Crea un dialogo de confirmacion con titulo y mensaje especificados.
     * Utiliza Alert de JavaFX para presentar el dialogo modal.
     *
     * @param titulo el titulo del dialogo
     * @param mensaje el mensaje de confirmacion
     * @return Optional con la respuesta del usuario
     */
    private Optional<ButtonType> crearDialogoConfirmacion(String titulo, String mensaje) {
        Alert dialogo = new Alert(Alert.AlertType.CONFIRMATION);
        dialogo.setTitle(titulo);
        dialogo.setHeaderText(null);
        dialogo.setContentText(mensaje);
        dialogo.initModality(Modality.APPLICATION_MODAL);
        return dialogo.showAndWait();
    }

    /**
     * Muestra un dialogo de error con titulo y mensaje especificados.
     * Presenta un Alert de tipo ERROR de forma modal.
     *
     * @param titulo el titulo del dialogo de error
     * @param mensaje el mensaje de error a mostrar
     */
    private void mostrarDialogoError(String titulo, String mensaje) {
        Try.run(() -> {
            Alert dialogo = new Alert(Alert.AlertType.ERROR);
            dialogo.setTitle(titulo);
            dialogo.setHeaderText(null);
            dialogo.setContentText(mensaje);
            dialogo.initModality(Modality.APPLICATION_MODAL);
            dialogo.showAndWait();
        });
    }

    /**
     * Crea el titulo principal de la ventana.
     * Genera una etiqueta estilizada con el nombre del sistema.
     *
     * @param texto el texto del titulo
     * @return etiqueta configurada como titulo
     */
    private Label crearTitulo(String texto) {
        Label titulo = new Label(texto);
        titulo.getStyleClass().add("titulo-principal");
        return titulo;
    }

    /**
     * Crea el subtitulo de la ventana.
     * Genera una etiqueta estilizada con descripcion del sistema.
     *
     * @param texto el texto del subtitulo
     * @return etiqueta configurada como subtitulo
     */
    private Label crearSubtitulo(String texto) {
        Label subtitulo = new Label(texto);
        subtitulo.getStyleClass().add("label-titulo");
        return subtitulo;
    }

    /**
     * Aplica estilos CSS al contenedor principal.
     * Agrega la clase root al BorderPane para aplicar el tema visual.
     */
    private void aplicarEstilos() {
        root.getStyleClass().add("root");
    }

    /**
     * Aplica estilos al root del panel y ejecuta la configuracion de estilos.
     * Prepara el BorderPane con las clases CSS necesarias antes de crear la escena.
     *
     * @param pane el BorderPane a estilizar
     * @return el BorderPane estilizado
     */
    private BorderPane aplicarEstilosARoot(BorderPane pane) {
        aplicarEstilos();
        return pane;
    }

    /**
     * Aplica los estilos CSS externos a la escena.
     * Carga el archivo theme-dark.css y lo agrega a la escena.
     *
     * @param escena la escena a la que aplicar estilos
     */
    private void aplicarEstilosCSS(Scene escena) {
        Try.run(() -> {
            String cssPath = getClass().getResource("/css/theme-dark.css").toExternalForm();
            escena.getStylesheets().add(cssPath);
        }).onFailure(error ->
            System.err.println("Error al cargar el archivo CSS: " + error.getMessage())
        );
    }
}
