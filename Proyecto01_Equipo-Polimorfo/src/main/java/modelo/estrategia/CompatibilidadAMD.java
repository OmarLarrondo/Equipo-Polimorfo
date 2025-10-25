package modelo.estrategia;

import java.util.List;
import java.util.ArrayList;
import modelo.componente.CPU;
import modelo.componente.ComponentePC;
import modelo.componente.FuenteAlimentacion;
import modelo.componente.MotherBoard;
import modelo.componente.GPU;

/**
 * Estrategia de compatibilidad para sistemas basados en procesadores AMD.
 * Verifica que todos los componentes sean compatibles entre si, especialmente
 * la CPU AMD con la placa base y la potencia de la fuente de alimentacion.
 *
 * Nota: Segun las especificaciones, las CPUs AMD no son totalmente compatibles
 * con las motherboards actuales del inventario, por lo que se generan advertencias.
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class CompatibilidadAMD implements EstrategiaCompatibilidad {

    /**
     * Verifica la compatibilidad de una lista de componentes para un sistema AMD.
     * Valida la compatibilidad entre CPU y MotherBoard, asi como la potencia de la fuente.
     * Genera advertencias sobre posibles problemas de compatibilidad con AMD.
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
                advertencias.add("ADVERTENCIA: CPU AMD " + cpu.obtenerNombre() +
                            " podria tener problemas de compatibilidad con MotherBoard " +
                            motherboard.obtenerNombre() + ". Se recomienda usar un adaptador");
            }
        }

        if (cpu != null && cpu.obtenerMarca().equalsIgnoreCase("AMD")) {
            advertencias.add("ADVERTENCIA: CPUs AMD requieren adaptador para compatibilidad completa");
        }

        if (fuente != null) {
            if (!verificarPotenciaFuente(componentes, fuente)) {
                advertencias.add("ADVERTENCIA: La fuente de alimentacion podria ser insuficiente para el sistema AMD");
            }
        }

        return new ResultadoCompatibilidad(esCompatible, advertencias, componentesAdaptados);
    }

    /**
     * Verifica que una CPU AMD sea compatible con una placa base.
     * Comprueba que las arquitecturas coincidan. Nota: segun especificaciones,
     * las CPUs AMD no son completamente compatibles con las motherboards actuales.
     *
     * @param cpu procesador AMD a verificar
     * @param motherboard placa base con la que se verifica compatibilidad
     * @return true si son parcialmente compatibles, false en caso contrario
     */
    private boolean verificarCPUMotherBoard(CPU cpu, MotherBoard motherboard) {
        if (!cpu.obtenerMarca().equalsIgnoreCase("AMD")) {
            return false;
        }
        return cpu.getArquitectura().contains("AMD64") || cpu.getArquitectura().contains("x86-64");
    }

    /**
     * Verifica que la fuente de alimentacion tenga potencia suficiente para todos los componentes AMD.
     * Calcula el consumo estimado de CPU, GPU, RAM y discos, y verifica que la fuente
     * tenga al menos un 20% de margen de seguridad. Los CPUs AMD tienden a consumir mas potencia.
     *
     * @param componentes lista de componentes del sistema
     * @param fuenteAlimentacion fuente de alimentacion a verificar
     * @return true si la potencia es suficiente, false en caso contrario
     */
    private boolean verificarPotenciaFuente(List<ComponentePC> componentes, FuenteAlimentacion fuenteAlimentacion) {
        int consumoEstimado = componentes.stream()
            .mapToInt(c -> {
                if (c instanceof CPU) return 140;
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
