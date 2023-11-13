package br.com.fiap.domain.service;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public interface Service<T, U> {

    public List<T> findAll();

    public T findById(U id);

    public T persist(T t);

}
