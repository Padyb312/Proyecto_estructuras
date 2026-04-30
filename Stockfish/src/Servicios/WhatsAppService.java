package Servicios;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class WhatsAppService {

	private static final String URL_BASE = "https://api.callmebot.com/whatsapp.php";

	private final String telefono;
	private final String apiKey;

	public WhatsAppService(String telefono, String apiKey) {
		this.telefono = telefono;
		this.apiKey = apiKey;
	}

	public boolean enviarMensaje(String mensaje) {
		try {
			String mensajeCodificado = URLEncoder.encode(mensaje, StandardCharsets.UTF_8);
			String urlCompleta = URL_BASE + "?phone=" + telefono + "&text=" + mensajeCodificado + "&apikey=" + apiKey;

			HttpURLConnection conexion = (HttpURLConnection) new URL(urlCompleta).openConnection();
			conexion.setRequestMethod("GET");
			conexion.setConnectTimeout(5000);
			conexion.setReadTimeout(5000);

			int codigo = conexion.getResponseCode();
			conexion.disconnect();
			return codigo == 200;

		} catch (IOException e) {
			System.err.println("Error al enviar WhatsApp: " + e.getMessage());
			return false;
		}
	}

	public boolean notificarEvento(String tipo, String actividad, String fecha, String hora) {
		String mensaje = "Recordatorio " + tipo + "\n Actividad: " + actividad + "\n Fecha: " + fecha + "\n Hora: "
				+ hora;
		return enviarMensaje(mensaje);
	}

	// Mensaje de prueba para verificar que la conexión funciona
	public boolean enviarPrueba() {
		return enviarMensaje("Conexion exitosa con tu organizador de eventos!");
	}
}