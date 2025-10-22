import java.util.Scanner;
import vista.VistaConsola;
import controlador.*;
import javafx.application.Application;


public class Main {

    public static void main(String[] args) {
        Main programa = new Main(); // instancia para usar los métodos no estáticos

        if (args.length == 0) {
            programa.mostrarAyuda();
            return;
        }

        //aqui no se como vamos a inciar la aplicacion, lo dejo como un solo arg
        String modo = args[0].toLowerCase();

        switch (modo) {
            case "consola":
                programa.iniciarModoConsola();
                break;

            case "gui":
                programa.iniciarModoGUI();
                break;

            default:
                programa.mostrarAyuda();
                break;
        }
    }

    /**
     * Inicia el programa en modo consola.
     */
    //no guarda los registros de compras.
    private void iniciarModoConsola() {
        Scanner scanner = new Scanner(System.in);
        VistaConsola vista = new VistaConsola(scanner);
        ControladorPrincipal controlador = new ControladorPrincipal(vista);

        controlador.iniciar();

        scanner.close();
    }

    /**
     * Inicia el programa en modo gráfico (JavaFX).
     * 
     */
    //ya no supe como hacer este 
    private void iniciarModoGUI() {
        Application.launch(MainJavaFX.class);
    }

    /**
     * Muestra un mensaje de ayuda con los modos disponibles.
     */
    private void mostrarAyuda() {
        System.out.println("Uso del programa:");
        System.out.println("  java Main consola   → Ejecuta la versión de consola");
        System.out.println("  java Main gui       → Ejecuta la versión gráfica (JavaFX)");
        System.out.println("  java Main ayuda     → Muestra este mensaje");
    }
}
