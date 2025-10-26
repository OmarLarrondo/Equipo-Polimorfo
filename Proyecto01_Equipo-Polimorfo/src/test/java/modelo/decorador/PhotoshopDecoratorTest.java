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
 * Clase de pruebas unitarias para PhotoshopDecorator.
 *
 * <p>Valida el correcto funcionamiento del decorador de Adobe Photoshop,
 * asegurando que agregue correctamente el software de edicion de imagenes
 * a las computadoras mediante el patron Decorator.
 *
 * <p>Esta suite de pruebas verifica:
 * <ul>
 *   <li>Creacion correcta del decorador con datos validos</li>
 *   <li>Manejo de excepciones con computadora null</li>
 *   <li>Formato correcto de la descripcion incluyendo Photoshop</li>
 *   <li>Calculo correcto del precio total</li>
 *   <li>Compatibilidad con decoradores anidados</li>
 *   <li>Busqueda case-insensitive de Photoshop instalado</li>
 * </ul>
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class PhotoshopDecoratorTest {

    /**
     * Provee argumentos para probar diferentes versiones de Photoshop y precios.
     * Retorna un stream de argumentos con nombres y precios de Photoshop.
     *
     * @return Stream de argumentos para versiones de Photoshop
     */
    private static Stream<Arguments> proveedorDatosPhotoshop() {
        return Stream.of(
            Arguments.of("Adobe Photoshop", 3200.0),
            Arguments.of("Adobe Photoshop CC 2024", 3500.0),
            Arguments.of("Photoshop Elements", 1800.0),
            Arguments.of("Adobe Creative Cloud", 5000.0)
        );
    }

    /**
     * Provee datos para pruebas de busqueda case-insensitive de Photoshop.
     * Cada argumento contiene el nombre original y variantes en diferentes casos.
     *
     * @return Stream de argumentos para pruebas case-insensitive
     */
    private static Stream<Arguments> proveedorDatosCaseInsensitive() {
        return Stream.of(
            Arguments.of("Adobe Photoshop", "adobe photoshop"),
            Arguments.of("Adobe Photoshop", "ADOBE PHOTOSHOP"),
            Arguments.of("Adobe Photoshop", "AdObE pHoToShOp"),
            Arguments.of("Photoshop Elements", "photoshop elements")
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
        computadora.agregarComponente(new CPU("Core i7-13700K", 24999.00, "Intel", "CPU", 16, "x86-64"));
        computadora.agregarComponente(new RAM("32 GB", 2400.0, "Kingston", "RAM", 32, "DDR4"));
        return computadora;
    }

    /**
     * Valida que el precio total sea la suma del precio base y el precio de Photoshop.
     * Esta funcion es pura y no tiene efectos secundarios.
     *
     * @param precioTotal el precio total calculado
     * @param precioBase el precio base de la computadora
     * @param precioPhotoshop el precio de Photoshop
     * @return true si la suma es correcta, false en caso contrario
     */
    private static boolean validarPrecioTotal(double precioTotal, double precioBase, double precioPhotoshop) {
        return Math.abs(precioTotal - (precioBase + precioPhotoshop)) < 0.01;
    }

    /**
     * Prueba parametrizada que valida la creacion correcta del decorador Photoshop.
     *
     * <p>Verifica que el constructor de PhotoshopDecorator cree instancias correctas
     * con diferentes versiones de Photoshop y precios.
     *
     * @param nombrePhotoshop el nombre de la version de Photoshop
     * @param precioPhotoshop el precio de Photoshop
     */
    @ParameterizedTest(name = "PhotoshopDecorator con {0} (${1})")
    @MethodSource("proveedorDatosPhotoshop")
    public void pruebaCrearPhotoshopDecoratorConDatosValidos(String nombrePhotoshop, double precioPhotoshop) {
        ComputadoraBasica computadoraBase = crearComputadoraBasicaDePrueba();
        PhotoshopDecorator decorador = new PhotoshopDecorator(computadoraBase, nombrePhotoshop, precioPhotoshop);

        assertNotNull(decorador, "El decorador no debe ser null");
        assertTrue(decorador.tieneSoftware(nombrePhotoshop),
            String.format("Debe tener %s instalado", nombrePhotoshop));
    }

    /**
     * Prueba que el constructor lance excepcion cuando la computadora es null.
     *
     * <p>Verifica que PhotoshopDecorator valide correctamente que la computadora
     * base no sea null, lanzando IllegalArgumentException en caso contrario.
     */
    @Test
    public void pruebaConstructorConComputadoraNullLanzaExcepcion() {
        IllegalArgumentException excepcion = assertThrows(
            IllegalArgumentException.class,
            () -> new PhotoshopDecorator(null, "Adobe Photoshop", 3200.0),
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
     * <p>Verifica que la descripcion incluya el nombre de Photoshop con el
     * formato correcto: "\n  + Software: [nombre]".
     *
     * @param nombrePhotoshop el nombre de la version de Photoshop
     * @param precioPhotoshop el precio de Photoshop
     */
    @ParameterizedTest(name = "Descripcion con {0}")
    @MethodSource("proveedorDatosPhotoshop")
    public void pruebaObtenerDescripcionIncluyePhotoshop(String nombrePhotoshop, double precioPhotoshop) {
        ComputadoraBasica computadoraBase = crearComputadoraBasicaDePrueba();
        PhotoshopDecorator decorador = new PhotoshopDecorator(computadoraBase, nombrePhotoshop, precioPhotoshop);

        String descripcion = decorador.obtenerDescripcion();

        assertNotNull(descripcion, "La descripcion no debe ser null");
        assertTrue(descripcion.contains("+ Software: " + nombrePhotoshop),
            String.format("La descripcion debe incluir '+ Software: %s'", nombrePhotoshop));
        assertTrue(descripcion.contains("PC de Prueba"),
            "La descripcion debe incluir el nombre de la computadora base");
    }

    /**
     * Prueba parametrizada que valida el calculo correcto del precio total.
     *
     * <p>Verifica que obtenerPrecioTotal() retorne la suma del precio base
     * de la computadora mas el precio de Photoshop.
     *
     * @param nombrePhotoshop el nombre de la version de Photoshop
     * @param precioPhotoshop el precio de Photoshop
     */
    @ParameterizedTest(name = "Precio total con {0} (${1})")
    @MethodSource("proveedorDatosPhotoshop")
    public void pruebaObtenerPrecioTotalSumaPrecios(String nombrePhotoshop, double precioPhotoshop) {
        ComputadoraBasica computadoraBase = crearComputadoraBasicaDePrueba();
        double precioBase = computadoraBase.obtenerPrecioTotal();

        PhotoshopDecorator decorador = new PhotoshopDecorator(computadoraBase, nombrePhotoshop, precioPhotoshop);
        double precioTotal = decorador.obtenerPrecioTotal();

        assertTrue(
            validarPrecioTotal(precioTotal, precioBase, precioPhotoshop),
            String.format("El precio total debe ser %.2f (base) + %.2f (Photoshop) = %.2f",
                precioBase, precioPhotoshop, precioBase + precioPhotoshop)
        );
    }

    /**
     * Prueba parametrizada que valida la busqueda case-insensitive de Photoshop.
     *
     * <p>Verifica que tieneSoftware() encuentre Photoshop instalado
     * independientemente de si se busca en mayusculas, minusculas o mixto.
     *
     * @param nombreOriginal el nombre original de Photoshop instalado
     * @param nombreBusqueda el nombre a buscar en diferente case
     */
    @ParameterizedTest(name = "Buscar '{0}' como '{1}'")
    @MethodSource("proveedorDatosCaseInsensitive")
    public void pruebaTieneSoftwareCaseInsensitive(String nombreOriginal, String nombreBusqueda) {
        ComputadoraBasica computadoraBase = crearComputadoraBasicaDePrueba();
        PhotoshopDecorator decorador = new PhotoshopDecorator(computadoraBase, nombreOriginal, 3200.0);

        assertTrue(decorador.tieneSoftware(nombreBusqueda),
            String.format("Debe encontrar '%s' al buscar '%s'", nombreOriginal, nombreBusqueda));
    }

    /**
     * Prueba que tieneSoftware retorne false cuando Photoshop no esta instalado.
     *
     * <p>Verifica que tieneSoftware() retorne false cuando se busca Photoshop
     * en una computadora que no tiene el decorador de Photoshop.
     */
    @Test
    public void pruebaTieneSoftwareRetornaFalseCuandoNoEstaInstalado() {
        ComputadoraBasica computadoraBase = crearComputadoraBasicaDePrueba();

        assertFalse(computadoraBase.tieneSoftware("Adobe Photoshop"),
            "La computadora sin decorador no debe tener Photoshop");
    }

    /**
     * Prueba que decoradores anidados acumulen correctamente los precios.
     *
     * <p>Verifica que al anidar PhotoshopDecorator con otros decoradores,
     * el precio total sea la suma del precio base mas todos los software.
     */
    @Test
    public void pruebaDecoradoresAnidadosAcumulanPrecios() {
        ComputadoraBasica computadoraBase = crearComputadoraBasicaDePrueba();
        double precioBase = computadoraBase.obtenerPrecioTotal();

        WindowsDecorator decorador1 = new WindowsDecorator(computadoraBase, "Windows 10/11", 2500.0);
        OfficeDecorator decorador2 = new OfficeDecorator(decorador1, "Microsoft Office 365", 1800.0);
        PhotoshopDecorator decorador3 = new PhotoshopDecorator(decorador2, "Adobe Photoshop", 3200.0);

        double precioEsperado = precioBase + 2500.0 + 1800.0 + 3200.0;
        double precioTotal = decorador3.obtenerPrecioTotal();

        assertTrue(
            Math.abs(precioTotal - precioEsperado) < 0.01,
            String.format("El precio total debe ser %.2f, pero fue %.2f", precioEsperado, precioTotal)
        );
    }

    /**
     * Prueba que decoradores anidados incluyan todo el software en la descripcion.
     *
     * <p>Verifica que al anidar PhotoshopDecorator con otros decoradores,
     * la descripcion final incluya todos los software agregados.
     */
    @Test
    public void pruebaDecoradoresAnidadosIncluyenTodoElSoftware() {
        ComputadoraBasica computadoraBase = crearComputadoraBasicaDePrueba();

        WindowsDecorator decorador1 = new WindowsDecorator(computadoraBase, "Windows 10/11", 2500.0);
        OfficeDecorator decorador2 = new OfficeDecorator(decorador1, "Microsoft Office 365", 1800.0);
        PhotoshopDecorator decorador3 = new PhotoshopDecorator(decorador2, "Adobe Photoshop", 3200.0);

        String descripcion = decorador3.obtenerDescripcion();

        assertTrue(descripcion.contains("Windows 10/11"), "Debe incluir Windows");
        assertTrue(descripcion.contains("Microsoft Office 365"), "Debe incluir Office");
        assertTrue(descripcion.contains("Adobe Photoshop"), "Debe incluir Photoshop");
    }

    /**
     * Prueba que tieneSoftware busque recursivamente en decoradores anidados.
     *
     * <p>Verifica que cuando PhotoshopDecorator esta anidado con otros decoradores,
     * tieneSoftware() pueda encontrar Photoshop en cualquier nivel de la cadena.
     */
    @Test
    public void pruebaTieneSoftwareBuscaRecursivamente() {
        ComputadoraBasica computadoraBase = crearComputadoraBasicaDePrueba();

        WindowsDecorator decorador1 = new WindowsDecorator(computadoraBase, "Windows 10/11", 2500.0);
        PhotoshopDecorator decorador2 = new PhotoshopDecorator(decorador1, "Adobe Photoshop", 3200.0);
        AutoCADDecorator decorador3 = new AutoCADDecorator(decorador2, "AutoCAD", 8500.0);

        assertTrue(decorador3.tieneSoftware("Windows 10/11"),
            "Debe encontrar Windows en el primer nivel");
        assertTrue(decorador3.tieneSoftware("Adobe Photoshop"),
            "Debe encontrar Photoshop en el segundo nivel");
        assertTrue(decorador3.tieneSoftware("AutoCAD"),
            "Debe encontrar AutoCAD en el tercer nivel");
    }

    /**
     * Prueba que obtenerComponentes delegue correctamente a la computadora base.
     *
     * <p>Verifica que PhotoshopDecorator retorne la misma lista de componentes
     * que la computadora base, sin modificarla.
     */
    @Test
    public void pruebaObtenerComponentesDelegaAComputadoraBase() {
        ComputadoraBasica computadoraBase = crearComputadoraBasicaDePrueba();
        int componentesBase = computadoraBase.obtenerComponentes().size();

        PhotoshopDecorator decorador = new PhotoshopDecorator(computadoraBase, "Adobe Photoshop", 3200.0);
        int componentesDecorador = decorador.obtenerComponentes().size();

        assertEquals(componentesBase, componentesDecorador,
            "La cantidad de componentes debe ser la misma");
    }

    /**
     * Prueba que agregarComponente delegue correctamente a la computadora base.
     *
     * <p>Verifica que al agregar un componente a traves de PhotoshopDecorator,
     * este se agregue a la computadora base subyacente.
     */
    @Test
    public void pruebaAgregarComponenteDelegaAComputadoraBase() {
        ComputadoraBasica computadoraBase = crearComputadoraBasicaDePrueba();
        int componentesIniciales = computadoraBase.obtenerComponentes().size();

        PhotoshopDecorator decorador = new PhotoshopDecorator(computadoraBase, "Adobe Photoshop", 3200.0);
        CPU nuevoCPU = new CPU("Core i9-13900K", 34999.00, "Intel", "CPU", 24, "x86-64");
        decorador.agregarComponente(nuevoCPU);

        assertEquals(componentesIniciales + 1, computadoraBase.obtenerComponentes().size(),
            "El componente debe agregarse a la computadora base");
    }

    /**
     * Prueba que multiples instancias de PhotoshopDecorator sean independientes.
     *
     * <p>Verifica que crear multiples instancias de PhotoshopDecorator con
     * diferentes computadoras no cause interferencia entre ellas.
     */
    @Test
    public void pruebaMultiplesInstanciasIndependientes() {
        ComputadoraBasica computadora1 = new ComputadoraBasica("PC 1");
        ComputadoraBasica computadora2 = new ComputadoraBasica("PC 2");

        PhotoshopDecorator decorador1 = new PhotoshopDecorator(computadora1, "Photoshop Elements", 1800.0);
        PhotoshopDecorator decorador2 = new PhotoshopDecorator(computadora2, "Adobe Creative Cloud", 5000.0);

        assertTrue(decorador1.tieneSoftware("Photoshop Elements"),
            "Decorador 1 debe tener Photoshop Elements");
        assertFalse(decorador1.tieneSoftware("Adobe Creative Cloud"),
            "Decorador 1 no debe tener Adobe Creative Cloud");

        assertTrue(decorador2.tieneSoftware("Adobe Creative Cloud"),
            "Decorador 2 debe tener Adobe Creative Cloud");
        assertFalse(decorador2.tieneSoftware("Photoshop Elements"),
            "Decorador 2 no debe tener Photoshop Elements");
    }
}
