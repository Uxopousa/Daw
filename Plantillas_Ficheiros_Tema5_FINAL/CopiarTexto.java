import java.io.*;

public class CopiarTexto {
    public static final String ORIXE = "entrada.txt";
    public static final String DESTINO = "copia.txt";

    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new FileReader(ORIXE));
             BufferedWriter bw = new BufferedWriter(new FileWriter(DESTINO))) {

            String liña;
            while ((liña = br.readLine()) != null) {
                bw.write(liña);
                bw.newLine();
            }
            System.out.println("✅ Copia de texto feita correctamente.");
        } catch (IOException e) {
            System.err.println("❌ Erro ao copiar texto: " + e.getMessage());
        }
    }
}
