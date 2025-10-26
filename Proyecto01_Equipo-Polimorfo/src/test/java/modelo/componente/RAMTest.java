package modelo.componente;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

/**
 * Clase de pruebas unitarias para RAM.
 *
 * <p>Valida la correcta creacion y funcionamiento de componentes RAM,
 * asegurando que los modulos de memoria se creen con las especificaciones correctas
 * y que se manejen adecuadamente los casos de parametros invalidos.
 *
 * <p>Esta suite de pruebas verifica:
 * <ul>
 *   <li>Creacion correcta de modulos RAM con todas sus especificaciones (capacidad, tipo de memoria, precio)</li>
 *   <li>Funcionamiento correcto de getters especificos (getCapacidadGB, getTipoMemoria)</li>
 *   <li>Formato correcto del metodo toString</li>
 *   <li>Manejo de excepciones para parametros invalidos (null, precio negativo)</li>
 *   <li>Herencia correcta de ComponenteHoja</li>
 * </ul>
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class RAMTest {

    /**
     * Provee argumentos para probar la creacion de modulos RAM validos.
     * Retorna un stream de argumentos conteniendo: nombre, precio, marca, tipo,
     * capacidad en GB y tipo de memoria.
     *
     * @return Stream de argumentos para RAMs validos
     */
    private static Stream<Arguments> proveedorDatosRAMValidos() {
        return Stream.of(
            Arguments.of("HyperX Fury 8GB", 899.00, "Kingston", "RAM", 8, "DDR4"),
            Arguments.of("HyperX Fury 16GB", 1599.00, "Kingston", "RAM", 16, "DDR4"),
            Arguments.of("HyperX Fury 32GB", 2999.00, "Kingston", "RAM", 32, "DDR4"),
            Arguments.of("XPG Spectrix 8GB", 950.00, "Adata", "RAM", 8, "DDR4"),
            Arguments.of("XPG Spectrix 16GB", 1699.00, "Adata", "RAM", 16, "DDR4"),
            Arguments.of("XPG Spectrix 32GB", 3199.00, "Adata", "RAM", 32, "DDR4"),
            Arguments.of("Corsair Vengeance 16GB", 1799.00, "Corsair", "RAM", 16, "DDR5"),
            Arguments.of("G.Skill Trident Z5 32GB", 3499.00, "G.Skill", "RAM", 32, "DDR5")
        );
    }

    /**
     * Provee datos invalidos para probar el manejo de excepciones en el constructor.
     *
     * @return Stream de argumentos con datos invalidos
     */
    private static Stream<Arguments> proveedorDatosRAMInvalidos() {
        return Stream.of(
            Arguments.of(null, 1000.0, "Kingston", "RAM", 16, "DDR4", "nombre null"),
            Arguments.of("HyperX", -100.0, "Kingston", "RAM", 16, "DDR4", "precio negativo"),
            Arguments.of("HyperX", 0.0, "Kingston", "RAM", 16, "DDR4", "precio cero"),
            Arguments.of("HyperX", 1000.0, null, "RAM", 16, "DDR4", "marca null"),
            Arguments.of("HyperX", 1000.0, "Kingston", null, 16, "DDR4", "tipo null")
        );
    }

    /**
     * Valida que un modulo RAM tenga las especificaciones esperadas.
     * Esta funcion es pura y no tiene efectos secundarios.
     *
     * @param ram el modulo RAM a validar
     * @param nombreEsperado el nombre esperado
     * @param precioEsperado el precio esperado
     * @param marcaEsperada la marca esperada
     * @param tipoEsperado el tipo esperado
     * @param capacidadEsperada la capacidad en GB esperada
     * @param tipoMemoriaEsperada el tipo de memoria esperada
     * @return true si todas las especificaciones coinciden, false en caso contrario
     */
    private static boolean validarRAM(RAM ram, String nombreEsperado, double precioEsperado,
                                      String marcaEsperada, String tipoEsperado,
                                      int capacidadEsperada, String tipoMemoriaEsperada) {
        return ram.obtenerNombre().equals(nombreEsperado) &&
               ram.obtenerPrecio() == precioEsperado &&
               ram.obtenerMarca().equals(marcaEsperada) &&
               ram.obtenerTipo().equals(tipoEsperado) &&
               ram.getCapacidadGB() == capacidadEsperada &&
               ram.getTipoMemoria().equals(tipoMemoriaEsperada);
    }

    /**
     * Valida que el formato toString contenga todos los elementos esperados.
     * Esta funcion es pura y no tiene efectos secundarios.
     *
     * @param toString la cadena toString de la RAM
     * @param nombre el nombre que debe contener
     * @param marca la marca que debe contener
     * @param capacidad capacidad en GB que debe contener
     * @param tipoMemoria el tipo de memoria que debe contener
     * @param precio el precio que debe contener
     * @return true si todos los elementos estan presentes, false en caso contrario
     */
    private static boolean validarFormatoToString(String toString, String nombre, String marca,
                                                  int capacidad, String tipoMemoria, double precio) {
        return toString.contains("RAM:") &&
               toString.contains(nombre) &&
               toString.contains(marca) &&
               toString.contains(String.valueOf(capacidad)) &&
               toString.contains(tipoMemoria) &&
               toString.contains(String.format("%.2f", precio));
    }

    /**
     * Prueba parametrizada que valida la creacion correcta de modulos RAM con datos validos.
     *
     * <p>Verifica que el constructor de RAM cree instancias correctas con:
     * <ul>
     *   <li>Nombre completo del modulo</li>
     *   <li>Precio segun especificacion</li>
     *   <li>Marca (Kingston, Adata, etc)</li>
     *   <li>Tipo "RAM"</li>
     *   <li>Capacidad en GB especifica</li>
     *   <li>Tipo de memoria (DDR4, DDR5, etc)</li>
     * </ul>
     *
     * @param nombre el nombre de la RAM
     * @param precio el precio de la RAM
     * @param marca la marca de la RAM
     * @param tipo el tipo de componente
     * @param capacidad la capacidad en GB
     * @param tipoMemoria el tipo de memoria
     */
    @ParameterizedTest(name = "RAM {0}: {4}GB {5}, ${1}")
    @MethodSource("proveedorDatosRAMValidos")
    public void pruebaCrearRAMConDatosValidos(String nombre, double precio, String marca,
                                              String tipo, int capacidad, String tipoMemoria) {
        RAM ram = new RAM(nombre, precio, marca, tipo, capacidad, tipoMemoria);

        assertNotNull(ram, "La RAM no debe ser null");
        assertTrue(
            validarRAM(ram, nombre, precio, marca, tipo, capacidad, tipoMemoria),
            String.format("La RAM %s no tiene las especificaciones correctas", nombre)
        );
    }

    /**
     * Prueba que valida el funcionamiento del getter getCapacidadGB.
     *
     * <p>Verifica que el metodo getCapacidadGB retorne correctamente
     * la capacidad en GB especificada en el constructor.
     */
    @Test
    public void pruebaGetCapacidadGB() {
        Stream.of(4, 8, 16, 32, 64, 128)
            .forEach(capacidad -> {
                RAM ram = new RAM("Test RAM", 1000.0, "TestBrand", "RAM", capacidad, "DDR4");
                assertEquals(capacidad, ram.getCapacidadGB(),
                    String.format("getCapacidadGB debe retornar %d", capacidad));
            });
    }

    /**
     * Prueba que valida el funcionamiento del getter getTipoMemoria.
     *
     * <p>Verifica que el metodo getTipoMemoria retorne correctamente
     * el tipo de memoria especificado en el constructor.
     */
    @Test
    public void pruebaGetTipoMemoria() {
        Stream.of("DDR3", "DDR4", "DDR5", "LPDDR4", "LPDDR5")
            .forEach(tipoMemoria -> {
                RAM ram = new RAM("Test RAM", 1000.0, "TestBrand", "RAM", 16, tipoMemoria);
                assertEquals(tipoMemoria, ram.getTipoMemoria(),
                    String.format("getTipoMemoria debe retornar %s", tipoMemoria));
            });
    }

    /**
     * Prueba parametrizada que valida el formato del metodo toString.
     *
     * <p>Verifica que toString retorne una cadena formateada que contenga:
     * <ul>
     *   <li>Prefijo "RAM:"</li>
     *   <li>Nombre del modulo</li>
     *   <li>Marca del modulo</li>
     *   <li>Tipo de memoria</li>
     *   <li>Capacidad en GB</li>
     *   <li>Precio formateado con dos decimales</li>
     * </ul>
     *
     * @param nombre el nombre de la RAM
     * @param precio el precio de la RAM
     * @param marca la marca de la RAM
     * @param tipo el tipo de componente
     * @param capacidad la capacidad en GB
     * @param tipoMemoria el tipo de memoria
     */
    @ParameterizedTest(name = "toString RAM {0}")
    @MethodSource("proveedorDatosRAMValidos")
    public void pruebaFormatoToString(String nombre, double precio, String marca,
                                      String tipo, int capacidad, String tipoMemoria) {
        RAM ram = new RAM(nombre, precio, marca, tipo, capacidad, tipoMemoria);
        String toString = ram.toString();

        assertNotNull(toString, "toString no debe retornar null");
        assertTrue(
            validarFormatoToString(toString, nombre, marca, capacidad, tipoMemoria, precio),
            String.format("El formato toString no es correcto para RAM %s", nombre)
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
        RAM ram = new RAM("HyperX Fury 16GB", 1599.00, "Kingston", "RAM", 16, "DDR4");

        assertEquals(ram.toString(), ram.mostrarDetalles(),
            "mostrarDetalles debe retornar el mismo resultado que toString");
    }

    /**
     * Prueba parametrizada que verifica el manejo de excepciones para parametros invalidos.
     *
     * <p>Valida que el constructor de RAM lance IllegalArgumentException cuando se intenta
     * crear una RAM con parametros invalidos. Esto incluye:
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
     * @param capacidad la capacidad en GB
     * @param tipoMemoria el tipo de memoria
     * @param descripcion descripcion del caso de prueba
     */
    @ParameterizedTest(name = "Excepcion con {6}")
    @MethodSource("proveedorDatosRAMInvalidos")
    public void pruebaConstructorConDatosInvalidosLanzaExcepcion(String nombre, double precio,
                                                                  String marca, String tipo,
                                                                  int capacidad, String tipoMemoria,
                                                                  String descripcion) {
        IllegalArgumentException excepcion = assertThrows(
            IllegalArgumentException.class,
            () -> new RAM(nombre, precio, marca, tipo, capacidad, tipoMemoria),
            String.format("Deberia lanzar IllegalArgumentException para %s", descripcion)
        );

        assertNotNull(excepcion.getMessage(),
            "El mensaje de error no debe ser null");
    }

    /**
     * Prueba que valida la herencia correcta de ComponenteHoja.
     *
     * <p>Verifica que RAM sea una instancia de ComponenteHoja y ComponentePC,
     * validando la jerarquia de herencia correcta.
     */
    @Test
    public void pruebaHerenciaDeComponenteHoja() {
        RAM ram = new RAM("HyperX Fury 16GB", 1599.00, "Kingston", "RAM", 16, "DDR4");

        assertTrue(ram instanceof ComponenteHoja,
            "RAM debe ser instancia de ComponenteHoja");
        assertTrue(ram instanceof ComponentePC,
            "RAM debe ser instancia de ComponentePC");
    }

    /**
     * Prueba que valida que los getters heredados de ComponenteHoja funcionan correctamente.
     *
     * <p>Verifica que obtenerNombre, obtenerPrecio, obtenerMarca y obtenerTipo
     * retornen los valores correctos especificados en el constructor.
     */
    @Test
    public void pruebaGettersHeredadosDeComponenteHoja() {
        String nombre = "XPG Spectrix 32GB";
        double precio = 3199.00;
        String marca = "Adata";
        String tipo = "RAM";
        int capacidad = 32;
        String tipoMemoria = "DDR4";

        RAM ram = new RAM(nombre, precio, marca, tipo, capacidad, tipoMemoria);

        assertEquals(nombre, ram.obtenerNombre(),
            "obtenerNombre debe retornar el nombre especificado");
        assertEquals(precio, ram.obtenerPrecio(),
            "obtenerPrecio debe retornar el precio especificado");
        assertEquals(marca, ram.obtenerMarca(),
            "obtenerMarca debe retornar la marca especificada");
        assertEquals(tipo, ram.obtenerTipo(),
            "obtenerTipo debe retornar el tipo especificado");
    }

    /**
     * Prueba que valida la inmutabilidad aparente de los datos de la RAM.
     *
     * <p>Verifica que multiples llamadas a los getters retornen los mismos valores,
     * asegurando que el estado interno no se modifica inadvertidamente.
     */
    @Test
    public void pruebaInmutabilidadDeGetters() {
        RAM ram = new RAM("Corsair Vengeance 16GB", 1799.00, "Corsair", "RAM", 16, "DDR5");

        String nombre1 = ram.obtenerNombre();
        String nombre2 = ram.obtenerNombre();
        double precio1 = ram.obtenerPrecio();
        double precio2 = ram.obtenerPrecio();
        int capacidad1 = ram.getCapacidadGB();
        int capacidad2 = ram.getCapacidadGB();
        String tipoMemoria1 = ram.getTipoMemoria();
        String tipoMemoria2 = ram.getTipoMemoria();

        assertEquals(nombre1, nombre2, "Multiples llamadas a obtenerNombre deben retornar el mismo valor");
        assertEquals(precio1, precio2, "Multiples llamadas a obtenerPrecio deben retornar el mismo valor");
        assertEquals(capacidad1, capacidad2, "Multiples llamadas a getCapacidadGB deben retornar el mismo valor");
        assertEquals(tipoMemoria1, tipoMemoria2, "Multiples llamadas a getTipoMemoria deben retornar el mismo valor");
    }

    /**
     * Prueba que valida diferentes combinaciones de capacidades y tipos de memoria.
     *
     * <p>Verifica que se puedan crear modulos RAM con diferentes combinaciones
     * de capacidad y tipo de memoria, todas validas.
     */
    @Test
    public void pruebaCombinacionesCapacidadYTipoMemoria() {
        Stream.of(
            Arguments.of(8, "DDR3"),
            Arguments.of(16, "DDR4"),
            Arguments.of(32, "DDR5"),
            Arguments.of(64, "DDR4")
        ).forEach(args -> {
            int capacidad = (int) args.get()[0];
            String tipoMemoria = (String) args.get()[1];

            RAM ram = new RAM("Test RAM", 1000.0, "TestBrand", "RAM", capacidad, tipoMemoria);

            assertEquals(capacidad, ram.getCapacidadGB());
            assertEquals(tipoMemoria, ram.getTipoMemoria());
        });
    }
}
