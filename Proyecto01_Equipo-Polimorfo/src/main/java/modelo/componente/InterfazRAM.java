package modelo.componente;

/**
 * Interfaz que representa las operaciones básicas de un módulo de memoria RAM.
 * 
 * <p>Esta interfaz permite que cualquier clase que implemente RAM pueda exponer
 * su capacidad en GB mediante el método {@link #getCapacidadGB()}.
 * 
 * @author Equipo-polimorfo
 * @version 1.0
 */
public interface InterfazRAM {
    /** 
     * <p>Nota importante:
 * <br>//OJOOOOOOOO
 * <br>//EN EL DIAGRAMA DE CLASES
 * <br>//HAY UN ATRIBUTO CAPACIDAD PERO SEGÚN YO AQUI NO SE PUEDE ESO
 * <br>//AL MENOS QUE SEA LA CONSTANTE  NO SE
 * */

    /**
     * Obtiene la capacidad de la memoria RAM.
     *
     * @return capacidad en GB
     */
    int getCapacidadGB();
}
