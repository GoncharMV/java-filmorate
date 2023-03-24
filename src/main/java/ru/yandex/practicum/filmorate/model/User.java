package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@NoArgsConstructor
@Getter
@Setter
@Builder
public class User {

    private Long id;
    private String name;
    @NotBlank(message = "login should not be null or blank")
    private String login;
    @Email(message = "invalid email")
    private String email;
    @Past(message = "invalid birthday")
    private LocalDate birthday;
    @JsonIgnore
    private final Set<Long> friends = new HashSet<>();

    public User(Long id, String name, String login, @NotNull String email,  LocalDate birthday) {
        this.id = id;
        this.email = email;
        this.login = login;
        if (name != null && !name.equals("")) {
            this.name = name;
        } else {
            this.name = login;
        }
        this.birthday = birthday;
    }


}
