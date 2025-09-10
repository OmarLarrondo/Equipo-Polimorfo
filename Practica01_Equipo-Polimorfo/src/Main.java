import simulacion.Simulacion;

/**
 * Clase principal que ejecuta la simulación completa de servicios de streaming.
 * 
 * Esta clase proporciona una interfaz clara para inicializar y ejecutar
 * la simulación donde seis usuarios (Alicia, Bob, César, Diego, Erika y Fausto) 
 * interactúan con cinco servicios de streaming (Memeflix, Momazon Prime Video, 
 * Spootify, Thisney+ y HVO Max) durante un año completo.
 * 
 * La simulación implementa los patrones Strategy y Observer para modelar
 * diferentes estrategias de cobro y notificaciones entre usuarios y servicios.
 * 
 * @author Equipo-Polimorfo
 */
public class Main {

    /**
     * Método principal que ejecuta la simulación completa de servicios de streaming.
     * 
     * Orquesta todo el flujo de la simulación: muestra un banner inicial,
     * configura la simulación, la ejecuta completamente y muestra un mensaje
     * de finalización exitosa.
     * 
     * @param args argumentos de línea de comandos (no utilizados en esta implementación)
     */
    public static void main(String[] args) {
        try {
            System.out.println("Iniciando simulación de servicios de streaming...\n");
            
            mostrarBanner();
            
            Simulacion simulacion = configurarSimulacion();
            
            System.out.println("\n Ejecutando simulación completa...\n");
            simulacion.ejecutarSimulacion();
            
            System.out.println("\n Simulación completada exitosamente.");
            System.out.println("Los reportes detallados han sido generados en archivos .txt");
            System.out.println("Revisa los archivos de salida para obtener análisis completos.");
            
        } catch (Exception e) {
            System.err.println("Error durante la ejecución de la simulación: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Muestra un banner con información sobre el proyecto y la simulación.
     * 
     * El banner incluye el título del proyecto, información del equipo,
     * detalles sobre los usuarios y servicios involucrados en la simulación,
     * y un diseño visual atractivo.
     */
    private static void mostrarBanner() {
        System.out.println("╔══════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                     SIMULACIÓN DE SERVICIOS DE STREAMING                     ║");
        System.out.println("║                              Práctica 01                                     ║");
        System.out.println("║                         Modelado y Programación                              ║");
        System.out.println("║                            Equipo Polimorfo                                  ║");
        System.out.println("╠══════════════════════════════════════════════════════════════════════════════╣");
        System.out.println("║                                                                              ║");
        System.out.println("║    USUARIOS: Alicia, Bob, César, Diego, Erika, Fausto                        ║");
        System.out.println("║                                                                              ║");
        System.out.println("║    SERVICIOS:                                                                ║");
        System.out.println("║     • Memeflix        (1, 2, 4 dispositivos)                                 ║");
        System.out.println("║     • Momazon Prime   (Normal, Premium)                                      ║");
        System.out.println("║     • Spootify        (Gratis, Premium)                                      ║");
        System.out.println("║     • Thisney+        (Plan único con incremento tarifario)                  ║");
        System.out.println("║     • HVO Max         (3 meses gratis, luego tarifa regular)                 ║");
        System.out.println("║                                                                              ║");
        System.out.println("║    DURACIÓN: 12 meses de simulación                                          ║");
        System.out.println("║    PATRONES: Strategy (cobros) + Observer (notificaciones)                   ║");
        System.out.println("║    SALIDA: Reportes detallados en archivos de texto                          ║");
        System.out.println("║                                                                              ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════════════════╝");
        System.out.println();
    }

    /**
     * Configura e inicializa una nueva instancia de la simulación.
     * 
     * Crea una nueva simulación configurada para comenzar en el mes 1
     * y lista para ejecutar el ciclo completo de 12 meses.
     * 
     * @return nueva instancia de {@link Simulacion} configurada y lista para ejecutar
     */
    private static Simulacion configurarSimulacion() {
        System.out.println("Configurando simulación...");
        System.out.println(" • Mes inicial: 1");
        System.out.println(" • Duración total: 12 meses");
        System.out.println(" • Patrones implementados: Strategy y Observer");
        
        return new Simulacion(1);
    }
}
