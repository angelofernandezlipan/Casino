package modelo;

// Superclase abstracta (molde)
public abstract class ApuestaBase {

    // Protected: Accesible por las subclases (ApuestaRojo, etc.)
    protected int montoApostado;
    protected String etiqueta;

    // Constructor para inicializar los atributos comunes
    public ApuestaBase(int montoApostado, String etiqueta) {
        this.montoApostado = montoApostado;
        this.etiqueta = etiqueta;
    }

    // Méthodo polimórfico ABSTRACTO
    // Obliga a las subclases a implementar su propia lógica de acierto
    public abstract boolean acierta(int numero, String color);

    // --- GETTERS ---
    // Métodos públicos para que otras clases (como Resultado) lean los datos.
    // Este es el méthodo que el compilador no encontraba.

    public int getMontoApostado() {
        return montoApostado;
    }

    public String getEtiqueta() {
        return etiqueta;
    }
}