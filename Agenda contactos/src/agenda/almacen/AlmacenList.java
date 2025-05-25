package agenda.almacen;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementación genérica de {@link Almacen} usando un {@link ArrayList}.
 * Proporciona operaciones de alta, baja y búsqueda con validación y manejo
 * de errores mediante {@link AlmacenException}.
 *
 * @param <T> Tipo de objetos a almacenar.
 */
public class AlmacenList<T> implements Almacen<T> {
    /** Lista interna que guarda los objetos. */
    private final List<T> lista = new ArrayList<>();

    /**
     * Añade un objeto al almacén.
     *
     * @param obj Objeto a añadir (no puede ser {@code null} ni duplicado).
     * @return El mismo objeto que se añadió.
     * @throws AlmacenException Si {@code obj} es {@code null} o ya existe.
     */
    @Override
    public T add(T obj) throws AlmacenException {
        if (obj == null) {
            throw new AlmacenException("No se puede añadir null");
        }
        if (lista.contains(obj)) {
            throw new AlmacenException("El objeto ya existe en el almacén");
        }
        lista.add(obj);
        return obj;
    }

    /**
     * Elimina un objeto del almacén.
     *
     * @param obj Objeto a eliminar (no puede ser {@code null} y debe existir).
     * @return El mismo objeto que se eliminó.
     * @throws AlmacenException Si {@code obj} es {@code null} o no existe.
     */
    @Override
    public T del(T obj) throws AlmacenException {
        if (obj == null) {
            throw new AlmacenException("No se puede eliminar null");
        }
        boolean removed = lista.remove(obj);
        if (!removed) {
            throw new AlmacenException("El objeto no existe en el almacén");
        }
        return obj;
    }

    /**
     * Busca todos los objetos que cumplan el filtro dado.
     *
     * @param f Filtro para validar cada elemento; si es {@code null}, devuelve lista vacía.
     * @return Lista de objetos que pasan el filtro.
     */
    @Override
    public List<T> search(Filtro<T> f) {
        List<T> resultados = new ArrayList<>();
        if (f == null) {
            return resultados;
        }
        for (T elemento : lista) {
            if (f.isValid(elemento)) {
                resultados.add(elemento);
            }
        }
        return resultados;
    }
}

