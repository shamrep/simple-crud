package org.simplecrud.service;

import java.util.List;
import java.util.Optional;

public interface Service <T> {
    Optional<T> get(long id);
//    List<T> getAll();
    long save(T t);
    boolean update(T t);
    boolean delete(T t);
}
