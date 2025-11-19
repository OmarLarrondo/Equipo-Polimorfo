package mvc.modelo.entidades.pelota;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import mvc.modelo.entidades.ObjetoBaseJuego;
import mvc.modelo.enums.LadoHorizontal;

/**
 * Representa la pelota del juego Pong.
 *
 * @author Equipo-polimorfo
 * @version 1.0
 */
public class Pelota extends ConfigPelota implements ObjetoBaseJuego {

    private ConfigPelota estadoOriginal;
    private boolean activo;
    
    public Pelota(
        int centroEnX,
        int centroEnY,
        int radio,
        int velocidadInicial,
        int velocidadMaxima,
        double anguloDireccional
    ) {
        super(
            centroEnX,
            centroEnY,
            radio,
            velocidadInicial,
            velocidadMaxima,
            anguloDireccional
        );
        this.estadoOriginal = new ConfigPelota(
            centroEnX,
            centroEnY,
            radio,
            velocidadInicial,
            velocidadMaxima,
            anguloDireccional
        );
        this.activo = true;
    }

    public Pelota(
        int centroEnX,
        int centroEnY,
        int radio,
        int velocidadInicial,
        int velocidadMaxima,
        double anguloDireccional,
        ConfigPelota estadoOriginal
    ) {
        super(
            centroEnX,
            centroEnY,
            radio,
            velocidadInicial,
            velocidadMaxima,
            anguloDireccional
        );
        this.estadoOriginal = estadoOriginal;
        this.activo = true;
    }

    /**
     * Calcula las velocidades horizontal y vertical, y las suma a las posiciones en x,y
     *  respectivamente.
     */
    public void moverse() {
        this.centroEnX += Math.cos(velocidad);
        this.centroEnY += Math.sin(velocidad);
    }

    /**
     * Genera un angulo aleatorio con respecto al eje x, con una desviacion uniforme
     *  de entre -pi/8 y pi/8, con respecto al sentido indicado.
     * Sirve para dar dinamismo al comienzo de una ronda, siendo su direccion no siempre igual.
     */
    public void inicializaDireccionLateral(LadoHorizontal sentido){
        if(sentido == LadoHorizontal.DERECHA)
            this.anguloDireccional = ( ((PI/4) * Math.random()) + (7*PI/8) ) % (2*PI);
        else
            this.anguloDireccional = ((PI/4) * Math.random()) - (PI*3/8);
    }

    /**
     * Actualiza el estado base de la pelota.
     * 
     * @throws NullPointerException si la configuracion es nula.
     */
    public void establecerEstadoOriginal(ConfigPelota configuracion)
        throws NullPointerException {
        if(configuracion == null){
            throw new NullPointerException("Configuracion nula no es valida.");
        }

        this.estadoOriginal = configuracion;
    }

    /**
     * Devuelve el estado original de la pelota.
     * 
     * @return configuracion original de la pelota
     */
    public ConfigPelota obtenerEstadoOriginal(){
        return this.estadoOriginal;
    }

    /**
     * Configura la pelota a un estado definido.
     * 
     * @param configuracion estado de pelota
     * @throws NullPointerException si la configuracion es nula
     */
    public void configurar(ConfigPelota configuracion)
        throws NullPointerException {
        if(configuracion == null){
            throw new NullPointerException("Configuracion nula no es valida.");
        }
        this.centroEnX = configuracion.obtenerCentroEnX();
        this.centroEnY = configuracion.obtenerCentroEnY();
        this.radio = configuracion.obtenerRadio();
        this.velocidad = (int)configuracion.obtenerVelocidad();
        this.velocidadMaxima = configuracion.obtenerVelocidadMaxima();
        this.anguloDireccional = configuracion.obtenerAnguloDireccional();
    }

    /**
     * Restaura la paleta a su estado original.
     */
    public void restaurarEstado() {
        this.configurar(this.estadoOriginal);
    }

    /**
     * Actualiza la posicion de la pelota basado en el tiempo transcurrido.
     *
     * @param deltaTime tiempo transcurrido desde la ultima actualizacion
     */
    @Override
    public void actualizar(double deltaTime) {
        if (!activo) return;

        double velocidadX = Math.cos(anguloDireccional) * velocidad;
        double velocidadY = -Math.sin(anguloDireccional) * velocidad;

        this.centroEnX += (int)(velocidadX * deltaTime);
        this.centroEnY += (int)(velocidadY * deltaTime);
    }

