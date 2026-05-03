package modelo;

import java.io.Serializable;

public class IdEvento implements Serializable {

	

	private static int cont = 0; 

	public IdEvento() {
		super();
	}

	public static int generarId() {
		int generar=cont;
		++cont;
		return generar;
	}

	public static int getCont() {
		return cont;
	}

	public static void setCont(int cont) { 
		IdEvento.cont = cont;
	}
}