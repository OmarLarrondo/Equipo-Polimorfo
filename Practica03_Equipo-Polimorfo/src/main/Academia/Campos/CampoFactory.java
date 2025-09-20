package main.Academia.Campos;

/**
 * Interfaz del patrón Factory para la creación de campos de entrenamiento.
 * Define el contrato para crear campos según la suma de niveles de habilidad.
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public interface CampoFactory {

    /**
     * Crea un campo de entrenamiento según la suma de niveles de habilidad.
     *
     * @param sumaNiveles Suma total de niveles de habilidad del grupo
     * @return Campo de entrenamiento apropiado
     */
    CampoEntrenamiento crearCampo(int sumaNiveles);
}
