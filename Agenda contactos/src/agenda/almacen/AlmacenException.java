/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package agenda.almacen;

/**
 *
 * @author uxopo
 */
/**
 * Excepción genérica para errores en el almacén de objetos.
 */
public class AlmacenException extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     * Crea una excepción sin mensaje ni causa específica.
     */
    public AlmacenException() {
        super("Error en el almacén");
    }

    /**
     * Crea una excepción con mensaje personalizado.
     *
     * @param mensaje Detalle sobre el error en el almacén.
     */
    public AlmacenException(String mensaje) {
        super("Error en el almacén: " + mensaje);
    }

    /**
     * Crea una excepción con causa original.
     *
     * @param causa Excepción que originó este error.
     */
    public AlmacenException(Throwable causa) {
        super("Error en el almacén", causa);
    }

    /**
     * Crea una excepción con mensaje personalizado y causa original.
     *
     * @param mensaje Detalle sobre el error en el almacén.
     * @param causa   Excepción que originó este error.
     */
    public AlmacenException(String mensaje, Throwable causa) {
        super("Error en el almacén: " + mensaje, causa);
    }
}
