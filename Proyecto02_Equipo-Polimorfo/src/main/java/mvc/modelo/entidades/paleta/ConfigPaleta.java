package mvc.modelo.entidades.paleta;

import java.util.ArrayList;

import javafx.scene.paint.Color;
import mvc.modelo.enums.LadoHorizontal;

/**
 * Guarda los datos de una paleta controlada por el jugador o la IA en el juego Pong.
 *
 * <p>Esta clase almacena la configuracion y estado de una paleta en el juego,
 * incluyendo su posicion, dimensiones, velocidad, espinas y apariencia visual.</p>
 *
 * <p><b>Características principales:</b></p>
 * <ul>
 *   <li>Gestion de posicion y dimensiones de la paleta</li>
 *   <li>Sistema de espinas personalizables</li>
 *   <li>Configuracion de limites de movimiento</li>
 *   <li>Personalizacion de colores</li>
 * </ul>
 *
 * @author Equipo-polimorfo
 * @version 2.0
 */
public class ConfigPaleta extends mvc.modelo.entidades.ObjetoJuego {
    protected int colisionEnX;
    protected int centro;
    protected int ancho;
    protected int anchoLateral;
    protected int grosor;

    protected int limiteNorte;
    protected int limiteSur;
    protected int velocidad;

    protected ArrayList<Integer> puntosSuperioresEspinas;
    protected int intervaloEspina;

    protected LadoHorizontal ladoPantalla;
    protected Color colorPrimario;
    protected Color colorSecundario;

    public ConfigPaleta(
        int colisionEnX,
        int centro,
        int ancho,
        int grosor,
        int velocidad,
        int limiteNorte,
        int limiteSur,
        ArrayList<Integer> puntosSuperioresEspinas,
        LadoHorizontal lado,
        Color colorPrimario,
        Color colorSecundario
    ) throws IndexOutOfBoundsException, NullPointerException {
        super(
            lado == LadoHorizontal.DERECHA ? colisionEnX : colisionEnX - grosor,
            centro - (ancho % 2 == 1 ? ancho - 1 : ancho) / 2,
            grosor,
            ancho % 2 == 1 ? ancho - 1 : ancho
        );
        
        this.verificaEnteroPositivo(colisionEnX, "Valor de colision horizontal no positivo no es valido.");
        this.verificaEnteroPositivo(centro, "Valor de centro vertical no positivo no es valido.");
        this.verificaEnteroPositivo(ancho, "Valor de ancho no positivo no es valido.");
        this.verificaEnteroPositivo(grosor, "Valor de grosor no positivo no es valido.");
        this.verificaEnteroPositivo(velocidad, "Valor de velocidad no positivo no es valido.");
        this.verificaEnteroPositivo(limiteNorte, "Valor de limite norte no positivo no es valido.");
        this.verificaEnteroPositivo(limiteSur, "Valor de limite sur no positivo no es valido.");

        if(ancho%2 == 1)
            ancho --;

        if(centro - ancho/2 < limiteNorte)
            throw new IndexOutOfBoundsException("Cara superior del trampolin excede limite norte.");
        if(centro + ancho/2 > limiteSur)
            throw new IndexOutOfBoundsException("Cara inferior del trampolin excede limite sur.");
        if(lado == null)
            throw new NullPointerException("Lado de la pantalla no puede ser nulo.");
        if(colorPrimario == null | colorSecundario == null)
            throw new NullPointerException("Color nulo no es valido.");

        this.colisionEnX = colisionEnX;
        this.centro = centro;
        this.ancho = ancho;
        this.anchoLateral = ancho/2;
        this.grosor = grosor;

        this.limiteNorte = limiteNorte;
        this.limiteSur = limiteSur;
        this.velocidad = velocidad;

        this.establecerEspinas(puntosSuperioresEspinas);

        this.ladoPantalla = lado;
        this.colorPrimario = colorPrimario;
        this.colorSecundario = colorSecundario;
    }

