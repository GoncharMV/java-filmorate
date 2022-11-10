package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.Objects;

@Data
public class User {
    private Integer id;
    private String email;
    private String login;
    private String name;
    private LocalDate birthdate;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        User otherUser = (User) obj;
        return Objects.equals(email, otherUser.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

}
