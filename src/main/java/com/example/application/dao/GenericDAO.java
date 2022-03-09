package com.example.application.dao;

import java.util.List;

//Is it an appropriate name for this interface?
public interface GenericDAO <T> {
    List<T> findAll();

    T show(int id);

    void add(T entity);

    void edit(T entity);

    void delete(T entity);
}
