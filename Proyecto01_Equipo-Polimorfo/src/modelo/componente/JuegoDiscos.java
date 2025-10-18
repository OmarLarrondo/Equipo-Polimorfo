package modelo.componente;

import java.util.ArrayList;
import java.util.List;
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
    /** Capacidad total de almacenamiento del conjunto de discos (en GB)  o TB . */
    private int capacidadAlmacenamiento;

    /** Tipo de almacenamiento de los discos (por ejemplo, "SSD" "HDD"). */
    private String tipoALmacenamiento;

    /** Lista de discos que forman el conjunto. */
    private List<Disco> discos;

    /**
     * Constructor para inicializar un conjunto de discos con capacidad, tipo y lista de discos.
     *
     * @param capacidadAlmacenamiento la capacidad total del conjunto de discos (GB)
     * @param tipoALmacenamiento tipo de almacenamiento (por ejemplo, "HDD", "SSD")
     * @param discos lista inicial de discos; puede ser null si se agregará después
     * @param nombre nombre del componente compuesto (para la superclase)
     * @param tipo tipo del componente compuesto (para la superclase)
     */
    public JuegoDiscos(int capacidadAlmacenamiento, String tipoALmacenamiento, List<Disco> discos,
                        String nombre, String tipo) {
        super(new ArrayList<>(), nombre, tipo); // Llamada explícita al constructor de la superclase
        this.capacidadAlmacenamiento = capacidadAlmacenamiento;
        this.tipoALmacenamiento = tipoALmacenamiento;
        this.discos = discos;
    }


    /**
     * Agrega un disco al conjunto de discos.
     *
     * @param nuevoDisco el disco a agregar; no puede ser {@code null}
     * @return {@code true} si el disco se agregó correctamente
     * @throws IllegalArgumentException si {@code nuevoDisco} es {@code null}
     */
    public boolean agregarDisco(Disco nuevoDisco){
        if (nuevoDisco == null) {
            throw new IllegalArgumentException("El disco a agregar no puede ser nulo.");
        }
        if (discos == null) {
            this.discos = new ArrayList<>();
        }
        return discos.add(nuevoDisco);
    }

    /**
     * Obtiene la capacidad total de almacenamiento del conjunto de discos.
     *
     * @return la capacidad total en GB
     * @throws IllegalStateException si la capacidad es menor o igual a 0
     */
    public int getCapacidadAlmacenamiento() {
        if (capacidadAlmacenamiento <= 0) {
            throw new IllegalStateException("La capacidad de almacenamiento no puede ser 0 o negativa");
        }
        return capacidadAlmacenamiento;
    }

    @Override
    public String toString() {
        return "JuegoDiscos [capacidadAlmacenamiento=" + capacidadAlmacenamiento + ", tipoALmacenamiento="
                + tipoALmacenamiento + ", discos=" + discos + "]";
    }    
}
