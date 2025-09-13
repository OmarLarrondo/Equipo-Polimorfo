package main.productos.preparadores;


/**
 * Preparador concreto para Pizza Hawaiana utilizando el patrón Template Method.
 * 
 * La Pizza Hawaiana es una pizza controvertida pero popular que combina sabores
 * salados y dulces mediante la combinación de jamón y piña. Originada en Canadá,
 * esta pizza ha ganado seguidores en todo el mundo por su sabor tropical único.
 * Esta implementación personaliza los pasos de colocación de queso y proteína
 * del algoritmo de preparación definido en PreparadorPizza.
 * 
 * Características específicas:
 * - Queso: Mozzarella rallada premium
 * - Proteína: Jamón ahumado y trozos de piña fresca
 * - Especialidad: Contraste dulce-salado tropical
 * 
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class PreparadorPizzaHawaiana extends PreparadorPizza {

    /**
     * Coloca mozzarella rallada premium sobre la pizza Hawaiana.
     * 
     * La mozzarella rallada de alta calidad proporciona una base neutra que
     * equilibra perfectamente los sabores intensos del jamón ahumado y
     * la dulzura natural de la piña, creando la armonía característica
     * de esta pizza tropical.
     */
    @Override
    protected void colocarQueso() {
        System.out.println("Colocando mozzarella rallada premium como base para sabores tropicales...");
    }

    /**
     * Coloca jamón ahumado y trozos de piña fresca sobre la pizza.
     * 
     * La combinación de jamón ahumado salado con trozos de piña dulce y jugosa
     * crea el contraste de sabores característico de la pizza hawaiana.
     * El jamón se dora durante el horneado mientras la piña se carameliza
     * ligeramente, intensificando ambos sabores.
     */
    @Override
    protected void colocarProteina() {
        System.out.println("Distribuyendo jamón ahumado en tiras y trozos de piña fresca jugosa...");
    }
}