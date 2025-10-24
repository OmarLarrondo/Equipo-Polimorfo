package modelo.componente;

import java.util.ArrayList;

/**
 * Representa un conjunto de memoria RAM como componente compuesto.
 *
 * <p>Esta clase permite manejar hasta 4 módulos de RAM, consultar la capacidad
 * total de almacenamiento de RAM y agregar nuevos módulos de forma segura.
 *
 * <p>Nota: El máximo de módulos de RAM que se pueden agregar es 4 sin importar la marca.
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class JuegoRAMs extends ComponenteCompuesto {

    /**
     * Constructor para inicializar un conjunto de módulos de RAM.
     *
     * @param nombre nombre del componente compuesto
     * @param tipo tipo del componente compuesto (por ejemplo, "Memoria RAM")
     */
    public JuegoRAMs(String nombre, String tipo) {
        super(new ArrayList<>(), nombre, tipo);
    }

    /**
     * Agrega un módulo de RAM al conjunto.
     *
     * @param nuevaRam módulo de RAM a agregar; no puede ser null
     * @return {@code true} si se agregó correctamente, {@code false} si se alcanzó el máximo de 4 módulos
     * @throws IllegalArgumentException si {@code nuevaRam} es null
     */
    public Boolean agregarRAM(RAM nuevaRam) {
        if (nuevaRam == null) {
            throw new IllegalArgumentException("El modulo de RAM a agregar no puede ser nulo.");
        }
        IteratorComponentePC iterator = getIterator();
        int contador = 0;
        while (iterator.hasNext()) {
            ComponentePC componente = iterator.next();
            if (componente instanceof RAM) {
                contador++;
            }
        }
        if (contador >= 4) {
            return false;
        }
        agregar(nuevaRam);
        return true;
    }

    /**
     * Obtiene la capacidad total de almacenamiento de RAM.
     *
     * @return capacidad total en GB
     */
    public int getCapacidadAlmacenamiento() {
        IteratorComponentePC iterator = getIterator();
        int total = 0;
        while (iterator.hasNext()) {
            ComponentePC componente = iterator.next();
            if (componente instanceof RAM) {
                RAM ram = (RAM) componente;
                total += ram.getCapacidadGB();
            }
        }
        return total;
    }
}
