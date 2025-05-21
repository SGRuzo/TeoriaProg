package Collections;
// Importación de clases necesarias para entrada de datos y manejo de listas
import java.util.ArrayList;  // Para usar listas dinámicas
import java.util.List;       // Interfaz List para declarar nuestras listas
import java.util.Scanner;    // Para leer entrada del usuario


/**
 * Clasificar números
 * Pedir números reales hasta escribir 0.
 * Guardar positivos en una lista, negativos en otra.
 * Mostrar las listas y suma de cada una.
 * Eliminar elementos mayores de 10 o menores de -10.
 */

/**
 * Clase principal que clasifica números reales en positivos y negativos
 */
public class ClasificaNum {

    // Constantes para configuración
    private static final double NUMERO_FIN = 0;       // Número que indica fin de entrada
    private static final double LIMITE_SUPERIOR = 10; // Límite superior para filtrado
    private static final double LIMITE_INFERIOR = -10; // Límite inferior para filtrado

    /**
     * Metodo principal que ejecuta el programa
     * @param args Argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        // 1. Crear un objeto Scanner para leer la entrada del usuario
        Scanner scanner = new Scanner(System.in);

        // 2. Crear dos listas: una para números positivos y otra para negativos
        List<Double> numerosPositivos = new ArrayList<>();
        List<Double> numerosNegativos = new ArrayList<>();

        System.out.println("Clasificador de Números Reales");
        System.out.println("Ingrese números (0 para terminar):");

        // 3. Bucle para leer números hasta que se ingrese 0
        while (true) {
            System.out.print("Ingrese un número real: ");

            // Validar que la entrada sea un número real
            while (!scanner.hasNextDouble()) {
                System.out.println("Error: Debe ingresar un número real.");
                System.out.print("Ingrese un número real: ");
                scanner.next(); // Limpiar el valor incorrecto
            }

            // Leer el número ingresado por el usuario
            double numero = scanner.nextDouble();

            // Verificar si es el número de fin (0)
            if (numero == NUMERO_FIN) {
                break; // Salir del bucle
            }

            // 4. Clasificar el número en la lista correspondiente
            if (numero > 0) {
                numerosPositivos.add(numero);
            } else {
                numerosNegativos.add(numero);
            }
        }

        // 5. Mostrar las listas originales y sus sumas
        System.out.println("\n=== LISTAS ORIGINALES ===");
        mostrarListaYSuma("Positivos", numerosPositivos);
        mostrarListaYSuma("Negativos", numerosNegativos);

        // 6. Eliminar elementos mayores de 10 o menores de -10
        filtrarLista(numerosPositivos, LIMITE_SUPERIOR, true);
        filtrarLista(numerosNegativos, LIMITE_INFERIOR, false);

        // 7. Mostrar las listas filtradas y sus nuevas sumas
        System.out.println("\n=== LISTAS FILTRADAS ===");
        mostrarListaYSuma("Positivos (filtrados)", numerosPositivos);
        mostrarListaYSuma("Negativos (filtrados)", numerosNegativos);

        // 8. Cerrar el scanner para liberar recursos
        scanner.close();
    }

    /**
     * Metodo para mostrar una lista y calcular su suma
     * @param nombre Nombre descriptivo de la lista
     * @param lista Lista de números a mostrar
     */
    private static void mostrarListaYSuma(String nombre, List<Double> lista) {
        System.out.println("\nLista de " + nombre + ":");

        // Mostrar cada elemento de la lista
        for (Double num : lista) {
            System.out.printf("%.2f ", num); // Formatear a 2 decimales
        }

        // Calcular y mostrar la suma de los elementos
        double suma = lista.stream().mapToDouble(Double::doubleValue).sum();
        System.out.printf("\nSuma de %s: %.2f\n", nombre, suma);
        System.out.println("Cantidad: " + lista.size());
    }

    /**
     * Metodo para filtrar una lista según límites
     * @param lista Lista a filtrar
     * @param limite Valor límite para el filtrado
     * @param esPositivo Indica si se filtra por límite superior (true) o inferior (false)
     */
    private static void filtrarLista(List<Double> lista, double limite, boolean esPositivo) {
        // Usar removeIf con una expresión lambda para eliminar elementos según condición
        if (esPositivo) {
            // Eliminar números mayores que el límite superior (10)
            lista.removeIf(num -> num > limite);
        } else {
            // Eliminar números menores que el límite inferior (-10)
            lista.removeIf(num -> num < limite);
        }
    }
}