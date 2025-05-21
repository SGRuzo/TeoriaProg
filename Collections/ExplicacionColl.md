## 📋 Índice

1. [🔍 Introducción a Collections](#introducción-a-collections)
2. [🗂️ La interfaz `Collection`](#la-interfaz-collection)
3. [📦 `Set`](#set)
4. [📋 `List`](#list)
5. [⚙️ Clase utilitaria `Collections`](#clase-utilitaria-collections)
6. [🗺️ `Map`](#map)
7. [📝 Ejercicios prácticos](#ejercicios-prácticos)
8. [💡 Consejos y mejores prácticas](#consejos-y-mejores-prácticas)

---

## 🔍 Introducción a Collections

Las **Collections** en Java son **marcos de trabajo** (frameworks) que agrupan interfaces, implementaciones y utilidades para **almacenar**, **gestionar** y **operar** con grupos de objetos.

* **¿Por qué usar Collections?**

    1. **Reutilización**: no reinventas la rueda.
    2. **Consistencia**: todas comparten métodos comunes (`add`, `remove`, `size`…).
    3. **Flexibilidad**: tienes muchas implementaciones según tus necesidades (arrays dinámicos, listas enlazadas, conjuntos, mapas…).

* **Analogía de pastelería**

    * Imagina tu pastelería con:

        * **Ingredientes** (harina, azúcar…) → **listas** o **sets**.
        * **Recetas** → colecciones ordenadas de pasos.
        * **Stock** → mapas clave–valor (ingrediente → cantidad).

---

## 🗂️ La interfaz `Collection`

`Collection<E>` es la **raíz** del framework. Define el **contrato** que comparten todos los tipos de colecciones.

```java
public interface Collection<E> extends Iterable<E> {
    boolean add(E e);           // 1
    boolean remove(Object o);   // 2
    int size();                 // 3
    boolean contains(Object o); // 4
    void clear();               // 5
    // …otros métodos…
}
```

1. `add(E e)`:

    * **Qué hace**: intenta añadir el elemento `e`.
    * **Devuelve**: `true` si cambia la colección (añadido con éxito).
    * **Nota**: algunas implementaciones devuelven siempre `true` (`List`), otras pueden rechazar duplicados (`Set`).

2. `remove(Object o)`:

    * **Qué hace**: elimina la primera aparición de `o` (según `equals`).
    * **Devuelve**: `true` si se encontró y eliminó.

3. `size()`:

    * **Qué hace**: cuenta cuántos elementos hay.
    * **Ejemplo**: si has añadido 3 ingredientes, `size()` es 3.

4. `contains(Object o)`:

    * **Qué hace**: comprueba si existe un elemento igual a `o`.
    * **Uso**: validar si un ingrediente ya está en la lista.

5. `clear()`:

    * **Qué hace**: elimina todos los elementos, dejando la colección vacía.

### 🍰 Ejemplo de pastelería con comentarios línea a línea

```java
// 1. Importamos la implementación ArrayList, que implementa Collection<E>
import java.util.ArrayList;      
import java.util.Collection;      

public class Pasteleria {
    public static void main(String[] args) {
        // 2. Declaramos una referencia a Collection<String>:
        //    - Collection<String>: colección de Strings (nombres de ingredientes)
        //    - new ArrayList<>(): creamos un ArrayList concreto
        Collection<String> ingredientes = new ArrayList<>(); 

        // 3. Añadimos ingredientes uno a uno
        ingredientes.add("Harina");   // añade "Harina", devuelve true  
        ingredientes.add("Azúcar");   // añade "Azúcar" 
        ingredientes.add("Huevos");   // añade "Huevos"

        // 4. Imprimimos el tamaño actual 
        System.out.println("Tenemos " + ingredientes.size() + " ingredientes.");
        //    → “Tenemos 3 ingredientes.”

        // 5. Comprobamos si contiene “Levadura”
        boolean hayLevadura = ingredientes.contains("Levadura");
        System.out.println("¿Hay levadura? " + hayLevadura);
        //    → “¿Hay levadura? false”

        // 6. Eliminamos “Azúcar”
        ingredientes.remove("Azúcar"); // quita el elemento, devuelve true

        // 7. Imprimimos la colección tras eliminar
        System.out.println("Ingredientes ahora: " + ingredientes);
        //    → “[Harina, Huevos]”
    }
}
```

> **Punto clave**: `Collection` no define orden ni duplicados; cada subinterfaz (“List”, “Set”…) añade semántica extra.

---

## 📦 `Set`

Un **Set** es una colección que **no permite duplicados**. Ideal para listas únicas:

```java
public interface Set<E> extends Collection<E> {
    // No añade métodos nuevos, la diferencia está en la semántica de permitir o no duplicados.
}
```

### Principales implementaciones

* **`HashSet<E>`**

    * Basado en **tabla hash**.
    * Operaciones en tiempo \~O(1).
* **`LinkedHashSet<E>`**

    * Hash + **lista enlazada interna** para mantener orden de inserción.
* **`TreeSet<E>`**

    * Basado en **árbol rojo-negro**.
    * Mantiene orden **natural** o por `Comparator`.

---

### 🍰 Ejemplo con comentarios línea a línea

```java
import java.util.HashSet;
import java.util.Set;

public class TiposPastel {
    public static void main(String[] args) {
        // 1. Creamos un HashSet de String
        Set<String> tiposDePastel = new HashSet<>();

        // 2. Añadimos tipos
        tiposDePastel.add("Tarta de manzana");  // true
        tiposDePastel.add("Cheesecake");        // true
        tiposDePastel.add("Cheesecake");        // false: duplicado no permitido

        // 3. Mostramos los elementos. 
        //    El orden no está garantizado en HashSet.
        System.out.println("Tipos únicos: " + tiposDePastel);

        // 4. Comprobamos tamaño
        System.out.println("Número de tipos: " + tiposDePastel.size());
    }
}
```

* **Nota**: al añadir el segundo `"Cheesecake"`, el `add` devuelve `false` y no modifica el set.

---

## 📋 `List`

Una **List** es una colección **ordenada** que **permite duplicados** y **acceso por índice**.

```java
public interface List<E> extends Collection<E> {
    E get(int index);             // 1
    E set(int index, E element);  // 2
    void add(int index, E element);// 3
    E remove(int index);          // 4
    int indexOf(Object o);        // 5
    // …otros métodos…
}
```

1. `get(int index)`:

    * Devuelve el elemento en posición `index` (0-based).

2. `set(int index, E element)`:

    * Reemplaza el elemento en `index` por `element`.
    * Devuelve el elemento antiguo.

3. `add(int index, E element)`:

    * Inserta `element` en la posición `index`, desplazando el resto.

4. `remove(int index)`:

    * Elimina el elemento en `index` y lo devuelve.

5. `indexOf(Object o)`:

    * Retorna la primera posición donde aparece `o`, o –1 si no existe.

### Implementaciones clave

* **`ArrayList<E>`**:

    * Basado en array dinámico.
    * Acceso rápido `get`/`set` en O(1).
    * Inserciones/borrados en medio en O(n).
* **`LinkedList<E>`**:

    * Lista doblemente enlazada.
    * Inserciones/borrados en medio en O(1) (si ya tienes el nodo).
    * Acceso por índice en O(n).

---

### 🍰 Ejemplo con comentarios línea a línea

```java
import java.util.ArrayList;
import java.util.List;

public class PedidoCliente {
    public static void main(String[] args) {
        // 1. Creamos un ArrayList de String
        List<String> pedidoDelCliente = new ArrayList<>();

        // 2. Añadimos productos
        pedidoDelCliente.add("Cupcake");  // [Cupcake]
        pedidoDelCliente.add("Brownie");  // [Cupcake, Brownie]
        pedidoDelCliente.add("Cupcake");  // [Cupcake, Brownie, Cupcake]

        // 3. Obtenemos el segundo elemento (índice 1)
        String segundo = pedidoDelCliente.get(1);
        System.out.println("Segundo artículo: " + segundo);
        // → “Segundo artículo: Brownie”

        // 4. Reemplazamos el primero por “Donut”
        String antiguo = pedidoDelCliente.set(0, "Donut");
        //    antiguo = “Cupcake”
        //    lista ahora: [Donut, Brownie, Cupcake]

        // 5. Insertamos “Éclair” en la posición 2
        pedidoDelCliente.add(2, "Éclair");
        //    lista ahora: [Donut, Brownie, Éclair, Cupcake]

        // 6. Eliminamos el tercer elemento (índice 2)
        String eliminado = pedidoDelCliente.remove(2);
        //    eliminado = “Éclair”
        //    lista ahora: [Donut, Brownie, Cupcake]

        // 7. Mostramos la lista final
        System.out.println("Pedido final: " + pedidoDelCliente);
    }
}
```

---

## ⚙️ Clase utilitaria `Collections`

`Collections` (plural) es una **clase** con **métodos estáticos** para operar sobre `Collection` y `List`.

```java
import java.util.Collections;

public class Utils {
    public void ejemplos(List<String> lista) {
        Collections.sort(lista);            // 1
        Collections.shuffle(lista);         // 2
        int idx = Collections.binarySearch(lista, "clave"); // 3
        Collections.reverse(lista);         // 4
        List<String> sync = Collections.synchronizedList(lista); // 5
    }
}
```

1. `sort(List<? extends Comparable>)`:

    * Ordena según el **orden natural** de los elementos.
2. `shuffle(List<?>)`:

    * Mezcla aleatoriamente los elementos (útil para “turnos” o “orden de preparación”).
3. `binarySearch(List<? extends Comparable>, key)`:

    * Busca `key` en lista ordenada. Devuelve índice o (–inserción–1).
4. `reverse(List<?>)`:

    * Invierte el orden de la lista “in place”.
5. `synchronizedList(List<T>)`:

    * Devuelve una **vista sincronizada** para accesos seguros en múltiples hilos.

---

### 🍰 Ejemplo con comentarios línea a línea

```java
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrdenacionRecetas {
    public static void main(String[] args) {
        // 1. Lista de recetas
        List<String> listaRecetas = new ArrayList<>();
        listaRecetas.add("Donut");
        listaRecetas.add("Tarta de queso");
        listaRecetas.add("Bizcocho");

        // 2. Ordenamos alfabéticamente
        Collections.sort(listaRecetas);
        //    listaRecetas = [Bizcocho, Donut, Tarta de queso]

        System.out.println("Recetas ordenadas: " + listaRecetas);

        // 3. Mezclamos para variar el orden de preparación
        Collections.shuffle(listaRecetas);
        //    orden aleatorio

        System.out.println("Recetas mezcladas: " + listaRecetas);

        // 4. Invertimos el orden
        Collections.reverse(listaRecetas);
        //    versión invertida de la mezcla

        System.out.println("Recetas invertidas: " + listaRecetas);
    }
}
```

---

## 🗺️ `Map`

`Map<K,V>` almacena **pares clave–valor**. No extiende `Collection`.

```java
public interface Map<K,V> {
    V put(K key, V value);           // 1
    V get(Object key);               // 2
    V remove(Object key);            // 3
    boolean containsKey(Object key); // 4
    Set<K> keySet();                 // 5
    Collection<V> values();          // 6
    Set<Map.Entry<K,V>> entrySet();  // 7
}
```

1. `put(K key, V value)`:

    * Inserta o reemplaza el valor asociado a `key`.
    * Devuelve el valor anterior o `null` si no existía.

2. `get(Object key)`:

    * Recupera el valor asociado a `key`, o `null` si no existe.

3. `remove(Object key)`:

    * Elimina la entrada con esa clave y devuelve su valor.

4. `containsKey(Object key)`:

    * Comprueba si existe una entrada para `key`.

5. `keySet()`:

    * Devuelve un `Set` con todas las claves.

6. `values()`:

    * Devuelve una `Collection` con todos los valores.

7. `entrySet()`:

    * Devuelve un `Set` de objetos `Map.Entry<K,V>`, con métodos `getKey()` y `getValue()`.

### Principales implementaciones

* **`HashMap<K,V>`**: tabla hash.
* **`LinkedHashMap<K,V>`**: como `HashMap` + orden de inserción.
* **`TreeMap<K,V>`**: árbol rojo-negro, orden natural o por `Comparator`.

---

### 🍰 Ejemplo con comentarios línea a línea

```java
import java.util.HashMap;
import java.util.Map;

public class ControlStock {
    public static void main(String[] args) {
        // 1. Creamos un HashMap de <String, Integer>
        //    clave: nombre del ingrediente
        //    valor: cantidad en stock (paquetes)
        Map<String, Integer> stock = new HashMap<>();

        // 2. Insertamos valores
        stock.put("Harina", 5);    // antiguaHarina = null
        stock.put("Azúcar", 3);    // antiguaAzucar = null

        // 3. Actualizamos stock de Harina
        int anterior = stock.put("Harina", 7);
        //    anterior = 5
        //    ahora stock.get("Harina") = 7

        // 4. Obtenemos stock actual
        int cantidadHarina = stock.get("Harina");
        System.out.println("Harina disponible: " + cantidadHarina);

        // 5. Comprobamos existencia de “Sal”
        boolean tieneSal = stock.containsKey("Sal");
        System.out.println("¿Hay sal? " + tieneSal);

        // 6. Recorremos todas las claves
        for (String clave : stock.keySet()) {
            System.out.println("Ingrediente: " + clave);
        }

        // 7. Recorremos pares clave–valor
        for (Map.Entry<String, Integer> entrada : stock.entrySet()) {
            System.out.println(entrada.getKey() + " → " + entrada.getValue() + " paquetes");
        }
    }
}
```

---

## 📝 Ejercicios prácticos

1. **Inventario de pasteles**

    * Crea un `List<String>` con cinco tipos de pasteles (incluye duplicados).
    * Usa `Collections.shuffle()` y muestra el orden final.
    * Comenta cada línea de tu código.

2. **Control de alérgenos**

    * Usa un `Set<String>` para registrar ingredientes alérgenos (“Nueces”, “Gluten”, “Lácteos”).
    * Comprueba si “Gluten” está en el set y elimina “Lácteos”.
    * Explica línea a línea qué ocurre.

3. **Pedidos de clientes**

    * Modela un `Map<String, Integer>` donde la clave sea el nombre del cliente y el valor, el número de pasteles pedidos.
    * Añade tres clientes, actualiza un pedido y recorre el mapa mostrando cada entrada.

---

## 💡 Consejos y mejores prácticas

* **Comprende las complejidades**:

    * `ArrayList`: get/set O(1), add/	remove al final O(1 amortizado), inserción en medio O(n).
    * `LinkedList`: get O(n), add/remove en cualquier posición O(1) (con iterador).

* **Parametrización genérica**:

    * Siempre usa `<Tipo>` para evitar “raw types” y `ClassCastException`.

* **Inmutabilidad y seguridad**:

    * Si no quieres que cambien tu lista, usa `List.copyOf(...)` (Java 10+).

* **Sincronización**:

    * En entornos multi-hilo, envuelve con `Collections.synchronizedXxx(...)` o usa `ConcurrentHashMap`, `CopyOnWriteArrayList`.

* **Comparadores personalizados**:

    * Para ordenar objetos propios, implementa `Comparable<T>` o pasa un `Comparator<T>` a `Collections.sort` o a la implementación.

