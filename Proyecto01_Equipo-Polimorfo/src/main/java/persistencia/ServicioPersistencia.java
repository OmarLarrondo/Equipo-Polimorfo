package persistencia;

import java.util.List;
import java.util.Map;

import modelo.computadora.ComputadoraBase;
import modelo.inventario.Inventario;
import modelo.ticket.Ticket;

/**
 * Interfaz que define el contrato para servicios de persistencia de datos del sistema.
 * Proporciona operaciones para almacenar y recuperar informacion de tickets, configuraciones
 * de computadoras y catalogos de componentes de manera persistente.
 *
 * <p>Esta interfaz abstrae el mecanismo de persistencia subyacente, permitiendo diferentes
 * implementaciones (SQLite, archivos CSV, bases de datos relacionales, etc.) sin afectar
 * la logica de negocio. La implementacion concreta {@link PersistenciaSQLite} utiliza
 * una base de datos SQLite para almacenar los datos.
 *
 * <p>Operaciones soportadas:
 * <ul>
 *   <li>Gestion de tickets (guardar, cargar, eliminar)</li>
 *   <li>Gestion de configuraciones de PCs personalizadas</li>
 *   <li>Persistencia del catalogo de componentes</li>
 * </ul>
 *
 * <p>Esta interfaz facilita el punto extra de persistencia de datos solicitado en el proyecto,
 * permitiendo que las ventas y configuraciones no se pierdan al cerrar la aplicacion.
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public interface ServicioPersistencia {

    /**
     * Guarda un ticket de compra de manera persistente.
     * Almacena toda la informacion del ticket incluyendo numero, fecha, cliente,
     * computadora configurada y resultado de compatibilidad.
     *
     * @param ticket el ticket a guardar, no debe ser nulo
     * @return true si el ticket se guardo exitosamente, false en caso de error
     */
    boolean guardarTicket(Ticket ticket);

    /**
     * Carga todos los tickets guardados desde el almacenamiento persistente.
     * Recupera el historial completo de ventas realizadas en el sistema.
     *
     * @return lista de tickets guardados, puede estar vacia si no hay tickets,
     *         nunca retorna null
     */
    List<Ticket> cargarTickets();

    /**
     * Elimina un ticket especifico del almacenamiento persistente.
     * Busca el ticket por su numero identificador unico y lo elimina si existe.
     *
     * @param numeroTicket el numero identificador del ticket a eliminar
     * @return true si el ticket fue eliminado exitosamente, false si no se encontro
     *         o hubo error al eliminar
     */
    boolean eliminarTicket(int numeroTicket);

    /**
     * Guarda una configuracion de computadora personalizada con un nombre identificador.
     * Permite al usuario guardar configuraciones personalizadas para reutilizarlas
     * posteriormente sin tener que seleccionar componentes nuevamente.
     *
     * @param nombre el nombre identificador para la configuracion, no debe ser nulo ni vacio
     * @param pc la computadora configurada a guardar, no debe ser nula
     * @return true si la configuracion se guardo exitosamente, false en caso de error
     */
    boolean guardarConfiguracion(String nombre, ComputadoraBase pc);

    /**
     * Carga todas las configuraciones de computadoras guardadas.
     * Recupera un mapa donde las claves son los nombres de las configuraciones
     * y los valores son las computadoras configuradas.
     *
     * @return mapa de configuraciones guardadas (nombre -> ComputadoraBase),
     *         puede estar vacio si no hay configuraciones, nunca retorna null
     */
    Map<String, ComputadoraBase> cargarConfiguraciones();

    /**
     * Guarda el catalogo completo de componentes del inventario.
     * Permite persistir todos los componentes disponibles en el sistema para
     * que puedan ser restaurados en futuras ejecuciones.
     *
     * @param inventario el inventario con el catalogo completo a guardar, no debe ser nulo
     * @return true si el catalogo se guardo exitosamente, false en caso de error
     */
    boolean guardarCatalogo(Inventario inventario);

    /**
     * Carga el catalogo de componentes desde el almacenamiento persistente.
     * Recupera el inventario completo con todos los componentes disponibles
     * que fueron guardados previamente.
     *
     * @return el inventario cargado con el catalogo de componentes,
     *         puede ser null si no hay catalogo guardado o hubo error al cargar
     */
    Inventario cargarCatalogo();
}
