package patrones.singleton;

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
        if(instancia == null){
            return new ConfiguracionJuego(instancia, anchoPantalla, altoPantalla, rapidezPelota, rapidesPaleta, volumen);
        }
        return instancia;
    }
    public void cargarConfiguracion(){
        //Aqui va su codigo
    }
    public void guardarConfiguracion(){
        //aqui va su codigo
    }
    public void establecerVolumen(double volumen){
        this.volumen = volumen;
    }
    public int obtenerAnchoPantalla(){
        return anchoPantalla;
    }
    public int obtenerAltoPanntalla(){
        return altoPantalla;
    }
    
}
