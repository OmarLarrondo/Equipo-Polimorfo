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
}
