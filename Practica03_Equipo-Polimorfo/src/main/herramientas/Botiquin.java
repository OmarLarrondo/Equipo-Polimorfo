package herramientas;

public class Botiquin extends Herramienta {

    @Override
    public Herramienta crearHerramienta() {
        return new Botiquin();
    }
    
}
