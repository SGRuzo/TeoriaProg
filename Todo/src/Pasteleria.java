// Importamos las clases necesarias para el programa
import java.io.*; // Para leer/escribir archivos
        import java.util.*; // Para usar ArrayList y Scanner
        import java.time.LocalDate; // Para manejar fechas

/**
 * Clase principal que maneja la pastelería Dulce Delirio
 * Versión simplificada con comentarios detallados en cada línea
 */
public class Pasteleria {

    // ==================== CLASE INGREDIENTE ====================
    /**
     * Clase que representa un ingrediente de pastelería
     */
    static class Ingrediente implements Serializable {
        private String nombre; // Nombre del ingrediente (ej: "harina")
        private int cantidad;  // Cantidad en gramos

        // Constructor: crea un nuevo ingrediente
        public Ingrediente(String nombre, int cantidad) {
            this.nombre = nombre; // Asigna el nombre
            this.cantidad = cantidad; // Asigna la cantidad
        }

        // Métodos para acceder a los datos (getters)
        public String getNombre() { return nombre; }
        public int getCantidad() { return cantidad; }

        // Metodo para reducir la cantidad del ingrediente
        public void usar(int gramos) {
            if(gramos > cantidad) { // Verifica si hay suficiente
                System.out.println("¡No hay suficiente "+nombre+"!");
                return;
            }
            cantidad -= gramos; // Reduce la cantidad
        }

        // Metodo para añadir más cantidad
        public void añadir(int gramos) {
            cantidad += gramos; // Aumenta la cantidad
        }

        // Metodo para mostrar el ingrediente
        @Override
        public String toString() {
            return nombre + ": " + cantidad + "g";
        }
    }

    // ==================== CLASE PRODUCTO ====================
    /**
     * Clase que representa un producto de pastelería
     */
    static class Producto implements Serializable {
        private String nombre; // Nombre del producto (ej: "tarta de chocolate")
        private double precio; // Precio en euros
        private ArrayList<Ingrediente> ingredientes; // Lista de ingredientes necesarios

        // Constructor: crea un nuevo producto
        public Producto(String nombre, double precio) {
            this.nombre = nombre;
            this.precio = precio;
            this.ingredientes = new ArrayList<>(); // Lista vacía al inicio
        }

        // Métodos para acceder a los datos
        public String getNombre() { return nombre; }
        public double getPrecio() { return precio; }
        public ArrayList<Ingrediente> getIngredientes() { return ingredientes; }

        // Metodo para añadir un ingrediente al producto
        public void añadirIngrediente(Ingrediente ing, int cantidad) {
            ingredientes.add(new Ingrediente(ing.getNombre(), cantidad));
        }

        // Metodo para verificar si se puede preparar (hay ingredientes suficientes)
        public boolean sePuedePreparar(ArrayList<Ingrediente> stock) {
            for(Ingrediente ingProducto : ingredientes) { // Para cada ingrediente del producto
                boolean encontrado = false;
                for(Ingrediente ingStock : stock) { // Busca en el stock
                    if(ingProducto.getNombre().equals(ingStock.getNombre())) {
                        if(ingProducto.getCantidad() > ingStock.getCantidad()) {
                            return false; // No hay suficiente cantidad
                        }
                        encontrado = true;
                        break;
                    }
                }
                if(!encontrado) return false; // No se encontró el ingrediente
            }
            return true; // Todos los ingredientes están disponibles
        }

        // Metodo para mostrar el producto
        @Override
        public String toString() {
            return nombre + " - " + precio + "€";
        }
    }

    // ==================== CLASE CLIENTE ====================
    /**
     * Clase que representa un cliente de la pastelería
     */
    static class Cliente implements Serializable {
        private String nombre; // Nombre del cliente
        private String telefono; // Teléfono de contacto

        // Constructor: crea un nuevo cliente
        public Cliente(String nombre, String telefono) {
            this.nombre = nombre;
            this.telefono = telefono;
        }

        // Métodos para acceder a los datos
        public String getNombre() { return nombre; }
        public String getTelefono() { return telefono; }

        // Metodo para mostrar el cliente
        @Override
        public String toString() {
            return nombre + " (" + telefono + ")";
        }
    }

    // ==================== CLASE PEDIDO ====================
    /**
     * Clase que representa un pedido realizado
     */
    static class Pedido implements Serializable {
        private Cliente cliente; // Cliente que hizo el pedido
        private ArrayList<Producto> productos; // Lista de productos pedidos
        private LocalDate fecha; // Fecha del pedido

