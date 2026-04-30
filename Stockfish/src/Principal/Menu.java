package Principal;

import modelo.*;
import Servicios.*;
import java.util.Scanner;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * @author Kenneth Damian Fonseca Bernal
 * @author Juan Jose Padilla
 */
public class Menu {

    static final DateTimeFormatter FMT_FECHA = DateTimeFormatter.ofPattern("dd/MM/yy");

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        ImplementacionOperacionCRUD operaciones = new ImplementacionOperacionCRUD();
        AutenticacionService auth = new AutenticacionService();
        NotificacionService notificaciones = null;

        // ─── Menú de autenticación ────────────────────────────────────────────
        boolean autenticado = false;
        String correoActivo = null;
        int opcionAuth;

        do {
            System.out.println("\n--------ACCESO-------");
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
                        autenticado  = true;

                        // Reconstruir WhatsApp si ya estaba configurado
                        WhatsAppService waLogin = auth.buildWhatsAppService(correoActivo);
                        if (waLogin != null) {
                            notificaciones = new NotificacionService(waLogin);
                            System.out.println("WhatsApp activo.");
                        }

                        // Cargar eventos guardados
                        try {
                            java.io.File arch = new java.io.File(archivoUsuario(correoActivo));
                            if (arch.exists()) {
                                List<Evento> guardados = operaciones.deserializar("",
                                    archivoUsuario(correoActivo));
                                if (guardados != null && !guardados.isEmpty()) {
                                    for (Evento e : guardados) {
                                        operaciones.crear(e);
                                    }
                                    // Fix contador IDs
                                    int maxId = guardados.stream()
                                        .mapToInt(e -> Integer.parseInt(e.getId()))
                                        .max().orElse(-1);
                                    IdEvento.setCont(maxId + 1);
                                    System.out.println("Bienvenido, " + correoActivo
                                        + "! (" + guardados.size() + " eventos cargados)");
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

        // ─── Menú principal ───────────────────────────────────────────────────
        String descripcion, materia, codigo;
        int prioridad = 0, comparacion = 0;
        boolean estado = false;
        int opcion;

        do {
            System.out.println("\n--------MENU-------");
            System.out.println("1.  Crear Evento");
            System.out.println("2.  Mostrar Por Codigo");
            System.out.println("3.  Mostrar Eventos");
            System.out.println("4.  Modificar Estado");
            System.out.println("5.  Eliminar Evento");
            System.out.println("6.  Guardar");
            System.out.println("7.  Notificaciones WhatsApp");
            System.out.println("8.  Configurar WhatsApp");
            System.out.println("9.  Eliminar Cuenta");
            System.out.println("10. Salir");
            opcion = teclado.nextInt();

            switch (opcion) {

                // ── CREAR EVENTO ─────────────────────────────────────────────
                case 1:
                    int opcionSub;
                    do {
                        System.out.println("\n----Sub MENU----");
                        System.out.println("1. Tarea");
                        System.out.println("2. Evento Personal o Actividad Extracurricular");
                        System.out.println("3. Volver");
                        opcionSub = teclado.nextInt();

                        switch (opcionSub) {
                            case 1:
                                // Fecha inicio
                                LocalDate fechaInicio = pedirFecha(teclado,
                                    "Digite Fecha de Inicio DD/MM/YY");

                                // Fecha entrega — no puede ser antes que inicio
                                LocalDate fechaEntrega = null;
                                do {
                                    fechaEntrega = pedirFecha(teclado,
                                        "Digite Fecha de Entrega DD/MM/YY");
                                    if (fechaEntrega.isBefore(fechaInicio)) {
                                        System.out.println(
                                            "La entrega no puede ser antes del inicio.");
                                        fechaEntrega = null;
                                    }
                                } while (fechaEntrega == null);

                                System.out.println("Digite Materia: ");
                                materia = teclado.next();

                                System.out.println("Ingrese Actividad: ");
                                descripcion = teclado.next();

                                LocalTime horaTarea = pedirHora(teclado);
                                prioridad = pedirPrioridad(teclado);
                                estado    = pedirEstado(teclado);

                                try {
                                    Evento tarea = new Tarea(fechaEntrega, descripcion,
                                        prioridad, estado, horaTarea, materia, fechaInicio);
                                    System.out.println(operaciones.crear(tarea)
                                        + " | ID: " + tarea.getId());
                                } catch (IllegalArgumentException e) {
                                    System.out.println("Error: " + e.getMessage());
                                }
                                break;

                            case 2:
                                LocalDate fechaEvento = pedirFecha(teclado,
                                    "Digite Fecha del Evento DD/MM/YY");

                                System.out.println("Descripcion (sin espacios): ");
                                descripcion = teclado.next();

                                LocalTime horaEvento = pedirHora(teclado);
                                prioridad = pedirPrioridad(teclado);
                                estado    = pedirEstado(teclado);

                                Evento personal = new ActividadPersonal(fechaEvento,
                                    descripcion, prioridad, estado, horaEvento);
                                System.out.println(operaciones.crear(personal)
                                    + " | ID: " + personal.getId());
                                break;

                            case 3:
                                System.out.println("Volviendo...");
                                break;

                            default:
                                System.out.println("Opcion Invalida");
                        }
                    } while (opcionSub != 3);
                    break;

                // ── MOSTRAR POR CODIGO ────────────────────────────────────────
                case 2:
                    System.out.println("Ingrese Codigo de Evento: ");
                    codigo = teclado.next();
                    Evento encontrado = operaciones.mostrarUno(codigo);
                    if (encontrado == null) {
                        System.out.println("Evento no encontrado.");
                    } else {
                        System.out.println(encontrado);
                    }
                    break;

                // ── MOSTRAR TODOS ─────────────────────────────────────────────
                case 3:
                    List<Evento> listaEventos = operaciones.mostrarTodos();
                    if (listaEventos.isEmpty()) {
                        System.out.println("No hay eventos registrados.");
                    } else {
                        for (Evento e : listaEventos) {
                            System.out.println(e);
                        }
                    }
                    break;

                // ── MODIFICAR ESTADO ──────────────────────────────────────────
                case 4:
                    System.out.println("Ingrese Codigo de Evento: ");
                    codigo = teclado.next();
                    Evento aModificar = operaciones.mostrarUno(codigo);
                    if (aModificar == null) {
                        System.out.println("Evento no encontrado.");
                        break;
                    }
                    estado = pedirEstado(teclado);
                    if (estado == aModificar.isEstado()) {
                        System.out.println("El estado no ha sido alterado ya que es el mismo.");
                    } else {
                        aModificar.setEstado(estado);
                        System.out.println(operaciones.modificar(codigo, aModificar));
                    }
                    break;

                // ── ELIMINAR EVENTO ───────────────────────────────────────────
                case 5:
                    System.out.println("Ingrese Codigo de Evento a Eliminar: ");
                    codigo = teclado.next();
                    Evento eliminado = operaciones.eliminar(codigo);
                    if (eliminado == null) {
                        System.out.println("Evento no encontrado.");
                    } else {
                        System.out.println("Evento eliminado: " + eliminado.getActividad());
                    }
                    break;

                // ── GUARDAR ───────────────────────────────────────────────────
                case 6:
                    try {
                        List<Evento> listaGuardar = operaciones.mostrarTodos();
                        System.out.println(operaciones.serializar(listaGuardar, "",
                            archivoUsuario(correoActivo)));
                        System.out.println("Guardado.");
                    } catch (Exception e) {
                        System.out.println("Error al guardar: " + e.getMessage());
                    }
                    break;

                // ── NOTIFICACIONES WHATSAPP ───────────────────────────────────
                case 7:
                    if (notificaciones == null) {
                        System.out.println("Configura WhatsApp primero (opcion 8).");
                        break;
                    }
                    int opcionNot;
                    do {
                        System.out.println("\n---- Notificaciones ----");
                        System.out.println("1. Notificar eventos pendientes");
                        System.out.println("2. Notificar evento especifico");
                        System.out.println("3. Volver");
                        opcionNot = teclado.nextInt();

                        switch (opcionNot) {
                            case 1:
                                notificaciones.notificarPendientes(operaciones.mostrarTodos());
                                break;

                            case 2:
                                System.out.println("Ingrese ID del evento: ");
                                String idNot = teclado.next();
                                notificaciones.notificarUno(operaciones.mostrarUno(idNot));
                                break;

                            case 3:
                                System.out.println("Volviendo...");
                                break;

                            default:
                                System.out.println("Opcion invalida.");
                        }
                    } while (opcionNot != 3);
                    break;

                // ── CONFIGURAR WHATSAPP ───────────────────────────────────────
                case 8:
                    int opcionWA;
                    do {
                        Usuario usuarioActivo = auth.getUsuario(correoActivo);
                        System.out.println();
                        if (usuarioActivo.tieneWhatsApp()) {
                            System.out.println("Estado: WhatsApp enlazado (+"
                                + usuarioActivo.getTelefono() + ")");
                        } else if (usuarioActivo.getTelefono() != null) {
                            System.out.println("Estado: Telefono guardado, falta ApiKey.");
                        } else {
                            System.out.println("Estado: Sin WhatsApp configurado.");
                        }

                        System.out.println("---- WhatsApp ----");
                        System.out.println("1. Activar/Reactivar WhatsApp");
                        System.out.println("2. Modificar telefono");
                        System.out.println("3. Modificar ApiKey");
                        System.out.println("4. Enviar mensaje de prueba");
                        System.out.println("5. Desenlazar WhatsApp");
                        System.out.println("6. Volver");
                        opcionWA = teclado.nextInt();

                        switch (opcionWA) {
                            case 1:
                                // Paso 1 — pedir número
                                System.out.println(
                                    "Ingrese su numero (10 digitos sin indicativo, Ej: 3001234567): ");
                                String telActivar = teclado.next();
                                String resultTel = auth.setTelefono(correoActivo, telActivar);
                                System.out.println(resultTel);

                                if (!resultTel.startsWith("Telefono")) break;

                                // Paso 2 — abrir WhatsApp con mensaje prellenado
                                System.out.println(
                                    "\nAhora se abrira WhatsApp con el mensaje de activacion listo.");
                                System.out.println(
                                    "Solo presiona ENVIAR en tu celular y espera la ApiKey.");
                                abrirActivacionWhatsApp();

                                // Paso 3 — ingresar ApiKey
                                System.out.println("\nIngrese la ApiKey que le llego por WhatsApp: ");
                                String apiKeyActivar = teclado.next();
                                System.out.println(auth.setApiKey(correoActivo, apiKeyActivar));
                                break;

                            case 2:
                                // Cambiar número — requiere reactivación
                                System.out.println(
                                    "Ingrese nuevo numero (10 digitos sin indicativo): ");
                                String telNuevo = teclado.next();
                                String resultNuevo = auth.setTelefono(correoActivo, telNuevo);
                                System.out.println(resultNuevo);

                                if (!resultNuevo.startsWith("Telefono")) break;

                                System.out.println(
                                    "\nComo cambio el numero debe reactivar WhatsApp.");
                                System.out.println(
                                    "Se abrira WhatsApp con el mensaje listo. Solo presiona ENVIAR.");
                                abrirActivacionWhatsApp();

                                System.out.println("\nIngrese la nueva ApiKey que le llego: ");
                                String apiKeyNueva = teclado.next();
                                System.out.println(auth.setApiKey(correoActivo, apiKeyNueva));
                                break;

                            case 3:
                                // Solo cambiar ApiKey — sin reactivar
                                System.out.println("Ingrese la nueva ApiKey: ");
                                String soloApiKey = teclado.next();
                                System.out.println(auth.setApiKey(correoActivo, soloApiKey));
                                break;

                            case 4:
                                // Mensaje de prueba
                                WhatsAppService waPrueba = auth.buildWhatsAppService(correoActivo);
                                if (waPrueba == null) {
                                    System.out.println("Configura WhatsApp primero (opcion 1).");
                                } else {
                                    System.out.println("Enviando mensaje de prueba...");
                                    boolean ok = waPrueba.enviarPrueba();
                                    System.out.println(ok
                                        ? "Mensaje enviado. Revisa tu WhatsApp."
                                        : "Error al enviar. Verifica tu ApiKey y numero.");
                                }
                                break;

                            case 5:
                                System.out.println("Seguro que desea desenlazar WhatsApp? (s/n): ");
                                String confirmar = teclado.next();
                                if (confirmar.equalsIgnoreCase("s")) {
                                    System.out.println(auth.desenlazarWhatsApp(correoActivo));
                                    notificaciones = null;
                                    System.out.println(
                                        "WhatsApp desenlazado. Notificaciones desactivadas.");
                                }
                                break;

                            case 6:
                                System.out.println("Volviendo...");
                                break;

                            default:
                                System.out.println("Opcion invalida.");
                        }

                        // Reconstruir NotificacionService si cambió la config
                        if (opcionWA == 1 || opcionWA == 2 || opcionWA == 3) {
                            WhatsAppService waActualizado =
                                auth.buildWhatsAppService(correoActivo);
                            if (waActualizado != null) {
                                notificaciones = new NotificacionService(waActualizado);
                                System.out.println("Notificaciones WhatsApp activas.");
                            }
                        }

                    } while (opcionWA != 6);
                    break;

                // ── ELIMINAR CUENTA ───────────────────────────────────────────
                case 9:
                    System.out.println("Ingrese su contraseña para confirmar: ");
                    String passElim = teclado.next();
                    String resultado = auth.eliminar(correoActivo, passElim);
                    System.out.println(resultado);
                    if (resultado.equals("Usuario eliminado exitosamente.")) {
                        new java.io.File(archivoUsuario(correoActivo)).delete();
                        System.out.println("Hasta luego!");
                        System.exit(0);
                    }
                    break;

                // ── SALIR ─────────────────────────────────────────────────────
                case 10:
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

        } while (opcion != 10);
    }

    // ─── Métodos auxiliares ───────────────────────────────────────────────────

    private static LocalDate pedirFecha(Scanner teclado, String mensaje) {
        LocalDate fecha = null;
        do {
            System.out.println(mensaje + ": ");
            try {
                fecha = LocalDate.parse(teclado.next(), FMT_FECHA);
            } catch (DateTimeParseException e) {
                System.out.println("Formato invalido. Use DD/MM/YY. Ej: 25/05/25");
            }
        } while (fecha == null);
        return fecha;
    }

    private static LocalTime pedirHora(Scanner teclado) {
        LocalTime hora = null;
        do {
            System.out.println("Digite la hora (HH:MM, Ej: 09:00): ");
            try {
                hora = LocalTime.parse(teclado.next());
            } catch (DateTimeParseException e) {
                System.out.println("Formato invalido. Use HH:MM");
            }
        } while (hora == null);
        return hora;
    }

    private static int pedirPrioridad(Scanner teclado) {
        int prioridad = 0;
        do {
            System.out.println("Ingrese Prioridad 1(Alta), 2(Media), 3(Baja): ");
            prioridad = teclado.nextInt();
            if (prioridad < 1 || prioridad > 3)
                System.out.println("Opcion invalida. Solo 1, 2 o 3.");
        } while (prioridad < 1 || prioridad > 3);
        return prioridad;
    }

    private static boolean pedirEstado(Scanner teclado) {
        int comparacion = 0;
        do {
            System.out.println("Ingrese estado 1(Completado), 2(Incompleto): ");
            comparacion = teclado.nextInt();
            if (comparacion < 1 || comparacion > 2)
                System.out.println("Opcion invalida. Solo 1 o 2.");
        } while (comparacion < 1 || comparacion > 2);
        return comparacion == 1;
    }

    /**
     * Abre el navegador con WhatsApp Web prellenado con el mensaje
     * de activacion de CallMeBot. El usuario solo presiona ENVIAR.
     */
    private static void abrirActivacionWhatsApp() {
        try {
            String mensaje = "I allow callmebot to send me messages";
            String url = "https://wa.me/34644597832?text="
                + java.net.URLEncoder.encode(mensaje,
                    java.nio.charset.StandardCharsets.UTF_8);

            if (java.awt.Desktop.isDesktopSupported()) {
                java.awt.Desktop.getDesktop().browse(new java.net.URI(url));
                System.out.println("Navegador abierto. Si no se abrio, ve manualmente a:");
            } else {
                System.out.println("Abre este enlace en tu navegador:");
            }
            System.out.println(url);

        } catch (Exception e) {
            System.out.println("No se pudo abrir el navegador.");
            System.out.println(
                "Envia manualmente al +34 644 59 78 32 en WhatsApp:");
            System.out.println("'I allow callmebot to send me messages'");
        }
    }

    private static String archivoUsuario(String correo) {
        return correo.replace("@", "").replace(".", "") + "Eventos";
    }
}