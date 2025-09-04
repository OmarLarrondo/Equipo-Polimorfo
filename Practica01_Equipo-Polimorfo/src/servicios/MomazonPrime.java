package servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import modelo.Usuario;
import patrones.EstrategiaCobro;
import patrones.Observer;

/**
 * Clase MomazonPrime, servicio de streaming con dos planes diferentes.
 * Planes disponibles:
 * - Normal: $110
 * - Premium: $150
 */
public class MomazonPrime extends Servicio{

    /**
     * Constructor para inicializar el servicio MomazonPrime
     * @param nombre Nombre del servicio (debería ser "Momazon Prime Video")
     */
    public MomazonPrime(String nombre) {
        super(nombre);
        inicializarRecomendaciones();
    }

    /**
     * Obtiene las estrategias de cobro disponibles para MomazonPrime
     * @return Lista con las descripciones de los planes disponibles
     */
    @Override
    public List<String> obtenerEstrategiasDisponibles(){
        return Arrays.asList(
            "normal",
            "premium"
        );
    }

    /**
     * Obtiene la recomendación específica para un mes dado
     * @param mes El número de mes (1-12) para el cual se solicita la recomendación
     * @return String La recomendación de contenido para el mes especificado
     */
    @Override
    public String obtenerRecomendacion(int mes){
        List<String> recomendaciones = recomendacionesPorMes.get(mes);
        if (recomendaciones != null && !recomendaciones.isEmpty()) {
            return recomendaciones.get(0);
        }
        return "Contenido original de Momazon Prime";
    }

    /**
     * Inicializa las recomendaciones mensuales específicas de MomazonPrime
     * Cada mes tiene contenido diferente enfocado en producciones originales
     */
    @Override
    public void inicializarRecomendaciones(){
        // Enero
        recomendacionesPorMes.put(1, Arrays.asList("The Boys", "Marvelous Mrs. Maisel"));
        // Febrero  
        recomendacionesPorMes.put(2, Arrays.asList("Jack Ryan", "The Man in the High Castle"));
        // Marzo
        recomendacionesPorMes.put(3, Arrays.asList("Fleabag", "Good Omens"));
        // Abril
        recomendacionesPorMes.put(4, Arrays.asList("The Expanse", "Hunters"));
        // Mayo
        recomendacionesPorMes.put(5, Arrays.asList("Upload", "Tales from the Loop"));
        // Junio
        recomendacionesPorMes.put(6, Arrays.asList("The Wheel of Time", "Invincible"));
        // Julio
        recomendacionesPorMes.put(7, Arrays.asList("Bosch", "Mozart in the Jungle"));
        // Agosto
        recomendacionesPorMes.put(8, Arrays.asList("Transparent", "Catastrophe"));
        // Septiembre
        recomendacionesPorMes.put(9, Arrays.asList("The Grand Tour", "American Gods"));
        // Octubre
        recomendacionesPorMes.put(10, Arrays.asList("Carnival Row", "Undone"));
        // Noviembre
        recomendacionesPorMes.put(11, Arrays.asList("The Terminal List", "Reacher"));
        // Diciembre
        recomendacionesPorMes.put(12, Arrays.asList("The Power", "Citadel"));
    }
}
