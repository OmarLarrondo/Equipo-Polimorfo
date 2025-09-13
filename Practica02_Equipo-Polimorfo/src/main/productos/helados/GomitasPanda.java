package main.productos.helados;

/**
 * Decorador concreto que agrega gomitas de panda como ingrediente extra
 * a un helado. Implementa el patron Decorator permitiendo agregar de 1 a 3
 * porciones de gomitas de panda al helado base.
 * 
 * <p>Cada porcion de gomitas de panda tiene un costo adicional de $8.0 pesos.
 * La descripcion del ingrediente se repetira tantas veces como porciones
 * se hayan agregado, siguiendo el formato requerido para el ticket.</p>
 * 
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class GomitasPanda extends DecoradorIngrediente {
    
    /** Numero de porciones de gomitas de panda agregadas al helado */
    private int cantidad;
    
    /** Precio por porcion de gomitas de panda en pesos */
    private static final double PRECIO_UNITARIO = 8.0;

    /**
     * Constructor que crea un decorador de gomitas de panda.
     * 
     * @param heladoBase el helado base que sera decorado, no puede ser null
     * @param cantidad numero de porciones de gomitas de panda (debe estar entre 1 y 3)
     * @throws IllegalArgumentException si heladoBase es null o cantidad no esta entre 1 y 3
     */
    public GomitasPanda(ComponenteHelado heladoBase, int cantidad) {
        super(heladoBase);
        if (cantidad < 1 || cantidad > 3) {
            throw new IllegalArgumentException("La cantidad de gomitas de panda debe estar entre 1 y 3");
        }
        this.cantidad = cantidad;
    }
    
    /**
     * Obtiene la descripcion del helado incluyendo las gomitas de panda.
     * El ingrediente se repite en la descripcion segun la cantidad agregada.
     * 
     * @return String con la descripcion del helado base seguida de las gomitas de panda
     *         en formato: "descripcion_base, gomitas de panda, gomitas de panda, ..."
     */
    @Override
    public String getDescripcion() {
        StringBuilder descripcion = new StringBuilder(heladoBase.getDescripcion());
        for (int i = 0; i < cantidad; i++) {
            descripcion.append(", gomitas de panda");
        }
        return descripcion.toString();
    }
    
    /**
     * Calcula el precio total del helado incluyendo el costo de las gomitas de panda.
     * 
     * @return double con el precio del helado base mas el costo de las gomitas de panda
     *         (cantidad × PRECIO_UNITARIO)
     */
    @Override
    public double getPrecio() {
        return heladoBase.getPrecio() + (cantidad * PRECIO_UNITARIO);
    }

}