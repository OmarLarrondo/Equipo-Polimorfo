package modelo.nucleo_juego.items;

import modelo.nucleo_juego.patron_composite.ObjetoJuego;
import modelo.nucleo_juego.patron_composite.Paleta;
import modelo.nucleo_juego.patron_composite.Pelota;



//SOLO APLICARA PARA INSTANCIAS (PELOTA Y PALETA) BUENO
//ESO VEO, PORQUE UNICAMENTE ELLAS DOS TIENEN ATRIBUTOS DE VELOCIDAD. XD
public class ItemAumentoVelocidad  implements Item{
    private double multiplicadorVelocidad;
    private double duracion;
    private boolean activo;
    double tiempoRestante = duracion;
    
    @Override
    public void aplicar(ObjetoJuego objeto) {
        objeto.establecerActivo(activo);
        if(objeto instanceof Paleta){
            double velocidadOriginal = ((Paleta)objeto).obtenerVelocidad();
            ((Paleta)objeto).establecerVelocidad(velocidadOriginal*multiplicadorVelocidad);
            activo = true;
            objeto.actualizar(tiempoRestante);

            //CHECAR LA DURACION, AUN NO ESAT ESO
            //si la duracion es 0, se manda a desactivar
            desactivar(objeto);

        }
        else if(objeto instanceof Pelota){
            double velocidadOriginal = ((Pelota)objeto).obtenerVelocidad();
            ((Pelota)objeto).establecerVelocidadGeneral(velocidadOriginal*multiplicadorVelocidad);
            activo = true;
            objeto.actualizar(tiempoRestante);

            //CHECAR LA DURACION, AUN NO ESAT ESO

            //si la duracion es 0, se manda a desactivar
            desactivar(objeto);
        }else{
            throw new IllegalArgumentException("el objeto dbee ser una paleta o una pelota");
        }

        
    }
    @Override
    public double obtenerDuracion() {
        return duracion;
    }
    @Override
    public boolean estaActivo() {
        return activo;
    }
    
    //revierte la velocidad
    @Override
    public void desactivar(ObjetoJuego objeto) {
        //objeto
        activo = false;


    }
}
