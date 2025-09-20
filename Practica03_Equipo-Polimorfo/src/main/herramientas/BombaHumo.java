package herramientas;

/**
 * Representa una herramienta concreta de tipo Bomba de Humo.
 * Esta clase hereda de {@link Herramienta} y solo se utiliza para 
 * distinguir el tipo de herramienta en el sistema.
 * <p>
 * No tiene métodos adicionales ("Bueno en el diagrama de clases no hay") aparte de los 
 * de la clase abstracta Herramienta
 * </p>
 * 
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class BombaHumo extends Herramienta {

    /**
     * Crea una nueva Bomba de Humo con un nombre y peso específicos.
     *
     * @param nombre Nombre de la herramienta (por ejemplo, "Bomba de humo")
     * @param peso Peso de la herramienta en unidades compatibles (no negativo)
     * @param cantidad Cantidad de herramientas
     */
    public BombaHumo(String nombre, double peso,int cantidad ){
        super(nombre, peso, cantidad);
    }
}
