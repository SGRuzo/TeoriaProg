package Collections;
/**
 * Mapa de académicos
 * Crear clase Academico con nombre y año de ingreso.
 * Guardarlos en un Map<Character, Academico>.
 * Añadir comprobación para no insertar letras inválidas.
 * Listar académicos ordenados por:
 * Clave (letra).
 * Nombre.
 * Año.
 */

// Importaciones necesarias para el funcionamiento del programa
import java.util.*;         // Importa todas las utilidades de Java (Map, TreeMap, etc.)
import java.util.stream.*;  // Para usar streams en el ordenamiento

/**
 * Clase que representa un Académico con nombre y año de ingreso
 */
class Academico {
    // Campos privados para encapsular los datos del académico
    private String nombre;     // Almacena el nombre del académico
    private int añoIngreso;    // Almacena el año de ingreso (usamos 'año' en lugar de 'anno' para claridad)

    /**
     * Constructor de la clase Academico
     * @param nombre Nombre completo del académico (no puede ser nulo o vacío)
     * @param añoIngreso Año de ingreso (debe ser un valor positivo)
     */
    public Academico(String nombre, int añoIngreso) {
        // Validación de parámetros
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (añoIngreso <= 0) {
            throw new IllegalArgumentException("El año de ingreso debe ser positivo");
        }

        // Asignación de valores a los campos
        this.nombre = nombre.trim();  // Elimina espacios en blanco al inicio/fin
        this.añoIngreso = añoIngreso;
    }

    // Métodos getters para acceder a los campos privados

    /**
     * @return El nombre del académico
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @return El año de ingreso del académico
     */
    public int getAñoIngreso() {
        return añoIngreso;
    }

    /**
     * Representación en String del académico
     * @return String formateada con los datos del académico
     */
    @Override
    public String toString() {
        return nombre + " (Año: " + añoIngreso + ")";
    }
}

/**
 * Clase principal que gestiona el mapa de académicos
 */
public class MapAcademicos {
    // Mapa para almacenar los académicos (clave: Character, valor: Academico)
    private static Map<Character, Academico> mapaAcademicos = new TreeMap<>();
    // Scanner para leer entrada del usuario
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Metodo principal que inicia la aplicación
     * @param args Argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        // Menú principal en bucle hasta que el usuario decida salir
        while (true) {
            System.out.println("\n=== GESTIÓN DE ACADÉMICOS ===");
            System.out.println("1. Añadir académico");
            System.out.println("2. Listar por letra inicial");
            System.out.println("3. Listar por nombre");
            System.out.println("4. Listar por año de ingreso");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");

            // Manejo de la opción del usuario
            try {
                int opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1:
                        añadirAcademico();
                        break;
                    case 2:
                        listarPorClave();
                        break;
                    case 3:
                        listarPorNombre();
                        break;
                    case 4:
                        listarPorAño();
                        break;
                    case 5:
                        System.out.println("Saliendo del sistema...");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Opción no válida. Intente nuevamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número válido.");
            }
        }
    }

    /**
     * Añade un nuevo académico al mapa
     */
    private static void añadirAcademico() {
        System.out.println("\n--- AÑADIR NUEVO ACADÉMICO ---");

        // Solicitar letra inicial
        System.out.print("Ingrese letra inicial (A-Z): ");
        String letraInput = scanner.nextLine().toUpperCase();

        // Validar la letra inicial
        if (letraInput.length() != 1 || !Character.isLetter(letraInput.charAt(0))) {
            System.out.println("Error: Debe ingresar una sola letra de A-Z");
            return;
        }
        char letra = letraInput.charAt(0);

        // Verificar si la letra ya existe en el mapa
        if (mapaAcademicos.containsKey(letra)) {
            System.out.println("Error: Ya existe un académico con esa letra inicial");
            return;
        }

        // Solicitar nombre completo
        System.out.print("Ingrese nombre completo: ");
        String nombre = scanner.nextLine();

        // Solicitar año de ingreso
        System.out.print("Ingrese año de ingreso: ");
        try {
            int año = Integer.parseInt(scanner.nextLine());

            // Crear y agregar el nuevo académico
            mapaAcademicos.put(letra, new Academico(nombre, año));
            System.out.println("Académico añadido correctamente.");
        } catch (NumberFormatException e) {
            System.out.println("Error: El año debe ser un número válido");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Lista los académicos ordenados por su clave (letra inicial)
     */
    private static void listarPorClave() {
        System.out.println("\n--- ACADÉMICOS ORDENADOS POR LETRA INICIAL ---");
        // TreeMap ya mantiene el orden por clave
        mapaAcademicos.forEach((letra, academico) ->
                System.out.println(letra + ": " + academico));
    }

    /**
     * Lista los académicos ordenados alfabéticamente por nombre
     */
    private static void listarPorNombre() {
        System.out.println("\n--- ACADÉMICOS ORDENADOS POR NOMBRE ---");
        mapaAcademicos.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.comparing(Academico::getNombre)))
                .forEach(entry ->
                        System.out.println(entry.getKey() + ": " + entry.getValue()));
    }

    /**
     * Lista los académicos ordenados por año de ingreso
     */
    private static void listarPorAño() {
        System.out.println("\n--- ACADÉMICOS ORDENADOS POR AÑO DE INGRESO ---");
        mapaAcademicos.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.comparingInt(Academico::getAñoIngreso)))
                .forEach(entry ->
                        System.out.println(entry.getKey() + ": " + entry.getValue()));
    }
}