package mvc.modelo.entidades.pelota;

import mvc.modelo.enums.LadoHorizontal;
import mvc.modelo.enums.LadoVertical;

/**
 * Representa la pelota del juego Pong.
 *
 * @author Equipo-polimorfo
 * @version 1.0
 */
public class ConfigPelota extends mvc.modelo.entidades.ObjetoJuego {

    protected static final double PI = Math.PI;

    protected int centroEnX;
    protected int centroEnY;
    protected int radio;
    protected int velocidad;
    protected int velocidadMaxima;
    protected double anguloDireccional;

    public ConfigPelota(
        int x,
        int y,
        int radio,
        int velocidadInicial,
        int velocidadMaxima,
        double anguloDireccional
    ) throws IndexOutOfBoundsException {
        super(x - radio, y - radio, 2.0 * radio, 2.0 * radio);
        
        this.verificaEnteroPositivo(x, "Valor de posicion horizontal no positivo no es valido.");
        this.verificaEnteroPositivo(y, "Valor de posicion vertical no positivo no es valido.");
        this.verificaEnteroPositivo(radio, "Valor de radio no positivo no es valido.");
        this.verificaEnteroPositivo(velocidadInicial, "Valor de velocidad no positivo no es valido.");
        this.verificaEnteroPositivo(velocidadMaxima, "Valor de velocidad maxima no positivo no es valido.");

        if(velocidadInicial > velocidadMaxima)
            throw new IndexOutOfBoundsException("La velocidad inicial no puede exceder la maxima.");
        if(velocidadMaxima > radio)
            throw new IndexOutOfBoundsException("La velocidad maxima no puede exceder el radio.");

        this.centroEnX = x;
        this.centroEnY = y;
        this.radio = radio;
        
        this.velocidad = velocidadInicial;
        this.velocidadMaxima = velocidadMaxima;
        
        this.anguloDireccional = anguloDireccional % (2*PI);
    }

    /**
     * Invierte el sentido horizontal de la pelota cambiando su angulo de direccion.
     */
    public void alternarSentidoHorizontal() {
        this.anguloDireccional = PI - this.anguloDireccional;
    }

    /**
     * Invierte el sentido vertical de la pelota cambiando su angulo de direccion.
     */
    public void alternarSentidoVertical() {
        this.anguloDireccional = - this.anguloDireccional;
    }

    /**
     * Establece la velocidad actual de la pelota.
     *
     * @param nuevaVelocidad la nueva velocidad a establecer
     * @throws IndexOutOfBoundsException si la velocidad no es positiva o excede la maxima
     */
    public void establecerVelocidadActual(int nuevaVelocidad) throws IndexOutOfBoundsException {
        this.verificaEnteroPositivo(
            nuevaVelocidad,
            "Valor de velocidad no positivo no es valido."
        );
        if(nuevaVelocidad > this.velocidadMaxima)
            throw new IndexOutOfBoundsException(
                "El valor excede la velocidad maxima permitida."
            );

        this.velocidad = nuevaVelocidad;
    }

    /**
     * Establece la velocidad maxima de la pelota.
     *
     * @param nuevaVelocidadMaxima la nueva velocidad maxima a establecer
     * @throws IndexOutOfBoundsException si la velocidad maxima no es positiva o excede el radio
     */
    public void establecerVelocidadMaxima(int nuevaVelocidadMaxima) throws IndexOutOfBoundsException {
        this.verificaEnteroPositivo(
            nuevaVelocidadMaxima,
            "Valor de velocidad maxima no positivo no es valido."
        );
        if(nuevaVelocidadMaxima > this.radio)
            throw new IndexOutOfBoundsException(
                "El valor excede el radio, esto podria generar problemas de colision."
            );
        if(nuevaVelocidadMaxima < this.velocidad)
            throw new IndexOutOfBoundsException(
                "La velocidad maxima no puede ser menor que la velocidad actual."
            );

        this.velocidadMaxima = nuevaVelocidadMaxima;
    }

    /**
     * Establece el radio de la pelota.
     *
     * @param nuevoRadio el nuevo radio a establecer
     * @throws IndexOutOfBoundsException si el radio no es positivo o es menor que la velocidad maxima
     */
    public void establecerRadio(int nuevoRadio) throws IndexOutOfBoundsException {
        this.verificaEnteroPositivo(
            nuevoRadio,
            "Valor de radio no positivo no es valido."
        );
        if(nuevoRadio < this.velocidadMaxima)
            throw new IndexOutOfBoundsException(
                "El radio no puede ser menor que la velocidad maxima."
            );

        this.radio = nuevoRadio;
    }

    /**
     * Establece la coordenada X del centro de la pelota.
     *
     * @param nuevoCentroEnX la nueva coordenada X del centro
     * @throws IndexOutOfBoundsException si el valor no es positivo
     */
    public void establecerCentroEnX(int nuevoCentroEnX) throws IndexOutOfBoundsException {
        this.verificaEnteroPositivo(
            nuevoCentroEnX,
            "Valor de posicion horizontal no positivo no es valido."
        );

        this.centroEnX = nuevoCentroEnX;
    }

    /**
     * Establece la coordenada Y del centro de la pelota.
     *
     * @param nuevoCentroEnY la nueva coordenada Y del centro
     * @throws IndexOutOfBoundsException si el valor no es positivo
     */
    public void establecerCentroEnY(int nuevoCentroEnY) throws IndexOutOfBoundsException {
        this.verificaEnteroPositivo(
            nuevoCentroEnY,
            "Valor de posicion vertical no positivo no es valido."
        );

        this.centroEnY = nuevoCentroEnY;
    }

