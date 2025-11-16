package mvc.modelo.entidades.bloque;

import javafx.scene.paint.Color;

/**
 * Alias de Bloques para compatibilidad con código existente.
 *
 * @author Equipo-polimorfo
 * @version 1.0
 */
public class BloqueAlteneranteStrategy implements Bloque{

    private boolean iniciaActivo;
    private boolean esActivo;
    private Color color;

    public BloqueAlteneranteStrategy(boolean iniciaActivo, Color color) {
        this.esActivo = this.iniciaActivo = iniciaActivo;
        this.color = color;
    }

    @Override
    public boolean recibirGolpe(){
        return this.alternar();
    }
    public boolean alternar(){
        this.esActivo = !this.esActivo;
        return this.esActivo;
    }
    @Override
    public void reestablecerEstadoInicial(){
        this.esActivo = this.iniciaActivo;
    }

    public void establecerTangibilidadInicial(boolean iniciaActivo){
        this.iniciaActivo = iniciaActivo;
    }
    @Override
    public void establecerEstadoActivo(boolean tangible){
        this.iniciaActivo = tangible;
    }
    @Override
    public void establecerColor(Color color){
        this.color = color;
    }

    public boolean obtenerTangibilidadInicial(){
        return this.iniciaActivo;
    }
    @Override
    public boolean estaActivo(){
        return this.esActivo;
    }
    @Override
    public Color obtenerColor(){
        return this.color;
    }

    @Override
    public BloqueAlteneranteStrategy clone(){
        return new BloqueAlteneranteStrategy(this.iniciaActivo, this.color);
    }
}
