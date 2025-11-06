package modelo.gestion_ia.patron_factory;

import java.util.Map;

import modelo.editor.niveles.TipoNivel;

public class ConfigNivel {
    private TipoNivel tipoNivel;
    private int dificultad;
    private String patron;
    private DatosMapa datosMapa;
    private Map<String, String> prototiposPaletas;

    public TipoNivel obtenerTipoNivel(){
        return tipoNivel;
    }
    public int obtenerDificultad(){
        return dificultad;
    }
    public String obtenerPatron(){
        return patron;
    }
    public DatosMapa obtenerDatosMaoa(){
        return datosMapa;
    }


    //FALTA
    public void establecerPrototipoPaleta(String jugador, String nombrePrototipo){
        //aqui va su codigo
    }
    public String obtenerPrototipoPaleta(String jugador){
        return prototiposPaletas.get(jugador);
    }
    public TipoNivel getTipoNivel() {
        return tipoNivel;
    }
    public void setTipoNivel(TipoNivel tipoNivel) {
        this.tipoNivel = tipoNivel;
    }
    public int getDificultad() {
        return dificultad;
    }
    public void setDificultad(int dificultad) {
        this.dificultad = dificultad;
    }
    public String getPatron() {
        return patron;
    }
    public void setPatron(String patron) {
        this.patron = patron;
    }
    public DatosMapa getDatosMapa() {
        return datosMapa;
    }
    public void setDatosMapa(DatosMapa datosMapa) {
        this.datosMapa = datosMapa;
    }
    public Map<String, String> getPrototiposPaletas() {
        return prototiposPaletas;
    }
    public void setPrototiposPaletas(Map<String, String> prototiposPaletas) {
        this.prototiposPaletas = prototiposPaletas;
    }
    
    
}
