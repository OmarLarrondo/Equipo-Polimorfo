package servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import modelo.Usuario;
import patrones.EstrategiaCobro;
import patrones.Observer;

/**
 * Clase Spootify, servicio de música streaming con dos planes.
 * Planes disponibles:
 * - Normal: Gratis (con publicidad)
 * - Premium: $80 (sin publicidad y funciones extra)
 */
public class Spootify extends Servicio {
    
    /**
     * Constructor para inicializar el servicio Spootify
     * @param nombre Nombre del servicio (debería ser "Spootify")
     */
    public Spootify(String nombre) {
        super(nombre);
        inicializarRecomendaciones();
    }

    /**
     * Obtiene las estrategias de cobro disponibles para Spootify
     * @return Lista con las descripciones de los planes disponibles
     */
    @Override
    public List<String> obtenerEstrategiasDisponibles(){
        return Arrays.asList(
            "versión normal",
            "versión premium"
        );
    }

    /**
     * Obtiene la recomendación específica para un mes dado
     * @param mes El número de mes (1-12) para el cual se solicita la recomendación
     * @return String La recomendación musical para el mes especificado
     */
    @Override
    public String obtenerRecomendacion(int mes){
        List<String> recomendaciones = recomendacionesPorMes.get(mes);
        if (recomendaciones != null && !recomendaciones.isEmpty()) {
            return recomendaciones.get(0);
        }
        return "Playlist personalizada de Spootify";
    }

    /**
     * Inicializa las recomendaciones mensuales específicas de Spootify
     * Cada mes tiene diferentes artistas y géneros musicales
     */
    @Override
    public void inicializarRecomendaciones(){
        // Enero
        recomendacionesPorMes.put(1, Arrays.asList("Playlist: Top Hits 2024", "Bad Bunny - Nuevo Album"));
        // Febrero  
        recomendacionesPorMes.put(2, Arrays.asList("Playlist: Amor y Romance", "Taylor Swift - Love Songs"));
        // Marzo
        recomendacionesPorMes.put(3, Arrays.asList("Playlist: Spring Vibes", "Dua Lipa - Dance Collection"));
        // Abril
        recomendacionesPorMes.put(4, Arrays.asList("Playlist: Workout Mix", "The Weeknd - Greatest Hits"));
        // Mayo
        recomendacionesPorMes.put(5, Arrays.asList("Playlist: Reggaeton Hits", "J Balvin - Colores"));
        // Junio
        recomendacionesPorMes.put(6, Arrays.asList("Playlist: Summer Festival", "Harry Styles - As It Was"));
        // Julio
        recomendacionesPorMes.put(7, Arrays.asList("Playlist: Rock Clásico", "Queen - Greatest Hits"));
        // Agosto
        recomendacionesPorMes.put(8, Arrays.asList("Playlist: Hip Hop Evolution", "Drake - Certified Lover Boy"));
        // Septiembre
        recomendacionesPorMes.put(9, Arrays.asList("Playlist: Back to School", "Olivia Rodrigo - SOUR"));
        // Octubre
        recomendacionesPorMes.put(10, Arrays.asList("Playlist: Halloween Hits", "Billie Eilish - Happier Than Ever"));
        // Noviembre
        recomendacionesPorMes.put(11, Arrays.asList("Playlist: Indie Folk", "Lana Del Rey - Blue Banisters"));
        // Diciembre
        recomendacionesPorMes.put(12, Arrays.asList("Playlist: Holiday Classics", "Mariah Carey - All I Want for Christmas"));
    }
}
