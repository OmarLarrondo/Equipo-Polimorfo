package Adapter;

/**
 * Clase que representa un disco musical en un sistema legado.
 * <p>Incluye información como nombre, artista, género musical,
 * año de estreno y precio de venta. Esta clase es adaptada
 * mediante {@link DiscoMusicalAdapter} para integrarse al
 * patrón Composite.</p>
 */
public class DiscoMusicalLegacy {
    /** Nombre del disco. */
    private String nombre;
    /** Artista o banda que interpreta el disco. */
    private String artista;
    /** Género musical del disco. */
    private String generoMusical;
    /** Año de estreno del disco. */
    private int anoEstreno;
    /** Precio de venta del disco. */
    private double precioVenta;

    /**
     * Constructor para inicializar un disco musical legacy.
     *
     * @param nombre el nombre del disco
     * @param artista  el artista o banda
     * @param generoMusical el género musical
     * @param anoEstreno el año de estreno
     * @param precioVenta el precio de venta
     */
    public DiscoMusicalLegacy(String nombre, String artista, String generoMusical,
                            int anoEstreno, double precioVenta) {
        this.nombre = nombre;
        this.artista = artista;
        this.generoMusical = generoMusical;
        this.anoEstreno = anoEstreno;
        this.precioVenta = precioVenta;
    }

    /** @return el nombre del disco */
    public String getNombre() {
        return nombre; 
    }

    /** @param nombre el nuevo nombre del disco */
    public void setNombre(String nombre) { 
        this.nombre = nombre; 
    }

    /** @return el artista o banda */
    public String getArtista() { 
        return artista; 
    }
    /** @param artista el nuevo artista del disco */
    public void setArtista(String artista) {
        this.artista = artista; 
    }

    /** @return el género musical */
    public String getGeneroMusical() { 
        return generoMusical; 
    }
    /** @param generoMusical el nuevo género musical */
    public void setGeneroMusical(String generoMusical) {
        this.generoMusical = generoMusical; 
    }

    /** @return el año de estreno */
    public int getAnoEstreno() { 
        return anoEstreno; 
    }
    /** @param anoEstreno el nuevo año de estreno */
    public void setAnoEstreno(int anoEstreno) {
        this.anoEstreno = anoEstreno; 
    }

    /** @return el precio de venta */
    public double getPrecioVenta() { 
        return precioVenta; 
    }
    /** @param precioVenta el nuevo precio de venta */
    public void setPrecioVenta(double precioVenta) { 
        this.precioVenta = precioVenta; 
    }

    /**
     * Obtiene todos los detalles completos del disco. //CHECAAAR
     * @return una cadena con el nombre, artista, género musical, año y precio de venta
     */
    public String obtenerDetallesCompletos() {
        return String.format(
            "Nombre: %s\nArtista: %s\nGénero musical: %s\nAño de estreno: %d\nPrecio de venta: %.2f",
            nombre, artista, generoMusical, anoEstreno, precioVenta
        );
    }

    /**
     * Simula la reproducción del disco.
     * @return un mensaje indicando que el disco está siendo reproducido
     */
    public String reproducir() {
        return " Reproduciendo disco '" + nombre + "' de " + artista + " estrenado en: " + anoEstreno;
    }

    /**
     * Devuelve una representación en cadena del objeto para depuración.
     * @return los valores de todos los atributos del disco
     */
    @Override
    public String toString() {
        return "DiscoMusicalLegacy [nombre=" + nombre + ", artista=" + artista +
            ", generoMusical=" + generoMusical + ", anoEstreno=" + anoEstreno +
            ", precioVenta=" + precioVenta + "]";
    }
}
