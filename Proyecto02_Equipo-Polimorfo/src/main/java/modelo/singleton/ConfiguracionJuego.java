package main.java.modelo.singleton;

public class ConfiguracionJuego {
    private ConfiguracionJuego instancia;
    private int anchoPantalla;
    private int altoPantalla;
    private double rapidezPelota;
    private double rapidesPaleta;
    private double volumen;

    private ConfiguracionJuego(ConfiguracionJuego instancia, int anchoPantalla, int altoPantalla, double rapidezPelota,
            double rapidesPaleta, double volumen) {
        this.instancia = instancia;
        this.anchoPantalla = anchoPantalla;
        this.altoPantalla = altoPantalla;
        this.rapidezPelota = rapidezPelota;
        this.rapidesPaleta = rapidesPaleta;
        this.volumen = volumen;
    }

    public ConfiguracionJuego obtenerInstancia(){
        //aqui va su codigo
        return null;
    }
    public void cargarConfiguracion(){
        //Aqui va su codigo
    }
    public void guardarConfiguracion(){
        //aqui va su codigo
    }
    public void establecerVolumen(double volumen){
        //aqui va su codigo
    }
    public int obtenerAnchoPantalla(){
        return anchoPantalla;
    }
    public int obtenerAltoPanntalla(){
        return altoPantalla;
    }
    
}
