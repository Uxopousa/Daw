public class EdadNoValidaException extends Exception {
    public EdadNoValidaException(String mensaje) {
        super(mensaje);
    }
}
public class Persona {
    private int edad;

    public void setEdad(int edad) throws EdadNoValidaException {
        if (edad < 0 || edad > 130) {
            throw new EdadNoValidaException("Edad no válida: " + edad);
        }
        this.edad = edad;
    }
}


public class Main {
    public static void main(String[] args) {
        Persona p = new Persona();
        try {
            p.setEdad(150);
        } catch (EdadNoValidaException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}