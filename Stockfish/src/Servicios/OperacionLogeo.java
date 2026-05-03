package Servicios;

public interface OperacionLogeo {

    boolean iniciarSesion(String correo, String contraseña);
    String registar(String correo, String contraseña, String contraseñaConfirmada);
    String eliminar(String correo, String contraseña);
}
