/**
 * Calendario de tarefas
 * Clase Tarefa con:
 * Nombre, descripción, fecha, hora, duración, estado.
 * Permite:
 * Crear, modificar, borrar tareas.
 * Validar datos (fecha/hora).
 * Guardar y cargar desde tarefas.dat.
 * Mostrar tareas activas, finalizadas.
 */

// Importaciones necesarias para el funcionamiento del programa
import java.io.*;                 // Para operaciones de entrada/salida
        import java.time.*;               // Para manejo de fechas y horas
        import java.time.format.*;        // Para formateo de fechas
        import java.util.*;               // Para colecciones y utilidades
import java.util.stream.Collectors;

/**
 * Enumeración que representa los posibles estados de una tarea
 */
enum EstadoTarefa {
    ACTIVA,      // Tarea pendiente de realizar
    FINALIZADA,  // Tarea completada
    CANCELADA    // Tarea cancelada
}

/**
 * Clase que representa una Tarea en el calendario
 */
class Tarefa implements Serializable {
    // Versión de serialización para control de compatibilidad
    private static final long serialVersionUID = 1L;

    // Campos privados para encapsular los datos de la tarea
    private String nome;          // Nombre de la tarea
    private String descricion;    // Descripción detallada
    private LocalDate data;       // Fecha de la tarea
    private LocalTime hora;       // Hora de la tarea
    private Duration duracion;    // Duración estimada
    private EstadoTarefa estado;  // Estado actual

    // Formateadores para fecha y hora
    public static final DateTimeFormatter FORMATO_DATA =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static final DateTimeFormatter FORMATO_HORA =
            DateTimeFormatter.ofPattern("HH:mm");

    /**
     * Constructor de la clase Tarefa
     * @param nome Nombre de la tarea (no puede ser nulo o vacío)
     * @param descricion Descripción de la tarea
     * @param data Fecha de la tarea (no puede ser nula)
     * @param hora Hora de la tarea (no puede ser nula)
     * @param duracion Duración en minutos (debe ser positiva)
     * @throws IllegalArgumentException Si algún parámetro no es válido
     */
    public Tarefa(String nome, String descricion, LocalDate data,
                  LocalTime hora, int duracion) {
        // Validación de parámetros
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome da tarefa non pode estar baleiro");
        }
        if (data == null) {
            throw new IllegalArgumentException("A data non pode ser nula");
        }
        if (hora == null) {
            throw new IllegalArgumentException("A hora non pode ser nula");
        }
        if (duracion <= 0) {
            throw new IllegalArgumentException("A duración debe ser positiva");
        }

        // Asignación de valores a los campos
        this.nome = nome.trim();
        this.descricion = (descricion != null) ? descricion.trim() : "";
        this.data = data;
        this.hora = hora;
        this.duracion = Duration.ofMinutes(duracion);
        this.estado = EstadoTarefa.ACTIVA; // Por defecto, la tarea está activa
    }

    // Métodos getters para acceder a los campos privados

    public String getNome() {
        return nome;
    }

    public String getDescricion() {
        return descricion;
    }

    public LocalDate getData() {
        return data;
    }

    public LocalTime getHora() {
        return hora;
    }

    public Duration getDuracion() {
        return duracion;
    }

    public EstadoTarefa getEstado() {
        return estado;
    }

    // Métodos setters con validación

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome da tarefa non pode estar baleiro");
        }
        this.nome = nome.trim();
    }

    public void setDescricion(String descricion) {
        this.descricion = (descricion != null) ? descricion.trim() : "";
    }

    public void setData(LocalDate data) {
        if (data == null) {
            throw new IllegalArgumentException("A data non pode ser nula");
        }
        this.data = data;
    }

    public void setHora(LocalTime hora) {
        if (hora == null) {
            throw new IllegalArgumentException("A hora non pode ser nula");
        }
        this.hora = hora;
    }

    public void setDuracion(int duracion) {
        if (duracion <= 0) {
            throw new IllegalArgumentException("A duración debe ser positiva");
        }
        this.duracion = Duration.ofMinutes(duracion);
    }

    /**
     * Cambia el estado de la tarea a FINALIZADA
     */
    public void finalizar() {
        this.estado = EstadoTarefa.FINALIZADA;
    }

    /**
     * Cambia el estado de la tarea a CANCELADA
     */
    public void cancelar() {
        this.estado = EstadoTarefa.CANCELADA;
    }

    /**
     * Representación en String de la tarea
     * @return String formateada con los datos de la tarea
     */
    @Override
    public String toString() {
        return String.format(
                "Nome: %s\nDescrición: %s\nData: %s\nHora: %s\nDuración: %d min\nEstado: %s",
                nome, descricion, data.format(FORMATO_DATA), hora.format(FORMATO_HORA),
                duracion.toMinutes(), estado.toString());
    }
}

