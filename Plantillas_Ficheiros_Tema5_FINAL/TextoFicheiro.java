import java.io.*;

public class TextoFicheiro {
    public static final String RUTA = "ficheiro.txt";

    public static void main(String[] args) {
        // ✍ Escritura de texto
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA))) {
            bw.write("Primeira liña");
            bw.newLine();
            bw.write("Segunda liña");
            System.out.println("✅ Texto escrito correctamente.");
        } catch (IOException e) {
            System.err.println("❌ Erro ao escribir texto: " + e.getMessage());
        }

        // 📖 Lectura de texto
        try (BufferedReader br = new BufferedReader(new FileReader(RUTA))) {
            String liña;
            System.out.println("📄 Contido do ficheiro:");
            while ((liña = br.readLine()) != null) {
                System.out.println(liña);
            }
        } catch (IOException e) {
            System.err.println("❌ Erro ao ler texto: " + e.getMessage());
        }
    }
}
