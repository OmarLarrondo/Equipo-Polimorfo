package main.Academia.Listas;

import main.MundoNinja.NinjaVoluntario;
import java.util.NoSuchElementException;

/**
 * Implementación concreta del patrón Iterator para iterar sobre un array
 * de ninjas voluntarios. Proporciona métodos para navegar secuencialmente
 * a través de los elementos almacenados en un array.
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class VoluntarioIterator implements Iterator<NinjaVoluntario> {

    private NinjaVoluntario[] voluntarios;
    private int size;
    private int currentIndex;

    /**
     * Constructor que inicializa el iterador con el array y su tamaño efectivo.
     *
     * @param voluntarios Array de ninjas voluntarios a iterar
     * @param size Número efectivo de elementos en el array
     */
    public VoluntarioIterator(NinjaVoluntario[] voluntarios, int size) {
        this.voluntarios = voluntarios;
        this.size = size;
        this.currentIndex = 0;
    }

    /**
     * Verifica si hay más elementos disponibles en la iteración.
     *
     * @return true si hay más elementos, false en caso contrario
     */
    @Override
    public boolean hasNext() {
        return currentIndex < size;
    }

    /**
     * Retorna el siguiente elemento en la iteración y avanza el iterador.
     *
     * @return El siguiente NinjaVoluntario
     * @throws NoSuchElementException Si no hay más elementos
     */
    @Override
    public NinjaVoluntario next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No hay más elementos en la iteración");
        }
        return voluntarios[currentIndex++];
    }
}
