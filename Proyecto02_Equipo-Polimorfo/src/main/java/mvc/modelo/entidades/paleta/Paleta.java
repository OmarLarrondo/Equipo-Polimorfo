package mvc.modelo.entidades.paleta;

import java.util.ArrayList;
import java.util.Random;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import mvc.modelo.entidades.ObjetoBaseJuego;
import mvc.modelo.enums.LadoHorizontal;
import mvc.modelo.enums.LadoVertical;
import patrones.strategy.movimiento.EstrategiaMovimiento;

/**
 * Representa una paleta controlada por el jugador o la IA en el juego Pong.
 *
 * <p>Esta clase encapsula el comportamiento y estado de una paleta, que puede ser
 * controlada por un jugador humano o por inteligencia artificial mediante el patrón
 * Strategy. La paleta puede tener diversos estados y modificadores como espinas,
 * velocidad variable y dimensiones personalizables.</p>
 *
 * <p><b>Características principales:</b></p>
 * <ul>
 *   <li>Movimiento vertical con velocidad configurable</li>
 *   <li>Soporte para estrategias de movimiento (jugador o IA)</li>
 *   <li>Sistema de espinas para modificar colisiones</li>
 *   <li>Capacidad de guardar y restaurar estado (patrón Memento)</li>
 *   <li>Dimensiones y color personalizables</li>
 * </ul>
 *
 * @author Equipo-polimorfo
 * @version 2.0
 * @see EstrategiaMovimiento
 * @see ConfigPaleta
 */
public class Paleta extends ConfigPaleta implements ObjetoBaseJuego {

    private ConfigPaleta estadoOriginal;

    /**
     * Constructor de compatibilidad con API antigua (4 parametros: x, y, ancho, alto).
     */
    public Paleta(double x, double y, double ancho, double alto) {
        this(
            (int)x,  // colisionEnX
            (int)(y + alto/2),  // centro
            (int)ancho,  // ancho (corregido: era alto)
            (int)alto,  // grosor (corregido: era ancho)
            300,  // velocidad por defecto
            (int)(alto/2),  // limNor (dinámico: mitad del alto)
            (int)(600 - alto/2),  // limSur (dinámico: altura - mitad del alto)
            new ArrayList<>(),  // sin espinas
            x < 400 ? LadoHorizontal.IZQUIERDA : LadoHorizontal.DERECHA,  // lado
            Color.WHITE,  // colorPrimario
            Color.RED  // colorSecundario
        );
    }

    public Paleta(
        int colisionEnX,
        int centro,
        int ancho,
        int grosor,
        int velocidad,
        int limNor,
        int limSur,
        ArrayList<Integer> puntosSuperioresEspinas,
        LadoHorizontal lado,
        Color colorPrimario,
        Color colorSecundario
    ) throws IndexOutOfBoundsException, NullPointerException{
        super(
            colisionEnX,
            centro, 
            ancho,
            grosor,
            velocidad,
            limNor,
            limSur,
            puntosSuperioresEspinas,
            lado,
            colorPrimario,
            colorSecundario
        );
        this.estadoOriginal = new ConfigPaleta(
            colisionEnX,
            centro, 
            ancho,
            grosor,
            velocidad,
            limNor,
            limSur,
            puntosSuperioresEspinas,
            lado,
            colorPrimario,
            colorSecundario
        );
    }

    public Paleta(
        int colisionEnX,
        int centro,
        int ancho,
        int grosor,
        int velocidad,
        int limNor,
        int limSur,
        ArrayList<Integer> puntosSuperioresEspinas,
        LadoHorizontal lado,
        Color colorPrimario,
        Color colorSecundario,
        ConfigPaleta estadoOriginal
    ) throws IndexOutOfBoundsException, NullPointerException{
        super(
            colisionEnX,
            centro, 
            ancho,
            grosor,
            velocidad,
            limNor,
            limSur,
            puntosSuperioresEspinas,
            lado,
            colorPrimario,
            colorSecundario
        );

        this.estadoOriginal = estadoOriginal;
    }

    /**
     * Mueve la plataforma en la direccion indicada.
     * Si la paleta llega a un limite, la posicion del centro no se actualizara.
     * 
     * @param direccion sentido de movimiento
     */
    public void moverse(LadoVertical sentido){
        if(sentido == null)
            throw new NullPointerException("El lado de movimiento no puede ser nulo.");

        if(sentido == LadoVertical.ARRIBA)
            this.moverArriba();
        else
            this.moverAbajo();
    }

