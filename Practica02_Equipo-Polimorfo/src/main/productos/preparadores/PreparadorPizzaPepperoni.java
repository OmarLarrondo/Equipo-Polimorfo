package main.productos.preparadores;

/**
 * Preparador concreto para Pizza Pepperoni utilizando el patrón Template Method.
 * 
 * La Pizza Pepperoni es una de las pizzas más populares, especialmente en Estados Unidos,
 * caracterizada por su sabor picante y su abundante cantidad de pepperoni.
 * Esta implementación personaliza los pasos de colocación de queso y proteína
 * del algoritmo de preparación definido en PreparadorPizza.
 * 
 * Características específicas:
 * - Queso: Mozzarella rallada clásica
 * - Proteína: Pepperoni en rodajas
 * - Especialidad: Cobertura generosa de pepperoni
 * 
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class PreparadorPizzaPepperoni extends PreparadorPizza {

    /**
     * Coloca mozzarella rallada sobre la pizza Pepperoni.
     * 
     * La mozzarella rallada proporciona una base cremosa perfecta para el pepperoni,
     * derritiéndose de manera uniforme y creando la textura clásica que se espera
     * de una pizza pepperoni tradicional.
     */
    @Override
    protected void colocarQueso() {
        System.out.println("Colocando mozzarella rallada en capa uniforme y generosa...");
    }

    /**
     * Coloca rodajas de pepperoni sobre la pizza.
     * 
     * El pepperoni es un embutido curado picante que se coloca sobre el queso
     * para que se cocine y libere sus aceites durante el horneado, proporcionando
     * el sabor característico y ligeramente picante de esta pizza popular.
     */
    @Override
    protected void colocarProteina() {
        System.out.println("Distribuyendo rodajas de pepperoni picante sobre toda la superficie...");
    }
}