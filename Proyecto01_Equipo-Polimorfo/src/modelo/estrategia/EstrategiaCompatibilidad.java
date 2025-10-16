package modelo.estrategia;

import java.util.List;
import modelo.componente.ComponentePC;

public interface EstrategiaCompatibilidad {
    ResultadoCompatibilidad verificarCompatibilidad(List<ComponentePC> componentes);
}
