package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
    public class Film {
        private Long id;
        @NotBlank(message = "name should not be null or blank")
        private final String name;
        @Size(max = 200, message = "description should be less then 200")
        private final String description;
        private final LocalDate releaseDate;
        @PositiveOrZero(message = "duration should not be negative")
        private final int duration;
    //    @JsonIgnore
        private Set<Long> likes = new HashSet<>();
     //   @JsonIgnore
        private long rate = 0;

        public void addLike(Long userId) {
            likes.add(userId);
            rate = likes.size();
        }

        public void removeLike(Long userId) {
            likes.remove(userId);
            rate = likes.size();
        }
    }
