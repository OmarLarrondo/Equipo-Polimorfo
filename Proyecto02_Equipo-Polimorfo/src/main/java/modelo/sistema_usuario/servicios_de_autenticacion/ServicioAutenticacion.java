package modelo.sistema_usuario.servicios_de_autenticacion;

import java.util.Map;
import java.time.LocalDateTime;
import java.util.HashMap;

import modelo.prototype.ConfigPaleta;
import modelo.sistema_usuario.patron_decorator.ComponenteUsuario;
import modelo.sistema_usuario.patron_decorator.Usuario;
import modelo.sistema_usuario.patron_decorator.DecoradorLogros;

public class ServicioAutenticacion {
    private Map<String, Usuario> usuarios; // clave = nombreUsuario
    private ComponenteUsuario usuarioActual;
    private CodificadorClave codificadorClave;

    public ServicioAutenticacion() {
        this.usuarios = new HashMap<>();
        this.codificadorClave = new CodificadorClave();
    }

    public ComponenteUsuario registrar(String usuario, String clave, String correo) {
        // Verifica si ya existe
        if (usuarios.containsKey(usuario)) {
            System.out.println("❌ Error: el usuario ya está registrado.");
            return null;
        }

        // Crea y guarda nuevo usuario
        String claveCodificada = codificadorClave.codificar(clave);
        //como agregar la id
        Usuario userNuevo = new Usuario("id", usuario, claveCodificada, correo);

        usuarios.put(usuario, userNuevo);
        System.out.println("✅ Usuario registrado correctamente: " + usuario);
        return userNuevo;
    }

    public boolean iniciarSesion(String usuario, String clave) {
        // Verifica existencia
        if (!usuarios.containsKey(usuario)) {
            System.out.println("❌ No existe el usuario: " + usuario);
            return false;
        }

        Usuario u = usuarios.get(usuario);
        if (codificadorClave.verificar(clave, u.getClave())) {
            usuarioActual = u;
            System.out.println("🔓 Sesión iniciada: " + usuario);
            return true;
        } else {
            System.out.println("❌ Contraseña incorrecta.");
            return false;
        }
    }

    public void cerrarSesion() {
        if (usuarioActual != null) {
            System.out.println("🔒 Sesión cerrada de: " + usuarioActual.obtenerDescripcion());
        }
        usuarioActual = null;
    }

    public ComponenteUsuario obtenerUsuarioActual() {
        return usuarioActual;
    }

    public boolean estaAutenticado() {
        return usuarioActual != null;
    }

    public boolean cambiarClave(String claveVieja, String claveNueva) {
        if (usuarioActual == null) {
            System.out.println("⚠️ No hay ningún usuario autenticado.");
            return false;
        }

        Usuario u = (Usuario) usuarioActual;
        if (!codificadorClave.verificar(claveVieja, u.getClave())) {
            System.out.println("❌ La contraseña actual no coincide.");
            return false;
        }

        u.setClave(claveNueva);
        System.out.println("✅ Contraseña actualizada correctamente.");
        return true;
    }

    public ComponenteUsuario decorarConLogros(Usuario usuario) {
        // Devuelve el usuario decorado con logros
        ComponenteUsuario decorado = new DecoradorLogros(usuario);
        System.out.println("🌟 Usuario decorado con logros: " + usuario.getNombreUsuario());
        return decorado;
    }
}
