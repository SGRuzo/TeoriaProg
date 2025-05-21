
# 🍰 Gestión de Ficheros en Java – Apuntes Detallados

---

## 📖 1. Introducción

En Java, la **gestión de ficheros** (o archivos) permite **crear**, **leer**, **escribir**, **modificar** y **borrar** datos persistentes en el sistema de archivos.
Usaremos analogías de una **pastelería** para entender cada operación:

* Un **fichero** es como un **recetario** donde guardamos nuestras recetas.
* Leer un fichero = consultar una receta.
* Escribir un fichero = anotar o actualizar una receta.

---

## 🧩 2. Lógica General

1. **Identificar el fichero**: Conocer su ruta y nombre.
2. **Abrir flujo**: Preparar el canal de entrada (Reader) o salida (Writer).
3. **Operar**: Leer o escribir datos.
4. **Cerrar flujo**: Liberar recursos al terminar.

---

---

## 📂 3. Clases Principales

| Clase                               | Uso principal                                                         |
| ----------------------------------- | --------------------------------------------------------------------- |
| `java.io.File`                      | Representa rutas (carpetas o ficheros).                               |
| `FileReader` / `FileWriter`         | Leer/escribir caracteres (texto).                                     |
| `BufferedReader` / `BufferedWriter` | Envolver un Reader/Writer para leer/escribir por líneas o con buffer. |
| `InputStream` / `OutputStream`      | Leer/escribir bytes (apto para binarios, imágenes, etc.).             |
| `java.nio.file.Path`                | Nueva API (NIO) para rutas, más potente.                              |
| `java.nio.file.Files`               | Operaciones estáticas (copiar, mover, leer todo, escribir todo).      |

---

---

## ✨ 4. Uso de `java.io.File`

```java
import java.io.File;
// Importamos la clase File del paquete java.io, que representa rutas de ficheros y directorios.

public class Pasteleria {
    public static void main(String[] args) {
        // 📁 Definir el recetario
        File recetario = new File("recetas/pasteles.txt");
        // 1. new File("…") crea un objeto File que apunta a la ruta "recetas/pasteles.txt".
        //    - No crea nada en disco aún, solo referencia la ubicación esperada.

        // ¿Existe?
        if (!recetario.exists()) {
            // 2. exists() consulta al sistema si el fichero o directorio ya existe.
            System.out.println("❌ No existe el recetario. Creándolo...");

            try {
                // 3. getParentFile() obtiene el File de la carpeta padre ("recetas")
                //    mkdirs() crea la carpeta y todas sus subcarpetas si no existen.
                boolean carpetasCreadas = recetario.getParentFile().mkdirs();

                // 4. createNewFile() crea el fichero vacío en disco si no existía.
                boolean ficheroCreado = recetario.createNewFile();

                // 5. Informamos al usuario si todo salió bien.
                System.out.println((carpetasCreadas && ficheroCreado)
                        ? "✅ Recetario creado."
                        : "⚠️ Falló al crear carpetas o fichero.");
            } catch (Exception e) {
                // 6. Capturamos cualquier excepción (I/O, permisos…) y mostramos la traza.
                e.printStackTrace();
            }
        } else {
            // 7. Si ya existe, simplemente confirmamos que está listo.
            System.out.println("📖 Recetario listo para usar.");
        }
    }
}

```

* `new File("ruta")` → referencia al fichero (no lo crea automáticamente).
* `exists()` → comprueba existencia.
* `mkdirs()` → crea carpetas padre si no existen.
* `createNewFile()` → crea fichero vacío.

---

---

## 📖 5. Lectura con `BufferedReader`

```java
import java.io.*;
// Importamos todas las clases de java.io para usar File, FileReader, BufferedReader y IOException.

public class LeerRecetas {
    public static void main(String[] args) {
        // 1. Definimos el File que queremos leer.
        File archivo = new File("recetas/pasteles.txt");

        // 2. Usamos try-with-resources para asegurar el cierre automático del stream.
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            // - FileReader abre un flujo de caracteres desde el fichero.
            // - BufferedReader añade buffering y el método readLine().

            String linea;
            // 3. Variable donde se almacenará cada línea leída.

            while ((linea = br.readLine()) != null) {
                // 4. readLine() devuelve null al llegar al final del fichero.
                //    Cada iteración lee una línea completa de texto.
                System.out.println("➡️ " + linea);
                // 5. Imprimimos la línea precedida de un emoji indicativo.
            }
        } catch (IOException e) {
            // 6. Capturamos errores de E/S (fichero no encontrado, permisos, etc.).
            e.printStackTrace();
        }
        // 7. Al salir del try-with-resources, BufferedReader (y FileReader) se cierran automáticamente.
    }
}

```

* `FileReader` → abre flujo de caracteres.
* `BufferedReader` → añade `readLine()` para línea a línea.
* `try-with-resources` → cierra automáticamente al salir.

