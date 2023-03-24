package ru.yandex.practicum.filmorate.storage;

import java.util.Collection;

public interface Storage<T> {

    Collection<T> getAll();
    T get(Long id);
    T create(T data);
    T update(T data);
    void delete(Long id);
}
