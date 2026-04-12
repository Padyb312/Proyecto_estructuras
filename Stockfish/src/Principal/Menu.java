package Principal;

import modelo.*;
import Servicios.*;
import java.util.Scanner;
import java.util.List;

public class Menu {

	public static void main(String[] args) {
		Scanner teclado = new Scanner(System.in);
		ImplementacionOperacionCRUD operaciones = new ImplementacionOperacionCRUD();

		String fechaInicio, fechaEntrga, descripcion;
		int prioridad = 0, franja=-1, comparacion=0;
		boolean estado; 
		int opcion;
		do {
			System.out.println("--------MENU-------");
			System.out.println("1. Crear Evento");
			System.out.println("2. Mostrar Por Codigo");
			System.out.println("3. Mostar Eventos");
			System.out.println("4. Modificar Evento");
			System.out.println("5. Eliminar Evento");
			System.out.println("6. Guardar (Serializar)");
			System.out.println("7. Cargar eventos Guardados (Deserializar)");
			System.out.println("9. Salir");
			opcion = teclado.nextInt();

			switch (opcion) {
			case 1:
				do {
					System.out.println("----Sub MENU----");
					System.out.println("1. Tarea");
					System.out.println("2. Evento Personal o activida Curricular");
					System.out.println("3. volver");
					opcion = teclado.nextInt();

					switch (opcion) {
					case 1:
						
						System.out.println("Digite Fecha de Inicio DD/MM/YY");
						fechaInicio = teclado.next();

						System.out.println("Digite Fecha de Entrega DD/MM/YY");
						fechaEntrga = teclado.next();
						
						System.out.println("Digite descripcion de la Tarea");
						descripcion = teclado.next();
						
						while (franja<0||franja>23) {
							System.out.println("Digite horario segun la siguinte franja");
							Franja fran = new Franja();
							fran.mostrarFranja();
							franja=teclado.nextInt();
							if (franja < 1 || franja > 23) {
								
							}
						}

						while (prioridad < 1 || prioridad > 3) {
							System.out.println("Ingrese Prioridad 1(Alta), 2(Media), 3(Baja): ");
							prioridad = teclado.nextInt();
							if (prioridad < 1 || prioridad > 3) {
								System.out.println("Opción inválida. Solo se permite 1, 2 o 3.");
							}
						}
						while (comparacion < 1 || comparacion > 2) {
							System.out.println("Ingrese estado 1(Completado), 2(Incompleto) ");
							comparacion = teclado.nextInt();
							if (comparacion < 1 || comparacion > 2) {
								System.out.println("Opción inválida. Solo se permite 1 o 2 ");
							}
							if(comparacion==1 ) {
								estado=true;
							}
							if(comparacion==2 ) {
								estado=false;
							}
						}
						

						break;
					case 2:

						break;

					case 3:
						System.out.println("volviendo");
						break;

					default:
						System.out.println("Opcion Invalida");
					}

				} while (opcion != 3);
				break;

			default:
				System.out.println("Opcion Invalida");
			}

		} while (opcion != 9);
	}

}
