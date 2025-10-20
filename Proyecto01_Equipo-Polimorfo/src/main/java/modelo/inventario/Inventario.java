package modelo.inventario;

import modelo.componente.*;
import java.util.*;
import java.util.stream.Stream;

/**
 * Clase Singleton que gestiona el inventario completo de componentes disponibles.
 *
 * <p>Esta clase mantiene un catálogo organizado por tipos de componentes (CPU, RAM,
 * MotherBoard, Disco, GPU, FuenteAlimentacion, Gabinete) y proporciona métodos para
 * acceder a ellos. También gestiona configuraciones prearmadas de computadoras.
 *
 * <p>Utiliza el patrón Singleton para garantizar una única instancia del inventario
 * en todo el sistema.
 *
 * @author Equipo-polimorfo
 * @version 1.0
 */
public class Inventario {

    private static Inventario instancia;

    private Map<String, List<ComponentePC>> catalogo;

    /**
     * Constructor privado que inicializa el catálogo e invoca su carga inicial.
     * Este constructor es privado para implementar el patrón Singleton.
     */
    private Inventario() {
        this.catalogo = new HashMap<>();
        inicializarCatalogo();
    }

    /**
     * Obtiene la instancia única del inventario.
     * Si no existe, la crea; de lo contrario, retorna la existente.
     *
     * @return la instancia única de Inventario
     */
    public static Inventario getInstance() {
        return Optional.ofNullable(instancia)
            .orElseGet(() -> {
                instancia = new Inventario();
                return instancia;
            });
    }

    /**
     * Inicializa el catálogo con todos los componentes disponibles.
     * Carga los componentes organizados por tipo: CPU, RAM, MotherBoard,
     * Disco, GPU, FuenteAlimentacion y Gabinete.
     */
    public void inicializarCatalogo() {
        catalogo.put("CPU", crearListaCPUs());
        catalogo.put("RAM", crearListaRAMs());
        catalogo.put("MotherBoard", crearListaMotherBoards());
        catalogo.put("Disco", crearListaDiscos());
        catalogo.put("GPU", crearListaGPUs());
        catalogo.put("FuenteAlimentacion", crearListaFuentes());
        catalogo.put("Gabinete", crearListaGabinetes());
    }

    /**
     * Obtiene la lista de componentes de un tipo específico.
     *
     * @param tipo el tipo de componente a buscar
     * @return una lista de componentes del tipo especificado, o lista vacía si no existe
     */
    public List<ComponentePC> obtenerComponentesPorTipo(String tipo) {
        return Optional.ofNullable(catalogo.get(tipo))
            .map(ArrayList::new)
            .orElse(new ArrayList<>());
    }

    /**
     * Obtiene un componente específico por tipo y nombre.
     *
     * @param tipo el tipo del componente
     * @param nombre el nombre del componente
     * @return el componente encontrado, o null si no existe
     */
    public ComponentePC obtenerComponente(String tipo, String nombre) {
        return Optional.ofNullable(catalogo.get(tipo))
            .flatMap(lista -> lista.stream()
                .filter(componente -> componente.obtenerNombre().equals(nombre))
                .findFirst())
            .orElse(null);
    }

    /**
     * Agrega un componente al catálogo en su tipo correspondiente.
     * Si el tipo no existe en el catálogo, lo crea.
     *
     * @param componente el componente a agregar
     */
    public void agregarComponente(ComponentePC componente) {
        String tipo = componente.obtenerTipo();
        catalogo.computeIfAbsent(tipo, k -> new ArrayList<>()).add(componente);
    }

    /**
     * Obtiene las configuraciones prearmadas disponibles.
     * Retorna un mapa con nombres de configuraciones y sus listas de componentes.
     *
     * @return mapa de configuraciones prearmadas
     */
    public Map<String, List<ComponentePC>> obtenerConfiguracionesPrearmadas() {
        return Map.of(
            "Gama Baja", crearConfiguracionGamaBaja(),
            "Gama Media", crearConfiguracionGamaMedia(),
            "Gama Alta", crearConfiguracionGamaAlta()
        );
    }

