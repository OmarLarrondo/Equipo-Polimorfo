package main.productos;

import main.productos.helados.ComponenteHelado;
import main.productos.helados.DecoradorIngrediente;
import main.enums.SaborHelado;
import main.productos.helados.HeladoSimple;
import java.util.Scanner;
import java.time.LocalDateTime;

/**
 * Clase que representa un helado como producto en la pizzería "El Pequeño Cesarín".
 * 
 * <p>Esta clase extiende de Producto e implementa el patrón Decorator para permitir
 * la adición dinámica de ingredientes extra al helado base. El helado se compone de
 * un ComponenteHelado que puede ser decorado con múltiples ingredientes.</p>
 * 
 * <p>El helado tiene un proceso de preparación interactivo donde primero se selecciona el sabor y luego se pueden agregar ingredientes
 * extras bajo demanda del cliente.</p>
 * 
 * <p>Los helados pueden tener máximo 3 unidades de cada tipo de ingrediente, y una vez
 * agregado un ingrediente, no puede ser removido.</p>
 * 
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class Helado extends Producto {
    
    /**
     * Componente del helado que puede ser simple o decorado con ingredientes
     */
    private ComponenteHelado componenteHelado;

    /**
     * Constructor que crea un helado basado en un ComponenteHelado.
     * 
     * <p>El constructor genera automáticamente los datos necesarios para el producto
     * basándose en el componente del helado. El precio y descripción se delegan
     * al ComponenteHelado para permitir el cálculo dinámico según los decoradores.</p>
     * 
     * @param componente el ComponenteHelado base (HeladoSimple o decorado)
     * @throws IllegalArgumentException si el componente es null
     */
    public Helado(ComponenteHelado componente) {
        super(
            "helado_" + LocalDateTime.now().toString().replaceAll("[^0-9]", "").substring(0, 10),
            "Helado",
            componente != null ? componente.getDescripcion() : "Helado",
            componente != null ? componente.getPrecio() : 0.0
        );
        
        if (componente == null) {
            throw new IllegalArgumentException("El componente del helado no puede ser null");
        }
        
        this.componenteHelado = componente;
    }

    /**
     * Implementa el proceso de preparación interactiva del helado.
     * 
     * <p>Según los requerimientos, el robot primero debe esperar la indicación
     * del sabor del helado y posteriormente ir agregando cada ingrediente extra
     * como el cliente se lo indique. El proceso es interactivo y permite al
     * cliente personalizar su helado paso a paso.</p>
     * 
     * <p>Durante la preparación se muestran los pasos que está siguiendo el robot,
     * incluyendo la selección del sabor base y la adición de cada ingrediente.</p>
     */
    @Override
    public void preparar() {
        System.out.println("Robot: Iniciando preparación del helado...");
        System.out.println("Robot: Esperando indicación del sabor del helado...");
        
        if (componenteHelado instanceof HeladoSimple) {
            System.out.println("Robot: Preparando " + componenteHelado.getDescripcion());
        } else {
            System.out.println("Robot: Preparando helado con ingredientes personalizados");
        }
        
        System.out.println("Robot: Sirviendo el helado en el vaso...");
        System.out.println("Robot: " + componenteHelado.getDescripcion() + " listo!");
        
        if (!(componenteHelado instanceof HeladoSimple)) {
            System.out.println("Robot: Ingredientes agregados según las indicaciones del cliente");
        }
        
        System.out.println("Robot: Helado preparado y listo para entregar");
        System.out.println("Precio total: $" + String.format("%.2f", componenteHelado.getPrecio()));
    }

    /**
     * Agrega un ingrediente extra al helado envolviendo el componente actual
     * con un nuevo decorador.
     * 
     * <p>Este método implementa el patrón Decorator permitiendo agregar dinámicamente
     * ingredientes al helado. Una vez agregado un ingrediente, no puede ser removido.</p>
     * 
     * <p>El método actualiza tanto el componenteHelado interno como los atributos
     * heredados de Producto para mantener la consistencia de datos.</p>
     * 
     * @param ingrediente el DecoradorIngrediente que se agregará al helado
     * @throws IllegalArgumentException si el ingrediente es null
     */
    public void agregarIngrediente(DecoradorIngrediente ingrediente) {
        if (ingrediente == null) {
            throw new IllegalArgumentException("El ingrediente no puede ser null");
        }
        
        this.componenteHelado = ingrediente;
        
        this.descripcion = componenteHelado.getDescripcion();
        this.precio = componenteHelado.getPrecio();
        
        System.out.println("Robot: Ingrediente agregado - Nueva descripción: " + getDescripcionCompleta());
    }

    /**
     * Obtiene la descripción completa del helado incluyendo todos los ingredientes.
     * 
     * <p>Este método delega al ComponenteHelado la generación de la descripción,
     * lo que permite que los decoradores construyan automáticamente la descripción
     * completa con todos los ingredientes agregados.</p>
     * 
     * @return String con la descripción completa del helado en el formato:
     *         "Helado de [sabor]" o "Helado de [sabor], [ingrediente1], [ingrediente2], ..."
     */
    public String getDescripcionCompleta() {
        return componenteHelado.getDescripcion();
    }

    /**
     * Calcula y obtiene el precio total del helado incluyendo todos los ingredientes.
     * 
     * <p>Este método delega al ComponenteHelado el cálculo del precio total,
     * permitiendo que los decoradores sumen automáticamente el costo de todos
     * los ingredientes agregados al precio base del helado.</p>
     * 
     * @return double con el precio total del helado incluyendo ingredientes extra
     */
    public double getPrecioTotal() {
        return componenteHelado.getPrecio();
    }

    /**
     * Obtiene el componente helado interno (para testing o uso avanzado).
     * 
     * @return ComponenteHelado el componente interno del helado
     */
    public ComponenteHelado getComponenteHelado() {
        return componenteHelado;
    }
}
