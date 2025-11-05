package modelo.sistema_usuario.patron_decorator;

import java.time.LocalDateTime;

public class Logro {
    private String id;
    private String nombre;
    private String descripcion;
    private String icono;
    private LocalDateTime desbloqueadoEn;

    public Logro(String id, String nombre, String descripcion, String icono) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.icono = icono;
        this.desbloqueadoEn = null; // aún no desbloqueado
    }

    public void desbloquear() {
        if (desbloqueadoEn == null) {
            this.desbloqueadoEn = LocalDateTime.now();
            System.out.println("🏆 Logro desbloqueado: " + nombre);
        }
    }

    public boolean estaDesbloqueado() {
        return desbloqueadoEn != null;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getIcono() {
        return icono;
    }

    public LocalDateTime getDesbloqueadoEn() {
        return desbloqueadoEn;
    }
}
