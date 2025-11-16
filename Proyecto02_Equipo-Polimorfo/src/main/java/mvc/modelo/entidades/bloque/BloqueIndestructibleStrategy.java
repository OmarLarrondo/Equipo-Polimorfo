package mvc.modelo.entidades.bloque;

import javafx.scene.paint.Color;

/**
 * Alias de Bloques para compatibilidad con código existente.
 *
 * @author Equipo-polimorfo
 * @version 1.0
 */
public class BloqueIndestructibleStrategy implements Bloque{
    private Color color;

    public BloqueIndestructibleStrategy(Color color){
        this.color = color;
    }

    @Override
    public boolean recibirGolpe(){
        return true;
    }
    @Override
    public void reestablecerEstadoInicial(){ }
    @Override
    public void establecerEstadoActivo(boolean estaActivo)
        throws UnsupportedOperationException{
        throw new UnsupportedOperationException("Método no implementado");
    }
    @Override
    public void establecerColor(Color color)
        throws NullPointerException{
        if(color == null)
            throw new NullPointerException("Color no puede ser nulo.");
        this.color = color;
    }

    @Override
    public boolean estaActivo(){
        return true;
    }
    @Override
    public Color obtenerColor(){
        return this.color;
    }

    @Override
    public BloqueIndestructibleStrategy clone(){
        return new BloqueIndestructibleStrategy(this.color);
    }
}
