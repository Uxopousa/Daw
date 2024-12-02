import java.util.Arrays;
import java.util.ArrayList;
import java.util.Scanner;

public class GuiaExamen {

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
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 5);

        sc.close();
    }

    // Método para mostrar el menú
    public static void mostrarMenu() {
        System.out.println("----- Guía de Funcionalidades -----");
        System.out.println("1. Operaciones con Arrays");
        System.out.println("2. Operaciones con Matrices");
        System.out.println("3. Métodos útiles de String");
        System.out.println("4. Métodos útiles de ArrayList");
        System.out.println("5. Salir");
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
        // Crear un array
        int[] numeros = {5, 3, 8, 1, 2};

        // Mostrar elementos
        System.out.println("Array original: " + Arrays.toString(numeros));

        // Ordenar array
        Arrays.sort(numeros);
        System.out.println("Array ordenado: " + Arrays.toString(numeros));

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
        // Crear una matriz
        int[][] matriz = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };

        // Mostrar matriz
        System.out.println("Matriz original:");
        for (int[] fila : matriz) {
            System.out.println(Arrays.toString(fila));
        }

        // Multiplicar por un escalar
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

        // Agregar elementos
        lista.add("Elemento 1");
        lista.add("Elemento 2");
        lista.add("Elemento 3");

        // Mostrar elementos
        System.out.println("Lista: " + lista);

        // Eliminar un elemento
        lista.remove("Elemento 2");
        System.out.println("Después de eliminar: " + lista);

        // Buscar un elemento
        System.out.println("Contiene 'Elemento 1': " + lista.contains("Elemento 1"));

        // Recorrer la lista
        System.out.println("Elementos en la lista:");
        for (String elemento : lista) {
            System.out.println(elemento);
        }
    }
}
