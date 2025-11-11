package modelo.sistema_usuario.patron_decorator;

public abstract class DecoradorUsuario implements ComponenteUsuario{
    protected ComponenteUsuario usuarioDecorado;

    public DecoradorUsuario(ComponenteUsuario usuarioDecorado) {
        this.usuarioDecorado = usuarioDecorado;
    }
    @Override
    public String obtenerDescripcion(){
        return usuarioDecorado.obtenerDescripcion();
    } 
    @Override
    public int obtenerNivel(){
        return usuarioDecorado.obtenerNivel();
    }
    @Override
    public int obtenerExperiencia(){
        return usuarioDecorado.obtenerExperiencia();
    } 
    public ComponenteUsuario getUsuarioDecorado(){
        return usuarioDecorado;
    }
}


