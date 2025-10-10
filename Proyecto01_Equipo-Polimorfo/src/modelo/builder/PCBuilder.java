package modelo.builder;

import modelo.computadora.ComputadoraBase;

/**
 * Interfaz que define el contrato para los constructores de computadoras.
 * Esta interfaz forma parte del patron Builder, permitiendo la construccion
 * paso a paso de objetos ComputadoraBase complejos.
 * Los builders concretos implementan esta interfaz para construir diferentes
 * tipos de computadoras (personalizadas o prearmadas).
 */
public interface PCBuilder {

    /**
     * Reinicia el builder, creando una nueva instancia de computadora.
     */
    void reiniciar();

    /**
     * Construye y agrega el procesador (CPU) a la computadora.
     */
    void construirCPU();

    /**
     * Construye y agrega la memoria RAM a la computadora.
     */
    void construirRAM();

    /**
     * Construye y agrega la placa madre (MotherBoard) a la computadora.
     */
    void construirMotherBoard();

    /**
     * Construye y agrega la tarjeta grafica (GPU) a la computadora.
     */
    void construirGPU();

    /**
     * Construye y agrega el almacenamiento (HDD/SSD) a la computadora.
     */
    void construirAlmacenamiento();

    /**
     * Construye y agrega la fuente de alimentacion a la computadora.
     */
    void construirFuente();

    /**
     * Construye y agrega el gabinete a la computadora.
     */
    void construirGabinete();

    /**
     * Obtiene el resultado de la construccion.
     *
     * @return La computadora construida
     */
    ComputadoraBase obtenerResultado();
}
