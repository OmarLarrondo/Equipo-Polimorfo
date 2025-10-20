package modelo.componente;

/**
 * Interfaz que representa las operaciones super básicas de un disco de almacenamiento.
 * <p>Permite que cualquier clase que implemente esta interfaz pueda exponer
 * la capacidad de almacenamiento del disco en gigabytes GB o TB
 * 
 * <p>Ejemplos de implementación pueden incluir discos duros (HDD), unidades
 * de estado sólido (SSD) 
 * 
 * @author Equipo-polimorfo
 * @version 1.0
 */
public interface InterfazDisco {

    /**
     * Obtiene la capacidad de almacenamiento del disco.
     * 
     * @return la capacidad de almacenamiento en gigabytes (GB)
     */
    public int getCapacidadAlmacenamiento();
}
