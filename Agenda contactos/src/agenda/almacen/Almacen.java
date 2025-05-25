package agenda.almacen;


import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

/**
 *
 * @author uxopo
 */
public interface Almacen<T> {
    T add(T obj) throws AlmacenException;
    T del(T obj) throws AlmacenException;
    List<T> search(Filtro<T> f);
}

