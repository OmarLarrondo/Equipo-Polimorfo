package patrones.factory.niveles;

import patrones.factory.ia.ConfigNivel;

public class NivelPersonalizadoFactory implements NivelFactory{

    @Override
    public Nivel crearNivel(ConfigNivel conf) {
        Nivel nivelPersonalizado = new Nivel();
        nivelPersonalizado.setNombre("Nivel Personalizado. Nombre: "+ conf.obtenerNombre());
        nivelPersonalizado.setDificultad(conf.getDificultad());
        //OBVIO NO?
        nivelPersonalizado.setMapaPersonalizado(false);
        nivelPersonalizado.setCreador("Sistema- Equipo-polimorfo");

        return nivelPersonalizado;
    }
    
}
