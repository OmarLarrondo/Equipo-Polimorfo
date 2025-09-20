package Academia.Listas;

import java.util.Collection;
import java.util.Hashtable;
import java.util.NoSuchElementException;

import MundoNinja.EstudianteNinja;

/**
 * Implementación de lista de estudiantes ninja usando HashTable como almacenamiento.
 * Implementa el patrón Iterator para permitir recorrido secuencial de los estudiantes.
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class ListaEstudiantesNinjas implements EstudianteNinjaIterator {
    private Hashtable<String, EstudianteNinja> tabla;

    /**
     * Constructor que inicializa la HashTable vacía.
     */
    public ListaEstudiantesNinjas() {
        this.tabla = new Hashtable<>();
    }

    /**
     * Agrega un estudiante ninja a la HashTable usando su nombre como clave.
     *
     * @param estudiante El estudiante ninja a agregar
     */
    @Override
    public void agregarEstudianteNinja(EstudianteNinja estudiante) {
        if (estudiante != null && estudiante.getNombre() != null) {
            tabla.put(estudiante.getNombre(), estudiante);
        }
    }

    /**
     * Busca un estudiante ninja por su nombre en la HashTable.
     *
     * @param nombre El nombre del estudiante a buscar
     * @return El estudiante encontrado o null si no existe
     */
    @Override
    public EstudianteNinja buscarEstudiante(String nombre) {
        return tabla.get(nombre);
    }

    /**
     * Retorna un iterador para recorrer los estudiantes ninja.
     *
     * @return Un iterador personalizado para EstudianteNinja
     */
    @Override
    public Iterator<EstudianteNinja> iterator() {
        return new EstudianteIterator();
    }

    /**
     * Retorna el número de estudiantes en la HashTable.
     *
     * @return El tamaño actual de la tabla
     */
    public int size() {
        return tabla.size();
    }

    /**
     * Verifica si la tabla está vacía.
     *
     * @return true si está vacía, false en caso contrario
     */
    public boolean isEmpty() {
        return tabla.isEmpty();
    }

    /**
     * Implementación interna del Iterator para EstudianteNinja.
     * Utiliza el iterator interno de la Collection values() de la HashTable.
     */
    private class EstudianteIterator implements Iterator<EstudianteNinja> {
        private java.util.Iterator<EstudianteNinja> iteradorInterno;

        /**
         * Constructor que inicializa el iterador interno.
         */
        public EstudianteIterator() {
            Collection<EstudianteNinja> valores = tabla.values();
            this.iteradorInterno = valores.iterator();
        }

        /**
         * Verifica si hay más elementos disponibles.
         *
         * @return true si hay más elementos, false en caso contrario
         */
        @Override
        public boolean hasNext() {
            return iteradorInterno.hasNext();
        }

        /**
         * Retorna el siguiente elemento en la iteración.
         *
         * @return El siguiente EstudianteNinja
         * @throws NoSuchElementException Si no hay más elementos
         */
        @Override
        public EstudianteNinja next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No hay más estudiantes en la iteración");
            }
            return iteradorInterno.next();
        }
    }
}
