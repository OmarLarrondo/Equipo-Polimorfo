package persistencia;

import java.util.List;
public class ManejadorArchivos {
    private String rutaBae;

    public void escribirArchivo(String ruta, String contenido){
        //aqui va su codigo
    }

    //¿SE USARA BUFFER? checar
    public String leerArchivo(String ruta){
        //aqui va su codigo
        return null;
    }
    //ver si eciste el arc
    public boolean existeArchivo(String ruta){
        //aqui va su codigo
        return false;
    }
    //ver que no sea null, buscarla, preguntar y eliminar
    public void eliminarArchivo(String ruta){
        //aqui va su codigo
    }
    //No null, try, advertencias de no eliminar algo existente,crear.
    public void crearDirectorio(String ruta){

    }
    //¿Para que sirvira???????
    public List<String> listaArchivos(String directorio){
        //aqui va su codigo
        return null;
    }
}
