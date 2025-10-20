package modelo.builder;

import modelo.computadora.ComputadoraBase;
import modelo.computadora.ComputadoraBasica;
import modelo.inventario.Inventario;
import modelo.componente.ComponentePC;

import java.util.List;
import java.util.Map;

/**
 * Builder concreto para construir computadoras prearmadas.
 * Este builder construye computadoras basandose en configuraciones
 * predefinidas segun el tipo de PC seleccionado (Gama Baja, Media, Alta).
 */
public class PCPrearmadoBuilder implements PCBuilder {

    private ComputadoraBase computadora;
    private String tipoPC;

    /**
     * Construye un nuevo PCPrearmadoBuilder con el tipo de PC especificado.
     *
     * @param tipoPC El tipo de configuracion predefinida (ej: "Gama Baja", "Gama Media", "Gama Alta")
     */
    public PCPrearmadoBuilder(String tipoPC) {
        this.tipoPC = tipoPC;
        this.computadora = new ComputadoraBasica("PC Prearmada - " + tipoPC);
    }

    /**
     * Reinicia el builder creando una nueva instancia de computadora.
     */
    @Override
    public void reiniciar() {
        this.computadora = new ComputadoraBasica("PC Prearmada - " + tipoPC);
    }

    /**
     * Construye y agrega el procesador segun la configuracion predefinida.
     */
    @Override
    public void construirCPU() {
        construirConfiguracionGama("CPU");
    }

    /**
     * Construye y agrega la memoria RAM segun la configuracion predefinida.
     */
    @Override
    public void construirRAM() {
        construirConfiguracionGama("RAM");
    }

    /**
     * Construye y agrega la placa madre segun la configuracion predefinida.
     */
    @Override
    public void construirMotherBoard() {
        construirConfiguracionGama("MotherBoard");
    }

    /**
     * Construye y agrega la tarjeta grafica segun la configuracion predefinida.
     */
    @Override
    public void construirGPU() {
        construirConfiguracionGama("GPU");
    }

    /**
     * Construye y agrega el almacenamiento segun la configuracion predefinida.
     */
    @Override
    public void construirAlmacenamiento() {
        construirConfiguracionGama("Disco");
    }

    /**
     * Construye y agrega la fuente de alimentacion segun la configuracion predefinida.
     */
    @Override
    public void construirFuente() {
        construirConfiguracionGama("FuenteAlimentacion");
    }

    /**
     * Construye y agrega el gabinete segun la configuracion predefinida.
     */
    @Override
    public void construirGabinete() {
        construirConfiguracionGama("Gabinete");
    }

    /**
     * Obtiene la computadora prearmada construida.
     *
     * @return La computadora prearmada construida
     */
    @Override
    public ComputadoraBase obtenerResultado() {
        return this.computadora;
    }

    /**
     * Construye un componente especifico basado en la configuracion de gama.
     * Selecciona el componente apropiado del inventario segun el tipo de PC.
     *
     * @param tipoComponente El tipo de componente a construir
     */
    private void construirConfiguracionGama(String tipoComponente) {
        Map<String, List<ComponentePC>> configuraciones = Inventario.getInstance()
            .obtenerConfiguracionesPrearmadas();

        if (configuraciones != null && configuraciones.containsKey(tipoPC)) {
            List<ComponentePC> componentes = configuraciones.get(tipoPC);

            ComponentePC componenteEncontrado = componentes.stream()
                .filter(c -> c.obtenerTipo().equals(tipoComponente))
                .findFirst()
                .orElse(null);

            if (componenteEncontrado != null) {
                computadora.agregarComponente(componenteEncontrado);
            } else {
                List<ComponentePC> componentesPorTipo = Inventario.getInstance()
                    .obtenerComponentesPorTipo(tipoComponente);

                if (componentesPorTipo != null && !componentesPorTipo.isEmpty()) {
                    int indice = seleccionarIndicePorGama(componentesPorTipo.size());
                    computadora.agregarComponente(componentesPorTipo.get(indice));
                }
            }
        } else {
            List<ComponentePC> componentesPorTipo = Inventario.getInstance()
                .obtenerComponentesPorTipo(tipoComponente);

            if (componentesPorTipo != null && !componentesPorTipo.isEmpty()) {
                int indice = seleccionarIndicePorGama(componentesPorTipo.size());
                computadora.agregarComponente(componentesPorTipo.get(indice));
            }
        }
    }

    /**
     * Selecciona el indice del componente segun la gama del PC.
     *
     * @param cantidadComponentes Cantidad total de componentes disponibles
     * @return El indice del componente a seleccionar
     */
    private int seleccionarIndicePorGama(int cantidadComponentes) {
        if (cantidadComponentes == 0) {
            return 0;
        }

        switch (tipoPC.toLowerCase()) {
            case "gama baja":
                return 0;
            case "gama media":
                return Math.min(cantidadComponentes / 2, cantidadComponentes - 1);
            case "gama alta":
                return cantidadComponentes - 1;
            default:
                return 0;
        }
    }
}
