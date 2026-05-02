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
		AutenticacionService auth = new AutenticacionService();

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

				if (auth.login(correoLogin, passLogin)) {
					correoActivo = correoLogin;
					autenticado = true;

					// Cargar eventos del usuario al iniciar sesión
					try {
						java.io.File archivoEventos = new java.io.File(archivoUsuario(correoActivo));
						if (archivoEventos.exists()) {
							List<Evento> eventosGuardados = operaciones.deserializar("", archivoUsuario(correoActivo));
							if (eventosGuardados != null && !eventosGuardados.isEmpty()) {
								for (Evento e : eventosGuardados) {
									operaciones.crear(e);
								}
								System.out.println("Bienvenido, " + correoActivo + "! (" + eventosGuardados.size()
										+ " eventos cargados)");
							} else {
								System.out.println("Bienvenido, " + correoActivo + "!");
							}
						} else {
							System.out.println("Bienvenido, " + correoActivo + "!");
						}
					} catch (Exception e) {
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

		String fechaInicio, fechaEntrga, descripcion, materia, codigo;
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
			System.out.println("6. Guardar");
			System.out.println("7. Eliminar Cuenta");
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
				try {
					List<Evento> listaGuardar = operaciones.mostrarTodos();
					System.out.println(operaciones.serializar(listaGuardar, "", archivoUsuario(correoActivo)));
					System.out.println("Guardado....");
				} catch (Exception e) {
					System.out.println("Error al guardar los Eventos: " + e.getMessage());
				}
				break;

			case 7:
				// Confirmar con contraseña antes de eliminar
				System.out.println("Ingrese su contraseña para confirmar: ");
				String passElim = teclado.next();
				String resultado = auth.eliminar(correoActivo, passElim);
				System.out.println(resultado);

				// Si se eliminó exitosamente cerrar el programa
				if (resultado.equals("Usuario eliminado exitosamente.")) {
					// Borrar también el archivo de eventos del usuario
					new java.io.File(archivoUsuario(correoActivo)).delete();
					System.out.println("Hasta luego!");
					System.exit(0);
				}
				break;

			case 8:
				// Guardar automáticamente al salir
				try {
					List<Evento> listaFinal = operaciones.mostrarTodos();
					operaciones.serializar(listaFinal, "", archivoUsuario(correoActivo));
					System.out.println("Eventos guardados. Hasta luego!");
				} catch (Exception e) {
					System.out.println("Error al guardar: " + e.getMessage());
					System.out.println("Saliendo....");
				}
				break;

			default:
				System.out.println("Opcion Invalida");
			}

		} while (opcion != 8);
	}

	/**
	 * Convierte el correo en un nombre de archivo válido. Ejemplo:
	 * juan@poligran.edu.co → juan_poligran_edu_co_eventos
	 */
	private static String archivoUsuario(String correo) {
		return correo.replace("@", "").replace(".", "") + "Eventos";
	}

}