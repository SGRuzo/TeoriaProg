
## 🧪 EJERCICIO 1 – Checked Exception: Leer un archivo

### 📌 Objetivo:

Leer un archivo de texto. Si el archivo no existe, debe lanzarse y capturarse una **FileNotFoundException** (que es checked).

---

### ✅ CÓDIGO COMENTADO

```java
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CheckedExceptionEjemplo {

    public static void main(String[] args) {
        // Creamos un objeto File que apunta a un archivo llamado "datos.txt"
        File archivo = new File("datos.txt");

        try {
            // Intentamos abrir el archivo con Scanner
            Scanner lector = new Scanner(archivo);

            // Si el archivo se abre correctamente, leemos línea por línea
            while (lector.hasNextLine()) {
                String linea = lector.nextLine(); // Leemos una línea del archivo
                System.out.println("Línea: " + linea); // Imprimimos la línea
            }

            lector.close(); // Cerramos el archivo

        } catch (FileNotFoundException e) {
            // Si el archivo no se encuentra, se ejecuta este bloque
            System.out.println("¡Archivo no encontrado!");
            System.out.println("Mensaje técnico: " + e.getMessage()); // Detalles técnicos
        }

        System.out.println("Programa terminado correctamente.");
    }
}
```

### 🧠 Explicación clave:

* **FileNotFoundException** es **checked** → el compilador obliga a usar `try-catch`.
* Este tipo de excepción se usa cuando **trabajas con archivos**.

---

## 🧪 EJERCICIO 2 – Unchecked Exception: División por cero

### 📌 Objetivo:

Pedir al usuario dos números y dividirlos. Si el segundo número es cero, se lanza una **ArithmeticException**.

---

### ✅ CÓDIGO COMENTADO

```java
import java.util.Scanner;

public class UncheckedExceptionEjemplo {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            // Pedimos el primer número
            System.out.print("Introduce el numerador: ");
            int numerador = sc.nextInt();

            // Pedimos el segundo número
            System.out.print("Introduce el denominador: ");
            int denominador = sc.nextInt();

            // Realizamos la división (puede lanzar ArithmeticException si denominador es 0)
            int resultado = numerador / denominador;

            // Mostramos el resultado
            System.out.println("Resultado: " + resultado);

        } catch (ArithmeticException e) {
            // Se captura si intentamos dividir por cero
            System.out.println("¡Error! No se puede dividir por cero.");
            System.out.println("Detalles: " + e.getMessage());
        }

        System.out.println("Programa terminado correctamente.");
    }
}
```

### 🧠 Explicación clave:

* **ArithmeticException** es **unchecked** → no estás obligado a capturarla, pero puedes hacerlo si quieres.
* Ocurre **durante la ejecución**, cuando se hace una operación matemática inválida.

---

## 🧪 EJERCICIO 3 – Error: Desbordamiento de pila (StackOverflowError)

### 📌 Objetivo:

Crear una **recursión infinita** para forzar un **StackOverflowError**, que es un tipo de **Error**, no Exception.

---

### ⚠️ MUCHO CUIDADO: este error detiene el programa

```java
public class ErrorEjemplo {

    public static void metodoInfinito() {
        // Llamamos al mismo método sin condición de salida
        metodoInfinito();
    }

    public static void main(String[] args) {
        try {
            // Llamamos al método recursivo
            metodoInfinito();
        } catch (StackOverflowError e) {
            // Aunque normalmente no se capturan los errores, aquí lo hacemos por seguridad
            System.out.println("¡Error crítico! Stack overflow detectado.");
            System.out.println("Detalles: " + e.getMessage());
        }

        System.out.println("Programa terminado (si llegó hasta aquí).");
    }
}
```

### 🧠 Explicación clave:

* **StackOverflowError** no es una excepción sino un **Error** (más grave).
* Ocurre cuando **la pila de llamadas** se llena por recursión infinita.
* Aunque **NO DEBES manejar errores como este normalmente**, aquí lo hacemos con fines didácticos.

---

## 📌 CONCLUSIÓN GENERAL

| Tipo                | ¿Debo manejarla?   | Ejemplo                 | Clase base         |
| ------------------- | ------------------ | ----------------------- | ------------------ |
| Checked Exception   | ✅ Sí               | `FileNotFoundException` | `Exception`        |
| Unchecked Exception | ❌ No (pero puedes) | `ArithmeticException`   | `RuntimeException` |
| Error               | ❌ No               | `StackOverflowError`    | `Error`            |

