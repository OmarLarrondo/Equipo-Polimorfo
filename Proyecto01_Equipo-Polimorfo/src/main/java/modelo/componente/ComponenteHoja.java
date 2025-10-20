package modelo.componente;

/**
 * Clase abstracta que representa un componente indivisible (hoja) de una PC.
 * 
 * <p>Esta clase implementa la interfaz {@link ComponentePC} y proporciona
 * la funcionalidad común a todos los componentes hoja, como nombre, precio,
 * marca y tipo. 
 * 
 * <p>Se considera abstracta porque ciertos componentes específicos (por ejemplo,
 * CPU o Motherboard) deben definir su propia lógica de compatibilidad,
 * por lo que el método {@link #esCompatibleConComponentePC()} se deja abstracto.
 * 
 * <p>Los componentes hoja son aquellos que no contienen subcomponentes, a diferencia
 * de los componentes compuestos.
 * 
 * @author Equipo-polimorfo
 * @version 1.0
 */
public abstract class ComponenteHoja implements ComponentePC {
    /**Nombre del componente(ejemplo: "Core i3-13100") */
    private String nombre;
    /**Precio del componente(ejemplo: 100.23) */
    private double precio;
    /**La marca del componente (ejemplo:"AMD", "Kingston") */
    private String marca;
    /**Tipo del componente (ejemplo: "RAM", "Procesador(CPU)") */
    private String tipo;

    /**
     * Construye un componente hoja con sus atributos básicos.
     * 
     * @param nombre el nombre del componente
     * @param precio el precio del componente
     * @param marca la marca o fabricante del componente
     * @param tipo el tipo de componente (CPU, GPU, RAM, etc.)
     */
    public ComponenteHoja(String nombre, double precio, String marca, String tipo) {
        if (nombre == null || marca == null || tipo == null)
        throw new IllegalArgumentException("Los datos del componente no pueden ser nulos");
        if (precio <= 0)
            throw new IllegalArgumentException("El precio debe ser positivo");
        this.nombre = nombre;
        this.precio = precio;
        this.marca = marca;
        this.tipo = tipo;
    }

    /**
     * Obtiene el nombre del componente.
     * @return el nombre del componente
     */
    public String obtenerNombre() {
        return nombre;
    }

    /**
     * Obtiene el precio del componente.
     * @return el precio del componente
     */
    public double obtenerPrecio() {
        return precio;
    }

    /**
     * Obtiene la marca del componente.
     * @return la marca del componente
     */
    public String obtenerMarca() {
        return marca;
    }

    /**
     * Obtiene el tipo de componente.
     * @return el tipo de componente
     */
    public String obtenerTipo() {
        return tipo;
    }

    /**
     * Muestra los detalles del componente.
     * 
     * <p>Por defecto devuelve la representación en cadena de {@link #toString()},
     * pero puede ser sobreescrito por clases hijas para detalles más específicos.
     * 
     * @return los detalles del componente como cadena
     */
    @Override
    public String mostrarDetalles() {
        return toString();
    }

    /**
     * Determina si este componente es compatible con otro componente de PC.
     * 
     * <p>Este método es abstracto porque la compatibilidad depende del tipo específico
     * de componente (por ejemplo, un AMD no es compatible con ninguna de las motherbords 
     * de monosChinosMX), y cada subclase debe definir su propia lógica.
     * 
     * @param componenete el componente a verificar compatibilidad.
     * 
     * @return {@code true} si es compatible, {@code false} en caso contrario
     */
    public boolean esCompatibleConComponentePC() {
        if (obtenerMarca().equalsIgnoreCase("AMD")) {
            return false;
        }
        return true;
    }

    /**
     * Devueleve una cadena de texto, que representa un componente.
     * @return La cadena que detalla el componente.
     */
    @Override
    public String toString() {
        return String.format("%s [%s] - Marca: %s - Precio: $%.2f", tipo, nombre, marca, precio);
    }

}

