package modelo.componente;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Clase de pruebas unitarias para ComponenteCompuesto.
 *
 * <p>Valida la correcta creacion y funcionamiento de componentes compuestos,
 * implementando el patron Composite para manejar jerarquias de componentes.
 *
 * <p>Esta suite de pruebas verifica:
 * <ul>
 *   <li>Creacion correcta de componentes compuestos con y sin componentes iniciales</li>
 *   <li>Funcionamiento correcto de agregar y remover componentes</li>
 *   <li>Calculo correcto del precio total (suma de precios de componentes hijos)</li>
 *   <li>Funcionamiento del metodo obtenerHijo con indices validos e invalidos</li>
 *   <li>Funcionamiento correcto del iterador personalizado</li>
 *   <li>Manejo de excepciones para operaciones invalidas</li>
 *   <li>Funcionamiento con listas null y vacias</li>
 * </ul>
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class ComponenteCompuestoTest {

    /**
     * Crea una lista de componentes de prueba.
     * Esta funcion es pura y no tiene efectos secundarios.
     *
     * @return lista de componentes de prueba
     */
    private static List<ComponentePC> crearComponentesPrueba() {
        return Stream.of(
            new CPU("Core i5", 1000.0, "Intel", "CPU", 6, "x86-64"),
            new RAM("8GB DDR4", 500.0, "Kingston", "RAM", 8, "DDR4"),
            new GPU("RTX 3060", 2000.0, "NVIDIA", "GPU", "GDDR6", 12)
        ).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    /**
     * Calcula el precio total de una lista de componentes.
     * Esta funcion es pura y no tiene efectos secundarios.
     *
     * @param componentes lista de componentes
     * @return precio total
     */
    private static double calcularPrecioTotal(List<ComponentePC> componentes) {
        return componentes.stream()
            .mapToDouble(ComponentePC::obtenerPrecio)
            .sum();
    }

    /**
     * Cuenta el numero de componentes usando el iterador.
     * Esta funcion es pura y no tiene efectos secundarios.
     *
     * @param compuesto el componente compuesto
     * @return numero de componentes
     */
    private static int contarComponentesConIterador(ComponenteCompuesto compuesto) {
        IteratorComponentePC iterator = compuesto.getIterator();
        int count = 0;
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }
        return count;
    }

    /**
     * Prueba la creacion de un componente compuesto con lista inicial null.
     *
     * <p>Verifica que se pueda crear un componente compuesto sin componentes iniciales
     * y que el precio sea 0.
     */
    @Test
    public void pruebaCrearComponenteCompuestoConListaNull() {
        ComponenteCompuesto compuesto = new ComponenteCompuesto(null, "Compuesto Test", "Tipo Test");

        assertNotNull(compuesto, "El componente compuesto no debe ser null");
        assertEquals("Compuesto Test", compuesto.obtenerNombre(),
            "El nombre debe ser el especificado");
        assertEquals("Tipo Test", compuesto.obtenerTipo(),
            "El tipo debe ser el especificado");
        assertEquals(0.0, compuesto.obtenerPrecio(),
            "El precio de un compuesto vacio debe ser 0");
    }

    /**
     * Prueba la creacion de un componente compuesto con lista inicial de componentes.
     *
     * <p>Verifica que se pueda crear un componente compuesto con componentes iniciales
     * y que el precio sea la suma de los precios de los componentes.
     */
    @Test
    public void pruebaCrearComponenteCompuestoConListaInicial() {
        List<ComponentePC> componentes = crearComponentesPrueba();
        double precioEsperado = calcularPrecioTotal(componentes);

        ComponenteCompuesto compuesto = new ComponenteCompuesto(componentes, "PC Completa", "Computadora");

        assertNotNull(compuesto, "El componente compuesto no debe ser null");
        assertEquals("PC Completa", compuesto.obtenerNombre(),
            "El nombre debe ser el especificado");
        assertEquals("Computadora", compuesto.obtenerTipo(),
            "El tipo debe ser el especificado");
        assertEquals(precioEsperado, compuesto.obtenerPrecio(),
            "El precio debe ser la suma de los precios de los componentes");
    }

    /**
     * Prueba el metodo agregar con un componente valido.
     *
     * <p>Verifica que se pueda agregar un componente correctamente y que
     * el precio total se actualice.
     */
    @Test
    public void pruebaAgregarComponenteValido() {
        ComponenteCompuesto compuesto = new ComponenteCompuesto(null, "PC", "Computadora");
        CPU cpu = new CPU("Core i7", 2000.0, "Intel", "CPU", 8, "x86-64");

        compuesto.agregar(cpu);

        assertEquals(2000.0, compuesto.obtenerPrecio(),
            "El precio debe ser el precio del componente agregado");
        assertEquals(1, contarComponentesConIterador(compuesto),
            "Debe haber 1 componente");
    }

    /**
     * Prueba el metodo agregar con multiples componentes.
     *
     * <p>Verifica que se puedan agregar multiples componentes y que
     * el precio total sea la suma de todos.
     */
    @Test
    public void pruebaAgregarMultiplesComponentes() {
        ComponenteCompuesto compuesto = new ComponenteCompuesto(null, "PC", "Computadora");
        List<ComponentePC> componentes = crearComponentesPrueba();

        componentes.forEach(compuesto::agregar);

        double precioEsperado = calcularPrecioTotal(componentes);
        assertEquals(precioEsperado, compuesto.obtenerPrecio(),
            "El precio debe ser la suma de todos los componentes");
        assertEquals(componentes.size(), contarComponentesConIterador(compuesto),
            "El numero de componentes debe coincidir");
    }

    /**
     * Prueba el metodo agregar con componente null.
     *
     * <p>Verifica que se lance IllegalArgumentException cuando se intenta
     * agregar un componente null.
     */
    @Test
    public void pruebaAgregarComponenteNullLanzaExcepcion() {
        ComponenteCompuesto compuesto = new ComponenteCompuesto(null, "PC", "Computadora");

        IllegalArgumentException excepcion = assertThrows(
            IllegalArgumentException.class,
            () -> compuesto.agregar(null),
            "Deberia lanzar IllegalArgumentException al agregar null"
        );

        assertTrue(excepcion.getMessage().contains("null"),
            "El mensaje debe indicar que el componente no puede ser null");
    }

    /**
     * Prueba el metodo remover con un componente existente.
     *
     * <p>Verifica que se pueda remover un componente correctamente y que
     * el precio total se actualice.
     */
    @Test
    public void pruebaRemoverComponenteExistente() {
        List<ComponentePC> componentes = crearComponentesPrueba();
        ComponenteCompuesto compuesto = new ComponenteCompuesto(new ArrayList<>(componentes),
                                                                 "PC", "Computadora");
        ComponentePC componenteARemover = componentes.get(0);
        double precioInicial = compuesto.obtenerPrecio();
        double precioComponente = componenteARemover.obtenerPrecio();

        compuesto.remover(componenteARemover);

        assertEquals(precioInicial - precioComponente, compuesto.obtenerPrecio(),
            "El precio debe reducirse por el precio del componente removido");
        assertEquals(componentes.size() - 1, contarComponentesConIterador(compuesto),
            "El numero de componentes debe reducirse en 1");
    }

    /**
     * Prueba el metodo remover con componente null.
     *
     * <p>Verifica que se lance IllegalStateException cuando se intenta
     * remover null.
     */
    @Test
    public void pruebaRemoverComponenteNullLanzaExcepcion() {
        ComponenteCompuesto compuesto = new ComponenteCompuesto(crearComponentesPrueba(),
                                                                 "PC", "Computadora");

        IllegalStateException excepcion = assertThrows(
            IllegalStateException.class,
            () -> compuesto.remover(null),
            "Deberia lanzar IllegalStateException al remover null"
        );

        assertTrue(excepcion.getMessage().contains("remover"),
            "El mensaje debe indicar que no hay nada para remover");
    }

    /**
     * Prueba el metodo remover cuando la lista de componentes es null.
     *
     * <p>Verifica que se lance IllegalStateException cuando se intenta
     * remover de una lista null.
     */
    @Test
    public void pruebaRemoverConListaNullLanzaExcepcion() {
        ComponenteCompuesto compuesto = new ComponenteCompuesto(null, "PC", "Computadora");
        CPU cpu = new CPU("Core i5", 1000.0, "Intel", "CPU", 6, "x86-64");

        IllegalStateException excepcion = assertThrows(
            IllegalStateException.class,
            () -> compuesto.remover(cpu),
            "Deberia lanzar IllegalStateException al remover de lista null"
        );

        assertNotNull(excepcion.getMessage(),
            "El mensaje de error no debe ser null");
    }

    /**
     * Prueba el metodo obtenerHijo con indice valido.
     *
     * <p>Verifica que se pueda obtener un componente hijo usando un indice valido.
     */
    @Test
    public void pruebaObtenerHijoConIndiceValido() {
        List<ComponentePC> componentes = crearComponentesPrueba();
        ComponenteCompuesto compuesto = new ComponenteCompuesto(componentes, "PC", "Computadora");

        Stream.of(0, 1, 2).forEach(indice -> {
            ComponentePC hijo = compuesto.obtenerHijo(indice);
            assertEquals(componentes.get(indice), hijo,
                String.format("El hijo en indice %d debe ser el componente correcto", indice));
        });
    }

    /**
     * Prueba el metodo obtenerHijo con indice invalido.
     *
     * <p>Verifica que se lance IllegalArgumentException cuando se usa un indice invalido.
     */
    @Test
    public void pruebaObtenerHijoConIndiceInvalidoLanzaExcepcion() {
        ComponenteCompuesto compuesto = new ComponenteCompuesto(crearComponentesPrueba(),
                                                                 "PC", "Computadora");

        Stream.of(-1, 5, 100).forEach(indice -> {
            assertThrows(
                IllegalArgumentException.class,
                () -> compuesto.obtenerHijo(indice),
                String.format("Deberia lanzar IllegalArgumentException para indice %d", indice)
            );
        });
    }

    /**
     * Prueba el metodo obtenerHijo cuando la lista es null.
     *
     * <p>Verifica que se lance IllegalStateException cuando se intenta
     * obtener un hijo de una lista null.
     */
    @Test
    public void pruebaObtenerHijoConListaNullLanzaExcepcion() {
        ComponenteCompuesto compuesto = new ComponenteCompuesto(null, "PC", "Computadora");

        IllegalStateException excepcion = assertThrows(
            IllegalStateException.class,
            () -> compuesto.obtenerHijo(0),
            "Deberia lanzar IllegalStateException cuando la lista es null"
        );

        assertTrue(excepcion.getMessage().contains("componentes"),
            "El mensaje debe indicar que no hay componentes");
    }

    /**
     * Prueba el metodo obtenerPrecio con componentes anidados.
     *
     * <p>Verifica que el precio se calcule correctamente incluso con
     * componentes compuestos anidados.
     */
    @Test
    public void pruebaObtenerPrecioConComponentesAnidados() {
        ComponenteCompuesto compuestoAnidado = new ComponenteCompuesto(
            crearComponentesPrueba(), "SubPC", "SubComputadora");
        double precioAnidado = compuestoAnidado.obtenerPrecio();

        ComponenteCompuesto compuestoPadre = new ComponenteCompuesto(null, "PC Padre", "Computadora");
        compuestoPadre.agregar(compuestoAnidado);
        CPU cpuExtra = new CPU("Core i9", 3000.0, "Intel", "CPU", 16, "x86-64");
        compuestoPadre.agregar(cpuExtra);

        assertEquals(precioAnidado + 3000.0, compuestoPadre.obtenerPrecio(),
            "El precio debe incluir el precio del compuesto anidado");
    }

    /**
     * Prueba el metodo obtenerMarca con componentes.
     *
     * <p>Verifica que obtenerMarca retorne la marca del primer componente.
     */
    @Test
    public void pruebaObtenerMarcaConComponentes() {
        List<ComponentePC> componentes = crearComponentesPrueba();
        ComponenteCompuesto compuesto = new ComponenteCompuesto(componentes, "PC", "Computadora");

        assertEquals(componentes.get(0).obtenerMarca(), compuesto.obtenerMarca(),
            "La marca debe ser la del primer componente");
    }

    /**
     * Prueba el metodo obtenerMarca con lista vacia o null.
     *
     * <p>Verifica que obtenerMarca retorne "Sin marca" cuando no hay componentes.
     */
    @Test
    public void pruebaObtenerMarcaConListaVacia() {
        ComponenteCompuesto compuestoNull = new ComponenteCompuesto(null, "PC", "Computadora");
        ComponenteCompuesto compuestoVacio = new ComponenteCompuesto(new ArrayList<>(), "PC", "Computadora");

        assertEquals("Sin marca", compuestoNull.obtenerMarca(),
            "Debe retornar 'Sin marca' cuando la lista es null");
        assertEquals("Sin marca", compuestoVacio.obtenerMarca(),
            "Debe retornar 'Sin marca' cuando la lista esta vacia");
    }

    /**
     * Prueba el metodo mostrarDetalles.
     *
     * <p>Verifica que mostrarDetalles genere una representacion jerarquica
     * de todos los componentes.
     */
    @Test
    public void pruebaMostrarDetalles() {
        ComponenteCompuesto compuesto = new ComponenteCompuesto(crearComponentesPrueba(),
                                                                 "PC Gamer", "Computadora");
        String detalles = compuesto.mostrarDetalles();

        assertNotNull(detalles, "mostrarDetalles no debe retornar null");
        assertTrue(detalles.contains("PC Gamer"),
            "Debe contener el nombre del compuesto");
        assertTrue(detalles.contains("Computadora"),
            "Debe contener el tipo del compuesto");
        assertTrue(detalles.contains("Core i5"),
            "Debe contener detalles de los componentes hijos");
    }

    /**
     * Prueba el iterador hasNext y next.
     *
     * <p>Verifica que el iterador funcione correctamente para recorrer
     * todos los componentes.
     */
    @Test
    public void pruebaIteradorHasNextYNext() {
        List<ComponentePC> componentes = crearComponentesPrueba();
        ComponenteCompuesto compuesto = new ComponenteCompuesto(componentes, "PC", "Computadora");
        IteratorComponentePC iterator = compuesto.getIterator();

        int count = 0;
        while (iterator.hasNext()) {
            ComponentePC componente = iterator.next();
            assertNotNull(componente, "El componente del iterador no debe ser null");
            assertTrue(componentes.contains(componente),
                "El componente debe estar en la lista original");
            count++;
        }

        assertEquals(componentes.size(), count,
            "El iterador debe recorrer todos los componentes");
    }

    /**
     * Prueba el iterador con lista vacia.
     *
     * <p>Verifica que el iterador funcione correctamente con una lista vacia.
     */
    @Test
    public void pruebaIteradorConListaVacia() {
        ComponenteCompuesto compuesto = new ComponenteCompuesto(new ArrayList<>(), "PC", "Computadora");
        IteratorComponentePC iterator = compuesto.getIterator();

        assertFalse(iterator.hasNext(),
            "hasNext debe retornar false para lista vacia");
    }

    /**
     * Prueba el iterador lanzando excepcion cuando no hay mas elementos.
     *
     * <p>Verifica que el iterador lance NoSuchElementException cuando se llama
     * next() sin mas elementos.
     */
    @Test
    public void pruebaIteradorNextSinElementosLanzaExcepcion() {
        ComponenteCompuesto compuesto = new ComponenteCompuesto(new ArrayList<>(), "PC", "Computadora");
        IteratorComponentePC iterator = compuesto.getIterator();

        assertThrows(
            java.util.NoSuchElementException.class,
            iterator::next,
            "Deberia lanzar NoSuchElementException cuando no hay mas elementos"
        );
    }

    /**
     * Prueba la implementacion de ComponentePC.
     *
     * <p>Verifica que ComponenteCompuesto implemente correctamente ComponentePC.
     */
    @Test
    public void pruebaImplementaComponentePC() {
        ComponenteCompuesto compuesto = new ComponenteCompuesto(null, "PC", "Computadora");

        assertTrue(compuesto instanceof ComponentePC,
            "ComponenteCompuesto debe implementar ComponentePC");
    }

    /**
     * Prueba agregar componentes a un compuesto creado con lista null.
     *
     * <p>Verifica que se pueda agregar componentes a un compuesto que inicialmente
     * tenia lista null, y que la lista se inicialice automaticamente.
     */
    @Test
    public void pruebaAgregarACompuestoConListaNull() {
        ComponenteCompuesto compuesto = new ComponenteCompuesto(null, "PC", "Computadora");
        CPU cpu = new CPU("Core i5", 1000.0, "Intel", "CPU", 6, "x86-64");

        compuesto.agregar(cpu);

        assertEquals(1000.0, compuesto.obtenerPrecio(),
            "El precio debe ser el del componente agregado");
        assertEquals(1, contarComponentesConIterador(compuesto),
            "Debe haber 1 componente");
    }
}
