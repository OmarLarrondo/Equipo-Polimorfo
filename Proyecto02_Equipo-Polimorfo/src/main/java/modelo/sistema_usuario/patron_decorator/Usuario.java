package main.java.modelo.sistema_usuario.patron_decorator;

import java.time.LocalDateTime;

import main.java.modelo.prototype.ConfigPaleta;

public class Usuario implements ComponenteUsuario{
    String id;
    String nombreUsuario;
    String clave;
    String correo;
    int nivel;
    int experiencia;
    LocalDateTime fechaCreacion;
    ConfigPaleta configPaletaPreferida;

    public String obtenerDescripcion(){
        //aqui va su codigo
        return null;
    }
    public int obtenerNivel(){
        return nivel;
    }
    public int obtenerExperiencia(){
        return experiencia;
    }
    public void agregarExperiencia(int exp){
        //aqui va su codigo
    }
    public void guardarConfiguracionPaleta(ConfigPaleta config){
        //aqui va su codigo
    }
    public ConfigPaleta obtenerConfiguracionPaleta(){
        //aqui va su codigo
        return null;
    }    
}

