/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package agenda.dao;

/**
 *
 * @author uxopo
 */
/**
 * Excepción genérica para errores en la capa de acceso a datos (DAO).
 */
public class DAOException extends Exception {
    private static final long serialVersionUID = 1L;

    /** Crea una DAOException con un mensaje genérico. */
    public DAOException() {
        super("Error en la capa de acceso a datos");
    }

    /**
     * Crea una DAOException con mensaje personalizado.
     *
     * @param message Detalle del error.
     */
    public DAOException(String message) {
        super("Error en la capa de acceso a datos: " + message);
    }

    /**
     * Crea una DAOException con causa original.
     *
     * @param cause Excepción raíz.
     */
    public DAOException(Throwable cause) {
        super("Error en la capa de acceso a datos", cause);
    }

    /**
     * Crea una DAOException con mensaje personalizado y causa original.
     *
     * @param message Detalle del error.
     * @param cause   Excepción raíz.
     */
    public DAOException(String message, Throwable cause) {
        super("Error en la capa de acceso a datos: " + message, cause);
    }
}

