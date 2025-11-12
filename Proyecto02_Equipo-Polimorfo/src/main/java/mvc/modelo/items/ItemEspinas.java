package mvc.modelo.items;

import patrones.prototype.ConfigPaleta;
import mvc.modelo.entidades.ObjetoJuego;
import mvc.modelo.entidades.Paleta;

/**
 * Item que agrega espinas temporalmente a la paleta del jugador.
 *
 * Las espinas otorgan capacidades adicionales a la paleta, permitiendo
 * interacciones especiales con otros objetos del juego. Este item utiliza
 * el patrón Memento mediante ConfigPaleta para guardar y restaurar el
 * estado completo de la paleta, incluyendo dimensiones, color, velocidad
 * y cantidad de espinas.
 *
 * Solo puede aplicarse a objetos de tipo Paleta. Al expirar la duración,
 * las espinas son removidas y el estado original es restaurado completamente.
 */
public class ItemEspinas implements Item{
    /**
     * Número de espinas que serán agregadas a la paleta.
     */
    private int cantidadEspinas;

    /**
     * Duración total del efecto en segundos.
     */
    private double duracion;

    /**
     * Indica si el efecto está actualmente aplicado.
     */
    private boolean activo;

    /**
     * Tiempo restante en segundos antes de que expire el efecto.
     */
    private double tiempoRestante;

    /**
     * Memento que almacena el estado original completo de la paleta.
     * Utilizado para restaurar todas las propiedades al desactivar el item.
     */
    private ConfigPaleta estadoOriginal;

    /**
     * Construye un nuevo item de espinas.
     *
     * @param cantidadEspinas número de espinas que se agregarán a la paleta
     * @param duracion tiempo en segundos que durarán las espinas activas
     * @param activo estado inicial del item, normalmente false hasta que se aplique
     */
    public ItemEspinas(int cantidadEspinas, double duracion, boolean activo) {
        if(cantidadEspinas <= 0) throw new IllegalArgumentException("La cantidad de espinas debe ser mayor a 0");
        if(duracion < 0) throw new IllegalArgumentException("La duracion no puede ser negativa");

        this.cantidadEspinas = cantidadEspinas;
        this.duracion = duracion;
        this.activo = activo;
        this.tiempoRestante = duracion;

    }

    /**
     * Aplica el efecto de espinas a la paleta especificada.
     *
     * Antes de agregar las espinas, guarda el estado completo de la paleta
     * en un memento (ConfigPaleta) para permitir la restauración exacta
     * posterior. Si el item ya está activo, ignora la aplicación.
     *
     * @param objeto la Paleta a la cual se agregarán las espinas
     * @throws IllegalArgumentException si el objeto no es una Paleta
     */
    @Override
    public void aplicar(ObjetoJuego objeto) {
        if(activo )return;

        if(objeto instanceof Paleta paleta){
            estadoOriginal = guardarEstadoOriginal(paleta);

            paleta.agregarEspina();
            paleta.establecerActivo(true);
            activo = true;
            tiempoRestante = duracion;
        }
        else{
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
     * Restaura el estado completo de la paleta desde el memento guardado,
     * revirtiendo todos los cambios aplicados (dimensiones, color, velocidad
     * y cantidad de espinas).
     *
     * @param objeto la Paleta de la cual se eliminarán las espinas
     * @throws IllegalArgumentException si el objeto no es una Paleta
     */
    @Override
    public void desactivar(ObjetoJuego objeto) {
        if(objeto instanceof Paleta paleta){
            restaurarEstadoOriginal(paleta);
            paleta.eliminarEspinas();
            paleta.establecerActivo(false);
            activo = false;
            tiempoRestante = duracion;
        }else{
            throw new IllegalArgumentException("Solo paletas, por favor.");
        }
    }

    /**
     * Actualiza el estado del item según el tiempo transcurrido.
     *
     * Decrementa el tiempo restante del efecto. Cuando el tiempo llega a cero
     * o menos, desactiva automáticamente el item y elimina las espinas.
     * Este método debe ser invocado en cada frame del juego.
     *
     * @param deltaTiempo tiempo transcurrido desde la última actualización en segundos
     * @param objeto la paleta cuyo estado será restaurado si expira el tiempo
     */
    @Override
    public void actualizar(double deltaTiempo, ObjetoJuego objeto){
        if(!activo) return;

        tiempoRestante = tiempoRestante - deltaTiempo;
        if(tiempoRestante<= 0){
            desactivar(objeto);
        }
    }

    /**
     * Guarda el estado completo actual de la paleta en un memento.
     *
     * Crea un nuevo objeto ConfigPaleta que almacena todas las propiedades
     * relevantes de la paleta: velocidad, dimensiones, color y cantidad de
     * espinas existentes. Este memento permite restaurar el estado exacto
     * posteriormente.
     *
     * @param paleta la paleta cuyo estado será guardado
     * @return memento conteniendo el estado completo de la paleta
     */
    private ConfigPaleta guardarEstadoOriginal(Paleta paleta) {
        ConfigPaleta copia = new ConfigPaleta();

        copia.setRapidez(paleta.obtenerVelocidad());
        copia.setAncho(paleta.obtenerAncho());
        copia.setAlto(paleta.obtenerAlto());
        copia.setColorPrimario(paleta.obtenerColor());
        copia.setCantidadEspinas(paleta.obtenerCantidadEspinas());

        return copia;
    }

    /**
     * Restaura el estado de la paleta desde el memento guardado.
     *
     * Aplica todas las propiedades almacenadas en el memento estadoOriginal
     * a la paleta, revirtiendo completamente cualquier cambio realizado.
     * Si no existe memento guardado, no realiza ninguna acción.
     *
     * @param paleta la paleta cuyo estado será restaurado
     */
    private void restaurarEstadoOriginal(Paleta paleta) {
        if (estadoOriginal == null) return;
        paleta.establecerVelocidad(estadoOriginal.getRapidez());
        paleta.setColor(estadoOriginal.getColor());
        paleta.setCantidadEspinas(estadoOriginal.getCantidadEspinas());
        paleta.setAncho(estadoOriginal.getAncho());
        paleta.setAlto(estadoOriginal.getAlto());
    }
}
