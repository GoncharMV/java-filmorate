package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exceptions.InvalidFilmExeption;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class FilmController {
    List<Film> films = new ArrayList<>();

    @GetMapping("/films")
    public List<Film> findAllFilms() {
        return films;
    }

    @PostMapping("/film")
    public Film postFilm(@RequestBody Film film) {
        if (film.getName().isBlank() || film.getName() == null
            && film.getDescription().length() > 200
            && film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))
            && film.getDuration() <= 0
        ) {
            throw new InvalidFilmExeption("Неверный формат фильма");
        }
        films.add(film);
        log.info("Добавлен новый фильм");
        return film;
    }
    /*
добавление фильма; ++
обновление фильма;
получение всех фильмов ++


название не может быть пустым;
максимальная длина описания — 200 символов;
дата релиза — не раньше 28 декабря 1895 года;
продолжительность фильма должна быть положительной.


*/
}
