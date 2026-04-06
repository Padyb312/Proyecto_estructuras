package modelo;

public class Tarea extends Evento {

	public String materia;
	public String fechaInicio;
	
	public Tarea(String fecha, String actividad, int prioridad, boolean estado, int franja, String materia,
			String fechaInicio) {
		super(fecha, actividad, prioridad, estado, franja);
		this.materia = materia;
		this.fechaInicio = fechaInicio;
	}
	public String getMateria() {
		return materia;
	}
	public void setMateria(String materia) {
		this.materia = materia;
	}
	public String getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	
	
	
}
