package modelo.componente;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

/**
 * Clase de pruebas unitarias para GPU.
 *
 * <p>Valida la correcta creacion y funcionamiento de componentes GPU,
 * asegurando que las tarjetas graficas se creen con las especificaciones correctas
 * y que se manejen adecuadamente los casos de parametros invalidos.
 *
 * <p>Esta suite de pruebas verifica:
 * <ul>
 *   <li>Creacion correcta de GPUs con todas sus especificaciones (VRAM, tipo de memoria, precio)</li>
 *   <li>Funcionamiento correcto de getters especificos (getVRAM, getTipoMemoriaGPU)</li>
 *   <li>Formato correcto del metodo toString</li>
 *   <li>Manejo de excepciones para parametros invalidos (null, precio negativo)</li>
 *   <li>Herencia correcta de ComponenteHoja</li>
 * </ul>
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class GPUTest {

    /**
     * Provee argumentos para probar la creacion de GPUs validos.
     * Retorna un stream de argumentos conteniendo: nombre, precio, marca, tipo,
     * tipo de memoria GPU y cantidad de VRAM.
     *
     * @return Stream de argumentos para GPUs validos
     */
    private static Stream<Arguments> proveedorDatosGPUValidos() {
        return Stream.of(
            Arguments.of("GTX 1660", 4999.00, "NVIDIA", "GPU", "GDDR5", 6),
            Arguments.of("RTX 3060", 8999.00, "NVIDIA", "GPU", "GDDR6", 12),
            Arguments.of("RTX 4070", 14999.00, "NVIDIA", "GPU", "GDDR6X", 12),
            Arguments.of("RTX 4080", 24999.00, "NVIDIA", "GPU", "GDDR6X", 16),
            Arguments.of("RTX 4090", 39999.00, "NVIDIA", "GPU", "GDDR6X", 24),
            Arguments.of("RX 6600", 6999.00, "AMD", "GPU", "GDDR6", 8),
            Arguments.of("RX 7900 XTX", 21999.00, "AMD", "GPU", "GDDR6", 24)
        );
    }

    /**
     * Provee datos invalidos para probar el manejo de excepciones en el constructor.
     *
     * @return Stream de argumentos con datos invalidos
     */
    private static Stream<Arguments> proveedorDatosGPUInvalidos() {
        return Stream.of(
            Arguments.of(null, 1000.0, "NVIDIA", "GPU", "GDDR6", 8, "nombre null"),
            Arguments.of("RTX 3060", -100.0, "NVIDIA", "GPU", "GDDR6", 8, "precio negativo"),
            Arguments.of("RTX 3060", 0.0, "NVIDIA", "GPU", "GDDR6", 8, "precio cero"),
            Arguments.of("RTX 3060", 1000.0, null, "GPU", "GDDR6", 8, "marca null"),
            Arguments.of("RTX 3060", 1000.0, "NVIDIA", null, "GDDR6", 8, "tipo null")
        );
    }

    /**
     * Valida que una GPU tenga las especificaciones esperadas.
     * Esta funcion es pura y no tiene efectos secundarios.
     *
     * @param gpu la GPU a validar
     * @param nombreEsperado el nombre esperado
     * @param precioEsperado el precio esperado
     * @param marcaEsperada la marca esperada
     * @param tipoEsperado el tipo esperado
     * @param tipoMemoriaEsperada el tipo de memoria GPU esperada
     * @param vramEsperada la cantidad de VRAM esperada
     * @return true si todas las especificaciones coinciden, false en caso contrario
     */
    private static boolean validarGPU(GPU gpu, String nombreEsperado, double precioEsperado,
                                      String marcaEsperada, String tipoEsperado,
                                      String tipoMemoriaEsperada, int vramEsperada) {
        return gpu.obtenerNombre().equals(nombreEsperado) &&
               gpu.obtenerPrecio() == precioEsperado &&
               gpu.obtenerMarca().equals(marcaEsperada) &&
               gpu.obtenerTipo().equals(tipoEsperado) &&
               gpu.getTipoMemoriaGPU().equals(tipoMemoriaEsperada) &&
               gpu.getVRAM() == vramEsperada;
    }

    /**
     * Valida que el formato toString contenga todos los elementos esperados.
     * Esta funcion es pura y no tiene efectos secundarios.
     *
     * @param toString la cadena toString de la GPU
     * @param nombre el nombre que debe contener
     * @param marca la marca que debe contener
     * @param vram cantidad de VRAM que debe contener
     * @param tipoMemoria el tipo de memoria que debe contener
     * @param precio el precio que debe contener
     * @return true si todos los elementos estan presentes, false en caso contrario
     */
    private static boolean validarFormatoToString(String toString, String nombre, String marca,
                                                  int vram, String tipoMemoria, double precio) {
        return toString.contains("GPU:") &&
               toString.contains(nombre) &&
               toString.contains(marca) &&
               toString.contains(String.valueOf(vram)) &&
               toString.contains(tipoMemoria) &&
               toString.contains(String.format("%.2f", precio));
    }

    /**
     * Prueba parametrizada que valida la creacion correcta de GPUs con datos validos.
     *
     * <p>Verifica que el constructor de GPU cree instancias correctas con:
     * <ul>
     *   <li>Nombre completo de la GPU</li>
     *   <li>Precio segun especificacion</li>
     *   <li>Marca (NVIDIA o AMD)</li>
     *   <li>Tipo "GPU"</li>
     *   <li>Tipo de memoria GPU especifica</li>
     *   <li>Cantidad de VRAM en GB</li>
     * </ul>
     *
     * @param nombre el nombre de la GPU
     * @param precio el precio de la GPU
     * @param marca la marca de la GPU
     * @param tipo el tipo de componente
     * @param tipoMemoria el tipo de memoria GPU
     * @param vram la cantidad de VRAM en GB
     */
    @ParameterizedTest(name = "GPU {0}: {5}GB {4}, ${1}")
    @MethodSource("proveedorDatosGPUValidos")
    public void pruebaCrearGPUConDatosValidos(String nombre, double precio, String marca,
                                              String tipo, String tipoMemoria, int vram) {
        GPU gpu = new GPU(nombre, precio, marca, tipo, tipoMemoria, vram);

        assertNotNull(gpu, "La GPU no debe ser null");
        assertTrue(
            validarGPU(gpu, nombre, precio, marca, tipo, tipoMemoria, vram),
            String.format("La GPU %s no tiene las especificaciones correctas", nombre)
        );
    }

    /**
     * Prueba que valida el funcionamiento del getter getVRAM.
     *
     * <p>Verifica que el metodo getVRAM retorne correctamente
     * la cantidad de VRAM especificada en el constructor.
     */
    @Test
    public void pruebaGetVRAM() {
        Stream.of(4, 6, 8, 12, 16, 24)
            .forEach(vram -> {
                GPU gpu = new GPU("Test GPU", 1000.0, "TestBrand", "GPU", "GDDR6", vram);
                assertEquals(vram, gpu.getVRAM(),
                    String.format("getVRAM debe retornar %d", vram));
            });
    }

    /**
     * Prueba que valida el funcionamiento del getter getTipoMemoriaGPU.
     *
     * <p>Verifica que el metodo getTipoMemoriaGPU retorne correctamente
     * el tipo de memoria especificado en el constructor.
     */
    @Test
    public void pruebaGetTipoMemoriaGPU() {
        Stream.of("GDDR5", "GDDR6", "GDDR6X", "HBM2", "HBM3")
            .forEach(tipoMemoria -> {
                GPU gpu = new GPU("Test GPU", 1000.0, "TestBrand", "GPU", tipoMemoria, 8);
                assertEquals(tipoMemoria, gpu.getTipoMemoriaGPU(),
                    String.format("getTipoMemoriaGPU debe retornar %s", tipoMemoria));
            });
    }

    /**
     * Prueba parametrizada que valida el formato del metodo toString.
     *
     * <p>Verifica que toString retorne una cadena formateada que contenga:
     * <ul>
     *   <li>Prefijo "GPU:"</li>
     *   <li>Nombre de la GPU</li>
     *   <li>Marca de la GPU</li>
     *   <li>Tipo del componente</li>
     *   <li>Cantidad de VRAM en GB</li>
     *   <li>Tipo de memoria</li>
     *   <li>Precio formateado con dos decimales</li>
     * </ul>
     *
     * @param nombre el nombre de la GPU
     * @param precio el precio de la GPU
     * @param marca la marca de la GPU
     * @param tipo el tipo de componente
     * @param tipoMemoria el tipo de memoria GPU
     * @param vram la cantidad de VRAM en GB
     */
    @ParameterizedTest(name = "toString GPU {0}")
    @MethodSource("proveedorDatosGPUValidos")
    public void pruebaFormatoToString(String nombre, double precio, String marca,
                                      String tipo, String tipoMemoria, int vram) {
        GPU gpu = new GPU(nombre, precio, marca, tipo, tipoMemoria, vram);
        String toString = gpu.toString();

        assertNotNull(toString, "toString no debe retornar null");
        assertTrue(
            validarFormatoToString(toString, nombre, marca, vram, tipoMemoria, precio),
            String.format("El formato toString no es correcto para GPU %s", nombre)
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
        GPU gpu = new GPU("RTX 4080", 24999.00, "NVIDIA", "GPU", "GDDR6X", 16);

        assertEquals(gpu.toString(), gpu.mostrarDetalles(),
            "mostrarDetalles debe retornar el mismo resultado que toString");
    }

    /**
     * Prueba parametrizada que verifica el manejo de excepciones para parametros invalidos.
     *
     * <p>Valida que el constructor de GPU lance IllegalArgumentException cuando se intenta
     * crear una GPU con parametros invalidos. Esto incluye:
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
     * @param tipoMemoria el tipo de memoria GPU
     * @param vram la cantidad de VRAM
     * @param descripcion descripcion del caso de prueba
     */
    @ParameterizedTest(name = "Excepcion con {6}")
    @MethodSource("proveedorDatosGPUInvalidos")
    public void pruebaConstructorConDatosInvalidosLanzaExcepcion(String nombre, double precio,
                                                                  String marca, String tipo,
                                                                  String tipoMemoria, int vram,
                                                                  String descripcion) {
        IllegalArgumentException excepcion = assertThrows(
            IllegalArgumentException.class,
            () -> new GPU(nombre, precio, marca, tipo, tipoMemoria, vram),
            String.format("Deberia lanzar IllegalArgumentException para %s", descripcion)
        );

        assertNotNull(excepcion.getMessage(),
            "El mensaje de error no debe ser null");
    }

    /**
     * Prueba que valida la herencia correcta de ComponenteHoja.
     *
     * <p>Verifica que GPU sea una instancia de ComponenteHoja y ComponentePC,
     * validando la jerarquia de herencia correcta.
     */
    @Test
    public void pruebaHerenciaDeComponenteHoja() {
        GPU gpu = new GPU("RTX 3060", 8999.00, "NVIDIA", "GPU", "GDDR6", 12);

        assertTrue(gpu instanceof ComponenteHoja,
            "GPU debe ser instancia de ComponenteHoja");
        assertTrue(gpu instanceof ComponentePC,
            "GPU debe ser instancia de ComponentePC");
    }

    /**
     * Prueba que valida que los getters heredados de ComponenteHoja funcionan correctamente.
     *
     * <p>Verifica que obtenerNombre, obtenerPrecio, obtenerMarca y obtenerTipo
     * retornen los valores correctos especificados en el constructor.
     */
    @Test
    public void pruebaGettersHeredadosDeComponenteHoja() {
        String nombre = "RTX 4090";
        double precio = 39999.00;
        String marca = "NVIDIA";
        String tipo = "GPU";
        String tipoMemoria = "GDDR6X";
        int vram = 24;

        GPU gpu = new GPU(nombre, precio, marca, tipo, tipoMemoria, vram);

        assertEquals(nombre, gpu.obtenerNombre(),
            "obtenerNombre debe retornar el nombre especificado");
        assertEquals(precio, gpu.obtenerPrecio(),
            "obtenerPrecio debe retornar el precio especificado");
        assertEquals(marca, gpu.obtenerMarca(),
            "obtenerMarca debe retornar la marca especificada");
        assertEquals(tipo, gpu.obtenerTipo(),
            "obtenerTipo debe retornar el tipo especificado");
    }

    /**
     * Prueba que valida la inmutabilidad aparente de los datos de la GPU.
     *
     * <p>Verifica que multiples llamadas a los getters retornen los mismos valores,
     * asegurando que el estado interno no se modifica inadvertidamente.
     */
    @Test
    public void pruebaInmutabilidadDeGetters() {
        GPU gpu = new GPU("RTX 4070", 14999.00, "NVIDIA", "GPU", "GDDR6X", 12);

        String nombre1 = gpu.obtenerNombre();
        String nombre2 = gpu.obtenerNombre();
        double precio1 = gpu.obtenerPrecio();
        double precio2 = gpu.obtenerPrecio();
        int vram1 = gpu.getVRAM();
        int vram2 = gpu.getVRAM();
        String tipoMemoria1 = gpu.getTipoMemoriaGPU();
        String tipoMemoria2 = gpu.getTipoMemoriaGPU();

        assertEquals(nombre1, nombre2, "Multiples llamadas a obtenerNombre deben retornar el mismo valor");
        assertEquals(precio1, precio2, "Multiples llamadas a obtenerPrecio deben retornar el mismo valor");
        assertEquals(vram1, vram2, "Multiples llamadas a getVRAM deben retornar el mismo valor");
        assertEquals(tipoMemoria1, tipoMemoria2, "Multiples llamadas a getTipoMemoriaGPU deben retornar el mismo valor");
    }

    /**
     * Prueba que valida diferentes combinaciones de VRAM y tipos de memoria.
     *
     * <p>Verifica que se puedan crear GPUs con diferentes combinaciones
     * de VRAM y tipo de memoria, todas validas.
     */
    @Test
    public void pruebaCombinacionesVRAMYTipoMemoria() {
        Stream.of(
            Arguments.of(6, "GDDR5"),
            Arguments.of(8, "GDDR6"),
            Arguments.of(12, "GDDR6X"),
            Arguments.of(24, "GDDR6X")
        ).forEach(args -> {
            int vram = (int) args.get()[0];
            String tipoMemoria = (String) args.get()[1];

            GPU gpu = new GPU("Test GPU", 1000.0, "TestBrand", "GPU", tipoMemoria, vram);

            assertEquals(vram, gpu.getVRAM());
            assertEquals(tipoMemoria, gpu.getTipoMemoriaGPU());
        });
    }
}
