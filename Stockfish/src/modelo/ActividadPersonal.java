package modelo;

public class ActividadPersonal extends Evento {
	
	private static final long serialVersionUID = 1L;

	public ActividadPersonal(String fecha, String actividad, int prioridad, boolean estado, int franja) {
		super(fecha, actividad, prioridad, estado, franja);
	}

}
