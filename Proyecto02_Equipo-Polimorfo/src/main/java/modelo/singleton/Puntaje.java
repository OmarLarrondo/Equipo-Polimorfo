package main.java.modelo.singleton;

import java.time.LocalDateTime;

public class Puntaje {
    private String nombreJugador;
    private int punytaje;
    private LocalDateTime fecha;
    private ModoJuego modojuego;
    
    public int compararCon(Puntaje otro){
        //aqui va su codigo
        return 0;
    }

    @Override
    public String toString() {
        return "Puntaje [nombreJugador=" + nombreJugador + ", punytaje=" + punytaje + ", fecha=" + fecha
                + ", modojuego=" + modojuego + "]";
    }
    
}
