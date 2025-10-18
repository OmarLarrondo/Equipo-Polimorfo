package modelo.decorador;

import java.util.List;

import modelo.componente.ComponentePC;
import modelo.computadora.ComputadoraBase;


/**
 * Clase abstracta que implemneta el patron Decorator para agregar Componentes
 * a las computadoras. Permite decorar dinamicamente un ComponeneteComputadora base
 * con componenetes, manteniendo la interfaz uniforme.
 * 
 * <p>Esta clase actua como decorador abstracto en el patron Decorator,
 * manteniendo una referencia al componente que decora (ComputadoraBase) y
 * definiendo la estructura basica que deben seguir todos los decoradores
 * concretos de componentes.</p>
 * 
 * <p>Las subclases concretas deben implementar los metodos abstractos para
 * definir como cada componente especifico modifica la descripcion y el
 * precio de la computadora base.</p>
 * 
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public abstract class SoftwareDecorator implements ComputadoraBase {
    /**El componente computadora  que sera decorado con componenetes... */
    protected ComputadoraBase computadora;
    /**El nombre del software */
    protected String nombreSoftware;
    /**precio del software */
    protected double precioSoftware;


    /**
     *  Constructor que inicializa el decorador con el componente computadora base,
     * asi como el nombre del sofware y el precio del software.
     * 
     * @param computadora el componente computadora que sera decorado, no puede ser null.
     * @param nombreSoftware el nombre del software a inicializar.
     * @param precioSoftware el precio del sotfware a inicializar.
     * @throws IllegalArgumentException si algo sale mal.
     */
    public SoftwareDecorator(ComputadoraBase computadora, String nombreSoftware, double precioSoftware) {
        if(computadora == null){
            throw new IllegalArgumentException("La computadora base no puede ser nulla");
        }
        this.computadora = computadora;
        this.nombreSoftware = nombreSoftware;
        this.precioSoftware = precioSoftware;
    }

    /**
     * OBtiene la descripcion de la computadora decorada con el componente.
     * Las subclases deben implementar este metodo para agregar la descripcion
     * del componenete especifico a la descripcion de la computdora base.
     * 
     * @return String con la descripcion completa de la computadora incliuyendo el componente.
     */
    @Override
    public abstract String obtenerDescripcion();

    /**
     * Calcula el precio total de la computadora incluyendo el software.
     *
     * @return El precio total de la computadora base mas el precio del software.
     */
    @Override
    public double obtenerPrecioTotal(){
        return computadora.obtenerPrecioTotal() + precioSoftware;
    }
    /**
     * Obtiene la lista de componentes de la computadora decorada.
     *
     * @return La lista de componentes de la computadora base.
     */
    @Override
    public List<ComponentePC> obtenerComponentes(){
        return computadora.obtenerComponentes();
    }
    /**
     * Agrega un componente a la computadora decorada.
     *
     * @param componente El componente a agregar a la computadora.
     */
    @Override
    public void agregarComponente(ComponentePC componente){
        computadora.agregarComponente(componente);
    }
    /**
     * Verifica si la computadora tiene un software especifico instalado.
     * Comprueba si el nombre del software actual coincide con el buscado,
     * o delega la busqueda a la computadora decorada.
     *
     * @param sotfwareABuscar El nombre del software a buscar.
     * @return {@code true} si la computadora tiene el software instalado,
     *         {@code false} en otro caso.
     */
    @Override
    public boolean tieneSotfware(String sotfwareABuscar){
        return nombreSoftware.equals(sotfwareABuscar) || computadora.tieneSotfware(sotfwareABuscar);
    }
    

}
