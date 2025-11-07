package modelo.ui_uix.Adapter;

import javafx.scene.input.MouseEvent;
import javafx.scene.input.MouseButton;


public class AdaptadorEntradaMouse implements AdaptadorEntrada {

    private boolean arriba = false;
    private boolean abajo = false;
    private boolean pausa = false;

    public void mousePresionado(MouseEvent e) {
        double y = e.getY();

        //CHECAR TAMAÑO DE L A PANGALLA
        if (y < 400) { 
            arriba = true;
        } else { 
            abajo = true;
        }

        //en la mitad,pausar
        if (e.getButton() == MouseButton.SECONDARY || e.getButton() == MouseButton.MIDDLE) {
            pausa = true; // al presionar
        }

    }

    public void mouseLiberado(MouseEvent e) {
        double y = e.getY();
        if (y < 400) {
            arriba = false;
        } else {
            abajo = false;
        }

        if (e.getButton() == MouseButton.SECONDARY || e.getButton() == MouseButton.MIDDLE) {
            pausa = false; // al soltar
        }

    }

    @Override
    public boolean arribaPresionado() {
        return arriba;
    }

    @Override
    public boolean abajoPresionado() {
        return abajo;
    }

    @Override
    public boolean pausaPresionado() {
        return pausa;
    }
}
