package Ficheiros.Clase.Boletin12_1;
import java.io.*;
import java.util.*;


public class XestionClientes {

    // Ruta del fichero binario que guardará la información
    private static final String RUTA_FICHEIRO = "clientes.dat";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Cliente> clientes = cargarClientes();

        int opcion;
        do {
            // Mostramos el menú
            System.out.println("\n--- MENÚ DE XESTIÓN DE CLIENTES ---");
            System.out.println("1. Engadir novo cliente");
            System.out.println("2. Modificar cliente");
            System.out.println("3. Dar de baixa cliente");
            System.out.println("4. Listar clientes");
            System.out.println("0. Saír");

            System.out.print("Escolle unha opción: ");
            opcion = sc.nextInt();
            sc.nextLine(); // Limpiamos buffer

            switch (opcion) {
                case 1:
                    engadirCliente(clientes, sc);
                    break;
                case 2:
                    modificarCliente(clientes, sc);
                    break;
                case 3:
                    eliminarCliente(clientes, sc);
                    break;
                case 4:
                    listarClientes(clientes);
                    break;
                case 0:
                    // Al salir, guardamos los clientes en disco
                    gardarClientes(clientes);
                    System.out.println("Datos gardados. Ata logo!");
                    break;
                default:
                    System.out.println("Opción non válida.");
            }
        } while (opcion != 0);

        sc.close();
    }

    // Metodo para engadir un cliente á lista
    private static void engadirCliente(ArrayList<Cliente> clientes, Scanner sc) {
        System.out.print("Introduce ID: ");
        int id = sc.nextInt();
        sc.nextLine(); // Limpiamos buffer

        // Verificamos que no exista ya un cliente con ese ID
        for (Cliente c : clientes) {
            if (c.getId() == id) {
                System.out.println("Xa existe un cliente con ese ID.");
                return;
            }
        }

        System.out.print("Introduce nome: ");
        String nome = sc.nextLine();

        System.out.print("Introduce teléfono: ");
        String telefono = sc.nextLine();

        Cliente novo = new Cliente(id, nome, telefono);
        clientes.add(novo);
        System.out.println("Cliente engadido correctamente.");
    }

    // Metodo para modificar un cliente xa existente
    private static void modificarCliente(ArrayList<Cliente> clientes, Scanner sc) {
        System.out.print("Introduce ID do cliente a modificar: ");
        int id = sc.nextInt();
        sc.nextLine(); // Limpiamos buffer

        for (Cliente c : clientes) {
            if (c.getId() == id) {
                System.out.print("Novo nome (" + c.getNome() + "): ");
                String novoNome = sc.nextLine();
                if (!novoNome.isEmpty()) {
                    c.setNome(novoNome);
                }

                System.out.print("Novo teléfono (" + c.getTelefono() + "): ");
                String novoTelefono = sc.nextLine();
                if (!novoTelefono.isEmpty()) {
                    c.setTelefono(novoTelefono);
                }

                System.out.println("Cliente modificado.");
                return;
            }
        }

        System.out.println("Non se atopou o cliente co ID indicado.");
    }

    // Metodo para eliminar un cliente polo seu ID
    private static void eliminarCliente(ArrayList<Cliente> clientes, Scanner sc) {
        System.out.print("Introduce ID do cliente a eliminar: ");
        int id = sc.nextInt();
        sc.nextLine(); // Limpiamos buffer

        Iterator<Cliente> it = clientes.iterator();
        while (it.hasNext()) {
            Cliente c = it.next();
            if (c.getId() == id) {
                it.remove();
                System.out.println("Cliente eliminado.");
                return;
            }
        }

        System.out.println("Cliente non atopado.");
    }

    // Metodo para mostrar todos os clientes
    private static void listarClientes(ArrayList<Cliente> clientes) {
        if (clientes.isEmpty()) {
            System.out.println("Non hai clientes rexistrados.");
            return;
        }

        System.out.println("\n--- LISTA DE CLIENTES ---");
        for (Cliente c : clientes) {
            System.out.println(c);
        }
    }

    // Metodo para gardar a lista de clientes nun ficheiro binario
    private static void gardarClientes(ArrayList<Cliente> clientes) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(RUTA_FICHEIRO))) {
            oos.writeObject(clientes); // Gardamos toda a lista
        } catch (IOException e) {
            System.err.println("Erro ao gardar os datos: " + e.getMessage());
        }
    }

    // Metodo para cargar os clientes desde o ficheiro binario
    private static ArrayList<Cliente> cargarClientes() {
        File ficheiro = new File(RUTA_FICHEIRO);
        if (!ficheiro.exists()) {
            return new ArrayList<>(); // Se o ficheiro non existe, devolvemos unha lista baleira
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ficheiro))) {
            return (ArrayList<Cliente>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao cargar os datos: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}