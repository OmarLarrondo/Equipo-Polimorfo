package main.Academia.Listas;

import java.util.Collection;
import java.util.Hashtable;

import main.MundoNinja.EstudianteNinja;

public class ListaEstudiantesNinjas implements EstudianteNinjaIterator {
    private Hashtable<String, EstudianteNinja> tabla;

    public ListaEstudiantesNinjas() {
        this.tabla = new Hashtable<>();
    }

    private void agregarEstudianteNinja(Estudiante e) {
        //aqui va cdoig 
    }
    private EstudianteNinja buscarEstudiante(String nombre){
        //aqui va codigo 
        return null;
    }

    //AQUI FALTA CHECAR SI USAREMOS ITERATOR DE JAVA.UTIL O HACEMOS UNO.
    private Iterator<EstudianteNinja> iterator(){
        return null;
    }
    // clase interna que implementa Iterator
    private class EstudianteIterator implements Iterator<EstudianteNinja> {
            //AQUI FALTA CHECAR SI USAREMOS ITERATOR DE JAVA.UTIL O HACEMOS UNO.
        private Iterator<EstudianteNinja> iteradorInterno;

        public EstudianteIterator() {
            Collection<EstudianteNinja> valores = tabla.values();
            this.iteradorInterno = valores.iterator();
        }

        @Override
        public boolean hasNext() {
            return iteradorInterno.hasNext();
        }

        @Override
        public EstudianteNinja next() {
            return iteradorInterno.next();
        }
    }



    
    
    
}
