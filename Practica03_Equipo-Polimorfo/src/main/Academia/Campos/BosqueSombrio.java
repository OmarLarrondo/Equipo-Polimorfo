package main.Academia.Campos;

/**
 * Representa un campo de entrenamiento concreto de tipo Bosque sombrio.
 * Esta clase hereda de {@link CampoEntrenamiento} y se utiliza para 
 * distinguir este tipo específico de campo en el sistema.
 * <p>
 * No tiene métodos adicionales aparte de los definidos en la clase base {@link CampoEntrenamiento}.
 * </p>
 * 
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class BosqueSombrio extends CampoEntrenamiento{

    /**
     * Crea un nuevo campo de entrenamiento de tipo Bosque Sombrio.
     *
     * @param nombre Nombre del campo (por ejemplo, "Bosque sombrio"")
     * @param descripción Descripción detallada del campo
     */
    public BosqueSombrio(String nombre, String descripción) {
        super(nombre, descripción);
    }

    
}
