package Composite;
public abstract class ProductoComponente {

    protected String nombre;
    protected String genero;
    protected double precio;

    public ProductoComponente(String nombre, String genero, double precio) {
        this.nombre = nombre;
        this.genero = genero;
        this.precio = precio;
    }

    /**
     * @return EL nombre del ProductoComponente
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @return EL genero del ProductoComponenete.
     */
    public String getGenero() {
        return genero;
    }

    /**
     * @return El precio del ProductoComponente
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * Obtiene la duración del producto en minutos.
     * <p>Para películas devuelve su duración real.
     * Para sagas devuelve la suma de las duraciones de todos sus componentes.
     * Para discos deberia ser 0.</p>
     * @return Los minutos de duracion del ProductoComponente.
     */
    public abstract int getMinutosDuracion();  

    /**
     * Obtiene la sinopsis del producto.
     * 
     * <p>Para películas devuelve su sinopsis.
     * Para sagas puede devolver un resumen de los componentes.
     * Para discos puede devolver "No aplica".</p>
     * 
     * @return La sinopsis del ProductoComponente
     */
    public abstract String getSinopsis();

    /**
     * Reproduce el producto.
     * 
     * <p>El comportamiento depende del tipo de producto:
     * <ul>
     *   <li>Película: simula la reproducción de la película.</li>
     *   <li>Saga: reproduce todos los productos que contiene (recursivamente).</li>
     *   <li>Disco (adaptado): simula la reproducción del disco.</li>
     * </ul>
     * </p>
     */
    public abstract String reproducir();  

    public abstract boolean equals(Object obj);

}
