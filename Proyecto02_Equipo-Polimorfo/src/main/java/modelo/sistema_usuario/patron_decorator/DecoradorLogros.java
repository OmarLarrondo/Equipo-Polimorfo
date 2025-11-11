package modelo.sistema_usuario.patron_decorator;

import java.util.ArrayList;
import java.util.List;

public class DecoradorLogros extends DecoradorUsuario{
    private List<Logro> logros;

    public DecoradorLogros(ComponenteUsuario usuarioDecorado) {
        super(usuarioDecorado);
        this.logros = new ArrayList<>();
    }

    public void agregarLogro(Logro logro){
        if(logro != null && !(logros.contains(logro))){
            logros.add(logro);
        }
        
    }
    public String obtenerDescripcion(){
        String descripcionBase = usuarioDecorado.obtenerDescripcion();
        if (logros.isEmpty()) {
            return descripcionBase + " | Sin logros aún";
        } else {
            return descripcionBase + " | Logros obtenidos: " + logros.size();
        }
    }
    public List<Logro> obtenerLogros(){
        return new ArrayList<>(logros);
    }
    
    public boolean tieneLogro(String id) {
        return logros.stream().anyMatch(l -> l.getId().equals(id));
    }

}
