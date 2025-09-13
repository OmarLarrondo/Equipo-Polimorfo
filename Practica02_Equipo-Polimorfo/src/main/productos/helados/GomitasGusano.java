package main.productos.helados;

/**
 * Decorador concreto que agrega gomitas de gusano como ingrediente extra
 * a un helado. Implementa el patron Decorator permitiendo agregar de 1 a 3
 * porciones de gomitas de gusano al helado base.
 * 
 * <p>Cada porcion de gomitas de gusano tiene un costo adicional de $8.0 pesos.
 * La descripcion del ingrediente se repetira tantas veces como porciones
 * se hayan agregado, siguiendo el formato requerido para el ticket.</p>
 * 
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class GomitasGusano extends DecoradorIngrediente {
    
    /** Numero de porciones de gomitas de gusano agregadas al helado */
    private int cantidad;
    
    /** Precio por porcion de gomitas de gusano en pesos */
    private static final double PRECIO_UNITARIO = 8.0;

    /**
     * Constructor que crea un decorador de gomitas de gusano.
     * 
     * @param heladoBase el helado base que sera decorado, no puede ser null
     * @param cantidad numero de porciones de gomitas de gusano (debe estar entre 1 y 3)
     * @throws IllegalArgumentException si heladoBase es null o cantidad no esta entre 1 y 3
     */
    public GomitasGusano(ComponenteHelado heladoBase, int cantidad) {
        super(heladoBase);
        if (cantidad < 1 || cantidad > 3) {
            throw new IllegalArgumentException("La cantidad de gomitas de gusano debe estar entre 1 y 3");
        }
        this.cantidad = cantidad;
    }
    
    /**
     * Obtiene la descripcion del helado incluyendo las gomitas de gusano.
     * El ingrediente se repite en la descripcion segun la cantidad agregada.
     * 
     * @return String con la descripcion del helado base seguida de las gomitas de gusano
     *         en formato: "descripcion_base, gomitas de gusano, gomitas de gusano, ..."
     */
    @Override
    public String getDescripcion() {
        StringBuilder descripcion = new StringBuilder(heladoBase.getDescripcion());
        for (int i = 0; i < cantidad; i++) {
            descripcion.append(", gomitas de gusano");
        }
        return descripcion.toString();
    }
    
    /**
     * Calcula el precio total del helado incluyendo el costo de las gomitas de gusano.
     * 
     * @return double con el precio del helado base mas el costo de las gomitas de gusano
     *         (cantidad × PRECIO_UNITARIO)
     */
    @Override
    public double getPrecio() {
        return heladoBase.getPrecio() + (cantidad * PRECIO_UNITARIO);
    }

}