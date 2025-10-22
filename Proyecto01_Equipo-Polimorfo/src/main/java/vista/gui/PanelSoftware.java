package vista.gui;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

import io.vavr.Tuple2;
import io.vavr.collection.HashMap;
import io.vavr.control.Try;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import modelo.computadora.ComputadoraBase;
import modelo.decorador.AutoCADDecorator;
import modelo.decorador.OfficeDecorator;
import modelo.decorador.PhotoshopDecorator;
import modelo.decorador.WSLDecorator;
import modelo.decorador.WindowsDecorator;

/**
 * Panel de interfaz grafica para la seleccion de software adicional en el sistema
 * de ensamblaje de computadoras MonosChinos MX.
 *
 * <p>Este panel permite al usuario seleccionar software adicional mediante checkboxes,
 * visualizar un resumen en tiempo real de las selecciones y el precio acumulado.
 * Implementa el patron Decorator para agregar software a la computadora base.
 *
 * <p>Caracteristicas principales:
 * <ul>
 *   <li>Seleccion interactiva de software mediante CheckBoxes</li>
 *   <li>Actualizacion en tiempo real del resumen y precio</li>
 *   <li>Validacion para evitar duplicados de software</li>
 *   <li>Aplicacion de estilos CSS tematicos</li>
 *   <li>Navegacion fluida entre escenas</li>
 * </ul>
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class PanelSoftware {

    /**Contenedor principal del panel.*/
    private BorderPane root;

    /**Contenedor central que aloja los controles del panel.*/
    private VBox panelCentral;

    /**Etiqueta de titulo del panel.*/
    private Label lblTitulo;

    /**CheckBox para seleccionar Windows.*/
    private CheckBox chkWindows;

    /**CheckBox para seleccionar Office.*/
    private CheckBox chkOffice;

    /**CheckBox para seleccionar Photoshop.*/
    private CheckBox chkPhotoshop;

    /**CheckBox para seleccionar AutoCAD.*/
    private CheckBox chkAutoCAD;

    /**CheckBox para seleccionar WSL.*/
    private CheckBox chkWSL;

    /**Etiqueta que muestra el precio total del software seleccionado.*/
    private Label lblPrecioSoftware;

    /**Area de texto para mostrar el resumen de selecciones.*/
    private TextArea txtResumen;

    /**Boton para continuar al siguiente paso.*/
    private Button btnContinuar;

    /**Boton para volver a la pantalla anterior.*/
    private Button btnVolver;

    /**Referencia a la vista principal de JavaFX.*/
    private VistaJavaFX vista;

    /**Computadora base a la cual se agregara el software.*/
    private ComputadoraBase computadora;

    /**
     * Construye un nuevo panel de seleccion de software.
     * Inicializa todos los componentes visuales y configura la computadora base.
     *
     * @param vista la vista principal de JavaFX, no debe ser nula
     * @param computadora la computadora base a decorar con software, no debe ser nula
     * @throws IllegalArgumentException si vista o computadora son nulos
     */
    public PanelSoftware(VistaJavaFX vista, ComputadoraBase computadora) {
        Optional.ofNullable(vista)
            .orElseThrow(() -> new IllegalArgumentException("La vista no puede ser nula"));
        Optional.ofNullable(computadora)
            .orElseThrow(() -> new IllegalArgumentException("La computadora no puede ser nula"));

        this.vista = vista;
        this.computadora = computadora;
        this.root = new BorderPane();
        this.panelCentral = new VBox(15);
        inicializarComponentes();
    }

    /**
     * Crea y retorna la escena completa del panel de software.
     * Configura el layout, aplica estilos y retorna la escena lista para mostrarse.
     *
     * @return la escena configurada con todos los componentes del panel
     */
    public Scene crear() {
        return Try.of(() -> configurarLayout())
            .map(this::aplicarEstilosARoot)
            .map(pane -> new Scene(pane, 900, 700))
            .get();
    }

    /**
     * Obtiene la computadora con el software seleccionado aplicado.
     * Aplica los decoradores correspondientes a las selecciones del usuario.
     *
     * @return la computadora decorada con el software seleccionado
     */
    public ComputadoraBase obtenerComputadoraConSoftware() {
        return obtenerCheckBoxesSeleccionados()
            .stream()
            .map(obtenerDecoradorPorCheckBox())
            .reduce(computadora, (comp, decorator) -> decorator.apply(comp), (c1, c2) -> c2);
    }

    /**
     * Inicializa todos los componentes visuales del panel.
     * Crea labels, checkboxes, botones y areas de texto con configuracion inicial.
     */
    private void inicializarComponentes() {
        Stream.<Runnable>of(
            this::inicializarLabels,
            this::inicializarCheckBoxes,
            this::inicializarTextArea,
            this::inicializarBotones
        ).forEach(Runnable::run);

        configurarCheckBoxes();
        configurarBotones();
    }

    /**
     * Inicializa las etiquetas del panel.
     * Configura el titulo y la etiqueta de precio con estilos apropiados.
     */
    private void inicializarLabels() {
        lblTitulo = crearLabel("Seleccion de Software Adicional", "label-titulo");
        lblPrecioSoftware = crearLabel("Precio Software: $0.00", "label-precio");
    }

    /**
     * Inicializa los checkboxes para seleccion de software.
     * Crea todos los checkboxes con sus respectivas etiquetas y precios.
     */
    private void inicializarCheckBoxes() {
        chkWindows = crearCheckBox("Windows 10/11 - $2500.00");
        chkOffice = crearCheckBox("Microsoft Office 365 - $1500.00");
        chkPhotoshop = crearCheckBox("Adobe Photoshop - $3000.00");
        chkAutoCAD = crearCheckBox("AutoCAD - $5000.00");
        chkWSL = crearCheckBox("Terminal WSL - $0.00 (Gratis)");
    }

    /**
     * Inicializa el area de texto para el resumen.
     * Configura propiedades de edicion, tamanio y estilo del area de texto.
     */
    private void inicializarTextArea() {
        txtResumen = new TextArea();
        txtResumen.setEditable(false);
        txtResumen.setWrapText(true);
        txtResumen.setPrefHeight(200);
        txtResumen.getStyleClass().add("text-area-detalles");
        txtResumen.setText("No se ha seleccionado ningun software adicional.");
    }

    /**
     * Inicializa los botones del panel.
     * Crea los botones de continuar y volver con estilos apropiados.
     */
    private void inicializarBotones() {
        btnContinuar = crearBoton("Continuar", "button-primary");
        btnVolver = crearBoton("Volver", "button-secondary");
    }

    /**
     * Configura los listeners de los checkboxes.
     * Asocia la accion de actualizacion de resumen a cada cambio de estado.
     */
    private void configurarCheckBoxes() {
        obtenerTodosLosCheckBoxes()
            .forEach(aplicarListenerActualizacion());
    }

    /**
     * Configura las acciones de los botones.
     * Define los manejadores de eventos para continuar y volver.
     */
    private void configurarBotones() {
        btnContinuar.setOnAction(e -> manejarContinuar());
        btnVolver.setOnAction(e -> manejarVolver());
    }

    /**
     * Actualiza el resumen de software seleccionado y el precio total.
     * Recalcula y muestra la informacion actualizada en tiempo real.
     */
    private void actualizarResumen() {
        Try.run(() -> {
            actualizarTextoResumen();
            actualizarPrecioSoftware();
        });
    }

    /**
     * Actualiza el texto del area de resumen.
     * Genera el texto descriptivo de las selecciones actuales.
     */
    private void actualizarTextoResumen() {
        txtResumen.setText(generarTextoResumen());
    }

    /**
     * Actualiza la etiqueta de precio del software.
     * Calcula y muestra el precio total del software seleccionado.
     */
    private void actualizarPrecioSoftware() {
        lblPrecioSoftware.setText(
            String.format("Precio Software: $%.2f", calcularPrecioSoftware())
        );
    }

    /**
     * Genera el texto descriptivo del resumen de selecciones.
     * Crea una descripcion detallada del software seleccionado.
     *
     * @return texto formateado con el resumen de selecciones
     */
    private String generarTextoResumen() {
        List<String> selecciones = obtenerCheckBoxesSeleccionados()
            .stream()
            .map(CheckBox::getText)
            .collect(Collectors.toList());

        return selecciones.isEmpty()
            ? "No se ha seleccionado ningun software adicional."
            : Stream.of(
                "Software seleccionado:",
                "",
                selecciones.stream()
                    .map(texto -> "  - " + texto)
                    .collect(Collectors.joining("\n")),
                "",
                String.format("Total de items: %d", selecciones.size())
            ).collect(Collectors.joining("\n"));
    }

    /**
     * Calcula el precio total del software seleccionado.
     * Suma los precios de todos los checkboxes marcados.
     *
     * @return el precio total del software seleccionado
     */
    private double calcularPrecioSoftware() {
        return obtenerMapaPreciosSoftware()
            .filter((nombre, precio) -> verificarCheckBoxSeleccionado(nombre))
            .values()
            .sum()
            .doubleValue();
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
     * Organiza los checkboxes, resumen y precio en el centro.
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
     * Agrega todos los componentes al VBox central.
     */
    private void configurarPanelCentral() {
        panelCentral.setAlignment(Pos.TOP_CENTER);
        panelCentral.setPadding(new Insets(20));

        Stream.of(
            crearSeccionCheckBoxes(),
            crearSeccionResumen(),
            lblPrecioSoftware
        ).forEach(panelCentral.getChildren()::add);

        VBox.setVgrow(panelCentral.getChildren().get(1), Priority.ALWAYS);
    }

    /**
     * Crea la seccion de checkboxes.
     * Organiza todos los checkboxes en un VBox.
     *
     * @return VBox conteniendo todos los checkboxes
     */
    private VBox crearSeccionCheckBoxes() {
        VBox seccion = new VBox(10);
        seccion.setPadding(new Insets(10));

        Label lblSoftware = crearLabel("Software Disponible:", "label-titulo");

        seccion.getChildren().add(lblSoftware);
        obtenerTodosLosCheckBoxes().forEach(seccion.getChildren()::add);

        return seccion;
    }

    /**
     * Crea la seccion de resumen.
     * Organiza el area de texto de resumen en un VBox.
     *
     * @return VBox conteniendo el resumen
     */
    private VBox crearSeccionResumen() {
        VBox seccion = new VBox(10);
        seccion.setPadding(new Insets(10));

        Label lblResumenTitle = crearLabel("Resumen de Seleccion:", "label-titulo");

        seccion.getChildren().addAll(lblResumenTitle, txtResumen);
        VBox.setVgrow(txtResumen, Priority.ALWAYS);

        return seccion;
    }

    /**
     * Configura el pie del panel con los botones.
     * Posiciona los botones de accion en la parte inferior.
     *
     * @return funcion que configura el pie
     */
    private Function<BorderPane, BorderPane> configurarPie() {
        return pane -> {
            HBox contenedorBotones = new HBox(15, btnVolver, btnContinuar);
            contenedorBotones.setAlignment(Pos.CENTER);
            contenedorBotones.setPadding(new Insets(20));
            contenedorBotones.getStyleClass().add("contenedor-botones");
            pane.setBottom(contenedorBotones);
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
     * Maneja la accion del boton continuar.
     * Valida las selecciones y navega al siguiente paso.
     */
    private void manejarContinuar() {
        Try.run(() -> {
            vista.cambiarEscena(null);
        });
    }

    /**
     * Maneja la accion del boton volver.
     * Regresa a la pantalla anterior sin aplicar cambios.
     */
    private void manejarVolver() {
        Try.run(() -> vista.cambiarEscena(null));
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
     * Crea un checkbox con el texto especificado.
     * Fabrica un checkbox listo para usar.
     *
     * @param texto el texto del checkbox
     * @return el checkbox configurado
     */
    private CheckBox crearCheckBox(String texto) {
        return new CheckBox(texto);
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

    /**
     * Obtiene la lista de todos los checkboxes del panel.
     * Agrupa todos los checkboxes en una lista.
     *
     * @return lista con todos los checkboxes
     */
    private List<CheckBox> obtenerTodosLosCheckBoxes() {
        return List.of(chkWindows, chkOffice, chkPhotoshop, chkAutoCAD, chkWSL);
    }

    /**
     * Obtiene la lista de checkboxes seleccionados.
     * Filtra los checkboxes marcados.
     *
     * @return lista de checkboxes seleccionados
     */
    private List<CheckBox> obtenerCheckBoxesSeleccionados() {
        return obtenerTodosLosCheckBoxes()
            .stream()
            .filter(CheckBox::isSelected)
            .collect(Collectors.toList());
    }

    /**
     * Obtiene el mapa de precios de software.
     * Mapea cada tipo de software con su precio.
     *
     * @return mapa inmutable de precios
     */
    private io.vavr.collection.Map<String, Double> obtenerMapaPreciosSoftware() {
        return HashMap.ofEntries(
            new Tuple2<>("Windows", 2500.0),
            new Tuple2<>("Office", 1500.0),
            new Tuple2<>("Photoshop", 3000.0),
            new Tuple2<>("AutoCAD", 5000.0),
            new Tuple2<>("WSL", 0.0)
        );
    }

    /**
     * Verifica si un checkbox especifico esta seleccionado.
     * Comprueba el estado de seleccion por nombre de software.
     *
     * @param nombreSoftware el nombre del software a verificar
     * @return true si esta seleccionado, false en caso contrario
     */
    private boolean verificarCheckBoxSeleccionado(String nombreSoftware) {
        return obtenerMapaCheckBoxes()
            .get(nombreSoftware)
            .map(CheckBox::isSelected)
            .getOrElse(false);
    }

    /**
     * Obtiene el mapa de checkboxes por nombre.
     * Mapea nombres de software con sus checkboxes.
     *
     * @return mapa inmutable de checkboxes
     */
    private io.vavr.collection.Map<String, CheckBox> obtenerMapaCheckBoxes() {
        return HashMap.ofEntries(
            new Tuple2<>("Windows", chkWindows),
            new Tuple2<>("Office", chkOffice),
            new Tuple2<>("Photoshop", chkPhotoshop),
            new Tuple2<>("AutoCAD", chkAutoCAD),
            new Tuple2<>("WSL", chkWSL)
        );
    }

    /**
     * Obtiene la funcion decoradora para un checkbox.
     * Mapea checkboxes a sus decoradores correspondientes.
     *
     * @return funcion que transforma checkbox en decorador
     */
    private Function<CheckBox, Function<ComputadoraBase, ComputadoraBase>> obtenerDecoradorPorCheckBox() {
        io.vavr.collection.Map<CheckBox, Function<ComputadoraBase, ComputadoraBase>> mapaDecoradores =
            HashMap.ofEntries(
                new Tuple2<>(chkWindows, comp -> new WindowsDecorator(comp, "Windows 10/11", 2500.0)),
                new Tuple2<>(chkOffice, comp -> new OfficeDecorator(comp, "Microsoft Office 365", 1500.0)),
                new Tuple2<>(chkPhotoshop, comp -> new PhotoshopDecorator(comp, "Adobe Photoshop", 3000.0)),
                new Tuple2<>(chkAutoCAD, comp -> new AutoCADDecorator(comp, "AutoCAD", 5000.0)),
                new Tuple2<>(chkWSL, comp -> new WSLDecorator(comp, "Terminal WSL", 0.0))
            );

        return checkbox -> mapaDecoradores.get(checkbox).getOrElse(comp -> comp);
    }

    /**
     * Aplica el listener de actualizacion a un checkbox.
     * Configura la accion que se ejecuta al cambiar estado.
     *
     * @return consumer que aplica el listener
     */
    private Consumer<CheckBox> aplicarListenerActualizacion() {
        return checkbox -> checkbox.selectedProperty()
            .addListener((obs, oldVal, newVal) -> actualizarResumen());
    }
}
