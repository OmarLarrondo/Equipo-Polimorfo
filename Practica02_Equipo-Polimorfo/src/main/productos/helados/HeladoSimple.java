package main.productos.helados;

import main.enums.SaborHelado;

/**
 * Clase que representa un helado simple sin ingredientes extra.
 * 
 * <p>Esta clase implementa la interfaz ComponenteHelado y actúa como el
 * componente concreto base en el patrón Decorator. Representa un helado
 * básico con un sabor específico (fresa, vainilla o chocolate) y un
 * precio fijo base.</p>
 * 
 * <p>Los helados simples pueden ser decorados posteriormente con ingredientes
 * extras utilizando los decoradores correspondientes.</p>
 * 
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class HeladoSimple implements ComponenteHelado {

    /** El sabor del helado (fresa, vainilla o chocolate) */
    private SaborHelado sabor;
    
    /** Precio base fijo para todos los helados simples */
    private static final double PRECIO_BASE = 45.0;

    /**
     * Constructor para crear un helado simple con el sabor especificado.
     * 
     * @param sabor el sabor del helado (debe ser FRESA, VAINILLA o CHOCOLATE)
     * @throws IllegalArgumentException si el sabor es null
     */
    public HeladoSimple(SaborHelado sabor) {
        if (sabor == null) {
            throw new IllegalArgumentException("El sabor del helado no puede ser null");
        }
        this.sabor = sabor;
    }

    /**
     * Obtiene la descripción del helado simple.
     * 
     * @return String con la descripción en el formato "Helado de [sabor]"
     *         donde [sabor] es el nombre del sabor en minúsculas
     */
    @Override
    public String getDescripcion() {
        String nombreSabor = obtenerNombreSabor();
        return "Helado de " + nombreSabor;
    }

    /**
     * Obtiene el precio del helado simple.
     * 
     * @return double con el precio base del helado (45.0 pesos)
     */
    @Override
    public double getPrecio() {
        return PRECIO_BASE;
    }
    
    /**
     * Convierte el enum SaborHelado a su representación en string amigable.
     * 
     * @return String con el nombre del sabor en minúsculas
     */
    private String obtenerNombreSabor() {
        switch (sabor) {
            case FRESA:
                return "fresa";
            case VAINILLA:
                return "vainilla";
            case CHOCOLATE:
                return "chocolate";
            default:
                return "desconocido";
        }
    }

}