package duoc.biblioteca.servicios;

// Importación de clases de colección
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import duoc.biblioteca.modelos.Libro;
import duoc.biblioteca.modelos.Usuario;
import duoc.biblioteca.excepciones.*;

import java.io.*;
import java.util.*;

public class Biblioteca {
    
    // Paso 3: Uso de ‘ArrayList’ para libro, colecciones como atributos de la clase
    private ArrayList<Libro> listaLibros = new ArrayList<>();
    // Paso 4: HashMap para usuarios (clave:RUT)
    private HashMap<String, Usuario> mapaUsuarios = new HashMap<>();
    // Paso 5: HashSet para titulos unicos
    private HashSet<String> titulosUnicos = new HashSet<>();
    // Paso 5: TreeSet para catalogo ordenado de libros por titulo
    private TreeSet<Libro> catalogoOrdenado = new TreeSet<>(Comparator.comparing(Libro::getTitulo));
    
      // método agregarLibro con HashSet
    public void agregarLibro(Libro libro) {
        if (titulosUnicos.add(libro.getTitulo())) {
            listaLibros.add(libro);
            System.out.println("✅ Libro agregado: " + libro.getTitulo());
        } else {
            System.out.println("⚠️ El libro ya existe en la biblioteca.");
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
                    Libro libro = new Libro(titulo, autor);
                    
                    if(titulosUnicos.add(titulo)){
                        listaLibros.add(libro);
                        catalogoOrdenado.add(libro);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    // Registrar un nuevo usuario
    public void cargarUsuariosDesdeCSV(String ruta) {
        try (BufferedReader lector = new BufferedReader(new FileReader(ruta))){
            String linea;
            while((linea = lector.readLine()) != null){
                String[] partes = linea.split(",");
                if (partes.length >=2) {
                    String rut = partes[0].trim();
                    String nombre = partes[1].trim();
                    mapaUsuarios.put(rut, new Usuario(rut, nombre));
                }
            }
        }catch (IOException e){
            System.out.println("Error al cargar usuarios: "+e.getMessage());
        }
    }    
    public void guardarUsuariosEnCSV(String ruta){
        try (PrintWriter escritor = new PrintWriter(new FileWriter(ruta))){
            for(Usuario usuario : mapaUsuarios.values()){
                escritor.println(usuario.getRut()+","+ usuario.getNombre());
            }
        }catch(IOException e){
            System.out.println("Error al guardar usuario: "+ e.getMessage());
        }
    }
    
    public void prestarLibroConRegistro(String rut, String nombre, String titulo) throws LibroNoEncontradoException, LibroYaPrestadoException {
        //Verificacion si usuario ya esta registrado (por el uso de HashMap)
        if (!mapaUsuarios.containsKey((rut))) {
            Usuario nuevoUsuario = new Usuario (rut, nombre);
            mapaUsuarios.put(rut, nuevoUsuario);
            System.out.println("Nuevo usuario registrado: "+ nombre);
        }
        if (titulo == null || titulo.trim().isEmpty()){
            throw new LibroNoEncontradoException("Debe ingresar un titulo valido");
        }
        String tituloBuscado = titulo.trim().toLowerCase(); //Normaliza el titulo
        
        //Buscar libro 
        boolean libroEncontrado = false;
        for (Libro libro : listaLibros){
            if (libro.getTitulo().trim().toLowerCase().equals(tituloBuscado)) {
                libroEncontrado = true;
                if(libro.estaPrestado()) {
                    throw new LibroYaPrestadoException("El libro '"+libro.getTitulo()+"' ya esta prestado"); 
                }
                libro.prestar();
                System.out.println("Libro '"+libro.getTitulo()+"' prestado con exito a "+ (mapaUsuarios.containsKey(rut)?mapaUsuarios.get(rut).getNombre() : nombre));
                return;
            }
        }    
        if (!libroEncontrado){
        throw new LibroNoEncontradoException("El libro '"+titulo+"' no existe en la biblioteca.");     
        }
    }
    // Mostrar todos los libros
    public void mostrarLibrosDisponibles() {
        System.out.println("\n::::::: LIBROS DISPONIBLES :::::::");
        for (Libro libro : catalogoOrdenado) {
            if (!libro.estaPrestado()) {
            System.out.println(libro);
            }
        }
    }

    // Guardar un informe de los libros a un archivo de texto
    public void guardarInforme(String ruta) {
        try (PrintWriter escritor = new PrintWriter(new FileWriter(ruta))) {
            escritor.println("::::::: INFORME DE BIBLIOTECA :::::::");
            escritor.println("Total libros: "+ listaLibros.size());
            escritor.println("Total usuarios: "+ mapaUsuarios.size());
            escritor.println("\n::::::: LIBROS :::::::");
            for (Libro libro : catalogoOrdenado) {
                escritor.println(libro);
            }
            escritor.println("\n::::::: USUARIOS REGISTRADOS :::::::");
            for (Usuario usuario : mapaUsuarios.values()) {
                escritor.println(usuario);
            }
        } catch (IOException e) {
            System.out.println("Error al guardar el informe: " + e.getMessage());
        }
    }
    public Set<Libro> getCatalogoOrdenado(){
        return Collections.unmodifiableSet(catalogoOrdenado);
    }
    public Collection<Usuario> getUsuarioRegistrados(){
        return Collections.unmodifiableCollection(mapaUsuarios.values());
    }
    public boolean existeUsuario(String rut){
        return mapaUsuarios.containsKey(rut);
    }
}    
