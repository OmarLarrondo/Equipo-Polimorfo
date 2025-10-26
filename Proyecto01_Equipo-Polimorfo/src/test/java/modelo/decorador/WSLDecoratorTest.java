package modelo.decorador;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import modelo.componente.CPU;
import modelo.componente.RAM;
import modelo.computadora.ComputadoraBase;
import modelo.computadora.ComputadoraBasica;

/**
 * Clase de pruebas unitarias para WSLDecorator.
 *
 * <p>Valida el correcto funcionamiento del decorador de WSL (Windows Subsystem for Linux),
 * asegurando que agregue correctamente el subsistema Linux y terminal
 * a las computadoras mediante el patron Decorator.
 *
 * <p>Esta suite de pruebas verifica:
 * <ul>
 *   <li>Creacion correcta del decorador con datos validos</li>
 *   <li>Manejo de excepciones con computadora null</li>
 *   <li>Formato correcto de la descripcion incluyendo WSL</li>
 *   <li>Calculo correcto del precio total (WSL puede ser gratis)</li>
 *   <li>Compatibilidad con decoradores anidados</li>
 *   <li>Busqueda case-insensitive de WSL instalado</li>
 * </ul>
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class WSLDecoratorTest {

    /**
     * Provee argumentos para probar diferentes versiones de WSL y precios.
     * Retorna un stream de argumentos con nombres y precios de WSL.
     *
     * @return Stream de argumentos para versiones de WSL
     */
    private static Stream<Arguments> proveedorDatosWSL() {
        return Stream.of(
            Arguments.of("WSL Terminal", 0.0),
            Arguments.of("WSL 2 Ubuntu", 0.0),
            Arguments.of("WSL Debian", 0.0),
            Arguments.of("Terminal Hacker con WSL", 0.0)
        );
    }

    /**
     * Provee datos para pruebas de busqueda case-insensitive de WSL.
     * Cada argumento contiene el nombre original y variantes en diferentes casos.
     *
     * @return Stream de argumentos para pruebas case-insensitive
     */
    private static Stream<Arguments> proveedorDatosCaseInsensitive() {
        return Stream.of(
            Arguments.of("WSL Terminal", "wsl terminal"),
            Arguments.of("WSL Terminal", "WSL TERMINAL"),
            Arguments.of("WSL Terminal", "WsL tErMiNaL"),
            Arguments.of("WSL 2 Ubuntu", "wsl 2 ubuntu")
        );
    }

    /**
     * Crea una computadora basica de prueba con componentes.
     * Esta funcion es pura y retorna una nueva computadora cada vez.
     *
     * @return Una nueva ComputadoraBasica con componentes de prueba
     */
    private static ComputadoraBasica crearComputadoraBasicaDePrueba() {
        ComputadoraBasica computadora = new ComputadoraBasica("PC Developer");
        computadora.agregarComponente(new CPU("Ryzen 7 7700X", 17999.00, "AMD", "CPU", 8, "x86-64 (AMD64)"));
        computadora.agregarComponente(new RAM("16 GB", 1200.0, "Kingston", "RAM", 16, "DDR4"));
        return computadora;
    }

    /**
     * Valida que el precio total sea la suma del precio base y el precio de WSL.
     * Esta funcion es pura y no tiene efectos secundarios.
     *
     * @param precioTotal el precio total calculado
     * @param precioBase el precio base de la computadora
     * @param precioWSL el precio de WSL (generalmente 0.0)
     * @return true si la suma es correcta, false en caso contrario
     */
    private static boolean validarPrecioTotal(double precioTotal, double precioBase, double precioWSL) {
        return Math.abs(precioTotal - (precioBase + precioWSL)) < 0.01;
    }

    /**
     * Prueba parametrizada que valida la creacion correcta del decorador WSL.
     *
     * <p>Verifica que el constructor de WSLDecorator cree instancias correctas
     * con diferentes versiones de WSL y precios.
     *
     * @param nombreWSL el nombre de la version de WSL
     * @param precioWSL el precio de WSL (generalmente 0.0 porque es gratis)
     */
    @ParameterizedTest(name = "WSLDecorator con {0} (${1})")
    @MethodSource("proveedorDatosWSL")
    public void pruebaCrearWSLDecoratorConDatosValidos(String nombreWSL, double precioWSL) {
        ComputadoraBasica computadoraBase = crearComputadoraBasicaDePrueba();
        WSLDecorator decorador = new WSLDecorator(computadoraBase, nombreWSL, precioWSL);

        assertNotNull(decorador, "El decorador no debe ser null");
        assertTrue(decorador.tieneSoftware(nombreWSL),
            String.format("Debe tener %s instalado", nombreWSL));
    }

    /**
     * Prueba que el constructor lance excepcion cuando la computadora es null.
     *
     * <p>Verifica que WSLDecorator valide correctamente que la computadora
     * base no sea null, lanzando IllegalArgumentException en caso contrario.
     */
    @Test
    public void pruebaConstructorConComputadoraNullLanzaExcepcion() {
        IllegalArgumentException excepcion = assertThrows(
            IllegalArgumentException.class,
            () -> new WSLDecorator(null, "WSL Terminal", 0.0),
            "Deberia lanzar IllegalArgumentException cuando computadora es null"
        );

        assertNotNull(excepcion.getMessage(),
            "El mensaje de error no debe ser null");
        assertTrue(excepcion.getMessage().contains("nula"),
            "El mensaje debe indicar que la computadora no puede ser nula");
    }

    /**
     * Prueba parametrizada que valida el formato de obtenerDescripcion.
     *
     * <p>Verifica que la descripcion incluya el nombre de WSL con el
     * formato correcto: "\n  + Software: [nombre]".
     *
     * @param nombreWSL el nombre de la version de WSL
     * @param precioWSL el precio de WSL
     */
    @ParameterizedTest(name = "Descripcion con {0}")
    @MethodSource("proveedorDatosWSL")
    public void pruebaObtenerDescripcionIncluyeWSL(String nombreWSL, double precioWSL) {
        ComputadoraBasica computadoraBase = crearComputadoraBasicaDePrueba();
        WSLDecorator decorador = new WSLDecorator(computadoraBase, nombreWSL, precioWSL);

        String descripcion = decorador.obtenerDescripcion();

        assertNotNull(descripcion, "La descripcion no debe ser null");
        assertTrue(descripcion.contains("+ Software: " + nombreWSL),
            String.format("La descripcion debe incluir '+ Software: %s'", nombreWSL));
        assertTrue(descripcion.contains("PC Developer"),
            "La descripcion debe incluir el nombre de la computadora base");
    }

    /**
     * Prueba parametrizada que valida el calculo correcto del precio total.
     *
     * <p>Verifica que obtenerPrecioTotal() retorne la suma del precio base
     * de la computadora mas el precio de WSL (generalmente 0.0).
     *
     * @param nombreWSL el nombre de la version de WSL
     * @param precioWSL el precio de WSL
     */
    @ParameterizedTest(name = "Precio total con {0} (${1})")
    @MethodSource("proveedorDatosWSL")
    public void pruebaObtenerPrecioTotalSumaPrecios(String nombreWSL, double precioWSL) {
        ComputadoraBasica computadoraBase = crearComputadoraBasicaDePrueba();
        double precioBase = computadoraBase.obtenerPrecioTotal();

        WSLDecorator decorador = new WSLDecorator(computadoraBase, nombreWSL, precioWSL);
        double precioTotal = decorador.obtenerPrecioTotal();

        assertTrue(
            validarPrecioTotal(precioTotal, precioBase, precioWSL),
            String.format("El precio total debe ser %.2f (base) + %.2f (WSL) = %.2f",
                precioBase, precioWSL, precioBase + precioWSL)
        );
    }

    /**
     * Prueba que WSL gratuito no incremente el precio total.
     *
     * <p>Verifica que cuando WSL tiene precio 0.0, el precio total
     * sea igual al precio base de la computadora.
     */
    @Test
    public void pruebaWSLGratuitoNoIncrementaPrecio() {
        ComputadoraBasica computadoraBase = crearComputadoraBasicaDePrueba();
        double precioBase = computadoraBase.obtenerPrecioTotal();

        WSLDecorator decorador = new WSLDecorator(computadoraBase, "WSL Terminal", 0.0);
        double precioTotal = decorador.obtenerPrecioTotal();

        assertEquals(precioBase, precioTotal, 0.01,
            "El precio total debe ser igual al precio base cuando WSL es gratis");
    }

    /**
     * Prueba parametrizada que valida la busqueda case-insensitive de WSL.
     *
     * <p>Verifica que tieneSoftware() encuentre WSL instalado
     * independientemente de si se busca en mayusculas, minusculas o mixto.
     *
     * @param nombreOriginal el nombre original de WSL instalado
     * @param nombreBusqueda el nombre a buscar en diferente case
     */
    @ParameterizedTest(name = "Buscar '{0}' como '{1}'")
    @MethodSource("proveedorDatosCaseInsensitive")
    public void pruebaTieneSoftwareCaseInsensitive(String nombreOriginal, String nombreBusqueda) {
        ComputadoraBasica computadoraBase = crearComputadoraBasicaDePrueba();
        WSLDecorator decorador = new WSLDecorator(computadoraBase, nombreOriginal, 0.0);

        assertTrue(decorador.tieneSoftware(nombreBusqueda),
            String.format("Debe encontrar '%s' al buscar '%s'", nombreOriginal, nombreBusqueda));
    }

    /**
     * Prueba que tieneSoftware retorne false cuando WSL no esta instalado.
     *
     * <p>Verifica que tieneSoftware() retorne false cuando se busca WSL
     * en una computadora que no tiene el decorador de WSL.
     */
    @Test
    public void pruebaTieneSoftwareRetornaFalseCuandoNoEstaInstalado() {
        ComputadoraBasica computadoraBase = crearComputadoraBasicaDePrueba();

        assertFalse(computadoraBase.tieneSoftware("WSL Terminal"),
            "La computadora sin decorador no debe tener WSL");
    }

    /**
     * Prueba que decoradores anidados acumulen correctamente los precios.
     *
     * <p>Verifica que al anidar WSLDecorator con otros decoradores,
     * el precio total sea la suma del precio base mas todos los software.
     */
    @Test
    public void pruebaDecoradoresAnidadosAcumulanPrecios() {
        ComputadoraBasica computadoraBase = crearComputadoraBasicaDePrueba();
        double precioBase = computadoraBase.obtenerPrecioTotal();

        WindowsDecorator decorador1 = new WindowsDecorator(computadoraBase, "Windows 10/11", 2500.0);
        OfficeDecorator decorador2 = new OfficeDecorator(decorador1, "Microsoft Office 365", 1800.0);
        WSLDecorator decorador3 = new WSLDecorator(decorador2, "WSL Terminal", 0.0);

        double precioEsperado = precioBase + 2500.0 + 1800.0 + 0.0;
        double precioTotal = decorador3.obtenerPrecioTotal();

        assertTrue(
            Math.abs(precioTotal - precioEsperado) < 0.01,
            String.format("El precio total debe ser %.2f, pero fue %.2f", precioEsperado, precioTotal)
        );
    }

    /**
     * Prueba que decoradores anidados incluyan todo el software en la descripcion.
     *
     * <p>Verifica que al anidar WSLDecorator con otros decoradores,
     * la descripcion final incluya todos los software agregados.
     */
    @Test
    public void pruebaDecoradoresAnidadosIncluyenTodoElSoftware() {
        ComputadoraBasica computadoraBase = crearComputadoraBasicaDePrueba();

        WindowsDecorator decorador1 = new WindowsDecorator(computadoraBase, "Windows 10/11", 2500.0);
        OfficeDecorator decorador2 = new OfficeDecorator(decorador1, "Microsoft Office 365", 1800.0);
        WSLDecorator decorador3 = new WSLDecorator(decorador2, "WSL Terminal", 0.0);

        String descripcion = decorador3.obtenerDescripcion();

        assertTrue(descripcion.contains("Windows 10/11"), "Debe incluir Windows");
        assertTrue(descripcion.contains("Microsoft Office 365"), "Debe incluir Office");
        assertTrue(descripcion.contains("WSL Terminal"), "Debe incluir WSL");
    }

    /**
     * Prueba que tieneSoftware busque recursivamente en decoradores anidados.
     *
     * <p>Verifica que cuando WSLDecorator esta anidado con otros decoradores,
     * tieneSoftware() pueda encontrar WSL en cualquier nivel de la cadena.
     */
    @Test
    public void pruebaTieneSoftwareBuscaRecursivamente() {
        ComputadoraBasica computadoraBase = crearComputadoraBasicaDePrueba();

        PhotoshopDecorator decorador1 = new PhotoshopDecorator(computadoraBase, "Adobe Photoshop", 3200.0);
        AutoCADDecorator decorador2 = new AutoCADDecorator(decorador1, "AutoCAD", 8500.0);
        WSLDecorator decorador3 = new WSLDecorator(decorador2, "WSL Terminal", 0.0);

        assertTrue(decorador3.tieneSoftware("Adobe Photoshop"),
            "Debe encontrar Photoshop en el primer nivel");
        assertTrue(decorador3.tieneSoftware("AutoCAD"),
            "Debe encontrar AutoCAD en el segundo nivel");
        assertTrue(decorador3.tieneSoftware("WSL Terminal"),
            "Debe encontrar WSL en el tercer nivel");
    }

    /**
     * Prueba que obtenerComponentes delegue correctamente a la computadora base.
     *
     * <p>Verifica que WSLDecorator retorne la misma lista de componentes
     * que la computadora base, sin modificarla.
     */
    @Test
    public void pruebaObtenerComponentesDelegaAComputadoraBase() {
        ComputadoraBasica computadoraBase = crearComputadoraBasicaDePrueba();
        int componentesBase = computadoraBase.obtenerComponentes().size();

        WSLDecorator decorador = new WSLDecorator(computadoraBase, "WSL Terminal", 0.0);
        int componentesDecorador = decorador.obtenerComponentes().size();

        assertEquals(componentesBase, componentesDecorador,
            "La cantidad de componentes debe ser la misma");
    }

    /**
     * Prueba que agregarComponente delegue correctamente a la computadora base.
     *
     * <p>Verifica que al agregar un componente a traves de WSLDecorator,
     * este se agregue a la computadora base subyacente.
     */
    @Test
    public void pruebaAgregarComponenteDelegaAComputadoraBase() {
        ComputadoraBasica computadoraBase = crearComputadoraBasicaDePrueba();
        int componentesIniciales = computadoraBase.obtenerComponentes().size();

        WSLDecorator decorador = new WSLDecorator(computadoraBase, "WSL Terminal", 0.0);
        CPU nuevoCPU = new CPU("Ryzen 9 7950X3D", 29999.00, "AMD", "CPU", 16, "x86-64 (AMD64)");
        decorador.agregarComponente(nuevoCPU);

        assertEquals(componentesIniciales + 1, computadoraBase.obtenerComponentes().size(),
            "El componente debe agregarse a la computadora base");
    }

    /**
     * Prueba que multiples instancias de WSLDecorator sean independientes.
     *
     * <p>Verifica que crear multiples instancias de WSLDecorator con
     * diferentes computadoras no cause interferencia entre ellas.
     */
    @Test
    public void pruebaMultiplesInstanciasIndependientes() {
        ComputadoraBasica computadora1 = new ComputadoraBasica("PC Dev 1");
        ComputadoraBasica computadora2 = new ComputadoraBasica("PC Dev 2");

        WSLDecorator decorador1 = new WSLDecorator(computadora1, "WSL 2 Ubuntu", 0.0);
        WSLDecorator decorador2 = new WSLDecorator(computadora2, "WSL Debian", 0.0);

        assertTrue(decorador1.tieneSoftware("WSL 2 Ubuntu"),
            "Decorador 1 debe tener WSL 2 Ubuntu");
        assertFalse(decorador1.tieneSoftware("WSL Debian"),
            "Decorador 1 no debe tener WSL Debian");

        assertTrue(decorador2.tieneSoftware("WSL Debian"),
            "Decorador 2 debe tener WSL Debian");
        assertFalse(decorador2.tieneSoftware("WSL 2 Ubuntu"),
            "Decorador 2 no debe tener WSL 2 Ubuntu");
    }

    /**
     * Prueba una configuracion completa con todos los decoradores.
     *
     * <p>Verifica que se puedan anidar todos los decoradores disponibles
     * (Windows, Office, Photoshop, AutoCAD, WSL) y que funcionen correctamente.
     */
    @Test
    public void pruebaConfiguracionCompletaConTodosLosDecoradore() {
        ComputadoraBasica computadoraBase = crearComputadoraBasicaDePrueba();
        double precioBase = computadoraBase.obtenerPrecioTotal();

        WindowsDecorator windows = new WindowsDecorator(computadoraBase, "Windows 11 Pro", 2800.0);
        OfficeDecorator office = new OfficeDecorator(windows, "Microsoft Office 365", 1800.0);
        PhotoshopDecorator photoshop = new PhotoshopDecorator(office, "Adobe Photoshop", 3200.0);
        AutoCADDecorator autocad = new AutoCADDecorator(photoshop, "AutoCAD", 8500.0);
        WSLDecorator wsl = new WSLDecorator(autocad, "WSL Terminal", 0.0);

        assertTrue(wsl.tieneSoftware("Windows 11 Pro"), "Debe tener Windows");
        assertTrue(wsl.tieneSoftware("Microsoft Office 365"), "Debe tener Office");
        assertTrue(wsl.tieneSoftware("Adobe Photoshop"), "Debe tener Photoshop");
        assertTrue(wsl.tieneSoftware("AutoCAD"), "Debe tener AutoCAD");
        assertTrue(wsl.tieneSoftware("WSL Terminal"), "Debe tener WSL");

        double precioEsperado = precioBase + 2800.0 + 1800.0 + 3200.0 + 8500.0 + 0.0;
        assertEquals(precioEsperado, wsl.obtenerPrecioTotal(), 0.01,
            "El precio total debe incluir todos los software");
    }
}
