package modelo.nucleo_juego.items;

import modelo.nucleo_juego.patron_composite.ObjetoJuego;

public class ItemAumentoVelocidad  implements Item{
    private double multiplicadorVelocidad;
    private double duracion;
    private boolean activo;
    
    @Override
    public void aplicar(ObjetoJuego objeto) {
        //Aqui va su codigo
        
    }
    @Override
    public double obtenerDuracion() {
        //Aqui va su codigo
        return 0;
    }
    @Override
    public boolean estaActivo() {
        //Aqui va su codigo
        return false;
    }
    @Override
    public void desactivar() {
        //Aqui va su codigo
        
    }

    

    
}
