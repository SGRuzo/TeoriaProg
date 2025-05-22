
## 📄 EJERCICIO 1: Fichero de Texto Plano

**Objetivo:** Guardar y luego leer una lista de nombres en un archivo `.txt`.

### 🔧 Solución Comentada

```java
import java.io.*;

public class TextoPlano {

    public static void main(String[] args) {
        // 1. Ruta del fichero
        String nombreFichero = "nombres.txt";

        // 2. Escribir nombres en el fichero
        try (PrintWriter writer = new PrintWriter(nombreFichero)) {
            // Cada println escribe una línea nueva en el archivo
            writer.println("Ana");
            writer.println("Luis");
            writer.println("Carlos");
            writer.println("Sofía");

            System.out.println("Nombres escritos correctamente.");
        } catch (IOException e) {
            System.out.println("Error escribiendo el archivo: " + e.getMessage());
        }

        // 3. Leer los nombres del fichero
        try (BufferedReader reader = new BufferedReader(new FileReader(nombreFichero))) {
            String linea;

            // Leemos línea a línea hasta que no haya más (null)
            while ((linea = reader.readLine()) != null) {
                System.out.println("Nombre leído: " + linea);
            }
        } catch (IOException e) {
            System.out.println("Error leyendo el archivo: " + e.getMessage());
        }
    }
}
```

---

## 📦 EJERCICIO 2: Fichero Binario

**Objetivo:** Guardar y leer varios números enteros en un archivo binario.

### 🔧 Solución Comentada

```java
import java.io.*;

public class BinarioSimple {

    public static void main(String[] args) {
        String archivo = "numeros.dat";

        // 1. Escribir números enteros en binario
        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(archivo))) {
            out.writeInt(100);
            out.writeInt(200);
            out.writeInt(300);
            System.out.println("Números escritos en binario.");
        } catch (IOException e) {
            System.out.println("Error escribiendo el archivo binario: " + e.getMessage());
        }

        // 2. Leer los números del archivo
        try (DataInputStream in = new DataInputStream(new FileInputStream(archivo))) {
            // Leemos hasta que ocurra un EOFException (fin del archivo)
            while (true) {
                int numero = in.readInt();
                System.out.println("Número leído: " + numero);
            }
        } catch (EOFException eof) {
            System.out.println("Fin del archivo binario alcanzado.");
        } catch (IOException e) {
            System.out.println("Error leyendo el archivo binario: " + e.getMessage());
        }
    }
}
```

---

## 👤 EJERCICIO 3: Fichero de Objetos Serializados

**Objetivo:** Guardar y recuperar objetos de una clase `Persona` usando serialización.

### 🔧 Solución Comentada

```java
import java.io.*;

// La clase debe implementar Serializable para poder guardarla
class Persona implements Serializable {
    String nombre;
    int edad;

    public Persona(String nombre, int edad) {
        this.nombre = nombre;
        this.edad = edad;
    }

    // Método para mostrar los datos
    public void mostrar() {
        System.out.println("Nombre: " + nombre + ", Edad: " + edad);
    }
}

public class Serializacion {

    public static void main(String[] args) {
        String archivo = "personas.ser";

        // Crear algunos objetos Persona
        Persona p1 = new Persona("Lucía", 30);
        Persona p2 = new Persona("Mario", 28);

        // 1. Guardar los objetos en un fichero
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(p1);
            oos.writeObject(p2);
            System.out.println("Objetos serializados correctamente.");
        } catch (IOException e) {
            System.out.println("Error serializando: " + e.getMessage());
        }

        // 2. Leer los objetos del fichero
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            // Lectura de los objetos en el mismo orden
            Persona persona1 = (Persona) ois.readObject();
            Persona persona2 = (Persona) ois.readObject();

            // Mostrar la información
            persona1.mostrar();
            persona2.mostrar();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error leyendo objetos: " + e.getMessage());
        }
    }
}
```

---

## 🧠 ¿Qué puedes practicar ahora?

1. **Modificar los ejemplos** para leer datos desde consola antes de escribirlos.
2. **Guardar una lista completa (ArrayList) de objetos `Persona`** en un solo fichero.
3. **Crear un menú de consola** que permita al usuario elegir entre escribir o leer del archivo.

