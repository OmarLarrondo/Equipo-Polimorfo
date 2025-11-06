package modelo.editor.niveles;

import modelo.gestion_ia.patron_factory.ConfigNivel;

public interface NivelFactory {
    public Nivel crearNivel(ConfigNivel conf);
}
