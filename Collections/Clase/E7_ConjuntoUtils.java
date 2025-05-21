package Collections.Clase;

import java.util.HashSet;
import java.util.Set;

public class E7_ConjuntoUtils {

    // Metodo estático genérico para unir dos conjuntos de cualquier tipo
    public static <E> Set<E> union(Set<E> conjunto1, Set<E> conjunto2) {
        // Crear un nuevo conjunto donde guardaremos la unión
        Set<E> resultado = new HashSet<>();

        // Añadimos todos los elementos del primer conjunto al resultado
        resultado.addAll(conjunto1);

        // Añadimos todos los elementos del segundo conjunto al resultado
        // Los duplicados se ignoran automáticamente porque es un Set
        resultado.addAll(conjunto2);

        // Devolvemos el conjunto resultante con la unión de ambos
        return resultado;
    }

    // Metodo principal para probar el metodo union
    public static void main(String[] args) {
        // Crear primer conjunto con algunos números
        Set<Integer> conjuntoA = new HashSet<>();
        conjuntoA.add(1);
        conjuntoA.add(2);
        conjuntoA.add(3);

        // Crear segundo conjunto con algunos números, algunos repetidos
        Set<Integer> conjuntoB = new HashSet<>();
        conjuntoB.add(3);
        conjuntoB.add(4);
        conjuntoB.add(5);

        // Mostrar conjuntos originales
        System.out.println("Conjunto A: " + conjuntoA);
        System.out.println("Conjunto B: " + conjuntoB);

        // Llamar al metodo union para unir ambos conjuntos
        Set<Integer> unionConjuntos = union(conjuntoA, conjuntoB);

        // Mostrar el resultado de la unión
        System.out.println("Unión de A y B: " + unionConjuntos);
    }
}
