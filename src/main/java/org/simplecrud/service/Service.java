package org.simplecrud.service;

import java.util.Optional;

public interface Service <T> {
//    Optional<T> getById(long id)
    Optional<T> get(long id);
//    List<T> getAll();
    long save(T t);
    void update(T t);
    void delete(T t);
    void delete(long id);
}
