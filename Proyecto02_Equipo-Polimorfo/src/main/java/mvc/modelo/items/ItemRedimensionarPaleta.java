package mvc.modelo.items;

import mvc.modelo.entidades.ObjetoJuego;
import mvc.modelo.entidades.Paleta;

public class ItemRedimensionarPaleta implements Item {
    private double mutiplicadorTamanio;
    private double duracion;
    private boolean activo;

    private double anchoOriginal;
    private double altoOriginal;
    
    public ItemRedimensionarPaleta(double multiplicadorTamanio, double duracion) {
        this.mutiplicadorTamanio = multiplicadorTamanio;
        this.duracion = duracion;
        this.activo = false;
    }


    @Override
    public void aplicar(ObjetoJuego objeto) {
        if(activo == true) return;

        if(!(objeto instanceof Paleta p)) throw new IllegalArgumentException("Solo se puede aplicar a paletas.!!!!!!!!!");

        anchoOriginal = p.obtenerAncho();
        altoOriginal = p.obtenerAlto();
        double nuevoAncho = anchoOriginal * mutiplicadorTamanio;
        double nuevoAlto = altoOriginal*mutiplicadorTamanio;
        p.setAlto(nuevoAlto);
        p.setAncho(nuevoAncho);
        activo = true;

    }

    @Override
    public double obtenerDuracion() {
        return duracion;

    }
    @Override
    public boolean estaActivo() {
        return activo;
    }
    @Override
    public void desactivar(ObjetoJuego objeto) {
        if(!(objeto instanceof Paleta p)) throw new IllegalArgumentException("Solo se puede aplicar a paletas.!!!!!!!!!");
        p.setAlto(altoOriginal);
        p.setAncho(anchoOriginal);
        activo = false;
    }    
}
