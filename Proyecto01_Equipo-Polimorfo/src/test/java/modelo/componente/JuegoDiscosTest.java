package modelo.componente;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

/**
 * Clase de pruebas unitarias para JuegoDiscos.
 *
 * <p>Valida la correcta creacion y funcionamiento de conjuntos de discos de almacenamiento,
 * asegurando que se puedan agregar multiples discos sin limite
 * y que se calculen correctamente las capacidades totales.
 *
 * <p>Esta suite de pruebas verifica:
 * <ul>
 *   <li>Creacion correcta de conjuntos de discos</li>
 *   <li>Funcionamiento del metodo agregarDisco sin limite de cantidad</li>
 *   <li>Calculo correcto de la capacidad total de almacenamiento</li>
 *   <li>Manejo de excepciones para discos null</li>
 *   <li>Funcionamiento correcto del precio total</li>
 *   <li>Herencia correcta de ComponenteCompuesto</li>
 * </ul>
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class JuegoDiscosTest {

    /**
     * Crea un disco de prueba.
     * Esta funcion es pura y no tiene efectos secundarios.
     *
     * @param capacidadGB la capacidad en GB
     * @param precio el precio del disco
     * @param tipo el tipo de disco (HDD o SSD)
     * @return un disco
     */
    private static Disco crearDisco(int capacidadGB, double precio, String tipo) {
        return new Disco(
            String.format("Disco %dGB %s", capacidadGB, tipo),
            precio,
            "Kingston",
            "Disco",
            capacidadGB,
            tipo
        );
    }

    /**
     * Prueba la creacion de un juego de discos vacio.
     *
     * <p>Verifica que se pueda crear un JuegoDiscos sin discos iniciales.
     */
    @Test
    public void pruebaCrearJuegoDiscosVacio() {
        JuegoDiscos juegoDiscos = new JuegoDiscos("Almacenamiento", "Discos");

        assertNotNull(juegoDiscos, "El JuegoDiscos no debe ser null");
        assertEquals("Almacenamiento", juegoDiscos.obtenerNombre(),
            "El nombre debe ser el especificado");
        assertEquals("Discos", juegoDiscos.obtenerTipo(),
            "El tipo debe ser el especificado");
        assertEquals(0, juegoDiscos.getCapacidadAlmacenamiento(),
            "La capacidad inicial debe ser 0");
        assertEquals(0.0, juegoDiscos.obtenerPrecio(),
            "El precio inicial debe ser 0");
    }

    /**
     * Prueba agregar un disco valido.
     *
     * <p>Verifica que se pueda agregar un disco correctamente
     * y que la capacidad total se actualice.
     */
    @Test
    public void pruebaAgregarDiscoValido() {
        JuegoDiscos juegoDiscos = new JuegoDiscos("Almacenamiento", "Discos");
        Disco disco = crearDisco(500, 1000.0, "SSD");

        boolean resultado = juegoDiscos.agregarDisco(disco);

        assertTrue(resultado, "agregarDisco debe retornar true cuando se agrega correctamente");
        assertEquals(500, juegoDiscos.getCapacidadAlmacenamiento(),
            "La capacidad debe ser 500GB");
        assertEquals(1000.0, juegoDiscos.obtenerPrecio(),
            "El precio debe ser el precio del disco agregado");
    }

    /**
     * Prueba agregar multiples discos.
     *
     * <p>Verifica que se puedan agregar multiples discos sin limite
     * y que la capacidad total sea la suma de todas.
     */
    @Test
    public void pruebaAgregarMultiplesDiscos() {
        JuegoDiscos juegoDiscos = new JuegoDiscos("Almacenamiento", "Discos");

        juegoDiscos.agregarDisco(crearDisco(500, 1000.0, "SSD"));
        juegoDiscos.agregarDisco(crearDisco(1000, 1500.0, "HDD"));
        juegoDiscos.agregarDisco(crearDisco(2000, 2000.0, "HDD"));

        assertEquals(3500, juegoDiscos.getCapacidadAlmacenamiento(),
            "La capacidad total debe ser 3500GB (500+1000+2000)");
        assertEquals(4500.0, juegoDiscos.obtenerPrecio(),
            "El precio total debe ser 4500.0 (1000+1500+2000)");
    }

    /**
     * Prueba que no hay limite en la cantidad de discos.
     *
     * <p>Verifica que se puedan agregar mas de 4 discos sin restricciones,
     * a diferencia de JuegoRAMs que tiene limite de 4.
     */
    @Test
    public void pruebaSinLimiteDeCantidad() {
        JuegoDiscos juegoDiscos = new JuegoDiscos("Almacenamiento", "Discos");

        Stream.of(500, 500, 1000, 1000, 2000, 2000).forEach(capacidad -> {
            Disco disco = crearDisco(capacidad, 1000.0, "SSD");
            boolean resultado = juegoDiscos.agregarDisco(disco);
            assertTrue(resultado,
                String.format("Debe poder agregar disco de %dGB", capacidad));
        });

        assertEquals(7000, juegoDiscos.getCapacidadAlmacenamiento(),
            "La capacidad total debe ser 7000GB (6 discos)");
        assertEquals(6000.0, juegoDiscos.obtenerPrecio(),
            "El precio total debe ser 6000.0 (1000*6)");
    }

    /**
     * Prueba agregar disco null lanza excepcion.
     *
     * <p>Verifica que se lance IllegalArgumentException cuando se intenta
     * agregar un disco null.
     */
    @Test
    public void pruebaAgregarDiscoNullLanzaExcepcion() {
        JuegoDiscos juegoDiscos = new JuegoDiscos("Almacenamiento", "Discos");

        IllegalArgumentException excepcion = assertThrows(
            IllegalArgumentException.class,
            () -> juegoDiscos.agregarDisco(null),
            "Deberia lanzar IllegalArgumentException al agregar null"
        );

        assertTrue(excepcion.getMessage().contains("nulo"),
            "El mensaje debe indicar que el disco no puede ser nulo");
    }

    /**
     * Prueba el metodo getCapacidadAlmacenamiento con diferentes capacidades.
     *
     * <p>Verifica que getCapacidadAlmacenamiento calcule correctamente
     * la suma de capacidades de todos los discos.
     */
    @Test
    public void pruebaGetCapacidadAlmacenamientoConDiferentesCapacidades() {
        JuegoDiscos juegoDiscos = new JuegoDiscos("Almacenamiento", "Discos");

        juegoDiscos.agregarDisco(crearDisco(500, 1000.0, "SSD"));
        assertEquals(500, juegoDiscos.getCapacidadAlmacenamiento(),
            "Capacidad debe ser 500GB");

        juegoDiscos.agregarDisco(crearDisco(1000, 1500.0, "HDD"));
        assertEquals(1500, juegoDiscos.getCapacidadAlmacenamiento(),
            "Capacidad debe ser 1500GB (500+1000)");

        juegoDiscos.agregarDisco(crearDisco(2000, 2000.0, "HDD"));
        assertEquals(3500, juegoDiscos.getCapacidadAlmacenamiento(),
            "Capacidad debe ser 3500GB (500+1000+2000)");
    }

    /**
     * Prueba que getCapacidadAlmacenamiento retorne 0 para conjunto vacio.
     *
     * <p>Verifica que un JuegoDiscos vacio tenga capacidad 0.
     */
    @Test
    public void pruebaGetCapacidadAlmacenamientoConConjuntoVacio() {
        JuegoDiscos juegoDiscos = new JuegoDiscos("Almacenamiento", "Discos");

        assertEquals(0, juegoDiscos.getCapacidadAlmacenamiento(),
            "La capacidad de un conjunto vacio debe ser 0");
    }

    /**
     * Prueba el funcionamiento del iterador heredado.
     *
     * <p>Verifica que el iterador funcione correctamente para recorrer
     * todos los discos.
     */
    @Test
    public void pruebaIteradorFuncionaCorrectamente() {
        JuegoDiscos juegoDiscos = new JuegoDiscos("Almacenamiento", "Discos");
        juegoDiscos.agregarDisco(crearDisco(500, 1000.0, "SSD"));
        juegoDiscos.agregarDisco(crearDisco(1000, 1500.0, "HDD"));

        IteratorComponentePC iterator = juegoDiscos.getIterator();
        int count = 0;

        while (iterator.hasNext()) {
            ComponentePC componente = iterator.next();
            assertNotNull(componente, "El componente no debe ser null");
            assertTrue(componente instanceof Disco, "El componente debe ser un Disco");
            count++;
        }

        assertEquals(2, count, "Debe haber 2 discos");
    }

    /**
     * Prueba la herencia correcta de ComponenteCompuesto.
     *
     * <p>Verifica que JuegoDiscos sea una instancia de ComponenteCompuesto y ComponentePC.
     */
    @Test
    public void pruebaHerenciaDeComponenteCompuesto() {
        JuegoDiscos juegoDiscos = new JuegoDiscos("Almacenamiento", "Discos");

        assertTrue(juegoDiscos instanceof ComponenteCompuesto,
            "JuegoDiscos debe ser instancia de ComponenteCompuesto");
        assertTrue(juegoDiscos instanceof ComponentePC,
            "JuegoDiscos debe ser instancia de ComponentePC");
    }

    /**
     * Prueba que el precio total se calcule correctamente.
     *
     * <p>Verifica que obtenerPrecio retorne la suma de los precios
     * de todos los discos.
     */
    @Test
    public void pruebaCalculoPrecioTotal() {
        JuegoDiscos juegoDiscos = new JuegoDiscos("Almacenamiento", "Discos");
        juegoDiscos.agregarDisco(crearDisco(500, 1000.0, "SSD"));
        juegoDiscos.agregarDisco(crearDisco(1000, 1500.0, "HDD"));
        juegoDiscos.agregarDisco(crearDisco(500, 1000.0, "SSD"));

        assertEquals(3500.0, juegoDiscos.obtenerPrecio(),
            "El precio total debe ser 3500.0 (1000+1500+1000)");
    }

    /**
     * Prueba agregar combinacion de SSD y HDD.
     *
     * <p>Verifica que se puedan agregar tanto SSD como HDD en el mismo conjunto.
     */
    @Test
    public void pruebaAgregarCombinacionSSDYHDD() {
        JuegoDiscos juegoDiscos = new JuegoDiscos("Almacenamiento", "Discos");

        juegoDiscos.agregarDisco(crearDisco(500, 1200.0, "SSD"));
        juegoDiscos.agregarDisco(crearDisco(2000, 1800.0, "HDD"));
        juegoDiscos.agregarDisco(crearDisco(1000, 1500.0, "SSD"));
        juegoDiscos.agregarDisco(crearDisco(4000, 2500.0, "HDD"));

        assertEquals(7500, juegoDiscos.getCapacidadAlmacenamiento(),
            "La capacidad total debe ser 7500GB");
        assertEquals(7000.0, juegoDiscos.obtenerPrecio(),
            "El precio total debe ser 7000.0");
    }

    /**
     * Prueba que solo cuenta discos para la capacidad.
     *
     * <p>Verifica que getCapacidadAlmacenamiento solo cuente discos,
     * no otros tipos de componentes que pudieran agregarse mediante
     * el metodo agregar heredado.
     */
    @Test
    public void pruebaSoloContabilizaDiscos() {
        JuegoDiscos juegoDiscos = new JuegoDiscos("Almacenamiento", "Discos");
        juegoDiscos.agregarDisco(crearDisco(500, 1000.0, "SSD"));

        CPU cpu = new CPU("Core i5", 2000.0, "Intel", "CPU", 6, "x86-64");
        juegoDiscos.agregar(cpu);

        assertEquals(500, juegoDiscos.getCapacidadAlmacenamiento(),
            "La capacidad solo debe contar discos, no otros componentes");
        assertEquals(3000.0, juegoDiscos.obtenerPrecio(),
            "El precio debe incluir todos los componentes");
    }

    /**
     * Prueba el metodo mostrarDetalles heredado.
     *
     * <p>Verifica que mostrarDetalles funcione correctamente con discos.
     */
    @Test
    public void pruebaMostrarDetalles() {
        JuegoDiscos juegoDiscos = new JuegoDiscos("Almacenamiento", "Discos");
        juegoDiscos.agregarDisco(crearDisco(500, 1000.0, "SSD"));
        juegoDiscos.agregarDisco(crearDisco(1000, 1500.0, "HDD"));

        String detalles = juegoDiscos.mostrarDetalles();

        assertNotNull(detalles, "mostrarDetalles no debe retornar null");
        assertTrue(detalles.contains("Almacenamiento"),
            "Debe contener el nombre del conjunto");
        assertTrue(detalles.contains("Discos"),
            "Debe contener el tipo del conjunto");
    }

    /**
     * Prueba obtenerMarca con discos.
     *
     * <p>Verifica que obtenerMarca retorne la marca del primer disco.
     */
    @Test
    public void pruebaObtenerMarcaConDiscos() {
        JuegoDiscos juegoDiscos = new JuegoDiscos("Almacenamiento", "Discos");
        Disco disco = crearDisco(500, 1000.0, "SSD");
        juegoDiscos.agregarDisco(disco);

        assertEquals("Kingston", juegoDiscos.obtenerMarca(),
            "La marca debe ser la del primer disco");
    }

    /**
     * Prueba agregar muchos discos sin limite.
     *
     * <p>Verifica que se puedan agregar una gran cantidad de discos
     * sin restricciones.
     */
    @Test
    public void pruebaAgregarMuchosDiscosSinLimite() {
        JuegoDiscos juegoDiscos = new JuegoDiscos("Almacenamiento", "Discos");

        Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).forEach(i -> {
            Disco disco = crearDisco(1000, 1500.0, i % 2 == 0 ? "SSD" : "HDD");
            boolean resultado = juegoDiscos.agregarDisco(disco);
            assertTrue(resultado,
                String.format("Debe poder agregar disco %d", i));
        });

        assertEquals(10000, juegoDiscos.getCapacidadAlmacenamiento(),
            "La capacidad total debe ser 10000GB (10 discos de 1TB)");
        assertEquals(15000.0, juegoDiscos.obtenerPrecio(),
            "El precio total debe ser 15000.0 (1500*10)");
    }

    /**
     * Prueba agregarDisco siempre retorna true con disco valido.
     *
     * <p>Verifica que agregarDisco siempre retorne true cuando el disco
     * no es null, sin importar cuantos discos ya existan.
     */
    @Test
    public void pruebaAgregarDiscoSiempreRetornaTrue() {
        JuegoDiscos juegoDiscos = new JuegoDiscos("Almacenamiento", "Discos");

        Stream.of(500, 1000, 2000, 4000, 500, 1000).forEach(capacidad -> {
            Disco disco = crearDisco(capacidad, 1000.0, "SSD");
            boolean resultado = juegoDiscos.agregarDisco(disco);
            assertTrue(resultado,
                String.format("agregarDisco debe retornar true para disco de %dGB", capacidad));
        });
    }

    /**
     * Prueba capacidad total con diferentes tamanios de discos.
     *
     * <p>Verifica el calculo correcto con discos de capacidades muy diferentes.
     */
    @Test
    public void pruebaCapacidadTotalConDiferentesTamanios() {
        JuegoDiscos juegoDiscos = new JuegoDiscos("Almacenamiento", "Discos");

        juegoDiscos.agregarDisco(crearDisco(256, 800.0, "SSD"));
        juegoDiscos.agregarDisco(crearDisco(500, 1000.0, "SSD"));
        juegoDiscos.agregarDisco(crearDisco(1000, 1300.0, "SSD"));
        juegoDiscos.agregarDisco(crearDisco(2000, 1700.0, "HDD"));
        juegoDiscos.agregarDisco(crearDisco(4000, 2500.0, "HDD"));

        assertEquals(7756, juegoDiscos.getCapacidadAlmacenamiento(),
            "La capacidad total debe ser 7756GB");
        assertEquals(7300.0, juegoDiscos.obtenerPrecio(),
            "El precio total debe ser 7300.0");
    }
}
