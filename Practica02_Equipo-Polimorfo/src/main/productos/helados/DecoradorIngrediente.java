package main.productos.helados;

public abstract class DecoradorIngrediente implements ComponenteHelado{

    protected ComponenteHelado heladoBase;

    public DecoradorIngrediente(ComponenteHelado heladoBase) {
        this.heladoBase = heladoBase;
    }

    public String getDescripcion(){
        //aqui va codigo 
        return null;
    }
    public double getPrecio(){
        //aqui va coddigo 
        return 0;
    }

}