package org.simplecrud.service;

public interface Service<T> {
    long save(T t);
}