### 🥐 Ejemplo de Pastelería

> Imagina que cada línea de `pasteles.txt` contiene:
> `Nombre;Ingredientes;Tiempo`
> Ejemplo:
>
> ```
> Tarta de Chocolate;Chocolate,Huevos,Harina;45
> Croissant;Mantequilla,Harina,Levadura;30
> ```

---

---

## ✏️ 6. Escritura con `BufferedWriter`

```java
import java.io.*;
// Importamos java.io para File, FileWriter, BufferedWriter y IOException.

public class AnotarReceta {
    public static void main(String[] args) {
        // 1. Apuntamos al mismo fichero donde guardamos las recetas.
        File archivo = new File("recetas/pasteles.txt");

        // 2. try-with-resources para cerrar automáticamente el stream al terminar.
        //    FileWriter(archivo, true) abre en modo "append" (añadir al final).
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true))) {
            // 3. Definimos la nueva receta en formato "Nombre;Ingredientes;Tiempo".
            String nuevaReceta = "Muffin de Vainilla;Harina,Huevos,Vainilla;25";

            bw.write(nuevaReceta);
            // 4. write() escribe la cadena EXACTA en el fichero (sin salto de línea al final).

            bw.newLine();
            // 5. newLine() inserta el salto de línea apropiado para el sistema operativo.

            System.out.println("📝 Receta anotada correctamente.");
            // 6. Mensaje de confirmación para el usuario.
        } catch (IOException e) {
            // 7. Capturamos posibles errores (p.ej. permisos denegados).
            e.printStackTrace();
        }
        // 8. BufferedWriter y FileWriter se cierran automáticamente al salir del bloque.
    }
}

```

* `FileWriter(..., true)` → modo **append**.
* `bw.write(...)` → escribe texto.
* `bw.newLine()` → salto de línea adecuado al SO.

---

---

## 🔄 7. API NIO (`java.nio.file`)

```java
import java.nio.file.*;
// Importamos clases de NIO: Path, Paths y Files.

public class OperacionesNIO {
    public static void main(String[] args) throws Exception {
        // 1. Paths.get() crea un objeto Path para la ruta de origen y destino.
        Path origen = Paths.get("recetas/pasteles.txt");
        Path copia   = Paths.get("recetas/copia_pasteles.txt");

        // 2. Files.copy() copia el fichero origen al destino.
        //    StandardCopyOption.REPLACE_EXISTING sobreescribe si ya existía.
        Files.copy(origen, copia, StandardCopyOption.REPLACE_EXISTING);
        System.out.println("📑 Copia creada.");

        // 3. Files.readAllLines() lee todas las líneas de un tirón en una List<String>.
        List<String> lineas = Files.readAllLines(origen);
        // 4. Iteramos e imprimimos cada línea.
        for (String linea : lineas) {
            System.out.println("📄 " + linea);
        }

        // 5. Para borrar un fichero, usar Files.delete(path).
        //    Descomenta la siguiente línea si deseas eliminar la copia:
        // Files.delete(copia);
        //    Arroja NoSuchFileException si no existe, o DirectoryNotEmptyException para carpetas.
    }
}
```

---

## 🎓 8. Ejercicios Prácticos de Pastelería

1. **Crear un recetario nuevo**

    * Programa que pida al usuario nombre de fichero y lo cree.
    * Si el fichero ya existe, avisar y preguntar si desea sobrescribir.

2. **Añadir varias recetas**

    * Modificar `AnotarReceta` para que, en un bucle, pregunte por nombre, ingredientes y tiempo, y lo guarde.

3. **Leer y mostrar filtro**

    * Lee todo el recetario y sólo muestra las recetas con tiempo menor a X minutos (X dado por el usuario).

4. **Copiar recetario con fecha**

    * Generar una copia con nombre `pasteles_YYYYMMDD.txt` usando NIO.

5. **Borrar recetas antiguas**

    * Dado un fichero de “recetas obsoletas”, lee su contenido y borra esas líneas del recetario original.

---

---

## ✅ 9. Buenas Prácticas

* **Cerrar siempre** los streams (uso de try-with-resources).
* **Gestionar excepciones**: distingue `FileNotFoundException` y `IOException`.
* **Evitar rutas duras**: usar variables o parámetros.
* **Comprobar permisos**: `canRead()`, `canWrite()`.
* **Usar NIO** para operaciones complejas (copiar, mover, atributos).

---

## 🎉 10. Resumen Final

* La gestión de ficheros en Java consta de **definir**, **abrir**, **operar** y **cerrar**.
* La analogía con una pastelería facilita entender recetarios, consultas y anotaciones.
* Con `java.io` cubres lo básico; con `java.nio.file` obtienes mayor potencia y flexibilidad.
* La práctica con ejercicios de creación, lectura, filtrado y copia refuerza los conocimientos.

