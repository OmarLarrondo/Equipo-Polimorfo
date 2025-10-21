package vista.gui;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.controlsfx.control.Notifications;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;

import controlador.ControladorPrincipal;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.componente.ComponentePC;
import modelo.ticket.Ticket;
import vista.Vista;

/**
 * Implementacion de la interfaz {@link Vista} para JavaFX que proporciona una interfaz grafica
 * de usuario para el sistema de ensamblaje de computadoras MonosChinos MX.
 *
 * <p>Esta clase actua como adaptador entre el patron MVC tradicional y JavaFX, transformando
 * operaciones de entrada/salida sincronas en interacciones graficas asincronas mediante dialogos
 * modales. Proporciona una experiencia de usuario moderna y profesional utilizando:
 * <ul>
 *   <li>Dialogos modales para entrada de usuario (TextInputDialog, ChoiceDialog, Alert)</li>
 *   <li>Notificaciones visuales elegantes mediante ControlsFX</li>
 *   <li>Iconos vectoriales mediante Ikonli FontAwesome</li>
 *   <li>Aplicacion de temas CSS personalizados</li>
 *   <li>Navegacion entre diferentes escenas de la aplicacion</li>
 * </ul>
 *
 * <p>La clase maneja la sincronizacion entre el hilo de JavaFX y el hilo de la logica de negocio
 * mediante el uso de CountDownLatch para bloquear operaciones hasta que el usuario proporcione
 * una respuesta en la interfaz grafica.
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class VistaJavaFX implements Vista {

    /**Ventana principal de la aplicacion JavaFX.*/
    private Stage primaryStage;

    /**Escena actual mostrada en la ventana principal.*/
    private Scene currentScene;

    /**Referencia al controlador principal del sistema.*/
    private ControladorPrincipal controlador;

    /**Cola para almacenar entradas del usuario de manera temporal.*/
    private Queue<String> entradaUsuario;

    /**
     * Construye una nueva vista JavaFX con la ventana principal especificada.
     * Inicializa la cola de entrada de usuario y configura el titulo de la ventana.
     *
     * @param stage la ventana principal de JavaFX, no debe ser nula
     * @throws IllegalArgumentException si stage es nulo
     */
    public VistaJavaFX(Stage stage) {
        if (stage == null) {
            throw new IllegalArgumentException("El stage no puede ser nulo");
        }
        this.primaryStage = stage;
        this.entradaUsuario = new LinkedList<>();
        this.primaryStage.setTitle("MonosChinos MX - Sistema de Ensamblaje");
    }

    /**
     * Configura el controlador principal del sistema.
     * Permite la inyeccion de dependencias del controlador despues de la construccion.
     *
     * @param controlador el controlador principal a utilizar
     */
    public void setControlador(ControladorPrincipal controlador) {
        this.controlador = controlador;
    }

    /**
     * Muestra un mensaje informativo al usuario mediante un dialogo de alerta.
     * Ejecuta la operacion en el hilo de JavaFX para garantizar la seguridad de hilos.
     *
     * @param mensaje el texto del mensaje a mostrar
     */
    @Override
    public void mostrarMensaje(String mensaje) {
        ejecutarEnHiloJavaFX(() ->
            crearAlerta(Alert.AlertType.INFORMATION, "Informacion", mensaje)
                .ifPresent(Alert::showAndWait)
        );
    }

    /**
     * Muestra un mensaje de error al usuario mediante un dialogo de alerta de error.
     * Ejecuta la operacion en el hilo de JavaFX para garantizar la seguridad de hilos.
     *
     * @param error el texto del error a mostrar
     */
    @Override
    public void mostrarError(String error) {
        ejecutarEnHiloJavaFX(() ->
            crearAlerta(Alert.AlertType.ERROR, "Error", error)
                .ifPresent(Alert::showAndWait)
        );
    }

    /**
     * Muestra un menu de opciones al usuario mediante un dialogo de seleccion.
     * Las opciones se presentan como botones numerados que el usuario puede seleccionar.
     * Este metodo almacena la opcion seleccionada en la cola de entrada para su posterior
     * lectura mediante {@link #leerOpcion()}.
     *
     * @param opciones lista de cadenas con las opciones del menu a mostrar
     */
    @Override
    public void mostrarMenu(List<String> opciones) {
        AtomicInteger opcionSeleccionada = new AtomicInteger(-1);
        CountDownLatch latch = new CountDownLatch(1);

        ejecutarEnHiloJavaFX(() -> {
            crearDialogoMenu(opciones)
                .ifPresent(opcion -> opcionSeleccionada.set(opcion));
            latch.countDown();
        });

        esperarLatch(latch);
        entradaUsuario.offer(String.valueOf(opcionSeleccionada.get()));
    }

    /**
     * Lee una opcion numerica del usuario desde la cola de entrada.
     * Este metodo extrae y convierte la opcion almacenada previamente por {@link #mostrarMenu(List)}.
     * Si no hay entrada disponible o la conversion falla, retorna -1.
     *
     * @return el numero de opcion seleccionado por el usuario, o -1 si no hay entrada valida
     */
    @Override
    public int leerOpcion() {
        return Optional.ofNullable(entradaUsuario.poll())
            .flatMap(this::convertirAEntero)
            .orElse(-1);
    }

    /**
     * Lee una cadena de texto del usuario mediante un dialogo de entrada de texto.
     * Bloquea hasta que el usuario proporcione una entrada o cancele el dialogo.
     *
     * @param prompt el mensaje a mostrar antes de leer la entrada
     * @return la cadena de texto ingresada por el usuario, o cadena vacia si se cancela
     */
    @Override
    public String leerTexto(String prompt) {
        AtomicReference<String> texto = new AtomicReference<>("");
        CountDownLatch latch = new CountDownLatch(1);

        ejecutarEnHiloJavaFX(() -> {
            crearDialogoTexto(prompt)
                .ifPresent(texto::set);
            latch.countDown();
        });

        esperarLatch(latch);
        return texto.get();
    }

    /**
     * Muestra una lista de componentes de PC con sus detalles y precios en un dialogo.
     * Presenta la informacion en un formato tabular con columnas para tipo, nombre, marca y precio.
     *
     * @param componentes la lista de componentes a mostrar
     */
    @Override
    public void mostrarComponentes(List<ComponentePC> componentes) {
        ejecutarEnHiloJavaFX(() ->
            crearDialogoComponentes(componentes)
                .ifPresent(dialogo -> dialogo.showAndWait())
        );
    }

    /**
     * Muestra un ticket de compra completo en un dialogo con area de texto.
     * Presenta toda la informacion del ticket incluyendo componentes, software,
     * compatibilidad y precio total en un formato legible.
     *
     * @param ticket el ticket a mostrar
     */
    @Override
    public void mostrarTicket(Ticket ticket) {
        ejecutarEnHiloJavaFX(() ->
            crearDialogoTicket(ticket)
                .ifPresent(dialogo -> dialogo.showAndWait())
        );
    }

    /**
     * Solicita confirmacion al usuario para una accion mediante un dialogo de confirmacion.
     * Bloquea hasta que el usuario confirme o cancele la operacion.
     *
     * @param mensaje el mensaje de confirmacion a mostrar
     * @return true si el usuario confirma, false en caso contrario
     */
    @Override
    public boolean confirmar(String mensaje) {
        AtomicReference<Boolean> confirmado = new AtomicReference<>(false);
        CountDownLatch latch = new CountDownLatch(1);

        ejecutarEnHiloJavaFX(() -> {
            crearDialogoConfirmacion("Confirmacion", mensaje)
                .ifPresent(confirmado::set);
            latch.countDown();
        });

        esperarLatch(latch);
        return confirmado.get();
    }

    /**
     * Limpia la pantalla o area de visualizacion.
     * En una aplicacion JavaFX esto tipicamente no tiene efecto visible,
     * pero puede resetear el estado de la escena actual si fuera necesario.
     */
    @Override
    public void limpiarPantalla() {
    }

    /**
     * Cambia la escena mostrada en la ventana principal.
     * Si la escena proporcionada es null, vuelve a la escena principal del menu.
     * Aplica el tema CSS configurado a la nueva escena.
     *
     * @param escena la nueva escena a mostrar, o null para volver al menu principal
     */
    public void cambiarEscena(Scene escena) {
        ejecutarEnHiloJavaFX(() ->
            Optional.ofNullable(escena)
                .or(() -> Optional.ofNullable(crearEscenaPrincipal()))
                .map(aplicarEstilos())
                .ifPresent(this::establecerEscena)
        );
    }

    /**
     * Crea la escena principal del menu de la aplicacion.
     * Construye una interfaz basica con titulo y botones para las operaciones principales
     * del sistema de ensamblaje de computadoras.
     *
     * @return la escena principal configurada
     */
    private Scene crearEscenaPrincipal() {
        return Optional.of(new BorderPane())
            .map(configurarEncabezadoPrincipal())
            .map(configurarContenidoCentral())
            .map(configurarPieBotones())
            .map(pane -> new Scene(pane, 800, 600))
            .orElseThrow(() -> new IllegalStateException("Error al crear escena principal"));
    }

    /**
     * Configura el encabezado de la escena principal con el titulo de la aplicacion.
     *
     * @return funcion que configura el encabezado en el BorderPane
     */
    private Function<BorderPane, BorderPane> configurarEncabezadoPrincipal() {
        return pane -> {
            Label titulo = crearTitulo("MonosChinos MX");
            HBox encabezado = new HBox(titulo);
            encabezado.getStyleClass().add("encabezado-panel");
            encabezado.setAlignment(Pos.CENTER);
            encabezado.setPadding(new Insets(30));
            pane.setTop(encabezado);
            return pane;
        };
    }

    /**
     * Configura el contenido central de la escena principal con mensaje de bienvenida.
     *
     * @return funcion que configura el contenido central en el BorderPane
     */
    private Function<BorderPane, BorderPane> configurarContenidoCentral() {
        return pane -> {
            Label mensaje = new Label("Sistema de Ensamblaje de Computadoras");
            mensaje.getStyleClass().add("label-titulo");
            VBox contenedor = new VBox(20, mensaje);
            contenedor.setAlignment(Pos.CENTER);
            contenedor.setPadding(new Insets(20));
            pane.setCenter(contenedor);
            return pane;
        };
    }

    /**
     * Configura el pie de la escena principal con botones de accion.
     *
     * @return funcion que configura el pie en el BorderPane
     */
    private Function<BorderPane, BorderPane> configurarPieBotones() {
        return pane -> {
            Button btnIniciar = crearBoton("Iniciar", FontAwesomeSolid.PLAY, "button-primary");
            btnIniciar.setOnAction(e -> Optional.ofNullable(controlador)
                .ifPresent(ControladorPrincipal::iniciar));

            HBox contenedorBotones = new HBox(15, btnIniciar);
            contenedorBotones.getStyleClass().add("contenedor-botones");
            contenedorBotones.setAlignment(Pos.CENTER);
            contenedorBotones.setPadding(new Insets(20));
            pane.setBottom(contenedorBotones);
            return pane;
        };
    }

    /**
     * Crea un dialogo de menu con opciones presentadas como botones.
     * El usuario selecciona una opcion haciendo clic en el boton correspondiente.
     *
     * @param opciones lista de opciones a mostrar
     * @return Optional conteniendo el indice de la opcion seleccionada (base 1), o vacio si se cancela
     */
    private Optional<Integer> crearDialogoMenu(List<String> opciones) {
        Alert dialogo = new Alert(Alert.AlertType.NONE);
        dialogo.setTitle("Menu");
        dialogo.setHeaderText("Seleccione una opcion:");
        dialogo.initModality(Modality.APPLICATION_MODAL);

        List<ButtonType> botones = IntStream.range(0, opciones.size())
            .mapToObj(i -> new ButtonType(String.format("%d. %s", i + 1, opciones.get(i))))
            .collect(Collectors.toList());

        dialogo.getButtonTypes().addAll(botones);

        return dialogo.showAndWait()
            .flatMap(botonSeleccionado ->
                IntStream.range(0, botones.size())
                    .filter(i -> botones.get(i).equals(botonSeleccionado))
                    .mapToObj(i -> i + 1)
                    .findFirst()
            );
    }

    /**
     * Crea un dialogo de entrada de texto con el prompt especificado.
     * Utiliza TextInputDialog de JavaFX para capturar la entrada del usuario.
     *
     * @param prompt el mensaje a mostrar en el dialogo
     * @return Optional conteniendo el texto ingresado, o vacio si se cancela
     */
    private Optional<String> crearDialogoTexto(String prompt) {
        TextInputDialog dialogo = new TextInputDialog();
        dialogo.setTitle("Entrada de Texto");
        dialogo.setHeaderText(prompt);
        dialogo.setContentText("Ingrese el texto:");
        dialogo.initModality(Modality.APPLICATION_MODAL);
        return dialogo.showAndWait();
    }

    /**
     * Crea un dialogo de confirmacion con titulo y mensaje especificados.
     * Presenta botones OK y Cancelar para que el usuario confirme o rechace.
     *
     * @param titulo el titulo del dialogo
     * @param mensaje el mensaje de confirmacion
     * @return Optional conteniendo true si se confirma, false si se cancela
     */
    private Optional<Boolean> crearDialogoConfirmacion(String titulo, String mensaje) {
        Alert dialogo = new Alert(Alert.AlertType.CONFIRMATION);
        dialogo.setTitle(titulo);
        dialogo.setHeaderText(null);
        dialogo.setContentText(mensaje);
        dialogo.initModality(Modality.APPLICATION_MODAL);

        return dialogo.showAndWait()
            .map(respuesta -> respuesta == ButtonType.OK);
    }

    /**
     * Crea un dialogo para mostrar una lista de componentes en formato tabular.
     * Presenta los componentes con sus propiedades principales en una tabla JavaFX.
     *
     * @param componentes la lista de componentes a mostrar
     * @return Optional conteniendo el dialogo configurado
     */
    private Optional<Alert> crearDialogoComponentes(List<ComponentePC> componentes) {
        Alert dialogo = new Alert(Alert.AlertType.INFORMATION);
        dialogo.setTitle("Lista de Componentes");
        dialogo.setHeaderText("Componentes Disponibles");
        dialogo.initModality(Modality.APPLICATION_MODAL);

        TextArea areaTexto = new TextArea();
        areaTexto.setEditable(false);
        areaTexto.setWrapText(false);
        areaTexto.setPrefSize(600, 400);

        String contenido = IntStream.range(0, componentes.size())
            .mapToObj(i -> formatearComponente(i + 1, componentes.get(i)))
            .collect(Collectors.joining("\n"));

        areaTexto.setText(contenido);

        dialogo.getDialogPane().setContent(areaTexto);
        dialogo.getDialogPane().setPrefSize(650, 500);

        return Optional.of(dialogo);
    }

    /**
     * Crea un dialogo para mostrar un ticket completo.
     * Presenta el ticket en un area de texto con formato legible.
     *
     * @param ticket el ticket a mostrar
     * @return Optional conteniendo el dialogo configurado
     */
    private Optional<Alert> crearDialogoTicket(Ticket ticket) {
        Alert dialogo = new Alert(Alert.AlertType.INFORMATION);
        dialogo.setTitle("Ticket de Compra");
        dialogo.setHeaderText(String.format("Ticket #%d", ticket.obtenerNumeroTicket()));
        dialogo.initModality(Modality.APPLICATION_MODAL);

        TextArea areaTexto = new TextArea();
        areaTexto.setEditable(false);
        areaTexto.setWrapText(false);
        areaTexto.setPrefSize(700, 500);
        areaTexto.getStyleClass().add("text-area-detalles");
        areaTexto.setText(ticket.generarTicket());

        ScrollPane scrollPane = new ScrollPane(areaTexto);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        dialogo.getDialogPane().setContent(scrollPane);
        dialogo.getDialogPane().setPrefSize(750, 600);

        return Optional.of(dialogo);
    }

    /**
     * Formatea un componente para visualizacion en formato texto.
     * Crea una representacion de una linea con el indice, tipo, nombre, marca y precio.
     *
     * @param indice el numero de orden del componente
     * @param componente el componente a formatear
     * @return cadena formateada con la informacion del componente
     */
    private String formatearComponente(int indice, ComponentePC componente) {
        return String.format("%d. [%s] %s - %s ($%.2f)",
            indice,
            componente.obtenerTipo(),
            componente.obtenerNombre(),
            componente.obtenerMarca(),
            componente.obtenerPrecio()
        );
    }

    /**
     * Crea una alerta con el tipo, titulo y mensaje especificados.
     * Configura la alerta como modal para bloquear la interaccion con otras ventanas.
     *
     * @param tipo el tipo de alerta (INFORMATION, ERROR, WARNING, etc.)
     * @param titulo el titulo de la alerta
     * @param mensaje el mensaje a mostrar
     * @return Optional conteniendo la alerta configurada
     */
    private Optional<Alert> crearAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.initModality(Modality.APPLICATION_MODAL);
        return Optional.of(alerta);
    }

    /**
     * Crea un titulo estilizado con el texto especificado.
     * Aplica la clase CSS titulo-principal para estilizado consistente.
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
     * Crea un boton estilizado con texto, icono y clase CSS especificados.
     * Utiliza FontIcon de Ikonli para iconos vectoriales escalables.
     *
     * @param texto el texto del boton
     * @param icono el icono de FontAwesome a usar
     * @param estiloClase la clase CSS a aplicar
     * @return boton configurado
     */
    private Button crearBoton(String texto, FontAwesomeSolid icono, String estiloClase) {
        Button boton = new Button(texto);
        boton.setGraphic(new FontIcon(icono));
        boton.getStyleClass().add(estiloClase);
        return boton;
    }

    /**
     * Convierte una cadena a entero de forma segura.
     * Encapsula el resultado en un Optional para manejo seguro de errores.
     *
     * @param texto la cadena a convertir
     * @return Optional conteniendo el entero, o vacio si la conversion falla
     */
    private Optional<Integer> convertirAEntero(String texto) {
        try {
            return Optional.of(Integer.parseInt(texto));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    /**
     * Ejecuta una operacion en el hilo de JavaFX de forma segura.
     * Si ya esta en el hilo de JavaFX, ejecuta directamente.
     * Si no, programa la ejecucion mediante Platform.runLater.
     *
     * @param operacion la operacion a ejecutar
     */
    private void ejecutarEnHiloJavaFX(Runnable operacion) {
        if (Platform.isFxApplicationThread()) {
            operacion.run();
        } else {
            Platform.runLater(operacion);
        }
    }

    /**
     * Espera a que un CountDownLatch llegue a cero.
     * Bloquea el hilo actual hasta que el latch sea liberado.
     * Maneja InterruptedException de forma silenciosa.
     *
     * @param latch el latch a esperar
     */
    private void esperarLatch(CountDownLatch latch) {
        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Establece la escena actual en la ventana principal.
     * Actualiza la referencia a la escena actual y la muestra en el stage.
     *
     * @param escena la escena a establecer
     */
    private void establecerEscena(Scene escena) {
        this.currentScene = escena;
        this.primaryStage.setScene(escena);
        this.primaryStage.show();
    }

    /**
     * Aplica estilos CSS a una escena.
     * Carga el archivo de tema oscuro y lo aplica a la escena proporcionada.
     *
     * @return funcion que aplica estilos a una escena
     */
    private Function<Scene, Scene> aplicarEstilos() {
        return escena -> {
            try {
                String cssPath = getClass().getResource("/css/theme-dark.css").toExternalForm();
                escena.getStylesheets().add(cssPath);
            } catch (Exception e) {
                System.err.println("Error al cargar el archivo CSS: " + e.getMessage());
            }
            return escena;
        };
    }
}
