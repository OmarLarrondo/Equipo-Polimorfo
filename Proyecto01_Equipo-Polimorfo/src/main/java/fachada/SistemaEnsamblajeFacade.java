package fachada;

import modelo.builder.DirectorPC;
import modelo.builder.PCBuilder;
import modelo.builder.PCPersonalizadoBuilder;
import modelo.builder.PCPrearmadoBuilder;
import modelo.computadora.ComputadoraBase;
import modelo.decorador.*;
import modelo.estrategia.CompatibilidadMixta;
import modelo.estrategia.EstrategiaCompatibilidad;
import modelo.estrategia.ResultadoCompatibilidad;
import modelo.fabrica.ComponenteFactory;
import modelo.fabrica.IntelFactory;
import modelo.inventario.Inventario;
import modelo.ticket.Ticket;

/**
 * Clase fachada que simplifica la interaccion con el sistema de ensamblaje de computadoras.
 * Implementa el patron Facade para ocultar la complejidad de los subsistemas subyacentes
 * (Builder, Factory, Decorator, Strategy, Singleton) y proporcionar una interfaz unificada
 * y facil de usar para el ensamblaje, personalizacion y verificacion de computadoras.
 *
 * <p>Esta fachada coordina las siguientes operaciones:
 * <ul>
 *   <li>Construccion de PCs personalizadas y prearmadas usando el patron Builder</li>
 *   <li>Agregado de software adicional mediante decoradores</li>
 *   <li>Verificacion de compatibilidad de componentes usando estrategias</li>
 *   <li>Generacion de tickets de compra</li>
 *   <li>Acceso al catalogo de componentes disponibles</li>
 * </ul>
 *
 * <p>El sistema utiliza una estrategia de compatibilidad mixta por defecto, que permite
 * ensamblar computadoras con componentes Intel y AMD aplicando adaptadores cuando sea necesario.
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class SistemaEnsamblajeFacade {

    private Inventario inventario;
    private DirectorPC director;
    private EstrategiaCompatibilidad estrategia;
    private int contadorTickets;

    /**
     * Construye una nueva instancia de SistemaEnsamblajeFacade.
     * Inicializa el inventario singleton, configura la estrategia de compatibilidad mixta,
     * y prepara el director con un builder temporal de Intel.
     * El contador de tickets comienza en 0.
     */
    public SistemaEnsamblajeFacade() {
        this.inventario = Inventario.getInstance();
        this.estrategia = new CompatibilidadMixta();
        PCBuilder builderTemporal = new PCPersonalizadoBuilder(new IntelFactory());
        this.director = new DirectorPC(builderTemporal);
        this.contadorTickets = 0;
    }

    /**
     * Inicializa el sistema cargando el catalogo completo de componentes disponibles.
     * Este metodo debe ser llamado antes de comenzar a construir computadoras para
     * asegurar que todos los componentes esten disponibles en el inventario.
     */
    public void inicializarSistema() {
        inventario.inicializarCatalogo();
    }

    /**
     * Construye una computadora personalizada utilizando la fabrica de componentes especificada.
     * Crea un builder personalizado con la factory proporcionada y usa el director
     * para orquestar la construccion de una PC completa con todos los componentes.
     *
     * <p>El proceso incluye la seleccion e instalacion de CPU, RAM, MotherBoard, GPU,
     * almacenamiento, fuente de alimentacion y gabinete, garantizando la compatibilidad
     * mediante la factory especificada (Intel o AMD).
     *
     * @param factory la fabrica de componentes a utilizar para crear CPU y MotherBoard compatibles,
     *               puede ser IntelFactory o AMDFactory
     * @return la computadora personalizada completa con todos los componentes instalados
     */
    public ComputadoraBase construirPCPersonalizada(ComponenteFactory factory) {
        PCBuilder builder = new PCPersonalizadoBuilder(factory);
        director.cambiarBuilder(builder);
        return director.construirPCCompleta();
    }

    /**
     * Construye una computadora prearmada segun el tipo de configuracion especificado.
     * Utiliza configuraciones predefinidas del inventario para ensamblar PCs
     * de diferentes gamas: baja, media o alta.
     *
     * <p>Las configuraciones prearmadas incluyen conjuntos balanceados de componentes
     * optimizados para diferentes rangos de precio y rendimiento. El tipo debe coincidir
     * con uno de los tipos disponibles en el inventario: "Gama Baja", "Gama Media" o "Gama Alta".
     *
     * @param tipo el tipo de PC prearmada a construir, debe ser "Gama Baja", "Gama Media" o "Gama Alta"
     * @return la computadora prearmada completa segun la configuracion del tipo especificado
     */
    public ComputadoraBase construirPCPrearmada(String tipo) {
        PCBuilder builder = new PCPrearmadoBuilder(tipo);
        director.cambiarBuilder(builder);
        return director.construirPCCompleta();
    }

    /**
     * Agrega software adicional a una computadora mediante el patron Decorator.
     * Verifica que el software no este ya instalado antes de aplicar el decorador correspondiente.
     * Cada software puede ser instalado una sola vez en la misma computadora.
     *
     * <p>Software disponible y sus precios:
     * <ul>
     *   <li>Windows 10/11 - $2799.00</li>
     *   <li>Microsoft Office 365 - $1899.00</li>
     *   <li>Adobe Photoshop - $2399.00</li>
     *   <li>AutoCAD - $3499.00</li>
     *   <li>WSL (Windows Subsystem for Linux) - $0.00</li>
     * </ul>
     *
     * @param computadora la computadora base a la que se agregara el software
     * @param tipoSoftware el nombre del software a instalar, debe ser uno de los tipos disponibles
     * @return la computadora decorada con el software agregado, o la misma computadora si
     *         el software ya estaba instalado
     */
    public ComputadoraBase agregarSoftware(ComputadoraBase computadora, String tipoSoftware) {
        if (computadora.tieneSoftware(tipoSoftware)) {
            return computadora;
        }

        return switch (tipoSoftware) {
            case "Windows 10/11" ->
                new WindowsDecorator(computadora, "Windows 10/11", 2799.00);
            case "Microsoft Office 365" ->
                new OfficeDecorator(computadora, "Microsoft Office 365", 1899.00);
            case "Adobe Photoshop" ->
                new PhotoshopDecorator(computadora, "Adobe Photoshop", 2399.00);
            case "AutoCAD" ->
                new AutoCADDecorator(computadora, "AutoCAD", 3499.00);
            case "WSL" ->
                new WSLDecorator(computadora, "WSL (Windows Subsystem for Linux)", 0.00);
            default ->
                computadora;
        };
    }

    /**
     * Verifica la compatibilidad de los componentes de una computadora.
     * Utiliza la estrategia de compatibilidad configurada para analizar todos los componentes
     * y determinar si son compatibles entre si, generando advertencias y aplicando
     * adaptadores cuando sea necesario.
     *
     * <p>La verificacion incluye:
     * <ul>
     *   <li>Compatibilidad entre CPU y MotherBoard (socket, chipset)</li>
     *   <li>Potencia suficiente de la fuente de alimentacion</li>
     *   <li>Deteccion de sistemas mixtos (Intel + AMD)</li>
     *   <li>Aplicacion automatica de adaptadores para CPUs AMD si es necesario</li>
     * </ul>
     *
     * @param computadora la computadora cuyos componentes seran verificados
     * @return un objeto ResultadoCompatibilidad que contiene el estado de compatibilidad,
     *         advertencias generadas y componentes adaptados
     */
    public ResultadoCompatibilidad verificarCompatibilidad(ComputadoraBase computadora) {
        return estrategia.verificarCompatibilidad(computadora.obtenerComponentes());
    }

    /**
     * Genera un ticket de compra para una computadora ensamblada.
     * Crea un ticket detallado que incluye toda la informacion de la compra:
     * componentes de hardware, software instalado, precio total, informacion de
     * compatibilidad y datos del cliente.
     *
     * <p>Cada ticket recibe un numero unico incremental que permite identificar
     * y rastrear cada compra en el sistema. El contador de tickets se incrementa
     * automaticamente con cada llamada a este metodo.
     *
     * @param computadora la computadora completa (con hardware y posiblemente software) que se esta vendiendo
     * @param cliente el nombre del cliente que realiza la compra
     * @param compatibilidad el resultado de la verificacion de compatibilidad de los componentes
     * @return un objeto Ticket que puede ser usado para generar el recibo impreso de la compra
     */
    public Ticket generarTicket(ComputadoraBase computadora, String cliente,
                                ResultadoCompatibilidad compatibilidad) {
        contadorTickets++;
        return new Ticket(contadorTickets, computadora, cliente, compatibilidad);
    }

    /**
     * Obtiene el inventario singleton que contiene el catalogo de componentes disponibles.
     * El inventario incluye todos los componentes de hardware organizados por tipo
     * (CPU, RAM, MotherBoard, GPU, Disco, FuenteAlimentacion, Gabinete) y las
     * configuraciones prearmadas disponibles.
     *
     * @return el inventario singleton con acceso al catalogo completo de componentes
     */
    public Inventario obtenerCatalogo() {
        return inventario;
    }
}
