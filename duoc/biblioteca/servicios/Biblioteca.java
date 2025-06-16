package duoc.biblioteca.servicios;

import duoc.biblioteca.modelos.Libro;
import duoc.biblioteca.modelos.Usuario;
import duoc.biblioteca.excepciones.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Biblioteca {

    private final ArrayList<Libro> libros = new ArrayList<>();
    private final HashMap<String, Usuario> usuarios = new HashMap<>();

    // Leer libros desde un archivo CSV
    public void cargarLibrosDesdeCSV(String ruta)throws IOException {
        try (BufferedReader lector = new BufferedReader(new FileReader(ruta))){
            String linea;
            while ((linea = lector.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length >= 2) {
                    String titulo = partes[0].trim();
                    String autor = partes[1].trim();
                    libros.add(new Libro(titulo, autor));
                }
            }
        } catch (FileNotFoundException e) {
            throw new IOException("El libro no se encontro: " + ruta,e);
        }
    }
    public void guardarUsuariosEnCSV (String ruta)throws IOException{
        try (FileWriter writer =new FileWriter(ruta)){
            for (Usuario usuario : usuarios.values()) {
                writer.write(usuario.getRut()+","+usuario.getNombre()+"\n");
            }
        }
    }

    // Registrar un nuevo usuario
    public void registrarUsuario(String rut, String nombre) {
        usuarios.put(rut, new Usuario(rut, nombre));
    }

    // Prestar un libro por título
    public void prestarLibro(String titulo) throws LibroNoEncontradoException, LibroYaPrestadoException {
        for (Libro libro : libros) {
            if (libro.getTitulo().equalsIgnoreCase(titulo)) {
                if (libro.estaPrestado()) {
                    throw new LibroYaPrestadoException("El libro ya fue prestado.");
                } else {
                    libro.prestar();
                    return;
                }
            }
        }
        throw new LibroNoEncontradoException("El libro no fue encontrado.");
    }

    // Mostrar todos los libros
    public void mostrarLibros() {
        for (Libro libro : libros) {
            System.out.println(libro);
        }
    }

    // Guardar un informe de los libros a un archivo de texto
    public void guardarInforme(String ruta) {
        try (FileWriter escritor = new FileWriter(ruta)){
            for (Libro libro : libros) {
                escritor.write(libro.toString() + "\n");
            }
            escritor.close();
        } catch (IOException e) {
            System.out.println("Error al guardar el informe: " + e.getMessage());
        }
    }
}