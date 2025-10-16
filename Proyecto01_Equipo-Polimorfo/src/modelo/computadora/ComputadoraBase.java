package modelo.computadora;

import java.util.List;

import modelo.componente.ComponentePC;

/**
 * Interfaz que define el componente base del patron Decorator para las computadoras.
 * Permite tratar de manera uniforme tanto  computadoras basicas como computadoras 
 * personalizadas con (PONER LAS COSAS PERSONALIZADAS), facilitando el aplicado 
 * dinamico de decoradores para crear combinaciones personalizados computadoras.
 * 
 * @author Equipo-Polimorfo
 * @version 1.0
 *  
 */
public interface ComputadoraBase {
    /**
     * Obtiene la descripcion completa de la computadora. (PONER EJEMPLO)
     * 
     * @return String conla descripcion de la computadora en formato:"
     * 
     */
    public String obtenerDescripcion();

    /**
     * Calcula el precio total de la computadora, sumando el costo base de la computadora,
     * mas el costo de toods los componenetes agregados.
     * @return
     */
    public double obtenerPrecioTotal();

    /**
     * Obtiene los componenetes de la compitadora.
     * @return
     */
    public List<ComponentePC> obtenerComponentes();

    /**
     * Agrega un componente a la computadora.
     * Se deben hacer las validaciones necesarias antes de agregarlo.
     * @param componente
     */
    public void agregarComponente(ComponentePC componente);

    /**
     * Checa si la computadora tiene software.
     * @param sotfwareABuscar
     * @return {@code true } si la computadora tiene software. {@code false} en otro caso
     */
    public boolean tieneSotfware(String sotfwareABuscar);
    
}
