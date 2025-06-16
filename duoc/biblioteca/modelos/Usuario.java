package duoc.biblioteca.modelos;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private String rut;
    private String nombre;
    private List<String> librosPrestados; // Historial de libros prestados al usuario

    public Usuario(String rut, String nombre) {
        if (!validarRUT(rut)) {
            throw new IllegalArgumentException("RUT invalido");
        }
        if (rut == null || rut.trim().isEmpty()) {
            throw new IllegalArgumentException("El rut no puede estar vacio");
        }
        if (nombre == null || rut.trim().isEmpty()) {
            throw new IllegalArgumentException("El RUT no puede estar vacio");
        }
        this.rut = rut.trim();
        this.nombre = nombre.trim();
        this.librosPrestados = new ArrayList<>();
    }
    public static boolean validarRUT(String rut){
        return rut != null && rut.matches("^\\d{7,8}^[\\dkK]$");//Verificar rut de 11 a 12 digitos
    }
    public void agregarLibrosPrestados (String tituloLibro){
        librosPrestados.add(tituloLibro);
    }
    public List<String> getLibrosPrestados(){
        return new ArrayList<>(librosPrestados);
    }

    public String getRut() {
        return rut;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return nombre + " (" + rut + ")";
    }
}