        // Constructor: crea un nuevo pedido
        public Pedido(Cliente cliente) {
            this.cliente = cliente;
            this.productos = new ArrayList<>();
            this.fecha = LocalDate.now(); // Fecha actual
        }

        // Metodo para añadir un producto al pedido
        public void añadirProducto(Producto p) {
            productos.add(p);
        }

        // Metodo para calcular el total del pedido
        public double getTotal() {
            double total = 0;
            for(Producto p : productos) {
                total += p.getPrecio(); // Suma los precios
            }
            return total;
        }

        // Metodo para mostrar el pedido
        @Override
        public String toString() {
            return "Pedido de " + cliente.getNombre() + " - Total: " + getTotal() + "€";
        }
    }

    // ==================== DATOS DE LA PASTELERÍA ====================
    // Listas para almacenar toda la información
    private static ArrayList<Ingrediente> ingredientes = new ArrayList<>();
    private static ArrayList<Producto> productos = new ArrayList<>();
    private static ArrayList<Cliente> clientes = new ArrayList<>();
    private static ArrayList<Pedido> pedidos = new ArrayList<>();

    // Scanner para leer la entrada del usuario
    private static Scanner scanner = new Scanner(System.in);

    // ==================== METODO PRINCIPAL ====================
    public static void main(String[] args) {
        cargarDatos(); // Cargar datos al iniciar

        // Menú principal
        while(true) {
            System.out.println("\n=== PASTELERÍA DULCE DELIRIO ===");
            System.out.println("1. Añadir Ingrediente");
            System.out.println("2. Añadir Producto");
            System.out.println("3. Registrar Cliente");
            System.out.println("4. Hacer Pedido");
            System.out.println("5. Mostrar Reportes");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch(opcion) {
                case 1: añadirIngrediente(); break;
                case 2: añadirProducto(); break;
                case 3: registrarCliente(); break;
                case 4: hacerPedido(); break;
                case 5: mostrarReportes(); break;
                case 6:
                    guardarDatos(); // Guardar antes de salir
                    System.out.println("¡Gracias por usar el sistema!");
                    return; // Salir del programa
                default:
                    System.out.println("Opción no válida");
            }
        }
    }

    // ==================== MÉTODOS PARA AÑADIR DATOS ====================

    /**
     * Metodo para añadir un nuevo ingrediente al stock
     */
    private static void añadirIngrediente() {
        System.out.print("\nNombre del ingrediente: ");
        String nombre = scanner.nextLine();

        System.out.print("Cantidad en gramos: ");
        int cantidad = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer

        // Crear y añadir el nuevo ingrediente
        ingredientes.add(new Ingrediente(nombre, cantidad));
        System.out.println("¡Ingrediente añadido!");
    }

    /**
     * Metodo para añadir un nuevo producto
     */
    private static void añadirProducto() {
        System.out.print("\nNombre del producto: ");
        String nombre = scanner.nextLine();

        System.out.print("Precio en euros: ");
        double precio = scanner.nextDouble();
        scanner.nextLine(); // Limpiar buffer

        // Crear el nuevo producto
        Producto nuevo = new Producto(nombre, precio);

        // Añadir ingredientes al producto
        System.out.println("\nIngredientes disponibles:");
        for(int i = 0; i < ingredientes.size(); i++) {
            System.out.println((i+1) + ". " + ingredientes.get(i));
        }

        System.out.println("Añade ingredientes (0 para terminar):");
        while(true) {
            System.out.print("Número de ingrediente: ");
            int num = scanner.nextInt();
            if(num == 0) break; // Terminar

            System.out.print("Cantidad necesaria (gramos): ");
            int cantidad = scanner.nextInt();

            // Añadir el ingrediente al producto
            nuevo.añadirIngrediente(ingredientes.get(num-1), cantidad);
        }
        scanner.nextLine(); // Limpiar buffer

        // Añadir el producto a la lista
        productos.add(nuevo);
        System.out.println("¡Producto añadido!");
    }

    /**
     * Metodo para registrar un nuevo cliente
     */
    private static void registrarCliente() {
        System.out.print("\nNombre del cliente: ");
        String nombre = scanner.nextLine();

        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine();

        // Crear y añadir el nuevo cliente
        clientes.add(new Cliente(nombre, telefono));
        System.out.println("¡Cliente registrado!");
    }

    // ==================== MÉTODOS PARA PEDIDOS ====================

