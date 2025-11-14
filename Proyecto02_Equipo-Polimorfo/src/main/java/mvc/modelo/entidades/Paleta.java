package mvc.modelo.entidades;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import patrones.prototype.ConfigPaleta;
import patrones.strategy.movimiento.EstrategiaMovimiento;

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
        cantidadEspinas = cantidadEspinas + 1;
        tieneEspinas = true;
    }

    public void eliminarEspinas(){
        cantidadEspinas = 0;
        tieneEspinas = false;
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
        estadoOriginal = getEstadoOriginal();
    }
    public void restaurarEstado(){
        if(estadoOriginal != null){
            setEstadoOriginal(estadoOriginal);
        }
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
