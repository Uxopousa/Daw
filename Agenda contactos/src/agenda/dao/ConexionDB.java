package agenda.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Utilidad para obtener conexiones JDBC usando los parámetros definidos
 * en bd.properties, que debe estar en la ruta proyecto-raíz/src/bd.properties.
 */
public final class ConexionDB {
    private static final String PROPERTIES_PATH = "src/bd.properties";
    private static final Properties PROPS = new Properties();

    // Carga bd.properties UNA SOLA VEZ al inicializar la clase
    static {
        try (FileInputStream fis = new FileInputStream(PROPERTIES_PATH)) {
            PROPS.load(fis);
        } catch (IOException e) {
            throw new ExceptionInInitializerError(
                "No se pudo cargar " + PROPERTIES_PATH + ": " + e.getMessage()
            );
        }
    }

    private ConexionDB() { /* utilitario no instanciable */ }

    /**
     * Devuelve una conexión JDBC configurada según bd.properties.
     *
     * @return Conexión a la base de datos.
     * @throws DAOException Si falta la propiedad url o hay error al conectar.
     */
    public static Connection getConexion() throws DAOException {
        String url  = PROPS.getProperty("url");
        String user = PROPS.getProperty("usuario");
        String pass = PROPS.getProperty("contrasinal");

        if (url == null || url.isBlank()) {
            throw new DAOException("Propiedad 'url' no definida en " + PROPERTIES_PATH);
        }

        try {
            return DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            throw new DAOException("Error estableciendo conexión JDBC: " + e.getMessage(), e);
        }
    }
}
