package Adapter;

import Composite.ProductoComponente;

/**
 * Adaptador que permite integrar {@link DiscoMusicalLegacy}
 * dentro de la jerarquía del patrón Composite.
 * <p>Esta clase hereda de {@code ProductoComponente} y traduce
 * las llamadas al sistema legado de discos musicales.</p>
 */
public class DiscoMusicalAdapter extends ProductoComponente {
    /** Instancia del disco musical legacy a adaptar. */
    private DiscoMusicalLegacy discoLegacy;
    /**
     * Constructor que inicializa el adaptador a partir de un disco legacy.
     *
     * @param nombre el nombre del producto (heredado de {@code ProductoComponente})
     * @param genero el género del producto 
     * @param precio el precio del producto
     * @param discoLegacy el objeto de tipo {@code DiscoMusicalLegacy} que se adapta
     */
    public DiscoMusicalAdapter(String nombre, String genero, double precio, DiscoMusicalLegacy discoLegacy) {
        super(nombre, genero, precio);
        this.discoLegacy = discoLegacy;
    }

    /**
     * Obtiene la información completa del disco musical adaptado.
     * @return los detalles del disco (nombre, artista, género, año y precio)
     */
    public String obtenerInformacion() {
        return discoLegacy.obtenerDetallesCompletos();
    }

    /**
     * Indica si el producto es compuesto.
     * <p>Discousical no es compuesto, por eso siempre dara false {@code false}.</p>
     *
     * @return {@code false}, ya que un disco no contiene otros productos
     */
    public boolean esCompuesto() { 
        return false;
    }

    /**
     * {@inheritDoc}
     * <p>Un disco musical no tiene duración acumulada, por lo que devuelve 0.</p>
     */
    @Override
    public int getMinutosDuracion() {
        System.out.println("Un disco no tiene duración...");
        return 0;
    }

    /**
     * {@inheritDoc}
     * <p>Un disco musical no tiene sinopsis, por lo que devuelve {@code null}.</p>
     */
    @Override
    public String getSinopsis() {
        System.out.println("Un disco no tiene sinopsis...");
        return null;
    }

    /**
     * {@inheritDoc}
     * <p>Delegado al método {@link DiscoMusicalLegacy#reproducir()}.</p>
     */
    @Override
    public String reproducir() {
        return discoLegacy.reproducir();
    }

    /**
     * {@inheritDoc}
     * <p>Compara este adaptador con otro objeto para determinar igualdad.
     * Dos adaptadores son iguales si tienen las mismas propiedades heredadas de ProductoComponente
     * y contienen discos legacy con las mismas características.</p>
     *
     * @param obj el objeto a comparar con este adaptador
     * @return {@code true} si los objetos son iguales, {@code false} en caso contrario
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        DiscoMusicalAdapter other = (DiscoMusicalAdapter) obj;

        return compararPropiedadesBase(other) && compararDiscoLegacy(other);
    }

    /**
     * Compara las propiedades base heredadas de ProductoComponente.
     *
     * @param other el otro adaptador a comparar
     * @return {@code true} si las propiedades base son iguales
     */
    private boolean compararPropiedadesBase(DiscoMusicalAdapter other) {
        return java.util.Objects.equals(nombre, other.nombre) &&
               java.util.Objects.equals(genero, other.genero) &&
               Double.compare(precio, other.precio) == 0;
    }

    /**
     * Compara los discos legacy contenidos en los adaptadores.
     *
     * @param other el otro adaptador a comparar
     * @return {@code true} si los discos legacy son iguales
     */
    private boolean compararDiscoLegacy(DiscoMusicalAdapter other) {
        if (discoLegacy == null) {
            return other.discoLegacy == null;
        }
        if (other.discoLegacy == null) {
            return false;
        }

        return java.util.Objects.equals(discoLegacy.getNombre(), other.discoLegacy.getNombre()) &&
               java.util.Objects.equals(discoLegacy.getArtista(), other.discoLegacy.getArtista()) &&
               java.util.Objects.equals(discoLegacy.getGeneroMusical(), other.discoLegacy.getGeneroMusical()) &&
               discoLegacy.getAnoEstreno() == other.discoLegacy.getAnoEstreno() &&
               Double.compare(discoLegacy.getPrecioVenta(), other.discoLegacy.getPrecioVenta()) == 0;
    }
}
