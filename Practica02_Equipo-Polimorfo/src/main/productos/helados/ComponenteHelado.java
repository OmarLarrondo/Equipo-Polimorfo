package main.productos.helados;

/**
 * Interfaz que define el componente base del patron Decorator para helados.
 * Permite tratar de manera uniforme tanto helados simples como helados
 * decorados con ingredientes extras, facilitando el apilado dinamico de
 * decoradores para crear combinaciones personalizadas de helados.
 * 
 * Esta interfaz es implementada por:
 * - HeladoSimple: componente concreto base
 * - DecoradorIngrediente: decorador abstracto para ingredientes extras
 * 
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public interface ComponenteHelado {
    
    /**
     * Obtiene la descripcion completa del helado, incluyendo el sabor base
     * y todos los ingredientes extras que hayan sido agregados.
     * 
     * @return String con la descripcion del helado en formato:
     *         "Helado de [sabor]" o "Helado de [sabor], [ingrediente1], [ingrediente2], ..."
     */
    public String getDescripcion();
    
    /**
     * Calcula el precio total del helado, sumando el costo base del helado
     * mas el costo de todos los ingredientes extras agregados.
     * 
     * @return double con el precio total del helado, debe ser mayor a 0
     */
    public double getPrecio();
}