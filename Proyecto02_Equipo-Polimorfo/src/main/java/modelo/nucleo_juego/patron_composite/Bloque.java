package modelo.nucleo_juego.patron_composite;

import modelo.Bloques;

/**
 * Alias de Bloques para compatibilidad con código existente.
 *
 * @author Equipo-polimorfo
 * @version 1.0
 */
public class Bloque extends Bloques {

    public Bloque(double x, double y, double ancho, double alto, int resistencia) {
        super(x, y, ancho, alto, resistencia);
    }
}
