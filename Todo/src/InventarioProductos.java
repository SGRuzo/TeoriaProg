/**
 *  Inventario de productos
 * Clase Producto con:
 * Código (alfanumérico).
 * Nombre.
 * Cantidad.
 * Funciones:
 * Alta, baja y modificación.
 * Validar código (alfanumérico, 5-10 caracteres) → si no, lanzar excepción.
 * Guardar en productos.dat al salir, cargar al iniciar.
 * Mostrar productos.
 * Usa:
 * Excepciones personalizadas.
 * Colección Map<String, Producto>.
 * Fichero binario.
 */

// Importaciones necesarias para el funcionamiento del programa
import java.io.*;                 // Para operaciones de entrada/salida
import java.util.*;               // Para colecciones y utilidades
import java.util.regex.Pattern;   // Para expresiones regulares


/**
 * Excepción personalizada para códigos de producto inválidos
 */
class CodigoProductoInvalidoException extends Exception {
    // Constructor que recibe un mensaje de error
    public CodigoProductoInvalidoException(String mensaje) {
        super(mensaje);  // Llama al constructor de la clase padre Exception
    }
}

/**
 * Clase que representa un Producto en el inventario
 */
class Producto implements Serializable {
    // Versión de serialización para control de compatibilidad
    private static final long serialVersionUID = 1L;

    // Campos privados para encapsular los datos del producto
    private String codigo;    // Código alfanumérico del producto
    private String nombre;    // Nombre del producto
    private int cantidad;     // Cantidad en stock

    // Expresión regular para validar el formato del código
    private static final Pattern PATRON_CODIGO = Pattern.compile("^[a-zA-Z0-9]{5,10}$");

    /**
     * Constructor de la clase Producto
     * @param codigo Código alfanumérico (5-10 caracteres)
     * @param nombre Nombre del producto
     * @param cantidad Cantidad en stock
     * @throws CodigoProductoInvalidoException Si el código no cumple con el formato
     */
    public Producto(String codigo, String nombre, int cantidad) throws CodigoProductoInvalidoException {
        // Validar el código antes de asignarlo
        if (!validarCodigo(codigo)) {
            throw new CodigoProductoInvalidoException(
                    "Código inválido. Debe ser alfanumérico (5-10 caracteres)");
        }

        // Asignar valores a los campos
        this.codigo = codigo;
        this.nombre = nombre;
        this.cantidad = cantidad;
    }

    /**
     * Valida el formato del código del producto
     * @param codigo Código a validar
     * @return true si el código es válido, false si no
     */
    private boolean validarCodigo(String codigo) {
        // Verifica que el código coincida con el patrón alfanumérico de 5-10 caracteres
        return codigo != null && PATRON_CODIGO.matcher(codigo).matches();
    }

    // Métodos getters para acceder a los campos privados

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    // Métodos setters con validación

    /**
     * Actualiza la cantidad en stock
     * @param cantidad Nueva cantidad (debe ser >= 0)
     * @throws IllegalArgumentException Si la cantidad es negativa
     */
    public void setCantidad(int cantidad) {
        if (cantidad < 0) {
            throw new IllegalArgumentException("La cantidad no puede ser negativa");
        }
        this.cantidad = cantidad;
    }

    /**
     * Representación en String del producto
     * @return String formateada con los datos del producto
     */
    @Override
    public String toString() {
        return String.format("Código: %-10s Nombre: %-20s Cantidad: %d",
                codigo, nombre, cantidad);
    }
}

/**
 * Clase principal que gestiona el inventario de productos
 */
public class InventarioProductos {
    // Mapa para almacenar los productos (clave: código, valor: Producto)
    private static Map<String, Producto> inventario = new HashMap<>();
    // Scanner para leer entrada del usuario
    private static final Scanner scanner = new Scanner(System.in);
    // Nombre del archivo para persistencia
    private static final String ARCHIVO_INVENTARIO = "productos.dat";

