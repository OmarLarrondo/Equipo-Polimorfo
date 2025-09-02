package modelo;


import patrones.Observer;

/**
 * Representa un usuario en la simulación de servicios de streaming.
 * Implementa el patrón Observer para recibir notificaciones de los servicios
 * como mensajes de bienvenida, despedida, recomendaciones y estados de cobro.
 * 
 * Cada usuario tiene una cuenta bancaria asociada que maneja todas las
 * transacciones financieras de los servicios de streaming contratados.
 * 
 * @author Equipo Polimórfo
 * @version 1.0
 */

public class Usuario implements Observer {
    /** Nombre del usuario */
    private String nombre;
    
    /** La cuenta de banco del usuario */
    private CuentaBanco cuentaBanco;

    /**
     * Constructor para inicializar un usuario con su nombre y dinero inicial.
     * Crea automáticamente una cuenta bancaria asociada.
     * 
     * @param nombre el nombre del usuario, no debe ser null ni vacío
     * @param dinero el dinero inicial que tendrá disponible en su cuenta bancaria
     */
    public Usuario(String nombre, double dinero) {
        this.nombre = nombre;
        this.cuentaBanco = new CuentaBanco(dinero, this);
    }
    
    /**
     * Notifica al usuario los meses que lleva usando un servicio específico.
     * Este método es parte del patrón Observer y es llamado mensualmente por
     * cada servicio para informar al usuario su tiempo de uso acumulado.
     * 
     * @param servicio el nombre del servicio de streaming
     * @param meses el número total de meses que ha estado suscrito al servicio
     */
    @Override
    public void notificarMesesUso(String servicio, int meses) {
        if (!esServicioValido(servicio)) {
            return;
        }
        
        System.out.println(String.format("%s, llevas %d meses usando %s",
                                        nombre, meses, servicio));
    }
    
    /**
     * Notifica al usuario las recomendaciones mensuales de contenido de un servicio.
     * Cada servicio envía recomendaciones personalizadas que cambian mes a mes,
     * como shows, películas, música o videos según el tipo de servicio.
     * 
     * @param servicio el nombre del servicio de streaming
     * @param recomendaciones el contenido recomendado para este mes
     * @param mes el número del mes actual de la simulación
     */
    @Override
    public void notificarRecomendacion(String servicio, String recomendaciones, int mes) {
        if (!esServicioValido(servicio)) {
            return;
        }
        
        System.out.println(String.format(
            "%s, recomendación del mes %d en %s: %s", 
            nombre, mes, servicio, recomendaciones));
    }

    
    /**
     * Notifica al usuario un mensaje de bienvenida al suscribirse a un servicio.
     * Distingue entre nuevas suscripciones y renovaciones después de cancelaciones.
     *
     * @param servicio el nombre del servicio al que se suscribe
     * @param esRenovacion true si el usuario se está reactivando después de una
     *                    cancelación previa, false si es primera vez
     */
    @Override
    public void notificarBienvenida(String servicio, boolean esRenovacion) {
        if (!esServicioValido(servicio)) {
            return;
        }
        
        if (esRenovacion) {
            System.out.println(String.format("Bienvenido de vuelta %s", nombre));
        } else {
            System.out.println(String.format(
                "%s, Bienvenido. Gracias por contratar %s", 
                nombre, servicio));
        }
    }

    /**
     * Notifica al usuario cuando se cancela o pierde una suscripción a un servicio.
     * Utiliza el mensaje específico requerido por la práctica para despedidas.
     * 
     * @param servicio el nombre del servicio que se está cancelando
     */
    @Override
    public void notificarDespedida(String servicio) {
        if (!esServicioValido(servicio)) {
            return;
        }
        
        System.out.println(String.format(
            "Lamentamos que dejes el servicio %s", 
            nombre));
    }

    /**
     * Notifica al usuario cuando un cobro mensual ha sido procesado exitosamente.
     * Muestra el desglose detallado del pago realizado según el formato requerido.
     * 
     * @param servicio el nombre del servicio que realizó el cobro
     * @param monto el monto que fue descontado de la cuenta bancaria
     * @param plan la descripción del plan específico contratado
     */
    @Override
    public void notificarCobroExitoso(String servicio, double monto, String plan) {
        if (!esServicioValido(servicio)) {
            return;
        }
        
        System.out.println(String.format(
            "%s paga $%.2f por el servicio de %s para %s",
            nombre, monto, servicio, plan));
    }
    /**
     * Notifica al usuario cuando un cobro no pudo ser procesado por fondos insuficientes.
     * Esto resulta en la pérdida automática de la suscripción al servicio.
     * 
     * @param servicio el nombre del servicio cuyo pago falló
     * @param monto el monto que no pudo ser descontado por falta de fondos
     */

    @Override
    public void notificarCobroFallido(String servicio, double monto) {
        if (!esServicioValido(servicio)) {
            return;
        }
        
        System.out.println(String.format(
            "El pago de %s por la cantidad de $%.2f falló",
            servicio, monto));
    }
    
    /**
     * Método auxiliar para validar que un servicio sea válido.
     * Centraliza la validación para evitar código duplicado.
     * 
     * @param servicio el nombre del servicio a validar
     * @return true si el servicio es válido, false si es null o vacío
     */
    private boolean esServicioValido(String servicio) {
        if (servicio == null || servicio.trim().isEmpty()) {
            System.out.println("Servicio inválido, no se puede procesar la notificación.");
            return false;
        }
        return true;
    }
    
    /**
     * Obtiene el dinero disponible en la cuenta bancaria del usuario.
     * 
     * @return el saldo actual de la cuenta bancaria
     */
    public double obtenerDineroDisponible() {
        return cuentaBanco.obtenerSaldo();
    }
    
    /**
     * Obtiene la cuenta bancaria asociada al usuario.
     * 
     * @return la instancia de CuentaBanco del usuario
     */
    public CuentaBanco obtenerCuentaBanco() {
        return cuentaBanco;
    }
    
    /**
     * Obtiene el nombre del usuario.
     * 
     * @return el nombre del usuario
     */
    public String obtenerNombre() {
        return nombre;
    }
}
