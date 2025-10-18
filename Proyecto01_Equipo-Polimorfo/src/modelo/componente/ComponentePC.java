package modelo.componente;

/**
 * Representa un componente de una computadora en el sistema.
 * 
 * <p>Esta interfaz define los métodos que deben implementar todos los tipos 
 * de componentes de PC (por ejemplo: CPU, GPU, RAM, MotherBoard, etc ...).
 * 
 * <p>Su propósito es estandarizar el acceso a la información de cada componente, 
 * permitiendo que las clases que la implementen puedan integrarse de forma
 * polimórfica en el sistema.
 * 
 * @author Equipo-poliformo
 * @version 1.0
 */
public interface ComponentePC {
    /**
     * Obtiene el nombre del componente.
     * @return el nombre del componente (por ejemplo: "Core i3-13100", "8GB", "MAG B760 Tomahawk WIFI DDR4", etc)
     */
    String obtenerNombre();

    /**
     * Obtiene el precio del componente.
     * 
     * @return el precio del componente (ejemplo: 1000.00, 20.34, 502.2)
     */
    double obtenerPrecio();

    /**
     * Obtiene la marca o fabricante del componente.
     * @return la marca del componente (por ejemplo, "AMD", "Intel", "Kingston", "ASUS", "Corsair", "Yeyian", etc ...)
     */
    String obtenerMarca();

    /**
     * Obtiene el tipo de componente dentro del sistema.
     * @return el tipo del componente (por ejemplo, "Procesador(CPU)", "RAM", "HDD", "SSD", "Gabinetes", etc ...)
     */
    String obtenerTipo();

    /**
     * Devuelve una representación en texto de los detalles del componente.
     * 
     * <p>Este método debe incluir información relevante como nombre, marca,
     * tipo, precio y características técnicas específicas(si es que las tiene, como nucleos etc...).
     * 
     * @return una cadena de texto con los detalles completos del componente.
     */
    String mostrarDetalles();
}
