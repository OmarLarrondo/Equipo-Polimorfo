package modelo.ticket;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import modelo.componente.ComponentePC;
import modelo.computadora.ComputadoraBase;
import modelo.estrategia.ResultadoCompatibilidad;

/**
 * Clase que representa un ticket de compra de una computadora en el sistema MonosChinos MX.
 * Encapsula toda la informacion de una compra incluyendo la computadora configurada,
 * el cliente, la fecha de compra y el resultado de verificacion de compatibilidad.
 *
 * <p>Esta clase genera un ticket detallado en formato de texto que incluye:
 * <ul>
 *   <li>Encabezado con informacion del ticket y cliente</li>
 *   <li>Lista completa de componentes de hardware con precios</li>
 *   <li>Software adicional instalado (si aplica)</li>
 *   <li>Informacion de compatibilidad y advertencias</li>
 *   <li>Precio total de la compra</li>
 * </ul>
 *
 * <p>El ticket es generado mediante el metodo {@link #generarTicket()} que orquesta
 * la llamada a metodos privados para construir cada seccion del ticket.
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class Ticket {

    /**Numero unico identificador del ticket.*/
    private int numeroTicket;

    /**La computadora configurada asociada a este ticket.*/
    private ComputadoraBase computadora;

    /**Fecha y hora de generacion del ticket.*/
    private Date fecha;

    /**Nombre del cliente que realiza la compra.*/
    private String cliente;

    /**Resultado de la verificacion de compatibilidad de componentes.*/
    private ResultadoCompatibilidad compatibilidad;

    /**
     * Construye un nuevo ticket de compra con la informacion especificada.
     * La fecha se inicializa automaticamente al momento de creacion del ticket.
     *
     * @param numero el numero unico del ticket
     * @param computadora la computadora configurada que se esta vendiendo
     * @param cliente el nombre del cliente
     * @param compatibilidad el resultado de verificacion de compatibilidad
     * @throws IllegalArgumentException si la computadora, cliente o compatibilidad son nulos
     */
    public Ticket(int numero, ComputadoraBase computadora, String cliente,
                  ResultadoCompatibilidad compatibilidad) {
        if (computadora == null) {
            throw new IllegalArgumentException("La computadora no puede ser nula");
        }
        if (cliente == null || cliente.trim().isEmpty()) {
            throw new IllegalArgumentException("El cliente no puede ser nulo o vacio");
        }
        if (compatibilidad == null) {
            throw new IllegalArgumentException("La compatibilidad no puede ser nula");
        }
        this.numeroTicket = numero;
        this.computadora = computadora;
        this.cliente = cliente;
        this.compatibilidad = compatibilidad;
        this.fecha = new Date();
    }

    /**
     * Genera el ticket completo en formato de texto.
     * Orquesta la construccion de todas las secciones del ticket
     * concatenando los resultados de los metodos helper.
     *
     * @return una cadena con el ticket completo formateado
     */
    public String generarTicket() {
        return List.of(
            generarEncabezado(),
            generarDetalleComponentes(),
            generarDetalleSoftware(),
            generarDetalleCompatibilidad(),
            generarPie()
        ).stream()
        .filter(seccion -> seccion != null && !seccion.isEmpty())
        .collect(Collectors.joining("\n\n"));
    }

    /**
     * Obtiene el precio total de la computadora incluyendo hardware y software.
     * Delega el calculo a la computadora que maneja tanto componentes base
     * como decoradores de software.
     *
     * @return el precio total de la computadora
     */
    public double obtenerPrecioTotal() {
        return computadora.obtenerPrecioTotal();
    }

    /**
     * Obtiene el numero identificador del ticket.
     *
     * @return el numero del ticket
     */
    public int obtenerNumeroTicket() {
        return numeroTicket;
    }

    /**
     * Genera el encabezado del ticket con logo, numero, fecha y cliente.
     * Construye las lineas del encabezado con la informacion basica del ticket.
     *
     * @return cadena con el encabezado formateado
     */
    private String generarEncabezado() {
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String separador = IntStream.range(0, 70)
            .mapToObj(i -> "=")
            .collect(Collectors.joining());

        return List.of(
            separador,
            "                     MONOS CHINOS MX",
            "            Sistema de Ensamblaje de Computadoras",
            separador,
            String.format("  Ticket No: %d", numeroTicket),
            String.format("  Fecha: %s", formatoFecha.format(fecha)),
            String.format("  Cliente: %s", cliente),
            separador
        ).stream().collect(Collectors.joining("\n"));
    }

    /**
     * Genera la seccion de detalle de componentes de hardware.
     * Itera sobre los componentes para formatear cada uno
     * con su informacion completa y precio individual.
     *
     * @return cadena con la lista de componentes formateados
     */
    private String generarDetalleComponentes() {
        String encabezado = "COMPONENTES DE HARDWARE:";

        String listaComponentes = computadora.obtenerComponentes().stream()
            .map(componente -> String.format("  - %s - Precio: $%.2f",
                componente.mostrarDetalles(),
                componente.obtenerPrecio()))
            .collect(Collectors.joining("\n"));

        return List.of(encabezado, listaComponentes)
            .stream()
            .collect(Collectors.joining("\n"));
    }

    /**
     * Genera la seccion de software instalado en la computadora.
     * Extrae las lineas que contienen "+ Software:" de la descripcion
     * de la computadora.
     *
     * @return cadena con la lista de software instalado, o cadena vacia si no hay software
     */
    private String generarDetalleSoftware() {
        String descripcion = computadora.obtenerDescripcion();

        List<String> lineasSoftware = descripcion.lines()
            .filter(linea -> linea.contains("+ Software:"))
            .collect(Collectors.toList());

        return lineasSoftware.isEmpty()
            ? ""
            : List.of("SOFTWARE ADICIONAL:", lineasSoftware.stream()
                .collect(Collectors.joining("\n")))
              .stream()
              .collect(Collectors.joining("\n"));
    }

    /**
     * Genera la seccion de informacion de compatibilidad.
     * Muestra el estado de compatibilidad, advertencias y componentes adaptados
     * procesando la informacion del resultado de compatibilidad.
     *
     * @return cadena con la informacion de compatibilidad formateada
     */
    private String generarDetalleCompatibilidad() {
        String encabezado = "INFORMACION DE COMPATIBILIDAD:";
        String estado = String.format("  Estado: %s",
            compatibilidad.isCompatible() ? "COMPATIBLE" : "INCOMPATIBLE");

        String advertencias = compatibilidad.getAdvertencias().isEmpty()
            ? "  Advertencias: Ninguna"
            : List.of(
                "  Advertencias:",
                compatibilidad.getAdvertencias().stream()
                    .map(adv -> "    - " + adv)
                    .collect(Collectors.joining("\n"))
              ).stream().collect(Collectors.joining("\n"));

        String adaptados = compatibilidad.getComponentesAdaptados().isEmpty()
            ? "  Componentes Adaptados: Ninguno"
            : List.of(
                "  Componentes Adaptados:",
                compatibilidad.getComponentesAdaptados().stream()
                    .map(comp -> String.format("    - %s", comp.mostrarDetalles()))
                    .collect(Collectors.joining("\n"))
              ).stream().collect(Collectors.joining("\n"));

        return List.of(encabezado, estado, advertencias, adaptados)
            .stream()
            .collect(Collectors.joining("\n"));
    }

    /**
     * Genera el pie del ticket con precio total y mensaje de agradecimiento.
     * Construye las lineas finales del ticket con el total y mensaje al cliente.
     *
     * @return cadena con el pie del ticket formateado
     */
    private String generarPie() {
        String separador = IntStream.range(0, 70)
            .mapToObj(i -> "=")
            .collect(Collectors.joining());

        return List.of(
            separador,
            String.format("  PRECIO TOTAL: $%.2f", obtenerPrecioTotal()),
            separador,
            "          Gracias por su compra en MonosChinos MX",
            "       Su PC sera ensamblada y enviada a la brevedad",
            separador
        ).stream().collect(Collectors.joining("\n"));
    }
}
