package agenda.ui;

import agenda.operations.AgendaOperations;
import agenda.almacen.AlmacenException;
import agenda.dao.ContactoDAO;
import agenda.dao.DAOException;
import agenda.modelo.Contacto;
import agenda.util.Valida;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Interfaz de línea de comandos para gestionar la agenda de contactos,
 * tanto en memoria como sincronizando con base de datos.
 */
public class Main {

    private final AgendaOperations ops;
    private final Scanner sc;

    /** Inicializa la capa de operaciones (memoria + BD) y el Scanner. */
    public Main() {
        // Inyectamos el DAO para poder usar BD además de la memoria
        this.ops = new AgendaOperations(new ContactoDAO());
        this.sc  = new Scanner(System.in);
    }

    /** Punto de entrada de la aplicación. */
    public static void main(String[] args) {
        new Main().run();
    }

    /** Bucle principal que muestra el menú y procesa las opciones hasta salir. */
    public void run() {
        boolean salir = false;
        while (!salir) {
            mostrarMenu();
            String opcion = sc.nextLine().trim();
            switch (opcion) {
                case "1": altaContacto();       break;
                case "2": modificarContacto();  break;
                case "3": eliminarContacto();   break;
                case "4": listarTodos();        break;
                case "5": buscarPorNombre();    break;
                case "6": listarFavoritos();    break;
                case "7": exportarCsv();        break;
                case "8": copiarBackup();       break;
                case "9": cargarDesdeBD();      break;
                case "10": guardarEnBD();       break;
                case "11": salir = true;        break;
                default:
                    System.out.println("Opción no válida. Elija 1–11.");
            }
            System.out.println();
        }
        sc.close();
        System.out.println("¡Hasta pronto!");
    }

    private void mostrarMenu() {
        System.out.println("=== Agenda de Contactos ===");
        System.out.println("1.  Alta de contacto");
        System.out.println("2.  Modificar contacto");
        System.out.println("3.  Eliminar contacto");
        System.out.println("4.  Listar todos los contactos");
        System.out.println("5.  Buscar contactos por nombre");
        System.out.println("6.  Listar contactos favoritos");
        System.out.println("7.  Exportar a CSV");
        System.out.println("8.  Copiar backup CSV");
        System.out.println("9.  Cargar desde BD");
        System.out.println("10. Guardar en BD");
        System.out.println("11. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private void altaContacto() {
        try {
            System.out.print("ID (0–9999): ");
            String id    = Valida.validateId(sc.nextLine());
            System.out.print("Nombre: ");
            String nom   = Valida.requireNonEmpty(sc.nextLine(), "El nombre no puede estar vacío");
            System.out.print("Teléfono: ");
            String tel   = Valida.validatePhone(sc.nextLine());

            Contacto c = new Contacto(id, nom, tel);
            ops.altaContacto(c);
            System.out.println("✅ Contacto añadido: " + c);
        } catch (IllegalArgumentException | AlmacenException e) {
            System.out.println("❌ Error al añadir: " + e.getMessage());
        }
    }

    private void modificarContacto() {
        try {
            System.out.print("ID a modificar: ");
            String id = Valida.validateId(sc.nextLine());

            Optional<Contacto> opt = ops.listarContactos().stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();

            if (opt.isEmpty()) {
                System.out.println("❌ No existe contacto con ID " + id);
                return;
            }

            Contacto c = opt.get();
            System.out.println("Actual: " + c);

            System.out.print("Nuevo nombre (enter = sin cambios): ");
            String nn = sc.nextLine().trim();
            if (!nn.isEmpty()) {
                c.setNombre(Valida.requireNonEmpty(nn, "El nombre no puede estar vacío"));
            }

            System.out.print("Nuevo teléfono (enter = sin cambios): ");
            String nt = sc.nextLine().trim();
            if (!nt.isEmpty()) {
                c.setTelefono(Valida.validatePhone(nt));
            }

            System.out.print("¿Favorito? (s/n + enter = sin cambios): ");
            String fv = sc.nextLine().trim().toLowerCase();
            if (fv.equals("s")) {
                c.setFavorito(true);
            } else if (fv.equals("n")) {
                c.setFavorito(false);
            }

            ops.modificarContacto(c);
            System.out.println("✅ Contacto modificado: " + c);
        } catch (IllegalArgumentException | AlmacenException e) {
            System.out.println("❌ Error al modificar: " + e.getMessage());
        }
    }

    private void eliminarContacto() {
        try {
            System.out.print("ID a eliminar: ");
            String id = Valida.validateId(sc.nextLine());
            ops.eliminarContacto(id);
            System.out.println("✅ Contacto eliminado: " + id);
        } catch (IllegalArgumentException | AlmacenException e) {
            System.out.println("❌ Error al eliminar: " + e.getMessage());
        }
    }

    private void listarTodos() {
        List<Contacto> todos = ops.listarContactos();
        if (todos.isEmpty()) {
            System.out.println("No hay contactos registrados.");
        } else {
            todos.forEach(System.out::println);
        }
    }

    private void buscarPorNombre() {
        System.out.print("Texto a buscar en nombre: ");
        String txt = sc.nextLine().trim();
        List<Contacto> res = ops.buscarPorNombre(txt);
        if (res.isEmpty()) {
            System.out.println("Sin coincidencias.");
        } else {
            res.forEach(System.out::println);
        }
    }

    private void listarFavoritos() {
        List<Contacto> favs = ops.listarFavoritos();
        if (favs.isEmpty()) {
            System.out.println("No hay favoritos.");
        } else {
            favs.forEach(System.out::println);
        }
    }

    private void exportarCsv() {
        try {
            Path p = Paths.get("contacts.csv");
            ops.exportarCsv(p);
            System.out.println("✅ Exportado en " + p.toAbsolutePath());
        } catch (IOException e) {
            System.out.println("❌ Error exportando CSV: " + e.getMessage());
        }
    }

    private void copiarBackup() {
        try {
            Path o = Paths.get("contacts.csv");
            Path d = Paths.get("contacts_backup.csv");
            ops.copiarBackup(o, d);
            System.out.println("✅ Backup en " + d.toAbsolutePath());
        } catch (IOException e) {
            System.out.println("❌ Error copiando backup: " + e.getMessage());
        }
    }

    private void cargarDesdeBD() {
        try {
            List<Contacto> bd = ops.cargarDesdeBD();
            if (bd.isEmpty()) {
                System.out.println("La base de datos no tiene contactos.");
            } else {
                System.out.println("Contactos en BD:");
                bd.forEach(System.out::println);
            }
        } catch (DAOException e) {
            System.out.println("❌ Error cargando BD: " + e.getMessage());
        }
    }

    private void guardarEnBD() {
        try {
            List<Contacto> todos = ops.listarContactos();
            ops.guardarEnBD(todos);
            System.out.println("✅ Todos los contactos guardados en BD");
        } catch (DAOException e) {
            System.out.println("❌ Error guardando en BD: " + e.getMessage());
        }
    }
}
