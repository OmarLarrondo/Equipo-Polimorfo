package modelo.estrategia;

import java.util.List;
import java.util.ArrayList;
import modelo.componente.ComponentePC;
import modelo.componente.MotherBoard;
import modelo.componente.CPU;
import modelo.componente.GPU;
import modelo.componente.FuenteAlimentacion;

/**
 * Estrategia de compatibilidad para sistemas basados en procesadores Intel.
 * Verifica que todos los componentes sean compatibles entre si, especialmente
 * la CPU Intel con la placa base y la potencia de la fuente de alimentacion.
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class CompatibilidadIntel implements EstrategiaCompatibilidad {

    /**
     * Verifica la compatibilidad de una lista de componentes para un sistema Intel.
     * Valida la compatibilidad entre CPU y MotherBoard, asi como la potencia de la fuente.
     *
     * @param componentes lista de componentes a verificar
     * @return resultado de compatibilidad con advertencias si aplican
     */
    @Override
    public ResultadoCompatibilidad verificarCompatibilidad(List<ComponentePC> componentes) {
        List<String> advertencias = new ArrayList<>();
        List<ComponentePC> componentesAdaptados = new ArrayList<>();
        boolean esCompatible = true;

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
                esCompatible = false;
                advertencias.add("INCOMPATIBILIDAD: CPU Intel " + cpu.obtenerNombre() +
                               " no es compatible con MotherBoard " + motherboard.obtenerNombre());
            }
        }

        if (cpu != null && !cpu.obtenerMarca().equalsIgnoreCase("Intel")) {
            advertencias.add("ADVERTENCIA: Se esperaba CPU Intel pero se encontro marca " + cpu.obtenerMarca());
        }

        if (fuente != null) {
            if (!verificarPotenciaFuente(componentes, fuente)) {
                advertencias.add("ADVERTENCIA: La fuente de alimentacion podria ser insuficiente para el sistema");
            }
        }

        return new ResultadoCompatibilidad(esCompatible, advertencias, componentesAdaptados);
    }

    /**
     * Verifica que una CPU Intel sea compatible con una placa base.
     * Comprueba que las arquitecturas coincidan y que la CPU sea de marca Intel.
     *
     * @param cpu procesador Intel a verificar
     * @param motherboard placa base con la que se verifica compatibilidad
     * @return true si son compatibles, false en caso contrario
     */
    private boolean verificarCPUMotherBoard(CPU cpu, MotherBoard motherboard) {
        return cpu.obtenerMarca().equalsIgnoreCase("Intel") &&
               cpu.getArquitectura().contains("x86-64") &&
               motherboard.getArquitecturaSeparada().contains("x86");
    }

    /**
     * Verifica que la fuente de alimentacion tenga potencia suficiente para todos los componentes.
     * Calcula el consumo estimado de CPU, GPU, RAM y discos, y verifica que la fuente
     * tenga al menos un 20% de margen de seguridad.
     *
     * @param componentes lista de componentes del sistema
     * @param fuenteAlimentacion fuente de alimentacion a verificar
     * @return true si la potencia es suficiente, false en caso contrario
     */
    private boolean verificarPotenciaFuente(List<ComponentePC> componentes, FuenteAlimentacion fuenteAlimentacion) {
        int consumoEstimado = componentes.stream()
            .mapToInt(c -> {
                if (c instanceof CPU) return 125;
                if (c instanceof GPU) return 250;
                if (c.obtenerTipo().equals("RAM")) return 10;
                if (c.obtenerTipo().equals("Disco") || c.obtenerTipo().equals("HDD") || c.obtenerTipo().equals("SSD")) return 15;
                if (c instanceof MotherBoard) return 80;
                return 0;
            })
            .sum();

        int potenciaRecomendada = (int) (consumoEstimado * 1.2);
        return fuenteAlimentacion.getPotenciaMaxima() >= potenciaRecomendada;
    }
}
