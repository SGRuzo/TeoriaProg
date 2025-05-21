package Collections.Clase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class E1_ConcatenadorListas {

    // Metodo xenérico estático que concatena dúas listas do mesmo tipo
    public static <T> List<T> concatenarListas(List<T> lista1, List<T> lista2) {
        // Creamos unha nova lista onde imos meter todos os elementos
        List<T> resultado = new ArrayList<>();

        // Engadimos todos os elementos da primeira lista
        resultado.addAll(lista1);

        // Engadimos agora os da segunda lista, despois dos anteriores
        resultado.addAll(lista2);

        // Devolvemos a lista final
        return resultado;
    }

    // Metodo main para probar que funciona
    public static void main(String[] args) {

        // Creamos dúas listas de String
        List<String> nomes1 = Arrays.asList("Ana", "Carlos", "Lucía");
        List<String> nomes2 = Arrays.asList("Pedro", "Sofía");

        // Chamamos ao metodo xenérico para concatenar listas de String
        List<String> nomesConcatenados = concatenarListas(nomes1, nomes2);

        // Mostramos o resultado por pantalla
        System.out.println("Lista de nomes concatenada: " + nomesConcatenados);

        // Tamén podemos probar con números (Integer)
        List<Integer> numeros1 = Arrays.asList(1, 2, 3);
        List<Integer> numeros2 = Arrays.asList(4, 5);

        // Concatenamos listas de Integer
        List<Integer> numerosConcatenados = concatenarListas(numeros1, numeros2);

        // Mostramos o resultado
        System.out.println("Lista de números concatenada: " + numerosConcatenados);
    }
}
