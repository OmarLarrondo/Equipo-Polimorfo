package modelo.componente;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa un conjunto de memoria RAM como componente compuesto.
 * 
 * <p>Esta clase permite manejar hasta 4 módulos de RAM, consultar la capacidad
 * total de almacenamiento de RAM y agregar nuevos módulos de forma segura.(si se puede)
 * 
 * <p>Nota: El máximo de módulos de RAM que se pueden agregar es 4(da ifual la marca).
 * 
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class JuegoRAMs extends ComponenteCompuesto {
    /** Capacidad total de almacenamiento de RAM (en GB) */
    private int capacidadAlmacenamiento;

    /** Lista de módulos de RAM */
    private List<RAM> RAMs;

    /**
     * Constructor para inicializar un conjunto de módulos de RAM.
     *
     * @param componentes lista de componentes (puede ser null si se agregará después)
     * @param nombre nombre del componente compuesto
     * @param tipo tipo del componente compuesto (por ejemplo, "Memoria RAM")
     * @param capacidadAlmacenamiento capacidad total en GB
     * @param rAMs lista inicial de módulos de RAM; puede ser null
     */
    public JuegoRAMs(List<ComponentePC> componentes, String nombre, String tipo,
                                    int capacidadAlmacenamiento, List<RAM> rAMs) {
        super(componentes, nombre, tipo);
        this.capacidadAlmacenamiento = capacidadAlmacenamiento;
        this.RAMs = rAMs != null ? rAMs : new ArrayList<>();
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
            throw new IllegalArgumentException("El módulo de RAM a agregar no puede ser nulo.");
        }
        if (RAMs.size() >= 4) {
            return false;
        }

        return RAMs.add(nuevaRam);
    }

    /**
     * Obtiene la capacidad total de almacenamiento de RAM.
     *
     * @return capacidad total en GB
     */
    public int getCapacidadAlmacenamiento() {
        int total = 0;
        for (RAM ram : RAMs) {
            total += ram.getCapacidadGB();
        }
        return total;
    }

    /**
     * Obtiene la lista de módulos de RAM.
     *
     * @return lista de RAMs
     */
    public List<RAM> getRAMs() {
        return RAMs;
    }
}
