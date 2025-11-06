package modelo.editor.niveles;

import java.util.ArrayList;
import java.util.List;
import modelo.Bloques;
import modelo.Bloque;

public class Nivel {
    private String id;
    private String nombre;
    private List<Bloques> bloques;
    private int dificultad;
    private boolean mapaPersonalizado;
    private String creador;
    

    public void  agregarBloque(Bloques bloque){
        if(bloque == null){
            throw new IllegalArgumentException("Error, el bloque no puuede ser null.");
        }
        bloques.add(bloque);
    }
    public void  eliminarBloque(Bloque bloque){
        if(bloque == null){
            throw new IllegalArgumentException("Estas eliminando un bloque null.");
        }
        bloques.remove(bloque); 
    }
    public List<Bloques> obtenerBloques(){
        if(bloques.isEmpty()){
            return new ArrayList<>();
        }
        return bloques;
    }

    //MMMMM, UN PONG COMPLETADO XD? O QUE 
    public boolean estaCompletado(){
        //aqui va us codigo
        //creo que no hace falta , porque ya quitamos el breakout
        return false;
    }

    public void reiniciar(){
        //Se reincia desde 0.
        id = null;
        nombre = null;
        dificultad = 0;
        mapaPersonalizado = false;
        creador = null;

        if(bloques.isEmpty()){
            bloques = new ArrayList<>();
        }else{
            bloques.clear();;
        }        
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public List<Bloques> getBloques() {
        return bloques;
    }
    public void setBloques(List<Bloques> bloques) {
        this.bloques = bloques;
    }
    public int getDificultad() {
        return dificultad;
    }
    public void setDificultad(int dificultad) {
        this.dificultad = dificultad;
    }
    public boolean isMapaPersonalizado() {
        return mapaPersonalizado;
    }
    public void setMapaPersonalizado(boolean mapaPersonalizado) {
        this.mapaPersonalizado = mapaPersonalizado;
    }
    public String getCreador() {
        return creador;
    }
    public void setCreador(String creador) {
        this.creador = creador;
    }
}
