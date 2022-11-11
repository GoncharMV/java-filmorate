package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
// {"id": 2,"email":"n-lllllllla@m-e","login":"jkj","name":"name","birthdate":"2000-11-11"}
@RestController
public class UserController {
    Map<Integer, User> userMap = new HashMap<>();

    @GetMapping("/users")
    public List<User> findAllUsers() {
        return new ArrayList<>(userMap.values());
    }

    @PostMapping("/users") //Для методов POST /post и POST /users добавьте логирование объектов, которые будут сохраняться.
    public User addUser(@RequestBody User user) {
        isUserValid(user);
        if (user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        userMap.put(user.getId(), user);
        return user;
    }

    @PutMapping("/users")
    public User updateUser(@RequestBody User newUser) {
        isUserValid(newUser);
        if (newUser.getName().isEmpty()) {
            newUser.setName(newUser.getLogin());
        }
        userMap.replace(newUser.getId(), newUser);
        return newUser;
    }


    private void isUserValid(User user) {
        if (user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            throw new ValidationException("bad login");
        }
        if (user.getBirthdate().isAfter(LocalDate.of(2022,11,11))) {
            throw new ValidationException("bad birthdate");
        }
        if (user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            throw new ValidationException("bad email");
        }
    }
}
