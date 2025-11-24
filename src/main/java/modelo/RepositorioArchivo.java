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
        File archivo = new File(fileName);

        // VALIDACIÓN (Caso 4): Verificar existencia antes de intentar abrir
        if (!archivo.exists() || !archivo.isFile()) {
            System.out.println("El archivo no existe o no es válido. Se iniciará vacío: " + fileName);
            historial = new ArrayList<>();
            return; // Salida temprana (Flujo normal)
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            Object data = ois.readObject();

            // Validación de tipo seguro
            if (data instanceof List<?>) {
                historial = (List<Resultado>) data;
                System.out.println("Historial cargado correctamente: " + fileName);
            } else {
                System.out.println("El archivo tiene un formato incorrecto.");
                historial = new ArrayList<>();
            }

        } catch (FileNotFoundException e) {
            // Esto teóricamente no debería pasar gracias al if(exists), pero Java obliga al catch
            System.err.println("Error inesperado: Archivo no encontrado.");
        } catch (IOException | ClassNotFoundException e) {
            // EXCEPCIÓN (Caso 7): Si el archivo está corrupto a nivel binario, reseteamos
            System.err.println("Error crítico leyendo el archivo (Corrupto): " + e.getMessage());
            historial = new ArrayList<>();
        }
    }

    @Override
    public void persistir() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(historial);
        } catch (IOException e) {
            // EXCEPCIÓN: Fallo de disco o permisos (Caso 4)
            System.err.println("Error crítico guardando el archivo: " + e.getMessage());
            // Aquí sí podríamos lanzar una RuntimeException si quisiéramos detener el programa
        }
    }
}