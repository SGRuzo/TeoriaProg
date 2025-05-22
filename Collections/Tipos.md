
## **1. Ejercicio con `ArrayList` (tipo `List`)**

**Objetivo:** Guardar una lista de nombres, eliminar duplicados manualmente y mostrarla.

```java
import java.util.ArrayList;

public class ListaNombres {
    public static void main(String[] args) {
        // Creamos un ArrayList para guardar nombres
        ArrayList<String> nombres = new ArrayList<>();

        // Añadimos varios nombres, algunos duplicados
        nombres.add("Ana");
        nombres.add("Luis");
        nombres.add("Ana"); // duplicado
        nombres.add("Carlos");
        nombres.add("Luis"); // duplicado

        // Mostramos la lista original
        System.out.println("Lista original:");
        for (String nombre : nombres) {
            System.out.println(nombre);
        }

        // Eliminamos duplicados manualmente
        ArrayList<String> sinDuplicados = new ArrayList<>();
        for (String nombre : nombres) {
            if (!sinDuplicados.contains(nombre)) {
                sinDuplicados.add(nombre);
            }
        }

        // Mostramos la lista sin duplicados
        System.out.println("\nLista sin duplicados:");
        for (String nombre : sinDuplicados) {
            System.out.println(nombre);
        }
    }
}
```

**Explicación breve:**

* `ArrayList` guarda elementos ordenados y permite duplicados.
* Creamos otra lista para evitar duplicados revisando si ya existe el elemento antes de añadirlo.

---

## **2. Ejercicio con `HashSet` (tipo `Set`)**

**Objetivo:** Guardar nombres únicos directamente.

```java
import java.util.HashSet;

public class ConjuntoNombres {
    public static void main(String[] args) {
        // Un HashSet automáticamente evita duplicados
        HashSet<String> nombres = new HashSet<>();

        // Añadimos varios nombres
        nombres.add("Ana");
        nombres.add("Luis");
        nombres.add("Ana"); // este no se añadirá
        nombres.add("Carlos");

        // Mostramos los elementos del conjunto
        System.out.println("Nombres en el HashSet:");
        for (String nombre : nombres) {
            System.out.println(nombre);
        }
    }
}
```

**Explicación breve:**

* `HashSet` **no permite duplicados**.
* Si intentas añadir un elemento que ya existe, simplemente **lo ignora**.

---

## **3. Ejercicio con `PriorityQueue` (tipo `Queue`)**

**Objetivo:** Simular una cola de tareas con prioridad (menor número = mayor prioridad).

```java
import java.util.PriorityQueue;

public class ColaDeTareas {
    public static void main(String[] args) {
        // Creamos una PriorityQueue de enteros (prioridad menor número)
        PriorityQueue<Integer> tareas = new PriorityQueue<>();

        // Añadimos tareas (los números indican prioridad)
        tareas.add(5);
        tareas.add(1);
        tareas.add(3);

        // Mostramos y eliminamos las tareas según prioridad
        System.out.println("Procesando tareas por prioridad:");
        while (!tareas.isEmpty()) {
            int tarea = tareas.poll(); // elimina y devuelve el más prioritario
            System.out.println("Tarea con prioridad: " + tarea);
        }
    }
}
```

**Explicación breve:**

* `PriorityQueue` ordena automáticamente los elementos.
* `poll()` devuelve y elimina el elemento con **mayor prioridad** (el más pequeño en este caso).

---

## **4. Ejercicio con `ArrayDeque` (tipo `Deque`)**

**Objetivo:** Simular una pila (LIFO) con doble cola.

```java
import java.util.ArrayDeque;

public class PilaConDeque {
    public static void main(String[] args) {
        // Creamos un ArrayDeque que usaremos como pila
        ArrayDeque<String> pila = new ArrayDeque<>();

        // Apilamos elementos (último en entrar, primero en salir)
        pila.push("Uno");
        pila.push("Dos");
        pila.push("Tres");

        // Desapilamos elementos y los mostramos
        System.out.println("Contenido de la pila:");
        while (!pila.isEmpty()) {
            String elemento = pila.pop(); // saca el último elemento añadido
            System.out.println(elemento);
        }
    }
}
```

**Explicación breve:**

* `push()` añade al principio, `pop()` elimina el último añadido.
* `ArrayDeque` es ideal para usarlo como pila o cola.

---

## **5. Ejercicio con `HashMap` (tipo `Map`)**

**Objetivo:** Guardar y mostrar el nombre y la edad de personas.

```java
import java.util.HashMap;

public class MapaDePersonas {
    public static void main(String[] args) {
        // Creamos un HashMap para almacenar pares nombre-edad
        HashMap<String, Integer> personas = new HashMap<>();

        // Añadimos elementos (clave: nombre, valor: edad)
        personas.put("Ana", 25);
        personas.put("Luis", 30);
        personas.put("Carlos", 28);

        // Recorremos el mapa mostrando claves y valores
        System.out.println("Contenido del mapa:");
        for (String nombre : personas.keySet()) {
            Integer edad = personas.get(nombre); // obtenemos la edad por clave
            System.out.println(nombre + " tiene " + edad + " años.");
        }
    }
}
```

**Explicación breve:**

* `put(clave, valor)` añade pares clave-valor.
* `get(clave)` obtiene el valor asociado a la clave.

