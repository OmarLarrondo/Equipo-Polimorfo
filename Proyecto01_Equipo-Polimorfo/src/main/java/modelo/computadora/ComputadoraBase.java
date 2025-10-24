package modelo.computadora;

import java.util.List;

import modelo.componente.ComponentePC;

/**
 * Interfaz que define el componente base del patron Decorator para las computadoras.
 * Permite tratar de manera uniforme tanto computadoras basicas como computadoras
 * personalizadas con software adicional (Windows, Office, Photoshop, AutoCAD, WSL),
 * facilitando la aplicacion dinamica de decoradores para crear combinaciones
 * personalizadas de computadoras.
 *
 * <p>Esta interfaz es implementada por {@link ComputadoraBasica} (componente concreto)
 * y {@link modelo.decorador.SoftwareDecorator} (decorador abstracto), permitiendo
 * agregar funcionalidad de software de forma incremental mediante decoradores concretos.
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public interface ComputadoraBase {
    /**
     * Obtiene la descripcion completa de la computadora.
     *
     * <p>Retorna una cadena que incluye el nombre de la computadora, la lista de
     * componentes de hardware y el software instalado (si tiene decoradores aplicados).
     *
     * <p>Ejemplo de formato:
     * <pre>
     * PC Personalizada
     *   - Procesador(CPU) [Core i7-13700K] - Marca: Intel - Precio: $8500.00
     *   - RAM [16 GB] - Marca: Kingston - Precio: $1200.00
     *   + Software: Windows 10/11
     *   + Software: Microsoft Office 365
     * </pre>
     *
     * @return String con la descripcion completa de la computadora.
     */
    public String obtenerDescripcion();

    /**
     * Calcula el precio total de la computadora.
     *
     * <p>Suma el precio de todos los componentes de hardware y el costo de
     * todo el software agregado mediante decoradores.
     *
     * @return El precio total de la computadora (componentes mas software).
     */
    public double obtenerPrecioTotal();

    /**
     * Obtiene la lista de componentes de hardware de la computadora.
     *
     * <p>Retorna todos los componentes fisicos instalados (CPU, RAM, GPU,
     * MotherBoard, Disco, FuenteAlimentacion, Gabinete). No incluye software.
     *
     * @return Lista de componentes de la computadora.
     */
    public List<ComponentePC> obtenerComponentes();

    /**
     * Agrega un componente de hardware a la computadora.
     *
     * <p>Permite agregar componentes fisicos (CPU, RAM, GPU, etc.) a la
     * computadora. Las implementaciones deben validar que el componente
     * no sea nulo antes de agregarlo.
     *
     * @param componente El componente a agregar a la computadora.
     * @throws IllegalArgumentException si el componente es nulo.
     */
    public void agregarComponente(ComponentePC componente);

    /**
     * Verifica si la computadora tiene un software especifico instalado.
     *
     * <p>Busca en la cadena de decoradores si existe un software con el nombre
     * especificado. Es util para evitar instalar el mismo software multiples veces.
     *
     * @param softwareABuscar El nombre del software a buscar (ejemplo: "Windows 10/11",
     *                        "Microsoft Office 365", "Adobe Photoshop").
     * @return {@code true} si la computadora tiene el software instalado,
     *         {@code false} en otro caso.
     */
    public boolean tieneSoftware(String softwareABuscar);
    
}
