package main.java.modelo.sistema_usuario.patron_decorator;

import java.util.List;

public class DecoradorLogros extends DecoradorUsuario{
    private List<Logro> logros;

    public DecoradorLogros(ComponenteUsuario usuarioDecorado) {
        super(usuarioDecorado);
        //TODO Auto-generated constructor stub
    }

    public void agregarLogro(Logro logro){
        //aqui va su codigo
        
    }
    public String obtenerDescripcion(){
        //aqui va su codigo
        return null;
    }
    public List<Logro> obtenerLogros(){
        //aqui va su codigo 
        return null;
    }
}
