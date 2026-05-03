package modelo;

import java.io.Serializable;
import java.util.List;

public class Usuario implements Serializable {

	private String correo;
	private String contraseña;
	private List<Evento> eventos;
	private String telegramChatId;

	public Usuario() {
	}

	public Usuario(String correo, String contraseña) {
		this.correo = correo;
		this.contraseña = contraseña;
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

	@Override
	public String toString() {
		return "Usuario{" + "correo='" + correo + '\'' + ", contraseña='" + contraseña + '\'' + ", telegramChatId='"
				+ telegramChatId + '\'' + ", eventos=" + eventos + '}';
	}
}
