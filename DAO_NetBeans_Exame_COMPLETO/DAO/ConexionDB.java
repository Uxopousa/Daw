import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Clase para establecer conexión coa base de datos.
 * Usa bd.properties (colocar na raíz do proxecto ao lado de build.xml)
 */
public class ConexionDB {
    private static final String PROPERTIES_FILE = "bd.properties";

    public static Connection getConexion() throws SQLException, IOException {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(PROPERTIES_FILE)) {
            props.load(fis);
        }

        String url = props.getProperty("url");
        String usuario = props.getProperty("usuario");
        String contrasinal = props.getProperty("contrasinal");

        return DriverManager.getConnection(url, usuario, contrasinal);
    }
}
