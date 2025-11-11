package mvc.modelo.items;

import mvc.modelo.entidades.ObjetoJuego;
import mvc.modelo.entidades.Paleta;
import mvc.modelo.entidades.Pelota;



//SOLO APLICARA PARA INSTANCIAS (PELOTA Y PALETA) BUENO
//ESO VEO, PORQUE UNICAMENTE ELLAS DOS TIENEN ATRIBUTOS DE VELOCIDAD. XD
public class ItemAumentoVelocidad  implements Item{
    private double multiplicadorVelocidad;
    private double duracion;
    private boolean activo;
    //AGREGAR AL DIAG
    private double tiempoRestante;

    
    //AGREAGAR AL DIAMA
    public ItemAumentoVelocidad(double multiplicadorVelocidad, double duracion, boolean activo, double tiempoRestante) {
        this.multiplicadorVelocidad = multiplicadorVelocidad;
        this.duracion = duracion;
        this.activo = activo;
        this.tiempoRestante = tiempoRestante;
    }

    @Override
    public void aplicar(ObjetoJuego objeto) {
        if(activo) return; //pa no volver aplicar si esta activo el efec, 

        if(objeto instanceof Paleta paleta){
            double velocidadOriginal = paleta.obtenerVelocidad();
            paleta.establecerVelocidad(velocidadOriginal*multiplicadorVelocidad);
            paleta.establecerActivo(true);
            activo = true;

            //CHECAR LA DURACION, AUN NO ESAT ESO
            //si la duracion es 0, se manda a desactivar
            desactivar(objeto);

        }
        else if(objeto instanceof Pelota pelota){
            double velocidadOriginal = pelota.obtenerVelocidad();
            pelota.establecerVelocidadGeneral(velocidadOriginal*multiplicadorVelocidad);
            pelota.establecerActivo(true);
            activo = true;

            //CHECAR LA DURACION, Se llamaria cada frame dle juego.
        }else{
            throw new IllegalArgumentException("el objeto dbee ser una paleta o una pelota");
        }
        tiempoRestante = duracion;

        
    }
    @Override
    public double obtenerDuracion() {
        return duracion;
    }
    @Override
    public boolean estaActivo() {
        return activo;
    }
    

    public void actualizar(double deltaTiempo, ObjetoJuego objeto){
        if(!activo) return;

        tiempoRestante -= deltaTiempo;
        if(tiempoRestante<= 0){
            desactivar(objeto);
        }
    }

    //revierte la velocidad
    @Override
    public void desactivar(ObjetoJuego objeto) {
        //objeto
        if(objeto instanceof Paleta){
            ((Paleta)objeto).restaurarEstado();
            ((Paleta)objeto).establecerActivo(false);
            activo = false;
        }
        else if(objeto instanceof Pelota){
            ((Pelota)objeto).restaurarEstado();
            ((Pelota)objeto).establecerActivo(false);
            activo = false;
        }else{
            throw new IllegalArgumentException("el objeto debe ser una paleta o una pelota");
        }
        activo = false;
        tiempoRestante = duracion;

    }
}
