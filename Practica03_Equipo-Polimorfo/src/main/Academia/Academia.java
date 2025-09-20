package main.Academia;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;
import java.util.concurrent.ThreadLocalRandom;

import main.MundoNinja.*;
import main.Academia.Listas.*;
import main.Academia.Paquetes.*;
import main.Academia.Campos.*;
import main.PaquetesHerramientas.*;

/**
 * Clase de dominio que coordina todas las actividades de la Academia Ninja.
 * Implementa la lógica de negocio pura para gestionar aspirantes, voluntarios,
 * formar grupos y asignar recursos.
 *
 * <p>Esta clase actúa como el modelo de dominio que:</p>
 * <ul>
 *   <li>Gestiona aspirantes (almacenados en HashTable)</li>
 *   <li>Gestiona voluntarios (almacenados en Array)</li>
 *   <li>Forma grupos según reglas de negocio</li>
 *   <li>Asigna paquetes de herramientas</li>
 *   <li>Asigna campos de entrenamiento</li>
 *   <li>Genera reportes y resúmenes</li>
 * </ul>
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class Academia {

    /** Lista de estudiantes aspirantes almacenada en HashTable */
    private final ListaEstudiantesNinjas aspirantes;

    /** Lista de ninjas voluntarios almacenada en Array */
    private final ListaNinjaVoluntarios voluntarios;

    /** Gestor para construir paquetes de herramientas */
    private final GestorPaquetes gestorPaquetes;

    /** Factory para crear campos de entrenamiento */
    private final CampoConcreteFactory campoFactory;

    /** Lista inmutable de grupos formados durante la sesión */
    private final List<Grupo> gruposFormados;

    /** Generador de números aleatorios para diversidad en datos */
    private static final Random RANDOM = ThreadLocalRandom.current();

    /**
     * Lista de nombres predefinidos para generar aspirantes.
     */
    private static final List<String> NOMBRES_ASPIRANTES = Arrays.asList(
        "Akira", "Yuki", "Hiroshi", "Sakura", "Takeshi",
        "Emiko", "Daichi", "Miho", "Ren", "Sato",
        "Kayo", "Hana", "Taro", "Nana", "Kenta"
    );

    /**
     * Lista de nombres predefinidos para generar voluntarios.
     */
    private static final List<String> NOMBRES_VOLUNTARIOS = Arrays.asList(
        "Sensei Hayato", "Maestro Kenji", "Capitán Rei", "Líder Shinji", "Instructor Masa"
    );

    /**
     * Constructor que inicializa la Academia con todas sus dependencias.
     */
    public Academia() {
        this.aspirantes = new ListaEstudiantesNinjas();
        this.voluntarios = new ListaNinjaVoluntarios();
        this.gestorPaquetes = new GestorPaquetes(new PaquetePersonalizadoBuilder());
        this.campoFactory = new CampoConcreteFactory();
        this.gruposFormados = new ArrayList<>();
    }

    /**
     * Inicializa los datos base del sistema con estudiantes y voluntarios predefinidos.
     * Crea al menos 10 aspirantes y 5 voluntarios con datos diversos.
     */
    public void inicializarDatos() {
        inicializarAspirantes();
        inicializarVoluntarios();
    }

    /**
     * Inicializa la lista de aspirantes.
     * Crea al menos 10 aspirantes con datos diversos usando el patrón Builder.
     */
    private void inicializarAspirantes() {
        Supplier<EstudianteNinja> generadorAspirantes = () -> {
            String nombre = NOMBRES_ASPIRANTES.get(RANDOM.nextInt(NOMBRES_ASPIRANTES.size()));
            int edad = RANDOM.nextInt(13) + 12;
            ClanesDeProcedencia clan = ClanesDeProcedencia.values()[RANDOM.nextInt(ClanesDeProcedencia.values().length)];
            int nivelHabilidad = RANDOM.nextInt(3) + 1;

            return new EstudianteNinja.Builder()
                .nombre(nombre + "_" + RANDOM.nextInt(1000))
                .edad(edad)
                .clanesDeProcedencia(clan)
                .nivelHabilidad(nivelHabilidad)
                .build();
        };

        Stream.generate(generadorAspirantes)
            .limit(12)
            .forEach(aspirantes::agregarEstudianteNinja);
    }

    /**
     * Inicializa la lista de voluntarios.
     * Crea al menos 5 voluntarios con rangos diversos usando el patrón Builder.
     */
    private void inicializarVoluntarios() {
        Function<Rangos, NinjaVoluntario> crearVoluntarioPorRango = rango -> {
            String nombre = NOMBRES_VOLUNTARIOS.get(RANDOM.nextInt(NOMBRES_VOLUNTARIOS.size()));
            int edad = RANDOM.nextInt(15) + 25; 
            ClanesDeProcedencia clan = ClanesDeProcedencia.values()[RANDOM.nextInt(ClanesDeProcedencia.values().length)];
            int nivelHabilidad = RANDOM.nextInt(3) + 4;

            return new NinjaVoluntario.Builder()
                .nombre(nombre + "_" + rango + "_" + RANDOM.nextInt(100))
                .edad(edad)
                .clanesDeProcedencia(clan)
                .rango(rango)
                .nivelHabilidad(nivelHabilidad)
                .build();
        };

        Arrays.stream(Rangos.values())
            .flatMap(rango ->
                Stream.generate(() -> crearVoluntarioPorRango.apply(rango))
                    .limit(2)
            )
            .forEach(voluntarios::agregarVoluntario);
    }

    /**
     * Forma grupos de aspirantes con voluntarios.
     * Aplica las reglas de negocio: Genin(1), Chunin(2), Jonin(3) aspirantes.
     *
     * @return Lista inmutable de grupos formados
     */
    public List<Grupo> formarGrupos() {
        List<Grupo> nuevosGrupos = new ArrayList<>();

        main.Academia.Listas.Iterator<NinjaVoluntario> iteradorVoluntarios = voluntarios.iterator();

        List<EstudianteNinja> aspirantesDisponibles = new ArrayList<>();
        main.Academia.Listas.Iterator<EstudianteNinja> iteradorAspirantes = aspirantes.iterator();
        while (iteradorAspirantes.hasNext()) {
            aspirantesDisponibles.add(iteradorAspirantes.next());
        }

        while (iteradorVoluntarios.hasNext() && !aspirantesDisponibles.isEmpty()) {
            NinjaVoluntario lider = iteradorVoluntarios.next();
            int capacidad = lider.getCapacidadVoluntarios();

            List<EstudianteNinja> miembrosGrupo = aspirantesDisponibles.stream()
                .limit(Math.min(capacidad, aspirantesDisponibles.size()))
                .collect(Collectors.toList());

            if (!miembrosGrupo.isEmpty()) {
                Grupo nuevoGrupo = new Grupo(lider, miembrosGrupo, null);

                nuevoGrupo.asignarCampo();

                nuevosGrupos.add(nuevoGrupo);

                aspirantesDisponibles.removeAll(miembrosGrupo);
            }
        }

        gruposFormados.addAll(nuevosGrupos);

        return Collections.unmodifiableList(nuevosGrupos);
    }

    /**
     * Asigna paquetes de herramientas a todos los grupos formados.
     * Utiliza el patrón Builder para la asignación según la suma de habilidades.
     */
    public void asignarPaquetes() {
        if (gruposFormados.isEmpty()) {
            return;
        }

        List<Grupo> grupos = gruposFormados;
        Function<Grupo, PaquetesHerramientas> seleccionarPaquete = grupo -> {
            int sumaHabilidades = grupo.calcularSumaHabilidades();
            int tamano = grupo.getEstudiantes().size() + 1;

            if (sumaHabilidades <= 8) {
                return gestorPaquetes.construirPaqueteBasico();
            } else if (sumaHabilidades <= 12) {
                return gestorPaquetes.construirPaqueteAvanzado();
            } else {
                return gestorPaquetes.construirPaqueteTactico();
            }
        };

        grupos.stream()
            .forEach(grupo -> {
                PaquetesHerramientas paquete = seleccionarPaquete.apply(grupo);
                grupo.asignarPaquete(paquete);
            });
    }

    /**
     * Asigna campos de entrenamiento a todos los grupos formados automáticamente
     * según la suma de niveles de habilidad de sus integrantes.
     */
    public void asignarCampos() {
        gruposFormados.stream()
            .forEach(grupo -> grupo.asignarCampo());
    }

    /**
     * Crea un paquete personalizado de herramientas usando el Builder.
     *
     * @return Paquete personalizado construido
     */
    public PaquetesHerramientas crearPaquetePersonalizado() {
        // Por ahora retorna un paquete táctico como personalizado
        // TODO: Implementar verdadera personalización cuando esté disponible
        return gestorPaquetes.construirPaqueteTactico();
    }

    /**
     * Muestra un resumen completo de todos los grupos formados.
     * Utiliza Stream processing para generar el output.
     */
    public void mostrarResumen() {
        String reporte = generarReporteCompleto();
        System.out.println(reporte);
    }

    /**
     * Genera un reporte completo de todos los grupos formados.
     * Utiliza Stream processing para generar el output.
     *
     * @return String con el reporte completo de la academia
     */
    private String generarReporteCompleto() {
        if (gruposFormados.isEmpty()) {
            return "No se han formado grupos aún. Ejecute formarGrupos() primero.";
        }

        StringBuilder reporte = new StringBuilder();
        reporte.append("=== REPORTE COMPLETO DE LA ACADEMIA NINJA ===\n\n");

        long totalAspirantes = gruposFormados.stream()
            .mapToLong(grupo -> grupo.getEstudiantes().size())
            .sum();

        long totalVoluntarios = gruposFormados.size();

        reporte.append(String.format("Total de grupos formados: %d\n", gruposFormados.size()));
        reporte.append(String.format("Total de aspirantes asignados: %d\n", totalAspirantes));
        reporte.append(String.format("Total de voluntarios activos: %d\n", totalVoluntarios));
        reporte.append("\n");

        String detalleGrupos = gruposFormados.stream()
            .map(grupo -> String.format("--- GRUPO %d ---\n%s\n",
                gruposFormados.indexOf(grupo) + 1, grupo.resumenTotal()))
            .collect(Collectors.joining("\n"));

        reporte.append(detalleGrupos);

        return reporte.toString();
    }

    /**
     * Ejecuta el proceso completo de la academia: formar grupos y asignar recursos.
     * Método principal que coordina toda la lógica de negocio.
     */
    public void ejecutarActividades() {
        formarGrupos();
        asignarPaquetes();
        asignarCampos();
    }

    /**
     * Obtiene la lista inmutable de grupos formados.
     *
     * @return Lista inmutable de grupos
     */
    public List<Grupo> getGruposFormados() {
        return Collections.unmodifiableList(gruposFormados);
    }

    /**
     * Obtiene estadísticas de la academia.
     *
     * @return Map con estadísticas clave
     */
    public Map<String, Object> obtenerEstadisticas() {
        return gruposFormados.stream()
            .collect(Collectors.toMap(
                grupo -> "Grupo_" + (gruposFormados.indexOf(grupo) + 1),
                grupo -> Map.of(
                    "lider", grupo.getLider().getNombre(),
                    "rango", grupo.getLider().getRango(),
                    "miembros", grupo.getEstudiantes().size(),
                    "sumaHabilidades", grupo.calcularSumaHabilidades(),
                    "campo", Optional.ofNullable(grupo.getLugarEntrenamiento())
                            .map(CampoEntrenamiento::getNombre)
                            .orElse("Sin asignar")
                )
            ));
    }

}
