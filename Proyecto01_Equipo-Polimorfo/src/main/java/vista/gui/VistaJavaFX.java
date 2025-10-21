package vista.gui;

import java.util.Queue;

import controlador.ControladorPrincipal;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import modelo.componente.ComponenteCompuesto;
import modelo.componente.ComponentePC;
import modelo.ticket.Ticket;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Mensaje...");
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    public void mostrarError(String error){
        Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error!");
            alerta.setContentText(error);
            alerta.showAndWait();
    }

    public void mostrarMenu(List<String> opciones){
        Dialog<ButtonType> dialogo = new Dialog<>();
        dialogo.setTitle("Menu.");

        List<ButtonType> botones = new ArrayList<>();
        for (String op : opciones) {
            ButtonType boton = new ButtonType(op);
            botones.add(boton);
            dialogo.getDialogPane().getButtonTypes().addAll(boton);
        }
        Optional <ButtonType> resultado = dialogo.showAndWait();
        int opcionElegida = -1;
        if(resultado.isPresent()){
            ButtonType presionado = resultado.get();
            for(int i = 0; i < botones.size(); i++){
                if (presionado.equals(botones.get(i))) {
                    opcionElegida = i+ 1;
                    break;
                }
            }
        }
        //FALTA CODIGO 
        // saun no lo veo si es que controladorPrincipal tiene algo para 
        // recibir la opcion controlador.
    }

    //este lo usara mostrar menu. O NO SE JAJA
    private int leerOpcion(){
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
