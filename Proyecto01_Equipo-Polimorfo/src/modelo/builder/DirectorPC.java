package modelo.builder;

import modelo.computadora.ComputadoraBase;

/**
 * Director que orquesta el proceso de construccion de computadoras.
 * Esta clase forma parte del patron Builder y se encarga de definir
 * el orden y los pasos necesarios para construir diferentes tipos
 * de computadoras utilizando un PCBuilder especifico.
 */
public class DirectorPC {

    private PCBuilder builder;

    /**
     * Construye un nuevo DirectorPC con el builder especificado.
     *
     * @param builder El builder a utilizar para construir computadoras
     */
    public DirectorPC(PCBuilder builder) {
        this.builder = builder;
    }

    /**
     * Cambia el builder actual por uno nuevo.
     * Esto permite reutilizar el director con diferentes builders.
     *
     * @param builder El nuevo builder a utilizar
     */
    public void cambiarBuilder(PCBuilder builder) {
        this.builder = builder;
    }

    /**
     * Construye una PC completa con todos los componentes.
     * Este metodo orquesta la construccion paso a paso de una computadora
     * completa incluyendo: CPU, RAM, MotherBoard, GPU, almacenamiento,
     * fuente de alimentacion y gabinete.
     *
     * @return La computadora completa construida
     */
    public ComputadoraBase construirPCCompleta() {
        builder.reiniciar();
        builder.construirCPU();
        builder.construirRAM();
        builder.construirMotherBoard();
        builder.construirGPU();
        builder.construirAlmacenamiento();
        builder.construirFuente();
        builder.construirGabinete();
        return builder.obtenerResultado();
    }

    /**
     * Construye una PC basica con los componentes minimos necesarios.
     * Este metodo orquesta la construccion de una computadora con los
     * componentes esenciales: CPU, RAM, MotherBoard y almacenamiento.
     *
     * @return La computadora basica construida
     */
    public ComputadoraBase construirPCBasica() {
        builder.reiniciar();
        builder.construirCPU();
        builder.construirRAM();
        builder.construirMotherBoard();
        builder.construirAlmacenamiento();
        return builder.obtenerResultado();
    }
}
