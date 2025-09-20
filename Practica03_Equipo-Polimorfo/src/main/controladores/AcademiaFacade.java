package controladores;

import Academia.Academia;
import MundoNinja.Grupo;
import MundoNinja.EstudianteNinja;
import ui.GestorInteraccion;

import Academia.Listas.Iterator;
import Academia.Paquetes.GestorPaquetes;
import Academia.Paquetes.PaqueteBuilder;
import MundoNinja.NinjaVoluntario;
import PaquetesHerramientas.PaquetesHerramientas;
/**
 * Facade que proporciona una interfaz simplificada para las operaciones
 * de alto nivel de la Academia Ninja. Encapsula la complejidad del subsistema
 * de la academia y proporciona métodos convenientes para el controlador principal.
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class AcademiaFacade {

    /** Instancia de la academia que maneja toda la lógica del dominio */
    private Academia academia;

    /**
     * Constructor del facade de la academia.
     *
     * @param academia Instancia de la academia a encapsular
     */
    public AcademiaFacade(Academia academia) {
        this.academia = academia;
    }

    /**
     * Inicializa los datos base del sistema con estudiantes y voluntarios predefinidos.
     */
    public void inicializarDatosBase() {
        academia.inicializarDatos();
    }

    /**
     * Muestra todos los estudiantes disponibles en el sistema.
     */
    public void mostrarEstudiantesDisponibles() {
        System.out.println("=== ESTUDIANTES DISPONIBLES ===");
    
        Iterator<EstudianteNinja> iter = academia.getAspirantes().iterator();
        while (iter.hasNext()) {
            EstudianteNinja e = iter.next();
            System.out.println(e.toString());
        }
    }


    /**
     * Muestra todos los voluntarios disponibles en el sistema.
     */
    public void mostrarVoluntariosDisponibles() {
        System.out.println("=== VOLUNTARIOS DISPONIBLES ===");
        Iterator<NinjaVoluntario> iter = academia.getVoluntarios().iterator();
        while (iter.hasNext()) {
            NinjaVoluntario v = iter.next();
            System.out.println(v.toString());
        }
    }

    /**
     * Forma grupos automáticamente asignando voluntarios como líderes
     * y estudiantes según la capacidad de cada rango.
     */
    public void formarGruposAutomaticamente() {
        academia.formarGrupos();
        System.out.println("Grupos formados exitosamente.");
    }

    /**
     * Asigna un paquete de herramientas a un grupo específico de manera interactiva.
     *
     * @param grupo Grupo al que se le asignará el paquete
     * @param gestorInteraccion Gestor para la interacción con el usuario
     */
    public void asignarPaqueteInteractivo(Grupo grupo, GestorInteraccion gestorInteraccion) {
        System.out.println("Asignando paquete al grupo dirigido por: " + grupo.toString());
        int eleccion = gestorInteraccion.leerEntero("Seleccione el paquete a asignar : \n1) Básico  \n2) Avanzado  \n3) Táctico  \n4) Personalizado");

        boolean confirmado = gestorInteraccion.confirmarAccion("¿Está seguro?");
        if (!confirmado) {
            System.out.println("Asignación cancelada por el usuario.");
            return;
        }

        GestorPaquetes gp = new GestorPaquetes(null); 
        PaquetesHerramientas paqueteElegido = null;
        
        switch (eleccion) {
            case 1:
            paqueteElegido = gp.construirPaqueteBasico();
                break;
            case 2:
            paqueteElegido = gp.construirPaqueteAvanzado();
                break;
            case 3:
            paqueteElegido = gp.construirPaqueteTactico();
                break;
            case 4:
            PaqueteBuilder builder = gp.dirigirConstruccionPersonalizada();

            int kunais = gestorInteraccion.leerEntero("Cuántos kunais quieres?");
            builder.addKunai(kunais);

            int shurikens = gestorInteraccion.leerEntero("Cuántos shurikens quieres?");
            builder.addShuriken(shurikens);

            int papeles = gestorInteraccion.leerEntero("Cuántos papeles bomba?");
            builder.addPapelBomba(papeles);

            int bombasHumo = gestorInteraccion.leerEntero("Cuántas bombas de humo?");
            builder.addBombaHumo(bombasHumo);

            int botiquines = gestorInteraccion.leerEntero("Cuántos botiquines quieres?");
            builder.addBotiquin(botiquines);

            paqueteElegido = builder.build(); 
                break;
            default:
                System.out.println("Opción no válida.");
                return;
                }

        grupo.asignarPaquete(paqueteElegido);
        System.out.println("Paquete asignado correctamente al grupo.");
    }

    /**
     * Asigna campos de entrenamiento a todos los grupos automáticamente
     * según la suma de niveles de habilidad de sus integrantes.
     */
    public void asignarCamposAutomaticamente() {
        academia.asignarCampos();
        System.out.println("Campos de entrenamiento asignados exitosamente.");
    }

    /**
     * Muestra un resumen final de todos los grupos con sus integrantes,
     * paquetes asignados y campos de entrenamiento.
     */
    public void mostrarResumenFinal() {
        academia.mostrarResumen();
    }

    /**
     * Verifica si hay grupos formados en el sistema.
     *
     * @return true si hay grupos formados, false en caso contrario
     */
    public boolean hayGruposFormados() {
        return academia.getGruposFormados() != null && !academia.getGruposFormados().isEmpty();
    }

    /**
     * Obtiene la lista de grupos formados.
     *
     * @return Lista de grupos formados en la academia
     */
    public Object obtenerGrupos() {
        System.out.println("Obteniendo lista de grupos...");
        return academia.getGruposFormados();
    }
}