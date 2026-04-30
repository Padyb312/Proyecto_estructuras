package modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public abstract class Evento implements Serializable {

	private String id;
	private LocalDate fecha;
	private String actividad;
	private int prioridad;
	private boolean estado;
	private LocalTime hora;

	public Evento(LocalDate fecha, String actividad, int prioridad, boolean estado, LocalTime hora) {
		this.id = String.valueOf(IdEvento.generarId()); // ID generado automáticamente
		this.fecha = fecha;
		this.actividad = actividad;
		this.prioridad = prioridad;
		this.estado = estado;
		this.hora = hora;
	}

	public String getId() {
		return id;
	}

	public String getActividad() {
		return actividad;
	}

	public void setActividad(String actividad) {
		this.actividad = actividad;
	}

	public int getPrioridad() {
		return prioridad;
	}

	public void setPrioridad(int prioridad) {
		this.prioridad = prioridad;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public LocalTime getHora() {
		return hora;
	}

	public void setHora(LocalTime hora) {
		this.hora = hora;
	}

	public String cambiarHora(LocalTime hora) {
		if (this.hora.equals(hora))
			return "Hora no cambiada";
		this.hora = hora;
		return "Hora actualizada a " + this.hora;
	}

	public String cambiarEstado(boolean estado) {

		if (isEstado() == estado) {
			return "Estado no cambiado";
		} else {
			setEstado(estado);

			return "Estado Actualizado a " + isEstado();
		}
	}

	public String cambiarPrioridad(int prioridad) {

		if (getPrioridad() == prioridad) {
			return "Prioridad no cambiado";
		} else {
			setPrioridad(prioridad);

			return "Prioridad Actualizada a " + getPrioridad();
		}
	}

	public String cambiarFecha(LocalDate fecha) {
		if (getFecha().equals(fecha)) {
			return "Fecha no cambiada";
		}
		setFecha(fecha);
		return "Fecha actualizada a " + getFecha();
	}

	@Override
	public String toString() {
		return "ID: " + id + " | Fecha: " + fecha + " | Hora: " + hora + "\n | Actividad: " + actividad
				+ " | Prioridad: " + prioridad + " | Estado: " + estado;
	}

}
