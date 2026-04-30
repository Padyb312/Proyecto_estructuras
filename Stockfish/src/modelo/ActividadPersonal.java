package modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class ActividadPersonal extends Evento implements Serializable {
	private static final long serialVersionUID = 1L;

	public ActividadPersonal(LocalDate fecha, String actividad, int prioridad, boolean estado, LocalTime hora) {
		super(fecha, actividad, prioridad, estado, hora);
	}

}
