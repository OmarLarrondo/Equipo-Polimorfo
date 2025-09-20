package main.Academia.Campos;

/**

 * Interfaz del patron Factory para la creacion de campos de entrenamiento.
 * Define el contrato para las fabricas que crean diferentes tipos de Campo entranimeinto.
 * 
>>>>>>> 6377a9a (Mis cambios locales antes de pull)
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public interface CampoFactory {

    /** 
     * Crea un campo de entranimiento dependiendo la suma de niveles de los integrantes.
     * @param sumaNiveles La suma total de los integrantes.
>>>>>>> 6377a9a (Mis cambios locales antes de pull)
     */
    CampoEntrenamiento crearCampo(int sumaNiveles);
}
