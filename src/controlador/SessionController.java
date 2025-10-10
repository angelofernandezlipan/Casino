package controlador;

import java.util.ArrayList;
import java.util.List;
import modelo.Usuario; // Asumiendo que usas paquetes

public class SessionController {

    // --- Patrón Singleton ---
    private static final SessionController INSTANCE = new SessionController();

    // --- Modelos y Estado ---
    private final List<Usuario> usuarios = new ArrayList<>();
    private Usuario usuarioActual; // Estado de la sesión

    // Constructor privado: Se inicializa solo dentro de la clase
    private SessionController() {
        // Usuarios hardcodeados para el prototipo
        usuarios.add(new Usuario("donnie", "1234", "Donnie"));
        usuarios.add(new Usuario("admin", "admin", "Administrador"));
    }

    public static SessionController getInstance() {
        return INSTANCE;
    }

    // --- Métodos de Lógica (Control) ---

    public Usuario iniciarSesion(String u, String p) {
        for (Usuario usuario : usuarios) {
            if (usuario.validarCredenciales(u, p)) {
                this.usuarioActual = usuario;
                return usuario;
            }
        }
        return null; // Credenciales incorrectas
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public void cerrarSesion() {
        usuarioActual = null;
    }

    public void registrarUsuario(String u, String p, String n) {
        Usuario nuevoUsuario = new Usuario(u, p, n);
        usuarios.add(nuevoUsuario);
    }

    public String getNombreUsuario() {
        return hayUsuario() ? usuarioActual.getNombre() : "";
    }

    public boolean hayUsuario() {
        return usuarioActual != null;
    }
}