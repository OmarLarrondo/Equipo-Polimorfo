package modelo.nucleo_juego.patron_composite;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import modelo.editor.prototype.ConfigPaleta;
import modelo.gestion_ia.patron_strategy.estrategiasMovimientos.EstrategiaMovimiento;

/**
 * Representa una paleta controlada por el jugador o la IA.
 *
 * @author Equipo-polimorfo
 * @version 1.0
 */
public class Paleta extends ObjetoJuego {

    private double velocidad;
    private Color color;
    private boolean tieneEspinas;
    private EstrategiaMovimiento estrategiaMovimiento;
    private ConfigPaleta estadoOriginal;
    private int cantidadEspinas;

    public Paleta(double x, double y, double ancho, double alto) {
        super(x, y, ancho, alto);
        this.velocidad = 400;
    }

    //
    @Override
    //checar como funciona la duracion del tiempoes
    public void actualizar(double deltaTime) {
        //tiempoRestante - deltaTime;
        //if(tiempoRestante<=0) restaurarEstado();
    }

    public void moverArriba(double deltaTime) {
        y -= velocidad * deltaTime;
    }

    public void moverAbajo(double deltaTime) {
        y += velocidad * deltaTime;
    }

    public double obtenerVelocidad() {
        return velocidad;
    }

    public void establecerVelocidad(double velocidad) {
        this.velocidad = velocidad;
    }

    //Puede que se agregue aleatoriamente en ella, random,
    //No se como hacer esto
    public void agregarEspina(){
        //aqui va su codigo
    }

    public void eliminarEspinas(){
        //aqui va su codig     
    }

    //CUANDO PIERDA, SE DEBE CENTRAR AL ORIGEN
    public void redimensionar(double nuevoAncho){
        //aqui va su codigo
    }


    @Override
    public Rectangle2D obtenerLimites() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'obtenerLimites'");
    }

    @Override
    public void dibujar(GraphicsContext gc) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'dibujar'");
    }
    public Paleta clonar(){
        //aqui va su codigo
        return null;

    }

    public void guardarEstado(){
        //aqui va us codig 
    }
    public void restaurarEstado(){
        //aqui va su codig o
        //falta hacer todo original
        activo = false;
    }
    public void setCantidadEspinas(int espinas){
        cantidadEspinas += espinas;

    }

    public ConfigPaleta getEstadoOriginal() {
        ConfigPaleta estado = new ConfigPaleta();
        estado.setRapidez(this.velocidad);
        estado.setCantidadEspinas(this.cantidadEspinas);
        estado.setAncho(this.ancho);
        estado.setAlto(this.alto);
        return estado;
    }

    public void setEstadoOriginal(ConfigPaleta estado) {
        this.velocidad = estado.getRapidez();
        this.cantidadEspinas = estado.getCantidadEspinas();
        this.ancho = estado.getAncho();
        this.alto = estado.getAlto();
    }

    public Color obtenerColor(){
        return color;
    }
    public int obtenerCantidadEspinas(){
        return cantidadEspinas;
    }
    public void setColor(Color color){
        this.color = color;
    }

    public void setAncho(double ancho){
        this.ancho = ancho;
    }

    public void setAlto(double alto){
        this.alto = alto;
    }
    public void setVelocidad(double velocidad){
        this.velocidad = velocidad;
    }
}
