package modelo;

public class ApuestaNegro extends ApuestaBase {

    public ApuestaNegro(int montoApostado) {
        super(montoApostado, "Negro");
    }

    @Override
    public boolean acierta(int numero, String color) {
        return color.equals("Negro");
    }
}