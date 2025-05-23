import java.io.*;

public class CopiarBinario {
    public static final String ORIXE = "entrada.bin";
    public static final String DESTINO = "copia.bin";

    public static void main(String[] args) {
        try (FileInputStream fis = new FileInputStream(ORIXE);
             FileOutputStream fos = new FileOutputStream(DESTINO)) {

            int b;
            while ((b = fis.read()) != -1) {
                fos.write(b);
            }
            System.out.println("✅ Copia binaria feita correctamente.");
        } catch (IOException e) {
            System.err.println("❌ Erro ao copiar binario: " + e.getMessage());
        }
    }
}
