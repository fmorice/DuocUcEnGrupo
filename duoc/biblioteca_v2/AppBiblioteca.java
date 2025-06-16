package duoc.biblioteca_v2;

import duoc.biblioteca.servicios.Biblioteca;
import duoc.biblioteca.excepciones.*;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AppBiblioteca {
    public static void main(String[] args) throws IOException {
        Biblioteca biblioteca = new Biblioteca();
        biblioteca.cargarLibrosDesdeCSV("libros.csv"); // debe estar en la raíz del proyecto
        biblioteca.guardarUsuariosEnCSV("usuarios.csv");
        Scanner scanner = new Scanner(System.in);

        int opcion = 0;
        while (opcion != 4) {
            System.out.println("\n=== Menú Biblioteca DUOC UC ===");
            System.out.println("1. Ver libros");
            System.out.println("2. Solicitar libro");
            System.out.println("3. Guardar informe");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = scanner.nextInt();
                scanner.nextLine(); // limpiar
                switch (opcion) {
                    case 1 -> biblioteca.mostrarLibros();
                    case 2 -> {
                        System.out.print("Ingrese título del libro: ");
                        String titulo = scanner.nextLine();
                        biblioteca.prestarLibro(titulo);
                        System.out.println("Libro prestado correctamente.");
                    }
                    case 3 -> {
                        biblioteca.guardarInforme("informe.txt");
                        System.out.println("Informe guardado.");
                    }
                    case 4 -> System.out.println("Hasta luego.");
                    default -> System.out.println("Opción inválida.");
                }

            } catch (LibroNoEncontradoException | LibroYaPrestadoException e) {
                System.out.println("⚠ ️Error:  " + e.getMessage());
            } catch (InputMismatchException e) {
                System.out.println("⚠️ Entrada inválida. Intente nuevamente.");
                scanner.nextLine(); // limpiar
            }
        }
    }
}