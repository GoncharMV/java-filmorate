package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

public class InMemoryUserStorage implements UserStorage {

    private final Map<Long, User> users = new HashMap<>();

    @Override
    public Collection<User> getAll() {
        return users.values();
    }

    @Override
    public User get(Long id) {
        isUserExists(id);
        return users.get(id);
    }

    @Override
    public User create(User user) {
        user.setId(generateId());
        setLoginAsName(user);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User update(User newUser) {
        setLoginAsName(newUser);
        users.replace(newUser.getId(), newUser);
        return newUser;
    }

    @Override
    public void delete(Long id) {
        isUserExists(id);
        users.remove(id);
    }

    public void isUserExists(Long id) {
        if (!users.containsKey(id)) {
            throw new IllegalArgumentException();
        }
    }

    private Long generateId() {
        return (long) users.size() + 1;
    }

    private void setLoginAsName(User user) {
        String name = user.getName();
        if (name == null || name.isEmpty()) {
            user.setName(user.getLogin());
        }
    }
}
