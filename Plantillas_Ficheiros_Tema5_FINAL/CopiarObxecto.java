import java.io.*;

public class CopiarObxecto {
    public static final String ORIXE = "orixinal.ser";
    public static final String DESTINO = "copia.ser";

    public static void main(String[] args) {
        try (FileInputStream fis = new FileInputStream(ORIXE);
             FileOutputStream fos = new FileOutputStream(DESTINO)) {

            fis.transferTo(fos); // dispoñible dende Java 9
            System.out.println("✅ Copia de obxecto serializado feita correctamente.");
        } catch (IOException e) {
            System.err.println("❌ Erro ao copiar obxecto: " + e.getMessage());
        }
    }
}
