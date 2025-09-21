package controladores;

import java.util.List;
import java.util.function.*;
import java.util.stream.*;
import java.util.concurrent.atomic.AtomicInteger;

import main.Academia.Academia;
import main.MundoNinja.Grupo;
import ui.MenuPrincipal;
import ui.GestorInteraccion;
import ui.MenuOpcion;
import main.Academia.Paquetes.GestorPaquetes;
import main.Academia.Paquetes.PaquetePersonalizadoBuilder;
import main.PaquetesHerramientas.PaquetesHerramientas;

/**
 * Controlador principal de la aplicación que coordina todos los componentes
 * del sistema de gestión de la Academia Ninja. Maneja el flujo principal
 * de la aplicación y la interacción entre los diferentes subsistemas.
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class AplicacionControlador {

    /** Facade que proporciona operaciones de alto nivel para la academia */
    private AcademiaFacade academiaFacade;

    /** Menú principal de la aplicación */
    private MenuPrincipal menuPrincipal;

    /** Gestor de interacciones con el usuario */
    private GestorInteraccion gestorInteraccion;

    /** Instancia de la academia */
    private Academia academia;

    /**
     * Constructor del controlador de aplicación.
     * Inicializa todos los componentes necesarios para el funcionamiento del sistema.
     */
    public AplicacionControlador() {
        inicializarComponentes();
    }

    /**
     * Inicia la aplicación ejecutando todo el flujo principal.
     */
    public void iniciarAplicacion() {
        try {
            inicializarDatos();
            ejecutarCicloMenu();
        } catch (Exception e) {
            gestorInteraccion.mostrarMensaje("Error en la aplicación: " + e.getMessage());
        } finally {
            gestorInteraccion.cerrarRecursos();
        }
    }

    /**
     * Crea y configura la instancia de la academia con todos sus componentes.
     *
     * @return Academia configurada y lista para usar
     */
    private Academia crearAcademia() {
        return new Academia();
    }

    /**
     * Inicializa todos los componentes del sistema.
     */
    private void inicializarComponentes() {
        this.gestorInteraccion = new GestorInteraccion();
        this.academia = crearAcademia();
        this.academiaFacade = new AcademiaFacade(academia);
        this.menuPrincipal = new MenuPrincipal(gestorInteraccion);
    }

    /**
     * Inicializa los datos base del sistema.
     */
    private void inicializarDatos() {
        gestorInteraccion.mostrarMensaje("Inicializando datos del sistema...");
        academiaFacade.inicializarDatosBase();
        gestorInteraccion.mostrarMensaje("Datos inicializados correctamente.");
    }

    /**
     * Ejecuta el ciclo principal del menú hasta que el usuario decide salir.
     */
    private void ejecutarCicloMenu() {
        boolean continuar = true;

        while (continuar) {
            menuPrincipal.mostrarMenuPrincipal();
            int opcion = gestorInteraccion.leerEntero("Seleccione una opción: ");
            MenuOpcion menuOpcion = menuPrincipal.procesarOpcionMenu(opcion);

            switch (menuOpcion) {
                case VER_ESTUDIANTES:
                    academiaFacade.mostrarEstudiantesDisponibles();
                    break;

                case VER_VOLUNTARIOS:
                    academiaFacade.mostrarVoluntariosDisponibles();
                    break;

                case FORMAR_GRUPOS:
                    academiaFacade.formarGruposAutomaticamente();
                    break;

                case ASIGNAR_PAQUETES:
                    if (academiaFacade.hayGruposFormados()) {
                        asignarPaquetesInteractivo();
                    } else {
                        gestorInteraccion.mostrarMensaje("Primero debe formar los grupos.");
                    }
                    break;

                case ASIGNAR_CAMPOS:
                    if (academiaFacade.hayGruposFormados()) {
                        academiaFacade.asignarCamposAutomaticamente();
                    } else {
                        gestorInteraccion.mostrarMensaje("Primero debe formar los grupos.");
                    }
                    break;

                case MOSTRAR_RESUMEN:
                    academiaFacade.mostrarResumenFinal();
                    break;

                case SALIR:
                    gestorInteraccion.mostrarMensaje("¡Gracias por usar el sistema de la Academia Ninja!");
                    continuar = false;
                    break;

                case OPCION_INVALIDA:
                default:
                    gestorInteraccion.mostrarMensaje("Opción no válida. Intente nuevamente.");
                    break;
            }

            if (continuar) {
                gestorInteraccion.pausar();
            }
        }
    }

    /**
     * Maneja la asignación interactiva de paquetes para cada grupo.
     * Itera sobre todos los grupos formados y permite al usuario seleccionar
     * un paquete para cada uno.
     */
    private void asignarPaquetesInteractivo() {
        if(academiaFacade.hayGruposFormados() != true){
            gestorInteraccion.mostrarMensaje("No hay grupos formados para asignar paquetes");
            return;
        }
        gestorInteraccion.mostrarMensaje("Obteniendo lista de grupos...");
        List<Grupo> grupos = (List<Grupo>)academiaFacade.obtenerGrupos();
        for(Grupo g: grupos){
            gestorInteraccion.mostrarLinea("\n" + "=".repeat(50));
            gestorInteraccion.mostrarLinea("INFORMACIÓN DEL GRUPO:");
            gestorInteraccion.mostrarLinea("=".repeat(50));
            gestorInteraccion.mostrarLinea(g.toString());
            gestorInteraccion.mostrarLinea("=".repeat(50));
            boolean respuesta = gestorInteraccion.confirmarAccion("Asignar paquete a este grupo?"   );

            if(respuesta){
                academiaFacade.asignarPaqueteInteractivo(g, gestorInteraccion);
            }else{
                continue;
            }
        }
    }   
}