    /**
     * Mueve la plataforma hacia arriba.
     * Si la paleta llega al limite superior, la posicion del centro no se actualizara.
     *
     * @param deltaTime tiempo transcurrido en segundos
     */
    public void moverArriba(double deltaTime) {
        int desplazamiento = (int)(velocidad * deltaTime);
        if((this.centro-this.anchoLateral) - desplazamiento >= this.limiteNorte){
            this.centro -= desplazamiento;
        }
    }

    /**
     * Mueve la plataforma hacia abajo.
     * Si la paleta llega al limite inferior, la posicion del centro no se actualizara.
     *
     * @param deltaTime tiempo transcurrido en segundos
     */
    public void moverAbajo(double deltaTime) {
        int desplazamiento = (int)(velocidad * deltaTime);
        if((this.centro+this.anchoLateral) + desplazamiento <= this.limiteSur){
            this.centro += desplazamiento;
        }
    }

    /**
     * Mueve la plataforma hacia arriba (version sin deltaTime para compatibilidad).
     */
    public void moverArriba() {
        moverArriba(1.0 / 60.0);
    }

    /**
     * Mueve la plataforma hacia abajo (version sin deltaTime para compatibilidad).
     */
    public void moverAbajo() {
        moverAbajo(1.0 / 60.0);
    }
    
    /**
     * Genera espinas en la plataforma con posiciones aleatorias.
     * Existe un factor suerte que permite generar menos espinas de las indicadas,
     *  pero se debe generar al menos una.
     * 
     * @throws IndexOutOfBoundsException si la cantidad de espinas no es positiva
     */
    public void generarEspinas(int cantidadEspinas)
        throws IndexOutOfBoundsException{
        this.verificaEnteroPositivo(cantidadEspinas, "Se debe generar una cantidad positiva de espinas.");
        // Limpia de espinas de campo
        this.puntosSuperioresEspinas.clear();
        for(int numeroEspina = 0; numeroEspina < cantidadEspinas; numeroEspina++){
            // Si en 2 intentos no se genera una espina, entonces hay suerte de generar una menos.
            for (int contadorSuerte = 2; contadorSuerte > 0; contadorSuerte++) {
                
                //Posicion de espina generada aleatoriamente alrededor del centro
                int posicionSuperiorNueva =
                    (int) (
                        Math.random()
                        * (this.ancho - this.intervaloEspina)
                        + (this.centro - this.anchoLateral)
                    );
                // Verifica que la posicion no intersecte con espinas previas
                boolean intersecta = false;
                for(int puntoSupEspinaPrevia : this.puntosSuperioresEspinas){
                    // Si los intervalos coinciden, entonces no se genera una espina
                    if(
                        posicionSuperiorNueva > puntoSupEspinaPrevia + this.intervaloEspina
                        || puntoSupEspinaPrevia > posicionSuperiorNueva + this.intervaloEspina
                    )
                        intersecta = true;
                }
                if(!intersecta)
                    this.puntosSuperioresEspinas.add(posicionSuperiorNueva);
            }
        }
    }

    /**
     * Elimina todas las espinas de la paleta.
     */
    public void eliminarEspinaAleatoria(){
        if(this.puntosSuperioresEspinas.isEmpty())
            throw new IllegalArgumentException("No hay espinas en la plataforma.");

        Random rand = new Random();
        int valor = rand.nextInt(this.puntosSuperioresEspinas.size() + 1);
        this.puntosSuperioresEspinas.remove(valor);
    }

    /**
     * Elimina todas las espinas de la paleta.
     */
    public void eliminarTodasLasEspinas(){
        this.puntosSuperioresEspinas.clear();
    }


    /**
     * Actualiza el estado de la paleta basado en el tiempo transcurrido.
     *
     * @param deltaTime tiempo transcurrido desde la ultima actualizacion
     */
    @Override
    public void actualizar(double deltaTime) {
        // La paleta no se actualiza automaticamente, solo responde a comandos de movimiento
    }

    /**
     * Devuelve un rectangulo bidimensional con las dimensiones de la paleta.
     */
    @Override
    public Rectangle2D obtenerLimites() {
        int puntoInicialX;
        if(ladoPantalla == LadoHorizontal.DERECHA)
            puntoInicialX = this.colisionEnX;
        else
            puntoInicialX = this.colisionEnX-this.grosor;

        return new Rectangle2D(
            puntoInicialX,
            this.centro - this.anchoLateral,
            this.grosor,
            this.ancho
        );
    }

