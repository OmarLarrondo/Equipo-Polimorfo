package main.productos.preparadores;

/**
 * Preparador concreto para Pizza Vegetariana utilizando el patrón Template Method.
 * 
 * La Pizza Vegetariana es una opción saludable y colorida que combina múltiples
 * vegetales frescos para crear una experiencia gastronómica rica en sabores y texturas.
 * Esta pizza es perfecta para quienes buscan una opción sin carne pero llena de sabor.
 * Esta implementación personaliza los pasos de colocación de queso y proteína
 * del algoritmo de preparación definido en PreparadorPizza.
 * 
 * Características específicas:
 * - Queso: Parmesano rallado añejado
 * - Proteína: Ninguna (utiliza hook method - se salta este paso)
 * - Ingredientes especiales: Vegetales variados (pimientos, champiñones, cebolla, aceitunas)
 * - Especialidad: Combinación colorida y nutritiva de vegetales frescos
 * 
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class PreparadorPizzaVegetariana extends PreparadorPizza {

    /**
     * Coloca queso parmesano rallado añejado sobre la pizza Vegetariana.
     * 
     * El parmesano añejado proporciona un sabor intenso y salado que complementa
     * perfectamente la variedad de vegetales frescos, agregando profundidad
     * de sabor sin competir con los sabores naturales de las verduras.
     * Su textura granulada se derrite creando una cobertura aromática.
     */
    @Override
    protected void colocarQueso() {
        System.out.println("Colocando parmesano rallado añejado para realzar sabores vegetales...");
    }

    /**
     * Coloca una variedad de vegetales frescos como ingredientes especiales.
     * 
     * Esta pizza utiliza vegetales frescos como ingredientes especiales en lugar
     * de proteína animal. La selección de vegetales proporciona diferentes texturas,
     * colores y sabores que hacen de esta pizza una opción saludable y sabrosa.
     * Los pimientos aportan dulzura, los champiñones umami, la cebolla sabor profundo,
     * y las aceitunas negras un toque salino mediterráneo.
     */
    @Override
    protected void colocarIngredientesEspeciales() {
        System.out.println("Distribuyendo pimientos rojos y verdes, champiñones frescos, cebolla morada y aceitunas negras...");
    }
}