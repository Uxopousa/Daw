// ProdutoDAO.java

/**
 * ProdutoDAO.java
 *
 * Exemplo de subclase de GenericDAO para a táboa "Produtos".
 * CAMBIAR estes elementos se precisas outra táboa:
 *  - TABLE  : nome da táboa
 *  - COLS   : columnas na orde de MySQL (col[0] = pk)
 *  - mapRow : como construir un Produto dende un ResultSet
 *  - setParams : como enlazar un Produto nun PreparedStatement
 *  - getKey : como extraer a clave primaria de Produto
 */
import java.sql.*;
import java.util.List;

public class ProdutoDAO extends GenericDAO<Produto> {

    private static final String TABLE = "Produtos";
    private static final String[] COLS = {"ID", "Nome", "Prezo", "Cantidade"};

    public ProdutoDAO() {
        super(TABLE, COLS);
    }

    @Override
    protected Produto mapRow(ResultSet rs) throws SQLException {
        // Lemos cada columna polo seu nome
        return new Produto(
            rs.getInt("ID"),
            rs.getString("Nome"),
            rs.getDouble("Prezo"),
            rs.getInt("Cantidade")
        );
    }

    @Override
    protected int setParams(PreparedStatement ps, Produto p, boolean includeKey)
            throws SQLException {
        // Enlazamos columnas[1..3] → Nome, Prezo, Cantidade
        ps.setString(1, p.getNome());
        ps.setDouble(2, p.getPrezo());
        ps.setInt(3, p.getCantidade());
        if (includeKey) {
            // Engadimos o ID como último parámetro para UPDATE
            ps.setInt(4, p.getId());
            return 4;
        }
        return 3;
    }

    @Override
    protected Object getKey(Produto p) {
        // O ID é a clave primaria (columns[0])
        return p.getId();
    }
}
