package main.Academia.Listas;

import java.util.Hashtable;
import main.MundoNinja.EstudianteNinja;

/**
 * Clase que representa una lista de estudiantes ninja utilizando una Hashtable.
 * Implementa la interfaz IterableCollection para permitir la iteración
 * sobre los elementos almacenados.
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class ListaEstudiantesNinjas implements IterableCollection<EstudianteNinja> {

    private Hashtable<String, EstudianteNinja> tabla;

    /**
     * Constructor que inicializa la tabla hash.
     */
    public ListaEstudiantesNinjas() {
        this.tabla = new Hashtable<>();
    }

    /**
     * Agrega un estudiante ninja a la tabla usando su nombre como clave.
     *
     * @param estudiante El estudiante ninja a agregar
     */
    public void agregarEstudiante(EstudianteNinja estudiante) {
        tabla.put(estudiante.getNombre(), estudiante);
    }

    /**
     * Busca un estudiante ninja por su nombre.
     *
     * @param nombre El nombre del estudiante ninja a buscar
     * @return El estudiante ninja encontrado o null si no existe
     */
    public EstudianteNinja buscarEstudiante(String nombre) {
        return tabla.get(nombre);
    }

    /**
     * Retorna un iterador para recorrer los estudiantes ninja.
     *
     * @return Un iterador de tipo EstudianteNinjaIterator
     */
    @Override
    public Iterator<EstudianteNinja> iterator() {
        return new EstudianteNinjaIterator(tabla);
    }

    /**
     * Retorna el número de estudiantes ninja en la tabla.
     *
     * @return El tamaño actual de la tabla
     */
    public int size() {
        return tabla.size();
    }

    /**
     * Verifica si la tabla está vacía.
     *
     * @return true si la tabla está vacía, false en caso contrario
     */
    public boolean isEmpty() {
        return tabla.isEmpty();
    }

    /**
     * Elimina un estudiante ninja de la tabla.
     *
     * @param nombre El nombre del estudiante ninja a eliminar
     * @return El estudiante ninja eliminado o null si no existía
     */
    public EstudianteNinja eliminarEstudiante(String nombre) {
        return tabla.remove(nombre);
    }

    /**
     * Verifica si existe un estudiante ninja con el nombre dado.
     *
     * @param nombre El nombre a verificar
     * @return true si existe un estudiante con ese nombre, false en caso contrario
     */
    public boolean contieneEstudiante(String nombre) {
        return tabla.containsKey(nombre);
    }
}
