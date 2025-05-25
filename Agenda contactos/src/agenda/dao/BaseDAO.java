/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package agenda.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Clase base para DAOs que proporciona la conexión a la base de datos
 * y define las operaciones CRUD genéricas.
 *
 * @param <T> Tipo de entidad gestionada por el DAO.
 */
public abstract class BaseDAO<T> {

    /**
     * Obtiene una conexión JDBC desde la clase ConexionDB.
     *
     * @return Conexión activa.
     * @throws DAOException si no se puede obtener la conexión.
     * @throws java.io.IOException
     */
    protected Connection getConexion() throws DAOException, IOException {
        return ConexionDB.getConexion();
    }

    /**
     * Inserta la entidad en la base de datos.
     *
     * @param entidad Entidad a insertar.
     * @throws DAOException si ocurre un error en la operación.
     */
    public abstract void insertar(T entidad) throws DAOException;

    /**
     * Recupera todas las entidades de la tabla.
     *
     * @return Lista de entidades.
     * @throws DAOException si ocurre un error en la consulta.
     */
    public abstract List<T> buscarTodos() throws DAOException;

    /**
     * Recupera una entidad por su identificador.
     *
     * @param id Identificador de la entidad.
     * @return Entidad encontrada, o {@code null} si no existe.
     * @throws DAOException si ocurre un error en la consulta.
     */
    public abstract T buscarPorId(String id) throws DAOException;

    /**
     * Actualiza los datos de una entidad existente.
     *
     * @param entidad Entidad con los datos actualizados.
     * @throws DAOException si ocurre un error en la operación.
     */
    public abstract void actualizar(T entidad) throws DAOException;

    /**
     * Elimina una entidad por su identificador.
     *
     * @param id Identificador de la entidad a eliminar.
     * @throws DAOException si ocurre un error en la operación.
     */
    public abstract void eliminarPorId(String id) throws DAOException;
}
