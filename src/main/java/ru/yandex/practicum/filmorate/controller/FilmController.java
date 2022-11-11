package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.*;

@RestController
@Slf4j
public class FilmController {
    Map<Integer, Film> films = new HashMap<>();
    private final static String FILM_STR = "Film";
    private final static String INVALID_STR = "invalid";

    @GetMapping("/films")
    public Collection<Film> findAllFilms() {
        return films.values();
    }

    @PostMapping("/films")
    public Film postFilm(@RequestBody Film film) {
        isFilmValid(film);
        film.setId(films.size() + 1);
        films.put(film.getId(), film);
        log.info("Film amount: {}", films.size());
        return film;
    }

    @PutMapping("/films")
    public Film updateFilm(@RequestBody Film film) {
        isFilmValid(film);
        if (!films.containsKey(film.getId())) {
            throw new ValidationException(FILM_STR + " id " + INVALID_STR);
        }
        films.replace(film.getId(), film);
        log.info("Film " + film.getId() + " updated");
        return film;
    }

    private void isFilmValid(Film film) {
        if (film.getName().isBlank() || film.getName() == null) {
            throw new ValidationException(FILM_STR + " name " + INVALID_STR);
        }
        if (film.getDescription().length() > 200) {
            throw new ValidationException(FILM_STR + " description " + INVALID_STR);
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895,12,28))) {
            throw new ValidationException(FILM_STR + " release date " + INVALID_STR);
        }
        if (film.getDuration() <= 0) {
            throw new ValidationException(FILM_STR + " duration " + INVALID_STR);
        }

    }
}
