package modelo.componente;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

/**
 * Clase de pruebas unitarias para JuegoRAMs.
 *
 * <p>Valida la correcta creacion y funcionamiento de conjuntos de modulos RAM,
 * asegurando que se respeten las restricciones del sistema (maximo 4 modulos)
 * y que se calculen correctamente las capacidades totales.
 *
 * <p>Esta suite de pruebas verifica:
 * <ul>
 *   <li>Creacion correcta de conjuntos de RAM</li>
 *   <li>Funcionamiento del metodo agregarRAM con limite de 4 modulos</li>
 *   <li>Calculo correcto de la capacidad total de almacenamiento</li>
 *   <li>Manejo de excepciones para modulos RAM null</li>
 *   <li>Funcionamiento correcto del precio total</li>
 *   <li>Herencia correcta de ComponenteCompuesto</li>
 * </ul>
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class JuegoRAMsTest {

    /**
     * Crea un modulo RAM de prueba.
     * Esta funcion es pura y no tiene efectos secundarios.
     *
     * @param capacidadGB la capacidad en GB
     * @param precio el precio del modulo
     * @return un modulo RAM
     */
    private static RAM crearRAM(int capacidadGB, double precio) {
        return new RAM(
            String.format("RAM %dGB", capacidadGB),
            precio,
            "Kingston",
            "RAM",
            capacidadGB,
            "DDR4"
        );
    }

    /**
     * Prueba la creacion de un juego de RAMs vacio.
     *
     * <p>Verifica que se pueda crear un JuegoRAMs sin modulos iniciales.
     */
    @Test
    public void pruebaCrearJuegoRAMsVacio() {
        JuegoRAMs juegoRAMs = new JuegoRAMs("Memoria RAM", "Memoria");

        assertNotNull(juegoRAMs, "El JuegoRAMs no debe ser null");
        assertEquals("Memoria RAM", juegoRAMs.obtenerNombre(),
            "El nombre debe ser el especificado");
        assertEquals("Memoria", juegoRAMs.obtenerTipo(),
            "El tipo debe ser el especificado");
        assertEquals(0, juegoRAMs.getCapacidadAlmacenamiento(),
            "La capacidad inicial debe ser 0");
        assertEquals(0.0, juegoRAMs.obtenerPrecio(),
            "El precio inicial debe ser 0");
    }

    /**
     * Prueba agregar un modulo RAM valido.
     *
     * <p>Verifica que se pueda agregar un modulo RAM correctamente
     * y que la capacidad total se actualice.
     */
    @Test
    public void pruebaAgregarRAMValido() {
        JuegoRAMs juegoRAMs = new JuegoRAMs("Memoria RAM", "Memoria");
        RAM ram = crearRAM(8, 500.0);

        Boolean resultado = juegoRAMs.agregarRAM(ram);

        assertTrue(resultado, "agregarRAM debe retornar true cuando se agrega correctamente");
        assertEquals(8, juegoRAMs.getCapacidadAlmacenamiento(),
            "La capacidad debe ser 8GB");
        assertEquals(500.0, juegoRAMs.obtenerPrecio(),
            "El precio debe ser el precio del modulo agregado");
    }

    /**
     * Prueba agregar multiples modulos RAM (hasta 4).
     *
     * <p>Verifica que se puedan agregar hasta 4 modulos RAM
     * y que la capacidad total sea la suma de todas.
     */
    @Test
    public void pruebaAgregarMultiplesRAMsHastaMaximo() {
        JuegoRAMs juegoRAMs = new JuegoRAMs("Memoria RAM", "Memoria");

        Stream.of(8, 16, 8, 16).forEach(capacidad -> {
            RAM ram = crearRAM(capacidad, 500.0);
            Boolean resultado = juegoRAMs.agregarRAM(ram);
            assertTrue(resultado,
                String.format("Debe poder agregar modulo de %dGB", capacidad));
        });

        assertEquals(48, juegoRAMs.getCapacidadAlmacenamiento(),
            "La capacidad total debe ser 48GB (8+16+8+16)");
        assertEquals(2000.0, juegoRAMs.obtenerPrecio(),
            "El precio total debe ser 2000.0 (500*4)");
    }

    /**
     * Prueba el limite de 4 modulos RAM.
     *
     * <p>Verifica que no se pueda agregar un quinto modulo RAM
     * y que agregarRAM retorne false.
     */
    @Test
    public void pruebaLimiteMaximoDe4Modulos() {
        JuegoRAMs juegoRAMs = new JuegoRAMs("Memoria RAM", "Memoria");

        Stream.of(8, 8, 8, 8).forEach(capacidad -> {
            RAM ram = crearRAM(capacidad, 500.0);
            juegoRAMs.agregarRAM(ram);
        });

        RAM ramExtra = crearRAM(16, 600.0);
        Boolean resultado = juegoRAMs.agregarRAM(ramExtra);

        assertFalse(resultado, "agregarRAM debe retornar false al intentar agregar un quinto modulo");
        assertEquals(32, juegoRAMs.getCapacidadAlmacenamiento(),
            "La capacidad debe permanecer en 32GB");
        assertEquals(2000.0, juegoRAMs.obtenerPrecio(),
            "El precio debe permanecer en 2000.0");
    }

    /**
     * Prueba agregar RAM null lanza excepcion.
     *
     * <p>Verifica que se lance IllegalArgumentException cuando se intenta
     * agregar un modulo RAM null.
     */
    @Test
    public void pruebaAgregarRAMNullLanzaExcepcion() {
        JuegoRAMs juegoRAMs = new JuegoRAMs("Memoria RAM", "Memoria");

        IllegalArgumentException excepcion = assertThrows(
            IllegalArgumentException.class,
            () -> juegoRAMs.agregarRAM(null),
            "Deberia lanzar IllegalArgumentException al agregar null"
        );

        assertTrue(excepcion.getMessage().contains("nulo"),
            "El mensaje debe indicar que el modulo no puede ser nulo");
    }

    /**
     * Prueba el metodo getCapacidadAlmacenamiento con diferentes capacidades.
     *
     * <p>Verifica que getCapacidadAlmacenamiento calcule correctamente
     * la suma de capacidades de todos los modulos.
     */
    @Test
    public void pruebaGetCapacidadAlmacenamientoConDiferentesCapacidades() {
        JuegoRAMs juegoRAMs = new JuegoRAMs("Memoria RAM", "Memoria");

        juegoRAMs.agregarRAM(crearRAM(8, 500.0));
        assertEquals(8, juegoRAMs.getCapacidadAlmacenamiento(),
            "Capacidad debe ser 8GB");

        juegoRAMs.agregarRAM(crearRAM(16, 800.0));
        assertEquals(24, juegoRAMs.getCapacidadAlmacenamiento(),
            "Capacidad debe ser 24GB (8+16)");

        juegoRAMs.agregarRAM(crearRAM(32, 1200.0));
        assertEquals(56, juegoRAMs.getCapacidadAlmacenamiento(),
            "Capacidad debe ser 56GB (8+16+32)");
    }

    /**
     * Prueba que getCapacidadAlmacenamiento retorne 0 para conjunto vacio.
     *
     * <p>Verifica que un JuegoRAMs vacio tenga capacidad 0.
     */
    @Test
    public void pruebaGetCapacidadAlmacenamientoConConjuntoVacio() {
        JuegoRAMs juegoRAMs = new JuegoRAMs("Memoria RAM", "Memoria");

        assertEquals(0, juegoRAMs.getCapacidadAlmacenamiento(),
            "La capacidad de un conjunto vacio debe ser 0");
    }

    /**
     * Prueba el funcionamiento del iterador heredado.
     *
     * <p>Verifica que el iterador funcione correctamente para recorrer
     * todos los modulos RAM.
     */
    @Test
    public void pruebaIteradorFuncionaCorrectamente() {
        JuegoRAMs juegoRAMs = new JuegoRAMs("Memoria RAM", "Memoria");
        juegoRAMs.agregarRAM(crearRAM(8, 500.0));
        juegoRAMs.agregarRAM(crearRAM(16, 800.0));

        IteratorComponentePC iterator = juegoRAMs.getIterator();
        int count = 0;

        while (iterator.hasNext()) {
            ComponentePC componente = iterator.next();
            assertNotNull(componente, "El componente no debe ser null");
            assertTrue(componente instanceof RAM, "El componente debe ser una RAM");
            count++;
        }

        assertEquals(2, count, "Debe haber 2 modulos RAM");
    }

    /**
     * Prueba la herencia correcta de ComponenteCompuesto.
     *
     * <p>Verifica que JuegoRAMs sea una instancia de ComponenteCompuesto y ComponentePC.
     */
    @Test
    public void pruebaHerenciaDeComponenteCompuesto() {
        JuegoRAMs juegoRAMs = new JuegoRAMs("Memoria RAM", "Memoria");

        assertTrue(juegoRAMs instanceof ComponenteCompuesto,
            "JuegoRAMs debe ser instancia de ComponenteCompuesto");
        assertTrue(juegoRAMs instanceof ComponentePC,
            "JuegoRAMs debe ser instancia de ComponentePC");
    }

    /**
     * Prueba que el precio total se calcule correctamente.
     *
     * <p>Verifica que obtenerPrecio retorne la suma de los precios
     * de todos los modulos RAM.
     */
    @Test
    public void pruebaCalculoPrecioTotal() {
        JuegoRAMs juegoRAMs = new JuegoRAMs("Memoria RAM", "Memoria");
        juegoRAMs.agregarRAM(crearRAM(8, 500.0));
        juegoRAMs.agregarRAM(crearRAM(16, 800.0));
        juegoRAMs.agregarRAM(crearRAM(8, 500.0));

        assertEquals(1800.0, juegoRAMs.obtenerPrecio(),
            "El precio total debe ser 1800.0 (500+800+500)");
    }

    /**
     * Prueba agregar 4 modulos del mismo tamanio.
     *
     * <p>Verifica el caso comun de 4 modulos identicos (configuracion dual channel).
     */
    @Test
    public void pruebaAgregar4ModulosIdenticos() {
        JuegoRAMs juegoRAMs = new JuegoRAMs("Memoria RAM", "Memoria");

        for (int i = 0; i < 4; i++) {
            RAM ram = crearRAM(16, 800.0);
            Boolean resultado = juegoRAMs.agregarRAM(ram);
            assertTrue(resultado,
                String.format("Debe poder agregar el modulo %d", i + 1));
        }

        assertEquals(64, juegoRAMs.getCapacidadAlmacenamiento(),
            "La capacidad total debe ser 64GB (16*4)");
        assertEquals(3200.0, juegoRAMs.obtenerPrecio(),
            "El precio total debe ser 3200.0 (800*4)");
    }

    /**
     * Prueba que solo cuenta modulos RAM para la capacidad.
     *
     * <p>Verifica que getCapacidadAlmacenamiento solo cuente modulos RAM,
     * no otros tipos de componentes que pudieran agregarse mediante
     * el metodo agregar heredado.
     */
    @Test
    public void pruebaSoloContabilizaModulosRAM() {
        JuegoRAMs juegoRAMs = new JuegoRAMs("Memoria RAM", "Memoria");
        juegoRAMs.agregarRAM(crearRAM(8, 500.0));

        CPU cpu = new CPU("Core i5", 1000.0, "Intel", "CPU", 6, "x86-64");
        juegoRAMs.agregar(cpu);

        assertEquals(8, juegoRAMs.getCapacidadAlmacenamiento(),
            "La capacidad solo debe contar modulos RAM, no otros componentes");
        assertEquals(1500.0, juegoRAMs.obtenerPrecio(),
            "El precio debe incluir todos los componentes");
    }

    /**
     * Prueba el metodo mostrarDetalles heredado.
     *
     * <p>Verifica que mostrarDetalles funcione correctamente con modulos RAM.
     */
    @Test
    public void pruebaMostrarDetalles() {
        JuegoRAMs juegoRAMs = new JuegoRAMs("Memoria RAM", "Memoria");
        juegoRAMs.agregarRAM(crearRAM(8, 500.0));
        juegoRAMs.agregarRAM(crearRAM(16, 800.0));

        String detalles = juegoRAMs.mostrarDetalles();

        assertNotNull(detalles, "mostrarDetalles no debe retornar null");
        assertTrue(detalles.contains("Memoria RAM"),
            "Debe contener el nombre del conjunto");
        assertTrue(detalles.contains("Memoria"),
            "Debe contener el tipo del conjunto");
    }

    /**
     * Prueba obtenerMarca con modulos RAM.
     *
     * <p>Verifica que obtenerMarca retorne la marca del primer modulo RAM.
     */
    @Test
    public void pruebaObtenerMarcaConModulos() {
        JuegoRAMs juegoRAMs = new JuegoRAMs("Memoria RAM", "Memoria");
        RAM ram = crearRAM(8, 500.0);
        juegoRAMs.agregarRAM(ram);

        assertEquals("Kingston", juegoRAMs.obtenerMarca(),
            "La marca debe ser la del primer modulo");
    }

    /**
     * Prueba la inmutabilidad del limite de 4 modulos.
     *
     * <p>Verifica que multiples intentos de agregar mas de 4 modulos
     * siempre fallen.
     */
    @Test
    public void prubaInmutabilidadLimite4Modulos() {
        JuegoRAMs juegoRAMs = new JuegoRAMs("Memoria RAM", "Memoria");

        Stream.of(8, 8, 8, 8).forEach(capacidad -> juegoRAMs.agregarRAM(crearRAM(capacidad, 500.0)));

        Stream.of(16, 32, 64).forEach(capacidad -> {
            RAM ramExtra = crearRAM(capacidad, 600.0);
            assertFalse(juegoRAMs.agregarRAM(ramExtra),
                String.format("No debe poder agregar modulo de %dGB despues del limite", capacidad));
        });

        assertEquals(32, juegoRAMs.getCapacidadAlmacenamiento(),
            "La capacidad debe permanecer en 32GB");
    }
}
