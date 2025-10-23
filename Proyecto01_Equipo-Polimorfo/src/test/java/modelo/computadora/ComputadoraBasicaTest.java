package modelo.computadora;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.jupiter.api.Test;

import java.util.List;

import modelo.computadora.ComputadoraBasica;
import modelo.componente.CPU;
import modelo.componente.Disco;
import modelo.componente.FuenteAlimentacion;
import modelo.componente.Gabinete;
import modelo.componente.GPU;
import modelo.componente.Gabinete;
import modelo.componente.MotherBoard;
import modelo.componente.RAM;
import modelo.componente.JuegoDiscos;
import modelo.componente.JuegoRAMs;
import modelo.decorador.AutoCADDecorator;
import modelo.decorador.OfficeDecorator;
import modelo.decorador.PhotoshopDecorator;
import modelo.decorador.WindowsDecorator;
import modelo.decorador.WSLDecorator;
import modelo.decorador.AutoCADDecorator;
import modelo.decorador.PhotoshopDecorator;

class ComputadoraBasicaTest{
    @Test
    public void pruebaPrecioTotal(){
        ComputadoraBasica compuNueva = new ComputadoraBasica("miCompuPrueba1");
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
        JuegoRAMs rams = new JuegoRAMs(null, "juegoRAMs", "Juego de RAMs", capacidadTotalRAMs, null);
        rams.agregarRAM(ram1);
        rams.agregarRAM(ram2);
        rams.agregarRAM(ram3);

        int capacidadTotalDiscos = disco1.getCapacidadAlmacenamiento()+disco2.getCapacidadAlmacenamiento();
        JuegoDiscos discos = new JuegoDiscos(capacidadTotalDiscos, "Juego de Discos", null, "juegoDiscos", "Juego de Discos");
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

        compuNuevaConWindowsYWSL.agregarComponente(gabinete);
        compuNuevaConWindowsYWSL.agregarComponente(rams);
        compuNuevaConWindowsYWSL.agregarComponente(disco1);

        double precioTotal = 7000.00+1800.00+1900.00+3000.00+9560.00+(2200.00+800.00)+(1000.00+1000.00+1000.00);

        assertEquals(precioTotal, compuNuevaConWindowsYWSL.obtenerPrecioTotal());
    }

    @Test
    public void pruebaDescripcion(){
        ComputadoraBasica compuNueva = new ComputadoraBasica("miCompuPrueba1");
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
        JuegoRAMs rams = new JuegoRAMs(null, "juegoRAMs", "Juego de RAMs", capacidadTotalRAMs, null);
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

        assertEquals("resultado descripcion software aquí", compuNuevaConWindowsYAutoCADYPhotoshopYWSL.obtenerDescripcion());
    }

    @Test
    public void pruebaTieneSoftware(){
        ComputadoraBasica compuNueva = new ComputadoraBasica("miCompuPrueba1");
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
        JuegoRAMs rams = new JuegoRAMs(null, "juegoRAMs", "Juego de RAMs", capacidadTotalRAMs, null);
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

        boolean[] resultadosEsperados = {true, true, false, true, false, false};
        boolean[] softwareInstalado = {
            compuNuevaConWindowsYPhotoshopYWSL.tieneSotfware("Windows"),
            compuNuevaConWindowsYPhotoshopYWSL.tieneSotfware("Programa pirata >:)"),
            compuNuevaConWindowsYPhotoshopYWSL.tieneSotfware("Photoshop"),
            compuNuevaConWindowsYPhotoshopYWSL.tieneSotfware("WSL"),
            compuNuevaConWindowsYPhotoshopYWSL.tieneSotfware("AutoCAD"),
            compuNuevaConWindowsYPhotoshopYWSL.tieneSotfware("Office")
        };

        assertEquals(resultadosEsperados, softwareInstalado);
    }
}