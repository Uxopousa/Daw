/**
 * Paquete para gestionar productos mediante ficheros.
 */
package gestion.de.productos;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;

/**
 * Clase principal para demostrar:
 *  1. Gestión de directorios y ficheros con java.io.File
 *  2. Serialización y deserialización de objetos
 *  3. Copia de seguridad con flujos con buffer
 *  4. Acceso aleatorio a datos con RandomAccessFile
 *
 *  Llevad esta clase al examen para mostrar un ejemplo completo.
 */
public class GestionDeProductos {

    /**
     * Método main donde se ejecutan todos los pasos:
     * @param args argumentos de línea de comandos (no utilizados)
     * @throws IOException si ocurre un error de E/S
     * @throws ClassNotFoundException si falta la clase al deserializar
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // 1) Crear el directorio "data" si no existe
        File dataDir = new File("data");
        if (!dataDir.exists()) {
            boolean creado = dataDir.mkdirs();  // crea data/ y padres
            System.out.println("Directorio data/ creado: " + creado);
        } else {
            System.out.println("Directorio data/ ya existe.");
        }

        // 2) Definir rutas para los ficheros de productos y precios
        File productosFile = new File(dataDir, "productos.dat");
        File preciosFile   = new File(dataDir, "precios.dat");

        // 3) Crear los ficheros si no existen
        if (!productosFile.exists()) {
            boolean creado = productosFile.createNewFile();
            System.out.println("Fichero productos.dat creado: " + creado);
        } else {
            System.out.println("productos.dat ya existe.");
        }
        if (!preciosFile.exists()) {
            boolean creado = preciosFile.createNewFile();
            System.out.println("Fichero precios.dat creado: " + creado);
        } else {
            System.out.println("precios.dat ya existe.");
        }

        // 4) Crear y serializar lista de productos
        Producto p1 = new Producto(1, "Mesa", 89.0);
        Producto p2 = new Producto(2, "Silla", 32.0);
        Producto p3 = new Producto(3, "Sillón", 150.0);
        ArrayList<Producto> productos = new ArrayList<>();
        productos.add(p1);
        productos.add(p2);
        productos.add(p3);

        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(productosFile))) {
            // Serializar la lista completa
            oos.writeObject(productos);
            System.out.println("Lista de productos serializada en productos.dat");
        }

        // 5) Deserializar y mostrar los productos
        ArrayList<Producto> listaProductos;
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(productosFile))) {
            @SuppressWarnings("unchecked")
            ArrayList<Producto> tmp = (ArrayList<Producto>) ois.readObject();
            listaProductos = tmp;
            System.out.println("Productos deserializados:");
            for (Producto prod : listaProductos) {
                System.out.println("  " + prod);
            }
        }

        // 6) Copia de seguridad con flujos buffered
        File backupDir = new File(dataDir, "backup");
        if (!backupDir.exists()) {
            backupDir.mkdirs();
        }
        File backup = new File(backupDir, "productos.bak");
        try (BufferedInputStream bis = new BufferedInputStream(
                new FileInputStream(productosFile));
             BufferedOutputStream bos = new BufferedOutputStream(
                new FileOutputStream(backup))) {
            byte[] buffer = new byte[4096];  // 4 KB
            int bytesRead;
            while ((bytesRead = bis.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }
            bos.flush();  // asegura que todo está escrito
            System.out.println("Backup con buffer completado en " + backup);
        }

        // 7) Acceso aleatorio a precios con RandomAccessFile
        try (RandomAccessFile raf = new RandomAccessFile(preciosFile, "rw")) {
            // Escribir todos los precios (8 bytes cada double)
            for (Producto prod : listaProductos) {
                raf.writeDouble(prod.getPrecio());
            }
            System.out.println("Precios escritos en precios.dat");

            // Modificar el segundo precio (offset = 8 bytes)
            raf.seek(8);
            double original = listaProductos.get(1).getPrecio();
            double nuevo    = original * 1.10;  // +10%
            raf.writeDouble(nuevo);
            System.out.printf("Segundo precio modificado: %.2f → %.2f%n", original, nuevo);

            // Leer todos los precios para verificar
            raf.seek(0);
            System.out.println("Leyendo precios actualizados:");
            for (int i = 0; i < listaProductos.size(); i++) {
                double precio = raf.readDouble();
                System.out.printf("  Precio %d: %.2f%n", i + 1, precio);
            }
        }
    }
}