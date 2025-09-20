package main.Academia.Listas;

/**
 * Interfaz genérica que define una colección iterable siguiendo el patrón Iterator.
 * Las clases que implementen esta interfaz deben proporcionar un método para obtener
 * un iterador que permita recorrer los elementos de la colección.
 *
 * @param <T> Tipo de elementos contenidos en la colección
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public interface IterableCollection<T> {

    /**
     * Retorna un iterador para recorrer los elementos de la colección.
     *
     * @return Un objeto Iterator que permite iterar sobre los elementos de tipo T
     */
    Iterator<T> iterator();
}
