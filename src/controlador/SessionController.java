package controlador;

import modelo.Usuario;
import java.util.ArrayList;
import java.util.List;

public class SessionController {

    // --- Modelos y Estado ---
    private final List<Usuario> usuarios = new ArrayList<>();
    private Usuario usuarioActual; // Solo un usuario a la vez

    // --- Singleton Pattern (Opcional, pero recomendado) ---
    private static final SessionController INSTANCE = new SessionController();

    // Constructor privado para evitar instanciación externa
    private SessionController() {
        // Inicializar usuarios hardcodeados (como en VentanaLogin original)
        usuarios.add(new Usuario("donnie", "1234", "Donnie"));
        usuarios.add(new Usuario("admin", "admin", "Administrador"));
    }

    public static SessionController getInstance() {
        return INSTANCE;
    }

    // ----------------------------------------------------------------

    // 1. Manejo de Inicio de Sesión
    public Usuario iniciarSesion(String u, String p) {
        for (Usuario usuario : usuarios) {
            if (usuario.validarCredenciales(u, p)) {
                this.usuarioActual = usuario;
                return usuario;
            }
        }
        return null; // Credenciales incorrectas
    }

    // 2. Manejo de Registro (Desafío de la iteración anterior)
    public void registrarUsuario(String u, String p, String n) {
        // Puedes agregar lógica de validación aquí (ej. campos vacíos)
        Usuario nuevoUsuario = new Usuario(u, p, n);
        usuarios.add(nuevoUsuario);
    }

    // 3. Obtener estado de la sesión
    public String getNombreUsuario() {
        return hayUsuario() ? usuarioActual.getNombre() : "";
    }

    public boolean hayUsuario() {
        return usuarioActual != null;
    }

    public void cerrarSesion() {
        usuarioActual = null;
    }
}