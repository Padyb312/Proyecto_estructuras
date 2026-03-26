package modelo;

import java.util.Arrays;

public class Calendario {
	private boolean[][] calendario = { /* 0 */{ false, false, false, false, false, false, false },
			/* 1 */{ false, false, false, false, false, false, false },
			/* 2 */{ false, false, false, false, false, false, false },
			/* 3 */{ false, false, false, false, false, false, false },
			/* 4 */{ false, false, false, false, false, false, false }, };

	public Calendario() {
		super();
	}

	public boolean[][] getCalendario() {
		return calendario;
	}

	public void setCalendario(boolean[][] calendario) {
		this.calendario = calendario;
	}

	public void agendar(int i, int j) {
		if (calendario[i][j] == false) {
			calendario[i][j] = true;
			System.out.println("Tarea Agregada");

		} else {
			System.out.println("Ya tiene una tarea ");
		}

	}

	public void eliminar(int i, int j) {
		if (calendario[i][j] == true) {
			calendario[i][j] = false;
			System.out.println("Tarea eliminada");
		} else {
			System.out.println("No tiene tarea para eliminar");
		}

	}

	public void mostrar() {
		System.out.println(Arrays.deepToString(calendario).replace("], ", "]\n"));
	}

}
