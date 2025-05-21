package Exception;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Crear una clase Usuario con nombre y correo electrónico.
 * Validar que el correo tenga un formato válido (texto@texto.dominio).
 * Si no, lanzar una excepción EmailNoValidoException.
 */
// Importación de la clase Pattern y Matcher del paquete java.util.regex
// Estas clases nos permiten trabajar con expresiones regulares para validar patrones

/**
 * Excepción personalizada para indicar que un email no tiene un formato válido
 * Extiende de Exception para crear nuestra propia excepción verificada
 */
class EmailNoValidoException extends Exception {
    // Constructor que recibe un mensaje de error
    public EmailNoValidoException(String mensaje) {
        // Llama al constructor de la clase padre (Exception) con el mensaje
        super(mensaje);
    }
}

/**
 * Clase que representa un usuario con nombre y correo electrónico
 * Incluye validación del formato del email
 */
class Usuario {
    // Variable de instancia para almacenar el nombre del usuario
    // private para encapsulamiento, solo accesible dentro de la clase
    private String nombre;

    // Variable de instancia para almacenar el email del usuario
    private String email;

    /**
     * Constructor de la clase Usuario
     * @param nombre String con el nombre del usuario
     * @param email String con el email del usuario
     * @throws EmailNoValidoException si el email no cumple con el formato válido
     */
    public Usuario(String nombre, String email) throws EmailNoValidoException {
        // Asigna el nombre recibido al atributo nombre
        this.nombre = nombre;

        // Valida el email antes de asignarlo
        // Si no es válido, se lanzará la excepción
        if (!validarEmail(email)) {
            // Lanza la excepción personalizada con un mensaje descriptivo
            throw new EmailNoValidoException("El email " + email + " no tiene un formato válido");
        }

        // Si pasa la validación, asigna el email
        this.email = email;
    }

    /**
     * Metodo para validar el formato de un email usando expresiones regulares
     * @param email String con el email a validar
     * @return true si el email es válido, false si no lo es
     */
    private boolean validarEmail(String email) {
        // Patrón de expresión regular para validar emails:
        // ^ indica inicio de cadena
        // [A-Za-z0-9+_.-]+ uno o más caracteres alfanuméricos, puntos, guiones, etc.
        // @ símbolo obligatorio
        // [A-Za-z0-9.-]+ dominio después del @
        // \\. punto antes de la extensión
        // [A-Za-z]{2,} extensión de al menos 2 caracteres
        // $ fin de cadena
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

        // Compila el patrón de expresión regular
        Pattern pattern = Pattern.compile(regex);

        // Crea un matcher que comparará el email con el patrón
        Matcher matcher = pattern.matcher(email);

        // Retorna true si el email coincide con el patrón, false si no
        return matcher.matches();
    }

    /**
     * Metodo getter para obtener el nombre del usuario
     * @return String con el nombre del usuario
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Metodo getter para obtener el email del usuario
     * @return String con el email del usuario
     */
    public String getEmail() {
        return email;
    }

    /**
     * Metodo para representar el objeto Usuario como String
     * @return String con la representación del usuario
     */
    @Override
    public String toString() {
        return "Usuario: " + nombre + ", Email: " + email;
    }
}

/**
 * Clase principal para demostrar el funcionamiento
 */
public class ValidacionGmail {
    public static void main(String[] args) {
        try {
            // Creación de un usuario con email válido
            Usuario usuario1 = new Usuario("Juan Pérez", "juan.perez@example.com");
            System.out.println(usuario1); // Imprime los datos del usuario

            // Intento de crear usuario con email inválido - esto lanzará la excepción
            Usuario usuario2 = new Usuario("Ana Gómez", "ana.gomez@example");
        } catch (EmailNoValidoException e) {
            // Captura la excepción y muestra el mensaje de error
            System.err.println("Error: " + e.getMessage());
        }
    }
}