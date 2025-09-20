package controladores;

import Academia.Academia;
import ui.MenuPrincipal;
import ui.GestorInteraccion;
import ui.MenuOpcion;
import main.MundoNinja.Grupo;
import main.Academia.Paquetes.GestorPaquetes;
import main.Academia.Paquetes.PaquetePersonalizadoBuilder;
import main.PaquetesHerramientas.PaquetesHerramientas;
import java.util.List;
import java.util.function.*;
import java.util.stream.*;
import java.util.concurrent.atomic.AtomicInteger;

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
        List<Grupo> grupos = academiaFacade.obtenerGrupos();

        Predicate<List<Grupo>> gruposVacios = List::isEmpty;
        if (gruposVacios.test(grupos)) {
            gestorInteraccion.mostrarMensaje("No hay grupos formados para asignar paquetes.");
            return;
        }

        Consumer<List<Grupo>> mostrarEncabezado = listaGrupos -> {
            gestorInteraccion.mostrarMensaje("\n=== ASIGNACIÓN DE PAQUETES ===");
            gestorInteraccion.mostrarMensaje("Total de grupos formados: " + listaGrupos.size());
        };

        Function<MenuPrincipal.TipoPaquete, Function<GestorPaquetes, PaquetesHerramientas>> seleccionarPaquete = tipo -> gestor -> {
            switch (tipo) {
                case BASICO:
                    gestorInteraccion.mostrarMensaje("Paquete Básico seleccionado.");
                    return gestor.construirPaqueteBasico();
                case AVANZADO:
                    gestorInteraccion.mostrarMensaje("Paquete Avanzado seleccionado.");
                    return gestor.construirPaqueteAvanzado();
                case TACTICO:
                    gestorInteraccion.mostrarMensaje("Paquete Táctico seleccionado.");
                    return gestor.construirPaqueteTactico();
                case PERSONALIZADO:
                    return menuPrincipal.crearPaquetePersonalizado(gestorInteraccion);
                default:
                    gestorInteraccion.mostrarMensaje("Opción no válida. Intente nuevamente.");
                    return null;
            }
        };

        Function<Grupo, Function<Integer, Consumer<GestorPaquetes>>> procesarGrupo = grupo -> numeroGrupo -> gestor -> {
            gestorInteraccion.mostrarMensaje("\n" + "=".repeat(60));
            gestorInteraccion.mostrarMensaje("GRUPO " + numeroGrupo + " de " + grupos.size());
            gestorInteraccion.mostrarMensaje("=".repeat(60));

            mostrarInformacionGrupo(grupo);

            Supplier<PaquetesHerramientas> solicitarPaquete = () -> {
                PaquetesHerramientas paquete = null;
                while (paquete == null) {
                    menuPrincipal.mostrarMenuPaquetes();
                    int opcionPaquete = gestorInteraccion.leerEntero("Seleccione un paquete para este grupo: ");
                    MenuPrincipal.TipoPaquete tipoPaquete = menuPrincipal.procesarOpcionPaquete(opcionPaquete);
                    paquete = seleccionarPaquete.apply(tipoPaquete).apply(gestor);
                }
                return paquete;
            };

            PaquetesHerramientas paqueteSeleccionado = solicitarPaquete.get();
            if (grupo.asignarPaquete(paqueteSeleccionado)) {
                gestorInteraccion.mostrarMensaje("¡Paquete asignado exitosamente!");
                gestorInteraccion.mostrarMensaje("Descripción: " + paqueteSeleccionado.getDescripcion());
            } else {
                gestorInteraccion.mostrarMensaje("Error al asignar el paquete.");
            }
        };

        mostrarEncabezado.accept(grupos);
        GestorPaquetes gestorPaquetes = new GestorPaquetes(new PaquetePersonalizadoBuilder());
        AtomicInteger contador = new AtomicInteger(1);

        grupos.stream()
            .forEach(grupo -> {
                int numeroActual = contador.getAndIncrement();
                procesarGrupo.apply(grupo).apply(numeroActual).accept(gestorPaquetes);
                if (numeroActual < grupos.size()) {
                    gestorInteraccion.pausar();
                }
            });

        gestorInteraccion.mostrarMensaje("\n¡Asignación de paquetes completada para todos los grupos!");
    }

    /**
     * Muestra información detallada de un grupo específico.
     *
     * @param grupo Grupo del cual mostrar información
     */
    private void mostrarInformacionGrupo(Grupo grupo) {
        Consumer<Grupo> mostrarLider = g -> {
            gestorInteraccion.mostrarMensaje("INFORMACIÓN DEL GRUPO:");
            gestorInteraccion.mostrarMensaje("Líder: " + g.getLider().getNombre() +
                                           " (Rango: " + g.getLider().getRango() +
                                           ", Habilidad: " + g.getLider().getNivelHabilidad() + ")");
        };

        Consumer<Grupo> mostrarEstudiantes = g -> {
            gestorInteraccion.mostrarMensaje("Estudiantes (" + g.getEstudiantes().size() + "):");
            g.getEstudiantes().stream()
                .map(estudiante -> "  - " + estudiante.getNombre() +
                                  " (Habilidad: " + estudiante.getNivelHabilidad() + ")")
                .forEach(gestorInteraccion::mostrarMensaje);
        };

        Consumer<Grupo> mostrarResumen = g -> {
            gestorInteraccion.mostrarMensaje("Suma total de habilidades: " + g.calcularSumaHabilidades());
            gestorInteraccion.mostrarMensaje("");
        };

        Stream.of(mostrarLider, mostrarEstudiantes, mostrarResumen)
            .forEach(funcion -> funcion.accept(grupo));
    }
}