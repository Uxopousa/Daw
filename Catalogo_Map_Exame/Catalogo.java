import java.util.HashMap;
import java.util.Map;

/**
 * Clase xenérica que almacena datos nun Map<K, V>
 */
public class Catalogo<K, V> {
    private Map<K, V> datos;

    public Catalogo() {
        datos = new HashMap<>();
    }

    public void engadir(K clave, V valor) {
        datos.put(clave, valor);
    }

    public V buscar(K clave) {
        return datos.get(clave);
    }

    public void listar() {
        for (Map.Entry<K, V> entrada : datos.entrySet()) {
            System.out.println("🔑 " + entrada.getKey() + " → " + entrada.getValue());
        }
    }
}
