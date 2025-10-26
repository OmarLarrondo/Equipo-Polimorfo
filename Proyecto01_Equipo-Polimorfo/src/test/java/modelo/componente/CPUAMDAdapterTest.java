package modelo.componente;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

/**
 * Clase de pruebas unitarias para CPUAMDAdapter.
 *
 * <p>Valida la correcta implementacion del patron Adapter para integrar
 * CPUs AMD como ComponentePC, asegurando que la delegacion de metodos
 * funcione correctamente.
 *
 * <p>Esta suite de pruebas verifica:
 * <ul>
 *   <li>Creacion correcta del adapter con CPU AMD</li>
 *   <li>Delegacion correcta de todos los metodos de ComponentePC</li>
 *   <li>Funcionamiento del metodo adaptar() especifico del adapter</li>
 *   <li>Manejo de excepciones para CPU null</li>
 *   <li>Implementacion correcta de la interfaz ComponentePC</li>
 * </ul>
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class CPUAMDAdapterTest {

    /**
     * Provee argumentos para probar CPUs AMD.
     * Retorna un stream de argumentos conteniendo: nombre, precio, nucleos y arquitectura.
     *
     * @return Stream de argumentos para CPUs AMD
     */
    private static Stream<Arguments> proveedorCPUsAMD() {
        return Stream.of(
            Arguments.of("Ryzen 5 5600G", 6999.00, 6, "x86-64 (AMD64)"),
            Arguments.of("Ryzen 5 7600X", 12999.00, 6, "x86-64 (AMD64)"),
            Arguments.of("Ryzen 7 7700X", 17999.00, 8, "x86-64 (AMD64)"),
            Arguments.of("Ryzen 9 7950X3D", 29999.00, 16, "x86-64 (AMD64)")
        );
    }

    /**
     * Crea un CPU AMD de prueba.
     * Esta funcion es pura y no tiene efectos secundarios.
     *
     * @param nombre el nombre del CPU
     * @param precio el precio del CPU
     * @param nucleos la cantidad de nucleos
     * @param arquitectura la arquitectura
     * @return un CPU AMD
     */
    private static CPU crearCPUAMD(String nombre, double precio, int nucleos, String arquitectura) {
        return new CPU(nombre, precio, "AMD", "CPU", nucleos, arquitectura);
    }

    /**
     * Prueba la creacion de un adapter con CPU AMD valido.
     *
     * <p>Verifica que se pueda crear un adapter correctamente con un CPU AMD.
     */
    @Test
    public void pruebaCrearAdapterConCPUAMDValido() {
        CPU cpuAMD = crearCPUAMD("Ryzen 5 5600G", 6999.00, 6, "x86-64 (AMD64)");
        CPUAMDAdapter adapter = new CPUAMDAdapter(cpuAMD);

        assertNotNull(adapter, "El adapter no debe ser null");
    }

    /**
     * Prueba la creacion de un adapter con CPU null.
     *
     * <p>Verifica que se lance IllegalArgumentException cuando se intenta
     * crear un adapter con CPU null.
     */
    @Test
    public void pruebaCrearAdapterConCPUNullLanzaExcepcion() {
        IllegalArgumentException excepcion = assertThrows(
            IllegalArgumentException.class,
            () -> new CPUAMDAdapter(null),
            "Deberia lanzar IllegalArgumentException con CPU null"
        );

        assertTrue(excepcion.getMessage().contains("nulo"),
            "El mensaje debe indicar que el CPU no puede ser nulo");
    }

    /**
     * Prueba parametrizada del metodo obtenerNombre.
     *
     * <p>Verifica que obtenerNombre delegue correctamente al CPU AMD interno.
     *
     * @param nombre el nombre del CPU
     * @param precio el precio del CPU
     * @param nucleos la cantidad de nucleos
     * @param arquitectura la arquitectura
     */
    @ParameterizedTest(name = "obtenerNombre para {0}")
    @MethodSource("proveedorCPUsAMD")
    public void pruebaDelegacionObtenerNombre(String nombre, double precio, int nucleos, String arquitectura) {
        CPU cpuAMD = crearCPUAMD(nombre, precio, nucleos, arquitectura);
        CPUAMDAdapter adapter = new CPUAMDAdapter(cpuAMD);

        assertEquals(nombre, adapter.obtenerNombre(),
            "obtenerNombre debe retornar el nombre del CPU AMD");
    }

    /**
     * Prueba parametrizada del metodo obtenerPrecio.
     *
     * <p>Verifica que obtenerPrecio delegue correctamente al CPU AMD interno.
     *
     * @param nombre el nombre del CPU
     * @param precio el precio del CPU
     * @param nucleos la cantidad de nucleos
     * @param arquitectura la arquitectura
     */
    @ParameterizedTest(name = "obtenerPrecio para {0}: ${1}")
    @MethodSource("proveedorCPUsAMD")
    public void pruebaDelegacionObtenerPrecio(String nombre, double precio, int nucleos, String arquitectura) {
        CPU cpuAMD = crearCPUAMD(nombre, precio, nucleos, arquitectura);
        CPUAMDAdapter adapter = new CPUAMDAdapter(cpuAMD);

        assertEquals(precio, adapter.obtenerPrecio(),
            "obtenerPrecio debe retornar el precio del CPU AMD");
    }

    /**
     * Prueba del metodo obtenerMarca.
     *
     * <p>Verifica que obtenerMarca delegue correctamente y retorne "AMD".
     */
    @Test
    public void pruebaDelegacionObtenerMarca() {
        CPU cpuAMD = crearCPUAMD("Ryzen 7 7700X", 17999.00, 8, "x86-64 (AMD64)");
        CPUAMDAdapter adapter = new CPUAMDAdapter(cpuAMD);

        assertEquals("AMD", adapter.obtenerMarca(),
            "obtenerMarca debe retornar AMD");
    }

    /**
     * Prueba del metodo obtenerTipo.
     *
     * <p>Verifica que obtenerTipo delegue correctamente y retorne "CPU".
     */
    @Test
    public void pruebaDelegacionObtenerTipo() {
        CPU cpuAMD = crearCPUAMD("Ryzen 9 7950X3D", 29999.00, 16, "x86-64 (AMD64)");
        CPUAMDAdapter adapter = new CPUAMDAdapter(cpuAMD);

        assertEquals("CPU", adapter.obtenerTipo(),
            "obtenerTipo debe retornar CPU");
    }

    /**
     * Prueba del metodo mostrarDetalles.
     *
     * <p>Verifica que mostrarDetalles delegue correctamente al CPU AMD interno.
     */
    @Test
    public void pruebaDelegacionMostrarDetalles() {
        CPU cpuAMD = crearCPUAMD("Ryzen 5 7600X", 12999.00, 6, "x86-64 (AMD64)");
        CPUAMDAdapter adapter = new CPUAMDAdapter(cpuAMD);

        String detallesCPU = cpuAMD.mostrarDetalles();
        String detallesAdapter = adapter.mostrarDetalles();

        assertEquals(detallesCPU, detallesAdapter,
            "mostrarDetalles debe retornar los mismos detalles del CPU AMD");
    }

    /**
     * Prueba del metodo adaptar.
     *
     * <p>Verifica que el metodo adaptar retorne un mensaje indicando
     * que el CPU AMD ha sido adaptado.
     */
    @Test
    public void pruebaMetodoAdaptar() {
        CPU cpuAMD = crearCPUAMD("Ryzen 5 5600G", 6999.00, 6, "x86-64 (AMD64)");
        CPUAMDAdapter adapter = new CPUAMDAdapter(cpuAMD);

        String mensajeAdaptacion = adapter.adaptar();

        assertNotNull(mensajeAdaptacion, "El mensaje de adaptacion no debe ser null");
        assertTrue(mensajeAdaptacion.contains("Ryzen 5 5600G"),
            "El mensaje debe contener el nombre del CPU");
        assertTrue(mensajeAdaptacion.contains("AMD"),
            "El mensaje debe mencionar AMD");
        assertTrue(mensajeAdaptacion.contains("adaptado"),
            "El mensaje debe indicar que fue adaptado");
    }

    /**
     * Prueba parametrizada del metodo adaptar con diferentes CPUs.
     *
     * <p>Verifica que el metodo adaptar funcione correctamente con
     * diferentes modelos de CPU AMD.
     *
     * @param nombre el nombre del CPU
     * @param precio el precio del CPU
     * @param nucleos la cantidad de nucleos
     * @param arquitectura la arquitectura
     */
    @ParameterizedTest(name = "adaptar {0}")
    @MethodSource("proveedorCPUsAMD")
    public void pruebaMetodoAdaptarConDiferentesCPUs(String nombre, double precio, int nucleos, String arquitectura) {
        CPU cpuAMD = crearCPUAMD(nombre, precio, nucleos, arquitectura);
        CPUAMDAdapter adapter = new CPUAMDAdapter(cpuAMD);

        String mensajeAdaptacion = adapter.adaptar();

        assertTrue(mensajeAdaptacion.contains(nombre),
            String.format("El mensaje debe contener el nombre %s", nombre));
    }

    /**
     * Prueba que el adapter implemente ComponentePC.
     *
     * <p>Verifica que CPUAMDAdapter implemente correctamente la interfaz ComponentePC.
     */
    @Test
    public void pruebaImplementaComponentePC() {
        CPU cpuAMD = crearCPUAMD("Ryzen 7 7700X", 17999.00, 8, "x86-64 (AMD64)");
        CPUAMDAdapter adapter = new CPUAMDAdapter(cpuAMD);

        assertTrue(adapter instanceof ComponentePC,
            "CPUAMDAdapter debe implementar ComponentePC");
    }

    /**
     * Prueba la delegacion completa de todos los metodos.
     *
     * <p>Verifica que todos los metodos de ComponentePC deleguen correctamente
     * al CPU AMD interno.
     */
    @Test
    public void pruebaDelegacionCompletaDeTodosLosMetodos() {
        String nombre = "Ryzen 5 7600X";
        double precio = 12999.00;
        int nucleos = 6;
        String arquitectura = "x86-64 (AMD64)";

        CPU cpuAMD = crearCPUAMD(nombre, precio, nucleos, arquitectura);
        CPUAMDAdapter adapter = new CPUAMDAdapter(cpuAMD);

        assertEquals(cpuAMD.obtenerNombre(), adapter.obtenerNombre(),
            "obtenerNombre debe delegar correctamente");
        assertEquals(cpuAMD.obtenerPrecio(), adapter.obtenerPrecio(),
            "obtenerPrecio debe delegar correctamente");
        assertEquals(cpuAMD.obtenerMarca(), adapter.obtenerMarca(),
            "obtenerMarca debe delegar correctamente");
        assertEquals(cpuAMD.obtenerTipo(), adapter.obtenerTipo(),
            "obtenerTipo debe delegar correctamente");
        assertEquals(cpuAMD.mostrarDetalles(), adapter.mostrarDetalles(),
            "mostrarDetalles debe delegar correctamente");
    }

    /**
     * Prueba que el adapter pueda usarse como ComponentePC.
     *
     * <p>Verifica que el adapter pueda usarse polimorficamente como ComponentePC.
     */
    @Test
    public void pruebaPuedeUsarseComoComponentePC() {
        CPU cpuAMD = crearCPUAMD("Ryzen 7 7700X", 17999.00, 8, "x86-64 (AMD64)");
        ComponentePC componente = new CPUAMDAdapter(cpuAMD);

        assertEquals("Ryzen 7 7700X", componente.obtenerNombre(),
            "Debe funcionar como ComponentePC");
        assertEquals(17999.00, componente.obtenerPrecio(),
            "Debe funcionar como ComponentePC");
        assertEquals("AMD", componente.obtenerMarca(),
            "Debe funcionar como ComponentePC");
        assertEquals("CPU", componente.obtenerTipo(),
            "Debe funcionar como ComponentePC");
    }

    /**
     * Prueba que multiples adapters con el mismo CPU sean independientes.
     *
     * <p>Verifica que crear multiples adapters con el mismo CPU AMD
     * produzca instancias independientes.
     */
    @Test
    public void pruebaMultiplesAdaptersSonIndependientes() {
        CPU cpuAMD = crearCPUAMD("Ryzen 5 5600G", 6999.00, 6, "x86-64 (AMD64)");

        CPUAMDAdapter adapter1 = new CPUAMDAdapter(cpuAMD);
        CPUAMDAdapter adapter2 = new CPUAMDAdapter(cpuAMD);

        assertNotSame(adapter1, adapter2,
            "Los adapters deben ser instancias diferentes");
        assertEquals(adapter1.obtenerNombre(), adapter2.obtenerNombre(),
            "Deben delegar al mismo CPU");
        assertEquals(adapter1.obtenerPrecio(), adapter2.obtenerPrecio(),
            "Deben delegar al mismo CPU");
    }

    /**
     * Prueba la inmutabilidad de la delegacion.
     *
     * <p>Verifica que multiples llamadas a los metodos del adapter
     * retornen siempre los mismos valores.
     */
    @Test
    public void pruebaInmutabilidadDeLaDelegacion() {
        CPU cpuAMD = crearCPUAMD("Ryzen 9 7950X3D", 29999.00, 16, "x86-64 (AMD64)");
        CPUAMDAdapter adapter = new CPUAMDAdapter(cpuAMD);

        String nombre1 = adapter.obtenerNombre();
        String nombre2 = adapter.obtenerNombre();
        double precio1 = adapter.obtenerPrecio();
        double precio2 = adapter.obtenerPrecio();
        String marca1 = adapter.obtenerMarca();
        String marca2 = adapter.obtenerMarca();
        String tipo1 = adapter.obtenerTipo();
        String tipo2 = adapter.obtenerTipo();

        assertEquals(nombre1, nombre2, "Multiples llamadas a obtenerNombre deben retornar el mismo valor");
        assertEquals(precio1, precio2, "Multiples llamadas a obtenerPrecio deben retornar el mismo valor");
        assertEquals(marca1, marca2, "Multiples llamadas a obtenerMarca deben retornar el mismo valor");
        assertEquals(tipo1, tipo2, "Multiples llamadas a obtenerTipo deben retornar el mismo valor");
    }

    /**
     * Prueba que el adapter pueda agregarse a un ComponenteCompuesto.
     *
     * <p>Verifica que el adapter pueda usarse en estructuras compuestas
     * como cualquier otro ComponentePC.
     */
    @Test
    public void pruebaPuedeAgregarseAComponenteCompuesto() {
        CPU cpuAMD = crearCPUAMD("Ryzen 7 7700X", 17999.00, 8, "x86-64 (AMD64)");
        CPUAMDAdapter adapter = new CPUAMDAdapter(cpuAMD);

        ComponenteCompuesto compuesto = new ComponenteCompuesto(null, "PC AMD", "Computadora");
        compuesto.agregar(adapter);

        assertEquals(17999.00, compuesto.obtenerPrecio(),
            "El precio del compuesto debe incluir el precio del adapter");
    }
}
