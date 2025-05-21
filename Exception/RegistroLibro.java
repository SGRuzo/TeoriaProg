package Exception;

/**
 * Registro de libro
 * Clase Libro con ISBN, título, autor.
 * Validar el ISBN (tiene que tener 13 dígitos).
 * Si no cumple, lanzar IsbnNoValidoException.
 */
// Importación de la clase List y ArrayList del paquete java.util para almacenar libros
import java.util.ArrayList;
import java.util.List;

/**
 * Excepción personalizada para indicar que un ISBN no es válido
 * Extiende de Exception para crear nuestra propia excepción verificada
 */
class IsbnNoValidoException extends Exception {
    // Constructor que recibe un mensaje de error
    public IsbnNoValidoException(String mensaje) {
        // Llama al constructor de la clase padre (Exception) con el mensaje
        super(mensaje);
    }
}

/**
 * Clase que representa un Libro con ISBN, título y autor
 * Incluye validación del formato del ISBN (debe tener exactamente 13 dígitos)
 */
class Libro {
    // Variables de instancia para almacenar los atributos del libro
    // private para encapsulamiento, solo accesible dentro de la clase
    private String isbn;  // Almacenado como String para preservar ceros a la izquierda
    private String titulo;
    private String autor;

    /**
     * Constructor de la clase Libro
     * @param isbn Número de identificación del libro (debe tener 13 dígitos)
     * @param titulo Título del libro
     * @param autor Autor del libro
     * @throws IsbnNoValidoException si el ISBN no cumple con el formato requerido
     */
    public Libro(String isbn, String titulo, String autor) throws IsbnNoValidoException {
        // Validamos el ISBN antes de asignar los valores
        if (!validarISBN(isbn)) {
            // Lanzamos excepción con mensaje descriptivo
            throw new IsbnNoValidoException("El ISBN " + isbn + " no es válido. Debe contener exactamente 13 dígitos.");
        }

        // Si el ISBN es válido, asignamos los valores
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
    }

    /**
     * Metodo para validar el formato del ISBN
     * @param isbn String con el ISBN a validar
     * @return true si el ISBN es válido (contiene exactamente 13 dígitos), false si no
     */
    private boolean validarISBN(String isbn) {
        // Verificamos que el ISBN no sea nulo y tenga exactamente 13 caracteres
        if (isbn == null || isbn.length() != 13) {
            return false;
        }

        // Verificamos que todos los caracteres sean dígitos
        for (int i = 0; i < isbn.length(); i++) {
            char c = isbn.charAt(i);
            if (!Character.isDigit(c)) {
                return false;
            }
        }

        // Si pasó todas las validaciones, el ISBN es válido
        return true;
    }

    // Métodos getters para acceder a los atributos privados

    /**
     * @return el ISBN del libro
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * @return el título del libro
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * @return el autor del libro
     */
    public String getAutor() {
        return autor;
    }

    /**
     * Representación en String del libro
     * @return String con la información del libro formateada
     */
    @Override
    public String toString() {
        return "Libro [ISBN: " + isbn + ", Título: " + titulo + ", Autor: " + autor + "]";
    }
}

/**
 * Clase principal para demostrar el funcionamiento
 */
public class RegistroLibro {
    public static void main(String[] args) {
        // Lista para almacenar libros creados
        List<Libro> biblioteca = new ArrayList<>();

        try {
            // Crear libros con ISBN válidos
            biblioteca.add(new Libro("9781234567890", "El Principito", "Antoine de Saint-Exupéry"));
            biblioteca.add(new Libro("1234567890123", "Cien años de soledad", "Gabriel García Márquez"));

            // Intentar crear libro con ISBN inválido - esto lanzará la excepción
            biblioteca.add(new Libro("978123456789", "Don Quijote", "Miguel de Cervantes")); // Solo 12 dígitos
            biblioteca.add(new Libro("97812345678901", "1984", "George Orwell")); // 14 dígitos
            biblioteca.add(new Libro("978A234567890", "Orgullo y prejuicio", "Jane Austen")); // Contiene letra

        } catch (IsbnNoValidoException e) {
            // Capturar y mostrar el error
            System.err.println("Error al crear libro: " + e.getMessage());
        }

        // Mostrar los libros creados exitosamente
        System.out.println("\nLibros en la biblioteca:");
        for (Libro libro : biblioteca) {
            System.out.println(libro);
        }
    }
}