/**
 * Clase principal que gestiona el calendario de tareas
 */
public class CalendarioTarefas {
    // Lista para almacenar todas las tareas
    private static List<Tarefa> tarefas = new ArrayList<>();
    // Scanner para leer entrada del usuario
    private static final Scanner scanner = new Scanner(System.in);
    // Nombre del archivo para persistencia
    private static final String ARQUIVO_TAREFAS = "tarefas.dat";

    /**
     * Metodo principal que inicia la aplicación
     * @param args Argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        // Cargar tareas al iniciar
        cargarTarefas();

        // Menú principal en bucle hasta que el usuario decida salir
        while (true) {
            System.out.println("\n=== CALENDARIO DE TAREFAS ===");
            System.out.println("1. Crear nova tarefa");
            System.out.println("2. Modificar tarefa existente");
            System.out.println("3. Borrar tarefa");
            System.out.println("4. Finalizar tarefa");
            System.out.println("5. Cancelar tarefa");
            System.out.println("6. Mostrar tarefas activas");
            System.out.println("7. Mostrar tarefas finalizadas");
            System.out.println("8. Gardar e saír");
            System.out.print("Seleccione unha opción: ");

            // Manejo de la opción del usuario
            try {
                int opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1:
                        crearTarefa();
                        break;
                    case 2:
                        modificarTarefa();
                        break;
                    case 3:
                        borrarTarefa();
                        break;
                    case 4:
                        finalizarTarefa();
                        break;
                    case 5:
                        cancelarTarefa();
                        break;
                    case 6:
                        mostrarTarefasActivas();
                        break;
                    case 7:
                        mostrarTarefasFinalizadas();
                        break;
                    case 8:
                        gardarTarefas();
                        System.out.println("Gardando datos e saíndo do sistema...");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Opción non válida. Intente novamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número válido.");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    /**
     * Crea una nueva tarea y la añade a la lista
     */
    private static void crearTarefa() {
        System.out.println("\n--- CREAR NOVA TAREFA ---");

        try {
            // Solicitar datos de la tarea
            System.out.print("Nome da tarefa: ");
            String nome = scanner.nextLine();

            System.out.print("Descrición (opcional): ");
            String descricion = scanner.nextLine();

            System.out.print("Data (dd/MM/yyyy): ");
            LocalDate data = LocalDate.parse(scanner.nextLine(), Tarefa.FORMATO_DATA);

            System.out.print("Hora (HH:mm): ");
            LocalTime hora = LocalTime.parse(scanner.nextLine(), Tarefa.FORMATO_HORA);

            System.out.print("Duración en minutos: ");
            int duracion = Integer.parseInt(scanner.nextLine());

            // Crear y agregar la nueva tarea
            Tarefa tarefa = new Tarefa(nome, descricion, data, hora, duracion);
            tarefas.add(tarefa);
            System.out.println("Tarefa creada correctamente.");
        } catch (DateTimeParseException e) {
            System.out.println("Error: Formato de data ou hora incorrecto.");
        } catch (NumberFormatException e) {
            System.out.println("Error: A duración debe ser un número entero.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Modifica una tarea existente
     */
    private static void modificarTarefa() {
        System.out.println("\n--- MODIFICAR TAREFA ---");

        // Mostrar tareas activas para selección
        List<Tarefa> activas = tarefas.stream()
                .filter(t -> t.getEstado() == EstadoTarefa.ACTIVA)
                .collect(Collectors.toList());

        if (activas.isEmpty()) {
            System.out.println("Non hay tarefas activas para modificar.");
            return;
        }

        System.out.println("Tarefas activas:");
        for (int i = 0; i < activas.size(); i++) {
            System.out.println((i + 1) + ". " + activas.get(i).getNome());
        }

        System.out.print("Seleccione a tarefa a modificar: ");
        try {
            int seleccion = Integer.parseInt(scanner.nextLine()) - 1;

            if (seleccion < 0 || seleccion >= activas.size()) {
                System.out.println("Selección non válida.");
                return;
            }

            Tarefa tarefa = activas.get(seleccion);

            // Menú de modificación
            while (true) {
                System.out.println("\nTarefa seleccionada:");
                System.out.println(tarefa);
                System.out.println("\n¿Qué desea modificar?");
                System.out.println("1. Nome");
                System.out.println("2. Descrición");
                System.out.println("3. Data");
                System.out.println("4. Hora");
                System.out.println("5. Duración");
                System.out.println("6. Terminar modificación");
                System.out.print("Seleccione unha opción: ");

                int opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1:
                        System.out.print("Novo nome: ");
                        tarefa.setNome(scanner.nextLine());
                        System.out.println("Nome actualizado.");
                        break;
                    case 2:
                        System.out.print("Nova descrición: ");
                        tarefa.setDescricion(scanner.nextLine());
                        System.out.println("Descrición actualizada.");
                        break;
                    case 3:
                        System.out.print("Nova data (dd/MM/yyyy): ");
                        tarefa.setData(LocalDate.parse(scanner.nextLine(), Tarefa.FORMATO_DATA));
                        System.out.println("Data actualizada.");
                        break;
                    case 4:
                        System.out.print("Nova hora (HH:mm): ");
                        tarefa.setHora(LocalTime.parse(scanner.nextLine(), Tarefa.FORMATO_HORA));
                        System.out.println("Hora actualizada.");
                        break;
                    case 5:
                        System.out.print("Nova duración (minutos): ");
                        tarefa.setDuracion(Integer.parseInt(scanner.nextLine()));
                        System.out.println("Duración actualizada.");
                        break;
                    case 6:
                        return;
                    default:
                        System.out.println("Opción non válida.");
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Debe ingresar un número válido.");
        } catch (DateTimeParseException e) {
            System.out.println("Error: Formato de data ou hora incorrecto.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Borra una tarea de la lista
     */
    private static void borrarTarefa() {
        System.out.println("\n--- BORRAR TAREFA ---");

        // Mostrar todas las tareas para selección
        if (tarefas.isEmpty()) {
            System.out.println("Non hay tarefas rexistradas.");
            return;
        }

        System.out.println("Tarefas rexistradas:");
        for (int i = 0; i < tarefas.size(); i++) {
            System.out.println((i + 1) + ". " + tarefas.get(i).getNome() +
                    " (" + tarefas.get(i).getEstado() + ")");
        }

        System.out.print("Seleccione a tarefa a borrar: ");
        try {
            int seleccion = Integer.parseInt(scanner.nextLine()) - 1;

            if (seleccion < 0 || seleccion >= tarefas.size()) {
                System.out.println("Selección non válida.");
                return;
            }

            Tarefa tarefa = tarefas.get(seleccion);
            tarefas.remove(seleccion);
            System.out.println("Tarefa '" + tarefa.getNome() + "' borrada correctamente.");
        } catch (NumberFormatException e) {
            System.out.println("Error: Debe ingresar un número válido.");
        }
    }

    /**
     * Cambia el estado de una tarea a FINALIZADA
     */
    private static void finalizarTarefa() {
        System.out.println("\n--- FINALIZAR TAREFA ---");
        List<Tarefa> activas = tarefas.stream()
                .filter(t -> t.getEstado() == EstadoTarefa.ACTIVA)
                .collect(Collectors.toList());

        if (activas.isEmpty()) {
            System.out.println("Non hay tarefas activas para finalizar.");
            return;
        }

        System.out.println("Tarefas activas:");
        for (int i = 0; i < activas.size(); i++) {
            System.out.println((i + 1) + ". " + activas.get(i).getNome());
        }

        System.out.print("Seleccione a tarefa a finalizar: ");
        try {
            int seleccion = Integer.parseInt(scanner.nextLine()) - 1;

            if (seleccion < 0 || seleccion >= activas.size()) {
                System.out.println("Selección non válida.");
                return;
            }

            Tarefa tarefa = activas.get(seleccion);
            tarefa.finalizar();
            System.out.println("Tarefa '" + tarefa.getNome() + "' finalizada correctamente.");
        } catch (NumberFormatException e) {
            System.out.println("Error: Debe ingresar un número válido.");
        }
    }

    /**
     * Cambia el estado de una tarea a CANCELADA
     */
    private static void cancelarTarefa() {
        System.out.println("\n--- CANCELAR TAREFA ---");
        List<Tarefa> activas = tarefas.stream()
                .filter(t -> t.getEstado() == EstadoTarefa.ACTIVA)
                .collect(Collectors.toList());

        if (activas.isEmpty()) {
            System.out.println("Non hay tarefas activas para cancelar.");
            return;
        }

        System.out.println("Tarefas activas:");
        for (int i = 0; i < activas.size(); i++) {
            System.out.println((i + 1) + ". " + activas.get(i).getNome());
        }

        System.out.print("Seleccione a tarefa a cancelar: ");
        try {
            int seleccion = Integer.parseInt(scanner.nextLine()) - 1;

            if (seleccion < 0 || seleccion >= activas.size()) {
                System.out.println("Selección non válida.");
                return;
            }

            Tarefa tarefa = activas.get(seleccion);
            tarefa.cancelar();
            System.out.println("Tarefa '" + tarefa.getNome() + "' cancelada correctamente.");
        } catch (NumberFormatException e) {
            System.out.println("Error: Debe ingresar un número válido.");
        }
    }

    /**
     * Muestra todas las tareas activas
     */
    private static void mostrarTarefasActivas() {
        System.out.println("\n--- TAREFAS ACTIVAS ---");
        List<Tarefa> activas = tarefas.stream()
                .filter(t -> t.getEstado() == EstadoTarefa.ACTIVA)
                .collect(Collectors.toList());

        if (activas.isEmpty()) {
            System.out.println("Non hay tarefas activas.");
            return;
        }

        activas.forEach(System.out::println);
        System.out.println("Total: " + activas.size() + " tarefas activas.");
    }

    /**
     * Muestra todas las tareas finalizadas
     */
    private static void mostrarTarefasFinalizadas() {
        System.out.println("\n--- TAREFAS FINALIZADAS ---");
        List<Tarefa> finalizadas = tarefas.stream()
                .filter(t -> t.getEstado() == EstadoTarefa.FINALIZADA)
                .collect(Collectors.toList());

        if (finalizadas.isEmpty()) {
            System.out.println("Non hay tarefas finalizadas.");
            return;
        }

        finalizadas.forEach(System.out::println);
        System.out.println("Total: " + finalizadas.size() + " tarefas finalizadas.");
    }

    /**
     * Guarda las tareas en un archivo binario
     */
    private static void gardarTarefas() {
        // Try-with-resources para garantizar que el ObjectOutputStream se cierre
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(ARQUIVO_TAREFAS))) {
            oos.writeObject(tarefas);
            System.out.println("Tarefas gardadas en " + ARQUIVO_TAREFAS);
        } catch (IOException e) {
            System.err.println("Error ao gardar as tarefas: " + e.getMessage());
        }
    }

    /**
     * Carga las tareas desde un archivo binario
     */
    @SuppressWarnings("unchecked")
    private static void cargarTarefas() {
        File arquivo = new File(ARQUIVO_TAREFAS);

        // Verificar si el archivo existe antes de intentar leerlo
        if (!arquivo.exists()) {
            System.out.println("Non se encontró arquivo de tarefas. Comezando con lista baleira.");
            return;
        }

        // Try-with-resources para garantizar que el ObjectInputStream se cierre
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(ARQUIVO_TAREFAS))) {
            tarefas = (List<Tarefa>) ois.readObject();
            System.out.println("Tarefas cargadas desde " + ARQUIVO_TAREFAS);
        } catch (IOException e) {
            System.err.println("Error ao cargar as tarefas: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Error: Clase non encontrada ao cargar tarefas");
        }
    }
}