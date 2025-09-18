package ui;

/**
 * Clase que maneja la presentación y lógica del menú principal de la aplicación.
 * Proporciona opciones para gestionar estudiantes, voluntarios, grupos, paquetes
 * y campos de entrenamiento de la Academia Ninja.
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class MenuPrincipal {

    /** Gestor de interacciones con el usuario */
    private GestorInteraccion gestorInteraccion;

    /**
     * Constructor del menú principal.
     *
     * @param gestorInteraccion Gestor para manejar las interacciones con el usuario
     */
    public MenuPrincipal(GestorInteraccion gestorInteraccion) {
        this.gestorInteraccion = gestorInteraccion;
    }

    /**
     * Muestra el menú principal con todas las opciones disponibles.
     */
    public void mostrarMenuPrincipal() {
        mostrarBanner();
        gestorInteraccion.mostrarLinea("=".repeat(50));
        gestorInteraccion.mostrarLinea("           MENÚ PRINCIPAL");
        gestorInteraccion.mostrarLinea("=".repeat(50));
        gestorInteraccion.mostrarLinea("1. Ver estudiantes disponibles");
        gestorInteraccion.mostrarLinea("2. Ver voluntarios disponibles");
        gestorInteraccion.mostrarLinea("3. Formar grupos");
        gestorInteraccion.mostrarLinea("4. Asignar paquetes de herramientas");
        gestorInteraccion.mostrarLinea("5. Asignar campos de entrenamiento");
        gestorInteraccion.mostrarLinea("6. Mostrar resumen final");
        gestorInteraccion.mostrarLinea("7. Salir");
        gestorInteraccion.mostrarLinea("=".repeat(50));
    }

    /**
     * Muestra el menú específico para la selección de paquetes de herramientas.
     */
    public void mostrarMenuPaquetes() {
        gestorInteraccion.mostrarLinea("\n" + "=".repeat(45));
        gestorInteraccion.mostrarLinea("        PAQUETES DE HERRAMIENTAS");
        gestorInteraccion.mostrarLinea("=".repeat(45));
        gestorInteraccion.mostrarLinea("1. Paquete Básico");
        gestorInteraccion.mostrarLinea("   - 1 Kunai, 1 Shuriken, 1 Botiquín");
        gestorInteraccion.mostrarLinea("");
        gestorInteraccion.mostrarLinea("2. Paquete Avanzado");
        gestorInteraccion.mostrarLinea("   - 2 Shuriken, 3 Papeles Bomba");
        gestorInteraccion.mostrarLinea("   - 2 Bombas de Humo, 2 Botiquines");
        gestorInteraccion.mostrarLinea("");
        gestorInteraccion.mostrarLinea("3. Paquete Táctico");
        gestorInteraccion.mostrarLinea("   - 3 Kunai, 2 Shuriken, 4 Papeles Bomba");
        gestorInteraccion.mostrarLinea("   - 2 Bombas de Humo");
        gestorInteraccion.mostrarLinea("");
        gestorInteraccion.mostrarLinea("4. Paquete Personalizado");
        gestorInteraccion.mostrarLinea("   - Crear paquete personalizado");
        gestorInteraccion.mostrarLinea("=".repeat(45));
    }

    /**
     * Procesa la opción seleccionada por el usuario y retorna la acción correspondiente.
     *
     * @param opcion Número de opción seleccionada por el usuario
     * @return MenuOpcion correspondiente a la selección del usuario
     */
    public MenuOpcion procesarOpcionMenu(int opcion) {
        switch (opcion) {
            case 1:
                return MenuOpcion.VER_ESTUDIANTES;
            case 2:
                return MenuOpcion.VER_VOLUNTARIOS;
            case 3:
                return MenuOpcion.FORMAR_GRUPOS;
            case 4:
                return MenuOpcion.ASIGNAR_PAQUETES;
            case 5:
                return MenuOpcion.ASIGNAR_CAMPOS;
            case 6:
                return MenuOpcion.MOSTRAR_RESUMEN;
            case 7:
                return MenuOpcion.SALIR;
            default:
                return MenuOpcion.OPCION_INVALIDA;
        }
    }

    /**
     * Muestra el banner de bienvenida de la Academia Ninja.
     */
    public void mostrarBanner() {
        gestorInteraccion.mostrarLinea("\n" + "=".repeat(60));
        gestorInteraccion.mostrarLinea("    🥷 ACADEMIA NINJA - ALDEA DE LAS CIENCIAS 🥷");
        gestorInteraccion.mostrarLinea("          Sistema de Gestión de Actividades");
        gestorInteraccion.mostrarLinea("=".repeat(60));
        gestorInteraccion.mostrarLinea("    Bienvenido al sistema del Vickage");
        gestorInteraccion.mostrarLinea("=".repeat(60));
    }
}