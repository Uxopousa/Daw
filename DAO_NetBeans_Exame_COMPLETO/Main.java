/**
 * PASOS PARA ADAPTAR NO EXAME:
 * 1. Crear clase modelo (Produto, Cliente, etc.)
 * 2. Crear clase DAO que herde de BaseDAO<T> e implemente os métodos
 * 3. Adaptar a clase Main para probar os métodos
 * 4. Engadir bd.properties na raíz do proxecto:
 *      url=jdbc:mysql://localhost:3306/NOME_BASE_DATOS
 *      usuario=root
 *      contrasinal=
 * 5. Crear a táboa en phpMyAdmin
 */

public class Main {
    public static void main(String[] args) {
        ProdutoDAO dao = new ProdutoDAO();

        Produto p1 = new Produto(1, "Portátil", 899.99, 10);
        dao.insertar(p1);

        System.out.println("📋 Lista de produtos:");
        for (Produto p : dao.buscarTodos()) {
            System.out.println(p);
        }

        Produto mod = new Produto(1, "Portátil PRO", 1099.99, 8);
        dao.actualizar(mod);

        Produto buscado = dao.buscarPorId(1);
        System.out.println("🔍 Produto atopado: " + buscado);

        dao.eliminarPorId(1);
    }
}
