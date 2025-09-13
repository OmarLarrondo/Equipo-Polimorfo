package sistema;

import java.util.List;
import java.time.LocalDateTime;

/**
 * Clase para representar el precio de los productos y el precio final.
 * Contiene los prodcutos, el monto total y la fecha/hora en que se genero.
 */
public class Ticket {
    /** Lista de los productos elegidos por el cliente */
    private List<String> items;

    /** Monto total del pedido */
    private double total;

    /** Fecha y hora en la que se generó el ticket. */
    private LocalDateTime fechaHora;

    /**
     * Crea un nuevo ticket con el pedido y el total especificados.
     * @param items lista de los artículos incluidos en el ticket
     * @param total monto total de la compra
     */
    public Ticket(List<String> items, double total) {
        this.items = items;
        this.total = total;
        this.fechaHora = LocalDateTime.now();
    }

    /**
     * Imprime el ticket 
     * Debe mostrar los precios de los productos y el total y la fecha/hora.
     */
    public void imprimir() {
    System.out.println("========================================");
    
    System.out.println("=== EL Pequeño Cesarin ===");
    System.out.println("=== Investigación Científica, C.U., Coyoacán, 04510 Ciudad de México, CDMX ===");
    System.out.println("Fecha/Hora: " + fechaHora);
    System.out.println("Folio: "+ (int)(Math.random()*10000));

    System.out.println("========================================");
    for (String item : items) {
        System.out.println(item);
    }
    System.out.println("-----------------------");
    System.out.println("Total: $" + total);
    System.out.println("=======================");
    System.out.println("--- Gracias por su compra! ---");

    //Observacion: el ticket solo imprime 

}


    /**
     * Obtiene la lista de artículos del ticket.
     * @return lista de artículos incluidos en el ticket
     */
    public List<String> getItems() {
        return items;
    }

    /**
     * Obtiene el monto total de la compra.
     * @return total de la compra
     */
    public double getTotal() {
        return total;
    }

    /**
     * Obtiene la fecha y hora de emisión del ticket.
     * @return fecha y hora en que se creó el ticket
     */
    public LocalDateTime getFechaHora() {
        return fechaHora;
    }
}
