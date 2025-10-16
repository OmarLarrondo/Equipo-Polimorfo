package modelo.computadora;

import java.util.ArrayList;
import java.util.List;

import modelo.componente.ComponentePC;

/**
 * Clase que representa un computadora Basica sin nada personalizado.
 * 
 * <p>Esta clase implemneta la interfaz {@code (ComputadoraBase)} y actua como el 
 * componente concreto base en el patron {@code Decorator}. Representa una computadora basica
 * con una lista de componentes (PONER LOS EJEMPLOS) y un nombre(PONER UN EJEMPLO). </p>
 * 
 * <p>A las computadoras basicas se les puede agregar componentes posteriormente con los 
 * decoradores(componenetes) correspondientes</p>
 * 
 */
public class ComputadoraBasica implements ComputadoraBase {
    /**La lista de compoentes de la computadora */
    private List<ComponentePC> componentes;
    /**EL nombre de la computadora */
    private String nombre;

    /**
     * Constructor para crear una computadora basica con unos componenetes especificados
     *  y un nombre especificado. 
     * @param componentes La lista de los componenetes.
     * @param nombre EL nombre  de la computadora.
     */
    public ComputadoraBasica(List<ComponentePC> componentes, String nombre) {
        this.componentes = componentes;
        if(nombre == null){
            throw new IllegalArgumentException("El nombre no puede ser nulo");
        }else{
            this.nombre = nombre;
        }
        
        this.componentes = new ArrayList<>();
        
    }

    //AQUI NO SE SI SEA COMO EL TOSTRING
    @Override
    public String obtenerDescripcion() {
        //aqui va su codig 
        return null;
    }
    
    /**
     * Obtiene el precio total de la computadora basica.
     * 
     * <p>Se validar y liuego sumar cada uno de la lista de componenetes {@code componentes}.
     * EL precio debe ser mayor o igual a 0.
     * @return El precio total de la computadora basica.
     */
    @Override
    public double obtenerPrecioTotal() {
        //aqui va su codigo 
        return 0;
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
     * Metodo para agregar un componente a la lista de componetes de la computadora basica.
     * 
     */
    @Override
    public void agregarComponente(ComponentePC componente) {
        //CREO QUE IGUAL SE DEBE VERIFCAR QUE SEA UN COMPONETE VALIDO.
        if(componente == null){
            System.out.println("Error: Componenete invalido");
        }
        componentes.add(componente);
    }

    /**
     * Metodo para ver si la computadora basica tiene softwaree.
     * @return {@code true } si tiene software. {@code false} en otro caso. 
     */
    
    @Override
    public boolean tieneSotfware(String sotfwareABuscar) {
        return false;
        //aqui va su codigo
    }


}
