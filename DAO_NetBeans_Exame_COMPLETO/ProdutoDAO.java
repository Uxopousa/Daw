import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO de exemplo: ProdutoDAO que herda de BaseDAO<Produto>
 */
public class ProdutoDAO extends BaseDAO<Produto> {

    @Override
    public void insertar(Produto p) {
        String sql = "INSERT INTO Produtos (id, nome, prezo, cantidade) VALUES (?, ?, ?, ?)";
        try (Connection con = getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, p.getId());
            ps.setString(2, p.getNome());
            ps.setDouble(3, p.getPrezo());
            ps.setInt(4, p.getCantidade());
            ps.executeUpdate();
            System.out.println("✅ Produto inserido: " + p);
        } catch (Exception e) {
            System.out.println("❌ Erro ao inserir produto: " + e.getMessage());
        }
    }

    @Override
    public List<Produto> buscarTodos() {
        List<Produto> lista = new ArrayList<>();
        String sql = "SELECT * FROM Produtos";
        try (Connection con = getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Produto p = new Produto(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getDouble("prezo"),
                    rs.getInt("cantidade")
                );
                lista.add(p);
            }
        } catch (Exception e) {
            System.out.println("❌ Erro ao buscar produtos: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public Produto buscarPorId(int id) {
        String sql = "SELECT * FROM Produtos WHERE id=?";
        try (Connection con = getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Produto(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getDouble("prezo"),
                        rs.getInt("cantidade")
                    );
                }
            }
        } catch (Exception e) {
            System.out.println("❌ Erro ao buscar produto: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void actualizar(Produto p) {
        String sql = "UPDATE Produtos SET nome=?, prezo=?, cantidade=? WHERE id=?";
        try (Connection con = getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getNome());
            ps.setDouble(2, p.getPrezo());
            ps.setInt(3, p.getCantidade());
            ps.setInt(4, p.getId());
            int filas = ps.executeUpdate();
            if (filas > 0) {
                System.out.println("✅ Produto actualizado: " + p);
            } else {
                System.out.println("⚠️ Produto non atopado con ID " + p.getId());
            }
        } catch (Exception e) {
            System.out.println("❌ Erro ao actualizar produto: " + e.getMessage());
        }
    }

    @Override
    public void eliminarPorId(int id) {
        String sql = "DELETE FROM Produtos WHERE id=?";
        try (Connection con = getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            int filas = ps.executeUpdate();
            if (filas > 0) {
                System.out.println("🗑 Produto eliminado con ID " + id);
            } else {
                System.out.println("⚠️ Produto non atopado con ID " + id);
            }
        } catch (Exception e) {
            System.out.println("❌ Erro ao eliminar produto: " + e.getMessage());
        }
    }
}
