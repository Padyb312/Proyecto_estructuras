package Principal;

import java.util.Scanner;

import modelo.*;

/**
 *
 * @author Kenneth Damian Fonseca Bernal
 * @author Juan Jose Padilla
 */

public class Stockfish_Academic {

	public static void main(String[] args) {

		Scanner teclado = new Scanner(System.in);
		Calendario usuario = new Calendario();

		System.out.println("Hola! Que accion desea realizar?");
		int eleccion;
		do {
			System.out.println("--------MENU-------");
			System.out.println("1. PROGRAMAR EVENTO");
			System.out.println("2. ELIMINAR EVENTO");
			System.out.println("3. VER CALENDARIO");
			System.out.println("4. SALIR");
			eleccion = teclado.nextInt();
			switch (eleccion) {
			case 1:

				System.out.println("Que dia desea agregar un recordatorio?");
				int dia = teclado.nextInt();
				System.out.println("Que semana desea agregar un recordatorio?");
				int semana = teclado.nextInt();
				if (dia >= 0 && dia < 7 && semana >= 0 && semana < 5) {
					usuario.agendar(semana, dia);
					break;

				}
				System.out.println("------------------------------");
				System.out.println("Semana invalida o Dia invalido");

				break;
			case 2:
				System.out.println("Que dia desea eliminar un recordatorio?");
				int diaa = teclado.nextInt();
				System.out.println("Que semana desea eliminar un recordatorio?");
				int semanaa = teclado.nextInt();
				if (diaa >= 0 && diaa < 7 && semanaa >= 0 && semanaa < 5) {
					usuario.eliminar(semanaa, diaa);
					break;

				}
				System.out.println("------------------------------");
				System.out.println("Semana invalida o Dia invalido");

				break;

			case 3:
				usuario.mostrar();
				break;
			case 4:
				System.out.println("PROGRAMA FINALIZADO");
				break;
			default:
				System.out.println("OPCION INVALIDA");

			}
		} while (eleccion != 4);
	}

}
