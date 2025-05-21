package Collections.Clase;

import java.util.HashSet;
import java.util.Set;

public class E8_ConjuntoUtils2 {

    // Metodo estático y genérico que calcula la intersección de dos conjuntos
    public static <E> Set<E> interseccion(Set<E> conxunto1, Set<E> conxunto2) {
        // Creamos un nuevo conjunto con los elementos del primer conjunto
        Set<E> resultado = new HashSet<>(conxunto1);

        // Usamos retainAll para quedarnos solo con los elementos que también están en el segundo conjunto
        resultado.retainAll(conxunto2);

        // Devolvemos el conjunto con los elementos comunes
        return resultado;
    }

    // Metodo principal para probar el metodo interseccion
    public static void main(String[] args) {
        // Creamos el primer conjunto con algunos números
        Set<Integer> conxuntoA = new HashSet<>();
        conxuntoA.add(1);
        conxuntoA.add(2);
        conxuntoA.add(3);
        conxuntoA.add(4);

        // Creamos el segundo conjunto con algunos números, algunos coinciden con el primero
        Set<Integer> conxuntoB = new HashSet<>();
        conxuntoB.add(3);
        conxuntoB.add(4);
        conxuntoB.add(5);
        conxuntoB.add(6);

        // Mostramos los conjuntos originales
        System.out.println("Conxunto A: " + conxuntoA);
        System.out.println("Conxunto B: " + conxuntoB);

        // Llamamos al metodo interseccion para obtener los elementos comunes
        Set<Integer> interseccion = interseccion(conxuntoA, conxuntoB);

        // Mostramos el resultado de la intersección
        System.out.println("Intersección de A e B: " + interseccion);
    }
}