    /**
     * Establece un punto horizontal para la colision de la plataforma con la pelota.
     * 
     * @param puntoColision distancia horizontal
     * @throws IndexOutOfBoundsException si la distancia no es positiva
     */
    public void establecerColisionEnX(int puntoColision)
        throws IndexOutOfBoundsException {
        this.verificaEnteroPositivo(puntoColision, "Valor de colision no positivo no es valido");

        this.colisionEnX = puntoColision;
    }

    /**
     * Establece un limite en la parte superior de la ventana de juego. Indica
     * el punto mas alto que puede alcanzar la paleta.
     *
     * @param limite posicion vertical del limite norte
     * @throws IndexOutOfBoundsException si el limite no es positivo o causa problemas
     *         de posicionamiento
     */
    public void establecerLimiteNorte(int limite)
        throws IndexOutOfBoundsException {
        this.verificaEnteroPositivo(limite, "Valor de limite norte no positivo no es valido.");

        if(this.centro - this.anchoLateral < limite)
            throw new IndexOutOfBoundsException(
                "El nuevo limite norte obligaria a la paleta a salir del area de juego."
            );

        this.limiteNorte = limite;
    }

    /**
     * Establece un limite en la parte inferior de la ventana de juego. Indica
     * el punto mas bajo que puede alcanzar la paleta.
     *
     * @param limite posicion vertical del limite sur
     * @throws IndexOutOfBoundsException si el limite no es positivo o causa problemas
     *         de posicionamiento
     */
    public void establecerLimiteSur(int limite)
        throws IndexOutOfBoundsException {
        this.verificaEnteroPositivo(limite, "Valor de limite sur no positivo no es valido.");

        if(this.centro + this.anchoLateral > limite)
            throw new IndexOutOfBoundsException(
                "El nuevo limite sur obligaria a la paleta a salir del area de juego."
            );
        if(limite <= this.limiteNorte)
            throw new IndexOutOfBoundsException(
                "El limite sur no puede estar por encima del limite norte."
            );

        this.limiteSur = limite;
    }

    /**
     * Establece el ancho (alto vertical) de la paleta.
     *
     * @param nuevoAncho el nuevo ancho de la paleta
     * @throws IndexOutOfBoundsException si el ancho no es positivo o causa problemas
     *         de posicionamiento
     */
    public void establecerAncho(int nuevoAncho)
        throws IndexOutOfBoundsException {
        this.verificaEnteroPositivo(nuevoAncho, "Valor de ancho no positivo no es valido.");

        if(nuevoAncho%2 == 1)
            nuevoAncho--;

        if(this.centro - nuevoAncho/2 < this.limiteNorte)
            throw new IndexOutOfBoundsException(
                "El nuevo ancho obligaria a la paleta a exceder el limite norte."
            );
        if(this.centro + nuevoAncho/2 > this.limiteSur)
            throw new IndexOutOfBoundsException(
                "El nuevo ancho obligaria a la paleta a exceder el limite sur."
            );

        this.ancho = nuevoAncho;
        this.anchoLateral = nuevoAncho/2;
    }

    /**
     * Establece el grosor (ancho horizontal) de la paleta.
     *
     * @param nuevoGrosor el nuevo grosor de la paleta
     * @throws IndexOutOfBoundsException si el grosor no es positivo
     */
    public void establecerGrosor(int nuevoGrosor)
        throws IndexOutOfBoundsException {
        this.verificaEnteroPositivo(nuevoGrosor, "Valor de grosor no positivo no es valido.");

        this.grosor = nuevoGrosor;
    }

    /**
     * Establece la posicion vertical del centro de la paleta.
     *
     * @param nuevoCentro la nueva posicion del centro
     * @throws IndexOutOfBoundsException si el centro no es positivo o causa problemas
     *         de posicionamiento
     */
    public void establecerCentro(int nuevoCentro)
        throws IndexOutOfBoundsException {
        this.verificaEnteroPositivo(nuevoCentro, "Valor de centro no positivo no es valido.");

        if(nuevoCentro - this.anchoLateral < this.limiteNorte)
            throw new IndexOutOfBoundsException("La nueva posicion excede el limite norte.");
        if(nuevoCentro + this.anchoLateral > this.limiteSur)
            throw new IndexOutOfBoundsException("La nueva posicion excede el limite sur.");

        this.centro = nuevoCentro;
    }

