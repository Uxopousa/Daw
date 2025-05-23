/**
 * Modelo de exemplo: Produto
 */
public class Produto {
    private int id;
    private String nome;
    private double prezo;
    private int cantidade;

    public Produto(int id, String nome, double prezo, int cantidade) {
        this.id = id;
        this.nome = nome;
        this.prezo = prezo;
        this.cantidade = cantidade;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public double getPrezo() { return prezo; }
    public int getCantidade() { return cantidade; }

    @Override
    public String toString() {
        return "Produto{id=" + id + ", nome='" + nome + "', prezo=" + prezo + ", cantidade=" + cantidade + '}';
    }
}
