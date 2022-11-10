package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {
    Map<String, User> userMap = new HashMap<>();

    @GetMapping("/users")
    public Map<String, User> findAllUsers() {
        return userMap;
    }

    @PostMapping("/users") //Для методов POST /post и POST /users добавьте логирование объектов, которые будут сохраняться.
    public User addUser(@RequestBody User user) {

        userMap.put(user.getEmail(), user);
        return user;
    }

    @PutMapping("/users")
    public User updateUser(@RequestBody User newUser) {

        userMap.replace(newUser.getEmail(), newUser);
        return newUser;
    }

    /*
создание пользователя;
обновление пользователя;
получение списка всех пользователей. ++


электронная почта не может быть пустой и должна содержать символ @;
логин не может быть пустым и содержать пробелы;
имя для отображения может быть пустым — в таком случае будет использован логин;
дата рождения не может быть в будущем.

*/
}
