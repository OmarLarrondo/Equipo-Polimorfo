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
 * Clase de pruebas unitarias para WindowsDecorator.
 *
 * <p>Valida el correcto funcionamiento del decorador de Windows,
 * asegurando que agregue correctamente el sistema operativo Windows
 * a las computadoras mediante el patron Decorator.
 *
 * <p>Esta suite de pruebas verifica:
 * <ul>
 *   <li>Creacion correcta del decorador con datos validos</li>
 *   <li>Manejo de excepciones con computadora null</li>
 *   <li>Formato correcto de la descripcion incluyendo Windows</li>
 *   <li>Calculo correcto del precio total</li>
 *   <li>Compatibilidad con decoradores anidados</li>
 *   <li>Busqueda case-insensitive de Windows instalado</li>
 * </ul>
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class WindowsDecoratorTest {

    /**
     * Provee argumentos para probar diferentes versiones de Windows y precios.
     * Retorna un stream de argumentos con nombres y precios de Windows.
     *
     * @return Stream de argumentos para versiones de Windows
     */
    private static Stream<Arguments> proveedorDatosWindows() {
        return Stream.of(
            Arguments.of("Windows 10/11", 2500.0),
            Arguments.of("Windows 10 Home", 2200.0),
            Arguments.of("Windows 11 Pro", 2800.0),
            Arguments.of("Windows Server", 5000.0)
        );
    }

    /**
     * Provee datos para pruebas de busqueda case-insensitive de Windows.
     * Cada argumento contiene el nombre original y variantes en diferentes casos.
     *
     * @return Stream de argumentos para pruebas case-insensitive
     */
    private static Stream<Arguments> proveedorDatosCaseInsensitive() {
        return Stream.of(
            Arguments.of("Windows 10/11", "windows 10/11"),
            Arguments.of("Windows 10/11", "WINDOWS 10/11"),
            Arguments.of("Windows 10/11", "WiNdOwS 10/11"),
            Arguments.of("Windows 11 Pro", "windows 11 pro")
        );
    }

    /**
     * Crea una computadora basica de prueba con componentes.
     * Esta funcion es pura y retorna una nueva computadora cada vez.
     *
     * @return Una nueva ComputadoraBasica con componentes de prueba
     */
    private static ComputadoraBasica crearComputadoraBasicaDePrueba() {
        ComputadoraBasica computadora = new ComputadoraBasica("PC de Prueba");
        computadora.agregarComponente(new CPU("Core i5-13600K", 18999.00, "Intel", "CPU", 14, "x86-64"));
        computadora.agregarComponente(new RAM("16 GB", 1200.0, "Kingston", "RAM", 16, "DDR4"));
        return computadora;
    }

    /**
     * Valida que el precio total sea la suma del precio base y el precio de Windows.
     * Esta funcion es pura y no tiene efectos secundarios.
     *
     * @param precioTotal el precio total calculado
     * @param precioBase el precio base de la computadora
     * @param precioWindows el precio de Windows
     * @return true si la suma es correcta, false en caso contrario
     */
    private static boolean validarPrecioTotal(double precioTotal, double precioBase, double precioWindows) {
        return Math.abs(precioTotal - (precioBase + precioWindows)) < 0.01;
    }

    /**
     * Prueba parametrizada que valida la creacion correcta del decorador Windows.
     *
     * <p>Verifica que el constructor de WindowsDecorator cree instancias correctas
     * con diferentes versiones de Windows y precios.
     *
     * @param nombreWindows el nombre de la version de Windows
     * @param precioWindows el precio de Windows
     */
    @ParameterizedTest(name = "WindowsDecorator con {0} (${1})")
    @MethodSource("proveedorDatosWindows")
    public void pruebaCrearWindowsDecoratorConDatosValidos(String nombreWindows, double precioWindows) {
        ComputadoraBasica computadoraBase = crearComputadoraBasicaDePrueba();
        WindowsDecorator decorador = new WindowsDecorator(computadoraBase, nombreWindows, precioWindows);

        assertNotNull(decorador, "El decorador no debe ser null");
        assertTrue(decorador.tieneSoftware(nombreWindows),
            String.format("Debe tener %s instalado", nombreWindows));
    }

    /**
     * Prueba que el constructor lance excepcion cuando la computadora es null.
     *
     * <p>Verifica que WindowsDecorator valide correctamente que la computadora
     * base no sea null, lanzando IllegalArgumentException en caso contrario.
     */
    @Test
    public void pruebaConstructorConComputadoraNullLanzaExcepcion() {
        IllegalArgumentException excepcion = assertThrows(
            IllegalArgumentException.class,
            () -> new WindowsDecorator(null, "Windows 10/11", 2500.0),
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
     * <p>Verifica que la descripcion incluya el nombre de Windows con el
     * formato correcto: "\n  + Software: [nombre]".
     *
     * @param nombreWindows el nombre de la version de Windows
     * @param precioWindows el precio de Windows
     */
    @ParameterizedTest(name = "Descripcion con {0}")
    @MethodSource("proveedorDatosWindows")
    public void pruebaObtenerDescripcionIncluyeWindows(String nombreWindows, double precioWindows) {
        ComputadoraBasica computadoraBase = crearComputadoraBasicaDePrueba();
        WindowsDecorator decorador = new WindowsDecorator(computadoraBase, nombreWindows, precioWindows);

        String descripcion = decorador.obtenerDescripcion();

        assertNotNull(descripcion, "La descripcion no debe ser null");
        assertTrue(descripcion.contains("+ Software: " + nombreWindows),
            String.format("La descripcion debe incluir '+ Software: %s'", nombreWindows));
        assertTrue(descripcion.contains("PC de Prueba"),
            "La descripcion debe incluir el nombre de la computadora base");
    }

    /**
     * Prueba parametrizada que valida el calculo correcto del precio total.
     *
     * <p>Verifica que obtenerPrecioTotal() retorne la suma del precio base
     * de la computadora mas el precio de Windows.
     *
     * @param nombreWindows el nombre de la version de Windows
     * @param precioWindows el precio de Windows
     */
    @ParameterizedTest(name = "Precio total con {0} (${1})")
    @MethodSource("proveedorDatosWindows")
    public void pruebaObtenerPrecioTotalSumaPrecios(String nombreWindows, double precioWindows) {
        ComputadoraBasica computadoraBase = crearComputadoraBasicaDePrueba();
        double precioBase = computadoraBase.obtenerPrecioTotal();

        WindowsDecorator decorador = new WindowsDecorator(computadoraBase, nombreWindows, precioWindows);
        double precioTotal = decorador.obtenerPrecioTotal();

        assertTrue(
            validarPrecioTotal(precioTotal, precioBase, precioWindows),
            String.format("El precio total debe ser %.2f (base) + %.2f (Windows) = %.2f",
                precioBase, precioWindows, precioBase + precioWindows)
        );
    }

    /**
     * Prueba parametrizada que valida la busqueda case-insensitive de Windows.
     *
     * <p>Verifica que tieneSoftware() encuentre Windows instalado
     * independientemente de si se busca en mayusculas, minusculas o mixto.
     *
     * @param nombreOriginal el nombre original de Windows instalado
     * @param nombreBusqueda el nombre a buscar en diferente case
     */
    @ParameterizedTest(name = "Buscar '{0}' como '{1}'")
    @MethodSource("proveedorDatosCaseInsensitive")
    public void pruebaTieneSoftwareCaseInsensitive(String nombreOriginal, String nombreBusqueda) {
        ComputadoraBasica computadoraBase = crearComputadoraBasicaDePrueba();
        WindowsDecorator decorador = new WindowsDecorator(computadoraBase, nombreOriginal, 2500.0);

        assertTrue(decorador.tieneSoftware(nombreBusqueda),
            String.format("Debe encontrar '%s' al buscar '%s'", nombreOriginal, nombreBusqueda));
    }

    /**
     * Prueba que tieneSoftware retorne false cuando Windows no esta instalado.
     *
     * <p>Verifica que tieneSoftware() retorne false cuando se busca Windows
     * en una computadora que no tiene el decorador de Windows.
     */
    @Test
    public void pruebaTieneSoftwareRetornaFalseCuandoNoEstaInstalado() {
        ComputadoraBasica computadoraBase = crearComputadoraBasicaDePrueba();

        assertFalse(computadoraBase.tieneSoftware("Windows 10/11"),
            "La computadora sin decorador no debe tener Windows");
    }

    /**
     * Prueba que decoradores anidados acumulen correctamente los precios.
     *
     * <p>Verifica que al anidar WindowsDecorator con otros decoradores,
     * el precio total sea la suma del precio base mas todos los software.
     */
    @Test
    public void pruebaDecoradoresAnidadosAcumulanPrecios() {
        ComputadoraBasica computadoraBase = crearComputadoraBasicaDePrueba();
        double precioBase = computadoraBase.obtenerPrecioTotal();

        WindowsDecorator decorador1 = new WindowsDecorator(computadoraBase, "Windows 10/11", 2500.0);
        OfficeDecorator decorador2 = new OfficeDecorator(decorador1, "Microsoft Office 365", 1800.0);

        double precioEsperado = precioBase + 2500.0 + 1800.0;
        double precioTotal = decorador2.obtenerPrecioTotal();

        assertTrue(
            Math.abs(precioTotal - precioEsperado) < 0.01,
            String.format("El precio total debe ser %.2f, pero fue %.2f", precioEsperado, precioTotal)
        );
    }

    /**
     * Prueba que decoradores anidados incluyan todo el software en la descripcion.
     *
     * <p>Verifica que al anidar WindowsDecorator con otros decoradores,
     * la descripcion final incluya todos los software agregados.
     */
    @Test
    public void pruebaDecoradoresAnidadosIncluyenTodoElSoftware() {
        ComputadoraBasica computadoraBase = crearComputadoraBasicaDePrueba();

        WindowsDecorator decorador1 = new WindowsDecorator(computadoraBase, "Windows 10/11", 2500.0);
        OfficeDecorator decorador2 = new OfficeDecorator(decorador1, "Microsoft Office 365", 1800.0);

        String descripcion = decorador2.obtenerDescripcion();

        assertTrue(descripcion.contains("Windows 10/11"), "Debe incluir Windows");
        assertTrue(descripcion.contains("Microsoft Office 365"), "Debe incluir Office");
    }

    /**
     * Prueba que tieneSoftware busque recursivamente en decoradores anidados.
     *
     * <p>Verifica que cuando WindowsDecorator esta anidado con otros decoradores,
     * tieneSoftware() pueda encontrar Windows en cualquier nivel de la cadena.
     */
    @Test
    public void pruebaTieneSoftwareBuscaRecursivamente() {
        ComputadoraBasica computadoraBase = crearComputadoraBasicaDePrueba();

        WindowsDecorator decorador1 = new WindowsDecorator(computadoraBase, "Windows 10/11", 2500.0);
        OfficeDecorator decorador2 = new OfficeDecorator(decorador1, "Microsoft Office 365", 1800.0);
        PhotoshopDecorator decorador3 = new PhotoshopDecorator(decorador2, "Adobe Photoshop", 3200.0);

        assertTrue(decorador3.tieneSoftware("Windows 10/11"),
            "Debe encontrar Windows en el primer nivel");
        assertTrue(decorador3.tieneSoftware("Microsoft Office 365"),
            "Debe encontrar Office en el segundo nivel");
        assertTrue(decorador3.tieneSoftware("Adobe Photoshop"),
            "Debe encontrar Photoshop en el tercer nivel");
    }

    /**
     * Prueba que obtenerComponentes delegue correctamente a la computadora base.
     *
     * <p>Verifica que WindowsDecorator retorne la misma lista de componentes
     * que la computadora base, sin modificarla.
     */
    @Test
    public void pruebaObtenerComponentesDelegaAComputadoraBase() {
        ComputadoraBasica computadoraBase = crearComputadoraBasicaDePrueba();
        int componentesBase = computadoraBase.obtenerComponentes().size();

        WindowsDecorator decorador = new WindowsDecorator(computadoraBase, "Windows 10/11", 2500.0);
        int componentesDecorador = decorador.obtenerComponentes().size();

        assertEquals(componentesBase, componentesDecorador,
            "La cantidad de componentes debe ser la misma");
    }

    /**
     * Prueba que agregarComponente delegue correctamente a la computadora base.
     *
     * <p>Verifica que al agregar un componente a traves de WindowsDecorator,
     * este se agregue a la computadora base subyacente.
     */
    @Test
    public void pruebaAgregarComponenteDelegaAComputadoraBase() {
        ComputadoraBasica computadoraBase = crearComputadoraBasicaDePrueba();
        int componentesIniciales = computadoraBase.obtenerComponentes().size();

        WindowsDecorator decorador = new WindowsDecorator(computadoraBase, "Windows 10/11", 2500.0);
        CPU nuevoCPU = new CPU("Core i7-13700K", 24999.00, "Intel", "CPU", 16, "x86-64");
        decorador.agregarComponente(nuevoCPU);

        assertEquals(componentesIniciales + 1, computadoraBase.obtenerComponentes().size(),
            "El componente debe agregarse a la computadora base");
    }

    /**
     * Prueba que multiples instancias de WindowsDecorator sean independientes.
     *
     * <p>Verifica que crear multiples instancias de WindowsDecorator con
     * diferentes computadoras no cause interferencia entre ellas.
     */
    @Test
    public void pruebaMultiplesInstanciasIndependientes() {
        ComputadoraBasica computadora1 = new ComputadoraBasica("PC 1");
        ComputadoraBasica computadora2 = new ComputadoraBasica("PC 2");

        WindowsDecorator decorador1 = new WindowsDecorator(computadora1, "Windows 10", 2200.0);
        WindowsDecorator decorador2 = new WindowsDecorator(computadora2, "Windows 11", 2800.0);

        assertTrue(decorador1.tieneSoftware("Windows 10"),
            "Decorador 1 debe tener Windows 10");
        assertFalse(decorador1.tieneSoftware("Windows 11"),
            "Decorador 1 no debe tener Windows 11");

        assertTrue(decorador2.tieneSoftware("Windows 11"),
            "Decorador 2 debe tener Windows 11");
        assertFalse(decorador2.tieneSoftware("Windows 10"),
            "Decorador 2 no debe tener Windows 10");
    }
}
