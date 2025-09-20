package Academia.Listas;

import MundoNinja.EstudianteNinja;

/**
 * Interfaz que define el contrato para contenedores iterables de EstudianteNinja.
 * Extiende IterableCollection para proporcionar funcionalidad de iteración específica.
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public interface EstudianteNinjaIterator extends IterableCollection<EstudianteNinja> {

    /**
     * Agrega un estudiante ninja a la colección.
     *
     * @param estudiante El estudiante ninja a agregar
     */
    void agregarEstudianteNinja(EstudianteNinja estudiante);

    /**
     * Busca un estudiante ninja por su nombre.
     *
     * @param nombre El nombre del estudiante a buscar
     * @return El estudiante encontrado o null si no existe
     */
    EstudianteNinja buscarEstudiante(String nombre);
}
