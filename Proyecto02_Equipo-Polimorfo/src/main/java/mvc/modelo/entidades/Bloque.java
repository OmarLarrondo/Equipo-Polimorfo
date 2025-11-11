package mvc.modelo.entidades;

/**
 * Representa un bloque destructible en el juego.
 * Forma parte del patrón Composite como componente hoja.
 *
 * @author Equipo-polimorfo
 * @version 1.0
 */
public class Bloque extends ObjetoJuego {

    private int resistencia;

    /**
     * Constructor de Bloque.
     *
     * @param x Posición X
     * @param y Posición Y
     * @param ancho Ancho del bloque
     * @param alto Alto del bloque
     * @param resistencia Resistencia del bloque (número de golpes para destruirlo)
     */
    public Bloque(double x, double y, double ancho, double alto, int resistencia) {
        super(x, y, ancho, alto);
        this.resistencia = resistencia;
    }

    /**
     * Obtiene la resistencia actual del bloque.
     *
     * @return resistencia
     */
    public int obtenerResistencia() {
        return resistencia;
    }

    /**
     * Reduce la resistencia del bloque en 1.
     */
    public void reducirResistencia() {
        if (resistencia > 0) {
            resistencia--;
        }
    }

    /**
     * Verifica si el bloque está destruido.
     *
     * @return true si la resistencia es 0, false en caso contrario
     */
    public boolean estaDestruido() {
        return resistencia <= 0;
    }

    /**
     * Actualiza el estado del bloque.
     *
     * @param deltaTime Tiempo transcurrido desde la última actualización
     */
    @Override
    public void actualizar(double deltaTime) {
        //aqui va su codigo
        // Los bloques generalmente son estáticos, sin lógica de actualización
    }

    /**
     * Obtiene los límites del bloque como un rectángulo.
     *
     * @return Rectángulo con los límites del bloque
     */
    @Override
    public javafx.geometry.Rectangle2D obtenerLimites() {
        //aqui va su codigo
        throw new UnsupportedOperationException("Unimplemented method 'obtenerLimites'");
    }

    /**
     * Dibuja el bloque en el contexto gráfico.
     *
     * @param gc Contexto gráfico de JavaFX
     */
    @Override
    public void dibujar(javafx.scene.canvas.GraphicsContext gc) {
        //aqui va su codigo
        throw new UnsupportedOperationException("Unimplemented method 'dibujar'");
    }
}
