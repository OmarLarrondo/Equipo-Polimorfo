package modelo.sistema_usuario.patron_decorator;

import java.time.LocalDateTime;
import modelo.editor.prototype.ConfigPaleta;

/**
 * Representa un usuario del sistema con atributos de identificación, 
 * nivel, experiencia y configuración de paleta preferida.
 * Implementa la interfaz {@link ComponenteUsuario}.
 * 
 * @author Equipo-polimorfo
 * @version 1.0
 */
public class Usuario implements ComponenteUsuario {

    /** Identificador único del usuario */
    private String id;

    /** Nombre de usuario */
    private String nombreUsuario;

    /** Clave de acceso */
    private String clave;

    /** Correo electrónico */
    private String correo;

    /** Nivel actual del usuario */
    private int nivel;

    /** Experiencia acumulada del usuario */
    private int experiencia;

    /** Fecha de creación del usuario */
    private LocalDateTime fechaCreacion;

    /** Configuración de paleta preferida del usuario */
    private ConfigPaleta configPaletaPreferida;

    /**
     * Crea un nuevo usuario con los datos básicos y nivel inicial 1.
     * 
     * @param id Identificador único del usuario
     * @param nombreUsuario Nombre de usuario
     * @param clave Clave de acceso
     * @param correo Correo electrónico
     */
    public Usuario(String id, String nombreUsuario, String clave, String correo) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.clave = clave;
        this.correo = correo;
        this.nivel = 1;
        this.experiencia = 0;
        this.fechaCreacion = LocalDateTime.now();
    }

    /**
     * Obtiene una descripción resumida del usuario.
     * 
     * @return Descripción del usuario con nombre, nivel y experiencia.
     */
    public String obtenerDescripcion() {
        return "Usuario [nombreUsuario=" + nombreUsuario + ", nivel=" + nivel + ", experiencia=" + experiencia + "]";
    }

    /**
     * Obtiene el nivel actual del usuario.
     * 
     * @return Nivel del usuario
     */
    public int obtenerNivel() {
        return nivel;
    }

    /**
     * Obtiene la experiencia acumulada del usuario.
     * 
     * @return Experiencia del usuario
     */
    public int obtenerExperiencia() {
        return experiencia;
    }

    /**
     * Agrega experiencia al usuario. Si se supera 100 puntos, se incrementa el nivel.
     * 
     * @param exp Cantidad de experiencia a agregar
     */
    public void agregarExperiencia(int exp) {
        if (exp > 0) {
            experiencia += exp;
        }

        while (experiencia >= 100) {
            experiencia -= 100;
            nivel++;
            System.out.println("Felicidades, ha subido de nivel!");
        }
    }

    /**
     * Guarda la configuración de paleta preferida del usuario.
     * 
     * @param config Configuración de paleta a guardar
     */
    public void guardarConfiguracionPaleta(ConfigPaleta config) {
        this.configPaletaPreferida = config;
    }

    /**
     * Obtiene la configuración de paleta preferida del usuario.
     * 
     * @return Configuración de paleta preferida
     */
    public ConfigPaleta obtenerConfiguracionPaleta() {
        return configPaletaPreferida;
    }

    // ---------- Getters y Setters ----------

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }

    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public int getNivel() { return nivel; }
    public void setNivel(int nivel) { this.nivel = nivel; }

    public int getExperiencia() { return experiencia; }
    public void setExperiencia(int experiencia) { this.experiencia = experiencia; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public ConfigPaleta getConfigPaletaPreferida() { return configPaletaPreferida; }
    public void setConfigPaletaPreferida(ConfigPaleta configPaletaPreferida) { this.configPaletaPreferida = configPaletaPreferida; }
}
