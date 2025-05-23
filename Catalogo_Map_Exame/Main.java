import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        // Exemplo 1: Catalogo de usuarios
        Catalogo<String, Usuario> catalogoUsuarios = new Catalogo<>();
        catalogoUsuarios.engadir("uxio1", new Usuario(1, "Uxío", "Rodríguez"));
        catalogoUsuarios.engadir("dompi2", new Usuario(2, "Dompi", "Fernández"));

        System.out.println("📚 Usuarios:");
        catalogoUsuarios.listar();

        Usuario atopado = catalogoUsuarios.buscar("uxio1");
        System.out.println("🔍 Usuario buscado: " + atopado.getNome());

        // Exemplo 2: Catalogo de libros (ID numérico)
        Catalogo<Integer, String> catalogoLibros = new Catalogo<>();
        catalogoLibros.engadir(101, "A sombra do vento");
        catalogoLibros.engadir(102, "1984");

        System.out.println("📖 Libros:");
        catalogoLibros.listar();
    }
}
