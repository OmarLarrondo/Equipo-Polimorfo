package main.Academia.Listas;

import java.util.List;
import java.util.ArrayList;

import main.MundoNinja.NinjaVoluntario;

public class ListaNinjaVoluntarios {

    private List<NinjaVoluntario> voluntarios;

    public ListaNinjaVoluntarios() {
        this.voluntarios = new ArrayList<>();
    }

    public void agregarVoluntario(NinjaVoluntario voluntario) {
        voluntarios.add(voluntario);
    }
    public NinjaVoluntario buscVoluntario(String nombre){
        //aqui va codigo
        return null;
    }

    //AQUI FALTA CHECAR SI USAREMOS ITERATOR DE JAVA.UTIL O HACEMOS UNO.
    public Iterator<NinjaVoluntario> iterator() {
        return new VoluntarioIterator();
    }

    // Clase interna que implementa Iterator
    private class VoluntarioIterator implements Iterator<NinjaVoluntario> {
        private int index = 0;

        @Override
        public boolean hasNext() {
            return index < voluntarios.size();
        }

        @Override
        public NinjaVoluntario next() {
            return voluntarios.get(index++);
        }

        @Override
        public void remove() {
            if (index > 0) {
                voluntarios.remove(--index);
            }
        }
    }

    public int size(){
        //aqui va su codigo 
        return 0;
    }

    public boolean isEmpty(){
        return size() == 0;
    }

}
