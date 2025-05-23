import java.io.*;

public class BinarioFicheiro {
    public static final String RUTA = "ficheiro.bin";

    public static void main(String[] args) {
        // ✍ Escritura binaria
        try (FileOutputStream fos = new FileOutputStream(RUTA)) {
            fos.write("Exemplo binario".getBytes());
            System.out.println("✅ Binario escrito correctamente.");
        } catch (IOException e) {
            System.err.println("❌ Erro ao escribir binario: " + e.getMessage());
        }

        // 📖 Lectura binaria
        try (FileInputStream fis = new FileInputStream(RUTA)) {
            int b;
            System.out.println("📄 Contido binario (como texto):");
            while ((b = fis.read()) != -1) {
                System.out.print((char) b);
            }
            System.out.println();
        } catch (IOException e) {
            System.err.println("❌ Erro ao ler binario: " + e.getMessage());
        }
    }
}