    /**
     * Crea la lista completa de CPUs disponibles (Intel y AMD).
     *
     * @return lista de CPUs disponibles
     */
    private List<ComponentePC> crearListaCPUs() {
        return Stream.of(
            new CPU("Intel Core i3-13100", 8999.00, "Intel", "CPU", 4, "x86-64"),
            new CPU("Intel Core i5-13600K", 18999.00, "Intel", "CPU", 14, "x86-64"),
            new CPU("Intel Core i7-13700K", 24999.00, "Intel", "CPU", 16, "x86-64"),
            new CPU("Intel Core i9-13900K", 34999.00, "Intel", "CPU", 24, "x86-64"),
            new CPU("AMD Ryzen 5 5600G", 7999.00, "AMD", "CPU", 6, "x86-64 (AMD64)"),
            new CPU("AMD Ryzen 5 7600X", 12999.00, "AMD", "CPU", 6, "x86-64 (AMD64)"),
            new CPU("AMD Ryzen 7 7700X", 17999.00, "AMD", "CPU", 8, "x86-64 (AMD64)"),
            new CPU("AMD Ryzen 9 7950X3D", 32999.00, "AMD", "CPU", 16, "x86-64 (AMD64)")
        ).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    /**
     * Crea la lista completa de módulos RAM disponibles (Adata y Kingston).
     *
     * @return lista de RAMs disponibles
     */
    private List<ComponentePC> crearListaRAMs() {
        return Stream.of(
            new RAM("Adata 8GB DDR4", 599.00, "Adata", "RAM", 8, "DDR4"),
            new RAM("Adata 16GB DDR4", 1199.00, "Adata", "RAM", 16, "DDR4"),
            new RAM("Adata 32GB DDR4", 2399.00, "Adata", "RAM", 32, "DDR4"),
            new RAM("Kingston 8GB DDR4", 649.00, "Kingston", "RAM", 8, "DDR4"),
            new RAM("Kingston 16GB DDR4", 1299.00, "Kingston", "RAM", 16, "DDR4"),
            new RAM("Kingston 32GB DDR4", 2599.00, "Kingston", "RAM", 32, "DDR4")
        ).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    /**
     * Crea la lista completa de placas madre disponibles (ASUS y MSI, Intel y AMD).
     *
     * @return lista de MotherBoards disponibles
     */
    private List<ComponentePC> crearListaMotherBoards() {
        return Stream.of(
            new MotherBoard("ASUS ROG Maximus Z790 Hero", 19999.00, "ASUS", "MotherBoard", "Z790", "LGA1700", "x86-64"),
            new MotherBoard("ASUS TUF Gaming B760-Plus WIFI D4", 8999.00, "ASUS", "MotherBoard", "B760", "LGA1700", "x86-64"),
            new MotherBoard("MSI MEG Z790 Godlike", 29999.00, "MSI", "MotherBoard", "Z790", "LGA1700", "x86-64"),
            new MotherBoard("MSI MAG B760 Tomahawk WIFI DDR4", 7999.00, "MSI", "MotherBoard", "B760", "LGA1700", "x86-64"),
            new MotherBoard("ASUS ROG Crosshair X670E Hero", 18999.00, "ASUS", "MotherBoard", "X670E", "AM5", "x86-64"),
            new MotherBoard("ASUS TUF Gaming B650-Plus WIFI", 8499.00, "ASUS", "MotherBoard", "B650", "AM5", "x86-64"),
            new MotherBoard("MSI MEG X670E Godlike", 28999.00, "MSI", "MotherBoard", "X670E", "AM5", "x86-64"),
            new MotherBoard("MSI MAG B650 Tomahawk WIFI", 7499.00, "MSI", "MotherBoard", "B650", "AM5", "x86-64")
        ).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    /**
     * Crea la lista completa de discos disponibles (HDD y SSD).
     *
     * @return lista de Discos disponibles
     */
    private List<ComponentePC> crearListaDiscos() {
        return Stream.of(
            new Disco("Western Digital Blue 500GB", 899.00, "Western Digital", "Disco", 500, "HDD"),
            new Disco("Western Digital Blue 1TB", 1299.00, "Western Digital", "Disco", 1000, "HDD"),
            new Disco("Seagate Barracuda 1TB", 1199.00, "Seagate", "Disco", 1000, "HDD"),
            new Disco("Seagate Barracuda 2TB", 1999.00, "Seagate", "Disco", 2000, "HDD"),
            new Disco("Kingston A400 500GB", 1299.00, "Kingston", "Disco", 500, "SSD"),
            new Disco("Kingston A400 1TB", 2299.00, "Kingston", "Disco", 1000, "SSD"),
            new Disco("Kingston A400 2TB", 4299.00, "Kingston", "Disco", 2000, "SSD"),
            new Disco("Kingston A400 4TB", 8299.00, "Kingston", "Disco", 4000, "SSD")
        ).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    /**
     * Crea la lista completa de tarjetas gráficas disponibles (NVIDIA).
     *
     * @return lista de GPUs disponibles
     */
    private List<ComponentePC> crearListaGPUs() {
        return Stream.of(
            new GPU("NVIDIA GTX 1660", 4999.00, "NVIDIA", "GPU", "GDDR5", 6),
            new GPU("NVIDIA RTX 3060", 8999.00, "NVIDIA", "GPU", "GDDR6", 12),
            new GPU("NVIDIA RTX 4070", 14999.00, "NVIDIA", "GPU", "GDDR6X", 12),
            new GPU("NVIDIA RTX 4080", 24999.00, "NVIDIA", "GPU", "GDDR6X", 16),
            new GPU("NVIDIA RTX 4090", 39999.00, "NVIDIA", "GPU", "GDDR6X", 24)
        ).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    /**
     * Crea la lista completa de fuentes de alimentación disponibles (EVGA, Corsair, XPG).
     *
     * @return lista de fuentes disponibles
     */
    private List<ComponentePC> crearListaFuentes() {
        return Stream.of(
            new FuenteAlimentacion("EVGA 800W", 2499.00, "EVGA", "FuenteAlimentacion", 800, "80 PLUS Gold"),
            new FuenteAlimentacion("EVGA 1000W", 3299.00, "EVGA", "FuenteAlimentacion", 1000, "80 PLUS Gold"),
            new FuenteAlimentacion("EVGA 1500W", 4999.00, "EVGA", "FuenteAlimentacion", 1500, "80 PLUS Platinum"),
            new FuenteAlimentacion("Corsair 800W", 2699.00, "Corsair", "FuenteAlimentacion", 800, "80 PLUS Gold"),
            new FuenteAlimentacion("Corsair 1200W", 3999.00, "Corsair", "FuenteAlimentacion", 1200, "80 PLUS Platinum"),
            new FuenteAlimentacion("Corsair 1500W", 5299.00, "Corsair", "FuenteAlimentacion", 1500, "80 PLUS Titanium"),
            new FuenteAlimentacion("XPG 500W", 1299.00, "XPG", "FuenteAlimentacion", 500, "80 PLUS Bronze"),
            new FuenteAlimentacion("XPG 700W", 1899.00, "XPG", "FuenteAlimentacion", 700, "80 PLUS Bronze"),
            new FuenteAlimentacion("XPG 1000W", 2799.00, "XPG", "FuenteAlimentacion", 1000, "80 PLUS Gold")
        ).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    /**
     * Crea la lista completa de gabinetes disponibles (NZXT y Yeyian).
     *
     * @return lista de gabinetes disponibles
     */
    private List<ComponentePC> crearListaGabinetes() {
        return Stream.of(
            new Gabinete("NZXT H6 Flow ATX", 2299.00, "NZXT", "Gabinete", "ATX"),
            new Gabinete("Yeyian Lancer ATX", 1899.00, "Yeyian", "Gabinete", "ATX")
        ).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    /**
     * Crea una configuración prearmada de gama baja.
     *
     * @return lista de componentes para PC gama baja
     */
    private List<ComponentePC> crearConfiguracionGamaBaja() {
        return List.of(
            obtenerComponente("CPU", "Intel Core i3-13100"),
            obtenerComponente("RAM", "Kingston 8GB DDR4"),
            obtenerComponente("MotherBoard", "MSI MAG B760 Tomahawk WIFI DDR4"),
            obtenerComponente("Disco", "Kingston A400 500GB"),
            obtenerComponente("GPU", "NVIDIA GTX 1660"),
            obtenerComponente("FuenteAlimentacion", "XPG 500W"),
            obtenerComponente("Gabinete", "Yeyian Lancer ATX")
        );
    }

    /**
     * Crea una configuración prearmada de gama media.
     *
     * @return lista de componentes para PC gama media
     */
    private List<ComponentePC> crearConfiguracionGamaMedia() {
        return List.of(
            obtenerComponente("CPU", "AMD Ryzen 5 7600X"),
            obtenerComponente("RAM", "Adata 16GB DDR4"),
            obtenerComponente("MotherBoard", "ASUS TUF Gaming B650-Plus WIFI"),
            obtenerComponente("Disco", "Kingston A400 1TB"),
            obtenerComponente("GPU", "NVIDIA RTX 3060"),
            obtenerComponente("FuenteAlimentacion", "EVGA 800W"),
            obtenerComponente("Gabinete", "NZXT H6 Flow ATX")
        );
    }

    /**
     * Crea una configuración prearmada de gama alta.
     *
     * @return lista de componentes para PC gama alta
     */
    private List<ComponentePC> crearConfiguracionGamaAlta() {
        return List.of(
            obtenerComponente("CPU", "Intel Core i9-13900K"),
            obtenerComponente("RAM", "Kingston 32GB DDR4"),
            obtenerComponente("MotherBoard", "ASUS ROG Maximus Z790 Hero"),
            obtenerComponente("Disco", "Kingston A400 2TB"),
            obtenerComponente("GPU", "NVIDIA RTX 4090"),
            obtenerComponente("FuenteAlimentacion", "Corsair 1500W"),
            obtenerComponente("Gabinete", "NZXT H6 Flow ATX")
        );
    }
}
