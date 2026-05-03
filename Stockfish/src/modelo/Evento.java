package modelo;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Evento implements Serializable {

	private String id;
	private LocalDateTime fecha;
	private String actividad;
	private String descripcion;
	private int prioridad;
	private boolean estado;

	public Evento() {
	}

	public Evento(String id, LocalDateTime fecha, String actividad, String descripcion, int prioridad, boolean estado) {
		this.id = String.valueOf(IdEvento.generarId()); // ID generado automáticamente
		this.fecha = fecha;
		this.actividad = actividad;
		this.descripcion = descripcion;
		this.prioridad = prioridad;
		this.estado = estado;
	}

	public String getId() {
		return id;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public String getActividad() {
		return actividad;
	}

	public void setActividad(String actividad) {
		this.actividad = actividad;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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

	@Override
	public String toString() {
		return "Evento{" + "id='" + id + '\'' + ", fecha=" + fecha + ", actividad='" + actividad + '\''
				+ ", descripcion='" + descripcion + '\'' + ", prioridad=" + prioridad + ", estado=" + estado + '}';
	}
}
