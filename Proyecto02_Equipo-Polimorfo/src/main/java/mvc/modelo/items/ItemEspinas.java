package mvc.modelo.items;

import io.vavr.control.Option;
import javafx.scene.paint.Color;
import mvc.modelo.entidades.ObjetoJuego;
import mvc.modelo.entidades.paleta.Paleta;

/**
 * Item que agrega espinas temporalmente a la paleta del jugador.
 *
 * <p>Las espinas otorgan capacidades adicionales a la paleta, permitiendo
 * interacciones especiales con otros objetos del juego. Este item utiliza
 * el patrón Memento mediante un record inmutable para guardar y restaurar
 * el estado completo de la paleta, incluyendo dimensiones, color, velocidad
 * y cantidad de espinas.</p>
 *
 * <p>Solo puede aplicarse a objetos de tipo Paleta. Al expirar la duración,
 * las espinas son removidas y el estado original es restaurado completamente.</p>
 */
public class ItemEspinas implements Item {

    /**
     * Memento inmutable que almacena el estado de una paleta.
     * Captura las propiedades esenciales: velocidad, dimensiones, color y espinas.
     *
     * @param velocidad velocidad de movimiento de la paleta
     * @param ancho ancho de la paleta
     * @param alto alto de la paleta
     * @param color color de la paleta
     * @param cantidadEspinas cantidad de espinas en la paleta
     */
    private record EstadoPaleta(
        double velocidad,
        double ancho,
        double alto,
        Color color,
        int cantidadEspinas
    ) {}

    /**
     * Número de espinas que serán agregadas a la paleta.
     */
    private final int cantidadEspinas;

    /**
     * Duración total del efecto en segundos.
     */
    private final double duracion;

    /**
     * Indica si el efecto está actualmente aplicado.
     */
    private boolean activo;

    /**
     * Tiempo restante en segundos antes de que expire el efecto.
     */
    private double tiempoRestante;

    /**
     * Estado original de la paleta envuelto en Option para manejo funcional.
     * Option.none() indica que no hay estado guardado.
     */
    private Option<EstadoPaleta> estadoOriginal;

    /**
     * Construye un nuevo item de espinas.
     *
     * @param cantidadEspinas número de espinas que se agregarán a la paleta
     * @param duracion tiempo en segundos que durarán las espinas activas
     * @param activo estado inicial del item, normalmente false hasta que se aplique
     * @throws IllegalArgumentException si cantidadEspinas es menor o igual a 0, o si duracion es negativa
     */
    public ItemEspinas(int cantidadEspinas, double duracion, boolean activo) {
        if (cantidadEspinas <= 0) {
            throw new IllegalArgumentException("La cantidad de espinas debe ser mayor a 0");
        }
        if (duracion < 0) {
            throw new IllegalArgumentException("La duracion no puede ser negativa");
        }

        this.cantidadEspinas = cantidadEspinas;
        this.duracion = duracion;
        this.activo = activo;
        this.tiempoRestante = duracion;
        this.estadoOriginal = Option.none();
    }

    /**
     * Aplica el efecto de espinas a la paleta especificada.
     *
     * <p>Antes de agregar las espinas, guarda el estado completo de la paleta
     * en un memento inmutable para permitir la restauración exacta posterior.
     * Si el item ya está activo, ignora la aplicación.</p>
     *
     * @param objeto la Paleta a la cual se agregarán las espinas
     * @throws IllegalArgumentException si el objeto no es una Paleta
     */
    @Override
    public void aplicar(ObjetoJuego objeto) {
        if (activo) {
            return;
        }

        if (objeto instanceof Paleta paleta) {
            estadoOriginal = Option.of(capturarEstado(paleta));

            paleta.agregarEspina();
            paleta.establecerActivo(true);
            activo = true;
            tiempoRestante = duracion;
        } else {
            throw new IllegalArgumentException("Solo es posible agregar espinas a Paletas");
        }
    }

    /**
     * Obtiene la duración configurada del efecto.
     *
     * @return duración total en segundos
     */
    @Override
    public double obtenerDuracion() {
        return duracion;
    }

