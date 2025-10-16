package modelo.estrategia;

import java.util.List;

import modelo.componente.ComponentePC;

public class ResultadoCompatibilidad {
    private boolean esCompatible;
    private List<String> advertencias;
    private List<ComponentePC> componentesAdaptados;

    public ResultadoCompatibilidad(boolean esCompatible, List<String> advertencias,
            List<ComponentePC> componentesAdaptados) {
        this.esCompatible = esCompatible;
        this.advertencias = advertencias;
        this.componentesAdaptados = componentesAdaptados;
    }

    public boolean isCompatible(){
        //aqui va su codig 
        return false;
    }
    
    public List<String> getAdvertencias(){
        //aqui va su codigo
        return null;
    }

    public List<ComponentePC> getComponenetesAdaptados(){
        //aqui va su codigo 
        return null;
    }

}
