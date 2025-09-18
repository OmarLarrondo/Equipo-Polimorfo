package herramientas;

/**
 * Interfaz del patrón Factory para la creación de herramientas ninja.
 * Define el contrato para las factorías que crean diferentes tipos de herramientas.
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public interface HerramientaFactory {

    /**
     * Crea una herramienta del tipo especificado con la cantidad indicada.
     *
     * @param tipo Tipo de herramienta a crear (kunai, shuriken, papel_bomba, bomba_humo, botiquin)
     * @param cantidad Cantidad de herramientas del tipo especificado
     * @return Herramienta creada con las características especificadas
     */
    Herramienta crearHerramienta(String tipo, int cantidad);
}