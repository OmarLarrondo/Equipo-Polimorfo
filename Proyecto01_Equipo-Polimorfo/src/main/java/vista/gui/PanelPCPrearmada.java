package vista.gui;

import io.vavr.control.Try;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import modelo.componente.ComponentePC;
import modelo.inventario.Inventario;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional; 
/**
 * Panel de la interfaz grafica para la seleccion de computadoras prearmadas.
 * Permite al usuario visualizar y seleccionar entre diferentes configuraciones
 * predefinidas de PCs obtenidas del inventario del sistema.
 *
 * <p>Este panel presenta un ComboBox con los tipos de PC prearmadas disponibles
 * (Gama Baja, Gama Media, Gama Alta), muestra los detalles de cada configuracion
 * incluyendo sus componentes y precio total, y permite al usuario confirmar su
 * seleccion para continuar con el proceso de compra.
 *
 * <p>Las configuraciones se cargan dinamicamente desde el Inventario singleton,
 * garantizando que siempre se muestren las configuraciones actualizadas y
 * disponibles en el sistema.
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class PanelPCPrearmada {
    /**Contenedor principal del panel con layout vertical.*/
    private VBox root;

    /**Etiqueta de titulo del panel.*/
    private Label lblTitulo;

    /**ComboBox para seleccionar el tipo de PC prearmada.*/
    private ComboBox<String> comboTipoPC;

    /**Area de texto para mostrar los detalles de la configuracion seleccionada.*/
    private TextArea txtDetallesPC;

    /**Etiqueta para mostrar el precio total de la PC seleccionada.*/
    private Label lblPrecio;

    /**Boton para confirmar la seleccion de la PC prearmada.*/
    private Button btnSeleccionar;

    /**Boton para volver al panel anterior.*/
    private Button btnVolver;

    /**Referencia a la vista principal de JavaFX para navegacion.*/
    private VistaJavaFX vista;

    /**Mapa de configuraciones prearmadas cargadas desde el inventario.*/
    private Map<String, List<ComponentePC>> configuraciones = new HashMap<>();

    /**
     * Construye un nuevo panel de PC prearmadas.
     * Valida que la vista no sea nula e inicializa el contenedor principal.
     *
     * @param vista la vista principal de JavaFX, no debe ser nula
     * @throws IllegalArgumentException si la vista es nula
     */
    public PanelPCPrearmada(VistaJavaFX vista) {
        validarVista(vista);
        this.vista = vista;
        root = new VBox(15);
    }

    /**
     * Crea y retorna la escena completa del panel de PC prearmadas.
     * Inicializa todos los componentes, carga las configuraciones del inventario,
     * configura el layout, establece los manejadores de eventos y aplica estilos CSS.
     *
     * @return la escena configurada con dimensiones de 900x700 pixeles y tema oscuro aplicado
     */
    public Scene crear(){
        inicializarComponentes();
        cargarConfiguraciones();
        configurarLayout();
        configurarBotones();
        aplicarEstilos();

        Scene scene = new Scene(root, 900, 700);
        scene.getStylesheets().add(getClass().getResource("/css/theme-dark.css").toExternalForm());
        return scene;
    }

    /**
     * Inicializa todos los componentes visuales del panel.
     * Crea las etiquetas, el ComboBox, el area de texto y los botones
     * con sus estilos correspondientes.
     */
    private void inicializarComponentes(){
        lblTitulo = crearLabel("Selecciona tu PC ¡prearmada!","label-titulo");
        comboTipoPC = new ComboBox<>();
        txtDetallesPC = new TextArea();
        txtDetallesPC.setEditable(false);
        txtDetallesPC.setWrapText(true);
        txtDetallesPC.setPrefRowCount(15);
        lblPrecio = crearLabel("Precio: $0 MXN", "label-primary");
        btnSeleccionar = crearBoton("Seleccionar","button-primary");
        btnVolver = crearBoton("Volver", "button-secondary");
    }

    /**
     * Configura el layout del panel organizando los componentes visuales.
     * Establece la alineacion, padding y agrega todos los componentes al contenedor principal.
     */
    private void configurarLayout(){
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(50));
        root.getChildren().addAll(lblTitulo, comboTipoPC, txtDetallesPC, lblPrecio,
                                    btnSeleccionar, btnVolver);
    }

    /**
     * Crea una etiqueta con texto y clase CSS especificados.
     *
     * @param texto el texto a mostrar en la etiqueta
     * @param styleClass la clase CSS a aplicar
     * @return la etiqueta creada y configurada
     */
    private Label crearLabel(String texto, String styleClass) {
        return Optional.of(new Label(texto))
            .map(label -> {
                label.getStyleClass().add(styleClass);
                return label;
            })
            .orElseThrow(() -> new IllegalStateException("Error al crear etiqueta"));
    }

    /**
     * Crea un boton con texto y clase CSS especificados.
     *
     * @param texto el texto a mostrar en el boton
     * @param styleClass la clase CSS a aplicar
     * @return el boton creado y configurado
     */
    private Button crearBoton(String texto, String styleClass) {
        return Optional.of(new Button(texto))
            .map(boton -> {
                boton.getStyleClass().add(styleClass);
                return boton;
            })
            .orElseThrow(() -> new IllegalStateException("Error al crear boton"));
    }

    /**
     * Carga las configuraciones de PC prearmadas desde el inventario del sistema.
     * Obtiene las configuraciones predefinidas (Gama Baja, Gama Media, Gama Alta)
     * del Inventario singleton y las almacena en el mapa local. Luego actualiza
     * el ComboBox con los nombres de las configuraciones disponibles.
     *
     * <p>Captura y maneja cualquier error que pueda ocurrir durante la carga
     * de las configuraciones, mostrando un dialogo de error al usuario si es necesario.
     */
    private void cargarConfiguraciones() {
        Try.run(() -> {
            configuraciones.clear();

            Map<String, List<ComponentePC>> prearmadasInventario =
                Inventario.getInstance().obtenerConfiguracionesPrearmadas();

            Optional.ofNullable(prearmadasInventario)
                .ifPresent(configuraciones::putAll);

            comboTipoPC.getItems().clear();
            comboTipoPC.getItems().addAll(configuraciones.keySet());
        }).onFailure(error -> {
            System.err.println("Error al cargar configuraciones: " + error.getMessage());
            mostrarDialogoError("Error", "No se pudieron cargar las configuraciones prearmadas");
        });
    }


    /**
     * Muestra los detalles de una configuracion de PC prearmada seleccionada.
     * Obtiene los componentes de la configuracion especificada, construye un texto
     * detallado con la informacion de cada componente (marca, nombre, precio) y
     * calcula el precio total. Actualiza el area de texto y la etiqueta de precio
     * con la informacion correspondiente.
     *
     * @param tipo el nombre del tipo de PC prearmada (ej: "Gama Baja", "Gama Media", "Gama Alta")
     */
    private void mostrarDetallesPC(String tipo) {
        List<ComponentePC> componentes = configuraciones.get(tipo);

        if (componentes == null || componentes.isEmpty()) {
            txtDetallesPC.setText("No hay detalles disponibles para la PC " + tipo);
            lblPrecio.setText("Precio: $0 MXN");
            return;
        }

        StringBuilder detalles = new StringBuilder("Detalles de la PC " + tipo + ":\n\n");
        double precioTotal = 0;

        for (ComponentePC c : componentes) {
            detalles.append(String.format("- %s: %s | Precio: $%.2f MXN\n",
                c.obtenerTipo(),
                c.obtenerNombre(),
                c.obtenerPrecio()));
            precioTotal += c.obtenerPrecio();
        }

        txtDetallesPC.setText(detalles.toString());
        lblPrecio.setText(String.format("Precio Total: $%.2f MXN", precioTotal));
    }
    /**
     * Configura los manejadores de eventos para los componentes interactivos del panel.
     * Establece las acciones para el ComboBox de seleccion, el boton de confirmacion
     * y el boton de volver, incluyendo validaciones y manejo de errores.
     */
    private void configurarBotones(){
        comboTipoPC.setOnAction(e ->
            Optional.ofNullable(comboTipoPC.getValue())
                .ifPresent(this::mostrarDetallesPC)
        );

        btnVolver.setOnAction(e ->
            Try.run(() -> {
                VentanaPrincipal panelAnterior = new VentanaPrincipal(vista);
                vista.cambiarEscena(panelAnterior.crear());
            }).onFailure(error ->
                mostrarDialogoError("Error", "No se pudo volver a la pantalla anterior")
            )
        );

        btnSeleccionar.setOnAction(e ->
            Optional.ofNullable(comboTipoPC.getValue())
                .map(configuraciones::get)
                .filter(componentes -> !componentes.isEmpty())
                .ifPresentOrElse(
                    componentesSeleccionados -> Try.run(() -> {
                        PanelComponentes panel = new PanelComponentes(vista, componentesSeleccionados);
                        vista.cambiarEscena(panel.crear());
                    }).onFailure(error ->
                        mostrarDialogoError("Error", "No se pudo crear el panel de componentes")
                    ),
                    () -> mostrarDialogoAdvertencia(
                        "Atencion",
                        "Debes seleccionar una PC prearmada antes de continuar."
                    )
                )
        );
    }

    /**
     * Aplica la clase CSS "root" al contenedor principal {@link VBox}.
     * Esto permite que el panel utilice los estilos definidos en el tema CSS.
     */
    private void aplicarEstilos() {
        root.getStyleClass().add("root");
    }

    /**
     * Valida que la vista de JavaFX no sea nula.
     * Lanza una excepcion si la vista proporcionada es nula.
     *
     * @param vista la vista a validar
     * @throws IllegalArgumentException si la vista es nula
     */
    private void validarVista(VistaJavaFX vista){
        Optional.ofNullable(vista)
            .orElseThrow(() -> new IllegalArgumentException("La vista no puede ser nula."));
    }

    /**
     * Muestra un dialogo de error con titulo y mensaje especificados.
     * Maneja de forma segura cualquier error que pueda ocurrir durante
     * la creacion y visualizacion del dialogo.
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
            dialogo.showAndWait();
        }).onFailure(error ->
            System.err.println("Error al mostrar dialogo: " + error.getMessage())
        );
    }

    /**
     * Muestra un dialogo de advertencia con titulo y mensaje especificados.
     * Maneja de forma segura cualquier error que pueda ocurrir durante
     * la creacion y visualizacion del dialogo.
     *
     * @param titulo el titulo del dialogo de advertencia
     * @param mensaje el mensaje de advertencia a mostrar
     */
    private void mostrarDialogoAdvertencia(String titulo, String mensaje) {
        Try.run(() -> {
            Alert dialogo = new Alert(Alert.AlertType.WARNING);
            dialogo.setTitle(titulo);
            dialogo.setHeaderText(null);
            dialogo.setContentText(mensaje);
            dialogo.showAndWait();
        }).onFailure(error ->
            System.err.println("Error al mostrar dialogo: " + error.getMessage())
        );
    }
}
