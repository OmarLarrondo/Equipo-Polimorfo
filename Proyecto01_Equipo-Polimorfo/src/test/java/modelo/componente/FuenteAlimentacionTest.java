package modelo.componente;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

/**
 * Clase de pruebas unitarias para FuenteAlimentacion.
 *
 * <p>Valida la correcta creacion y funcionamiento de componentes FuenteAlimentacion,
 * asegurando que las fuentes de poder se creen con las especificaciones correctas
 * y que se manejen adecuadamente los casos de parametros invalidos.
 *
 * <p>Esta suite de pruebas verifica:
 * <ul>
 *   <li>Creacion correcta de fuentes con todas sus especificaciones (potencia, certificacion, precio)</li>
 *   <li>Funcionamiento correcto de getters especificos (getPotenciaMaxima, getCertificacion)</li>
 *   <li>Formato correcto del metodo toString</li>
 *   <li>Manejo de excepciones para parametros invalidos (null, precio negativo)</li>
 *   <li>Herencia correcta de ComponenteHoja</li>
 * </ul>
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class FuenteAlimentacionTest {

    /**
     * Provee argumentos para probar la creacion de fuentes de alimentacion validas.
     * Retorna un stream de argumentos conteniendo: nombre, precio, marca, tipo,
     * potencia maxima y certificacion.
     *
     * @return Stream de argumentos para fuentes de alimentacion validas
     */
    private static Stream<Arguments> proveedorDatosFuenteValidos() {
        return Stream.of(
            Arguments.of("500W BR", 899.00, "XPG", "Fuente de alimentacion", 500, "80 PLUS Bronze"),
            Arguments.of("700W GQ", 1299.00, "XPG", "Fuente de alimentacion", 700, "80 PLUS Gold"),
            Arguments.of("1000W PQ", 1999.00, "XPG", "Fuente de alimentacion", 1000, "80 PLUS Platinum"),
            Arguments.of("800W GQ", 1899.00, "EVGA", "Fuente de alimentacion", 800, "80 PLUS Gold"),
            Arguments.of("1000W G5", 2499.00, "EVGA", "Fuente de alimentacion", 1000, "80 PLUS Gold"),
            Arguments.of("1500W T2", 4999.00, "EVGA", "Fuente de alimentacion", 1500, "80 PLUS Titanium"),
            Arguments.of("RM850x", 2699.00, "Corsair", "Fuente de alimentacion", 800, "80 PLUS Gold"),
            Arguments.of("HX1200", 3999.00, "Corsair", "Fuente de alimentacion", 1200, "80 PLUS Platinum"),
            Arguments.of("AX1500i", 5999.00, "Corsair", "Fuente de alimentacion", 1500, "80 PLUS Titanium")
        );
    }

    /**
     * Provee datos invalidos para probar el manejo de excepciones en el constructor.
     *
     * @return Stream de argumentos con datos invalidos
     */
    private static Stream<Arguments> proveedorDatosFuenteInvalidos() {
        return Stream.of(
            Arguments.of(null, 1000.0, "Corsair", "Fuente de alimentacion", 800, "80 PLUS Gold", "nombre null"),
            Arguments.of("RM850x", -100.0, "Corsair", "Fuente de alimentacion", 800, "80 PLUS Gold", "precio negativo"),
            Arguments.of("RM850x", 0.0, "Corsair", "Fuente de alimentacion", 800, "80 PLUS Gold", "precio cero"),
            Arguments.of("RM850x", 1000.0, null, "Fuente de alimentacion", 800, "80 PLUS Gold", "marca null"),
            Arguments.of("RM850x", 1000.0, "Corsair", null, 800, "80 PLUS Gold", "tipo null")
        );
    }

    /**
     * Valida que una fuente de alimentacion tenga las especificaciones esperadas.
     * Esta funcion es pura y no tiene efectos secundarios.
     *
     * @param fuente la fuente a validar
     * @param nombreEsperado el nombre esperado
     * @param precioEsperado el precio esperado
     * @param marcaEsperada la marca esperada
     * @param tipoEsperado el tipo esperado
     * @param potenciaEsperada la potencia maxima esperada
     * @param certificacionEsperada la certificacion esperada
     * @return true si todas las especificaciones coinciden, false en caso contrario
     */
    private static boolean validarFuenteAlimentacion(FuenteAlimentacion fuente, String nombreEsperado,
                                                     double precioEsperado, String marcaEsperada,
                                                     String tipoEsperado, int potenciaEsperada,
                                                     String certificacionEsperada) {
        return fuente.obtenerNombre().equals(nombreEsperado) &&
               fuente.obtenerPrecio() == precioEsperado &&
               fuente.obtenerMarca().equals(marcaEsperada) &&
               fuente.obtenerTipo().equals(tipoEsperado) &&
               fuente.getPotenciaMaxima() == potenciaEsperada &&
               fuente.getCertificacion().equals(certificacionEsperada);
    }

    /**
     * Valida que el formato toString contenga todos los elementos esperados.
     * Esta funcion es pura y no tiene efectos secundarios.
     *
     * @param toString la cadena toString de la fuente
     * @param nombre el nombre que debe contener
     * @param marca la marca que debe contener
     * @param potencia la potencia que debe contener
     * @param certificacion la certificacion que debe contener
     * @param precio el precio que debe contener
     * @return true si todos los elementos estan presentes, false en caso contrario
     */
    private static boolean validarFormatoToString(String toString, String nombre, String marca,
                                                  int potencia, String certificacion, double precio) {
        return toString.contains("Fuente:") &&
               toString.contains(nombre) &&
               toString.contains(marca) &&
               toString.contains(String.valueOf(potencia)) &&
               toString.contains(certificacion) &&
               toString.contains(String.format("%.2f", precio));
    }

    /**
     * Prueba parametrizada que valida la creacion correcta de fuentes con datos validos.
     *
     * <p>Verifica que el constructor de FuenteAlimentacion cree instancias correctas con:
     * <ul>
     *   <li>Nombre completo de la fuente</li>
     *   <li>Precio segun especificacion</li>
     *   <li>Marca (EVGA, Corsair, XPG, etc)</li>
     *   <li>Tipo "Fuente de alimentacion"</li>
     *   <li>Potencia maxima en vatios</li>
     *   <li>Certificacion de eficiencia</li>
     * </ul>
     *
     * @param nombre el nombre de la fuente
     * @param precio el precio de la fuente
     * @param marca la marca de la fuente
     * @param tipo el tipo de componente
     * @param potencia la potencia maxima
     * @param certificacion la certificacion de eficiencia
     */
    @ParameterizedTest(name = "Fuente {0}: {4}W {5}, ${1}")
    @MethodSource("proveedorDatosFuenteValidos")
    public void pruebaCrearFuenteConDatosValidos(String nombre, double precio, String marca,
                                                  String tipo, int potencia, String certificacion) {
        FuenteAlimentacion fuente = new FuenteAlimentacion(nombre, precio, marca, tipo, potencia, certificacion);

        assertNotNull(fuente, "La fuente no debe ser null");
        assertTrue(
            validarFuenteAlimentacion(fuente, nombre, precio, marca, tipo, potencia, certificacion),
            String.format("La fuente %s no tiene las especificaciones correctas", nombre)
        );
    }

    /**
     * Prueba que valida el funcionamiento del getter getPotenciaMaxima.
     *
     * <p>Verifica que el metodo getPotenciaMaxima retorne correctamente
     * la potencia especificada en el constructor.
     */
    @Test
    public void pruebaGetPotenciaMaxima() {
        Stream.of(500, 700, 800, 1000, 1200, 1500)
            .forEach(potencia -> {
                FuenteAlimentacion fuente = new FuenteAlimentacion("Test PSU", 1000.0, "TestBrand",
                                                                    "Fuente de alimentacion", potencia, "80 PLUS Gold");
                assertEquals(potencia, fuente.getPotenciaMaxima(),
                    String.format("getPotenciaMaxima debe retornar %d", potencia));
            });
    }

    /**
     * Prueba que valida el funcionamiento del getter getCertificacion.
     *
     * <p>Verifica que el metodo getCertificacion retorne correctamente
     * la certificacion especificada en el constructor.
     */
    @Test
    public void pruebaGetCertificacion() {
        Stream.of("80 PLUS Bronze", "80 PLUS Gold", "80 PLUS Platinum", "80 PLUS Titanium")
            .forEach(certificacion -> {
                FuenteAlimentacion fuente = new FuenteAlimentacion("Test PSU", 1000.0, "TestBrand",
                                                                    "Fuente de alimentacion", 800, certificacion);
                assertEquals(certificacion, fuente.getCertificacion(),
                    String.format("getCertificacion debe retornar %s", certificacion));
            });
    }

    /**
     * Prueba parametrizada que valida el formato del metodo toString.
     *
     * <p>Verifica que toString retorne una cadena formateada que contenga:
     * <ul>
     *   <li>Prefijo "Fuente:"</li>
     *   <li>Nombre de la fuente</li>
     *   <li>Marca de la fuente</li>
     *   <li>Potencia en vatios</li>
     *   <li>Certificacion de eficiencia</li>
     *   <li>Precio formateado con dos decimales</li>
     * </ul>
     *
     * @param nombre el nombre de la fuente
     * @param precio el precio de la fuente
     * @param marca la marca de la fuente
     * @param tipo el tipo de componente
     * @param potencia la potencia maxima
     * @param certificacion la certificacion de eficiencia
     */
    @ParameterizedTest(name = "toString Fuente {0}")
    @MethodSource("proveedorDatosFuenteValidos")
    public void pruebaFormatoToString(String nombre, double precio, String marca,
                                      String tipo, int potencia, String certificacion) {
        FuenteAlimentacion fuente = new FuenteAlimentacion(nombre, precio, marca, tipo, potencia, certificacion);
        String toString = fuente.toString();

        assertNotNull(toString, "toString no debe retornar null");
        assertTrue(
            validarFormatoToString(toString, nombre, marca, potencia, certificacion, precio),
            String.format("El formato toString no es correcto para fuente %s", nombre)
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
        FuenteAlimentacion fuente = new FuenteAlimentacion("RM850x", 2699.00, "Corsair",
                                                            "Fuente de alimentacion", 800, "80 PLUS Gold");

        assertEquals(fuente.toString(), fuente.mostrarDetalles(),
            "mostrarDetalles debe retornar el mismo resultado que toString");
    }

    /**
     * Prueba parametrizada que verifica el manejo de excepciones para parametros invalidos.
     *
     * <p>Valida que el constructor de FuenteAlimentacion lance IllegalArgumentException cuando se intenta
     * crear una fuente con parametros invalidos. Esto incluye:
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
     * @param potencia la potencia maxima
     * @param certificacion la certificacion
     * @param descripcion descripcion del caso de prueba
     */
    @ParameterizedTest(name = "Excepcion con {6}")
    @MethodSource("proveedorDatosFuenteInvalidos")
    public void pruebaConstructorConDatosInvalidosLanzaExcepcion(String nombre, double precio,
                                                                  String marca, String tipo,
                                                                  int potencia, String certificacion,
                                                                  String descripcion) {
        IllegalArgumentException excepcion = assertThrows(
            IllegalArgumentException.class,
            () -> new FuenteAlimentacion(nombre, precio, marca, tipo, potencia, certificacion),
            String.format("Deberia lanzar IllegalArgumentException para %s", descripcion)
        );

        assertNotNull(excepcion.getMessage(),
            "El mensaje de error no debe ser null");
    }

    /**
     * Prueba que valida la herencia correcta de ComponenteHoja.
     *
     * <p>Verifica que FuenteAlimentacion sea una instancia de ComponenteHoja y ComponentePC,
     * validando la jerarquia de herencia correcta.
     */
    @Test
    public void pruebaHerenciaDeComponenteHoja() {
        FuenteAlimentacion fuente = new FuenteAlimentacion("1000W G5", 2499.00, "EVGA",
                                                            "Fuente de alimentacion", 1000, "80 PLUS Gold");

        assertTrue(fuente instanceof ComponenteHoja,
            "FuenteAlimentacion debe ser instancia de ComponenteHoja");
        assertTrue(fuente instanceof ComponentePC,
            "FuenteAlimentacion debe ser instancia de ComponentePC");
    }

    /**
     * Prueba que valida que los getters heredados de ComponenteHoja funcionan correctamente.
     *
     * <p>Verifica que obtenerNombre, obtenerPrecio, obtenerMarca y obtenerTipo
     * retornen los valores correctos especificados en el constructor.
     */
    @Test
    public void pruebaGettersHeredadosDeComponenteHoja() {
        String nombre = "AX1500i";
        double precio = 5999.00;
        String marca = "Corsair";
        String tipo = "Fuente de alimentacion";
        int potencia = 1500;
        String certificacion = "80 PLUS Titanium";

        FuenteAlimentacion fuente = new FuenteAlimentacion(nombre, precio, marca, tipo, potencia, certificacion);

        assertEquals(nombre, fuente.obtenerNombre(),
            "obtenerNombre debe retornar el nombre especificado");
        assertEquals(precio, fuente.obtenerPrecio(),
            "obtenerPrecio debe retornar el precio especificado");
        assertEquals(marca, fuente.obtenerMarca(),
            "obtenerMarca debe retornar la marca especificada");
        assertEquals(tipo, fuente.obtenerTipo(),
            "obtenerTipo debe retornar el tipo especificado");
    }

    /**
     * Prueba que valida la inmutabilidad aparente de los datos de la fuente.
     *
     * <p>Verifica que multiples llamadas a los getters retornen los mismos valores,
     * asegurando que el estado interno no se modifica inadvertidamente.
     */
    @Test
    public void pruebaInmutabilidadDeGetters() {
        FuenteAlimentacion fuente = new FuenteAlimentacion("HX1200", 3999.00, "Corsair",
                                                            "Fuente de alimentacion", 1200, "80 PLUS Platinum");

        String nombre1 = fuente.obtenerNombre();
        String nombre2 = fuente.obtenerNombre();
        double precio1 = fuente.obtenerPrecio();
        double precio2 = fuente.obtenerPrecio();
        int potencia1 = fuente.getPotenciaMaxima();
        int potencia2 = fuente.getPotenciaMaxima();
        String certificacion1 = fuente.getCertificacion();
        String certificacion2 = fuente.getCertificacion();

        assertEquals(nombre1, nombre2, "Multiples llamadas a obtenerNombre deben retornar el mismo valor");
        assertEquals(precio1, precio2, "Multiples llamadas a obtenerPrecio deben retornar el mismo valor");
        assertEquals(potencia1, potencia2, "Multiples llamadas a getPotenciaMaxima deben retornar el mismo valor");
        assertEquals(certificacion1, certificacion2, "Multiples llamadas a getCertificacion deben retornar el mismo valor");
    }

    /**
     * Prueba que valida diferentes combinaciones de potencia y certificacion.
     *
     * <p>Verifica que se puedan crear fuentes con diferentes combinaciones
     * de potencia y certificacion, todas validas.
     */
    @Test
    public void pruebaCombinacionesPotenciaYCertificacion() {
        Stream.of(
            Arguments.of(500, "80 PLUS Bronze"),
            Arguments.of(800, "80 PLUS Gold"),
            Arguments.of(1000, "80 PLUS Platinum"),
            Arguments.of(1500, "80 PLUS Titanium")
        ).forEach(args -> {
            int potencia = (int) args.get()[0];
            String certificacion = (String) args.get()[1];

            FuenteAlimentacion fuente = new FuenteAlimentacion("Test PSU", 1000.0, "TestBrand",
                                                                "Fuente de alimentacion", potencia, certificacion);

            assertEquals(potencia, fuente.getPotenciaMaxima());
            assertEquals(certificacion, fuente.getCertificacion());
        });
    }
}
