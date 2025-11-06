package persistencia;

import java.util.List;

import modelo.editor.niveles.Nivel;
import modelo.nucleo_juego.patron_state.estados.EstadoJuego;
import modelo.singleton.Puntaje;
import modelo.sistema_usuario.patron_decorator.Usuario;

/**
 * Servicio para persistir y cargar datos del juego.
 *
 * @author Equipo-polimorfo
 * @version 1.0
 */
public class ServicioPersistencia {

    private ManejadorArchivos manejadorArchivos;

    //AGREGAR AL DIAGRAM
    public ServicioPersistencia() {
    }
//AGREGAR AL DIAGRAM
    public void guardarPuntajes(List<Puntaje> puntajes) {
    }
//AGREGAR AL DIAGRAM
    public List<Puntaje> cargarPuntajes() {
        return null;
    }

    public void guardarJuego(EstadoJuego estadoJuego, String nombreArchivo){
        //aqui va su codigo

    }
    public EstadoJuego cargarJuego(String nombreArchivo){
        return null;
    //aqui va su codigo
    }
    public void guardarUsuario(Usuario usuario){
        //aqui va su codigo
    }
    public Usuario cargarUsuario(String nombreUsuario){
        return null;
        //aqui va su codigo
    }
    public void guardarNivel(Nivel nivel, String nombreArchivo){
        //aqui va su codigo
    }
    public Nivel cargarNivel(String nombreArchivo){
        return null;
        //aqui va su codigo
    }
}
