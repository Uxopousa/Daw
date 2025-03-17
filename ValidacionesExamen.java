
/**
 * Clase ValidacionesExamen
 * 
 * Esta clase contiene métodos estáticos para validar diferentes tipos de datos.
 * El propósito es centralizar las validaciones y reutilizarlas en distintas partes del código.
 * También facilita las pruebas unitarias y mejora la organización del código.
 */
public class ValidacionesExamen {

    /**
     * Valida que una matrícula cumpla con el formato español (1234ABC).
     * Usa una expresión regular para verificar el formato.
     * 
     * @param matricula Matrícula a validar
     * @return Matrícula validada si es correcta
     * @throws IllegalArgumentException si la matrícula no es válida
     */
    public static String validarMatricula(String matricula) {
        if (matricula == null || !matricula.matches("\\d{4}[A-Z]{3}")) {
            throw new IllegalArgumentException("Matrícula no válida (formato: 1234ABC)");
        }
        return matricula;
    }

    /**
     * Valida que un texto no sea nulo ni vacío.
     * 
     * @param texto Texto a validar
     * @param mensajeError Mensaje de error en caso de fallo
     * @return Texto validado
     * @throws IllegalArgumentException si el texto es nulo o vacío
     */
    public static String validarTexto(String texto, String mensajeError) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new IllegalArgumentException(mensajeError);
        }
        return texto;
    }

    /**
     * Valida que un año esté dentro de un rango lógico (entre 1900 y el año actual).
     * 
     * @param año Año a validar
     * @return Año validado
     * @throws IllegalArgumentException si el año está fuera de rango
     */
    public static int validarAño(int año) {
        int añoActual = java.time.Year.now().getValue();
        if (año < 1900 || año > añoActual) {
            throw new IllegalArgumentException("Año no válido");
        }
        return año;
    }

    /**
     * Valida que un valor de precio sea positivo.
     * 
     * @param precio Precio a validar
     * @return Precio validado
     * @throws IllegalArgumentException si el precio es negativo o cero
     */
    public static double validarPrecio(double precio) {
        if (precio <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor que 0");
        }
        return precio;
    }

    // ------------------------
    // Ejemplo de uso en un constructor
    // ------------------------
    public static class Coche {
        private String matricula;
        private String marca;
        private String modelo;
        private int año;
        private double precioDia;

        public Coche(String matricula, String marca, String modelo, int año, double precioDia) {
            this.matricula = ValidacionesExamen.validarMatricula(matricula);
            this.marca = ValidacionesExamen.validarTexto(marca, "La marca no puede estar vacía");
            this.modelo = ValidacionesExamen.validarTexto(modelo, "El modelo no puede estar vacío");
            this.año = ValidacionesExamen.validarAño(año);
            this.precioDia = ValidacionesExamen.validarPrecio(precioDia);
        }

        @Override
        public String toString() {
            return String.format("%s %s (%d) - %.2f €/día", marca, modelo, año, precioDia);
        }
    }

    // ------------------------
    // Ejemplo de uso en la entrada por teclado
    // ------------------------
    public static void main(String[] args) {
        java.util.Scanner scanner = new java.util.Scanner(System.in);

        try {
            System.out.print("Introduce la matrícula: ");
            String matricula = ValidacionesExamen.validarMatricula(scanner.nextLine());

            System.out.print("Introduce la marca: ");
            String marca = ValidacionesExamen.validarTexto(scanner.nextLine(), "La marca no puede estar vacía");

            System.out.print("Introduce el modelo: ");
            String modelo = ValidacionesExamen.validarTexto(scanner.nextLine(), "El modelo no puede estar vacío");

            System.out.print("Introduce el año: ");
            int año = scanner.nextInt();
            año = ValidacionesExamen.validarAño(año);

            System.out.print("Introduce el precio por día: ");
            double precio = scanner.nextDouble();
            precio = ValidacionesExamen.validarPrecio(precio);

            // Crear objeto Coche con valores validados
            Coche coche = new Coche(matricula, marca, modelo, año, precio);
            System.out.println("Coche creado: " + coche);

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
