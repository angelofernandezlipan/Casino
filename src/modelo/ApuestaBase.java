package modelo;

// Superclase abstracta (molde)

public abstract class ApuestaBase {

    protected int montoApostado;
    protected String etiqueta;

    public ApuestaBase{
        this.montoApostado = montoApostado;
        this.etiqueta = etiqueta;
    }

    // Method polimórfico ABSTRACTO
    // Cada subclase DEBE implementar su propia lógica de acierto.
    public abstract boolean acierta(int numero, String color);

    // Getters
    public int getMontoApostado() {
        return montoApostado;
    }

    public String getEtiqueta() {
        return etiqueta;
    }
}
