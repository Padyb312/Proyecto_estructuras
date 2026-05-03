package modelo;

import java.io.Serializable;
import java.util.List;

public class Usuario implements Serializable {

	private String correo;
	private String contraseña;
	private List<Evento> eventos;
	private String telegramChatId;
	private String nombres;
	private String apellidos;
	private String facultad;

	public Usuario(String correo, String contraseña, String nombres, String apellidos, String facultad) {
		super();
		this.correo = correo;
		this.contraseña = contraseña;
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.facultad = facultad;
		this.eventos = eventos;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	public List<Evento> getEventos() {
		return eventos;
	}

	public void setEventos(List<Evento> eventos) {
		this.eventos = eventos;
	}

	public String getTelegramChatId() {
		return telegramChatId;
	}

	public void setTelegramChatId(String telegramChatId) {
		this.telegramChatId = telegramChatId;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getFacultad() {
		return facultad;
	}

	public void setFacultad(String facultad) {
		this.facultad = facultad;
	}

	@Override
	public String toString() {
		return "Usuario [correo=" + correo + ", contraseña=" + contraseña + ", eventos=" + eventos + ", telegramChatId="
				+ telegramChatId + ", nombres=" + nombres + ", apellidos=" + apellidos + ", facultad=" + facultad + "]";
	}

}
