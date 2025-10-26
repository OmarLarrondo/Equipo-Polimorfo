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
 * Clase de pruebas unitarias para IntelFactory.
 *
 * <p>Valida la correcta creacion de componentes Intel mediante el patron Abstract Factory,
 * asegurando que los CPUs y MotherBoards se creen con las especificaciones correctas
 * y que se manejen adecuadamente los casos de modelos invalidos.
 *
 * <p>Esta suite de pruebas verifica:
 * <ul>
 *   <li>Creacion correcta de CPUs Intel con todas sus especificaciones (nucleos, arquitectura, precio)</li>
 *   <li>Creacion correcta de MotherBoards Intel compatibles (chipset, socket, precio)</li>
 *   <li>Manejo de excepciones para modelos no reconocidos</li>
 *   <li>Integridad de datos de fabricante y tipo de componente</li>
 * </ul>
 *
 * <p>Patron validado: Abstract Factory para familias de componentes Intel compatibles entre si.
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class IntelFactoryTest {

    /**
     * Provee argumentos para probar la creacion de CPUs Intel validos.
     * Retorna un stream de argumentos conteniendo: modelo, cantidad de nucleos,
     * precio esperado y arquitectura.
     *
     * @return Stream de argumentos para CPUs Intel validos
     */
    private static Stream<Arguments> proveedorModelosCPUIntelValidos() {
        return Stream.of(
            Arguments.of("Core i3-13100", 4, 8999.00, "x86-64"),
            Arguments.of("Core i5-13600K", 14, 18999.00, "x86-64"),
            Arguments.of("Core i7-13700K", 16, 24999.00, "x86-64"),
            Arguments.of("Core i9-13900K", 24, 34999.00, "x86-64")
        );
    }

    /**
     * Provee argumentos para probar la creacion de MotherBoards Intel validos.
     * Retorna un stream de argumentos conteniendo: modelo, chipset, socket y precio esperado.
     *
     * @return Stream de argumentos para MotherBoards Intel validos
     */
    private static Stream<Arguments> proveedorModelosMotherBoardIntelValidos() {
        return Stream.of(
            Arguments.of("ROG Maximus Z790 Hero", "Z790", "LGA1700", 19999.00),
            Arguments.of("TUF Gaming B760-Plus WIFI D4", "B760", "LGA1700", 8999.00),
            Arguments.of("MEG Godlike", "Z790", "LGA1700", 29999.00),
            Arguments.of("MAG B760 Tomahawk WIFI DDR4", "B760", "LGA1700", 7999.00)
        );
    }

    /**
     * Provee modelos de CPU Intel invalidos para probar el manejo de excepciones.
     *
     * @return Stream de modelos de CPU invalidos
     */
    private static Stream<String> proveedorModelosCPUIntelInvalidos() {
        return Stream.of(
            "Core i9-14900K",
            "Ryzen 5 5600G",
            "Core i7-12700K",
            "ModeloInexistente",
            "",
            "Core i5"
        );
    }

    /**
     * Provee modelos de MotherBoard Intel invalidos para probar el manejo de excepciones.
     *
     * @return Stream de modelos de MotherBoard invalidos
     */
    private static Stream<String> proveedorModelosMotherBoardIntelInvalidos() {
        return Stream.of(
            "ROG Strix X670E",
            "TUF Gaming B650",
            "ModeloInexistente",
            "",
            "Z790",
            "ASUS"
        );
    }

    /**
     * Valida que un CPU tenga las especificaciones esperadas.
     * Esta funcion es pura y no tiene efectos secundarios.
     *
     * @param cpu la CPU a validar
     * @param nombreEsperado el nombre esperado completo del CPU
     * @param precioEsperado el precio esperado
     * @param marcaEsperada la marca esperada
     * @param nucleosEsperados la cantidad de nucleos esperados
     * @param arquitecturaEsperada la arquitectura esperada
     * @return true si todas las especificaciones coinciden, false en caso contrario
     */
    private static boolean validarCPU(CPU cpu, String nombreEsperado, double precioEsperado,
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
     * Valida que un MotherBoard tenga las especificaciones esperadas.
     * Esta funcion es pura y no tiene efectos secundarios.
     *
     * @param motherBoard el MotherBoard a validar
     * @param nombreEsperado el nombre esperado completo
     * @param precioEsperado el precio esperado
     * @param marcaEsperada la marca esperada
     * @param chipsetEsperado el chipset esperado
     * @param socketEsperado el socket esperado
     * @return true si todas las especificaciones coinciden, false en caso contrario
     */
    private static boolean validarMotherBoard(MotherBoard motherBoard, String nombreEsperado,
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
     * Prueba parametrizada que valida la creacion correcta de todos los modelos de CPU Intel.
     *
     * <p>Verifica que IntelFactory.crearCPU() cree instancias correctas con:
     * <ul>
     *   <li>Nombre completo con prefijo "Intel"</li>
     *   <li>Precio segun especificacion del modelo</li>
     *   <li>Marca "Intel"</li>
     *   <li>Tipo "CPU"</li>
     *   <li>Cantidad de nucleos especifica del modelo</li>
     *   <li>Arquitectura x86-64</li>
     * </ul>
     *
     * <p>Modelos probados: Core i3-13100, Core i5-13600K, Core i7-13700K, Core i9-13900K
     *
     * @param modelo el nombre corto del modelo de CPU
     * @param nucleosEsperados cantidad de nucleos esperados
     * @param precioEsperado precio esperado en pesos mexicanos
     * @param arquitecturaEsperada arquitectura del procesador esperada
     */
    @ParameterizedTest(name = "CPU Intel {0}: {1} nucleos, ${2}, {3}")
    @MethodSource("proveedorModelosCPUIntelValidos")
    public void pruebaCrearCPUsIntelConModelosValidos(String modelo, int nucleosEsperados,
                                                       double precioEsperado,
                                                       String arquitecturaEsperada) {
        ComponenteFactory factory = new IntelFactory();
        CPU cpu = factory.crearCPU(modelo);

        assertNotNull(cpu, "La CPU no debe ser null");

        String nombreCompleto = "Intel " + modelo;
        assertTrue(
            validarCPU(cpu, nombreCompleto, precioEsperado, "Intel",
                      nucleosEsperados, arquitecturaEsperada),
            String.format("La CPU %s no tiene las especificaciones correctas", modelo)
        );
    }

    /**
     * Prueba parametrizada que valida la creacion correcta de todos los modelos de MotherBoard Intel.
     *
     * <p>Verifica que IntelFactory.crearMotherBoard() cree instancias correctas con:
     * <ul>
     *   <li>Nombre completo con marca (ASUS o MSI)</li>
     *   <li>Precio segun especificacion del modelo</li>
     *   <li>Marca correcta (ASUS o MSI)</li>
     *   <li>Tipo "MotherBoard"</li>
     *   <li>Chipset correcto (Z790 o B760)</li>
     *   <li>Socket LGA1700 (compatible con CPUs Intel 12a y 13a gen)</li>
     *   <li>Arquitectura x86-64</li>
     * </ul>
     *
     * <p>Modelos probados: ROG Maximus Z790 Hero, TUF Gaming B760-Plus WIFI D4,
     * MEG Godlike, MAG B760 Tomahawk WIFI DDR4
     *
     * @param modelo el nombre corto del modelo de MotherBoard
     * @param chipsetEsperado chipset de la placa madre
     * @param socketEsperado socket del procesador
     * @param precioEsperado precio esperado en pesos mexicanos
     */
    @ParameterizedTest(name = "MotherBoard Intel {0}: {1} chipset, {2} socket, ${3}")
    @MethodSource("proveedorModelosMotherBoardIntelValidos")
    public void pruebaCrearMotherBoardsIntelConModelosValidos(String modelo, String chipsetEsperado,
                                                               String socketEsperado,
                                                               double precioEsperado) {
        ComponenteFactory factory = new IntelFactory();
        MotherBoard motherBoard = factory.crearMotherBoard(modelo);

        assertNotNull(motherBoard, "El MotherBoard no debe ser null");

        String marca = modelo.contains("ROG") || modelo.contains("TUF") ? "ASUS" : "MSI";
        String nombreCompleto = marca.equals("ASUS") ?
            "ASUS " + modelo :
            "MSI " + (modelo.equals("MEG Godlike") ? "MEG Z790 Godlike" : modelo);

        assertTrue(
            validarMotherBoard(motherBoard, nombreCompleto, precioEsperado, marca,
                             chipsetEsperado, socketEsperado),
            String.format("El MotherBoard %s no tiene las especificaciones correctas", modelo)
        );
    }

    /**
     * Prueba parametrizada que verifica el manejo de excepciones para modelos de CPU invalidos.
     *
     * <p>Valida que IntelFactory.crearCPU() lance IllegalArgumentException cuando se intenta
     * crear un CPU con un modelo no soportado. Esto incluye:
     * <ul>
     *   <li>Modelos de generaciones anteriores o posteriores no disponibles</li>
     *   <li>Modelos de otros fabricantes (AMD)</li>
     *   <li>Nombres incompletos o incorrectos</li>
     *   <li>Cadenas vacias</li>
     *   <li>Modelos inexistentes</li>
     * </ul>
     *
     * @param modeloInvalido el nombre de un modelo de CPU no soportado
     */
    @ParameterizedTest(name = "CPU Intel invalido: \"{0}\" debe lanzar excepcion")
    @MethodSource("proveedorModelosCPUIntelInvalidos")
    public void pruebaCrearCPUIntelConModeloInvalidoLanzaExcepcion(String modeloInvalido) {
        ComponenteFactory factory = new IntelFactory();

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
     * <p>Valida que IntelFactory.crearMotherBoard() lance IllegalArgumentException cuando se intenta
     * crear un MotherBoard con un modelo no soportado. Esto incluye:
     * <ul>
     *   <li>Modelos de otros fabricantes no disponibles en inventario</li>
     *   <li>Modelos para plataformas AMD</li>
     *   <li>Nombres incompletos (solo chipset o solo marca)</li>
     *   <li>Cadenas vacias</li>
     *   <li>Modelos inexistentes</li>
     * </ul>
     *
     * @param modeloInvalido el nombre de un modelo de MotherBoard no soportado
     */
    @ParameterizedTest(name = "MotherBoard Intel invalido: \"{0}\" debe lanzar excepcion")
    @MethodSource("proveedorModelosMotherBoardIntelInvalidos")
    public void pruebaCrearMotherBoardIntelConModeloInvalidoLanzaExcepcion(String modeloInvalido) {
        ComponenteFactory factory = new IntelFactory();

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
     * Prueba que verifica la consistencia del patron Abstract Factory para CPUs Intel.
     *
     * <p>Valida que multiples llamadas a crearCPU() con el mismo modelo produzcan
     * instancias independientes con las mismas especificaciones (objetos diferentes
     * pero valores iguales).
     */
    @Test
    public void pruebaConsistenciaCreacionMultiplesCPUsIntel() {
        ComponenteFactory factory = new IntelFactory();

        Stream.of("Core i3-13100", "Core i5-13600K", "Core i7-13700K", "Core i9-13900K")
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
            });
    }

    /**
     * Prueba que verifica la consistencia del patron Abstract Factory para MotherBoards Intel.
     *
     * <p>Valida que multiples llamadas a crearMotherBoard() con el mismo modelo produzcan
     * instancias independientes con las mismas especificaciones.
     */
    @Test
    public void pruebaConsistenciaCreacionMultiplesMotherBoardsIntel() {
        ComponenteFactory factory = new IntelFactory();

        Stream.of("ROG Maximus Z790 Hero", "TUF Gaming B760-Plus WIFI D4",
                 "MEG Godlike", "MAG B760 Tomahawk WIFI DDR4")
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
}