    /**
     * Metodo para realizar un nuevo pedido
     */
    private static void hacerPedido() {
        if(clientes.isEmpty()) {
            System.out.println("\nNo hay clientes registrados");
            return;
        }

        if(productos.isEmpty()) {
            System.out.println("\nNo hay productos disponibles");
            return;
        }

        // Seleccionar cliente
        System.out.println("\nClientes registrados:");
        for(int i = 0; i < clientes.size(); i++) {
            System.out.println((i+1) + ". " + clientes.get(i));
        }

        System.out.print("Seleccione un cliente: ");
        int numCliente = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer

        // Crear el pedido
        Pedido pedido = new Pedido(clientes.get(numCliente-1));

        // Añadir productos al pedido
        System.out.println("\nProductos disponibles:");
        for(int i = 0; i < productos.size(); i++) {
            System.out.println((i+1) + ". " + productos.get(i));
        }

        System.out.println("Añade productos (0 para terminar):");
        while(true) {
            System.out.print("Número de producto: ");
            int num = scanner.nextInt();
            if(num == 0) break; // Terminar

            // Verificar si hay ingredientes suficientes
            Producto p = productos.get(num-1);
            if(p.sePuedePreparar(ingredientes)) {
                pedido.añadirProducto(p);
                System.out.println("¡Producto añadido!");
            } else {
                System.out.println("No se puede preparar este producto ahora");
            }
        }
        scanner.nextLine(); // Limpiar buffer

        // Añadir el pedido a la lista
        pedidos.add(pedido);
        System.out.println("¡Pedido realizado! Total: " + pedido.getTotal() + "€");
    }

    // ==================== MÉTODOS PARA REPORTES ====================

    /**
     * Metodo para mostrar reportes de información
     */
    private static void mostrarReportes() {
        System.out.println("\n=== REPORTES ===");
        System.out.println("1. Ingredientes en stock");
        System.out.println("2. Productos disponibles");
        System.out.println("3. Clientes registrados");
        System.out.println("4. Historial de pedidos");
        System.out.print("Seleccione una opción: ");

        int opcion = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer

        switch(opcion) {
            case 1:
                System.out.println("\nINGREDIENTES EN STOCK:");
                for(Ingrediente ing : ingredientes) {
                    System.out.println(ing);
                }
                break;
            case 2:
                System.out.println("\nPRODUCTOS DISPONIBLES:");
                for(Producto p : productos) {
                    System.out.println(p);
                }
                break;
            case 3:
                System.out.println("\nCLIENTES REGISTRADOS:");
                for(Cliente c : clientes) {
                    System.out.println(c);
                }
                break;
            case 4:
                System.out.println("\nHISTORIAL DE PEDIDOS:");
                for(Pedido ped : pedidos) {
                    System.out.println(ped);
                }
                break;
            default:
                System.out.println("Opción no válida");
        }
    }

    // ==================== MÉTODOS PARA PERSISTENCIA ====================

    /**
     * Metodo para guardar todos los datos en archivos
     */
    private static void guardarDatos() {
        guardarLista(ingredientes, "ingredientes.dat");
        guardarLista(productos, "productos.dat");
        guardarLista(clientes, "clientes.dat");
        guardarLista(pedidos, "pedidos.dat");
        System.out.println("Datos guardados correctamente");
    }

    /**
     * Metodo genérico para guardar una lista en archivo
     */
    private static <T> void guardarLista(ArrayList<T> lista, String archivo) {
        try {
            FileOutputStream fos = new FileOutputStream(archivo);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(lista); // Escribe la lista completa
            oos.close();
        } catch(Exception e) {
            System.out.println("Error al guardar " + archivo);
        }
    }

    /**
     * Metodo para cargar todos los datos desde archivos
     */
    @SuppressWarnings("unchecked")
    private static void cargarDatos() {
        ingredientes = cargarLista("ingredientes.dat");
        productos = cargarLista("productos.dat");
        clientes = cargarLista("clientes.dat");
        pedidos = cargarLista("pedidos.dat");
        System.out.println("Datos cargados correctamente");
    }

    /**
     * Metodo genérico para cargar una lista desde archivo
     */
    private static <T> ArrayList<T> cargarLista(String archivo) {
        try {
            FileInputStream fis = new FileInputStream(archivo);
            ObjectInputStream ois = new ObjectInputStream(fis);
            ArrayList<T> lista = (ArrayList<T>) ois.readObject(); // Lee la lista
            ois.close();
            return lista;
        } catch(Exception e) {
            return new ArrayList<>(); // Si hay error, retorna lista vacía
        }
    }
}