package agenda.operations;

import agenda.almacen.Almacen;
import agenda.almacen.AlmacenException;
import agenda.almacen.AlmacenList;
import agenda.dao.ContactoDAO;
import agenda.dao.DAOException;
import agenda.modelo.Contacto;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Proporciona las operaciones de gestión de contactos en memoria,
 * de exportación/copia de seguridad a fichero y sincronización con BD.
 */
public class AgendaOperations {
    /** Almacén genérico en memoria de contactos. */
    private final Almacen<Contacto> almacen;
    /** DAO para persistencia en base de datos (puede ser null si no se usa). */
    private final ContactoDAO contactoDAO;

    /**
     * Crea una AgendaOperations que sólo trabaja en memoria.
     */
    public AgendaOperations() {
        this.almacen = new AlmacenList<>();
        this.contactoDAO = null;
    }

    /**
     * Crea una AgendaOperations que también puede sincronizar con BD.
     *
     * @param contactoDAO DAO a usar para carga/guardado en base de datos.
     * @throws NullPointerException si contactoDAO es null.
     */
    public AgendaOperations(ContactoDAO contactoDAO) {
        this.almacen = new AlmacenList<>();
        this.contactoDAO = Objects.requireNonNull(contactoDAO, "DAO no puede ser null");
    }

    /**
     * Da de alta un nuevo contacto en memoria.
     *
     * @param contacto Contacto a añadir (no puede ser null ni duplicado).
     * @throws AlmacenException Si el contacto es null o ya existe.
     */
    public void altaContacto(Contacto contacto) throws AlmacenException {
        if (contacto == null) {
            throw new IllegalArgumentException("El contacto no puede ser null");
        }
        almacen.add(contacto);
    }

    /**
     * Modifica un contacto existente en memoria (identificado por su ID).
     *
     * @param contacto Contacto con ID ya existente y nuevos datos.
     * @throws AlmacenException Si no existe un contacto con ese ID.
     */
    public void modificarContacto(Contacto contacto) throws AlmacenException {
        if (contacto == null) {
            throw new IllegalArgumentException("El contacto no puede ser null");
        }
        List<Contacto> encontrados = almacen.search(c -> c.getId().equals(contacto.getId()));
        if (encontrados.isEmpty()) {
            throw new AlmacenException("No existe ningún contacto con ID " + contacto.getId());
        }
        almacen.del(encontrados.get(0));
        almacen.add(contacto);
    }

    /**
     * Elimina un contacto por su ID en memoria.
     *
     * @param id Identificador de 4 dígitos del contacto.
     * @throws AlmacenException Si no existe ningún contacto con ese ID.
     */
    public void eliminarContacto(String id) throws AlmacenException {
        Optional<Contacto> opc = almacen.search(c -> c.getId().equals(id)).stream().findFirst();
        if (opc.isEmpty()) {
            throw new AlmacenException("No existe ningún contacto con ID " + id);
        }
        almacen.del(opc.get());
    }

    /**
     * Lista todos los contactos en memoria, ordenados por ID.
     *
     * @return Lista inmodificable de todos los contactos.
     */
    public List<Contacto> listarContactos() {
        List<Contacto> copia = almacen.search(c -> true);
        copia.sort(Comparator.comparing(Contacto::getId));
        return Collections.unmodifiableList(copia);
    }

    /**
     * Busca contactos en memoria cuyo nombre contenga el texto dado (case-insensitive).
     *
     * @param texto Subcadena a buscar en el nombre.
     * @return Lista inmodificable de contactos que coinciden.
     */
    public List<Contacto> buscarPorNombre(String texto) {
        if (texto == null || texto.isBlank()) {
            return Collections.emptyList();
        }
        String minus = texto.toLowerCase();
        List<Contacto> resultados = almacen.search(
            c -> c.getNombre().toLowerCase().contains(minus)
        );
        resultados.sort(Comparator.comparing(Contacto::getNombre));
        return Collections.unmodifiableList(resultados);
    }

    /**
     * Lista únicamente los contactos marcados como favoritos en memoria, ordenados por nombre.
     *
     * @return Lista inmodificable de contactos favoritos.
     */
    public List<Contacto> listarFavoritos() {
        List<Contacto> favs = almacen.search(Contacto::isFavorito);
        favs.sort(Comparator.comparing(Contacto::getNombre));
        return Collections.unmodifiableList(favs);
    }

    /**
     * Exporta todos los contactos en memoria a un fichero CSV con cabecera:
     * ID,Nombre,Teléfono,Favorito
     *
     * @param path Ruta del fichero CSV a generar.
     * @throws IOException Si ocurre un error de E/S.
     */
    public void exportarCsv(Path path) throws IOException {
        try (PrintWriter pw = new PrintWriter(Files.newBufferedWriter(path))) {
            pw.println("ID,Nombre,Teléfono,Favorito");
            for (Contacto c : almacen.search(c -> true)) {
                pw.printf("%s,%s,%s,%b%n",
                    c.getId(), c.getNombre(), c.getTelefono(), c.isFavorito()
                );
            }
        }
    }

    /**
     * Copia el fichero CSV de contactos en memoria a una copia de seguridad.
     *
     * @param origen  Ruta del fichero original.
     * @param destino Ruta del fichero de backup.
     * @throws IOException Si ocurre un error de E/S.
     */
    public void copiarBackup(Path origen, Path destino) throws IOException {
        Files.copy(origen, destino, StandardCopyOption.REPLACE_EXISTING);
    }

    /**
     * Carga todos los contactos desde la base de datos usando el DAO.
     *
     * @return Lista de contactos obtenidos de BD.
     * @throws DAOException Si el DAO no fue configurado o ocurre un error SQL.
     */
    public List<Contacto> cargarDesdeBD() throws DAOException {
        if (contactoDAO == null) {
            throw new DAOException("ContactoDAO no configurado en AgendaOperations");
        }
        return contactoDAO.buscarTodos();
    }

    /**
     * Guarda una lista de contactos en la base de datos: inserta los nuevos
     * y actualiza los existentes, según su ID.
     *
     * @param contactos Lista de contactos a sincronizar con BD.
     * @throws DAOException Si el DAO no fue configurado o ocurre un error SQL.
     */
    public void guardarEnBD(List<Contacto> contactos) throws DAOException {
        if (contactoDAO == null) {
            throw new DAOException("ContactoDAO no configurado en AgendaOperations");
        }
        for (Contacto c : contactos) {
            Contacto existente = contactoDAO.buscarPorId(c.getId());
            if (existente == null) {
                contactoDAO.insertar(c);
            } else {
                contactoDAO.actualizar(c);
            }
        }
    }

    /**
     * Permite acceder al almacén en memoria (por ejemplo, para pruebas).
     *
     * @return La instancia de Almacen&lt;Contacto&gt; utilizada.
     */
    public Almacen<Contacto> getAlmacen() {
        return almacen;
    }
}
