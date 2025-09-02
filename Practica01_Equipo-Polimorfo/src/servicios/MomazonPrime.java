package servicios;

import java.util.ArrayList;
import java.util.List;

import modelo.Usuario;
import patrones.EstrategiaCobro;
import patrones.Observer;

/**
 * clase de momazonPrime, extiende servicio, debe 
 * implementar los metodos de servicio
 */

public class MomazonPrime extends Servicio{

    /**
     * Constructor, cada servicioo debe inicializarlo
     * @param nombre
     */

    public MomazonPrime(String nombre) {
        super(nombre);
        this.observers = new ArrayList<>();
        this.recomendacionesPorMes = new java.util.HashMap<>();
        inicializarRecomendaciones();
    }

    /**
     * metodo para obtener las estrategias disponibles de memeflix
     * @return la lista de estrategias de memeflix
     */
    @Override
    public List<String> obtenerEstrategiasDisponibles(){
        return null;
        //aqui va s codfig 
        
    }

    /**
     * metodo para obtener las recomendaciones
     * @return las recomendaciones de memeflix
     */
    @Override
    public String obtenerRecomendacion(int mes){
        return null; //HACER
    }

    @Override
    /**
     * metodo para inicializar las recomendaciones
     */
    public void inicializarRecomendaciones(){
        //hacer
    }

    @Override
    public void agregarObserver(Observer observer, EstrategiaCobro estrategia) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'agregarObserver'");
    }

    @Override
    public void removerObserver(Observer observer) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removerObserver'");
    }

    @Override
    public void notificarMesesUso() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'notificarMesesUso'");
    }

    @Override
    public void notificarRecomendacion(int mes) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'notificarRecomendacion'");
    }

    @Override
    public void notificarBienvenida(Observer observer, boolean esRenovacion) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'notificarBienvenida'");
    }

    @Override
    public void notificarDespedida(Observer observer) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'notificarDespedida'");
    }

    @Override
    public void actualizarHistorialUsuario(Usuario usuario, EstrategiaCobro estratehia) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actualizarHistorialUsuario'");
    }

    @Override
    public String obtenerNombreServicio() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'obtenerNombreServicio'");
    }


    
}
