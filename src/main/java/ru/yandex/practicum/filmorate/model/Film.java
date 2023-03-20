package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
    public class Film {
        private Long id;
        @NotBlank(message = "name should not be null or blank")
        private String name;
        @Size(max = 200, message = "description should be less then 200")
        private String description;
        private LocalDate releaseDate;
        @PositiveOrZero(message = "duration should not be negative")
        private int duration;
        private Mpa mpa;
        private LinkedHashSet<Genre> genres = new LinkedHashSet<>();
        @JsonIgnore
        private final Set<Long> likes = new HashSet<>();
        private int rate = 0;

    }
