package modelo.ui_uix.observer;

import java.util.Map;
import javafx.scene.media.AudioClip;
import javafx.scene.media.MediaPlayer;
import modelo.ui_uix.singleton.ConfiguracionJuego;
public class GestorAudio {
    Map<String, AudioClip> efectosSonido;
    MediaPlayer musicaFondo;
    double voumen;
    boolean silenciado;
    ConfiguracionJuego configuracion;

    public void reproducirSonido(String nombreSonido){
        //aqui va su codigo 
    }
    public void reproducirMusicaFondo(){
        //aqui va su codigo 
    }
    public void detenerMusicaFondo(){
        //aqui va su codigo 
    }
    public void establecerVolumen(double volumen){
        //aqui va su codigo 
    }
    public void silenciar(){
        //aqui va su codigo 
    }
    public void activarSonido(){
        //aqui va su codigo 
    }

    
    
}
