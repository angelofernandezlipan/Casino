package modelo;

public class ApuestaRojo extends ApuestaBase {

    public ApuestaRojo(int montoApostado) {
        super(montoApostado, "Rojo"); // Llama al constructor padre
    }

    @Override
    public boolean acierta(int numero, String color) {
        // Encapsula su propia lógica de acierto [cite: 157]
        return color.equals("Rojo");
    }
}