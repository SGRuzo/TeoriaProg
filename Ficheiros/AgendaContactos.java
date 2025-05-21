package Ficheiros;

import java.io.*; // Para operaciones de entrada/salida
import java.util.ArrayList; // Para usar listas dinámicas
import java.util.List; // Para la interfaz List
import java.util.Scanner; // Para leer entrada del usuario

/**
 * Agenda de contactos
 * Crea una clase Contacto (nombre, teléfono, email). Permite:
 * Añadir, editar, borrar contactos.
 * Guardarlos y cargarlos desde un fichero binario.
 * Listar los contactos por consola.
 */


/**
 * Clase que representa un Contacto con nombre, teléfono y email
 * Implementa Serializable para permitir la serialización de objetos
 */
class Contacto implements Serializable {
    // Versión de serialización para control de compatibilidad
    private static final long serialVersionUID = 1L;

    // Campos privados para encapsular los datos del contacto
    private String nombre; // Almacena el nombre del contacto
    private String telefono; // Almacena el número de teléfono
    private String email; // Almacena el correo electrónico

    /**
     * Constructor de la clase Contacto
     * @param nombre Nombre del contacto (no puede ser nulo o vacío)
     * @param telefono Teléfono del contacto (debe contener solo dígitos)
     * @param email Email del contacto (debe tener formato válido)
     * @throws IllegalArgumentException si los datos no son válidos
     */
    public Contacto(String nombre, String telefono, String email) {
        // Validación del nombre, no puede ser nulo ni vacío
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }

        // Validación del teléfono, solo puede contener números
        if (!telefono.matches("\\d+")) {
            throw new IllegalArgumentException("El teléfono solo puede contener dígitos");
        }

        // Validación básica del email, debe contener "@" y "."
        if (!email.contains("@") || !email.contains(".")) {
            throw new IllegalArgumentException("El email no tiene un formato válido");
        }

        // Asignación de valores si pasan las validaciones
        this.nombre = nombre.trim();
        this.telefono = telefono;
        this.email = email;
    }

    // Métodos getters y setters para cada campo

    /**
     * @return el nombre del contacto
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece un nuevo nombre
     * @param nombre Nuevo nombre (no puede ser nulo o vacío)
     */
    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        this.nombre = nombre.trim();
    }

    /**
     * @return el teléfono del contacto
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Establece un nuevo teléfono
     * @param telefono Nuevo teléfono (solo dígitos)
     */
    public void setTelefono(String telefono) {
        if (!telefono.matches("\\d+")) {
            throw new IllegalArgumentException("El teléfono solo puede contener dígitos");
        }
        this.telefono = telefono;
    }

    /**
     * @return el email del contacto
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece un nuevo email
     * @param email Nuevo email (debe tener formato válido)
     */
    public void setEmail(String email) {
        if (!email.contains("@") || !email.contains(".")) {
            throw new IllegalArgumentException("El email no tiene un formato válido");
        }
        this.email = email;
    }

    /**
     * Representación en String del contacto
     * @return String formateado con los datos del contacto
     */
    @Override
    public String toString() {
        return String.format("Nombre: %-20s Teléfono: %-15s Email: %s", nombre, telefono, email);
    }
}

/**
 * Clase que gestiona la agenda de contactos
 */
class Agenda {
    // Lista para almacenar los contactos
    private List<Contacto> contactos;
    // Nombre del archivo para guardar/recuperar los datos
    private static final String ARCHIVO = "agenda.dat";

    /**
     * Constructor de la agenda
     * Intenta cargar contactos desde archivo al iniciar
     */
    public Agenda() {
        contactos = new ArrayList<>();
        cargarDesdeArchivo(); // Intenta cargar contactos existentes
    }

    /**
     * Añade un nuevo contacto a la agenda
     * @param contacto Contacto a añadir
     * @return true si se añadió correctamente, false si ya existía
     */
    public boolean añadirContacto(Contacto contacto) {
        // Verifica si el contacto ya existe (por nombre)
        if (buscarPorNombre(contacto.getNombre()) != null) {
            return false;
        }
        contactos.add(contacto);
        return true;
    }

    /**
     * Edita un contacto existente
     * @param nombre Nombre del contacto a editar
     * @param nuevoTelefono Nuevo teléfono
     * @param nuevoEmail Nuevo email
     * @return true si se editó correctamente, false si no se encontró
     */
    public boolean editarContacto(String nombre, String nuevoTelefono, String nuevoEmail) {
        Contacto contacto = buscarPorNombre(nombre);
        if (contacto == null) {
            return false;
        }

        try {
            contacto.setTelefono(nuevoTelefono);
            contacto.setEmail(nuevoEmail);
            return true;
        } catch (IllegalArgumentException e) {
            System.err.println("Error al editar: " + e.getMessage());
            return false;
        }
    }

    /**
     * Borra un contacto de la agenda
     * @param nombre Nombre del contacto a borrar
     * @return true si se borró correctamente, false si no se encontró
     */
    public boolean borrarContacto(String nombre) {
        Contacto contacto = buscarPorNombre(nombre);
        if (contacto == null) {
            return false;
        }
        return contactos.remove(contacto);
    }