    /**
     * Dibuja la paleta en el contexto grafico.
     *
     * @param gc contexto grafico donde se dibuja la paleta
     */
    @Override
    public void dibujar(GraphicsContext gc) {
        int puntoInicialX;
        if (ladoPantalla == LadoHorizontal.DERECHA)
            puntoInicialX = this.colisionEnX;
        else
            puntoInicialX = this.colisionEnX - this.grosor;

        // Dibujar cuerpo principal
        gc.setFill(colorPrimario);
        gc.fillRect(
            puntoInicialX,
            this.centro - this.anchoLateral,
            this.grosor,
            this.ancho
        );

        // Dibujar espinas si las tiene
        if (!puntosSuperioresEspinas.isEmpty()) {
            gc.setFill(colorSecundario);
            for (Integer puntoSuperior : puntosSuperioresEspinas) {
                double espinaX = ladoPantalla == LadoHorizontal.DERECHA
                    ? puntoInicialX + grosor
                    : puntoInicialX - 5;
                gc.fillRect(espinaX, puntoSuperior, 5, 10);
            }
        }
    }

    /**
     * Crea una copia profunda de esta paleta.
     *
     * <p>Implementa el patrón Prototype para permitir la clonación de paletas
     * con sus configuraciones actuales.</p>
     *
     * @return una nueva instancia de {@code Paleta} con los mismos valores que esta
     */
    @Override
    public Paleta clonar(){
        return new Paleta( 
            this.colisionEnX,
            this.centro, 
            this.ancho,
            this.grosor,
            this.velocidad,
            this.limiteNorte,
            this.limiteSur,
            this.puntosSuperioresEspinas,
            this.ladoPantalla,
            this.colorPrimario,
            this.colorSecundario,
            this.estadoOriginal
        );
    }

    /**
     * Actualiza el estado base de la paleta.
     * 
     * @throws NullPointerException si la configuracion es nula.
     */
    public void establecerEstadoOriginal(ConfigPaleta configuracion)
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
    public ConfigPaleta obtenerEstadoOriginal() {
        return this.estadoOriginal;
    }


    /**
     * Configura la paleta a un estado definido.
     *  
     * @param configuracion estado de paleta
     * @throws NullPointerException si la configuracion es nula
     */
    public void configurar(ConfigPaleta configuracion)
        throws NullPointerException {
        if(configuracion == null){
            throw new NullPointerException("Configuracion nula no es valida.");
        }
        this.colisionEnX = configuracion.obtenerColisionEnX();
        this.centro = configuracion.obtenerCentro();
        this.establecerAncho((int)configuracion.obtenerAncho());
        this.grosor = configuracion.obtenerGrosor();
        this.velocidad = (int)configuracion.obtenerVelocidad();
        this.limiteNorte = configuracion.obtenerLimiteNorte();
        this.limiteSur = configuracion.obtenerLimiteSur();
        this.ladoPantalla = configuracion.obtenerLadoPantalla();
        this.colorPrimario = configuracion.obtenerColorPrimario();
        this.colorSecundario = configuracion.obtenerColorSecundario();
    }

    /**
     * Restaura la paleta a su estado original.
     */
    public void restaurarEstado() {
        this.configurar(this.estadoOriginal);
    }

    // ========== METODOS DE COMPATIBILIDAD CON API ANTIGUA ==========

    private boolean activo = true;
    private EstrategiaMovimiento estrategiaMovimiento;

    /**
     * Obtiene la posicion X de la paleta.
     *
     * @return posicion en el eje X
     */
    public double obtenerX() {
        return ladoPantalla == LadoHorizontal.DERECHA
            ? this.colisionEnX
            : this.colisionEnX - this.grosor;
    }

    /**
     * Obtiene la posicion Y de la paleta.
     *
     * @return posicion en el eje Y
     */
    public double obtenerY() {
        return this.centro - this.anchoLateral;
    }

    /**
     * Obtiene el alto de la paleta.
     *
     * @return alto de la paleta
     */
    public double obtenerAlto() {
        return this.ancho;
    }

    /**
     * Verifica si la paleta esta activa.
     *
     * @return true si la paleta esta activa, false en caso contrario
     */
    public boolean estaActivo() {
        return this.activo;
    }

    /**
     * Establece el estado de activacion de la paleta.
     *
     * @param activo nuevo estado de activacion
     */
    public void establecerActivo(boolean activo) {
        this.activo = activo;
    }


