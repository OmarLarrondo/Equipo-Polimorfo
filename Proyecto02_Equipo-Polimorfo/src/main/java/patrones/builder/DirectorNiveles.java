package patrones.builder;

import patrones.factory.niveles.Nivel;

public class DirectorNiveles {
    private ConstructorMapa constructor;

    public DirectorNiveles(ConstructorMapa constructor) {
        this.constructor = constructor;
    }

    public Nivel construirNivelFacil() {
        return constructor.reiniciar()
                        .establecerNombre("Nivel Fácil")
                        .establecerDificultad(1)
                        .agregarPatronBloques(50, 50)
                        .construir();
    }

    public Nivel construirNivelMedio() {
        return constructor.reiniciar()
                        .establecerNombre("Nivel Medio")
                        .establecerDificultad(2)
                        .agregarBloqueIndestructible(50, 25)
                        .agregarPatronBloques(50, 100)
                        .construir();
    }

    public Nivel construirNivelDificil() {
        return constructor.reiniciar()
                        .establecerNombre("Nivel Difícil")
                        .establecerDificultad(3)
                        .agregarBloqueIndestructible(100, 50)
                        .agregarPatronBloques(50, 100)
                        .construir();
    }
}
