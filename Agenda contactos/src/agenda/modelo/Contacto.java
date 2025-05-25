package agenda.modelo;

/**
 *
 * @author uxopo
 */
import java.util.Objects;

/**
 * Representa un contacto telefónico con un identificador de 4 dígitos,
 * nombre, teléfono y marca de favorito.
 */
public class Contacto {
    private String id;       // ID formateado a 4 dígitos, ej. "0001"
    private String nombre;   // Nombre completo del contacto
    private String telefono; // Número de teléfono del contacto
    private boolean favorito;// Indica si el contacto es favorito

    /**
     * Construye un Contacto (no favorito).
     *
     * @param id       Cadena numérica para el ID; se formatea a 4 dígitos.
     * @param nombre   Nombre completo del contacto.
     * @param telefono Número de teléfono del contacto.
     * @throws IllegalArgumentException Si el id no es un número válido.
     */
    public Contacto(String id, String nombre, String telefono) {
        // 1) Convertir id a entero y validar
        int valorId;
        try {
            valorId = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("ID debe ser un número de hasta 4 dígitos", e);
        }
        // 2) Formatear a 4 dígitos
        this.id = String.format("%04d", valorId);

        this.nombre = nombre;
        this.telefono = telefono;
        this.favorito = false;
    }

    /**
     * Construye un Contacto, indicando además si es favorito.
     *
     * @param id        Cadena numérica para el ID; formateada a 4 dígitos.
     * @param nombre    Nombre completo.
     * @param telefono  Teléfono.
     * @param favorito  true si es favorito; false en caso contrario.
     * @throws IllegalArgumentException Si el id no es un número válido.
     */
    public Contacto(String id, String nombre, String telefono, boolean favorito) {
        this(id, nombre, telefono);
        this.favorito = favorito;
    }

    /** @return ID formateado de 4 dígitos. */
    public String getId() {
        return id;
    }

    /**
     * Cambia el ID del contacto, validando y formateando a 4 dígitos.
     *
     * @param id Nueva cadena numérica para el ID.
     * @throws IllegalArgumentException Si el id no es un número válido.
     */
    public void setId(String id) {
        int valorId;
        try {
            valorId = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("ID debe ser un número de hasta 4 dígitos", e);
        }
        this.id = String.format("%04d", valorId);
    }

    /** @return Nombre del contacto. */
    public String getNombre() {
        return nombre;
    }

    /** @param nombre Nuevo nombre del contacto. */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /** @return Teléfono del contacto. */
    public String getTelefono() {
        return telefono;
    }

    /** @param telefono Nuevo número de teléfono. */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /** @return true si el contacto está marcado como favorito. */
    public boolean isFavorito() {
        return favorito;
    }

    /** @param favorito true para marcar como favorito; false para desmarcar. */
    public void setFavorito(boolean favorito) {
        this.favorito = favorito;
    }

    /**
     * Dos contactos son iguales si comparten el mismo ID.
     *
     * @param o Objeto a comparar.
     * @return true si es otro Contacto con idéntico ID.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contacto)) return false;
        Contacto other = (Contacto) o;
        return id.equals(other.id);
    }

    /** @return Código hash basado en el ID. */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Representación estándar: “[id] nombre – teléfono”
     *
     * @return Cadena formateada.
     */
    @Override
    public String toString() {
        return String.format("[%s] %s – %s", id, nombre, telefono);
    }
}
