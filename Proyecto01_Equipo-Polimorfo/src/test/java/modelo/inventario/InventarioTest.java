package modelo.inventario;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import modelo.componente.ComponentePC;

/**
 * Clase de pruebas unitarias para el Inventario singleton.
 * Valida el correcto funcionamiento de la gestion de componentes y configuraciones prearmadas.
 *
 * <p>Esta suite de pruebas verifica:
 * <ul>
 *   <li>Obtencion correcta de configuraciones prearmadas (Gama Baja, Media, Alta)</li>
 *   <li>Obtencion de componentes por tipo</li>
 *   <li>Busqueda de componentes especificos por tipo y nombre</li>
 *   <li>Manejo de componentes inexistentes</li>
 * </ul>
 *
 * <p>El Inventario implementa el patron Singleton para garantizar una unica instancia
 * compartida en toda la aplicacion.
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class InventarioTest{

    /**
     * Prueba la obtencion de configuraciones prearmadas del inventario.
     * Verifica que existan las tres gamas y que cada una contenga exactamente 7 componentes.
     *
     * <p>Configuraciones esperadas:
     * <ul>
     *   <li>Gama Baja: 7 componentes</li>
     *   <li>Gama Media: 7 componentes</li>
     *   <li>Gama Alta: 7 componentes</li>
     * </ul>
     */
    @Test
    public void pruebaConfiguracionesPrearmadas(){
        Inventario ventanaInventario = Inventario.getInstance();
        ventanaInventario.inicializarCatalogo();

        Map<String, List<ComponentePC>> configuraciones = ventanaInventario.obtenerConfiguracionesPrearmadas();

        assertNotNull(configuraciones.get("Gama Baja"));
        assertNotNull(configuraciones.get("Gama Media"));
        assertNotNull(configuraciones.get("Gama Alta"));

        assertEquals(7, configuraciones.get("Gama Baja").size());
        assertEquals(7, configuraciones.get("Gama Media").size());
        assertEquals(7, configuraciones.get("Gama Alta").size());
    }

    /**
     * Prueba la obtencion de componentes por tipo de hardware.
     * Verifica que el metodo obtenerComponentesPorTipo retorne las listas correctas
     * para cada tipo de componente hardware.
     *
     * <p>Valida que:
     * <ul>
     *   <li>Cada tipo de componente retorne una lista no nula</li>
     *   <li>Las listas contengan componentes</li>
     * </ul>
     */
    @Test
    public void pruebaComponentesPorTipo(){
        Inventario ventanaInventario = Inventario.getInstance();
        ventanaInventario.inicializarCatalogo();

        List<ComponentePC> cpus = ventanaInventario.obtenerComponentesPorTipo("CPU");
        List<ComponentePC> rams = ventanaInventario.obtenerComponentesPorTipo("RAM");
        List<ComponentePC> motherboards = ventanaInventario.obtenerComponentesPorTipo("MotherBoard");
        List<ComponentePC> discos = ventanaInventario.obtenerComponentesPorTipo("Disco");
        List<ComponentePC> gpus = ventanaInventario.obtenerComponentesPorTipo("GPU");
        List<ComponentePC> fuentes = ventanaInventario.obtenerComponentesPorTipo("FuenteAlimentacion");
        List<ComponentePC> gabinetes = ventanaInventario.obtenerComponentesPorTipo("Gabinete");

        assertNotNull(cpus);
        assertNotNull(rams);
        assertNotNull(motherboards);
        assertNotNull(discos);
        assertNotNull(gpus);
        assertNotNull(fuentes);
        assertNotNull(gabinetes);

        assertEquals(true, cpus.size() > 0);
        assertEquals(true, rams.size() > 0);
        assertEquals(true, motherboards.size() > 0);
        assertEquals(true, discos.size() > 0);
        assertEquals(true, gpus.size() > 0);
        assertEquals(true, fuentes.size() > 0);
        assertEquals(true, gabinetes.size() > 0);
    }

    /**
     * Prueba la busqueda de componentes especificos por tipo y nombre.
     * Verifica que el metodo obtenerComponente retorne el componente correcto
     * cuando existe, y null cuando no existe.
     *
     * <p>Casos de prueba:
     * <ul>
     *   <li>Componente existente: retorna objeto no nulo</li>
     *   <li>Componente con nombre desconocido: retorna null</li>
     *   <li>Tipo de componente desconocido: retorna null</li>
     * </ul>
     */
    @Test
    public void pruebaObtenerComponente(){
        Inventario ventanaInventario = Inventario.getInstance();
        ventanaInventario.inicializarCatalogo();

        List<ComponentePC> cpus = ventanaInventario.obtenerComponentesPorTipo("CPU");
        if (!cpus.isEmpty()) {
            ComponentePC primerCPU = cpus.get(0);
            ComponentePC cpuObtenido = ventanaInventario.obtenerComponente("CPU", primerCPU.obtenerNombre());
            assertNotNull(cpuObtenido);
            assertEquals(primerCPU.obtenerNombre(), cpuObtenido.obtenerNombre());
        }

        assertNull(ventanaInventario.obtenerComponente("CPU", "Nombre desconocido"));
        assertNull(ventanaInventario.obtenerComponente("Componente desconocido", "Nombre cualquiera"));
    }
}
