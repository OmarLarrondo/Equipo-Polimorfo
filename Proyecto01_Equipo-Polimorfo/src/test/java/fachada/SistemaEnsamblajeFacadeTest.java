package fachada;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import modelo.computadora.ComputadoraBase;
import modelo.fabrica.IntelFactory;

/**
 * Clase de pruebas unitarias para la fachada SistemaEnsamblajeFacade.
 * Valida el correcto funcionamiento de la construccion de PCs prearmadas y personalizadas,
 * asi como la agregacion de software mediante el patron Decorator.
 *
 * <p>Esta suite de pruebas verifica:
 * <ul>
 *   <li>Construccion correcta de PCs de Gama Baja, Media y Alta</li>
 *   <li>Agregacion de software a computadoras mediante decoradores</li>
 *   <li>Integracion del patron Facade con Builder y Decorator</li>
 * </ul>
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
class SistemaEnsamblajeFacadeTest{

    /**
     * Prueba la construccion de una PC prearmada de Gama Baja.
     * Verifica que el sistema ensamble correctamente una configuracion de gama baja
     * con todos los componentes y software especificados.
     *
     * <p>Valida:
     * <ul>
     *   <li>Nombre correcto de la PC</li>
     *   <li>Presencia de todos los softwares agregados</li>
     *   <li>Cantidad correcta de componentes (7)</li>
     *   <li>Precio total valido</li>
     * </ul>
     */
    @Test
    public void pruebaPCPrearmadoGamaBaja(){
        SistemaEnsamblajeFacade ensamblador = new SistemaEnsamblajeFacade();
        ensamblador.inicializarSistema();
        ComputadoraBase compuGenerada = ensamblador.construirPCPrearmada("Gama Baja");
        compuGenerada = ensamblador.agregarSoftware(compuGenerada, "Windows 10/11");
        compuGenerada = ensamblador.agregarSoftware(compuGenerada, "Microsoft Office 365");
        compuGenerada = ensamblador.agregarSoftware(compuGenerada, "Adobe Photoshop");
        compuGenerada = ensamblador.agregarSoftware(compuGenerada, "WSL (Windows Subsystem for Linux)");
        compuGenerada = ensamblador.agregarSoftware(compuGenerada, "AutoCAD");

        String descripcion = compuGenerada.obtenerDescripcion();

        assertTrue(descripcion.contains("PC Prearmada - Gama Baja"));
        assertTrue(descripcion.contains("Windows"));
        assertTrue(descripcion.contains("Office"));
        assertTrue(descripcion.contains("Photoshop"));
        assertTrue(descripcion.contains("AutoCAD"));
        assertEquals(7, compuGenerada.obtenerComponentes().size());
        assertTrue(compuGenerada.obtenerPrecioTotal() > 0);
    }

    /**
     * Prueba la construccion de una PC prearmada de Gama Media.
     * Verifica que el sistema ensamble correctamente una configuracion de gama media
     * con todos los componentes y software especificados.
     *
     * <p>Valida:
     * <ul>
     *   <li>Nombre correcto de la PC</li>
     *   <li>Presencia de todos los softwares agregados</li>
     *   <li>Cantidad correcta de componentes (7)</li>
     *   <li>Precio total valido</li>
     * </ul>
     */
    @Test
    public void pruebaPCPrearmadoGamaMedia(){
        SistemaEnsamblajeFacade ensamblador = new SistemaEnsamblajeFacade();
        ensamblador.inicializarSistema();
        ComputadoraBase compuGenerada = ensamblador.construirPCPrearmada("Gama Media");
        compuGenerada = ensamblador.agregarSoftware(compuGenerada, "Windows 10/11");
        compuGenerada = ensamblador.agregarSoftware(compuGenerada, "Microsoft Office 365");
        compuGenerada = ensamblador.agregarSoftware(compuGenerada, "Adobe Photoshop");
        compuGenerada = ensamblador.agregarSoftware(compuGenerada, "WSL (Windows Subsystem for Linux)");
        compuGenerada = ensamblador.agregarSoftware(compuGenerada, "AutoCAD");

        String descripcion = compuGenerada.obtenerDescripcion();

        assertTrue(descripcion.contains("PC Prearmada - Gama Media"));
        assertTrue(descripcion.contains("Windows"));
        assertTrue(descripcion.contains("Office"));
        assertTrue(descripcion.contains("Photoshop"));
        assertTrue(descripcion.contains("AutoCAD"));
        assertEquals(7, compuGenerada.obtenerComponentes().size());
        assertTrue(compuGenerada.obtenerPrecioTotal() > 0);
    }

    /**
     * Prueba la construccion de una PC prearmada de Gama Alta.
     * Verifica que el sistema ensamble correctamente una configuracion de gama alta
     * con todos los componentes y software especificados.
     *
     * <p>Valida:
     * <ul>
     *   <li>Nombre correcto de la PC</li>
     *   <li>Presencia de todos los softwares agregados</li>
     *   <li>Cantidad correcta de componentes (7)</li>
     *   <li>Precio total valido</li>
     * </ul>
     */
    @Test
    public void pruebaPCPrearmadoGamaAlta(){
        SistemaEnsamblajeFacade ensamblador = new SistemaEnsamblajeFacade();
        ensamblador.inicializarSistema();
        ComputadoraBase compuGenerada = ensamblador.construirPCPrearmada("Gama Alta");
        compuGenerada = ensamblador.agregarSoftware(compuGenerada, "Windows 10/11");
        compuGenerada = ensamblador.agregarSoftware(compuGenerada, "Microsoft Office 365");
        compuGenerada = ensamblador.agregarSoftware(compuGenerada, "Adobe Photoshop");
        compuGenerada = ensamblador.agregarSoftware(compuGenerada, "WSL (Windows Subsystem for Linux)");
        compuGenerada = ensamblador.agregarSoftware(compuGenerada, "AutoCAD");

        String descripcion = compuGenerada.obtenerDescripcion();

        assertTrue(descripcion.contains("PC Prearmada - Gama Alta"));
        assertTrue(descripcion.contains("Windows"));
        assertTrue(descripcion.contains("Office"));
        assertTrue(descripcion.contains("Photoshop"));
        assertTrue(descripcion.contains("AutoCAD"));
        assertEquals(7, compuGenerada.obtenerComponentes().size());
        assertTrue(compuGenerada.obtenerPrecioTotal() > 0);
    }

    /**
     * Prueba la construccion de una PC personalizada utilizando la fachada.
     * Verifica que el sistema construya correctamente una PC personalizada con factory Intel
     * y agregue el software especificado.
     *
     * <p>Esta prueba valida la integracion del patron Facade con el patron Builder
     * para PCs personalizadas y la correcta aplicacion de decoradores de software.
     */
    @Test
    public void pruebaPCPersonalizado(){
        SistemaEnsamblajeFacade ensamblador = new SistemaEnsamblajeFacade();
        ensamblador.inicializarSistema();
        ComputadoraBase compuGenerada = ensamblador.construirPCPersonalizada(new IntelFactory());
        compuGenerada = ensamblador.agregarSoftware(compuGenerada, "Windows 10/11");
        compuGenerada = ensamblador.agregarSoftware(compuGenerada, "Microsoft Office 365");
        compuGenerada = ensamblador.agregarSoftware(compuGenerada, "Adobe Photoshop");
        compuGenerada = ensamblador.agregarSoftware(compuGenerada, "WSL (Windows Subsystem for Linux)");
        compuGenerada = ensamblador.agregarSoftware(compuGenerada, "AutoCAD");

        String descripcion = compuGenerada.obtenerDescripcion();

        assertTrue(descripcion.contains("Windows"));
        assertTrue(descripcion.contains("Office"));
        assertTrue(descripcion.contains("Photoshop"));
        assertTrue(descripcion.contains("AutoCAD"));
        assertTrue(compuGenerada.obtenerPrecioTotal() > 0);
        assertEquals(7, compuGenerada.obtenerComponentes().size());
    }
}
