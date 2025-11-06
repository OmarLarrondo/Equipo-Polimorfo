package modelo.nucleo_juego.items;

import modelo.nucleo_juego.patron_composite.ObjetoJuego;

public interface Item {
    public void aplicar(ObjetoJuego objeto); 
    public double obtenerDuracion();
    public boolean estaActivo();
    public void desactivar(ObjetoJuego objeto);
}
