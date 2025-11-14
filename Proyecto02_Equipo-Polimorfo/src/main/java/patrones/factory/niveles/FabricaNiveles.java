package patrones.factory.niveles;

import java.util.List;

import patrones.singleton.GestorPrototiposPaleta;

/**
 * Clase que centraliza la creación de niveles en el juego.
 * 
 * <p>
 * Utiliza el patrón Factory para delegar la creación de niveles
 * a las distintas implementaciones según el tipo de nivel
 * (CLASICO, PERSONALIZADO, INTELIGENTE). Permite crear niveles
 * completos a partir de configuraciones o datos de mapa.
 * </p>
 * @author Equipo-polimorfo
 * @version 1.0
 */
public class FabricaNiveles {
    /** Gestor de prototipos de paleta (singleton). */
    private GestorPrototiposPaleta gestorPrototipos;

    /**
     * Constructor por defecto.
     * Inicializa el gestor de prototipos de paleta.
     */
    public FabricaNiveles() {
        //FAAAAAAAAAAAAAAAAAAAAAALTTTTTTTTTTTTTTTAAAAAAAAAAAAAAAAA
        //gestorPrototipos = GestorPrototiposPaleta.obtenerInstancia();
    }

    /**
     * Crea un nivel a partir de una configuración {@link ConfigNivel}.
     * 
     * @param config Configuración con el tipo de nivel, dificultad, patrón, etc.
     * @return Nivel creado según la configuración.
     */
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
            case INTELIGENTE:{
                NivelIAFactory IAFactory = new NivelIAFactory();
                return IAFactory.crearNivel(config);
            }
            default:
                throw new IllegalArgumentException("Tipo de nivel no soportado: " + tipo);
        }
    }

    /**
     * Crea un nivel clásico con una dificultad específica.
     * 
     * @param dificultad Valor de dificultad del nivel.
     * @return Nivel clásico creado.
     */
    public Nivel crearNivelClasico(int dificultad) {
        ConfigNivel config = new ConfigNivel();
        config.setDificultad(dificultad);
        config.setTipoNivel(TipoNivel.CLASICO);
        NivelClasicoFactory clasicoFactory = new NivelClasicoFactory();
        return clasicoFactory.crearNivel(config);
    }

    /**
     * Obtiene la lista de prototipos de paleta disponibles para un tipo de nivel.
     * 
     * @param tipoNivel Tipo de nivel para el cual se desean los prototipos.
     * @return Lista de nombres de prototipos de paleta disponibles.
     */
    public List<String> obtenerPrototiposPaletaParaNivel(TipoNivel tipoNivel) {
        String tipo = tipoNivel.name();
        return gestorPrototipos.listaPrototiposDisponiblesJava();
    }
}