    /**
     * Busca un contacto por nombre
     * @param nombre Nombre a buscar
     * @return El contacto encontrado o null si no existe
     */
    public Contacto buscarPorNombre(String nombre) {
        for (Contacto c : contactos) {
            if (c.getNombre().equalsIgnoreCase(nombre)) {
                return c;
            }
        }
        return null;
    }

    /**
     * Lista todos los contactos en la consola
     */
    public void listarContactos() {
        if (contactos.isEmpty()) {
            System.out.println("No hay contactos en la agenda.");
            return;
        }

        System.out.println("\n--- LISTA DE CONTACTOS ---");
        for (int i = 0; i < contactos.size(); i++) {
            System.out.printf("%d. %s%n", i+1, contactos.get(i));
        }
        System.out.println("--------------------------\n");
    }

    /**
     * Guarda los contactos en un archivo binario
     */
    public void guardarEnArchivo() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO))) {
            oos.writeObject(contactos);
            System.out.println("Agenda guardada correctamente.");
        } catch (IOException e) {
            System.err.println("Error al guardar la agenda: " + e.getMessage());
        }
    }

    /**
     * Carga los contactos desde un archivo binario
     */
    @SuppressWarnings("unchecked")
    private void cargarDesdeArchivo() {
        File file = new File(ARCHIVO);
        if (!file.exists()) {
            return; // No hay archivo para cargar
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARCHIVO))) {
            contactos = (List<Contacto>) ois.readObject();
            System.out.println("Agenda cargada correctamente.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al cargar la agenda: " + e.getMessage());
        }
    }
}

/**
 * Clase principal con el menú interactivo
 */
public class AgendaContactos {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Agenda agenda = new Agenda();
        int opcion;

        do {
            mostrarMenu();
            opcion = obtenerOpcion(scanner);

            switch (opcion) {
                case 1:
                    añadirContacto(agenda, scanner);
                    break;
                case 2:
                    editarContacto(agenda, scanner);
                    break;
                case 3:
                    borrarContacto(agenda, scanner);
                    break;
                case 4:
                    agenda.listarContactos();
                    break;
                case 5:
                    agenda.guardarEnArchivo();
                    break;
                case 6:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (opcion != 6);

        scanner.close();
    }

    /**
     * Muestra el menú de opciones
     */
    private static void mostrarMenu() {
        System.out.println("\n=== AGENDA DE CONTACTOS ===");
        System.out.println("1. Añadir contacto");
        System.out.println("2. Editar contacto");
        System.out.println("3. Borrar contacto");
        System.out.println("4. Listar contactos");
        System.out.println("5. Guardar agenda");
        System.out.println("6. Salir");
        System.out.print("Seleccione una opción: ");
    }

    /**
     * Obtiene la opción del usuario
     * @param scanner Objeto Scanner para leer entrada
     * @return Opción seleccionada (1-6)
     */
    private static int obtenerOpcion(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.print("Por favor ingrese un número (1-6): ");
            scanner.next(); // Limpiar entrada inválida
        }
        return scanner.nextInt();
    }

    /**
     * Proceso para añadir un nuevo contacto
     * @param agenda Agenda donde se añadirá
     * @param scanner Scanner para leer entrada
     */
    private static void añadirContacto(Agenda agenda, Scanner scanner) {
        scanner.nextLine(); // Limpiar buffer

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        try {
            Contacto nuevo = new Contacto(nombre, telefono, email);
            if (agenda.añadirContacto(nuevo)) {
                System.out.println("Contacto añadido correctamente.");
            } else {
                System.out.println("Error: Ya existe un contacto con ese nombre.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error al crear contacto: " + e.getMessage());
        }
    }

    /**
     * Proceso para editar un contacto existente
     * @param agenda Agenda donde se editará
     * @param scanner Scanner para leer entrada
     */
    private static void editarContacto(Agenda agenda, Scanner scanner) {
        scanner.nextLine(); // Limpiar buffer

        System.out.print("Nombre del contacto a editar: ");
        String nombre = scanner.nextLine();

        System.out.print("Nuevo teléfono: ");
        String telefono = scanner.nextLine();

        System.out.print("Nuevo email: ");
        String email = scanner.nextLine();

        if (agenda.editarContacto(nombre, telefono, email)) {
            System.out.println("Contacto editado correctamente.");
        } else {
            System.out.println("Error: No se encontró el contacto.");
        }
    }

    /**
     * Proceso para borrar un contacto
     * @param agenda Agenda donde se borrará
     * @param scanner Scanner para leer entrada
     */
    private static void borrarContacto(Agenda agenda, Scanner scanner) {
        scanner.nextLine(); // Limpiar buffer

        System.out.print("Nombre del contacto a borrar: ");
        String nombre = scanner.nextLine();

        if (agenda.borrarContacto(nombre)) {
            System.out.println("Contacto borrado correctamente.");
        } else {
            System.out.println("Error: No se encontró el contacto.");
        }
    }
}
