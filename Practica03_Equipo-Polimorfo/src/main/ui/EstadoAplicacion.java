package ui;

/**
 * Enumeración que define los diferentes estados por los que puede pasar
 * la aplicación durante su ciclo de vida. Útil para controlar el flujo
 * de la aplicación y manejar transiciones entre estados.
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public enum EstadoAplicacion {

    /**
     * Estado inicial cuando la aplicación está inicializando sus componentes
     * y cargando datos necesarios para el funcionamiento.
     */
    INICIALIZANDO,

    /**
     * Estado cuando la aplicación está mostrando el menú principal
     * y esperando la selección del usuario.
     */
    MENU_PRINCIPAL,

    /**
     * Estado cuando la aplicación está procesando una operación
     * solicitada por el usuario (formación de grupos, asignaciones, etc.).
     */
    PROCESANDO,

    /**
     * Estado final cuando la aplicación está cerrando y liberando recursos
     * antes de terminar la ejecución.
     */
    FINALIZANDO
}