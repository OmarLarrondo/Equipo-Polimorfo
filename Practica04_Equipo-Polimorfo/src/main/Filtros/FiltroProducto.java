package Filtros;

import java.util.List;

import Composite.ProductoComponente;

public interface FiltroProducto {
    public List<ProductoComponente> filtrar(List<ProductoComponente> Items, String criterio);
    
}
