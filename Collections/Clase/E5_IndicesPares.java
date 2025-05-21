package Collections.Clase;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class E5_IndicesPares {

    public static void main(String[] args) {
        // Crear un Scanner para leer datos desde el teclado
        Scanner sc = new Scanner(System.in);

        // Crear una lista para guardar números enteros positivos
        List<Integer> positivos = new ArrayList<>();

        System.out.println("Introduce números enteros (introduce -1 para terminar):");

        while (true) {
            int num = sc.nextInt(); // Leer un número entero desde la consola

            if (num == -1) {
                // Si el número es -1, salir del bucle y dejar de pedir números
                break;
            }

            if (num > 0) {
                // Si el número es positivo, añadirlo a la lista
                positivos.add(num);
            }
            // Si es 0 o negativo (excepto -1), no se guarda
        }

        System.out.println("\nNúmeros en índices pares multiplicados por 100:");

        // Recorrer la lista usando índices para acceder a posiciones pares
        for (int i = 0; i < positivos.size(); i++) {
            if (i % 2 == 0) {
                // Si el índice es par (0, 2, 4, ...) mostramos el número multiplicado por 100
                int valorMultiplicado = positivos.get(i) * 100;
                System.out.println("Índice " + i + ": " + valorMultiplicado);
            }
        }

        // Cerrar el scanner para liberar recursos
        sc.close();
    }
}
