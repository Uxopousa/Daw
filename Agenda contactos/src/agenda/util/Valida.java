package agenda.util;

import java.util.regex.Pattern;

/**
 * Utilidad para validar entradas de texto, identificadores y teléfonos.
 * Proporciona métodos para comprobar nulls, vacíos, formato de ID y de teléfono.
 */
public final class Valida {

    private Valida() {
        // Clase de utilidades no instanciable
    }

    /** Patrón para validar números de teléfono (incluye opcional +prefijo y formatos comunes). */
    private static final Pattern PHONE_PATTERN = Pattern.compile(
        "^(\\+\\d{1,3}( )?)?((\\(\\d{1,3}\\))|\\d{1,4})[- .]?\\d{3,4}[- .]?\\d{4}$"
    );

    /**
     * Verifica que un objeto no sea {@code null}.
     *
     * @param <T> Tipo del objeto.
     * @param obj Objeto a comprobar.
     * @param mensajeError Mensaje de excepción si es {@code null}.
     * @return El mismo objeto, si no es {@code null}.
     * @throws IllegalArgumentException Si {@code obj} es {@code null}.
     */
    public static <T> T requireNonNull(T obj, String mensajeError) {
        if (obj == null) {
            throw new IllegalArgumentException(mensajeError);
        }
        return obj;
    }

    /**
     * Verifica que una cadena no sea {@code null} ni esté vacía tras trim().
     *
     * @param texto Cadena a comprobar.
     * @param mensajeError Mensaje de excepción si es inválida.
     * @return La misma cadena recortada.
     * @throws IllegalArgumentException Si {@code texto} es {@code null} o vacío.
     */
    public static String requireNonEmpty(String texto, String mensajeError) {
        requireNonNull(texto, mensajeError);
        String trimmed = texto.trim();
        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException(mensajeError);
        }
        return trimmed;
    }

    /**
     * Comprueba si una cadena cumple el patrón de teléfono.
     *
     * @param telefono Cadena a validar.
     * @return {@code true} si coincide con el patrón, {@code false} en caso contrario.
     */
    public static boolean isValidPhone(String telefono) {
        if (telefono == null) return false;
        return PHONE_PATTERN.matcher(telefono.trim()).matches();
    }

    /**
     * Valida un teléfono: no nulo, no vacío y formato correcto.
     *
     * @param telefono Cadena a validar.
     * @return La cadena de teléfono recortada.
     * @throws IllegalArgumentException Si es {@code null}, vacía o no cumple el patrón.
     */
    public static String validatePhone(String telefono) {
        String t = requireNonEmpty(telefono, "El teléfono no puede estar vacío");
        if (!PHONE_PATTERN.matcher(t).matches()) {
            throw new IllegalArgumentException(
                "Teléfono no válido. Debe incluir prefijo (opcional) y formato correcto"
            );
        }
        return t;
    }

    /**
     * Comprueba si un identificador es numérico y cabe en 4 dígitos (0–9999).
     *
     * @param id Cadena a validar.
     * @return {@code true} si es un entero entre 0 y 9999, {@code false} en caso contrario.
     */
    public static boolean isValidId(String id) {
        if (id == null) return false;
        try {
            int valor = Integer.parseInt(id.trim());
            return valor >= 0 && valor <= 9999;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Valida un identificador: no nulo, numérico y hasta 4 dígitos.
     * Devuelve el ID formateado con ceros a la izquierda a 4 dígitos.
     *
     * @param id Cadena a validar.
     * @return ID de 4 dígitos, p.ej. "0007", "0423".
     * @throws IllegalArgumentException Si {@code id} es {@code null}, vacío,
     *         no numérico o fuera de rango 0–9999.
     */
    public static String validateId(String id) {
        String s = requireNonEmpty(id, "El ID no puede estar vacío");
        try {
            int valor = Integer.parseInt(s);
            if (valor < 0 || valor > 9999) {
                throw new IllegalArgumentException("El ID debe estar entre 0 y 9999");
            }
            return String.format("%04d", valor);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El ID debe ser un número válido", e);
        }
    }
}
