
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Clase ValidarFecha
 * 
 * Esta clase contiene métodos estáticos para validar fechas en diferentes formatos y rangos.
 * Se utiliza la API java.time para facilitar el manejo de fechas.
 */
public class ValidarFecha {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Valida que una fecha tenga el formato correcto (dd/MM/yyyy).
     * 
     * @param fecha Fecha en formato String
     * @return LocalDate Fecha validada
     * @throws IllegalArgumentException si el formato es incorrecto
     */
    public static LocalDate validarFecha(String fecha) {
        try {
            return LocalDate.parse(fecha, FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Fecha no válida (formato: dd/MM/yyyy)");
        }
    }

    /**
     * Valida que una fecha esté dentro de un rango específico.
     * 
     * @param fecha Fecha en formato String
     * @param minimo Fecha mínima permitida
     * @param maximo Fecha máxima permitida
     * @return LocalDate Fecha validada
     * @throws IllegalArgumentException si la fecha está fuera de rango
     */
    public static LocalDate validarFechaEnRango(String fecha, LocalDate minimo, LocalDate maximo) {
        LocalDate fechaParseada = validarFecha(fecha);

        if (fechaParseada.isBefore(minimo) || fechaParseada.isAfter(maximo)) {
            throw new IllegalArgumentException("Fecha fuera de rango");
        }

        return fechaParseada;
    }

    /**
     * Valida que una fecha no sea futura.
     * 
     * @param fecha Fecha en formato String
     * @return LocalDate Fecha validada
     * @throws IllegalArgumentException si la fecha es futura
     */
    public static LocalDate validarFechaPasada(String fecha) {
        LocalDate fechaParseada = validarFecha(fecha);

        if (fechaParseada.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha no puede estar en el futuro");
        }

        return fechaParseada;
    }

    /**
     * Valida que una fecha sea un día hábil (no sábado ni domingo).
     * 
     * @param fecha Fecha en formato String
     * @return LocalDate Fecha validada
     * @throws IllegalArgumentException si la fecha es fin de semana
     */
    public static LocalDate validarDiaHabil(String fecha) {
        LocalDate fechaParseada = validarFecha(fecha);

        if (fechaParseada.getDayOfWeek().getValue() == 6 || fechaParseada.getDayOfWeek().getValue() == 7) {
            throw new IllegalArgumentException("La fecha debe ser un día hábil (no sábado ni domingo)");
        }

        return fechaParseada;
    }

    // ------------------------
    // Ejemplo de uso en la clase Main
    // ------------------------
    public static void main(String[] args) {
        String fecha = "15/03/2025";

        try {
            System.out.println("Fecha válida: " + validarFecha(fecha));

            LocalDate rangoMin = LocalDate.of(2000, 1, 1);
            LocalDate rangoMax = LocalDate.now();
            System.out.println("Fecha en rango: " + validarFechaEnRango(fecha, rangoMin, rangoMax));

            System.out.println("Fecha pasada: " + validarFechaPasada(fecha));

            System.out.println("Día hábil: " + validarDiaHabil(fecha));
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
