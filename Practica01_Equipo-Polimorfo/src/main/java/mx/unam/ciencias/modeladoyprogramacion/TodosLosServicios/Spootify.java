package main.java.mx.unam.ciencias.modeladoyprogramacion.TodosLosServicios;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase Spootify, es un servicio. Debe implemntar todos los metodos 
 * de Servicio.
 */
public class Spootify extends Servicio {
    /**
     * Constructor, cada servicioo debe inicializarlo
     * @param nombre
     */

    public Spootify(String nombre) {
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


    
}
