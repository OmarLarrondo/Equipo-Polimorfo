package mvc.modelo.entidades.bloque;

import java.util.HashMap;

    //AGREGAR AL DIAGRAMA

/**
 * Representa un bloque en el campo de juego que puede ser destruido
 * por la pelota.
 *
 * @author Equipo-polimorfo
 * @version 1.0
 */
public class MapaBloques implements Cloneable{

    private final HashMap<Integer, HashMap<Integer, Bloque>> mapa;

    public MapaBloques(HashMap<Integer, HashMap<Integer, Bloque>> mapa){
        this.mapa = mapa;
    }

    public HashMap<Integer, HashMap<Integer, Bloque>> obtenerMapa(){
        if(this.mapa == null){
            return new HashMap<>();
        }
        return mapa;
    }

    public boolean agregarBloque(Bloque bloqueAAgregar, int i, int j){
        if(this.mapa == null || bloqueAAgregar == null)
            return false;
        
        if(i < 0 || j < 0)
            return false;
        
        boolean sobreescribe = true;


        if (!mapa.containsKey(i)){
            mapa.put((Integer)i, new HashMap<>());
            sobreescribe = false;
        }
        else {
            if(!mapa.get(i).containsKey(j))
                sobreescribe = false;
        }

        mapa.get(i).put(j, bloqueAAgregar);
        return sobreescribe;
    }

    public Bloque obtenerBloque(int i, int j){
        if(this.mapa == null)
            return null;

        if (!mapa.containsKey(i))
            return null;
        
        if(!mapa.get(i).containsKey(j))
            return null;
        
        return mapa.get(i).get(j);
    }

    public boolean removerBloque(int i, int j){
        if(this.mapa == null)
            return false;
        
        if(i < 0 || j < 0)
            return false;

        if (!mapa.containsKey(i))
            return false;
        
        if(!mapa.get(i).containsKey(j))
            return false;
        
        mapa.get(i).remove(j);
        if (mapa.get(i).isEmpty())
            mapa.remove(i);
        
        return true;
    }

    @Override
    public MapaBloques clone() {
        HashMap<Integer, HashMap<Integer, Bloque>> copiaMapa = new HashMap<>();

        for (var fila : this.mapa.entrySet()) {
            // Copiar los submapas filas
            copiaMapa.put(
                fila.getKey(),
                new HashMap<>(fila.getValue())
            );
        }

        return new MapaBloques(copiaMapa);
    }
}
