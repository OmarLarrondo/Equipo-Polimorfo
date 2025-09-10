package servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import modelo.Usuario;
import patrones.EstrategiaCobro;
import patrones.Observer;

/**
 * Clase HVOMax, servicio de streaming con período de prueba gratuito.
 * Plan único:
 * - Primeros 3 meses: Gratis
 * - Del cuarto mes en adelante: $140
 */
public class HVOMax extends Servicio{
    
    /**
     * Constructor para inicializar el servicio HVOMax
     * @param nombre Nombre del servicio (debería ser "HVO Max")
     */
    public HVOMax(String nombre) {
        super(nombre);
        inicializarRecomendaciones();
    }

    /**
     * Obtiene las estrategias de cobro disponibles para HVOMax
     * @return Lista con la descripción del plan único disponible
     */
    @Override
    public List<String> obtenerEstrategiasDisponibles() {
        return Arrays.asList("plan único");
    }

    /**
     * Obtiene la recomendación específica para un mes dado
     * @param mes El número de mes (1-12) para el cual se solicita la recomendación
     * @return String La recomendación de contenido premium para el mes especificado
     */
    @Override
    public String obtenerRecomendacion(int mes) {
        List<String> recomendaciones = recomendacionesPorMes.get(mes);
        if (recomendaciones != null && !recomendaciones.isEmpty()) {
            return recomendaciones.get(0);
        }
        return "Contenido premium de HVO Max";
    }

    /**
     * Inicializa las recomendaciones mensuales específicas de HVOMax
     * Cada mes tiene contenido premium y series exclusivas
     */
    @Override
    public void inicializarRecomendaciones() {
        // Enero
        recomendacionesPorMes.put(1, Arrays.asList("Game of Thrones", "Succession"));
        // Febrero  
        recomendacionesPorMes.put(2, Arrays.asList("The White Lotus", "Euphoria"));
        // Marzo
        recomendacionesPorMes.put(3, Arrays.asList("Barry", "Insecure"));
        // Abril
        recomendacionesPorMes.put(4, Arrays.asList("Mare of Easttown", "Hacks"));
        // Mayo
        recomendacionesPorMes.put(5, Arrays.asList("The Nevers", "In Treatment"));
        // Junio
        recomendacionesPorMes.put(6, Arrays.asList("The Sopranos", "Sex and the City"));
        // Julio
        recomendacionesPorMes.put(7, Arrays.asList("True Detective", "Westworld"));
        // Agosto
        recomendacionesPorMes.put(8, Arrays.asList("The Wire", "Veep"));
        // Septiembre
        recomendacionesPorMes.put(9, Arrays.asList("Curb Your Enthusiasm", "Silicon Valley"));
        // Octubre
        recomendacionesPorMes.put(10, Arrays.asList("Lovecraft Country", "I May Destroy You"));
        // Noviembre
        recomendacionesPorMes.put(11, Arrays.asList("The Flight Attendant", "And Just Like That"));
        // Diciembre
        recomendacionesPorMes.put(12, Arrays.asList("The Last of Us", "House of the Dragon"));
    }
}
