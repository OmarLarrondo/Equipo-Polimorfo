package patrones.factory.niveles;

import mvc.modelo.entidades.Bloque;
import patrones.builder.TipoBloque;
import patrones.factory.ia.ConfigNivel;
import mvc.modelo.entidades.Paleta;

/**
 * Implementación concreta de {@link NivelFactory} que crea instancias del nivel
 * clásico (1 vs 1).
 * 
 * <p>
 * Esta fábrica se encarga de construir un nivel clásico con las configuraciones
 * básicas para dos jugadores, agregando bloques predeterminados o basados en un
 * patrón definido en la configuración {@link ConfigNivel}.
 * </p>
 * 
 * @author  Equipo-polimorfo
 * @version 1.0
 */
public class NivelClasicoFactory implements NivelFactory {

    /**
     * Crea un nivel clásico (1 vs 1) NO IA, a partir de una configuración dada.
     * 
     * <p>Se establecen propiedades por defecto (nombre, dificultad, creador, etc.)
     * y se agregan bloques según el patrón proporcionado en {@code conf}.</p>
     * 
     * @param conf la configuración del nivel, que incluye la dificultad y un patrón opcional.
     * @return una instancia de {@link Nivel} configurada como "Nivel Clásico".
     */
    @Override
    public Nivel crearNivel(ConfigNivel conf) {
        Nivel nivelClasico = new Nivel();

        nivelClasico.setNombre("Nivel Clásico(1vs1)");
        nivelClasico.setDificultad(conf.obtenerDificultad());
        nivelClasico.setMapaPersonalizado(false); 
        nivelClasico.setCreador("Sistema-Equipo-polimorfo");

        //checar donde meter resto LOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOL
        Paleta paletaPlayer1 = new Paleta(50,300, 20,100);
        Paleta paletaPLayer2 = new Paleta(50,300, 20,100);

        
        //CHECAR COMO ES ESTA PARTE, SAUL DIJO QUE UNA MATRIZ(CREO)
        //SUPONGAMOS QUE EL PATRON ES TIPO: [XXXX-XX, NO SE]
        String patron = conf.obtenerPatron();
        if (patron != null && !patron.isEmpty()) {
            //FALTA IMPLEMNETAR ESTA PARTE, DEPENDIENDO COMO ESTE EL PATRON ESCRITO, NO SE COMO ESTARA. ENTONCES NO ES POSIBLE HACERLO.
            agregarBloquesDesdePatron(patron, nivelClasico);
        } else {
            for (int i = 0; i < 5; i++) {
                // Suponia agregar 5 bloques, no se coo seira
                Bloque bloque = new Bloque(50 + i * 55, 50, 50, 20, 1, TipoBloque.DESTRUCTIBLE);
                nivelClasico.agregarBloque(bloque);
            }
        }

        return nivelClasico;
    }

    /**
     * Método auxiliar para interpretar el patrón de bloques definido en la configuración
     * y agregar los bloques correspondientes al nivel.
     * 
     * @param patron cadena que representa el patrón del mapa (por ejemplo, una matriz de caracteres).
     * @param nivel el nivel al que se agregarán los bloques interpretados.
     */
    //FALTA IMPLEMNTAR
    private void agregarBloquesDesdePatron(String patron, Nivel nivel) {
        // Interpretar patron y agregar bloques al nivel
        //falta implemnetar de acuerdo como este el patron string, ya sea
        //filtrar, cuando detetcte un _. 
    }
}
