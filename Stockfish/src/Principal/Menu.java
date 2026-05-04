package Principal;

import modelo.*;
import Servicios.*;
import java.util.Scanner;
import java.util.List;
import java.time.LocalDateTime;

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

					List<Evento> eventosGuardados = auth.obtenerEventos(correoActivo);
					if (!eventosGuardados.isEmpty()) {
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

					String chatId = auth.obtenerTelegramChatId(correoActivo);
					System.out.println("Verificando recordatorios...");
					Usuario usuario = auth.obtenerUsuario(correoActivo);
					Utilidades.verificarEventosCercanos(operaciones.mostrarTodos(), chatId, usuario);

				} else {
					System.out.println("Correo o contraseña incorrectos.");
				}
				break;

			case 2:
				System.out.println("Ingrese sus Nombres: ");
				String nombres = teclado.next();
				System.out.println("Ingrese sus Apellidos: ");
				String apellidos = teclado.next();
				System.out.println("Ingrese su Facultad: ");
				String Facultad = teclado.next();
				System.out.println("Correo institucional (@poligran.edu.co): ");
				String correoReg = teclado.next();
				System.out.println("Contraseña: ");
				String passReg = teclado.next();
				System.out.println("Confirmar contraseña: ");
				String confirmacion = teclado.next();
				System.out.println(auth.registrar(correoReg, passReg, confirmacion, nombres, apellidos, Facultad));
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
			System.out.println("3. Mostrar Eventos");
			System.out.println("4. Modificar Estado");
			System.out.println("5. Eliminar Evento");
			System.out.println("6. Guardar");
			System.out.println("7. Ajustes");
			System.out.println("8. Eliminar Cuenta");
			System.out.println("9. Salir");
			opcion = teclado.nextInt();

			switch (opcion) {
			case 1:
				teclado.nextLine();
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
						System.out.println("Opcion invalida. Solo se permite 1, 2 o 3.");
					}
				} while (prioridad < 1 || prioridad > 3);

				comparacion = 0;
				do {
					System.out.println("Ingrese estado 1(Completado), 2(Incompleto) ");
					comparacion = teclado.nextInt();
					if (comparacion == 1)
						estado = true;
					if (comparacion == 2)
						estado = false;
					if (comparacion < 1 || comparacion > 2) {
						System.out.println("Opcion invalida. Solo se permite 1 o 2 ");
					}
				} while (comparacion < 1 || comparacion > 2);

				Evento nuevoEvento = new Evento(null, Utilidades.parseFecha(fechaEntrga, horaStr), actividad,
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
					if (comparacion == 1)
						estado = true;
					if (comparacion == 2)
						estado = false;
					if (comparacion < 1 || comparacion > 2) {
						System.out.println("Opcion invalida. Solo se permite 1 o 2 ");
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
				auth.actualizarEventos(correoActivo, operaciones.mostrarTodos());
				System.out.println("Guardado....");
				break;

			// ─── Sección Ajustes ─────────────────────────────────────────────────────
			case 7:
				teclado.nextLine();
				int opcionAjustes;
				do {
					System.out.println("\n-------- AJUSTES --------");
					System.out.println("1. Configurar Telegram");
					System.out.println("2. Configurar Usuario");
					System.out.println("3. Volver al menu principal");
					opcionAjustes = teclado.nextInt();
					teclado.nextLine();

					switch (opcionAjustes) {

					// ── Submenu Telegram ──────────────────────────────────────────
					case 1:
						int opcionTelegram;
						do {
							System.out.println("\n-------- CONFIGURAR TELEGRAM --------");
							System.out.println("Para recibir notificaciones necesitas:");
							System.out.println("  1. Buscar tu bot en Telegram y presionar START");
							System.out.println("     -> Nombre del bot: @JasbelBOT");
							System.out.println("  2. Buscar @userinfobot en Telegram,");
							System.out.println("     escribirle cualquier mensaje y copiar tu ID");
							System.out.println("  3. Ingresar ese ID en la opcion 2 de este menu");
							System.out.println("--------------------------------------");
							System.out.println("1. Ver mi Chat ID actual");
							System.out.println("2. Ingresar / Actualizar Chat ID");
							System.out.println("3. Volver a Ajustes");
							opcionTelegram = teclado.nextInt();
							teclado.nextLine();

							switch (opcionTelegram) {
							case 1:
								String chatIdActual = auth.obtenerTelegramChatId(correoActivo);
								if (chatIdActual != null && !chatIdActual.isBlank() && !chatIdActual.equals("0")) {
									System.out.println("Tu Chat ID actual es: " + chatIdActual);
								} else {
									System.out.println("No tienes un Chat ID configurado.");
								}
								break;
							case 2:
								System.out.println("Ingrese su Chat ID (0 para desactivar notificaciones): ");
								String nuevoChatId = teclado.nextLine();
								auth.actualizarTelegramChatId(correoActivo, nuevoChatId);
								System.out.println("Chat ID actualizado correctamente.");
								break;
							case 3:
								System.out.println("Volviendo a Ajustes...");
								break;
							default:
								System.out.println("Opcion invalida.");
							}
						} while (opcionTelegram != 3);
						break;

					// ── Submenu Configurar Usuario ────────────────────────────────
					case 2:
						Usuario usuarioActual = auth.obtenerUsuario(correoActivo);
						System.out.println("\n-------- CONFIGURAR USUARIO --------");
						System.out.println("Datos actuales:");
						System.out.println("  Nombres:   " + usuarioActual.getNombres());
						System.out.println("  Apellidos: " + usuarioActual.getApellidos());
						System.out.println("  Facultad:  " + usuarioActual.getFacultad());
						System.out.println("------------------------------------");
						System.out.println("Ingrese nuevos Nombres (Enter para mantener actual): ");
						String nuevoNombre = teclado.nextLine();
						System.out.println("Ingrese nuevos Apellidos (Enter para mantener actual): ");
						String nuevoApellido = teclado.nextLine();
						System.out.println("Ingrese nueva Facultad (Enter para mantener actual): ");
						String nuevaFacultad = teclado.nextLine();

						// Conservar valor actual si el usuario no ingresa nada
						if (nuevoNombre.isBlank())
							nuevoNombre = usuarioActual.getNombres();
						if (nuevoApellido.isBlank())
							nuevoApellido = usuarioActual.getApellidos();
						if (nuevaFacultad.isBlank())
							nuevaFacultad = usuarioActual.getFacultad();

						System.out.println(
								auth.actualizarDatosUsuario(correoActivo, nuevoNombre, nuevoApellido, nuevaFacultad));
						break;

					case 3:
						System.out.println("Volviendo al menu principal...");
						break;

					default:
						System.out.println("Opcion invalida.");
					}
				} while (opcionAjustes != 3);
				break;

			case 8:
				System.out.println("Ingrese su contraseña para confirmar: ");
				String passElim = teclado.next();
				String resultado = auth.eliminar(correoActivo, passElim);
				System.out.println(resultado);
				if (resultado.equals("Usuario eliminado exitosamente.")) {
					System.out.println("Hasta luego!");
					System.exit(0);
				}
				break;

			case 9:
				auth.actualizarEventos(correoActivo, operaciones.mostrarTodos());
				System.out.println("Eventos guardados. Hasta luego!");
				break;

			default:
				System.out.println("Opcion Invalida");
			}

		} while (opcion != 9);
	}
}