package mvc.modelo.items;

import java.util.ArrayList;
import java.util.List;

import mvc.modelo.entidades.ObjetoJuego;
import mvc.modelo.entidades.ObjetoJuegoCompuesto;
import mvc.modelo.entidades.Pelota;

/**
 * Item que clona pelotas existentes para crear múltiples pelotas en juego.
 *
 * Este power-up utiliza el patrón Prototype para crear copias de las pelotas
 * actuales en el juego, multiplicando las oportunidades de impacto. Las pelotas
 * clonadas tienen velocidades ligeramente variadas (factor aleatorio entre 0.8
 * y 1.2) para crear trayectorias divergentes.
 *
 * Las pelotas creadas son agregadas al contenedor de objetos del juego mediante
 * el patrón Composite (ObjetoJuegoCompuesto), y son rastreadas internamente
 * para permitir su eliminación cuando expira el efecto.
 */
public class ItemMultiPelota implements Item{
    /**
     * Número máximo de pelotas adicionales a crear.
     */
    private int cantidadPelotas;

    /**
     * Duración total del efecto en segundos.
     */
    private double duracion;

    /**
     * Indica si el efecto está actualmente activo.
     */
    private boolean activo;

    /**
     * Tiempo restante antes de que expire el efecto en segundos.
     */
    private double tiempoRestante;

    /**
     * Lista de pelotas creadas por este item.
     * Mantiene referencias para permitir su eliminación al desactivar.
     */
    private List<Pelota> pelotasCreadas;


    /**
     * Construye un nuevo item de multi-pelota.
     *
     * @param cantidadPelotas número máximo de pelotas adicionales a crear
     * @param duracion tiempo en segundos que durarán las pelotas clonadas
     * @param activo estado inicial del item, normalmente false hasta que se aplique
     */
    public ItemMultiPelota(int cantidadPelotas, double duracion, boolean activo) {
        if(cantidadPelotas <= 0) throw new IllegalArgumentException("La cantidad de pelotas debe ser mayor a 0");
        if(duracion < 0) throw new IllegalArgumentException("La duracion no puede ser negativa");

        this.cantidadPelotas = cantidadPelotas;
        this.duracion = duracion;
        this.activo = activo;
        this.tiempoRestante = duracion;
        this.pelotasCreadas = new ArrayList<>();

    }

    /**
     * Aplica el efecto creando clones de las pelotas existentes.
     *
     * Busca pelotas en el contenedor compuesto y crea copias de ellas usando
     * el método clonar() (patrón Prototype). Cada clon recibe velocidades
     * modificadas por un factor aleatorio entre 0.8 y 1.2 para crear
     * trayectorias variadas.
     *
     * Las pelotas clonadas son agregadas al contenedor y rastreadas
     * internamente. El proceso se detiene al alcanzar el límite de
     * cantidadPelotas o al procesar todas las pelotas existentes.
     *
     * @param objeto el ObjetoJuegoCompuesto que contiene las pelotas a clonar
     */
    @Override
    public void aplicar(ObjetoJuego objeto) {
        if(objeto instanceof ObjetoJuegoCompuesto contenedor){
            List<ObjetoJuego> hijosConter = contenedor.obtenerHijos();
            int creadas = 0;
            for(int i = 0; i < hijosConter.size(); i++){
                if(hijosConter.get(i) instanceof Pelota p){
                    if(creadas>= cantidadPelotas) break;
                    Pelota copiaPelota = p.clonar();
                    double aleatorio = 0.8 + Math.random() * 0.4;
                    copiaPelota.establecerVelocidadX(p.obtenerVelocidadX()* aleatorio);
                    copiaPelota.establecerVelocidadY(p.obtenerVelocidadY()* aleatorio);

                    contenedor.agregar(copiaPelota);
                    pelotasCreadas.add(copiaPelota);
                    creadas++;
                    activo = true;
                    tiempoRestante = duracion;
                }
            }
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
     * Verifica si el efecto de multi-pelota está actualmente activo.
     *
     * @return true si el efecto está aplicado, false en caso contrario
     */
    @Override
    public boolean estaActivo() {
        return activo;
    }

    /**
     * Desactiva el efecto eliminando todas las pelotas clonadas.
     *
     * Remueve del contenedor todas las pelotas que fueron creadas por
     * este item, retornando el juego al número original de pelotas.
     *
     * @param objeto el ObjetoJuegoCompuesto del cual se eliminarán las pelotas
     */
    @Override
    public void desactivar(ObjetoJuego objeto) {
        if(objeto instanceof ObjetoJuegoCompuesto contenedor){
            for (Pelota p : pelotasCreadas) {
                contenedor.eliminar(p);
            }
            activo = false;
            tiempoRestante = duracion;
        }
    }

    /**
     * Actualiza el estado del item según el tiempo transcurrido.
     *
     * Decrementa el tiempo restante del efecto. Cuando el tiempo llega a cero
     * o menos, desactiva automáticamente el item y elimina las pelotas clonadas.
     * Este método debe ser invocado en cada frame del juego.
     *
     * @param deltaTiempo tiempo transcurrido desde la última actualización en segundos
     * @param objeto el contenedor del cual se eliminarán las pelotas si expira el tiempo
     */
    @Override
    public void actualizar(double deltaTiempo, ObjetoJuego objeto) {
        if(!activo) return;

        tiempoRestante = tiempoRestante - deltaTiempo;
        if(tiempoRestante <= 0){
            desactivar(objeto);
        }
    }

}
