package modelo;

import java.io.Serializable;

public abstract class Evento implements Serializable {
	public String id;
	public String fecha;
	public String actividad;
	public int prioridad;
	public boolean estado;
	public int franja;

	public Evento(String fecha, String actividad, int prioridad, boolean estado, int franja) {
		super();
		this.actividad = actividad;
		this.prioridad = prioridad;
		this.estado = estado;
		this.fecha = fecha;
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

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public int getFranja() {
		return franja;
	}

	public void setFranja(int franja) {
		this.franja = franja;
	}

	public String cambiarEstado(boolean estado) {
		boolean origen = isEstado();
		if (isEstado() == estado) {
			return "Estado no cambiado";
		} else {
			setEstado(estado);

			return "Estado Actualizado a " + isEstado();
		}
	}

	public String cambiarPrioridad(int prioridad) {
		int origen = getPrioridad();
		if (getPrioridad() == prioridad) {
			return "Prioridad no cambiado";
		} else {
			setPrioridad(prioridad);

			return "Prioridad Actualizada a " + getPrioridad();
		}
	}

	public String cambiarFecha(String fecha) {
		String fechaOriginal = getFecha();
		if (fechaOriginal.equals(fecha)) {
			return "Fecha no cambiada";
		} else {
			setFecha(fecha);

			return "Prioridad Actualizada a " + getFecha();
		}

	}

	public String cambiarFranja(int franja) {
		int origen = getFranja();
		if (getPrioridad() == franja) {
			return "Franja no cambiada";
		} else {
			setFranja(franja);

			return "Franja Actualizada a " + getFranja();
		}
	}
}
