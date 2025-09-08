package main.productos.preparadores;

/**
 * Preparador concreto para Pizza Carne Lovery utilizando el patrón Template Method.
 * 
 * La Pizza Carne Lovery es la opción definitiva para los amantes de la carne,
 * combinando múltiples tipos de proteína para crear una experiencia gastronómica
 * rica, abundante y completamente satisfactoria. Esta pizza está diseñada para
 * quienes buscan la máxima cantidad de proteína y sabores cárnicos intensos.
 * Esta implementación personaliza los pasos de colocación de queso y proteína
 * del algoritmo de preparación definido en PreparadorPizza.
 * 
 * Características específicas:
 * - Queso: Cheddar maduro rallado
 * - Proteína: Combinación de pepperoni, salchicha italiana, jamón y tocino
 * - Especialidad: Explosión de sabores cárnicos y textura robusta
 * 
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class PreparadorPizzaCarneLovery extends PreparadorPizza {

    /**
     * Coloca queso cheddar maduro rallado sobre la pizza Carne Lovery.
     * 
     * El cheddar maduro proporciona un sabor fuerte y distintivo que puede
     * competir con la intensidad de múltiples carnes sin ser opacado.
     * Su consistencia cremosa al derretirse crea la base perfecta para
     * sostener y complementar la abundante variedad de proteínas cárnicas.
     */
    @Override
    protected void colocarQueso() {
        System.out.println("Colocando cheddar maduro rallado, resistente a sabores cárnicos intensos...");
    }

    /**
     * Coloca múltiples tipos de carne sobre la pizza Carne Lovery.
     * 
     * Esta pizza combina cuatro tipos diferentes de carne para crear una
     * experiencia culinaria extraordinaria: pepperoni para el picante,
     * salchicha italiana para el sabor especiado, jamón para la suavidad
     * salada, y tocino crujiente para la textura y el sabor ahumado.
     * La combinación resulta en una pizza extremadamente satisfactoria.
     */
    @Override
    protected void colocarProteina() {
        System.out.println("Cargando generosamente con pepperoni, salchicha italiana, jamón ahumado y tocino crujiente...");
    }
}