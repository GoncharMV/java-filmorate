package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.*;

@RestController
@Slf4j
public class UserController {
    Map<Integer, User> users = new HashMap<>();
    private final static String USER_STR = "user";
    private final static String INVALID_STR = "invalid";

    @GetMapping("/users")
    public Collection<User> findAllUsers() {
        return users.values();
    }

    @PostMapping("/users")
    public User addUser(@RequestBody User user) {
        isUserValid(user);
        user.setId(users.size() + 1);
        String name = user.getName();
        if (name == null) {
            user.setName(user.getLogin());
        }
        users.put(user.getId(), user);
        log.info("Users amount: {}", users.size());
        return user;
    }

    @PutMapping("/users")
    public User updateUser(@RequestBody User newUser) {
        isUserValid(newUser);
        if (newUser.getName().isEmpty()) {
            newUser.setName(newUser.getLogin());
        }
        if (!users.containsKey(newUser.getId())) {
            throw new ValidationException(USER_STR + " id " + INVALID_STR);
        }
        users.replace(newUser.getId(), newUser);
        log.info("User " + newUser.getId() + " updated");
        return newUser;
    }

    private void isUserValid(User user) {
        if (user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            throw new ValidationException(USER_STR + " login " + INVALID_STR);
        }
        if (user.getBirthday().isAfter(LocalDate.of(2022,11,11))) {
            throw new ValidationException(USER_STR + " birthday " + INVALID_STR);
        }
        if (user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            throw new ValidationException(USER_STR + " email " + INVALID_STR);
        }
    }
}
