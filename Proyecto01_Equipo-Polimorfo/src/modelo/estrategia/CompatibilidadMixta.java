package modelo.estrategia;

import java.util.List;

import modelo.componente.CPU;
import modelo.componente.ComponentePC;
import modelo.componente.FuenteAlimentacion;
import modelo.componente.MotherBoard;

public class CompatibilidadMixta implements EstrategiaCompatibilidad {
        @Override
    public ResultadoCompatibilidad verificarCompatibilidad(List<ComponentePC> componentes) {
        return null;
        // aqui va su codigo
    }

    private boolean verificarCPUMotherBoard(CPU cpu,MotherBoard motherboard ){
        //aqui va su codigo
        return false;
    }
    private boolean verificarPotenciaFuente(List<ComponentePC> componentes, FuenteAlimentacion fuenteAlimentacion){
        //aqui va du codigo
        return false;
    }
}