    /**
     * Devuelve un cuadrado bidimensional con las dimensiones de la pelota.
     */
    @Override
    public Rectangle2D obtenerLimites() {
        return new Rectangle2D(
            this.centroEnX - this.radio,
            this.centroEnY - this.radio,
            2 * this.radio,
            2 * this.radio
        );
    }

    /**
     * Dibuja la pelota en el contexto grafico.
     *
     * @param gc contexto grafico donde se dibuja la pelota
     */
    @Override
    public void dibujar(GraphicsContext gc) {
        if (!activo) return;

        gc.setFill(javafx.scene.paint.Color.WHITE);
        gc.fillOval(
            this.centroEnX - this.radio,
            this.centroEnY - this.radio,
            2 * this.radio,
            2 * this.radio
        );
    }

    // ========== METODOS DE COMPATIBILIDAD CON API ANTIGUA ==========

    /**
     * Establece la componente horizontal de la velocidad.
     * Actualiza el angulo direccional y la magnitud de velocidad.
     *
     * @param velocidadX nueva velocidad en el eje X
     */
    public void establecerVelocidadX(double velocidadX) {
        double velocidadY = obtenerVelocidadY();
        this.velocidad = (int)Math.sqrt(velocidadX * velocidadX + velocidadY * velocidadY);
        this.anguloDireccional = Math.atan2(velocidadY, velocidadX);
    }

    /**
     * Establece la componente vertical de la velocidad.
     * Actualiza el angulo direccional y la magnitud de velocidad.
     *
     * @param velocidadY nueva velocidad en el eje Y
     */
    public void establecerVelocidadY(double velocidadY) {
        double velocidadX = obtenerVelocidadX();
        this.velocidad = (int)Math.sqrt(velocidadX * velocidadX + velocidadY * velocidadY);
        this.anguloDireccional = Math.atan2(velocidadY, velocidadX);
    }

    /**
     * Invierte la componente horizontal de la velocidad.
     */
    public void invertirX() {
        alternarSentidoHorizontal();
    }

    /**
     * Invierte la componente vertical de la velocidad.
     */
    public void invertirY() {
        alternarSentidoVertical();
    }

    /**
     * Reinicia la pelota a su estado inicial.
     */
    public void reiniciar() {
        restaurarEstado();
    }

    /**
     * Establece la velocidad general de la pelota.
     *
     * @param velocidad nueva velocidad
     */
    public void establecerVelocidadGeneral(double velocidad) {
        this.velocidad = (int)velocidad;
    }

    /**
     * Obtiene la posicion X de la pelota.
     *
     * @return posicion en el eje X
     */
    public double obtenerX() {
        return this.centroEnX - this.radio;
    }

    /**
     * Obtiene la posicion Y de la pelota.
     *
     * @return posicion en el eje Y
     */
    public double obtenerY() {
        return this.centroEnY - this.radio;
    }

    /**
     * Obtiene el ancho de la pelota (diametro).
     *
     * @return ancho de la pelota
     */
    public double obtenerAncho() {
        return 2 * this.radio;
    }

    /**
     * Obtiene el alto de la pelota (diametro).
     *
     * @return alto de la pelota
     */
    public double obtenerAlto() {
        return 2 * this.radio;
    }

    /**
     * Verifica si la pelota esta activa.
     *
     * @return true si la pelota esta activa, false en caso contrario
     */
    public boolean estaActivo() {
        return this.activo;
    }

    /**
     * Establece el estado de activacion de la pelota.
     *
     * @param activo nuevo estado de activacion
     */
    public void establecerActivo(boolean activo) {
        this.activo = activo;
    }

    /**
     * Crea una copia de la pelota.
     *
     * @return copia de la pelota
     */
    public Pelota clonar() {
        Pelota copia = new Pelota(
            this.centroEnX,
            this.centroEnY,
            this.radio,
            this.velocidad,
            this.velocidadMaxima,
            this.anguloDireccional,
            this.estadoOriginal
        );
        copia.activo = this.activo;
        return copia;
    }
}
