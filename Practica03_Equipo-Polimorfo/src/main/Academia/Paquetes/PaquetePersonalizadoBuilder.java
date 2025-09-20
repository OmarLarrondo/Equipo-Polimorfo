package main.Academia.Paquetes;

import main.herramientas.Herramienta;
import main.herramientas.HerramientaConcreteFactory;
import main.herramientas.HerramientaFactory;
import main.PaquetesHerramientas.PaquetePersonalizado;
import main.PaquetesHerramientas.PaquetesHerramientas;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementación concreta del patrón Builder para construir paquetes personalizados
 * de herramientas ninja. Permite agregar diferentes tipos de herramientas
 * en cantidades específicas.
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class PaquetePersonalizadoBuilder implements PaqueteBuilder {

    /** Lista de herramientas que conformarán el paquete personalizado */
    private List<Herramienta> herramientas;

    /** Factory para crear las herramientas individuales */
    private HerramientaFactory herramientaFactory;

    /**
     * Constructor del builder de paquetes personalizados.
     * Inicializa la lista de herramientas y la factory.
     */
    public PaquetePersonalizadoBuilder() {
        this.herramientas = new ArrayList<>();
        this.herramientaFactory = new HerramientaConcreteFactory();
    }

    /**
     * Reinicia el builder para construir un nuevo paquete.
     *
     * @return Esta instancia del builder para encadenamiento de métodos
     */
    @Override
    public PaqueteBuilder reset() {
        this.herramientas.clear();
        return this;
    }

    /**
     * Agrega kunais al paquete en la cantidad especificada.
     *
     * @param cantidad Número de kunais a agregar
     * @return Esta instancia del builder para encadenamiento de métodos
     */
    @Override
    public PaqueteBuilder addKunai(int cantidad) {
        crearHerramienta("kunai", cantidad);
        return this;
    }

    /**
     * Agrega shurikens al paquete en la cantidad especificada.
     *
     * @param cantidad Número de shurikens a agregar
     * @return Esta instancia del builder para encadenamiento de métodos
     */
    @Override
    public PaqueteBuilder addShuriken(int cantidad) {
        crearHerramienta("shuriken", cantidad);
        return this;
    }

    /**
     * Agrega papeles bomba al paquete en la cantidad especificada.
     *
     * @param cantidad Número de papeles bomba a agregar
     * @return Esta instancia del builder para encadenamiento de métodos
     */
    @Override
    public PaqueteBuilder addPapelBomba(int cantidad) {
        crearHerramienta("papel_bomba", cantidad);
        return this;
    }

    /**
     * Agrega bombas de humo al paquete en la cantidad especificada.
     *
     * @param cantidad Número de bombas de humo a agregar
     * @return Esta instancia del builder para encadenamiento de métodos
     */
    @Override
    public PaqueteBuilder addBombaHumo(int cantidad) {
        crearHerramienta("bomba_humo", cantidad);
        return this;
    }

    /**
     * Agrega botiquines al paquete en la cantidad especificada.
     *
     * @param cantidad Número de botiquines a agregar
     * @return Esta instancia del builder para encadenamiento de métodos
     */
    @Override
    public PaqueteBuilder addBotiquin(int cantidad) {
        crearHerramienta("botiquin", cantidad);
        return this;
    }

    /**
     * Construye y retorna el paquete personalizado con todas las herramientas agregadas.
     *
     * @return Paquete de herramientas personalizado construido
     */
    @Override
    public PaquetesHerramientas build() {
        return new PaquetePersonalizado(new ArrayList<>(herramientas));
    }

    /**
     * Método auxiliar para crear herramientas usando la factory y agregarlas a la lista.
     *
     * @param tipo Tipo de herramienta a crear
     * @param cantidad Cantidad de herramientas a crear
     */
    private void crearHerramienta(String tipo, int cantidad) {
        for (int i = 0; i < cantidad; i++) {
            Herramienta herramienta = herramientaFactory.crearHerramienta(tipo, 1);
            herramientas.add(herramienta);
        }
    }
}