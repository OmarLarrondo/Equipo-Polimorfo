package main.PaquetesHerramientas;

import main.herramientas.Herramienta;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Clase abstracta que representa un paquete de herramientas ninja.
 * Implementa funcionalidad base para gestionar herramientas.
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public abstract class PaquetesHerramientas {

    /** Lista de herramientas contenidas en el paquete */
    protected List<Herramienta> herramientas;

    /** Peso total calculado del paquete */
    protected double pesoTotal;

    /**
     * Constructor por defecto que inicializa la lista de herramientas.
     */
    protected PaquetesHerramientas() {
        this.herramientas = new ArrayList<>();
        this.pesoTotal = 0.0;
    }

    /**
     * Constructor que inicializa el paquete con una lista de herramientas.
     *
     * @param herramientas Lista de herramientas iniciales
     */
    protected PaquetesHerramientas(List<Herramienta> herramientas) {
        this.herramientas = Optional.ofNullable(herramientas)
            .map(ArrayList::new)
            .orElseGet(ArrayList::new);
        this.pesoTotal = calcularPesoTotal();
    }

    /**
     * Agrega una herramienta al paquete si no es nula.
     * Actualiza automáticamente el peso total.
     *
     * @param herramienta Herramienta a agregar
     */
    public void agregarHerramienta(Herramienta herramienta) {
        Optional.ofNullable(herramienta)
            .ifPresent(h -> {
                herramientas.add(h);
                this.pesoTotal = calcularPesoTotal();
            });
    }

    /**
     * Calcula el peso total del paquete.
     *
     * @return Peso total del paquete
     */
    public double calcularPesoTotal() {
        return herramientas.stream()
            .mapToDouble(Herramienta::getPeso)
            .sum();
    }

    /**
     * Obtiene la lista de herramientas como copia defensiva.
     *
     * @return Lista inmutable de herramientas
     */
    public List<Herramienta> getHerramientas() {
        return new ArrayList<>(herramientas);
    }

    /**
     * Obtiene el peso total del paquete.
     *
     * @return Peso total actual
     */
    public double getPesoTotal() {
        return pesoTotal;
    }

    /**
     * Método abstracto que debe ser implementado por las clases concretas
     * para proporcionar una descripción específica del paquete.
     *
     * @return Descripción del paquete
     */
    public abstract String getDescripcion();

    /**
     * Método auxiliar funcional para crear múltiples herramientas del mismo tipo.
     *
     * @param herramientaSupplier Supplier que crea herramientas
     * @param cantidad Cantidad de herramientas a crear
     */
    protected void agregarHerramientas(Supplier<Herramienta> herramientaSupplier, int cantidad) {
        if (cantidad > 0) {
            java.util.stream.Stream.generate(herramientaSupplier)
                .limit(cantidad)
                .forEach(this::agregarHerramienta);
        }
    }
}
