package patrones.strategy.colision;

import mvc.modelo.entidades.ObjetoJuego;
import mvc.modelo.entidades.paleta.Paleta;
import mvc.modelo.entidades.pelota.Pelota;

/**
 * Estrategia de colision entre pelota y paleta.
 * <p>
 * Implementa el patron Strategy para calcular rebotes dinamicos basados
 * en el punto de impacto en la paleta, generando angulos variables que
 * añaden profundidad al gameplay.
 * </p>
 *
 * @author Equipo-polimorfo
 * @version 1.0
 */
public class EstrategiaColisionPelotaPaleta implements EstrategiaColision {

    /**
     * Angulo maximo de rebote en radianes (60 grados).
     */
    private static final double ANGULO_MAXIMO = Math.toRadians(60);

    /**
     * Factor de incremento de velocidad al rebotar (5%).
     */
    private static final double FACTOR_ACELERACION = 1.05;

    /**
     * Maneja la colision entre pelota y paleta.
     * <p>
     * Calcula el angulo de rebote basado en el punto de impacto,
     * invierte la direccion horizontal de la pelota y aplica
     * un pequeno incremento de velocidad.
     * </p>
     *
     * @param obj1 primer objeto (pelota o paleta)
     * @param obj2 segundo objeto (paleta o pelota)
     */
    @Override
    public void manejarColision(final ObjetoJuego obj1, final ObjetoJuego obj2) {
        io.vavr.control.Try.run(() -> {
            // Determinar cual es la pelota y cual es la paleta
            final io.vavr.control.Option<Pelota> pelotaOpt = io.vavr.control.Option.of(obj1)
                .filter(o -> o instanceof Pelota)
                .map(o -> (Pelota) o)
                .orElse(() -> io.vavr.control.Option.of(obj2)
                    .filter(o -> o instanceof Pelota)
                    .map(o -> (Pelota) o));

            final io.vavr.control.Option<Paleta> paletaOpt = io.vavr.control.Option.of(obj1)
                .filter(o -> o instanceof Paleta)
                .map(o -> (Paleta) o)
                .orElse(() -> io.vavr.control.Option.of(obj2)
                    .filter(o -> o instanceof Paleta)
                    .map(o -> (Paleta) o));

            // Aplicar logica de colision usando programacion funcional
            pelotaOpt.flatMap(pelota -> paletaOpt.map(paleta -> {
                // Calcular nuevo angulo de rebote
                final double nuevoAngulo = calcularAnguloRebote(pelota, paleta);
                
                // Obtener velocidad actual y calcular nueva velocidad
                final double velocidadActual = Math.sqrt(
                    Math.pow(pelota.obtenerX(), 2) + 
                    Math.pow(pelota.obtenerY(), 2)
                );
                final double nuevaVelocidad = velocidadActual * FACTOR_ACELERACION;
                
                // Aplicar nuevo angulo y velocidad
                pelota.establecerVelocidadX(nuevaVelocidad * Math.cos(nuevoAngulo));
                pelota.establecerVelocidadY(nuevaVelocidad * Math.sin(nuevoAngulo));
                
                // Invertir direccion horizontal para que rebote
                pelota.invertirX();
                
                return pelota;
            }));
        });
    }

    /**
     * Verifica si hay colision entre pelota y paleta usando AABB.
     * <p>
     * Implementa deteccion de colision Axis-Aligned Bounding Box,
     * verificando superposicion de rectangulos en ambos ejes.
     * </p>
     *
     * @param obj1 primer objeto (pelota o paleta)
     * @param obj2 segundo objeto (paleta o pelota)
     * @return true si hay colision, false en caso contrario
     */
    @Override
    public boolean verificarColision(final ObjetoJuego obj1, final ObjetoJuego obj2) {
        return io.vavr.control.Try.of(() -> {
            // Determinar cual es la pelota y cual es la paleta
            final io.vavr.control.Option<Pelota> pelotaOpt = io.vavr.control.Option.of(obj1)
                .filter(o -> o instanceof Pelota)
                .map(o -> (Pelota) o)
                .orElse(() -> io.vavr.control.Option.of(obj2)
                    .filter(o -> o instanceof Pelota)
                    .map(o -> (Pelota) o));

            final io.vavr.control.Option<Paleta> paletaOpt = io.vavr.control.Option.of(obj1)
                .filter(o -> o instanceof Paleta)
                .map(o -> (Paleta) o)
                .orElse(() -> io.vavr.control.Option.of(obj2)
                    .filter(o -> o instanceof Paleta)
                    .map(o -> (Paleta) o));

            // Verificar colision usando AABB
            return pelotaOpt.flatMap(pelota -> paletaOpt.map(paleta -> {
                final double pelotaX = pelota.obtenerX();
                final double pelotaY = pelota.obtenerY();
                final double pelotaRadio = pelota.obtenerAncho() / 2.0;
                
                final double paletaX = paleta.obtenerX();
                final double paletaY = paleta.obtenerY();
                final double paletaAncho = 20.0; // Ancho de paleta
                final double paletaAlto = paleta.obtenerAlto();

                // AABB collision detection
                return (pelotaX + pelotaRadio >= paletaX) &&
                       (pelotaX - pelotaRadio <= paletaX + paletaAncho) &&
                       (pelotaY + pelotaRadio >= paletaY) &&
                       (pelotaY - pelotaRadio <= paletaY + paletaAlto);
            })).getOrElse(false);
        }).getOrElse(false);
    }
    
    /**
     * Calcula el angulo de rebote basado en el punto de impacto en la paleta.
     * <p>
     * El angulo varia segun donde impacta la pelota:
     * - Centro de la paleta: angulo mas recto (cerca de 0 grados)
     * - Bordes de la paleta: angulo mas agudo (hasta 60 grados)
     * </p>
     *
     * @param pelota la pelota que impacta
     * @param paleta la paleta impactada
     * @return angulo de rebote en radianes
     */
    private double calcularAnguloRebote(final Pelota pelota, final Paleta paleta) {
        return io.vavr.control.Try.of(() -> {
            // Calcular punto de impacto relativo (0.0 = centro, -1.0 = arriba, 1.0 = abajo)
            final double puntoImpacto = (pelota.obtenerY() - (paleta.obtenerY() + paleta.obtenerAlto() / 2.0)) 
                                      / (paleta.obtenerAlto() / 2.0);
            
            // Limitar punto de impacto entre -1.0 y 1.0
            final double puntoImpactoLimitado = Math.max(-1.0, Math.min(1.0, puntoImpacto));
            
            // Calcular angulo proporcional (-60 a 60 grados)
            return puntoImpactoLimitado * ANGULO_MAXIMO;
        }).getOrElse(0.0);
    }
}
