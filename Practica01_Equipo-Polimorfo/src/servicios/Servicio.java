package servicios;

import java.util.List;
import java.util.Map;

import patrones.Observer;
import patrones.Subject;
import patrones.EstrategiaCobro;
import modelo.Usuario;
import modelo.HistorialServicio;
import modelo.ResultadoCobro;
import modelo.CuentaBanco;



public abstract class Servicio implements Subject {

    //EN EL DIAGRAMA DE CLASES LOS ATRIBUTOS SON PRIVADOS, NO PROTECT CHECAR

    /**Nombre del servicio */
    protected String nombre;
    /**Lista de observadores */
    protected List<Observer> observers;
    /**Lista de contenidos audiovisuales disponibles */
    protected List<String> audiovisuales;
    /**Recomendaciones por mes<MES,RECOMENDACIONES> */
    protected Map<Integer, List<String>> recomendacionesPorMes;
    /**Historial de los usuarios del servicio */
    protected Map<Integer, List<String>> historialesUsuarios;

    /**
     * Constructor para inicializar los atributos de Servicio... <code> \n </code>
     * observers y recomendacionesPorMes se inicializarán en la implementación
     * 
     * @param nombre nombre de servicio a inicializar
     */
    public Servicio(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Agrega un observer al servicio
     * lo debe override un servicio
     * 
     * @param observer observador que se agregara a servicio
     */
    protected void agregarObserver(Observer usuario, EstrategiaCobro planUsuario) {
	observers.add(observer);
    }

    /**
     * Remueve un observador del servicio
     * lo debe override un servicio
     * @param observer Observador que se removera del servicio
     */
    protected void removerObserver(Observer usuario){
	this.observers.remove(observer);
    }
    
    /**
     * Notifica a un Usuario los meses que ha contradado una suscripción a un servicio
     * @param usuario Usuario a quien va dirigida la notificación
     * 
     */
    public abstract void notificarMesesUso(Observer usuario);

    /**
     * Notifica las recomendaciones a un Usuario de un servicio
     * @param usuario Usuario a quien va dirigida la notificación
     */
    public abstract void notificarRecomendacion(Observer usuario);

    /**
     * Notifica la bienvenida al Usuario de un servicio
     * @param usuario Usuario a quien va dirigida la notificación
     * @param esRenovacion
     */
    public abstract void notificarBienvenida(Observer usuario, boolean esRenovacion);

    /**
     * Notifica al Usuario que la renta no se pudo realizar por fondos insuficientes.
     * @param usuario Usuario a quien va dirigida la notificación
     */
    public abstract void notificarSaldoInsuficiente(Observer usuario);
    
    /**
     * Notifica la despedida a un Usuario de un Servicio
     * @param usuario Usuario a quien va dirigida la notificación
     */
    public abstract void notificarDespedida(Observer usuario);


    /**
     * Notifica a un Usuario que ya no se encuentra suscrito como para realizar acciones de un suscriptor
     * @param usuario Usuario a quien va dirigida la notificación
     */
    public abstract void notificarUsuarioNoRegistrado(Observer usuario);

    /**
     * Notifica a un Usuario que ya se encuentra suscrito
     * @param usuario Usuario a quien va dirigida la notificación
     */
    public abstract void notificarSuscripcionYaActiva(Observer usuario);

    /**
     * Metodo para procesar el cobro mensual de todos los usuarios.   
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
	    this.notificarUsuarioNoRegistrado(usuario);
	    return false;
	}
	
	HistorialServicio historialUsuario = this.historialesUsuarios.get(usuario);
	EstrategiaCobro planVigente = historialUsuario.obtenerEstrategia();

	int mesesUso = historialUsuario.obtenerTotalMeses();
	double fondosRequeridos = planVigente.calcularCosto(mesesActividad);
	CuentaBanco cuentaBancoUsuario = historialUsuario.obtenerCuentaBancoUsuario();
	
	// Envío de solicitud de cobro y respuesta
	String mensajeSolicitudCobro =  "Cobro de mes " + mesesActividad+1 + " por servicio " + this.nombre + ".";
	boolean cobroExitoso = cuentaBancoUsuario.procesarSolicitudCobro(this, fondosRequeridos, mensajeSolicitud);
	if(cobroExitoso){
	    this.notificarSaldoInsuficiente(usuario);
	    this.cancelarSuscripcionUsuario(usuario);
       	} else {
	    this.notificarMesesUso(usuario);
	    this.notificarRecomendacion(usuario);
	}
									 
	return cobroExitoso;
    }
    
    /**
     * Metodo para Suscribir un usuario al Servicio.
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
        if (nuevoUsuario == null) {
	    return false;
	}

	// Verifica que no esté ya suscrito
	if(this.tieneSuscripcionActiva(nuevoUsuario)){
	    this.notificarSuscripcionYaActiva(nuevoUsuario);
	    return false;
	}

	// Comparación de ingresos con cuota para rentar servicio
	CuentaBanco cuentaBancoUsuario = usuario.obtenerCuentaBanco();
	double saldoUsuario = cuentaBancoUsuario.obtenerSaldo();
	double fondosRequeridos = planUsuario.calcularCosto(mesesActividadUsuario);
	
	if (saldoUsuario <= fondosRequeridos){
	    this.notificarSaldoInsuficiente(nuevoUsuario);
	    return false;
	}

	boolean esRenovacion;	
	if(this.historialesUsuarios.containsKey(nuevoUsuario)){
	    // Renovación de usuario
	    HistorialServicio historialUsuario = this.historialesUsuarios.get(nuevoUsuario);
	    
	    // Revisa que el plan sea de la empresa y lo activa.
	    boolean exitoActivacionPlan = historialUsuario.activar(planUsuario);
	    if (!exitoActivacionPlan){
		this.notificarPlanInvalido(nuevoUsuario);
		return;
	    }

	    esRenovacion = true;
	}
	else {
	    try{
		// Trata de crear un nuevo historial, y recibe una excepción si el plan no corresponde al servicio
		HistorialServicio nuevoHistorial = this.crearHistorialUsuario(cuentaBancoUsuario, planUsuario);
		this.historialesUsuarios.put(nuevoUsuario, nuevoHistorial);
	    }
	    catch (Exception e){
		this.notificarPlanInvalido(nuevoUsuario);
		return false;
	    }
	    esRenovacion = false;
	}
	
	// Requerimientos cumplidos: adición de nuevo observer	
	this.observers.add(nuevoUsuario);	
	this.notificarBienvenida(nuevoUsuario, esRenovacion);
	this.notificarRecomendacion(nuevoUsuario);
	return true;
    }

    /**
     *  Metodo para cancelar la Suscripcion de un usuario, ya sea por que asi lo quiso o no tiene dinero.
     *  
     * Este método está implementado en la clase abstracta para proporcionar
     * una funcionalidad común que todas las subclases pueden usar directamente.
     * Las subclases pueden sobrescribir este método si requieren un comportamiento
     * diferente.
     * @param usuario Usuario a quien cancelarle la suscripción
     * @return boolean Indicador del éxito de la cancelación
     */
    public boolean cancelarSuscripcionUsuario(Observer usuario){
	if (nuevoUsuario == null) {
	    return false;
	}
	if(!this.tieneSuscripcionActiva(usuario)){
	    this.notificarUsuarioNoRegistrado(usuario);
	    return false;
	}
	
	this.observers.remove(usuario);
	HistorialServicio historialUsuario = this.historialesUsuarios.get(usuario);
	historialUsuario.desactivar();
	this.notificarDespedida();
	return true;
    }

    /**
     * Metodo para cambiar el plan actual del Usuario
     * 
     * Este método está implementado en la clase abstracta para proporcionar
     * una funcionalidad común que todas las subclases pueden usar directamente.
     * Las subclases pueden sobrescribir este método si requieren un comportamiento
     * diferente.
     * 
     * @param usuario Usuario a quien cambiarle el plan
     * @param nuevoPlan Nueva EstrategiaCobro a asociar al usuario.
     * @return
     */
    public boolean cambiarPlanUsuario(Observer usuario, EstrategiaCobro nuevoPlan){
	if (nuevoUsuario == null) {
	    return false;
	}
	if(!this.tieneSuscripcionActiva(usuario)){
	    this.notificarUsuarioNoRegistrado(usuario);
	    return false;
	}

	// Hace un intento de cambio de plan, y devuelve falso si el plan no corresponde al servicio
	HistorialServicio historialUsuario = this.historialesUsuarios.get(usuario);
	boolean exitoCambioPlan = historialUsuario.cambiarEstrategia(nuevoPlan);

	if(!exitoCambioPlan){
	    this.notificarPlanInvalido(usuario);
	    return false;
	}
	
        return true;
    }

    /**
     * Obtiene el historial del usuario. 
     * 
     * Este método está implementado en la clase abstracta para proporcionar
     * una funcionalidad común que todas las subclases pueden usar directamente.
     * Las subclases pueden sobrescribir este método si requieren un comportamiento
     * diferente.
     * @param usuario Usuario de quien se quiere obtener el historial
     * @return HistorialServicio Historial asociado al usuario
     * @throws NullPointerException en caso de insertar valores inválidos a un Map
     */
    public HistorialServicio obtenerHistorialUsuario(Observer usuario) throws NullPointerException {
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
     * @throws Exception en caso de que el plan de cobro no pertenezca al servicio en cuestión
     */
    protected HistorialServicio crearHistorialUsuario(Usuario usuario, EstrategiaCobro planUsuario) throws Exception{
	return HistorialServicio nuevoHistorialUsuario = new HistorialServicio(usuario.cuentaBanco, EstrategiaCobro planUsuario);
    }

    /**
     * Verifica si un usuario tiene una suscripcion de un servicio activa
     * 
     * Este método está implementado en la clase abstracta para proporcionar
     * una funcionalidad común que todas las subclases pueden usar directamente.
     * Las subclases pueden sobrescribir este método si requieren un comportamiento
     * diferente.
     * @param usuario Usuario de quien se busca saber si tiene suscripción activa
     * @return boolean Indicador de si el usuario está suscrito o no
     */
    public boolean tieneSuscripcionActiva(Usuario usuario){
	return this.observers.contains(usuario);
    } 

    /**
     * Obtiene las estrategias de cobro disponibles
     * @return List<String> Estrategias de cobro disponibles
     */
    public abstract List<String> obtenerEstrategiasDisponibles();    

    /**
     * Obtiene el nombre
     * @return String el nombre del Servicio
     */
    public String obtenerNombreServicio(){
	return this.nombre;
    }
}
