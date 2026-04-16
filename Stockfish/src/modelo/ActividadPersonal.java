package modelo;

import java.io.Serializable;

public class ActividadPersonal extends Evento implements Serializable {

	public ActividadPersonal(String fecha, String actividad, int prioridad, boolean estado, int franja) {
		super(fecha, actividad, prioridad, estado, franja);
	}

}
