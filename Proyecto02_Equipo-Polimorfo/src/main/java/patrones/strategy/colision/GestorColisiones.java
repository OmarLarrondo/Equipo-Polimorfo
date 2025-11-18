package patrones.strategy.colision;

import java.util.Map;

import mvc.modelo.ModeloJuego;
import mvc.modelo.entidades.Bloque;
import mvc.modelo.entidades.ObjetoJuego;
import patrones.strategy.colision.EstrategiaColision;

/**
 * Gestor centralizado de colisiones que orquesta todas las estrategias.
 * <p>
 * Coordina la verificacion y manejo de colisiones entre todos los objetos del juego
 * usando el patron Strategy para delegar comportamientos especificos.
 * </p>
 *
 * @author Equipo-polimorfo
 * @version 1.0
 */
public class GestorColisiones {
    
    private Map<String, EstrategiaColision> estrategias;

    /**
     * Constructor que inicializa el gestor con un mapa de estrategias.
     *
     * @param estrategias mapa de estrategias indexadas por clave
     */
    public GestorColisiones(final Map<String, EstrategiaColision> estrategias) {
        this.estrategias = estrategias;
    }

    /**
     * Registra una nueva estrategia de colision en el gestor.
     *
     * @param clave identificador unico de la estrategia
     * @param estrategia implementacion de la estrategia
     */
    public void registrarEstrategia(final String clave, final EstrategiaColision estrategia) {
        io.vavr.control.Try.run(() -> {
            estrategias.put(clave, estrategia);
        });
    }

    /**
     * Verifica y maneja todas las colisiones del juego.
     * <p>
     * Orquesta las verificaciones de colisiones en el siguiente orden:
     * 1. Pelota con paredes (rebotes y puntos)
     * 2. Pelota con paletas
     * 3. Pelota con bloques (destruccion y generacion de items)
     * </p>
     *
     * @param modeloJuego modelo del juego con todas las entidades
     */
    public void verificarTodasColisiones(final ModeloJuego modeloJuego) {
        io.vavr.control.Try.run(() -> {
            // Obtener estrategias usando Vavr Option
            final io.vavr.control.Option<EstrategiaColision> estrategiaPared = 
                io.vavr.control.Option.of(estrategias.get("pelota-pared"));
            final io.vavr.control.Option<EstrategiaColision> estrategiaPaleta = 
                io.vavr.control.Option.of(estrategias.get("pelota-paleta"));
            final io.vavr.control.Option<EstrategiaColision> estrategiaBloque = 
                io.vavr.control.Option.of(estrategias.get("pelota-bloque"));

            // Obtener pelota del modelo y envolver en Option
            io.vavr.control.Option.of(modeloJuego.obtenerPelota()).forEach(pelota -> {
                
                // 1. Verificar colision con paredes
                estrategiaPared.forEach(estrategia -> {
                    if (estrategia.verificarColision(pelota, null)) {
                        // Guardar posicion antes de manejar colision
                        final double xAntes = pelota.obtenerX();
                        
                        estrategia.manejarColision(pelota, null);
                        
                        // Verificar si la pelota fue reiniciada (punto anotado)
                        if (estrategia instanceof EstrategiaColisionPelotaPared pared) {
                            if (pared.pelotaSalioPorIzquierda(pelota) && xAntes <= 0) {
                                // Punto para jugador 2
                                modeloJuego.incrementarPuntaje(2, 1);
                            } else if (pared.pelotaSalioPorDerecha(pelota)) {
                                // Punto para jugador 1
                                modeloJuego.incrementarPuntaje(1, 1);
                            }
                        }
                    }
                });

                // 2. Verificar colision con paleta jugador 1
                io.vavr.control.Option.of(modeloJuego.obtenerJugador1()).forEach(paleta1 -> {
                    estrategiaPaleta.forEach(estrategia -> {
                        if (estrategia.verificarColision(pelota, paleta1)) {
                            estrategia.manejarColision(pelota, paleta1);
                        }
                    });
                });

                // 3. Verificar colision con paleta jugador 2
                io.vavr.control.Option.of(modeloJuego.obtenerJugador2()).forEach(paleta2 -> {
                    estrategiaPaleta.forEach(estrategia -> {
                        if (estrategia.verificarColision(pelota, paleta2)) {
                            estrategia.manejarColision(pelota, paleta2);
                        }
                    });
                });

                // 4. Verificar colisiones con bloques
                final io.vavr.collection.List<Bloque> bloques = 
                    io.vavr.collection.List.ofAll(modeloJuego.obtenerBloques());
                
                bloques.filter(Bloque::estaActivo).forEach(bloque -> {
                    estrategiaBloque.forEach(estrategia -> {
                        if (estrategia.verificarColision(pelota, bloque)) {
                            estrategia.manejarColision(pelota, bloque);
                            
                            // Generar item si el bloque fue destruido y es BONUS
                            if (bloque.estaDestruido() && 
                                estrategia instanceof EstrategiaColisionPelotaBloque bloqueEstrategia) {
                                bloqueEstrategia.intentarGenerarItem(bloque)
                                    .forEach(modeloJuego::generarItem);
                            }
                        }
                    });
                });
            });
        });
    }

    /**
     * Maneja una colision especifica entre dos objetos del juego.
     * <p>
     * Metodo auxiliar que puede ser usado para manejar colisiones
     * especificas sin verificar primero si existe la colision.
     * </p>
     *
     * @param obj1 primer objeto de la colision
     * @param obj2 segundo objeto de la colision
     */
    public void manejarColision(final ObjetoJuego obj1, final ObjetoJuego obj2) {
        io.vavr.control.Try.run(() -> {
            // Intentar con todas las estrategias hasta encontrar una aplicable
            estrategias.values().stream()
                .filter(estrategia -> estrategia.verificarColision(obj1, obj2))
                .findFirst()
                .ifPresent(estrategia -> estrategia.manejarColision(obj1, obj2));
        });
    }
}
