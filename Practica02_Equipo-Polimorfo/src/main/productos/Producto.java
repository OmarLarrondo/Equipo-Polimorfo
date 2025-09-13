package main.productos;

/**
 * Clase abstracta que representa un producto genérico en la pizzería
 * "El Pequeño Cesarín".
 * 
 * Esta clase define la estructura base común para todos los productos
 * que se pueden ordenar en la pizzería, incluyendo información básica
 * como identificador, nombre, descripción y precio. Cada tipo específico
 * de producto (Pizza, Helado) debe heredar de esta clase e implementar
 * su propio proceso de preparación.
 * 
 * Los productos son parte del sistema de pedidos y pueden ser agregados
 * a un pedido para su posterior preparación y entrega por el robot.
 * 
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public abstract class Producto {
    
    /**
     * Identificador único del producto
     */
    protected String id;
    
    /**
     * Nombre del producto (ej: "Pizza Margherita", "Helado de Fresa")
     */
    protected String nombre;
    
    /**
     * Descripción detallada del producto y sus características
     */
    protected String descripcion;
    
    /**
     * Precio del producto en pesos mexicanos
     */
    protected double precio;

    /**
     * Constructor para crear un nuevo producto con la información básica.
     * 
     * Este constructor inicializa todos los atributos básicos que son
     * comunes a cualquier tipo de producto en la pizzería. Las clases
     * hijas pueden agregar atributos específicos adicionales.
     * 
     * @param id Identificador único del producto, no puede ser null ni vacío
     * @param nombre Nombre del producto, no puede ser null ni vacío
     * @param descripcion Descripción del producto, no puede ser null ni vacía
     * @param precio Precio del producto en pesos, debe ser mayor a 0
     * @throws IllegalArgumentException si algún parámetro String es null o vacío,
     *         o si el precio es menor o igual a 0
     */
    public Producto(String id, String nombre, String descripcion, double precio) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("El id del producto no puede ser null o vacío");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto no puede ser null o vacío");
        }
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción del producto no puede ser null o vacía");
        }
        if (precio <= 0) {
            throw new IllegalArgumentException("El precio del producto debe ser mayor a 0");
        }
        
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    /**
     * Obtiene el identificador único del producto.
     * 
     * @return String con el identificador del producto
     */
    public String getId() {
        return id;
    }

    /**
     * Obtiene el nombre del producto.
     * 
     * @return String con el nombre del producto
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene la descripción detallada del producto.
     * 
     * @return String con la descripción del producto
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Obtiene el precio del producto.
     * 
     * @return double con el precio del producto en pesos mexicanos
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * Método abstracto que debe ser implementado por cada tipo específico
     * de producto para definir su proceso de preparación.
     * 
     * Cada clase hija (Pizza, Helado) implementa este método de acuerdo
     * a sus requerimientos específicos:
     * - Pizza: utiliza el patrón Template Method a través de PreparadorPizza
     * - Helado: gestiona la preparación interactiva con ingredientes extras
     * 
     * El robot llama este método durante el proceso de preparación para
     * ejecutar los pasos específicos de cada producto.
     */
    public abstract void preparar();
}