package controladores;

import java.util.List;
import main.Academia.Academia;
import main.MundoNinja.Grupo;
import main.MundoNinja.EstudianteNinja;
import ui.GestorInteraccion;

import main.Academia.Listas.Iterator;
import main.MundoNinja.NinjaVoluntario;
import main.PaquetesHerramientas.PaquetesHerramientas;
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

        while (true) {
            System.out.println("Seleccione el paquete a asignar :");
            System.out.println("1) Básico");
            System.out.println("2) Avanzado");
            System.out.println("3) Táctico");
            System.out.println("4) Personalizado");
            System.out.println("5) Cancelar asignación");
            int eleccion = gestorInteraccion.leerEntero(">> Ingrese su opción: ");

            if (eleccion == 5) {
                System.out.println("Asignación cancelada por el usuario.");
                return;
            }

            if (eleccion < 1 || eleccion > 4) {
                System.out.println("Opción no válida. Intente nuevamente.");
                continue;
            }

            boolean confirmado = gestorInteraccion.confirmarAccion("¿Está seguro?");
            if (!confirmado) {
                System.out.println("Selección cancelada. Puede elegir otra opción.");
                continue;
            }

            PaquetesHerramientas paqueteElegido = null;

            switch (eleccion) {
                case 1:
                    paqueteElegido = academia.construirPaqueteBasico();
                    break;
                case 2:
                    paqueteElegido = academia.construirPaqueteAvanzado();
                    break;
                case 3:
                    paqueteElegido = academia.construirPaqueteTactico();
                    break;
                case 4:
                    int kunais = gestorInteraccion.leerEntero("Cuántos kunais quieres?");
                    int shurikens = gestorInteraccion.leerEntero("Cuántos shurikens quieres?");
                    int papeles = gestorInteraccion.leerEntero("Cuántos papeles bomba?");
                    int bombasHumo = gestorInteraccion.leerEntero("Cuántas bombas de humo?");
                    int botiquines = gestorInteraccion.leerEntero("Cuántos botiquines quieres?");

                    paqueteElegido = academia.crearPaquetePersonalizado(kunais, shurikens, papeles, bombasHumo, botiquines);
                    break;
            }

            grupo.asignarPaquete(paqueteElegido);
            System.out.println("Paquete asignado correctamente al grupo.");
            break;
        }
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
    public List<Grupo> obtenerGrupos() {
        return academia.getGruposFormados();
    }
}