package modelo.componente;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

/**
 * Clase de pruebas unitarias para MotherBoard.
 *
 * <p>Valida la correcta creacion y funcionamiento de componentes MotherBoard,
 * asegurando que las placas base se creen con las especificaciones correctas
 * y que se manejen adecuadamente los casos de parametros invalidos.
 *
 * <p>Esta suite de pruebas verifica:
 * <ul>
 *   <li>Creacion correcta de MotherBoards con todas sus especificaciones (chipset, socket, arquitectura, precio)</li>
 *   <li>Funcionamiento correcto de getters especificos (getChipset, getSocket, getArquitecturaSeparada)</li>
 *   <li>Formato correcto del metodo toString</li>
 *   <li>Manejo de excepciones para parametros invalidos (null, precio negativo)</li>
 *   <li>Herencia correcta de ComponenteHoja</li>
 * </ul>
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class MotherBoardTest {

    /**
     * Provee argumentos para probar la creacion de MotherBoards validos.
     * Retorna un stream de argumentos conteniendo: nombre, precio, marca, tipo,
     * chipset, socket y arquitectura soportada.
     *
     * @return Stream de argumentos para MotherBoards validos
     */
    private static Stream<Arguments> proveedorDatosMotherBoardValidos() {
        return Stream.of(
            Arguments.of("ROG Maximus Z790 Hero", 19999.00, "ASUS", "MotherBoard", "Z790", "LGA1700", "x86-64"),
            Arguments.of("TUF Gaming B760-Plus WIFI D4", 8999.00, "ASUS", "MotherBoard", "B760", "LGA1700", "x86-64"),
            Arguments.of("MEG Z790 Godlike", 29999.00, "MSI", "MotherBoard", "Z790", "LGA1700", "x86-64"),
            Arguments.of("MAG B760 Tomahawk WIFI DDR4", 7999.00, "MSI", "MotherBoard", "B760", "LGA1700", "x86-64"),
            Arguments.of("ROG Strix X670E", 16999.00, "ASUS", "MotherBoard", "X670E", "AM5", "x86-64"),
            Arguments.of("TUF Gaming B650", 9999.00, "ASUS", "MotherBoard", "B650", "AM5", "x86-64")
        );
    }

    /**
     * Provee datos invalidos para probar el manejo de excepciones en el constructor.
     *
     * @return Stream de argumentos con datos invalidos
     */
    private static Stream<Arguments> proveedorDatosMotherBoardInvalidos() {
        return Stream.of(
            Arguments.of(null, 1000.0, "ASUS", "MotherBoard", "Z790", "LGA1700", "x86-64", "nombre null"),
            Arguments.of("ROG", -100.0, "ASUS", "MotherBoard", "Z790", "LGA1700", "x86-64", "precio negativo"),
            Arguments.of("ROG", 0.0, "ASUS", "MotherBoard", "Z790", "LGA1700", "x86-64", "precio cero"),
            Arguments.of("ROG", 1000.0, null, "MotherBoard", "Z790", "LGA1700", "x86-64", "marca null"),
            Arguments.of("ROG", 1000.0, "ASUS", null, "Z790", "LGA1700", "x86-64", "tipo null")
        );
    }

    /**
     * Valida que un MotherBoard tenga las especificaciones esperadas.
     * Esta funcion es pura y no tiene efectos secundarios.
     *
     * @param mb el MotherBoard a validar
     * @param nombreEsperado el nombre esperado
     * @param precioEsperado el precio esperado
     * @param marcaEsperada la marca esperada
     * @param tipoEsperado el tipo esperado
     * @param chipsetEsperado el chipset esperado
     * @param socketEsperado el socket esperado
     * @param arquitecturaEsperada la arquitectura esperada
     * @return true si todas las especificaciones coinciden, false en caso contrario
     */
    private static boolean validarMotherBoard(MotherBoard mb, String nombreEsperado, double precioEsperado,
                                              String marcaEsperada, String tipoEsperado,
                                              String chipsetEsperado, String socketEsperado,
                                              String arquitecturaEsperada) {
        return mb.obtenerNombre().equals(nombreEsperado) &&
               mb.obtenerPrecio() == precioEsperado &&
               mb.obtenerMarca().equals(marcaEsperada) &&
               mb.obtenerTipo().equals(tipoEsperado) &&
               mb.getChipset().equals(chipsetEsperado) &&
               mb.getSocket().equals(socketEsperado) &&
               mb.getArquitecturaSeparada().equals(arquitecturaEsperada);
    }

    /**
     * Valida que el formato toString contenga todos los elementos esperados.
     * Esta funcion es pura y no tiene efectos secundarios.
     *
     * @param toString la cadena toString del MotherBoard
     * @param nombre el nombre que debe contener
     * @param marca la marca que debe contener
     * @param chipset el chipset que debe contener
     * @param socket el socket que debe contener
     * @param arquitectura la arquitectura que debe contener
     * @param precio el precio que debe contener
     * @return true si todos los elementos estan presentes, false en caso contrario
     */
    private static boolean validarFormatoToString(String toString, String nombre, String marca,
                                                  String chipset, String socket,
                                                  String arquitectura, double precio) {
        return toString.contains("Motherboard:") &&
               toString.contains(nombre) &&
               toString.contains(marca) &&
               toString.contains(chipset) &&
               toString.contains(socket) &&
               toString.contains(arquitectura) &&
               toString.contains(String.format("%.2f", precio));
    }

    /**
     * Prueba parametrizada que valida la creacion correcta de MotherBoards con datos validos.
     *
     * <p>Verifica que el constructor de MotherBoard cree instancias correctas con:
     * <ul>
     *   <li>Nombre completo de la placa base</li>
     *   <li>Precio segun especificacion</li>
     *   <li>Marca (ASUS, MSI, etc)</li>
     *   <li>Tipo "MotherBoard"</li>
     *   <li>Chipset especifico</li>
     *   <li>Socket compatible</li>
     *   <li>Arquitectura soportada</li>
     * </ul>
     *
     * @param nombre el nombre del MotherBoard
     * @param precio el precio del MotherBoard
     * @param marca la marca del MotherBoard
     * @param tipo el tipo de componente
     * @param chipset el chipset
     * @param socket el socket
     * @param arquitectura la arquitectura soportada
     */
    @ParameterizedTest(name = "MotherBoard {0}: {4} chipset, {5} socket, ${1}")
    @MethodSource("proveedorDatosMotherBoardValidos")
    public void pruebaCrearMotherBoardConDatosValidos(String nombre, double precio, String marca,
                                                       String tipo, String chipset, String socket,
                                                       String arquitectura) {
        MotherBoard mb = new MotherBoard(nombre, precio, marca, tipo, chipset, socket, arquitectura);

        assertNotNull(mb, "El MotherBoard no debe ser null");
        assertTrue(
            validarMotherBoard(mb, nombre, precio, marca, tipo, chipset, socket, arquitectura),
            String.format("El MotherBoard %s no tiene las especificaciones correctas", nombre)
        );
    }

    /**
     * Prueba que valida el funcionamiento del getter getChipset.
     *
     * <p>Verifica que el metodo getChipset retorne correctamente
     * el chipset especificado en el constructor.
     */
    @Test
    public void pruebaGetChipset() {
        Stream.of("Z790", "B760", "X670E", "B650", "Z690", "B550")
            .forEach(chipset -> {
                MotherBoard mb = new MotherBoard("Test MB", 1000.0, "TestBrand", "MotherBoard",
                                                  chipset, "LGA1700", "x86-64");
                assertEquals(chipset, mb.getChipset(),
                    String.format("getChipset debe retornar %s", chipset));
            });
    }

    /**
     * Prueba que valida el funcionamiento del getter getSocket.
     *
     * <p>Verifica que el metodo getSocket retorne correctamente
     * el socket especificado en el constructor.
     */
    @Test
    public void pruebaGetSocket() {
        Stream.of("LGA1700", "AM5", "LGA1200", "AM4", "TR4")
            .forEach(socket -> {
                MotherBoard mb = new MotherBoard("Test MB", 1000.0, "TestBrand", "MotherBoard",
                                                  "Z790", socket, "x86-64");
                assertEquals(socket, mb.getSocket(),
                    String.format("getSocket debe retornar %s", socket));
            });
    }

    /**
     * Prueba que valida el funcionamiento del getter getArquitecturaSeparada.
     *
     * <p>Verifica que el metodo getArquitecturaSeparada retorne correctamente
     * la arquitectura especificada en el constructor.
     */
    @Test
    public void pruebaGetArquitecturaSeparada() {
        Stream.of("x86-64", "x86-64 (AMD64)", "ARM64")
            .forEach(arquitectura -> {
                MotherBoard mb = new MotherBoard("Test MB", 1000.0, "TestBrand", "MotherBoard",
                                                  "Z790", "LGA1700", arquitectura);
                assertEquals(arquitectura, mb.getArquitecturaSeparada(),
                    String.format("getArquitecturaSeparada debe retornar %s", arquitectura));
            });
    }

    /**
     * Prueba parametrizada que valida el formato del metodo toString.
     *
     * <p>Verifica que toString retorne una cadena formateada que contenga:
     * <ul>
     *   <li>Prefijo "Motherboard:"</li>
     *   <li>Nombre del MotherBoard</li>
     *   <li>Marca del MotherBoard</li>
     *   <li>Chipset</li>
     *   <li>Socket</li>
     *   <li>Arquitectura</li>
     *   <li>Precio formateado con dos decimales</li>
     * </ul>
     *
     * @param nombre el nombre del MotherBoard
     * @param precio el precio del MotherBoard
     * @param marca la marca del MotherBoard
     * @param tipo el tipo de componente
     * @param chipset el chipset
     * @param socket el socket
     * @param arquitectura la arquitectura soportada
     */
    @ParameterizedTest(name = "toString MotherBoard {0}")
    @MethodSource("proveedorDatosMotherBoardValidos")
    public void pruebaFormatoToString(String nombre, double precio, String marca,
                                      String tipo, String chipset, String socket,
                                      String arquitectura) {
        MotherBoard mb = new MotherBoard(nombre, precio, marca, tipo, chipset, socket, arquitectura);
        String toString = mb.toString();

        assertNotNull(toString, "toString no debe retornar null");
        assertTrue(
            validarFormatoToString(toString, nombre, marca, chipset, socket, arquitectura, precio),
            String.format("El formato toString no es correcto para MotherBoard %s", nombre)
        );
    }

    /**
     * Prueba que valida el metodo mostrarDetalles hereda de ComponenteHoja.
     *
     * <p>Verifica que mostrarDetalles retorne el mismo resultado que toString,
     * validando la herencia correcta de ComponenteHoja.
     */
    @Test
    public void pruebaMostrarDetallesHeredaDeToString() {
        MotherBoard mb = new MotherBoard("ROG Maximus Z790 Hero", 19999.00, "ASUS", "MotherBoard",
                                         "Z790", "LGA1700", "x86-64");

        assertEquals(mb.toString(), mb.mostrarDetalles(),
            "mostrarDetalles debe retornar el mismo resultado que toString");
    }

    /**
     * Prueba parametrizada que verifica el manejo de excepciones para parametros invalidos.
     *
     * <p>Valida que el constructor de MotherBoard lance IllegalArgumentException cuando se intenta
     * crear un MotherBoard con parametros invalidos. Esto incluye:
     * <ul>
     *   <li>Nombre null</li>
     *   <li>Precio negativo o cero</li>
     *   <li>Marca null</li>
     *   <li>Tipo null</li>
     * </ul>
     *
     * @param nombre el nombre (posiblemente null)
     * @param precio el precio (posiblemente negativo o cero)
     * @param marca la marca (posiblemente null)
     * @param tipo el tipo (posiblemente null)
     * @param chipset el chipset
     * @param socket el socket
     * @param arquitectura la arquitectura
     * @param descripcion descripcion del caso de prueba
     */
    @ParameterizedTest(name = "Excepcion con {7}")
    @MethodSource("proveedorDatosMotherBoardInvalidos")
    public void pruebaConstructorConDatosInvalidosLanzaExcepcion(String nombre, double precio,
                                                                  String marca, String tipo,
                                                                  String chipset, String socket,
                                                                  String arquitectura, String descripcion) {
        IllegalArgumentException excepcion = assertThrows(
            IllegalArgumentException.class,
            () -> new MotherBoard(nombre, precio, marca, tipo, chipset, socket, arquitectura),
            String.format("Deberia lanzar IllegalArgumentException para %s", descripcion)
        );

        assertNotNull(excepcion.getMessage(),
            "El mensaje de error no debe ser null");
    }

    /**
     * Prueba que valida la herencia correcta de ComponenteHoja.
     *
     * <p>Verifica que MotherBoard sea una instancia de ComponenteHoja y ComponentePC,
     * validando la jerarquia de herencia correcta.
     */
    @Test
    public void pruebaHerenciaDeComponenteHoja() {
        MotherBoard mb = new MotherBoard("TUF Gaming B760-Plus WIFI D4", 8999.00, "ASUS", "MotherBoard",
                                         "B760", "LGA1700", "x86-64");

        assertTrue(mb instanceof ComponenteHoja,
            "MotherBoard debe ser instancia de ComponenteHoja");
        assertTrue(mb instanceof ComponentePC,
            "MotherBoard debe ser instancia de ComponentePC");
    }

    /**
     * Prueba que valida que los getters heredados de ComponenteHoja funcionan correctamente.
     *
     * <p>Verifica que obtenerNombre, obtenerPrecio, obtenerMarca y obtenerTipo
     * retornen los valores correctos especificados en el constructor.
     */
    @Test
    public void pruebaGettersHeredadosDeComponenteHoja() {
        String nombre = "MEG Z790 Godlike";
        double precio = 29999.00;
        String marca = "MSI";
        String tipo = "MotherBoard";
        String chipset = "Z790";
        String socket = "LGA1700";
        String arquitectura = "x86-64";

        MotherBoard mb = new MotherBoard(nombre, precio, marca, tipo, chipset, socket, arquitectura);

        assertEquals(nombre, mb.obtenerNombre(),
            "obtenerNombre debe retornar el nombre especificado");
        assertEquals(precio, mb.obtenerPrecio(),
            "obtenerPrecio debe retornar el precio especificado");
        assertEquals(marca, mb.obtenerMarca(),
            "obtenerMarca debe retornar la marca especificada");
        assertEquals(tipo, mb.obtenerTipo(),
            "obtenerTipo debe retornar el tipo especificado");
    }

    /**
     * Prueba que valida la inmutabilidad aparente de los datos del MotherBoard.
     *
     * <p>Verifica que multiples llamadas a los getters retornen los mismos valores,
     * asegurando que el estado interno no se modifica inadvertidamente.
     */
    @Test
    public void pruebaInmutabilidadDeGetters() {
        MotherBoard mb = new MotherBoard("ROG Strix X670E", 16999.00, "ASUS", "MotherBoard",
                                         "X670E", "AM5", "x86-64");

        String nombre1 = mb.obtenerNombre();
        String nombre2 = mb.obtenerNombre();
        double precio1 = mb.obtenerPrecio();
        double precio2 = mb.obtenerPrecio();
        String chipset1 = mb.getChipset();
        String chipset2 = mb.getChipset();
        String socket1 = mb.getSocket();
        String socket2 = mb.getSocket();
        String arquitectura1 = mb.getArquitecturaSeparada();
        String arquitectura2 = mb.getArquitecturaSeparada();

        assertEquals(nombre1, nombre2, "Multiples llamadas a obtenerNombre deben retornar el mismo valor");
        assertEquals(precio1, precio2, "Multiples llamadas a obtenerPrecio deben retornar el mismo valor");
        assertEquals(chipset1, chipset2, "Multiples llamadas a getChipset deben retornar el mismo valor");
        assertEquals(socket1, socket2, "Multiples llamadas a getSocket deben retornar el mismo valor");
        assertEquals(arquitectura1, arquitectura2, "Multiples llamadas a getArquitecturaSeparada deben retornar el mismo valor");
    }

    /**
     * Prueba que valida diferentes combinaciones de chipset, socket y arquitectura.
     *
     * <p>Verifica que se puedan crear MotherBoards con diferentes combinaciones
     * de especificaciones, todas validas.
     */
    @Test
    public void pruebaCombinacionesChipsetSocketArquitectura() {
        Stream.of(
            Arguments.of("Z790", "LGA1700", "x86-64"),
            Arguments.of("B650", "AM5", "x86-64"),
            Arguments.of("X670E", "AM5", "x86-64"),
            Arguments.of("B760", "LGA1700", "x86-64")
        ).forEach(args -> {
            String chipset = (String) args.get()[0];
            String socket = (String) args.get()[1];
            String arquitectura = (String) args.get()[2];

            MotherBoard mb = new MotherBoard("Test MB", 1000.0, "TestBrand", "MotherBoard",
                                             chipset, socket, arquitectura);

            assertEquals(chipset, mb.getChipset());
            assertEquals(socket, mb.getSocket());
            assertEquals(arquitectura, mb.getArquitecturaSeparada());
        });
    }
}
