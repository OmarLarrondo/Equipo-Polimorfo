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
 * Clase de pruebas unitarias para OfficeDecorator.
 *
 * <p>Valida el correcto funcionamiento del decorador de Microsoft Office,
 * asegurando que agregue correctamente la suite ofimatica Office
 * a las computadoras mediante el patron Decorator.
 *
 * <p>Esta suite de pruebas verifica:
 * <ul>
 *   <li>Creacion correcta del decorador con datos validos</li>
 *   <li>Manejo de excepciones con computadora null</li>
 *   <li>Formato correcto de la descripcion incluyendo Office</li>
 *   <li>Calculo correcto del precio total</li>
 *   <li>Compatibilidad con decoradores anidados</li>
 *   <li>Busqueda case-insensitive de Office instalado</li>
 * </ul>
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class OfficeDecoratorTest {

    /**
     * Provee argumentos para probar diferentes versiones de Office y precios.
     * Retorna un stream de argumentos con nombres y precios de Office.
     *
     * @return Stream de argumentos para versiones de Office
     */
    private static Stream<Arguments> proveedorDatosOffice() {
        return Stream.of(
            Arguments.of("Microsoft Office 365", 1800.0),
            Arguments.of("Office 2021 Home", 1500.0),
            Arguments.of("Office 2021 Professional", 2200.0),
            Arguments.of("Office 365 Business", 2500.0)
        );
    }

    /**
     * Provee datos para pruebas de busqueda case-insensitive de Office.
     * Cada argumento contiene el nombre original y variantes en diferentes casos.
     *
     * @return Stream de argumentos para pruebas case-insensitive
     */
    private static Stream<Arguments> proveedorDatosCaseInsensitive() {
        return Stream.of(
            Arguments.of("Microsoft Office 365", "microsoft office 365"),
            Arguments.of("Microsoft Office 365", "MICROSOFT OFFICE 365"),
            Arguments.of("Microsoft Office 365", "MiCrOsOfT OfFiCe 365"),
            Arguments.of("Office 2021 Home", "office 2021 home")
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
     * Valida que el precio total sea la suma del precio base y el precio de Office.
     * Esta funcion es pura y no tiene efectos secundarios.
     *
     * @param precioTotal el precio total calculado
     * @param precioBase el precio base de la computadora
     * @param precioOffice el precio de Office
     * @return true si la suma es correcta, false en caso contrario
     */
    private static boolean validarPrecioTotal(double precioTotal, double precioBase, double precioOffice) {
        return Math.abs(precioTotal - (precioBase + precioOffice)) < 0.01;
    }

    /**
     * Prueba parametrizada que valida la creacion correcta del decorador Office.
     *
     * <p>Verifica que el constructor de OfficeDecorator cree instancias correctas
     * con diferentes versiones de Office y precios.
     *
     * @param nombreOffice el nombre de la version de Office
     * @param precioOffice el precio de Office
     */
    @ParameterizedTest(name = "OfficeDecorator con {0} (${1})")
    @MethodSource("proveedorDatosOffice")
    public void pruebaCrearOfficeDecoratorConDatosValidos(String nombreOffice, double precioOffice) {
        ComputadoraBasica computadoraBase = crearComputadoraBasicaDePrueba();
        OfficeDecorator decorador = new OfficeDecorator(computadoraBase, nombreOffice, precioOffice);

        assertNotNull(decorador, "El decorador no debe ser null");
        assertTrue(decorador.tieneSoftware(nombreOffice),
            String.format("Debe tener %s instalado", nombreOffice));
    }

    /**
     * Prueba que el constructor lance excepcion cuando la computadora es null.
     *
     * <p>Verifica que OfficeDecorator valide correctamente que la computadora
     * base no sea null, lanzando IllegalArgumentException en caso contrario.
     */
    @Test
    public void pruebaConstructorConComputadoraNullLanzaExcepcion() {
        IllegalArgumentException excepcion = assertThrows(
            IllegalArgumentException.class,
            () -> new OfficeDecorator(null, "Microsoft Office 365", 1800.0),
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
     * <p>Verifica que la descripcion incluya el nombre de Office con el
     * formato correcto: "\n  + Software: [nombre]".
     *
     * @param nombreOffice el nombre de la version de Office
     * @param precioOffice el precio de Office
     */
    @ParameterizedTest(name = "Descripcion con {0}")
    @MethodSource("proveedorDatosOffice")
    public void pruebaObtenerDescripcionIncluyeOffice(String nombreOffice, double precioOffice) {
        ComputadoraBasica computadoraBase = crearComputadoraBasicaDePrueba();
        OfficeDecorator decorador = new OfficeDecorator(computadoraBase, nombreOffice, precioOffice);

        String descripcion = decorador.obtenerDescripcion();

        assertNotNull(descripcion, "La descripcion no debe ser null");
        assertTrue(descripcion.contains("+ Software: " + nombreOffice),
            String.format("La descripcion debe incluir '+ Software: %s'", nombreOffice));
        assertTrue(descripcion.contains("PC de Prueba"),
            "La descripcion debe incluir el nombre de la computadora base");
    }

    /**
     * Prueba parametrizada que valida el calculo correcto del precio total.
     *
     * <p>Verifica que obtenerPrecioTotal() retorne la suma del precio base
     * de la computadora mas el precio de Office.
     *
     * @param nombreOffice el nombre de la version de Office
     * @param precioOffice el precio de Office
     */
    @ParameterizedTest(name = "Precio total con {0} (${1})")
    @MethodSource("proveedorDatosOffice")
    public void pruebaObtenerPrecioTotalSumaPrecios(String nombreOffice, double precioOffice) {
        ComputadoraBasica computadoraBase = crearComputadoraBasicaDePrueba();
        double precioBase = computadoraBase.obtenerPrecioTotal();

        OfficeDecorator decorador = new OfficeDecorator(computadoraBase, nombreOffice, precioOffice);
        double precioTotal = decorador.obtenerPrecioTotal();

        assertTrue(
            validarPrecioTotal(precioTotal, precioBase, precioOffice),
            String.format("El precio total debe ser %.2f (base) + %.2f (Office) = %.2f",
                precioBase, precioOffice, precioBase + precioOffice)
        );
    }

    /**
     * Prueba parametrizada que valida la busqueda case-insensitive de Office.
     *
     * <p>Verifica que tieneSoftware() encuentre Office instalado
     * independientemente de si se busca en mayusculas, minusculas o mixto.
     *
     * @param nombreOriginal el nombre original de Office instalado
     * @param nombreBusqueda el nombre a buscar en diferente case
     */
    @ParameterizedTest(name = "Buscar '{0}' como '{1}'")
    @MethodSource("proveedorDatosCaseInsensitive")
    public void pruebaTieneSoftwareCaseInsensitive(String nombreOriginal, String nombreBusqueda) {
        ComputadoraBasica computadoraBase = crearComputadoraBasicaDePrueba();
        OfficeDecorator decorador = new OfficeDecorator(computadoraBase, nombreOriginal, 1800.0);

        assertTrue(decorador.tieneSoftware(nombreBusqueda),
            String.format("Debe encontrar '%s' al buscar '%s'", nombreOriginal, nombreBusqueda));
    }

    /**
     * Prueba que tieneSoftware retorne false cuando Office no esta instalado.
     *
     * <p>Verifica que tieneSoftware() retorne false cuando se busca Office
     * en una computadora que no tiene el decorador de Office.
     */
    @Test
    public void pruebaTieneSoftwareRetornaFalseCuandoNoEstaInstalado() {
        ComputadoraBasica computadoraBase = crearComputadoraBasicaDePrueba();

        assertFalse(computadoraBase.tieneSoftware("Microsoft Office 365"),
            "La computadora sin decorador no debe tener Office");
    }

    /**
     * Prueba que decoradores anidados acumulen correctamente los precios.
     *
     * <p>Verifica que al anidar OfficeDecorator con otros decoradores,
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
     * <p>Verifica que al anidar OfficeDecorator con otros decoradores,
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
     * <p>Verifica que cuando OfficeDecorator esta anidado con otros decoradores,
     * tieneSoftware() pueda encontrar Office en cualquier nivel de la cadena.
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
     * <p>Verifica que OfficeDecorator retorne la misma lista de componentes
     * que la computadora base, sin modificarla.
     */
    @Test
    public void pruebaObtenerComponentesDelegaAComputadoraBase() {
        ComputadoraBasica computadoraBase = crearComputadoraBasicaDePrueba();
        int componentesBase = computadoraBase.obtenerComponentes().size();

        OfficeDecorator decorador = new OfficeDecorator(computadoraBase, "Microsoft Office 365", 1800.0);
        int componentesDecorador = decorador.obtenerComponentes().size();

        assertEquals(componentesBase, componentesDecorador,
            "La cantidad de componentes debe ser la misma");
    }

    /**
     * Prueba que agregarComponente delegue correctamente a la computadora base.
     *
     * <p>Verifica que al agregar un componente a traves de OfficeDecorator,
     * este se agregue a la computadora base subyacente.
     */
    @Test
    public void pruebaAgregarComponenteDelegaAComputadoraBase() {
        ComputadoraBasica computadoraBase = crearComputadoraBasicaDePrueba();
        int componentesIniciales = computadoraBase.obtenerComponentes().size();

        OfficeDecorator decorador = new OfficeDecorator(computadoraBase, "Microsoft Office 365", 1800.0);
        CPU nuevoCPU = new CPU("Core i7-13700K", 24999.00, "Intel", "CPU", 16, "x86-64");
        decorador.agregarComponente(nuevoCPU);

        assertEquals(componentesIniciales + 1, computadoraBase.obtenerComponentes().size(),
            "El componente debe agregarse a la computadora base");
    }

    /**
     * Prueba que multiples instancias de OfficeDecorator sean independientes.
     *
     * <p>Verifica que crear multiples instancias de OfficeDecorator con
     * diferentes computadoras no cause interferencia entre ellas.
     */
    @Test
    public void pruebaMultiplesInstanciasIndependientes() {
        ComputadoraBasica computadora1 = new ComputadoraBasica("PC 1");
        ComputadoraBasica computadora2 = new ComputadoraBasica("PC 2");

        OfficeDecorator decorador1 = new OfficeDecorator(computadora1, "Office 2021 Home", 1500.0);
        OfficeDecorator decorador2 = new OfficeDecorator(computadora2, "Office 365 Business", 2500.0);

        assertTrue(decorador1.tieneSoftware("Office 2021 Home"),
            "Decorador 1 debe tener Office 2021 Home");
        assertFalse(decorador1.tieneSoftware("Office 365 Business"),
            "Decorador 1 no debe tener Office 365 Business");

        assertTrue(decorador2.tieneSoftware("Office 365 Business"),
            "Decorador 2 debe tener Office 365 Business");
        assertFalse(decorador2.tieneSoftware("Office 2021 Home"),
            "Decorador 2 no debe tener Office 2021 Home");
    }
}
