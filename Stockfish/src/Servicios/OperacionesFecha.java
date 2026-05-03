package Servicios;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OperacionesFecha {

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

}
