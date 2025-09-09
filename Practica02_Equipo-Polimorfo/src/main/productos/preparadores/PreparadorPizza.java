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
 * 6. Colocar ingredientes especiales (hook opcional)
 * 7. Colocar proteína (hook opcional)
 * 8. Meter al horno
 * 9. Esperar
 * 10. Sacar del horno
 * 11. Empaquetar
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
        colocarIngredientesEspeciales();
        colocarProteina();
        meterAlHorno();
        esperar();
        sacarDelHorno();
        empaquetado();
    }

    /**
     * Prepara la masa de la pizza antes de aplanarla. 
     * (Este paso lo comparten todas las pizzas.)
     */
    protected void prepararMasa(){
        // aquí va código
    }

    /**
     * Aplana y estira la masa para tener forma y consistencia.
     * (Este paso lo comparten todas las pizzas.)
     */
    protected void aplanaMasa(){
        // aquí va su código
    }

    /**
     * Coloca la salsa de tomate sobre la masa ya aplanada.
     * (Este paso lo comparten todas las pizzas.)
     */
    protected void colocarSalsaTomate(){
        // aquí va su código
    }

    /**
     * Coloca el queso sobre la pizza.(en caso que lleve)
     * <p>Este método es abstracto y debe ser implementado por cada subclase concreta
     * según el tipo de pizza que se esté preparando.</p>
     */
    protected abstract void colocarQueso();

    /**
     * Coloca especias adicionales sobre la pizza, como orégano o albahaca.
     * (Este paso lo comparten todas las pizzas, si se requiere una pizza en especifico lo puede sobreescribir,
     * por ejmeplo, la hawainana puede tener dif especies que la margherita)
     */
    protected void colocarEspecias(){
        // aquí va código
    }

    /**
     * Coloca la proteína (ej. jamón, pollo, pepperoni) sobre la pizza.
     * <p>Este método es abstracto y debe ser implementado por cada subclase concreta
     * según el tipo de pizza que se esté preparando.</p>
     */
    protected abstract void colocarProteina();

    /**
     * Introduce la pizza en el horno para cocinarla.
     * (Este paso lo comparten todas las pizzas.)
     */
    protected void meterAlHorno(){
        // aquí va su código
    }

    /**
     * Espera el tiempo necesario mientras la pizza se hornea
     * (Este paso lo comparten todas las pizzas.).
     */
    protected void esperar(){
        // aquí va código
    }

    /**
     * Saca la pizza del horno una vez que está cocida.
     * (Este paso lo comparten todas las pizzas.)
     */
    protected void sacarDelHorno(){
        // aquí va código
    }

    /**
     * Empaqueta la pizza lista para entrega o servicio al cliente.
     * (Este paso lo comparten todas las pizzas.)
     */
    protected void empaquetado(){
        // aquí va su código
=======
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
     * Método hook para colocar ingredientes especiales específicos de cada pizza.
     * 
     * Este método se ejecuta después de colocar las especias y antes de la proteína,
     * permitiendo que cada tipo de pizza agregue ingredientes únicos como albahaca fresca,
     * vegetales variados, o cualquier ingrediente especial que no sea proteína animal.
     * 
     * La implementación por defecto no hace nada, las subclases pueden sobrescribirlo
     * opcionalmente según sus necesidades específicas.
     */
    protected void colocarIngredientesEspeciales() {
	
    }
    
    /**
     * Método hook para colocar la proteína específica de cada tipo de pizza.
     * 
     * Este método permite que las subclases agreguen proteína animal específica
     * según el tipo de pizza. La implementación por defecto no hace nada,
     * lo cual es apropiado para pizzas vegetarianas que no requieren proteína animal.
     * 
     * Las pizzas que sí requieren proteína deben sobrescribir este método
     * para agregar los ingredientes proteicos correspondientes.
     */
    protected void colocarProteina() {
	
    }
    
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
