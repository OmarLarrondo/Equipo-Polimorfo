package servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import modelo.Usuario;
import patrones.EstrategiaCobro;
import patrones.Observer;

/**
 * Clase Memeflix, servicio de streaming que ofrece múltiples planes 
 * basados en el número de dispositivos simultáneos.
 * Planes disponibles:
 * - 1 dispositivo: $120
 * - 2 dispositivos: $170  
 * - 4 dispositivos: $200
 */
public class Memeflix extends Servicio{

    /**
     * Constructor para inicializar el servicio Memeflix
     * @param nombre Nombre del servicio (debería ser "Memeflix")
     */
    public Memeflix(String nombre) {
        super(nombre);
        inicializarRecomendaciones();
    }

    /**
     * Obtiene las estrategias de cobro disponibles para Memeflix
     * @return Lista con las descripciones de los planes disponibles
     */
    @Override
    public List<String> obtenerEstrategiasDisponibles(){
        return Arrays.asList(
            "1 dispositivo",
            "2 dispositivos", 
            "4 dispositivos"
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
            // Devolver la primera recomendación del mes
            return recomendaciones.get(0);
        }
        return "Contenido exclusivo de Memeflix";
    }

    /**
     * Inicializa las recomendaciones mensuales específicas de Memeflix
     * Cada mes tiene contenido diferente para mantener el interés de los usuarios
     */
    @Override
    public void inicializarRecomendaciones(){
        // Enero
        recomendacionesPorMes.put(1, Arrays.asList("Casa de Naipes", "Stranger Things"));
        // Febrero  
        recomendacionesPorMes.put(2, Arrays.asList("The Crown", "Narcos"));
        // Marzo
        recomendacionesPorMes.put(3, Arrays.asList("Black Mirror", "Orange is the New Black"));
        // Abril
        recomendacionesPorMes.put(4, Arrays.asList("Breaking Bad", "Better Call Saul"));
        // Mayo
        recomendacionesPorMes.put(5, Arrays.asList("The Umbrella Academy", "Dark"));
        // Junio
        recomendacionesPorMes.put(6, Arrays.asList("Ozark", "Mindhunter"));
        // Julio
        recomendacionesPorMes.put(7, Arrays.asList("Money Heist", "Elite"));
        // Agosto
        recomendacionesPorMes.put(8, Arrays.asList("The Witcher", "Lucifer"));
        // Septiembre
        recomendacionesPorMes.put(9, Arrays.asList("Squid Game", "All of Us Are Dead"));
        // Octubre
        recomendacionesPorMes.put(10, Arrays.asList("The Haunting of Hill House", "Midnight Mass"));
        // Noviembre
        recomendacionesPorMes.put(11, Arrays.asList("The Queen's Gambit", "Bridgerton"));
        // Diciembre
        recomendacionesPorMes.put(12, Arrays.asList("Emily in Paris", "The Christmas Chronicles"));
    }
}
