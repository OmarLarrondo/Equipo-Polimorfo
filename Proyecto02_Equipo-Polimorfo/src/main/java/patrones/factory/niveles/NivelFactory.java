package patrones.factory.niveles;

import patrones.factory.ia.ConfigNivel;

public interface NivelFactory {
    public Nivel crearNivel(ConfigNivel conf);
}
