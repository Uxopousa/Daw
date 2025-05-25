/**
 * Produto.java
 *
 * Clase modelo para representar un rexistro da táboa "Produtos".
 * Esta é a clase que usarás co teu ProdutoDAO.
 */
public class Produto {
    
    private int id;
    private String nome;
    private double prezo;
    private int cantidade;

    /**
     * Constructor completo de Produto.
     *
     * @param id         identificador único do produto
     * @param nome       nome do produto
     * @param prezo      prezo do produto
     * @param cantidade  cantidade dispoñible
     */
    public Produto(int id, String nome, double prezo, int cantidade) {
        this.id = id;
        this.nome = nome;
        this.prezo = prezo;
        this.cantidade = cantidade;
    }

    /** @return o ID do produto */
    public int getId() {
        return id;
    }

    /** @param id novo ID do produto */
    public void setId(int id) {
        this.id = id;
    }

    /** @return o nome do produto */
    public String getNome() {
        return nome;
    }

    /** @param nome novo nome do produto */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /** @return o prezo do produto */
    public double getPrezo() {
        return prezo;
    }

    /** @param prezo novo prezo do produto */
    public void setPrezo(double prezo) {
        this.prezo = prezo;
    }

    /** @return a cantidade dispoñible */
    public int getCantidade() {
        return cantidade;
    }

    /** @param cantidade nova cantidade dispoñible */
    public void setCantidade(int cantidade) {
        this.cantidade = cantidade;
    }

    /**
     * Representación en String do obxecto Produto,
     * útil para depuración e impresión en consola.
     */
    @Override
    public String toString() {
        return String.format(
            "Produto[ID=%d, Nome='%s', Prezo=%.2f, Cantidade=%d]",
            id, nome, prezo, cantidade
        );
    }

    /**
     * Igualdade baseada no ID do produto.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Produto)) return false;
        Produto other = (Produto) obj;
        return this.id == other.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
