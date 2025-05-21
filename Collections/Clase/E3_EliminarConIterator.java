package Collections.Clase;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class E3_EliminarConIterator {

    public static void main(String[] args) {

        // Creamos un objeto Random para generar números aleatorios
        Random random = new Random();

        // Creamos una lista para guardar los 100 números enteros
        List<Integer> numeros = new ArrayList<>();

        // Bucle para insertar 100 números aleatorios entre 1 y 10 (ambos inclusive)
        for (int i = 0; i < 100; i++) {
            int num = random.nextInt(10) + 1; // Genera un número entre 1 y 10
            numeros.add(num); // Añade el número a la lista
        }

        // Mostramos la lista antes de eliminar los elementos
        System.out.println("Lista antes de eliminar 5 y 7:");
        System.out.println(numeros);

        // Creamos un iterador para recorrer la lista y eliminar elementos
        Iterator<Integer> it = numeros.iterator();

        // Mientras haya elementos por recorrer en la lista
        while (it.hasNext()) {
            int valor = it.next(); // Obtenemos el siguiente elemento

            // Si el valor es 5 o 7, lo eliminamos usando el iterador
            if (valor == 5 || valor == 7) {
                it.remove(); // Elimina el elemento actual de la lista
            }
        }

        // Mostramos la lista después de eliminar los números 5 y 7
        System.out.println("\nLista después de eliminar 5 y 7:");
        System.out.println(numeros);
    }
}
