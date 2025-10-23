package vista.gui;

import io.vavr.control.Try;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import modelo.componente.ComponentePC;
import modelo.componente.Disco;
import modelo.componente.JuegoDiscos;
import modelo.componente.JuegoRAMs;
import modelo.componente.RAM;
import modelo.computadora.ComputadoraBase;
import modelo.computadora.ComputadoraBasica;
import modelo.inventario.Inventario;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Panel de interfaz grafica para la seleccion y configuracion de componentes
 * de computadoras en el sistema MonosChinos MX.
 *
 * <p>Este panel permite al usuario seleccionar componentes individuales para
 * construir una computadora personalizada o modificar una configuracion prearmada.
 * Implementa un sistema interactivo de seleccion mediante ComboBoxes para componentes
 * unicos (CPU, GPU, MotherBoard, Gabinete, Fuente) y ListViews para componentes
 * multiples (RAM y Discos).
 *
 * <p>Caracteristicas principales:
 * <ul>
 *   <li>Seleccion de componentes mediante ComboBoxes cargados desde el Inventario</li>
 *   <li>Soporte para multiples modulos RAM (maximo 4)</li>
 *   <li>Soporte para multiples discos de almacenamiento</li>
 *   <li>Actualizacion en tiempo real del precio total</li>
 *   <li>Validacion de componentes obligatorios antes de continuar</li>
 *   <li>Navegacion fluida entre escenas</li>
 *   <li>Aplicacion de estilos CSS tematicos</li>
 * </ul>
 *
 * <p>El panel soporta dos modos de operacion:
 * <ul>
 *   <li>Modo Personalizado: el usuario selecciona todos los componentes desde cero</li>
 *   <li>Modo Prearmado: los componentes vienen pre-seleccionados desde una configuracion</li>
 * </ul>
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class PanelComponentes {

    /**Contenedor principal del panel con layout BorderPane.*/
    private BorderPane root;

    /**Panel lateral para controles y botones.*/
    private VBox panelLateral;

    /**Grid para organizar los ComboBoxes de componentes.*/
    private GridPane gridComponentes;

    /**Etiqueta de titulo del panel.*/
    private Label lblTitulo;

    /**Etiqueta que muestra el precio total de los componentes seleccionados.*/
    private Label lblPrecioTotal;

    /**ComboBox para seleccionar el procesador CPU.*/
    private ComboBox<ComponentePC> comboCPU;

    /**ComboBox para seleccionar la memoria RAM base.*/
    private ComboBox<ComponentePC> comboRAM;

    /**ComboBox para seleccionar la placa madre.*/
    private ComboBox<ComponentePC> comboMotherBoard;

    /**ComboBox para seleccionar la tarjeta grafica GPU.*/
    private ComboBox<ComponentePC> comboGPU;

    /**ComboBox para seleccionar el almacenamiento base.*/
    private ComboBox<ComponentePC> comboAlmacenamiento;

    /**ComboBox para seleccionar la fuente de alimentacion.*/
    private ComboBox<ComponentePC> comboFuente;

    /**ComboBox para seleccionar el gabinete.*/
    private ComboBox<ComponentePC> comboGabinete;

    /**Lista visual de modulos RAM seleccionados.*/
    private ListView<ComponentePC> listaRAMsSeleccionadas;

    /**Lista visual de discos seleccionados.*/
    private ListView<ComponentePC> listaDiscosSeleccionados;

    /**Boton para agregar un modulo RAM adicional.*/
    private Button btnAgregarRAM;

    /**Boton para agregar un disco adicional.*/
    private Button btnAgregarDisco;

    /**Boton para continuar al siguiente paso.*/
    private Button btnContinuar;

    /**Boton para volver a la pantalla anterior.*/
    private Button btnVolver;

    /**Referencia a la vista principal de JavaFX para navegacion.*/
    private VistaJavaFX vista;

    /**Lista de componentes iniciales para modo prearmado.*/
    private List<ComponentePC> componentesIniciales;

    /**
     * Construye un nuevo panel de componentes para PC personalizada.
     * Inicializa el panel sin componentes pre-seleccionados, permitiendo
     * al usuario construir una computadora desde cero.
     *
     * @param vista la vista principal de JavaFX, no debe ser nula
     * @throws IllegalArgumentException si la vista es nula
     */
    public PanelComponentes(VistaJavaFX vista) {
        validarVista(vista);
        this.vista = vista;
        this.componentesIniciales = new ArrayList<>();
        this.root = new BorderPane();
        this.panelLateral = new VBox(15);
        this.gridComponentes = new GridPane();
    }

    /**
     * Construye un nuevo panel de componentes para PC prearmada.
     * Inicializa el panel con componentes pre-seleccionados desde una
     * configuracion prearmada, permitiendo al usuario revisarlos o modificarlos.
     *
     * @param vista la vista principal de JavaFX, no debe ser nula
     * @param componentesPrearmados lista de componentes pre-seleccionados, no debe ser nula
     * @throws IllegalArgumentException si vista o componentesPrearmados son nulos
     */
    public PanelComponentes(VistaJavaFX vista, List<ComponentePC> componentesPrearmados) {
        validarVista(vista);
        Optional.ofNullable(componentesPrearmados)
            .orElseThrow(() -> new IllegalArgumentException("Los componentes no pueden ser nulos"));

        this.vista = vista;
        this.componentesIniciales = new ArrayList<>(componentesPrearmados);
        this.root = new BorderPane();
        this.panelLateral = new VBox(15);
        this.gridComponentes = new GridPane();
    }

    /**
     * Crea y retorna la escena completa del panel de componentes.
     * Inicializa todos los componentes visuales, carga los datos del inventario,
     * configura el layout, establece los manejadores de eventos y aplica estilos CSS.
     *
     * @return la escena configurada con dimensiones de 900x700 pixeles y tema oscuro aplicado
     */
    public Scene crear() {
        inicializarComponentes();
        cargarComponentes();
        preseleccionarComponentes();
        configurarComboBoxes();
        configurarBotones();
        configurarLayout();
        aplicarEstilos();
        actualizarPrecioTotal();

        Scene scene = new Scene(root, 900, 700);
        scene.getStylesheets().add(getClass().getResource("/css/theme-dark.css").toExternalForm());
        return scene;
    }

    /**
     * Obtiene la lista de componentes seleccionados por el usuario.
     * Recolecta todos los componentes desde los ComboBoxes y ListViews,
     * organizandolos en una lista unificada. Los componentes RAM y Disco
     * multiples se agrupan en componentes compuestos (JuegoRAMs y JuegoDiscos).
     *
     * @return lista de componentes seleccionados, incluyendo componentes compuestos
     */
    public List<ComponentePC> obtenerComponentesSeleccionados() {
        return Try.of(() -> {
            List<ComponentePC> componentes = new ArrayList<>();

            agregarComponenteDesdeCombo(comboCPU, componentes);
            agregarComponenteDesdeCombo(comboMotherBoard, componentes);
            agregarComponenteDesdeCombo(comboGPU, componentes);
            agregarComponenteDesdeCombo(comboFuente, componentes);
            agregarComponenteDesdeCombo(comboGabinete, componentes);

            agregarJuegoRAMs(componentes);
            agregarJuegoDiscos(componentes);

            return componentes;
        }).getOrElse(new ArrayList<>());
    }

    /**
     * Inicializa todos los componentes visuales del panel.
     * Crea las etiquetas, ComboBoxes, ListViews y botones con configuracion inicial.
     */
    private void inicializarComponentes() {
        Stream.<Runnable>of(
            this::inicializarLabels,
            this::inicializarComboBoxes,
            this::inicializarListViews,
            this::inicializarBotones
        ).forEach(Runnable::run);
    }

    /**
     * Inicializa las etiquetas del panel.
     * Crea el titulo y la etiqueta de precio con estilos apropiados.
     */
    private void inicializarLabels() {
        lblTitulo = crearLabel("Configura tu PC - Selecciona Componentes", "label-titulo");
        lblPrecioTotal = crearLabel("Precio Total: $0.00 MXN", "label-precio");
    }

    /**
     * Inicializa todos los ComboBoxes para seleccion de componentes.
     * Crea ComboBoxes para CPU, RAM, MotherBoard, GPU, Almacenamiento, Fuente y Gabinete.
     */
    private void inicializarComboBoxes() {
        comboCPU = crearComboBox();
        comboRAM = crearComboBox();
        comboMotherBoard = crearComboBox();
        comboGPU = crearComboBox();
        comboAlmacenamiento = crearComboBox();
        comboFuente = crearComboBox();
        comboGabinete = crearComboBox();
    }

    /**
     * Inicializa las listas visuales para componentes multiples.
     * Crea ListViews para RAMs y Discos seleccionados.
     */
    private void inicializarListViews() {
        listaRAMsSeleccionadas = crearListView();
        listaDiscosSeleccionados = crearListView();
    }

    /**
     * Inicializa los botones del panel.
     * Crea los botones de agregar RAM, agregar disco, continuar y volver.
     */
    private void inicializarBotones() {
        btnAgregarRAM = crearBoton("Agregar RAM", "button-secondary");
        btnAgregarDisco = crearBoton("Agregar Disco", "button-secondary");
        btnContinuar = crearBoton("Continuar", "button-primary");
        btnVolver = crearBoton("Volver", "button-secondary");
    }

    /**
     * Carga los componentes disponibles desde el inventario.
     * Obtiene las listas de componentes por tipo del Inventario singleton
     * y las carga en los ComboBoxes correspondientes.
     */
    private void cargarComponentes() {
        Try.run(() -> {
            Inventario inventario = Inventario.getInstance();

            cargarComponentesEnCombo(comboCPU, inventario.obtenerComponentesPorTipo("CPU"));
            cargarComponentesEnCombo(comboRAM, inventario.obtenerComponentesPorTipo("RAM"));
            cargarComponentesEnCombo(comboMotherBoard, inventario.obtenerComponentesPorTipo("MotherBoard"));
            cargarComponentesEnCombo(comboGPU, inventario.obtenerComponentesPorTipo("GPU"));
            cargarComponentesEnCombo(comboAlmacenamiento, inventario.obtenerComponentesPorTipo("Disco"));
            cargarComponentesEnCombo(comboFuente, inventario.obtenerComponentesPorTipo("FuenteAlimentacion"));
            cargarComponentesEnCombo(comboGabinete, inventario.obtenerComponentesPorTipo("Gabinete"));
        }).onFailure(error ->
            mostrarDialogoError("Error", "No se pudieron cargar los componentes del inventario")
        );
    }

    /**
     * Pre-selecciona componentes si vienen de una configuracion prearmada.
     * Busca y selecciona en los ComboBoxes los componentes que vienen en la lista inicial.
     */
    private void preseleccionarComponentes() {
        if (componentesIniciales.isEmpty()) {
            return;
        }

        componentesIniciales.forEach(componente ->
            seleccionarComponenteEnCombo(componente)
        );
    }

    /**
     * Selecciona un componente en el ComboBox correspondiente segun su tipo.
     * Busca el ComboBox apropiado para el tipo de componente y lo selecciona.
     *
     * @param componente el componente a seleccionar
     */
    private void seleccionarComponenteEnCombo(ComponentePC componente) {
        Try.run(() -> {
            String tipo = componente.obtenerTipo();

            obtenerComboBoxPorTipo(tipo)
                .ifPresent(combo -> seleccionarEnCombo(combo, componente));
        });
    }

    /**
     * Obtiene el ComboBox correspondiente a un tipo de componente.
     * Mapea el tipo de componente con su ComboBox asociado.
     *
     * @param tipo el tipo de componente
     * @return Optional conteniendo el ComboBox si existe
     */
    private Optional<ComboBox<ComponentePC>> obtenerComboBoxPorTipo(String tipo) {
        return Stream.of(
            tipo.equals("CPU") ? Optional.of(comboCPU) : Optional.<ComboBox<ComponentePC>>empty(),
            tipo.equals("RAM") ? Optional.of(comboRAM) : Optional.<ComboBox<ComponentePC>>empty(),
            tipo.equals("MotherBoard") ? Optional.of(comboMotherBoard) : Optional.<ComboBox<ComponentePC>>empty(),
            tipo.equals("GPU") ? Optional.of(comboGPU) : Optional.<ComboBox<ComponentePC>>empty(),
            tipo.equals("Disco") ? Optional.of(comboAlmacenamiento) : Optional.<ComboBox<ComponentePC>>empty(),
            tipo.equals("FuenteAlimentacion") ? Optional.of(comboFuente) : Optional.<ComboBox<ComponentePC>>empty(),
            tipo.equals("Gabinete") ? Optional.of(comboGabinete) : Optional.<ComboBox<ComponentePC>>empty()
        ).filter(Optional::isPresent)
         .map(Optional::get)
         .findFirst();
    }

    /**
     * Selecciona un componente especifico en un ComboBox.
     * Busca el componente en los items del ComboBox y lo selecciona.
     *
     * @param combo el ComboBox donde seleccionar
     * @param componente el componente a seleccionar
     */
    private void seleccionarEnCombo(ComboBox<ComponentePC> combo, ComponentePC componente) {
        combo.getItems().stream()
            .filter(item -> item.obtenerNombre().equals(componente.obtenerNombre()))
            .findFirst()
            .ifPresent(combo::setValue);
    }

    /**
     * Configura los listeners de los ComboBoxes.
     * Asocia la accion de actualizacion de precio a cada cambio de seleccion.
     */
    private void configurarComboBoxes() {
        Stream.of(comboCPU, comboRAM, comboMotherBoard, comboGPU,
                  comboAlmacenamiento, comboFuente, comboGabinete)
            .forEach(aplicarListenerActualizacionPrecio());
    }

    /**
     * Configura las acciones de los botones.
     * Define los manejadores de eventos para agregar RAM, agregar disco, continuar y volver.
     */
    private void configurarBotones() {
        btnAgregarRAM.setOnAction(e -> agregarRAM());
        btnAgregarDisco.setOnAction(e -> agregarDisco());
        btnContinuar.setOnAction(e -> manejarContinuar());
        btnVolver.setOnAction(e -> manejarVolver());
    }

    /**
     * Configura el layout completo del panel.
     * Organiza el grid de componentes, el panel lateral y los botones en el BorderPane.
     *
     * @return el BorderPane configurado
     */
    private BorderPane configurarLayout() {
        return Optional.of(root)
            .map(configurarCentro())
            .map(configurarLateral())
            .orElseThrow(() -> new IllegalStateException("Error al configurar layout"));
    }

    /**
     * Configura el area central del panel con el grid de componentes.
     * Organiza los ComboBoxes en un GridPane con etiquetas descriptivas.
     *
     * @return funcion que configura el centro del BorderPane
     */
    private Function<BorderPane, BorderPane> configurarCentro() {
        return pane -> {
            configurarGridComponentes();

            ScrollPane scroll = new ScrollPane(gridComponentes);
            scroll.setFitToWidth(true);
            scroll.setFitToHeight(true);

            pane.setCenter(scroll);
            return pane;
        };
    }

    /**
     * Configura el grid de componentes con todos los ComboBoxes.
     * Organiza los controles en filas con etiquetas y ComboBoxes.
     */
    private void configurarGridComponentes() {
        gridComponentes.setHgap(15);
        gridComponentes.setVgap(15);
        gridComponentes.setPadding(new Insets(20));
        gridComponentes.setAlignment(Pos.TOP_CENTER);

        agregarFilaComponente(0, "CPU (Procesador):", comboCPU);
        agregarFilaComponente(1, "MotherBoard (Placa Madre):", comboMotherBoard);
        agregarFilaComponente(2, "GPU (Tarjeta Grafica):", comboGPU);
        agregarFilaComponente(3, "RAM (Memoria Base):", comboRAM);
        agregarFilaComponente(4, "Almacenamiento Base:", comboAlmacenamiento);
        agregarFilaComponente(5, "Fuente de Alimentacion:", comboFuente);
        agregarFilaComponente(6, "Gabinete:", comboGabinete);

        VBox seccionRAM = crearSeccionRAMsAdicionales();
        gridComponentes.add(seccionRAM, 0, 7, 2, 1);

        VBox seccionDiscos = crearSeccionDiscosAdicionales();
        gridComponentes.add(seccionDiscos, 0, 8, 2, 1);
    }

    /**
     * Agrega una fila de componente al grid con etiqueta y ComboBox.
     * Organiza la etiqueta y el control en la fila especificada.
     *
     * @param fila el numero de fila donde agregar
     * @param textoLabel el texto de la etiqueta
     * @param combo el ComboBox a agregar
     */
    private void agregarFilaComponente(int fila, String textoLabel, ComboBox<ComponentePC> combo) {
        Label label = crearLabel(textoLabel, "label-primary");
        gridComponentes.add(label, 0, fila);
        gridComponentes.add(combo, 1, fila);
        GridPane.setHgrow(combo, Priority.ALWAYS);
    }

    /**
     * Crea la seccion de RAMs adicionales con lista y boton.
     * Organiza el ListView de RAMs seleccionadas con su boton de agregar.
     *
     * @return VBox conteniendo la seccion de RAMs
     */
    private VBox crearSeccionRAMsAdicionales() {
        VBox seccion = new VBox(10);
        seccion.setPadding(new Insets(10, 0, 10, 0));

        Label lblRAMsAdicionales = crearLabel("RAMs Adicionales (Maximo 4):", "label-primary");
        listaRAMsSeleccionadas.setPrefHeight(100);

        HBox contenedorBoton = new HBox(btnAgregarRAM);
        contenedorBoton.setAlignment(Pos.CENTER_LEFT);

        seccion.getChildren().addAll(lblRAMsAdicionales, listaRAMsSeleccionadas, contenedorBoton);
        return seccion;
    }

    /**
     * Crea la seccion de discos adicionales con lista y boton.
     * Organiza el ListView de discos seleccionados con su boton de agregar.
     *
     * @return VBox conteniendo la seccion de discos
     */
    private VBox crearSeccionDiscosAdicionales() {
        VBox seccion = new VBox(10);
        seccion.setPadding(new Insets(10, 0, 10, 0));

        Label lblDiscosAdicionales = crearLabel("Discos Adicionales:", "label-primary");
        listaDiscosSeleccionados.setPrefHeight(100);

        HBox contenedorBoton = new HBox(btnAgregarDisco);
        contenedorBoton.setAlignment(Pos.CENTER_LEFT);

        seccion.getChildren().addAll(lblDiscosAdicionales, listaDiscosSeleccionados, contenedorBoton);
        return seccion;
    }

    /**
     * Configura el panel lateral con titulo, precio y botones.
     * Organiza los controles de informacion y navegacion en el lateral derecho.
     *
     * @return funcion que configura el lateral del BorderPane
     */
    private Function<BorderPane, BorderPane> configurarLateral() {
        return pane -> {
            panelLateral.setAlignment(Pos.TOP_CENTER);
            panelLateral.setPadding(new Insets(20));
            panelLateral.setPrefWidth(300);

            panelLateral.getChildren().addAll(
                lblTitulo,
                lblPrecioTotal,
                btnContinuar,
                btnVolver
            );

            pane.setRight(panelLateral);
            return pane;
        };
    }

    /**
     * Agrega un modulo RAM adicional a la lista de RAMs seleccionadas.
     * Valida que no se exceda el maximo de 4 modulos y que haya una RAM seleccionada.
     */
    private void agregarRAM() {
        Try.run(() -> {
            if (listaRAMsSeleccionadas.getItems().size() >= 4) {
                mostrarDialogoAdvertencia("Limite Alcanzado",
                    "Solo puedes agregar hasta 4 modulos de RAM en total.");
                return;
            }

            Optional.ofNullable(comboRAM.getValue())
                .ifPresentOrElse(
                    ram -> {
                        listaRAMsSeleccionadas.getItems().add(ram);
                        actualizarPrecioTotal();
                    },
                    () -> mostrarDialogoAdvertencia("Sin Seleccion",
                        "Debes seleccionar una RAM del menu primero.")
                );
        });
    }

    /**
     * Agrega un disco adicional a la lista de discos seleccionados.
     * Valida que haya un disco seleccionado en el ComboBox.
     */
    private void agregarDisco() {
        Try.run(() -> {
            Optional.ofNullable(comboAlmacenamiento.getValue())
                .ifPresentOrElse(
                    disco -> {
                        listaDiscosSeleccionados.getItems().add(disco);
                        actualizarPrecioTotal();
                    },
                    () -> mostrarDialogoAdvertencia("Sin Seleccion",
                        "Debes seleccionar un disco del menu primero.")
                );
        });
    }

    /**
     * Calcula el precio total de todos los componentes seleccionados.
     * Suma los precios de los ComboBoxes y los componentes en las listas.
     *
     * @return el precio total de los componentes
     */
    private double calcularPrecioTotal() {
        return Try.of(() -> {
            double total = 0.0;

            total += obtenerPrecioDesdeCombo(comboCPU);
            total += obtenerPrecioDesdeCombo(comboMotherBoard);
            total += obtenerPrecioDesdeCombo(comboGPU);
            total += obtenerPrecioDesdeCombo(comboRAM);
            total += obtenerPrecioDesdeCombo(comboAlmacenamiento);
            total += obtenerPrecioDesdeCombo(comboFuente);
            total += obtenerPrecioDesdeCombo(comboGabinete);

            total += listaRAMsSeleccionadas.getItems().stream()
                .mapToDouble(ComponentePC::obtenerPrecio)
                .sum();

            total += listaDiscosSeleccionados.getItems().stream()
                .mapToDouble(ComponentePC::obtenerPrecio)
                .sum();

            return total;
        }).getOrElse(0.0);
    }

    /**
     * Obtiene el precio del componente seleccionado en un ComboBox.
     * Retorna 0.0 si no hay seleccion.
     *
     * @param combo el ComboBox del que obtener el precio
     * @return el precio del componente seleccionado o 0.0
     */
    private double obtenerPrecioDesdeCombo(ComboBox<ComponentePC> combo) {
        return Optional.ofNullable(combo.getValue())
            .map(ComponentePC::obtenerPrecio)
            .orElse(0.0);
    }

    /**
     * Actualiza la etiqueta de precio total con el calculo actual.
     * Recalcula y muestra el precio total formateado.
     */
    private void actualizarPrecioTotal() {
        Try.run(() -> {
            double total = calcularPrecioTotal();
            lblPrecioTotal.setText(String.format("Precio Total: $%.2f MXN", total));
        });
    }

    /**
     * Maneja la accion del boton continuar.
     * Valida que todos los componentes obligatorios esten seleccionados,
     * crea la computadora con los componentes y navega a PanelSoftware.
     */
    private void manejarContinuar() {
        Try.run(() -> {
            if (!validarComponentesObligatorios()) {
                mostrarDialogoAdvertencia("Componentes Incompletos",
                    "Debes seleccionar todos los componentes obligatorios:\n" +
                    "CPU, MotherBoard, GPU, RAM, Almacenamiento, Fuente y Gabinete.");
                return;
            }

            ComputadoraBase computadora = crearComputadora();
            PanelSoftware panelSoftware = new PanelSoftware(vista, computadora);
            vista.cambiarEscena(panelSoftware.crear());
        }).onFailure(error ->
            mostrarDialogoError("Error", "No se pudo continuar: " + error.getMessage())
        );
    }

    /**
     * Crea una computadora con todos los componentes seleccionados.
     * Construye una instancia de ComputadoraBasica y agrega todos los componentes.
     *
     * @return la computadora configurada con todos los componentes
     */
    private ComputadoraBase crearComputadora() {
        String nombrePC = componentesIniciales.isEmpty() ? "PC Personalizada" : "PC Prearmada";
        ComputadoraBase computadora = new ComputadoraBasica(nombrePC);

        obtenerComponentesSeleccionados()
            .forEach(computadora::agregarComponente);

        return computadora;
    }

    /**
     * Valida que todos los componentes obligatorios esten seleccionados.
     * Verifica que CPU, MotherBoard, GPU, RAM, Almacenamiento, Fuente y Gabinete
     * tengan valores seleccionados.
     *
     * @return true si todos los componentes obligatorios estan seleccionados
     */
    private boolean validarComponentesObligatorios() {
        return Stream.of(comboCPU, comboMotherBoard, comboGPU, comboRAM,
                        comboAlmacenamiento, comboFuente, comboGabinete)
            .allMatch(combo -> combo.getValue() != null);
    }

    /**
     * Maneja la accion del boton volver.
     * Regresa a la ventana principal del sistema.
     */
    private void manejarVolver() {
        Try.run(() -> {
            VentanaPrincipal panelAnterior = new VentanaPrincipal(vista);
            vista.cambiarEscena(panelAnterior.crear());
        }).onFailure(error ->
            mostrarDialogoError("Error", "No se pudo volver a la pantalla anterior")
        );
    }

    /**
     * Agrega un componente compuesto de RAMs a la lista de componentes.
     * Crea un JuegoRAMs con todas las RAMs seleccionadas y lo agrega a la lista.
     *
     * @param componentes la lista donde agregar el juego de RAMs
     */
    private void agregarJuegoRAMs(List<ComponentePC> componentes) {
        List<RAM> ramsSeleccionadas = new ArrayList<>();

        Optional.ofNullable(comboRAM.getValue())
            .filter(comp -> comp instanceof RAM)
            .map(comp -> (RAM) comp)
            .ifPresent(ramsSeleccionadas::add);

        listaRAMsSeleccionadas.getItems().stream()
            .filter(comp -> comp instanceof RAM)
            .map(comp -> (RAM) comp)
            .forEach(ramsSeleccionadas::add);

        if (!ramsSeleccionadas.isEmpty()) {
            int capacidadTotal = ramsSeleccionadas.stream()
                .mapToInt(RAM::getCapacidadGB)
                .sum();

            JuegoRAMs juegoRAMs = new JuegoRAMs(
                new ArrayList<>(ramsSeleccionadas),
                "Conjunto de RAMs",
                "RAM",
                capacidadTotal,
                ramsSeleccionadas
            );

            componentes.add(juegoRAMs);
        }
    }

    /**
     * Agrega un componente compuesto de discos a la lista de componentes.
     * Crea un JuegoDiscos con todos los discos seleccionados y lo agrega a la lista.
     *
     * @param componentes la lista donde agregar el juego de discos
     */
    private void agregarJuegoDiscos(List<ComponentePC> componentes) {
        List<Disco> discosSeleccionados = new ArrayList<>();

        Optional.ofNullable(comboAlmacenamiento.getValue())
            .filter(comp -> comp instanceof Disco)
            .map(comp -> (Disco) comp)
            .ifPresent(discosSeleccionados::add);

        listaDiscosSeleccionados.getItems().stream()
            .filter(comp -> comp instanceof Disco)
            .map(comp -> (Disco) comp)
            .forEach(discosSeleccionados::add);

        if (!discosSeleccionados.isEmpty()) {
            int capacidadTotal = discosSeleccionados.stream()
                .mapToInt(Disco::getCapacidadAlmacenamiento)
                .sum();

            JuegoDiscos juegoDiscos = new JuegoDiscos(
                capacidadTotal,
                "Mixto",
                discosSeleccionados,
                "Conjunto de Discos",
                "Disco"
            );

            componentes.add(juegoDiscos);
        }
    }

    /**
     * Agrega un componente desde un ComboBox a la lista si esta seleccionado.
     * Verifica que el ComboBox tenga un valor y lo agrega a la lista.
     *
     * @param combo el ComboBox del que obtener el componente
     * @param componentes la lista donde agregar el componente
     */
    private void agregarComponenteDesdeCombo(ComboBox<ComponentePC> combo, List<ComponentePC> componentes) {
        Optional.ofNullable(combo.getValue())
            .filter(comp -> !(comp instanceof RAM || comp instanceof Disco))
            .ifPresent(componentes::add);
    }

    /**
     * Carga una lista de componentes en un ComboBox.
     * Configura el ComboBox con los componentes y establece el formato de visualizacion.
     *
     * @param combo el ComboBox a configurar
     * @param componentes la lista de componentes a cargar
     */
    private void cargarComponentesEnCombo(ComboBox<ComponentePC> combo, List<ComponentePC> componentes) {
        ObservableList<ComponentePC> items = FXCollections.observableArrayList(componentes);
        combo.setItems(items);
        combo.setButtonCell(new javafx.scene.control.ListCell<ComponentePC>() {
            @Override
            protected void updateItem(ComponentePC item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "Seleccionar..." : formatearComponente(item));
            }
        });
        combo.setCellFactory(param -> new javafx.scene.control.ListCell<ComponentePC>() {
            @Override
            protected void updateItem(ComponentePC item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : formatearComponente(item));
            }
        });
    }

    /**
     * Formatea un componente para visualizacion en ComboBox o ListView.
     * Crea una representacion legible con nombre, marca y precio.
     *
     * @param componente el componente a formatear
     * @return cadena formateada con la informacion del componente
     */
    private String formatearComponente(ComponentePC componente) {
        return String.format("%s - %s ($%.2f)",
            componente.obtenerNombre(),
            componente.obtenerMarca(),
            componente.obtenerPrecio()
        );
    }

    /**
     * Aplica estilos CSS al panel.
     * Configura las clases de estilo para el contenedor principal.
     */
    private void aplicarEstilos() {
        root.getStyleClass().add("root");
        gridComponentes.getStyleClass().add("grid-componentes");
        panelLateral.getStyleClass().add("panel-lateral");
    }

    /**
     * Valida que la vista de JavaFX no sea nula.
     * Lanza una excepcion si la vista proporcionada es nula.
     *
     * @param vista la vista a validar
     * @throws IllegalArgumentException si la vista es nula
     */
    private void validarVista(VistaJavaFX vista) {
        Optional.ofNullable(vista)
            .orElseThrow(() -> new IllegalArgumentException("La vista no puede ser nula"));
    }

    /**
     * Crea una etiqueta con texto y clase CSS especificados.
     * Fabrica una etiqueta configurada con estilo.
     *
     * @param texto el texto de la etiqueta
     * @param styleClass la clase CSS a aplicar
     * @return la etiqueta configurada
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
     * Fabrica un boton configurado con estilo.
     *
     * @param texto el texto del boton
     * @param styleClass la clase CSS a aplicar
     * @return el boton configurado
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
     * Crea un ComboBox configurado para componentes de PC.
     * Fabrica un ComboBox con configuracion base.
     *
     * @return el ComboBox configurado
     */
    private ComboBox<ComponentePC> crearComboBox() {
        ComboBox<ComponentePC> combo = new ComboBox<>();
        combo.setPrefWidth(350);
        return combo;
    }

    /**
     * Crea un ListView configurado para componentes de PC.
     * Fabrica un ListView con formato personalizado.
     *
     * @return el ListView configurado
     */
    private ListView<ComponentePC> crearListView() {
        ListView<ComponentePC> lista = new ListView<>();
        lista.setCellFactory(param -> new javafx.scene.control.ListCell<ComponentePC>() {
            @Override
            protected void updateItem(ComponentePC item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : formatearComponente(item));
            }
        });
        return lista;
    }

    /**
     * Aplica el listener de actualizacion de precio a un ComboBox.
     * Configura la accion que se ejecuta al cambiar la seleccion.
     *
     * @return consumer que aplica el listener
     */
    private Consumer<ComboBox<ComponentePC>> aplicarListenerActualizacionPrecio() {
        return combo -> combo.valueProperty()
            .addListener((obs, oldVal, newVal) -> actualizarPrecioTotal());
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
