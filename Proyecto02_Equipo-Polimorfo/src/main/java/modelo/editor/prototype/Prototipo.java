package modelo.editor.prototype;

import modelo.nucleo_juego.patron_composite.ObjetoJuego;

public interface Prototipo {
    public ObjetoJuego clonar(String nombre);    
}