    /**
     * Verifica si el efecto de espinas está actualmente activo.
     *
     * @return true si las espinas están aplicadas, false en caso contrario
     */
    @Override
    public boolean estaActivo() {
        return activo;
    }

    /**
     * Desactiva el efecto y elimina las espinas de la paleta.
     *
     * <p>Restaura el estado completo de la paleta desde el memento guardado,
     * revirtiendo todos los cambios aplicados (dimensiones, color, velocidad
     * y cantidad de espinas).</p>
     *
     * @param objeto la Paleta de la cual se eliminarán las espinas
     * @throws IllegalArgumentException si el objeto no es una Paleta
     */
    @Override
    public void desactivar(ObjetoJuego objeto) {
        if (objeto instanceof Paleta paleta) {
            estadoOriginal.forEach(estado -> restaurarEstado(paleta, estado));

            paleta.eliminarEspinas();
            paleta.establecerActivo(false);
            activo = false;
            tiempoRestante = duracion;
        } else {
            throw new IllegalArgumentException("Solo paletas, por favor.");
        }
    }

    /**
     * Actualiza el estado del item según el tiempo transcurrido.
     *
     * <p>Decrementa el tiempo restante del efecto. Cuando el tiempo llega a cero
     * o menos, desactiva automáticamente el item y elimina las espinas.
     * Este método debe ser invocado en cada frame del juego.</p>
     *
     * @param deltaTiempo tiempo transcurrido desde la última actualización en segundos
     * @param objeto la paleta cuyo estado será restaurado si expira el tiempo
     */
    @Override
    public void actualizar(double deltaTiempo, ObjetoJuego objeto) {
        if (!activo) {
            return;
        }

        tiempoRestante = tiempoRestante - deltaTiempo;
        if (tiempoRestante <= 0) {
            desactivar(objeto);
        }
    }

    /**
     * Captura el estado actual de una paleta en un memento inmutable.
     *
     * <p>Este método implementa el patrón Memento creando una instantánea
     * inmutable de las propiedades esenciales de la paleta.</p>
     *
     * @param paleta la paleta cuyo estado será capturado
     * @return memento inmutable conteniendo el estado de la paleta
     */
    private EstadoPaleta capturarEstado(Paleta paleta) {
        return new EstadoPaleta(
            paleta.obtenerVelocidad(),
            paleta.obtenerAncho(),
            paleta.obtenerAlto(),
            paleta.obtenerColor(),
            paleta.obtenerCantidadEspinas()
        );
    }

    /**
     * Restaura el estado de una paleta desde un memento.
     *
     * <p>Aplica todas las propiedades almacenadas en el memento a la paleta,
     * revirtiendo completamente cualquier cambio realizado.</p>
     *
     * @param paleta la paleta cuyo estado será restaurado
     * @param estado el memento conteniendo el estado a restaurar
     */
    private void restaurarEstado(Paleta paleta, EstadoPaleta estado) {
        paleta.establecerVelocidad(estado.velocidad());
        paleta.setColor(estado.color());
        paleta.setCantidadEspinas(estado.cantidadEspinas());
        paleta.setAncho(estado.ancho());
        paleta.setAlto(estado.alto());
    }

    /**
     * Obtiene la posición X del item para renderizado.
     * Los items de espinas no tienen posición física.
     *
     * @return 0.0 (sin posición física)
     */
    @Override
    public double obtenerX() {
        return 0.0;
    }

    /**
     * Obtiene la posición Y del item para renderizado.
     * Los items de espinas no tienen posición física.
     *
     * @return 0.0 (sin posición física)
     */
    @Override
    public double obtenerY() {
        return 0.0;
    }

    /**
     * Obtiene el ancho del item para renderizado.
     *
     * @return 20.0 píxeles
     */
    @Override
    public double obtenerAncho() {
        return 20.0;
    }

    /**
     * Obtiene el alto del item para renderizado.
     *
     * @return 20.0 píxeles
     */
    @Override
    public double obtenerAlto() {
        return 20.0;
    }
}
