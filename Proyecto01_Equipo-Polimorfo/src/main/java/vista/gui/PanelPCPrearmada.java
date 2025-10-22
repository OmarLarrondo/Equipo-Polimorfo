package vista.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import modelo.componente.ComponenteCompuesto;
import modelo.componente.ComponentePC;

import java.util.List;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap; 


public class PanelPCPrearmada {
    private VBox root;
    private Label lblTitulo;
    private ComboBox<String> comboTipoPC;
    private TextArea txtDetallesPC;
    private Label lblPrecio;
    private Button btnSeleccionar;
    private Button btnVolver;
    private VistaJavaFX vista;
    private Map<String, List<ComponentePC>> configuraciones = new HashMap<>();

    public PanelPCPrearmada(VistaJavaFX vista) {
        validarVista(vista);
        this.vista = vista;
        root = new VBox(15);
    }

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
    private void  inicializarComponentes(){
        lblTitulo = crearLabel("Selecciona tu PC ¡prearmada!","label-titulo");
        comboTipoPC = new ComboBox<>();
        txtDetallesPC = new TextArea();
        lblPrecio = crearLabel("Precio", "label-primary");
        btnSeleccionar = crearBoton("Seleccionar","button-primary");
        btnVolver = crearBoton("Volver", "button-secondary");

    }

    private void configurarLayout(){
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(50));
        root.getChildren().addAll(lblTitulo, comboTipoPC, txtDetallesPC, lblPrecio
                                    , btnSeleccionar, btnVolver);
    }

    private Label crearLabel(String texto, String styleClass) {
        Label label = new Label(texto);
        label.getStyleClass().add(styleClass);
        return label;
    }

    private Button crearBoton(String texto, String styleClass) {
        Button boton = new Button(texto);
        boton.getStyleClass().add(styleClass);
        return boton;
    }


    /**
     * este metodo va a :
     * <p>Obtener los tipos de PC prearmadas(ejemplo: "Gamer", "Godina", "Escuela", etc...)</p>
     * <p>Llenar el comboBox con esos nombres.</p>
     * 
     */

     //YA NO SUPE COMO HACERLO CARGAR CONFIGURACIONES       
    private void cargarConfiguraciones() {
        configuraciones.clear();

        // --- PC Gamer ---
        ComponenteCompuesto pcGamer = new ComponenteCompuesto(null, "PC Gamer", "Gamer");
        pcGamer.agregar(new ComponenteHoja("Core i5-13600K", 18999.00, "Intel", "CPU"));

        configuraciones.put("Gamer", List.of(pcGamer));

        
        configuraciones.put("Gamer", listaGamer);    
        configuraciones.put("Oficina", listaOficina);
        configuraciones.put("Personal", listaPersonal);
        configuraciones.put("Economia", listaEconomia);
    
        // También llenamos el ComboBox
        comboTipoPC.getItems().clear();
        comboTipoPC.getItems().addAll(configuraciones.keySet());
    }


    private void mostrarDetallesPC(String tipo) {
        // Obtenemos la lista de componentes para el tipo seleccionado
        List<ComponentePC> componentes = configuraciones.get(tipo);

        if (componentes == null || componentes.isEmpty()) {
            txtDetallesPC.setText("No hay detalles disponibles para la PC " + tipo);
            lblPrecio.setText("Precio: $0 MXN");
            return;
        }

        // Construir el texto de detalles
        StringBuilder detalles = new StringBuilder("Detalles de la PC " + tipo + ":\n");
        double precioTotal = 0;

        for (ComponentePC c : componentes) {
            detalles.append("- ").append(c.obtenerMarca())
                    .append(": ").append(c.obtenerNombre())
                    .append(" | Precio: $").append(c.obtenerPrecio()).append(" MXN\n");
            precioTotal += c.obtenerPrecio();
        }

        txtDetallesPC.setText(detalles.toString());
        lblPrecio.setText("Precio: $" + precioTotal + " MXN");
    }




    private void configurarBotones(){
        comboTipoPC.setOnAction(e -> {
            System.out.println("Se selecciono Menu para ver las opciones de prearmado");
            String tipoSeleccionado = comboTipoPC.getValue();
            mostrarDetallesPC(tipoSeleccionado);
        });

        btnVolver.setOnAction(e -> {
            System.out.println("Volver a la pantalla anterior.");
            PanelSeleccionTipo panelAnterior = new PanelSeleccionTipo(vista);
            vista.cambiarEscena(panelAnterior.crear());
        });


        btnSeleccionar.setOnAction(e -> {
            // Obtener la opción seleccionada del combobbox
            String tipoSeleccionado = comboTipoPC.getValue();
        
            if (tipoSeleccionado != null) {
                System.out.println("Se confirmó la PC prearmada: " + tipoSeleccionado);
            
                // Suponiendo que tienes un método que obtiene la configuración de esa PC
                List<ComponentePC> componentesSeleccionados = configuraciones.get(tipoSeleccionado);
            
                // Crear el siguiente panel, pasando la configuración seleccionada
                PanelComponentes panel = new PanelComponentes(vista, componentesSeleccionados);
            
                // Cambiar a la nueva escena
                vista.cambiarEscena(panel.crear());
            
            } else {
                System.out.println("No se seleccionó ninguna PC.");
                // Mostrar un diálogo al usuario
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setTitle("Atención");
                alerta.setHeaderText(null);
                alerta.setContentText("Debes seleccionar una PC prearmada antes de continuar.");
                alerta.showAndWait();
            }
        });


    }

    /**
     * Aplica la clase CSS "root" al contenedor principal {@link VBox}.
     */
    private void aplicarEstilos() {
        root.getStyleClass().add("root");
    }

    private void validarVista(VistaJavaFX vista){
        if(vista == null){
            throw new IllegalArgumentException("La vista no puede ser nula.");
        }
    }


    
}
