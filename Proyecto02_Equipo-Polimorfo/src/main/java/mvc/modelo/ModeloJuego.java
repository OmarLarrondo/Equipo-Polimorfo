package mvc.modelo;

import java.nio.channels.Pipe;
import java.util.List;

import patrones.factory.niveles.Nivel;
import patrones.factory.ia.*;
import mvc.modelo.entidades.Bloque;
import mvc.modelo.entidades.Paleta;
import mvc.modelo.entidades.Pelota;
import mvc.modelo.items.Item;
import mvc.modelo.enums.ModoJuego;
import patrones.singleton.GestorPrototiposPaleta;


/**
 * Modelo principal del juego que contiene el estado completo
 * de la partida actual.
 *
 * @author Equipo-polimorfo
 * @version 1.0
 */
public class ModeloJuego {

    private Pelota pelota;
    private Paleta jugador1;
    private Paleta jugador2; 
    private List<Bloque> bloques;
    private List<Item> items;
    private Nivel nivel;
    private int puntaje1;
    private int puntaje2;
    private ModoJuego modoJuego;
    private GestorPrototiposPaleta gestorPrototiposPaleta;

    //MMM ESTO NO VIENE EN EL DIAGRAMA, CHECAR LOL
    private ModoJuego modoActual;

    public ModeloJuego() {
    }

    //AGREGAR AL DIAM
    public ModoJuego obtenerModoActual() {
        return modoActual;
    }
//AGREGAR AL DIAGRAM
    public void establecerModo(ModoJuego modo) {
        this.modoActual = modo;
    }

    public void actulizar(double tiempoDelta){
//aqui va su codigo 
    }
    public void reiniciar(){
        //aqui va su codigo 
    }
    public void agregarBloque(Bloque bloque){
        //aqui va su codigo
    }
    public void eliminarBloque(Bloque bloque){

    }
    public void generarItem(Item item){
        //aqui va us codigo
    }
    public void incrementarPuntaje(int jugador, int puntos){
        //aqui va us codigo
    }
    /** 
    public DatosEstadoJuego obtenerEstado(){
        return null;
        //Aqui va su codigo
    }
        */
    public void inicializarPaletasDesdePrototipos(String nombreProto, String nombreProto2){
        //aqui va us codigo 
    }
    public void guardarEstadoPaletas(){
        //aqui va us coifg 
    }
    public void restaurarEstadoPaletas(){
        //aqui va su codiog
    }
}
