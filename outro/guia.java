import java.util.ArrayList;
import java.util.Scanner;

public class GuiaExamen {

    private static final int MAX_INTENTOS = 2;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Índice de funcionalidades
        mostrarMenu();

        // Menú de funcionalidades
        int opcion;
        do {
            opcion = leerOpcion(sc);

            switch (opcion) {
                case 1:
                    trabajarConArrays(sc);
                    break;
                case 2:
                    trabajarConMatrices();
                    break;
                case 3:
                    metodosUtilesString();
                    break;
                case 4:
                    metodosArrayList();
                    break;
                case 5:
                    manejarValidaciones(sc);
                    break;
                case 6:
                    manejarExcepciones(sc);
                    break;
                case 7:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 7);

        sc.close();
    }

    // Método para mostrar el menú
    public static void mostrarMenu() {
        System.out.println("----- Guía de Funcionalidades -----");
        System.out.println("1. Operaciones con Arrays");
        System.out.println("2. Operaciones con Matrices");
        System.out.println("3. Métodos útiles de String");
        System.out.println("4. Métodos útiles de ArrayList");
        System.out.println("5. Validaciones");
        System.out.println("6. Excepciones");
        System.out.println("7. Salir");
        System.out.print("Elige una opción: ");
    }

    // Método para leer una opción válida
    public static int leerOpcion(Scanner sc) {
        while (!sc.hasNextInt()) {
            System.out.println("Entrada no válida. Introduce un número.");
            sc.next();
        }
        return sc.nextInt();
    }

    // 1. Operaciones con Arrays
    public static void trabajarConArrays(Scanner sc) {
        int[] numeros = {5, 3, 8, 1, 2};

        // Mostrar elementos
        System.out.println("Array original:");
        for (int i = 0; i < numeros.length; i++) {
            System.out.print(numeros[i] + " ");
        }
        System.out.println();

        // Ordenar array (manual)
        for (int i = 0; i < numeros.length - 1; i++) {
            for (int j = i + 1; j < numeros.length; j++) {
                if (numeros[i] > numeros[j]) {
                    int temp = numeros[i];
                    numeros[i] = numeros[j];
                    numeros[j] = temp;
                }
            }
        }

        System.out.println("Array ordenado:");
        for (int num : numeros) {
            System.out.print(num + " ");
        }
        System.out.println();

        // Sumar elementos
        int suma = 0;
        for (int num : numeros) {
            suma += num;
        }
        System.out.println("Suma de los elementos: " + suma);

        // Buscar un elemento
        System.out.print("Introduce un número para buscar: ");
        int clave = sc.nextInt();
        boolean encontrado = false;
        for (int num : numeros) {
            if (num == clave) {
                encontrado = true;
                break;
            }
        }
        System.out.println(encontrado ? "Número encontrado." : "Número no encontrado.");
    }

    // 2. Operaciones con Matrices
    public static void trabajarConMatrices() {
        int[][] matriz = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };

        System.out.println("Matriz original:");
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println();
        }

        int escalar = 2;
        System.out.println("Matriz multiplicada por " + escalar + ":");
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                matriz[i][j] *= escalar;
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println();
        }
    }

    // 3. Métodos útiles de String
    public static void metodosUtilesString() {
        String texto = "Hola Mundo";
        System.out.println("Texto original: " + texto);
        System.out.println("Longitud: " + texto.length());
        System.out.println("Mayúsculas: " + texto.toUpperCase());
        System.out.println("Minúsculas: " + texto.toLowerCase());
        System.out.println("Contiene 'Hola': " + texto.contains("Hola"));
        System.out.println("Reemplazar 'Mundo' por 'Java': " + texto.replace("Mundo", "Java"));
        System.out.println("Subcadena (0, 4): " + texto.substring(0, 4));
    }

    // 4. Métodos útiles de ArrayList
    public static void metodosArrayList() {
        ArrayList<String> lista = new ArrayList<>();

        lista.add("Elemento 1");
        lista.add("Elemento 2");
        lista.add("Elemento 3");

        System.out.println("Lista original: " + lista);

        lista.remove("Elemento 2");
        System.out.println("Después de eliminar: " + lista);

        System.out.println("Contiene 'Elemento 1': " + lista.contains("Elemento 1"));

        System.out.println("Elementos de la lista:");
        for (String elemento : lista) {
            System.out.println(elemento);
        }
    }

    // 5. Validaciones
    public static void manejarValidaciones(Scanner sc) {
        try {
            int numero = validarNumeroConIntentos(sc, 1, 100);
            System.out.println("Número válido: " + numero);

            String texto = validarTextoConIntentos(sc);
            System.out.println("Texto válido: " + texto);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static int validarNumeroConIntentos(Scanner sc, int min, int max) {
        for (int intento = 1; intento <= MAX_INTENTOS; intento++) {
            System.out.print("Introduce un número entre " + min + " y " + max + ": ");
            if (sc.hasNextInt()) {
                int numero = sc.nextInt();
                if (numero >= min && numero <= max) {
                    return numero;
                } else {
                    System.out.println("El número debe estar entre " + min + " y " + max + ".");
                }
            } else {
                System.out.println("Entrada no válida. Debes introducir un número.");
                sc.next();
            }
        }
        throw new IllegalArgumentException("Número no válido después de " + MAX_INTENTOS + " intentos.");
    }

    public static String validarTextoConIntentos(Scanner sc) {
        sc.nextLine();
        for (int intento = 1; intento <= MAX_INTENTOS; intento++) {
            System.out.print("Introduce un texto no vacío: ");
            String texto = sc.nextLine();
            if (texto != null && !texto.trim().isEmpty()) {
                return texto;
            } else {
                System.out.println("El texto no puede estar vacío.");
            }
        }
        throw new IllegalArgumentException("Texto no válido después de " + MAX_INTENTOS + " intentos.");
    }

    // 6. Manejo de Excepciones
    public static void manejarExcepciones(Scanner sc) {
        try {
            System.out.print("Introduce un número positivo: ");
            int numero = sc.nextInt();
            validarNumeroPositivo(numero);
            System.out.println("Número válido: " + numero);
        } catch (Exception e) {
            System.out.println("Excepción capturada: " + e.getMessage());
        }
    }

    public static void validarNumeroPositivo(int numero) throws Exception {
        if (numero < 0) {
            throw new Exception("El número no puede ser negativo.");
        }
    }
}
