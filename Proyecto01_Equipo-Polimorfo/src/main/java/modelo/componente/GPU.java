package modelo.componente;

/**
 * Clase que representa una tarjeta gráfica (GPU) como componente hoja de una PC.
 * 
 * <p>Extiende {@link ComponenteHoja} e incluye atributos específicos de las GPU,
 * como (tipo de memoria y cantidad de VRAM).
 * 
 * <p>La compatibilidad de una GPU con otros componentes se define principalmente
 * para GPUs AMD con ciertas motherboards, por lo que el método
 * {@link #esCompatibleConComponentePC()} debe ser implementado considerando
 * esa regla.
 * 
 * @author Equipo-polimorfo
 * @version 1.0
 */
public class GPU extends ComponenteHoja {
    /**El tipo de memoria de la GPU (GDDR6, GDDR5).*/
    private String tipoMemoriaGPU;
    /**la vRam del GPU la VRAM es la memoria exclusiva de la tarjeta gráfica que permite
     * manejar y renderizar imágenes y gráficos de manera rápida y eficiente.(ejemplo: 8GB, 24GB) 
     * */
    private int vRam;

    /**
     * Construye una GPU con sus atributos básicos y específicos.
     * 
     * @param nombre el nombre de la GPU (ejemplo: "GTX 1660", "RTX 4080")
     * @param precio el precio de la GPU (ejemplo: 1200.02)
     * @param marca la marca de la GPU (por ejemplo, AMD, NVIDIA)
     * @param tipo el tipo de componente (siempre "GPU" en este caso)
     * @param tipoMemoriaGPU el tipo de memoria de la GPU (GDDR6, GDDR5, etc.)
     * @param vRam la cantidad de memoria de video en GB (ejmeplo 8, 24)
     */
    public GPU(String nombre, double precio, String marca, String tipo, String tipoMemoriaGPU, int vRam) {
        super(nombre, precio, marca, tipo);
        this.tipoMemoriaGPU = tipoMemoriaGPU;
        this.vRam = vRam;
    }

    /**
     * Obtiene el tipo de memoria de la GPU.
     * @return el tipo de memoria (por ejemplo, "GDDR6")
     */
    public String getTipoMemoriaGPU() {
        return tipoMemoriaGPU;
    }

    /**
     * Obtiene la cantidad de VRAM de la GPU en GB.
     * @return la cantidad de VRAM
     */
    public int getVRAM() {
        return vRam;
    }

    /**
     * Devuelve una representación en texto con los detalles completos de la GPU.
     * @return una cadena formateada con el nombre, marca, tipo, VRAM, tipo de memoria y precio.
     */
    @Override
    public String toString() {
        return String.format("GPU: %s | Marca: %s | Tipo: %s | VRAM: %d GB | Tipo Memoria: %s | Precio: $%.2f",
            obtenerNombre(), obtenerMarca(), obtenerTipo(), vRam, tipoMemoriaGPU, obtenerPrecio());
    }
}
