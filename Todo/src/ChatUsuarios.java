/**
 * Chat de usuarios
 * Clase Mensaje con:
 * Emisor, receptor, texto, fecha.
 * Crear clase Usuario, y gestionar:
 * Envío y recepción de mensajes (guardados en fichero).
 * Filtrar por usuario, palabra clave.
 * Excepciones si el usuario no existe.
 * Mostrar historial por consola.
 */

import java.io.*;
        import java.time.*;
        import java.time.format.*;
        import java.util.*;
        import java.util.stream.*;

/**
 * Clase que representa un mensaje en el chat
 * Implementa Serializable para permitir guardar en archivos binarios
 */
class Mensaje implements Serializable {
    // Versión de serialización para control de compatibilidad
    private static final long serialVersionUID = 1L;

    // Campos privados para encapsular los datos del mensaje
    private String emisor;       // Nombre del usuario que envía el mensaje
    private String receptor;     // Nombre del usuario que recibe el mensaje
    private String texto;        // Contenido del mensaje
    private LocalDateTime fecha; // Fecha y hora del mensaje

    // Formateador para mostrar fechas legibles
    private static final DateTimeFormatter FORMATO_FECHA =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    /**
     * Constructor de la clase Mensaje
     * @param emisor Nombre del emisor (no puede ser nulo o vacío)
     * @param receptor Nombre del receptor (no puede ser nulo o vacío)
     * @param texto Contenido del mensaje (no puede ser nulo)
     */
    public Mensaje(String emisor, String receptor, String texto) {
        // Validación de parámetros
        if (emisor == null || emisor.trim().isEmpty()) {
            throw new IllegalArgumentException("El emisor no puede estar vacío");
        }
        if (receptor == null || receptor.trim().isEmpty()) {
            throw new IllegalArgumentException("El receptor no puede estar vacío");
        }
        if (texto == null) {
            throw new IllegalArgumentException("El texto no puede ser nulo");
        }

        // Asignación de valores
        this.emisor = emisor.trim();
        this.receptor = receptor.trim();
        this.texto = texto;
        this.fecha = LocalDateTime.now(); // Fecha actual al crear el mensaje
    }

    // Métodos getters para acceder a los campos privados
    public String getEmisor() {
        return emisor;
    }

    public String getReceptor() {
        return receptor;
    }

    public String getTexto() {
        return texto;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    /**
     * Representación en String del mensaje
     * @return String formateada con los datos del mensaje
     */
    @Override
    public String toString() {
        return String.format("[%s] De: %s → Para: %s: %s",
                fecha.format(FORMATO_FECHA), emisor, receptor, texto);
    }
}

/**
 * Clase que representa un usuario del sistema de chat
 */
class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nombre; // Nombre único del usuario

    /**
     * Constructor de la clase Usuario
     * @param nombre Nombre del usuario (no puede ser nulo o vacío)
     */
    public Usuario(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        this.nombre = nombre.trim();
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return nombre.equalsIgnoreCase(usuario.nombre);
    }

    @Override
    public int hashCode() {
        return nombre.toLowerCase().hashCode();
    }
}

/**
 * Excepción personalizada para usuarios no encontrados
 */
class UsuarioNoEncontradoException extends Exception {
    public UsuarioNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}

/**
 * Clase principal que gestiona el sistema de chat
 */
public class ChatUsuarios {
    // Lista de usuarios registrados
    private static Set<Usuario> usuarios = new HashSet<>();
    // Lista de todos los mensajes
    private static List<Mensaje> mensajes = new ArrayList<>();
    // Scanner para entrada del usuario
    private static final Scanner scanner = new Scanner(System.in);
    // Archivo para guardar los datos
    private static final String ARCHIVO_DATOS = "chat_data.dat";

