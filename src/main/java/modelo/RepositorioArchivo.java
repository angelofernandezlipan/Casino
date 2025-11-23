package modelo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RepositorioArchivo implements IRepositorioResultados {

    private List<Resultado> historial = new ArrayList<>();
    private final String fileName;

    public RepositorioArchivo(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void guardar(Resultado r) {
        historial.add(r);
    }

    @Override
    public List<Resultado> obtenerTodos() {
        return historial;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void cargar() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            historial = (List<Resultado>) ois.readObject();
            System.out.println("Historial cargado desde: " + fileName);
        } catch (FileNotFoundException e) {
            System.out.println("No se encontró archivo de historial, se creará uno nuevo: " + fileName);
            historial = new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al cargar historial: " + e.getMessage());
            historial = new ArrayList<>();
        }
    }

    @Override
    public void persistir() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(historial);
            System.out.println("Historial guardado en: " + fileName);
        } catch (IOException e) {
            System.out.println("Error al guardar historial: " + e.getMessage());
        }
    }
}
