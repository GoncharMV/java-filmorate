package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;

@RestController
@Slf4j
public class FilmController {
    Map<Integer, Film> films = new HashMap<>();
    private static final String FILM_STR = "Film";
    private static final String INVALID_STR = "invalid";

    @GetMapping("/films")
    public Collection<Film> findAllFilms() {
        return films.values();
    }

    @PostMapping("/films")
    public Film postFilm(@Valid @RequestBody Film film) {
        isFilmReleaseDateValid(film);
        film.setId(films.size() + 1);
        films.put(film.getId(), film);
        log.info("Film amount: {}", films.size());
        return film;
    }

    @PutMapping("/films")
    public Film updateFilm(@Valid @RequestBody Film film) {
        isFilmReleaseDateValid(film);
        if (!films.containsKey(film.getId())) {
            throw new ValidationException(FILM_STR + " id " + INVALID_STR);
        }
        films.replace(film.getId(), film);
        log.info("Film " + film.getId() + " updated");
        return film;
    }

    private void isFilmReleaseDateValid(Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895,12,28))) {
            throw new ValidationException(FILM_STR + " release date " + INVALID_STR);
        }
    }
}
