package simulacion;

import java.util.List;

import estrategias.MomazonPremium;

import java.util.ArrayList;


import modelo.Usuario;
import patrones.EstrategiaCobro;
import servicios.HVOMax;
import servicios.MomazonPrime;
import servicios.Servicio;
import servicios.Spootify;
import servicios.Memeflix;
import utilidades.GestorArchivos;

/**
 * Clase para orquestar toda la interacción temporal del sistema, 
 * ejecutando los comportamientos específicos de cada usuario en cada mes 
 * según las especificaciones de la práctica
 * 
 * Administra la lista de usuarios, los servicios disponibles, el registro de eventos 
 * de la simulación y la escritura de reportes en archivos.
 * 
 * Esta clase modela un escenario en el que distintos usuarios aplican 
 * comportamientos (como Alicia, Bob, etc.) a lo largo de los meses simulados.
 * 
 * @author Equipo-Polimorfo  
 */
public class Simulacion {

    /**Lista de usuarios registrados en la simulación.*/
    private List<Usuario> usuarios;

    /**Lista de servicios disponibles en la simulación.*/
    private List<Servicio> servicios;

    /** Número que representa el mes actual dentro de la simulación. [1-12]*/
    private int mesActual;

    /** Registro completo de todas las operaciones realizadas en la simulación.*/
    private List<String> registroCompleto;

    /** Gestor para todas las transacciones hechas en cada mes y para cada cliente.*/
    private GestorArchivos gestorArchivos;

    /**
     * Constructor de la simulación.
     *
     * @param mesActual mes inicial en el que comienza la simulación.
     */
    public Simulacion(int mesActual){
        this.mesActual = mesActual;
    }

    /**
     * Inicializa la lista de usuarios que participarán en la simulación.
     * debe agregar a cada uno de los usuarios a la lista, con su respectivo dinero incial
     */
    public void inicializarUsuarios(){
        usuarios = new ArrayList<>();
        usuarios.add(new Usuario("Alicia", 15000.00));
        usuarios.add(new Usuario("Bob", 2400.00));
        usuarios.add(new Usuario("Cesar", 5000.00));
        usuarios.add(new Usuario("diego", 9000.00));
        usuarios.add(new Usuario("Erika", 10000.00));
        usuarios.add(new Usuario("Fausto", 5000.00));
    }

    /**
     * Inicializa la lista de servicios que estarán disponibles en la simulación.
     */
    public void inicialzarServicios(){
        servicios = new ArrayList<>();
        //se debe crear una instancia de cada servico y agregarla a servicios
        Servicio HVOMax = new HVOMax("HVO Max");
        servicios.add(HVOMax);

        Servicio Memeflix = new Memeflix("Memeflix");
        servicios.add(Memeflix);

        Servicio MomazonPrime = new MomazonPrime("MomazonPrime");
        servicios.add(MomazonPrime);
        
        Servicio Spootify = new Spootify("Spootify");
        servicios.add(Spootify);

        Servicio Thisney = new servicios.Thisney("Thisney");
        servicios.add(Thisney);
    }

    /**
     * Ejecuta el flujo completo de la simulación.
     * Se debe inicializan las estructuras principales, rprocesar mes por mes,
     * generar el reportecompleto un archivio creo
     */
    public void ejecutarSimulacioh(){
        inicializarUsuarios();
        inicialzarServicios();
        registroCompleto = new ArrayList<>();
        gestorArchivos = new GestorArchivos();

        for(int mes = mesActual; mes<=12; mes++){
            procesarMes();
        }
        generarReporteCompleto();

    }

    /**
     * Procesa todas las operaciones correspondientes a un mes de la simulación.
     */
    public void procesarMes(){
        int mes = mesActual++;
        aplicarComportamientoAlicia(mes);
        aplicarComportamientoBob(mes);
        aplicarComportamientoCesar(mes);
        aplicarComportamientoDiego(mes);
        aplicarComportamientoErika(mes);
        aplicarComportamientoFausto(mes);

        

    }

    /**
     * Aplica el comportamiento definido para el usuario Alicia en un mes específico.
     *
     * @param mes mes en el que se ejecuta el comportamiento.
     */
    public void aplicarComportamientoAlicia(int mes){

    }

