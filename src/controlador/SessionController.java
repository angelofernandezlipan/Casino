package controlador;

import modelo.Usuario;
import java.util.ArrayList;
import java.util.List;

public class SessionController {

    private final List<Usuario> usuarios = new ArrayList<>();
    private Usuario usuarioActual; // Solo un usuario a la vez

    private static final SessionController INSTANCE = new SessionController();

    // Idealmente, cargar usuarios desde una base de datos o archivo (para después)

    private SessionController() {
        usuarios.add(new Usuario("donnie", "1234", "Donnie"));
        usuarios.add(new Usuario("admin", "admin", "Administrador"));
    }

    public static SessionController getInstance() {
        return INSTANCE;
    }

    // ----------------------------------------------------------------

    public Usuario iniciarSesion(String u, String p) {
        for (Usuario usuario : usuarios) {
            if (usuario.validarCredenciales(u, p)) {
                this.usuarioActual = usuario;
                return usuario;
            }
        }
        return null; // Credenciales incorrectas
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

    public void cerrarSesion() {
        usuarioActual = null;
    }
}