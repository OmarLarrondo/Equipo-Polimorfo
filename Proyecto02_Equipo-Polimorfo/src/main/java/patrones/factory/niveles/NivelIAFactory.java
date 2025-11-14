package patrones.factory.niveles;

import mvc.modelo.entidades.Bloque;
import patrones.builder.TipoBloque;
import mvc.modelo.entidades.Paleta;

/**
 * Implementación concreta de {@link NivelFactory} que crea un nivel
 * en el modo "Jugador vs IA" (inteligencia artificial).
 * 
 * <p>
 * Este nivel genera una configuración en la que un jugador humano
 * se enfrenta a una paleta controlada por la IA. La dificultad,
 * el patrón de bloques y otros parámetros se definen mediante
 * una instancia de {@link ConfigNivel}.
 * </p>
 * 
 * <p><b>Nota:</b> Falta implementar la interpretación del patrón de bloques,
 * así como la integración del controlador de IA para la paleta.</p>
 * 
 * @author  Equipo-polimorfo
 * @version 1.0
 */
public class NivelIAFactory implements NivelFactory{

    /**{@inheritDoc} */
    @Override
    public Nivel crearNivel(ConfigNivel conf) {
        Nivel nivelInteligente = new Nivel();
        nivelInteligente.setNombre("Nivel VS IA. -- Dificultad: "+ conf.obtenerDificultad());
        nivelInteligente.setDificultad(conf.getDificultad());
        //OBVIO NO?
        nivelInteligente.setMapaPersonalizado(false);
        nivelInteligente.setCreador("Sistema- Equipo-polimorfo");

        Paleta paletaJugador = new Paleta(50,300, 20,100);
        Paleta paletaIA = new Paleta(50,300, 20,100);
        //paletaIA.setControlador(new ControlPaletaIA(conf.obtenerDificultad()));
        
        //CHECAR COMO ES ESTA PARTE, SAUL DIJO QUE UNA MATRIZ(CREO)
        //SUPONGAMOS QUE EL PATRON ES TIPO: [XXXX-XX, NO SE]
        String patron = conf.obtenerPatron();
        if (patron != null && !patron.isEmpty()) {
            //FALTA IMPLEMNETAR ESTA PARTE, DEPENDIENDO COMO ESTE EL PATRON ESCRITO, NO SE COMO ESTARA. ENTONCES NO ES POSIBLE HACERLO.
            agregarBloquesDesdePatron(patron, nivelInteligente);
        } else {
            //DEFAULT
            for (int i = 0; i < 5; i++) {
                // Suponia agregar 5 bloques, no se coo seira
                Bloque bloque = new Bloque(50 + i * 55, 50, 50, 20, 1, TipoBloque.DESTRUCTIBLE);
                nivelInteligente.agregarBloque(bloque);
            }
        }

        return nivelInteligente;
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
