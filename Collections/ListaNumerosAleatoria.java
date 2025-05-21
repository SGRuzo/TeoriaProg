package Collections;

// Importación de clases necesarias
import java.util.ArrayList;  // Para usar la estructura de datos List
import java.util.List;       // Interfaz List para declarar nuestra lista
import java.util.Random;     // Para generar números aleatorios


/**
 * COLECCIONES
 * Lista aleatoria de números
 * Insertar 100 números aleatorios (1 a 10) en una lista.
 * Eliminar los que valgan 5 y 7.
 * Mostrar la lista antes y después.
 */

/**
 * Clase principal que demuestra el manejo de una lista de números aleatorios
 */
public class ListaNumerosAleatoria {

    // Constantes para configuración
    private static final int CANTIDAD_NUMEROS = 100; // Total de números a generar
    private static final int MIN_NUMERO = 1;         // Valor mínimo del rango aleatorio
    private static final int MAX_NUMERO = 10;        // Valor máximo del rango aleatorio
    private static final int NUMERO_ELIMINAR_1 = 5;  // Primer número a eliminar
    private static final int NUMERO_ELIMINAR_2 = 7;  // Segundo número a eliminar

    /**
     * Metodo principal que ejecuta el programa
     * @param args Argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        // 1. Crear una lista para almacenar los números enteros
        List<Integer> numeros = new ArrayList<>();

        // 2. Crear un objeto Random para generar números aleatorios
        Random random = new Random();

        // 3. Generar y agregar 100 números aleatorios a la lista
        for (int i = 0; i < CANTIDAD_NUMEROS; i++) {
            // Genera un número aleatorio entre MIN_NUMERO y MAX_NUMERO (inclusive)
            int numeroAleatorio = random.nextInt(MAX_NUMERO) + MIN_NUMERO;
            // Agrega el número generado a la lista
            numeros.add(numeroAleatorio);
        }

        // 4. Mostrar la lista original
        System.out.println("=== LISTA ORIGINAL ===");
        mostrarLista(numeros);

        // 5. Eliminar todos los números 5 y 7 de la lista
        // Usamos removeIf con una expresión lambda para eliminar múltiples valores
        numeros.removeIf(num -> num == NUMERO_ELIMINAR_1 || num == NUMERO_ELIMINAR_2);

        // 6. Mostrar la lista modificada
        System.out.println("\n=== LISTA MODIFICADA (sin " + NUMERO_ELIMINAR_1 + " y " + NUMERO_ELIMINAR_2 + ") ===");
        mostrarLista(numeros);
    }

    /**
     * Metodo para mostrar los elementos de una lista
     * @param lista Lista de números a mostrar
     */
    private static void mostrarLista(List<Integer> lista) {
        // Contador para organizar la salida en filas
        int contador = 0;

        // Recorre todos los elementos de la lista
        for (Integer numero : lista) {
            // Imprime cada número seguido de un espacio
            System.out.print(numero + " ");
            contador++;

            // Cada 10 números, hace un salto de línea para mejor legibilidad
            if (contador % 10 == 0) {
                System.out.println();
            }
        }

        // Si la última línea no está completa, añade un salto de línea final
        if (contador % 10 != 0) {
            System.out.println();
        }

        // Muestra el total de números en la lista
        System.out.println("Total números: " + lista.size());
    }
}