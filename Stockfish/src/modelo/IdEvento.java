package modelo;

import java.io.Serializable;

public class IdEvento implements Serializable {

	

	private static int cont = 0; // 👈 static para que sea compartido entre todos

	public IdEvento() {
		super();
	}

	public static int generarId() { // 👈 static para llamarlo sin instanciar
		return ++cont;
	}

	public static int getCont() {
		return cont;
	}

	public static void setCont(int cont) { // 👈 Para restaurar al deserializar
		IdEvento.cont = cont;
	}
}