package patrones.singleton;

import java.util.List;

import patrones.prototype.PrototipoPaleta;
import mvc.modelo.entidades.Paleta;

public class GestorPrototiposPaleta {
    private GestorPrototiposPaleta instancia;
    private PrototipoPaleta prototipos;

    private GestorPrototiposPaleta(PrototipoPaleta prototipos) {
        this.prototipos = prototipos;
    }
    public GestorPrototiposPaleta obtenerInstancia(){
        if(instancia == null){
            return new GestorPrototiposPaleta(prototipos);
        }
        return instancia;
    }
    
    public void registrarPrototiposDefecto(){
        //aqui va su codigo 
    }
    public Paleta obtenerPrototipo(String nombre){
        //aqui va su codigo 
        return null;
    }
    public void  guardarPrototipoUsuario(String nombre, Paleta paleta){
        //aqui va su codigo
    }
    public List<String> listaPrototiposDisponibles(){
        //aqui va su codigo
        return null;
    } 
    
    
}
