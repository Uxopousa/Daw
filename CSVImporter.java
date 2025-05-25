/**
 * CSVImporter.java
 * 
 * Importa un ficheiro CSV a unha táboa MySQL de lonxitude variable.
 */
import java.io.*;
import java.sql.*;
import java.util.Arrays;

public class CSVImporter {
    
    /**
     * Lê o CSV de ruta csvPath, crea a táboa tableName en CVSImports
     * e importa todos os rexistros (todos os campos varchar(128)).
     * 
     * @param csvPath   ruta do ficheiro CSV
     * @param tableName nome da táboa a crear e encher
     * @throws SQLException se falla calquera operación SQL
     * @throws IOException  se falla lectura do CSV
     */
    public void importCSV(String csvPath, String tableName) throws SQLException, IOException {
        // 1. Ler cabeceira
        String[] header;
        try (BufferedReader br = new BufferedReader(new FileReader(csvPath))) {
            String line = br.readLine();
            if (line == null) throw new IOException("CSV baleiro");
            header = line.split(",", -1);
        }
        
        // 2. Crear táboa
        String dropSQL = "DROP TABLE IF EXISTS `" + tableName + "`;";
        StringBuilder create = new StringBuilder("CREATE TABLE `")
            .append(tableName).append("` (");
        for (int i = 0; i < header.length; i++) {
            create.append("`").append(header[i].trim())
                  .append("` VARCHAR(128)");
            if (i < header.length - 1) create.append(", ");
        }
        create.append(");");
        
        try (Connection conn = ConexionBD.getConexion();
             Statement st = conn.createStatement()) {
            st.executeUpdate(dropSQL);
            st.executeUpdate(create.toString());
        }
        
        // 3. Inserir datos
        String placeholders = String.join(",", Arrays.stream(header)
            .map(h -> "?").toArray(String[]::new));
        String insertSQL = "INSERT INTO `" + tableName + "` (" +
            String.join(",", Arrays.stream(header)
                .map(h -> "`" + h.trim() + "`").toArray(String[]::new)) +
            ") VALUES (" + placeholders + ");";
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(insertSQL);
             BufferedReader br = new BufferedReader(new FileReader(csvPath))) {
            
            // saltar cabeceira
            br.readLine();
            String row;
            while ((row = br.readLine()) != null) {
                String[] cols = row.split(",", -1);
                for (int i = 0; i < cols.length; i++) {
                    ps.setString(i+1, cols[i]);
                }
                ps.executeUpdate();
            }
        }
    }
}
