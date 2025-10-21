package vista.gui;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import org.controlsfx.control.Notifications;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import modelo.ticket.Ticket;
import persistencia.ServicioPersistencia;

/**
 * Panel de interfaz grafica para visualizar el historial de tickets de venta del sistema.
 * Proporciona funcionalidades para cargar, visualizar, filtrar y eliminar tickets guardados
 * en la base de datos mediante el servicio de persistencia.
 *
 * <p>Esta clase implementa una interfaz moderna con JavaFX que incluye:
 * <ul>
 *   <li>Tabla con lista de tickets mostrando numero, fecha, cliente y total</li>
 *   <li>Area de detalles que muestra el ticket completo seleccionado</li>
 *   <li>Botones para cargar, eliminar tickets y volver al menu principal</li>
 *   <li>Notificaciones visuales de exito o error en las operaciones</li>
 *   <li>Confirmacion antes de operaciones destructivas</li>
 * </ul>
 *
 * <p>La implementacion integra bibliotecas externas como ControlsFX
 * para notificaciones mejoradas e Ikonli para iconos vectoriales,
 * proporcionando una experiencia de usuario moderna y profesional.
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class PanelHistorial {

    /**Contenedor principal con layout BorderPane.*/
    private final BorderPane root;

    /**Tabla que muestra la lista de tickets.*/
    private final TableView<Ticket> tablaTickets;

    /**Columna para el numero de ticket.*/
    private final TableColumn<Ticket, Integer> colNumero;

    /**Columna para la fecha del ticket.*/
    private final TableColumn<Ticket, String> colFecha;

    /**Columna para el nombre del cliente.*/
    private final TableColumn<Ticket, String> colCliente;

    /**Columna para el precio total.*/
    private final TableColumn<Ticket, Double> colTotal;

    /**Area de texto para mostrar los detalles completos del ticket seleccionado.*/
    private final TextArea txtDetalles;

    /**Boton para recargar el historial desde la base de datos.*/
    private final Button btnCargar;

    /**Boton para eliminar el ticket seleccionado.*/
    private final Button btnEliminar;

    /**Boton para volver al menu principal.*/
    private final Button btnVolver;

    /**Referencia a la vista principal de JavaFX.*/
    private final VistaJavaFX vista;

    /**Servicio de persistencia para operaciones con la base de datos.*/
    private final ServicioPersistencia persistencia;

    /**Formateador de fechas con formato dd/MM/yyyy HH:mm:ss.*/
    private final SimpleDateFormat formatoFecha;

    /**Formateador de precios con locale mexicano.*/
    private final NumberFormat formatoPrecio;

    /**
     * Construye un nuevo panel de historial con las dependencias inyectadas.
     * Inicializa todos los componentes de la interfaz y configura el formateador
     * de fecha y precio para mostrar informacion correctamente formateada.
     *
     * @param vista la vista principal de JavaFX para navegacion
     * @param persistencia el servicio de persistencia para acceso a datos
     * @throws IllegalArgumentException si vista o persistencia son nulos
     */
    public PanelHistorial(VistaJavaFX vista, ServicioPersistencia persistencia) {
        if (vista == null || persistencia == null) {
            throw new IllegalArgumentException("Vista y persistencia no pueden ser nulos");
        }

        this.vista = vista;
        this.persistencia = persistencia;
        this.root = new BorderPane();
        this.tablaTickets = new TableView<>();
        this.colNumero = new TableColumn<>("No. Ticket");
        this.colFecha = new TableColumn<>("Fecha");
        this.colCliente = new TableColumn<>("Cliente");
        this.colTotal = new TableColumn<>("Total");
        this.txtDetalles = new TextArea();
        this.btnCargar = new Button("Cargar");
        this.btnEliminar = new Button("Eliminar");
        this.btnVolver = new Button("Volver");
        this.formatoFecha = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        this.formatoPrecio = NumberFormat.getCurrencyInstance(new Locale("es", "MX"));
    }

    /**
     * Crea y retorna la escena completa del panel de historial.
     * Orquesta la construccion de todos los componentes invocando
     * metodos privados que configuran cada seccion de la interfaz.
     *
     * @return la escena configurada y lista para mostrar
     */
    public Scene crear() {
        return Optional.of(root)
            .map(configurarEncabezado())
            .map(configurarContenidoCentral())
            .map(configurarPieBotones())
            .map(this::aplicarEstilos)
            .map(r -> new Scene(r, 1000, 700))
            .orElseThrow(() -> new IllegalStateException("Error al crear la escena"));
    }

    /**
     * Configura el encabezado del panel con el titulo.
     * Aplica estilos al titulo y lo posiciona en la parte superior.
     *
     * @return funcion que configura el encabezado en el BorderPane
     */
    private Function<BorderPane, BorderPane> configurarEncabezado() {
        return pane -> {
            Label titulo = new Label("Historial de Ventas");
            titulo.getStyleClass().add("titulo-principal");
            HBox encabezado = new HBox(titulo);
            encabezado.getStyleClass().add("encabezado-panel");
            encabezado.setAlignment(Pos.CENTER);
            encabezado.setPadding(new Insets(20));
            pane.setTop(encabezado);
            return pane;
        };
    }

    /**
     * Configura el contenido central del panel con la tabla y area de detalles.
     * Utiliza un SplitPane para permitir redimensionamiento entre tabla y detalles.
     *
     * @return funcion que configura el contenido central en el BorderPane
     */
    private Function<BorderPane, BorderPane> configurarContenidoCentral() {
        return pane -> {
            configurarTabla();
            configurarAreaDetalles();

            SplitPane splitPane = new SplitPane();
            splitPane.getItems().addAll(
                crearContenedorTabla(),
                crearContenedorDetalles()
            );
            splitPane.setDividerPositions(0.5);

            pane.setCenter(splitPane);
            return pane;
        };
    }

    /**
     * Crea el contenedor de la tabla con titulo.
     * Empaqueta la tabla en un VBox con etiqueta descriptiva.
     *
     * @return contenedor VBox con la tabla de tickets
     */
    private VBox crearContenedorTabla() {
        Label lblTabla = new Label("Lista de Tickets");
        lblTabla.getStyleClass().add("label-titulo");

        VBox contenedor = new VBox(10, lblTabla, tablaTickets);
        contenedor.getStyleClass().add("contenedor-tabla");
        contenedor.setPadding(new Insets(10));
        VBox.setVgrow(tablaTickets, Priority.ALWAYS);

        return contenedor;
    }

    /**
     * Crea el contenedor del area de detalles con titulo.
     * Empaqueta el TextArea en un VBox con etiqueta descriptiva.
     *
     * @return contenedor VBox con el area de detalles
     */
    private VBox crearContenedorDetalles() {
        Label lblDetalles = new Label("Detalles del Ticket");
        lblDetalles.getStyleClass().add("label-titulo");

        VBox contenedor = new VBox(10, lblDetalles, txtDetalles);
        contenedor.getStyleClass().add("contenedor-detalles");
        contenedor.setPadding(new Insets(10));
        VBox.setVgrow(txtDetalles, Priority.ALWAYS);

        return contenedor;
    }

    /**
     * Configura el area de texto para mostrar detalles del ticket.
     * Establece propiedades de edicion, estilo y fuente monoespaciada.
     */
    private void configurarAreaDetalles() {
        txtDetalles.setEditable(false);
        txtDetalles.setWrapText(false);
        txtDetalles.getStyleClass().add("text-area-detalles");
        txtDetalles.setText("Seleccione un ticket para ver sus detalles...");
    }

    /**
     * Configura el pie del panel con los botones de accion.
     * Aplica iconos a los botones y los organiza en un contenedor horizontal.
     *
     * @return funcion que configura el pie en el BorderPane
     */
    private Function<BorderPane, BorderPane> configurarPieBotones() {
        return pane -> {
            configurarBotones();

            HBox contenedorBotones = new HBox(15, btnCargar, btnEliminar, btnVolver);
            contenedorBotones.getStyleClass().add("contenedor-botones");
            contenedorBotones.setAlignment(Pos.CENTER);
            contenedorBotones.setPadding(new Insets(20));

            pane.setBottom(contenedorBotones);
            return pane;
        };
    }

    /**
     * Configura la tabla de tickets con sus columnas y comportamiento.
     * Establece las factories de valores para cada columna,
     * configura el listener de seleccion y carga los datos iniciales.
     */
    private void configurarTabla() {
        colNumero.setCellValueFactory(cellData ->
            new javafx.beans.property.SimpleObjectProperty<>(
                cellData.getValue().obtenerNumeroTicket()
            )
        );

        colFecha.setCellValueFactory(cellData ->
            new javafx.beans.property.SimpleStringProperty(
                formatoFecha.format(cellData.getValue().obtenerFecha())
            )
        );

        colCliente.setCellValueFactory(cellData ->
            new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().obtenerCliente()
            )
        );

        colTotal.setCellValueFactory(cellData ->
            new javafx.beans.property.SimpleObjectProperty<>(
                cellData.getValue().obtenerPrecioTotal()
            )
        );

        colTotal.setCellFactory(columna -> new javafx.scene.control.TableCell<Ticket, Double>() {
            @Override
            protected void updateItem(Double precio, boolean empty) {
                super.updateItem(precio, empty);
                setText(empty || precio == null ? null : formatoPrecio.format(precio));
            }
        });

        colNumero.setPrefWidth(100);
        colFecha.setPrefWidth(150);
        colCliente.setPrefWidth(200);
        colTotal.setPrefWidth(120);

        tablaTickets.getColumns().addAll(colNumero, colFecha, colCliente, colTotal);
        tablaTickets.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tablaTickets.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> Optional.ofNullable(newValue)
                .ifPresent(this::mostrarDetallesTicket)
        );

        cargarHistorial();
    }

    /**
     * Configura los botones con sus iconos y manejadores de eventos.
     * Establece iconos vectoriales de FontAwesome y conecta los eventos
     * a las acciones correspondientes.
     */
    private void configurarBotones() {
        btnCargar.setGraphic(new FontIcon(FontAwesomeSolid.SYNC_ALT));
        btnCargar.getStyleClass().add("button-primary");
        btnCargar.setOnAction(e -> ejecutarConNotificacion(
            this::cargarHistorial,
            "Historial actualizado correctamente",
            "Error al cargar el historial"
        ));

        btnEliminar.setGraphic(new FontIcon(FontAwesomeSolid.TRASH));
        btnEliminar.getStyleClass().add("button-danger");
        btnEliminar.setOnAction(e -> obtenerTicketSeleccionado()
            .ifPresentOrElse(
                this::confirmarYEliminarTicket,
                () -> mostrarAdvertencia("Debe seleccionar un ticket para eliminar")
            )
        );

        btnVolver.setGraphic(new FontIcon(FontAwesomeSolid.ARROW_LEFT));
        btnVolver.getStyleClass().add("button-secondary");
        btnVolver.setOnAction(e -> vista.cambiarEscena(null));
    }

    /**
     * Carga el historial de tickets desde la base de datos.
     * Recupera todos los tickets guardados mediante el servicio de persistencia
     * y los muestra en la tabla usando una lista observable.
     */
    private void cargarHistorial() {
        Optional.ofNullable(persistencia.cargarTickets())
            .map(FXCollections::observableArrayList)
            .ifPresentOrElse(
                tablaTickets::setItems,
                () -> tablaTickets.setItems(FXCollections.observableArrayList())
            );
    }

    /**
     * Muestra los detalles completos de un ticket en el area de texto.
     * Transforma el ticket a su representacion textual completa.
     *
     * @param ticket el ticket cuyos detalles mostrar
     */
    private void mostrarDetallesTicket(Ticket ticket) {
        Optional.ofNullable(ticket)
            .map(Ticket::generarTicket)
            .ifPresent(txtDetalles::setText);
    }

    /**
     * Obtiene el ticket actualmente seleccionado en la tabla.
     * Encapsula la seleccion en un Optional para manejo seguro de valores nulos.
     *
     * @return Optional conteniendo el ticket seleccionado o vacio si no hay seleccion
     */
    private Optional<Ticket> obtenerTicketSeleccionado() {
        return Optional.ofNullable(tablaTickets.getSelectionModel().getSelectedItem());
    }

    /**
     * Confirma con el usuario y elimina el ticket seleccionado.
     * Muestra un dialogo de confirmacion antes de proceder con la eliminacion.
     *
     * @param ticket el ticket a eliminar
     */
    private void confirmarYEliminarTicket(Ticket ticket) {
        crearDialogoConfirmacion(
            "Confirmar eliminacion",
            String.format("Esta seguro que desea eliminar el ticket #%d?", ticket.obtenerNumeroTicket())
        ).filter(respuesta -> respuesta == ButtonType.OK)
         .ifPresent(respuesta -> eliminarTicket(ticket));
    }

    /**
     * Elimina un ticket de la base de datos y actualiza la interfaz.
     * Muestra notificaciones de exito o error segun el resultado de la operacion.
     *
     * @param ticket el ticket a eliminar
     */
    private void eliminarTicket(Ticket ticket) {
        if (persistencia.eliminarTicket(ticket.obtenerNumeroTicket())) {
            cargarHistorial();
            txtDetalles.clear();
            txtDetalles.setText("Seleccione un ticket para ver sus detalles...");
            mostrarExito("Ticket eliminado correctamente");
        } else {
            mostrarError("No se pudo eliminar el ticket");
        }
    }

    /**
     * Ejecuta una operacion y muestra notificacion segun el resultado.
     * Patron de funcion de orden superior para encapsular manejo de errores.
     *
     * @param operacion la operacion a ejecutar
     * @param mensajeExito mensaje a mostrar si la operacion es exitosa
     * @param mensajeError mensaje a mostrar si la operacion falla
     */
    private void ejecutarConNotificacion(Runnable operacion, String mensajeExito, String mensajeError) {
        try {
            operacion.run();
            mostrarExito(mensajeExito);
        } catch (Exception e) {
            mostrarError(mensajeError + ": " + e.getMessage());
        }
    }

    /**
     * Crea un dialogo de confirmacion con titulo y mensaje especificados.
     * Utiliza ControlsFX Alert para dialogos nativos mejorados.
     *
     * @param titulo el titulo del dialogo
     * @param mensaje el mensaje del dialogo
     * @return Optional conteniendo la respuesta del usuario
     */
    private Optional<ButtonType> crearDialogoConfirmacion(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.initModality(Modality.APPLICATION_MODAL);
        return alert.showAndWait();
    }

    /**
     * Muestra una notificacion de exito al usuario.
     * Utiliza ControlsFX Notifications para notificaciones elegantes.
     *
     * @param mensaje el mensaje de exito a mostrar
     */
    private void mostrarExito(String mensaje) {
        Notifications.create()
            .title("Exito")
            .text(mensaje)
            .showInformation();
    }

    /**
     * Muestra una notificacion de error al usuario.
     * Utiliza ControlsFX Notifications para notificaciones elegantes.
     *
     * @param mensaje el mensaje de error a mostrar
     */
    private void mostrarError(String mensaje) {
        Notifications.create()
            .title("Error")
            .text(mensaje)
            .showError();
    }

    /**
     * Muestra una notificacion de advertencia al usuario.
     * Utiliza ControlsFX Notifications para notificaciones elegantes.
     *
     * @param mensaje el mensaje de advertencia a mostrar
     */
    private void mostrarAdvertencia(String mensaje) {
        Notifications.create()
            .title("Advertencia")
            .text(mensaje)
            .showWarning();
    }

    /**
     * Aplica estilos globales al panel.
     * Carga el archivo CSS de tema oscuro y lo aplica al contenedor principal.
     *
     * @param pane el panel al que aplicar estilos
     * @return el panel con estilos aplicados
     */
    private BorderPane aplicarEstilos(BorderPane pane) {
        try {
            String cssPath = getClass().getResource("/css/theme-dark.css").toExternalForm();
            pane.getStylesheets().add(cssPath);
        } catch (Exception e) {
            System.err.println("Error al cargar el archivo CSS: " + e.getMessage());
        }
        return pane;
    }
}
