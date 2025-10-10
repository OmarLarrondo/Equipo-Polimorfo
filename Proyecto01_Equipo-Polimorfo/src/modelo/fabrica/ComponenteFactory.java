package modelo.fabrica;

import modelo.componente.CPU;
import modelo.componente.MotherBoard;

/**
 * Interfaz que define el contrato para fabricas de componentes de PC.
 * Esta interfaz forma parte del patron Abstract Factory, permitiendo la
 * creacion de familias de componentes compatibles entre si (Intel o AMD).
 */
public interface ComponenteFactory {

    /**
     * Crea una CPU segun el modelo especificado.
     *
     * @param modelo El nombre del modelo de CPU a crear
     * @return Una instancia de CPU configurada segun el modelo
     */
    CPU crearCPU(String modelo);

    /**
     * Crea una placa madre segun el modelo especificado.
     *
     * @param modelo El nombre del modelo de MotherBoard a crear
     * @return Una instancia de MotherBoard configurada segun el modelo
     */
    MotherBoard crearMotherBoard(String modelo);
}
