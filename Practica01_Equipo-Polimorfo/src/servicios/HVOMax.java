package servicios;

import java.util.ArrayList;
import java.util.List;

import modelo.Usuario;
import patrones.EstrategiaCobro;
import patrones.Observer;



public class HVOMax extends Servicio{
    /**
     * Constructor, cada servicioo debe inicializarlo
     * @param nombre
     */

    public HVOMax(String nombre) {
        super(nombre);
        this.observers = new ArrayList<>();
        this.recomendacionesPorMes = new java.util.HashMap<>();
        inicializarRecomendaciones();
    }

    @Override
    public void agregarObserver(Observer observer, EstrategiaCobro estrategia) {
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
        }
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
    public List<String> obtenerEstrategiasDisponibles() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'obtenerEstrategiasDisponibles'");
    }

    @Override
    public String obtenerRecomendacion(int mes) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'obtenerRecomendacion'");
    }

    @Override
    public void inicializarRecomendaciones() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'inicializarRecomendaciones'");
    }

    @Override
    public String obtenerNombreServicio() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'obtenerNombreServicio'");
    }




}
