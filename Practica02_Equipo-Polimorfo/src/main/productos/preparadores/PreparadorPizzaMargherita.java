package main.productos.preparadores;

/**
 * Preparador concreto para Pizza Margherita utilizando el patrón Template Method.
 * 
 * La Pizza Margherita es una pizza vegetariana clásica italiana que representa
 * los colores de la bandera italiana: rojo (tomate), blanco (mozzarella) y verde (albahaca).
 * Esta implementación personaliza los pasos de colocación de queso y proteína
 * del algoritmo de preparación definido en PreparadorPizza.
 * 
 * Características específicas:
 * - Queso: Mozzarella fresca di bufala
 * - Proteína: Ninguna (utiliza hook method - se salta este paso)
 * - Ingrediente especial: Albahaca fresca tradicional italiana
 * 
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class PreparadorPizzaMargherita extends PreparadorPizza {

    /**
     * Coloca mozzarella fresca di bufala sobre la pizza Margherita.
     * 
     * La mozzarella fresca es el queso tradicional para esta pizza clásica italiana,
     * proporcionando una textura cremosa y un sabor suave que complementa
     * perfectamente la salsa de tomate y la albahaca fresca.
     */
    @Override
    protected void colocarQueso() {
        System.out.println("Colocando mozzarella fresca di bufala en trozos generosos...");
    }

    /**
     * Agrega albahaca fresca como ingrediente especial de la pizza Margherita.
     * 
     * La albahaca fresca es el ingrediente distintivo que completa la trilogía
     * de colores de la bandera italiana en esta pizza clásica. Se agrega como
     * ingrediente especial antes del paso de proteína (que se omite en esta pizza).
     * La albahaca proporciona el aroma y sabor característico de la auténtica
     * Pizza Margherita italiana.
     */
    @Override
    protected void colocarIngredientesEspeciales() {
        System.out.println("Agregando hojas de albahaca fresca italiana como ingrediente especial...");
    }
}