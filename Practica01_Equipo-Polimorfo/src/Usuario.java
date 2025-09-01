import java.io.*;
import java.util.Map;
import java.util.Date;
import java.util.HashMap;

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
            System.out.println("Servicio inválido, no se puede registrar la notificación.");
            return;
        }

        if (meses < 0) {
            System.out.println("Meses inválidos, no se puede registrar la notificación.");
            return;
        }

        if (cuentaBanco == null) {
            System.out.println("No hay cuenta asociada a "+nombre +", no se puede procesar la notificación.");
            return;
        }   

        System.out.println(String.format(
            "Usuario %s ha usado %s durante %d meses", nombre, servicio, meses));

        historialSuscripciones.put(servicio, meses);
    }

    @Override
    public void notificarRecomendacion(String servicio, String recomendaciones, int mes) {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void notificarBienvenida(String servicio, boolean esRenovacion) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void notificarDespedida(String servicio) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void notificarCobroExitoso(String servicio, double monto, String plan) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void notificarCobroFallido(String servicio, double monto) {
        // TODO Auto-generated method stub
        
    }

}
