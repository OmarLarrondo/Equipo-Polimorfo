package modelo.sistema_usuario.servicios_de_autenticacion;

import java.util.Map;
import modelo.sistema_usuario.patron_decorator.ComponenteUsuario;
import modelo.sistema_usuario.patron_decorator.Usuario;

public class ServicioAutenticacion {
    private Map<String, Usuario> usuarios;
    private ComponenteUsuario usuarioActual;
    private CodificadorClave codificadorClave;

    public ComponenteUsuario registrar(String usuario, String clave, String correo){
        //aqui va su codigo
        return null;
    }
    public boolean iniciarSesion(String usuario,String clave){
        return false;
    }
    public void cerrarSesion(){

    }
    public ComponenteUsuario obtenerUsuarioActual(){
        return null;
    }
    public boolean estaAutenticado(){
        return false;
    }
    public boolean cambiarClave(String claveVieja, String claveNueva){
        return false;
    }
    public ComponenteUsuario decorarConLogros(Usuario usuario){
        return null;
    }
}
