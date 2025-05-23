import java.io.*;

/**
 * Exemplo completo de uso de RandomAccessFile con rexistros de lonxitude fixa.
 * Cada rexistro contén:
 * - ID (int, 4 bytes)
 * - Nome (String de 20 caracteres, 40 bytes)
 * - Estado (String de 5 caracteres, 10 bytes)
 * TOTAL: 4 + 40 + 10 = 54 bytes por rexistro
 */

public class AccesoAleatorio_COMPLETO {
    public static final String RUTA = "usuarios.dat";
    public static final int REXISTRO_BYTES = 54;

    public static void main(String[] args) {
        // ✍ Crear rexistros
        try (RandomAccessFile raf = new RandomAccessFile(RUTA, "rw")) {
            escribirRexistro(raf, 0, 1, "ana", "activo");
            escribirRexistro(raf, 1, 2, "bruno", "inact");
            escribirRexistro(raf, 2, 3, "carla", "activo");
            System.out.println("✅ Rexistros escritos.");
        } catch (IOException e) {
            System.err.println("❌ Erro ao escribir: " + e.getMessage());
        }

        // 📖 Ler rexistro 2
        try (RandomAccessFile raf = new RandomAccessFile(RUTA, "r")) {
            lerRexistro(raf, 1); // segundo rexistro (posición 1)
        } catch (IOException e) {
            System.err.println("❌ Erro ao ler: " + e.getMessage());
        }
    }

    // 📦 Método para escribir un rexistro na posición indicada
    public static void escribirRexistro(RandomAccessFile raf, int pos, int id, String nome, String estado) throws IOException {
        raf.seek(pos * REXISTRO_BYTES);

        raf.writeInt(id); // 4 bytes

        String nomeFix = formatString(nome, 20); // 20 chars = 40 bytes
        raf.writeChars(nomeFix);

        String estadoFix = formatString(estado, 5); // 5 chars = 10 bytes
        raf.writeChars(estadoFix);
    }

    // 🔍 Método para ler un rexistro completo da posición indicada
    public static void lerRexistro(RandomAccessFile raf, int pos) throws IOException {
        raf.seek(pos * REXISTRO_BYTES);

        int id = raf.readInt();

        char[] nomeChars = new char[20];
        for (int i = 0; i < nomeChars.length; i++) {
            nomeChars[i] = raf.readChar(); // cada char = 2 bytes
        }
        String nome = new String(nomeChars).trim();

        char[] estadoChars = new char[5];
        for (int i = 0; i < estadoChars.length; i++) {
            estadoChars[i] = raf.readChar();
        }
        String estado = new String(estadoChars).trim();

        System.out.printf("📄 Rexistro lido → ID: %d | Nome: %s | Estado: %s%n", id, nome, estado);
    }

    // 🧰 Formatear unha cadea a lonxitude fixa, recheando con espazos
    public static String formatString(String s, int len) {
        StringBuilder sb = new StringBuilder(s);
        while (sb.length() < len) {
            sb.append(' ');
        }
        return sb.substring(0, len);
    }
}
