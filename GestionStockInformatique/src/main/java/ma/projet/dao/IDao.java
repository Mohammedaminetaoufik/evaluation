package ma.projet.dao;

import java.util.List;

public interface IDao<T, K> {
    T save(T o);
    T update(T o);
    boolean delete(T o);
    T findById(K k);
    List<T> findAll();
}