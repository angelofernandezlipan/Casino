package modelo;

public class ApuestaImpar extends ApuestaBase {

    public ApuestaImpar(int montoApostado) {
        super(montoApostado, "Impar");
    }

    @Override
    public boolean acierta(int numero, String color) {
        return numero % 2 != 0;
    }
}