package org.simplecrud.repository;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    Optional<T> get(long id);
    List<T> getAll();
    long save(T t);
    boolean update(T t);
    boolean delete(T t);
}
