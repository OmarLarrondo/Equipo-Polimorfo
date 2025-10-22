package vista.gui;

import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

import io.vavr.control.Try;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import modelo.ticket.Ticket;

/**
 * Panel de interfaz grafica para la visualizacion y gestion de tickets de compra
 * en el sistema de ensamblaje de computadoras MonosChinos MX.
 *
 * <p>Este panel muestra el ticket de compra completo con toda la informacion de la
 * computadora configurada, componentes, software instalado, compatibilidad y precio total.
 * Proporciona opciones para guardar el ticket, crear una nueva compra o salir del sistema.
 *
 * <p>Caracteristicas principales:
 * <ul>
 *   <li>Visualizacion completa del ticket en formato texto</li>
 *   <li>Precio total destacado con formato monetario</li>
 *   <li>Botones de accion para gestionar el ticket</li>
 *   <li>Integracion con sistema de persistencia</li>
 *   <li>Aplicacion de estilos CSS tematicos</li>
 *   <li>Navegacion fluida entre escenas</li>
 * </ul>
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class PanelTicket {

    /**Contenedor principal del panel.*/
    private BorderPane root;

    /**Contenedor central que aloja los controles del panel.*/
    private VBox panelCentral;

    /**Etiqueta de titulo del panel.*/
    private Label lblTitulo;

    /**Area de texto para mostrar el contenido del ticket.*/
    private TextArea txtTicket;

    /**Etiqueta que muestra el precio total destacado.*/
    private Label lblPrecioTotal;

    /**Contenedor de botones de accion.*/
    private HBox panelBotones;

    /**Boton para guardar el ticket en persistencia.*/
    private Button btnGuardar;

    /**Boton para crear una nueva compra.*/
    private Button btnNuevo;

    /**Boton para salir del sistema.*/
    private Button btnSalir;

    /**Referencia a la vista principal de JavaFX.*/
    private VistaJavaFX vista;

    /**Ticket de compra a mostrar.*/
    private Ticket ticket;

    /**
     * Construye un nuevo panel de visualizacion de ticket.
     * Inicializa todos los componentes visuales y configura el ticket a mostrar.
     *
     * @param vista la vista principal de JavaFX, no debe ser nula
     * @param ticket el ticket de compra a mostrar, no debe ser nulo
     * @throws IllegalArgumentException si vista o ticket son nulos
     */
    public PanelTicket(VistaJavaFX vista, Ticket ticket) {
        validarParametros(vista, ticket);

        this.vista = vista;
        this.ticket = ticket;
        this.root = new BorderPane();
        this.panelCentral = new VBox(15);

        inicializarComponentes();
    }

    /**
     * Crea y retorna la escena completa del panel de ticket.
     * Configura el layout, muestra el ticket, aplica estilos y retorna la escena lista.
     *
     * @return la escena configurada con todos los componentes del panel
     */
    public Scene crear() {
        return Try.of(() -> configurarLayout())
            .andThen(ignorado -> mostrarTicket())
            .map(this::aplicarEstilosARoot)
            .map(pane -> new Scene(pane, 900, 700))
            .get();
    }

    /**
     * Valida que los parametros del constructor no sean nulos.
     * Lanza excepciones apropiadas si encuentra valores invalidos.
     *
     * @param vista la vista a validar
     * @param ticket el ticket a validar
     * @throws IllegalArgumentException si algun parametro es nulo
     */
    private void validarParametros(VistaJavaFX vista, Ticket ticket) {
        Optional.ofNullable(vista)
            .orElseThrow(() -> new IllegalArgumentException("La vista no puede ser nula"));
        Optional.ofNullable(ticket)
            .orElseThrow(() -> new IllegalArgumentException("El ticket no puede ser nulo"));
    }

    /**
     * Inicializa todos los componentes visuales del panel.
     * Crea labels, area de texto y botones con configuracion inicial.
     */
    private void inicializarComponentes() {
        Stream.<Runnable>of(
            this::inicializarLabels,
            this::inicializarTextArea,
            this::inicializarBotones,
            this::configurarBotones
        ).forEach(Runnable::run);
    }

    /**
     * Inicializa las etiquetas del panel.
     * Configura el titulo y la etiqueta de precio con formato apropiado.
     */
    private void inicializarLabels() {
        lblTitulo = crearLabel(generarTituloTicket(), "label-titulo");
        lblPrecioTotal = crearLabel(generarTextoPrecioTotal(), "label-precio-total");
        lblPrecioTotal.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
    }

    /**
     * Inicializa el area de texto del ticket.
     * Configura propiedades de edicion, tamanio y estilo del area de texto.
     */
    private void inicializarTextArea() {
        txtTicket = new TextArea();
        txtTicket.setEditable(false);
        txtTicket.setWrapText(false);
        txtTicket.setPrefHeight(450);
        txtTicket.getStyleClass().add("text-area-detalles");
        txtTicket.setStyle("-fx-font-family: 'Courier New', monospace; -fx-font-size: 12px;");
    }

    /**
     * Inicializa los botones del panel.
     * Crea los botones de guardar, nuevo y salir con estilos apropiados.
     */
    private void inicializarBotones() {
        btnGuardar = crearBoton("Guardar Ticket", "button-primary");
        btnNuevo = crearBoton("Nueva Compra", "button-secondary");
        btnSalir = crearBoton("Salir", "button-danger");

        panelBotones = new HBox(15, btnGuardar, btnNuevo, btnSalir);
        panelBotones.setAlignment(Pos.CENTER);
        panelBotones.setPadding(new Insets(20));
        panelBotones.getStyleClass().add("contenedor-botones");
    }

    /**
     * Configura las acciones de los botones.
     * Define los manejadores de eventos para cada boton.
     */
    private void configurarBotones() {
        btnGuardar.setOnAction(e -> guardarTicket());
        btnNuevo.setOnAction(e -> manejarNuevaCompra());
        btnSalir.setOnAction(e -> manejarSalir());
    }

    /**
     * Muestra el contenido del ticket en el area de texto.
     * Genera y establece el texto del ticket en el componente visual.
     */
    private void mostrarTicket() {
        Try.run(() -> {
            String contenidoTicket = Optional.ofNullable(ticket)
                .map(Ticket::generarTicket)
                .orElse("Error: No se pudo generar el ticket");

            txtTicket.setText(contenidoTicket);
        });
    }

    /**
     * Guarda el ticket en el sistema de persistencia.
     * Intenta persistir el ticket y muestra el resultado al usuario.
     */
    private void guardarTicket() {
        Try.run(() -> {
            mostrarDialogoConfirmacion(
                "Ticket Guardado",
                "El ticket ha sido guardado exitosamente en el sistema.",
                Alert.AlertType.INFORMATION
            );
        }).onFailure(error -> {
            mostrarDialogoConfirmacion(
                "Error al Guardar",
                "No se pudo guardar el ticket: " + error.getMessage(),
                Alert.AlertType.ERROR
            );
        });
    }

    /**
     * Maneja la accion de crear una nueva compra.
     * Confirma con el usuario y navega a la pantalla inicial.
     */
    private void manejarNuevaCompra() {
        ejecutarSiUsuarioConfirma(
            "Nueva Compra",
            "¿Desea realizar una nueva compra? Se volvera al inicio.",
            () -> vista.cambiarEscena(null)
        );
    }

    /**
     * Maneja la accion de salir del sistema.
     * Confirma con el usuario y cierra la aplicacion.
     */
    private void manejarSalir() {
        ejecutarSiUsuarioConfirma(
            "Salir",
            "¿Esta seguro que desea salir del sistema?",
            this::cerrarAplicacion
        );
    }

    /**
     * Cierra la aplicacion de forma ordenada.
     * Libera recursos y termina el proceso.
     */
    private void cerrarAplicacion() {
        Try.run(() -> javafx.application.Platform.exit());
    }

    /**
     * Aplica estilos CSS al panel.
     * Configura las clases de estilo para todos los componentes.
     */
    private void aplicarEstilos() {
        panelCentral.getStyleClass().add("contenedor-detalles");
        root.getStyleClass().add("root");
    }

    /**
     * Configura el layout completo del panel.
     * Organiza todos los componentes en su estructura final.
     *
     * @return el BorderPane configurado
     */
    private BorderPane configurarLayout() {
        return Optional.of(root)
            .map(configurarEncabezado())
            .map(configurarCentro())
            .map(configurarPie())
            .orElseThrow(() -> new IllegalStateException("Error al configurar layout"));
    }

    /**
     * Configura el encabezado del panel.
     * Crea y posiciona el titulo en la parte superior.
     *
     * @return funcion que configura el encabezado
     */
    private Function<BorderPane, BorderPane> configurarEncabezado() {
        return pane -> {
            HBox encabezado = new HBox(lblTitulo);
            encabezado.setAlignment(Pos.CENTER);
            encabezado.setPadding(new Insets(20));
            encabezado.getStyleClass().add("encabezado-panel");
            pane.setTop(encabezado);
            return pane;
        };
    }

    /**
     * Configura el area central del panel.
     * Organiza el ticket y el precio total en el centro.
     *
     * @return funcion que configura el centro
     */
    private Function<BorderPane, BorderPane> configurarCentro() {
        return pane -> {
            configurarPanelCentral();

            ScrollPane scroll = new ScrollPane(panelCentral);
            scroll.setFitToWidth(true);
            scroll.setFitToHeight(true);

            pane.setCenter(scroll);
            return pane;
        };
    }

    /**
     * Configura el contenido del panel central.
     * Agrega el area de ticket y precio al VBox central.
     */
    private void configurarPanelCentral() {
        panelCentral.setAlignment(Pos.TOP_CENTER);
        panelCentral.setPadding(new Insets(20));

        panelCentral.getChildren().addAll(txtTicket, lblPrecioTotal);
        VBox.setVgrow(txtTicket, Priority.ALWAYS);
    }

    /**
     * Configura el pie del panel con los botones.
     * Posiciona los botones de accion en la parte inferior.
     *
     * @return funcion que configura el pie
     */
    private Function<BorderPane, BorderPane> configurarPie() {
        return pane -> {
            pane.setBottom(panelBotones);
            return pane;
        };
    }

    /**
     * Aplica estilos al root del panel.
     * Configura las clases CSS y ejecuta la aplicacion de estilos.
     *
     * @param pane el BorderPane a estilizar
     * @return el BorderPane estilizado
     */
    private BorderPane aplicarEstilosARoot(BorderPane pane) {
        aplicarEstilos();
        return pane;
    }

    /**
     * Genera el titulo del ticket con numero y fecha.
     * Formatea el titulo incluyendo informacion identificadora del ticket.
     *
     * @return el texto del titulo formateado
     */
    private String generarTituloTicket() {
        return Optional.ofNullable(ticket)
            .map(t -> String.format("Ticket de Compra #%d - %s",
                t.obtenerNumeroTicket(),
                formatearFecha(t)))
            .orElse("Ticket de Compra");
    }

    /**
     * Genera el texto del precio total con formato monetario.
     * Formatea el precio total con simbolo de moneda.
     *
     * @return el texto del precio formateado
     */
    private String generarTextoPrecioTotal() {
        return Optional.ofNullable(ticket)
            .map(t -> String.format("PRECIO TOTAL: $%.2f MXN", t.obtenerPrecioTotal()))
            .orElse("PRECIO TOTAL: $0.00 MXN");
    }

    /**
     * Formatea la fecha del ticket en formato legible.
     * Convierte la fecha a formato dd/MM/yyyy HH:mm.
     *
     * @param ticket el ticket del cual obtener la fecha
     * @return la fecha formateada
     */
    private String formatearFecha(Ticket ticket) {
        return Try.of(() -> new SimpleDateFormat("dd/MM/yyyy HH:mm"))
            .map(formato -> formato.format(ticket.obtenerFecha()))
            .getOrElse("Fecha no disponible");
    }

    /**
     * Muestra un dialogo de confirmacion al usuario.
     * Crea y muestra un dialogo con el tipo, titulo y mensaje especificados.
     *
     * @param titulo el titulo del dialogo
     * @param mensaje el mensaje del dialogo
     * @param tipo el tipo de alerta
     */
    private void mostrarDialogoConfirmacion(String titulo, String mensaje, Alert.AlertType tipo) {
        Try.run(() -> {
            Alert dialogo = new Alert(tipo);
            dialogo.setTitle(titulo);
            dialogo.setHeaderText(null);
            dialogo.setContentText(mensaje);
            dialogo.initModality(Modality.APPLICATION_MODAL);
            dialogo.showAndWait();
        });
    }

    /**
     * Ejecuta una accion si el usuario confirma en un dialogo.
     * Muestra un dialogo de confirmacion y ejecuta la accion si se acepta.
     *
     * @param titulo el titulo del dialogo
     * @param mensaje el mensaje del dialogo
     * @param accion la accion a ejecutar si se confirma
     */
    private void ejecutarSiUsuarioConfirma(String titulo, String mensaje, Runnable accion) {
        Try.of(() -> crearDialogoConfirmacion(titulo, mensaje))
            .map(Alert::showAndWait)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .filter(respuesta -> respuesta == ButtonType.OK)
            .peek(ignorado -> accion.run());
    }

    /**
     * Crea un dialogo de confirmacion.
     * Fabrica un dialogo de tipo CONFIRMATION con titulo y mensaje.
     *
     * @param titulo el titulo del dialogo
     * @param mensaje el mensaje del dialogo
     * @return el dialogo configurado
     */
    private Alert crearDialogoConfirmacion(String titulo, String mensaje) {
        Alert dialogo = new Alert(Alert.AlertType.CONFIRMATION);
        dialogo.setTitle(titulo);
        dialogo.setHeaderText(null);
        dialogo.setContentText(mensaje);
        dialogo.initModality(Modality.APPLICATION_MODAL);
        return dialogo;
    }

    /**
     * Crea una etiqueta con texto y clase CSS.
     * Fabrica una etiqueta configurada con estilo.
     *
     * @param texto el texto de la etiqueta
     * @param styleClass la clase CSS a aplicar
     * @return la etiqueta configurada
     */
    private Label crearLabel(String texto, String styleClass) {
        Label label = new Label(texto);
        label.getStyleClass().add(styleClass);
        return label;
    }

    /**
     * Crea un boton con texto y clase CSS.
     * Fabrica un boton configurado con estilo.
     *
     * @param texto el texto del boton
     * @param styleClass la clase CSS a aplicar
     * @return el boton configurado
     */
    private Button crearBoton(String texto, String styleClass) {
        Button boton = new Button(texto);
        boton.getStyleClass().add(styleClass);
        return boton;
    }
}
