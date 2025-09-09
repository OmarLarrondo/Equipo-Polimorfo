package main.productos.preparadores;

import main.enums.TipoMasa;

/**
 * Clase abstracta que define el flujo general para preparar una pizza.
 * <p> Implementa el patrón <b>TEMPLATE</b>, donde el método {@link #preparar(TipoMasa)}
 * define los pasos generales de preparación de la pizza. 
 * Algunos pasos pueden ser implementados por subclases concretas, como {@link #colocarQueso()} o {@link #colocarProteina()} 
 * y {@link #colocarProteina()}.</p>
 */
public abstract class PreparadorPizza {

    /**
     * Método PLANTILLA(TEMPLATE) que define el flujo completo de preparación de la pizza.
     *
     * @param masa tipo de masa a usar para la pizza
     */
    public void preparar(TipoMasa masa){
        prepararMasa();
        aplanaMasa();
        colocarSalsaTomate();
        colocarQueso();
        colocarEspecias();
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
    }
}
