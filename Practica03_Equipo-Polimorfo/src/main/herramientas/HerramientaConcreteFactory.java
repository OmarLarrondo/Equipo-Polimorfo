package main.herramientas;

/**
 * Implementación concreta del patrón Factory para la creación de herramientas ninja.
 * Esta clase implementa la lógica específica para crear diferentes tipos de herramientas
 * según el tipo especificado y validar que el tipo sea válido.
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class HerramientaConcreteFactory implements HerramientaFactory {

    /**
     * Crea una herramienta del tipo especificado con la cantidad indicada.
     *
     * @param tipo Tipo de herramienta a crear
     * @param cantidad Cantidad de herramientas del tipo especificado
     * @return Herramienta creada con las características especificadas
     * @throws IllegalArgumentException Si el tipo no es válido
     */
    @Override
    public Herramienta crearHerramienta(String tipo, int cantidad) {
        if (!validarTipo(tipo)) {
            throw new IllegalArgumentException("Tipo de herramienta no válido: " + tipo);
        }

        switch (tipo.toLowerCase()) {
            case "kunai":
                return new Kunai(tipo,5,cantidad);
            case "shuriken":
                return new Shuriken(tipo,10,cantidad);
            case "papel_bomba":
                return new PapelBomba(tipo,15,cantidad);
            case "bomba_humo":
                return new BombaHumo(tipo,20,cantidad);
            case "botiquin":
                return new Botiquin(tipo,25,cantidad);
            default:
                throw new IllegalArgumentException("Tipo de herramienta no reconocido: " + tipo);
        }
    }

    /**
     * Valida si el tipo de herramienta especificado es válido.
     *
     * @param tipo Tipo de herramienta a validar
     * @return true si el tipo es válido, false en caso contrario
     */
    private boolean validarTipo(String tipo) {
        String tipoLower = tipo.toLowerCase();
        return tipoLower.equals("kunai") ||
            tipoLower.equals("shuriken") ||
            tipoLower.equals("papel_bomba") ||
            tipoLower.equals("bomba_humo") ||
            tipoLower.equals("botiquin");
    }
}