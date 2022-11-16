package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class Film {
    private Integer id;
    @NotBlank(message = "name should not be null or blank")
    private final String name;
    @Size(max = 200, message = "description should be less then 200")
    private final String description;
    private final LocalDate releaseDate;
    @PositiveOrZero(message = "duration should not be negative")
    private final int duration;
}
