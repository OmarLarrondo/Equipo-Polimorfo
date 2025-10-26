package modelo.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import modelo.componente.ComponentePC;
import modelo.computadora.ComputadoraBase;
import modelo.fabrica.AMDFactory;
import modelo.fabrica.ComponenteFactory;
import modelo.fabrica.IntelFactory;
import modelo.inventario.Inventario;

/**
 * Clase de pruebas unitarias para PCPersonalizadoBuilder.
 * Valida la correcta construccion de computadoras personalizadas utilizando el patron Builder
 * con factories especificas (Intel o AMD).
 *
 * <p>Esta suite de pruebas verifica:
 * <ul>
 *   <li>Construccion completa de PCs con IntelFactory</li>
 *   <li>Construccion completa de PCs con AMDFactory</li>
 *   <li>Seleccion correcta de componentes compatibles</li>
 *   <li>Integracion con el patron Abstract Factory</li>
 *   <li>Comportamiento correcto del patron Builder (reinicio, construccion paso a paso)</li>
 *   <li>Compatibilidad entre CPU y MotherBoard segun la factory utilizada</li>
 * </ul>
 *
 * <p>Patrones validados:
 * <ul>
 *   <li>Builder: Construccion paso a paso de computadoras personalizadas</li>
 *   <li>Abstract Factory: Familias de componentes compatibles (Intel/AMD)</li>
 *   <li>Singleton: Acceso al inventario unico de componentes</li>
 * </ul>
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
class PCPersonalizadoBuilderTest{

    /**
     * Inicializa el inventario antes de cada prueba.
     * Asegura que el singleton de Inventario este disponible con todos los componentes.
     */
    @BeforeEach
    public void inicializarInventario() {
        Inventario.getInstance().inicializarCatalogo();
    }

    /**
     * Provee factories para pruebas parametrizadas.
     * Retorna un stream con IntelFactory y AMDFactory junto con sus nombres.
     *
     * @return Stream de argumentos con factory y nombre descriptivo
     */
    private static Stream<Arguments> proveedorFactories() {
        return Stream.of(
            Arguments.of(new IntelFactory(), "Intel"),
            Arguments.of(new AMDFactory(), "AMD")
        );
    }

    /**
     * Provee los tipos de componentes esperados en una PC completa.
     * Una PC personalizada debe tener exactamente 7 tipos de componentes.
     *
     * @return Stream de tipos de componentes
     */
    private static Stream<String> proveedorTiposComponentes() {
        return Stream.of("CPU", "RAM", "MotherBoard", "Disco", "GPU", "FuenteAlimentacion", "Gabinete");
    }

    /**
     * Valida que una computadora tenga todos los componentes necesarios.
     * Una PC completa debe tener al menos un componente de cada tipo basico.
     * Esta funcion es pura y no tiene efectos secundarios.
     *
     * @param computadora la computadora a validar
     * @return true si tiene todos los tipos de componentes necesarios, false en caso contrario
     */
    private static boolean validarComputadoraTieneTodosLosComponentes(ComputadoraBase computadora) {
        List<ComponentePC> componentes = computadora.obtenerComponentes();

        return proveedorTiposComponentes()
            .allMatch(tipo -> componentes.stream()
                .anyMatch(c -> c.obtenerTipo().equals(tipo)));
    }

    /**
     * Cuenta la cantidad de componentes de un tipo especifico.
     * Esta funcion es pura y no tiene efectos secundarios.
     *
     * @param componentes lista de componentes a analizar
     * @param tipo el tipo de componente a contar
     * @return cantidad de componentes del tipo especificado
     */
    private static long contarComponentesPorTipo(List<ComponentePC> componentes, String tipo) {
        return componentes.stream()
            .filter(c -> c.obtenerTipo().equals(tipo))
            .count();
    }

    /**
     * Valida que el CPU de una computadora sea de la marca esperada.
     * Esta funcion es pura y no tiene efectos secundarios.
     *
     * @param computadora la computadora a validar
     * @param marcaEsperada la marca esperada del CPU (Intel o AMD)
     * @return true si el CPU tiene la marca esperada, false en caso contrario
     */
    private static boolean validarCPUPorMarca(ComputadoraBase computadora, String marcaEsperada) {
        return computadora.obtenerComponentes().stream()
            .filter(c -> c.obtenerTipo().equals("CPU"))
            .findFirst()
            .map(cpu -> cpu.obtenerMarca().equals(marcaEsperada))
            .orElse(false);
    }

    /**
     * Obtiene el CPU de una computadora.
     * Esta funcion es pura y no tiene efectos secundarios.
     *
     * @param computadora la computadora de donde obtener el CPU
     * @return Optional con el CPU si existe, vacio en caso contrario
     */
    private static Optional<ComponentePC> obtenerCPU(ComputadoraBase computadora) {
        return computadora.obtenerComponentes().stream()
            .filter(c -> c.obtenerTipo().equals("CPU"))
            .findFirst();
    }

    /**
     * Obtiene el MotherBoard de una computadora.
     * Esta funcion es pura y no tiene efectos secundarios.
     *
     * @param computadora la computadora de donde obtener el MotherBoard
     * @return Optional con el MotherBoard si existe, vacio en caso contrario
     */
    private static Optional<ComponentePC> obtenerMotherBoard(ComputadoraBase computadora) {
        return computadora.obtenerComponentes().stream()
            .filter(c -> c.obtenerTipo().equals("MotherBoard"))
            .findFirst();
    }

    /**
     * Calcula el precio total sumando todos los componentes.
     * Esta funcion es pura y no tiene efectos secundarios.
     *
     * @param componentes lista de componentes
     * @return precio total de todos los componentes
     */
    private static double calcularPrecioTotal(List<ComponentePC> componentes) {
        return componentes.stream()
            .mapToDouble(ComponentePC::obtenerPrecio)
            .sum();
    }

    /**
     * Prueba parametrizada que valida la creacion completa de PC personalizada con diferentes factories.
     *
     * <p>Verifica que PCPersonalizadoBuilder construya correctamente una computadora completa
     * utilizando cualquier ComponenteFactory (Intel o AMD). La construccion debe:
     * <ul>
     *   <li>Crear una computadora no nula</li>
     *   <li>Incluir todos los componentes necesarios (7 tipos)</li>
     *   <li>Tener un precio total positivo</li>
     *   <li>Generar una descripcion no vacia</li>
     * </ul>
     *
     * @param factory la factory a utilizar (IntelFactory o AMDFactory)
     * @param nombreFactory nombre descriptivo de la factory
     */
    @ParameterizedTest(name = "Construccion completa con {1}Factory")
    @MethodSource("proveedorFactories")
    public void pruebaCrearPCPersonalizadaCompleta(ComponenteFactory factory, String nombreFactory) {
        PCBuilder builder = new PCPersonalizadoBuilder(factory);

        builder.construirCPU();
        builder.construirRAM();
        builder.construirMotherBoard();
        builder.construirGPU();
        builder.construirAlmacenamiento();
        builder.construirFuente();
        builder.construirGabinete();

        ComputadoraBase computadora = builder.obtenerResultado();

        assertNotNull(computadora, "La computadora no debe ser null");
        assertTrue(validarComputadoraTieneTodosLosComponentes(computadora),
            String.format("La PC con %sFactory debe tener todos los componentes necesarios", nombreFactory));
        assertTrue(computadora.obtenerPrecioTotal() > 0,
            "El precio total debe ser positivo");
        assertNotNull(computadora.obtenerDescripcion(),
            "La descripcion no debe ser null");
    }

    /**
     * Prueba que valida que todos los componentes se agreguen correctamente con IntelFactory.
     *
     * <p>Verifica que una PC construida con IntelFactory tenga exactamente los 7 tipos
     * de componentes requeridos: CPU, RAM, MotherBoard, Disco, GPU, FuenteAlimentacion y Gabinete.
     */
    @Test
    public void pruebaTodosLosComponentesSeAgreganConIntelFactory() {
        PCBuilder builder = new PCPersonalizadoBuilder(new IntelFactory());

        builder.construirCPU();
        builder.construirRAM();
        builder.construirMotherBoard();
        builder.construirGPU();
        builder.construirAlmacenamiento();
        builder.construirFuente();
        builder.construirGabinete();

        ComputadoraBase computadora = builder.obtenerResultado();
        List<ComponentePC> componentes = computadora.obtenerComponentes();

        assertTrue(componentes.size() >= 7,
            "Debe haber al menos 7 componentes");

        proveedorTiposComponentes().forEach(tipo -> {
            long cantidad = contarComponentesPorTipo(componentes, tipo);
            assertTrue(cantidad >= 1,
                String.format("Debe haber al menos 1 componente de tipo %s", tipo));
        });
    }

    /**
     * Prueba que valida que todos los componentes se agreguen correctamente con AMDFactory.
     *
     * <p>Verifica que una PC construida con AMDFactory tenga exactamente los 7 tipos
     * de componentes requeridos, confirmando que la factory AMD funciona correctamente
     * con los componentes del inventario.
     */
    @Test
    public void pruebaTodosLosComponentesSeAgreganConAMDFactory() {
        PCBuilder builder = new PCPersonalizadoBuilder(new AMDFactory());

        builder.construirCPU();
        builder.construirRAM();
        builder.construirMotherBoard();
        builder.construirGPU();
        builder.construirAlmacenamiento();
        builder.construirFuente();
        builder.construirGabinete();

        ComputadoraBase computadora = builder.obtenerResultado();
        List<ComponentePC> componentes = computadora.obtenerComponentes();

        assertTrue(componentes.size() >= 7,
            "Debe haber al menos 7 componentes");

        proveedorTiposComponentes().forEach(tipo -> {
            long cantidad = contarComponentesPorTipo(componentes, tipo);
            assertTrue(cantidad >= 1,
                String.format("Debe haber al menos 1 componente de tipo %s", tipo));
        });
    }

    /**
     * Prueba que verifica que el CPU seleccionado sea de marca Intel cuando se usa IntelFactory.
     *
     * <p>Valida que PCPersonalizadoBuilder filtre correctamente los CPUs del inventario
     * segun la marca de la factory utilizada, seleccionando solo procesadores Intel
     * cuando se construye con IntelFactory.
     */
    @Test
    public void pruebaCPUEsIntelConIntelFactory() {
        PCBuilder builder = new PCPersonalizadoBuilder(new IntelFactory());
        builder.construirCPU();

        ComputadoraBase computadora = builder.obtenerResultado();

        assertTrue(validarCPUPorMarca(computadora, "Intel"),
            "El CPU debe ser de marca Intel cuando se usa IntelFactory");

        obtenerCPU(computadora).ifPresent(cpu -> {
            assertTrue(cpu.obtenerNombre().contains("Intel"),
                "El nombre del CPU debe contener 'Intel'");
        });
    }

    /**
     * Prueba que verifica que el CPU seleccionado sea de marca AMD cuando se usa AMDFactory.
     *
     * <p>Valida que PCPersonalizadoBuilder filtre correctamente los CPUs del inventario
     * segun la marca de la factory utilizada, seleccionando solo procesadores AMD
     * cuando se construye con AMDFactory.
     */
    @Test
    public void pruebaCPUEsAMDConAMDFactory() {
        PCBuilder builder = new PCPersonalizadoBuilder(new AMDFactory());
        builder.construirCPU();

        ComputadoraBase computadora = builder.obtenerResultado();

        assertTrue(validarCPUPorMarca(computadora, "AMD"),
            "El CPU debe ser de marca AMD cuando se usa AMDFactory");

        obtenerCPU(computadora).ifPresent(cpu -> {
            assertTrue(cpu.obtenerNombre().contains("AMD"),
                "El nombre del CPU debe contener 'AMD'");
        });
    }

    /**
     * Prueba que verifica el comportamiento del metodo reiniciar().
     *
     * <p>Valida que al llamar reiniciar() se cree una nueva instancia de computadora,
     * descartando cualquier componente previamente agregado. Las dos computadoras
     * (antes y despues del reinicio) deben ser instancias diferentes.
     */
    @Test
    public void pruebaReiniciarBuilderCreaComputadoraNueva() {
        PCBuilder builder = new PCPersonalizadoBuilder(new IntelFactory());

        builder.construirCPU();
        builder.construirRAM();
        ComputadoraBase computadora1 = builder.obtenerResultado();

        builder.reiniciar();
        builder.construirGPU();
        ComputadoraBase computadora2 = builder.obtenerResultado();

        assertNotSame(computadora1, computadora2,
            "Reiniciar debe crear una nueva instancia de computadora");

        assertTrue(computadora1.obtenerComponentes().size() >= 2,
            "La primera computadora debe tener los componentes agregados antes del reinicio");

        assertTrue(computadora2.obtenerComponentes().size() >= 1,
            "La segunda computadora debe tener solo los componentes agregados despues del reinicio");
    }

    /**
     * Prueba que verifica la construccion paso a paso del Builder.
     *
     * <p>Valida que el patron Builder permita agregar componentes de forma incremental,
     * verificando el estado de la computadora despues de cada paso de construccion.
     */
    @Test
    public void pruebaConstruccionPasoPorPaso() {
        PCBuilder builder = new PCPersonalizadoBuilder(new IntelFactory());
        ComputadoraBase computadora = builder.obtenerResultado();

        assertEquals(0, computadora.obtenerComponentes().size(),
            "La computadora debe estar vacia inicialmente");

        builder.construirCPU();
        assertTrue(computadora.obtenerComponentes().size() >= 1,
            "Despues de construir CPU debe haber al menos 1 componente");

        builder.construirRAM();
        assertTrue(computadora.obtenerComponentes().size() >= 2,
            "Despues de construir RAM debe haber al menos 2 componentes");

        builder.construirMotherBoard();
        assertTrue(computadora.obtenerComponentes().size() >= 3,
            "Despues de construir MotherBoard debe haber al menos 3 componentes");

        builder.construirGPU();
        builder.construirAlmacenamiento();
        builder.construirFuente();
        builder.construirGabinete();

        assertTrue(computadora.obtenerComponentes().size() >= 7,
            "Despues de construir todos los componentes debe haber al menos 7");
    }

    /**
     * Prueba que verifica que el precio total incluya todos los componentes.
     *
     * <p>Valida que el metodo obtenerPrecioTotal() de la computadora construida
     * retorne la suma correcta de todos los precios de los componentes agregados.
     */
    @Test
    public void pruebaPrecioTotalIncluyeTodosLosComponentes() {
        PCBuilder builder = new PCPersonalizadoBuilder(new IntelFactory());

        builder.construirCPU();
        builder.construirRAM();
        builder.construirMotherBoard();
        builder.construirGPU();
        builder.construirAlmacenamiento();
        builder.construirFuente();
        builder.construirGabinete();

        ComputadoraBase computadora = builder.obtenerResultado();
        List<ComponentePC> componentes = computadora.obtenerComponentes();

        double precioEsperado = calcularPrecioTotal(componentes);
        double precioObtenido = computadora.obtenerPrecioTotal();

        assertEquals(precioEsperado, precioObtenido, 0.01,
            "El precio total debe ser la suma de todos los componentes");
    }

    /**
     * Prueba que verifica la compatibilidad entre CPU y MotherBoard con IntelFactory.
     *
     * <p>Valida que cuando se usa IntelFactory, el MotherBoard creado sea compatible
     * con procesadores Intel. Verifica que el MotherBoard tenga arquitectura x86-64
     * y sea creado mediante la factory correspondiente.
     */
    @Test
    public void pruebaMotherBoardCompatibleConCPUIntel() {
        PCBuilder builder = new PCPersonalizadoBuilder(new IntelFactory());

        builder.construirCPU();
        builder.construirMotherBoard();

        ComputadoraBase computadora = builder.obtenerResultado();

        Optional<ComponentePC> cpu = obtenerCPU(computadora);
        Optional<ComponentePC> motherBoard = obtenerMotherBoard(computadora);

        assertTrue(cpu.isPresent(), "Debe existir un CPU");
        assertTrue(motherBoard.isPresent(), "Debe existir un MotherBoard");

        cpu.ifPresent(c -> assertEquals("Intel", c.obtenerMarca(),
            "El CPU debe ser Intel"));

        motherBoard.ifPresent(mb -> {
            assertNotNull(mb.obtenerNombre(),
                "El MotherBoard debe tener un nombre");
            assertTrue(mb.obtenerNombre().contains("ASUS") || mb.obtenerNombre().contains("MSI"),
                "El MotherBoard debe ser de marca ASUS o MSI del inventario");
        });
    }

    /**
     * Prueba que verifica la compatibilidad entre CPU y MotherBoard con AMDFactory.
     *
     * <p>Valida que cuando se usa AMDFactory, el MotherBoard retornado sea del stock actual
     * (placas Intel) que posteriormente seran adaptadas. Confirma la estrategia de
     * MonosChinos MX de usar el inventario existente con CPUs AMD.
     */
    @Test
    public void pruebaMotherBoardCompatibleConCPUAMD() {
        PCBuilder builder = new PCPersonalizadoBuilder(new AMDFactory());

        builder.construirCPU();
        builder.construirMotherBoard();

        ComputadoraBase computadora = builder.obtenerResultado();

        Optional<ComponentePC> cpu = obtenerCPU(computadora);
        Optional<ComponentePC> motherBoard = obtenerMotherBoard(computadora);

        assertTrue(cpu.isPresent(), "Debe existir un CPU");
        assertTrue(motherBoard.isPresent(), "Debe existir un MotherBoard");

        cpu.ifPresent(c -> assertEquals("AMD", c.obtenerMarca(),
            "El CPU debe ser AMD"));

        motherBoard.ifPresent(mb -> {
            assertNotNull(mb.obtenerNombre(),
                "El MotherBoard debe tener un nombre");
            assertTrue(mb.obtenerNombre().contains("ASUS") || mb.obtenerNombre().contains("MSI"),
                "El MotherBoard debe ser del stock actual (ASUS o MSI)");
        });
    }

    /**
     * Prueba que valida que la factory determine correctamente la marca del CPU.
     *
     * <p>Verifica que PCPersonalizadoBuilder utilice correctamente el metodo privado
     * obtenerMarcaFactory() para determinar si debe filtrar CPUs Intel o AMD del inventario.
     * Esta prueba confirma la integracion entre el Builder y el patron Abstract Factory.
     */
    @Test
    public void pruebaFactoryDeterminaMarcaCPU() {
        PCBuilder builderIntel = new PCPersonalizadoBuilder(new IntelFactory());
        PCBuilder builderAMD = new PCPersonalizadoBuilder(new AMDFactory());

        builderIntel.construirCPU();
        builderAMD.construirCPU();

        ComputadoraBase pcIntel = builderIntel.obtenerResultado();
        ComputadoraBase pcAMD = builderAMD.obtenerResultado();

        assertTrue(validarCPUPorMarca(pcIntel, "Intel"),
            "IntelFactory debe resultar en CPU Intel");
        assertTrue(validarCPUPorMarca(pcAMD, "AMD"),
            "AMDFactory debe resultar en CPU AMD");

        Optional<ComponentePC> cpuIntel = obtenerCPU(pcIntel);
        Optional<ComponentePC> cpuAMD = obtenerCPU(pcAMD);

        cpuIntel.ifPresent(cpu ->
            assertTrue(cpu.obtenerNombre().startsWith("Intel"),
                "El nombre del CPU Intel debe comenzar con 'Intel'"));

        cpuAMD.ifPresent(cpu ->
            assertTrue(cpu.obtenerNombre().startsWith("AMD"),
                "El nombre del CPU AMD debe comenzar con 'AMD'"));
    }

    /**
     * Prueba que verifica la consistencia del Builder con multiples construcciones.
     *
     * <p>Valida que el mismo builder pueda crear multiples computadoras mediante
     * el metodo reiniciar(), y que cada una sea una instancia independiente con
     * sus propios componentes.
     */
    @Test
    public void pruebaConsistenciaMultiplesConstrucciones() {
        PCBuilder builder = new PCPersonalizadoBuilder(new IntelFactory());

        builder.construirCPU();
        builder.construirRAM();
        ComputadoraBase pc1 = builder.obtenerResultado();

        builder.reiniciar();
        builder.construirGPU();
        builder.construirAlmacenamiento();
        ComputadoraBase pc2 = builder.obtenerResultado();

        builder.reiniciar();
        builder.construirFuente();
        builder.construirGabinete();
        ComputadoraBase pc3 = builder.obtenerResultado();

        assertNotSame(pc1, pc2, "PC1 y PC2 deben ser instancias diferentes");
        assertNotSame(pc2, pc3, "PC2 y PC3 deben ser instancias diferentes");
        assertNotSame(pc1, pc3, "PC1 y PC3 deben ser instancias diferentes");

        assertTrue(pc1.obtenerComponentes().size() >= 2,
            "PC1 debe tener al menos 2 componentes");
        assertTrue(pc2.obtenerComponentes().size() >= 2,
            "PC2 debe tener al menos 2 componentes");
        assertTrue(pc3.obtenerComponentes().size() >= 2,
            "PC3 debe tener al menos 2 componentes");
    }

    /**
     * Prueba parametrizada que verifica que cada tipo de componente se agregue correctamente.
     *
     * <p>Valida que los metodos individuales de construccion (construirCPU, construirRAM, etc.)
     * agreguen efectivamente el tipo correcto de componente a la computadora.
     *
     * @param tipoComponente el tipo de componente a verificar
     */
    @ParameterizedTest(name = "Construccion de componente tipo {0}")
    @MethodSource("proveedorTiposComponentes")
    public void pruebaConstruccionComponenteIndividual(String tipoComponente) {
        PCBuilder builder = new PCPersonalizadoBuilder(new IntelFactory());

        switch (tipoComponente) {
            case "CPU" -> builder.construirCPU();
            case "RAM" -> builder.construirRAM();
            case "MotherBoard" -> builder.construirMotherBoard();
            case "Disco" -> builder.construirAlmacenamiento();
            case "GPU" -> builder.construirGPU();
            case "FuenteAlimentacion" -> builder.construirFuente();
            case "Gabinete" -> builder.construirGabinete();
        }

        ComputadoraBase computadora = builder.obtenerResultado();
        List<ComponentePC> componentes = computadora.obtenerComponentes();

        long cantidad = contarComponentesPorTipo(componentes, tipoComponente);
        assertTrue(cantidad >= 1,
            String.format("Debe haber al menos 1 componente de tipo %s", tipoComponente));
    }
}
