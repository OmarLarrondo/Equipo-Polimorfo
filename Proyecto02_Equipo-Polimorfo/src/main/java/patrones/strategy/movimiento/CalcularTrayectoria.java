package patrones.strategy.movimiento;

import mvc.modelo.entidades.Pelota;

public class CalcularTrayectoria {

    private double limiteSuperior;
    private double limiteInferior;
    private double anchoCampo;

    /**
     * Predice la coordenada Y donde la pelota impactará con la línea vertical xObjetivo,
     * considerando los rebotes entre los límites superior e inferior del campo.
     */
    public double predecirPosicionImpacto(Pelota pelota, double xObjetivo) {

        double posX = pelota.obtenerX();
        double posY = pelota.obtenerY();
        double velX = pelota.obtenerVelocidadX();
        double velY = pelota.obtenerVelocidadY();


        // Si la pelota no se mueve horizontalmente, no se puede predecir.
        if (velX == 0) return posY;

        // Tiempo hasta alcanzar la posición X objetivo(Paleta).
        /**
         *      xobjetivo(Posicion de la paleta) - posX (Posicion de la pelota)
         * t= ______________________________________________________________________
         *                          velY(Velocidad a la que se mueve la pelota en y)
         */
        double tiempo = (xObjetivo - posX) / velX;

        // Si la pelota se aleja del objetivo, no hay impacto futuro.
        if (tiempo < 0) return posY;



        // Movimiento vertical total en ese tiempo
        /**
         * cada seg, la pelota se mueve en y {@link velY}, entonces, se multiplica
         * el tiempo que tardara en llegar a la paleta, por el dezplazamiento y.
         * nos da la posicion en y de la pelota.
         */
        double desplazamientoY = velY * tiempo;

        double alturaCampo = limiteInferior - limiteSuperior;

        // Posición tentativa sin considerar rebotes
        /**
         * 
         * posy(Posicion inical donde estaba la pelota) + desplazamiento.(donde llegara la pelota
         * 
         */
        double yTentativa = posY + desplazamientoY;

        // Mapeo reflejado entre los límites del campo
        // Patrón espejo: [superior → inferior → superior → inferior ...]

        double rango = 2 * alturaCampo;
        
        double offset = (yTentativa - limiteSuperior) % rango;

        if (offset < 0) offset += rango; // Asegurar valor positivo del módulo

        double yFinal;
        if (offset <= alturaCampo) {
            yFinal = limiteSuperior + offset;
        } else {
            // Se refleja en el límite inferior y sube de nuevo
            yFinal = limiteInferior - (offset - alturaCampo);
        }

        return yFinal;
    }
}
