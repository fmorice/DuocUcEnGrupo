package duoc.biblioteca_v2;

import duoc.biblioteca.servicios.Biblioteca;
import duoc.biblioteca.excepciones.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AppBiblioteca {
    public static void main(String[] args) {
        Biblioteca biblioteca = new Biblioteca();
        biblioteca.cargarLibrosDesdeCSV("libros.csv"); // debe estar en la raíz del proyecto
        Scanner scanner = new Scanner(System.in);

        int opcion = 0;
        while (opcion != 4) {
            System.out.println("\n=== Menú Biblioteca DUOC UC ===");
            System.out.println("1. Ver libros");
            System.out.println("2. Prestar libro");
            System.out.println("3. Guardar informe");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = scanner.nextInt();
                scanner.nextLine(); // limpiar

                if (opcion == 1) {
                    biblioteca.mostrarLibros();
                } else if (opcion == 2) {
                    System.out.print("Ingrese título del libro: ");
                    String titulo = scanner.nextLine();
                    biblioteca.prestarLibro(titulo);
                    System.out.println("Libro prestado correctamente.");
                } else if (opcion == 3) {
                    biblioteca.guardarInforme("informe.txt");
                    System.out.println("Informe guardado.");
                } else if (opcion == 4) {
                    System.out.println("Hasta luego.");
                } else {
                    System.out.println("Opción inválida.");
                }

            } catch (LibroNoEncontradoException | LibroYaPrestadoException e) {
                System.out.println("⚠️ " + e.getMessage());
            } catch (InputMismatchException e) {
                System.out.println("⚠️ Entrada inválida. Intente nuevamente.");
                scanner.nextLine(); // limpiar
            }
        }
    }
}
