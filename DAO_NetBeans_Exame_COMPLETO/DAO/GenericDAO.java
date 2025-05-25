// GenericDAO.java

/**
 * GenericDAO.java
 *
 * Clase xenérica para operacións CRUD en calquera táboa MySQL
 * usando a clase de conexión ConexionDB (que le db.properties).
 *
 * ------------------------------
 * Exemplo de uso en App.java
 * ------------------------------
 * // Suponse que tes un ficheiro db.properties no classpath coas claves:
 * // db.url, db.user, db.password
 *
 * public class App {
 *     public static void main(String[] args) {
 *         try {
 *             ProdutoDAO dao = new ProdutoDAO();
 *             // INSERT
 *             Produto p = new Produto(1, "Portátil", 899.99, 10);
 *             dao.insert(p);
 *             // SELECT ALL
 *             dao.getAll().forEach(System.out::println);
 *             // UPDATE
 *             p.setPrezo(799.99);
 *             dao.update(p);
 *             // DELETE
 *             dao.delete(p);
 *         } catch (SQLException e) {
 *             e.printStackTrace();
 *         }
 *     }
 * }
 *
 * Pasos para crear un DAO para outra táboa:
 * 1) Define a túa clase modelo (por exemplo Cliente).
 * 2) Crea ClienteDAO extends GenericDAO<Cliente>.
 * 3) No constructor chama super("NomeTabla", new String[]{"col1","col2",...}).
 * 4) Implementa mapRow(), setParams(), getKey().
 */

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class GenericDAO<T> {

    /** Nome da táboa en MySQL (debe coincidir co db.properties para URL). */
    private final String tableName;
    /** Array de columnas: columns[0] é a clave primaria. */
    private final String[] columns;

    /**
     * @param tableName nome da táboa en MySQL
     * @param columns   array de columnas na orde que usarás nas cláusulas SQL
     */
    protected GenericDAO(String tableName, String[] columns) {
        this.tableName = tableName;
        this.columns   = columns;
    }

    /**
     * Convierte a fila actual do ResultSet nun obxecto T.
     * @param rs ResultSet posicionado nunha fila válida
     * @return   instancia de T con valores lidos do ResultSet
     */
    protected abstract T mapRow(ResultSet rs) throws SQLException;

    /**
     * Enlaza os parámetros no PreparedStatement:
     * - if includeKey=false: enlaza columnas[1..end] para INSERT
     * - if includeKey=true: enlaza columnas[1..end] e logo columnas[0] para UPDATE
     * @param ps          PreparedStatement con "?" na orde de columns[]
     * @param obj         obxecto a gravar ou actualizar
     * @param includeKey  indicar se engadir ao final a clave primaria
     * @return número total de parámetros enlazados
     */
    protected abstract int setParams(PreparedStatement ps, T obj, boolean includeKey)
            throws SQLException;

    /**
     * Devolve o valor da clave primaria do obxecto (correspondente a columns[0]).
     */
    protected abstract Object getKey(T obj);

    /** Obtén unha conexión MySQL usando ConexionDB (db.properties). */
    private Connection getConnection() throws SQLException {
        return ConexionDB.getConexion();
    }

    /** Insire un novo rexistro na táboa. */
    public void insert(T obj) throws SQLException {
        // Construír SQL: INSERT INTO tableName (col1,col2,...) VALUES (?,?,...)
        String cols = String.join(", ", columns);
        String phs  = String.join(", ", java.util.Collections.nCopies(columns.length, "?"));
        String sql  = String.format("INSERT INTO %s (%s) VALUES (%s)", tableName, cols, phs);

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            setParams(ps, obj, false);
            ps.executeUpdate();
        }
    }

    /** Actualiza un rexistro existente (WHERE col1 = ?). */
    public void update(T obj) throws SQLException {
        // Construír SET parte: "col2 = ?, col3 = ?, ..."
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < columns.length; i++) {
            sb.append(columns[i]).append(" = ?");
            if (i < columns.length - 1) sb.append(", ");
        }
        String sql = String.format("UPDATE %s SET %s WHERE %s = ?", tableName, sb, columns[0]);

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            setParams(ps, obj, true);
            int n = ps.executeUpdate();
            if (n == 0) {
                throw new SQLException(
                    String.format("Non existe rexistro en %s con %s = %s",
                                  tableName, columns[0], getKey(obj))
                );
            }
        }
    }

    /** Elimina un rexistro (WHERE col1 = ?). */
    public void delete(T obj) throws SQLException {
        String sql = String.format("DELETE FROM %s WHERE %s = ?", tableName, columns[0]);
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setObject(1, getKey(obj));
            int n = ps.executeUpdate();
            if (n == 0) {
                throw new SQLException(
                    String.format("Non existe rexistro en %s con %s = %s",
                                  tableName, columns[0], getKey(obj))
                );
            }
        }
    }

    /** Devolve todos os rexistros da táboa. */
    public List<T> getAll() throws SQLException {
        String sql = String.format("SELECT * FROM %s", tableName);
        List<T> list = new ArrayList<>();

        try (Connection c = getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        }

        return list;
    }
}
