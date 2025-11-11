package patrones.singleton;

import java.util.ArrayList;
import java.util.List;

public class GestorPuntajes {
    private static GestorPuntajes instancia; //singleton, instancia global
    private List<Puntaje> puntajesAltos; //mejores puntajes
    private PuntajeSesion partidaActual; //partida actual

    private GestorPuntajes() {
        puntajesAltos = new ArrayList<>();
    }

    public GestorPuntajes obtenerInstancia(){
        if(instancia == null){
            return  new GestorPuntajes();
        }
        return instancia;
    }
    public void agregarPuntaje(Puntaje puntaje){
        if(puntaje == null) throw new IllegalArgumentException("No es posible agregar un puntaje nulo.");
        puntajesAltos.add(puntaje);

    }
    public List<Puntaje> obtenerPuntajesAltos(int limite){
        puntajesAltos.sort((o1, o2)-> -o1.compararConNumero(o2));
        
        List<Puntaje> altos = new ArrayList<>();
        for(int i = 0; i <Math.min(limite, puntajesAltos.size()); i++){
            altos.add(puntajesAltos.get(i));
        }
        return altos;
    }
    public void guardarPuntajes(){
        // se debe GUARDAR EN UN ARCHIVO, SQL O JSON, 
        //aqui va su codigo
    }
    public void cargarPuntajes(){
        // se debe CARGAREN UN ARCHIVO, SQL O JSON,
        //aqui va su codigo
    }
    public void  actualizarSesionActual(int puntajeFinal){
        //aqui va su codigo 
    }
    
}
