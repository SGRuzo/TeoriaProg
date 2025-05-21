package Ficheiros;
/**
 * Contador de palabras
 * Pedir un fichero .txt.
 * Leerlo y contar cuántas veces aparece cada palabra (ignorando mayúsculas, tildes y signos).
 * Guardar el resultado en resumen_palabras.txt.
 */

// Importación de clases necesarias para entrada/salida y manejo de archivos
import java.io.BufferedReader;   // Para leer texto de un archivo eficientemente
import java.io.BufferedWriter;   // Para escribir texto en un archivo eficientemente
import java.io.File;            // Para manejar operaciones con archivos
import java.io.FileReader;      // Para leer caracteres de un archivo
import java.io.FileWriter;      // Para escribir caracteres en un archivo
import java.io.IOException;     // Para manejar excepciones de entrada/salida
// Importación de clases para colecciones y utilidades
import java.util.HashMap;       // Para almacenar palabras y sus conteos (clave-valor)
import java.util.Map;          // Interfaz para estructuras de clave-valor
import java.util.Scanner;       // Para leer entrada del usuario
import java.util.regex.Pattern; // Para trabajar con expresiones regulares



/**
 * Clase principal que implementa el contador de palabras
 */
public class ContadorPalabras {
    // Scanner para leer la entrada del usuario (static para usarlo en varios métodos)
    private static final Scanner scanner = new Scanner(System.in);
    // Patrón de expresión regular para identificar palabras (letras y apóstrofes)
    private static final Pattern PATRON_PALABRA = Pattern.compile("[\\p{L}']+");

    /**
     * Metodo principal que inicia la aplicación
     * @param args Argumentos de la línea de comandos (no se usan)
     */
    public static void main(String[] args) {
        System.out.println("=== CONTADOR DE PALABRAS ===");

        // Solicita al usuario la ruta del archivo a analizar
        String rutaArchivo = solicitarRutaArchivo();

        try {
            // 1. Leer el archivo y contar las palabras
            Map<String, Integer> conteoPalabras = contarPalabrasEnArchivo(rutaArchivo);

            // 2. Mostrar estadísticas básicas
            mostrarEstadisticas(conteoPalabras);

            // 3. Guardar los resultados en un archivo
            guardarResultados(conteoPalabras);

            System.out.println("\nProceso completado exitosamente.");
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            scanner.close();  // Cierra el scanner al finalizar
        }
    }

    /**
     * Solicita al usuario la ruta del archivo .txt a analizar
     * @return Ruta del archivo validada
     */
    private static String solicitarRutaArchivo() {
        String ruta;
        while (true) {
            System.out.print("\nIngrese la ruta del archivo .txt: ");
            ruta = scanner.nextLine().trim();  // Elimina espacios en blanco al inicio/fin

            // Verifica que el archivo exista y tenga extensión .txt
            if (!ruta.endsWith(".txt")) {
                System.out.println("El archivo debe tener extensión .txt");
                continue;
            }

            File archivo = new File(ruta);
            if (!archivo.exists() || !archivo.isFile()) {
                System.out.println("El archivo no existe o no es válido.");
                continue;
            }

            if (archivo.length() == 0) {
                System.out.println("El archivo está vacío.");
                continue;
            }

            return ruta;  // Retorna la ruta válida
        }
    }

    /**
     * Cuenta las palabras en el archivo especificado
     * @param rutaArchivo Ruta del archivo a procesar
     * @return Mapa con palabras como clave y conteo como valor
     * @throws IOException Si ocurre un error al leer el archivo
     */
    private static Map<String, Integer> contarPalabrasEnArchivo(String rutaArchivo) throws IOException {
        // Mapa para almacenar las palabras y sus conteos
        Map<String, Integer> conteo = new HashMap<>();

        // Try-with-resources para garantizar que el BufferedReader se cierre
        try (BufferedReader lector = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;

            // Lee el archivo línea por línea
            while ((linea = lector.readLine()) != null) {
                // Procesa cada palabra de la línea
                procesarLinea(linea, conteo);
            }
        }

        return conteo;
    }

