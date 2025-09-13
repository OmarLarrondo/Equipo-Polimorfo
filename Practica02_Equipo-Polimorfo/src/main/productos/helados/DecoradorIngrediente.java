package main.productos.helados;

/**
 * Clase abstracta que implementa el patron Decorator para agregar ingredientes
 * extras a los helados. Permite decorar dinamicamente un ComponenteHelado base
 * con ingredientes adicionales, manteniendo la interfaz uniforme.
 * 
 * <p>Esta clase actua como decorador abstracto en el patron Decorator,
 * manteniendo una referencia al componente que decora (heladoBase) y
 * definiendo la estructura basica que deben seguir todos los decoradores
 * concretos de ingredientes.</p>
 * 
 * <p>Las subclases concretas deben implementar los metodos abstractos para
 * definir como cada ingrediente especifico modifica la descripcion y el
 * precio del helado base.</p>
 * 
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public abstract class DecoradorIngrediente implements ComponenteHelado {

    /** El componente helado que sera decorado con ingredientes extras */
    protected ComponenteHelado heladoBase;

    /**
     * Constructor que inicializa el decorador con el componente helado base.
     * 
     * @param heladoBase el componente helado que sera decorado, no puede ser null
     * @throws IllegalArgumentException si heladoBase es null
     */
    public DecoradorIngrediente(ComponenteHelado heladoBase) {
        if (heladoBase == null) {
            throw new IllegalArgumentException("El helado base no puede ser null");
        }
        this.heladoBase = heladoBase;
    }

    /**
     * Obtiene la descripcion del helado decorado con el ingrediente.
     * Las subclases deben implementar este metodo para agregar la descripcion
     * del ingrediente especifico a la descripcion del helado base.
     * 
     * @return String con la descripcion completa del helado incluyendo el ingrediente
     */
    @Override
    public abstract String getDescripcion();
    
    /**
     * Calcula el precio total del helado decorado con el ingrediente.
     * Las subclases deben implementar este metodo para sumar el costo
     * del ingrediente especifico al precio del helado base.
     * 
     * @return double con el precio total del helado incluyendo el ingrediente
     */
    @Override
    public abstract double getPrecio();

}