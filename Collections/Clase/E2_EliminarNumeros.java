package Collections.Clase;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class E2_EliminarNumeros {

    public static void main(String[] args) {

        // Creamos un objeto Random para generar números aleatorios
        Random random = new Random();

        // Creamos una lista para guardar los 100 números enteros
        List<Integer> numeros = new ArrayList<>();

        // Bucle para insertar 100 números aleatorios entre 1 y 10
        for (int i = 0; i < 100; i++) {
            // Genera un número entre 1 y 10 (inclusive)
            int num = random.nextInt(10) + 1;
            numeros.add(num); // Añade el número a la lista
        }

        // Mostramos la lista antes de eliminar elementos
        System.out.println("Lista antes de eliminar 5 y 7:");
        System.out.println(numeros);

        // Usamos un Iterator para eliminar elementos mientras recorremos la lista
        Iterator<Integer> it = numeros.iterator();

        while (it.hasNext()) {
            int valor = it.next(); // Obtenemos el siguiente elemento de la lista

            // Comprobamos si el valor es 5 o 7
            if (valor == 5 || valor == 7) {
                it.remove(); // Eliminamos ese elemento de la lista
            }
        }

        // Mostramos la lista después de eliminar los números 5 y 7
        System.out.println("\nLista después de eliminar 5 y 7:");
        System.out.println(numeros);
    }
}