    /**
     * Método principal que inicia la aplicación
     */
    public static void main(String[] args) {
        cargarDatos(); // Cargar datos al iniciar

        // Menú principal
        while (true) {
            System.out.println("\n=== SISTEMA DE CHAT ===");
            System.out.println("1. Registrar nuevo usuario");
            System.out.println("2. Enviar mensaje");
            System.out.println("3. Ver mensajes recibidos");
            System.out.println("4. Buscar mensajes por palabra clave");
            System.out.println("5. Mostrar historial completo");
            System.out.println("6. Salir");
            System.out.print("Seleccione opción: ");

            try {
                int opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1: registrarUsuario(); break;
                    case 2: enviarMensaje(); break;
                    case 3: verMensajesRecibidos(); break;
                    case 4: buscarPorPalabraClave(); break;
                    case 5: mostrarHistorial(); break;
                    case 6:
                        guardarDatos();
                        System.out.println("Saliendo del sistema...");
                        return;
                    default:
                        System.out.println("Opción no válida");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un número válido");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    /**
     * Registra un nuevo usuario en el sistema
     */
    private static void registrarUsuario() {
        System.out.print("\nIngrese nombre de usuario: ");
        String nombre = scanner.nextLine();

        try {
            Usuario nuevo = new Usuario(nombre);
            if (usuarios.add(nuevo)) {
                System.out.println("Usuario registrado exitosamente");
            } else {
                System.out.println("El usuario ya existe");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Envía un mensaje entre usuarios
     * @throws UsuarioNoEncontradoException Si el emisor o receptor no existen
     */
    private static void enviarMensaje() throws UsuarioNoEncontradoException {
        System.out.print("\nIngrese su nombre (emisor): ");
        String emisor = scanner.nextLine();

        System.out.print("Ingrese nombre del receptor: ");
        String receptor = scanner.nextLine();

        // Verificar que ambos usuarios existan
        if (!usuarioExiste(emisor)) {
            throw new UsuarioNoEncontradoException("Emisor no encontrado: " + emisor);
        }
        if (!usuarioExiste(receptor)) {
            throw new UsuarioNoEncontradoException("Receptor no encontrado: " + receptor);
        }

        System.out.print("Ingrese el mensaje: ");
        String texto = scanner.nextLine();

        // Crear y guardar el mensaje
        Mensaje mensaje = new Mensaje(emisor, receptor, texto);
        mensajes.add(mensaje);
        System.out.println("Mensaje enviado correctamente");
    }

    /**
     * Verifica si un usuario existe
     * @param nombre Nombre del usuario a buscar
     * @return true si existe, false si no
     */
    private static boolean usuarioExiste(String nombre) {
        return usuarios.contains(new Usuario(nombre));
    }

    /**
     * Muestra los mensajes recibidos por un usuario
     */
    private static void verMensajesRecibidos() {
        System.out.print("\nIngrese su nombre (receptor): ");
        String receptor = scanner.nextLine();

        if (!usuarioExiste(receptor)) {
            System.out.println("Usuario no encontrado");
            return;
        }

        System.out.println("\n=== MENSAJES RECIBIDOS ===");
        mensajes.stream()
                .filter(m -> m.getReceptor().equalsIgnoreCase(receptor))
                .forEach(System.out::println);
    }

    /**
     * Busca mensajes que contengan una palabra clave
     */
    private static void buscarPorPalabraClave() {
        System.out.print("\nIngrese palabra clave a buscar: ");
        String palabra = scanner.nextLine().toLowerCase();

        System.out.println("\n=== RESULTADOS DE BÚSQUEDA ===");
        mensajes.stream()
                .filter(m -> m.getTexto().toLowerCase().contains(palabra))
                .forEach(System.out::println);
    }

    /**
     * Muestra todo el historial de mensajes
     */
    private static void mostrarHistorial() {
        System.out.println("\n=== HISTORIAL COMPLETO ===");
        if (mensajes.isEmpty()) {
            System.out.println("No hay mensajes registrados");
            return;
        }

        // Agrupar mensajes por fecha (día)
        Map<LocalDate, List<Mensaje>> porFecha = mensajes.stream()
                .collect(Collectors.groupingBy(m -> m.getFecha().toLocalDate()));

        // Mostrar ordenado por fecha
        porFecha.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> {
                    System.out.println("\n--- " + entry.getKey() + " ---");
                    entry.getValue().forEach(System.out::println);
                });
    }

    /**
     * Guarda los datos (usuarios y mensajes) en archivo binario
     */
    @SuppressWarnings("unchecked")
    private static void cargarDatos() {
        File archivo = new File(ARCHIVO_DATOS);
        if (!archivo.exists()) return;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARCHIVO_DATOS))) {
            usuarios = (Set<Usuario>) ois.readObject();
            mensajes = (List<Mensaje>) ois.readObject();
            System.out.println("Datos cargados correctamente");
        } catch (Exception e) {
            System.out.println("Error al cargar datos: " + e.getMessage());
        }
    }

    /**
     * Carga los datos desde archivo binario
     */
    private static void guardarDatos() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO_DATOS))) {
            oos.writeObject(usuarios);
            oos.writeObject(mensajes);
            System.out.println("Datos guardados correctamente");
        } catch (Exception e) {
            System.out.println("Error al guardar datos: " + e.getMessage());
        }
    }
}