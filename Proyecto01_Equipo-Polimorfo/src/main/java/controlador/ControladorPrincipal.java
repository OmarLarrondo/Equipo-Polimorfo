package controlador;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import fachada.SistemaEnsamblajeFacade;
import modelo.componente.ComponentePC;
import modelo.computadora.ComputadoraBase;
import modelo.estrategia.ResultadoCompatibilidad;
import modelo.fabrica.AMDFactory;
import modelo.fabrica.ComponenteFactory;
import modelo.fabrica.IntelFactory;
import modelo.inventario.Inventario;
import modelo.ticket.Ticket;
import persistencia.ServicioPersistencia;
import vista.Vista;

/**
 * Controlador principal del sistema de ensamblaje de computadoras MonosChinos MX.
 * Implementa el patron MVC (Model-View-Controller) como coordinador central que gestiona
 * la interaccion entre la vista, el modelo (fachada del sistema) y la persistencia de datos.
 *
 * <p>Este controlador orquesta todo el flujo de negocio del sistema:
 * <ul>
 *   <li>Construccion de PCs personalizadas y prearmadas</li>
 *   <li>Seleccion interactiva de componentes</li>
 *   <li>Agregado de software adicional</li>
 *   <li>Verificacion de compatibilidad</li>
 *   <li>Generacion y persistencia de tickets</li>
 *   <li>Visualizacion del historial de ventas</li>
 * </ul>
 *
 * <p>El controlador delega operaciones complejas a la fachada del sistema
 * y presenta resultados a traves de la vista.
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class ControladorPrincipal {

    private SistemaEnsamblajeFacade sistema;
    private Vista vista;
    private ServicioPersistencia persistencia;

    /**
     * Construye un nuevo controlador principal del sistema.
     * Inicializa la fachada del sistema y configura la vista para la interaccion con el usuario.
     * La persistencia es opcional y puede configurarse posteriormente con {@link #setPersistencia}.
     *
     * @param vista la vista a utilizar para la interaccion con el usuario, no debe ser nula
     * @throws IllegalArgumentException si la vista es nula
     */
    public ControladorPrincipal(Vista vista) {
        if (vista == null) {
            throw new IllegalArgumentException("La vista no puede ser nula");
        }
        this.vista = vista;
        this.sistema = new SistemaEnsamblajeFacade();
        this.persistencia = null;
    }

    /**
     * Configura el servicio de persistencia del controlador.
     * Permite inyectar opcionalmente un servicio de persistencia para guardar
     * tickets y configuraciones de manera permanente.
     *
     * @param persistencia el servicio de persistencia a utilizar, puede ser null
     *                    si no se desea persistencia
     */
    public void setPersistencia(ServicioPersistencia persistencia) {
        this.persistencia = persistencia;
    }

    /**
     * Inicia el controlador y ejecuta el ciclo principal del sistema.
     * Inicializa el catalogo de componentes y entra en un ciclo de menu principal
     * que se mantiene hasta que el usuario decida salir.
     *
     * <p>El ciclo consiste en:
     * <ol>
     *   <li>Mostrar menu principal</li>
     *   <li>Leer opcion del usuario</li>
     *   <li>Procesar la opcion seleccionada</li>
     *   <li>Repetir hasta que el usuario salga</li>
     * </ol>
     */
    public void iniciar() {
        mostrarBienvenida();
        inicializarSistema();
        ejecutarCicloPrincipal();
    }

    /**
     * Muestra el mensaje de bienvenida al usuario.
     */
    private void mostrarBienvenida() {
        Stream.of(
            "==============================================",
            "   Bienvenido a MonosChinos MX",
            "   Sistema de Ensamblaje de Computadoras",
            "==============================================\n"
        ).forEach(vista::mostrarMensaje);
    }

    /**
     * Inicializa el sistema cargando el catalogo de componentes.
     */
    private void inicializarSistema() {
        vista.mostrarMensaje("Inicializando sistema...");
        sistema.inicializarSistema();
        vista.mostrarMensaje("Sistema inicializado correctamente.\n");
    }

    /**
     * Ejecuta el ciclo principal del menu usando recursion.
     * Continua hasta que el usuario seleccione la opcion de salir.
     */
    private void ejecutarCicloPrincipal() {
        Stream.generate(() -> obtenerYProcesarOpcion())
            .takeWhile(continuar -> continuar)
            .forEach(resultado -> {});
    }

    /**
     * Obtiene una opcion del usuario y la procesa, retornando si debe continuar.
     *
     * @return true si debe continuar el ciclo, false si debe terminar
     */
    private boolean obtenerYProcesarOpcion() {
        mostrarMenuPrincipal();
        int opcion = vista.leerOpcion();
        return procesarOpcionYDeterminarContinuacion(opcion);
    }

    /**
     * Procesa una opcion del menu y determina si debe continuar el ciclo.
     *
     * @param opcion la opcion seleccionada por el usuario
     * @return true para continuar el ciclo, false para salir
     */
    private boolean procesarOpcionYDeterminarContinuacion(int opcion) {
        return Optional.of(opcion)
            .filter(o -> o == 4)
            .map(o -> finalizarSistema())
            .orElseGet(() -> procesarOpcionMenuYContinuar(opcion));
    }

    /**
     * Procesa una opcion del menu que no es salir.
     *
     * @param opcion la opcion seleccionada por el usuario
     * @return true para continuar el ciclo
     */
    private boolean procesarOpcionMenuYContinuar(int opcion) {
        procesarOpcionMenu(opcion);
        return true;
    }

    /**
     * Finaliza el sistema mostrando mensaje de despedida.
     *
     * @return false para terminar el ciclo principal
     */
    private boolean finalizarSistema() {
        vista.limpiarPantalla();
        Stream.of(
            "==============================================",
            "   Gracias por usar MonosChinos MX",
            "   Hasta pronto!",
            "=============================================="
        ).forEach(vista::mostrarMensaje);
        return false;
    }

    /**
     * Muestra el menu principal del sistema con todas las opciones disponibles.
     * Presenta las opciones de construccion de PCs, historial y salida.
     */
    private void mostrarMenuPrincipal() {
        List<String> opciones = Arrays.asList(
            "Ensamblar PC Personalizada",
            "Seleccionar PC Prearmada",
            "Ver Historial de Compras",
            "Salir"
        );

        vista.limpiarPantalla();
        vista.mostrarMensaje("\n========== MENU PRINCIPAL ==========");
        vista.mostrarMenu(opciones);
    }

    /**
     * Procesa la opcion seleccionada por el usuario en el menu principal.
     * Delega a los metodos especializados segun la opcion elegida,
     * manejando entradas invalidas con mensajes de error apropiados.
     *
     * @param opcion el numero de opcion seleccionado (1-4)
     */
    private void procesarOpcionMenu(int opcion) {
        Optional.of(opcion)
            .filter(o -> o >= 1 && o <= 4)
            .ifPresentOrElse(
                this::ejecutarAccionSegunOpcion,
                () -> vista.mostrarError("Opcion invalida. Por favor seleccione una opcion valida.")
            );
    }

    /**
     * Ejecuta la accion correspondiente a la opcion del menu.
     * Delega a los metodos apropiados segun la opcion seleccionada.
     *
     * @param opcion la opcion valida a ejecutar (1-3)
     */
    private void ejecutarAccionSegunOpcion(int opcion) {
        switch (opcion) {
            case 1 -> procesarPCPersonalizada();
            case 2 -> procesarPCPrearmada();
            case 3 -> mostrarHistorial();
            default -> {}
        }
    }

    /**
     * Procesa el flujo completo para ensamblar una PC personalizada.
     * Guia al usuario a traves de los siguientes pasos:
     * <ol>
     *   <li>Seleccion de familia de procesador (Intel/AMD)</li>
     *   <li>Creacion de la factory apropiada</li>
     *   <li>Construccion de la PC con la factory seleccionada</li>
     *   <li>Agregado opcional de software</li>
     *   <li>Finalizacion de la compra con generacion de ticket</li>
     * </ol>
     */
    private void procesarPCPersonalizada() {
        vista.limpiarPantalla();
        vista.mostrarMensaje("\n========== ENSAMBLAR PC PERSONALIZADA ==========\n");

        seleccionarFabricaComponentes()
            .map(sistema::construirPCPersonalizada)
            .map(this::agregarSoftwareAdicional)
            .ifPresentOrElse(
                this::finalizarCompra,
                () -> vista.mostrarError("No se pudo construir la PC personalizada.")
            );

        esperarUsuario();
    }

    /**
     * Permite al usuario seleccionar la familia de procesador y retorna la factory correspondiente.
     * Mapea la seleccion a la factory apropiada.
     *
     * @return Optional con la factory seleccionada, vacio si la seleccion es invalida
     */
    private Optional<ComponenteFactory> seleccionarFabricaComponentes() {
        vista.mostrarMensaje("Seleccione la familia de procesador:");
        vista.mostrarMenu(Arrays.asList("Intel", "AMD"));

        int opcion = vista.leerOpcion();

        return Optional.of(opcion)
            .filter(o -> o == 1 || o == 2)
            .map(o -> o == 1 ? new IntelFactory() : new AMDFactory());
    }

    /**
     * Procesa el flujo completo para seleccionar una PC prearmada del catalogo.
     * Presenta las configuraciones disponibles y permite al usuario elegir una,
     * seguido de la opcion de agregar software y finalizar la compra.
     */
    private void procesarPCPrearmada() {
        vista.limpiarPantalla();
        vista.mostrarMensaje("\n========== SELECCIONAR PC PREARMADA ==========\n");

        mostrarConfiguracionesPrearmadas();

        List<String> tiposPC = Arrays.asList("Gama Baja", "Gama Media", "Gama Alta");
        vista.mostrarMensaje("\nSeleccione el tipo de PC:");
        vista.mostrarMenu(tiposPC);

        int opcion = vista.leerOpcion();

        Optional.of(opcion)
            .filter(o -> o >= 1 && o <= 3)
            .map(o -> tiposPC.get(o - 1))
            .map(sistema::construirPCPrearmada)
            .map(this::agregarSoftwareAdicional)
            .ifPresentOrElse(
                this::finalizarCompra,
                () -> vista.mostrarError("Opcion invalida.")
            );

        esperarUsuario();
    }

    /**
     * Muestra las configuraciones de PCs prearmadas disponibles en el catalogo.
     * Itera sobre cada configuracion y presenta sus componentes y precio total.
     */
    private void mostrarConfiguracionesPrearmadas() {
        Inventario inventario = sistema.obtenerCatalogo();
        inventario.obtenerConfiguracionesPrearmadas()
            .forEach((tipo, componentes) -> {
                double precioTotal = componentes.stream()
                    .mapToDouble(ComponentePC::obtenerPrecio)
                    .sum();

                vista.mostrarMensaje(String.format("\n--- %s ---", tipo));
                vista.mostrarMensaje(String.format("Precio total: $%.2f", precioTotal));
                vista.mostrarComponentes(componentes);
            });
    }

    /**
     * Permite al usuario seleccionar y agregar software adicional a la computadora.
     * Presenta un menu de software disponible y aplica decoradores para cada
     * software seleccionado, evitando duplicados.
     *
     * @param pc la computadora base a la que se agregara software
     * @return la computadora decorada con el software seleccionado
     */
    private ComputadoraBase agregarSoftwareAdicional(ComputadoraBase pc) {
        vista.limpiarPantalla();
        vista.mostrarMensaje("\n========== AGREGAR SOFTWARE ADICIONAL ==========\n");

        return Optional.of(pc)
            .filter(computadora -> vista.confirmar("Desea agregar software adicional a su PC?"))
            .map(this::procesarSeleccionSoftware)
            .orElse(pc);
    }

    /**
     * Procesa la seleccion de software por parte del usuario.
     *
     * @param pc la computadora base
     * @return la computadora con el software agregado
     */
    private ComputadoraBase procesarSeleccionSoftware(ComputadoraBase pc) {
        List<String> softwareDisponible = Arrays.asList(
            "Windows 10/11",
            "Microsoft Office 365",
            "Adobe Photoshop",
            "AutoCAD",
            "WSL"
        );

        return IntStream.range(0, softwareDisponible.size())
            .boxed()
            .map(i -> obtenerSoftwareSiUsuarioSelecciona(i, softwareDisponible))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .reduce(pc,
                (computadora, software) -> sistema.agregarSoftware(computadora, software),
                (c1, c2) -> c1
            );
    }

    /**
     * Pregunta al usuario si desea agregar un software especifico.
     * Encapsula la logica de decision del usuario.
     *
     * @param indice el indice del software en la lista
     * @param softwareDisponible la lista de software disponible
     * @return Optional con el nombre del software si el usuario lo selecciona, vacio en caso contrario
     */
    private Optional<String> obtenerSoftwareSiUsuarioSelecciona(int indice, List<String> softwareDisponible) {
        String software = softwareDisponible.get(indice);
        return vista.confirmar(String.format("Agregar %s?", software))
            ? Optional.of(software)
            : Optional.empty();
    }

    /**
     * Finaliza el proceso de compra de una computadora.
     * Realiza los siguientes pasos:
     * <ol>
     *   <li>Muestra la configuracion final de la PC</li>
     *   <li>Verifica compatibilidad de componentes</li>
     *   <li>Solicita confirmacion y nombre del cliente</li>
     *   <li>Genera el ticket de compra</li>
     *   <li>Guarda el ticket si hay persistencia configurada</li>
     *   <li>Muestra el ticket generado</li>
     * </ol>
     *
     * @param pc la computadora configurada final a comprar
     */
    private void finalizarCompra(ComputadoraBase pc) {
        vista.limpiarPantalla();
        vista.mostrarMensaje("\n========== CONFIGURACION FINAL ==========\n");
        vista.mostrarMensaje(pc.obtenerDescripcion());
        vista.mostrarMensaje(String.format("\nPrecio total: $%.2f", pc.obtenerPrecioTotal()));

        ResultadoCompatibilidad compatibilidad = sistema.verificarCompatibilidad(pc);
        mostrarResultadoCompatibilidad(compatibilidad);

        Optional.of(pc)
            .filter(computadora -> vista.confirmar("\nDesea finalizar la compra?"))
            .ifPresentOrElse(
                computadora -> ejecutarFinalizacionCompra(computadora, compatibilidad),
                () -> vista.mostrarMensaje("Compra cancelada.")
            );
    }

    /**
     * Ejecuta los pasos finales de la compra.
     *
     * @param pc la computadora a comprar
     * @param compatibilidad el resultado de verificacion de compatibilidad
     */
    private void ejecutarFinalizacionCompra(ComputadoraBase pc, ResultadoCompatibilidad compatibilidad) {
        String cliente = vista.leerTexto("Ingrese el nombre del cliente:");
        Ticket ticket = sistema.generarTicket(pc, cliente, compatibilidad);

        Optional.ofNullable(persistencia)
            .ifPresent(p -> guardarTicketConMensaje(p, ticket));

        vista.limpiarPantalla();
        vista.mostrarMensaje("\n========== COMPRA FINALIZADA ==========\n");
        vista.mostrarTicket(ticket);
        vista.mostrarMensaje("\nTicket generado exitosamente!");
    }

    /**
     * Guarda un ticket usando el servicio de persistencia y muestra mensaje de resultado.
     * Maneja el resultado de la operacion mostrando mensaje apropiado.
     *
     * @param persistencia el servicio de persistencia a utilizar
     * @param ticket el ticket a guardar
     */
    private void guardarTicketConMensaje(ServicioPersistencia persistencia, Ticket ticket) {
        boolean guardado = persistencia.guardarTicket(ticket);
        vista.mostrarMensaje(guardado
            ? "Ticket guardado en el historial."
            : "Advertencia: No se pudo guardar el ticket en el historial."
        );
    }

    /**
     * Muestra el resultado de la verificacion de compatibilidad de componentes.
     * Presenta el estado de compatibilidad y las advertencias generadas, si existen.
     *
     * @param compatibilidad el resultado de la verificacion a mostrar
     */
    private void mostrarResultadoCompatibilidad(ResultadoCompatibilidad compatibilidad) {
        vista.mostrarMensaje("\n--- Verificacion de Compatibilidad ---");
        vista.mostrarMensaje("Estado: " + (compatibilidad.isCompatible() ? "COMPATIBLE" : "INCOMPATIBLE"));

        Optional.of(compatibilidad.getAdvertencias())
            .filter(Predicate.not(List::isEmpty))
            .ifPresent(advertencias -> {
                vista.mostrarMensaje("\nAdvertencias:");
                advertencias.forEach(adv -> vista.mostrarMensaje("  - " + adv));
            });
    }

    /**
     * Muestra el historial de compras guardadas en el sistema.
     * Si hay persistencia configurada, carga y muestra todos los tickets guardados.
     * Si no hay persistencia o no hay tickets, muestra un mensaje apropiado.
     */
    private void mostrarHistorial() {
        vista.limpiarPantalla();
        vista.mostrarMensaje("\n========== HISTORIAL DE COMPRAS ==========\n");

        Optional.ofNullable(persistencia)
            .map(ServicioPersistencia::cargarTickets)
            .filter(Predicate.not(List::isEmpty))
            .ifPresentOrElse(
                this::mostrarListaTickets,
                () -> vista.mostrarMensaje("No hay compras registradas en el historial.")
            );

        esperarUsuario();
    }

    /**
     * Muestra una lista de tickets del historial.
     * Itera sobre cada ticket y lo presenta de forma formateada.
     *
     * @param tickets la lista de tickets a mostrar
     */
    private void mostrarListaTickets(List<Ticket> tickets) {
        IntStream.range(0, tickets.size())
            .forEach(i -> {
                Ticket ticket = tickets.get(i);
                vista.mostrarMensaje(String.format("\n--- Compra #%d ---", i + 1));
                vista.mostrarTicket(ticket);
                vista.mostrarMensaje("\n" + "-".repeat(70));
            });
    }

    /**
     * Pausa la ejecucion esperando que el usuario presione Enter para continuar.
     * Utiliza la vista para solicitar la interaccion del usuario.
     */
    private void esperarUsuario() {
        vista.leerTexto("\nPresione Enter para continuar...");
    }
}
