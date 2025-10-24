package controlador;

import java.util.ArrayList;
import java.util.List;
import modelo.Usuario;

public class SessionController {

    // Patrón Singleton: Única instancia centralizada (V4)
    private static final SessionController INSTANCE = new SessionController();

    private final List<Usuario> usuarios = new ArrayList<>();
    private Usuario usuarioActual;

    private SessionController() {
        // Carga de usuarios hardcodeados
        usuarios.add(new Usuario("donnie", "1234", "Donnie"));
        usuarios.add(new Usuario("admin", "admin", "Administrador"));
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
        usuarioActual = null;
    }
}