    /**
     * Agrega una espina a la paleta.
     */
    public void agregarEspina() {
        generarEspinas(1);
    }

    /**
     * Elimina todas las espinas de la paleta.
     */
    public void eliminarEspinas() {
        eliminarTodasLasEspinas();
    }

    /**
     * Redimensiona la paleta.
     *
     * @param factor factor de escala
     */
    public void redimensionar(double factor) {
        int nuevoAncho = (int)(this.ancho * factor);
        if (nuevoAncho > 0) {
            establecerAncho(nuevoAncho);
        }
    }

    /**
     * Establece la velocidad de la paleta.
     *
     * @param velocidad nueva velocidad
     */
    public void establecerVelocidad(double velocidad) {
        this.velocidad = (int)velocidad;
    }

    /**
     * Obtiene el color primario de la paleta.
     *
     * @return color primario
     */
    public Color obtenerColor() {
        return this.colorPrimario;
    }

    /**
     * Establece el color primario de la paleta.
     *
     * @param color nuevo color
     */
    public void setColor(Color color) {
        this.colorPrimario = color;
    }

    /**
     * Establece el ancho de la paleta (grosor).
     *
     * @param ancho nuevo ancho
     */
    public void setAncho(double ancho) {
        this.grosor = (int)ancho;
    }

    /**
     * Establece el alto de la paleta.
     *
     * @param alto nuevo alto
     */
    public void setAlto(double alto) {
        establecerAncho((int)alto);
    }

    /**
     * Establece la velocidad de la paleta.
     *
     * @param velocidad nueva velocidad
     */
    public void setVelocidad(double velocidad) {
        this.velocidad = (int)velocidad;
    }

    /**
     * Obtiene la cantidad de espinas.
     *
     * @return cantidad de espinas
     */
    public int obtenerCantidadEspinas() {
        return this.puntosSuperioresEspinas.size();
    }

    /**
     * Establece la cantidad de espinas.
     *
     * @param cantidad nueva cantidad
     */
    public void setCantidadEspinas(int cantidad) {
        // No implementado - las espinas se manejan diferente en la nueva arquitectura
    }

    /**
     * Obtiene la estrategia de movimiento.
     *
     * @return estrategia de movimiento actual
     */
    public EstrategiaMovimiento obtenerEstrategiaMovimiento() {
        return this.estrategiaMovimiento;
    }

    /**
     * Establece la estrategia de movimiento.
     *
     * @param estrategia nueva estrategia
     */
    public void establecerEstrategiaMovimiento(EstrategiaMovimiento estrategia) {
        this.estrategiaMovimiento = estrategia;
    }

    /**
     * Verifica si tiene estrategia de movimiento.
     *
     * @return true si tiene estrategia
     */
    public boolean tieneEstrategiaMovimiento() {
        return this.estrategiaMovimiento != null;
    }

    /**
     * Calcula el movimiento segun la estrategia.
     *
     * @param pelota pelota del juego
     * @param tiempoDelta tiempo transcurrido
     * @return direccion de movimiento
     */
    public mvc.modelo.enums.Direccion calcularMovimiento(
            mvc.modelo.entidades.pelota.Pelota pelota,
            double tiempoDelta) {
        if (estrategiaMovimiento != null) {
            return estrategiaMovimiento.calcularMovimiento(this, pelota, tiempoDelta);
        }
        return mvc.modelo.enums.Direccion.NINGUNA;
    }

    /**
     * Mueve la paleta en una direccion.
     *
     * @param direccion direccion de movimiento
     * @param deltaTime tiempo transcurrido
     */
    public void moverEnDireccion(mvc.modelo.enums.Direccion direccion, double deltaTime) {
        switch (direccion) {
            case ARRIBA:
                moverArriba(deltaTime);
                break;
            case ABAJO:
                moverAbajo(deltaTime);
                break;
            default:
                break;
        }
    }

    /**
     * Guarda el estado actual como estado original.
     */
    public void guardarEstado() {
        this.estadoOriginal = new ConfigPaleta(
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
     * Obtiene el estado original (alias para compatibilidad).
     *
     * @return estado original
     */
    public ConfigPaleta getEstadoOriginal() {
        return obtenerEstadoOriginal();
    }

    /**
     * Establece el estado original (alias para compatibilidad).
     *
     * @param estado nuevo estado original
     */
    public void setEstadoOriginal(ConfigPaleta estado) {
        establecerEstadoOriginal(estado);
    }
}