package patrones.factory.niveles;

import patrones.factory.ia.ConfigNivel;

/**
 * Implementación concreta de {@link NivelFactory} que crea instancias
 * de niveles personalizados definidos por el usuario.
 * 
 * <p>
 * Esta fábrica genera un objeto {@link Nivel} configurado según los
 * valores proporcionados en {@link ConfigNivel}, como el nombre y la
 * dificultad. Aunque se denomina "personalizado", la configuración
 * predeterminada establece el creador como el sistema.
 * </p>
 * 
 * @author  Equipo-polimorfo
 * @version 1.0
 */
public class NivelPersonalizadoFactory implements NivelFactory {

    /** {@inheritDoc} */
    @Override
    public Nivel crearNivel(ConfigNivel conf) {
        Nivel nivelPersonalizado = new Nivel();
        nivelPersonalizado.setNombre("Nivel Personalizado. Nombre: " + conf.obtenerNombre());
        nivelPersonalizado.setDificultad(conf.getDificultad());
        nivelPersonalizado.setMapaPersonalizado(true);
        nivelPersonalizado.setCreador(conf.obtenerNombre());
        return nivelPersonalizado;
    }
}
