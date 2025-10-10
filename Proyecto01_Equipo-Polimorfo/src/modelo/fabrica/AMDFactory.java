package modelo.fabrica;

import modelo.componente.CPU;
import modelo.componente.MotherBoard;

/**
 * Fabrica concreta para la creacion de componentes AMD.
 * Implementa el patron Abstract Factory para crear CPUs y placas madre
 * de la familia AMD, asegurando la compatibilidad entre componentes.
 */
public class AMDFactory implements ComponenteFactory {

    /**
     * Crea una CPU AMD segun el modelo especificado.
     * Delega la creacion al metodo privado crearCPUAMD.
     *
     * @param modelo El nombre del modelo de CPU AMD a crear
     * @return Una instancia de CPU AMD configurada
     */
    @Override
    public CPU crearCPU(String modelo) {
        return crearCPUAMD(modelo);
    }

    /**
     * Crea una placa madre compatible con AMD segun el modelo especificado.
     * Delega la creacion al metodo privado crearMotherBoardAMD.
     *
     * @param modelo El nombre del modelo de MotherBoard a crear
     * @return Una instancia de MotherBoard compatible con AMD
     */
    @Override
    public MotherBoard crearMotherBoard(String modelo) {
        return crearMotherBoardAMD(modelo);
    }

    /**
     * Metodo privado que crea CPUs AMD especificas.
     * Soporta los siguientes modelos:
     * - Ryzen 5 5600G: 6 nucleos (Serie 5000 - Socket AM4)
     * - Ryzen 5 7600X: 6 nucleos (Serie 7000 - Socket AM5)
     * - Ryzen 7 7700X: 8 nucleos (Serie 7000 - Socket AM5)
     * - Ryzen 9 7950X3D: 16 nucleos (Serie 7000 - Socket AM5)
     *
     * @param modelo El nombre del modelo de CPU AMD
     * @return Una CPU AMD configurada con sus especificaciones
     */
    private CPU crearCPUAMD(String modelo) {
        return switch (modelo) {
            case "Ryzen 5 5600G" ->
                new CPU("AMD Ryzen 5 5600G", 7999.00, "AMD", 6, "AMD");
            case "Ryzen 5 7600X" ->
                new CPU("AMD Ryzen 5 7600X", 12999.00, "AMD", 6, "AMD");
            case "Ryzen 7 7700X" ->
                new CPU("AMD Ryzen 7 7700X", 17999.00, "AMD", 8, "AMD");
            case "Ryzen 9 7950X3D" ->
                new CPU("AMD Ryzen 9 7950X3D", 32999.00, "AMD", 16, "AMD");
            default ->
                throw new IllegalArgumentException("Modelo de CPU AMD no reconocido: " + modelo);
        };
    }

    /**
     * Metodo privado que crea placas madre compatibles con procesadores AMD.
     * Soporta los siguientes modelos adaptados para AMD:
     * - ASUS ROG Maximus AMD X670: Chipset X670, Socket AM5
     * - ASUS TUF Gaming B650-Plus WIFI: Chipset B650, Socket AM5
     * - MSI MEG X670E Godlike: Chipset X670E, Socket AM5
     * - MSI MAG B650 Tomahawk WIFI: Chipset B650, Socket AM5
     *
     * @param modelo El nombre del modelo de MotherBoard
     * @return Una MotherBoard compatible con procesadores AMD
     */
    private MotherBoard crearMotherBoardAMD(String modelo) {
        return switch (modelo) {
            case "ROG Maximus AMD X670" ->
                new MotherBoard("ASUS ROG Crosshair X670E Hero", 18999.00, "ASUS", "X670E", "AM5", "AMD");
            case "TUF Gaming B650-Plus WIFI" ->
                new MotherBoard("ASUS TUF Gaming B650-Plus WIFI", 8499.00, "ASUS", "B650", "AM5", "AMD");
            case "MEG X670E Godlike" ->
                new MotherBoard("MSI MEG X670E Godlike", 28999.00, "MSI", "X670E", "AM5", "AMD");
            case "MAG B650 Tomahawk WIFI" ->
                new MotherBoard("MSI MAG B650 Tomahawk WIFI", 7499.00, "MSI", "B650", "AM5", "AMD");
            default ->
                throw new IllegalArgumentException("Modelo de MotherBoard AMD no reconocido: " + modelo);
        };
    }
}
