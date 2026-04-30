package modelo;

import java.time.LocalDate;
import java.time.LocalTime;

public class Tarea extends Evento {

	private String materia;
	private LocalDate fechaInicio;

	public Tarea(LocalDate fecha, String actividad, int prioridad, boolean estado, LocalTime hora, String materia,
			LocalDate fechaInicio) {
		super(fecha, actividad, prioridad, estado, hora);

		if (fechaInicio == null || fecha == null) {
			throw new IllegalArgumentException("Las fechas no pueden ser nulas.");
		}
		if (fechaInicio.isAfter(fecha)) {
			throw new IllegalArgumentException("La fecha de inicio no puede ser posterior a la de entrega.");
		}

		this.materia = materia;
		this.fechaInicio = fechaInicio;
	}

	public LocalDate getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDate fechaInicio) {
		if (fechaInicio.isAfter(this.getFecha())) {
			throw new IllegalArgumentException("La fecha de inicio no puede ser posterior a la de entrega.");
		}
		this.fechaInicio = fechaInicio;
	}

	public void setFechaEntrega(LocalDate fechaEntrega) {
		if (this.fechaInicio != null && fechaEntrega.isBefore(this.fechaInicio)) {
			throw new IllegalArgumentException("La fecha de entrega no puede ser anterior a la de inicio.");
		}
		this.setFecha(fechaEntrega);
	}

	public String getMateria() {
		return materia;
	}

	public void setMateria(String materia) {
		this.materia = materia;
	}

	@Override
	public String toString() {
		return super.toString() + " | Materia: " + materia + " | Fecha inicio: " + fechaInicio;
	}

}
