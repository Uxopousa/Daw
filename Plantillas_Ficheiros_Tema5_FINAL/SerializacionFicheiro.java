import java.io.*;

public class SerializacionFicheiro {
    public static final String RUTA = "obxecto.ser";

    public static void main(String[] args) {
        // ✍ Serializar obxecto
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(RUTA))) {
            Usuario u = new Usuario("ana", 25);
            oos.writeObject(u);
            System.out.println("✅ Usuario serializado");
        } catch (IOException e) {
            System.err.println("❌ Erro ao serializar: " + e.getMessage());
        }

        // 📖 Deserializar obxecto
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(RUTA))) {
            Usuario u2 = (Usuario) ois.readObject();
            System.out.println("📄 Usuario lido: " + u2);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("❌ Erro ao deserializar: " + e.getMessage());
        }
    }
}

class Usuario implements Serializable {
    private String nome;
    private int idade;

    public Usuario(String nome, int idade) {
        this.nome = nome;
        this.idade = idade;
    }

    public String toString() {
        return "Usuario{" + nome + ", " + idade + "}";
    }
}