    /**
     * Aplica el comportamiento definido para el usuario Bob en un mes específico.
     *
     * @param mes mes en el que se ejecuta el comportamiento.
     */
    public void aplicarComportamientoBob(int mes){

    }

    /**
     * Aplica el comportamiento definido para el usuario Cesar en un mes específico.
     *
     * @param mes mes en el que se ejecuta el comportamiento.
     */
    public void aplicarComportamientoCesar(int mes){

    }

    /**
     * Aplica el comportamiento definido para el usuario Diego en un mes específico.
     *
     * @param mes mes en el que se ejecuta el comportamiento.
     */
    public void aplicarComportamientoDiego(int mes){

    }

    /**
     * Aplica el comportamiento definido para el usuario Erika en un mes específico.
     *
     * @param mes mes en el que se ejecuta el comportamiento.
     */
    public void aplicarComportamientoErika(int mes){

    }

    /**
     * Aplica el comportamiento definido para el usuario Fausto en un mes específico.
     *
     * @param mes mes en el que se ejecuta el comportamiento.
     */
    public void aplicarComportamientoFausto(int mes){

    }

    /**
     * Suscribe a un usuario a un servicio bajo una estrategia de cobro específica.
     * verifica que sea valido los parametros y verifica que tenga dineor disponible
     *
     * @param usuario usuario que se suscribe.
     * @param servicio servicio al que se suscribe el usuario.
     * @param estrategia estrategia de cobro utilizada.
     * @return {@code true} si la suscripción fue exitosa, 
     *          {@code false} en caso contrario.
     */
    public boolean suscribirUsuarioAServicio(Usuario usuario, Servicio servicio, EstrategiaCobro estrategia){
        if(usuario == null || servicio == null || estrategia == null){
            return false;
        }
        if(usuario.obtenerDineroDisponible()< estrategia.calcularCosto(mesActual)){
            return false;
        }
        try {
            servicio.agregarObserver(usuario, estrategia);
            return true;
        } catch (Exception e) {
            return false;        
        }
    }

    /**
     * Cancela la suscripción de un usuario a un servicio.
     *
     * @param usuario usuario cuya suscripción será cancelada.
     * @param servicio servicio al cual estaba suscrito el usuario.
     * @return {@code true} si la cancelación fue exitosa, 
     *          {@code false} en caso contrario.
     */
    public boolean cancelarSuscripcion(Usuario usuario, Servicio servicio){
        try {
            servicio.removerObserver(usuario);
            return true;   
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Cambia la estrategia de cobro de la suscripción de un usuario a un servicio.
     *
     * @param usuario usuario cuya suscripción será modificada.
     * @param servicio servicio al que está suscrito el usuario.
     * @param estrategia nueva estrategia de cobro a aplicar.
     * @return {@code true} si el cambio fue exitoso,
     *           {@code false} en caso contrario.
     */
    public boolean cambiarSuscripcionUsuario(Usuario usuario, Servicio servicio, EstrategiaCobro estrategia){
        if(usuario == null || estrategia == null){
            return false;
        }
        if (usuario.obtenerDineroDisponible() < estrategia.calcularCosto(mesActual)) {
            return false;   
        }
        try {
            servicio.cambiarPlanUsuario(usuario, estrategia);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    /**
     * Genera un reporte con todas las operaciones registradas en la simulación.
     */
    public void generarReporteCompleto(){

    }

    /**
     * Busca y devuelve un usuario a partir de su nombre.
     *
     * @param nombre nombre del usuario a buscar.
     * @return el objeto {@link Usuario} correspondiente, o 
     *              {@code null} si no existe.
     */
    public Usuario obtenerUsuario(String nombre){
        for (Usuario user: usuarios){
            if(user.obtenerNombre().equals(nombre)){
                return user;
            }
        }
        return null;
    }

    /**
     * Busca y devuelve un servicio a partir de su nombre.
     *
     * @param nombre nombre del servicio a buscar.
     * @return el objeto {@link Servicio} correspondiente, o 
     *              {@code null} si no existe.
     */
    public Servicio obtenerServicio(String nombre){
        for (Servicio servi: servicios){
            if(servi.obtenerNombreServicio().equals(nombre)){
                return servi;
            }
        }
        return null;
    }
}
