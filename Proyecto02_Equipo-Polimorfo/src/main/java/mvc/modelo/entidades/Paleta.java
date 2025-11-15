package mvc.modelo.entidades;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import patrones.prototype.ConfigPaleta;
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
public class Paleta extends ObjetoJuego {

    private double velocidad;
    private Color color;
    private boolean tieneEspinas;
    private EstrategiaMovimiento estrategiaMovimiento;
    private ConfigPaleta estadoOriginal;
    private int cantidadEspinas;

    /**
     * Construye una nueva paleta en la posición y dimensiones especificadas.
     *
     * @param x     coordenada X de la esquina superior izquierda
     * @param y     coordenada Y de la esquina superior izquierda
     * @param ancho ancho de la paleta en píxeles
     * @param alto  alto de la paleta en píxeles
     */
    public Paleta(double x, double y, double ancho, double alto) {
        super(x, y, ancho, alto);
        this.velocidad = 400;
    }

    /**
     * Actualiza el estado de la paleta en cada frame del juego.
     *
     * <p>Este método se invoca en cada iteración del game loop para actualizar
     * temporizadores, efectos temporales, y otros estados que dependen del tiempo.</p>
     *
     * @param deltaTime tiempo transcurrido desde el último frame en segundos
     */
    @Override
    public void actualizar(double deltaTime) {
        // Por implementar: actualizar temporizadores de efectos temporales
    }

    /**
     * Mueve la paleta hacia arriba con su velocidad actual.
     *
     * @param deltaTime tiempo transcurrido en segundos para calcular el desplazamiento
     */
    public void moverArriba(double deltaTime) {
        y -= velocidad * deltaTime;
    }

    /**
     * Mueve la paleta hacia abajo con su velocidad actual.
     *
     * @param deltaTime tiempo transcurrido en segundos para calcular el desplazamiento
     */
    public void moverAbajo(double deltaTime) {
        y += velocidad * deltaTime;
    }

    /**
     * Obtiene la velocidad actual de movimiento de la paleta.
     *
     * @return la velocidad en píxeles por segundo
     */
    public double obtenerVelocidad() {
        return velocidad;
    }

    /**
     * Establece la velocidad de movimiento de la paleta.
     *
     * @param velocidad la nueva velocidad en píxeles por segundo
     */
    public void establecerVelocidad(double velocidad) {
        this.velocidad = velocidad;
    }

    /**
     * Agrega una espina a la paleta.
     *
     * <p>Las espinas modifican el comportamiento de las colisiones con la pelota,
     * permitiendo efectos especiales como cambios de dirección o velocidad.</p>
     */
    public void agregarEspina(){
        cantidadEspinas = cantidadEspinas + 1;
        tieneEspinas = true;
    }

    /**
     * Elimina todas las espinas de la paleta.
     *
     * <p>Restaura el comportamiento normal de colisión de la paleta.</p>
     */
    public void eliminarEspinas(){
        cantidadEspinas = 0;
        tieneEspinas = false;
    }

    /**
     * Redimensiona el ancho de la paleta.
     *
     * <p>Este método permite modificar dinámicamente el tamaño de la paleta
     * durante el juego, típicamente como resultado de power-ups.</p>
     *
     * @param nuevoAncho el nuevo ancho de la paleta en píxeles
     */
    public void redimensionar(double nuevoAncho){
        this.ancho = nuevoAncho;
    }


