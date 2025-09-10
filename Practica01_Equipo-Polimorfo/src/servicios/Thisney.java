package servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import modelo.Usuario;
import patrones.EstrategiaCobro;
import patrones.Observer;

/**
 * Clase Thisney, servicio de streaming con tarifa progresiva.
 * Plan único:
 * - Primeros 3 meses: $130
 * - Del cuarto mes en adelante: $160
 */
public class Thisney extends Servicio{
    
    /**
     * Constructor para inicializar el servicio Thisney
     * @param nombre Nombre del servicio (debería ser "Thisney+")
     */
    public Thisney(String nombre) {
        super(nombre);
        inicializarRecomendaciones();
    }

    /**
     * Obtiene las estrategias de cobro disponibles para Thisney
     * @return Lista con la descripción del plan único disponible
     */
    @Override
    public List<String> obtenerEstrategiasDisponibles(){
        return Arrays.asList("plan único");
    }

    /**
     * Obtiene la recomendación específica para un mes dado
     * @param mes El número de mes (1-12) para el cual se solicita la recomendación
     * @return String La recomendación de contenido Disney para el mes especificado
     */
    @Override
    public String obtenerRecomendacion(int mes){
        List<String> recomendaciones = recomendacionesPorMes.get(mes);
        if (recomendaciones != null && !recomendaciones.isEmpty()) {
            return recomendaciones.get(0);
        }
        return "Contenido familiar de Thisney";
    }

    /**
     * Inicializa las recomendaciones mensuales específicas de Thisney
     * Cada mes tiene contenido familiar y clásicos Disney
     */
    @Override
    public void inicializarRecomendaciones(){
        // Enero
        recomendacionesPorMes.put(1, Arrays.asList("Frozen II", "The Mandalorian"));
        // Febrero  
        recomendacionesPorMes.put(2, Arrays.asList("WandaVision", "Soul"));
        // Marzo
        recomendacionesPorMes.put(3, Arrays.asList("The Falcon and The Winter Soldier", "Raya and the Last Dragon"));
        // Abril
        recomendacionesPorMes.put(4, Arrays.asList("The Bad Batch", "Cruella"));
        // Mayo
        recomendacionesPorMes.put(5, Arrays.asList("Loki", "Black Widow"));
        // Junio
        recomendacionesPorMes.put(6, Arrays.asList("Luca", "What If...?"));
        // Julio
        recomendacionesPorMes.put(7, Arrays.asList("Jungle Cruise", "Turner & Hooch"));
        // Agosto
        recomendacionesPorMes.put(8, Arrays.asList("Free Guy", "The Book of Boba Fett"));
        // Septiembre
        recomendacionesPorMes.put(9, Arrays.asList("Shang-Chi", "Hawkeye"));
        // Octubre
        recomendacionesPorMes.put(10, Arrays.asList("Eternals", "Dopesick"));
        // Noviembre
        recomendacionesPorMes.put(11, Arrays.asList("Encanto", "The Beatles: Get Back"));
        // Diciembre
        recomendacionesPorMes.put(12, Arrays.asList("Spider-Man: No Way Home", "The Book of Boba Fett"));
    }
}

