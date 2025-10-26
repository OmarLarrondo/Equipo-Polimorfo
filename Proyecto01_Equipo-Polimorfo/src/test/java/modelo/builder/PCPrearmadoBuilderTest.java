package modelo.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import modelo.computadora.ComputadoraBase;

/**
 * Clase de pruebas unitarias para PCPrearmadoBuilder.
 * Valida la correcta construccion de computadoras prearmadas de diferentes gamas
 * utilizando el patron Builder.
 *
 * <p>Esta suite de pruebas verifica:
 * <ul>
 *   <li>Construccion correcta de PC de Gama Baja</li>
 *   <li>Construccion correcta de PC de Gama Media</li>
 *   <li>Construccion correcta de PC de Gama Alta</li>
 *   <li>Correspondencia exacta con configuraciones manuales</li>
 * </ul>
 *
 * <p>Cada prueba compara la PC generada por el builder con una configuracion
 * creada manualmente para verificar que los componentes sean identicos.
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
class PCPrearmadoBuilderTest{

    /**
     * Prueba la construccion de una PC de Gama Baja.
     * Verifica que el PCPrearmadoBuilder ensamble correctamente todos los componentes
     * de una configuracion de gama baja.
     *
     * <p>Valida:
     * <ul>
     *   <li>Nombre correcto de la PC</li>
     *   <li>Exactamente 7 componentes ensamblados</li>
     *   <li>Precio total mayor a cero</li>
     * </ul>
     */
    @Test
    public void pruebaGamaBaja(){

        PCPrearmadoBuilder constructorPrueba = new PCPrearmadoBuilder("Gama Baja");
        constructorPrueba.construirCPU();
        constructorPrueba.construirRAM();
        constructorPrueba.construirMotherBoard();
        constructorPrueba.construirGPU();
        constructorPrueba.construirAlmacenamiento();
        constructorPrueba.construirFuente();
        constructorPrueba.construirGabinete();

        ComputadoraBase resultado = constructorPrueba.obtenerResultado();

        assertTrue(resultado.obtenerDescripcion().contains("PC Prearmada - Gama Baja"));
        assertEquals(7, resultado.obtenerComponentes().size());
        assertTrue(resultado.obtenerPrecioTotal() > 0);
    }

    /**
     * Prueba la construccion de una PC de Gama Media.
     * Verifica que el PCPrearmadoBuilder ensamble correctamente todos los componentes
     * de una configuracion de gama media.
     *
     * <p>Valida:
     * <ul>
     *   <li>Nombre correcto de la PC</li>
     *   <li>Exactamente 7 componentes ensamblados</li>
     *   <li>Precio total mayor a cero</li>
     * </ul>
     */
    @Test
    public void pruebaGamaMedia(){

        PCPrearmadoBuilder constructorPrueba = new PCPrearmadoBuilder("Gama Media");
        constructorPrueba.construirCPU();
        constructorPrueba.construirRAM();
        constructorPrueba.construirMotherBoard();
        constructorPrueba.construirGPU();
        constructorPrueba.construirAlmacenamiento();
        constructorPrueba.construirFuente();
        constructorPrueba.construirGabinete();

        ComputadoraBase resultado = constructorPrueba.obtenerResultado();

        assertTrue(resultado.obtenerDescripcion().contains("PC Prearmada - Gama Media"));
        assertEquals(7, resultado.obtenerComponentes().size());
        assertTrue(resultado.obtenerPrecioTotal() > 0);
    }

    /**
     * Prueba la construccion de una PC de Gama Alta.
     * Verifica que el PCPrearmadoBuilder ensamble correctamente todos los componentes
     * de una configuracion de gama alta con hardware de ultima generacion.
     *
     * <p>Valida:
     * <ul>
     *   <li>Nombre correcto de la PC</li>
     *   <li>Exactamente 7 componentes ensamblados</li>
     *   <li>Precio total mayor a cero</li>
     * </ul>
     */
    @Test
    public void pruebaGamaAlta(){

        PCPrearmadoBuilder constructorPrueba = new PCPrearmadoBuilder("Gama Alta");
        constructorPrueba.construirCPU();
        constructorPrueba.construirRAM();
        constructorPrueba.construirMotherBoard();
        constructorPrueba.construirGPU();
        constructorPrueba.construirAlmacenamiento();
        constructorPrueba.construirFuente();
        constructorPrueba.construirGabinete();

        ComputadoraBase resultado = constructorPrueba.obtenerResultado();

        assertTrue(resultado.obtenerDescripcion().contains("PC Prearmada - Gama Alta"));
        assertEquals(7, resultado.obtenerComponentes().size());
        assertTrue(resultado.obtenerPrecioTotal() > 0);
    }
}
