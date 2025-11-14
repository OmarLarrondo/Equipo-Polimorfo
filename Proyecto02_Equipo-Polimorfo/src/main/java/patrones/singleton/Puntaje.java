package patrones.singleton;

import java.time.LocalDateTime;
import mvc.modelo.enums.ModoJuego;

/**
 * Representa un registro de puntaje obtenido por un jugador en una partida.
 * 
 * <p>La clase almacena información relevante sobre el desempeño del jugador,
 * incluyendo su nombre, el valor del puntaje, la fecha en la que se obtuvo
 * y el modo de juego utilizado.</p>
 * 
 * <p>Implementa {@link Comparable} para permitir ordenar puntajes de manera
 * natural, colocando primero los puntajes más altos.</p>
 * 
 * @author equipo-polimorfo
 * @version 1.0
 */
public class Puntaje implements Comparable<Puntaje> {
    /** Nombre del jugador que obtuvo el puntaje. */
    private String nombreJugador;

    /** Valor numérico del puntaje obtenido. */
    private int puntaje;

    /** Fecha y hora en que se registró el puntaje. */
    private LocalDateTime fecha;

    /** Modo de juego en que se obtuvo el puntaje. */
    private ModoJuego modoJuego;

    /**
     * Crea un nuevo objeto {@code Puntaje} con la información proporcionada.
     *
     * @param nombreJugador nombre del jugador
     * @param puntaje valor del puntaje obtenido
     * @param fecha fecha y hora de registro
     * @param modoJuego modo de juego asociado al puntaje
     * @throws IllegalArgumentException si nombreJugador es nulo o vacío, 
     *                                  si fecha es nula, 
     *                                  o si puntaje es negativo
     */
    public Puntaje(String nombreJugador, int puntaje, LocalDateTime fecha, ModoJuego modoJuego) {
        this.nombreJugador = nombreJugador;
        this.puntaje = puntaje;
        this.fecha = fecha;
        this.modoJuego = modoJuego;
    }

    /**
     * Compara este puntaje con otro para establecer un orden natural.
     * 
     * <p>El orden natural coloca primero a los puntajes más altos.</p>
     *
     * @param otro el puntaje a comparar
     * @return un valor negativo si {@code otro.puntaje > this.puntaje},
     *         cero si ambos puntajes son iguales,
     *         y un valor positivo si {@code this.puntaje > otro.puntaje}
     */
    @Override
    public int compareTo(Puntaje otro) {
        return Integer.compare(otro.puntaje, this.puntaje);
    }

    /**
     * Compara este puntaje con otro según la fecha en la que fueron obtenidos.
     *
     * @param otro puntaje contra el cual se realiza la comparación
     * @return un valor negativo si este puntaje es anterior a {@code otro},
     *         cero si ambas fechas son iguales,
     *         y un valor positivo si este puntaje es posterior
     * @throws IllegalArgumentException si {@code otro} es nulo
     */
    public int compararConFecha(Puntaje otro) {
        if (otro == null) {
            throw new IllegalArgumentException("Puntaje nulo.");
        }
        return this.fecha.compareTo(otro.fecha);
    }

    /**
     * Compara alfabéticamente el nombre del jugador de este puntaje
     * con el de otro.
     *
     * @param otro puntaje a comparar
     * @return un valor negativo, cero o positivo dependiendo del orden lexicográfico
     */
    public int compararConNombre(Puntaje otro) {
        return this.nombreJugador.compareTo(otro.nombreJugador);
    }

    /**
     * Devuelve una representación en cadena del puntaje, incluyendo
     * el nombre del jugador, su puntaje, la fecha de registro y el modo de juego.
     *
     * @return una cadena descriptiva del puntaje
     */
    @Override
    public String toString() {
        return "Puntaje [nombreJugador=" + nombreJugador +
            ", puntaje=" + puntaje + 
            ", fecha=" + fecha +
            ", modoJuego=" + modoJuego + "]";
    }

    //Getters and Setters...
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

    public ModoJuego getModoJuego() {
        return modoJuego;
    }

    public void setModoJuego(ModoJuego modoJuego) {
        this.modoJuego = modoJuego;
    }

    // Getters y Setters
    
}
