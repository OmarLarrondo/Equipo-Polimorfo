package modelo.componente;

/**
 * Define un iterador personalizado para recorrer componentes de tipo ComponentePC.
 *
 * <p>Esta interfaz forma parte del patron Iterator, permitiendo recorrer
 * colecciones de componentes sin exponer su estructura interna.
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public interface IteratorComponentePC {
    /**
     * Verifica si existen mas elementos en la coleccion.
     *
     * @return {@code true} si hay mas elementos por recorrer, {@code false} en caso contrario
     */
    public boolean hasNext();

    /**
     * Obtiene el siguiente elemento de la coleccion.
     *
     * @return el siguiente ComponentePC en la iteracion
     * @throws java.util.NoSuchElementException si no hay mas elementos disponibles
     */
    public ComponentePC next();
}
