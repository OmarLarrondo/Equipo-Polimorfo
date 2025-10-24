package modelo.componente;

import java.util.ArrayList;

/**
 * Representa un conjunto de discos de almacenamiento como componente compuesto.
 * <p>Esta clase permite manejar múltiples discos, consultar la capacidad total
 * de almacenamiento y agregar nuevos discos de forma segura.
 *
 * <p>Ejemplos de tipos de almacenamiento: "HDD", "SSD"
 *
 * @author Equipo-polimorfo
 * @version 1.0
 */
public class JuegoDiscos extends ComponenteCompuesto {

    /**
     * Constructor para inicializar un conjunto de discos.
     *
     * @param nombre nombre del componente compuesto (para la superclase)
     * @param tipo tipo del componente compuesto (para la superclase)
     */
    public JuegoDiscos(String nombre, String tipo) {
        super(new ArrayList<>(), nombre, tipo);
    }

    /**
     * Agrega un disco al conjunto de discos.
     *
     * @param nuevoDisco el disco a agregar; no puede ser {@code null}
     * @return {@code true} si el disco se agregó correctamente
     * @throws IllegalArgumentException si {@code nuevoDisco} es {@code null}
     */
    public boolean agregarDisco(Disco nuevoDisco) {
        if (nuevoDisco == null) {
            throw new IllegalArgumentException("El disco a agregar no puede ser nulo.");
        }
        agregar(nuevoDisco);
        return true;
    }

    /**
     * Obtiene la capacidad total de almacenamiento del conjunto de discos.
     *
     * @return la capacidad total en GB
     */
    public int getCapacidadAlmacenamiento() {
        IteratorComponentePC iterator = getIterator();
        int total = 0;
        while (iterator.hasNext()) {
            ComponentePC componente = iterator.next();
            if (componente instanceof Disco) {
                Disco disco = (Disco) componente;
                total += disco.getCapacidadAlmacenamiento();
            }
        }
        return total;
    }
}
