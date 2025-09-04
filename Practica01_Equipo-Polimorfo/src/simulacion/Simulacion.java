package simulacion;

import java.util.List;
import java.util.ArrayList;

import modelo.Usuario;
import modelo.ResultadoCobro;

import estrategias.*;

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
    private List<ResultadoCobro> registroCompleto;

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
     * Obtiene una instancia de estrategia de cobro basada en el nombre del servicio y descripción del plan.
     * 
     * @param nombreServicio nombre del servicio (ej: "Memeflix", "HVO Max")
     * @param descripcionPlan descripción del plan específico
     * @return instancia de la estrategia de cobro correspondiente, null si no se encuentra
     */
    private EstrategiaCobro obtenerEstrategiaPorNombre(String nombreServicio, String descripcionPlan) {
        switch (nombreServicio.toLowerCase()) {
            case "memeflix":
                switch (descripcionPlan.toLowerCase()) {
                    case "1 dispositivo":
                        return new MemefixUnDispositivo();
                    case "2 dispositivos":
                        return new MemefixDosDispositivos();
                    case "4 dispositivos":
                        return new MemefixCuatroDispositivos();
                    default:
                        return null;
                }
            case "momazonprime":
                switch (descripcionPlan.toLowerCase()) {
                    case "normal":
                        return new MomazonNormal();
                    case "premium":
                        return new MomazonPremium();
                    default:
                        return null;
                }
            case "spootify":
                switch (descripcionPlan.toLowerCase()) {
                    case "gratis":
                        return new SpootifyGratis();
                    case "premium":
                        return new SpootifyPremium();
                    default:
                        return null;
                }
            case "thisney":
                return new ThisneyPlan();
            case "hvo max":
                return new HVOMaxPlan();
            default:
                return null;
        }
    }

    /**
     * Procesa los cobros mensuales para todos los usuarios en todos los servicios.
     * Recopila todos los resultados de cobro en una lista para el registro.
     * 
     * @return lista de ResultadoCobro con todas las transacciones del mes
     */
    private List<ResultadoCobro> procesarCobrosMensuales() {
        List<ResultadoCobro> resultadosCobros = new ArrayList<>();
        
        for (Servicio servicio : servicios) {
            for (Usuario usuario : usuarios) {
                if (servicio.tieneSuscripcionActiva(usuario)) {
                    boolean cobroExitoso = servicio.solicitarCobroMensual(usuario);
                    
                    var historial = servicio.obtenerHistorialUsuario(usuario);
                    if (historial != null && historial.obtenerEstrategia() != null) {
                        int proximoMes = historial.obtenerTotalMeses() + 1;
                        double monto = historial.obtenerEstrategia().calcularCosto(proximoMes);
                        String descripcion = String.format("Cobro de mes %d por servicio %s.", 
                            proximoMes, servicio.obtenerNombreServicio());
                        
                        ResultadoCobro resultado = new ResultadoCobro(
                            usuario, 
                            servicio.obtenerNombreServicio(), 
                            monto, 
                            cobroExitoso, 
                            descripcion
                        );
                        resultadosCobros.add(resultado);
                    }
                }
            }
        }
        
        return resultadosCobros;
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
        usuarios.add(new Usuario("Diego", 9000.00));
        usuarios.add(new Usuario("Erika", 10000.00));
        usuarios.add(new Usuario("Fausto", 5000.00));
    }

    /**
     * Inicializa la lista de servicios que estarán disponibles en la simulación.
     */
    public void inicializarServicios(){
        servicios = new ArrayList<>();
	
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
     * Se debe inicializan las estructuras principales, procesar mes por mes,
     * generar el reportecompleto un archivio creo
     */
    public void ejecutarSimulacion(){
        inicializarUsuarios();
        inicializarServicios();
        registroCompleto = new ArrayList<>();
        gestorArchivos = new GestorArchivos();

        for(int mes = mesActual; mes<=12; mes++){
            procesarMes();
        }
        generarReporteCompleto();

    }

    /**
     * Procesa todas las operaciones correspondientes a un mes de la simulación.
     * Ejecuta las operaciones en el orden correcto: comportamientos de usuarios,
     * cobros mensuales, y registro de transacciones.
     */
    public void procesarMes(){
        int mes = mesActual++;
        
        System.out.println("\n=== PROCESANDO MES " + mes + " ===");
        
        aplicarComportamientoAlicia(mes);
        aplicarComportamientoBob(mes);
        aplicarComportamientoCesar(mes);
        aplicarComportamientoDiego(mes);
        aplicarComportamientoErika(mes);
        aplicarComportamientoFausto(mes);

        List<ResultadoCobro> transaccionesMes = procesarCobrosMensuales();
        
        if (transaccionesMes != null && !transaccionesMes.isEmpty()) {
            registroCompleto.addAll(transaccionesMes);
            
            long cobrosExitosos = transaccionesMes.stream()
                .mapToLong(t -> t.isExitoso() ? 1 : 0)
                .sum();
            long cobrosFallidos = transaccionesMes.size() - cobrosExitosos;
            double totalCobrado = transaccionesMes.stream()
                .filter(ResultadoCobro::isExitoso)
                .mapToDouble(ResultadoCobro::getMonto)
                .sum();
            
            System.out.println("Transacciones en mes " + mes + ": " + transaccionesMes.size());
            System.out.println("Cobros exitosos: " + cobrosExitosos);
            System.out.println("Cobros fallidos: " + cobrosFallidos);
            System.out.println("Total cobrado: $" + String.format("%.2f", totalCobrado));
        } else {
            System.out.println("No hubo transacciones en el mes " + mes);
        }
        
        System.out.println("=== FIN MES " + mes + " ===\n");
    }

    /**
     * Aplica el comportamiento definido para el usuario Alicia en un mes específico.
     * Alicia contrata todos los servicios con la versión más cara disponible desde el primer mes.
     *
     * @param mes mes en el que se ejecuta el comportamiento.
     */
    public void aplicarComportamientoAlicia(int mes){
        Usuario alicia = obtenerUsuario("Alicia");
        if (alicia == null) return;
        
        if (mes == 1) {
            
            Servicio memeflix = obtenerServicio("Memeflix");
            if (memeflix != null) {
                EstrategiaCobro estrategiaMemeflix = obtenerEstrategiaPorNombre("Memeflix", "4 dispositivos");
                if (estrategiaMemeflix != null) {
                    suscribirUsuarioAServicio(alicia, memeflix, estrategiaMemeflix);
                }
            }
            
            Servicio momazon = obtenerServicio("MomazonPrime");
            if (momazon != null) {
                EstrategiaCobro estrategiaMomazon = obtenerEstrategiaPorNombre("MomazonPrime", "premium");
                if (estrategiaMomazon != null) {
                    suscribirUsuarioAServicio(alicia, momazon, estrategiaMomazon);
                }
            }
            
            Servicio spootify = obtenerServicio("Spootify");
            if (spootify != null) {
                EstrategiaCobro estrategiaSpootify = obtenerEstrategiaPorNombre("Spootify", "premium");
                if (estrategiaSpootify != null) {
                    suscribirUsuarioAServicio(alicia, spootify, estrategiaSpootify);
                }
            }
            
            Servicio thisney = obtenerServicio("Thisney");
            if (thisney != null) {
                EstrategiaCobro estrategiaThisney = obtenerEstrategiaPorNombre("Thisney", "plan único");
                if (estrategiaThisney != null) {
                    suscribirUsuarioAServicio(alicia, thisney, estrategiaThisney);
                }
            }
            
            Servicio hvoMax = obtenerServicio("HVO Max");
            if (hvoMax != null) {
                EstrategiaCobro estrategiaHVO = obtenerEstrategiaPorNombre("HVO Max", "plan único");
                if (estrategiaHVO != null) {
                    suscribirUsuarioAServicio(alicia, hvoMax, estrategiaHVO);
                }
            }
        }
    }

    /**
     * Aplica el comportamiento definido para el usuario Bob en un mes específico.
     * Bob contrata todos los servicios en versión cara inicialmente, pero los va cancelando
     * conforme se queda sin dinero.
     *
     * @param mes mes en el que se ejecuta el comportamiento.
     */
    public void aplicarComportamientoBob(int mes){
        Usuario bob = obtenerUsuario("Bob");
        if (bob == null) return;
        
        if (mes == 1) {
            
            Servicio memeflix = obtenerServicio("Memeflix");
            if (memeflix != null) {
                EstrategiaCobro estrategiaMemeflix = obtenerEstrategiaPorNombre("Memeflix", "4 dispositivos");
                if (estrategiaMemeflix != null) {
                    suscribirUsuarioAServicio(bob, memeflix, estrategiaMemeflix);
                }
            }
            
            Servicio momazon = obtenerServicio("MomazonPrime");
            if (momazon != null) {
                EstrategiaCobro estrategiaMomazon = obtenerEstrategiaPorNombre("MomazonPrime", "premium");
                if (estrategiaMomazon != null) {
                    suscribirUsuarioAServicio(bob, momazon, estrategiaMomazon);
                }
            }
            
            Servicio spootify = obtenerServicio("Spootify");
            if (spootify != null) {
                EstrategiaCobro estrategiaSpootify = obtenerEstrategiaPorNombre("Spootify", "premium");
                if (estrategiaSpootify != null) {
                    suscribirUsuarioAServicio(bob, spootify, estrategiaSpootify);
                }
            }
            
            Servicio thisney = obtenerServicio("Thisney");
            if (thisney != null) {
                EstrategiaCobro estrategiaThisney = obtenerEstrategiaPorNombre("Thisney", "plan único");
                if (estrategiaThisney != null) {
                    suscribirUsuarioAServicio(bob, thisney, estrategiaThisney);
                }
            }
            
            Servicio hvoMax = obtenerServicio("HVO Max");
            if (hvoMax != null) {
                EstrategiaCobro estrategiaHVO = obtenerEstrategiaPorNombre("HVO Max", "plan único");
                if (estrategiaHVO != null) {
                    suscribirUsuarioAServicio(bob, hvoMax, estrategiaHVO);
                }
            }
        }
        
        else if (mes == 3) {
            Servicio thisney = obtenerServicio("Thisney");
            if (thisney != null) {
                cancelarSuscripcion(bob, thisney);
            }
            
            Servicio hvoMax = obtenerServicio("HVO Max");
            if (hvoMax != null) {
                cancelarSuscripcion(bob, hvoMax);
            }
        }
        
        else if (mes == 4) {
            Servicio memeflix = obtenerServicio("Memeflix");
            if (memeflix != null) {
                cancelarSuscripcion(bob, memeflix);
            }
            
            Servicio momazon = obtenerServicio("MomazonPrime");
            if (momazon != null) {
                cancelarSuscripcion(bob, momazon);
            }
        }
    }

    /**
     * Aplica el comportamiento definido para el usuario Cesar en un mes específico.
     * César contrata inicialmente Thisney+ y HVO Max, luego agrega Spootify Premium en el mes 7.
     *
     * @param mes mes en el que se ejecuta el comportamiento.
     */
    public void aplicarComportamientoCesar(int mes){
        Usuario cesar = obtenerUsuario("Cesar");
        if (cesar == null) return;
        
        if (mes == 1) {
            
            Servicio thisney = obtenerServicio("Thisney");
            if (thisney != null) {
                EstrategiaCobro estrategiaThisney = obtenerEstrategiaPorNombre("Thisney", "plan único");
                if (estrategiaThisney != null) {
                    suscribirUsuarioAServicio(cesar, thisney, estrategiaThisney);
                }
            }
            
            Servicio hvoMax = obtenerServicio("HVO Max");
            if (hvoMax != null) {
                EstrategiaCobro estrategiaHVO = obtenerEstrategiaPorNombre("HVO Max", "plan único");
                if (estrategiaHVO != null) {
                    suscribirUsuarioAServicio(cesar, hvoMax, estrategiaHVO);
                }
            }
        }
        
        else if (mes == 7) {
            Servicio spootify = obtenerServicio("Spootify");
            if (spootify != null) {
                EstrategiaCobro estrategiaSpootify = obtenerEstrategiaPorNombre("Spootify", "premium");
                if (estrategiaSpootify != null) {
                    suscribirUsuarioAServicio(cesar, spootify, estrategiaSpootify);
                }
            }
        }
    }

    /**
     * Aplica el comportamiento definido para el usuario Diego en un mes específico.
     * Diego se suscribe inicialmente a HVO Max, Momazon Premium y Spootify gratis,
     * luego va agregando y cambiando servicios en meses posteriores.
     *
     * @param mes mes en el que se ejecuta el comportamiento.
     */
    public void aplicarComportamientoDiego(int mes){
        Usuario diego = obtenerUsuario("Diego");
        if (diego == null) return;
        
        if (mes == 1) {
            
            Servicio hvoMax = obtenerServicio("HVO Max");
            if (hvoMax != null) {
                EstrategiaCobro estrategiaHVO = obtenerEstrategiaPorNombre("HVO Max", "plan único");
                if (estrategiaHVO != null) {
                    suscribirUsuarioAServicio(diego, hvoMax, estrategiaHVO);
                }
            }
            
            Servicio momazon = obtenerServicio("MomazonPrime");
            if (momazon != null) {
                EstrategiaCobro estrategiaMomazon = obtenerEstrategiaPorNombre("MomazonPrime", "premium");
                if (estrategiaMomazon != null) {
                    suscribirUsuarioAServicio(diego, momazon, estrategiaMomazon);
                }
            }
            
            Servicio spootify = obtenerServicio("Spootify");
            if (spootify != null) {
                EstrategiaCobro estrategiaSpootify = obtenerEstrategiaPorNombre("Spootify", "gratis");
                if (estrategiaSpootify != null) {
                    suscribirUsuarioAServicio(diego, spootify, estrategiaSpootify);
                }
            }
        }
        
        else if (mes == 6) {
            Servicio thisney = obtenerServicio("Thisney");
            if (thisney != null) {
                EstrategiaCobro estrategiaThisney = obtenerEstrategiaPorNombre("Thisney", "plan único");
                if (estrategiaThisney != null) {
                    suscribirUsuarioAServicio(diego, thisney, estrategiaThisney);
                }
            }
        }
        
        else if (mes == 7) {
            
            Servicio memeflix = obtenerServicio("Memeflix");
            if (memeflix != null) {
                EstrategiaCobro estrategiaMemeflix = obtenerEstrategiaPorNombre("Memeflix", "1 dispositivo");
                if (estrategiaMemeflix != null) {
                    suscribirUsuarioAServicio(diego, memeflix, estrategiaMemeflix);
                }
            }
            
            Servicio spootify = obtenerServicio("Spootify");
            if (spootify != null) {
                EstrategiaCobro estrategiaSpootifyPremium = obtenerEstrategiaPorNombre("Spootify", "premium");
                if (estrategiaSpootifyPremium != null) {
                    cambiarSuscripcionUsuario(diego, spootify, estrategiaSpootifyPremium);
                }
            }
            
            Servicio momazon = obtenerServicio("MomazonPrime");
            if (momazon != null) {
                cancelarSuscripcion(diego, momazon);
            }
        }
    }

    /**
     * Aplica el comportamiento definido para el usuario Erika en un mes específico.
     * Erika tiene un comportamiento complejo: se suscribe inicialmente a varios servicios,
     * realiza cancelaciones y cambios en el mes 3, cancela todo en el mes 6,
     * y vuelve a suscribirse a algunos servicios en el mes 10.
     *
     * @param mes mes en el que se ejecuta el comportamiento.
     */
    public void aplicarComportamientoErika(int mes){
        Usuario erika = obtenerUsuario("Erika");
        if (erika == null) return;
        
        if (mes == 1) {
            
            Servicio memeflix = obtenerServicio("Memeflix");
            if (memeflix != null) {
                EstrategiaCobro estrategiaMemeflix = obtenerEstrategiaPorNombre("Memeflix", "4 dispositivos");
                if (estrategiaMemeflix != null) {
                    suscribirUsuarioAServicio(erika, memeflix, estrategiaMemeflix);
                }
            }
            
            Servicio spootify = obtenerServicio("Spootify");
            if (spootify != null) {
                EstrategiaCobro estrategiaSpootify = obtenerEstrategiaPorNombre("Spootify", "gratis");
                if (estrategiaSpootify != null) {
                    suscribirUsuarioAServicio(erika, spootify, estrategiaSpootify);
                }
            }
            
            Servicio hvoMax = obtenerServicio("HVO Max");
            if (hvoMax != null) {
                EstrategiaCobro estrategiaHVO = obtenerEstrategiaPorNombre("HVO Max", "plan único");
                if (estrategiaHVO != null) {
                    suscribirUsuarioAServicio(erika, hvoMax, estrategiaHVO);
                }
            }
        }
        
        else if (mes == 3) {
            
            Servicio hvoMax = obtenerServicio("HVO Max");
            if (hvoMax != null) {
                cancelarSuscripcion(erika, hvoMax);
            }
            
            Servicio thisney = obtenerServicio("Thisney");
            if (thisney != null) {
                EstrategiaCobro estrategiaThisney = obtenerEstrategiaPorNombre("Thisney", "plan único");
                if (estrategiaThisney != null) {
                    suscribirUsuarioAServicio(erika, thisney, estrategiaThisney);
                }
            }
        }
        
        else if (mes == 6) {
            
            Servicio memeflix = obtenerServicio("Memeflix");
            if (memeflix != null) {
                cancelarSuscripcion(erika, memeflix);
            }
            
            Servicio spootify = obtenerServicio("Spootify");
            if (spootify != null) {
                cancelarSuscripcion(erika, spootify);
            }
            
            Servicio thisney = obtenerServicio("Thisney");
            if (thisney != null) {
                cancelarSuscripcion(erika, thisney);
            }
        }
        
        else if (mes == 10) {
            
            Servicio momazon = obtenerServicio("MomazonPrime");
            if (momazon != null) {
                EstrategiaCobro estrategiaMomazon = obtenerEstrategiaPorNombre("MomazonPrime", "premium");
                if (estrategiaMomazon != null) {
                    suscribirUsuarioAServicio(erika, momazon, estrategiaMomazon);
                }
            }
            
            Servicio hvoMax = obtenerServicio("HVO Max");
            if (hvoMax != null) {
                EstrategiaCobro estrategiaHVO = obtenerEstrategiaPorNombre("HVO Max", "plan único");
                if (estrategiaHVO != null) {
                    suscribirUsuarioAServicio(erika, hvoMax, estrategiaHVO);
                }
            }
            
            Servicio thisney = obtenerServicio("Thisney");
            if (thisney != null) {
                EstrategiaCobro estrategiaThisney = obtenerEstrategiaPorNombre("Thisney", "plan único");
                if (estrategiaThisney != null) {
                    suscribirUsuarioAServicio(erika, thisney, estrategiaThisney);
                }
            }
        }
    }

    /**
     * Aplica el comportamiento definido para el usuario Fausto en un mes específico.
     * Fausto tiene un patrón de suscripciones y cancelaciones múltiples:
     * inicialmente se suscribe a servicios, luego los cancela y cambia,
     * vuelve a suscribirse y finalmente cancela todo.
     *
     * @param mes mes en el que se ejecuta el comportamiento.
     */
    public void aplicarComportamientoFausto(int mes){
        Usuario fausto = obtenerUsuario("Fausto");
        if (fausto == null) return;
        
        if (mes == 1) {
            
            Servicio thisney = obtenerServicio("Thisney");
            if (thisney != null) {
                EstrategiaCobro estrategiaThisney = obtenerEstrategiaPorNombre("Thisney", "plan único");
                if (estrategiaThisney != null) {
                    suscribirUsuarioAServicio(fausto, thisney, estrategiaThisney);
                }
            }
            
            Servicio hvoMax = obtenerServicio("HVO Max");
            if (hvoMax != null) {
                EstrategiaCobro estrategiaHVO = obtenerEstrategiaPorNombre("HVO Max", "plan único");
                if (estrategiaHVO != null) {
                    suscribirUsuarioAServicio(fausto, hvoMax, estrategiaHVO);
                }
            }
        }
        
        else if (mes == 3) {
            
            Servicio thisney = obtenerServicio("Thisney");
            if (thisney != null) {
                cancelarSuscripcion(fausto, thisney);
            }
            
            Servicio hvoMax = obtenerServicio("HVO Max");
            if (hvoMax != null) {
                cancelarSuscripcion(fausto, hvoMax);
            }
            
            Servicio memeflix = obtenerServicio("Memeflix");
            if (memeflix != null) {
                EstrategiaCobro estrategiaMemeflix = obtenerEstrategiaPorNombre("Memeflix", "1 dispositivo");
                if (estrategiaMemeflix != null) {
                    suscribirUsuarioAServicio(fausto, memeflix, estrategiaMemeflix);
                }
            }
        }
        
        else if (mes == 5) {
            
            Servicio thisney = obtenerServicio("Thisney");
            if (thisney != null) {
                EstrategiaCobro estrategiaThisney = obtenerEstrategiaPorNombre("Thisney", "plan único");
                if (estrategiaThisney != null) {
                    suscribirUsuarioAServicio(fausto, thisney, estrategiaThisney);
                }
            }
            
            Servicio hvoMax = obtenerServicio("HVO Max");
            if (hvoMax != null) {
                EstrategiaCobro estrategiaHVO = obtenerEstrategiaPorNombre("HVO Max", "plan único");
                if (estrategiaHVO != null) {
                    suscribirUsuarioAServicio(fausto, hvoMax, estrategiaHVO);
                }
            }
        }
        
        else if (mes == 6) {
            
            Servicio memeflix = obtenerServicio("Memeflix");
            if (memeflix != null) {
                cancelarSuscripcion(fausto, memeflix);
            }
            
            Servicio thisney = obtenerServicio("Thisney");
            if (thisney != null) {
                cancelarSuscripcion(fausto, thisney);
            }
            
            Servicio hvoMax = obtenerServicio("HVO Max");
            if (hvoMax != null) {
                cancelarSuscripcion(fausto, hvoMax);
            }
        }
    }

    /**
     * Suscribe a un usuario a un servicio bajo una estrategia de cobro específica.
     * verifica que sea valido los parametros y verifica que tenga dinero disponible
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
            servicio.suscribirUsuario(usuario, estrategia);
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
            servicio.cancelarSuscripcionUsuario(usuario);
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
     * Utiliza el GestorArchivos para crear archivos detallados con todas las transacciones
     * y el estado final de los usuarios.
     */
    public void generarReporteCompleto(){
        if (gestorArchivos == null || registroCompleto == null) {
            System.err.println("Error: No se puede generar el reporte. Gestor de archivos o registro no inicializados.");
            return;
        }
        
        System.out.println("\n=== GENERANDO REPORTES FINALES ===");
        
        try {
            String archivoCompleto = "reporte_simulacion_completo.txt";
            if (registroCompleto != null && !registroCompleto.isEmpty()) {
                System.out.println("Generando reporte con " + registroCompleto.size() + " transacciones registradas...");
                gestorArchivos.escribirTransaccionesCompletas(registroCompleto, archivoCompleto);
            } else {
                System.out.println("No hay transacciones para reportar.");
                gestorArchivos.escribirTransaccionesCompletas(new ArrayList<>(), archivoCompleto);
            }
            
            String archivoEstadoFinal = "estado_final_usuarios.txt";
            gestorArchivos.escribirEstadoFinalUsuarios(usuarios, archivoEstadoFinal);
            
            if (registroCompleto != null) {
                for (int mes = 1; mes <= 12; mes++) {
                    String archivoMensual = String.format("resumen_mes_%02d.txt", mes);
                    gestorArchivos.escribirResumenMensual(mes, registroCompleto, archivoMensual);
                }
            }
            
            mostrarResumenFinal();
            
        } catch (Exception e) {
            System.err.println("Error al generar reportes: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("=== REPORTES GENERADOS EXITOSAMENTE ===\n");
    }
    
    /**
     * Muestra un resumen estadístico final de la simulación en consola.
     */
    private void mostrarResumenFinal() {
        System.out.println("\n▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
        System.out.println("                 RESUMEN FINAL DE LA SIMULACIÓN");
        System.out.println("▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
        
        double totalDineroInicial = 0;
        double totalDineroFinal = 0;
        
        for (Usuario usuario : usuarios) {
            switch (usuario.obtenerNombre()) {
                case "Alicia": totalDineroInicial += 15000; break;
                case "Bob": totalDineroInicial += 2400; break;
                case "Cesar": totalDineroInicial += 5000; break;
                case "Diego": totalDineroInicial += 9000; break;
                case "Erika": totalDineroInicial += 10000; break;
                case "Fausto": totalDineroInicial += 5000; break;
            }
            totalDineroFinal += usuario.obtenerDineroDisponible();
        }
        
        double totalGastado = totalDineroInicial - totalDineroFinal;
        
        System.out.printf("Total de usuarios: %d%n", usuarios.size());
        System.out.printf("Dinero inicial total: $%.2f%n", totalDineroInicial);
        System.out.printf("Dinero final total: $%.2f%n", totalDineroFinal);
        System.out.printf("Total gastado en servicios: $%.2f%n", totalGastado);
        System.out.printf("Total de servicios disponibles: %d%n", servicios.size());
        
        System.out.println("\nESTADO FINAL POR USUARIO:");
        for (Usuario usuario : usuarios) {
            System.out.printf("- %s: $%.2f disponibles%n", 
                usuario.obtenerNombre(), usuario.obtenerDineroDisponible());
        }
        
        System.out.println("▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
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
