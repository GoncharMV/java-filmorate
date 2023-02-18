package ru.yandex.practicum.filmorate.storage;

import java.util.Collection;

public interface Storage<T> {

    Collection<T> getAll();
    T get(Long id);
    T create(T data);
    T update(T data);
    void delete(Long id);
}

/*
Здравстуй! Я сделала общий интерфейс хранилища, потому что у user and film по сути одинаковые методы.
И я хотела сделать их реализацию в одном абстрактном классе, но не смогла сообразить, как в таком случае генерировать id
потому что сейчас они генерируются в хранилищах тупо по размеру мапы, но это мне кажется не очень хороший вариант.
Мб можешь подсказать что?
 */
