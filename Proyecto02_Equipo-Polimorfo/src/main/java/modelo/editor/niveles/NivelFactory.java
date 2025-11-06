package modelo.editor.niveles;

import modelo.factory.ConfigNivel;

public interface NivelFactory {
    public Nivel crearNivel(ConfigNivel conf);
}
