package modelo.fabrica;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import modelo.componente.CPU;
import modelo.componente.MotherBoard;

/**
 * Clase de pruebas unitarias para AMDFactory.
 *
 * <p>Valida la correcta creacion de componentes AMD mediante el patron Abstract Factory,
 * asegurando que los CPUs AMD se creen con las especificaciones correctas y que los
 * MotherBoards retornados sean del stock actual (Intel), que posteriormente seran
 * adaptados mediante el patron Adapter para trabajar con CPUs AMD.
 *
 * <p>Esta suite de pruebas verifica:
 * <ul>
 *   <li>Creacion correcta de CPUs AMD con todas sus especificaciones (nucleos, arquitectura AMD64, precio)</li>
 *   <li>Creacion de MotherBoards del stock actual para ser usados con adaptadores</li>
 *   <li>Manejo de excepciones para modelos no reconocidos</li>
 *   <li>Integridad de datos de fabricante y tipo de componente</li>
 * </ul>
 *
 * <p>Patrones validados:
 * <ul>
 *   <li>Abstract Factory: Familias de componentes AMD</li>
 *   <li>Preparacion para Adapter: MotherBoards Intel que seran adaptados para AMD</li>
 * </ul>
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class AMDFactoryTest {

    /**
     * Provee argumentos para probar la creacion de CPUs AMD validos.
     * Retorna un stream de argumentos conteniendo: modelo, cantidad de nucleos,
     * precio esperado y arquitectura AMD64.
     *
     * @return Stream de argumentos para CPUs AMD validos
     */
    private static Stream<Arguments> proveedorModelosCPUAMDValidos() {
        return Stream.of(
            Arguments.of("Ryzen 5 5600G", 6, 7999.00, "x86-64 (AMD64)"),
            Arguments.of("Ryzen 5 7600X", 6, 12999.00, "x86-64 (AMD64)"),
            Arguments.of("Ryzen 7 7700X", 8, 17999.00, "x86-64 (AMD64)"),
            Arguments.of("Ryzen 9 7950X3D", 16, 32999.00, "x86-64 (AMD64)")
        );
    }

    /**
     * Provee argumentos para probar la creacion de MotherBoards desde el stock actual.
     * Retorna un stream de argumentos conteniendo: modelo, chipset Intel, socket Intel y precio.
     * Estos MotherBoards seran adaptados posteriormente para funcionar con CPUs AMD.
     *
     * @return Stream de argumentos para MotherBoards del stock actual
     */
    private static Stream<Arguments> proveedorModelosMotherBoardAMDValidos() {
        return Stream.of(
            Arguments.of("ROG Maximus Z790 Hero", "Z790", "LGA1700", 19999.00),
            Arguments.of("TUF Gaming B760-Plus WIFI D4", "B760", "LGA1700", 8999.00),
            Arguments.of("MEG Z790 Godlike", "Z790", "LGA1700", 29999.00),
            Arguments.of("MAG B760 Tomahawk WIFI DDR4", "B760", "LGA1700", 7999.00)
        );
    }

    /**
     * Provee modelos de CPU AMD invalidos para probar el manejo de excepciones.
     *
     * @return Stream de modelos de CPU invalidos
     */
    private static Stream<String> proveedorModelosCPUAMDInvalidos() {
        return Stream.of(
            "Ryzen 9 5950X",
            "Core i7-13700K",
            "Ryzen 5 3600",
            "ModeloInexistente",
            "",
            "Ryzen"
        );
    }

    /**
     * Provee modelos de MotherBoard invalidos para probar el manejo de excepciones.
     *
     * @return Stream de modelos de MotherBoard invalidos
     */
    private static Stream<String> proveedorModelosMotherBoardAMDInvalidos() {
        return Stream.of(
            "X670E Aorus Master",
            "B650 Gaming Plus",
            "ModeloInexistente",
            "",
            "AM5",
            "AMD"
        );
    }

    /**
     * Valida que un CPU AMD tenga las especificaciones esperadas.
     * Esta funcion es pura y no tiene efectos secundarios.
     *
     * @param cpu la CPU a validar
     * @param nombreEsperado el nombre esperado completo del CPU
     * @param precioEsperado el precio esperado
     * @param marcaEsperada la marca esperada
     * @param nucleosEsperados la cantidad de nucleos esperados
     * @param arquitecturaEsperada la arquitectura esperada (debe incluir AMD64)
     * @return true si todas las especificaciones coinciden, false en caso contrario
     */
    private static boolean validarCPUAMD(CPU cpu, String nombreEsperado, double precioEsperado,
                                         String marcaEsperada, int nucleosEsperados,
                                         String arquitecturaEsperada) {
        return cpu.obtenerNombre().equals(nombreEsperado) &&
               cpu.obtenerPrecio() == precioEsperado &&
               cpu.obtenerMarca().equals(marcaEsperada) &&
               cpu.obtenerTipo().equals("CPU") &&
               cpu.getCantidadNucleos() == nucleosEsperados &&
               cpu.getArquitectura().equals(arquitecturaEsperada);
    }

    /**
     * Valida que un MotherBoard del stock actual tenga las especificaciones correctas.
     * Esta funcion es pura y no tiene efectos secundarios.
     * Nota: Estos MotherBoards seran posteriormente adaptados para AMD.
     *
     * @param motherBoard el MotherBoard a validar
     * @param nombreEsperado el nombre esperado completo
     * @param precioEsperado el precio esperado
     * @param marcaEsperada la marca esperada (ASUS o MSI)
     * @param chipsetEsperado el chipset esperado (Intel)
     * @param socketEsperado el socket esperado (LGA1700)
     * @return true si todas las especificaciones coinciden, false en caso contrario
     */
    private static boolean validarMotherBoardDelStock(MotherBoard motherBoard, String nombreEsperado,
                                                      double precioEsperado, String marcaEsperada,
                                                      String chipsetEsperado, String socketEsperado) {
        return motherBoard.obtenerNombre().equals(nombreEsperado) &&
               motherBoard.obtenerPrecio() == precioEsperado &&
               motherBoard.obtenerMarca().equals(marcaEsperada) &&
               motherBoard.obtenerTipo().equals("MotherBoard") &&
               motherBoard.getChipset().equals(chipsetEsperado) &&
               motherBoard.getSocket().equals(socketEsperado) &&
               motherBoard.getArquitecturaSeparada().equals("x86-64");
    }

    /**
     * Prueba parametrizada que valida la creacion correcta de todos los modelos de CPU AMD.
     *
     * <p>Verifica que AMDFactory.crearCPU() cree instancias correctas con:
     * <ul>
     *   <li>Nombre completo con prefijo "AMD"</li>
     *   <li>Precio segun especificacion del modelo</li>
     *   <li>Marca "AMD"</li>
     *   <li>Tipo "CPU"</li>
     *   <li>Cantidad de nucleos especifica del modelo</li>
     *   <li>Arquitectura x86-64 (AMD64)</li>
     * </ul>
     *
     * <p>Modelos probados:
     * <ul>
     *   <li>Ryzen 5 5600G (Serie 5000, Socket AM4)</li>
     *   <li>Ryzen 5 7600X (Serie 7000, Socket AM5)</li>
     *   <li>Ryzen 7 7700X (Serie 7000, Socket AM5)</li>
     *   <li>Ryzen 9 7950X3D (Serie 7000, Socket AM5)</li>
     * </ul>
     *
     * @param modelo el nombre corto del modelo de CPU
     * @param nucleosEsperados cantidad de nucleos esperados
     * @param precioEsperado precio esperado en pesos mexicanos
     * @param arquitecturaEsperada arquitectura del procesador esperada
     */
    @ParameterizedTest(name = "CPU AMD {0}: {1} nucleos, ${2}, {3}")
    @MethodSource("proveedorModelosCPUAMDValidos")
    public void pruebaCrearCPUsAMDConModelosValidos(String modelo, int nucleosEsperados,
                                                     double precioEsperado,
                                                     String arquitecturaEsperada) {
        ComponenteFactory factory = new AMDFactory();
        CPU cpu = factory.crearCPU(modelo);

        assertNotNull(cpu, "La CPU no debe ser null");

        String nombreCompleto = "AMD " + modelo;
        assertTrue(
            validarCPUAMD(cpu, nombreCompleto, precioEsperado, "AMD",
                         nucleosEsperados, arquitecturaEsperada),
            String.format("La CPU %s no tiene las especificaciones correctas", modelo)
        );
    }

    /**
     * Prueba parametrizada que valida la creacion de MotherBoards del stock actual.
     *
     * <p>Verifica que AMDFactory.crearMotherBoard() retorne MotherBoards del stock actual
     * (placas Intel) que posteriormente seran adaptadas mediante el patron Adapter para
     * trabajar con CPUs AMD. Esta es la estrategia de MonosChinos MX para expandir su
     * negocio sin necesidad de adquirir nuevo inventario de placas madre.
     *
     * <p>Las placas retornadas tienen:
     * <ul>
     *   <li>Nombre completo con marca (ASUS o MSI)</li>
     *   <li>Precio del stock actual</li>
     *   <li>Marca correcta (ASUS o MSI)</li>
     *   <li>Tipo "MotherBoard"</li>
     *   <li>Chipset Intel (Z790 o B760)</li>
     *   <li>Socket Intel LGA1700</li>
     *   <li>Arquitectura x86-64</li>
     * </ul>
     *
     * <p>Modelos del stock disponibles: ROG Maximus Z790 Hero, TUF Gaming B760-Plus WIFI D4,
     * MEG Godlike, MAG B760 Tomahawk WIFI DDR4
     *
     * @param modelo el nombre corto del modelo de MotherBoard del stock
     * @param chipsetEsperado chipset de la placa madre (Intel)
     * @param socketEsperado socket del procesador (LGA1700)
     * @param precioEsperado precio esperado en pesos mexicanos
     */
    @ParameterizedTest(name = "MotherBoard Stock (para AMD) {0}: {1} chipset, {2} socket, ${3}")
    @MethodSource("proveedorModelosMotherBoardAMDValidos")
    public void pruebaCrearMotherBoardsAMDConModelosValidos(String modelo, String chipsetEsperado,
                                                             String socketEsperado,
                                                             double precioEsperado) {
        ComponenteFactory factory = new AMDFactory();
        MotherBoard motherBoard = factory.crearMotherBoard(modelo);

        assertNotNull(motherBoard, "El MotherBoard no debe ser null");

        String marca = modelo.contains("ROG") || modelo.contains("TUF") ? "ASUS" : "MSI";
        String nombreCompleto = marca.equals("ASUS") ?
            "ASUS " + modelo :
            "MSI " + modelo;

        assertTrue(
            validarMotherBoardDelStock(motherBoard, nombreCompleto, precioEsperado, marca,
                                      chipsetEsperado, socketEsperado),
            String.format("El MotherBoard %s no tiene las especificaciones correctas del stock", modelo)
        );
    }

    /**
     * Prueba parametrizada que verifica el manejo de excepciones para modelos de CPU AMD invalidos.
     *
     * <p>Valida que AMDFactory.crearCPU() lance IllegalArgumentException cuando se intenta
     * crear un CPU con un modelo no soportado. Esto incluye:
     * <ul>
     *   <li>Modelos AMD de generaciones no incluidas en el inventario (Ryzen 3000, 5000 no-G)</li>
     *   <li>Modelos de otros fabricantes (Intel)</li>
     *   <li>Nombres incompletos o incorrectos</li>
     *   <li>Cadenas vacias</li>
     *   <li>Modelos inexistentes</li>
     * </ul>
     *
     * @param modeloInvalido el nombre de un modelo de CPU no soportado
     */
    @ParameterizedTest(name = "CPU AMD invalido: \"{0}\" debe lanzar excepcion")
    @MethodSource("proveedorModelosCPUAMDInvalidos")
    public void pruebaCrearCPUAMDConModeloInvalidoLanzaExcepcion(String modeloInvalido) {
        ComponenteFactory factory = new AMDFactory();

        IllegalArgumentException excepcion = assertThrows(
            IllegalArgumentException.class,
            () -> factory.crearCPU(modeloInvalido),
            String.format("Deberia lanzar IllegalArgumentException para el modelo: %s", modeloInvalido)
        );

        assertTrue(
            excepcion.getMessage().contains("no reconocido") ||
            excepcion.getMessage().contains(modeloInvalido),
            "El mensaje de error debe indicar que el modelo no es reconocido"
        );
    }

    /**
     * Prueba parametrizada que verifica el manejo de excepciones para modelos de MotherBoard invalidos.
     *
     * <p>Valida que AMDFactory.crearMotherBoard() lance IllegalArgumentException cuando se intenta
     * crear un MotherBoard con un modelo no disponible en el stock actual. Esto incluye:
     * <ul>
     *   <li>Modelos nativos AMD que no estan en el inventario</li>
     *   <li>Modelos de otros fabricantes no disponibles</li>
     *   <li>Nombres incompletos (solo socket o solo marca)</li>
     *   <li>Cadenas vacias</li>
     *   <li>Modelos inexistentes</li>
     * </ul>
     *
     * @param modeloInvalido el nombre de un modelo de MotherBoard no disponible en stock
     */
    @ParameterizedTest(name = "MotherBoard AMD invalido: \"{0}\" debe lanzar excepcion")
    @MethodSource("proveedorModelosMotherBoardAMDInvalidos")
    public void pruebaCrearMotherBoardAMDConModeloInvalidoLanzaExcepcion(String modeloInvalido) {
        ComponenteFactory factory = new AMDFactory();

        IllegalArgumentException excepcion = assertThrows(
            IllegalArgumentException.class,
            () -> factory.crearMotherBoard(modeloInvalido),
            String.format("Deberia lanzar IllegalArgumentException para el modelo: %s", modeloInvalido)
        );

        assertTrue(
            excepcion.getMessage().contains("no reconocido") ||
            excepcion.getMessage().contains(modeloInvalido),
            "El mensaje de error debe indicar que el modelo no es reconocido"
        );
    }

    /**
     * Prueba que verifica la consistencia del patron Abstract Factory para CPUs AMD.
     *
     * <p>Valida que multiples llamadas a crearCPU() con el mismo modelo produzcan
     * instancias independientes con las mismas especificaciones (objetos diferentes
     * pero valores iguales).
     */
    @Test
    public void pruebaConsistenciaCreacionMultiplesCPUsAMD() {
        ComponenteFactory factory = new AMDFactory();

        Stream.of("Ryzen 5 5600G", "Ryzen 5 7600X", "Ryzen 7 7700X", "Ryzen 9 7950X3D")
            .forEach(modelo -> {
                CPU cpu1 = factory.crearCPU(modelo);
                CPU cpu2 = factory.crearCPU(modelo);

                assertNotSame(cpu1, cpu2,
                    "Las instancias deben ser diferentes (patron Factory crea nuevos objetos)");
                assertEquals(cpu1.obtenerNombre(), cpu2.obtenerNombre(),
                    "Los nombres deben ser identicos");
                assertEquals(cpu1.obtenerPrecio(), cpu2.obtenerPrecio(),
                    "Los precios deben ser identicos");
                assertEquals(cpu1.getCantidadNucleos(), cpu2.getCantidadNucleos(),
                    "La cantidad de nucleos debe ser identica");
                assertTrue(cpu1.getArquitectura().contains("AMD64"),
                    "La arquitectura debe incluir AMD64");
            });
    }

    /**
     * Prueba que verifica la consistencia del patron Abstract Factory para MotherBoards AMD.
     *
     * <p>Valida que multiples llamadas a crearMotherBoard() con el mismo modelo produzcan
     * instancias independientes con las mismas especificaciones del stock actual.
     */
    @Test
    public void pruebaConsistenciaCreacionMultiplesMotherBoardsAMD() {
        ComponenteFactory factory = new AMDFactory();

        Stream.of("ROG Maximus Z790 Hero", "TUF Gaming B760-Plus WIFI D4",
                 "MEG Z790 Godlike", "MAG B760 Tomahawk WIFI DDR4")
            .forEach(modelo -> {
                MotherBoard mb1 = factory.crearMotherBoard(modelo);
                MotherBoard mb2 = factory.crearMotherBoard(modelo);

                assertNotSame(mb1, mb2,
                    "Las instancias deben ser diferentes (patron Factory crea nuevos objetos)");
                assertEquals(mb1.obtenerNombre(), mb2.obtenerNombre(),
                    "Los nombres deben ser identicos");
                assertEquals(mb1.obtenerPrecio(), mb2.obtenerPrecio(),
                    "Los precios deben ser identicos");
                assertEquals(mb1.getChipset(), mb2.getChipset(),
                    "Los chipsets deben ser identicos");
                assertEquals(mb1.getSocket(), mb2.getSocket(),
                    "Los sockets deben ser identicos");
            });
    }

    /**
     * Prueba que valida que los CPUs AMD tengan arquitectura AMD64 distintiva.
     *
     * <p>Verifica que todos los procesadores AMD creados tengan la arquitectura
     * x86-64 (AMD64) que los distingue de los procesadores Intel, permitiendo
     * identificar la familia del procesador.
     */
    @Test
    public void pruebaTodosCPUsAMDTienenArquitecturaAMD64() {
        ComponenteFactory factory = new AMDFactory();

        boolean todosConAMD64 = Stream.of("Ryzen 5 5600G", "Ryzen 5 7600X",
                                          "Ryzen 7 7700X", "Ryzen 9 7950X3D")
            .map(factory::crearCPU)
            .allMatch(cpu -> cpu.getArquitectura().contains("AMD64"));

        assertTrue(todosConAMD64,
            "Todos los CPUs AMD deben tener arquitectura que incluya AMD64");
    }

    /**
     * Prueba que valida que los MotherBoards retornados por AMDFactory son del stock Intel.
     *
     * <p>Verifica que todas las placas madre retornadas tengan socket LGA1700,
     * confirmando que son del stock actual (Intel) y necesitaran adaptacion
     * para trabajar con CPUs AMD.
     */
    @Test
    public void pruebaTodosMotherBoardsAMDSonDelStockIntel() {
        ComponenteFactory factory = new AMDFactory();

        boolean todosSonLGA1700 = Stream.of("ROG Maximus Z790 Hero",
                                           "TUF Gaming B760-Plus WIFI D4",
                                           "MEG Z790 Godlike",
                                           "MAG B760 Tomahawk WIFI DDR4")
            .map(factory::crearMotherBoard)
            .allMatch(mb -> mb.getSocket().equals("LGA1700"));

        assertTrue(todosSonLGA1700,
            "Todos los MotherBoards deben tener socket LGA1700 (stock Intel)");
    }
}
