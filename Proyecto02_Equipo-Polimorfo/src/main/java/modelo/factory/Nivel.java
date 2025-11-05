package modelo.factory;

import java.util.List;
import modelo.Bloques;
import modelo.Bloque;

public class Nivel {
    private String id;
    private String nombres;
    private List<Bloques> bloques;
    private int dificultad;
    private boolean mapaPersonalizado;
    private String creador;
    

    public void  agregarBloque(Bloque bloque){
        //aqu va su codigo
    }
    public void  eliminarBloque(Bloque bloque){
        //aqui va us codfigo 
    }
    public List<Bloque> obtenerBloques(){
        //aqui va su codigo 
        return null;
    }
    public boolean estaCompletado(){
        //aqui va us codigo
        return false;
    }
    public void reiniciar(){
        // aqui ba su codigo
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getNombres() {
        return nombres;
    }
    public void setNombres(String nombres) {
        this.nombres = nombres;
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
