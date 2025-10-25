package modelo.builder;

import modelo.computadora.ComputadoraBase;
import modelo.computadora.ComputadoraBasica;
import modelo.fabrica.ComponenteFactory;
import modelo.inventario.Inventario;
import modelo.componente.ComponentePC;

import java.util.List;
import java.util.Optional;

/**
 * Builder concreto para construir computadoras personalizadas.
 * Este builder permite la construccion paso a paso de una computadora
 * seleccionando componentes individuales, garantizando compatibilidad
 * mediante el uso de una ComponenteFactory especifica (Intel o AMD).
 */
public class PCPersonalizadoBuilder implements PCBuilder {

    private ComputadoraBase computadora;
    private ComponenteFactory factory;

    /**
     * Construye un nuevo PCPersonalizadoBuilder con la fabrica especificada.
     *
     * @param factory La fabrica de componentes a utilizar para crear CPU y MotherBoard compatibles
     */
    public PCPersonalizadoBuilder(ComponenteFactory factory) {
        this.factory = factory;
        this.computadora = new ComputadoraBasica("PC Personalizada");
    }

    /**
     * Reinicia el builder creando una nueva instancia de computadora.
     */
    @Override
    public void reiniciar() {
        this.computadora = new ComputadoraBasica("PC Personalizada");
    }

    /**
     * Construye y agrega un procesador a la computadora.
     * Utiliza la factory para crear un CPU compatible con la familia seleccionada.
     * Filtra los CPUs del inventario segun la marca de la factory (Intel o AMD).
     */
    @Override
    public void construirCPU() {
        obtenerMarcaFactory()
            .flatMap(marca -> Inventario.getInstance()
                .obtenerComponentesPorTipo("CPU")
                .stream()
                .filter(cpu -> cpu.obtenerMarca().equals(marca))
                .findFirst())
            .ifPresent(computadora::agregarComponente);
    }

    /**
     * Construye y agrega memoria RAM a la computadora.
     */
    @Override
    public void construirRAM() {
        List<ComponentePC> rams = Inventario.getInstance()
            .obtenerComponentesPorTipo("RAM");

        if (rams != null && !rams.isEmpty()) {
            computadora.agregarComponente(rams.get(0));
        }
    }

    /**
     * Construye y agrega una placa madre a la computadora.
     * Utiliza la factory para crear un MotherBoard compatible con la familia seleccionada.
     * Obtiene el primer modelo disponible del inventario y lo crea usando la factory
     * para garantizar compatibilidad con la familia de procesador.
     */
    @Override
    public void construirMotherBoard() {
        Inventario.getInstance()
            .obtenerComponentesPorTipo("MotherBoard")
            .stream()
            .findFirst()
            .map(ComponentePC::obtenerNombre)
            .map(this::extraerModeloMotherBoard)
            .map(factory::crearMotherBoard)
            .ifPresent(computadora::agregarComponente);
    }

    /**
     * Construye y agrega una tarjeta grafica a la computadora.
     */
    @Override
    public void construirGPU() {
        List<ComponentePC> gpus = Inventario.getInstance()
            .obtenerComponentesPorTipo("GPU");

        if (gpus != null && !gpus.isEmpty()) {
            computadora.agregarComponente(gpus.get(0));
        }
    }

    /**
     * Construye y agrega almacenamiento a la computadora.
     */
    @Override
    public void construirAlmacenamiento() {
        List<ComponentePC> discos = Inventario.getInstance()
            .obtenerComponentesPorTipo("Disco");

        if (discos != null && !discos.isEmpty()) {
            computadora.agregarComponente(discos.get(0));
        }
    }

    /**
     * Construye y agrega una fuente de alimentacion a la computadora.
     */
    @Override
    public void construirFuente() {
        List<ComponentePC> fuentes = Inventario.getInstance()
            .obtenerComponentesPorTipo("FuenteAlimentacion");

        if (fuentes != null && !fuentes.isEmpty()) {
            computadora.agregarComponente(fuentes.get(0));
        }
    }

    /**
     * Construye y agrega un gabinete a la computadora.
     */
    @Override
    public void construirGabinete() {
        List<ComponentePC> gabinetes = Inventario.getInstance()
            .obtenerComponentesPorTipo("Gabinete");

        if (gabinetes != null && !gabinetes.isEmpty()) {
            computadora.agregarComponente(gabinetes.get(0));
        }
    }

    /**
     * Obtiene la computadora construida.
     *
     * @return La computadora personalizada construida
     */
    @Override
    public ComputadoraBase obtenerResultado() {
        return this.computadora;
    }

    /**
     * Obtiene la marca de procesador que la factory esta configurada para crear.
     * Crea un CPU de prueba usando un modelo generico para determinar si la factory
     * es de tipo Intel o AMD.
     *
     * @return Optional con la marca de la factory (Intel o AMD), vacio si no se puede determinar
     */
    private Optional<String> obtenerMarcaFactory() {
        return Optional.of(factory)
            .map(f -> {
                try {
                    return f.crearCPU("Core i3-13100");
                } catch (IllegalArgumentException e) {
                    try {
                        return f.crearCPU("Ryzen 5 5600G");
                    } catch (IllegalArgumentException ex) {
                        return null;
                    }
                }
            })
            .map(cpu -> cpu.obtenerMarca());
    }

    /**
     * Extrae el nombre del modelo de motherboard del nombre completo.
     * Remueve el prefijo de la marca (ASUS o MSI) para obtener solo el modelo.
     *
     * @param nombreCompleto el nombre completo de la motherboard incluyendo marca
     * @return el nombre del modelo sin la marca
     */
    private String extraerModeloMotherBoard(String nombreCompleto) {
        return Optional.ofNullable(nombreCompleto)
            .map(nombre -> nombre.replaceFirst("^(ASUS|MSI)\\s+", ""))
            .orElse(nombreCompleto);
    }
}