    /**
     * Obtiene el rectángulo delimitador de la paleta para detección de colisiones.
     *
     * @return un {@code Rectangle2D} que representa los límites de la paleta
     */
    @Override
    public Rectangle2D obtenerLimites() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'obtenerLimites'");
    }

    /**
     * Dibuja la paleta en el contexto gráfico especificado.
     *
     * @param gc el contexto gráfico donde se dibujará la paleta
     */
    @Override
    public void dibujar(GraphicsContext gc) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'dibujar'");
    }

    /**
     * Crea una copia profunda de esta paleta.
     *
     * <p>Implementa el patrón Prototype para permitir la clonación de paletas
     * con sus configuraciones actuales.</p>
     *
     * @return una nueva instancia de {@code Paleta} con los mismos valores que esta
     */
    public Paleta clonar(){
        //aqui va su codigo
        return null;

    }

    /**
     * Guarda el estado actual de la paleta.
     *
     * <p>Implementa el patrón Memento para permitir la restauración posterior
     * del estado original de la paleta.</p>
     */
    public void guardarEstado(){
        estadoOriginal = getEstadoOriginal();
    }

    /**
     * Restaura la paleta a su estado previamente guardado.
     *
     * <p>Si se ha guardado un estado previamente, restaura todas las propiedades
     * de la paleta a esos valores y la desactiva.</p>
     */
    public void restaurarEstado(){
        if(estadoOriginal != null){
            setEstadoOriginal(estadoOriginal);
        }
        activo = false;
    }

    /**
     * Modifica la cantidad de espinas de la paleta.
     *
     * @param espinas número de espinas a agregar (puede ser negativo para reducir)
     */
    public void setCantidadEspinas(int espinas){
        cantidadEspinas += espinas;

    }

    /**
     * Obtiene una configuración que representa el estado actual de la paleta.
     *
     * <p>Crea un objeto {@code ConfigPaleta} con todos los valores actuales de
     * velocidad, espinas, ancho y alto.</p>
     *
     * @return una nueva configuración con el estado actual de la paleta
     */
    public ConfigPaleta getEstadoOriginal() {
        ConfigPaleta estado = new ConfigPaleta();
        estado.setRapidez(this.velocidad);
        estado.setCantidadEspinas(this.cantidadEspinas);
        estado.setAncho(this.ancho);
        estado.setAlto(this.alto);
        return estado;
    }

    /**
     * Establece el estado de la paleta desde una configuración.
     *
     * <p>Restaura todas las propiedades de la paleta desde un objeto
     * {@code ConfigPaleta} previamente guardado.</p>
     *
     * @param estado la configuración con los valores a restaurar
     */
    public void setEstadoOriginal(ConfigPaleta estado) {
        this.velocidad = estado.getRapidez();
        this.cantidadEspinas = estado.getCantidadEspinas();
        this.ancho = estado.getAncho();
        this.alto = estado.getAlto();
    }

    /**
     * Obtiene el color actual de la paleta.
     *
     * @return el color de la paleta
     */
    public Color obtenerColor(){
        return color;
    }

    /**
     * Obtiene la cantidad de espinas que tiene la paleta actualmente.
     *
     * @return número de espinas
     */
    public int obtenerCantidadEspinas(){
        return cantidadEspinas;
    }

    /**
     * Obtiene la estrategia de movimiento actualmente asignada a esta paleta.
     *
     * <p>La estrategia de movimiento determina cómo se comporta la paleta durante el juego.
     * Una paleta puede tener:</p>
     * <ul>
     *   <li><b>Estrategia de IA:</b> Si la paleta es controlada por la inteligencia artificial</li>
     *   <li><b>Estrategia de jugador:</b> Si la paleta es controlada por un jugador humano</li>
     *   <li><b>Ninguna estrategia (null):</b> Si aún no se ha configurado</li>
     * </ul>
     *
     * @return la estrategia de movimiento actual, o {@code null} si no se ha establecido ninguna
     */
    public EstrategiaMovimiento obtenerEstrategiaMovimiento() {
        return estrategiaMovimiento;
    }

    /**
     * Establece la estrategia de movimiento para esta paleta.
     *
     * <p>Este método permite cambiar dinámicamente el comportamiento de la paleta,
     * lo cual es útil para:</p>
     * <ul>
     *   <li>Asignar una estrategia de IA con un nivel de dificultad específico</li>
     *   <li>Cambiar entre control manual y automático</li>
     *   <li>Modificar el nivel de dificultad de la IA durante el juego</li>
     * </ul>
     *
     * <p><b>Ejemplo de uso:</b></p>
     * <pre>
     * // Asignar IA de nivel 7
     * FabricaIA.obtenerInstancia()
     *     .crearIA(DificultadIA.NIVEL_7)
     *     .forEach(paleta::establecerEstrategiaMovimiento);
     * </pre>
     *
     * @param estrategia la nueva estrategia de movimiento. Puede ser {@code null} para remover la estrategia.
     */
    public void establecerEstrategiaMovimiento(final EstrategiaMovimiento estrategia) {
        this.estrategiaMovimiento = estrategia;
    }

    /**
     * Calcula la dirección de movimiento basándose en la estrategia actual.
     *
     * <p>Este método delega el cálculo del movimiento a la estrategia de movimiento configurada.
     * Es particularmente útil para paletas controladas por IA.</p>
     *
     * <p>Si no hay estrategia configurada, retorna {@code null}. El código llamador debe
     * manejar este caso apropiadamente.</p>
     *
     * <p><b>Ejemplo de uso en el game loop:</b></p>
     * <pre>
     * // Para una paleta controlada por IA
     * Direccion movimiento = paleta.calcularMovimiento(pelota, deltaTime);
     * if (movimiento != null && movimiento != Direccion.NINGUNA) {
     *     if (movimiento == Direccion.ARRIBA) {
     *         paleta.moverArriba(deltaTime);
     *     } else if (movimiento == Direccion.ABAJO) {
     *         paleta.moverAbajo(deltaTime);
     *     }
     * }
     * </pre>
     *
     * @param pelota      la pelota del juego, necesaria para que la estrategia calcule el movimiento óptimo
     * @param tiempoDelta el tiempo transcurrido desde el último frame en segundos
     * @return la dirección de movimiento calculada por la estrategia, o {@code null} si no hay estrategia configurada
     */
    public mvc.modelo.enums.Direccion calcularMovimiento(
            final mvc.modelo.entidades.Pelota pelota,
            final double tiempoDelta) {
        if (estrategiaMovimiento == null) {
            return null;
        }
        return estrategiaMovimiento.calcularMovimiento(this, pelota, tiempoDelta);
    }

    /**
     * Mueve la paleta en una dirección específica durante un intervalo de tiempo.
     *
     * <p>Este método es un wrapper conveniente que ejecuta el movimiento apropiado
     * basándose en la dirección especificada. Simplifica el código del game loop
     * al eliminar la necesidad de múltiples if/else.</p>
     *
     * <p><b>Ejemplo de uso:</b></p>
     * <pre>
     * // Calcular y aplicar movimiento de IA en una sola operación
     * Direccion direccion = paleta.calcularMovimiento(pelota, delta);
     * paleta.moverEnDireccion(direccion, delta);
     * </pre>
     *
     * @param direccion   la dirección en la que mover la paleta. Si es {@code null} o {@code NINGUNA}, no se mueve.
     * @param tiempoDelta el tiempo transcurrido en segundos
     */
    public void moverEnDireccion(final mvc.modelo.enums.Direccion direccion, final double tiempoDelta) {
        if (direccion == null || direccion == mvc.modelo.enums.Direccion.NINGUNA) {
            return;
        }

        switch (direccion) {
            case ARRIBA:
                moverArriba(tiempoDelta);
                break;
            case ABAJO:
                moverAbajo(tiempoDelta);
                break;
            default:
                // No hacer nada para NINGUNA u otros casos
                break;
        }
    }

    /**
     * Verifica si esta paleta tiene una estrategia de movimiento configurada.
     *
     * @return {@code true} si hay una estrategia configurada, {@code false} en caso contrario
     */
    public boolean tieneEstrategiaMovimiento() {
        return estrategiaMovimiento != null;
    }

    /**
     * Establece el color de la paleta.
     *
     * @param color el nuevo color para la paleta
     */
    public void setColor(Color color){
        this.color = color;
    }

    /**
     * Establece el ancho de la paleta.
     *
     * @param ancho el nuevo ancho en píxeles
     */
    public void setAncho(double ancho){
        this.ancho = ancho;
    }

    /**
     * Establece el alto de la paleta.
     *
     * @param alto el nuevo alto en píxeles
     */
    public void setAlto(double alto){
        this.alto = alto;
    }

    /**
     * Establece la velocidad de la paleta.
     *
     * @param velocidad la nueva velocidad en píxeles por segundo
     */
    public void setVelocidad(double velocidad){
        this.velocidad = velocidad;
    }
}
