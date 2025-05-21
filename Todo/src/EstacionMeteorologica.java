
/**
 * Estación meteorológica
 * Clase RexistroTemperatura:
 * Temperatura y hora del sistema.
 * El programa permite:
 * Añadir lecturas.
 * Listarlas.
 * Mostrar máx, mín y media.
 * Guardar en rexistros_yyyyMMdd.dat.
 * Usa:
 * Fechas.
 * Validaciones.
 * Fichero binario.
 * List<RexistroTemperatura>.
 */

// Importaciones necesarias para el funcionamiento del programa
import java.io.*;                 // Para operaciones de entrada/salida
        import java.time.LocalDateTime;   // Para manejar fecha y hora
import java.time.format.DateTimeFormatter; // Para formatear fechas
import java.util.ArrayList;       // Para usar List
import java.util.List;            // Interfaz List
import java.util.Scanner;         // Para leer entrada del usuario
import java.util.stream.Collectors; // Para operaciones con streams

/**
 * Clase que representa un registro de temperatura con su fecha/hora
 */
class RexistroTemperatura implements Serializable {
    // Versión de serialización para control de compatibilidad
    private static final long serialVersionUID = 1L;

    // Campos privados para encapsular los datos del registro
    private double temperatura;      // Valor de la temperatura registrada
    private LocalDateTime dataHora;  // Fecha y hora del registro

    // Formato para mostrar fechas (dd/MM/yyyy HH:mm:ss)
    private static final DateTimeFormatter FORMATO_DATA =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    /**
     * Constructor de la clase RexistroTemperatura
     * @param temperatura Valor de la temperatura
     * @param dataHora Fecha y hora del registro
     */
    public RexistroTemperatura(double temperatura, LocalDateTime dataHora) {
        this.temperatura = temperatura;
        this.dataHora = dataHora;
    }

    // Métodos getters para acceder a los campos privados

    public double getTemperatura() {
        return temperatura;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    /**
     * Representación en String del registro
     * @return String formateada con los datos del registro
     */
    @Override
    public String toString() {
        return String.format("Temperatura: %.1f°C - Data/Hora: %s",
                temperatura, dataHora.format(FORMATO_DATA));
    }
}

/**
 * Clase principal que gestiona la estación meteorológica
 */
public class EstacionMeteorologica {
    // Lista para almacenar todos los registros de temperatura
    private static List<RexistroTemperatura> rexistros = new ArrayList<>();
    // Scanner para leer entrada del usuario
    private static final Scanner scanner = new Scanner(System.in);
    // Formato para el nombre del archivo (yyyyMMdd)
    private static final DateTimeFormatter FORMATO_ARCHIVO =
            DateTimeFormatter.ofPattern("yyyyMMdd");

    /**
     * Metodo principal que inicia la aplicación
     * @param args Argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        // Cargar registros al iniciar
        cargarRexistros();

        // Menú principal en bucle hasta que el usuario decida salir
        while (true) {
            System.out.println("\n=== ESTACIÓN METEOROLÓXICA ===");
            System.out.println("1. Engadir lectura de temperatura");
            System.out.println("2. Listar todas as lecturas");
            System.out.println("3. Mostrar estatísticas (máx, mín, media)");
            System.out.println("4. Gardar e saír");
            System.out.print("Seleccione unha opción: ");

            // Manejo de la opción del usuario
            try {
                int opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1:
                        engadirLectura();
                        break;
                    case 2:
                        listarLecturas();
                        break;
                    case 3:
                        mostrarEstatisticas();
                        break;
                    case 4:
                        gardarRexistros();
                        System.out.println("Gardando datos e saíndo do sistema...");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Opción non válida. Intente novamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número válido.");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    /**
     * Añade una nueva lectura de temperatura
     */
    private static void engadirLectura() {
        System.out.println("\n--- ENGADIR LECTURA ---");

        // Solicitar temperatura
        System.out.print("Introduza a temperatura en °C: ");
        try {
            double temperatura = Double.parseDouble(scanner.nextLine());

            // Crear registro con temperatura y fecha/hora actual
            RexistroTemperatura rexistro = new RexistroTemperatura(
                    temperatura, LocalDateTime.now());

            // Añadir a la lista
            rexistros.add(rexistro);
            System.out.println("Lectura engadida correctamente.");
        } catch (NumberFormatException e) {
            System.out.println("Error: A temperatura debe ser un número válido.");
        }
    }

    /**
     * Lista todas las lecturas registradas
     */
    private static void listarLecturas() {
        System.out.println("\n--- LISTADO DE LECTURAS ---");

        if (rexistros.isEmpty()) {
            System.out.println("Non hay lecturas rexistradas.");
            return;
        }

        // Mostrar todas las lecturas numeradas
        for (int i = 0; i < rexistros.size(); i++) {
            System.out.println((i + 1) + ". " + rexistros.get(i));
        }
    }

    /**
     * Muestra estadísticas de las temperaturas (máx, mín, media)
     */
    private static void mostrarEstatisticas() {
        System.out.println("\n--- ESTATÍSTICAS ---");

        if (rexistros.isEmpty()) {
            System.out.println("Non hay lecturas para calcular estatísticas.");
            return;
        }

        // Calcular estadísticas usando streams
        double max = rexistros.stream()
                .mapToDouble(RexistroTemperatura::getTemperatura)
                .max()
                .getAsDouble();

        double min = rexistros.stream()
                .mapToDouble(RexistroTemperatura::getTemperatura)
                .min()
                .getAsDouble();

        double media = rexistros.stream()
                .mapToDouble(RexistroTemperatura::getTemperatura)
                .average()
                .getAsDouble();

        // Mostrar resultados formateados
        System.out.printf("Temperatura máxima: %.1f°C\n", max);
        System.out.printf("Temperatura mínima: %.1f°C\n", min);
        System.out.printf("Temperatura media: %.1f°C\n", media);
    }

    /**
     * Guarda los registros en un archivo binario con nombre basado en la fecha actual
     */
    private static void gardarRexistros() {
        // Generar nombre de archivo con la fecha actual (yyyyMMdd)
        String nomeArquivo = "rexistros_" + LocalDateTime.now().format(FORMATO_ARCHIVO) + ".dat";

        // Try-with-resources para garantizar que el ObjectOutputStream se cierre
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(nomeArquivo))) {
            oos.writeObject(rexistros);
            System.out.println("Rexistros gardados en " + nomeArquivo);
        } catch (IOException e) {
            System.err.println("Error ao gardar os rexistros: " + e.getMessage());
        }
    }

    /**
     * Carga los registros desde el archivo binario del día actual
     */
    @SuppressWarnings("unchecked")
    private static void cargarRexistros() {
        // Generar nombre de archivo con la fecha actual (yyyyMMdd)
        String nomeArquivo = "rexistros_" + LocalDateTime.now().format(FORMATO_ARCHIVO) + ".dat";
        File arquivo = new File(nomeArquivo);

        // Verificar si el archivo existe antes de intentar leerlo
        if (!arquivo.exists()) {
            System.out.println("Non se encontró arquivo de rexistros. Comezando con lista baleira.");
            return;
        }

        // Try-with-resources para garantizar que el ObjectInputStream se cierre
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(nomeArquivo))) {
            rexistros = (List<RexistroTemperatura>) ois.readObject();
            System.out.println("Rexistros cargados desde " + nomeArquivo);
        } catch (IOException e) {
            System.err.println("Error ao cargar os rexistros: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Error: Clase non encontrada ao cargar rexistros");
        }
    }
}