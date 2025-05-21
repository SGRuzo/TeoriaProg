package Collections.Clase;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class E4_NumerosRealesPorColecciones {

    public static void main(String[] args) {
        // Crear scanner para leer números desde la consola
        Scanner sc = new Scanner(System.in);

        // Crear dos listas: una para números positivos y otra para negativos
        List<Double> positivos = new ArrayList<>();
        List<Double> negativos = new ArrayList<>();

        System.out.println("Introduce números reales (0 para terminar):");

        // Leer números hasta que se introduzca un 0
        while (true) {
            double num = sc.nextDouble(); // Leer número

            if (num == 0) {
                // Si el número es 0, salir del bucle y dejar de pedir más números
                break;
            }

            if (num > 0) {
                // Si el número es positivo, añadirlo a la lista de positivos
                positivos.add(num);
            } else {
                // Si es negativo, añadirlo a la lista de negativos
                negativos.add(num);
            }
        }

        // Mostrar las dos listas antes de eliminar números grandes
        System.out.println("\nNúmeros positivos:");
        System.out.println(positivos);

        System.out.println("Números negativos:");
        System.out.println(negativos);

        // Calcular y mostrar la suma de los números positivos
        double sumaPositivos = 0;
        for (double p : positivos) {
            sumaPositivos += p;
        }
        System.out.println("Suma de positivos: " + sumaPositivos);

        // Calcular y mostrar la suma de los números negativos
        double sumaNegativos = 0;
        for (double n : negativos) {
            sumaNegativos += n;
        }
        System.out.println("Suma de negativos: " + sumaNegativos);

        // Eliminar de positivos los números mayores que 10 usando iterador
        Iterator<Double> itPos = positivos.iterator();
        while (itPos.hasNext()) {
            double val = itPos.next();
            if (val > 10) {
                itPos.remove();
            }
        }

        // Eliminar de negativos los números menores que -10 usando iterador
        Iterator<Double> itNeg = negativos.iterator();
        while (itNeg.hasNext()) {
            double val = itNeg.next();
            if (val < -10) {
                itNeg.remove();
            }
        }

        // Mostrar las listas después de la eliminación
        System.out.println("\nNúmeros positivos después de eliminar > 10:");
        System.out.println(positivos);

        System.out.println("Números negativos después de eliminar < -10:");
        System.out.println(negativos);

        // Cerrar el scanner para liberar recursos
        sc.close();
    }
}
