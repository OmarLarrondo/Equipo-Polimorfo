package mvc.modelo.entidades.bloque;

import javafx.scene.paint.Color;
/**
 * Alias de Bloques para compatibilidad con código existente.
 *
 * @author Equipo-polimorfo
 * @version 1.0
 */
public class BloqueDestructibleStrategy implements Bloque{

    private int saludInicial;
    private int saludRestante;
    private boolean estaActivo;
    private Color color;

    public BloqueDestructibleStrategy(int saludInicial, boolean estaActivo, Color color){
        this.saludInicial = this.saludRestante = saludInicial;
        this.estaActivo = estaActivo;
        this.color = color;
    }

    @Override
    public boolean recibirGolpe(){
        if(this.saludRestante > 0){
            this.saludRestante --;
            return true;
        }
        return false;
    }
    @Override
    public void reestablecerEstadoInicial(){
        this.saludRestante = this.saludInicial;
        this.estaActivo = true;
    }

    public void establecerSaludInicial(int s)
        throws IndexOutOfBoundsException{
            if(s < 0)
                throw new IndexOutOfBoundsException("Valor negativo no es valido.");
        this.saludInicial = s;
        if(this.saludRestante > s)
            this.saludRestante = s;
    }
    public void establecerSaludRestante(int s){
            if(s < 0)
                throw new IndexOutOfBoundsException("Valor negativo no es valido.");
            if(s > this.saludInicial)
                throw new IndexOutOfBoundsException("Valor excede la salud inicial.");
        this.saludRestante = s;
    }
    @Override
    public void establecerEstadoActivo(boolean estaActivo)
        throws UnsupportedOperationException{
        throw new UnsupportedOperationException("El estado del bloque depende de su salud restante.");
    }
    @Override
    public void establecerColor(Color color)
        throws NullPointerException{
        if(color == null)
            throw new NullPointerException("Color no puede ser nulo.");
        this.color = color;
    }

    public int obtenerSaludInicial(){
        return this.saludInicial;
    }
    public int obtenerSaludRestante(){
        return this.saludRestante;
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
    public BloqueDestructibleStrategy clone(){
        return new BloqueDestructibleStrategy(this.saludInicial, this.estaActivo, this.color);
    }

}
