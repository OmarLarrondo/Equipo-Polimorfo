package modelo.ui_uix.Adapter;

import javafx.scene.input.KeyCode;
import java.util.HashSet;
import java.util.Set;

public class AdaptadorEntradaTeclado implements AdaptadorEntrada {
    private Set<KeyCode> teclasPresionadas = new HashSet<>();

    public void teclaPresionada(KeyCode key) {
        teclasPresionadas.add(key);
    }

    public void teclaLiberada(KeyCode key) {
        teclasPresionadas.remove(key);
    }

    @Override
    public boolean arribaPresionado() {
        return teclasPresionadas.contains(KeyCode.W) || teclasPresionadas.contains(KeyCode.UP);
    }

    @Override
    public boolean abajoPresionado() {
        return teclasPresionadas.contains(KeyCode.S) || teclasPresionadas.contains(KeyCode.DOWN);
    }

    @Override
    public boolean pausaPresionado() {
        return teclasPresionadas.contains(KeyCode.P) || teclasPresionadas.contains(KeyCode.ESCAPE);
    }
}
