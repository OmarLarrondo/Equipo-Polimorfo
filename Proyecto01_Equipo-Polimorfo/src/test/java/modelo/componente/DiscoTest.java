package modelo.componente;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

/**
 * Clase de pruebas unitarias para Disco.
 *
 * <p>Valida la correcta creacion y funcionamiento de componentes Disco,
 * asegurando que los discos de almacenamiento se creen con las especificaciones correctas
 * y que se manejen adecuadamente los casos de parametros invalidos.
 *
 * <p>Esta suite de pruebas verifica:
 * <ul>
 *   <li>Creacion correcta de discos con todas sus especificaciones (capacidad, tipo de alimentacion, precio)</li>
 *   <li>Funcionamiento correcto de getters especificos (getCapacidadAlmacenamiento, getTipoAlimentacion)</li>
 *   <li>Formato correcto del metodo toString</li>
 *   <li>Manejo de excepciones para parametros invalidos (null, precio negativo)</li>
 *   <li>Herencia correcta de ComponenteHoja</li>
 * </ul>
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class DiscoTest {

    /**
     * Provee argumentos para probar la creacion de discos validos.
     * Retorna un stream de argumentos conteniendo: nombre, precio, marca, tipo,
     * capacidad de almacenamiento y tipo de alimentacion.
     *
     * @return Stream de argumentos para discos validos
     */
    private static Stream<Arguments> proveedorDatosDiscoValidos() {
        return Stream.of(
            Arguments.of("Blue 500GB", 899.00, "Western Digital", "Disco", 500, "HDD"),
            Arguments.of("Blue 1TB", 1299.00, "Western Digital", "Disco", 1000, "HDD"),
            Arguments.of("Barracuda 1TB", 1199.00, "Seagate", "Disco", 1000, "HDD"),
            Arguments.of("Barracuda 2TB", 1899.00, "Seagate", "Disco", 2000, "HDD"),
            Arguments.of("A400 500GB", 1299.00, "Kingston", "Disco", 500, "SSD"),
            Arguments.of("A400 1TB", 2199.00, "Kingston", "Disco", 1000, "SSD"),
            Arguments.of("A400 2TB", 3999.00, "Kingston", "Disco", 2000, "SSD"),
            Arguments.of("A400 4TB", 7499.00, "Kingston", "Disco", 4000, "SSD")
        );
    }

    /**
     * Provee datos invalidos para probar el manejo de excepciones en el constructor.
     *
     * @return Stream de argumentos con datos invalidos
     */
    private static Stream<Arguments> proveedorDatosDiscoInvalidos() {
        return Stream.of(
            Arguments.of(null, 1000.0, "Kingston", "Disco", 500, "SSD", "nombre null"),
            Arguments.of("A400", -100.0, "Kingston", "Disco", 500, "SSD", "precio negativo"),
            Arguments.of("A400", 0.0, "Kingston", "Disco", 500, "SSD", "precio cero"),
            Arguments.of("A400", 1000.0, null, "Disco", 500, "SSD", "marca null"),
            Arguments.of("A400", 1000.0, "Kingston", null, 500, "SSD", "tipo null")
        );
    }

    /**
     * Valida que un disco tenga las especificaciones esperadas.
     * Esta funcion es pura y no tiene efectos secundarios.
     *
     * @param disco el disco a validar
     * @param nombreEsperado el nombre esperado
     * @param precioEsperado el precio esperado
     * @param marcaEsperada la marca esperada
     * @param tipoEsperado el tipo esperado
     * @param capacidadEsperada la capacidad de almacenamiento esperada
     * @param tipoAlimentacionEsperada el tipo de alimentacion esperado
     * @return true si todas las especificaciones coinciden, false en caso contrario
     */
    private static boolean validarDisco(Disco disco, String nombreEsperado, double precioEsperado,
                                        String marcaEsperada, String tipoEsperado,
                                        int capacidadEsperada, String tipoAlimentacionEsperada) {
        return disco.obtenerNombre().equals(nombreEsperado) &&
               disco.obtenerPrecio() == precioEsperado &&
               disco.obtenerMarca().equals(marcaEsperada) &&
               disco.obtenerTipo().equals(tipoEsperado) &&
               disco.getCapacidadAlmacenamiento() == capacidadEsperada &&
               disco.getTipoAlimentacion().equals(tipoAlimentacionEsperada);
    }

    /**
     * Valida que el formato toString contenga todos los elementos esperados.
     * Esta funcion es pura y no tiene efectos secundarios.
     *
     * @param toString la cadena toString del disco
     * @param nombre el nombre que debe contener
     * @param marca la marca que debe contener
     * @param capacidad capacidad de almacenamiento que debe contener
     * @param tipoAlimentacion el tipo de alimentacion que debe contener
     * @param precio el precio que debe contener
     * @return true si todos los elementos estan presentes, false en caso contrario
     */
    private static boolean validarFormatoToString(String toString, String nombre, String marca,
                                                  int capacidad, String tipoAlimentacion, double precio) {
        return toString.contains("Disco:") &&
               toString.contains(nombre) &&
               toString.contains(marca) &&
               toString.contains(String.valueOf(capacidad)) &&
               toString.contains(tipoAlimentacion) &&
               toString.contains(String.format("%.2f", precio));
    }

    /**
     * Prueba parametrizada que valida la creacion correcta de discos con datos validos.
     *
     * <p>Verifica que el constructor de Disco cree instancias correctas con:
     * <ul>
     *   <li>Nombre completo del disco</li>
     *   <li>Precio segun especificacion</li>
     *   <li>Marca (Western Digital, Seagate, Kingston, etc)</li>
     *   <li>Tipo "Disco"</li>
     *   <li>Capacidad de almacenamiento en GB</li>
     *   <li>Tipo de alimentacion (HDD, SSD)</li>
     * </ul>
     *
     * @param nombre el nombre del disco
     * @param precio el precio del disco
     * @param marca la marca del disco
     * @param tipo el tipo de componente
     * @param capacidad la capacidad de almacenamiento
     * @param tipoAlimentacion el tipo de alimentacion
     */
    @ParameterizedTest(name = "Disco {0}: {4}GB {5}, ${1}")
    @MethodSource("proveedorDatosDiscoValidos")
    public void pruebaCrearDiscoConDatosValidos(String nombre, double precio, String marca,
                                                String tipo, int capacidad, String tipoAlimentacion) {
        Disco disco = new Disco(nombre, precio, marca, tipo, capacidad, tipoAlimentacion);

        assertNotNull(disco, "El disco no debe ser null");
        assertTrue(
            validarDisco(disco, nombre, precio, marca, tipo, capacidad, tipoAlimentacion),
            String.format("El disco %s no tiene las especificaciones correctas", nombre)
        );
    }

    /**
     * Prueba que valida el funcionamiento del getter getCapacidadAlmacenamiento.
     *
     * <p>Verifica que el metodo getCapacidadAlmacenamiento retorne correctamente
     * la capacidad especificada en el constructor.
     */
    @Test
    public void pruebaGetCapacidadAlmacenamiento() {
        Stream.of(256, 500, 1000, 2000, 4000, 8000)
            .forEach(capacidad -> {
                Disco disco = new Disco("Test Disco", 1000.0, "TestBrand", "Disco", capacidad, "SSD");
                assertEquals(capacidad, disco.getCapacidadAlmacenamiento(),
                    String.format("getCapacidadAlmacenamiento debe retornar %d", capacidad));
            });
    }

    /**
     * Prueba que valida el funcionamiento del getter getTipoAlimentacion.
     *
     * <p>Verifica que el metodo getTipoAlimentacion retorne correctamente
     * el tipo de alimentacion especificado en el constructor.
     */
    @Test
    public void pruebaGetTipoAlimentacion() {
        Stream.of("HDD", "SSD", "M.2", "NVMe", "SATA")
            .forEach(tipoAlimentacion -> {
                Disco disco = new Disco("Test Disco", 1000.0, "TestBrand", "Disco", 1000, tipoAlimentacion);
                assertEquals(tipoAlimentacion, disco.getTipoAlimentacion(),
                    String.format("getTipoAlimentacion debe retornar %s", tipoAlimentacion));
            });
    }

    /**
     * Prueba parametrizada que valida el formato del metodo toString.
     *
     * <p>Verifica que toString retorne una cadena formateada que contenga:
     * <ul>
     *   <li>Prefijo "Disco:"</li>
     *   <li>Nombre del disco</li>
     *   <li>Marca del disco</li>
     *   <li>Tipo del componente</li>
     *   <li>Capacidad de almacenamiento en GB</li>
     *   <li>Tipo de alimentacion</li>
     *   <li>Precio formateado con dos decimales</li>
     * </ul>
     *
     * @param nombre el nombre del disco
     * @param precio el precio del disco
     * @param marca la marca del disco
     * @param tipo el tipo de componente
     * @param capacidad la capacidad de almacenamiento
     * @param tipoAlimentacion el tipo de alimentacion
     */
    @ParameterizedTest(name = "toString Disco {0}")
    @MethodSource("proveedorDatosDiscoValidos")
    public void pruebaFormatoToString(String nombre, double precio, String marca,
                                      String tipo, int capacidad, String tipoAlimentacion) {
        Disco disco = new Disco(nombre, precio, marca, tipo, capacidad, tipoAlimentacion);
        String toString = disco.toString();

        assertNotNull(toString, "toString no debe retornar null");
        assertTrue(
            validarFormatoToString(toString, nombre, marca, capacidad, tipoAlimentacion, precio),
            String.format("El formato toString no es correcto para disco %s", nombre)
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
        Disco disco = new Disco("A400 1TB", 2199.00, "Kingston", "Disco", 1000, "SSD");

        assertEquals(disco.toString(), disco.mostrarDetalles(),
            "mostrarDetalles debe retornar el mismo resultado que toString");
    }

    /**
     * Prueba parametrizada que verifica el manejo de excepciones para parametros invalidos.
     *
     * <p>Valida que el constructor de Disco lance IllegalArgumentException cuando se intenta
     * crear un disco con parametros invalidos. Esto incluye:
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
     * @param capacidad la capacidad de almacenamiento
     * @param tipoAlimentacion el tipo de alimentacion
     * @param descripcion descripcion del caso de prueba
     */
    @ParameterizedTest(name = "Excepcion con {6}")
    @MethodSource("proveedorDatosDiscoInvalidos")
    public void pruebaConstructorConDatosInvalidosLanzaExcepcion(String nombre, double precio,
                                                                  String marca, String tipo,
                                                                  int capacidad, String tipoAlimentacion,
                                                                  String descripcion) {
        IllegalArgumentException excepcion = assertThrows(
            IllegalArgumentException.class,
            () -> new Disco(nombre, precio, marca, tipo, capacidad, tipoAlimentacion),
            String.format("Deberia lanzar IllegalArgumentException para %s", descripcion)
        );

        assertNotNull(excepcion.getMessage(),
            "El mensaje de error no debe ser null");
    }

    /**
     * Prueba que valida la herencia correcta de ComponenteHoja.
     *
     * <p>Verifica que Disco sea una instancia de ComponenteHoja y ComponentePC,
     * validando la jerarquia de herencia correcta.
     */
    @Test
    public void pruebaHerenciaDeComponenteHoja() {
        Disco disco = new Disco("Blue 1TB", 1299.00, "Western Digital", "Disco", 1000, "HDD");

        assertTrue(disco instanceof ComponenteHoja,
            "Disco debe ser instancia de ComponenteHoja");
        assertTrue(disco instanceof ComponentePC,
            "Disco debe ser instancia de ComponentePC");
    }

    /**
     * Prueba que valida que los getters heredados de ComponenteHoja funcionan correctamente.
     *
     * <p>Verifica que obtenerNombre, obtenerPrecio, obtenerMarca y obtenerTipo
     * retornen los valores correctos especificados en el constructor.
     */
    @Test
    public void pruebaGettersHeredadosDeComponenteHoja() {
        String nombre = "Barracuda 2TB";
        double precio = 1899.00;
        String marca = "Seagate";
        String tipo = "Disco";
        int capacidad = 2000;
        String tipoAlimentacion = "HDD";

        Disco disco = new Disco(nombre, precio, marca, tipo, capacidad, tipoAlimentacion);

        assertEquals(nombre, disco.obtenerNombre(),
            "obtenerNombre debe retornar el nombre especificado");
        assertEquals(precio, disco.obtenerPrecio(),
            "obtenerPrecio debe retornar el precio especificado");
        assertEquals(marca, disco.obtenerMarca(),
            "obtenerMarca debe retornar la marca especificada");
        assertEquals(tipo, disco.obtenerTipo(),
            "obtenerTipo debe retornar el tipo especificado");
    }

    /**
     * Prueba que valida la inmutabilidad aparente de los datos del disco.
     *
     * <p>Verifica que multiples llamadas a los getters retornen los mismos valores,
     * asegurando que el estado interno no se modifica inadvertidamente.
     */
    @Test
    public void pruebaInmutabilidadDeGetters() {
        Disco disco = new Disco("A400 4TB", 7499.00, "Kingston", "Disco", 4000, "SSD");

        String nombre1 = disco.obtenerNombre();
        String nombre2 = disco.obtenerNombre();
        double precio1 = disco.obtenerPrecio();
        double precio2 = disco.obtenerPrecio();
        int capacidad1 = disco.getCapacidadAlmacenamiento();
        int capacidad2 = disco.getCapacidadAlmacenamiento();
        String tipoAlimentacion1 = disco.getTipoAlimentacion();
        String tipoAlimentacion2 = disco.getTipoAlimentacion();

        assertEquals(nombre1, nombre2, "Multiples llamadas a obtenerNombre deben retornar el mismo valor");
        assertEquals(precio1, precio2, "Multiples llamadas a obtenerPrecio deben retornar el mismo valor");
        assertEquals(capacidad1, capacidad2, "Multiples llamadas a getCapacidadAlmacenamiento deben retornar el mismo valor");
        assertEquals(tipoAlimentacion1, tipoAlimentacion2, "Multiples llamadas a getTipoAlimentacion deben retornar el mismo valor");
    }

    /**
     * Prueba que valida diferentes combinaciones de capacidad y tipo de alimentacion.
     *
     * <p>Verifica que se puedan crear discos con diferentes combinaciones
     * de capacidad y tipo de alimentacion, todas validas.
     */
    @Test
    public void pruebaCombinacionesCapacidadYTipoAlimentacion() {
        Stream.of(
            Arguments.of(500, "HDD"),
            Arguments.of(1000, "SSD"),
            Arguments.of(2000, "HDD"),
            Arguments.of(4000, "SSD")
        ).forEach(args -> {
            int capacidad = (int) args.get()[0];
            String tipoAlimentacion = (String) args.get()[1];

            Disco disco = new Disco("Test Disco", 1000.0, "TestBrand", "Disco", capacidad, tipoAlimentacion);

            assertEquals(capacidad, disco.getCapacidadAlmacenamiento());
            assertEquals(tipoAlimentacion, disco.getTipoAlimentacion());
        });
    }
}
