package mvc.modelo.entidades.bloque;

import javafx.scene.paint.Color;

/**
 * Alias de Bloques para compatibilidad con código existente.
 *
 * @author Equipo-polimorfo
 * @version 1.0
 */
public interface Bloque extends Cloneable{

    public boolean recibirGolpe();
    public void reestablecerEstadoInicial();

    public void establecerEstadoActivo(boolean estaActivo) throws UnsupportedOperationException;
    public void establecerColor(Color color) throws NullPointerException;

    public boolean estaActivo();
    public Color obtenerColor();

}
