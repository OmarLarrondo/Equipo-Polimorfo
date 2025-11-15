package patrones.prototype;

import javafx.scene.paint.Color;
import mvc.modelo.entidades.Paleta;

/**
 * Clase que encapsula la configuración visual y funcional de
 * una {@link Paleta)
 *
 * <p>Las instancias de esta clase permiten almacenar atributos 
 * predeterminados (ancho, alto, color, velocidad, etc.) que 
 * posteriormente pueden aplicarse a una paleta concreta mediante 
 * el método {@link #aplicarA(Paleta)}.</p>
 *
 * <p>Actúa como una plantilla de configuración que puede
 * reutilizarse para generar múltiples instancias de paletas
 * </p>
 *
 * @author Equipo-polimorfo
 * @version  1.0
 */
public class ConfigPaleta {
    /** Anchura de la paleta en unidades del juego.(Adaptativo) */
    private double ancho;

    /** Altura de la paleta en unidades del juego.(Adaptativo) */
    private double alto;

    /** Velocidad de movimiento horizontal/vertical de la paleta. */
    private double rapidez;

    /** Color principal utilizado para la representación gráfica. */
    private Color colorPrimario;

    /** Color secundario para detalles visuales. */
    private Color colorSecundario;

    /** Cantidad de espinas o pinchos aplicados al borde de la paleta. */
    private int cantidadEspinas;

    /**
     * Aplica esta configuración a una instancia específica de
     * {@link Paleta}.
     *
     * <p>Este método modifica directamente los atributos de la
     * paleta proporcionada, ajustando sus dimensiones, velocidad 
     * y apariencia visual según los parámetros almacenados en la 
     * configuración.</p>
     *
     * @param paleta la instancia de {@link Paleta} a la cual se 
     *               aplicará la configuración actual.
     */
    public void aplicarA(Paleta paleta) {
        paleta.setAncho(ancho);
        paleta.setAlto(alto);
        paleta.setVelocidad(rapidez);
        paleta.setColor(colorPrimario);
        paleta.setCantidadEspinas(cantidadEspinas);
    }

    //GETTERS

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


    //SETTERS
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
