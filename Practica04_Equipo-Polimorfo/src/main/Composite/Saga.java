package Composite;
import java.util.ArrayList;
import java.util.List;
/**
 * Clase para represnetar una saga de peliculas o incluso mas sagas.
 * Representa al componente en el patron composite.
 * Esta clase extiende de {@code ProductoComponente}, por ende debe implemntar los metodos abstractos y 
 * tendra las mismas propiedas y aparte tendra una lista de ProductosCOmponenetes y un descuento
 */
public class Saga extends ProductoComponente{
    /**La lista de productosComponentes para las sagas. */
    private List<ProductoComponente> componentes;
    /** EL descuento que se aplicara. */
    private double descuento;

    /**
     * Constructor para inicializar los atributos de Saga.
     * @param nombre El nomnbre de la saga.
     * @param genero El genero de la saga.
     * @param precio EL precio de la saga.
     * @param descuento EL descuento de la saga.
     */
    public Saga(String nombre, String genero, double precio,
                double descuento) {
        super(nombre, genero, precio);
        this.descuento = descuento;
        this.componentes = new ArrayList<>();
    }

    /**
     * @return La lista de componetees de la saga.
     */
    public List<ProductoComponente> getComponentes() {
        return componentes;
    }
    
    /**
     * @return El descuento a aplicar a la saga.
     */
    public double getDescuento() {
        return descuento;
    }

    /**
     * @return EL precio total de la saga aplicando el descuento n%.
     */
    public double getPrecioSaga(){
        double PrecioSaga = 0;
        for (ProductoComponente prodComp : componentes) {
            PrecioSaga += prodComp.getPrecio();
        }
        return PrecioSaga*(1-descuento);
    }
    /**
     * @param descuento EL descuenato a asignar a la saga.
     */
    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    /**
     * {@inheritDoc}
     * <p>Devuelve la suma de la duración de todos los componentes de la saga.</p>
     */
    @Override
    public int getMinutosDuracion() {
        int sumaDuracion = 0;
        for (ProductoComponente prodCom : componentes) {
            sumaDuracion += prodCom.getMinutosDuracion();
        }
        return sumaDuracion;
    }

    /**
     * {@inheritDoc}
     * <p>Devuelve un resumen de las sinopsis de todos los componentes de la saga.</p>
     */
    @Override
    public String getSinopsis() {
        StringBuilder sb = new StringBuilder();
        sb.append("Saga: "+nombre+ "\n");
        for (ProductoComponente prodComp : componentes) {
            sb.append("==").append(prodComp.getNombre()).append(": Sinopsis: "+ prodComp.getSinopsis()).append("\n");   
        }
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     * <p>Reproduce recursivamente todos los componentes de la saga.</p>
     */
    @Override
    public String reproducir() {
        StringBuilder sb = new StringBuilder();
        sb.append("▶ Reproduciendo la Saga: "+nombre+ "\n");
        for (ProductoComponente prodComp : componentes) {
            sb.append("==").append(prodComp.reproducir());   
        }
        return sb.toString(); 
    }
    /**
     * {@inheritDoc}
     * <p>Devuelve la representación en cadena de la saga y sus componentes.</p>
     */
    @Override
    public String toString() {
        return "Saga [nombre=" + nombre + ", genero=" + genero + ", precio=" + precio + ", componentes=" + componentes
                + ", descuento=" + descuento + "]";
    }
}