    /**
     * Establece la velocidad de movimiento de la paleta.
     *
     * @param nuevaVelocidad la nueva velocidad
     * @throws IndexOutOfBoundsException si la velocidad no es positiva
     */
    public void establecerVelocidad(int nuevaVelocidad)
        throws IndexOutOfBoundsException {
        this.verificaEnteroPositivo(nuevaVelocidad, "Valor de velocidad no positivo no es valido.");

        this.velocidad = nuevaVelocidad;
    }

    /**
     * Establece el intervalo entre espinas.
     *
     * @param nuevoIntervalo el nuevo intervalo entre espinas
     * @throws IndexOutOfBoundsException si el intervalo no es positivo
     */
    public void establecerIntervaloEspina(int nuevoIntervalo)
        throws IndexOutOfBoundsException {
        this.verificaEnteroPositivo(nuevoIntervalo, "Valor de intervalo no positivo no es valido.");

        this.intervaloEspina = nuevoIntervalo;
    }

    /**
     * Establece las posiciones de las espinas en la paleta.
     *
     * @param puntosSuperioresEspinas lista de posiciones Y de las espinas
     * @throws NullPointerException si la lista es nula o contiene valores nulos
     */
    public void establecerEspinas(ArrayList<Integer> puntosSuperioresEspinas)
        throws NullPointerException {
        if(puntosSuperioresEspinas == null)
            throw new NullPointerException("Lista de espinas no puede ser nula.");
        if(puntosSuperioresEspinas.contains(null))
            throw new NullPointerException("Lista contiene espinas nulas.");

        this.puntosSuperioresEspinas = puntosSuperioresEspinas;
        this.intervaloEspina = puntosSuperioresEspinas.isEmpty()
            ? 0
            : this.ancho / puntosSuperioresEspinas.size();
    }

    /**
     * Establece el lado de la pantalla donde se ubica la paleta.
     *
     * @param lado el lado de la pantalla (IZQUIERDA o DERECHA)
     * @throws NullPointerException si el lado es nulo
     */
    public void establecerLadoPantalla(LadoHorizontal lado)
        throws NullPointerException {
        if(lado == null)
            throw new NullPointerException("Lado de pantalla no puede ser nulo.");

        this.ladoPantalla = lado;
    }

    /**
     * Establece el color primario de la paleta.
     *
     * @param color el color primario
     * @throws NullPointerException si el color es nulo
     */
    public void establecerColorPrimario(Color color)
        throws NullPointerException {
        if(color == null)
            throw new NullPointerException("Color primario no puede ser nulo.");

        this.colorPrimario = color;
    }

    /**
     * Establece el color secundario de la paleta.
     *
     * @param color el color secundario
     * @throws NullPointerException si el color es nulo
     */
    public void establecerColorSecundario(Color color)
        throws NullPointerException {
        if(color == null)
            throw new NullPointerException("Color secundario no puede ser nulo.");

        this.colorSecundario = color;
    }

    /**
     * Obtiene la posicion horizontal de colision de la paleta.
     *
     * @return la posicion X de colision
     */
    public int obtenerColisionEnX() {
        return this.colisionEnX;
    }

    /**
     * Obtiene la posicion vertical del centro de la paleta.
     *
     * @return la posicion Y del centro
     */
    public int obtenerCentro() {
        return this.centro;
    }

    /**
     * Obtiene el limite norte (superior) de movimiento.
     *
     * @return el limite norte
     */
    public int obtenerLimiteNorte() {
        return this.limiteNorte;
    }

    /**
     * Obtiene el limite sur (inferior) de movimiento.
     *
     * @return el limite sur
     */
    public int obtenerLimiteSur() {
        return this.limiteSur;
    }

    /**
     * Obtiene el ancho (alto vertical) de la paleta.
     *
     * @return el ancho de la paleta
     */
    @Override
    public double obtenerAncho() {
        return (double)this.grosor;
    }

