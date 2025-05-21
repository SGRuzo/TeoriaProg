package Ficheiros.Clase.Boletin12_1;
import java.io.*;

// Clase Cliente con los atributos básicos de un cliente
class Cliente implements Serializable {
    private int id;
    private String nome;
    private String telefono;

    // Constructor para crear un cliente
    public Cliente(int id, String nome, String telefono) {
        this.id = id;
        this.nome = nome;
        this.telefono = telefono;
    }

    // Getters e setters para cada atributo
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    // Metodo para mostrar la información del cliente de forma bonita
    @Override
    public String toString() {
        return "ID: " + id + ", Nome: " + nome + ", Teléfono: " + telefono;
    }
}