package Academia.Listas;

import MundoNinja.NinjaVoluntario;
import java.util.NoSuchElementException;

/**
 * Implementación concreta del Iterator para NinjaVoluntario.
 * Permite iterar sobre un array de ninjas voluntarios de forma secuencial.
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class VoluntarioIterator implements Iterator<NinjaVoluntario> {

    private NinjaVoluntario[] voluntarios;
    private int posicionActual;
    private int tamaño;

    /**
     * Constructor que inicializa el iterador con el array y tamaño.
     *
     * @param voluntarios Array de ninjas voluntarios
     * @param tamaño Número actual de elementos en el array
     */
    public VoluntarioIterator(NinjaVoluntario[] voluntarios, int tamaño) {
        this.voluntarios = voluntarios;
        this.tamaño = tamaño;
        this.posicionActual = 0;
    }

    /**
     * Verifica si hay más elementos disponibles en la iteración.
     *
     * @return true si hay más elementos, false en caso contrario
     */
    @Override
    public boolean hasNext() {
        return posicionActual < tamaño;
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
            throw new NoSuchElementException("No hay más voluntarios en la iteración");
        }
        return voluntarios[posicionActual++];
    }
}
