package duoc.biblioteca.excepciones;

public class LibroYaPrestadoException extends Exception {
    public LibroYaPrestadoException(String mensaje) {
        super(mensaje);
    }
}