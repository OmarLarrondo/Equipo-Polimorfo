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
 * Clase de pruebas unitarias para AutoCADDecorator.
 *
 * <p>Valida el correcto funcionamiento del decorador de AutoCAD,
 * asegurando que agregue correctamente el software de diseno asistido
 * por computadora a las computadoras mediante el patron Decorator.
 *
 * <p>Esta suite de pruebas verifica:
 * <ul>
 *   <li>Creacion correcta del decorador con datos validos</li>
 *   <li>Manejo de excepciones con computadora null</li>
 *   <li>Formato correcto de la descripcion incluyendo AutoCAD</li>
 *   <li>Calculo correcto del precio total</li>
 *   <li>Compatibilidad con decoradores anidados</li>
 *   <li>Busqueda case-insensitive de AutoCAD instalado</li>
 * </ul>
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class AutoCADDecoratorTest {

    /**
     * Provee argumentos para probar diferentes versiones de AutoCAD y precios.
     * Retorna un stream de argumentos con nombres y precios de AutoCAD.
     *
     * @return Stream de argumentos para versiones de AutoCAD
     */
    private static Stream<Arguments> proveedorDatosAutoCAD() {
        return Stream.of(
            Arguments.of("AutoCAD", 8500.0),
            Arguments.of("AutoCAD 2024", 9000.0),
            Arguments.of("AutoCAD LT", 5500.0),
            Arguments.of("AutoCAD Architecture", 10000.0)
        );
    }

    /**
     * Provee datos para pruebas de busqueda case-insensitive de AutoCAD.
     * Cada argumento contiene el nombre original y variantes en diferentes casos.
     *
     * @return Stream de argumentos para pruebas case-insensitive
     */
    private static Stream<Arguments> proveedorDatosCaseInsensitive() {
        return Stream.of(
            Arguments.of("AutoCAD", "autocad"),
            Arguments.of("AutoCAD", "AUTOCAD"),
            Arguments.of("AutoCAD", "AuToCaD"),
            Arguments.of("AutoCAD LT", "autocad lt")
        );
    }

    /**
     * Crea una computadora basica de prueba con componentes.
     * Esta funcion es pura y retorna una nueva computadora cada vez.
     *
     * @return Una nueva ComputadoraBasica con componentes de prueba
     */
    private static ComputadoraBasica crearComputadoraBasicaDePrueba() {
        ComputadoraBasica computadora = new ComputadoraBasica("Workstation de Prueba");
        computadora.agregarComponente(new CPU("Core i9-13900K", 34999.00, "Intel", "CPU", 24, "x86-64"));
        computadora.agregarComponente(new RAM("32 GB", 2400.0, "Kingston", "RAM", 32, "DDR5"));
        return computadora;
    }

    /**
     * Valida que el precio total sea la suma del precio base y el precio de AutoCAD.
     * Esta funcion es pura y no tiene efectos secundarios.
     *
     * @param precioTotal el precio total calculado
     * @param precioBase el precio base de la computadora
     * @param precioAutoCAD el precio de AutoCAD
     * @return true si la suma es correcta, false en caso contrario
     */
    private static boolean validarPrecioTotal(double precioTotal, double precioBase, double precioAutoCAD) {
        return Math.abs(precioTotal - (precioBase + precioAutoCAD)) < 0.01;
    }

    /**
     * Prueba parametrizada que valida la creacion correcta del decorador AutoCAD.
     *
     * <p>Verifica que el constructor de AutoCADDecorator cree instancias correctas
     * con diferentes versiones de AutoCAD y precios.
     *
     * @param nombreAutoCAD el nombre de la version de AutoCAD
     * @param precioAutoCAD el precio de AutoCAD
     */
    @ParameterizedTest(name = "AutoCADDecorator con {0} (${1})")
    @MethodSource("proveedorDatosAutoCAD")
    public void pruebaCrearAutoCADDecoratorConDatosValidos(String nombreAutoCAD, double precioAutoCAD) {
        ComputadoraBasica computadoraBase = crearComputadoraBasicaDePrueba();
        AutoCADDecorator decorador = new AutoCADDecorator(computadoraBase, nombreAutoCAD, precioAutoCAD);

        assertNotNull(decorador, "El decorador no debe ser null");
        assertTrue(decorador.tieneSoftware(nombreAutoCAD),
            String.format("Debe tener %s instalado", nombreAutoCAD));
    }

    /**
     * Prueba que el constructor lance excepcion cuando la computadora es null.
     *
     * <p>Verifica que AutoCADDecorator valide correctamente que la computadora
     * base no sea null, lanzando IllegalArgumentException en caso contrario.
     */
    @Test
    public void pruebaConstructorConComputadoraNullLanzaExcepcion() {
        IllegalArgumentException excepcion = assertThrows(
            IllegalArgumentException.class,
            () -> new AutoCADDecorator(null, "AutoCAD", 8500.0),
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
     * <p>Verifica que la descripcion incluya el nombre de AutoCAD con el
     * formato correcto: "\n  + Software: [nombre]".
     *
     * @param nombreAutoCAD el nombre de la version de AutoCAD
     * @param precioAutoCAD el precio de AutoCAD
     */
    @ParameterizedTest(name = "Descripcion con {0}")
    @MethodSource("proveedorDatosAutoCAD")
    public void pruebaObtenerDescripcionIncluyeAutoCAD(String nombreAutoCAD, double precioAutoCAD) {
        ComputadoraBasica computadoraBase = crearComputadoraBasicaDePrueba();
        AutoCADDecorator decorador = new AutoCADDecorator(computadoraBase, nombreAutoCAD, precioAutoCAD);

        String descripcion = decorador.obtenerDescripcion();

        assertNotNull(descripcion, "La descripcion no debe ser null");
        assertTrue(descripcion.contains("+ Software: " + nombreAutoCAD),
            String.format("La descripcion debe incluir '+ Software: %s'", nombreAutoCAD));
        assertTrue(descripcion.contains("Workstation de Prueba"),
            "La descripcion debe incluir el nombre de la computadora base");
    }

    /**
     * Prueba parametrizada que valida el calculo correcto del precio total.
     *
     * <p>Verifica que obtenerPrecioTotal() retorne la suma del precio base
     * de la computadora mas el precio de AutoCAD.
     *
     * @param nombreAutoCAD el nombre de la version de AutoCAD
     * @param precioAutoCAD el precio de AutoCAD
     */
    @ParameterizedTest(name = "Precio total con {0} (${1})")
    @MethodSource("proveedorDatosAutoCAD")
    public void pruebaObtenerPrecioTotalSumaPrecios(String nombreAutoCAD, double precioAutoCAD) {
        ComputadoraBasica computadoraBase = crearComputadoraBasicaDePrueba();
        double precioBase = computadoraBase.obtenerPrecioTotal();

        AutoCADDecorator decorador = new AutoCADDecorator(computadoraBase, nombreAutoCAD, precioAutoCAD);
        double precioTotal = decorador.obtenerPrecioTotal();

        assertTrue(
            validarPrecioTotal(precioTotal, precioBase, precioAutoCAD),
            String.format("El precio total debe ser %.2f (base) + %.2f (AutoCAD) = %.2f",
                precioBase, precioAutoCAD, precioBase + precioAutoCAD)
        );
    }

    /**
     * Prueba parametrizada que valida la busqueda case-insensitive de AutoCAD.
     *
     * <p>Verifica que tieneSoftware() encuentre AutoCAD instalado
     * independientemente de si se busca en mayusculas, minusculas o mixto.
     *
     * @param nombreOriginal el nombre original de AutoCAD instalado
     * @param nombreBusqueda el nombre a buscar en diferente case
     */
    @ParameterizedTest(name = "Buscar '{0}' como '{1}'")
    @MethodSource("proveedorDatosCaseInsensitive")
    public void pruebaTieneSoftwareCaseInsensitive(String nombreOriginal, String nombreBusqueda) {
        ComputadoraBasica computadoraBase = crearComputadoraBasicaDePrueba();
        AutoCADDecorator decorador = new AutoCADDecorator(computadoraBase, nombreOriginal, 8500.0);

        assertTrue(decorador.tieneSoftware(nombreBusqueda),
            String.format("Debe encontrar '%s' al buscar '%s'", nombreOriginal, nombreBusqueda));
    }

    /**
     * Prueba que tieneSoftware retorne false cuando AutoCAD no esta instalado.
     *
     * <p>Verifica que tieneSoftware() retorne false cuando se busca AutoCAD
     * en una computadora que no tiene el decorador de AutoCAD.
     */
    @Test
    public void pruebaTieneSoftwareRetornaFalseCuandoNoEstaInstalado() {
        ComputadoraBasica computadoraBase = crearComputadoraBasicaDePrueba();

        assertFalse(computadoraBase.tieneSoftware("AutoCAD"),
            "La computadora sin decorador no debe tener AutoCAD");
    }

    /**
     * Prueba que decoradores anidados acumulen correctamente los precios.
     *
     * <p>Verifica que al anidar AutoCADDecorator con otros decoradores,
     * el precio total sea la suma del precio base mas todos los software.
     */
    @Test
    public void pruebaDecoradoresAnidadosAcumulanPrecios() {
        ComputadoraBasica computadoraBase = crearComputadoraBasicaDePrueba();
        double precioBase = computadoraBase.obtenerPrecioTotal();

        WindowsDecorator decorador1 = new WindowsDecorator(computadoraBase, "Windows 10/11", 2500.0);
        PhotoshopDecorator decorador2 = new PhotoshopDecorator(decorador1, "Adobe Photoshop", 3200.0);
        AutoCADDecorator decorador3 = new AutoCADDecorator(decorador2, "AutoCAD", 8500.0);

        double precioEsperado = precioBase + 2500.0 + 3200.0 + 8500.0;
        double precioTotal = decorador3.obtenerPrecioTotal();

        assertTrue(
            Math.abs(precioTotal - precioEsperado) < 0.01,
            String.format("El precio total debe ser %.2f, pero fue %.2f", precioEsperado, precioTotal)
        );
    }

    /**
     * Prueba que decoradores anidados incluyan todo el software en la descripcion.
     *
     * <p>Verifica que al anidar AutoCADDecorator con otros decoradores,
     * la descripcion final incluya todos los software agregados.
     */
    @Test
    public void pruebaDecoradoresAnidadosIncluyenTodoElSoftware() {
        ComputadoraBasica computadoraBase = crearComputadoraBasicaDePrueba();

        WindowsDecorator decorador1 = new WindowsDecorator(computadoraBase, "Windows 10/11", 2500.0);
        PhotoshopDecorator decorador2 = new PhotoshopDecorator(decorador1, "Adobe Photoshop", 3200.0);
        AutoCADDecorator decorador3 = new AutoCADDecorator(decorador2, "AutoCAD", 8500.0);

        String descripcion = decorador3.obtenerDescripcion();

        assertTrue(descripcion.contains("Windows 10/11"), "Debe incluir Windows");
        assertTrue(descripcion.contains("Adobe Photoshop"), "Debe incluir Photoshop");
        assertTrue(descripcion.contains("AutoCAD"), "Debe incluir AutoCAD");
    }

    /**
     * Prueba que tieneSoftware busque recursivamente en decoradores anidados.
     *
     * <p>Verifica que cuando AutoCADDecorator esta anidado con otros decoradores,
     * tieneSoftware() pueda encontrar AutoCAD en cualquier nivel de la cadena.
     */
    @Test
    public void pruebaTieneSoftwareBuscaRecursivamente() {
        ComputadoraBasica computadoraBase = crearComputadoraBasicaDePrueba();

        OfficeDecorator decorador1 = new OfficeDecorator(computadoraBase, "Microsoft Office 365", 1800.0);
        AutoCADDecorator decorador2 = new AutoCADDecorator(decorador1, "AutoCAD", 8500.0);
        WSLDecorator decorador3 = new WSLDecorator(decorador2, "WSL Terminal", 0.0);

        assertTrue(decorador3.tieneSoftware("Microsoft Office 365"),
            "Debe encontrar Office en el primer nivel");
        assertTrue(decorador3.tieneSoftware("AutoCAD"),
            "Debe encontrar AutoCAD en el segundo nivel");
        assertTrue(decorador3.tieneSoftware("WSL Terminal"),
            "Debe encontrar WSL en el tercer nivel");
    }

    /**
     * Prueba que obtenerComponentes delegue correctamente a la computadora base.
     *
     * <p>Verifica que AutoCADDecorator retorne la misma lista de componentes
     * que la computadora base, sin modificarla.
     */
    @Test
    public void pruebaObtenerComponentesDelegaAComputadoraBase() {
        ComputadoraBasica computadoraBase = crearComputadoraBasicaDePrueba();
        int componentesBase = computadoraBase.obtenerComponentes().size();

        AutoCADDecorator decorador = new AutoCADDecorator(computadoraBase, "AutoCAD", 8500.0);
        int componentesDecorador = decorador.obtenerComponentes().size();

        assertEquals(componentesBase, componentesDecorador,
            "La cantidad de componentes debe ser la misma");
    }

    /**
     * Prueba que agregarComponente delegue correctamente a la computadora base.
     *
     * <p>Verifica que al agregar un componente a traves de AutoCADDecorator,
     * este se agregue a la computadora base subyacente.
     */
    @Test
    public void pruebaAgregarComponenteDelegaAComputadoraBase() {
        ComputadoraBasica computadoraBase = crearComputadoraBasicaDePrueba();
        int componentesIniciales = computadoraBase.obtenerComponentes().size();

        AutoCADDecorator decorador = new AutoCADDecorator(computadoraBase, "AutoCAD", 8500.0);
        RAM nuevaRAM = new RAM("64 GB", 4800.0, "Kingston", "RAM", 64, "DDR5");
        decorador.agregarComponente(nuevaRAM);

        assertEquals(componentesIniciales + 1, computadoraBase.obtenerComponentes().size(),
            "El componente debe agregarse a la computadora base");
    }

    /**
     * Prueba que multiples instancias de AutoCADDecorator sean independientes.
     *
     * <p>Verifica que crear multiples instancias de AutoCADDecorator con
     * diferentes computadoras no cause interferencia entre ellas.
     */
    @Test
    public void pruebaMultiplesInstanciasIndependientes() {
        ComputadoraBasica computadora1 = new ComputadoraBasica("Workstation 1");
        ComputadoraBasica computadora2 = new ComputadoraBasica("Workstation 2");

        AutoCADDecorator decorador1 = new AutoCADDecorator(computadora1, "AutoCAD LT", 5500.0);
        AutoCADDecorator decorador2 = new AutoCADDecorator(computadora2, "AutoCAD Architecture", 10000.0);

        assertTrue(decorador1.tieneSoftware("AutoCAD LT"),
            "Decorador 1 debe tener AutoCAD LT");
        assertFalse(decorador1.tieneSoftware("AutoCAD Architecture"),
            "Decorador 1 no debe tener AutoCAD Architecture");

        assertTrue(decorador2.tieneSoftware("AutoCAD Architecture"),
            "Decorador 2 debe tener AutoCAD Architecture");
        assertFalse(decorador2.tieneSoftware("AutoCAD LT"),
            "Decorador 2 no debe tener AutoCAD LT");
    }
}
