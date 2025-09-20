package Academia.Campos;

/**
 * Implementación concreta del patrón Factory para la creación de campos de entrenamiento.
 * Esta clase implementa la lógica específica para crear diferentes tipos de campos
 * según la suma de niveles de habilidad de los integrantes del grupo.
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class CampoConcreteFactory implements CampoFactory {

    /**
     * Crea un campo de entrenamiento según la suma de niveles de habilidad.
     *
     * @param sumaNiveles Suma total de niveles de habilidad del grupo
     * @return Campo de entrenamiento apropiado para el nivel
     */
    public CampoEntrenamiento crearCampo(int sumaNiveles) {
        String tipoCampo = determinarTipoCampo(sumaNiveles);

        switch (tipoCampo) {
            case "Valle del Dragón":
                return new ValleDragon();
            case "Bosque Sombrío":
                return new BosqueSombrio();
            case "Montaña Espiritual":
                return new MontañaEspiritual();
            default:
                return new ValleDragon(); // Campo por defecto
        }
    }

    /**
     * Determina el tipo de campo según la suma de niveles.
     *
     * @param suma Suma de niveles de habilidad
     * @return Nombre del tipo de campo
     */
    private String determinarTipoCampo(int suma) {
        if (suma < 7) {
            return "Valle del Dragón";
        } else if (suma >= 8 && suma <= 11) {
            return "Bosque Sombrío";
        } else {
            return "Montaña Espiritual";
        }
    }
}