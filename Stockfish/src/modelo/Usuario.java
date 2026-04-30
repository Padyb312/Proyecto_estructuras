package modelo;

import java.io.Serializable;

public class Usuario implements Serializable {

	private String correo;
	private String passwordHash;
	private String telefono;
	private String whatsAppApiKey;

	public Usuario(String correo, String passwordHash) {
		this.correo = correo;
		this.passwordHash = passwordHash;
		this.telefono = null;
		this.whatsAppApiKey = null;
	}

	public String getCorreo() {
		return correo;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public String getTelefono() {
		return telefono;
	}

	public String getWhatsAppApiKey() {
		return whatsAppApiKey;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public void setWhatsAppApiKey(String apiKey) {
		this.whatsAppApiKey = apiKey;
	}

	// Desenlazar WhatsApp completamente
	public void desenlazarWhatsApp() {
		this.telefono = null;
		this.whatsAppApiKey = null;
	}

	// Verificar si tiene WhatsApp configurado
	public boolean tieneWhatsApp() {
		return telefono != null && !telefono.isEmpty() && whatsAppApiKey != null && !whatsAppApiKey.isEmpty();
	}
}