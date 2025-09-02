package modelo;

import java.io.*;
import java.util.Map;
import java.util.Date;
import java.util.HashMap;

import patrones.Observer;

/**
 * Clase para representar un Usuario con atrubutos como nombre, 
 * dineroInicial, cuentaBanco, historialSuscripciones
 */

public class Usuario implements Observer{ // IMPLEMNATAR METODOS DE LA INTERFACE
    /*Nombre del usuario */
    private String nombre;
    /*Dinero inicial del usuario */
    private Double dineroInicial;
    /*La cuenta de banco del usuario*/
    private CuentaBanco cuentaBanco;

    /*
     * private Map<String, Integer> historialSuscripciones;
     * SE ELIMINO A PETICION DE SAUL. CHECAR************************************
     */

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
    }
    
    /**
     * Notifca los meses usando un servicio,verifica que el servico sea valido, los meses no sean menores a 0
     * , que este asociado a una cuenta de banco.
     * imprime un msj en consola.
     * Crea una nueva llave, si existe la reemplaza. 
     * 
     * @param servicio EL servicio para consultar los meses de uso
     * @param meses Los meses usando el servicio
     */
    @Override
    public void notificarMesesUso(String servicio, int meses) {
        if (servicio == null || servicio.trim().isEmpty()) {
            System.out.println("Servicio inválido, no se puede procesar la notificación.");
            return;
        }
    
        System.out.println(String.format("%s, llevas %d meses usando %s",
                                                nombre, meses, servicio));
    }



    
    /**
     * Metodo para notificar las recomendaciones en tal mes de un servicio.
     * Verifica que el servicio sea Valido.
     * 
     * @param servicio EL servicio contratado por el usuario
     * @param recomendaciones las recomendaciones del mes.
     * @param mes Mes donde se dan las recomendaciones
     */
    @Override
    public void notificarRecomendacion(String servicio, String recomendaciones, int mes) {
        if (servicio == null || servicio.trim().isEmpty()) {
            System.out.println("Servicio inválido.");
            return;
        }

        System.out.println(String.format(
            "%s, recomendación del mes %d en %s: %s", 
                    nombre, mes, servicio, recomendaciones));
    }

    
    /**
     * Notifica al usuario una bienvenida.
     * Dependiendo del valor de <code>esRenovacion</code>, se imprime un mensaje
     * de bienvenida general o de bienvenida por renovación.
     *
     * @param servicio Nombre del servicio que se ha activado.
     * @param esRenovacion <code>true</code> si el usuario está renovando un servicio,
     *                     <code>false</code> si es una contratación nueva.
     */
    @Override
    public void notificarBienvenida(String servicio, boolean esRenovacion) {
        if (servicio == null || servicio.trim().isEmpty()) {
            System.out.println("Servicio inválido.");
            return;
        }
        if(esRenovacion){
            System.out.println(String.format(
                        "Bienvenid@ de vuelta %s”.", 
                                nombre));
        }else{
        System.out.println(String.format(
                        "%s, Bienvenid@ Graciaas por contratar %s    ", 
                                nombre, servicio));
        }
    }

    /**
     * Metodo para notifcar al usuario una despedida.
     * 
     * @param servicio Nombre del servicio que se despide
     * 
     */
    @Override
    public void notificarDespedida(String servicio) {
        if (servicio == null || servicio.trim().isEmpty()) {
            System.out.println("Servicio inválido.");
            return;
        }
        System.out.println(String.format(  
                    "Ya no tienes contratado %s.", 
                            servicio));
    }

    /**
     * Metodo para notificar el cobro exitos realizado por un servicio.
     * 
     * @param servicio Nombre del servicio que tiene contratado el cleinte. 
     * @param monto monto que cobra el servicio..
     * @param plan plan contratado por el cliete.
     * 
     */
    @Override
    public void notificarCobroExitoso(String servicio, double monto, String plan) {
        if (servicio == null || servicio.trim().isEmpty()) {
            System.out.println("Servicio inválido.");
            return;
        }
        System.out.println(String.format(
            "%s paga $%.2f por el servicio de %s para %s"
            , nombre, monto , servicio, plan  ));
    }
    /**
     * Metodo para notificar al usuario un cobro fallido.
     * 
     * @param servicio el nombre del servicio que no salio bien el pago
     * @param monto el monto que se debio descontar, pero no se desconto
     */

    @Override
    public void notificarCobroFallido(String servicio, double monto) {
        if (servicio == null || servicio.trim().isEmpty()) {
            System.out.println("Servicio inválido.");
            return;
        }
        System.out.println(String.format("El pago de %s, por la cantidad de $%.2f fallo"
        , servicio,monto));
        
    }

}