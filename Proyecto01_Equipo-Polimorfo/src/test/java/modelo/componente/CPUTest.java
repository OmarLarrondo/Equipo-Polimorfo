package modelo.componente;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

/**
 * Clase de pruebas unitarias para CPU.
 *
 * <p>Valida la correcta creacion y funcionamiento de componentes CPU,
 * asegurando que los procesadores se creen con las especificaciones correctas
 * y que se manejen adecuadamente los casos de parametros invalidos.
 *
 * <p>Esta suite de pruebas verifica:
 * <ul>
 *   <li>Creacion correcta de CPUs con todas sus especificaciones (nucleos, arquitectura, precio)</li>
 *   <li>Funcionamiento correcto de getters especificos (getCantidadNucleos, getArquitectura)</li>
 *   <li>Formato correcto del metodo toString</li>
 *   <li>Manejo de excepciones para parametros invalidos (null, precio negativo)</li>
 *   <li>Herencia correcta de ComponenteHoja</li>
 * </ul>
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class CPUTest {

    /**
     * Provee argumentos para probar la creacion de CPUs validos.
     * Retorna un stream de argumentos conteniendo: nombre, precio, marca, tipo,
     * cantidad de nucleos y arquitectura.
     *
     * @return Stream de argumentos para CPUs validos
     */
    private static Stream<Arguments> proveedorDatosCPUValidos() {
        return Stream.of(
            Arguments.of("Core i3-13100", 8999.00, "Intel", "CPU", 4, "x86-64"),
            Arguments.of("Core i5-13600K", 18999.00, "Intel", "CPU", 14, "x86-64"),
            Arguments.of("Core i7-13700K", 24999.00, "Intel", "CPU", 16, "x86-64"),
            Arguments.of("Core i9-13900K", 34999.00, "Intel", "CPU", 24, "x86-64"),
            Arguments.of("Ryzen 5 5600G", 6999.00, "AMD", "CPU", 6, "x86-64 (AMD64)"),
            Arguments.of("Ryzen 5 7600X", 12999.00, "AMD", "CPU", 6, "x86-64 (AMD64)"),
            Arguments.of("Ryzen 7 7700X", 17999.00, "AMD", "CPU", 8, "x86-64 (AMD64)"),
            Arguments.of("Ryzen 9 7950X3D", 29999.00, "AMD", "CPU", 16, "x86-64 (AMD64)")
        );
    }

    /**
     * Provee datos invalidos para probar el manejo de excepciones en el constructor.
     *
     * @return Stream de argumentos con datos invalidos
     */
    private static Stream<Arguments> proveedorDatosCPUInvalidos() {
        return Stream.of(
            Arguments.of(null, 1000.0, "Intel", "CPU", 4, "x86-64", "nombre null"),
            Arguments.of("Core i5", -100.0, "Intel", "CPU", 4, "x86-64", "precio negativo"),
            Arguments.of("Core i5", 0.0, "Intel", "CPU", 4, "x86-64", "precio cero"),
            Arguments.of("Core i5", 1000.0, null, "CPU", 4, "x86-64", "marca null"),
            Arguments.of("Core i5", 1000.0, "Intel", null, 4, "x86-64", "tipo null")
        );
    }

    /**
     * Valida que un CPU tenga las especificaciones esperadas.
     * Esta funcion es pura y no tiene efectos secundarios.
     *
     * @param cpu la CPU a validar
     * @param nombreEsperado el nombre esperado
     * @param precioEsperado el precio esperado
     * @param marcaEsperada la marca esperada
     * @param tipoEsperado el tipo esperado
     * @param nucleosEsperados la cantidad de nucleos esperados
     * @param arquitecturaEsperada la arquitectura esperada
     * @return true si todas las especificaciones coinciden, false en caso contrario
     */
    private static boolean validarCPU(CPU cpu, String nombreEsperado, double precioEsperado,
                                      String marcaEsperada, String tipoEsperado,
                                      int nucleosEsperados, String arquitecturaEsperada) {
        return cpu.obtenerNombre().equals(nombreEsperado) &&
               cpu.obtenerPrecio() == precioEsperado &&
               cpu.obtenerMarca().equals(marcaEsperada) &&
               cpu.obtenerTipo().equals(tipoEsperado) &&
               cpu.getCantidadNucleos() == nucleosEsperados &&
               cpu.getArquitectura().equals(arquitecturaEsperada);
    }

    /**
     * Valida que el formato toString contenga todos los elementos esperados.
     * Esta funcion es pura y no tiene efectos secundarios.
     *
     * @param toString la cadena toString del CPU
     * @param nombre el nombre que debe contener
     * @param marca la marca que debe contener
     * @param nucleos cantidad de nucleos que debe contener
     * @param arquitectura la arquitectura que debe contener
     * @param precio el precio que debe contener
     * @return true si todos los elementos estan presentes, false en caso contrario
     */
    private static boolean validarFormatoToString(String toString, String nombre, String marca,
                                                  int nucleos, String arquitectura, double precio) {
        return toString.contains("CPU:") &&
               toString.contains(nombre) &&
               toString.contains(marca) &&
               toString.contains(String.valueOf(nucleos)) &&
               toString.contains(arquitectura) &&
               toString.contains(String.format("%.2f", precio));
    }

    /**
     * Prueba parametrizada que valida la creacion correcta de CPUs con datos validos.
     *
     * <p>Verifica que el constructor de CPU cree instancias correctas con:
     * <ul>
     *   <li>Nombre completo del procesador</li>
     *   <li>Precio segun especificacion</li>
     *   <li>Marca (Intel o AMD)</li>
     *   <li>Tipo "CPU"</li>
     *   <li>Cantidad de nucleos especifica</li>
     *   <li>Arquitectura (x86-64 o x86-64 AMD64)</li>
     * </ul>
     *
     * @param nombre el nombre del CPU
     * @param precio el precio del CPU
     * @param marca la marca del CPU
     * @param tipo el tipo de componente
     * @param nucleos la cantidad de nucleos
     * @param arquitectura la arquitectura del procesador
     */
    @ParameterizedTest(name = "CPU {0}: {4} nucleos, {5}, ${1}")
    @MethodSource("proveedorDatosCPUValidos")
    public void pruebaCrearCPUConDatosValidos(String nombre, double precio, String marca,
                                               String tipo, int nucleos, String arquitectura) {
        CPU cpu = new CPU(nombre, precio, marca, tipo, nucleos, arquitectura);

        assertNotNull(cpu, "La CPU no debe ser null");
        assertTrue(
            validarCPU(cpu, nombre, precio, marca, tipo, nucleos, arquitectura),
            String.format("La CPU %s no tiene las especificaciones correctas", nombre)
        );
    }

    /**
     * Prueba que valida el funcionamiento del getter getCantidadNucleos.
     *
     * <p>Verifica que el metodo getCantidadNucleos retorne correctamente
     * la cantidad de nucleos especificada en el constructor.
     */
    @Test
    public void pruebaGetCantidadNucleos() {
        Stream.of(4, 6, 8, 12, 16, 24)
            .forEach(nucleos -> {
                CPU cpu = new CPU("Test CPU", 1000.0, "TestBrand", "CPU", nucleos, "x86-64");
                assertEquals(nucleos, cpu.getCantidadNucleos(),
                    String.format("getCantidadNucleos debe retornar %d", nucleos));
            });
    }

    /**
     * Prueba que valida el funcionamiento del getter getArquitectura.
     *
     * <p>Verifica que el metodo getArquitectura retorne correctamente
     * la arquitectura especificada en el constructor.
     */
    @Test
    public void pruebaGetArquitectura() {
        Stream.of("x86-64", "x86-64 (AMD64)", "ARM64", "RISC-V")
            .forEach(arquitectura -> {
                CPU cpu = new CPU("Test CPU", 1000.0, "TestBrand", "CPU", 8, arquitectura);
                assertEquals(arquitectura, cpu.getArquitectura(),
                    String.format("getArquitectura debe retornar %s", arquitectura));
            });
    }

    /**
     * Prueba parametrizada que valida el formato del metodo toString.
     *
     * <p>Verifica que toString retorne una cadena formateada que contenga:
     * <ul>
     *   <li>Prefijo "CPU:"</li>
     *   <li>Nombre del procesador</li>
     *   <li>Marca del procesador</li>
     *   <li>Tipo del componente</li>
     *   <li>Cantidad de nucleos</li>
     *   <li>Arquitectura</li>
     *   <li>Precio formateado con dos decimales</li>
     * </ul>
     *
     * @param nombre el nombre del CPU
     * @param precio el precio del CPU
     * @param marca la marca del CPU
     * @param tipo el tipo de componente
     * @param nucleos la cantidad de nucleos
     * @param arquitectura la arquitectura del procesador
     */
    @ParameterizedTest(name = "toString CPU {0}")
    @MethodSource("proveedorDatosCPUValidos")
    public void pruebaFormatoToString(String nombre, double precio, String marca,
                                      String tipo, int nucleos, String arquitectura) {
        CPU cpu = new CPU(nombre, precio, marca, tipo, nucleos, arquitectura);
        String toString = cpu.toString();

        assertNotNull(toString, "toString no debe retornar null");
        assertTrue(
            validarFormatoToString(toString, nombre, marca, nucleos, arquitectura, precio),
            String.format("El formato toString no es correcto para CPU %s", nombre)
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
        CPU cpu = new CPU("Core i7-13700K", 24999.00, "Intel", "CPU", 16, "x86-64");

        assertEquals(cpu.toString(), cpu.mostrarDetalles(),
            "mostrarDetalles debe retornar el mismo resultado que toString");
    }

    /**
     * Prueba parametrizada que verifica el manejo de excepciones para parametros invalidos.
     *
     * <p>Valida que el constructor de CPU lance IllegalArgumentException cuando se intenta
     * crear un CPU con parametros invalidos. Esto incluye:
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
     * @param nucleos la cantidad de nucleos
     * @param arquitectura la arquitectura
     * @param descripcion descripcion del caso de prueba
     */
    @ParameterizedTest(name = "Excepcion con {6}")
    @MethodSource("proveedorDatosCPUInvalidos")
    public void pruebaConstructorConDatosInvalidosLanzaExcepcion(String nombre, double precio,
                                                                  String marca, String tipo,
                                                                  int nucleos, String arquitectura,
                                                                  String descripcion) {
        IllegalArgumentException excepcion = assertThrows(
            IllegalArgumentException.class,
            () -> new CPU(nombre, precio, marca, tipo, nucleos, arquitectura),
            String.format("Deberia lanzar IllegalArgumentException para %s", descripcion)
        );

        assertNotNull(excepcion.getMessage(),
            "El mensaje de error no debe ser null");
    }

    /**
     * Prueba que valida la herencia correcta de ComponenteHoja.
     *
     * <p>Verifica que CPU sea una instancia de ComponenteHoja y ComponentePC,
     * validando la jerarquia de herencia correcta.
     */
    @Test
    public void pruebaHerenciaDeComponenteHoja() {
        CPU cpu = new CPU("Core i5-13600K", 18999.00, "Intel", "CPU", 14, "x86-64");

        assertTrue(cpu instanceof ComponenteHoja,
            "CPU debe ser instancia de ComponenteHoja");
        assertTrue(cpu instanceof ComponentePC,
            "CPU debe ser instancia de ComponentePC");
    }

    /**
     * Prueba que valida que los getters heredados de ComponenteHoja funcionan correctamente.
     *
     * <p>Verifica que obtenerNombre, obtenerPrecio, obtenerMarca y obtenerTipo
     * retornen los valores correctos especificados en el constructor.
     */
    @Test
    public void pruebaGettersHeredadosDeComponenteHoja() {
        String nombre = "Ryzen 7 7700X";
        double precio = 17999.00;
        String marca = "AMD";
        String tipo = "CPU";
        int nucleos = 8;
        String arquitectura = "x86-64 (AMD64)";

        CPU cpu = new CPU(nombre, precio, marca, tipo, nucleos, arquitectura);

        assertEquals(nombre, cpu.obtenerNombre(),
            "obtenerNombre debe retornar el nombre especificado");
        assertEquals(precio, cpu.obtenerPrecio(),
            "obtenerPrecio debe retornar el precio especificado");
        assertEquals(marca, cpu.obtenerMarca(),
            "obtenerMarca debe retornar la marca especificada");
        assertEquals(tipo, cpu.obtenerTipo(),
            "obtenerTipo debe retornar el tipo especificado");
    }

    /**
     * Prueba que valida la inmutabilidad aparente de los datos del CPU.
     *
     * <p>Verifica que multiples llamadas a los getters retornen los mismos valores,
     * asegurando que el estado interno no se modifica inadvertidamente.
     */
    @Test
    public void pruebaInmutabilidadDeGetters() {
        CPU cpu = new CPU("Core i9-13900K", 34999.00, "Intel", "CPU", 24, "x86-64");

        String nombre1 = cpu.obtenerNombre();
        String nombre2 = cpu.obtenerNombre();
        double precio1 = cpu.obtenerPrecio();
        double precio2 = cpu.obtenerPrecio();
        int nucleos1 = cpu.getCantidadNucleos();
        int nucleos2 = cpu.getCantidadNucleos();
        String arquitectura1 = cpu.getArquitectura();
        String arquitectura2 = cpu.getArquitectura();

        assertEquals(nombre1, nombre2, "Multiples llamadas a obtenerNombre deben retornar el mismo valor");
        assertEquals(precio1, precio2, "Multiples llamadas a obtenerPrecio deben retornar el mismo valor");
        assertEquals(nucleos1, nucleos2, "Multiples llamadas a getCantidadNucleos deben retornar el mismo valor");
        assertEquals(arquitectura1, arquitectura2, "Multiples llamadas a getArquitectura deben retornar el mismo valor");
    }
}
