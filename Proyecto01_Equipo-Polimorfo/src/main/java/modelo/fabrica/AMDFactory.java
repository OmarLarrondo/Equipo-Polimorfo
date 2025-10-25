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
                new CPU("AMD Ryzen 5 5600G", 7999.00, "AMD", "CPU", 6, "x86-64 (AMD64)");
            case "Ryzen 5 7600X" ->
                new CPU("AMD Ryzen 5 7600X", 12999.00, "AMD", "CPU", 6, "x86-64 (AMD64)");
            case "Ryzen 7 7700X" ->
                new CPU("AMD Ryzen 7 7700X", 17999.00, "AMD", "CPU", 8, "x86-64 (AMD64)");
            case "Ryzen 9 7950X3D" ->
                new CPU("AMD Ryzen 9 7950X3D", 32999.00, "AMD", "CPU", 16, "x86-64 (AMD64)");
            default ->
                throw new IllegalArgumentException("Modelo de CPU AMD no reconocido: " + modelo);
        };
    }

    /**
     * Metodo privado que crea placas madre compatibles del stock actual.
     * Segun los requerimientos, las CPUs AMD deben trabajar con los componentes
     * actuales en stock (motherboards Intel). Retorna motherboards Intel que
     * seran adaptadas mediante el patron Adapter para trabajar con CPUs AMD.
     * Soporta los siguientes modelos del stock:
     * - ASUS ROG Maximus Z790 Hero: Chipset Z790, Socket LGA1700
     * - ASUS TUF Gaming B760-Plus WIFI D4: Chipset B760, Socket LGA1700
     * - MSI MEG Z790 Godlike: Chipset Z790, Socket LGA1700
     * - MSI MAG B760 Tomahawk WIFI DDR4: Chipset B760, Socket LGA1700
     *
     * @param modelo El nombre del modelo de MotherBoard
     * @return Una MotherBoard del stock actual (Intel)
     */
    private MotherBoard crearMotherBoardAMD(String modelo) {
        return switch (modelo) {
            case "ROG Maximus Z790 Hero" ->
                new MotherBoard("ASUS ROG Maximus Z790 Hero", 19999.00, "ASUS", "MotherBoard", "Z790", "LGA1700", "x86-64");
            case "TUF Gaming B760-Plus WIFI D4" ->
                new MotherBoard("ASUS TUF Gaming B760-Plus WIFI D4", 8999.00, "ASUS", "MotherBoard", "B760", "LGA1700", "x86-64");
            case "MEG Z790 Godlike" ->
                new MotherBoard("MSI MEG Z790 Godlike", 29999.00, "MSI", "MotherBoard", "Z790", "LGA1700", "x86-64");
            case "MAG B760 Tomahawk WIFI DDR4" ->
                new MotherBoard("MSI MAG B760 Tomahawk WIFI DDR4", 7999.00, "MSI", "MotherBoard", "B760", "LGA1700", "x86-64");
            default ->
                throw new IllegalArgumentException("Modelo de MotherBoard no reconocido: " + modelo);
        };
    }
}
