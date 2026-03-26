package modelo;

public class Dia {
	private int hora[] = new int[24];

	public Dia(int[] hora) {
		super();
		this.hora = hora;
	}

	public int[] getHora() {
		return hora;
	}

	public void setHora(int[] hora) {
		this.hora = hora;
	}

}
