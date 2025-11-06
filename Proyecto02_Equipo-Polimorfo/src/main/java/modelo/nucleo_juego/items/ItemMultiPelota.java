package modelo.nucleo_juego.items;

import modelo.nucleo_juego.patron_composite.ObjetoJuego;

public class ItemMultiPelota implements Item{
    private int cantidadPelotas;
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

    
}
