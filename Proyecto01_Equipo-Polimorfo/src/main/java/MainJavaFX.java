import javafx.application.Application;
import javafx.stage.Stage;
import vista.gui.VistaJavaFX;
import vista.gui.VentanaPrincipal;
import controlador.ControladorPrincipal;
import fachada.SistemaEnsamblajeFacade;

/**
 * Clase principal para el lanzamiento de la aplicacion JavaFX del sistema
 * de ensamblaje de computadoras MonosChinos MX.
 *
 * <p>Esta clase extiende {@link Application} de JavaFX y configura el ciclo de vida
 * completo de la aplicacion, incluyendo:
 * <ul>
 *   <li>Inicializacion del sistema MVC (Modelo-Vista-Controlador)</li>
 *   <li>Configuracion de la fachada del sistema de ensamblaje</li>
 *   <li>Creacion y configuracion de la ventana principal</li>
 *   <li>Inicio del controlador con la vista JavaFX</li>
 * </ul>
 *
 * <p>El metodo {@link #start(Stage)} es invocado automaticamente por la plataforma
 * JavaFX cuando la aplicacion esta lista para comenzar. La aplicacion utiliza
 * el patron MVC con una fachada para simplificar las interacciones con el modelo
 * de negocio.
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class MainJavaFX extends Application {

    /**
     * Punto de entrada principal para la aplicacion JavaFX.
     * Configura e inicializa todos los componentes del sistema MVC y muestra
     * la ventana principal al usuario.
     *
     * <p>Pasos de inicializacion:
     * <ol>
     *   <li>Crea la vista JavaFX con el stage primario</li>
     *   <li>Inicializa la fachada del sistema de ensamblaje</li>
     *   <li>Crea el controlador principal y lo conecta con la vista y fachada</li>
     *   <li>Configura la ventana principal con la vista</li>
     *   <li>Muestra la ventana principal al usuario</li>
     * </ol>
     *
     * @param primaryStage el escenario principal proporcionado por la plataforma JavaFX
     * @throws Exception si ocurre algun error durante la inicializacion
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        VistaJavaFX vista = new VistaJavaFX(primaryStage);

        SistemaEnsamblajeFacade facade = new SistemaEnsamblajeFacade();

        ControladorPrincipal controlador = new ControladorPrincipal(vista);
        vista.setControlador(controlador);

        VentanaPrincipal ventanaPrincipal = new VentanaPrincipal(vista);
        vista.cambiarEscena(ventanaPrincipal.crear());

        primaryStage.setTitle("MonosChinos MX - Sistema de Ensamblaje de PC");
        primaryStage.show();
    }

    /**
     * Metodo main que lanza la aplicacion JavaFX.
     * Invoca {@link Application#launch(String...)} que a su vez llamara a
     * {@link #start(Stage)} cuando la plataforma JavaFX este lista.
     *
     * @param args argumentos de linea de comandos (no utilizados actualmente)
     */
    public static void main(String[] args) {
        launch(args);
    }
}
