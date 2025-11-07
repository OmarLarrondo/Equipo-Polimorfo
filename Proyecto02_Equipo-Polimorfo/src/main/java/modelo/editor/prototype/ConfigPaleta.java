package modelo.editor.prototype;

import javafx.scene.paint.Color;
import modelo.nucleo_juego.patron_composite.Paleta;

public class ConfigPaleta {
    private double ancho; 
    private double alto;
    private double rapidez;
    private Color colorPrimario;
    private Color colorSecundario;
    private int cantidadEspinas;


    public void aplicarA(Paleta paleta){
        //Aqui va su codigo
    }


    public double getAncho() {
        return ancho;
    }


    public double getAlto() {
        return alto;
    }


    public double getRapidez() {
        return rapidez;
    }


    public Color getColorPrimario() {
        return colorPrimario;
    }


    public Color getColorSecundario() {
        return colorSecundario;
    }


    public int getCantidadEspinas() {
        return cantidadEspinas;
    }


    public void setAncho(double ancho) {
        this.ancho = ancho;
    }


    public void setAlto(double alto) {
        this.alto = alto;
    }


    public void setRapidez(double rapidez) {
        this.rapidez = rapidez;
    }


    public void setColorPrimario(Color colorPrimario) {
        this.colorPrimario = colorPrimario;
    }


    public void setColorSecundario(Color colorSecundario) {
        this.colorSecundario = colorSecundario;
    }
    public Color getColor(){
        return colorPrimario;
    }


    public void setCantidadEspinas(int cantidadDeEspinas) {
        this.cantidadEspinas = cantidadDeEspinas;
    }
    

}
