package modelo.sistema_usuario.patron_decorator;

public abstract class DecoradorUsuario implements ComponenteUsuario{
    protected ComponenteUsuario usuarioDecorado;

    public DecoradorUsuario(ComponenteUsuario usuarioDecorado) {
        this.usuarioDecorado = usuarioDecorado;
    }
    public String obtenerDescripcion(){
        return usuarioDecorado.obtenerDescripcion();
    } 
    public int obtenerNivel(){
        return usuarioDecorado.obtenerNivel();
    }
    public int obtenerExperiencia(){
        return usuarioDecorado.obtenerExperiencia();
    } 
    
}
