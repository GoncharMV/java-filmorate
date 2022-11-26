package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.*;

@RestController
@Slf4j
public class UserController {
    Map<Integer, User> users = new HashMap<>();
    private static final String USER_STR = "user";
    private static final String INVALID_STR = "invalid";

    @GetMapping("/users")
    public Collection<User> findAllUsers() {
        return users.values();
    }

    @PostMapping("/users")
    public User addUser(@Valid @RequestBody User user) {
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
    public User updateUser(@Valid @RequestBody User newUser) {
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
}
