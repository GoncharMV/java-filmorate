package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Data
public class User {

    private Long id;
    private String name;
    @NotBlank(message = "login should not be null or blank")
    private final String login;
    @Email(message = "invalid email")
    private final String email;
    @Past(message = "invalid birthday")
    private final LocalDate birthday;
    @JsonIgnore
    private Set<Long> friends = new HashSet<>();


}