    /**
     * Metodo principal que inicia la aplicación
     * @param args Argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        // Cargar inventario al iniciar
        cargarInventario();

        // Menú principal en bucle hasta que el usuario decida salir
        while (true) {
            System.out.println("\n=== GESTIÓN DE INVENTARIO ===");
            System.out.println("1. Añadir producto");
            System.out.println("2. Eliminar producto");
            System.out.println("3. Modificar producto");
            System.out.println("4. Mostrar productos");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");

            // Manejo de la opción del usuario
            try {
                int opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1:
                        añadirProducto();
                        break;
                    case 2:
                        eliminarProducto();
                        break;
                    case 3:
                        modificarProducto();
                        break;
                    case 4:
                        mostrarProductos();
                        break;
                    case 5:
                        guardarInventario();
                        System.out.println("Saliendo del sistema...");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Opción no válida. Intente nuevamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número válido.");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    /**
     * Añade un nuevo producto al inventario
     */
    private static void añadirProducto() {
        System.out.println("\n--- AÑADIR NUEVO PRODUCTO ---");

        // Solicitar código del producto
        System.out.print("Ingrese código (5-10 caracteres alfanuméricos): ");
        String codigo = scanner.nextLine();

        // Solicitar nombre del producto
        System.out.print("Ingrese nombre: ");
        String nombre = scanner.nextLine();

        // Solicitar cantidad
        System.out.print("Ingrese cantidad: ");
        try {
            int cantidad = Integer.parseInt(scanner.nextLine());

            // Crear y agregar el nuevo producto
            Producto producto = new Producto(codigo, nombre, cantidad);
            inventario.put(codigo, producto);
            System.out.println("Producto añadido correctamente.");
        } catch (NumberFormatException e) {
            System.out.println("Error: La cantidad debe ser un número entero");
        } catch (CodigoProductoInvalidoException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Elimina un producto del inventario
     */
    private static void eliminarProducto() {
        System.out.println("\n--- ELIMINAR PRODUCTO ---");
        System.out.print("Ingrese código del producto a eliminar: ");
        String codigo = scanner.nextLine();

        if (inventario.containsKey(codigo)) {
            inventario.remove(codigo);
            System.out.println("Producto eliminado correctamente.");
        } else {
            System.out.println("Error: No existe un producto con ese código");
        }
    }

    /**
     * Modifica un producto existente
     */
    private static void modificarProducto() {
        System.out.println("\n--- MODIFICAR PRODUCTO ---");
        System.out.print("Ingrese código del producto a modificar: ");
        String codigo = scanner.nextLine();

        if (!inventario.containsKey(codigo)) {
            System.out.println("Error: No existe un producto con ese código");
            return;
        }

        Producto producto = inventario.get(codigo);

        // Menú de modificación
        while (true) {
            System.out.println("\nProducto seleccionado:");
            System.out.println(producto);
            System.out.println("\n¿Qué desea modificar?");
            System.out.println("1. Nombre");
            System.out.println("2. Cantidad");
            System.out.println("3. Terminar modificación");
            System.out.print("Seleccione una opción: ");

            try {
                int opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1:
                        System.out.print("Nuevo nombre: ");
                        String nuevoNombre = scanner.nextLine();
                        producto = new Producto(producto.getCodigo(), nuevoNombre, producto.getCantidad());
                        inventario.put(codigo, producto);
                        System.out.println("Nombre actualizado.");
                        break;
                    case 2:
                        System.out.print("Nueva cantidad: ");
                        int nuevaCantidad = Integer.parseInt(scanner.nextLine());
                        producto.setCantidad(nuevaCantidad);
                        System.out.println("Cantidad actualizada.");
                        break;
                    case 3:
                        return;
                    default:
                        System.out.println("Opción no válida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: La cantidad debe ser un número entero");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    /**
     * Muestra todos los productos del inventario
     */
    private static void mostrarProductos() {
        System.out.println("\n--- LISTADO DE PRODUCTOS ---");

        if (inventario.isEmpty()) {
            System.out.println("No hay productos en el inventario.");
            return;
        }

        // Mostrar productos ordenados por código
        inventario.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> System.out.println(entry.getValue()));

        System.out.println("Total productos: " + inventario.size());
    }

    /**
     * Guarda el inventario en un archivo binario
     */
    private static void guardarInventario() {
        // Try-with-resources para garantizar que el ObjectOutputStream se cierre
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(ARCHIVO_INVENTARIO))) {
            oos.writeObject(inventario);
            System.out.println("Inventario guardado correctamente.");
        } catch (IOException e) {
            System.err.println("Error al guardar el inventario: " + e.getMessage());
        }
    }

    /**
     * Carga el inventario desde un archivo binario
     */
    @SuppressWarnings("unchecked")
    private static void cargarInventario() {
        File archivo = new File(ARCHIVO_INVENTARIO);

        // Verificar si el archivo existe antes de intentar leerlo
        if (!archivo.exists()) {
            System.out.println("No se encontró archivo de inventario. Se creará uno nuevo.");
            return;
        }

        // Try-with-resources para garantizar que el ObjectInputStream se cierre
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(ARCHIVO_INVENTARIO))) {
            inventario = (Map<String, Producto>) ois.readObject();
            System.out.println("Inventario cargado correctamente.");
        } catch (IOException e) {
            System.err.println("Error al cargar el inventario: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Error: Clase no encontrada al cargar inventario");
        }
    }
}