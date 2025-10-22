package vista.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Representa un panel de selección del tipo de PC (Personalizada o Prearmada) o 
 * puede regresar al panel anterior dentro de la aplicación de monosChinos.
 * <p>
 * Esta clase se encarga de:
 * <ul>
 *   <li>Crear los componentes gráficos (labels y botones).</li>
 *   <li>Configurar el layout y estilos CSS.</li>
 *   <li>Definir la lógica de los botones para navegar a otros paneles.</li>
 * </ul>
 * <p>
 * La escena creada por este panel tiene un tamaño de 900x700 píxeles y utiliza
 * la hoja de estilos CSS definida en <code>/css/estilos.css</code>.
 * 
 * @author Equipo-polimorfo
 * @version 1.0
 */
public class PanelSeleccionTipo {
    /**COntenedor principal del panel */
    private VBox root;
    /**Etiqueta para el titulo del panel {@link PanelSeleccionTIopo} */
    private Label lblTitulo;
    /**Boton para personalizar una PC */
    private Button btnPersonalizada;
    /**Boton para escoger una PC ya armada */
    private Button btnPrearmada;
    /**Boton para volver a la pantalla anterior*/
    private Button btnVolver;
    /**Esta e sla referencia a la vista principal de JavaFX */
    private VistaJavaFX vista;

    /**
     * Constructor del panel.
     *
     * @param vista instancia de {@link VistaJavaFX} usada para cambiar escenas;
     *              no puede ser nula.
     * @throws IllegalArgumentException si la vista es nula.
     */
    public PanelSeleccionTipo(VistaJavaFX vista) {
        validarVista(vista);
        this.vista = vista;
        root = new VBox(15);
    }

    /**
     * Crea la {@link Scene} que contiene este panel, inicializando componentes,
     * layout, eventos y estilos.
     *
     * @return la escena completa lista para ser mostrada.
     */
    public Scene crear() {
        inicializarComponentes();
        configurarLayout();
        configurarBotones();
        aplicarEstilos();
        //OJO NO SE, LO PUSE EN SOFWARE EL TAMANO
        Scene scene = new Scene(root, 900, 700);
        scene.getStylesheets().add(getClass().getResource("/css/estilos.css").toExternalForm());
        return scene;
    }

    /**
     * Inicializa los labels y botones del panel, aplicando las clases CSS correspondientes.
     */
    private void inicializarComponentes() {
        lblTitulo = crearLabel("Selecciona el tipo de PC", "label-titulo");

        btnPersonalizada = crearBoton("Personalizada", "button-primary");
        btnPrearmada = crearBoton("Prearmada", "button-primary");
        btnVolver = crearBoton("Volver", "button-secondary");
    }

    /**
     * Configura el layout del {@link VBox} principal, alineación y padding.
     */
    private void configurarLayout() {
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(50));
        root.getChildren().addAll(lblTitulo, btnPersonalizada, btnPrearmada, btnVolver);
    }

    /**
     * Configura los eventos de los botones, cambiando la escena según la acción:
     * <ul>
     *   <li>btnPersonalizada → PanelComponentes</li>
     *   <li>btnPrearmada → PanelPCPrearmada</li>
     *   <li>btnVolver → VentanaPrincipal</li>
     * </ul>
     */
    private void configurarBotones() {
        btnPersonalizada.setOnAction(e -> {
            System.out.println("Se seleccionó PC personalizada");
            //CUANDO LO HICE NO ESTABA PANELCOMPONENET.CREAR(), PERO EN LE DIGRAMA SI ESTA
            PanelComponentes panel = new PanelComponentes(vista);
            vista.cambiarEscena(panel.crear());
        });

        btnPrearmada.setOnAction(e -> {
            System.out.println("Se seleccionó PC prearmada");
            PanelPCPrearmada panel = new PanelPCPrearmada(vista);
            vista.cambiarEscena(panel.crear());
        });
        
        btnVolver.setOnAction(e -> {
            System.out.println("Volver a la pantalla anterior.");
            PanelSeleccionTipo panelAnterior = new PanelSeleccionTipo(vista);
            vista.cambiarEscena(panelAnterior.crear());
        });

    }

    /**
     * Aplica la clase CSS "root" al contenedor principal {@link VBox}.
     */
    private void aplicarEstilos() {
        root.getStyleClass().add("root");
    }

    /**
     * Valida que la vista no sea nula.
     *
     * @param vistaValidar la instancia de {@link VistaJavaFX} a validar.
     * @throws IllegalArgumentException si la vista es nula.
     */
    private void validarVista(VistaJavaFX vistaValidar) {
        if (vistaValidar == null) {
            throw new IllegalArgumentException("La vista no puede ser nula");
        }
    }

    /**
     * Crea un {@link Button} con el texto y clase CSS indicados.
     *
     * @param texto el texto que mostrará el botón.
     * @param styleClass la clase CSS a aplicar.
     * @return el botón creado.
     */
    private Button crearBoton(String texto, String styleClass) {
        Button boton = new Button(texto);
        boton.getStyleClass().add(styleClass);
        return boton;
    }

    /**
     * Crea un {@link Label} con el texto y clase CSS indicados.
     *
     * @param texto el texto que mostrará el label.
     * @param styleClass la clase CSS a aplicar.
     * @return el label creado.
     */
    private Label crearLabel(String texto, String styleClass) {
        Label label = new Label(texto);
        label.getStyleClass().add(styleClass);
        return label;
    }
}
