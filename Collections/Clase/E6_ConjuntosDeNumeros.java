package Collections.Clase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class E6_ConjuntosDeNumeros {

    public static void main(String[] args) {
        // Crear una lista para guardar números enteros (entre 1 y 10, con repeticiones)
        List<Integer> listaNumeros = new ArrayList<>();

        // Añadimos números, algunos repetidos
        listaNumeros.add(1);
        listaNumeros.add(2);
        listaNumeros.add(3);
        listaNumeros.add(2);
        listaNumeros.add(4);
        listaNumeros.add(5);
        listaNumeros.add(5);
        listaNumeros.add(6);
        listaNumeros.add(7);
        listaNumeros.add(8);
        listaNumeros.add(7);
        listaNumeros.add(9);
        listaNumeros.add(10);
        listaNumeros.add(1);

        // Mostrar la lista original
        System.out.println("Lista original (con posibles repeticiones):");
        System.out.println(listaNumeros);

        // Crear un conjunto para elementos sin repetir (conjunto con todos los elementos únicos de la lista)
        Set<Integer> conjuntoSinRepetir = new HashSet<>(listaNumeros);
        System.out.println("\nConjunto sin repetir (todos los elementos únicos):");
        System.out.println(conjuntoSinRepetir);

        // Crear conjuntos para elementos repetidos y únicos
        Set<Integer> repetidos = new HashSet<>();   // Para guardar los números que aparecen más de una vez
        Set<Integer> unicos = new HashSet<>();      // Para guardar los números que aparecen una sola vez

        // Para contar cuántas veces aparece cada número, usamos un Set temporal para detectar repeticiones
        Set<Integer> vistos = new HashSet<>();

        // Recorremos la lista para identificar repetidos y únicos
        for (Integer num : listaNumeros) {
            if (!vistos.contains(num)) {
                // Si el número no está en 'vistos', es la primera vez que aparece
                vistos.add(num);
                unicos.add(num);  // Lo añadimos como único por ahora
            } else {
                // Si ya estaba en 'vistos', es repetido
                repetidos.add(num);
                unicos.remove(num);  // Ya no es único porque aparece más de una vez
            }
        }

        // Mostrar el conjunto de números repetidos
        System.out.println("\nConjunto de números repetidos:");
        System.out.println(repetidos);

        // Mostrar el conjunto de números únicos (aparecen sólo una vez)
        System.out.println("\nConjunto de números únicos (aparecen sólo una vez):");
        System.out.println(unicos);
    }
}
