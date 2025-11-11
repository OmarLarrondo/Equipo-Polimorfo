package patrones.prototype;

import mvc.modelo.entidades.ObjetoJuego;

public interface Prototipo {
    public ObjetoJuego clonar(String nombre);    
}
