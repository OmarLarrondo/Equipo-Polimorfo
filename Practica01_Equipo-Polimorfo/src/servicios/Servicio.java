package servicios;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import patrones.Observer;
import patrones.Subject;
import patrones.EstrategiaCobro;
import modelo.Usuario;
import modelo.HistorialServicio;
import modelo.ResultadoCobro;
import modelo.CuentaBanco;



public abstract class Servicio implements Subject {

    /**Nombre del servicio */
    protected String nombre;
    /**Lista de observadores */
    protected List<Observer> observers;
    /**Lista de contenidos audiovisuales disponibles */
    protected List<String> audiovisuales;
    /**Recomendaciones por mes<MES,RECOMENDACIONES> */
    protected Map<Integer, List<String>> recomendacionesPorMes;
    /**Historial de los usuarios del servicio */
    protected Map<Observer, HistorialServicio> historialesUsuarios;

    /**
     * Constructor para inicializar los atributos de Servicio.
     * Inicializa todas las estructuras de datos necesarias para el funcionamiento
     * del servicio incluyendo listas de observadores, audiovisuales y mapas.
     * 
     * @param nombre nombre de servicio a inicializar
     */
    public Servicio(String nombre) {
        this.nombre = nombre;
        this.observers = new ArrayList<>();
        this.audiovisuales = new ArrayList<>();
        this.recomendacionesPorMes = new HashMap<>();
        this.historialesUsuarios = new HashMap<>();
    }

