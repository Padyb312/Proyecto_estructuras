package Servicios;

import modelo.Usuario;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class AutenticacionService {

	private static final String ARCHIVO = "Usuarios";
	private static final String DOMINIO = "@poligran.edu.co";

	private List<Usuario> usuarios;

	public AutenticacionService() {
		this.usuarios = cargarUsuarios();
	}

	// Retorna true si las credenciales son correctas
	public boolean login(String correo, String password) {
		if (!correoValido(correo))
			return false;

		String hash = hashSHA256(password);
		for (Usuario u : usuarios) {
			if (u.getCorreo().equals(correo) && u.getPasswordHash().equals(hash)) {
				return true;
			}
		}
		return false;
	}

	// Retorna mensaje según resultado del registro
	public String registrar(String correo, String password, String confirmacion) {
		if (!correoValido(correo)) {
			return "El correo debe ser institucional. Ejemplo: nombre" + DOMINIO;
		}
		if (!password.equals(confirmacion)) {
			return "Las contraseñas no coinciden.";
		}
		for (Usuario u : usuarios) {
			if (u.getCorreo().equals(correo)) {
				return "El correo ya está registrado.";
			}
		}
		usuarios.add(new Usuario(correo, hashSHA256(password)));
		guardarUsuarios();
		return "Usuario registrado exitosamente.";
	}

	// Valida que el correo termine en @poligran.edu.co y tenga algo antes
	private boolean correoValido(String correo) {
		return correo != null && correo.endsWith(DOMINIO) && correo.length() > DOMINIO.length();
	}

	private boolean telefonoValido(String telefono) {
		return telefono != null && telefono.matches("\\d{10}") // exactamente 10 dígitos
				&& telefono.startsWith("3"); // móviles colombianos
	}
	// ─── Persistencia ────────────────────────────────────────────────────────────

	private void guardarUsuarios() {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO))) {
			oos.writeObject(usuarios);
		} catch (IOException e) {
			System.err.println("Error al guardar usuarios: " + e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	private List<Usuario> cargarUsuarios() {
		File archivo = new File(ARCHIVO);
		if (!archivo.exists())
			return new ArrayList<>();
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
			return (List<Usuario>) ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			System.err.println("Error al cargar usuarios: " + e.getMessage());
			return new ArrayList<>();
		}
	}

	// ─── Hash SHA-256 (Java puro, sin librerías externas) ────────────────────────

	private String hashSHA256(String texto) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] bytes = digest.digest(texto.getBytes("UTF-8"));
			StringBuilder sb = new StringBuilder();
			for (byte b : bytes) {
				sb.append(String.format("%02x", b));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			throw new RuntimeException("Error al hashear contraseña", e);
		}
	}

	// Retorna mensaje según resultado de la eliminación
	public String eliminar(String correo, String password) {
		if (!correoValido(correo)) {
			return "Correo inválido.";
		}
		String hash = hashSHA256(password);
		for (int i = 0; i < usuarios.size(); i++) {
			if (usuarios.get(i).getCorreo().equals(correo) && usuarios.get(i).getPasswordHash().equals(hash)) {
				usuarios.remove(i);
				guardarUsuarios();
				return "Usuario eliminado exitosamente.";
			}
		}
		return "Correo o contraseña incorrectos.";
	}
	// ── Obtener usuario activo completo ─────────────────────────────────────────

	public Usuario getUsuario(String correo) {
		for (Usuario u : usuarios) {
			if (u.getCorreo().equals(correo))
				return u;
		}
		return null;
	}

	// ── Setters de WhatsApp independientes ──────────────────────────────────────

	public String setTelefono(String correo, String telefono) {
		if (!telefonoValido(telefono)) {
			return "Teléfono inválido. Debe tener 10 dígitos. Ej: 3001234567";
		}
		Usuario u = getUsuario(correo);
		if (u == null)
			return "Usuario no encontrado.";

		u.setTelefono("57" + telefono);
		guardarUsuarios();
		return "Teléfono actualizado: +57" + telefono;
	}

	public String setApiKey(String correo, String apiKey) {
		if (apiKey == null || apiKey.isBlank()) {
			return "La ApiKey no puede estar vacía.";
		}
		Usuario u = getUsuario(correo);
		if (u == null)
			return "Usuario no encontrado.";

		u.setWhatsAppApiKey(apiKey);
		guardarUsuarios();
		return "ApiKey guardada correctamente.";
	}

	public String desenlazarWhatsApp(String correo) {
		Usuario u = getUsuario(correo);
		if (u == null)
			return "Usuario no encontrado.";

		u.desenlazarWhatsApp();
		guardarUsuarios();
		return "WhatsApp desenlazado.";
	}

	// ── Construir WhatsAppService si el usuario tiene todo configurado ───────────

	public WhatsAppService buildWhatsAppService(String correo) {
		Usuario u = getUsuario(correo);
		if (u != null && u.tieneWhatsApp()) {
			return new WhatsAppService(u.getTelefono(), u.getWhatsAppApiKey());
		}
		return null; // sin WhatsApp configurado
	}
}