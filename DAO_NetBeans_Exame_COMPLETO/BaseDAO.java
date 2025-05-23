import java.sql.Connection;
import java.util.List;

/**
 * Clase abstracta xenérica para DAO.
 * Métodos comúns para inserir, buscar, actualizar e eliminar.
 * As subclases implementan a lóxica concreta.
 */
public abstract class BaseDAO<T> {
    public abstract void insertar(T entidade);
    public abstract List<T> buscarTodos();
    public abstract T buscarPorId(int id);
    public abstract void actualizar(T entidade);
    public abstract void eliminarPorId(int id);

    protected Connection getConexion() throws Exception {
        return ConexionDB.getConexion();
    }
}