    /**
     * Establece la posicion del centro de la pelota.
     *
     * @param nuevoCentroEnX la nueva coordenada X del centro
     * @param nuevoCentroEnY la nueva coordenada Y del centro
     * @throws IndexOutOfBoundsException si alguno de los valores no es positivo
     */
    public void establecerPosicionCentro(int nuevoCentroEnX, int nuevoCentroEnY) throws IndexOutOfBoundsException {
        this.verificaEnteroPositivo(nuevoCentroEnX, "Valor de posicion horizontal no positivo no es valido.");
        this.verificaEnteroPositivo(nuevoCentroEnY, "Valor de posicion vertical no positivo no es valido.");

        this.centroEnX = nuevoCentroEnX;
        this.centroEnY = nuevoCentroEnY;
    }

    /**
     * Establece el angulo direccional de la pelota.
     *
     * @param nuevoAnguloDireccional el nuevo angulo direccional en radianes
     * @throws IndexOutOfBoundsException si el angulo esta fuera del rango valido
     */
    public void establecerAnguloDireccional(double nuevoAnguloDireccional) throws IndexOutOfBoundsException {
        if(nuevoAnguloDireccional < 0 || nuevoAnguloDireccional >= 2*PI)
            throw new IndexOutOfBoundsException(
                "El angulo direccional debe estar en el rango [0, 2*PI)."
            );

        this.anguloDireccional = nuevoAnguloDireccional;
    }

    /**
     * Obtiene la velocidad actual de la pelota.
     *
     * @return la velocidad actual
     */
    public double obtenerVelocidad() {
        return (double)this.velocidad;
    }

    /**
     * Obtiene la componente X de la velocidad.
     *
     * @return la velocidad en el eje X
     */
    public double obtenerVelocidadX() {
        return this.velocidad * Math.cos(this.anguloDireccional);
    }

    /**
     * Obtiene la componente Y de la velocidad.
     *
     * @return la velocidad en el eje Y
     */
    public double obtenerVelocidadY() {
        return this.velocidad * Math.sin(this.anguloDireccional);
    }

    /**
     * Obtiene la velocidad maxima de la pelota.
     *
     * @return la velocidad maxima
     */
    public int obtenerVelocidadMaxima() {
        return this.velocidadMaxima;
    }

    /**
     * Obtiene el radio de la pelota.
     *
     * @return el radio
     */
    public int obtenerRadio() {
        return this.radio;
    }

    /**
     * Obtiene la coordenada X del centro de la pelota.
     *
     * @return la coordenada X del centro
     */
    public int obtenerCentroEnX() {
        return this.centroEnX;
    }

    /**
     * Obtiene la coordenada Y del centro de la pelota.
     *
     * @return la coordenada Y del centro
     */
    public int obtenerCentroEnY() {
        return this.centroEnY;
    }

    /**
     * Obtiene el angulo direccional de la pelota.
     *
     * @return el angulo direccional en radianes
     */
    public double obtenerAnguloDireccional() {
        return this.anguloDireccional;
    }

    /**
     * Obtiene el sentido horizontal de movimiento de la pelota.
     *
     * @return DERECHA si se mueve hacia la derecha, IZQUIERDA en caso contrario
     */
    public LadoHorizontal obtenerSentidoHorizontal() {
        double velocidadX = this.obtenerVelocidadX();
        return velocidadX > 0
            ? LadoHorizontal.DERECHA
            : LadoHorizontal.IZQUIERDA;
    }

    /**
     * Obtiene el sentido vertical de movimiento de la pelota.
     *
     * @return ARRIBA si se mueve hacia arriba, ABAJO en caso contrario
     */
    public LadoVertical obtenerSentidoVertical() {
        double velocidadY = this.obtenerVelocidadY();
        return velocidadY < 0
            ? LadoVertical.ARRIBA
            : LadoVertical.ABAJO;
    }

    /**
     * Verifica que un entero sea positivo.
     *
     * @param entero el entero a verificar
     * @param mensajeError el mensaje de error si la validacion falla
     * @throws IndexOutOfBoundsException si el entero no es positivo
     */
    protected void verificaEnteroPositivo(int entero, String mensajeError) throws IndexOutOfBoundsException {
        if(entero <= 0)
            throw new IndexOutOfBoundsException(mensajeError);
    }

    /**
     * Actualiza el estado de la pelota. Implementacion por defecto que no hace nada.
     * Las subclases deben sobreescribir este metodo para proporcionar funcionalidad.
     *
     * @param deltaTime el tiempo transcurrido desde la ultima actualizacion
     */
    @Override
    public void actualizar(double deltaTime) {
    }

    /**
     * Obtiene los limites rectangulares de la pelota.
     *
     * @return un rectangulo que representa los limites de la pelota
     */
    @Override
    public javafx.geometry.Rectangle2D obtenerLimites() {
        return new javafx.geometry.Rectangle2D(
            this.centroEnX - this.radio,
            this.centroEnY - this.radio,
            2.0 * this.radio,
            2.0 * this.radio
        );
    }

    /**
     * Dibuja la pelota en el contexto grafico. Implementacion por defecto que no hace nada.
     * Las subclases deben sobreescribir este metodo para proporcionar renderizado.
     *
     * @param gc el contexto grafico donde dibujar
     */
    @Override
    public void dibujar(javafx.scene.canvas.GraphicsContext gc) {
    }
}
