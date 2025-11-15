package patrones.prototype;

import mvc.modelo.entidades.ObjetoJuego;

/**
 * Interfaz que define el contrato para los prototipos utilizados
 * en la creación de objetos mediante el patrón Prototype.
 *
 * <p>Las clases que implementan esta interfaz deben proporcionar
 * un método de clonación que genere una nueva instancia del
 * {@link ObjetoJuego} correspondiente, copiando sus atributos
 * esenciales y permitiendo diferenciarla mediante un nombre
 * asignado dinámicamente.</p>
 *
 * <p>El uso de este patrón evita la creación directa mediante
 * constructores, permitiendo la duplicación eficiente de
 * objetos configurados previamente</p>
 *
 * @author Equipo-polimorfo
 * @version 1.0
 */
public interface Prototipo {

    /**
     * Crea y devuelve una copia del objeto prototípico.
     *
     * @param nombre el nombre que se asignará al objeto clonado;
     *               permite identificar la instancia generada a
     *               partir del prototipo.
     * @return una nueva instancia de {@link ObjetoJuego} que
     *         representa el clon del prototipo.
     */
    ObjetoJuego clonar(String nombre);
}
