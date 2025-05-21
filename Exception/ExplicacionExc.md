
## 📑 Índice

1. [🚀 Introducción ampliada](#introducción-ampliada)
2. [❓ ¿Qué es una excepción? (más detalle)](#qué-es-una-excepción-más-detalle)
3. [🌳 Jerarquía de excepciones en Java (explicada línea a línea)](#jerarquía-de-excepciones-en-java-explicada-línea-a-línea)
4. [🎯 Tipos de excepciones en Java (detallado con ejemplos)](#tipos-de-excepciones-en-java-detallado-con-ejemplos)
5. [🔧 Manejo de excepciones con comentarios exhaustivos](#manejo-de-excepciones-con-comentarios-exhaustivos)
6. [🛠️ Definición de excepciones personalizadas con todo detalle](#definición-de-excepciones-personalizadas-con-todo-detalle)
7. [🎂 Ejemplo completo de una `Pasteleria` paso a paso](#ejemplo-completo-de-una-pasteleria-paso-a-paso)
8. [✏️ Ejercicios propuestos con guías y soluciones paso a paso](#ejercicios-propuestos-con-guías-y-soluciones-paso-a-paso)
9. [📚 Buenas prácticas y consejos profundos](#buenas-prácticas-y-consejos-profundos)
10. [🔗 Recursos adicionales](#recursos-adicionales)

---

## 🚀 Introducción ampliada

> **¿Por qué estudiar excepciones?**  
> En el mundo real de la programación, **cualquier** cosa puede fallar: desde la lectura de un archivo que no existe hasta una red que se cae o un usuario que introduce mal los datos. Java ofrece un sistema robusto para **detectar**, **gestionar** y **recuperarse** de esos errores sin que la aplicación termine de forma desastrosa.

1. **Detección temprana**: El compilador de Java te obliga a pensar en ciertas excepciones (checked), lo que mejora la calidad del código.
2. **Separación de responsabilidades**: Tu código “normal” va en bloques `try`, el de manejo de errores en `catch`, y la limpieza (cerrar recursos) en `finally`.
3. **Claridad para otros devs**: Usar excepciones bien nombradas y con mensajes claros es como documentar tu código en tiempo de ejecución.

---

## ❓ ¿Qué es una excepción? (más detalle)

* Es **un objeto** que hereda de `Throwable`.
* Representa un evento anómalo:
    * **Errores graves** (`Error`): normalmente no manejables (p.ej. `OutOfMemoryError`).
    * **Excepciones** (`Exception`): condiciones que la aplicación **puede** y **debe** manejar.

> **Analogía pastelera:**  
> Imagínate que al hacer una tarta te quedas sin azúcar: tú “lanzas” una señal (excepción) que dice “¡Falta azúcar!” y alguien en la cocina (el bloque `catch`) decide si sale a comprar azúcar, usa edulcorante, o interrumpe el pedido.

---

## 🌳 Jerarquía de excepciones en Java (explicada línea a línea)

```plaintext
java.lang.Throwable
├── Error
│   └── OutOfMemoryError
│   └── StackOverflowError
└── Exception
    ├── RuntimeException
    │   └── NullPointerException
    │   └── ArrayIndexOutOfBoundsException
    └── IOException
        └── FileNotFoundException
```

1. **`Throwable`**
    - Raíz de toda la jerarquía.
    - Define métodos clave como `getMessage()`, `printStackTrace()`, y `getCause()`.

2. **`Error`**
    - Problemas graves del entorno JVM (no manejables).
    - Ejemplo: `OutOfMemoryError` (memoria insuficiente).

3. **`Exception`**
    - Base para excepciones manejables.
    - **Checked**: Deben declararse o capturarse (ej. `IOException`).
    - **Unchecked**: Heredan de `RuntimeException` (ej. `NullPointerException`).

---

## 🎯 Tipos de Excepciones en Java (Detallado con Ejemplos Cotidianos)

### 📌 Clasificación Principal
1. **Checked Exceptions (Verificadas)**
    - **Características:**
        - El compilador obliga a manejarlas con `try-catch` o `throws`.
        - Heredan de `Exception` (no de `RuntimeException`).
    - **Ejemplo:**
      ```java
      try {
          Files.readString(Path.of("receta.txt")); // Intento de lectura
      } catch (IOException e) { // Captura obligatoria
          System.out.println("Archivo no encontrado. Usando receta por defecto.");
      }
      ```

2. **Unchecked Exceptions (RuntimeExceptions)**
    - **Características:**
        - Errores de lógica (no obligan a manejo).
        - Heredan de `RuntimeException`.
    - **Ejemplo:**
      ```java
      String nombre = null;
      System.out.println(nombre.length()); // Lanza NullPointerException
      ```

3. **Errores (Errors)**
    - **Características:**
        - Problemas críticos de la JVM (no manejables).
        - Ejemplo: `OutOfMemoryError` (memoria agotada).

---

### 🚨 Tabla de Excepciones Comunes
| **Tipo**                | **Categoría**       | **Causa Típica**                          |
|-------------------------|---------------------|-------------------------------------------|
| `NullPointerException`  | Unchecked           | Llamar a método de objeto `null`.         |
| `FileNotFoundException` | Checked             | Archivo no existe en ruta especificada.   |
| `ArithmeticException`   | Unchecked           | División por cero.                        |

---

### 🔍 Explicación de `Throwable`
- **Métodos Clave:**
  ```java
  try {
      // Código riesgoso
  } catch (Exception e) {
      System.out.println("Mensaje: " + e.getMessage());  // Obtiene el mensaje de error
      e.printStackTrace();  // Imprime la traza de ejecución
      System.out.println("Causa: " + e.getCause());  // Muestra la excepción original
  }
  ```

---

## 🔧 Manejo de excepciones con comentarios exhaustivos

### 1. Bloque `try`
```java
try {
    // ① Código que podría lanzar una excepción
    prepararMasa();  // Método riesgoso
    System.out.println("Masa lista");  // Ejecuta solo si no hay excepción
}
```
- **Propósito:** Delimita el código que puede generar errores.
- **Regla:** Siempre debe ir acompañado de `catch` o `finally`.

### 2. Bloque `catch`
```java
catch (IngredienteFaltanteException e) {
    // ② Manejo específico para falta de ingredientes
    System.err.println("Error: " + e.getMessage());  // Mensaje personalizado
    // e.printStackTrace();  // Opcional para depuración
}
```
- **Múltiples `catch`:** Ordenados de específico a general.
  ```java
  catch (FileNotFoundException e) { ... }
  catch (IOException e) { ... }
  ```

### 3. Bloque `finally`
```java
finally {
    // ③ Siempre se ejecuta (con o sin excepción)
    limpiarHorno();  // Liberar recursos (ej. cerrar archivos)
}
```
- **Uso común:** Cerrar conexiones de BD, archivos, etc.

### 4. Palabras Clave `throw` y `throws`
```java
// Método que declara una excepción
public void hornear() throws TemperaturaAltaException {
    if (temperatura > 200) {
        // Lanzar excepción manualmente
        throw new TemperaturaAltaException("¡Demasiado caliente! " + temperatura);
    }
}
```
- **`throws`:** Declara excepciones que un método puede lanzar.
- **`throw`:** Lanza una instancia de excepción.

---

## 🛠️ Definición de excepciones personalizadas con todo detalle

```java
/**
 * Excepción lanzada cuando falta un ingrediente.
 * Hereda de Exception (checked).
 */
public class IngredienteFaltanteException extends Exception {
    // ① Constructor con mensaje
    public IngredienteFaltanteException(String mensaje) {
        super(mensaje);  // Llama al constructor de Exception
    }

    // ② Constructor con mensaje y causa
    public IngredienteFaltanteException(String mensaje, Throwable causa) {
        super(mensaje, causa);  // Permite encadenar excepciones
    }
}
```
- **Uso:**
  ```java
  throw new IngredienteFaltanteException("Falta harina", e);  // 'e' es la causa original
  ```

---

## 🎂 Ejemplo completo de una `Pasteleria` paso a paso

```java
public class Pasteleria {

    public void prepararPastel(String tipo) {
        try {
            verificarIngredientes(tipo);  // ① Puede lanzar IngredienteFaltanteException
            amasar();  // ② Puede lanzar MasaInconsistenteException
            hornear();  // ③ Puede lanzar MasaQuemadaException
            System.out.println(tipo + " listo 🎉");
        } catch (IngredienteFaltanteException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (MasaQuemadaException e) {
            System.err.println("Masa quemada: " + e.getMessage());
        } finally {
            System.out.println("Limpieza completada 🧼");  // ④ Siempre se ejecuta
        }
    }

    private void verificarIngredientes(String tipo) throws IngredienteFaltanteException {
        if (!tieneHarina()) {
            // ⑤ Lanza excepción con mensaje detallado
            throw new IngredienteFaltanteException("Harina insuficiente para " + tipo);
        }
    }

    private void amasar() throws MasaInconsistenteException {
        if (/* masa inconsistente */) {
            throw new MasaInconsistenteException("Agua al 70%");
        }
    }

    private void hornear() throws MasaQuemadaException {
        int temp = new Random().nextInt(300);
        if (temp > 180) {
            throw new MasaQuemadaException("Temperatura: " + temp);
        }
    }
}
```

---

## ✏️ Ejercicios propuestos con guías y soluciones paso a paso

### Ejercicio 1: `ChocolateDerretidoException`
```java
// ① Define la excepción
public class ChocolateDerretidoException extends Exception {
    public ChocolateDerretidoException(String msg) {
        super(msg);
    }
}

// ② Intégrala en tu código
private void verificarChocolate() throws ChocolateDerretidoException {
    if (temperatura > 30) {
        throw new ChocolateDerretidoException("Chocolate derretido a " + temperatura + "°C");
    }
}
```

### Ejercicio 2: `PedidoNoEntregadoException`
```java
// ① Define la excepción
public class PedidoNoEntregadoException extends Exception {
    public PedidoNoEntregadoException(String msg) {
        super(msg);
    }
}

// ② Simula el envío
private void enviarPedido() throws PedidoNoEntregadoException {
    boolean exito = new Random().nextBoolean();
    if (!exito) {
        throw new PedidoNoEntregadoException("Error en reparto");
    }
}
```

---

## 📚 Buenas prácticas y consejos profundos

1. **Mensajes Descriptivos:**  
   Incluye datos relevantes en los mensajes de error (ej. `"Temperatura actual: " + temp`).

2. **Try-with-Resources:**
   ```java
   try (BufferedReader br = new BufferedReader(new FileReader("receta.txt"))) {
       // Lectura automáticamente cerrada al final
   } catch (IOException e) {
       e.printStackTrace();
   }
   ```

3. **Encadenamiento de Excepciones:**
   ```java
   catch (SQLException e) {
       throw new MiExcepcion("Error en BD", e);  // Conserva la causa original
   }
   ```

---

## 🔗 Recursos adicionales

- 📘 **Libro:** *Effective Java* de Joshua Bloch (Capítulo sobre excepciones).
- 🌐 **Tutorial Oficial:** [Java Exceptions (Oracle)](https://docs.oracle.com/javase/tutorial/essential/exceptions/).
- 🛠️ **Herramienta:** [EJML (Efficient Java Matrix Library)](https://ejml.org) para manejo seguro de operaciones matemáticas.