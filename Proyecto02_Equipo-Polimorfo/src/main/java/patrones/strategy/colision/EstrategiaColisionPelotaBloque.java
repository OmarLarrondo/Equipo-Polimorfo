package patrones.strategy.colision;

import io.vavr.control.Option;
import mvc.modelo.entidades.Bloque;
import mvc.modelo.entidades.ObjetoJuego;
import mvc.modelo.items.Item;
import patrones.builder.TipoBloque;
import patrones.factory.items.FabricaItems;

/**
 * Estrategia de colision entre pelota y bloque.
 * Implementa el patron Strategy para manejar colisiones especificas.
 */
public class EstrategiaColisionPelotaBloque implements EstrategiaColision{

    /**
     * Probabilidad de generacion de item al destruir bloque BONUS (0.0 - 1.0).
     */
    private static final double PROBABILIDAD_ITEM = 0.7;

    /**
     * Maneja la colision entre una pelota y un bloque.
     * Cuando se implemente completamente, debera:
     * - Calcular la direccion de rebote de la pelota
     * - Decrementar la resistencia del bloque
     * - Generar items si el bloque es tipo BONUS
     *
     * @param obj1 primer objeto de la colision (pelota)
     * @param obj2 segundo objeto de la colision (bloque)
     */
    @Override
    public void manejarColision(ObjetoJuego obj1, ObjetoJuego obj2) {
        // aqui va su codigo de manejo de colision
        // Cuando se implemente, usar:
        // if (obj2 instanceof Bloque bloque) {
        //     generarItem(bloque).forEach(item -> modelo.generarItem(item));
        // }
    }

    /**
     * Verifica si hay colision entre dos objetos.
     *
     * @param obj1 primer objeto
     * @param obj2 segundo objeto
     * @return true si hay colision, false en caso contrario
     */
    @Override
    public boolean verificarColision(ObjetoJuego obj1, ObjetoJuego obj2) {
        // aqui va su codigo
        return false;
    }

    /**
     * Genera un item de forma aleatoria si el bloque es de tipo BONUS.
     * Implementa programacion funcional pura usando Vavr.
     * El item aparece en la posicion del bloque destruido.
     *
     * @param bloque bloque que fue destruido
     * @return Option conteniendo el item generado si corresponde, Option.none() en caso contrario
     */
    private Option<Item> generarItem(Bloque bloque) {
        return Option.of(bloque)
            .filter(b -> b.obtenerTipo() == TipoBloque.BONUS)
            .filter(b -> Math.random() < PROBABILIDAD_ITEM)
            .flatMap(b -> Option.ofOptional(
                FabricaItems.generarItemAleatorioEn(
                    b.obtenerX(),
                    b.obtenerY()
                ).toJavaOptional()
            ));
    }

    /**
     * Genera un item si el bloque cumple las condiciones.
     * Metodo publico que puede ser usado externamente para generar items.
     *
     * @param bloque bloque del cual generar el item
     * @return Option conteniendo el item generado o Option.none()
     */
    public Option<Item> intentarGenerarItem(Bloque bloque) {
        return generarItem(bloque);
    }
}
