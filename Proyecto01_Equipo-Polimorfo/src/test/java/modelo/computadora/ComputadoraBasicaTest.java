package modelo.computadora;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import modelo.computadora.ComputadoraBasica;
import modelo.componente.CPU;
import modelo.componente.Disco;
import modelo.componente.FuenteAlimentacion;
import modelo.componente.Gabinete;
import modelo.componente.GPU;
import modelo.componente.MotherBoard;
import modelo.componente.RAM;
import modelo.componente.JuegoDiscos;
import modelo.componente.JuegoRAMs;
import modelo.decorador.AutoCADDecorator;
import modelo.decorador.OfficeDecorator;
import modelo.decorador.PhotoshopDecorator;
import modelo.decorador.WindowsDecorator;
import modelo.decorador.WSLDecorator;

/**
 * Clase de pruebas unitarias para validar el comportamiento de la clase ComputadoraBasica
 * y su interaccion con componentes de hardware y decoradores de software.
 *
 * Esta suite de pruebas verifica tres aspectos fundamentales:
 * 1. Calculo correcto del precio total (hardware + software)
 * 2. Generacion de descripciones completas con cadena de decoradores
 * 3. Deteccion de software instalado mediante navegacion de decoradores
 *
 * Patrones validados:
 * - Patron Decorator: Aplicacion dinamica de software (Windows, Office, AutoCAD, Photoshop, WSL)
 * - Patron Composite: Manejo de componentes compuestos (JuegoRAMs, JuegoDiscos)
 *
 * Esta clase implementa pruebas unitarias para el sistema de ensamblado de computadoras
 * de MonosChinos MX, validando la integracion entre componentes y decoradores.
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class ComputadoraBasicaTest{

    /**
     * Prueba unitaria para verificar el calculo correcto del precio total de una computadora
     * con multiples componentes de hardware y decoradores de software.
     *
     * Se valida que el metodo obtenerPrecioTotal() retorne la suma exacta de todos los
     * componentes agregados (CPU, fuente de alimentacion, gabinete, GPU, tarjeta madre,
     * conjunto de RAMs y conjunto de discos), incluyendo los costos adicionales de los
     * decoradores de software (Windows y WSL).
     *
     * Componentes de prueba:
     * - CPU Intel Core i3-13100: $7000.00
     * - Fuente EVGA Supernova G5: $1800.00
     * - Gabinete NXZT H6 Flow ATX: $1900.00
     * - GPU NVIDIA GTX 1660: $3000.00
     * - Tarjeta madre ASUS ROG Maximus Z790 Hero: $9560.00
     * - 3 modulos de RAM (8GB c/u): $3000.00 total
     * - 2 discos (2TB SSD + 1TB HDD): $3020.00 total
     * - Subtotal Hardware: $29280.00
     *
     * Decoradores aplicados:
     * - Windows: $350.00
     * - WSL: $200.00
     * - Subtotal Software: $550.00
     *
     * El precio total esperado es $29830.00 (hardware + software).
     *
     * Esta prueba valida que el patron Composite calcule correctamente los precios
     * de componentes compuestos (JuegoRAMs y JuegoDiscos) y que el patron Decorator
     * agregue correctamente los costos de software sin duplicacion.
     */
    @Test
    public void pruebaPrecioTotal(){
        ComputadoraBasica compuNueva = new ComputadoraBasica("miCompuPruebaPrecio");
        CPU procesador = new CPU("Core i3-13100", 7000.00, "Intel", "CPU", 4, "Raptor Lake (13a Gen)");
        FuenteAlimentacion fuente = new FuenteAlimentacion("Supernova G5", 1800.00, "EVGA", "Fuente de alimentacion", 1000, "Plus Gold");
        Gabinete gabinete = new Gabinete("H6 Flow ATX", 1900.00, "NXZT", "Gabinete", "ATX");
        GPU grafica = new GPU("GTX 1660", 3000.00, "NVIDIA", "Tarjeta grafica", "GDDR5", 6);
        MotherBoard tarjeta = new MotherBoard("ROG Maximus Z790 Hero", 9560.00, "ASUS", "Tarjeta madre", "Intel Z790 chipset", "LGA 1700", "Ninguna");

        Disco disco1 = new Disco("NV3", 2200.00, "Kingston", "SSD", 2048, "Unidad de bajo consumo");
        Disco disco2 = new Disco("WD10EZEX", 820.00, "Western Digital", "HDD", 1024, "Unidad de bajo consumo");

        RAM ram1 = new RAM("UDIMM", 1000.00, "Adata", "RAM", 8, "DDR5");
        RAM ram2 = new RAM("DIMM", 1000.00, "Kingston", "RAM", 8, "DDR4");
        RAM ram3 = new RAM("UDIMM", 1000.00, "Adata", "RAM", 8, "DDR5");

        int capacidadTotalRAMs = ram1.getCapacidadGB()+ram2.getCapacidadGB()+ram3.getCapacidadGB();
        JuegoRAMs rams = new JuegoRAMs("juegoRAMs", "RAM");
        rams.agregarRAM(ram1);
        rams.agregarRAM(ram2);
        rams.agregarRAM(ram3);

        int capacidadTotalDiscos = disco1.getCapacidadAlmacenamiento()+disco2.getCapacidadAlmacenamiento();
        JuegoDiscos discos = new JuegoDiscos("juegoDiscos", "Disco");
        discos.agregarDisco(disco1);
        discos.agregarDisco(disco2);

        compuNueva.agregarComponente(procesador);
        compuNueva.agregarComponente(fuente);
        compuNueva.agregarComponente(gabinete);
        compuNueva.agregarComponente(grafica);
        compuNueva.agregarComponente(tarjeta);

        compuNueva.agregarComponente(rams);
        compuNueva.agregarComponente(discos);

        WindowsDecorator compuNuevaConWindows = new WindowsDecorator(compuNueva, "Windows", 350.00);
        WSLDecorator compuNuevaConWindowsYWSL = new WSLDecorator(compuNuevaConWindows, "WSL", 200.00);

        double precioTotal = 29830.0;

        assertEquals(precioTotal, compuNuevaConWindowsYWSL.obtenerPrecioTotal());
    }

    /**
     * Prueba unitaria para verificar la generacion correcta de la descripcion completa
     * de una computadora decorada con multiples softwares.
     *
     * Se valida que el metodo obtenerDescripcion() retorne una cadena que incluya:
     * - La descripcion base de la computadora con sus componentes de hardware
     * - La informacion de todos los decoradores de software aplicados en orden
     *   (Windows, AutoCAD, Photoshop, WSL)
     *
     * Esta prueba verifica el patron Decorator mediante la cadena de responsabilidad
     * de descripciones, donde cada decorador agrega su propia informacion a la
     * descripcion heredada del componente envuelto.
     *
     * Componentes de prueba:
     *
     * Agregados a la computadora base:
     * - CPU Intel Core i3-13100
     * - Fuente EVGA Supernova G5
     * - Gabinete NXXT H6 Flow ATX
     * - GPU NVIDIA GTX 1660
     *
     * Agregados via decorador:
     * - Tarjeta madre ASUS ROG Maximus Z790 Hero
     * - 3 modulos de RAM de 8GB cada uno (JuegoRAMs)
     * - Disco SSD Kingston de 2TB
     *
     * Decoradores aplicados: Windows, AutoCAD, Photoshop, WSL
     */
    @Test
    public void pruebaDescripcion(){
        ComputadoraBasica compuNueva = new ComputadoraBasica("miCompuPruebaDescripcion");
        CPU procesador = new CPU("Core i3-13100", 7000.00, "Intel", "CPU", 4, "Raptor Lake (13a Gen)");
        FuenteAlimentacion fuente = new FuenteAlimentacion("Supernova G5", 1800.00, "EVGA", "Fuente de alimentacion", 1000, "Plus Gold");
        Gabinete gabinete = new Gabinete("H6 Flow ATX", 1900.00, "NXZT", "Gabinete", "ATX");
        GPU grafica = new GPU("GTX 1660", 3000.00, "NVIDIA", "Tarjeta grafica", "GDDR5", 6);
        MotherBoard tarjeta = new MotherBoard("ROG Maximus Z790 Hero", 9560.00, "ASUS", "Tarjeta madre", "Intel Z790 chipset", "LGA 1700", "Ninguna");

        Disco disco1 = new Disco("NV3", 2200.00, "Kingston", "SSD", 2048, "Unidad de bajo consumo");

        RAM ram1 = new RAM("UDIMM", 1000.00, "Adata", "RAM", 8, "DDR5");
        RAM ram2 = new RAM("DIMM", 1000.00, "Kingston", "RAM", 8, "DDR4");
        RAM ram3 = new RAM("UDIMM", 1000.00, "Adata", "RAM", 8, "DDR5");

        int capacidadTotalRAMs = ram1.getCapacidadGB()+ram2.getCapacidadGB()+ram3.getCapacidadGB();
        JuegoRAMs rams = new JuegoRAMs("juegoRAMs", "RAM");
        rams.agregarRAM(ram1);
        rams.agregarRAM(ram2);
        rams.agregarRAM(ram3);

        compuNueva.agregarComponente(procesador);
        compuNueva.agregarComponente(fuente);
        compuNueva.agregarComponente(gabinete);
        compuNueva.agregarComponente(grafica);

        WindowsDecorator compuNuevaConWindows = new WindowsDecorator(compuNueva, "Windows", 350.00);
        AutoCADDecorator compuNuevaConWindowsYAutoCAD = new AutoCADDecorator(compuNuevaConWindows, "AutoCAD", 200.00);
        PhotoshopDecorator compuNuevaConWindowsYAutoCADYPhotoshop = new PhotoshopDecorator(compuNuevaConWindowsYAutoCAD, "Photoshop", 200.00);
        WSLDecorator compuNuevaConWindowsYAutoCADYPhotoshopYWSL = new WSLDecorator(compuNuevaConWindowsYAutoCADYPhotoshop, "WSL", 200.00);

        compuNuevaConWindowsYAutoCADYPhotoshopYWSL.agregarComponente(tarjeta);
        compuNuevaConWindowsYAutoCADYPhotoshopYWSL.agregarComponente(rams);
        compuNuevaConWindowsYAutoCADYPhotoshopYWSL.agregarComponente(disco1);

        assertEquals("miCompuPruebaDescripcion\n  - CPU: Core i3-13100 | Marca: Intel | Tipo: CPU | Núcleos: 4 | Arquitectura: Raptor Lake (13a Gen) | Precio: $7000.00\n  - Fuente: Supernova G5 | Marca: EVGA | Potencia: 1000 W | Certificacion: Plus Gold | Precio: $1800.00\n  - Gabinete: H6 Flow ATX | Marca: NXZT | Tipo: Gabinete | Tamanio: ATX | Precio: $1900.00\n  - GPU: GTX 1660 | Marca: NVIDIA | Tipo: Tarjeta grafica | VRAM: 6 GB | Tipo Memoria: GDDR5 | Precio: $3000.00\n  - Motherboard: ROG Maximus Z790 Hero | Marca: ASUS | Chipset: Intel Z790 chipset | Socket: LGA 1700 | Arquitectura: Ninguna | Precio: $9560.00\n  - Componente: juegoRAMs (RAM)\n - RAM: UDIMM | Marca: Adata | Tipo: DDR5 | Capacidad: 8 GB | Precio: $1000.00\n - RAM: DIMM | Marca: Kingston | Tipo: DDR4 | Capacidad: 8 GB | Precio: $1000.00\n - RAM: UDIMM | Marca: Adata | Tipo: DDR5 | Capacidad: 8 GB | Precio: $1000.00\n\n  - Disco: NV3 | Marca: Kingston | Tipo: SSD | Capacidad: 2048 GB | Alimentación: Unidad de bajo consumo | Precio: $2200.00\n  + Software: Windows\n  + Software: AutoCAD\n  + Software: Photoshop\n  + Software: WSL", compuNuevaConWindowsYAutoCADYPhotoshopYWSL.obtenerDescripcion());
    }

    /**
     * Prueba unitaria para verificar la funcionalidad del metodo tieneSoftware()
     * que determina si un software especifico esta instalado en una computadora decorada.
     *
     * Se valida que el metodo retorne:
     * - true cuando el software especificado esta presente como decorador
     * - false cuando el software especificado no esta presente
     *
     * Esta prueba verifica la capacidad del patron Decorator para navegar por la cadena
     * de decoradores y detectar la presencia de software especifico, independientemente
     * de su posicion en la cadena de decoracion.
     *
     * Configuracion de prueba:
     * - Computadora base con CPU, fuente, gabinete y GPU
     * - Decoradores aplicados: Windows, Photoshop, WSL
     * - Decoradores NO aplicados: AutoCAD, Office
     *
     * Casos de prueba validados:
     * - Verificacion de Windows: debe retornar true (instalado)
     * - Verificacion de software inexistente ("Programa pirata >:)"): debe retornar false
     * - Verificacion de Photoshop: debe retornar true (instalado)
     * - Verificacion de WSL: debe retornar true (instalado)
     * - Verificacion de AutoCAD (no instalado): debe retornar false
     * - Verificacion de Office (no instalado): debe retornar false
     */
    @Test
    public void pruebaTieneSoftware(){
        ComputadoraBasica compuNueva = new ComputadoraBasica("miCompuPruebaSoftware");
        CPU procesador = new CPU("Core i3-13100", 7000.00, "Intel", "CPU", 4, "Raptor Lake (13a Gen)");
        FuenteAlimentacion fuente = new FuenteAlimentacion("Supernova G5", 1800.00, "EVGA", "Fuente de alimentacion", 1000, "Plus Gold");
        Gabinete gabinete = new Gabinete("H6 Flow ATX", 1900.00, "NXZT", "Gabinete", "ATX");
        GPU grafica = new GPU("GTX 1660", 3000.00, "NVIDIA", "Tarjeta grafica", "GDDR5", 6);
        MotherBoard tarjeta = new MotherBoard("ROG Maximus Z790 Hero", 9560.00, "ASUS", "Tarjeta madre", "Intel Z790 chipset", "LGA 1700", "Ninguna");

        Disco disco1 = new Disco("NV3", 2200.00, "Kingston", "SSD", 2048, "Unidad de bajo consumo");

        RAM ram1 = new RAM("UDIMM", 1000.00, "Adata", "RAM", 8, "DDR5");
        RAM ram2 = new RAM("DIMM", 1000.00, "Kingston", "RAM", 8, "DDR4");
        RAM ram3 = new RAM("UDIMM", 1000.00, "Adata", "RAM", 8, "DDR5");

        int capacidadTotalRAMs = ram1.getCapacidadGB()+ram2.getCapacidadGB()+ram3.getCapacidadGB();
        JuegoRAMs rams = new JuegoRAMs("juegoRAMs", "RAM");
        rams.agregarRAM(ram1);
        rams.agregarRAM(ram2);
        rams.agregarRAM(ram3);

        compuNueva.agregarComponente(procesador);
        compuNueva.agregarComponente(fuente);
        compuNueva.agregarComponente(gabinete);
        compuNueva.agregarComponente(grafica);

        WindowsDecorator compuNuevaConWindows = new WindowsDecorator(compuNueva, "Windows", 350.00);
        PhotoshopDecorator compuNuevaConWindowsYPhotoshop = new PhotoshopDecorator(compuNuevaConWindows, "Photoshop", 200.00);
        WSLDecorator compuNuevaConWindowsYPhotoshopYWSL = new WSLDecorator(compuNuevaConWindowsYPhotoshop, "WSL", 200.00);

        compuNuevaConWindowsYPhotoshopYWSL.agregarComponente(tarjeta);
        compuNuevaConWindowsYPhotoshopYWSL.agregarComponente(rams);
        compuNuevaConWindowsYPhotoshopYWSL.agregarComponente(disco1);

        boolean[] resultadosEsperados = {true, false, true, true, false, false};
        boolean[] softwareInstalado = {
            compuNuevaConWindowsYPhotoshopYWSL.tieneSoftware("Windows"),
            compuNuevaConWindowsYPhotoshopYWSL.tieneSoftware("Programa pirata >:)"),
            compuNuevaConWindowsYPhotoshopYWSL.tieneSoftware("Photoshop"),
            compuNuevaConWindowsYPhotoshopYWSL.tieneSoftware("WSL"),
            compuNuevaConWindowsYPhotoshopYWSL.tieneSoftware("AutoCAD"),
            compuNuevaConWindowsYPhotoshopYWSL.tieneSoftware("Office")
        };

        assertArrayEquals(resultadosEsperados, softwareInstalado);
    }

    /**
     * Prueba la funcionalidad de busqueda case-insensitive del metodo tieneSoftware().
     *
     * <p>Verifica que el metodo pueda encontrar software instalado independientemente
     * de como se escriba el nombre (mayusculas, minusculas o combinaciones).
     * Esta funcionalidad mejora la experiencia del usuario al evitar errores por
     * diferencias de capitalizacion.
     *
     * <p>Se prueban multiples variaciones de capitalizacion:
     * <ul>
     *   <li>Forma exacta como fue registrado</li>
     *   <li>Todo en minusculas</li>
     *   <li>Todo en mayusculas</li>
     *   <li>Combinaciones mixtas</li>
     * </ul>
     *
     * Configuracion de prueba:
     * - Computadora base "PC Test"
     * - Decoradores aplicados: Windows ($350.00), AutoCAD ($500.00)
     *
     * Validaciones realizadas:
     * - 4 variaciones de capitalizacion para Windows (instalado)
     * - 4 variaciones de capitalizacion para AutoCAD (instalado)
     * - 2 variaciones de capitalizacion para Photoshop (no instalado)
     *
     * Esta prueba valida el fix implementado en SoftwareDecorator.tieneSoftware()
     * que cambio de equals() a equalsIgnoreCase() para permitir busquedas
     * insensibles a mayusculas/minusculas.
     */
    @Test
    public void pruebaBusquedaSoftwareCaseInsensitive(){
        ComputadoraBasica compu = new ComputadoraBasica("PC Test");

        WindowsDecorator compuConWindows = new WindowsDecorator(compu, "Windows", 350.00);
        AutoCADDecorator compuCompleta = new AutoCADDecorator(compuConWindows, "AutoCAD", 500.00);

        assertTrue(compuCompleta.tieneSoftware("Windows"));
        assertTrue(compuCompleta.tieneSoftware("windows"));
        assertTrue(compuCompleta.tieneSoftware("WINDOWS"));
        assertTrue(compuCompleta.tieneSoftware("WiNdOwS"));

        assertTrue(compuCompleta.tieneSoftware("AutoCAD"));
        assertTrue(compuCompleta.tieneSoftware("autocad"));
        assertTrue(compuCompleta.tieneSoftware("AUTOCAD"));
        assertTrue(compuCompleta.tieneSoftware("AuToCAd"));

        assertFalse(compuCompleta.tieneSoftware("Photoshop"));
        assertFalse(compuCompleta.tieneSoftware("PHOTOSHOP"));
    }
}
