package modelo.componente;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Representa un componente compuesto de una PC, que puede contener otros
 * componentes (hijos) y calcular información agregada como precio total.
 * 
 * <p>Ejemplo de uso: un conjunto de componentes de una PC (tarjeta madre,
 * GPU, RAM, discos, duende).
 * 
 * @author Equipo-polimorfo
 * @version 1.0
 */
public class ComponenteCompuesto implements ComponentePC {
    /** La lista de componenete */
    private List<ComponentePC> componentes;
    /**Noombre del componente compuesto */ 
    private String nombre;
    /**Tipo del componente compuesto */
    private String tipo;

    /**
     * Constructor para inicializar el componente compuesto.
     * 
     * @param componentes lista inicial de componentes; puede ser null
     * @param nombre nombre del componente compuesto
     * @param tipo tipo del componente compuesto
     */
    public ComponenteCompuesto(List<ComponentePC> componentes, String nombre, String tipo) {
        this.componentes = componentes;
        this.nombre = nombre;
        this.tipo = tipo;
    }

    /**
     * Agrega un componente hijo al componente compuesto.
     * 
     * @param componenteAgregar componente a agregar (no puede ser null)
     */
    public void agregar(ComponentePC componenteAgregar){
        if (componenteAgregar == null) {
            throw new IllegalArgumentException("El componente a agregar no puede ser null.");
        }
        if (componentes == null) {
            this.componentes = new ArrayList<>();
        }
        componentes.add(componenteAgregar);
    }

    /**
     * Remueve un componente hijo del componente compuesto.
     * 
     * @param componenteRemover componente a remover
     */
    public void remover(ComponentePC componenteRemover){
        if(componentes == null || componenteRemover == null){
            throw new IllegalStateException("No hay nada para remover");
        }
        componentes.remove(componenteRemover);
    }

    /**
     * Obtiene un hijo específico por índice.
     * 
     * @param numHijo índice del hijo (0 basado)
     * @return el componente hijo en la posición indicada
     */
    public ComponentePC obtenerHijo(int numHijo){
        if(numHijo < 0 || numHijo > componentes.size()){
            throw new IllegalArgumentException("Indice invalido!!");
        }
        if(componentes == null) throw new IllegalStateException("No hay componentes");
        return componentes.get(numHijo);
    }

    @Override
    public String obtenerNombre() {
        return nombre;
    }

    @Override
    public double obtenerPrecio() {
        //AQUI NO SE SI ES TODOS LOS COMPONENTESPC SEGUN YO SI, PORQUE AQUI NO HAY 
        // NINGUN ATRIBUTO PRECIO.
        if (componentes == null) {
            return 0;
        }
        double total = 0;
        for(ComponentePC cm : componentes){
            total += cm.obtenerPrecio();
        }
        return total;
    }

    @Override
    public String obtenerMarca() {
        if (componentes == null || componentes.isEmpty()) return "Sin marca";
        // Retorna la marca del primer componente...
        return componentes.get(0).obtenerMarca();
    }

    @Override
    public String obtenerTipo() {
        return tipo;
    }

    @Override
    public String mostrarDetalles() {
        StringBuilder sb = new StringBuilder();
        sb.append("Componente: ").append(nombre).append(" (").append(tipo).append(")\n");
        if (componentes != null) {
            for (ComponentePC cm : componentes) {
                sb.append(" - ").append(cm.mostrarDetalles()).append("\n");
            }
        }
        return sb.toString();
    }

    //NO ME ACUERDO COMO ES.
    /**
     * Obtiene un iterador sobre los componentes hijos.
     * 
     * @return un Iterator de ComponentePC
     */
    public Iterator<ComponentePC> getIterator(){
        //aqui va su codigo
        return null;
    }
}
