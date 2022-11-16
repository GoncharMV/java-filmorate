package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Data
public class User {
    private Integer id;
    @Email(message = "invalid email")
    private final String email;
    @NotBlank(message = "login should not be null or blank")
    private final String login;
    private String name;
    @Past(message = "invalid birthday")
    private final LocalDate birthday;
}
