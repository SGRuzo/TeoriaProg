import java.io.*;
import java.util.HashMap;
import java.util.Map; /**
 * Clase que gestiona las operaciones del parking.
 */
public class GestorParking {
    // Mapa que almacena los vehículos (clave: matrícula)
    private Map<String, Vehiculo> vehiculos;
    // Nombre del archivo para persistencia
    private final String FICHERO_DATOS = "vehiculos.dat";

    /**
     * Constructor del gestor de parking.
     * Inicializa el mapa y carga los datos existentes.
     */
    public GestorParking() {
        vehiculos = new HashMap<>();
        cargarDatos();
    }

    /**
     * Añade un nuevo vehículo al parking.
     * @param v Vehículo a añadir
     * @throws Vehiculo.VehiculoYaExisteException Si ya existe un vehículo con esa matrícula
     */
    public void anadirVehiculo(Vehiculo v) throws Vehiculo.VehiculoYaExisteException {
        if (vehiculos.containsKey(v.getMatricula())) {
            throw new Vehiculo.VehiculoYaExisteException("⚠️ Ya existe un vehículo con esa matrícula.");
        }
        vehiculos.put(v.getMatricula(), v);
        System.out.println("✅ Vehículo añadido correctamente.");
    }

    /**
     * Busca un vehículo por matrícula.
     * @param matricula Matrícula a buscar
     * @return Vehículo encontrado
     * @throws Vehiculo.VehiculoNoEncontradoException Si no se encuentra el vehículo
     */
    public Vehiculo buscarVehiculo(String matricula) throws Vehiculo.VehiculoNoEncontradoException {
        Vehiculo v = vehiculos.get(matricula);
        if (v == null) {
            throw new Vehiculo.VehiculoNoEncontradoException("❌ Vehículo no encontrado.");
        }
        return v;
    }

    /**
     * Elimina un vehículo del parking.
     * @param matricula Matrícula del vehículo a eliminar
     * @throws Vehiculo.VehiculoNoEncontradoException Si no se encuentra el vehículo
     */
    public void eliminarVehiculo(String matricula) throws Vehiculo.VehiculoNoEncontradoException {
        if (!vehiculos.containsKey(matricula)) {
            throw new Vehiculo.VehiculoNoEncontradoException("❌ No se puede eliminar: vehículo no encontrado.");
        }
        vehiculos.remove(matricula);
        System.out.println("✅ Vehículo eliminado.");
    }

    /**
     * Muestra todos los vehículos en el parking.
     */
    public void mostrarVehiculos() {
        if (vehiculos.isEmpty()) {
            System.out.println("🚫 No hay vehículos registrados.");
            return;
        }
        System.out.println("\n🚗 Lista de vehículos:\n--------------------");
        for (Vehiculo v : vehiculos.values()) {
            System.out.println(v);
            System.out.println("--------------------");
        }
    }

    /**
     * Guarda los datos en un archivo binario.
     */
    public void guardarDatos() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FICHERO_DATOS))) {
            oos.writeObject(vehiculos);
            System.out.println("💾 Datos guardados correctamente.");
        } catch (IOException e) {
            System.out.println("❌ Error al guardar los datos: " + e.getMessage());
        }
    }

    /**
     * Carga los datos desde un archivo binario.
     */
    @SuppressWarnings("unchecked")
    public void cargarDatos() {
        File fichero = new File(FICHERO_DATOS);
        if (!fichero.exists()) {
            System.out.println("📁 No se encontró fichero anterior. Se creará uno nuevo.");
            return;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FICHERO_DATOS))) {
            vehiculos = (Map<String, Vehiculo>) ois.readObject();
            System.out.println("📥 Datos cargados correctamente desde el fichero.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("❌ Error al cargar los datos: " + e.getMessage());
        }
    }

    /**
     * Obtiene el mapa completo de vehículos.
     * @return Mapa de vehículos
     */
    public Map<String, Vehiculo> getVehiculos() {
        return vehiculos;
    }
}
