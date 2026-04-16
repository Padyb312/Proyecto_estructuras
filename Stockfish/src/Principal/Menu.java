package Principal;

import modelo.*;
import Servicios.*;
import java.util.Scanner;
import java.util.List;

/**
 *
 * @author Kenneth Damian Fonseca Bernal
 * @author Juan Jose Padilla
 */

public class Menu {

	public static void main(String[] args) {
		Scanner teclado = new Scanner(System.in);
		ImplementacionOperacionCRUD operaciones = new ImplementacionOperacionCRUD();

		String fechaInicio, fechaEntrga, descripcion, materia, codigo, nuevaFecha;
		int prioridad = 0, franja = -1, comparacion = 0;
		boolean estado = false;
		int opcion;
		do {
			System.out.println("--------MENU-------");
			System.out.println("1. Crear Evento");
			System.out.println("2. Mostrar Por Codigo");
			System.out.println("3. Mostar Eventos");
			System.out.println("4. Modificar Estado");
			System.out.println("5. Eliminar Evento");
			System.out.println("6. Guardar (Serializar)");
			System.out.println("7. Cargar eventos Guardados (Deserializar)");
			System.out.println("8. Salir");
			opcion = teclado.nextInt();

			switch (opcion) {
			case 1:
				do {
					System.out.println("----Sub MENU----");
					System.out.println("1. Tarea");
					System.out.println("2. Evento Personal o activida extraCurricular");
					System.out.println("3. volver");
					opcion = teclado.nextInt();

					switch (opcion) {
					case 1:

						System.out.println("Digite Fecha de Inicio DD/MM/YY");
						fechaInicio = teclado.next();

						System.out.println("Digite Fecha de Entrega DD/MM/YY");
						fechaEntrga = teclado.next();

						System.out.println("Digite Materia ");
						materia = teclado.next();

						System.out.println("Ingrese Actividad ");
						descripcion = teclado.next();

						do {
							System.out.println("Digite horario segun la siguinte franja");
							Franja fran = new Franja();
							fran.mostrarFranja();
							franja = teclado.nextInt();
							if (franja < 1 || franja > 23) {
								System.out.println("Opción inválida. Solo se permite numeros del 0 al 23.");
							}
						} while (franja < 0 || franja > 23);

						do {
							System.out.println("Ingrese Prioridad 1(Alta), 2(Media), 3(Baja): ");
							prioridad = teclado.nextInt();
							if (prioridad < 1 || prioridad > 3) {
								System.out.println("Opción inválida. Solo se permite 1, 2 o 3.");
							}
						} while (prioridad < 1 || prioridad > 3);

						do {

							System.out.println("Ingrese estado 1(Completado), 2(Incompleto) ");
							comparacion = teclado.nextInt();
							if (comparacion == 1) {
								estado = true;
							}
							if (comparacion == 2) {
								estado = false;
							}
							if (comparacion < 1 || comparacion > 2) {
								System.out.println("Opción inválida. Solo se permite 1 o 2 ");
							}

						} while (comparacion < 1 || comparacion > 2);

						Evento tareaBase = new Tarea(fechaEntrga, descripcion, prioridad, estado, franja, materia,
								fechaInicio);

						System.out.println(operaciones.crear(tareaBase) + " Con el id: " + tareaBase.getId());

						break;
					case 2:

						System.out.println("Digite Fecha de Evento DD/MM/YY");
						fechaEntrga = teclado.next();

						System.out.println("Descripcion  (Sin espacios) ");
						descripcion = teclado.next();

						do {

							System.out.println("Digite horario segun la siguinte franja");
							Franja fran = new Franja();
							fran.mostrarFranja();
							franja = teclado.nextInt();
							if (franja < 1 || franja > 23) {
								System.out.println("Opción inválida. Solo se permite numeros del 0 al 23.");
							}

						} while (franja < 0 || franja > 23);

						System.out.println("Ingrese Prioridad 1(Alta), 2(Media), 3(Baja): ");
						prioridad = teclado.nextInt();
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
							if (comparacion == 1) {
								estado = true;
							}
							if (comparacion == 2) {
								estado = false;
							}
							if (comparacion < 1 || comparacion > 2) {
								System.out.println("Opción inválida. Solo se permite 1 o 2 ");
							}

						}
						Evento personalBase = new ActividadPersonal(fechaEntrga, descripcion, prioridad, estado,
								franja);
						System.out.println(operaciones.crear(personalBase) + " Con el id: " + personalBase.getId());

						break;

					case 3:
						System.out.println("volviendo");
						break;

					default:
						System.out.println("Opcion Invalida");
					}

				} while (opcion != 3);
				break;

			case 2:

				System.out.println("Ingrese Codigo de Evento");
				codigo = teclado.next();
				System.out.println(operaciones.mostrarUno(codigo));
				break;

			case 3:

				List<Evento> listaEventos = operaciones.mostrarTodos();

				if (listaEventos.isEmpty()) {
					System.out.println("No hay eventos registrados");
				} else {
					for (Evento e : listaEventos) {
						System.out.println(e);
					}
				}
				break;

			case 4:

				System.out.println("Ingrese Codigo de Evento");
				codigo = teclado.next();

				while (comparacion < 1 || comparacion > 2) {
					System.out.println("Ingrese estado 1(Completado), 2(Incompleto) ");
					comparacion = teclado.nextInt();
					if (comparacion == 1) {
						estado = true;
					}
					if (comparacion == 2) {
						estado = false;
					}
					if (comparacion < 1 || comparacion > 2) {
						System.out.println("Opción inválida. Solo se permite 1 o 2 ");
					}

				}

				Evento cambiarEstado = operaciones.mostrarUno(codigo);
				if (estado == cambiarEstado.isEstado()) {
					System.out.println("El estado no ha sido alterado ya que es el mismo ");
				} else {
					cambiarEstado.setEstado(estado);
					System.out.println(operaciones.modificar(codigo, cambiarEstado));
				}

				break;

			case 5:
				System.out.println("Ingrese Codigo de Evento a Eliminar");
				codigo = teclado.next();
				System.out.println(operaciones.eliminar(codigo));
				break;

			case 6:
				String patch = "";
				String name = "Eventos";

				List<Evento> listaGuardar = operaciones.mostrarTodos();
				System.out.println(operaciones.serializar(listaGuardar, patch, name));
				System.out.println("Guardado....");
				break;

			case 7:
				String patch2 = "";
				String name2 = "Eventos";
				List<Evento> listaImportar = operaciones.deserializar(patch2, name2);
				if (listaImportar != null && !listaImportar.isEmpty()) {
					System.out.println("Importado.... " + listaImportar.size() + " eventos cargados");
					for (Evento puntero : listaImportar) {
						operaciones.crear(puntero);
					}

				} else {
					System.out.println("No se encontraron eventos");
				}

				break;

			case 8:
				System.out.println("Saliendo....");
				break;

			default:
				System.out.println("Opcion Invalida");
			}

		} while (opcion != 8);
		// System.out.println("Ruta actual: " + System.getProperty("user.dir"));
	}

}
