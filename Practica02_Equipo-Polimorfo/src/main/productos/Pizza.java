package main.productos;

import main.enums.TipoMasa;
import main.productos.preparadores.PreparadorPizza;

/**
 * Representa una pizza en la pizzería "El Pequeño Cesarín".
 * 
 * Esta clase implementa el patrón Template Method a través de su composición
 * con PreparadorPizza, permitiendo que diferentes tipos de pizza sigan
 * la misma secuencia de preparación pero con ingredientes específicos.
 * 
 * Una pizza cuenta con información básica (heredada de Producto) más
 * un indicador de si es vegetariana, el tipo de masa seleccionado por
 * el cliente, y un preparador específico que define los ingredientes únicos.
 * 
 * El proceso de preparación incluye 10 pasos: preparar masa, aplanar,
 * colocar salsa, queso, especias, proteína, hornear, esperar, sacar y empaquetar.
 * 
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class Pizza extends Producto {
    
    /**
     * Indica si la pizza es apta para vegetarianos
     */
    private boolean esVegetariana;
    
    /**
     * Tipo de masa seleccionado por el cliente (napolitana, romana o americana)
     */
    private TipoMasa tipoMasa;
    
    /**
     * Preparador específico que implementa el patrón Template Method
     * para definir ingredientes únicos de cada tipo de pizza
     */
    private PreparadorPizza preparadorPizza;

    /**
     * Constructor para crear una nueva pizza.
     * 
     * El tipo de masa debe ser establecido posteriormente mediante setTipoMasa()
     * antes de poder preparar la pizza, permitiendo que el cliente seleccione
     * su preferencia de masa durante el proceso de pedido.
     * 
     * @param id Identificador único de la pizza
     * @param nombre Nombre de la pizza (ej: "Margherita", "Pepperoni")
     * @param descripcion Descripción detallada de los ingredientes
     * @param precio Precio de la pizza en pesos
     * @param esVegetariana true si la pizza es apta para vegetarianos, false en caso contrario
     * @param preparadorPizza Preparador específico que define los ingredientes de esta pizza
     * @throws IllegalArgumentException si algún parámetro String es null o vacío,
     *         si el precio es negativo, o si el preparadorPizza es null
     */
    public Pizza(String id, String nombre, String descripcion, double precio,
                 boolean esVegetariana, PreparadorPizza preparadorPizza) {
        super(id, nombre, descripcion, precio);
        
        if (preparadorPizza == null) {
            throw new IllegalArgumentException("El preparador de pizza no puede ser null");
        }
        
        this.esVegetariana = esVegetariana;
        this.tipoMasa = null;
        this.preparadorPizza = preparadorPizza;
    }

    /**
     * Prepara la pizza siguiendo la secuencia de pasos del patrón Template Method.
     * 
     * Este método valida que el tipo de masa haya sido establecido por el cliente
     * antes de iniciar la preparación, luego delega la preparación al preparador
     * específico que ejecuta los 10 pasos de preparación mostrando cada paso al usuario.
     * 
     * @throws IllegalStateException si el tipo de masa no ha sido establecido
     *         mediante setTipoMasa() antes de llamar este método
     */
    @Override
    public void preparar() {
        if (tipoMasa == null) {
            throw new IllegalStateException(
                "Debe establecer el tipo de masa antes de preparar la pizza. "
                + "Use setTipoMasa() para seleccionar: NAPOLITANA, ROMANA o AMERICANA");
        }
        
        System.out.println("Preparando " + nombre + "...");
        preparadorPizza.preparar(tipoMasa);
    }
    
    /**
     * Establece el tipo de masa seleccionado por el cliente.
     * 
     * Este método debe ser llamado después de la construcción de la pizza
     * y antes de su preparación, permitiendo que el cliente elija entre
     * masa napolitana, romana o americana según sus preferencias.
     * 
     * @param masa El tipo de masa elegido por el cliente
     * @throws IllegalArgumentException si el parámetro masa es null
     */
    public void setTipoMasa(TipoMasa masa) {
        if (masa == null) {
            throw new IllegalArgumentException("El tipo de masa no puede ser null");
        }
        this.tipoMasa = masa;
    }
    
    /**
     * Indica si la pizza es apta para vegetarianos.
     * 
     * @return true si la pizza no contiene ingredientes de origen animal,
     *         false si contiene proteína animal como pepperoni, jamón, etc.
     */
    public boolean isVegetariana() {
        return esVegetariana;
    }
    
    /**
     * Obtiene el tipo de masa actualmente establecido para esta pizza.
     * 
     * @return El tipo de masa establecido, o null si no se ha establecido aún
     */
    public TipoMasa getTipoMasa() {
        return tipoMasa;
    }
    
    /**
     * Obtiene el preparador asociado a esta pizza.
     * 
     * @return El preparador que define los ingredientes específicos de esta pizza
     */
    public PreparadorPizza getPreparadorPizza() {
        return preparadorPizza;
    }
}
