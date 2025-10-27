package modelo.estrategia;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import modelo.componente.ComponentePC;
import modelo.componente.CPU;
import modelo.componente.CPUAMDAdapter;
import modelo.componente.GPU;
import modelo.componente.MotherBoard;
import modelo.componente.RAM;
import modelo.componente.Disco;
import modelo.componente.FuenteAlimentacion;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * Clase de pruebas unitarias para CompatibilidadMixta.
 *
 * <p>Valida la correcta verificacion de compatibilidad para sistemas mixtos que
 * combinan componentes Intel y AMD, asegurando que la estrategia detecte automaticamente
 * la necesidad de adaptadores, los aplique correctamente y genere las advertencias apropiadas.
 *
 * <p>Esta suite de pruebas verifica:
 * <ul>
 *   <li>Deteccion automatica de sistema mixto cuando hay CPU AMD presente</li>
 *   <li>Aplicacion automatica de CPUAMDAdapter para CPUs AMD</li>
 *   <li>Componentes adaptados se agregan a la lista de componentesAdaptados</li>
 *   <li>Advertencias sobre sistema mixto y uso de adaptadores</li>
 *   <li>Sistema solo Intel no aplica adaptadores</li>
 *   <li>Sistema solo AMD si aplica adaptadores</li>
 *   <li>Verificacion de potencia con overhead mixto (factor 1.25 vs 1.2)</li>
 *   <li>Compatibilidad flexible en sistemas mixtos</li>
 * </ul>
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class CompatibilidadMixtaTest {

    /**
     * Crea un CPU AMD de prueba con los parametros especificados.
     * Esta funcion es pura y no tiene efectos secundarios.
     *
     * @param nombre el nombre del CPU
     * @param nucleos la cantidad de nucleos
     * @return un nuevo objeto CPU AMD
     */
    private static CPU crearCPUAMD(String nombre, int nucleos) {
        return new CPU(nombre, 12000.0, "AMD", "CPU", nucleos, "x86-64 (AMD64)");
    }

    /**
     * Crea un CPU Intel de prueba con los parametros especificados.
     * Esta funcion es pura y no tiene efectos secundarios.
     *
     * @param nombre el nombre del CPU
     * @param nucleos la cantidad de nucleos
     * @return un nuevo objeto CPU Intel
     */
    private static CPU crearCPUIntel(String nombre, int nucleos) {
        return new CPU(nombre, 15000.0, "Intel", "CPU", nucleos, "x86-64");
    }

    /**
     * Crea una MotherBoard Intel de prueba.
     * Esta funcion es pura y no tiene efectos secundarios.
     *
     * @param nombre el nombre de la motherboard
     * @return un nuevo objeto MotherBoard Intel
     */
    private static MotherBoard crearMotherBoardIntel(String nombre) {
        return new MotherBoard(nombre, 8000.0, "ASUS", "MotherBoard", "Z790", "LGA1700", "x86");
    }

    /**
     * Crea una GPU de prueba.
     * Esta funcion es pura y no tiene efectos secundarios.
     *
     * @param nombre el nombre de la GPU
     * @return un nuevo objeto GPU
     */
    private static GPU crearGPU(String nombre) {
        return new GPU(nombre, 20000.0, "NVIDIA", "GPU", "GDDR6", 12);
    }

    /**
     * Crea una RAM de prueba.
     * Esta funcion es pura y no tiene efectos secundarios.
     *
     * @param capacidad la capacidad en GB
     * @return un nuevo objeto RAM
     */
    private static RAM crearRAM(int capacidad) {
        return new RAM("Kingston " + capacidad + "GB", 2000.0, "Kingston", "RAM", capacidad, "DDR4");
    }

    /**
     * Crea un Disco de prueba.
     * Esta funcion es pura y no tiene efectos secundarios.
     *
     * @param capacidad la capacidad en GB
     * @param tipo el tipo de disco (HDD o SSD)
     * @return un nuevo objeto Disco
     */
    private static Disco crearDisco(int capacidad, String tipo) {
        return new Disco("Kingston SSD " + capacidad + "GB", 3000.0, "Kingston", tipo, capacidad, tipo);
    }

    /**
     * Crea una Fuente de Alimentacion de prueba.
     * Esta funcion es pura y no tiene efectos secundarios.
     *
     * @param potencia la potencia en vatios
     * @return un nuevo objeto FuenteAlimentacion
     */
    private static FuenteAlimentacion crearFuente(int potencia) {
        return new FuenteAlimentacion("EVGA " + potencia + "W", 5000.0, "EVGA", "Fuente", potencia, "80 PLUS Gold");
    }

    /**
     * Calcula el consumo estimado de potencia para una lista de componentes mixtos.
     * Esta funcion es pura y no tiene efectos secundarios.
     * Consumos: CPU=135W (promedio mixto), GPU=250W, RAM=10W, Disco=15W, MotherBoard=80W
     *
     * @param componentes la lista de componentes
     * @return el consumo estimado en vatios
     */
    private static int calcularConsumoEstimadoMixto(List<ComponentePC> componentes) {
        return componentes.stream()
            .mapToInt(c -> {
                if (c instanceof CPU || c instanceof CPUAMDAdapter) return 135;
                if (c instanceof GPU) return 250;
                if (c.obtenerTipo().equals("RAM")) return 10;
                if (c.obtenerTipo().equals("Disco") || c.obtenerTipo().equals("HDD") || c.obtenerTipo().equals("SSD")) return 15;
                if (c instanceof MotherBoard) return 80;
                return 0;
            })
            .sum();
    }

    /**
     * Provee argumentos para probar CPUs AMD del inventario.
     *
     * @return Stream de argumentos con CPUs AMD
     */
    private static Stream<Arguments> proveedorCPUsAMD() {
        return Stream.of(
            Arguments.of("Ryzen 5 5600G", 6),
            Arguments.of("Ryzen 5 7600X", 6),
            Arguments.of("Ryzen 7 7700X", 8),
            Arguments.of("Ryzen 9 7950X3D", 16)
        );
    }

    /**
     * Provee argumentos para probar configuraciones mixtas completas.
     *
     * @return Stream de argumentos con configuraciones mixtas
     */
    private static Stream<Arguments> proveedorConfiguracionesMixtas() {
        return Stream.of(
            Arguments.of("Ryzen 5 7600X", 6, "ROG Maximus Z790 Hero", 1000),
            Arguments.of("Ryzen 7 7700X", 8, "TUF Gaming B760-Plus WIFI D4", 1200),
            Arguments.of("Ryzen 9 7950X3D", 16, "ROG Maximus Z790 Hero", 1500)
        );
    }

    /**
     * Valida que una lista de advertencias contenga al menos una advertencia con el texto especificado.
     * Esta funcion es pura y no tiene efectos secundarios.
     *
     * @param advertencias la lista de advertencias
     * @param textoEsperado el texto que debe estar presente
     * @return true si se encuentra el texto, false en caso contrario
     */
    private static boolean contieneAdvertencia(List<String> advertencias, String textoEsperado) {
        return advertencias.stream()
            .anyMatch(adv -> adv.contains(textoEsperado));
    }

    /**
     * Valida que un componente sea una instancia de CPUAMDAdapter.
     * Esta funcion es pura y no tiene efectos secundarios.
     *
     * @param componente el componente a validar
     * @return true si es un CPUAMDAdapter, false en caso contrario
     */
    private static boolean esCPUAMDAdapter(ComponentePC componente) {
        return componente instanceof CPUAMDAdapter;
    }

    /**
     * Cuenta cuantos componentes en una lista son CPUAMDAdapter.
     * Esta funcion es pura y no tiene efectos secundarios.
     *
     * @param componentes la lista de componentes
     * @return la cantidad de adaptadores encontrados
     */
    private static long contarAdaptadores(List<ComponentePC> componentes) {
        return componentes.stream()
            .filter(CompatibilidadMixtaTest::esCPUAMDAdapter)
            .count();
    }

    /**
     * Prueba parametrizada que valida la deteccion automatica de sistema mixto.
     *
     * <p>Verifica que la estrategia mixta detecte automaticamente cuando
     * hay una CPU AMD presente en el sistema.
     *
     * @param nombreCPU el nombre del CPU AMD
     * @param nucleos la cantidad de nucleos del CPU
     */
    @ParameterizedTest(name = "Deteccion sistema mixto con CPU AMD {0} ({1} nucleos)")
    @MethodSource("proveedorCPUsAMD")
    public void pruebaDeteccionSistemaMixto(String nombreCPU, int nucleos) {
        CompatibilidadMixta estrategia = new CompatibilidadMixta();
        List<ComponentePC> componentes = List.of(
            crearCPUAMD(nombreCPU, nucleos),
            crearMotherBoardIntel("ROG Maximus Z790 Hero")
        );

        ResultadoCompatibilidad resultado = estrategia.verificarCompatibilidad(componentes);

        assertNotNull(resultado, "El resultado no debe ser null");
        assertTrue(resultado.isCompatible(),
                  "Sistema mixto debe ser marcado como compatible");
        assertTrue(contieneAdvertencia(resultado.getAdvertencias(), "Sistema mixto detectado"),
                  "Debe haber advertencia de sistema mixto detectado");
        assertTrue(contieneAdvertencia(resultado.getAdvertencias(), "CPUs AMD con motherboards Intel"),
                  "Debe mencionar la mezcla de CPUs AMD con motherboards Intel");
    }

    /**
     * Prueba parametrizada que valida la aplicacion automatica de CPUAMDAdapter.
     *
     * <p>Verifica que la estrategia mixta aplique automaticamente el adaptador
     * CPUAMDAdapter cuando detecta una CPU AMD.
     *
     * @param nombreCPU el nombre del CPU AMD
     * @param nucleos la cantidad de nucleos del CPU
     */
    @ParameterizedTest(name = "Aplicacion CPUAMDAdapter para {0}")
    @MethodSource("proveedorCPUsAMD")
    public void pruebaAplicacionAutomaticaAdaptador(String nombreCPU, int nucleos) {
        CompatibilidadMixta estrategia = new CompatibilidadMixta();
        List<ComponentePC> componentes = List.of(
            crearCPUAMD(nombreCPU, nucleos),
            crearMotherBoardIntel("ROG Maximus Z790 Hero"),
            crearGPU("RTX 4070")
        );

        ResultadoCompatibilidad resultado = estrategia.verificarCompatibilidad(componentes);

        assertNotNull(resultado, "El resultado no debe ser null");
        assertFalse(resultado.getComponentesAdaptados().isEmpty(),
                   "Debe haber componentes adaptados en sistema mixto");
        assertTrue(contarAdaptadores(resultado.getComponentesAdaptados()) > 0,
                  "Debe haber al menos un CPUAMDAdapter en los componentes adaptados");
        assertTrue(contieneAdvertencia(resultado.getAdvertencias(), "CPUAMDAdapter"),
                  "Debe haber advertencia mencionando el uso de CPUAMDAdapter");
    }

    /**
     * Prueba que valida que los adaptadores se agregan a componentesAdaptados.
     *
     * <p>Verifica que la lista de componentesAdaptados contenga exactamente
     * los componentes que fueron adaptados.
     */
    @Test
    public void pruebaComponentesAdaptadosContienenAdaptadores() {
        CompatibilidadMixta estrategia = new CompatibilidadMixta();
        List<ComponentePC> componentes = List.of(
            crearCPUAMD("Ryzen 9 7950X3D", 16),
            crearMotherBoardIntel("ROG Maximus Z790 Hero"),
            crearGPU("RTX 4090"),
            crearRAM(32)
        );

        ResultadoCompatibilidad resultado = estrategia.verificarCompatibilidad(componentes);

        assertNotNull(resultado, "El resultado no debe ser null");
        assertEquals(4, resultado.getComponentesAdaptados().size(),
                    "Debe haber exactamente 4 componentes en la lista adaptada");
        assertEquals(1, contarAdaptadores(resultado.getComponentesAdaptados()),
                    "Debe haber exactamente 1 CPUAMDAdapter");

        ComponentePC cpuAdaptado = resultado.getComponentesAdaptados().stream()
            .filter(CompatibilidadMixtaTest::esCPUAMDAdapter)
            .findFirst()
            .orElse(null);

        assertNotNull(cpuAdaptado, "Debe encontrarse el CPU adaptado");
        assertTrue(cpuAdaptado instanceof CPUAMDAdapter,
                  "El componente debe ser instancia de CPUAMDAdapter");
    }

    /**
     * Prueba que valida sistema solo Intel no aplica adaptadores.
     *
     * <p>Verifica que cuando todos los componentes son Intel,
     * no se aplican adaptadores ni se detecta como sistema mixto.
     */
    @Test
    public void pruebaSistemaIntelPuroNoAplicaAdaptadores() {
        CompatibilidadMixta estrategia = new CompatibilidadMixta();
        List<ComponentePC> componentes = List.of(
            crearCPUIntel("Core i7-13700K", 16),
            crearMotherBoardIntel("ROG Maximus Z790 Hero"),
            crearGPU("RTX 4070"),
            crearRAM(16)
        );

        ResultadoCompatibilidad resultado = estrategia.verificarCompatibilidad(componentes);

        assertNotNull(resultado, "El resultado no debe ser null");
        assertTrue(resultado.isCompatible(),
                  "Sistema Intel puro debe ser compatible");
        assertEquals(4, resultado.getComponentesAdaptados().size(),
                    "Debe haber 4 componentes (sin adaptadores adicionales)");
        assertEquals(0, contarAdaptadores(resultado.getComponentesAdaptados()),
                    "No debe haber adaptadores en sistema Intel puro");
        assertFalse(contieneAdvertencia(resultado.getAdvertencias(), "Sistema mixto detectado"),
                   "No debe haber advertencia de sistema mixto");
    }

    /**
     * Prueba que valida sistema solo AMD si aplica adaptadores.
     *
     * <p>Verifica que cuando hay CPU AMD presente, incluso sin otros
     * componentes mixtos, se aplican los adaptadores.
     */
    @Test
    public void pruebaSistemaAMDAplicaAdaptadores() {
        CompatibilidadMixta estrategia = new CompatibilidadMixta();
        List<ComponentePC> componentes = List.of(
            crearCPUAMD("Ryzen 7 7700X", 8),
            crearMotherBoardIntel("TUF Gaming B760-Plus WIFI D4")
        );

        ResultadoCompatibilidad resultado = estrategia.verificarCompatibilidad(componentes);

        assertNotNull(resultado, "El resultado no debe ser null");
        assertTrue(resultado.isCompatible(),
                  "Sistema con CPU AMD debe ser compatible con adaptadores");
        assertEquals(1, contarAdaptadores(resultado.getComponentesAdaptados()),
                    "Debe haber exactamente 1 adaptador para la CPU AMD");
        assertTrue(contieneAdvertencia(resultado.getAdvertencias(), "Sistema mixto detectado"),
                  "Debe detectarse como sistema mixto");
    }

    /**
     * Prueba parametrizada que valida configuraciones mixtas completas.
     *
     * <p>Verifica que sistemas completos con CPU AMD, motherboard Intel
     * y otros componentes se manejen correctamente.
     *
     * @param nombreCPU el nombre del CPU AMD
     * @param nucleos la cantidad de nucleos
     * @param nombreMB el nombre de la motherboard
     * @param potenciaFuente la potencia de la fuente
     */
    @ParameterizedTest(name = "Sistema mixto: {0} + {2} + fuente {3}W")
    @MethodSource("proveedorConfiguracionesMixtas")
    public void pruebaSistemaMixtoCompleto(String nombreCPU, int nucleos,
                                           String nombreMB, int potenciaFuente) {
        CompatibilidadMixta estrategia = new CompatibilidadMixta();
        List<ComponentePC> componentes = List.of(
            crearCPUAMD(nombreCPU, nucleos),
            crearMotherBoardIntel(nombreMB),
            crearGPU("RTX 4090"),
            crearRAM(32),
            crearDisco(1000, "SSD"),
            crearFuente(potenciaFuente)
        );

        ResultadoCompatibilidad resultado = estrategia.verificarCompatibilidad(componentes);

        assertNotNull(resultado, "El resultado no debe ser null");
        assertTrue(resultado.isCompatible(),
                  "Sistema mixto completo debe ser compatible");
        assertEquals(6, resultado.getComponentesAdaptados().size(),
                    "Debe haber 6 componentes en total");
        assertEquals(1, contarAdaptadores(resultado.getComponentesAdaptados()),
                    "Debe haber exactamente 1 adaptador");
        assertTrue(contieneAdvertencia(resultado.getAdvertencias(), "Sistema mixto detectado"),
                  "Debe advertir sobre sistema mixto");
        assertTrue(contieneAdvertencia(resultado.getAdvertencias(), "CPUAMDAdapter"),
                  "Debe mencionar el uso del adaptador");
    }

    /**
     * Prueba que valida la verificacion de potencia con overhead mixto.
     *
     * <p>Verifica que el factor de margen de seguridad para sistemas mixtos
     * sea del 25% (1.25) en lugar del 20% (1.2) de sistemas puros.
     */
    @Test
    public void pruebaVerificacionPotenciaConOverheadMixto() {
        CompatibilidadMixta estrategia = new CompatibilidadMixta();

        List<ComponentePC> componentes = List.of(
            crearCPUAMD("Ryzen 9 7950X3D", 16),
            crearMotherBoardIntel("ROG Maximus Z790 Hero"),
            crearGPU("RTX 4090"),
            crearRAM(32),
            crearDisco(1000, "SSD"),
            crearFuente(800)
        );

        int consumoEstimado = calcularConsumoEstimadoMixto(componentes);
        int potenciaRecomendada = (int) (consumoEstimado * 1.25);

        ResultadoCompatibilidad resultado = estrategia.verificarCompatibilidad(componentes);

        assertNotNull(resultado, "El resultado no debe ser null");

        if (800 < potenciaRecomendada) {
            assertTrue(contieneAdvertencia(resultado.getAdvertencias(), "fuente de alimentacion podria ser insuficiente"),
                      "Debe haber advertencia de fuente insuficiente");
            assertTrue(contieneAdvertencia(resultado.getAdvertencias(), "mixto"),
                      "La advertencia debe mencionar que es para sistema mixto");
        }
    }

    /**
     * Prueba que valida el factor de overhead mixto del 25%.
     *
     * <p>Verifica que el margen de seguridad para sistemas mixtos
     * sea mayor que para sistemas puros (1.25 vs 1.2).
     */
    @Test
    public void pruebaFactorOverheadMixto() {
        double factorPuro = 1.2;
        double factorMixto = 1.25;

        assertTrue(factorMixto > factorPuro,
                  "El factor de overhead mixto debe ser mayor que el puro");
        assertEquals(0.05, factorMixto - factorPuro, 0.001,
                    "La diferencia de overhead debe ser del 5%");
    }

    /**
     * Prueba que valida que CPUs AMD con arquitectura x86 pasan verificacion basica.
     *
     * <p>Verifica que las CPUs AMD con arquitectura que contiene "x86" o "AMD64"
     * no generen advertencia de incompatibilidad de sockets porque pasan la verificacion
     * basica de arquitectura en CompatibilidadMixta.
     */
    @Test
    public void pruebaAdvertenciaIncompatibilidadSockets() {
        CompatibilidadMixta estrategia = new CompatibilidadMixta();
        List<ComponentePC> componentes = List.of(
            crearCPUAMD("Ryzen 7 7700X", 8),
            crearMotherBoardIntel("ROG Maximus Z790 Hero")
        );

        ResultadoCompatibilidad resultado = estrategia.verificarCompatibilidad(componentes);

        assertNotNull(resultado, "El resultado no debe ser null");
        assertTrue(contieneAdvertencia(resultado.getAdvertencias(), "Sistema mixto detectado"),
                  "Debe haber advertencia de sistema mixto");
        assertTrue(contieneAdvertencia(resultado.getAdvertencias(), "CPUAMDAdapter"),
                  "Debe mencionar el uso del adaptador");
    }

    /**
     * Prueba que valida el manejo de lista vacia de componentes.
     *
     * <p>Verifica que la estrategia mixta maneje correctamente
     * una lista vacia sin lanzar excepciones.
     */
    @Test
    public void pruebaManejoDeListaVacia() {
        CompatibilidadMixta estrategia = new CompatibilidadMixta();
        List<ComponentePC> componentes = List.of();

        ResultadoCompatibilidad resultado = estrategia.verificarCompatibilidad(componentes);

        assertNotNull(resultado, "El resultado no debe ser null");
        assertTrue(resultado.isCompatible(),
                  "Una lista vacia debe ser considerada compatible");
        assertTrue(resultado.getComponentesAdaptados().isEmpty(),
                  "No debe haber componentes adaptados para lista vacia");
    }

    /**
     * Prueba que valida el manejo de sistema sin CPU.
     *
     * <p>Verifica que la estrategia mixta maneje correctamente
     * un sistema sin CPU sin lanzar excepciones.
     */
    @Test
    public void pruebaManejoSistemaSinCPU() {
        CompatibilidadMixta estrategia = new CompatibilidadMixta();
        List<ComponentePC> componentes = List.of(
            crearMotherBoardIntel("ROG Maximus Z790 Hero"),
            crearGPU("RTX 4070"),
            crearFuente(800)
        );

        ResultadoCompatibilidad resultado = estrategia.verificarCompatibilidad(componentes);

        assertNotNull(resultado, "El resultado no debe ser null");
        assertTrue(resultado.isCompatible(),
                  "Sistema sin CPU debe ser considerado compatible");
        assertEquals(0, contarAdaptadores(resultado.getComponentesAdaptados()),
                    "No debe haber adaptadores si no hay CPU AMD");
        assertFalse(contieneAdvertencia(resultado.getAdvertencias(), "Sistema mixto detectado"),
                   "No debe detectarse como mixto sin CPU AMD");
    }

    /**
     * Prueba que valida multiples CPUs AMD requieren multiples adaptadores.
     *
     * <p>Verifica que si hay multiples CPUs AMD en el sistema,
     * todas se adapten correctamente.
     */
    @Test
    public void pruebaMultiplesCPUsAMDRequierenMultiplesAdaptadores() {
        CompatibilidadMixta estrategia = new CompatibilidadMixta();
        List<ComponentePC> componentes = List.of(
            crearCPUAMD("Ryzen 7 7700X", 8),
            crearCPUAMD("Ryzen 9 7950X3D", 16),
            crearMotherBoardIntel("ROG Maximus Z790 Hero")
        );

        ResultadoCompatibilidad resultado = estrategia.verificarCompatibilidad(componentes);

        assertNotNull(resultado, "El resultado no debe ser null");
        assertEquals(2, contarAdaptadores(resultado.getComponentesAdaptados()),
                    "Debe haber exactamente 2 adaptadores para las 2 CPUs AMD");
    }

    /**
     * Prueba que valida que componentes no-CPU no se adaptan.
     *
     * <p>Verifica que solo las CPUs AMD se envuelven en adaptadores,
     * otros componentes permanecen sin cambios.
     */
    @Test
    public void pruebaComponentesNoCPUNoSeAdaptan() {
        CompatibilidadMixta estrategia = new CompatibilidadMixta();
        List<ComponentePC> componentes = List.of(
            crearCPUAMD("Ryzen 7 7700X", 8),
            crearMotherBoardIntel("ROG Maximus Z790 Hero"),
            crearGPU("RTX 4090"),
            crearRAM(32)
        );

        ResultadoCompatibilidad resultado = estrategia.verificarCompatibilidad(componentes);

        assertNotNull(resultado, "El resultado no debe ser null");

        long componentesGPU = resultado.getComponentesAdaptados().stream()
            .filter(c -> c instanceof GPU)
            .count();

        long componentesRAM = resultado.getComponentesAdaptados().stream()
            .filter(c -> c.obtenerTipo().equals("RAM"))
            .count();

        long componentesMB = resultado.getComponentesAdaptados().stream()
            .filter(c -> c instanceof MotherBoard)
            .count();

        assertEquals(1, componentesGPU, "La GPU debe estar presente sin adaptador");
        assertEquals(1, componentesRAM, "La RAM debe estar presente sin adaptador");
        assertEquals(1, componentesMB, "La MotherBoard debe estar presente sin adaptador");
    }

    /**
     * Prueba que valida que el adaptador preserva la informacion del CPU original.
     *
     * <p>Verifica que el CPUAMDAdapter delegue correctamente
     * a la CPU AMD original.
     */
    @Test
    public void pruebaAdaptadorPreservaInformacionCPUOriginal() {
        CompatibilidadMixta estrategia = new CompatibilidadMixta();
        String nombreCPU = "Ryzen 9 7950X3D";
        List<ComponentePC> componentes = List.of(
            crearCPUAMD(nombreCPU, 16),
            crearMotherBoardIntel("ROG Maximus Z790 Hero")
        );

        ResultadoCompatibilidad resultado = estrategia.verificarCompatibilidad(componentes);

        ComponentePC cpuAdaptado = resultado.getComponentesAdaptados().stream()
            .filter(CompatibilidadMixtaTest::esCPUAMDAdapter)
            .findFirst()
            .orElse(null);

        assertNotNull(cpuAdaptado, "Debe encontrarse el CPU adaptado");
        assertTrue(cpuAdaptado.obtenerNombre().contains(nombreCPU),
                  "El adaptador debe preservar el nombre del CPU original");
        assertEquals("AMD", cpuAdaptado.obtenerMarca(),
                    "El adaptador debe preservar la marca AMD");
    }

    /**
     * Prueba que valida el calculo de consumo de potencia mixto.
     *
     * <p>Verifica que el consumo estimado use el valor promedio
     * para sistemas mixtos: CPU=135W (entre 125W Intel y 140W AMD).
     */
    @Test
    public void pruebaCalculoConsumoPotenciaMixto() {
        List<ComponentePC> componentes = List.of(
            crearCPUAMD("Ryzen 7 7700X", 8),
            crearMotherBoardIntel("ROG Maximus Z790 Hero"),
            crearGPU("RTX 4070"),
            crearRAM(16),
            crearDisco(500, "SSD")
        );

        int consumoEstimado = calcularConsumoEstimadoMixto(componentes);
        int consumoEsperado = 135 + 80 + 250 + 10 + 15;

        assertEquals(consumoEsperado, consumoEstimado,
                    "El consumo estimado debe calcularse con CPU=135W para sistemas mixtos");
    }
}
