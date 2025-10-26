package modelo.componente;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

/**
 * Clase de pruebas unitarias para Gabinete.
 *
 * <p>Valida la correcta creacion y funcionamiento de componentes Gabinete,
 * asegurando que los gabinetes se creen con las especificaciones correctas
 * y que se manejen adecuadamente los casos de parametros invalidos.
 *
 * <p>Esta suite de pruebas verifica:
 * <ul>
 *   <li>Creacion correcta de gabinetes con todas sus especificaciones (tamanio, precio)</li>
 *   <li>Funcionamiento correcto del getter especifico (getTamanio)</li>
 *   <li>Formato correcto del metodo toString</li>
 *   <li>Manejo de excepciones para parametros invalidos (null, precio negativo)</li>
 *   <li>Herencia correcta de ComponenteHoja</li>
 * </ul>
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class GabineteTest {

    /**
     * Provee argumentos para probar la creacion de gabinetes validos.
     * Retorna un stream de argumentos conteniendo: nombre, precio, marca, tipo y tamanio.
     *
     * @return Stream de argumentos para gabinetes validos
     */
    private static Stream<Arguments> proveedorDatosGabineteValidos() {
        return Stream.of(
            Arguments.of("H6 Flow", 1599.00, "NZXT", "Gabinete", "ATX"),
            Arguments.of("Lancer", 1399.00, "Yeyian", "Gabinete", "ATX"),
            Arguments.of("H510", 1299.00, "NZXT", "Gabinete", "ATX"),
            Arguments.of("4000D Airflow", 1799.00, "Corsair", "Gabinete", "ATX"),
            Arguments.of("Meshify 2", 1999.00, "Fractal Design", "Gabinete", "ATX"),
            Arguments.of("O11 Dynamic", 2499.00, "Lian Li", "Gabinete", "ATX")
        );
    }

    /**
     * Provee datos invalidos para probar el manejo de excepciones en el constructor.
     *
     * @return Stream de argumentos con datos invalidos
     */
    private static Stream<Arguments> proveedorDatosGabineteInvalidos() {
        return Stream.of(
            Arguments.of(null, 1000.0, "NZXT", "Gabinete", "ATX", "nombre null"),
            Arguments.of("H6 Flow", -100.0, "NZXT", "Gabinete", "ATX", "precio negativo"),
            Arguments.of("H6 Flow", 0.0, "NZXT", "Gabinete", "ATX", "precio cero"),
            Arguments.of("H6 Flow", 1000.0, null, "Gabinete", "ATX", "marca null"),
            Arguments.of("H6 Flow", 1000.0, "NZXT", null, "ATX", "tipo null")
        );
    }

    /**
     * Valida que un gabinete tenga las especificaciones esperadas.
     * Esta funcion es pura y no tiene efectos secundarios.
     *
     * @param gabinete el gabinete a validar
     * @param nombreEsperado el nombre esperado
     * @param precioEsperado el precio esperado
     * @param marcaEsperada la marca esperada
     * @param tipoEsperado el tipo esperado
     * @param tamanioEsperado el tamanio esperado
     * @return true si todas las especificaciones coinciden, false en caso contrario
     */
    private static boolean validarGabinete(Gabinete gabinete, String nombreEsperado,
                                          double precioEsperado, String marcaEsperada,
                                          String tipoEsperado, String tamanioEsperado) {
        return gabinete.obtenerNombre().equals(nombreEsperado) &&
               gabinete.obtenerPrecio() == precioEsperado &&
               gabinete.obtenerMarca().equals(marcaEsperada) &&
               gabinete.obtenerTipo().equals(tipoEsperado) &&
               gabinete.getTamanio().equals(tamanioEsperado);
    }

    /**
     * Valida que el formato toString contenga todos los elementos esperados.
     * Esta funcion es pura y no tiene efectos secundarios.
     *
     * @param toString la cadena toString del gabinete
     * @param nombre el nombre que debe contener
     * @param marca la marca que debe contener
     * @param tamanio el tamanio que debe contener
     * @param precio el precio que debe contener
     * @return true si todos los elementos estan presentes, false en caso contrario
     */
    private static boolean validarFormatoToString(String toString, String nombre, String marca,
                                                  String tamanio, double precio) {
        return toString.contains("Gabinete:") &&
               toString.contains(nombre) &&
               toString.contains(marca) &&
               toString.contains(tamanio) &&
               toString.contains(String.format("%.2f", precio));
    }

    /**
     * Prueba parametrizada que valida la creacion correcta de gabinetes con datos validos.
     *
     * <p>Verifica que el constructor de Gabinete cree instancias correctas con:
     * <ul>
     *   <li>Nombre completo del gabinete</li>
     *   <li>Precio segun especificacion</li>
     *   <li>Marca (NZXT, Yeyian, Corsair, etc)</li>
     *   <li>Tipo "Gabinete"</li>
     *   <li>Tamanio (ATX, Micro-ATX, etc)</li>
     * </ul>
     *
     * @param nombre el nombre del gabinete
     * @param precio el precio del gabinete
     * @param marca la marca del gabinete
     * @param tipo el tipo de componente
     * @param tamanio el tamanio del gabinete
     */
    @ParameterizedTest(name = "Gabinete {0}: {4}, ${1}")
    @MethodSource("proveedorDatosGabineteValidos")
    public void pruebaCrearGabineteConDatosValidos(String nombre, double precio, String marca,
                                                    String tipo, String tamanio) {
        Gabinete gabinete = new Gabinete(nombre, precio, marca, tipo, tamanio);

        assertNotNull(gabinete, "El gabinete no debe ser null");
        assertTrue(
            validarGabinete(gabinete, nombre, precio, marca, tipo, tamanio),
            String.format("El gabinete %s no tiene las especificaciones correctas", nombre)
        );
    }

    /**
     * Prueba que valida el funcionamiento del getter getTamanio.
     *
     * <p>Verifica que el metodo getTamanio retorne correctamente
     * el tamanio especificado en el constructor.
     */
    @Test
    public void pruebaGetTamanio() {
        Stream.of("ATX", "Micro-ATX", "Mini-ITX", "E-ATX", "XL-ATX")
            .forEach(tamanio -> {
                Gabinete gabinete = new Gabinete("Test Case", 1000.0, "TestBrand", "Gabinete", tamanio);
                assertEquals(tamanio, gabinete.getTamanio(),
                    String.format("getTamanio debe retornar %s", tamanio));
            });
    }

    /**
     * Prueba parametrizada que valida el formato del metodo toString.
     *
     * <p>Verifica que toString retorne una cadena formateada que contenga:
     * <ul>
     *   <li>Prefijo "Gabinete:"</li>
     *   <li>Nombre del gabinete</li>
     *   <li>Marca del gabinete</li>
     *   <li>Tipo del componente</li>
     *   <li>Tamanio del gabinete</li>
     *   <li>Precio formateado con dos decimales</li>
     * </ul>
     *
     * @param nombre el nombre del gabinete
     * @param precio el precio del gabinete
     * @param marca la marca del gabinete
     * @param tipo el tipo de componente
     * @param tamanio el tamanio del gabinete
     */
    @ParameterizedTest(name = "toString Gabinete {0}")
    @MethodSource("proveedorDatosGabineteValidos")
    public void pruebaFormatoToString(String nombre, double precio, String marca,
                                      String tipo, String tamanio) {
        Gabinete gabinete = new Gabinete(nombre, precio, marca, tipo, tamanio);
        String toString = gabinete.toString();

        assertNotNull(toString, "toString no debe retornar null");
        assertTrue(
            validarFormatoToString(toString, nombre, marca, tamanio, precio),
            String.format("El formato toString no es correcto para gabinete %s", nombre)
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
        Gabinete gabinete = new Gabinete("H6 Flow", 1599.00, "NZXT", "Gabinete", "ATX");

        assertEquals(gabinete.toString(), gabinete.mostrarDetalles(),
            "mostrarDetalles debe retornar el mismo resultado que toString");
    }

    /**
     * Prueba parametrizada que verifica el manejo de excepciones para parametros invalidos.
     *
     * <p>Valida que el constructor de Gabinete lance IllegalArgumentException cuando se intenta
     * crear un gabinete con parametros invalidos. Esto incluye:
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
     * @param tamanio el tamanio
     * @param descripcion descripcion del caso de prueba
     */
    @ParameterizedTest(name = "Excepcion con {5}")
    @MethodSource("proveedorDatosGabineteInvalidos")
    public void pruebaConstructorConDatosInvalidosLanzaExcepcion(String nombre, double precio,
                                                                  String marca, String tipo,
                                                                  String tamanio, String descripcion) {
        IllegalArgumentException excepcion = assertThrows(
            IllegalArgumentException.class,
            () -> new Gabinete(nombre, precio, marca, tipo, tamanio),
            String.format("Deberia lanzar IllegalArgumentException para %s", descripcion)
        );

        assertNotNull(excepcion.getMessage(),
            "El mensaje de error no debe ser null");
    }

    /**
     * Prueba que valida la herencia correcta de ComponenteHoja.
     *
     * <p>Verifica que Gabinete sea una instancia de ComponenteHoja y ComponentePC,
     * validando la jerarquia de herencia correcta.
     */
    @Test
    public void pruebaHerenciaDeComponenteHoja() {
        Gabinete gabinete = new Gabinete("Lancer", 1399.00, "Yeyian", "Gabinete", "ATX");

        assertTrue(gabinete instanceof ComponenteHoja,
            "Gabinete debe ser instancia de ComponenteHoja");
        assertTrue(gabinete instanceof ComponentePC,
            "Gabinete debe ser instancia de ComponentePC");
    }

    /**
     * Prueba que valida que los getters heredados de ComponenteHoja funcionan correctamente.
     *
     * <p>Verifica que obtenerNombre, obtenerPrecio, obtenerMarca y obtenerTipo
     * retornen los valores correctos especificados en el constructor.
     */
    @Test
    public void pruebaGettersHeredadosDeComponenteHoja() {
        String nombre = "O11 Dynamic";
        double precio = 2499.00;
        String marca = "Lian Li";
        String tipo = "Gabinete";
        String tamanio = "ATX";

        Gabinete gabinete = new Gabinete(nombre, precio, marca, tipo, tamanio);

        assertEquals(nombre, gabinete.obtenerNombre(),
            "obtenerNombre debe retornar el nombre especificado");
        assertEquals(precio, gabinete.obtenerPrecio(),
            "obtenerPrecio debe retornar el precio especificado");
        assertEquals(marca, gabinete.obtenerMarca(),
            "obtenerMarca debe retornar la marca especificada");
        assertEquals(tipo, gabinete.obtenerTipo(),
            "obtenerTipo debe retornar el tipo especificado");
    }

    /**
     * Prueba que valida la inmutabilidad aparente de los datos del gabinete.
     *
     * <p>Verifica que multiples llamadas a los getters retornen los mismos valores,
     * asegurando que el estado interno no se modifica inadvertidamente.
     */
    @Test
    public void pruebaInmutabilidadDeGetters() {
        Gabinete gabinete = new Gabinete("Meshify 2", 1999.00, "Fractal Design", "Gabinete", "ATX");

        String nombre1 = gabinete.obtenerNombre();
        String nombre2 = gabinete.obtenerNombre();
        double precio1 = gabinete.obtenerPrecio();
        double precio2 = gabinete.obtenerPrecio();
        String tamanio1 = gabinete.getTamanio();
        String tamanio2 = gabinete.getTamanio();

        assertEquals(nombre1, nombre2, "Multiples llamadas a obtenerNombre deben retornar el mismo valor");
        assertEquals(precio1, precio2, "Multiples llamadas a obtenerPrecio deben retornar el mismo valor");
        assertEquals(tamanio1, tamanio2, "Multiples llamadas a getTamanio deben retornar el mismo valor");
    }

    /**
     * Prueba que valida diferentes tamanios de gabinetes.
     *
     * <p>Verifica que se puedan crear gabinetes con diferentes tamanios,
     * todos validos para diferentes factores de forma.
     */
    @Test
    public void pruebaDiferentesTamaniosDeGabinetes() {
        Stream.of("ATX", "Micro-ATX", "Mini-ITX", "E-ATX", "XL-ATX")
            .forEach(tamanio -> {
                Gabinete gabinete = new Gabinete("Test Case", 1000.0, "TestBrand", "Gabinete", tamanio);
                assertEquals(tamanio, gabinete.getTamanio(),
                    String.format("El gabinete debe tener tamanio %s", tamanio));
            });
    }

    /**
     * Prueba que valida la creacion de gabinetes de diferentes marcas.
     *
     * <p>Verifica que se puedan crear gabinetes de diferentes marcas populares,
     * todas con el mismo tamanio ATX.
     */
    @Test
    public void pruebaDiferentesMarcasDeGabinetes() {
        Stream.of("NZXT", "Yeyian", "Corsair", "Fractal Design", "Lian Li", "Cooler Master")
            .forEach(marca -> {
                Gabinete gabinete = new Gabinete("Test Case", 1000.0, marca, "Gabinete", "ATX");
                assertEquals(marca, gabinete.obtenerMarca(),
                    String.format("El gabinete debe tener marca %s", marca));
            });
    }
}
