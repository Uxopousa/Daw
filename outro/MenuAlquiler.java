
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class MenuAlquiler {

    private static List<Coche> coches = new ArrayList<>();

    public static void main(String[] args) {
        inicializarCoches();
        mostrarMenu();
    }

    // ------------------------
    // Clase Coche
    // ------------------------
    static class Coche {
        private String matricula;
        private String marca;
        private String modelo;
        private double precioDia;
        private boolean alquilado;

        public Coche(String matricula, String marca, String modelo, double precioDia) {
            this.matricula = ValidacionesExamen.validarMatricula(matricula);
            this.marca = ValidacionesExamen.validarTexto(marca, "La marca no puede estar vacía");
            this.modelo = ValidacionesExamen.validarTexto(modelo, "El modelo no puede estar vacío");
            this.precioDia = ValidacionesExamen.validarPrecio(precioDia);
            this.alquilado = false;
        }

        public String getMatricula() {
            return matricula;
        }

        public boolean isAlquilado() {
            return alquilado;
        }

        public void alquilar() {
            if (alquilado) {
                throw new IllegalStateException("El coche ya está alquilado");
            }
            this.alquilado = true;
        }

        public void devolver() {
            if (!alquilado) {
                throw new IllegalStateException("El coche no está alquilado");
            }
            this.alquilado = false;
        }

        @Override
        public String toString() {
            return String.format("%s %s - %.2f €/día [%s]", marca, modelo, precioDia, alquilado ? "ALQUILADO" : "DISPONIBLE");
        }
    }

    // ------------------------
    // Inicializar coches
    // ------------------------
    private static void inicializarCoches() {
        coches.add(new Coche("1234ABC", "Toyota", "Corolla", 30.5));
        coches.add(new Coche("5678DEF", "Ford", "Focus", 28.0));
        coches.add(new Coche("9101GHI", "Seat", "Ibiza", 25.0));
    }

    // ------------------------
    // Mostrar menú
    // ------------------------
    private static void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);
        int opcion = -1;

        while (opcion != 0) {
            System.out.println("\n=== MENÚ ALQUILER DE COCHES ===");
            System.out.println("1. Listar coches disponibles");
            System.out.println("2. Alquilar coche");
            System.out.println("3. Devolver coche");
            System.out.println("0. Salir");

            opcion = validarEntero(scanner, "Introduce una opción: ");

            switch (opcion) {
                case 1 -> listarCochesDisponibles();
                case 2 -> alquilarCoche(scanner);
                case 3 -> devolverCoche(scanner);
                case 0 -> System.out.println("Saliendo del sistema...");
                default -> System.out.println("Opción no válida");
            }
        }
    }

    // ------------------------
    // Método para validar un entero
    // ------------------------
    private static int validarEntero(Scanner scanner, String mensaje) {
        System.out.print(mensaje);
        while (!scanner.hasNextInt()) {
            System.out.println("Error: introduce un valor numérico válido.");
            scanner.next(); // Limpiar el buffer
            System.out.print(mensaje);
        }
        return scanner.nextInt();
    }

    // ------------------------
    // Listar coches disponibles
    // ------------------------
    private static void listarCochesDisponibles() {
        System.out.println("\n=== COCHES DISPONIBLES ===");
        coches.stream()
                .filter(coche -> !coche.isAlquilado())
                .forEach(System.out::println);
    }

    // ------------------------
    // Alquilar coche
    // ------------------------
    private static void alquilarCoche(Scanner scanner) {
        System.out.print("Introduce la matrícula del coche a alquilar: ");
        String matricula = scanner.next();

        Optional<Coche> coche = coches.stream()
                .filter(c -> c.getMatricula().equals(matricula) && !c.isAlquilado())
                .findFirst();

        if (coche.isPresent()) {
            coche.get().alquilar();
            System.out.println("Coche alquilado correctamente.");
        } else {
            System.out.println("Coche no disponible o ya alquilado.");
        }
    }

    // ------------------------
    // Devolver coche
    // ------------------------
    private static void devolverCoche(Scanner scanner) {
        System.out.print("Introduce la matrícula del coche a devolver: ");
        String matricula = scanner.next();

        Optional<Coche> coche = coches.stream()
                .filter(c -> c.getMatricula().equals(matricula) && c.isAlquilado())
                .findFirst();

        if (coche.isPresent()) {
            coche.get().devolver();
            System.out.println("Coche devuelto correctamente.");
        } else {
            System.out.println("No existe un coche alquilado con esa matrícula.");
        }
    }
}
