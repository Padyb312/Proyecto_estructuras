package Servicios;

import modelo.Evento;
import modelo.Tarea;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class NotificacionService {

	private final WhatsAppService whatsApp;

	public NotificacionService(WhatsAppService whatsApp) {
		this.whatsApp = whatsApp;
	}

	/**
	 * Revisa todos los eventos y notifica según fecha y prioridad. Reglas: -
	 * Prioridad Alta (1) → notifica si faltan 3 días o menos - Prioridad Media (2)
	 * → notifica si faltan 2 días o menos - Prioridad Baja (3) → notifica solo si
	 * es hoy - Eventos completados → se ignoran
	 */
	public void notificarPendientes(List<Evento> eventos) {
		LocalDate hoy = LocalDate.now();
		List<Evento> aNotificar = filtrarParaNotificar(eventos, hoy);

		if (aNotificar.isEmpty()) {
			System.out.println("No hay eventos pendientes para notificar.");
			return;
		}

		System.out.println("Enviando " + aNotificar.size() + " notificacion(es)...");
		for (Evento e : aNotificar) {
			boolean ok = whatsApp.notificarEvento(obtenerTipo(e), e.getActividad(), e.getFecha().toString(),
					e.getHora().toString());
			System.out.println(ok ? "  Notificado: " + e.getActividad() : "  Error al notificar: " + e.getActividad());
		}
	}

	/**
	 * Notifica un único evento por su referencia. Útil para forzar una notificación
	 * manual desde el menú.
	 */
	public void notificarUno(Evento evento) {
		if (evento == null) {
			System.out.println("Evento no encontrado.");
			return;
		}
		boolean ok = whatsApp.notificarEvento(obtenerTipo(evento), evento.getActividad(), evento.getFecha().toString(),
				evento.getHora().toString());
		System.out.println(ok ? "Notificacion enviada." : "Error al enviar notificacion.");
	}

	// ── Lógica de filtrado ────────────────────────────────────────────────────

	private List<Evento> filtrarParaNotificar(List<Evento> eventos, LocalDate hoy) {
		List<Evento> resultado = new ArrayList<>();
		for (Evento e : eventos) {
			if (e == null || e.isEstado())
				continue; // ignorar nulos y completados

			long diasRestantes = hoy.until(e.getFecha()).getDays();

			if (diasRestantes < 0)
				continue; // ignorar eventos pasados

			if (debeNotificar(e.getPrioridad(), diasRestantes)) {
				resultado.add(e);
			}
		}
		return resultado;
	}

	private boolean debeNotificar(int prioridad, long diasRestantes) {
		switch (prioridad) {
		case 1:
			return diasRestantes <= 3; // Alta
		case 2:
			return diasRestantes <= 2; // Media
		case 3:
			return diasRestantes == 0; // Baja — solo hoy
		default:
			return false;
		}
	}

	private String obtenerTipo(Evento e) {
		return e instanceof Tarea ? "Tarea" : "Actividad Personal";
	}
}