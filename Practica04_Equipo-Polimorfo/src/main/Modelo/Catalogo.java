import java.util.ArrayList;
import java.util.List;

import Composite.ProductoComponente;
import Visualizar.VisualizadorProducto;

public class Catalogo {
    private List<ProductoComponente> productos = new ArrayList<>();
    private VisualizadorProducto visualizador;
    
    
    public Catalogo(List<ProductoComponente> productos, VisualizadorProducto visualizador) {
        this.productos = productos;
        this.visualizador = visualizador;
    }

    public void agregarProducto(ProductoComponente producto){
        productos.add(producto);
    }
    
    public void removerProducto(ProductoComponente producto){
        productos.remove(producto);
    }
    public List<ProductoComponente> verCatalogoCompleto(){
        //aqu i va su codig 
        return null;
    }
    public List<ProductoComponente> filtrarPorGenero(String genero){
        //Aqui va su codigo 
        return null;
    }
    public List<ProductoComponente> filtrarPorPrecioMaximo(double precioMaximo){
        //aqui va su codigo
        return null;
    }
    public String mostrarProductoCompleto(ProductoComponente producto){
        //aqui va su codigo 
        return null;
    } 
    public List<ProductoComponente> mostrarCatalogo(){
        //aqui va su codigo
        return null;
    }
}