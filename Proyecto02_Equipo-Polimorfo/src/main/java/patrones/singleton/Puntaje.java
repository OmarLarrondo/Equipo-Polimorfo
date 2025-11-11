package patrones.singleton;

import java.time.LocalDateTime;
import mvc.modelo.enums.ModoJuego;

public class Puntaje {
    private String nombreJugador;
    private int puntaje;
    private LocalDateTime fecha;
    private ModoJuego modojuego;
    

    public int compararConNumero(Puntaje otro){
        if(otro == null){
            throw new IllegalArgumentException("No es valido nulo.");
        }
        int segundoPun = otro.getPuntaje();
        if (this.puntaje<segundoPun) {
            return -1;
        }else if (puntaje> segundoPun){
            return 1;
        }else{
            return 0;
        }
    }
    public int compararConFecha(Puntaje otro){
        if(otro == null){
            throw new IllegalArgumentException("No es valido nulo.");
        }
        LocalDateTime segundoPun = otro.getFecha();
        if (fecha.isBefore(segundoPun)) {
            return -1;
        }else if (fecha.equals(segundoPun)){
            return 1;
        }else{
            return 0;
        }
        
    }
    public int compararConNombre(Puntaje otro){
        return this.nombreJugador.compareTo(otro.getNombreJugador());
    }

    @Override
    public String toString() {
        return "Puntaje [nombreJugador=" + nombreJugador + ", punytaje=" + puntaje + ", fecha=" + fecha
                + ", modojuego=" + modojuego + "]";
    }
    public String getNombreJugador() {
        return nombreJugador;
    }
    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }
    public int getPuntaje() {
        return puntaje;
    }
    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }
    public LocalDateTime getFecha() {
        return fecha;
    }
    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
    public ModoJuego getModojuego() {
        return modojuego;
    }
    public void setModojuego(ModoJuego modojuego) {
        this.modojuego = modojuego;
    }
}
