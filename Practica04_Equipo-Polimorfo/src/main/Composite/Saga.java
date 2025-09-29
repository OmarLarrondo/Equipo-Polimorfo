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
        sb.append(" Reproduciendo la Saga: "+nombre+ "\n");
        for (ProductoComponente prodComp : componentes) {
            sb.append("==").append(prodComp.reproducir());   
        }
        return sb.toString();
    }

    /**
     * Agrega un componente a la saga.
     *
     * @param componente el componente a agregar
     */
    public void agregarComponente(ProductoComponente componente) {
        if (componente != null) {
            componentes.add(componente);
        }
    }

    /**
     * Remueve un componente de la saga.
     *
     * @param componente el componente a remover
     */
    public void removerComponente(ProductoComponente componente) {
        componentes.remove(componente);
    }

    /**
     * Obtiene la lista de componentes hijos de la saga.
     *
     * @return una nueva lista con los componentes de la saga
     */
    public List<ProductoComponente> obtenerHijos() {
        return new ArrayList<>(componentes);
    }

    /**
     * Verifica si este producto es compuesto (siempre true para sagas).
     *
     * @return true, ya que las sagas siempre son productos compuestos
     */
    public boolean esCompuesto() {
        return true;
    }

    /**
     * Obtiene la información completa de la saga y todos sus componentes.
     *
     * @return la información completa de la saga en formato String
     */
    public String obtenerInformacionCompleta() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== SAGA: ").append(nombre).append(" ===\n");
        sb.append("Género: ").append(genero).append("\n");
        sb.append("Precio individual: $").append(String.format("%.2f", precio)).append("\n");
        sb.append("Precio con descuento: $").append(String.format("%.2f", getPrecioSaga())).append("\n");
        sb.append("Descuento aplicado: ").append(String.format("%.1f", descuento * 100)).append("%\n");
        sb.append("Duración total: ").append(getMinutosDuracion()).append(" minutos\n");
        sb.append("Componentes:\n");

        for (ProductoComponente componente : componentes) {
            sb.append("  • ").append(componente.getNombre());
            if (componente instanceof Saga) {
                sb.append(" (Saga)");
            }
            sb.append(" - ").append(componente.getGenero());
            sb.append(" - $").append(String.format("%.2f", componente.getPrecio())).append("\n");
        }

        return sb.toString();
    }

    /**
     * Obtiene la duración total de la saga en minutos.
     * Alias del método getMinutosDuracion() para cumplir con el diagrama.
     *
     * @return la duración total en minutos
     */
    public int getDuracionTotal() {
        return getMinutosDuracion();
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

    /**
     * Compara este objeto Saga con otro objeto para determinar si son iguales.
     * <p>Dos sagas se consideran iguales si tienen la misma lista de componentes y el mismo descuento.
     * La comparación maneja correctamente valores null, verifica el tipo del objeto y usa
     * Double.doubleToLongBits() para comparar valores double de forma precisa.</p>
     *
     * @param obj el objeto a comparar con esta saga
     * @return true si los objetos son iguales, false en caso contrario
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Saga other = (Saga) obj;
        if (componentes == null) {
            if (other.componentes != null)
                return false;
        } else if (!componentes.equals(other.componentes))
            return false;
        if (Double.doubleToLongBits(descuento) != Double.doubleToLongBits(other.descuento))
            return false;
        return true;
    }
    
}
