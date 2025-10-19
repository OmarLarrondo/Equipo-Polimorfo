package modelo.computadora;

import java.util.ArrayList;
import java.util.List;

import modelo.componente.ComponentePC;

/**
 * Clase que representa una computadora basica sin software personalizado.
 *
 * <p>Esta clase implementa la interfaz {@code ComputadoraBase} y actua como el
 * componente concreto base en el patron {@code Decorator}. Representa una computadora basica
 * con una lista de componentes de hardware (CPU, RAM, GPU, MotherBoard, Disco, FuenteAlimentacion, Gabinete)
 * y un nombre (por ejemplo: "PC Personalizada", "PC Prearmada - Gama Alta").
 *
 * <p>A las computadoras basicas se les puede agregar software posteriormente mediante
 * decoradores (WindowsDecorator, OfficeDecorator, PhotoshopDecorator, etc.).
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class ComputadoraBasica implements ComputadoraBase {
    /**La lista de compoentes de la computadora */
    private List<ComponentePC> componentes;
    /**EL nombre de la computadora */
    private String nombre;

    /**
     * Constructor para crear una computadora basica con un nombre especificado.
     * La lista de componentes se inicializa vacia y se agregan posteriormente.
     *
     * @param nombre El nombre de la computadora (ejemplo: "PC Personalizada", "PC Prearmada - Gama Alta").
     * @throws IllegalArgumentException si el nombre es nulo.
     */
    public ComputadoraBasica(String nombre) {
        if(nombre == null){
            throw new IllegalArgumentException("El nombre no puede ser nulo");
        }
        this.nombre = nombre;
        this.componentes = new ArrayList<>();
    }

    /**
     * Obtiene la descripcion completa de la computadora basica.
     *
     * <p>Retorna una cadena con el nombre de la computadora seguido de
     * la lista de todos sus componentes con sus detalles.
     *
     * @return String con el nombre y detalles de cada componente.
     */
    @Override
    public String obtenerDescripcion() {
        return componentes.stream()
            .map(componente -> "  - " + componente.mostrarDetalles())
            .reduce(nombre, (descripcion, detalle) -> descripcion + "\n" + detalle);
    }
    
    /**
     * Obtiene el precio total de la computadora basica.
     *
     * <p>Calcula la suma de los precios de todos los componentes en la lista.
     * Si la lista esta vacia, retorna 0.0.
     *
     * @return El precio total de la computadora basica (suma de precios de componentes).
     */
    @Override
    public double obtenerPrecioTotal() {
        return componentes.stream()
            .mapToDouble(ComponentePC::obtenerPrecio)
            .sum();
    }

    /**
     * Metodo para obtener los componenetes de la computadora basica.
     * 
     * @return Los componenetes de la computadora.
     */
    @Override
    public List<ComponentePC> obtenerComponentes() {
        if(componentes != null){
        return componentes;
        }else{
            System.out.println("No hay componentes en la computadora basica");
            return null;
        }
    }

    /**
     * Agrega un componente a la lista de componentes de la computadora basica.
     *
     * <p>Valida que el componente no sea nulo antes de agregarlo. Si el componente
     * es valido, lo agrega a la lista de componentes de la computadora.
     *
     * @param componente El componente a agregar a la computadora.
     * @throws IllegalArgumentException si el componente es nulo.
     */
    @Override
    public void agregarComponente(ComponentePC componente) {
        if(componente == null){
            throw new IllegalArgumentException("El componente no puede ser nulo");
        }
        componentes.add(componente);
    }

    /**
     * Verifica si la computadora basica tiene un software especifico instalado.
     *
     * <p>Una computadora basica no tiene ningun software instalado por defecto,
     * por lo que siempre retorna {@code false}. El software se agrega mediante
     * decoradores (WindowsDecorator, OfficeDecorator, etc.).
     *
     * @param sotfwareABuscar El nombre del software a buscar.
     * @return {@code false} siempre, ya que la computadora basica no tiene software.
     */
    @Override
    public boolean tieneSotfware(String sotfwareABuscar) {
        return false;
    }


}
