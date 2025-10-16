package modelo.estrategia;

import java.util.List;

import modelo.componente.ComponentePC;
import modelo.componente.MotherBoard;
import modelo.componente.*;;

public class CompatibilidadIntel implements EstrategiaCompatibilidad {

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
