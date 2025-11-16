package patrones.prototype;

import java.util.HashMap;
import java.util.Map;

import mvc.modelo.entidades.ObjetoJuego;
import mvc.modelo.entidades.paleta.Paleta;

/**
 * Implementación del patrón Prototype para la creación de objetos
 * {@link Paleta} a partir de prototipos previamente registrados.
 *
 * <p>Esta clase mantiene un repositorio interno de paletas base, cada una
 * asociada a un nombre, permitiendo clonar dichas instancias para crear
 * nuevas paletas sin necesidad de construirlas desde cero.</p>
 *
 * @author Equipo-polimorfo
 * @version 1.0
 */
public class PrototipoPaleta  implements Prototipo {
    /** Mapa que almacena las paletas prototipo por nombre(ejemplo: [IA, Paleta]) */ 
    private Map<String, Paleta> prototipos = new HashMap<>();

    /**
     * Registra un nuevo prototipo de paleta.
     *
     * <p>El nombre proporcionado funcionará como identificador único. Si ya
     * existe un prototipo con el mismo nombre, será reemplazado.</p>
     *
     * @param nombre nombre asociado al prototipo.
     * @param paleta instancia base de {@link Paleta} que actuará como prototipo.
     */
    public void registrarPrototipo(String nombre, Paleta paleta){
        prototipos.put(nombre, paleta);
    }

    /**
     * Crea una paleta personalizada a partir de un prototipo existente.
     *
     * <p>Primero se clona el prototipo identificado por {@code nombreBase},
     * y posteriormente se aplica la configuración proporcionada para ajustar
     * atributos como colores, velocidad, tamaño o espinas etc...</p>
     *
     * @param nombreBase nombre del prototipo a clonar.
     * @param config configuración adicional a aplicar sobre la paleta clonada.
     * @return una instancia nueva de {@link Paleta} configurada.
     */
    public Paleta crearPaletaPersonalizada(String nombreBase,ConfigPaleta config){
        Paleta personaliPaleta = (Paleta)clonar(nombreBase);
        config.aplicarA(personaliPaleta);
        return personaliPaleta;
    }

    /**
     * Crea un clon del prototipo identificado por el nombre proporcionado.
     *
     * <p>El método genera una nueva instancia de {@link Paleta} copiando los
     * atributos espaciales (posición y tamaño) del prototipo original.</p>
     *
     * <p>Si el nombre no corresponde a un prototipo registrado, se lanzará
     * una excepción.</p>
     *
     * @param nombre identificador del prototipo a clonar.
     * @return una nueva instancia de {@link ObjetoJuego}, concretamente una {@link Paleta}.
     * @throws IllegalArgumentException si no existe un prototipo con ese nombre.
     */
    @Override
    public ObjetoJuego clonar(String nombre) {
        Paleta base = prototipos.get(nombre);
        if (base == null) {
            throw new IllegalArgumentException("No existe prototipo con el nombre: " + nombre);
        }

        return new Paleta(base.obtenerX(), base.obtenerY(), base.obtenerAncho(), base.obtenerAlto());
    }
        
    
}
