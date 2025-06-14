package duoc.biblioteca.modelos;

public class Usuario {
    private String rut;
    private String nombre;

    public Usuario(String rut, String nombre) {
        this.rut = rut;
        this.nombre = nombre;
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