package main.Academia.Campos;

/**

 * Interfaz del patron Factory para la creacion de campos de entrenamiento.
 * Define el contrato para las fabricas que crean diferentes tipos de Campo entranimeinto.
 * 
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public interface CampoFactory {

    /** 
     * Crea un campo de entranimiento dependiendo la suma de niveles de los integrantes.
     * @param sumaNiveles La suma total de los integrantes.
     */
    CampoEntrenamiento crearCampo(int sumaNiveles);
}
