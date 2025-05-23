import java.io.*;

public class AccesoAleatorioFicheiro {
    public static final String RUTA = "rexistros.dat";

    public static void main(String[] args) {
        try (RandomAccessFile raf = new RandomAccessFile(RUTA, "rw")) {
            // ✍ Escribir tres números enteiro
            raf.writeInt(111);
            raf.writeInt(222);
            raf.writeInt(333);
            System.out.println("✅ Rexistros escritos");

            // 📖 Ler o segundo rexistro
            raf.seek(4); // 1 int = 4 bytes
            int segundo = raf.readInt();
            System.out.println("🔍 Segundo rexistro: " + segundo);
        } catch (IOException e) {
            System.err.println("❌ Erro en acceso aleatorio: " + e.getMessage());
        }
    }
}
