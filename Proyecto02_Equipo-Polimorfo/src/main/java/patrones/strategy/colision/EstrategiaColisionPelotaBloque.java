package patrones.strategy.colision;

import io.vavr.control.Option;
import mvc.modelo.entidades.Bloque;
import mvc.modelo.entidades.ObjetoJuego;
import mvc.modelo.entidades.pelota.Pelota;
import mvc.modelo.items.Item;
import patrones.builder.TipoBloque;
import patrones.factory.items.FabricaItems;

/**
 * Estrategia de colision entre pelota y bloque.
 * <p>
 * Implementa el patron Strategy para manejar colisiones especificas.
 * Gestiona la destruccion de bloques, generacion de items y rebote de la pelota.
 * </p>
 *
 * @author Equipo-polimorfo
 * @version 1.0
 */
public class EstrategiaColisionPelotaBloque implements EstrategiaColision {

    /**
     * Probabilidad de generacion de item al destruir bloque BONUS (0.0 - 1.0).
     */
    private static final double PROBABILIDAD_ITEM = 0.2;

    /**
     * Maneja la colision entre una pelota y un bloque.
     * <p>
     * Reduce la resistencia del bloque, invierte la velocidad de la pelota
     * y desactiva el bloque si su resistencia llega a cero.
     * La generacion de items debe ser manejada externamente por el GestorColisiones.
     * </p>
     *
     * @param obj1 primer objeto de la colision (pelota)
     * @param obj2 segundo objeto de la colision (bloque)
     */
    @Override
    public void manejarColision(final ObjetoJuego obj1, final ObjetoJuego obj2) {
        io.vavr.control.Try.run(() -> {
            // Determinar cual es la pelota y cual es el bloque
            final io.vavr.control.Option<Pelota> pelotaOpt = io.vavr.control.Option.of(obj1)
                .filter(o -> o instanceof Pelota)
                .map(o -> (Pelota) o)
                .orElse(() -> io.vavr.control.Option.of(obj2)
                    .filter(o -> o instanceof Pelota)
                    .map(o -> (Pelota) o));

            final io.vavr.control.Option<Bloque> bloqueOpt = io.vavr.control.Option.of(obj1)
                .filter(o -> o instanceof Bloque)
                .map(o -> (Bloque) o)
                .orElse(() -> io.vavr.control.Option.of(obj2)
                    .filter(o -> o instanceof Bloque)
                    .map(o -> (Bloque) o));

            // Aplicar logica de colision usando programacion funcional
            pelotaOpt.flatMap(pelota -> bloqueOpt.map(bloque -> {
                // Reducir resistencia del bloque
                bloque.reducirResistencia();
                
                // Calcular direccion del rebote basado en posicion relativa
                final double pelotaCentroX = pelota.obtenerX();
                final double pelotaCentroY = pelota.obtenerY();
                final double bloqueCentroX = bloque.obtenerX() + bloque.obtenerAncho() / 2.0;
                final double bloqueCentroY = bloque.obtenerY() + bloque.obtenerAlto() / 2.0;
                
                // Calcular diferencias
                final double deltaX = Math.abs(pelotaCentroX - bloqueCentroX);
                final double deltaY = Math.abs(pelotaCentroY - bloqueCentroY);
                
                // Determinar si rebota horizontalmente o verticalmente
                if (deltaX > deltaY) {
                    // Colision por el lado (izquierda o derecha)
                    pelota.invertirX();
                } else {
                    // Colision por arriba o abajo
                    pelota.invertirY();
                }
                
                // Desactivar bloque si fue destruido
                if (bloque.estaDestruido()) {
                    bloque.establecerActivo(false);
                }
                
                return bloque;
            }));
        });
    }

    /**
     * Verifica si hay colision entre pelota y bloque usando AABB.
     * <p>
     * Solo detecta colisiones con bloques activos (no destruidos).
     * Implementa deteccion de colision Axis-Aligned Bounding Box.
     * </p>
     *
     * @param obj1 primer objeto
     * @param obj2 segundo objeto
     * @return true si hay colision, false en caso contrario
     */
    @Override
    public boolean verificarColision(final ObjetoJuego obj1, final ObjetoJuego obj2) {
        return io.vavr.control.Try.of(() -> {
            // Determinar cual es la pelota y cual es el bloque
            final io.vavr.control.Option<Pelota> pelotaOpt = io.vavr.control.Option.of(obj1)
                .filter(o -> o instanceof Pelota)
                .map(o -> (Pelota) o)
                .orElse(() -> io.vavr.control.Option.of(obj2)
                    .filter(o -> o instanceof Pelota)
                    .map(o -> (Pelota) o));

            final io.vavr.control.Option<Bloque> bloqueOpt = io.vavr.control.Option.of(obj1)
                .filter(o -> o instanceof Bloque)
                .map(o -> (Bloque) o)
                .orElse(() -> io.vavr.control.Option.of(obj2)
                    .filter(o -> o instanceof Bloque)
                    .map(o -> (Bloque) o));

            // Verificar colision solo si el bloque esta activo
            final Boolean resultado = pelotaOpt.flatMap(pelota -> bloqueOpt
                .filter(Bloque::estaActivo)
                .map(bloque -> {
                    final double pelotaX = pelota.obtenerX();
                    final double pelotaY = pelota.obtenerY();
                    final double pelotaRadio = pelota.obtenerAncho() / 2.0;

                    final double bloqueX = bloque.obtenerX();
                    final double bloqueY = bloque.obtenerY();
                    final double bloqueAncho = bloque.obtenerAncho();
                    final double bloqueAlto = bloque.obtenerAlto();

                    // AABB collision detection
                    final boolean colisiona = (pelotaX + pelotaRadio >= bloqueX) &&
                                              (pelotaX - pelotaRadio <= bloqueX + bloqueAncho) &&
                                              (pelotaY + pelotaRadio >= bloqueY) &&
                                              (pelotaY - pelotaRadio <= bloqueY + bloqueAlto);
                    return Boolean.valueOf(colisiona);
                })
            ).getOrElse(Boolean.FALSE);

            return resultado.booleanValue();
        }).getOrElse(false);
    }

    /**
     * Genera un item de forma aleatoria si el bloque es de tipo BONUS.
     * <p>
     * Implementa programacion funcional pura usando Vavr.
     * El item aparece en la posicion del bloque destruido.
     * </p>
     *
     * @param bloque bloque que fue destruido
     * @return Option conteniendo el item generado si corresponde, Option.none() en caso contrario
     */
    private io.vavr.control.Option<Item> generarItem(final Bloque bloque) {
        return io.vavr.control.Option.of(bloque)
            .filter(b -> b.obtenerTipo() == TipoBloque.BONUS)
            .filter(b -> Math.random() < PROBABILIDAD_ITEM)
            .flatMap(b -> io.vavr.control.Option.ofOptional(
                FabricaItems.generarItemAleatorioEn(
                    b.obtenerX(),
                    b.obtenerY()
                ).toJavaOptional()
            ));
    }

    /**
     * Genera un item si el bloque cumple las condiciones.
     * <p>
     * Metodo publico que puede ser usado externamente para generar items
     * despues de que un bloque es destruido.
     * </p>
     *
     * @param bloque bloque del cual generar el item
     * @return Option conteniendo el item generado o Option.none()
     */
    public io.vavr.control.Option<Item> intentarGenerarItem(final Bloque bloque) {
        return generarItem(bloque);
    }
}
