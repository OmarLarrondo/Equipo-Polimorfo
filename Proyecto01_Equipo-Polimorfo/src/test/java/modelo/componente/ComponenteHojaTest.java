package modelo.componente;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

/**
 * Clase de pruebas unitarias para ComponenteHoja.
 *
 * <p>Valida la correcta funcionalidad de la clase abstracta ComponenteHoja
 * mediante el uso de implementaciones concretas como CPU, RAM y GPU.
 *
 * <p>Esta suite de pruebas verifica:
 * <ul>
 *   <li>Funcionamiento correcto del constructor con parametros validos</li>
 *   <li>Validacion de parametros en el constructor (null, precio negativo)</li>
 *   <li>Funcionamiento correcto de getters (obtenerNombre, obtenerPrecio, obtenerMarca, obtenerTipo)</li>
 *   <li>Funcionamiento correcto de toString</li>
 *   <li>Funcionamiento correcto de mostrarDetalles (que por defecto delega a toString)</li>
 *   <li>Implementacion correcta de la interfaz ComponentePC</li>
 * </ul>
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class ComponenteHojaTest {

    /**
     * Provee diferentes implementaciones concretas de ComponenteHoja.
     * Retorna un stream de componentes para probar la funcionalidad compartida.
     *
     * @return Stream de ComponenteHoja
     */
    private static Stream<ComponenteHoja> proveedorComponentesHoja() {
        return Stream.of(
            new CPU("Core i5-13600K", 18999.00, "Intel", "CPU", 14, "x86-64"),
            new RAM("HyperX Fury 16GB", 1599.00, "Kingston", "RAM", 16, "DDR4"),
            new GPU("RTX 4080", 24999.00, "NVIDIA", "GPU", "GDDR6X", 16),
            new Disco("A400 1TB", 2199.00, "Kingston", "Disco", 1000, "SSD"),
            new MotherBoard("ROG Maximus Z790 Hero", 19999.00, "ASUS", "MotherBoard", "Z790", "LGA1700", "x86-64"),
            new FuenteAlimentacion("RM850x", 2699.00, "Corsair", "Fuente de alimentacion", 800, "80 PLUS Gold"),
            new Gabinete("H6 Flow", 1599.00, "NZXT", "Gabinete", "ATX")
        );
    }

    /**
     * Provee casos de prueba para validacion de parametros invalidos.
     *
     * @return Stream de argumentos con parametros invalidos
     */
    private static Stream<Arguments> proveedorParametrosInvalidos() {
        return Stream.of(
            Arguments.of(null, 1000.0, "Marca", "Tipo", "nombre null"),
            Arguments.of("Nombre", -100.0, "Marca", "Tipo", "precio negativo"),
            Arguments.of("Nombre", 0.0, "Marca", "Tipo", "precio cero"),
            Arguments.of("Nombre", 1000.0, null, "Tipo", "marca null"),
            Arguments.of("Nombre", 1000.0, "Marca", null, "tipo null")
        );
    }

    /**
     * Prueba que todas las implementaciones concretas sean instancias de ComponenteHoja.
     *
     * <p>Verifica la herencia correcta de todas las clases concretas.
     */
    @Test
    public void pruebaImplementacionesConcretasSonInstanciasDeComponenteHoja() {
        proveedorComponentesHoja().forEach(componente -> {
            assertTrue(componente instanceof ComponenteHoja,
                String.format("%s debe ser instancia de ComponenteHoja", componente.getClass().getSimpleName()));
        });
    }

    /**
     * Prueba que todas las implementaciones concretas implementen ComponentePC.
     *
     * <p>Verifica que ComponenteHoja implemente correctamente ComponentePC.
     */
    @Test
    public void pruebaImplementacionesConcretasImplementanComponentePC() {
        proveedorComponentesHoja().forEach(componente -> {
            assertTrue(componente instanceof ComponentePC,
                String.format("%s debe implementar ComponentePC", componente.getClass().getSimpleName()));
        });
    }

    /**
     * Prueba el metodo obtenerNombre con diferentes componentes.
     *
     * <p>Verifica que obtenerNombre retorne el nombre especificado en el constructor.
     */
    @Test
    public void pruebaObtenerNombreRetornaNombreCorrecto() {
        Stream.of(
            Arguments.of(new CPU("Core i7", 2000.0, "Intel", "CPU", 8, "x86-64"), "Core i7"),
            Arguments.of(new RAM("Kingston 8GB", 500.0, "Kingston", "RAM", 8, "DDR4"), "Kingston 8GB"),
            Arguments.of(new GPU("RTX 3060", 1500.0, "NVIDIA", "GPU", "GDDR6", 12), "RTX 3060")
        ).forEach(args -> {
            ComponenteHoja componente = (ComponenteHoja) args.get()[0];
            String nombreEsperado = (String) args.get()[1];

            assertEquals(nombreEsperado, componente.obtenerNombre(),
                "obtenerNombre debe retornar el nombre especificado");
        });
    }

    /**
     * Prueba el metodo obtenerPrecio con diferentes componentes.
     *
     * <p>Verifica que obtenerPrecio retorne el precio especificado en el constructor.
     */
    @Test
    public void pruebaObtenerPrecioRetornaPrecioCorrecto() {
        Stream.of(
            Arguments.of(new CPU("Core i5", 1000.0, "Intel", "CPU", 6, "x86-64"), 1000.0),
            Arguments.of(new RAM("Kingston 16GB", 800.0, "Kingston", "RAM", 16, "DDR4"), 800.0),
            Arguments.of(new GPU("RTX 4090", 3500.0, "NVIDIA", "GPU", "GDDR6X", 24), 3500.0)
        ).forEach(args -> {
            ComponenteHoja componente = (ComponenteHoja) args.get()[0];
            double precioEsperado = (double) args.get()[1];

            assertEquals(precioEsperado, componente.obtenerPrecio(),
                "obtenerPrecio debe retornar el precio especificado");
        });
    }

    /**
     * Prueba el metodo obtenerMarca con diferentes componentes.
     *
     * <p>Verifica que obtenerMarca retorne la marca especificada en el constructor.
     */
    @Test
    public void pruebaObtenerMarcaRetornaMarcaCorrecta() {
        Stream.of(
            Arguments.of(new CPU("Core i5", 1000.0, "Intel", "CPU", 6, "x86-64"), "Intel"),
            Arguments.of(new RAM("HyperX", 500.0, "Kingston", "RAM", 8, "DDR4"), "Kingston"),
            Arguments.of(new GPU("RTX 3060", 1500.0, "NVIDIA", "GPU", "GDDR6", 12), "NVIDIA"),
            Arguments.of(new CPU("Ryzen 5", 800.0, "AMD", "CPU", 6, "x86-64 (AMD64)"), "AMD")
        ).forEach(args -> {
            ComponenteHoja componente = (ComponenteHoja) args.get()[0];
            String marcaEsperada = (String) args.get()[1];

            assertEquals(marcaEsperada, componente.obtenerMarca(),
                "obtenerMarca debe retornar la marca especificada");
        });
    }

    /**
     * Prueba el metodo obtenerTipo con diferentes componentes.
     *
     * <p>Verifica que obtenerTipo retorne el tipo especificado en el constructor.
     */
    @Test
    public void pruebaObtenerTipoRetornaTipoCorrecto() {
        Stream.of(
            Arguments.of(new CPU("Core i5", 1000.0, "Intel", "CPU", 6, "x86-64"), "CPU"),
            Arguments.of(new RAM("HyperX", 500.0, "Kingston", "RAM", 8, "DDR4"), "RAM"),
            Arguments.of(new GPU("RTX 3060", 1500.0, "NVIDIA", "GPU", "GDDR6", 12), "GPU"),
            Arguments.of(new Disco("SSD 500GB", 1000.0, "Kingston", "Disco", 500, "SSD"), "Disco")
        ).forEach(args -> {
            ComponenteHoja componente = (ComponenteHoja) args.get()[0];
            String tipoEsperado = (String) args.get()[1];

            assertEquals(tipoEsperado, componente.obtenerTipo(),
                "obtenerTipo debe retornar el tipo especificado");
        });
    }

    /**
     * Prueba el metodo toString de la clase base.
     *
     * <p>Verifica que toString genere una cadena que contenga la informacion basica
     * del componente (tipo, nombre, marca y precio).
     */
    @Test
    public void pruebaToStringContieneInformacionBasica() {
        CPU cpu = new CPU("Core i5", 1000.0, "Intel", "CPU", 6, "x86-64");

        String toString = cpu.toString();

        assertNotNull(toString, "toString no debe retornar null");
        assertTrue(toString.contains("Core i5") || toString.contains("Intel") || toString.contains("CPU"),
            "toString debe contener informacion del componente");
    }

    /**
     * Prueba que mostrarDetalles por defecto delegue a toString.
     *
     * <p>Verifica que la implementacion base de mostrarDetalles retorne
     * el mismo resultado que toString.
     */
    @Test
    public void pruebaMostrarDetallesDelegaAToStringEnLaBase() {
        ComponenteHoja componente = new CPU("Core i5", 1000.0, "Intel", "CPU", 6, "x86-64") {
            @Override
            public String toString() {
                return super.toString();
            }
        };

        String toString = componente.toString();
        String mostrarDetalles = componente.mostrarDetalles();

        assertEquals(toString, mostrarDetalles,
            "mostrarDetalles debe retornar el mismo resultado que toString por defecto");
    }

    /**
     * Prueba parametrizada de validacion de parametros invalidos.
     *
     * <p>Verifica que el constructor lance IllegalArgumentException con
     * parametros invalidos.
     *
     * @param nombre el nombre (posiblemente null)
     * @param precio el precio (posiblemente negativo o cero)
     * @param marca la marca (posiblemente null)
     * @param tipo el tipo (posiblemente null)
     * @param descripcion descripcion del caso de prueba
     */
    @ParameterizedTest(name = "Excepcion con {4}")
    @MethodSource("proveedorParametrosInvalidos")
    public void pruebaConstructorConParametrosInvalidosLanzaExcepcion(String nombre, double precio,
                                                                       String marca, String tipo,
                                                                       String descripcion) {
        IllegalArgumentException excepcion = assertThrows(
            IllegalArgumentException.class,
            () -> new CPU(nombre, precio, marca, tipo, 6, "x86-64"),
            String.format("Deberia lanzar IllegalArgumentException para %s", descripcion)
        );

        assertNotNull(excepcion.getMessage(),
            "El mensaje de error no debe ser null");
    }

    /**
     * Prueba que los getters sean consistentes con multiples llamadas.
     *
     * <p>Verifica que multiples llamadas a los getters retornen siempre
     * los mismos valores (inmutabilidad aparente).
     */
    @Test
    public void pruebaGettersSonConsistentes() {
        ComponenteHoja componente = new CPU("Core i7", 2000.0, "Intel", "CPU", 8, "x86-64");

        String nombre1 = componente.obtenerNombre();
        String nombre2 = componente.obtenerNombre();
        double precio1 = componente.obtenerPrecio();
        double precio2 = componente.obtenerPrecio();
        String marca1 = componente.obtenerMarca();
        String marca2 = componente.obtenerMarca();
        String tipo1 = componente.obtenerTipo();
        String tipo2 = componente.obtenerTipo();

        assertEquals(nombre1, nombre2, "Multiples llamadas a obtenerNombre deben ser consistentes");
        assertEquals(precio1, precio2, "Multiples llamadas a obtenerPrecio deben ser consistentes");
        assertEquals(marca1, marca2, "Multiples llamadas a obtenerMarca deben ser consistentes");
        assertEquals(tipo1, tipo2, "Multiples llamadas a obtenerTipo deben ser consistentes");
    }

    /**
     * Prueba que diferentes tipos de componentes tengan formato toString correcto.
     *
     * <p>Verifica que cada tipo de componente implemente toString adecuadamente.
     */
    @Test
    public void pruebaDiferentesTiposComponentesToStringFormato() {
        proveedorComponentesHoja().forEach(componente -> {
            String toString = componente.toString();
            assertNotNull(toString,
                String.format("toString de %s no debe ser null", componente.getClass().getSimpleName()));
            assertFalse(toString.isEmpty(),
                String.format("toString de %s no debe estar vacio", componente.getClass().getSimpleName()));
        });
    }

    /**
     * Prueba que ComponenteHoja almacene correctamente todos los atributos.
     *
     * <p>Verifica que el constructor inicialice correctamente todos los campos.
     */
    @Test
    public void pruebaConstructorInicializaCorrectamenteTodosLosCampos() {
        String nombre = "Componente Test";
        double precio = 1234.56;
        String marca = "Marca Test";
        String tipo = "Tipo Test";

        ComponenteHoja componente = new CPU(nombre, precio, marca, tipo, 6, "x86-64");

        assertEquals(nombre, componente.obtenerNombre(), "El nombre debe estar inicializado correctamente");
        assertEquals(precio, componente.obtenerPrecio(), "El precio debe estar inicializado correctamente");
        assertEquals(marca, componente.obtenerMarca(), "La marca debe estar inicializada correctamente");
        assertEquals(tipo, componente.obtenerTipo(), "El tipo debe estar inicializado correctamente");
    }

    /**
     * Prueba que componentes del mismo tipo con diferentes valores sean independientes.
     *
     * <p>Verifica que crear multiples instancias no cause interferencia entre ellas.
     */
    @Test
    public void pruebaMultiplesInstanciasSonIndependientes() {
        ComponenteHoja comp1 = new CPU("Core i5", 1000.0, "Intel", "CPU", 6, "x86-64");
        ComponenteHoja comp2 = new CPU("Core i7", 2000.0, "Intel", "CPU", 8, "x86-64");

        assertNotSame(comp1, comp2, "Las instancias deben ser diferentes");
        assertNotEquals(comp1.obtenerNombre(), comp2.obtenerNombre(), "Los nombres deben ser diferentes");
        assertNotEquals(comp1.obtenerPrecio(), comp2.obtenerPrecio(), "Los precios deben ser diferentes");
    }

    /**
     * Prueba que toString incluya el formato basico esperado.
     *
     * <p>Verifica que el toString base incluya tipo, nombre, marca y precio.
     */
    @Test
    public void pruebaToStringFormatoBasico() {
        ComponenteHoja componente = new CPU("Test CPU", 1500.0, "TestMarca", "TestTipo", 6, "x86-64") {
            @Override
            public String toString() {
                return super.toString();
            }
        };

        String toString = componente.toString();

        assertTrue(toString.contains("TestTipo"), "Debe contener el tipo");
        assertTrue(toString.contains("Test CPU"), "Debe contener el nombre");
        assertTrue(toString.contains("TestMarca"), "Debe contener la marca");
        assertTrue(toString.contains("1500"), "Debe contener el precio");
    }

    /**
     * Prueba que los precios sean numeros positivos.
     *
     * <p>Verifica que el constructor rechace precios <= 0.
     */
    @Test
    public void pruebaPreciosDebenSerPositivos() {
        assertThrows(IllegalArgumentException.class,
            () -> new CPU("Test", -1.0, "Marca", "CPU", 6, "x86-64"),
            "Debe rechazar precio negativo");

        assertThrows(IllegalArgumentException.class,
            () -> new CPU("Test", 0.0, "Marca", "CPU", 6, "x86-64"),
            "Debe rechazar precio cero");
    }

    /**
     * Prueba que los parametros String no puedan ser null.
     *
     * <p>Verifica que el constructor rechace nombre, marca o tipo null.
     */
    @Test
    public void pruebaParametrosStringNoNulos() {
        assertThrows(IllegalArgumentException.class,
            () -> new CPU(null, 1000.0, "Marca", "CPU", 6, "x86-64"),
            "Debe rechazar nombre null");

        assertThrows(IllegalArgumentException.class,
            () -> new CPU("Nombre", 1000.0, null, "CPU", 6, "x86-64"),
            "Debe rechazar marca null");

        assertThrows(IllegalArgumentException.class,
            () -> new CPU("Nombre", 1000.0, "Marca", null, 6, "x86-64"),
            "Debe rechazar tipo null");
    }
}
