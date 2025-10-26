package modelo.ticket;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import modelo.computadora.ComputadoraBasica;
import modelo.componente.CPU;
import modelo.componente.Disco;
import modelo.componente.FuenteAlimentacion;
import modelo.componente.Gabinete;
import modelo.componente.GPU;
import modelo.componente.MotherBoard;
import modelo.componente.RAM;
import modelo.componente.JuegoRAMs;
import modelo.componente.ComponentePC;
import modelo.decorador.WindowsDecorator;
import modelo.decorador.OfficeDecorator;
import modelo.decorador.PhotoshopDecorator;
import modelo.estrategia.ResultadoCompatibilidad;

/**
 * Clase de pruebas unitarias para Ticket.
 *
 * <p>Valida la correcta creacion y funcionamiento de tickets de compra,
 * asegurando que los tickets se generen con las especificaciones correctas
 * y que se manejen adecuadamente los casos de parametros invalidos.
 *
 * <p>Esta suite de pruebas verifica:
 * <ul>
 *   <li>Creacion correcta de tickets con todas sus especificaciones</li>
 *   <li>Funcionamiento correcto de getters</li>
 *   <li>Generacion correcta del formato del ticket</li>
 *   <li>Manejo de excepciones para parametros invalidos</li>
 *   <li>Encapsulamiento correcto de la fecha</li>
 *   <li>Formato correcto de todas las secciones del ticket</li>
 * </ul>
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class TicketTest {

    /**
     * Crea una computadora basica de prueba con componentes completos.
     * Esta funcion es pura y no tiene efectos secundarios.
     *
     * @return una computadora basica configurada para pruebas
     */
    private static ComputadoraBasica crearComputadoraPrueba() {
        ComputadoraBasica compu = new ComputadoraBasica("PC Gamer");

        CPU cpu = new CPU("Core i5-13600K", 18999.00, "Intel", "CPU", 14, "x86-64");
        GPU gpu = new GPU("RTX 3060", 8500.00, "NVIDIA", "Tarjeta grafica", "GDDR6", 12);
        MotherBoard mother = new MotherBoard("TUF Gaming B760-Plus", 3500.00, "ASUS",
            "Tarjeta madre", "B760", "LGA 1700", "Intel");
        FuenteAlimentacion fuente = new FuenteAlimentacion("CV650", 1500.00, "Corsair",
            "Fuente de alimentacion", 650, "80+ Bronze");
        Gabinete gabinete = new Gabinete("H6 Flow ATX", 1900.00, "NZXT", "Gabinete", "ATX");
        RAM ram = new RAM("8GB DDR4", 800.00, "Kingston", "RAM", 8, "DDR4");
        Disco disco = new Disco("500GB SSD", 1200.00, "Kingston", "SSD", 500, "SATA");

        compu.agregarComponente(cpu);
        compu.agregarComponente(gpu);
        compu.agregarComponente(mother);
        compu.agregarComponente(fuente);
        compu.agregarComponente(gabinete);
        compu.agregarComponente(ram);
        compu.agregarComponente(disco);

        return compu;
    }

    /**
     * Crea un resultado de compatibilidad sin advertencias ni adaptaciones.
     * Esta funcion es pura y no tiene efectos secundarios.
     *
     * @return resultado de compatibilidad compatible sin advertencias
     */
    private static ResultadoCompatibilidad crearCompatibilidadLimpia() {
        return new ResultadoCompatibilidad(true, new ArrayList<>(), new ArrayList<>());
    }

    /**
     * Crea un resultado de compatibilidad con advertencias.
     * Esta funcion es pura y no tiene efectos secundarios.
     *
     * @return resultado de compatibilidad con advertencias
     */
    private static ResultadoCompatibilidad crearCompatibilidadConAdvertencias() {
        List<String> advertencias = new ArrayList<>();
        advertencias.add("La fuente de alimentacion podria ser insuficiente para GPU de alto rendimiento");
        advertencias.add("Se recomienda refrigeracion adicional");
        return new ResultadoCompatibilidad(true, advertencias, new ArrayList<>());
    }

    /**
     * Crea un resultado de compatibilidad con componentes adaptados.
     * Esta funcion es pura y no tiene efectos secundarios.
     *
     * @return resultado de compatibilidad con componentes adaptados
     */
    private static ResultadoCompatibilidad crearCompatibilidadConAdaptados() {
        List<ComponentePC> adaptados = new ArrayList<>();
        adaptados.add(new CPU("Ryzen 5 7600X", 12999.00, "AMD", "CPU", 6, "x86-64 (AMD64)"));
        return new ResultadoCompatibilidad(true, new ArrayList<>(), adaptados);
    }

    /**
     * Provee argumentos para probar tickets invalidos.
     *
     * @return Stream de argumentos con datos invalidos
     */
    private static Stream<Arguments> proveedorDatosTicketInvalidos() {
        ComputadoraBasica compu = crearComputadoraPrueba();
        ResultadoCompatibilidad compatibilidad = crearCompatibilidadLimpia();

        return Stream.of(
            Arguments.of(1, null, "Juan Perez", compatibilidad, "computadora null"),
            Arguments.of(1, compu, null, compatibilidad, "cliente null"),
            Arguments.of(1, compu, "", compatibilidad, "cliente vacio"),
            Arguments.of(1, compu, "   ", compatibilidad, "cliente solo espacios"),
            Arguments.of(1, compu, "Juan Perez", null, "compatibilidad null")
        );
    }

    /**
     * Prueba que valida la creacion correcta de un ticket con datos validos.
     *
     * <p>Verifica que el constructor de Ticket cree instancias correctas con:
     * <ul>
     *   <li>Numero de ticket</li>
     *   <li>Computadora configurada</li>
     *   <li>Nombre del cliente</li>
     *   <li>Resultado de compatibilidad</li>
     *   <li>Fecha generada automaticamente</li>
     * </ul>
     */
    @Test
    public void pruebaCrearTicketConDatosValidos() {
        ComputadoraBasica compu = crearComputadoraPrueba();
        ResultadoCompatibilidad compatibilidad = crearCompatibilidadLimpia();

        Ticket ticket = new Ticket(1, compu, "Juan Perez", compatibilidad);

        assertNotNull(ticket, "El ticket no debe ser null");
        assertEquals(1, ticket.obtenerNumeroTicket(), "El numero de ticket debe ser 1");
        assertEquals("Juan Perez", ticket.obtenerCliente(), "El cliente debe ser Juan Perez");
        assertNotNull(ticket.obtenerFecha(), "La fecha debe generarse automaticamente");
        assertSame(compu, ticket.obtenerComputadora(), "La computadora debe ser la misma");
        assertSame(compatibilidad, ticket.obtenerCompatibilidad(), "La compatibilidad debe ser la misma");
    }

    /**
     * Prueba que valida que la fecha se genera automaticamente al crear el ticket.
     *
     * <p>Verifica que la fecha del ticket sea cercana a la fecha actual
     * (dentro de un margen de 1 segundo).
     */
    @Test
    public void pruebaFechaGeneradaAutomaticamente() {
        ComputadoraBasica compu = crearComputadoraPrueba();
        ResultadoCompatibilidad compatibilidad = crearCompatibilidadLimpia();

        Date antes = new Date();
        Ticket ticket = new Ticket(1, compu, "Maria Lopez", compatibilidad);
        Date despues = new Date();

        Date fechaTicket = ticket.obtenerFecha();

        assertTrue(
            !fechaTicket.before(antes) && !fechaTicket.after(despues),
            "La fecha del ticket debe estar entre antes y despues de su creacion"
        );
    }

    /**
     * Prueba parametrizada que verifica el manejo de excepciones para parametros invalidos.
     *
     * <p>Valida que el constructor de Ticket lance IllegalArgumentException cuando se intenta
     * crear un ticket con parametros invalidos.
     *
     * @param numero el numero del ticket
     * @param computadora la computadora (posiblemente null)
     * @param cliente el cliente (posiblemente null o vacio)
     * @param compatibilidad la compatibilidad (posiblemente null)
     * @param descripcion descripcion del caso de prueba
     */
    @ParameterizedTest(name = "Excepcion con {4}")
    @MethodSource("proveedorDatosTicketInvalidos")
    public void pruebaConstructorConDatosInvalidosLanzaExcepcion(int numero,
                                                                  ComputadoraBasica computadora,
                                                                  String cliente,
                                                                  ResultadoCompatibilidad compatibilidad,
                                                                  String descripcion) {
        IllegalArgumentException excepcion = assertThrows(
            IllegalArgumentException.class,
            () -> new Ticket(numero, computadora, cliente, compatibilidad),
            String.format("Deberia lanzar IllegalArgumentException para %s", descripcion)
        );

        assertNotNull(excepcion.getMessage(),
            "El mensaje de error no debe ser null");
    }

    /**
     * Prueba que valida el funcionamiento del getter obtenerNumeroTicket.
     *
     * <p>Verifica que el metodo obtenerNumeroTicket retorne correctamente
     * el numero especificado en el constructor.
     */
    @ParameterizedTest(name = "Numero de ticket: {0}")
    @ValueSource(ints = {1, 100, 999, 5000, 99999})
    public void pruebaObtenerNumeroTicket(int numero) {
        ComputadoraBasica compu = crearComputadoraPrueba();
        ResultadoCompatibilidad compatibilidad = crearCompatibilidadLimpia();

        Ticket ticket = new Ticket(numero, compu, "Cliente Test", compatibilidad);

        assertEquals(numero, ticket.obtenerNumeroTicket(),
            String.format("obtenerNumeroTicket debe retornar %d", numero));
    }

    /**
     * Prueba que valida el funcionamiento del getter obtenerCliente.
     *
     * <p>Verifica que el metodo obtenerCliente retorne correctamente
     * el nombre del cliente especificado en el constructor.
     */
    @Test
    public void pruebaObtenerCliente() {
        ComputadoraBasica compu = crearComputadoraPrueba();
        ResultadoCompatibilidad compatibilidad = crearCompatibilidadLimpia();

        Stream.of("Juan Perez", "Maria Lopez", "Carlos Garcia", "Ana Martinez")
            .forEach(cliente -> {
                Ticket ticket = new Ticket(1, compu, cliente, compatibilidad);
                assertEquals(cliente, ticket.obtenerCliente(),
                    String.format("obtenerCliente debe retornar %s", cliente));
            });
    }

    /**
     * Prueba que valida el encapsulamiento de la fecha del ticket.
     *
     * <p>Verifica que modificar la fecha retornada por obtenerFecha()
     * no afecte la fecha interna del ticket (encapsulamiento correcto).
     */
    @Test
    public void pruebaEncapsulamientoFecha() {
        ComputadoraBasica compu = crearComputadoraPrueba();
        ResultadoCompatibilidad compatibilidad = crearCompatibilidadLimpia();

        Ticket ticket = new Ticket(1, compu, "Test Cliente", compatibilidad);
        Date fechaOriginal = ticket.obtenerFecha();
        Date fechaModificable = ticket.obtenerFecha();

        fechaModificable.setTime(0);

        assertNotEquals(0, ticket.obtenerFecha().getTime(),
            "Modificar la fecha retornada no debe afectar la fecha interna del ticket");
        assertEquals(fechaOriginal.getTime(), ticket.obtenerFecha().getTime(),
            "La fecha interna debe permanecer sin cambios");
    }

    /**
     * Prueba que valida que obtenerPrecioTotal delega correctamente a la computadora.
     *
     * <p>Verifica que el metodo obtenerPrecioTotal del ticket retorne
     * el mismo precio que obtenerPrecioTotal de la computadora.
     */
    @Test
    public void pruebaObtenerPrecioTotalDelegaAComputadora() {
        ComputadoraBasica compu = crearComputadoraPrueba();
        ResultadoCompatibilidad compatibilidad = crearCompatibilidadLimpia();

        Ticket ticket = new Ticket(1, compu, "Test Cliente", compatibilidad);

        assertEquals(compu.obtenerPrecioTotal(), ticket.obtenerPrecioTotal(),
            "obtenerPrecioTotal del ticket debe delegar a la computadora");
    }

    /**
     * Prueba que valida que el precio total incluye el software decorado.
     *
     * <p>Verifica que cuando se agregan decoradores de software,
     * el precio total del ticket incluye el costo del software.
     */
    @Test
    public void pruebaObtenerPrecioTotalConSoftware() {
        ComputadoraBasica compu = crearComputadoraPrueba();
        WindowsDecorator compuConWindows = new WindowsDecorator(compu, "Windows", 350.00);
        OfficeDecorator compuCompleta = new OfficeDecorator(compuConWindows, "Office", 500.00);

        ResultadoCompatibilidad compatibilidad = crearCompatibilidadLimpia();
        Ticket ticket = new Ticket(1, compuCompleta, "Test Cliente", compatibilidad);

        double precioEsperado = compu.obtenerPrecioTotal() + 350.00 + 500.00;

        assertEquals(precioEsperado, ticket.obtenerPrecioTotal(), 0.01,
            "El precio total debe incluir el costo del software");
    }

    /**
     * Prueba que valida la generacion basica del ticket.
     *
     * <p>Verifica que generarTicket() retorne una cadena no vacia
     * que contiene informacion basica del ticket.
     */
    @Test
    public void pruebaGenerarTicketBasico() {
        ComputadoraBasica compu = crearComputadoraPrueba();
        ResultadoCompatibilidad compatibilidad = crearCompatibilidadLimpia();

        Ticket ticket = new Ticket(1, compu, "Juan Perez", compatibilidad);
        String ticketGenerado = ticket.generarTicket();

        assertNotNull(ticketGenerado, "El ticket generado no debe ser null");
        assertFalse(ticketGenerado.isEmpty(), "El ticket generado no debe estar vacio");
    }

    /**
     * Prueba que valida que el ticket contiene el encabezado correcto.
     *
     * <p>Verifica que el ticket generado contenga:
     * <ul>
     *   <li>Nombre de la empresa (MONOS CHINOS MX)</li>
     *   <li>Numero de ticket</li>
     *   <li>Nombre del cliente</li>
     *   <li>Fecha de generacion</li>
     * </ul>
     */
    @Test
    public void pruebaTicketContieneEncabezado() {
        ComputadoraBasica compu = crearComputadoraPrueba();
        ResultadoCompatibilidad compatibilidad = crearCompatibilidadLimpia();

        Ticket ticket = new Ticket(42, compu, "Juan Perez", compatibilidad);
        String ticketGenerado = ticket.generarTicket();

        assertTrue(ticketGenerado.contains("MONOS CHINOS MX"),
            "El ticket debe contener el nombre de la empresa");
        assertTrue(ticketGenerado.contains("Ticket No: 42"),
            "El ticket debe contener el numero de ticket");
        assertTrue(ticketGenerado.contains("Cliente: Juan Perez"),
            "El ticket debe contener el nombre del cliente");
        assertTrue(ticketGenerado.contains("Fecha:"),
            "El ticket debe contener la fecha");
    }

    /**
     * Prueba que valida que el ticket contiene la seccion de componentes.
     *
     * <p>Verifica que el ticket generado contenga la seccion de
     * componentes de hardware con sus detalles.
     */
    @Test
    public void pruebaTicketContieneComponentes() {
        ComputadoraBasica compu = crearComputadoraPrueba();
        ResultadoCompatibilidad compatibilidad = crearCompatibilidadLimpia();

        Ticket ticket = new Ticket(1, compu, "Juan Perez", compatibilidad);
        String ticketGenerado = ticket.generarTicket();

        assertTrue(ticketGenerado.contains("COMPONENTES DE HARDWARE:"),
            "El ticket debe contener el encabezado de componentes");
        assertTrue(ticketGenerado.contains("Core i5-13600K"),
            "El ticket debe contener la CPU");
        assertTrue(ticketGenerado.contains("RTX 3060"),
            "El ticket debe contener la GPU");
        assertTrue(ticketGenerado.contains("Kingston"),
            "El ticket debe contener componentes Kingston");
    }

    /**
     * Prueba que valida que el ticket muestra el software cuando esta instalado.
     *
     * <p>Verifica que cuando se agregan decoradores de software,
     * estos aparecen en la seccion de software del ticket.
     */
    @Test
    public void pruebaTicketConSoftwareInstalado() {
        ComputadoraBasica compu = crearComputadoraPrueba();
        WindowsDecorator compuConWindows = new WindowsDecorator(compu, "Windows", 350.00);
        OfficeDecorator compuConSoftware = new OfficeDecorator(compuConWindows, "Office", 500.00);

        ResultadoCompatibilidad compatibilidad = crearCompatibilidadLimpia();
        Ticket ticket = new Ticket(1, compuConSoftware, "Juan Perez", compatibilidad);
        String ticketGenerado = ticket.generarTicket();

        assertTrue(ticketGenerado.contains("SOFTWARE ADICIONAL:"),
            "El ticket debe contener el encabezado de software");
        assertTrue(ticketGenerado.contains("+ Software: Windows"),
            "El ticket debe mostrar Windows instalado");
        assertTrue(ticketGenerado.contains("+ Software: Office"),
            "El ticket debe mostrar Office instalado");
    }

    /**
     * Prueba que valida que el ticket no muestra seccion de software si no hay instalado.
     *
     * <p>Verifica que cuando no hay software instalado,
     * la seccion de software no aparece en el ticket.
     */
    @Test
    public void pruebaTicketSinSoftwareNoMuestraSeccion() {
        ComputadoraBasica compu = crearComputadoraPrueba();
        ResultadoCompatibilidad compatibilidad = crearCompatibilidadLimpia();

        Ticket ticket = new Ticket(1, compu, "Juan Perez", compatibilidad);
        String ticketGenerado = ticket.generarTicket();

        assertFalse(ticketGenerado.contains("SOFTWARE ADICIONAL:"),
            "El ticket no debe mostrar seccion de software si no hay instalado");
    }

    /**
     * Prueba que valida que el ticket contiene la informacion de compatibilidad.
     *
     * <p>Verifica que el ticket generado contenga la seccion de
     * informacion de compatibilidad con estado y advertencias.
     */
    @Test
    public void pruebaTicketContieneCompatibilidad() {
        ComputadoraBasica compu = crearComputadoraPrueba();
        ResultadoCompatibilidad compatibilidad = crearCompatibilidadLimpia();

        Ticket ticket = new Ticket(1, compu, "Juan Perez", compatibilidad);
        String ticketGenerado = ticket.generarTicket();

        assertTrue(ticketGenerado.contains("INFORMACION DE COMPATIBILIDAD:"),
            "El ticket debe contener el encabezado de compatibilidad");
        assertTrue(ticketGenerado.contains("Estado: COMPATIBLE"),
            "El ticket debe mostrar el estado de compatibilidad");
    }

    /**
     * Prueba que valida que el ticket muestra las advertencias de compatibilidad.
     *
     * <p>Verifica que cuando hay advertencias de compatibilidad,
     * estas aparecen en la seccion correspondiente del ticket.
     */
    @Test
    public void pruebaTicketConAdvertenciasDeCompatibilidad() {
        ComputadoraBasica compu = crearComputadoraPrueba();
        ResultadoCompatibilidad compatibilidad = crearCompatibilidadConAdvertencias();

        Ticket ticket = new Ticket(1, compu, "Juan Perez", compatibilidad);
        String ticketGenerado = ticket.generarTicket();

        assertTrue(ticketGenerado.contains("Advertencias:"),
            "El ticket debe contener el encabezado de advertencias");
        assertTrue(ticketGenerado.contains("fuente de alimentacion podria ser insuficiente"),
            "El ticket debe mostrar la advertencia sobre la fuente");
        assertTrue(ticketGenerado.contains("refrigeracion adicional"),
            "El ticket debe mostrar la advertencia sobre refrigeracion");
    }

    /**
     * Prueba que valida que el ticket muestra los componentes adaptados.
     *
     * <p>Verifica que cuando hay componentes que requirieron adaptacion,
     * estos aparecen en la seccion de componentes adaptados del ticket.
     */
    @Test
    public void pruebaTicketConComponentesAdaptados() {
        ComputadoraBasica compu = crearComputadoraPrueba();
        ResultadoCompatibilidad compatibilidad = crearCompatibilidadConAdaptados();

        Ticket ticket = new Ticket(1, compu, "Juan Perez", compatibilidad);
        String ticketGenerado = ticket.generarTicket();

        assertTrue(ticketGenerado.contains("Componentes Adaptados:"),
            "El ticket debe contener el encabezado de componentes adaptados");
        assertTrue(ticketGenerado.contains("Ryzen 5 7600X"),
            "El ticket debe mostrar el componente AMD adaptado");
    }

    /**
     * Prueba que valida que el ticket contiene el precio total en el pie.
     *
     * <p>Verifica que el ticket generado contenga el precio total
     * correctamente formateado en la seccion del pie.
     */
    @Test
    public void pruebaTicketContienePrecioTotal() {
        ComputadoraBasica compu = crearComputadoraPrueba();
        ResultadoCompatibilidad compatibilidad = crearCompatibilidadLimpia();

        Ticket ticket = new Ticket(1, compu, "Juan Perez", compatibilidad);
        String ticketGenerado = ticket.generarTicket();
        double precioTotal = ticket.obtenerPrecioTotal();

        assertTrue(ticketGenerado.contains("PRECIO TOTAL:"),
            "El ticket debe contener el encabezado de precio total");
        assertTrue(ticketGenerado.contains(String.format("$%.2f", precioTotal)),
            "El ticket debe mostrar el precio total formateado");
    }

    /**
     * Prueba que valida que el ticket contiene el mensaje de agradecimiento.
     *
     * <p>Verifica que el ticket generado contenga el mensaje
     * de agradecimiento al cliente en el pie.
     */
    @Test
    public void pruebaTicketContieneMensajeAgradecimiento() {
        ComputadoraBasica compu = crearComputadoraPrueba();
        ResultadoCompatibilidad compatibilidad = crearCompatibilidadLimpia();

        Ticket ticket = new Ticket(1, compu, "Juan Perez", compatibilidad);
        String ticketGenerado = ticket.generarTicket();

        assertTrue(ticketGenerado.contains("Gracias por su compra"),
            "El ticket debe contener mensaje de agradecimiento");
        assertTrue(ticketGenerado.contains("MonosChinos MX"),
            "El ticket debe mencionar MonosChinos MX en el mensaje");
    }

    /**
     * Prueba que valida la inmutabilidad de los datos del ticket.
     *
     * <p>Verifica que multiples llamadas a los getters retornen los mismos valores,
     * asegurando que el estado interno no se modifica inadvertidamente.
     */
    @Test
    public void pruebaInmutabilidadDeGetters() {
        ComputadoraBasica compu = crearComputadoraPrueba();
        ResultadoCompatibilidad compatibilidad = crearCompatibilidadLimpia();

        Ticket ticket = new Ticket(42, compu, "Juan Perez", compatibilidad);

        int numero1 = ticket.obtenerNumeroTicket();
        int numero2 = ticket.obtenerNumeroTicket();
        String cliente1 = ticket.obtenerCliente();
        String cliente2 = ticket.obtenerCliente();
        double precio1 = ticket.obtenerPrecioTotal();
        double precio2 = ticket.obtenerPrecioTotal();
        Date fecha1 = ticket.obtenerFecha();
        Date fecha2 = ticket.obtenerFecha();

        assertEquals(numero1, numero2,
            "Multiples llamadas a obtenerNumeroTicket deben retornar el mismo valor");
        assertEquals(cliente1, cliente2,
            "Multiples llamadas a obtenerCliente deben retornar el mismo valor");
        assertEquals(precio1, precio2,
            "Multiples llamadas a obtenerPrecioTotal deben retornar el mismo valor");
        assertEquals(fecha1.getTime(), fecha2.getTime(),
            "Multiples llamadas a obtenerFecha deben retornar fechas con el mismo tiempo");
    }

    /**
     * Prueba que valida la generacion completa de un ticket con todos los elementos.
     *
     * <p>Verifica que un ticket con software, advertencias y componentes adaptados
     * genere correctamente todas las secciones del ticket.
     */
    @Test
    public void pruebaTicketCompletoConTodosLosElementos() {
        ComputadoraBasica compu = crearComputadoraPrueba();
        WindowsDecorator compuConWindows = new WindowsDecorator(compu, "Windows", 350.00);
        PhotoshopDecorator compuCompleta = new PhotoshopDecorator(compuConWindows, "Photoshop", 800.00);

        List<String> advertencias = new ArrayList<>();
        advertencias.add("Advertencia de prueba");
        List<ComponentePC> adaptados = new ArrayList<>();
        adaptados.add(new CPU("Ryzen 9 7950X3D", 29999.00, "AMD", "CPU", 16, "x86-64 (AMD64)"));
        ResultadoCompatibilidad compatibilidad = new ResultadoCompatibilidad(true, advertencias, adaptados);

        Ticket ticket = new Ticket(999, compuCompleta, "Cliente Premium", compatibilidad);
        String ticketGenerado = ticket.generarTicket();

        assertTrue(ticketGenerado.contains("Ticket No: 999"), "Debe contener numero de ticket");
        assertTrue(ticketGenerado.contains("Cliente: Cliente Premium"), "Debe contener cliente");
        assertTrue(ticketGenerado.contains("COMPONENTES DE HARDWARE:"), "Debe contener seccion de hardware");
        assertTrue(ticketGenerado.contains("SOFTWARE ADICIONAL:"), "Debe contener seccion de software");
        assertTrue(ticketGenerado.contains("+ Software: Windows"), "Debe mostrar Windows");
        assertTrue(ticketGenerado.contains("+ Software: Photoshop"), "Debe mostrar Photoshop");
        assertTrue(ticketGenerado.contains("INFORMACION DE COMPATIBILIDAD:"), "Debe contener compatibilidad");
        assertTrue(ticketGenerado.contains("Advertencia de prueba"), "Debe mostrar advertencia");
        assertTrue(ticketGenerado.contains("Ryzen 9 7950X3D"), "Debe mostrar componente adaptado");
        assertTrue(ticketGenerado.contains("PRECIO TOTAL:"), "Debe contener precio total");
    }

    /**
     * Prueba que valida que getters retornan las mismas instancias de objetos.
     *
     * <p>Verifica que los getters de computadora y compatibilidad retornen
     * las mismas referencias de objetos que fueron pasados al constructor.
     */
    @Test
    public void pruebaGettersRetornanMismasReferencias() {
        ComputadoraBasica compu = crearComputadoraPrueba();
        ResultadoCompatibilidad compatibilidad = crearCompatibilidadLimpia();

        Ticket ticket = new Ticket(1, compu, "Test Cliente", compatibilidad);

        assertSame(compu, ticket.obtenerComputadora(),
            "obtenerComputadora debe retornar la misma referencia");
        assertSame(compatibilidad, ticket.obtenerCompatibilidad(),
            "obtenerCompatibilidad debe retornar la misma referencia");
    }
}
