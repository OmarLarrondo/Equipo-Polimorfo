package mvc.modelo.entidades.bloque;

import javafx.scene.paint.Color;

/**
 * Alias de Bloques para compatibilidad con código existente.
 *
 * @author Equipo-polimorfo
 * @version 1.0
 */
public class NieblaStrategy implements Bloque{
    private Color color;

    public NieblaStrategy(Color color){
        this.color = color;
    }
    
    @Override
    public boolean recibirGolpe(){
        return false;
    }
    @Override
    public void reestablecerEstadoInicial(){ }
    @Override
    public void establecerEstadoActivo(boolean tangible)
        throws UnsupportedOperationException{
        throw new UnsupportedOperationException("Neblina no se \"desactiva\"");
    }
    @Override
    public void establecerColor(Color color){
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
    public NieblaStrategy clone(){
        return new NieblaStrategy(this.color);
    }
}
