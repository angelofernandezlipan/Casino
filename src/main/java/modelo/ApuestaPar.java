package modelo;

public class ApuestaPar extends ApuestaBase {

    public ApuestaPar(int montoApostado) {
        super(montoApostado, "Par");
    }

    @Override
    public boolean acierta(int numero, String color) {
        // La lógica de un "número par" no incluye el 0
        return numero != 0 && numero % 2 == 0;
    }
}