package main.Academia.Listas;

import main.MundoNinja.NinjaVoluntario;

/**
 * Clase que representa una lista de ninjas voluntarios utilizando un array.
 * Implementa la interfaz IterableCollection para permitir la iteración
 * sobre los elementos almacenados.
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class ListaNinjaVoluntarios implements IterableCollection<NinjaVoluntario> {

    private NinjaVoluntario[] voluntarios;
    private int size;
    private static final int CAPACIDAD_INICIAL = 10;

    /**
     * Constructor que inicializa la lista con una capacidad por defecto.
     */
    public ListaNinjaVoluntarios() {
        this.voluntarios = new NinjaVoluntario[CAPACIDAD_INICIAL];
        this.size = 0;
    }

    /**
     * Agrega un ninja voluntario a la lista.
     * Si la capacidad actual es insuficiente, redimensiona el array.
     *
     * @param voluntario El ninja voluntario a agregar
     */
    public void agregarVoluntario(NinjaVoluntario voluntario) {
        if (size >= voluntarios.length) {
            redimensionar();
        }
        voluntarios[size++] = voluntario;
    }

    /**
     * Busca un ninja voluntario por su nombre.
     *
     * @param nombre El nombre del ninja voluntario a buscar
     * @return El ninja voluntario encontrado o null si no existe
     */
    public NinjaVoluntario buscVoluntario(String nombre) {
        for (int i = 0; i < size; i++) {
            if (voluntarios[i].getNombre().equals(nombre)) {
                return voluntarios[i];
            }
        }
        return null;
    }

    /**
     * Retorna un iterador para recorrer los ninjas voluntarios.
     *
     * @return Un iterador de tipo VoluntarioIterator
     */
    @Override
    public Iterator<NinjaVoluntario> iterator() {
        return new VoluntarioIterator(voluntarios, size);
    }

    /**
     * Redimensiona el array cuando se agota la capacidad.
     */
    private void redimensionar() {
        NinjaVoluntario[] nuevoArray = new NinjaVoluntario[voluntarios.length * 2];
        System.arraycopy(voluntarios, 0, nuevoArray, 0, size);
        voluntarios = nuevoArray;
    }

    /**
     * Retorna el número de ninjas voluntarios en la lista.
     *
     * @return El tamaño actual de la lista
     */
    public int size() {
        return size;
    }

    /**
     * Verifica si la lista está vacía.
     *
     * @return true si la lista está vacía, false en caso contrario
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Obtiene un ninja voluntario en la posición especificada.
     *
     * @param index La posición del elemento a obtener
     * @return El ninja voluntario en la posición especificada
     * @throws IndexOutOfBoundsException Si el índice está fuera de los límites
     */
    public NinjaVoluntario get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Índice fuera de los límites: " + index);
        }
        return voluntarios[index];
    }
}
