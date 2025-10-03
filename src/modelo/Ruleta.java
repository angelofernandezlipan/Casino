package modelo;

import java.util.Random;
import java.util.Arrays;
import java.util.List;

public class Ruleta {

    private final List<Integer> numerosRojos = Arrays.asList(1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36);
    private final Random rng = new Random();

    public int girarRuleta() {
        return rng.nextInt(37); // Números de 0 a 36
    }

    // Ahora recibe el ENUM TipoApuesta
    public boolean evaluarResultado(int numero, TipoApuesta tipoApuesta) {
        if (numero == 0) return false;

        return switch (tipoApuesta) {
            case ROJO -> numerosRojos.contains(numero);
            case NEGRO -> !numerosRojos.contains(numero) && numero != 0;
            case PAR -> numero % 2 == 0;
            case IMPAR -> numero % 2 != 0;
        };
    }

    // Getters (historial próximamente a desarrollar)

    // Esto se fue en la versión 5

    // ¿Más getters?
}