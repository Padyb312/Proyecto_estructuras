package modelo;

import java.util.ArrayList;
import java.util.List;

public class Dia_prueba {
	public List<Franja> dia = new ArrayList<>(7);

	public Dia_prueba() {
		/* 0 */dia.add(new Franja());
		/* 1 */dia.add(new Franja());
		/* 2 */dia.add(new Franja());
		/* 3 */dia.add(new Franja());
		/* 4 */dia.add(new Franja());
		/* 5 */dia.add(new Franja());
		/* 6 */dia.add(new Franja());

	}

	public void mostrarDia() {
		String[] nombres = { "Domingo", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado" };
		for (int i = 0; i < dia.size(); i++) {
			System.out.println(nombres[i]);
			dia.get(i).mostrarFranaja();
		}

	}

}
