/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package agenda.dao;

/**
 *
 * @author uxopo
 */
import agenda.modelo.Contacto;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * DAO para la entidad Contacto; provee operaciones CRUD
 * contra la tabla "contactos" con columnas id, nombre, telefono y favorito.
 */
public class ContactoDAO extends BaseDAO<Contacto> {
    private static final String SQL_INSERT      = "INSERT INTO contactos (id, nombre, telefono, favorito) VALUES (?, ?, ?, ?)";
    private static final String SQL_SELECT_ALL  = "SELECT id, nombre, telefono, favorito FROM contactos";
    private static final String SQL_SELECT_BY_ID = "SELECT id, nombre, telefono, favorito FROM contactos WHERE id = ?";
    private static final String SQL_UPDATE      = "UPDATE contactos SET nombre = ?, telefono = ?, favorito = ? WHERE id = ?";
    private static final String SQL_DELETE      = "DELETE FROM contactos WHERE id = ?";

    /**
     * Inserta un nuevo contacto.
     *
     * @param contacto Contacto a insertar (no puede ser null).
     * @throws DAOException si ocurre un error SQL.
     */
    @Override
    public void insertar(Contacto contacto) throws DAOException {
        if (contacto == null) {
            throw new IllegalArgumentException("El contacto no puede ser null");
        }
        try (Connection con = getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_INSERT)) {
            ps.setString(1, contacto.getId());
            ps.setString(2, contacto.getNombre());
            ps.setString(3, contacto.getTelefono());
            ps.setBoolean(4, contacto.isFavorito());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("No se pudo insertar el contacto con ID " + contacto.getId(), e);
        } catch (IOException ex) {
            Logger.getLogger(ContactoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Recupera todos los contactos.
     *
     * @return Lista de contactos (vacía si no hay ninguno).
     * @throws DAOException si ocurre un error SQL.
     */
    @Override
    public List<Contacto> buscarTodos() throws DAOException {
        List<Contacto> lista = new ArrayList<>();
        try (Connection con = getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(SQL_SELECT_ALL)) {
            while (rs.next()) {
                lista.add(new Contacto(
                    rs.getString("id"),
                    rs.getString("nombre"),
                    rs.getString("telefono"),
                    rs.getBoolean("favorito")
                ));
            }
        } catch (SQLException e) {
            throw new DAOException("Error al obtener todos los contactos", e);
        } catch (IOException ex) {
            Logger.getLogger(ContactoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

    /**
     * Busca un contacto por su ID.
     *
     * @param id Identificador del contacto.
     * @return Objeto Contacto o {@code null} si no existe.
     * @throws DAOException si ocurre un error SQL.
     */
    @Override
    public Contacto buscarPorId(String id) throws DAOException {
        try (Connection con = getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_SELECT_BY_ID)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Contacto(
                        rs.getString("id"),
                        rs.getString("nombre"),
                        rs.getString("telefono"),
                        rs.getBoolean("favorito")
                    );
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error al buscar contacto con ID " + id, e);
        } catch (IOException ex) {
            Logger.getLogger(ContactoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Actualiza un contacto existente.
     *
     * @param contacto Contacto con los datos nuevos (no puede ser null).
     * @throws DAOException si ocurre un error SQL o no existe el contacto.
     */
    @Override
    public void actualizar(Contacto contacto) throws DAOException {
        if (contacto == null) {
            throw new IllegalArgumentException("El contacto no puede ser null");
        }
        try (Connection con = getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_UPDATE)) {
            ps.setString(1, contacto.getNombre());
            ps.setString(2, contacto.getTelefono());
            ps.setBoolean(3, contacto.isFavorito());
            ps.setString(4, contacto.getId());
            int filas = ps.executeUpdate();
            if (filas == 0) {
                throw new DAOException("No existe contacto con ID " + contacto.getId());
            }
        } catch (SQLException e) {
            throw new DAOException("No se pudo actualizar el contacto con ID " + contacto.getId(), e);
        } catch (IOException ex) {
            Logger.getLogger(ContactoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Elimina un contacto por su ID.
     *
     * @param id Identificador del contacto.
     * @throws DAOException si ocurre un error SQL o no existe el contacto.
     */
    @Override
    public void eliminarPorId(String id) throws DAOException {
        try (Connection con = getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_DELETE)) {
            ps.setString(1, id);
            int filas = ps.executeUpdate();
            if (filas == 0) {
                throw new DAOException("No existe contacto con ID " + id);
            }
        } catch (SQLException e) {
            throw new DAOException("No se pudo eliminar el contacto con ID " + id, e);
        } catch (IOException ex) {
            Logger.getLogger(ContactoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
