package duoc.biblioteca_v2;

import duoc.biblioteca.servicios.Biblioteca;
import duoc.biblioteca.excepciones.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AppBiblioteca {
    public static void main(String[] args){
        Biblioteca biblioteca = new Biblioteca();
        try {
            biblioteca.cargarLibrosDesdeCSV("libros.csv"); // debe estar en la raíz del proyecto
            biblioteca.cargarUsuariosDesdeCSV("usuarios.csv");
        }catch (Exception e){
            System.out.println("Error al cargar datos iniciales: "+e.getMessage());
        }    
        Scanner scanner = new Scanner(System.in);
        int opcion = 0;
        while (opcion != 4) {
            System.out.println("\n=== Menú Biblioteca DUOC UC ===");
            System.out.println("1. Ver libros disponibles");
            System.out.println("2. Prestar libro");
            System.out.println("3. Guardar informe");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = scanner.nextInt();
                scanner.nextLine(); // limpiar
                switch (opcion) {
                    case 1:
                        biblioteca.mostrarLibrosDisponibles();
                        break;
                    case 2:
                        System.out.println("Ingrese RUT del usuario (sin puntos ni guion): ");
                        String rut = scanner.nextLine().trim();
                        String nombre = "";
                        if (!biblioteca.existeUsuario(rut)) {
                            System.out.println("Ingrese el nombre del usuario: ");
                            nombre=scanner.nextLine().trim();
                            if (nombre.isEmpty()) {
                                System.out.println("El nombre no puede quedar vacio");
                                break;
                            }
                        }
                        System.out.print("Ingrese título del libro: ");
                        String titulo = scanner.nextLine();
                        try {
                            biblioteca.prestarLibroConRegistro(rut, nombre, titulo);
                            biblioteca.guardarUsuariosEnCSV("usuarios.csv");
                        }catch (LibroNoEncontradoException | LibroYaPrestadoException e){
                            System.out.println("Error: "+e.getMessage());
                        }
                        break;
                    case 3:
                        biblioteca.guardarInforme("informe.txt");
                        System.out.println("Informe guardado.");
                        break;
                    case 4:
                        biblioteca.guardarUsuariosEnCSV("usuarios.csv");
                        System.out.println("Hasta luego.");
                        break;
                    default:
                        System.out.println("Opción inválida.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("⚠️ Entrada inválida. Intente nuevamente.");
                scanner.nextLine(); // limpiar
            }
        }
        scanner.close();
    }
}
