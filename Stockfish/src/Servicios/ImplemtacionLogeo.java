package Servicios;

import modelo.Evento;
import modelo.Usuario;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class ImplemtacionLogeo implements OperacionLogeo, OperacionArchivo {

	private static final String ARCHIVO = "Usuarios";
	private static final String DOMINIO = "@poligran.edu.co";

	private List<Usuario> usuarios;

	public ImplemtacionLogeo() {
		this.usuarios = cargarUsuarios();
	}

	// Retorna true si las credenciales son correctas
	public boolean iniciarSesion(String correo, String password) {
		if (!correoValido(correo))
			return false;

		String hash = hashSHA256(password);
		for (Usuario u : usuarios) {
			if (u.getCorreo().equals(correo) && u.getContraseña().equals(hash)) {
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

	// Retorna los eventos guardados del usuario, o lista vacía si no tiene
	public List<Evento> obtenerEventos(String correo) {
		for (Usuario u : usuarios) {
			if (u.getCorreo().equals(correo)) {
				List<Evento> eventos = u.getEventos();
				return (eventos != null) ? eventos : new ArrayList<>();
			}
		}
		return new ArrayList<>();
	}

	// Actualiza los eventos del usuario y guarda el archivo
	public void actualizarEventos(String correo, List<Evento> eventos) {
		for (Usuario u : usuarios) {
			if (u.getCorreo().equals(correo)) {
				u.setEventos(eventos);
				guardarUsuarios();
				return;
			}
		}
	}

	// Valida que el correo termine en @poligran.edu.co y tenga algo antes
	private boolean correoValido(String correo) {
		return correo != null && correo.endsWith(DOMINIO) && correo.length() > DOMINIO.length();
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
			if (usuarios.get(i).getCorreo().equals(correo) && usuarios.get(i).getContraseña().equals(hash)) {
				usuarios.remove(i);
				guardarUsuarios();
				return "Usuario eliminado exitosamente.";
			}
		}
		return "Correo o contraseña incorrectos.";
	}

	@Override
	public String registar(String correo, String contraseña, String contraseñaConfirmada) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String serializar(List<Usuario> Usuarios, String path, String name) throws Exception {
		try {
			FileOutputStream fos = new FileOutputStream(path + name);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(Usuarios);
			oos.close();
			fos.close();
			return "File created!!";
		} catch (IOException ioe) {
			return "Error file " + ioe.getMessage();
		}
	}

	@Override
	public List<Usuario> deserializar(String path, String name) throws Exception {
		List<Usuario> lista = null;
		try {
			FileInputStream fis = new FileInputStream(path + name);
			ObjectInputStream ois = new ObjectInputStream(fis);
			lista = (List<Usuario>) ois.readObject();
			ois.close();
			fis.close();
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		} catch (ClassNotFoundException c) {
			System.err.println(c.getMessage());
		}
		return lista;
	}
}