package Collections;
/**
 * Unir e intersectar conjuntos
 * Crear dos Set<Integer> con valores.
 * Crear métodos estáticos para:
 * Unión.
 * Intersección.
 * Mostrar resultados por consola.
 */

// Importaciones necesarias para el manejo de conjuntos
import java.util.HashSet;   // Implementación de Set basada en tabla hash
import java.util.Set;       // Interfaz principal para trabajar con conjuntos
import java.util.Arrays;    // Para inicializar conjuntos fácilmente


/**
 * Clase que demuestra operaciones básicas con conjuntos: unión e intersección
 */
public class UnirIntersectar {

    /**
     * Metodo principal que ejecuta la demostración
     * @param args Argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        // Crear dos conjuntos de números enteros con valores iniciales
        Set<Integer> conjuntoA = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5));
        Set<Integer> conjuntoB = new HashSet<>(Arrays.asList(4, 5, 6, 7, 8));

        // Mostrar los conjuntos originales
        System.out.println("Conjunto A: " + conjuntoA);
        System.out.println("Conjunto B: " + conjuntoB);

        // Calcular y mostrar la unión de los conjuntos
        Set<Integer> union = unionConjuntos(conjuntoA, conjuntoB);
        System.out.println("\nUnión de A y B: " + union);

        // Calcular y mostrar la intersección de los conjuntos
        Set<Integer> interseccion = interseccionConjuntos(conjuntoA, conjuntoB);
        System.out.println("Intersección de A y B: " + interseccion);
    }

    /**
     * Calcula la unión de dos conjuntos de enteros
     * @param conjuntoA Primer conjunto
     * @param conjuntoB Segundo conjunto
     * @return Nuevo conjunto conteniendo todos los elementos de ambos conjuntos
     */
    public static Set<Integer> unionConjuntos(Set<Integer> conjuntoA, Set<Integer> conjuntoB) {
        // Crear un nuevo conjunto para no modificar los originales
        Set<Integer> resultado = new HashSet<>();

        // Agregar todos los elementos del primer conjunto
        resultado.addAll(conjuntoA);

        // Agregar todos los elementos del segundo conjunto
        resultado.addAll(conjuntoB);

        // La unión automáticamente elimina duplicados por naturaleza de Set
        return resultado;
    }

    /**
     * Calcula la intersección de dos conjuntos de enteros
     * @param conjuntoA Primer conjunto
     * @param conjuntoB Segundo conjunto
     * @return Nuevo conjunto conteniendo solo los elementos comunes
     */
    public static Set<Integer> interseccionConjuntos(Set<Integer> conjuntoA, Set<Integer> conjuntoB) {
        // Crear un nuevo conjunto para no modificar los originales
        Set<Integer> resultado = new HashSet<>();

        // Iterar sobre los elementos del primer conjunto
        for (Integer elemento : conjuntoA) {
            // Si el elemento también está en el segundo conjunto, agregarlo al resultado
            if (conjuntoB.contains(elemento)) {
                resultado.add(elemento);
            }
        }

        return resultado;
    }
}