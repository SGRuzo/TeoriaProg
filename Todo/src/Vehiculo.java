import java.io.*;
import java.util.*;

/**
 * Clase principal que representa un vehículo en el sistema de parking.
 * Implementa Serializable para permitir la serialización de objetos.
 */
public class Vehiculo implements Serializable {
    // Atributos de la clase Vehiculo
    private String matricula;       // Matrícula del vehículo (formato: 4 dígitos + 3 letras)
    private String marca;           // Marca del vehículo (ej: Toyota, Ford)
    private String modelo;          // Modelo específico del vehículo
    private int añoFabricacion;     // Año de fabricación (entre 1950 y año actual)
    private int velocidadActual;    // Velocidad actual (0-200 km/h)

    /**
     * Constructor principal de la clase vehiculo.
     * @param matricula Matrícula del vehículo (debe cumplir formato)
     * @param marca Marca del vehículo
     * @param modelo Modelo del vehículo
     * @param añoFabricacion Año de fabricación
     * @param velocidadActual Velocidad actual en km/h
     * @throws MatriculaNoValidaException Si la matrícula no cumple el formato
     * @throws VelocidadNoValidaException Si la velocidad está fuera de rango
     */
    public Vehiculo(String matricula, String marca, String modelo, int añoFabricacion, int velocidadActual)
            throws MatriculaNoValidaException, VelocidadNoValidaException {
        setMatricula(matricula);    // Validación de matrícula
        this.marca = marca;
        this.modelo = modelo;
        this.añoFabricacion = añoFabricacion;
        setVelocidadActual(velocidadActual); // Validación de velocidad
    }

    // Métodos getters y setters con validaciones

    /**
     * Obtiene la matrícula del vehículo.
     * @return String con la matrícula
     */
    public String getMatricula() {
        return matricula;
    }

    /**
     * Establece la matrícula del vehículo con validación de formato.
     * @param matricula Matrícula a establecer
     * @throws MatriculaNoValidaException Si el formato no es válido
     */
    public void setMatricula(String matricula) throws MatriculaNoValidaException {
        // Validación con expresión regular: 4 dígitos + 3 letras mayúsculas
        if (!matricula.matches("\\d{4}[A-Z]{3}")) {
            throw new MatriculaNoValidaException("Formato de matrícula inválido. Debe ser 4 dígitos + 3 letras (ej: 1234ABC)");
        }
        this.matricula = matricula;
    }

    /**
     * Obtiene la marca del vehículo.
     * @return String con la marca
     */
    public String getMarca() {
        return marca;
    }

    /**
     * Establece la marca del vehículo.
     * @param marca Marca a establecer
     */
    public void setMarca(String marca) {
        this.marca = marca;
    }

    /**
     * Obtiene el modelo del vehículo.
     * @return String con el modelo
     */
    public String getModelo() {
        return modelo;
    }

    /**
     * Establece el modelo del vehículo.
     * @param modelo Modelo a establecer
     */
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    /**
     * Obtiene el año de fabricación.
     * @return int con el año de fabricación
     */
    public int getAñoFabricacion() {
        return añoFabricacion;
    }

    /**
     * Establece el año de fabricación.
     * @param añoFabricacion Año a establecer
     */
    public void setAñoFabricacion(int añoFabricacion) {
        this.añoFabricacion = añoFabricacion;
    }

    /**
     * Obtiene la velocidad actual.
     * @return int con la velocidad en km/h
     */
    public int getVelocidadActual() {
        return velocidadActual;
    }

    /**
     * Establece la velocidad actual con validación de rango.
     * @param velocidadActual Velocidad a establecer (0-200 km/h)
     * @throws VelocidadNoValidaException Si está fuera de rango
     */
    public void setVelocidadActual(int velocidadActual) throws VelocidadNoValidaException {
        if (velocidadActual < 0 || velocidadActual > 200) {
            throw new VelocidadNoValidaException("La velocidad debe estar entre 0 y 200 km/h");
        }
        this.velocidadActual = velocidadActual;
    }

    /**
     * Aumenta la velocidad del vehículo.
     * @param incremento Cantidad a aumentar
     * @throws VelocidadNoValidaException Si supera el límite máximo
     */
    public void aumentarVelocidad(int incremento) throws VelocidadNoValidaException {
        setVelocidadActual(this.velocidadActual + incremento);
    }

    /**
     * Disminuye la velocidad del vehículo.
     * @param decremento Cantidad a disminuir
     * @throws VelocidadNoValidaException Si es menor que 0
     */
    public void disminuirVelocidad(int decremento) throws VelocidadNoValidaException {
        setVelocidadActual(this.velocidadActual - decremento);
    }

    /**
     * Representación en String del vehículo.
     * @return String con los datos formateados
     */
    @Override
    public String toString() {
        return String.format("📌 %s - %s %s (%d) - Velocidad: %d km/h",
                matricula, marca, modelo, añoFabricacion, velocidadActual);
    }

    // ==================== CLASES DE EXCEPCIÓN INTERNAS ====================

    /**
     * Excepción para matrículas inválidas.
     */
    public static class MatriculaNoValidaException extends Exception {
        public MatriculaNoValidaException(String mensaje) {
            super(mensaje);
        }
    }

    /**
     * Excepción para velocidades inválidas.
     */
    public static class VelocidadNoValidaException extends Exception {
        public VelocidadNoValidaException(String mensaje) {
            super(mensaje);
        }
    }

    /**
     * Excepción para vehículos duplicados.
     */
    public static class VehiculoYaExisteException extends Exception {
        public VehiculoYaExisteException(String mensaje) {
            super(mensaje);
        }
    }

    /**
     * Excepción para vehículos no encontrados.
     */
    public static class VehiculoNoEncontradoException extends Exception {
        public VehiculoNoEncontradoException(String mensaje) {
            super(mensaje);
        }
    }
}

/**
 * Clase principal con el menú interactivo.
 */
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        GestorParking gestor = new GestorParking();

        int opcion;
        do {
            mostrarMenu();
            try {
                opcion = Integer.parseInt(sc.nextLine());
                switch (opcion) {
                    case 1 -> añadirVehiculo(sc, gestor);
                    case 2 -> gestor.mostrarVehiculos();
                    case 3 -> buscarVehiculo(sc, gestor);
                    case 4 -> modificarVehiculo(sc, gestor);
                    case 5 -> eliminarVehiculo(sc, gestor);
                    case 6 -> aumentarVelocidad(sc, gestor);
                    case 7 -> disminuirVelocidad(sc, gestor);
                    case 8 -> verPorMarca(sc, gestor);
                    case 9 -> verPorAnio(sc, gestor);
                    case 10 -> verEstadisticas(gestor);
                    case 11 -> gestor.guardarDatos();
                    case 12 -> {
                        gestor.guardarDatos();
                        System.out.println("👋 Saliendo del programa. ¡Hasta pronto!");
                    }
                    default -> System.out.println("❗ Opción no válida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Error: introduce un número válido.");
                opcion = -1;
            }
        } while (opcion != 12);
    }

    // Resto de métodos auxiliares para el menú...
    // [Se mantienen igual que en el código original]
}