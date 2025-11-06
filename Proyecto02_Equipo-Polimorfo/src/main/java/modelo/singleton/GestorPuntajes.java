package modelo.singleton;

import java.util.List;

import persistencia.ServicioPersistencia;

public class GestorPuntajes {
    private GestorPuntajes instancia;
    private List<Puntaje> puntajesAltos;
    private PuntajeSesion sesionActual;
    private ServicioPersistencia ServicioPersistencia;

    
    
    private GestorPuntajes() {
    }

    public GestorPuntajes obtenerInstancia(){
        //aqui va su codigo 
        return null;
    }
    public void agregarPuntaje(Puntaje puntaje){
        //aqui va su codigo 
    }
    public List<Puntaje> obtenerPuntajesAltos(int limite){
        //aqui va su codigo
        return null;
    }
    public void guardarPuntajes(){
        //aqui va su codigo
    }
    public void cargarPuntajes(){
        //aqui va su codigo
    }
    public void  actualizarSesionActual(int puntos){
        //aqui va su codigo
    }
    
    
}
