package modelo;

import java.util.ArrayList;
import java.util.List;

public final class Franja {

	public String franja[] = {   "00:00 a.m - 01:00 a.m = 0",
		    "01:00 a.m - 02:00 a.m = 1",
		    "02:00 a.m - 03:00 a.m = 2",
		    "03:00 a.m - 04:00 a.m = 3",
		    "04:00 a.m - 05:00 a.m = 4",
		    "05:00 a.m - 06:00 a.m = 5",
		    "06:00 a.m - 07:00 a.m = 6",
		    "07:00 a.m - 08:00 a.m = 7",
		    "08:00 a.m - 09:00 a.m = 8",
		    "09:00 a.m - 10:00 a.m = 9",
		    "10:00 a.m - 11:00 a.m = 10",
		    "11:00 a.m - 12:00 p.m = 11",
		    "12:00 p.m - 01:00 p.m = 12",
		    "01:00 p.m - 02:00 p.m = 13",
		    "02:00 p.m - 03:00 p.m = 14",
		    "03:00 p.m - 04:00 p.m = 15",
		    "04:00 p.m - 05:00 p.m = 16",
		    "05:00 p.m - 06:00 p.m = 17",
		    "06:00 p.m - 07:00 p.m = 18",
		    "07:00 p.m - 08:00 p.m = 19",
		    "08:00 p.m - 09:00 p.m = 20",
		    "09:00 p.m - 10:00 p.m = 21",
		    "10:00 p.m - 11:00 p.m = 22",
		    "11:00 p.m - 00:00 a.m = 23"
	};

	// Contructor_Franja
	public Franja() {
	}


	// Imprimir_franja
	public void mostrarFranja() {
		for (int i = 0; i < franja.length; i++) {
			System.out.println(franja[i]);
		}
	}

}
