package vista.gui;

import java.util.Queue;

import controlador.ControladorPrincipal;
import javafx.scene.Scene;
import javafx.stage.Stage;
import modelo.componente.ComponenteCompuesto;
import modelo.componente.ComponentePC;
import modelo.ticket.Ticket;

import java.util.List;

public class VistaJavaFX {
    private Stage primaryStage;
    private Scene currentScene;
    private ControladorPrincipal controlador;
    private Queue<String> entradaUsuario;

    public VistaJavaFX(Stage stage){
        this.primaryStage = stage;
    } 
    
    public void setControlador(ControladorPrincipal controlador){
        this.controlador = controlador;
    }

    public void mostrarMensaje(String mensaje){
        //aqui va su codigo 
    }

    public void mostrarError(String error){
        //aqui va su codigo
    }

    public void mostrarMenu(List<String> opciones){
        //aqui va su codigo
    }

    public int leerOpcion(){
        //aqui va su codigo
        return 0;
    }

    public String leerTexto(String prompt){
        //aqui va su codigo
        return null;

    }
    public void  mostrarComponentes(List<ComponentePC> componenetes){
        //aqui va su codigo

    }
    public void mostrarTicket(Ticket ticket){
        //aqui va su codigo 
    }

    public boolean confirmar(String mensaje){
        //aqui va su codigo 
        return false;
    }
    public void limpiarPantalla(){
        //aqui va su codigo 
    }
    public void cambiarEscena(Scene escena){
        //aqui va su codigo 
    }
    private Scene crearEscenaPrincipal(){
        //aqui va su codigo
        return null;

    }
}
