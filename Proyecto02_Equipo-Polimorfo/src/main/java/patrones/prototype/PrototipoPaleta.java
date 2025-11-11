package patrones.prototype;

import java.util.HashMap;
import java.util.Map;

import mvc.modelo.entidades.ObjetoJuego;
import mvc.modelo.entidades.Paleta;

public class PrototipoPaleta  implements Prototipo {
    private Map<String, Paleta> prototipos = new HashMap<>();


    public void registrarPrototipo(String nombre, Paleta paleta){
        prototipos.put(nombre, paleta);
    }

    public Paleta crearPaletaPersonalizada(String nombreBase,ConfigPaleta config){
        Paleta personaliPaleta = (Paleta)clonar(nombreBase);
        config.aplicarA(personaliPaleta);
        return personaliPaleta;
    }

    @Override
    public ObjetoJuego clonar(String nombre) {
        Paleta base = prototipos.get(nombre);
        if (base == null) {
            throw new IllegalArgumentException("No existe prototipo con el nombre: " + nombre);
        }

        return new Paleta(base.obtenerX(), base.obtenerY(), base.obtenerAncho(), base.obtenerAlto());
    }
        
    
}
