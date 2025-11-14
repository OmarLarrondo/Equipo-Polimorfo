package patrones.singleton;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase Singleton encargada de gestionar los puntajes del juego.
 *
 * <p>Esta clase almacena los puntajes más altos, administra la sesión
 * actual del jugador y proporciona métodos para persistir y cargar la
 * información desde el {@link SQLITE} .</p>
 *
 * <p>Al implementar el patrón Singleton, garantiza que exista una única
 * instancia global accesible desde cualquier parte del sistema.</p>
 *
 * @author Equipo-polimorfo
 * @version 1.0
 */
public class GestorPuntajes {
    /** Instancia única del Singleton. */
    private static GestorPuntajes instancia;

    /** Lista de puntajes más altos del sistema. */
    private List<Puntaje> puntajesAltos;

    /** Puntaje asociado a la partida en curso. */
    private PuntajeSesion partidaActual;

    /**
     * Constructor privado del Singleton.
     *
     * <p>Inicializa la lista de puntajes altos. El constructor es privado para
     * evitar instanciaciones externas.</p>
     */
    private GestorPuntajes() {
        puntajesAltos = new ArrayList<>();
    }

    /**
     * Obtiene la instancia única del gestor de puntajes.
     * <p>Si la instancia aún no ha sido creada, se construye en este punto.</p>
     * @return la instancia única de {@code GestorPuntajes}.
     */
    public static GestorPuntajes obtenerInstancia(){
        if(instancia == null){
            return  new GestorPuntajes();
        }
        return instancia;
    }


    /**
     * Agrega un puntaje nuevo a la lista de puntajes.
     * <p>Se  deben hacer validaciones a puntaje  y agregarlo a la lista puntajesAltos.</p>
     * @param puntaje el puntaje nuevo a agregar.
     */
    public void agregarPuntaje(Puntaje puntaje){
        if(puntaje == null) throw new IllegalArgumentException("No es posible agregar un puntaje nulo.");
        puntajesAltos.add(puntaje);
    }

    /**
     * Obtiene los puntajes mas altos de la lista de puntajes.
     * <p>Se debe ordenar la lista, crear una lista de tamaño limite y 
     * regresarla.</p>
     * @param limite el limite maximo de puntajes a obtener.
     * @return Una lista con los punatjes mas altos, de tamaño a lo mas {@link limite}
     */
    public List<Puntaje> obtenerPuntajesAltos(int limite){
        puntajesAltos.sort((o1, o2)-> -o1.compararConNumero(o2));
        
        List<Puntaje> altos = new ArrayList<>();
        for(int i = 0; i <Math.min(limite, puntajesAltos.size()); i++){
            altos.add(puntajesAltos.get(i));
        }
        return altos;
    }

    /**
     * Guarda los puntajes altos en un medio de persistencia.
     *
     * <p>Dependiendo de la implementación deseada, este método debe escribir la
     * información en la persistencia</p>
     */
    public void guardarPuntajes(){
        // se debe GUARDAR EN UN ARCHIVO, SQL O JSON, 
        //aqui va su codigo
    }

    /**
     * Carga los puntajes previamente almacenados desde un medio de persistencia.
     *
     * <p>Debe reconstruir la lista {@link #puntajesAltos} a partir de los datos
     * recuperados.</p>
     */
    public void cargarPuntajes(){
        // se debe CARGAREN UN ARCHIVO, SQL O JSON,
        //aqui va su codigo
    }
    
    /**
     * Actualiza la información del puntaje de la sesión actual.
     *
     * <p>Debe utilizarse una vez finalizada la partida del jugador para
     * registrar su puntaje final.</p>
     *
     * @param puntajeFinal puntaje obtenido por el jugador en la sesión actual.
     */
    public void  actualizarSesionActual(int puntajeFinal){
        //aqui va su codigo 
    }
    
}
