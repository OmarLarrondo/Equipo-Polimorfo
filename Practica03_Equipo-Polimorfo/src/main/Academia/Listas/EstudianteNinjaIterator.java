package main.Academia.Listas;

import main.MundoNinja.EstudianteNinja;
import java.util.Hashtable;
import java.util.Enumeration;
import java.util.NoSuchElementException;

/**
 * Implementación concreta del patrón Iterator para iterar sobre una Hashtable
 * de estudiantes ninja. Proporciona métodos para navegar secuencialmente
 * a través de los elementos almacenados en la tabla hash.
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class EstudianteNinjaIterator implements Iterator<EstudianteNinja> {

    private Enumeration<EstudianteNinja> enumeration;

    /**
     * Constructor que inicializa el iterador con la Hashtable de estudiantes.
     *
     * @param tabla Hashtable de estudiantes ninja a iterar
     */
    public EstudianteNinjaIterator(Hashtable<String, EstudianteNinja> tabla) {
        this.enumeration = tabla.elements();
    }

    /**
     * Verifica si hay más elementos disponibles en la iteración.
     *
     * @return true si hay más elementos, false en caso contrario
     */
    @Override
    public boolean hasNext() {
        return enumeration.hasMoreElements();
    }

    /**
     * Retorna el siguiente elemento en la iteración y avanza el iterador.
     *
     * @return El siguiente EstudianteNinja
     * @throws NoSuchElementException Si no hay más elementos
     */
    @Override
    public EstudianteNinja next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No hay más elementos en la iteración");
        }
        return enumeration.nextElement();
    }
}
