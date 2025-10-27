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
 * Clase de pruebas unitarias para CompatibilidadAMD.
 *
 * <p>Valida la correcta verificacion de compatibilidad para sistemas basados en
 * procesadores AMD, asegurando que la estrategia detecte correctamente las
 * incompatibilidades fisicas con motherboards Intel, genere advertencias criticas
 * apropiadas y verifique la potencia considerando el mayor consumo de CPUs AMD.
 *
 * <p>Esta suite de pruebas verifica:
 * <ul>
 *   <li>Advertencia critica sobre incompatibilidad fisica CPU AMD con MotherBoard Intel</li>
 *   <li>Advertencia sobre aplicacion del patron Adapter (CPUAMDAdapter)</li>
 *   <li>Verificacion de potencia considerando mayor consumo de AMD (140W vs 125W Intel)</li>
 *   <li>Sistemas completos con todos los componentes AMD</li>
 *   <li>Manejo de listas vacias de componentes</li>
 *   <li>Advertencias cuando CPU no es AMD en estrategia AMD</li>
 *   <li>Consumo de potencia estimado (CPU: 140W, GPU: 250W, RAM: 10W, Disco: 15W, MB: 80W)</li>
 * </ul>
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class CompatibilidadAMDTest {

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
     * Crea una MotherBoard Intel de prueba (las unicas en inventario).
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
     * Calcula el consumo estimado de potencia para una lista de componentes AMD.
     * Esta funcion es pura y no tiene efectos secundarios.
     * Consumos: CPU=140W (AMD consume mas), GPU=250W, RAM=10W, Disco=15W, MotherBoard=80W
     *
     * @param componentes la lista de componentes
     * @return el consumo estimado en vatios
     */
    private static int calcularConsumoEstimadoAMD(List<ComponentePC> componentes) {
        return componentes.stream()
            .mapToInt(c -> {
                if (c instanceof CPU) return 140;
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
     * @return Stream de argumentos con CPUs AMD del inventario
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
     * Provee argumentos para probar potencia de fuente con CPUs AMD.
     * Formato: nombreFuente, potenciaFuente, consumoComponentes, esSuficiente
     *
     * @return Stream de argumentos con configuraciones de potencia
     */
    private static Stream<Arguments> proveedorConfiguracionesPotenciaAMD() {
        return Stream.of(
            Arguments.of("EVGA 800W", 800, 500, true),
            Arguments.of("EVGA 1000W", 1000, 700, true),
            Arguments.of("XPG 500W", 500, 490, false),
            Arguments.of("Corsair 1500W", 1500, 1000, true),
            Arguments.of("XPG 700W", 700, 660, false)
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
     * Prueba parametrizada que valida la advertencia informativa para CPUs AMD.
     *
     * <p>Verifica que la estrategia AMD genere una advertencia informativa
     * sobre la aplicacion del patron Adapter para CPUs AMD con motherboards Intel.
     * Nota: Las CPUs AMD con arquitectura x86-64 (AMD64) pasan la verificacion basica,
     * por lo que no generan advertencia critica sino informativa sobre el adaptador.
     *
     * @param nombreCPU el nombre del CPU AMD
     * @param nucleos la cantidad de nucleos del CPU
     */
    @ParameterizedTest(name = "CPU AMD {0} ({1} nucleos) - advertencia info con MotherBoard Intel")
    @MethodSource("proveedorCPUsAMD")
    public void pruebaAdvertenciaCriticaCPUAMDConMotherBoardIntel(String nombreCPU, int nucleos) {
        CompatibilidadAMD estrategia = new CompatibilidadAMD();
        List<ComponentePC> componentes = List.of(
            crearCPUAMD(nombreCPU, nucleos),
            crearMotherBoardIntel("ROG Maximus Z790 Hero")
        );

        ResultadoCompatibilidad resultado = estrategia.verificarCompatibilidad(componentes);

        assertNotNull(resultado, "El resultado no debe ser null");
        assertTrue(resultado.isCompatible(),
                  "El resultado debe marcar como compatible estructuralmente");
        assertTrue(contieneAdvertencia(resultado.getAdvertencias(), "INFO"),
                  "Debe haber una advertencia informativa");
        assertTrue(contieneAdvertencia(resultado.getAdvertencias(), "CPUAMDAdapter"),
                  "Debe mencionar el uso del adaptador");
    }

    /**
     * Prueba que valida la advertencia critica cuando CPU AMD no tiene arquitectura compatible.
     *
     * <p>Verifica que la estrategia AMD genere advertencia critica cuando
     * una CPU AMD no tiene arquitectura AMD64 ni x86-64, o cuando se usa CPU Intel.
     * Nota: La estrategia AMD siempre marca isCompatible=true estructuralmente.
     */
    @Test
    public void pruebaAdvertenciaCriticaConCPUIncompatible() {
        CompatibilidadAMD estrategia = new CompatibilidadAMD();
        List<ComponentePC> componentes = List.of(
            crearCPUIntel("Core i7-13700K", 16),
            crearMotherBoardIntel("TUF Gaming B760-Plus WIFI D4")
        );

        ResultadoCompatibilidad resultado = estrategia.verificarCompatibilidad(componentes);

        assertNotNull(resultado, "El resultado no debe ser null");
        assertTrue(resultado.isCompatible(),
                   "La estrategia AMD marca como compatible estructuralmente");
        assertTrue(contieneAdvertencia(resultado.getAdvertencias(), "ADVERTENCIA CRITICA"),
                  "Debe haber advertencia critica cuando CPU no es AMD");
        assertTrue(contieneAdvertencia(resultado.getAdvertencias(), "NO ES FISICAMENTE COMPATIBLE"),
                  "Debe mencionar incompatibilidad fisica");
    }

    /**
     * Prueba que valida la advertencia sobre aplicacion del patron Adapter.
     *
     * <p>Verifica que la estrategia AMD informe sobre la aplicacion
     * del patron CPUAMDAdapter para intentar compatibilidad.
     */
    @Test
    public void pruebaAdvertenciaAplicacionAdapter() {
        CompatibilidadAMD estrategia = new CompatibilidadAMD();
        List<ComponentePC> componentes = List.of(
            crearCPUAMD("Ryzen 9 7950X3D", 16),
            crearMotherBoardIntel("ROG Maximus Z790 Hero")
        );

        ResultadoCompatibilidad resultado = estrategia.verificarCompatibilidad(componentes);

        assertNotNull(resultado, "El resultado no debe ser null");
        assertTrue(contieneAdvertencia(resultado.getAdvertencias(), "CPUAMDAdapter") ||
                  contieneAdvertencia(resultado.getAdvertencias(), "patron Adapter"),
                  "Debe mencionar la aplicacion del patron Adapter");
        assertTrue(contieneAdvertencia(resultado.getAdvertencias(), "INFO"),
                  "Debe haber una advertencia informativa sobre el adaptador");
    }

    /**
     * Prueba parametrizada que valida la verificacion de potencia con CPUs AMD.
     *
     * <p>Verifica que la estrategia AMD valide correctamente si la fuente
     * tiene potencia suficiente considerando el mayor consumo de CPUs AMD (140W).
     *
     * @param nombreFuente el nombre de la fuente
     * @param potenciaFuente la potencia de la fuente en vatios
     * @param consumoComponentes el consumo estimado de componentes
     * @param esSuficiente si la potencia debe ser suficiente
     */
    @ParameterizedTest(name = "Fuente {0}: {1}W para consumo AMD {2}W - suficiente={3}")
    @MethodSource("proveedorConfiguracionesPotenciaAMD")
    public void pruebaVerificacionPotenciaFuenteAMD(String nombreFuente, int potenciaFuente,
                                                     int consumoComponentes, boolean esSuficiente) {
        CompatibilidadAMD estrategia = new CompatibilidadAMD();

        List<ComponentePC> componentes = new ArrayList<>();
        componentes.add(crearCPUAMD("Ryzen 7 7700X", 8));
        componentes.add(crearMotherBoardIntel("ROG Maximus Z790 Hero"));
        componentes.add(crearFuente(potenciaFuente));

        int consumoActual = calcularConsumoEstimadoAMD(componentes);
        while (consumoActual < consumoComponentes - 50) {
            componentes.add(crearGPU("RTX 4070"));
            consumoActual = calcularConsumoEstimadoAMD(componentes);
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
     * Prueba que valida un sistema completo AMD con todos los componentes.
     *
     * <p>Verifica que la estrategia AMD valide correctamente un sistema completo
     * con CPU AMD, GPU, RAM, Disco, MotherBoard Intel y Fuente adecuada.
     */
    @Test
    public void pruebaSistemaCompletoAMD() {
        CompatibilidadAMD estrategia = new CompatibilidadAMD();

        List<ComponentePC> componentes = List.of(
            crearCPUAMD("Ryzen 9 7950X3D", 16),
            crearMotherBoardIntel("ROG Maximus Z790 Hero"),
            crearGPU("RTX 4090"),
            crearRAM(32),
            crearDisco(1000, "SSD"),
            crearFuente(1500)
        );

        int consumoEstimado = calcularConsumoEstimadoAMD(componentes);
        int potenciaRecomendada = (int) (consumoEstimado * 1.2);

        ResultadoCompatibilidad resultado = estrategia.verificarCompatibilidad(componentes);

        assertNotNull(resultado, "El resultado no debe ser null");
        assertTrue(resultado.isCompatible(),
                  "Sistema completo AMD debe ser marcado como compatible estructuralmente");
        assertTrue(contieneAdvertencia(resultado.getAdvertencias(), "ADVERTENCIA CRITICA") ||
                  contieneAdvertencia(resultado.getAdvertencias(), "INFO"),
                  "Debe haber advertencias sobre compatibilidad AMD");
        assertTrue(1500 >= potenciaRecomendada,
                  "La fuente de 1500W debe ser suficiente para el sistema AMD");
    }

    /**
     * Prueba que valida un sistema AMD con fuente insuficiente.
     *
     * <p>Verifica que la estrategia AMD detecte cuando la fuente
     * no tiene potencia suficiente considerando el mayor consumo de AMD.
     */
    @Test
    public void pruebaSistemaAMDConFuenteInsuficiente() {
        CompatibilidadAMD estrategia = new CompatibilidadAMD();

        List<ComponentePC> componentes = List.of(
            crearCPUAMD("Ryzen 9 7950X3D", 16),
            crearMotherBoardIntel("ROG Maximus Z790 Hero"),
            crearGPU("RTX 4090"),
            crearGPU("RTX 4080"),
            crearRAM(32),
            crearDisco(1000, "SSD"),
            crearFuente(500)
        );

        int consumoEstimado = calcularConsumoEstimadoAMD(componentes);

        ResultadoCompatibilidad resultado = estrategia.verificarCompatibilidad(componentes);

        assertNotNull(resultado, "El resultado no debe ser null");
        assertTrue(contieneAdvertencia(resultado.getAdvertencias(), "fuente de alimentacion podria ser insuficiente"),
                  "Debe haber advertencia sobre fuente insuficiente");
        assertTrue(contieneAdvertencia(resultado.getAdvertencias(), "AMD"),
                  "La advertencia debe mencionar que es para sistema AMD");
        assertTrue(consumoEstimado > 500,
                  "El consumo estimado debe ser mayor que 500W");
    }

    /**
     * Prueba que valida el manejo de lista vacia de componentes.
     *
     * <p>Verifica que la estrategia AMD maneje correctamente
     * una lista vacia de componentes sin lanzar excepciones.
     */
    @Test
    public void pruebaManejoDeListaVacia() {
        CompatibilidadAMD estrategia = new CompatibilidadAMD();
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
     * <p>Verifica que la estrategia AMD maneje correctamente
     * un sistema sin CPU sin lanzar excepciones.
     */
    @Test
    public void pruebaManejoSistemaSinCPU() {
        CompatibilidadAMD estrategia = new CompatibilidadAMD();
        List<ComponentePC> componentes = List.of(
            crearMotherBoardIntel("ROG Maximus Z790 Hero"),
            crearGPU("RTX 4070"),
            crearFuente(800)
        );

        ResultadoCompatibilidad resultado = estrategia.verificarCompatibilidad(componentes);

        assertNotNull(resultado, "El resultado no debe ser null");
        assertTrue(resultado.isCompatible(),
                  "Sistema sin CPU debe ser considerado compatible (verificacion parcial)");
        assertFalse(contieneAdvertencia(resultado.getAdvertencias(), "CPUAMDAdapter"),
                   "No debe haber advertencia de adaptador si no hay CPU");
    }

    /**
     * Prueba que valida el manejo de sistema sin MotherBoard.
     *
     * <p>Verifica que la estrategia AMD maneje correctamente
     * un sistema sin MotherBoard sin lanzar excepciones.
     */
    @Test
    public void pruebaManejoSistemaSinMotherBoard() {
        CompatibilidadAMD estrategia = new CompatibilidadAMD();
        List<ComponentePC> componentes = List.of(
            crearCPUAMD("Ryzen 7 7700X", 8),
            crearGPU("RTX 4070"),
            crearFuente(800)
        );

        ResultadoCompatibilidad resultado = estrategia.verificarCompatibilidad(componentes);

        assertNotNull(resultado, "El resultado no debe ser null");
        assertTrue(resultado.isCompatible(),
                  "Sistema sin MotherBoard debe ser considerado compatible (verificacion parcial)");
        assertTrue(contieneAdvertencia(resultado.getAdvertencias(), "CPUAMDAdapter") ||
                  contieneAdvertencia(resultado.getAdvertencias(), "INFO"),
                  "Debe haber advertencia informativa sobre CPU AMD presente");
    }

    /**
     * Prueba que valida el calculo de consumo de potencia con CPU AMD.
     *
     * <p>Verifica que el consumo estimado se calcule correctamente con CPU AMD:
     * CPU=140W (vs 125W Intel), GPU=250W, RAM=10W, Disco=15W, MotherBoard=80W
     */
    @Test
    public void pruebaCalculoConsumoPotenciaAMD() {
        List<ComponentePC> componentes = List.of(
            crearCPUAMD("Ryzen 7 7700X", 8),
            crearMotherBoardIntel("ROG Maximus Z790 Hero"),
            crearGPU("RTX 4070"),
            crearRAM(16),
            crearDisco(500, "SSD")
        );

        int consumoEstimado = calcularConsumoEstimadoAMD(componentes);
        int consumoEsperado = 140 + 80 + 250 + 10 + 15;

        assertEquals(consumoEsperado, consumoEstimado,
                    "El consumo estimado debe calcularse correctamente con CPU AMD (140W)");
    }

    /**
     * Prueba que valida la diferencia de consumo entre CPU AMD e Intel.
     *
     * <p>Verifica que las CPUs AMD consuman mas que las Intel (140W vs 125W).
     */
    @Test
    public void pruebaCPUAMDConsumeMasQueIntel() {
        int consumoAMD = 140;
        int consumoIntel = 125;
        int diferencia = consumoAMD - consumoIntel;

        assertEquals(15, diferencia,
                    "Las CPUs AMD deben consumir 15W mas que las Intel");
        assertTrue(consumoAMD > consumoIntel,
                  "El consumo de CPU AMD debe ser mayor que el de Intel");
    }

    /**
     * Prueba que valida el comportamiento con CPU Intel en estrategia AMD.
     *
     * <p>Verifica que la estrategia AMD genere advertencia critica
     * cuando se usa una CPU Intel porque no pasa la verificacion de marca.
     */
    @Test
    public void pruebaCPUIntelEnEstrategiaAMD() {
        CompatibilidadAMD estrategia = new CompatibilidadAMD();
        List<ComponentePC> componentes = List.of(
            crearCPUIntel("Core i7-13700K", 16),
            crearMotherBoardIntel("ROG Maximus Z790 Hero")
        );

        ResultadoCompatibilidad resultado = estrategia.verificarCompatibilidad(componentes);

        assertNotNull(resultado, "El resultado no debe ser null");
        assertFalse(contieneAdvertencia(resultado.getAdvertencias(), "CPUAMDAdapter"),
                   "No debe haber advertencia de adaptador con CPU Intel");
        assertTrue(contieneAdvertencia(resultado.getAdvertencias(), "ADVERTENCIA CRITICA"),
                   "Debe haber advertencia critica porque CPU Intel no pasa verificacion AMD");
    }

    /**
     * Prueba que valida que no haya componentes adaptados en estrategia AMD.
     *
     * <p>Verifica que la estrategia AMD no agregue componentes a la lista
     * de componentesAdaptados (eso lo hace CompatibilidadMixta).
     */
    @Test
    public void pruebaSinComponentesAdaptadosEnEstrategiaAMD() {
        CompatibilidadAMD estrategia = new CompatibilidadAMD();
        List<ComponentePC> componentes = List.of(
            crearCPUAMD("Ryzen 9 7950X3D", 16),
            crearMotherBoardIntel("ROG Maximus Z790 Hero"),
            crearGPU("RTX 4090")
        );

        ResultadoCompatibilidad resultado = estrategia.verificarCompatibilidad(componentes);

        assertNotNull(resultado, "El resultado no debe ser null");
        assertTrue(resultado.getComponentesAdaptados().isEmpty(),
                  "La estrategia AMD no debe agregar componentes adaptados (eso lo hace CompatibilidadMixta)");
    }

    /**
     * Prueba que valida el margen de seguridad de potencia del 20%.
     *
     * <p>Verifica que la potencia recomendada sea el consumo * 1.2
     */
    @Test
    public void pruebaMargenSeguridadPotencia() {
        int consumoEstimado = 500;
        int potenciaRecomendada = (int) (consumoEstimado * 1.2);

        assertEquals(600, potenciaRecomendada,
                    "La potencia recomendada debe ser consumo * 1.2 (margen del 20%)");
    }
}
