package modelo;

import java.util.ArrayList;
import java.util.List;

public class RepositorioEnMemoria implements IRepositorioResultados {

    private final List<Resultado> historial = new ArrayList<>();

    @Override
    public void guardar(Resultado r) {
        historial.add(r);
    }

    @Override
    public List<Resultado> obtenerTodos() {
        return historial;
    }

    @Override
    public void cargar() {
        // No hace nada, la memoria siempre está "cargada".
    }

    @Override
    public void persistir() {
        // No hace nada, los datos en memoria son volátiles.
    }
}