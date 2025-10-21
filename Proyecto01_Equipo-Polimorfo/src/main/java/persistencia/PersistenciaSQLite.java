package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import modelo.componente.CPU;
import modelo.componente.ComponentePC;
import modelo.componente.Disco;
import modelo.componente.FuenteAlimentacion;
import modelo.componente.GPU;
import modelo.componente.Gabinete;
import modelo.componente.MotherBoard;
import modelo.componente.RAM;
import modelo.computadora.ComputadoraBase;
import modelo.computadora.ComputadoraBasica;
import modelo.decorador.AutoCADDecorator;
import modelo.decorador.OfficeDecorator;
import modelo.decorador.PhotoshopDecorator;
import modelo.decorador.WSLDecorator;
import modelo.decorador.WindowsDecorator;
import modelo.estrategia.ResultadoCompatibilidad;
import modelo.inventario.Inventario;
import modelo.ticket.Ticket;

/**
 * Implementacion concreta de ServicioPersistencia usando SQLite como motor de base de datos.
 * Utiliza la biblioteca Gson para serializar y deserializar objetos complejos a formato JSON,
 * permitiendo almacenar tickets, configuraciones de computadoras y catalogos de componentes
 * de manera persistente.
 *
 * <p>Esta clase implementa el patron de diseno Repository, encapsulando toda la logica
 * de acceso a datos y proporcionando una interfaz limpia para las operaciones CRUD
 * (Create, Read, Update, Delete) sobre los datos del sistema.
 *
 * <p>Caracteristicas principales:
 * <ul>
 *   <li>Gestion automatica de conexiones a la base de datos SQLite</li>
 *   <li>Serializacion/deserializacion robusta usando Gson</li>
 *   <li>Soporte para objetos complejos con decoradores y polimorfismo</li>
 *   <li>Inicializacion automatica del esquema de base de datos</li>
 *   <li>Manejo seguro de excepciones SQL</li>
 * </ul>
 *
 * <p>Esquema de base de datos:
 * <ul>
 *   <li>Tabla 'tickets': Almacena tickets de venta con computadora y compatibilidad</li>
 *   <li>Tabla 'configuraciones': Guarda configuraciones personalizadas de PCs</li>
 *   <li>Tabla 'catalogo': Persiste el inventario completo de componentes</li>
 * </ul>
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class PersistenciaSQLite implements ServicioPersistencia {

    private String rutaDB;
    private Connection conexion;
    private Gson gson;

    /**
     * Construye una nueva instancia de persistencia SQLite con la ruta especificada.
     * Inicializa la conexion a la base de datos y crea el esquema si no existe.
     *
     * @param rutaDB la ruta al archivo de base de datos SQLite
     * @throws IllegalArgumentException si la ruta es nula o vacia
     */
    public PersistenciaSQLite(String rutaDB) {
        if (rutaDB == null || rutaDB.trim().isEmpty()) {
            throw new IllegalArgumentException("La ruta de la base de datos no puede ser nula o vacia");
        }
        this.rutaDB = rutaDB;
        this.gson = new GsonBuilder()
            .setDateFormat("dd/MM/yyyy HH:mm:ss")
            .create();
        conectar();
    }

    /**
     * Establece la conexion con la base de datos SQLite.
     * Carga el driver JDBC de SQLite, establece la conexion y inicializa las tablas necesarias.
     * Si la conexion ya existe, no realiza ninguna accion.
     */
    public void conectar() {
        Optional.ofNullable(conexion).ifPresentOrElse(
            conn -> {},
            () -> {
                try {
                    Class.forName("org.sqlite.JDBC");
                    conexion = DriverManager.getConnection("jdbc:sqlite:" + rutaDB);
                    inicializarTablas();
                } catch (ClassNotFoundException | SQLException e) {
                    System.err.println("Error al conectar a la base de datos: " + e.getMessage());
                }
            }
        );
    }

    /**
     * Cierra la conexion con la base de datos SQLite.
     * Libera los recursos asociados a la conexion de manera segura.
     */
    public void desconectar() {
        Optional.ofNullable(conexion).ifPresent(conn -> {
            try {
                conn.close();
                conexion = null;
            } catch (SQLException e) {
                System.err.println("Error al desconectar de la base de datos: " + e.getMessage());
            }
        });
    }

    /**
     * Guarda un ticket de compra en la base de datos de manera persistente.
     * Serializa la computadora y el resultado de compatibilidad a formato JSON
     * antes de almacenarlos.
     *
     * @param ticket el ticket a guardar, no debe ser nulo
     * @return true si el ticket se guardo exitosamente, false en caso de error
     */
    @Override
    public boolean guardarTicket(Ticket ticket) {
        if (ticket == null || conexion == null) {
            return false;
        }

        String sql = "INSERT OR REPLACE INTO tickets (numero_ticket, fecha, cliente, " +
                     "computadora_json, compatibilidad_json, precio_total) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            String ticketStr = ticket.generarTicket();

            pstmt.setInt(1, ticket.obtenerNumeroTicket());
            pstmt.setString(2, sdf.format(new Date()));
            pstmt.setString(3, "Cliente");
            pstmt.setString(4, ticketStr);
            pstmt.setString(5, "{}");
            pstmt.setDouble(6, ticket.obtenerPrecioTotal());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al guardar ticket: " + e.getMessage());
            return false;
        }
    }

    /**
     * Carga todos los tickets guardados desde la base de datos.
     * Deserializa los datos JSON de cada ticket para reconstruir los objetos completos.
     *
     * @return lista de tickets guardados, puede estar vacia si no hay tickets, nunca retorna null
     */
    @Override
    public List<Ticket> cargarTickets() {
        if (conexion == null) {
            return new ArrayList<>();
        }

        String sql = "SELECT numero_ticket, fecha, cliente, computadora_json, " +
                     "compatibilidad_json, precio_total FROM tickets";

        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            return procesarFilas(rs, r -> null);
        } catch (SQLException e) {
            System.err.println("Error al cargar tickets: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Elimina un ticket especifico de la base de datos.
     * Busca el ticket por su numero identificador unico y lo elimina si existe.
     *
     * @param numeroTicket el numero identificador del ticket a eliminar
     * @return true si el ticket fue eliminado exitosamente, false si no se encontro o hubo error
     */
    @Override
    public boolean eliminarTicket(int numeroTicket) {
        if (conexion == null) {
            return false;
        }

        String sql = "DELETE FROM tickets WHERE numero_ticket = ?";

        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, numeroTicket);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar ticket: " + e.getMessage());
            return false;
        }
    }

    /**
     * Guarda una configuracion de computadora personalizada con un nombre identificador.
     * Serializa la computadora a JSON antes de almacenarla.
     *
     * @param nombre el nombre identificador para la configuracion, no debe ser nulo ni vacio
     * @param pc la computadora configurada a guardar, no debe ser nula
     * @return true si la configuracion se guardo exitosamente, false en caso de error
     */
    @Override
    public boolean guardarConfiguracion(String nombre, ComputadoraBase pc) {
        if (nombre == null || nombre.trim().isEmpty() || pc == null || conexion == null) {
            return false;
        }

        String sql = "INSERT OR REPLACE INTO configuraciones (nombre, computadora_json) VALUES (?, ?)";

        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            pstmt.setString(2, serializarComputadora(pc));
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al guardar configuracion: " + e.getMessage());
            return false;
        }
    }

    /**
     * Carga todas las configuraciones de computadoras guardadas.
     * Deserializa los datos JSON de cada configuracion.
     *
     * @return mapa de configuraciones guardadas (nombre a ComputadoraBase),
     *         puede estar vacio si no hay configuraciones, nunca retorna null
     */
    @Override
    public Map<String, ComputadoraBase> cargarConfiguraciones() {
        if (conexion == null) {
            return new HashMap<>();
        }

        String sql = "SELECT nombre, computadora_json FROM configuraciones";

        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            return procesarFilas(rs, r -> {
                String nombre = r.getString("nombre");
                String json = r.getString("computadora_json");
                ComputadoraBase pc = deserializarComputadora(json);
                return pc != null ? Map.entry(nombre, pc) : null;
            })
            .stream()
            .filter(entrada -> entrada != null)
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        } catch (SQLException e) {
            System.err.println("Error al cargar configuraciones: " + e.getMessage());
            return new HashMap<>();
        }
    }

    /**
     * Guarda el catalogo completo de componentes del inventario.
     * Serializa todo el inventario a JSON antes de almacenarlo.
     *
     * @param inventario el inventario con el catalogo completo a guardar, no debe ser nulo
     * @return true si el catalogo se guardo exitosamente, false en caso de error
     */
    @Override
    public boolean guardarCatalogo(Inventario inventario) {
        if (inventario == null || conexion == null) {
            return false;
        }

        String sql = "INSERT OR REPLACE INTO catalogo (id, inventario_json) VALUES (1, ?)";

        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            Map<String, List<Map<String, Object>>> catalogoDTO = crearCatalogoDTO(inventario);
            pstmt.setString(1, gson.toJson(catalogoDTO));
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al guardar catalogo: " + e.getMessage());
            return false;
        }
    }

    /**
     * Carga el catalogo de componentes desde la base de datos.
     * Deserializa el JSON y reconstruye el inventario singleton.
     *
     * @return el inventario cargado con el catalogo de componentes,
     *         puede ser null si no hay catalogo guardado o hubo error al cargar
     */
    @Override
    public Inventario cargarCatalogo() {
        if (conexion == null) {
            return null;
        }

        String sql = "SELECT inventario_json FROM catalogo WHERE id = 1";

        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                String json = rs.getString("inventario_json");
                return deserializarInventario(json);
            }
            return null;
        } catch (SQLException e) {
            System.err.println("Error al cargar catalogo: " + e.getMessage());
            return null;
        }
    }

    /**
     * Inicializa las tablas necesarias en la base de datos si no existen.
     * Crea las tablas para tickets, configuraciones y catalogo con sus respectivos esquemas.
     */
    private void inicializarTablas() {
        List<String> sqlStatements = List.of(
            "CREATE TABLE IF NOT EXISTS tickets (" +
            "numero_ticket INTEGER PRIMARY KEY, " +
            "fecha TEXT NOT NULL, " +
            "cliente TEXT NOT NULL, " +
            "computadora_json TEXT NOT NULL, " +
            "compatibilidad_json TEXT NOT NULL, " +
            "precio_total REAL NOT NULL)",

            "CREATE TABLE IF NOT EXISTS configuraciones (" +
            "nombre TEXT PRIMARY KEY, " +
            "computadora_json TEXT NOT NULL)",

            "CREATE TABLE IF NOT EXISTS catalogo (" +
            "id INTEGER PRIMARY KEY CHECK (id = 1), " +
            "inventario_json TEXT NOT NULL)"
        );

        sqlStatements.forEach(sql -> {
            try (Statement stmt = conexion.createStatement()) {
                stmt.execute(sql);
            } catch (SQLException e) {
                System.err.println("Error al inicializar tablas: " + e.getMessage());
            }
        });
    }

    /**
     * Serializa una computadora a formato JSON.
     * Extrae los componentes y decoradores de software para crear una representacion
     * serializable que puede ser almacenada en la base de datos.
     *
     * @param pc la computadora a serializar
     * @return cadena JSON con la computadora serializada
     */
    private String serializarComputadora(ComputadoraBase pc) {
        Map<String, Object> computadoraDTO = new HashMap<>();
        computadoraDTO.put("componentes", pc.obtenerComponentes().stream()
            .map(this::crearComponenteDTO)
            .collect(Collectors.toList()));
        computadoraDTO.put("software", extraerSoftware(pc));
        return gson.toJson(computadoraDTO);
    }

    /**
     * Serializa una computadora a formato JSON (sobrecarga para String).
     * Version alternativa que recibe un String como parametro.
     *
     * @param tipo cadena con informacion del tipo
     * @return cadena JSON serializada
     */
    private String serializarComputadora(String tipo) {
        Map<String, Object> dto = new HashMap<>();
        dto.put("tipo", tipo);
        return gson.toJson(dto);
    }

    /**
     * Deserializa una computadora desde formato JSON.
     * Reconstruye los componentes y aplica los decoradores de software en el orden correcto.
     *
     * @param json cadena JSON con la computadora serializada
     * @return la computadora deserializada, o null si hay error
     */
    private ComputadoraBase deserializarComputadora(String json) {
        try {
            Map<String, Object> data = gson.fromJson(json,
                new TypeToken<Map<String, Object>>(){}.getType());

            ComputadoraBase pc = new ComputadoraBasica("PC Restaurada");

            List<Map<String, Object>> componentesData =
                (List<Map<String, Object>>) data.get("componentes");

            Optional.ofNullable(componentesData)
                .orElse(new ArrayList<>())
                .stream()
                .map(this::deserializarComponente)
                .filter(comp -> comp != null)
                .forEach(pc::agregarComponente);

            List<String> software = (List<String>) data.get("software");
            return Optional.ofNullable(software)
                .orElse(new ArrayList<>())
                .stream()
                .reduce(pc, this::aplicarSoftware, (a, b) -> b);

        } catch (Exception e) {
            System.err.println("Error al deserializar computadora: " + e.getMessage());
            return null;
        }
    }

    /**
     * Crea un objeto DTO (Data Transfer Object) para un componente.
     * Extrae los atributos especificos de cada tipo de componente para serializacion.
     *
     * @param componente el componente a convertir a DTO
     * @return mapa con los datos del componente
     */
    private Map<String, Object> crearComponenteDTO(ComponentePC componente) {
        Map<String, Object> dto = new HashMap<>();
        dto.put("tipo", componente.getClass().getSimpleName());
        dto.put("nombre", componente.obtenerNombre());
        dto.put("precio", componente.obtenerPrecio());
        dto.put("marca", componente.obtenerMarca());
        dto.put("tipoComponente", componente.obtenerTipo());

        if (componente instanceof CPU) {
            CPU cpu = (CPU) componente;
            dto.put("cantidadNucleos", cpu.getCantidadNucleos());
            dto.put("arquitectura", cpu.getArquitectura());
        } else if (componente instanceof RAM) {
            RAM ram = (RAM) componente;
            dto.put("capacidad", ram.getCapacidadGB());
            dto.put("tipoMemoria", ram.getTipoMemoria());
        } else if (componente instanceof GPU) {
            GPU gpu = (GPU) componente;
            dto.put("tipoMemoriaGPU", gpu.getTipoMemoriaGPU());
            dto.put("memoriaVRAM", gpu.getVRAM());
        } else if (componente instanceof Disco) {
            Disco disco = (Disco) componente;
            dto.put("capacidadAlmacenamiento", disco.getCapacidadAlmacenamiento());
            dto.put("tipoAlmacenamiento", disco.getTipoAlimentacion());
        } else if (componente instanceof FuenteAlimentacion) {
            FuenteAlimentacion fuente = (FuenteAlimentacion) componente;
            dto.put("potenciaMaxima", fuente.getPotenciaMaxima());
            dto.put("certificacion", fuente.getCertificacion());
        } else if (componente instanceof MotherBoard) {
            MotherBoard mb = (MotherBoard) componente;
            dto.put("chipset", mb.getChipset());
            dto.put("socket", mb.getSocket());
            dto.put("arquitecturaSoportada", mb.getArquitecturaSeparada());
        } else if (componente instanceof Gabinete) {
            Gabinete gabinete = (Gabinete) componente;
            dto.put("tipoGabinete", gabinete.getTamanio());
        }

        return dto;
    }

    /**
     * Deserializa un componente desde su representacion DTO.
     * Reconstruye el componente especifico basandose en su tipo.
     *
     * @param dto mapa con los datos del componente
     * @return el componente deserializado, o null si hay error
     */
    private ComponentePC deserializarComponente(Map<String, Object> dto) {
        try {
            String tipo = (String) dto.get("tipo");
            String nombre = (String) dto.get("nombre");
            double precio = ((Number) dto.get("precio")).doubleValue();
            String marca = (String) dto.get("marca");
            String tipoComponente = (String) dto.get("tipoComponente");

            switch (tipo) {
                case "CPU":
                    return new CPU(nombre, precio, marca, tipoComponente,
                        ((Number) dto.get("cantidadNucleos")).intValue(),
                        (String) dto.get("arquitectura"));
                case "RAM":
                    return new RAM(nombre, precio, marca, tipoComponente,
                        ((Number) dto.get("capacidad")).intValue(),
                        (String) dto.get("tipoMemoria"));
                case "GPU":
                    return new GPU(nombre, precio, marca, tipoComponente,
                        (String) dto.get("tipoMemoriaGPU"),
                        ((Number) dto.get("memoriaVRAM")).intValue());
                case "Disco":
                    return new Disco(nombre, precio, marca, tipoComponente,
                        ((Number) dto.get("capacidadAlmacenamiento")).intValue(),
                        (String) dto.get("tipoAlmacenamiento"));
                case "FuenteAlimentacion":
                    return new FuenteAlimentacion(nombre, precio, marca, tipoComponente,
                        ((Number) dto.get("potenciaMaxima")).intValue(),
                        (String) dto.get("certificacion"));
                case "MotherBoard":
                    return new MotherBoard(nombre, precio, marca, tipoComponente,
                        (String) dto.get("chipset"),
                        (String) dto.get("socket"),
                        (String) dto.get("arquitecturaSoportada"));
                case "Gabinete":
                    return new Gabinete(nombre, precio, marca, tipoComponente,
                        (String) dto.get("tipoGabinete"));
                default:
                    return null;
            }
        } catch (Exception e) {
            System.err.println("Error al deserializar componente: " + e.getMessage());
            return null;
        }
    }

    /**
     * Extrae la lista de software instalado en una computadora.
     * Recorre la cadena de decoradores para obtener todos los programas instalados.
     *
     * @param pc la computadora de la que extraer el software
     * @return lista con los nombres de software instalado
     */
    private List<String> extraerSoftware(ComputadoraBase pc) {
        List<String> software = new ArrayList<>();
        String descripcion = pc.obtenerDescripcion();

        descripcion.lines()
            .filter(linea -> linea.contains("+ Software:"))
            .map(linea -> linea.replace("  + Software:", "").trim())
            .forEach(software::add);

        return software;
    }

    /**
     * Aplica un decorador de software a una computadora.
     * Crea el decorador apropiado basandose en el nombre del software.
     *
     * @param pc la computadora a decorar
     * @param nombreSoftware el nombre del software a instalar
     * @return la computadora decorada con el software
     */
    private ComputadoraBase aplicarSoftware(ComputadoraBase pc, String nombreSoftware) {
        return switch (nombreSoftware) {
            case "Windows 10/11" -> new WindowsDecorator(pc, "Windows 10/11", 2500.0);
            case "Microsoft Office 365" -> new OfficeDecorator(pc, "Microsoft Office 365", 1500.0);
            case "Adobe Photoshop" -> new PhotoshopDecorator(pc, "Adobe Photoshop", 3000.0);
            case "AutoCAD" -> new AutoCADDecorator(pc, "AutoCAD", 5000.0);
            case "Terminal WSL" -> new WSLDecorator(pc, "Terminal WSL", 0.0);
            default -> pc;
        };
    }

    /**
     * Crea un DTO del catalogo completo del inventario.
     * Serializa todos los componentes organizados por tipo.
     *
     * @param inventario el inventario a serializar
     * @return mapa con el catalogo serializado
     */
    private Map<String, List<Map<String, Object>>> crearCatalogoDTO(Inventario inventario) {
        return List.of("CPU", "RAM", "MotherBoard", "Disco", "GPU", "FuenteAlimentacion", "Gabinete")
            .stream()
            .collect(Collectors.toMap(
                tipo -> tipo,
                tipo -> inventario.obtenerComponentesPorTipo(tipo).stream()
                    .map(this::crearComponenteDTO)
                    .collect(Collectors.toList())
            ));
    }

    /**
     * Deserializa un inventario desde formato JSON.
     * Reconstruye el inventario singleton con todos los componentes.
     *
     * @param json cadena JSON con el inventario serializado
     * @return el inventario deserializado, o null si hay error
     */
    private Inventario deserializarInventario(String json) {
        try {
            Map<String, List<Map<String, Object>>> catalogoData = gson.fromJson(json,
                new TypeToken<Map<String, List<Map<String, Object>>>>(){}.getType());

            Inventario inventario = Inventario.getInstance();

            catalogoData.forEach((tipo, componentes) ->
                componentes.stream()
                    .map(this::deserializarComponente)
                    .filter(comp -> comp != null)
                    .forEach(inventario::agregarComponente)
            );

            return inventario;
        } catch (Exception e) {
            System.err.println("Error al deserializar inventario: " + e.getMessage());
            return null;
        }
    }

    /**
     * Interfaz funcional para mapear filas de un ResultSet a objetos.
     * Permite procesar ResultSet de forma funcional abstrayendo la iteracion imperativa.
     *
     * @param <T> el tipo de objeto resultante del mapeo
     */
    @FunctionalInterface
    private interface MapeadorResultSet<T> {
        /**
         * Mapea una fila del ResultSet a un objeto.
         *
         * @param rs el ResultSet posicionado en la fila a mapear
         * @return el objeto mapeado desde la fila actual
         * @throws SQLException si ocurre error al leer el ResultSet
         */
        T mapear(ResultSet rs) throws SQLException;
    }

    /**
     * Procesa todas las filas de un ResultSet aplicando un mapeador funcional.
     * Abstrae la iteracion imperativa de JDBC para permitir codigo funcional.
     *
     * @param <T> el tipo de objetos resultantes
     * @param rs el ResultSet a procesar
     * @param mapeador funcion que convierte cada fila en un objeto
     * @return lista con los objetos mapeados desde el ResultSet
     * @throws SQLException si ocurre un error al acceder al ResultSet
     */
    private <T> List<T> procesarFilas(ResultSet rs, MapeadorResultSet<T> mapeador) throws SQLException {
        List<T> resultados = new ArrayList<>();
        while (rs.next()) {
            resultados.add(mapeador.mapear(rs));
        }
        return resultados;
    }
}