    /**
     * Obtiene la mitad del ancho de la paleta.
     *
     * @return la mitad del ancho
     */
    public int obtenerAnchoLateral() {
        return this.anchoLateral;
    }

    /**
     * Obtiene el grosor (ancho horizontal) de la paleta.
     *
     * @return el grosor
     */
    public int obtenerGrosor() {
        return this.grosor;
    }

    /**
     * Obtiene la velocidad de movimiento de la paleta.
     *
     * @return la velocidad
     */
    public double obtenerVelocidad() {
        return (double)this.velocidad;
    }

    /**
     * Verifica si la paleta tiene espinas.
     *
     * @return true si tiene espinas, false en caso contrario
     */
    public boolean tieneEspinas() {
        return !this.puntosSuperioresEspinas.isEmpty();
    }

    /**
     * Obtiene la lista de posiciones de las espinas.
     *
     * @return lista de posiciones Y de las espinas
     */
    public ArrayList<Integer> obtenerPuntosSuperioresEspinas() {
        return this.puntosSuperioresEspinas;
    }

    /**
     * Obtiene el intervalo entre espinas.
     *
     * @return el intervalo entre espinas
     */
    public int obtenerIntervaloEspina() {
        return this.intervaloEspina;
    }

    /**
     * Obtiene el lado de la pantalla donde se ubica la paleta.
     *
     * @return el lado de la pantalla
     */
    public LadoHorizontal obtenerLadoPantalla() {
        return this.ladoPantalla;
    }

    /**
     * Obtiene el color primario de la paleta.
     *
     * @return el color primario
     */
    public Color obtenerColorPrimario() {
        return this.colorPrimario;
    }

    /**
     * Obtiene el color secundario de la paleta.
     *
     * @return el color secundario
     */
    public Color obtenerColorSecundario() {
        return this.colorSecundario;
    }

    /**
     * Crea una copia de esta configuracion de paleta.
     *
     * @return una nueva instancia de ConfigPaleta con los mismos valores
     */
    public ConfigPaleta clonar() {
        return new ConfigPaleta(
            this.colisionEnX,
            this.centro,
            this.ancho,
            this.grosor,
            this.velocidad,
            this.limiteNorte,
            this.limiteSur,
            new ArrayList<>(this.puntosSuperioresEspinas),
            this.ladoPantalla,
            this.colorPrimario,
            this.colorSecundario
        );
    }

    /**
     * Verifica que un entero sea positivo.
     *
     * @param entero el entero a verificar
     * @param mensajeError el mensaje de error si la validacion falla
     * @throws IndexOutOfBoundsException si el entero no es positivo
     */
    protected void verificaEnteroPositivo(int entero, String mensajeError)
        throws IndexOutOfBoundsException {
        if(entero <= 0)
            throw new IndexOutOfBoundsException(mensajeError);
    }

    /**
     * Actualiza el estado de la paleta. Implementacion por defecto que no hace nada.
     * Las subclases deben sobreescribir este metodo para proporcionar funcionalidad.
     *
     * @param deltaTime el tiempo transcurrido desde la ultima actualizacion
     */
    @Override
    public void actualizar(double deltaTime) {
    }

    /**
     * Obtiene los limites rectangulares de la paleta.
     *
     * @return un rectangulo que representa los limites de la paleta
     */
    @Override
    public javafx.geometry.Rectangle2D obtenerLimites() {
        int puntoInicialX = (ladoPantalla == LadoHorizontal.DERECHA)
            ? this.colisionEnX
            : this.colisionEnX - this.grosor;

        return new javafx.geometry.Rectangle2D(
            puntoInicialX,
            this.centro - this.anchoLateral,
            this.grosor,
            this.ancho
        );
    }

    /**
     * Dibuja la paleta en el contexto grafico. Implementacion por defecto que no hace nada.
     * Las subclases deben sobreescribir este metodo para proporcionar renderizado.
     *
     * @param gc el contexto grafico donde dibujar
     */
    @Override
    public void dibujar(javafx.scene.canvas.GraphicsContext gc) {
    }
}
