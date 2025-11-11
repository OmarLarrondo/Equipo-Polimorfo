package mvc.modelo.items;

import mvc.modelo.entidades.ObjetoJuego;

public interface Item {
    public void aplicar(ObjetoJuego objeto); 
    public double obtenerDuracion();
    public boolean estaActivo();
    public void desactivar(ObjetoJuego objeto);
}