    /**
     * Agrega un observador al servicio.
     * Implementación del método requerido por la interfaz Subject.
     * 
     * @param observer observador que se agregará al servicio
     */
    @Override
    public void agregarObserver(Observer observer) {
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
        }
    }

    /**
     * Remueve un observador del servicio.
     * Implementación del método requerido por la interfaz Subject.
     * @param observer Observador que se removerá del servicio
     */
    @Override
    public void removerObserver(Observer observer){
        if (observer != null) {
            this.observers.remove(observer);
        }
    }

    /**
     * Notifica a un observador específico enviándole un mensaje.
     * Implementación del método requerido por la interfaz Subject.
     * 
     * @param observer El observador que recibirá la notificación
     * @param mensaje El mensaje a enviar al observador
     */
    @Override
    public void notificarObserver(Observer observer, String mensaje) {
        if (observer != null && mensaje != null) {
            // Determinar el tipo de mensaje y llamar al método correspondiente del observer
            if (mensaje.contains("meses usando")) {
                // Extraer información para notificación de meses de uso
                String[] partes = mensaje.split(",");
                if (partes.length >= 2) {
                    try {
                        String mesesStr = partes[1].trim().split(" ")[1];
                        int meses = Integer.parseInt(mesesStr);
                        observer.notificarMesesUso(this.nombre, meses);
                    } catch (NumberFormatException e) {
                        System.err.println("Error al procesar notificación de meses de uso: " + mensaje);
                    }
                }
            } else if (mensaje.contains("recomendación del mes")) {
                // Extraer información para recomendación
                String[] partes = mensaje.split(":");
                if (partes.length >= 2) {
                    try {
                        String mesYRecomendacion = partes[0];
                        String recomendacion = partes[1].trim();
                        String[] mesInfo = mesYRecomendacion.split("mes ");
                        if (mesInfo.length >= 2) {
                            String mesStr = mesInfo[1].trim().split(" ")[0];
                            int mes = Integer.parseInt(mesStr);
                            observer.notificarRecomendacion(this.nombre, recomendacion, mes);
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Error al procesar recomendación: " + mensaje);
                    }
                }
            } else if (mensaje.contains("Bienvenido")) {
                boolean esRenovacion = mensaje.contains("de vuelta");
                observer.notificarBienvenida(this.nombre, esRenovacion);
            } else if (mensaje.contains("Lamentamos")) {
                observer.notificarDespedida(this.nombre);
            } else if (mensaje.contains("paga $")) {
                // Extraer información de cobro exitoso
                try {
                    String[] partes = mensaje.split(" paga \\$");
                    if (partes.length >= 2) {
                        String[] montoYPlan = partes[1].split(" por el servicio de ");
                        if (montoYPlan.length >= 2) {
                            double monto = Double.parseDouble(montoYPlan[0]);
                            String planInfo = montoYPlan[1].split(" para ")[1];
                            observer.notificarCobroExitoso(this.nombre, monto, planInfo);
                        }
                    }
                } catch (Exception e) {
                    System.err.println("Error al procesar cobro exitoso: " + mensaje);
                }
            } else if (mensaje.contains("El pago") && mensaje.contains("falló")) {
                // Extraer información de cobro fallido
                try {
                    String[] partes = mensaje.split("\\$");
                    if (partes.length >= 2) {
                        String montoStr = partes[1].split(" ")[0];
                        double monto = Double.parseDouble(montoStr);
                        observer.notificarCobroFallido(this.nombre, monto);
                    }
                } catch (Exception e) {
                    System.err.println("Error al procesar cobro fallido: " + mensaje);
                }
            }
        }
    }
    
    /**
     * Notifica a un Usuario los meses que ha contratado una suscripción a un servicio
     * @param usuario Usuario a quien va dirigida la notificación
     */
    public void notificarMesesUso(Observer usuario) {
        if (usuario != null) {
            HistorialServicio historial = historialesUsuarios.get(usuario);
            if (historial != null) {
                int meses = historial.obtenerTotalMeses();
                String mensaje = String.format("%s, llevas %d meses usando %s", 
                    ((Usuario)usuario).obtenerNombre(), meses, this.nombre);
                notificarObserver(usuario, mensaje);
            }
        }
    }

    /**
     * Notifica las recomendaciones a un Usuario de un servicio
     * @param usuario Usuario a quien va dirigida la notificación
     * @param mes El mes actual para obtener la recomendación correspondiente
     */
    public void notificarRecomendacion(Observer usuario, int mes) {
        if (usuario != null) {
            String recomendacion = obtenerRecomendacion(mes);
            if (recomendacion != null && !recomendacion.isEmpty()) {
                String mensaje = String.format("%s, recomendación del mes %d en %s: %s", 
                    ((Usuario)usuario).obtenerNombre(), mes, this.nombre, recomendacion);
                notificarObserver(usuario, mensaje);
            }
        }
    }

    /**
     * Notifica la bienvenida al Usuario de un servicio
     * @param usuario Usuario a quien va dirigida la notificación
     * @param esRenovacion true si es renovación, false si es nueva suscripción
     */
    public void notificarBienvenida(Observer usuario, boolean esRenovacion) {
        if (usuario != null) {
            String mensaje;
            if (esRenovacion) {
                mensaje = String.format("Bienvenido de vuelta %s", ((Usuario)usuario).obtenerNombre());
            } else {
                mensaje = String.format("%s, Bienvenido. Gracias por contratar %s", 
                    ((Usuario)usuario).obtenerNombre(), this.nombre);
            }
            notificarObserver(usuario, mensaje);
        }
    }

    /**
     * Notifica al Usuario que la renta no se pudo realizar por fondos insuficientes.
     * @param usuario Usuario a quien va dirigida la notificación
     */
    public void notificarSaldoInsuficiente(Observer usuario) {
        if (usuario != null) {
            String mensaje = String.format("Fondos insuficientes para el servicio de %s", this.nombre);
            notificarObserver(usuario, mensaje);
        }
    }
    
    /**
     * Notifica la despedida a un Usuario de un Servicio
     * @param usuario Usuario a quien va dirigida la notificación
     */
    public void notificarDespedida(Observer usuario) {
        if (usuario != null) {
            String mensaje = String.format("Lamentamos que dejes el servicio %s", ((Usuario)usuario).obtenerNombre());
            notificarObserver(usuario, mensaje);
        }
    }

    /**
     * Notifica a un Usuario que ya no se encuentra suscrito como para realizar acciones de un suscriptor
     * @param usuario Usuario a quien va dirigida la notificación
     */
    public void notificarUsuarioNoSuscrito(Observer usuario) {
        if (usuario != null) {
            String mensaje = String.format("Usuario %s no está suscrito al servicio %s", 
                ((Usuario)usuario).obtenerNombre(), this.nombre);
            notificarObserver(usuario, mensaje);
        }
    }

    /**
     * Notifica a un Usuario que ya se encuentra suscrito
     * @param usuario Usuario a quien va dirigida la notificación
     */
    public void notificarUsuarioYaSuscrito(Observer usuario) {
        if (usuario != null) {
            String mensaje = String.format("Usuario %s ya está suscrito al servicio %s", 
                ((Usuario)usuario).obtenerNombre(), this.nombre);
            notificarObserver(usuario, mensaje);
        }
    }

    /**
     * Método para procesar el cobro mensual de un usuario específico.   
     *  
     * Este método está implementado en la clase abstracta para proporcionar
     * una funcionalidad común que todas las subclases pueden usar directamente.
     * Las subclases pueden sobrescribir este método si requieren un comportamiento
     * diferente.
     * 
     * @param usuario Usuario a quien realizarle el cobro
     * @return boolean Indicador del éxito de realizar un cobro
     */
    public boolean solicitarCobroMensual(Observer usuario){
        if(usuario == null){
            return false;
        }

        if(!this.tieneSuscripcionActiva(usuario)){
            this.notificarUsuarioNoSuscrito(usuario);
            return false;
        }
        
        HistorialServicio historialUsuario = this.historialesUsuarios.get(usuario);
        if(historialUsuario == null) {
            return false;
        }
        
        EstrategiaCobro planVigente = historialUsuario.obtenerEstrategia();
        if(planVigente == null) {
            return false;
        }

        int mesesUso = historialUsuario.obtenerTotalMeses();
        // Para el cobro, usamos mesesUso + 1 ya que estamos cobrando el siguiente mes
        double fondosRequeridos = planVigente.calcularCosto(mesesUso + 1);
        CuentaBanco cuentaBancoUsuario = ((Usuario)usuario).obtenerCuentaBanco();
        
        // Envío de solicitud de cobro y respuesta
        String mensajeSolicitudCobro = "Cobro de mes " + (mesesUso + 1) + " por servicio " + this.nombre + ".";
        boolean cobroExitoso = cuentaBancoUsuario.procesarSolicitudCobro(this, fondosRequeridos, mensajeSolicitudCobro);
        
        if(!cobroExitoso){
            // Si el cobro falló, notificar saldo insuficiente y cancelar suscripción
            this.notificarSaldoInsuficiente(usuario);
            this.cancelarSuscripcionUsuario(usuario);
        } else {
            // Si el cobro fue exitoso, incrementar meses y enviar notificaciones
            historialUsuario.incrementarMeses();
            this.notificarMesesUso(usuario);
            this.notificarRecomendacion(usuario, mesesUso + 1);
            
            // Notificar cobro exitoso con detalles
            String planDescripcion = planVigente.obtenerDescripcionPlan();
            String mensajeCobroExitoso = String.format("%s paga $%.2f por el servicio de %s para %s",
                ((Usuario)usuario).obtenerNombre(), fondosRequeridos, this.nombre, planDescripcion);
            notificarObserver(usuario, mensajeCobroExitoso);
        }
                                                     
        return cobroExitoso;
    }
    
    /**
     * Método para suscribir un usuario al Servicio.
     * 
     * Este método está implementado en la clase abstracta para proporcionar
     * una funcionalidad común que todas las subclases pueden usar directamente.
     * Las subclases pueden sobrescribir este método si requieren un comportamiento
     * diferente.

     * @param nuevoUsuario Usuario a suscribir
     * @param planUsuario Plan del servicio que se desea contratar
     * @return boolean Indicador del éxito de la suscripción
     */
    public boolean suscribirUsuario(Observer nuevoUsuario, EstrategiaCobro planUsuario){
        if (nuevoUsuario == null || planUsuario == null) {
            return false;
        }

        // Verifica que no esté ya suscrito
        if(this.tieneSuscripcionActiva(nuevoUsuario)){
            this.notificarUsuarioYaSuscrito(nuevoUsuario);
            return false;
        }

        // Verificar disponibilidad de estrategias antes de proceder
        List<String> estrategiasDisponibles = this.obtenerEstrategiasDisponibles();
        boolean planValido = false;
        for(String estrategia : estrategiasDisponibles) {
            if(estrategia.equals(planUsuario.obtenerDescripcionPlan())) {
                planValido = true;
                break;
            }
        }
        
        if(!planValido) {
            this.notificarPlanInvalido(nuevoUsuario);
            return false;
        }

        // Comparación de ingresos con cuota para rentar servicio
        CuentaBanco cuentaBancoUsuario = ((Usuario)nuevoUsuario).obtenerCuentaBanco();
        double saldoUsuario = cuentaBancoUsuario.obtenerSaldo();
        
        // Para nuevos usuarios, calcular costo del primer mes
        int mesesParaCalculo = 1;
        if(this.historialesUsuarios.containsKey(nuevoUsuario)) {
            // Para usuarios existentes, usar el total de meses acumulados
            HistorialServicio historialExistente = this.historialesUsuarios.get(nuevoUsuario);
            mesesParaCalculo = historialExistente.obtenerTotalMeses() + 1;
        }
        
        double fondosRequeridos = planUsuario.calcularCosto(mesesParaCalculo);
        
        if (saldoUsuario < fondosRequeridos){
            this.notificarSaldoInsuficiente(nuevoUsuario);
            return false;
        }

        boolean esRenovacion;	
        if(this.historialesUsuarios.containsKey(nuevoUsuario)){
            // Renovación de usuario
            HistorialServicio historialUsuario = this.historialesUsuarios.get(nuevoUsuario);
            
            // Activa la suscripción con el nuevo plan
            historialUsuario.activar(planUsuario);
            esRenovacion = true;
        }
        else {
            // Nuevo usuario - crear historial
            HistorialServicio nuevoHistorial = this.crearHistorialUsuario((Usuario)nuevoUsuario, planUsuario);
            this.historialesUsuarios.put(nuevoUsuario, nuevoHistorial);
            esRenovacion = false;
        }
        
        // Requerimientos cumplidos: adición de nuevo observer	
        this.agregarObserver(nuevoUsuario);	
        this.notificarBienvenida(nuevoUsuario, esRenovacion);
        this.notificarRecomendacion(nuevoUsuario, 1); // Recomendación del primer mes
        return true;
    }

    /**
     * Notifica a un Usuario que el plan proporcionado no es válido para este servicio
     * @param usuario Usuario a quien va dirigida la notificación
     */
    public void notificarPlanInvalido(Observer usuario) {
        if (usuario != null) {
            String mensaje = String.format("Plan inválido para el servicio %s", this.nombre);
            notificarObserver(usuario, mensaje);
        }
    }

    /**
     *  Método para cancelar la suscripción de un usuario, ya sea porque así lo quiso o no tiene dinero.
     *  
     * Este método está implementado en la clase abstracta para proporcionar
     * una funcionalidad común que todas las subclases pueden usar directamente.
     * Las subclases pueden sobrescribir este método si requieren un comportamiento
     * diferente.
     * @param usuario Usuario a quien cancelarle la suscripción
     * @return boolean Indicador del éxito de la cancelación
     */
    public boolean cancelarSuscripcionUsuario(Observer usuario){
        if (usuario == null) {
            return false;
        }
        if(!this.tieneSuscripcionActiva(usuario)){
            this.notificarUsuarioNoSuscrito(usuario);
            return false;
        }
        
        this.removerObserver(usuario);
        HistorialServicio historialUsuario = this.historialesUsuarios.get(usuario);
        if(historialUsuario != null) {
            historialUsuario.desactivar();
        }
        this.notificarDespedida(usuario);
        return true;
    }

    /**
     * Método para cambiar el plan actual del Usuario
     * 
     * Este método está implementado en la clase abstracta para proporcionar
     * una funcionalidad común que todas las subclases pueden usar directamente.
     * Las subclases pueden sobrescribir este método si requieren un comportamiento
     * diferente.
     * 
     * @param usuario Usuario a quien cambiarle el plan
     * @param nuevoPlan Nueva EstrategiaCobro a asociar al usuario.
     * @return boolean Indicador del éxito del cambio de plan
     */
    public boolean cambiarPlanUsuario(Observer usuario, EstrategiaCobro nuevoPlan){
        if (usuario == null || nuevoPlan == null) {
            return false;
        }
        if(!this.tieneSuscripcionActiva(usuario)){
            this.notificarUsuarioNoSuscrito(usuario);
            return false;
        }

        // Verificar que el plan sea válido para este servicio
        List<String> estrategiasDisponibles = this.obtenerEstrategiasDisponibles();
        boolean planValido = false;
        for(String estrategia : estrategiasDisponibles) {
            if(estrategia.equals(nuevoPlan.obtenerDescripcionPlan())) {
                planValido = true;
                break;
            }
        }
        
        if(!planValido) {
            this.notificarPlanInvalido(usuario);
            return false;
        }

        // Hace un intento de cambio de plan
        HistorialServicio historialUsuario = this.historialesUsuarios.get(usuario);
        if(historialUsuario == null) {
            return false;
        }
        
        try {
            historialUsuario.cambiarEstrategia(nuevoPlan);
            return true;
        } catch (Exception e) {
            this.notificarPlanInvalido(usuario);
            return false;
        }
    }

    /**
     * Obtiene el historial del usuario. 
     * 
     * Este método está implementado en la clase abstracta para proporcionar
     * una funcionalidad común que todas las subclases pueden usar directamente.
     * Las subclases pueden sobrescribir este método si requieren un comportamiento
     * diferente.
     * @param usuario Usuario de quien se quiere obtener el historial
     * @return HistorialServicio Historial asociado al usuario, null si no existe
     */
    public HistorialServicio obtenerHistorialUsuario(Observer usuario) {
        return this.historialesUsuarios.get(usuario);
    }

    /**
     *  Crea el historial para un Usuario. 
     * 
     * Este método está implementado en la clase abstracta para proporcionar
     * una funcionalidad común que todas las subclases pueden usar directamente.
     * Las subclases pueden sobrescribir este método si requieren un comportamiento
     * diferente.
     * @param usuario Usuario a quien se le quiere crear un historial
     * @param planUsuario Plan a asignar al usuario al momento de crearlo
     * @return HistorialServicio nuevo historial creado asociado a la suscripción del usuario
     */
    protected HistorialServicio crearHistorialUsuario(Usuario usuario, EstrategiaCobro planUsuario) {
        HistorialServicio nuevoHistorialUsuario = new HistorialServicio(this.nombre);
        nuevoHistorialUsuario.activar(planUsuario);
        return nuevoHistorialUsuario;
    }

    /**
     * Verifica si un usuario tiene una suscripción de un servicio activa
     * 
     * Este método está implementado en la clase abstracta para proporcionar
     * una funcionalidad común que todas las subclases pueden usar directamente.
     * Las subclases pueden sobrescribir este método si requieren un comportamiento
     * diferente.
     * @param usuario Usuario de quien se busca saber si tiene suscripción activa
     * @return boolean Indicador de si el usuario está suscrito o no
     */
    public boolean tieneSuscripcionActiva(Observer usuario){
        return this.observers.contains(usuario);
    } 

    /**
     * Obtiene las estrategias de cobro disponibles para este servicio específico
     * @return List<String> Lista con las descripciones de las estrategias de cobro disponibles
     */
    public abstract List<String> obtenerEstrategiasDisponibles();    

    /**
     * Obtiene la recomendación específica para un mes dado
     * @param mes El número de mes (1-12) para el cual se solicita la recomendación
     * @return String La recomendación para el mes especificado
     */
    public abstract String obtenerRecomendacion(int mes);

    /**
     * Inicializa las recomendaciones mensuales específicas del servicio.
     * Este método debe ser llamado en el constructor de cada servicio concreto
     * para llenar el mapa de recomendaciones por mes.
     */
    public abstract void inicializarRecomendaciones();

    /**
     * Obtiene el nombre del servicio
     * @return String el nombre del Servicio
     */
    public String obtenerNombreServicio(){
        return this.nombre;
    }
}
