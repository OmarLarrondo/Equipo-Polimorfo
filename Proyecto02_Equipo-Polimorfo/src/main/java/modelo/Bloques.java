package modelo;

import modelo.nucleo_juego.patron_composite.ObjetoJuego;

/**
 * Representa un bloque en el campo de juego que puede ser destruido
 * por la pelota.
 *
 * @author Equipo-polimorfo
 * @version 1.0
 */
public class Bloques extends ObjetoJuego {

    private int resistencia;
    private int puntos;

    public Bloques(double x, double y, double ancho, double alto, int resistencia) {
        super(x, y, ancho, alto);
        this.resistencia = resistencia;
        this.puntos = resistencia * 10;
    }

    @Override
    public void actualizar(double deltaTime) {
    }

    public void recibirDanio() {
        resistencia--;
        if (resistencia <= 0) {
            activo = false;
        }
    }

    public int obtenerResistencia() {
        return resistencia;
    }

    public int obtenerPuntos() {
        return puntos;
    }
}
