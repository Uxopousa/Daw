public class Usuario {
    private int numeroSocio;
    private String nome;
    private String apelidos;

    public Usuario(int numeroSocio, String nome, String apelidos) {
        this.numeroSocio = numeroSocio;
        this.nome = nome;
        this.apelidos = apelidos;
    }

    public int getNumeroSocio() { return numeroSocio; }
    public String getNome() { return nome; }
    public String getApelidos() { return apelidos; }

    @Override
    public String toString() {
        return numeroSocio + ": " + apelidos + ", " + nome;
    }
}
