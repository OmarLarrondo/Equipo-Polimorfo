package main.productos.helados;

public class GomitasGusano extends DecoradorIngrediente {
    private int cantidad;
    

    
    public GomitasGusano(ComponenteHelado heladoBase, int cantidad) {
        super(heladoBase);
        this.cantidad = cantidad;
    }
    
    @Override
    public String getDescripcion(){
        //aqui va codigo
        return null;
    }
    @Override
    public double getPrecio(){
        //aqui va codigo
        return 0;
    }


}