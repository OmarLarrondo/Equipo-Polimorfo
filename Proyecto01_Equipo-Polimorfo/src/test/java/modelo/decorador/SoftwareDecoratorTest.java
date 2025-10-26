package modelo.decorador;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import modelo.componente.ComponentePC;
import modelo.componente.CPU;
import modelo.computadora.ComputadoraBase;
import modelo.computadora.ComputadoraBasica;

/**
 * Clase de pruebas unitarias para SoftwareDecorator.
 *
 * <p>Valida el correcto funcionamiento de la clase abstracta SoftwareDecorator,
 * asegurando que los decoradores de software funcionen correctamente como
 * parte del patron Decorator para agregar software a computadoras.
 *
 * <p>Esta suite de pruebas verifica:
 * <ul>
 *   <li>Constructor valida que la computadora base no sea null</li>
 *   <li>Calculo correcto del precio total (precio base + precio software)</li>
 *   <li>Delegacion correcta de metodos a la computadora base</li>
 *   <li>Busqueda case-insensitive y recursiva de software instalado</li>
 *   <li>Decoradores anidados funcionan correctamente</li>
 * </ul>
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class SoftwareDecoratorTest {

    /**
     * Implementacion concreta de SoftwareDecorator para pruebas.
     * Permite testear la funcionalidad de la clase abstracta.
     */
    private static class SoftwareDecoratorConcreto extends SoftwareDecorator {
        /**
         * Constructor que inicializa el decorador concreto de prueba.
         *
         * @param computadora La computadora base a decorar.
         * @param nombreSoftware El nombre del software.
         * @param precioSoftware El precio del software.
         */
        public SoftwareDecoratorConcreto(ComputadoraBase computadora, String nombreSoftware, double precioSoftware) {
            super(computadora, nombreSoftware, precioSoftware);
        }

        /**
         * Obtiene la descripcion de la computadora incluyendo el software.
         *
         * @return La descripcion de la computadora base mas el software.
         */
        @Override
        public String obtenerDescripcion() {
            return computadora.obtenerDescripcion() + "\n  + Software: " + nombreSoftware;
        }
    }

    /**
     * Provee argumentos para probar diferentes nombres de software.
     * Retorna un stream de argumentos con nombres y precios de software.
     *
     * @return Stream de argumentos para software valido
     */
    private static Stream<Arguments> proveedorDatosSoftwareValidos() {
        return Stream.of(
            Arguments.of("Windows 10/11", 2500.0),
            Arguments.of("Microsoft Office 365", 1800.0),
            Arguments.of("Adobe Photoshop", 3200.0),
            Arguments.of("AutoCAD", 8500.0),
            Arguments.of("WSL Terminal", 0.0)
        );
    }

    /**
     * Provee datos para pruebas de busqueda case-insensitive.
     * Cada argumento contiene el nombre original y variantes en diferentes casos.
     *
     * @return Stream de argumentos para pruebas case-insensitive
     */
    private static Stream<Arguments> proveedorDatosCaseInsensitive() {
        return Stream.of(
            Arguments.of("Windows 10/11", "windows 10/11"),
            Arguments.of("Windows 10/11", "WINDOWS 10/11"),
            Arguments.of("Windows 10/11", "WiNdOwS 10/11"),
            Arguments.of("Microsoft Office 365", "microsoft office 365"),
            Arguments.of("Adobe Photoshop", "ADOBE PHOTOSHOP")
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
        return computadora;
    }

    /**
     * Valida que el precio total sea la suma del precio base y el precio del software.
     * Esta funcion es pura y no tiene efectos secundarios.
     *
     * @param precioTotal el precio total calculado
     * @param precioBase el precio base de la computadora
     * @param precioSoftware el precio del software
     * @return true si la suma es correcta, false en caso contrario
     */
    private static boolean validarPrecioTotal(double precioTotal, double precioBase, double precioSoftware) {
        return Math.abs(precioTotal - (precioBase + precioSoftware)) < 0.01;
    }

    /**
     * Prueba que el constructor lance excepcion cuando la computadora es null.
     *
     * <p>Verifica que SoftwareDecorator valide correctamente que la computadora
     * base no sea null, lanzando IllegalArgumentException en caso contrario.
     */
    @Test
    public void pruebaConstructorConComputadoraNullLanzaExcepcion() {
        IllegalArgumentException excepcion = assertThrows(
            IllegalArgumentException.class,
            () -> new SoftwareDecoratorConcreto(null, "Software de Prueba", 1000.0),
            "Deberia lanzar IllegalArgumentException cuando computadora es null"
        );

        assertNotNull(excepcion.getMessage(),
            "El mensaje de error no debe ser null");
        assertTrue(excepcion.getMessage().contains("nula"),
            "El mensaje debe indicar que la computadora no puede ser nula");
    }

    /**
     * Prueba parametrizada que valida el calculo correcto del precio total.
     *
     * <p>Verifica que obtenerPrecioTotal() retorne la suma del precio base
     * de la computadora mas el precio del software agregado.
     *
     * @param nombreSoftware el nombre del software
     * @param precioSoftware el precio del software
     */
    @ParameterizedTest(name = "Precio total con {0} (${1})")
    @MethodSource("proveedorDatosSoftwareValidos")
    public void pruebaObtenerPrecioTotalSumaPrecios(String nombreSoftware, double precioSoftware) {
        ComputadoraBasica computadoraBase = crearComputadoraBasicaDePrueba();
        double precioBase = computadoraBase.obtenerPrecioTotal();

        SoftwareDecorator decorador = new SoftwareDecoratorConcreto(computadoraBase, nombreSoftware, precioSoftware);
        double precioTotal = decorador.obtenerPrecioTotal();

        assertTrue(
            validarPrecioTotal(precioTotal, precioBase, precioSoftware),
            String.format("El precio total debe ser %.2f (base) + %.2f (software) = %.2f",
                precioBase, precioSoftware, precioBase + precioSoftware)
        );
    }

    /**
     * Prueba que obtenerComponentes delegue correctamente a la computadora base.
     *
     * <p>Verifica que el decorador retorne la misma lista de componentes
     * que la computadora base, sin modificarla.
     */
    @Test
    public void pruebaObtenerComponentesDelegaAComputadoraBase() {
        ComputadoraBasica computadoraBase = crearComputadoraBasicaDePrueba();
        List<ComponentePC> componentesBase = computadoraBase.obtenerComponentes();

        SoftwareDecorator decorador = new SoftwareDecoratorConcreto(computadoraBase, "Software", 1000.0);
        List<ComponentePC> componentesDecorador = decorador.obtenerComponentes();

        assertEquals(componentesBase, componentesDecorador,
            "obtenerComponentes debe delegar a la computadora base");
        assertEquals(componentesBase.size(), componentesDecorador.size(),
            "La cantidad de componentes debe ser la misma");
    }

    /**
     * Prueba que agregarComponente delegue correctamente a la computadora base.
     *
     * <p>Verifica que al agregar un componente a traves del decorador,
     * este se agregue a la computadora base subyacente.
     */
    @Test
    public void pruebaAgregarComponenteDelegaAComputadoraBase() {
        ComputadoraBasica computadoraBase = crearComputadoraBasicaDePrueba();
        int componentesIniciales = computadoraBase.obtenerComponentes().size();

        SoftwareDecorator decorador = new SoftwareDecoratorConcreto(computadoraBase, "Software", 1000.0);
        CPU nuevoCPU = new CPU("Core i7-13700K", 24999.00, "Intel", "CPU", 16, "x86-64");
        decorador.agregarComponente(nuevoCPU);

        assertEquals(componentesIniciales + 1, computadoraBase.obtenerComponentes().size(),
            "El componente debe agregarse a la computadora base");
        assertTrue(computadoraBase.obtenerComponentes().contains(nuevoCPU),
            "La computadora base debe contener el nuevo componente");
    }

    /**
     * Prueba parametrizada que valida la busqueda case-insensitive de software.
     *
     * <p>Verifica que tieneSoftware() encuentre el software instalado
     * independientemente de si se busca en mayusculas, minusculas o mixto.
     *
     * @param nombreOriginal el nombre original del software instalado
     * @param nombreBusqueda el nombre a buscar en diferente case
     */
    @ParameterizedTest(name = "Buscar '{0}' como '{1}'")
    @MethodSource("proveedorDatosCaseInsensitive")
    public void pruebaTieneSoftwareCaseInsensitive(String nombreOriginal, String nombreBusqueda) {
        ComputadoraBasica computadoraBase = crearComputadoraBasicaDePrueba();
        SoftwareDecorator decorador = new SoftwareDecoratorConcreto(computadoraBase, nombreOriginal, 1000.0);

        assertTrue(decorador.tieneSoftware(nombreBusqueda),
            String.format("Debe encontrar '%s' al buscar '%s'", nombreOriginal, nombreBusqueda));
    }

    /**
     * Prueba que tieneSoftware retorne false cuando el software no esta instalado.
     *
     * <p>Verifica que tieneSoftware() retorne false cuando se busca un
     * software que no fue agregado a la computadora.
     */
    @Test
    public void pruebaTieneSoftwareRetornaFalseCuandoNoEstaInstalado() {
        ComputadoraBasica computadoraBase = crearComputadoraBasicaDePrueba();
        SoftwareDecorator decorador = new SoftwareDecoratorConcreto(computadoraBase, "Windows 10/11", 2500.0);

        assertFalse(decorador.tieneSoftware("Adobe Photoshop"),
            "No debe encontrar software que no esta instalado");
        assertFalse(decorador.tieneSoftware("AutoCAD"),
            "No debe encontrar software que no esta instalado");
    }

    /**
     * Prueba que tieneSoftware busque recursivamente en decoradores anidados.
     *
     * <p>Verifica que cuando hay multiples decoradores anidados, tieneSoftware()
     * encuentre software instalado en cualquier nivel de la cadena de decoracion.
     */
    @Test
    public void pruebaTieneSoftwareBuscaRecursivamente() {
        ComputadoraBasica computadoraBase = crearComputadoraBasicaDePrueba();
        SoftwareDecorator decorador1 = new SoftwareDecoratorConcreto(computadoraBase, "Windows 10/11", 2500.0);
        SoftwareDecorator decorador2 = new SoftwareDecoratorConcreto(decorador1, "Microsoft Office 365", 1800.0);
        SoftwareDecorator decorador3 = new SoftwareDecoratorConcreto(decorador2, "Adobe Photoshop", 3200.0);

        assertTrue(decorador3.tieneSoftware("Windows 10/11"),
            "Debe encontrar Windows en el primer nivel");
        assertTrue(decorador3.tieneSoftware("Microsoft Office 365"),
            "Debe encontrar Office en el segundo nivel");
        assertTrue(decorador3.tieneSoftware("Adobe Photoshop"),
            "Debe encontrar Photoshop en el tercer nivel");
    }

    /**
     * Prueba que decoradores anidados acumulen correctamente los precios.
     *
     * <p>Verifica que al anidar multiples decoradores, el precio total
     * sea la suma del precio base mas todos los precios de software.
     */
    @Test
    public void pruebaDecoradoresAnidadosAcumulanPrecios() {
        ComputadoraBasica computadoraBase = crearComputadoraBasicaDePrueba();
        double precioBase = computadoraBase.obtenerPrecioTotal();

        SoftwareDecorator decorador1 = new SoftwareDecoratorConcreto(computadoraBase, "Windows", 2500.0);
        SoftwareDecorator decorador2 = new SoftwareDecoratorConcreto(decorador1, "Office", 1800.0);
        SoftwareDecorator decorador3 = new SoftwareDecoratorConcreto(decorador2, "Photoshop", 3200.0);

        double precioEsperado = precioBase + 2500.0 + 1800.0 + 3200.0;
        double precioTotal = decorador3.obtenerPrecioTotal();

        assertTrue(
            Math.abs(precioTotal - precioEsperado) < 0.01,
            String.format("El precio total debe ser %.2f, pero fue %.2f", precioEsperado, precioTotal)
        );
    }

    /**
     * Prueba que obtenerDescripcion incluya el software agregado.
     *
     * <p>Verifica que la descripcion de la computadora decorada incluya
     * el nombre del software junto con la descripcion base.
     */
    @Test
    public void pruebaObtenerDescripcionIncluyeSoftware() {
        ComputadoraBasica computadoraBase = crearComputadoraBasicaDePrueba();
        String nombreSoftware = "Windows 10/11";

        SoftwareDecorator decorador = new SoftwareDecoratorConcreto(computadoraBase, nombreSoftware, 2500.0);
        String descripcion = decorador.obtenerDescripcion();

        assertNotNull(descripcion, "La descripcion no debe ser null");
        assertTrue(descripcion.contains(nombreSoftware),
            "La descripcion debe incluir el nombre del software");
        assertTrue(descripcion.contains("PC de Prueba"),
            "La descripcion debe incluir el nombre de la computadora base");
    }

    /**
     * Prueba que decoradores anidados incluyan todo el software en la descripcion.
     *
     * <p>Verifica que al anidar multiples decoradores, la descripcion final
     * incluya todos los software agregados en el orden correcto.
     */
    @Test
    public void pruebaDecoradoresAnidadosIncluyenTodoElSoftware() {
        ComputadoraBasica computadoraBase = crearComputadoraBasicaDePrueba();

        SoftwareDecorator decorador1 = new SoftwareDecoratorConcreto(computadoraBase, "Windows", 2500.0);
        SoftwareDecorator decorador2 = new SoftwareDecoratorConcreto(decorador1, "Office", 1800.0);
        SoftwareDecorator decorador3 = new SoftwareDecoratorConcreto(decorador2, "Photoshop", 3200.0);

        String descripcion = decorador3.obtenerDescripcion();

        assertTrue(descripcion.contains("Windows"), "Debe incluir Windows");
        assertTrue(descripcion.contains("Office"), "Debe incluir Office");
        assertTrue(descripcion.contains("Photoshop"), "Debe incluir Photoshop");
    }

    /**
     * Prueba que la computadora base sin decoradores no tenga software.
     *
     * <p>Verifica que al llamar tieneSoftware() en una ComputadoraBasica
     * sin decoradores, siempre retorne false.
     */
    @Test
    public void pruebaComputadoraBaseSinDecoradoresNoTieneSoftware() {
        ComputadoraBasica computadoraBase = crearComputadoraBasicaDePrueba();

        assertFalse(computadoraBase.tieneSoftware("Windows"),
            "La computadora basica sin decoradores no debe tener software");
        assertFalse(computadoraBase.tieneSoftware("Office"),
            "La computadora basica sin decoradores no debe tener software");
    }
}
