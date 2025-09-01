package main.java.mx.unam.ciencias.modeladoyprogramacion;

import java.io.*;
import java.util.Map;
import java.util.Date;
import java.util.HashMap;

/**
 * Clase para representar un Usuario con atrubutos como nombre, 
 * dineroInicial, cuentaBanco, historialSuscripciones
 * TIENE NOMBRE, SU DINERO TOTAL, LISTA DE LOS SERVICIOS QUE TIENE CONTRATADOS.
 * 
 */

public class Usuario implements Observer{ // IMPLEMNATAR METODOS DE LA INTERFACE
    /*Nombre del usuario */
    private String nombre;
    /*Dinero inicial del usuario */
    private Double dineroInicial;
    /*La cuenta de banco del usuario*/
    private CuentaBanco cuentaBanco;
    /*El historial de suscriones del usuario */
    private Map<String, Date> historialSuscripciones;

    /**
     * Constructor para inicializar los atributos del Usuario
     * @param nombre
     * @param dineroInicial
     * @param cuentaBanco
     */
    public Usuario(String nombre, Double dineroInicial, CuentaBanco cuentaBanco) {
            this.nombre = nombre;
            this.dineroInicial = dineroInicial;
            this.cuentaBanco = cuentaBanco;
            this.historialSuscripciones = new HashMap<>();
        }
    //Metodos del Usuario



                    


        
    

    
}