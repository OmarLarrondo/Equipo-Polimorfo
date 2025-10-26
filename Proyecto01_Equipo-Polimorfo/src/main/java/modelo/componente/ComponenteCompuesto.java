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
    /** Lista de componentes hijos que conforman este componente compuesto */
    private List<ComponentePC> componentes;

    /** Nombre del componente compuesto */
    private String nombre;

    /** Tipo del componente compuesto */
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
     * <p>Si la lista de componentes es null, se inicializa automáticamente.
     *
     * @param componenteAgregar componente a agregar (no puede ser null)
     * @throws IllegalArgumentException si el componente a agregar es null
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
     * @throws IllegalStateException si la lista de componentes es null o el componente a remover es null
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
     * @throws IllegalArgumentException si el índice es inválido
     * @throws IllegalStateException si la lista de componentes es null
     */
    public ComponentePC obtenerHijo(int numHijo){
        if(componentes == null) throw new IllegalStateException("No hay componentes");
        if(numHijo < 0 || numHijo >= componentes.size()){
            throw new IllegalArgumentException("Indice invalido!!");
        }
        return componentes.get(numHijo);
    }

    /**
     * Obtiene el nombre del componente compuesto.
     *
     * @return el nombre del componente compuesto
     */
    @Override
    public String obtenerNombre() {
        return nombre;
    }

    /**
     * Calcula y obtiene el precio total del componente compuesto.
     *
     * <p>El precio total es la suma de los precios de todos los componentes hijos.
     * Si no hay componentes, retorna 0.
     *
     * @return el precio total del componente compuesto
     */
    @Override
    public double obtenerPrecio() {
        if (componentes == null) {
            return 0;
        }
        double total = 0;
        for(ComponentePC cm : componentes){
            total += cm.obtenerPrecio();
        }
        return total;
    }

    /**
     * Obtiene la marca del componente compuesto.
     *
     * <p>Retorna la marca del primer componente hijo. Si no hay componentes,
     * retorna "Sin marca".
     *
     * @return la marca del primer componente o "Sin marca" si está vacío
     */
    @Override
    public String obtenerMarca() {
        if (componentes == null || componentes.isEmpty()) return "Sin marca";
        return componentes.get(0).obtenerMarca();
    }

    /**
     * Obtiene el tipo del componente compuesto.
     *
     * @return el tipo del componente compuesto
     */
    @Override
    public String obtenerTipo() {
        return tipo;
    }

    /**
     * Genera una representación en texto de los detalles del componente compuesto.
     *
     * <p>Incluye el nombre, tipo y los detalles de todos los componentes hijos
     * de forma jerárquica.
     *
     * @return una cadena con los detalles completos del componente compuesto y sus hijos
     */
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

    /**
     * Obtiene un iterador sobre los componentes hijos.
     *
     * @return un IteratorComponentePC para recorrer los componentes
     */
    public IteratorComponentePC getIterator(){
        return new IteradorComponentes();
    }

    /**
     * Implementacion concreta del patron Iterator para recorrer los componentes.
     *
     * <p>Esta clase interna privada proporciona un mecanismo seguro para iterar
     * sobre la lista de componentes sin exponer la estructura interna del compuesto.
     */
    private class IteradorComponentes implements IteratorComponentePC {
        /** Posicion actual en la iteracion */
        private int posicion;

        /**
         * Construye un nuevo iterador inicializado en la primera posicion.
         */
        public IteradorComponentes() {
            this.posicion = 0;
        }

        /**
         * Verifica si existen mas elementos en la coleccion de componentes.
         *
         * @return {@code true} si hay mas elementos por recorrer, {@code false} en caso contrario
         */
        @Override
        public boolean hasNext() {
            return componentes != null && posicion < componentes.size();
        }

        /**
         * Obtiene el siguiente componente de la iteracion.
         *
         * @return el siguiente ComponentePC en la coleccion
         * @throws java.util.NoSuchElementException si no hay mas elementos disponibles
         */
        @Override
        public ComponentePC next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException("No hay mas elementos en la iteracion");
            }
            return componentes.get(posicion++);
        }
    }
}