    /**
     * Procesa una línea de texto, extrayendo y contando palabras
     * @param linea Línea de texto a procesar
     * @param conteo Mapa donde se acumulan los conteos
     */
    private static void procesarLinea(String linea, Map<String, Integer> conteo) {
        // Elimina tildes y convierte a minúsculas para normalización
        String lineaNormalizada = normalizarTexto(linea);

        // Usa el patrón de expresión regular para encontrar palabras
        java.util.regex.Matcher matcher = PATRON_PALABRA.matcher(lineaNormalizada);

        // Itera sobre todas las palabras encontradas en la línea
        while (matcher.find()) {
            String palabra = matcher.group();

            // Si la palabra es muy corta (como 'a' o 'y'), la ignoramos
            if (palabra.length() <= 2) continue;

            // Actualiza el conteo en el mapa
            conteo.put(palabra, conteo.getOrDefault(palabra, 0) + 1);
        }
    }

    /**
     * Normaliza el texto eliminando tildes y convirtiendo a minúsculas
     * @param texto Texto a normalizar
     * @return Texto normalizado
     */
    private static String normalizarTexto(String texto) {
        // Convertir a minúsculas
        texto = texto.toLowerCase();

        // Reemplazar caracteres acentuados por sus equivalentes sin acento
        texto = texto.replaceAll("[áàäâ]", "a")
                .replaceAll("[éèëê]", "e")
                .replaceAll("[íìïî]", "i")
                .replaceAll("[óòöô]", "o")
                .replaceAll("[úùüû]", "u");

        // Eliminar signos de puntuación (excepto apóstrofes para palabras como "don't")
        texto = texto.replaceAll("[^\\p{L}' ]", "");

        return texto;
    }

    /**
     * Muestra estadísticas básicas del conteo
     * @param conteo Mapa con los conteos de palabras
     */
    private static void mostrarEstadisticas(Map<String, Integer> conteo) {
        System.out.println("\n--- ESTADÍSTICAS ---");
        System.out.println("Total de palabras únicas: " + conteo.size());

        // Encuentra la palabra más frecuente
        Map.Entry<String, Integer> masFrecuente = null;
        int totalPalabras = 0;

        for (Map.Entry<String, Integer> entrada : conteo.entrySet()) {
            totalPalabras += entrada.getValue();

            if (masFrecuente == null || entrada.getValue() > masFrecuente.getValue()) {
                masFrecuente = entrada;
            }
        }

        System.out.println("Total de palabras (sin contar únicas): " + totalPalabras);
        System.out.printf("Palabra más frecuente: '%s' (%d ocurrencias)%n",
                masFrecuente.getKey(), masFrecuente.getValue());
    }

    /**
     * Guarda los resultados del conteo en un archivo
     * @param conteo Mapa con los conteos de palabras
     * @throws IOException Si ocurre un error al escribir el archivo
     */
    private static void guardarResultados(Map<String, Integer> conteo) throws IOException {
        String nombreArchivoResultado = "resumen_palabras.txt";
        System.out.println("\nGuardando resultados en: " + nombreArchivoResultado);

        // Try-with-resources para garantizar que el BufferedWriter se cierre
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(nombreArchivoResultado))) {
            // Escribe un encabezado en el archivo de resultados
            escritor.write("=== RESUMEN DE CONTEO DE PALABRAS ===\n");
            escritor.write("Palabra\t\t\tOcurrencias\n");
            escritor.write("--------------------------------\n");

            // Ordena las entradas por conteo descendente
            conteo.entrySet().stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                    .forEach(entrada -> {
                        try {
                            // Escribe cada palabra y su conteo en una línea
                            escritor.write(String.format("%-20s\t%d%n",
                                    entrada.getKey(), entrada.getValue()));
                        } catch (IOException e) {
                            System.err.println("Error al escribir resultados: " + e.getMessage());
                        }
                    });

            escritor.write("\n=== FIN DEL RESUMEN ===");
        }
    }
}