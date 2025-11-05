package modelo.sistema_usuario.patron_decorator;

public abstract class DecoradorUsuario implements ComponenteUsuario{
    protected ComponenteUsuario usuarioDecorado;

    public DecoradorUsuario(ComponenteUsuario usuarioDecorado) {
        this.usuarioDecorado = usuarioDecorado;
    }
    public String obtenerDescripcion(){
        return null;
        //aqui va su codigo 
    } 
    public int obtenerNivel(){
        //aqui va su codigo
        return 0;
    }
    public int obtenerExperiencia(){
        //aqui va su codigo
        return 0;
    } 
    
}
