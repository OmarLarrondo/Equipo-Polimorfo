package modelo.editor.niveles;

import java.util.List;

import modelo.Bloques;
import modelo.gestion_ia.patron_factory.ConfigNivel;
import modelo.gestion_ia.patron_factory.DatosMapa;
import modelo.ui_uix.singleton.GestorPrototiposPaleta;

public class FabricaNiveles {
    private GestorPrototiposPaleta gestorPrototipos;
    
    public FabricaNiveles() {
        //gestorPrototipos = GestorPrototiposPaleta.obtenerInstancia();
    }
    
    public Nivel crearNivel(ConfigNivel config) {
        TipoNivel tipo = config.obtenerTipoNivel();

        switch (tipo) {
            case CLASICO: {
                NivelClasicoFactory clasicoFactory = new NivelClasicoFactory();
                return clasicoFactory.crearNivel(config);
            }

            case PERSONALIZADO: {
                NivelPersonalizadoFactory persoFactory = new NivelPersonalizadoFactory();
                return persoFactory.crearNivel(config);
            }

            //falta INTELIGENTE

            default:
                throw new IllegalArgumentException("Tipo de nivel no soportado: " + tipo);
        }
    }

    public Nivel crearNivelClasico(int dificultad) {
        ConfigNivel config = new ConfigNivel();
        config.setDificultad(dificultad);
        config.setTipoNivel(TipoNivel.CLASICO);

        NivelClasicoFactory clasicoFactory = new NivelClasicoFactory();
        return clasicoFactory.crearNivel(config);
    }

    public Nivel crearNivelPersonalizado(DatosMapa datosMapa) {
        Nivel nivel = new Nivel();
        nivel.setNombre(datosMapa.obtenerNombre());
        nivel.setDificultad(1);
        nivel.setMapaPersonalizado(true);
        nivel.setCreador("Usuario");

        int anchoBloque = 50;
        int altoBloque = 20;
        int separacion = 5;

        for (int fila = 0; fila < datosMapa.obtenerFilas(); fila++) {
            for (int col = 0; col < datosMapa.obtenerColumnas(); col++) {
                int x = 50 + col * (anchoBloque + separacion);
                int y = 50 + fila * (altoBloque + separacion);
                Bloques bloque = new Bloques(x, y, anchoBloque, altoBloque, 1);
                nivel.agregarBloque(bloque);
            }
        }

        return nivel;
    }

    public List<String> obtenerPrototiposPaletaParaNivel(TipoNivel tipoNivel) {
        String tipo = tipoNivel.name();
        return gestorPrototipos.listaPrototiposDisponibles();
    }
}
