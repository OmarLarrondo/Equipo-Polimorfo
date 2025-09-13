package main.productos.helados;

/**
 * Decorador concreto que agrega chispas de chocolate como ingrediente extra
 * a un helado. Implementa el patron Decorator permitiendo agregar de 1 a 3
 * porciones de chispas de chocolate al helado base.
 * 
 * <p>Cada porcion de chispas de chocolate tiene un costo adicional de $12.0 pesos.
 * La descripcion del ingrediente se repetira tantas veces como porciones
 * se hayan agregado, siguiendo el formato requerido para el ticket.</p>
 * 
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class ChispasChocolate extends DecoradorIngrediente {
    
    /** Numero de porciones de chispas de chocolate agregadas al helado */
    private int cantidad;
    
    /** Precio por porcion de chispas de chocolate en pesos */
    private static final double PRECIO_UNITARIO = 12.0;

    /**
     * Constructor que crea un decorador de chispas de chocolate.
     * 
     * @param heladoBase el helado base que sera decorado, no puede ser null
     * @param cantidad numero de porciones de chispas de chocolate (debe estar entre 1 y 3)
     * @throws IllegalArgumentException si heladoBase es null o cantidad no esta entre 1 y 3
     */
    public ChispasChocolate(ComponenteHelado heladoBase, int cantidad) {
        super(heladoBase);
        if (cantidad < 1 || cantidad > 3) {
            throw new IllegalArgumentException("La cantidad de chispas de chocolate debe estar entre 1 y 3");
        }
        this.cantidad = cantidad;
    }
    
    /**
     * Obtiene la descripcion del helado incluyendo las chispas de chocolate.
     * El ingrediente se repite en la descripcion segun la cantidad agregada.
     * 
     * @return String con la descripcion del helado base seguida de las chispas de chocolate
     *         en formato: "descripcion_base, chispas de chocolate, chispas de chocolate, ..."
     */
    @Override
    public String getDescripcion() {
        StringBuilder descripcion = new StringBuilder(heladoBase.getDescripcion());
        for (int i = 0; i < cantidad; i++) {
            descripcion.append(", chispas de chocolate");
        }
        return descripcion.toString();
    }
    
    /**
     * Calcula el precio total del helado incluyendo el costo de las chispas de chocolate.
     * 
     * @return double con el precio del helado base mas el costo de las chispas de chocolate
     *         (cantidad × PRECIO_UNITARIO)
     */
    @Override
    public double getPrecio() {
        return heladoBase.getPrecio() + (cantidad * PRECIO_UNITARIO);
    }

}