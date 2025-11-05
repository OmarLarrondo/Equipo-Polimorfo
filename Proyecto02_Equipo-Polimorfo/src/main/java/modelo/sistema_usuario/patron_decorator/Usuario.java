package modelo.sistema_usuario.patron_decorator;

import java.time.LocalDateTime;
import modelo.prototype.ConfigPaleta;

public class Usuario implements ComponenteUsuario{
    private String id;
    private String nombreUsuario;
    private String clave;
    private String correo;
    private int nivel;
    private int experiencia;
    private LocalDateTime fechaCreacion;
    private ConfigPaleta configPaletaPreferida;

    public Usuario(String id, String nombreUsuario, String clave, String correo) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.clave = clave;
        this.correo = correo;
        this.nivel = 1;
        this.experiencia = 0;
        this.fechaCreacion = LocalDateTime.now();
    }

    public String obtenerDescripcion(){
        return "Usuario [nombreUsuario=" + nombreUsuario + ", nivel="
                + nivel + ", experiencia=" + experiencia;
        
    }
    public int obtenerNivel(){
        return nivel;
    }
    public int obtenerExperiencia(){
        return experiencia;
    }
    public void agregarExperiencia(int exp){
        if (exp > 0) {
            experiencia += exp;
        }

        while (experiencia >= 100) {
            experiencia -= 100;
            nivel ++;
            System.out.println("Felicidades, ha subido de nivel!");
            
        }
    }
    public void guardarConfiguracionPaleta(ConfigPaleta config){
        this.configPaletaPreferida = config;
    }
    public ConfigPaleta obtenerConfiguracionPaleta(){
        return configPaletaPreferida;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(int experiencia) {
        this.experiencia = experiencia;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public ConfigPaleta getConfigPaletaPreferida() {
        return configPaletaPreferida;
    }

    public void setConfigPaletaPreferida(ConfigPaleta configPaletaPreferida) {
        this.configPaletaPreferida = configPaletaPreferida;
    }    
    
}

