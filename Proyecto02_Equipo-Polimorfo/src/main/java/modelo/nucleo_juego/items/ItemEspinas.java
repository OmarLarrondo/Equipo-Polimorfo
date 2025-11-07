package modelo.nucleo_juego.items;

import java.io.ObjectOutput;

import modelo.editor.prototype.ConfigPaleta;
import modelo.nucleo_juego.patron_composite.ObjetoJuego;
import modelo.nucleo_juego.patron_composite.Paleta;

public class ItemEspinas implements Item{
    private int cantidadEspinas;
    private double duracion;
    private boolean activo;
    //AGREGAR AL DIAG
    private double tiempoRestante;
    private ConfigPaleta estadoOriginal;


    public ItemEspinas(int cantidadEspinas, double duracion, boolean activo) {
        this.cantidadEspinas = cantidadEspinas;
        this.duracion = duracion;
        this.activo = activo;
        this.tiempoRestante = duracion;

    }

    @Override
    public void aplicar(ObjetoJuego objeto) {
        if(activo )return;  //pa no volver aplicar si esta activo el efec,

        if(objeto instanceof Paleta paleta){
            //IMPORTANTE, GUARDAR EL ESTADO ANTES DE APLICAR CAMBIOS
            estadoOriginal = guardarEstadoOriginal(paleta);

            paleta.agregarEspina();
            paleta.establecerActivo(true);
            activo = true;
            tiempoRestante = duracion;
        }
        else{
            throw new IllegalArgumentException("Solo es posible agregar espinas a Paletas");
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
    
    @Override
    public void desactivar(ObjetoJuego objeto) {
        if(objeto instanceof Paleta paleta){
            restaurarEstadoOriginal(paleta);
            paleta.eliminarEspinas();
            paleta.establecerActivo(false);
            activo = false;
            tiempoRestante = duracion;
        }else{
            throw new IllegalArgumentException("Solo paletas, por favor.");
        }
    }


    public void actualizar(double deltaTiempo, ObjetoJuego objeto){
        if(!activo) return;

        tiempoRestante -= deltaTiempo;
        if(tiempoRestante<= 0){
            desactivar(objeto);
        }
    }
    // MÉTODOS DE ESTADO ORIGINAL
    
    
    private ConfigPaleta guardarEstadoOriginal(Paleta paleta) {
        ConfigPaleta copia = new ConfigPaleta();

        copia.setRapidez(paleta.obtenerVelocidad());
        copia.setAncho(paleta.obtenerAncho());
        copia.setAlto(paleta.obtenerAlto());
        copia.setColorPrimario(paleta.obtenerColor());
        copia.setCantidadEspinas(paleta.obtenerCantidadEspinas());

        return copia;
    }

    private void restaurarEstadoOriginal(Paleta paleta) {
        if (estadoOriginal == null) return;
        paleta.establecerVelocidad(estadoOriginal.getRapidez());
        paleta.setColor(estadoOriginal.getColor());
        paleta.setCantidadEspinas(estadoOriginal.getCantidadEspinas());
        paleta.setAncho(estadoOriginal.getAncho());
        paleta.setAlto(estadoOriginal.getAlto());
    }
}
