package main.Academia.Listas;

/**
 * Interfaz genérica del patrón Iterator que define el contrato para iterar
 * sobre colecciones de elementos de tipo T.
 * Esta interfaz proporciona los métodos básicos para la navegación secuencial
 * de elementos en una colección.
 *
 * @param <T> Tipo de elementos sobre los que se itera
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public interface Iterator<T> {

    /**
     * Verifica si hay más elementos disponibles en la iteración.
     *
     * @return true si hay más elementos, false en caso contrario
     */
    boolean hasNext();

    /**
     * Retorna el siguiente elemento en la iteración y avanza el iterador.
     *
     * @return El siguiente elemento de tipo T
     * @throws java.util.NoSuchElementException Si no hay más elementos
     */
    T next();
}