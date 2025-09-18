package ui;

/**
 * Enumeración que define todas las opciones disponibles en el menú principal
 * de la aplicación de la Academia Ninja. Cada opción representa una funcionalidad
 * específica del sistema.
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public enum MenuOpcion {

    /**
     * Opción para visualizar todos los estudiantes ninja disponibles
     * en el sistema con sus características.
     */
    VER_ESTUDIANTES,

    /**
     * Opción para visualizar todos los ninjas voluntarios disponibles
     * en el sistema con sus características y rangos.
     */
    VER_VOLUNTARIOS,

    /**
     * Opción para formar grupos automáticamente asignando voluntarios
     * como líderes y estudiantes según las capacidades de cada rango.
     */
    FORMAR_GRUPOS,

    /**
     * Opción para asignar paquetes de herramientas a los grupos formados,
     * ya sean paquetes predefinidos o personalizados.
     */
    ASIGNAR_PAQUETES,

    /**
     * Opción para asignar campos de entrenamiento a los grupos
     * basándose en la suma de niveles de habilidad de sus integrantes.
     */
    ASIGNAR_CAMPOS,

    /**
     * Opción para mostrar un resumen completo de todos los grupos
     * con sus integrantes, paquetes y campos asignados.
     */
    MOSTRAR_RESUMEN,

    /**
     * Opción para salir de la aplicación de forma segura.
     */
    SALIR,

    /**
     * Opción que indica que el usuario ingresó una opción no válida
     * en el menú principal.
     */
    OPCION_INVALIDA
}