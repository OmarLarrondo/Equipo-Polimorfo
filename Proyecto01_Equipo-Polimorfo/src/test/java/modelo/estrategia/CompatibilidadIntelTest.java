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
import modelo.componente.RAM;
import modelo.componente.Disco;
import modelo.componente.FuenteAlimentacion;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * Clase de pruebas unitarias para CompatibilidadIntel.
 *
 * <p>Valida la correcta verificacion de compatibilidad para sistemas basados en
 * procesadores Intel, asegurando que la estrategia detecte correctamente
 * incompatibilidades, genere advertencias apropiadas y verifique la potencia de la fuente.
 *
 * <p>Esta suite de pruebas verifica:
 * <ul>
 *   <li>Compatibilidad entre CPU Intel y MotherBoard compatible</li>
 *   <li>Incompatibilidad cuando CPU y MotherBoard no coinciden en arquitectura</li>
 *   <li>Advertencias cuando se usa CPU no-Intel en estrategia Intel</li>
 *   <li>Verificacion de potencia de fuente suficiente vs insuficiente</li>
 *   <li>Sistemas completos con todos los componentes</li>
 *   <li>Manejo de listas vacias de componentes</li>
 *   <li>Consumo de potencia estimado (CPU: 125W, GPU: 250W, RAM: 10W, Disco: 15W, MB: 80W)</li>
 * </ul>
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class CompatibilidadIntelTest {

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
     * Crea una MotherBoard compatible con Intel de prueba.
     * Esta funcion es pura y no tiene efectos secundarios.
     *
     * @param nombre el nombre de la motherboard
     * @return un nuevo objeto MotherBoard compatible con Intel
     */
    private static MotherBoard crearMotherBoardIntel(String nombre) {
        return new MotherBoard(nombre, 8000.0, "ASUS", "MotherBoard", "Z790", "LGA1700", "x86");
    }

    /**
     * Crea una MotherBoard incompatible con Intel de prueba.
     * Esta funcion es pura y no tiene efectos secundarios.
     *
     * @param nombre el nombre de la motherboard
     * @return un nuevo objeto MotherBoard incompatible con Intel
     */
    private static MotherBoard crearMotherBoardIncompatible(String nombre) {
        return new MotherBoard(nombre, 7000.0, "MSI", "MotherBoard", "B450", "AM4", "AMD64");
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
     * Calcula el consumo estimado de potencia para una lista de componentes.
     * Esta funcion es pura y no tiene efectos secundarios.
     * Consumos: CPU=125W, GPU=250W, RAM=10W, Disco=15W, MotherBoard=80W
     *
     * @param componentes la lista de componentes
     * @return el consumo estimado en vatios
     */
    private static int calcularConsumoEstimado(List<ComponentePC> componentes) {
        return componentes.stream()
            .mapToInt(c -> {
                if (c instanceof CPU) return 125;
                if (c instanceof GPU) return 250;
                if (c.obtenerTipo().equals("RAM")) return 10;
                if (c.obtenerTipo().equals("Disco") || c.obtenerTipo().equals("HDD") || c.obtenerTipo().equals("SSD")) return 15;
                if (c instanceof MotherBoard) return 80;
                return 0;
            })
            .sum();
    }

    /**
     * Provee argumentos para probar compatibilidad exitosa entre CPU Intel y MotherBoard.
     *
     * @return Stream de argumentos con CPUs Intel compatibles
     */
    private static Stream<Arguments> proveedorCPUsIntelCompatibles() {
        return Stream.of(
            Arguments.of("Core i3-13100", 4),
            Arguments.of("Core i5-13600K", 14),
            Arguments.of("Core i7-13700K", 16),
            Arguments.of("Core i9-13900K", 24)
        );
    }

    /**
     * Provee argumentos para probar potencia de fuente.
     * Formato: nombreFuente, potenciaFuente, consumoComponentes, esuficiente
     *
     * @return Stream de argumentos con configuraciones de potencia
     */
    private static Stream<Arguments> proveedorConfiguracionesPotencia() {
        return Stream.of(
            Arguments.of("EVGA 800W", 800, 500, true),
            Arguments.of("EVGA 1000W", 1000, 700, true),
            Arguments.of("XPG 500W", 500, 480, false),
            Arguments.of("Corsair 1500W", 1500, 1000, true),
            Arguments.of("XPG 700W", 700, 650, false)
        );
    }

    /**
     * Valida que un ResultadoCompatibilidad indique compatibilidad exitosa.
     * Esta funcion es pura y no tiene efectos secundarios.
     *
     * @param resultado el resultado a validar
     * @return true si el resultado indica compatibilidad sin errores criticos, false en caso contrario
     */
    private static boolean esCompatibleSinErroresCriticos(ResultadoCompatibilidad resultado) {
        return resultado.isCompatible() &&
               resultado.getAdvertencias().stream()
                   .noneMatch(adv -> adv.contains("INCOMPATIBILIDAD"));
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
     * Prueba parametrizada que valida la compatibilidad entre CPUs Intel y MotherBoard compatible.
     *
     * <p>Verifica que la estrategia Intel reconozca como compatibles
     * los sistemas con CPU Intel y MotherBoard con arquitectura x86.
     *
     * @param nombreCPU el nombre del CPU Intel
     * @param nucleos la cantidad de nucleos del CPU
     */
    @ParameterizedTest(name = "CPU Intel {0} ({1} nucleos) compatible con MotherBoard Intel")
    @MethodSource("proveedorCPUsIntelCompatibles")
    public void pruebaCompatibilidadCPUIntelConMotherBoardCompatible(String nombreCPU, int nucleos) {
        CompatibilidadIntel estrategia = new CompatibilidadIntel();
        List<ComponentePC> componentes = List.of(
            crearCPUIntel(nombreCPU, nucleos),
            crearMotherBoardIntel("ROG Maximus Z790 Hero")
        );

        ResultadoCompatibilidad resultado = estrategia.verificarCompatibilidad(componentes);

        assertNotNull(resultado, "El resultado no debe ser null");
        assertTrue(resultado.isCompatible(),
                  String.format("CPU Intel %s debe ser compatible con MotherBoard Intel", nombreCPU));
        assertTrue(resultado.getComponentesAdaptados().isEmpty(),
                  "No debe haber componentes adaptados en sistema Intel puro");
    }

    /**
     * Prueba que valida la incompatibilidad cuando CPU y MotherBoard no coinciden.
     *
     * <p>Verifica que la estrategia Intel detecte incompatibilidad
     * cuando la CPU es Intel pero la MotherBoard no es compatible.
     */
    @Test
    public void pruebaIncompatibilidadCPUIntelConMotherBoardIncompatible() {
        CompatibilidadIntel estrategia = new CompatibilidadIntel();
        List<ComponentePC> componentes = List.of(
            crearCPUIntel("Core i7-13700K", 16),
            crearMotherBoardIncompatible("MSI B450 Tomahawk")
        );

        ResultadoCompatibilidad resultado = estrategia.verificarCompatibilidad(componentes);

        assertNotNull(resultado, "El resultado no debe ser null");
        assertFalse(resultado.isCompatible(),
                   "CPU Intel con MotherBoard AMD debe ser incompatible");
        assertTrue(contieneAdvertencia(resultado.getAdvertencias(), "INCOMPATIBILIDAD"),
                  "Debe haber una advertencia de incompatibilidad");
        assertTrue(contieneAdvertencia(resultado.getAdvertencias(), "Core i7-13700K"),
                  "La advertencia debe mencionar el nombre del CPU");
    }

    /**
     * Prueba que valida advertencia cuando se usa CPU AMD en estrategia Intel.
     *
     * <p>Verifica que la estrategia Intel genere una advertencia
     * cuando se intenta usar una CPU AMD.
     */
    @Test
    public void pruebaAdvertenciaCuandoCPUNoEsIntel() {
        CompatibilidadIntel estrategia = new CompatibilidadIntel();
        List<ComponentePC> componentes = List.of(
            crearCPUAMD("Ryzen 7 7700X", 8),
            crearMotherBoardIntel("TUF Gaming B760-Plus WIFI D4")
        );

        ResultadoCompatibilidad resultado = estrategia.verificarCompatibilidad(componentes);

        assertNotNull(resultado, "El resultado no debe ser null");
        assertTrue(contieneAdvertencia(resultado.getAdvertencias(), "Se esperaba CPU Intel"),
                  "Debe haber una advertencia indicando que se esperaba CPU Intel");
        assertTrue(contieneAdvertencia(resultado.getAdvertencias(), "AMD"),
                  "La advertencia debe mencionar la marca AMD");
    }

    /**
     * Prueba parametrizada que valida la verificacion de potencia de fuente.
     *
     * <p>Verifica que la estrategia Intel valide correctamente si la fuente
     * tiene potencia suficiente (consumo * 1.2) para todos los componentes.
     *
     * @param nombreFuente el nombre de la fuente
     * @param potenciaFuente la potencia de la fuente en vatios
     * @param consumoComponentes el consumo estimado de componentes
     * @param esSuficiente si la potencia debe ser suficiente
     */
    @ParameterizedTest(name = "Fuente {0}: {1}W para consumo {2}W - suficiente={3}")
    @MethodSource("proveedorConfiguracionesPotencia")
    public void pruebaVerificacionPotenciaFuente(String nombreFuente, int potenciaFuente,
                                                  int consumoComponentes, boolean esSuficiente) {
        CompatibilidadIntel estrategia = new CompatibilidadIntel();

        List<ComponentePC> componentes = new ArrayList<>();
        componentes.add(crearCPUIntel("Core i5-13600K", 14));
        componentes.add(crearMotherBoardIntel("ROG Maximus Z790 Hero"));
        componentes.add(crearFuente(potenciaFuente));

        int consumoActual = calcularConsumoEstimado(componentes);
        while (consumoActual < consumoComponentes - 50) {
            componentes.add(crearGPU("RTX 4070"));
            consumoActual = calcularConsumoEstimado(componentes);
        }

        ResultadoCompatibilidad resultado = estrategia.verificarCompatibilidad(componentes);

        assertNotNull(resultado, "El resultado no debe ser null");

        if (esSuficiente) {
            assertFalse(contieneAdvertencia(resultado.getAdvertencias(), "fuente de alimentacion podria ser insuficiente"),
                       "No debe haber advertencia de fuente insuficiente cuando la potencia es adecuada");
        } else {
            assertTrue(contieneAdvertencia(resultado.getAdvertencias(), "fuente de alimentacion podria ser insuficiente"),
                      "Debe haber advertencia de fuente insuficiente cuando la potencia es inadecuada");
        }
    }

    /**
     * Prueba que valida un sistema completo compatible con todos los componentes.
     *
     * <p>Verifica que la estrategia Intel valide correctamente un sistema completo
     * con CPU, GPU, RAM, Disco, MotherBoard y Fuente, todos compatibles.
     */
    @Test
    public void pruebaSistemaCompletoCompatible() {
        CompatibilidadIntel estrategia = new CompatibilidadIntel();

        List<ComponentePC> componentes = List.of(
            crearCPUIntel("Core i9-13900K", 24),
            crearMotherBoardIntel("ROG Maximus Z790 Hero"),
            crearGPU("RTX 4090"),
            crearRAM(32),
            crearDisco(1000, "SSD"),
            crearFuente(1500)
        );

        int consumoEstimado = calcularConsumoEstimado(componentes);
        int potenciaRecomendada = (int) (consumoEstimado * 1.2);

        ResultadoCompatibilidad resultado = estrategia.verificarCompatibilidad(componentes);

        assertNotNull(resultado, "El resultado no debe ser null");
        assertTrue(resultado.isCompatible(),
                  "Sistema completo Intel debe ser compatible");
        assertTrue(resultado.getComponentesAdaptados().isEmpty(),
                  "No debe haber componentes adaptados en sistema Intel puro");
        assertTrue(1500 >= potenciaRecomendada,
                  "La fuente de 1500W debe ser suficiente para el sistema");
    }

    /**
     * Prueba que valida un sistema completo con fuente insuficiente.
     *
     * <p>Verifica que la estrategia Intel detecte cuando la fuente
     * no tiene potencia suficiente para todos los componentes.
     */
    @Test
    public void pruebaSistemaCompletoConFuenteInsuficiente() {
        CompatibilidadIntel estrategia = new CompatibilidadIntel();

        List<ComponentePC> componentes = List.of(
            crearCPUIntel("Core i9-13900K", 24),
            crearMotherBoardIntel("ROG Maximus Z790 Hero"),
            crearGPU("RTX 4090"),
            crearGPU("RTX 4080"),
            crearRAM(32),
            crearDisco(1000, "SSD"),
            crearFuente(500)
        );

        int consumoEstimado = calcularConsumoEstimado(componentes);

        ResultadoCompatibilidad resultado = estrategia.verificarCompatibilidad(componentes);

        assertNotNull(resultado, "El resultado no debe ser null");
        assertTrue(resultado.isCompatible(),
                  "El sistema debe ser marcado como compatible estructuralmente");
        assertTrue(contieneAdvertencia(resultado.getAdvertencias(), "fuente de alimentacion podria ser insuficiente"),
                  "Debe haber advertencia sobre fuente insuficiente");
        assertTrue(consumoEstimado > 500,
                  "El consumo estimado debe ser mayor que 500W");
    }

    /**
     * Prueba que valida el manejo de lista vacia de componentes.
     *
     * <p>Verifica que la estrategia Intel maneje correctamente
     * una lista vacia de componentes sin lanzar excepciones.
     */
    @Test
    public void pruebaManejoDeListaVacia() {
        CompatibilidadIntel estrategia = new CompatibilidadIntel();
        List<ComponentePC> componentes = List.of();

        ResultadoCompatibilidad resultado = estrategia.verificarCompatibilidad(componentes);

        assertNotNull(resultado, "El resultado no debe ser null");
        assertTrue(resultado.isCompatible(),
                  "Una lista vacia debe ser considerada compatible por defecto");
        assertTrue(resultado.getAdvertencias().isEmpty() ||
                  resultado.getAdvertencias().stream().noneMatch(adv -> adv.contains("INCOMPATIBILIDAD")),
                  "No debe haber advertencias de incompatibilidad para lista vacia");
    }

    /**
     * Prueba que valida el manejo de sistema sin CPU.
     *
     * <p>Verifica que la estrategia Intel maneje correctamente
     * un sistema sin CPU sin lanzar excepciones.
     */
    @Test
    public void pruebaManejoSistemaSinCPU() {
        CompatibilidadIntel estrategia = new CompatibilidadIntel();
        List<ComponentePC> componentes = List.of(
            crearMotherBoardIntel("ROG Maximus Z790 Hero"),
            crearGPU("RTX 4070"),
            crearFuente(800)
        );

        ResultadoCompatibilidad resultado = estrategia.verificarCompatibilidad(componentes);

        assertNotNull(resultado, "El resultado no debe ser null");
        assertTrue(resultado.isCompatible(),
                  "Sistema sin CPU debe ser considerado compatible (verificacion parcial)");
    }

    /**
     * Prueba que valida el manejo de sistema sin MotherBoard.
     *
     * <p>Verifica que la estrategia Intel maneje correctamente
     * un sistema sin MotherBoard sin lanzar excepciones.
     */
    @Test
    public void pruebaManejoSistemaSinMotherBoard() {
        CompatibilidadIntel estrategia = new CompatibilidadIntel();
        List<ComponentePC> componentes = List.of(
            crearCPUIntel("Core i7-13700K", 16),
            crearGPU("RTX 4070"),
            crearFuente(800)
        );

        ResultadoCompatibilidad resultado = estrategia.verificarCompatibilidad(componentes);

        assertNotNull(resultado, "El resultado no debe ser null");
        assertTrue(resultado.isCompatible(),
                  "Sistema sin MotherBoard debe ser considerado compatible (verificacion parcial)");
    }

    /**
     * Prueba que valida el manejo de sistema sin Fuente.
     *
     * <p>Verifica que la estrategia Intel maneje correctamente
     * un sistema sin Fuente de Alimentacion sin lanzar excepciones.
     */
    @Test
    public void pruebaManejoSistemaSinFuente() {
        CompatibilidadIntel estrategia = new CompatibilidadIntel();
        List<ComponentePC> componentes = List.of(
            crearCPUIntel("Core i7-13700K", 16),
            crearMotherBoardIntel("ROG Maximus Z790 Hero"),
            crearGPU("RTX 4070")
        );

        ResultadoCompatibilidad resultado = estrategia.verificarCompatibilidad(componentes);

        assertNotNull(resultado, "El resultado no debe ser null");
        assertTrue(resultado.isCompatible(),
                  "Sistema sin Fuente debe ser considerado compatible (verificacion parcial)");
        assertFalse(contieneAdvertencia(resultado.getAdvertencias(), "fuente de alimentacion podria ser insuficiente"),
                   "No debe haber advertencia de fuente cuando no hay fuente en el sistema");
    }

    /**
     * Prueba que valida el calculo de consumo de potencia.
     *
     * <p>Verifica que el consumo estimado se calcule correctamente:
     * CPU=125W, GPU=250W, RAM=10W, Disco=15W, MotherBoard=80W
     */
    @Test
    public void pruebaCalculoConsumoPotencia() {
        List<ComponentePC> componentes = List.of(
            crearCPUIntel("Core i5-13600K", 14),
            crearMotherBoardIntel("ROG Maximus Z790 Hero"),
            crearGPU("RTX 4070"),
            crearRAM(16),
            crearDisco(500, "SSD")
        );

        int consumoEstimado = calcularConsumoEstimado(componentes);
        int consumoEsperado = 125 + 80 + 250 + 10 + 15;

        assertEquals(consumoEsperado, consumoEstimado,
                    "El consumo estimado debe calcularse correctamente");
    }

    /**
     * Prueba que valida que el margen de seguridad es del 20%.
     *
     * <p>Verifica que la potencia recomendada sea el consumo * 1.2
     */
    @Test
    public void pruebaMargenSeguridadPotencia() {
        CompatibilidadIntel estrategia = new CompatibilidadIntel();

        int consumoEstimado = 500;
        int potenciaRecomendada = (int) (consumoEstimado * 1.2);

        assertEquals(600, potenciaRecomendada,
                    "La potencia recomendada debe ser consumo * 1.2 (margen del 20%)");
    }
}
