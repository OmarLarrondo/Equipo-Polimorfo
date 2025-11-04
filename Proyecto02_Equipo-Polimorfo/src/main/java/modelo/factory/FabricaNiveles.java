package main.java.modelo.factory;

public class FabricaNiveles {
    private GestorPrototiposPaleta gestorPrototipos;
    
    public Nivel crearNivel(ConfigNivel config){
        //aqui va su codigo
        return null;
    }
    public Nivel crearNivelClasico(int dificultad){
        //aqui va su codigo
        return null;
    }
    public Nivel crearNivelBreakout(String patron){
        //aqui va us codig 
        return null;
    }
    public Nivel crearNivelPersonalizado(DatosMapa datosMapa){
        //aqui va su codigo 
        return null;
    }
    public List<String> obtenerPrototiposPaletaParaNivel(TipoNivel tipoNivel){
        //aqui va su codigo
        return null;
    }
    
}
