package modelo.estrategia;

import java.util.List;
import java.util.ArrayList;
import modelo.componente.CPU;
import modelo.componente.ComponentePC;
import modelo.componente.CPUAMDAdapter;
import modelo.componente.FuenteAlimentacion;
import modelo.componente.MotherBoard;
import modelo.componente.GPU;

/**
 * Estrategia de compatibilidad para sistemas mixtos con componentes Intel y AMD.
 * Detecta cuando se mezclan componentes de diferentes fabricantes y aplica adaptadores
 * cuando es necesario para lograr la compatibilidad.
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class CompatibilidadMixta implements EstrategiaCompatibilidad {

    /**
     * Verifica la compatibilidad de una lista de componentes en un sistema mixto.
     * Detecta CPUs AMD que requieran adaptadores y los aplica automaticamente.
     * Genera advertencias sobre la mezcla de componentes de diferentes fabricantes.
     *
     * @param componentes lista de componentes a verificar
     * @return resultado de compatibilidad con componentes adaptados y advertencias
     */
    @Override
    public ResultadoCompatibilidad verificarCompatibilidad(List<ComponentePC> componentes) {
        List<String> advertencias = new ArrayList<>();
        List<ComponentePC> componentesAdaptados = new ArrayList<>();
        boolean esCompatible = true;

        if (requiereAdaptador(componentes)) {
            advertencias.add("ADVERTENCIA: Sistema mixto detectado - Se aplicaran adaptadores para CPUs AMD");
            componentesAdaptados = aplicarAdaptadores(componentes);
            advertencias.add("INFO: Se han aplicado adaptadores CPUAMDAdapter a los procesadores AMD");
        } else {
            componentesAdaptados.addAll(componentes);
        }

        CPU cpu = componentes.stream()
            .filter(c -> c instanceof CPU)
            .map(c -> (CPU) c)
            .findFirst()
            .orElse(null);

        MotherBoard motherboard = componentes.stream()
            .filter(c -> c instanceof MotherBoard)
            .map(c -> (MotherBoard) c)
            .findFirst()
            .orElse(null);

        FuenteAlimentacion fuente = componentes.stream()
            .filter(c -> c instanceof FuenteAlimentacion)
            .map(c -> (FuenteAlimentacion) c)
            .findFirst()
            .orElse(null);

        if (cpu != null && motherboard != null) {
            if (!verificarCPUMotherBoard(cpu, motherboard)) {
                advertencias.add("ADVERTENCIA: Configuracion mixta entre CPU " + cpu.obtenerMarca() +
                               " y MotherBoard " + motherboard.obtenerMarca() +
                               " - Se recomienda verificar compatibilidad");
            }
        }

        if (fuente != null) {
            if (!verificarPotenciaFuente(componentes, fuente)) {
                advertencias.add("ADVERTENCIA: La fuente de alimentacion podria ser insuficiente para el sistema mixto");
            }
        }

        return new ResultadoCompatibilidad(esCompatible, advertencias, componentesAdaptados);
    }

    /**
     * Verifica si la lista de componentes requiere adaptadores.
     * Detecta la presencia de CPUs AMD que necesiten ser adaptados.
     *
     * @param componentes lista de componentes a verificar
     * @return true si se requieren adaptadores, false en caso contrario
     */
    private boolean requiereAdaptador(List<ComponentePC> componentes) {
        return componentes.stream()
            .filter(c -> c instanceof CPU)
            .map(c -> (CPU) c)
            .anyMatch(cpu -> cpu.obtenerMarca().equalsIgnoreCase("AMD"));
    }

    /**
     * Aplica adaptadores a los componentes que lo requieran.
     * Envuelve las CPUs AMD en CPUAMDAdapter para lograr compatibilidad.
     *
     * @param componentes lista original de componentes
     * @return lista de componentes con adaptadores aplicados
     */
    private List<ComponentePC> aplicarAdaptadores(List<ComponentePC> componentes) {
        return componentes.stream()
            .map(c -> {
                if (c instanceof CPU) {
                    CPU cpu = (CPU) c;
                    if (cpu.obtenerMarca().equalsIgnoreCase("AMD")) {
                        return new CPUAMDAdapter(cpu);
                    }
                }
                return c;
            })
            .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    /**
     * Verifica la compatibilidad entre CPU y MotherBoard en sistemas mixtos.
     * Permite mayor flexibilidad en la compatibilidad comparado con estrategias puras.
     *
     * @param cpu procesador a verificar
     * @param motherboard placa base con la que se verifica compatibilidad
     * @return true si hay compatibilidad basica, false en caso contrario
     */
    private boolean verificarCPUMotherBoard(CPU cpu, MotherBoard motherboard) {
        return cpu.getArquitectura().contains("x86") || cpu.getArquitectura().contains("AMD64");
    }

    /**
     * Verifica que la fuente de alimentacion tenga potencia suficiente para sistemas mixtos.
     * Calcula el consumo estimado considerando el overhead de sistemas mixtos.
     *
     * @param componentes lista de componentes del sistema
     * @param fuenteAlimentacion fuente de alimentacion a verificar
     * @return true si la potencia es suficiente, false en caso contrario
     */
    private boolean verificarPotenciaFuente(List<ComponentePC> componentes, FuenteAlimentacion fuenteAlimentacion) {
        int consumoEstimado = componentes.stream()
            .mapToInt(c -> {
                if (c instanceof CPU) return 135;
                if (c instanceof GPU) return 250;
                if (c.obtenerTipo().equals("RAM")) return 10;
                if (c.obtenerTipo().equals("Disco") || c.obtenerTipo().equals("HDD") || c.obtenerTipo().equals("SSD")) return 15;
                if (c instanceof MotherBoard) return 80;
                return 0;
            })
            .sum();

        int potenciaRecomendada = (int) (consumoEstimado * 1.25);
        return fuenteAlimentacion.getPotenciaMaxima() >= potenciaRecomendada;
    }
}
