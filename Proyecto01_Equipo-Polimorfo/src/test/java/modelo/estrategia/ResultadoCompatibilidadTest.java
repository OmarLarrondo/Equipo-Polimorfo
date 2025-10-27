package modelo.estrategia;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import modelo.componente.ComponentePC;
import modelo.componente.CPU;
import modelo.componente.GPU;
import modelo.componente.MotherBoard;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * Clase de pruebas unitarias para ResultadoCompatibilidad.
 *
 * <p>Valida la correcta creacion y funcionamiento de objetos ResultadoCompatibilidad,
 * asegurando que encapsulen correctamente el resultado de una verificacion de compatibilidad
 * con su estado, advertencias y componentes adaptados.
 *
 * <p>Esta suite de pruebas verifica:
 * <ul>
 *   <li>Creacion correcta con diferentes combinaciones de compatibilidad, advertencias y componentes</li>
 *   <li>Funcionamiento correcto de getters (isCompatible, getAdvertencias, getComponentesAdaptados)</li>
 *   <li>Manejo correcto de listas vacias</li>
 *   <li>Manejo correcto de listas con elementos</li>
 *   <li>Inmutabilidad de las referencias retornadas por los getters</li>
 * </ul>
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class ResultadoCompatibilidadTest {

    /**
     * Crea un CPU de prueba con los parametros especificados.
     * Esta funcion es pura y no tiene efectos secundarios.
     *
     * @param nombre el nombre del CPU
     * @param marca la marca del CPU
     * @return un nuevo objeto CPU
     */
    private static CPU crearCPU(String nombre, String marca) {
        return new CPU(nombre, 10000.0, marca, "CPU", 8, "x86-64");
    }

    /**
     * Crea una GPU de prueba con los parametros especificados.
     * Esta funcion es pura y no tiene efectos secundarios.
     *
     * @param nombre el nombre de la GPU
     * @return un nuevo objeto GPU
     */
    private static GPU crearGPU(String nombre) {
        return new GPU(nombre, 15000.0, "NVIDIA", "GPU", "GDDR6", 8);
    }

    /**
     * Crea una MotherBoard de prueba con los parametros especificados.
     * Esta funcion es pura y no tiene efectos secundarios.
     *
     * @param nombre el nombre de la motherboard
     * @return un nuevo objeto MotherBoard
     */
    private static MotherBoard crearMotherBoard(String nombre) {
        return new MotherBoard(nombre, 8000.0, "ASUS", "MotherBoard", "Z790", "LGA1700", "x86");
    }

    /**
     * Provee argumentos para probar la creacion de ResultadoCompatibilidad validos.
     * Retorna un stream de argumentos conteniendo: esCompatible, lista de advertencias,
     * lista de componentes adaptados.
     *
     * @return Stream de argumentos para ResultadoCompatibilidad validos
     */
    private static Stream<Arguments> proveedorDatosResultadosValidos() {
        return Stream.of(
            Arguments.of(true, List.of(), List.of()),
            Arguments.of(false, List.of(), List.of()),
            Arguments.of(true, List.of("Advertencia 1"), List.of()),
            Arguments.of(true, List.of("Advertencia 1", "Advertencia 2"), List.of()),
            Arguments.of(false, List.of("Error critico"), List.of()),
            Arguments.of(true, List.of(), List.of(crearCPU("Ryzen 5 7600X", "AMD"))),
            Arguments.of(true, List.of("Sistema mixto detectado"),
                        List.of(crearCPU("Ryzen 7 7700X", "AMD"))),
            Arguments.of(true, List.of("Advertencia 1", "Advertencia 2"),
                        List.of(crearCPU("Ryzen 9 7950X3D", "AMD"), crearGPU("RTX 4090")))
        );
    }

    /**
     * Valida que un ResultadoCompatibilidad tenga las propiedades esperadas.
     * Esta funcion es pura y no tiene efectos secundarios.
     *
     * @param resultado el resultado a validar
     * @param esCompatibleEsperado el estado de compatibilidad esperado
     * @param cantidadAdvertenciasEsperada la cantidad de advertencias esperadas
     * @param cantidadComponentesAdaptadosEsperada la cantidad de componentes adaptados esperados
     * @return true si todas las propiedades coinciden, false en caso contrario
     */
    private static boolean validarResultado(ResultadoCompatibilidad resultado,
                                           boolean esCompatibleEsperado,
                                           int cantidadAdvertenciasEsperada,
                                           int cantidadComponentesAdaptadosEsperada) {
        return resultado.isCompatible() == esCompatibleEsperado &&
               resultado.getAdvertencias().size() == cantidadAdvertenciasEsperada &&
               resultado.getComponentesAdaptados().size() == cantidadComponentesAdaptadosEsperada;
    }

    /**
     * Valida que una lista de advertencias contenga todas las advertencias esperadas.
     * Esta funcion es pura y no tiene efectos secundarios.
     *
     * @param advertenciasActuales las advertencias actuales
     * @param advertenciasEsperadas las advertencias esperadas
     * @return true si todas las advertencias esperadas estan presentes, false en caso contrario
     */
    private static boolean validarAdvertencias(List<String> advertenciasActuales,
                                               List<String> advertenciasEsperadas) {
        return advertenciasActuales.size() == advertenciasEsperadas.size() &&
               advertenciasActuales.containsAll(advertenciasEsperadas);
    }

    /**
     * Valida que una lista de componentes adaptados contenga todos los componentes esperados.
     * Esta funcion es pura y no tiene efectos secundarios.
     *
     * @param componentesActuales los componentes actuales
     * @param componentesEsperados los componentes esperados
     * @return true si todos los componentes esperados estan presentes, false en caso contrario
     */
    private static boolean validarComponentesAdaptados(List<ComponentePC> componentesActuales,
                                                       List<ComponentePC> componentesEsperados) {
        return componentesActuales.size() == componentesEsperados.size() &&
               componentesActuales.containsAll(componentesEsperados);
    }

    /**
     * Prueba parametrizada que valida la creacion correcta de ResultadoCompatibilidad.
     *
     * <p>Verifica que el constructor cree instancias correctas con:
     * <ul>
     *   <li>Estado de compatibilidad (true o false)</li>
     *   <li>Lista de advertencias (vacia o con elementos)</li>
     *   <li>Lista de componentes adaptados (vacia o con elementos)</li>
     * </ul>
     *
     * @param esCompatible el estado de compatibilidad
     * @param advertencias la lista de advertencias
     * @param componentesAdaptados la lista de componentes adaptados
     */
    @ParameterizedTest(name = "Resultado compatible={0}, {1} advertencias, {2} componentes adaptados")
    @MethodSource("proveedorDatosResultadosValidos")
    public void pruebaCrearResultadoConDatosValidos(boolean esCompatible,
                                                     List<String> advertencias,
                                                     List<ComponentePC> componentesAdaptados) {
        ResultadoCompatibilidad resultado = new ResultadoCompatibilidad(
            esCompatible, advertencias, componentesAdaptados
        );

        assertNotNull(resultado, "El resultado no debe ser null");
        assertTrue(
            validarResultado(resultado, esCompatible, advertencias.size(), componentesAdaptados.size()),
            "El resultado no tiene las propiedades correctas"
        );
    }

    /**
     * Prueba que valida el funcionamiento del getter isCompatible.
     *
     * <p>Verifica que el metodo isCompatible retorne correctamente
     * el estado de compatibilidad especificado en el constructor.
     */
    @Test
    public void pruebaIsCompatible() {
        Stream.of(true, false)
            .forEach(esCompatible -> {
                ResultadoCompatibilidad resultado = new ResultadoCompatibilidad(
                    esCompatible, new ArrayList<>(), new ArrayList<>()
                );
                assertEquals(esCompatible, resultado.isCompatible(),
                    String.format("isCompatible debe retornar %b", esCompatible));
            });
    }

    /**
     * Prueba que valida el funcionamiento del getter getAdvertencias.
     *
     * <p>Verifica que el metodo getAdvertencias retorne correctamente
     * la lista de advertencias especificada en el constructor.
     */
    @Test
    public void pruebaGetAdvertencias() {
        List<String> advertencias = List.of("Advertencia 1", "Advertencia 2", "Advertencia 3");
        ResultadoCompatibilidad resultado = new ResultadoCompatibilidad(
            true, advertencias, new ArrayList<>()
        );

        List<String> advertenciasObtenidas = resultado.getAdvertencias();
        assertNotNull(advertenciasObtenidas, "getAdvertencias no debe retornar null");
        assertTrue(
            validarAdvertencias(advertenciasObtenidas, advertencias),
            "getAdvertencias debe retornar todas las advertencias especificadas"
        );
    }

    /**
     * Prueba que valida el funcionamiento del getter getComponentesAdaptados.
     *
     * <p>Verifica que el metodo getComponentesAdaptados retorne correctamente
     * la lista de componentes adaptados especificada en el constructor.
     */
    @Test
    public void pruebaGetComponentesAdaptados() {
        List<ComponentePC> componentes = List.of(
            crearCPU("Ryzen 5 7600X", "AMD"),
            crearGPU("RTX 4080")
        );
        ResultadoCompatibilidad resultado = new ResultadoCompatibilidad(
            true, new ArrayList<>(), componentes
        );

        List<ComponentePC> componentesObtenidos = resultado.getComponentesAdaptados();
        assertNotNull(componentesObtenidos, "getComponentesAdaptados no debe retornar null");
        assertTrue(
            validarComponentesAdaptados(componentesObtenidos, componentes),
            "getComponentesAdaptados debe retornar todos los componentes especificados"
        );
    }

    /**
     * Prueba que valida el manejo correcto de listas vacias.
     *
     * <p>Verifica que ResultadoCompatibilidad maneje correctamente
     * listas vacias tanto de advertencias como de componentes adaptados.
     */
    @Test
    public void pruebaManejoDeListasVacias() {
        ResultadoCompatibilidad resultado = new ResultadoCompatibilidad(
            true, List.of(), List.of()
        );

        assertTrue(resultado.isCompatible(), "Debe ser compatible");
        assertNotNull(resultado.getAdvertencias(), "getAdvertencias no debe retornar null");
        assertTrue(resultado.getAdvertencias().isEmpty(), "La lista de advertencias debe estar vacia");
        assertNotNull(resultado.getComponentesAdaptados(), "getComponentesAdaptados no debe retornar null");
        assertTrue(resultado.getComponentesAdaptados().isEmpty(),
                  "La lista de componentes adaptados debe estar vacia");
    }

    /**
     * Prueba que valida el manejo correcto de listas con elementos.
     *
     * <p>Verifica que ResultadoCompatibilidad maneje correctamente
     * listas con multiples elementos tanto de advertencias como de componentes adaptados.
     */
    @Test
    public void pruebaManejoDeListasConElementos() {
        List<String> advertencias = List.of(
            "Advertencia de compatibilidad",
            "Advertencia de potencia",
            "Advertencia de arquitectura"
        );
        List<ComponentePC> componentes = List.of(
            crearCPU("Ryzen 5 7600X", "AMD"),
            crearCPU("Ryzen 7 7700X", "AMD"),
            crearMotherBoard("ROG Maximus Z790 Hero")
        );

        ResultadoCompatibilidad resultado = new ResultadoCompatibilidad(
            false, advertencias, componentes
        );

        assertFalse(resultado.isCompatible(), "Debe ser incompatible");
        assertEquals(3, resultado.getAdvertencias().size(),
                    "Debe haber exactamente 3 advertencias");
        assertEquals(3, resultado.getComponentesAdaptados().size(),
                    "Debe haber exactamente 3 componentes adaptados");
    }

    /**
     * Prueba que valida la inmutabilidad de las referencias retornadas por los getters.
     *
     * <p>Verifica que multiples llamadas a los getters retornen las mismas referencias,
     * asegurando que el estado interno no se modifica inadvertidamente.
     */
    @Test
    public void pruebaInmutabilidadDeGetters() {
        List<String> advertencias = List.of("Advertencia 1", "Advertencia 2");
        List<ComponentePC> componentes = List.of(crearCPU("Ryzen 5 7600X", "AMD"));

        ResultadoCompatibilidad resultado = new ResultadoCompatibilidad(
            true, advertencias, componentes
        );

        boolean compatible1 = resultado.isCompatible();
        boolean compatible2 = resultado.isCompatible();
        List<String> advertencias1 = resultado.getAdvertencias();
        List<String> advertencias2 = resultado.getAdvertencias();
        List<ComponentePC> componentes1 = resultado.getComponentesAdaptados();
        List<ComponentePC> componentes2 = resultado.getComponentesAdaptados();

        assertEquals(compatible1, compatible2,
                    "Multiples llamadas a isCompatible deben retornar el mismo valor");
        assertSame(advertencias1, advertencias2,
                  "Multiples llamadas a getAdvertencias deben retornar la misma referencia");
        assertSame(componentes1, componentes2,
                  "Multiples llamadas a getComponentesAdaptados deben retornar la misma referencia");
    }

    /**
     * Prueba que valida el estado compatible con advertencias.
     *
     * <p>Verifica que sea posible tener un sistema compatible pero con advertencias,
     * lo cual es un escenario valido (por ejemplo, warnings que no impiden el funcionamiento).
     */
    @Test
    public void pruebaCompatibleConAdvertencias() {
        List<String> advertencias = List.of(
            "ADVERTENCIA: La fuente de alimentacion podria ser insuficiente"
        );

        ResultadoCompatibilidad resultado = new ResultadoCompatibilidad(
            true, advertencias, new ArrayList<>()
        );

        assertTrue(resultado.isCompatible(), "El sistema debe ser compatible");
        assertEquals(1, resultado.getAdvertencias().size(),
                    "Debe haber exactamente 1 advertencia");
        assertTrue(resultado.getAdvertencias().get(0).contains("fuente de alimentacion"),
                  "La advertencia debe mencionar la fuente de alimentacion");
    }

    /**
     * Prueba que valida el estado incompatible con advertencias explicativas.
     *
     * <p>Verifica que sea posible tener un sistema incompatible con advertencias
     * que expliquen las razones de la incompatibilidad.
     */
    @Test
    public void pruebaIncompatibleConAdvertencias() {
        List<String> advertencias = List.of(
            "INCOMPATIBILIDAD: CPU Intel Core i7-13700K no es compatible con MotherBoard ROG Maximus AMD",
            "ADVERTENCIA: Arquitecturas incompatibles detectadas"
        );

        ResultadoCompatibilidad resultado = new ResultadoCompatibilidad(
            false, advertencias, new ArrayList<>()
        );

        assertFalse(resultado.isCompatible(), "El sistema debe ser incompatible");
        assertEquals(2, resultado.getAdvertencias().size(),
                    "Debe haber exactamente 2 advertencias");
        assertTrue(resultado.getAdvertencias().stream()
                  .anyMatch(adv -> adv.contains("INCOMPATIBILIDAD")),
                  "Al menos una advertencia debe indicar incompatibilidad");
    }

    /**
     * Prueba que valida el escenario de sistema mixto con componentes adaptados.
     *
     * <p>Verifica el caso tipico de un sistema mixto donde se aplican adaptadores
     * y se generan advertencias sobre la configuracion.
     */
    @Test
    public void pruebaSistemaMixtoConAdaptadores() {
        List<String> advertencias = List.of(
            "ADVERTENCIA: Sistema mixto detectado - CPUs AMD con motherboards Intel",
            "INFO: Se aplica CPUAMDAdapter para intentar compatibilidad"
        );
        List<ComponentePC> componentesAdaptados = List.of(
            crearCPU("Ryzen 9 7950X3D", "AMD")
        );

        ResultadoCompatibilidad resultado = new ResultadoCompatibilidad(
            true, advertencias, componentesAdaptados
        );

        assertTrue(resultado.isCompatible(), "El sistema mixto debe ser compatible con adaptadores");
        assertEquals(2, resultado.getAdvertencias().size(),
                    "Debe haber exactamente 2 advertencias");
        assertEquals(1, resultado.getComponentesAdaptados().size(),
                    "Debe haber exactamente 1 componente adaptado");
        assertTrue(resultado.getAdvertencias().stream()
                  .anyMatch(adv -> adv.contains("CPUAMDAdapter")),
                  "Debe haber una advertencia sobre el uso del adaptador");
    }
}
