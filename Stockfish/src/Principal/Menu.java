package Principal;

import modelo.*;
import Servicios.*;
import java.util.Scanner;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import Servicios.OperacionesFecha;

/**
 *
 * @author Kenneth Damian Fonseca Bernal
 * @author Juan Jose Padilla
 */

public class Menu {

	public static void main(String[] args) {
		Scanner teclado = new Scanner(System.in);
		ImplementacionOperacionCRUD operaciones = new ImplementacionOperacionCRUD();
		ImplemtacionLogeo auth = new ImplemtacionLogeo();

		// ─── Menú de autenticación ───────────────────────────────────────────────────
		boolean autenticado = false;
		String correoActivo = null;
		int opcionAuth;

		do {
			System.out.println("--------ACCESO-------");
			System.out.println("1. Iniciar Sesion");
			System.out.println("2. Registrarse");
			System.out.println("3. Salir");
			opcionAuth = teclado.nextInt();

			switch (opcionAuth) {
			case 1:
				System.out.println("Correo institucional (@poligran.edu.co): ");
				String correoLogin = teclado.next();
				System.out.println("Contraseña: ");
				String passLogin = teclado.next();

				if (auth.iniciarSesion(correoLogin, passLogin)) {
					correoActivo = correoLogin;
					autenticado = true;

					// Cargar eventos del usuario desde el archivo de usuarios
					List<Evento> eventosGuardados = auth.obtenerEventos(correoActivo);
					if (!eventosGuardados.isEmpty()) {
						// Actualizar contador de IdEvento para evitar IDs duplicados
						int maxId = eventosGuardados.stream().mapToInt(e -> {
							try {
								return Integer.parseInt(e.getId());
							} catch (NumberFormatException ex) {
								return 0;
							}
						}).max().orElse(0);
						if (maxId >= IdEvento.getCont()) {
							IdEvento.setCont(maxId + 1);
						}
						for (Evento e : eventosGuardados) {
							operaciones.crear(e);
						}
						System.out.println(
								"Bienvenido, " + correoActivo + "! (" + eventosGuardados.size() + " eventos cargados)");
					} else {
						System.out.println("Bienvenido, " + correoActivo + "!");
					}

				} else {
					System.out.println("Correo o contraseña incorrectos.");
				}
				break;

			case 2:
				System.out.println("Correo institucional (@poligran.edu.co): ");
				String correoReg = teclado.next();
				System.out.println("Contraseña: ");
				String passReg = teclado.next();
				System.out.println("Confirmar contraseña: ");
				String confirmacion = teclado.next();

				System.out.println(auth.registrar(correoReg, passReg, confirmacion));
				break;

			case 3:
				System.out.println("Saliendo....");
				System.exit(0);
				break;

			default:
				System.out.println("Opcion Invalida");
			}

		} while (!autenticado);

		// ─── Menú principal ──────────────────────────────────────────────────────────

		String fechaEntrga, actividad, descripcion, codigo, horaStr;
		int prioridad = 0, comparacion = 0;
		boolean estado = false;
		int opcion;

		do {
			System.out.println("--------MENU-------");
			System.out.println("1. Crear Evento");
			System.out.println("2. Mostrar Por Codigo");
			System.out.println("3. Mostar Eventos");
			System.out.println("4. Modificar Estado");
			System.out.println("5. Eliminar Evento");
			System.out.println("6. Guardar");
			System.out.println("7. Eliminar Cuenta");
			System.out.println("8. Salir");
			opcion = teclado.nextInt();

			switch (opcion) {
			case 1:
				teclado.nextLine(); // limpiar buffer
				System.out.println("Digite Fecha DD/MM/YY");
				fechaEntrga = teclado.nextLine();

				System.out.println("Digite la hora del evento (ej: 10:30 AM o 2:45 PM): ");
				horaStr = teclado.nextLine();
				System.out.println("Ingrese Actividad ");
				actividad = teclado.nextLine();

				System.out.println("Ingrese Descripcion ");
				descripcion = teclado.nextLine();

				do {
					System.out.println("Ingrese Prioridad 1(Alta), 2(Media), 3(Baja): ");
					prioridad = teclado.nextInt();
					if (prioridad < 1 || prioridad > 3) {
						System.out.println("Opción inválida. Solo se permite 1, 2 o 3.");
					}
				} while (prioridad < 1 || prioridad > 3);

				comparacion = 0;
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

				Evento nuevoEvento = new Evento(null, OperacionesFecha.parseFecha(fechaEntrga, horaStr), actividad,
						descripcion, prioridad, estado);
				System.out.println(operaciones.crear(nuevoEvento) + " Con el id: " + nuevoEvento.getId());
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

				comparacion = 0;
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
				if (cambiarEstado == null) {
					System.out.println("Evento no encontrado.");
				} else if (estado == cambiarEstado.isEstado()) {
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
				// Guardar eventos dentro del archivo de usuarios
				auth.actualizarEventos(correoActivo, operaciones.mostrarTodos());
				System.out.println("Guardado....");
				break;

			case 7:
				System.out.println("Ingrese su contraseña para confirmar: ");
				String passElim = teclado.next();
				String resultado = auth.eliminar(correoActivo, passElim);
				System.out.println(resultado);

				if (resultado.equals("Usuario eliminado exitosamente.")) {
					System.out.println("Hasta luego!");
					System.exit(0);
				}
				break;

			case 8:
				// Guardar automáticamente al salir
				auth.actualizarEventos(correoActivo, operaciones.mostrarTodos());
				System.out.println("Eventos guardados. Hasta luego!");
				break;

			default:
				System.out.println("Opcion Invalida");
			}

		} while (opcion != 8);
	}

	

}