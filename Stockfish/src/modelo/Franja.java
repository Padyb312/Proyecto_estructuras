package modelo;

import java.util.ArrayList;
import java.util.List;

public class Franja {

	public List<String> franja = new ArrayList<>();

	public Franja() {
	}

	{
		/* 0 */franja.add("00:00 a.m a 01:00 a.m = 0");
		/* 1 */franja.add("01:00 a.m a 02:00 a.m = 1");
		/* 2 */franja.add("02:00 a.m a 03:00 a.m = 2");
		/* 3 */franja.add("03:00 a.m a 04:00 a.m = 3");
		/* 4 */franja.add("04:00 a.m a 05:00 a.m = 4");
		/* 5 */franja.add("05:00 a.m a 06:00 a.m = 5");
		/* 6 */franja.add("06:00 a.m a 07:00 a.m = 6");
		/* 7 */franja.add("07:00 a.m a 08:00 a.m = 7");
		/* 8 */franja.add("08:00 a.m a 09:00 a.m = 8");
		/* 9 */franja.add("09:00 a.m a 10:00 a.m = 9");
		/* 10 */franja.add("10:00 a.m a 11:00 a.m = 10");
		/* 11 */franja.add("11:00 a.m a 12:00 p.m = 11");
		/* 12 */franja.add("12:00 p.m a 01:00 p.m = 12");
		/* 13 */franja.add("01:00 p.m a 02:00 p.m = 13");
		/* 14 */franja.add("02:00 p.m a 03:00 p.m = 14");
		/* 15 */franja.add("03:00 p.m a 04:00 p.m = 15");
		/* 16 */franja.add("04:00 p.m a 05:00 p.m = 16");
		/* 17 */franja.add("05:00 p.m a 06:00 p.m = 17");
		/* 18 */franja.add("06:00 p.m a 07:00 p.m = 18");
		/* 19 */franja.add("07:00 p.m a 08:00 p.m = 19");
		/* 20 */franja.add("08:00 p.m a 09:00 p.m = 20");
		/* 21 */franja.add("09:00 p.m a 10:00 p.m = 21");
		/* 22 */franja.add("10:00 p.m a 11:00 p.m = 22");
		/* 23 */franja.add("11:00 p.m a 00:00 a.m = 23");

	}

	public Franja(List<String> franja) {
		super();
		this.franja = franja;
	}

	public List<String> getFranja() {
		return franja;
	}

	public void setFranja(List<String> franja) {
		this.franja = franja;
	}

	public void mostrarFranaja() {
		for (String f : franja) {
			System.out.println(" "+f);

		}
	}

}
