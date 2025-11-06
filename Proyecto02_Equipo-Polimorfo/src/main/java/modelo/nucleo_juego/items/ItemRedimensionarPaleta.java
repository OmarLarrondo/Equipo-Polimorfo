package modelo.nucleo_juego.items;

import modelo.nucleo_juego.patron_composite.ObjetoJuego;
import modelo.nucleo_juego.patron_composite.Paleta;

public class ItemRedimensionarPaleta implements Item {
    private double mutiplicadorTamanio;
    private double duracion;
    private boolean activo;

    @Override
    public void aplicar(ObjetoJuego objeto) {
        //aqui va su codigo

    }
    @Override
    public double obtenerDuracion() {
        //aqui va su codigo
        return 0;

    }
    @Override
    public boolean estaActivo() {
        //aqui va su codigo
        return false;
    }
    @Override
    public void desactivar() {
        //aqui va su codigo
    }
    
    private void guardarEstadoOriginal(Paleta paleta){

        //aqui va su codigo
    }
    
    private void restaurarEstadoOriginal(Paleta paleta){
    //aqui va su codigo
    }
}
