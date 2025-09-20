package main.MundoNinja;

import java.util.List;

import main.Academia.Campos.CampoConcreteFactory;
import main.Academia.Campos.CampoEntrenamiento;
import main.PaquetesHerramientas.PaquetesHerramientas;

/**
 * Clase para reresentar un grupo, formado por: un lider, una Lista de aspirantes,
 * un paquete de Herramienta ninja, asi como un lugar de entrenamiento.
 */
public class Grupo {
    /**EL lider del grupo(Es un VoluntarioNinja) */
    private NinjaVoluntario lider;
    /**La lista de Estudiantes(dependiendo el nivel del lider) */
    private List<EstudianteNinja> estudiantes;
    /**El paquete de Herramienta elegido */
    private PaquetesHerramientas paquete;
    /**Lugar de entrenamiento (segun la suma de los integrantes) */
    private CampoEntrenamiento lugarEntrenamiento;

    /**
     * Crea un grupo con un líder, lista de estudiantes y lugar de entrenamiento.
     * @param lider El líder del grupo (NinjaVoluntario)
     * @param estudiantes Lista de estudiantes del grupo
     * @param lugarEntrenamiento Lugar donde se entrenará el grupo
     */
    public Grupo(NinjaVoluntario lider, List<EstudianteNinja> estudiantes,
                CampoEntrenamiento lugarEntrenamiento) {
        this.lider = lider;
        this.estudiantes = estudiantes;
        this.lugarEntrenamiento = lugarEntrenamiento;
    }
    /**
     * Metodo para agregar un estudiante a la lista de Estudiantes.
     * @param estudiante Estudiante ninja a Agregar
     * @return {@code true } si se agrego correctamete {@code false} en otro caso
     */
    public boolean agregarEstudiante(EstudianteNinja estudiante){
        if(estudiante == null){
            return false;
        }
        estudiantes.add(estudiante);
        return true;
    }
    /**
     * Metodo para calcular la suma de las habilidades de todos los integrantes
     * @return La suma total de los integrantes
     */
    public int calcularSumaHabilidades(){
        int sumaTotal = lider.getNivelHabilidad();
        for(EstudianteNinja e : estudiantes){
            sumaTotal += e.getNivelHabilidad();
        }
        return sumaTotal;
    }
    /**
     * Metodo para asignar un paquete al grupo(puede ser personalizado o prefabricados)
     * @param p el paquete a asignar al grupo
     * @return {@code true } si se asigno el paquete correctamente, {@code false} en otro caso
     */
    public boolean asignarPaquete(PaquetesHerramientas p){
        if(p == null){
            return false;
        }
        this.paquete = p;
        return true;
    }

    /**
     * Asigna al grupo el campo de entranmiento que corresponda segun a la suma total
     * <p>Dependiendo la suma de las habilidades: </p>
     * <ul>
     * <li>Si sumaHabilidades <7: Valle del Dragón</li>
     * <li>Si sumaHabilidades esta entre 8-11: Bosque Sombrío </li>
     * <li> Si sumaHabilidades >12: Montaña Espiritual</li>
     * </ul>
     * @return {@code true} si se asigno correctamente {@code false} en otro caso 
     */
    public boolean asignarCampo() {
        try {
            CampoConcreteFactory factory = new CampoConcreteFactory();
            int sumaTotal = calcularSumaHabilidades();
            this.lugarEntrenamiento = factory.crearCampo(sumaTotal);
            return true; // asignación true
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * El resumen total del grupo debe mostar:
     * <p>
     *  <ul>
     * <li>Los integrantes</li>
     * <li>El paquete adquirido</li>
     * <li> Peso total </li>
     * <li> Campo asignado y su descripcion</li>
     * </ul></p>
     * 
     * @return el resumen Total del grupo
     */
    public String resumenTotal(){
        StringBuilder sb = new StringBuilder();
        sb.append("Lider: ").append(lider).append("\n");
        sb.append("Estudiantes: \n");
        for(EstudianteNinja e : estudiantes){
            sb.append("--").append(e.getNombre()).append("--");
        }
        sb.append("Paquete: ").append(paquete).append("\n");
        sb.append("Peso total de las Herramientas: ").append(paquete.getPesoTotal()).append("\n");
        sb.append("Lugar de entrenamiento").append(lugarEntrenamiento).append("\n");
        sb.append("Descripcion del lugar: ").append(lugarEntrenamiento.getDescripcion());
        return sb.toString();
    }

    /**
     * Obtiene el líder del grupo.
     *
     * @return El ninja voluntario que lidera el grupo
     */
    public NinjaVoluntario getLider() {
        return lider;
    }

    /**
     * Obtiene la lista de estudiantes del grupo.
     *
     * @return Lista de estudiantes ninja en el grupo
     */
    public List<EstudianteNinja> getEstudiantes() {
        return estudiantes;
    }

    /**
     * Obtiene el paquete de herramientas asignado al grupo.
     *
     * @return El paquete de herramientas del grupo
     */
    public PaquetesHerramientas getPaquete() {
        return paquete;
    }

    /**
     * Obtiene el lugar de entrenamiento asignado al grupo.
     *
     * @return El campo de entrenamiento del grupo
     */
    public CampoEntrenamiento getLugarEntrenamiento() {
        return lugarEntrenamiento;
    }
}
