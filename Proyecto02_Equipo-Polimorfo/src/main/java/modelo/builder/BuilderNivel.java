package modelo.builder;

import modelo.factory.niveles.Nivel;

public interface BuilderNivel {
    BuilderNivel reiniciar();
    BuilderNivel establecerNombre(String nombre);
    BuilderNivel establecerDificultad(int dificultad);
    BuilderNivel agregarBloque(double x, double y, TipoBloque tipo);
    Nivel construir();
}

