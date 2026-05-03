package Servicios;

import modelo.Evento;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class Utilidades {

	/**
	 * Convierte una fecha en formato DD/MM/YY y una hora en formato 12h (AM/PM) a
	 * LocalDateTime. Formatos aceptados: "10:30 AM", "2:45 PM", "10 AM", "3 PM" Si
	 * el formato es invalido, retorna la fecha con hora 12:00 PM por defecto.
	 *
	 * @param fecha fecha en formato DD/MM/YY
	 * @param hora  hora en formato 12h con AM o PM
	 * @return LocalDateTime combinando fecha y hora
	 */
	public static LocalDateTime parseFecha(String fecha, String hora) {
		try {
			LocalDate date = LocalDate.parse(fecha.trim(), DateTimeFormatter.ofPattern("dd/MM/yy"));
			// Intentar formato con minutos: 10:30 AM / 2:45 PM
			try {
				DateTimeFormatter fmtCompleto = DateTimeFormatter.ofPattern("h:mm a");
				return date.atTime(java.time.LocalTime.parse(hora.trim().toUpperCase(), fmtCompleto));
			} catch (Exception e1) {
				// Intentar formato solo hora: 10 AM / 2 PM
				DateTimeFormatter fmtSimple = DateTimeFormatter.ofPattern("h a");
				return date.atTime(java.time.LocalTime.parse(hora.trim().toUpperCase(), fmtSimple));
			}
		} catch (Exception e) {
			System.out.println("Formato de hora invalido, se usara 12:00 PM por defecto.");
			return LocalDate.parse(fecha.trim(), DateTimeFormatter.ofPattern("dd/MM/yy")).atTime(12, 0);
		}
	}

	// Token del bot de Telegram — reemplazar por el token real de @BotFather
	private static final String TOKEN = "8700680523:AAEt6U4Q9MwAcI4O2UFKo1-jdYL0mKLu8PY";

	// Días de anticipación con los que se envía el recordatorio
	private static final int DIAS_ANTICIPACION = 3;

	/**
	 * Revisa los eventos del usuario y envía notificaciones por Telegram para
	 * aquellos que ocurren dentro de los próximos DIAS_ANTICIPACION días. Solo se
	 * ejecuta si el usuario tiene un chatId de Telegram registrado.
	 *
	 * @param eventos lista de eventos del usuario
	 * @param chatId  ID de chat de Telegram del usuario
	 */
	public static void verificarEventosCercanos(List<Evento> eventos, String chatId) {
		if (chatId == null || chatId.isBlank() || chatId.equals("0")) {
			return;
		}
		if (eventos == null || eventos.isEmpty()) {
			return;
		}

		LocalDateTime ahora = LocalDateTime.now();
		boolean hayEventosCercanos = false;

		for (Evento e : eventos) {
			if (e.getFecha() == null || e.isEstado()) {
				continue; // saltar eventos sin fecha o ya completados
			}

			long diasRestantes = ChronoUnit.DAYS.between(ahora.toLocalDate(), e.getFecha().toLocalDate());

			if (diasRestantes >= 0 && diasRestantes <= DIAS_ANTICIPACION) {
				hayEventosCercanos = true;

				String prioridadTexto = switch (e.getPrioridad()) {
				case 1 -> "Alta";
				case 2 -> "Media";
				case 3 -> "Baja";
				default -> "Sin prioridad";
				};

				String mensaje;
				if (diasRestantes == 0) {
					mensaje = "RECORDATORIO - HOY\n" + "Actividad: " + e.getActividad() + "\n" + "Descripcion: "
							+ e.getDescripcion() + "\n" + "Hora: " + e.getFecha().toLocalTime() + "\n" + "Prioridad: "
							+ prioridadTexto + "\n" + "ID: " + e.getId();
				} else {
					mensaje = "RECORDATORIO - en " + diasRestantes + " dia(s)\n" + "Actividad: " + e.getActividad()
							+ "\n" + "Descripcion: " + e.getDescripcion() + "\n" + "Fecha: "
							+ e.getFecha().toLocalDate() + "\n" + "Hora: " + e.getFecha().toLocalTime() + "\n"
							+ "Prioridad: " + prioridadTexto + "\n" + "ID: " + e.getId();
				}

				enviarMensaje(chatId, mensaje);
			}
		}

		if (!hayEventosCercanos) {
			System.out.println("Sin eventos proximos en los proximos " + DIAS_ANTICIPACION + " dias.");
		}
	}

	/**
	 * Envía un mensaje de texto a un chat de Telegram usando la API del bot.
	 *
	 * @param chatId  ID del chat destino
	 * @param mensaje texto del mensaje
	 */
	private static void enviarMensaje(String chatId, String mensaje) {
		try {
			String urlStr = "https://api.telegram.org/bot" + TOKEN + "/sendMessage" + "?chat_id="
					+ URLEncoder.encode(chatId, "UTF-8") + "&text=" + URLEncoder.encode(mensaje, "UTF-8");

			HttpURLConnection con = (HttpURLConnection) new URL(urlStr).openConnection();
			con.setRequestMethod("GET");
			con.setConnectTimeout(5000);
			con.setReadTimeout(5000);

			int code = con.getResponseCode();
			if (code == 200) {
				System.out.println("Notificacion Telegram enviada correctamente.");
			} else {
				InputStream err = con.getErrorStream();
				String respuesta = err != null ? new String(err.readAllBytes()) : "sin detalles";
				System.err.println("Error Telegram (HTTP " + code + "): " + respuesta);
			}
			con.disconnect();

		} catch (Exception e) {
			System.err.println("No se pudo conectar con Telegram: " + e.getMessage());
		}
	}

}
