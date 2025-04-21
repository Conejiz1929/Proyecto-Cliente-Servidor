package modelo;

import java.util.Arrays;
import java.util.List;

public class Usuario {

    private String id;
    private String nombre;
    private String email;
    private String password;
    private String rol; // CLIENTE, ADMINISTRADOR, PROVEEDOR, COLABORADOR
    private boolean activo;

    // Roles disponibles en el sistema
    public static final List<String> ROLES = Arrays.asList(
            "CLIENTE", "ADMINISTRADOR", "PROVEEDOR", "COLABORADOR"
    );

    public Usuario(String id, String nombre, String email, String password, String rol) {
        if (!ROLES.contains(rol)) {
            throw new IllegalArgumentException("Rol no válido");
        }

        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.rol = rol;
        this.activo = true;
    }

    // Métodos para verificar rol
    public boolean esCliente() {
        return "CLIENTE".equals(rol);
    }

    public boolean esAdministrador() {
        return "ADMINISTRADOR".equals(rol);
    }

    public boolean esProveedor() {
        return "PROVEEDOR".equals(rol);
    }

    public boolean esColaborador() {
        return "COLABORADOR".equals(rol);
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRol() {
        return rol;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public static List<String> getROLES() {
        return ROLES;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
    
    
}