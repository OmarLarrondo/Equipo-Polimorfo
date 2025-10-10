package modelo.fabrica;

import modelo.componente.CPU;
import modelo.componente.MotherBoard;

/**
 * Fabrica concreta para la creacion de componentes Intel.
 * Implementa el patron Abstract Factory para crear CPUs y placas madre
 * de la familia Intel, asegurando la compatibilidad entre componentes.
 */
public class IntelFactory implements ComponenteFactory {

    /**
     * Crea una CPU Intel segun el modelo especificado.
     * Delega la creacion al metodo privado crearCPUIntel.
     *
     * @param modelo El nombre del modelo de CPU Intel a crear
     * @return Una instancia de CPU Intel configurada
     */
    @Override
    public CPU crearCPU(String modelo) {
        return crearCPUIntel(modelo);
    }

    /**
     * Crea una placa madre compatible con Intel segun el modelo especificado.
     * Delega la creacion al metodo privado crearMotherBoardIntel.
     *
     * @param modelo El nombre del modelo de MotherBoard a crear
     * @return Una instancia de MotherBoard compatible con Intel
     */
    @Override
    public MotherBoard crearMotherBoard(String modelo) {
        return crearMotherBoardIntel(modelo);
    }

    /**
     * Metodo privado que crea CPUs Intel especificas.
     * Soporta los siguientes modelos de la 13a generacion:
     * - Core i3-13100: 4 nucleos
     * - Core i5-13600K: 14 nucleos
     * - Core i7-13700K: 16 nucleos
     * - Core i9-13900K: 24 nucleos
     *
     * @param modelo El nombre del modelo de CPU Intel
     * @return Una CPU Intel configurada con sus especificaciones
     */
    private CPU crearCPUIntel(String modelo) {
        return switch (modelo) {
            case "Core i3-13100" ->
                new CPU("Intel Core i3-13100", 8999.00, "Intel", 4, "Intel");
            case "Core i5-13600K" ->
                new CPU("Intel Core i5-13600K", 18999.00, "Intel", 14, "Intel");
            case "Core i7-13700K" ->
                new CPU("Intel Core i7-13700K", 24999.00, "Intel", 16, "Intel");
            case "Core i9-13900K" ->
                new CPU("Intel Core i9-13900K", 34999.00, "Intel", 24, "Intel");
            default ->
                throw new IllegalArgumentException("Modelo de CPU Intel no reconocido: " + modelo);
        };
    }

    /**
     * Metodo privado que crea placas madre compatibles con procesadores Intel.
     * Soporta los siguientes modelos:
     * - ASUS ROG Maximus Z790 Hero: Chipset Z790, Socket LGA1700
     * - ASUS TUF Gaming B760-Plus WIFI D4: Chipset B760, Socket LGA1700
     * - MSI MEG Godlike: Chipset Z790, Socket LGA1700
     * - MSI MAG B760 Tomahawk WIFI DDR4: Chipset B760, Socket LGA1700
     *
     * @param modelo El nombre del modelo de MotherBoard
     * @return Una MotherBoard compatible con procesadores Intel
     */
    private MotherBoard crearMotherBoardIntel(String modelo) {
        return switch (modelo) {
            case "ROG Maximus Z790 Hero" ->
                new MotherBoard("ASUS ROG Maximus Z790 Hero", 19999.00, "ASUS", "Z790", "LGA1700", "Intel");
            case "TUF Gaming B760-Plus WIFI D4" ->
                new MotherBoard("ASUS TUF Gaming B760-Plus WIFI D4", 8999.00, "ASUS", "B760", "LGA1700", "Intel");
            case "MEG Godlike" ->
                new MotherBoard("MSI MEG Z790 Godlike", 29999.00, "MSI", "Z790", "LGA1700", "Intel");
            case "MAG B760 Tomahawk WIFI DDR4" ->
                new MotherBoard("MSI MAG B760 Tomahawk WIFI DDR4", 7999.00, "MSI", "B760", "LGA1700", "Intel");
            default ->
                throw new IllegalArgumentException("Modelo de MotherBoard Intel no reconocido: " + modelo);
        };
    }
}
