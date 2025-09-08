package main.productos.preparadores;

import main.enums.TipoMasa;

/**
 * Clase abstracta que implementa el patrón Template Method para la preparación
 * de pizzas en la pizzería "El Pequeño Cesarín".
 * 
 * Define un algoritmo de preparación de pizza con 10 pasos fijos, donde algunos
 * pasos pueden ser personalizados por las subclases concretas según el tipo de pizza.
 * 
 * El algoritmo de preparación incluye:
 * 1. Preparar la masa
 * 2. Aplanar la masa  
 * 3. Colocar salsa de tomate
 * 4. Colocar queso (personalizable)
 * 5. Colocar especias
 * 6. Colocar proteína (personalizable)
 * 7. Meter al horno
 * 8. Esperar
 * 9. Sacar del horno
 * 10. Empaquetar
 * 
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public abstract class PreparadorPizza {

    /**
     * Método template que define la secuencia completa de preparación de una pizza.
     * Este método no debe ser sobrescrito por las subclases, ya que define el
     * algoritmo invariante de preparación.
     * 
     * @param masa El tipo de masa seleccionado por el cliente (napolitana, romana o americana)
     */
    public final void preparar(TipoMasa masa) {
        System.out.println("=== Iniciando preparación de pizza con masa " + masa.toString().toLowerCase() + " ===");
        
        prepararMasa(masa);
        aplanarMasa();
        colocarSalsaTomate();
        colocarQueso();
        colocarEspecias();
        colocarProteina();
        meterAlHorno();
        esperar();
        sacarDelHorno();
        empaquetar();
        
        System.out.println("=== Pizza terminada y empaquetada ===");
    }
    
    /**
     * Prepara la masa según el tipo especificado por el cliente.
     * Cada tipo de masa tiene características específicas de preparación.
     * 
     * @param masa El tipo de masa a preparar
     */
    protected void prepararMasa(TipoMasa masa) {
        System.out.print("Preparando masa " + masa.toString().toLowerCase());
        switch (masa) {
            case NAPOLITANA:
                System.out.println(" (delgada con bordes elevados)...");
                break;
            case ROMANA:
                System.out.println(" (muy fina y crujiente)...");
                break;
            case AMERICANA:
                System.out.println(" (gruesa y esponjosa)...");
                break;
        }
    }
    
    /**
     * Aplana la masa preparada para darle la forma circular característica de la pizza.
     */
    protected void aplanarMasa() {
        System.out.println("Aplanando la masa y dándole forma circular...");
    }
    
    /**
     * Coloca la salsa de tomate sobre la masa aplanada.
     * Utiliza salsa de tomate tradicional para todas las pizzas.
     */
    protected void colocarSalsaTomate() {
        System.out.println("Colocando salsa de tomate fresca sobre la masa...");
    }
    
    /**
     * Coloca el queso específico para cada tipo de pizza.
     * Este método debe ser implementado por cada subclase concreta
     * según el tipo de queso que corresponda a cada pizza.
     */
    protected abstract void colocarQueso();
    
    /**
     * Coloca las especias aromáticas sobre la pizza.
     * Utiliza una mezcla estándar de especias italiana para todas las pizzas.
     */
    protected void colocarEspecias() {
        System.out.println("Agregando especias aromáticas (orégano, albahaca, pimienta)...");
    }
    
    /**
     * Coloca la proteína específica para cada tipo de pizza.
     * Este método debe ser implementado por cada subclase concreta
     * según el tipo de proteína que corresponda a cada pizza.
     * Para pizzas vegetarianas, este método puede no agregar proteína.
     */
    protected abstract void colocarProteina();
    
    /**
     * Introduce la pizza al horno para su cocción.
     * Utiliza un horno de piedra a temperatura alta.
     */
    protected void meterAlHorno() {
        System.out.println("Metiendo la pizza al horno de piedra a 450°C...");
    }
    
    /**
     * Espera el tiempo necesario para que la pizza se cocine completamente.
     * El tiempo de cocción es estándar para todas las pizzas.
     */
    protected void esperar() {
        System.out.println("Esperando 12-15 minutos para cocción completa...");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Saca la pizza del horno una vez que está completamente cocida.
     */
    protected void sacarDelHorno() {
        System.out.println("Sacando la pizza del horno (¡cuidado, está muy caliente!)...");
    }
    
    /**
     * Empaqueta la pizza en una caja para su entrega al cliente.
     * Utiliza cajas estándar de cartón con el logo de la pizzería.
     */
    protected void empaquetar() {
        System.out.println("Empaquetando la pizza en caja de cartón 'El Pequeño Cesarín'...");
    }
}
