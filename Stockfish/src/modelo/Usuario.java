package modelo;

import java.io.Serializable;

public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    private String correo;
    private String passwordHash;

    public Usuario(String correo, String passwordHash) {
        this.correo = correo;
        this.passwordHash = passwordHash;
    }

    public String getCorreo() {
        return correo;
    }

    public String getPasswordHash() {
        return passwordHash;
    }
}