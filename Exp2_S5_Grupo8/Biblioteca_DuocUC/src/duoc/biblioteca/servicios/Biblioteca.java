package duoc.biblioteca.servicios;

// Importación de clases de colección
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import duoc.biblioteca.modelos.Libro;
import duoc.biblioteca.modelos.Usuario;
import duoc.biblioteca.excepciones.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Biblioteca {
    
    // Paso 3: Uso de ‘ArrayList’ para libro, colecciones como atributos de la clase
    private ArrayList<Libro> listaLibros = new ArrayList<>();
    private HashMap<String, Usuario> mapaUsuarios = new HashMap<>();
    private HashSet<String> titulosUnicos = new HashSet<>();
    private HashSet<String> rutsUnicos = new HashSet<>();

    private ArrayList<Libro> libros = new ArrayList<>();
    private HashMap<String, Usuario> usuarios = new HashMap<>();
    
      // método agregarLibro con HashSet
    public void agregarLibro(Libro libro) {
        if (titulosUnicos.add(libro.getTitulo())) {
            listaLibros.add(libro);
            System.out.println("✅ Libro agregado: " + libro.getTitulo());
        } else {
            System.out.println("⚠️ El libro ya existe en la biblioteca.");
        }
    }

     // Método para registrar usuario usando HashMap y HashSet
    public void registrarUsuario(Usuario usuario) {
        String rut = usuario.getRut();
        if (rutsUnicos.add(rut)) {
            mapaUsuarios.put(rut, usuario);
            System.out.println("✅ Usuario registrado: " + usuario.getNombre());
        } else {
            System.out.println("⚠️ El usuario ya está registrado.");
        }
    }
    // Leer libros desde un archivo CSV
    public void cargarLibrosDesdeCSV(String ruta) {
        try {
            BufferedReader lector = new BufferedReader(new FileReader(ruta));
            String linea;

            while ((linea = lector.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length >= 2) {
                    String titulo = partes[0].trim();
                    String autor = partes[1].trim();
                    libros.add(new Libro(titulo, autor));
                }
            }

            lector.close();
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
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
        try {
            FileWriter escritor = new FileWriter(ruta);

            for (Libro libro : libros) {
                escritor.write(libro.toString() + "\n");
            }

            escritor.close();
        } catch (IOException e) {
            System.out.println("Error al guardar el informe: " + e.getMessage());
        }
    }
}
