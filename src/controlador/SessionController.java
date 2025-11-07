package controlador;

import java.util.ArrayList;
import java.util.List;
import modelo.Usuario;
import java.io.*;

public class SessionController {

    // Patrón Singleton: Única instancia centralizada (V4)
    private static final SessionController INSTANCE = new SessionController();

    private List<Usuario> usuarios = new ArrayList<>();
    private Usuario usuarioActual;

    // Constante para el nombre del archivo de datos
    private static final String ARCHIVO_DATOS = "casino.dat";

    private SessionController() {
        // Carga de usuarios hardcodeados
        usuarios.add(new Usuario("donnie", "1234", "Donnie"));
        usuarios.add(new Usuario("admin", "admin", "Administrador"));
        guardarDatos();
    }

    public static SessionController getInstance() {
        return INSTANCE;
    }

    // Métodos de Lógica (Control)
    public Usuario iniciarSesion(String u, String p) {
        for (Usuario usuario : usuarios) {
            if (usuario.validarCredenciales(u, p)) {
                this.usuarioActual = usuario;
                return usuario;
            }
        }
        return null;
    }

    public Usuario getUsuarioActual() { // Necesario para VentanaJuego y Estadisticas
        return usuarioActual;
    }

    public void cerrarSesion() {
        guardadDatos();
        usuarioActual = null;
    }

    // Nuevos méthodos de persistencia (Versión 7)
    @SuppressWarnings("unchecked")
    private void cargarDatos() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARCHIVO_DATOS))) {
            usuarios = (List<Usuario>) ois.readObject();
            System.out.println("Datos cargados desde " + ARCHIVO_DATOS);
        } catch (FileNotFoundException e) {
            System.out.println("No se encontró archivo de datos. Se creará uno nuevo.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al cargar datos: " + e.getMessage());
            usuarios = new ArrayList<>(); // Empezar de cero si hay error
        }
    }

    private void guardarDatos() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO_DATOS))) {
            oos.writeObject(usuarios);
            System.out.println("Datos guardados en " + ARCHIVO_DATOS);
        } catch (IOException e) {
            System.out.println("Error al guardar datos: " + e.getMessage());
        }
    }
}